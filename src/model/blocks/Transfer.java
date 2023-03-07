/**
 * Software end-user license agreement.
 *
 * The LICENSE.TXT containing the license is located in the JGPSS project.
 * License.txt can be downloaded here:
 * href="http://www-eio.upc.es/~Pau/index.php?q=node/28
 *
 * NOTICE TO THE USER: BY COPYING, INSTALLING OR USING THIS SOFTWARE OR PART OF
 * THIS SOFTWARE, YOU AGREE TO THE   TERMS AND CONDITIONS OF THE LICENSE AGREEMENT
 * AS IF IT WERE A WRITTEN AGREEMENT NEGOTIATED AND SIGNED BY YOU. THE LICENSE
 * AGREEMENT IS ENFORCEABLE AGAINST YOU AND ANY OTHER LEGAL PERSON ACTING ON YOUR
 * BEHALF.
 * IF, AFTER READING THE TERMS AND CONDITIONS HEREIN, YOU DO NOT AGREE TO THEM,
 * YOU MAY NOT INSTALL THIS SOFTWARE ON YOUR COMPUTER.
 * UPC IS THE OWNER OF ALL THE INTELLECTUAL PROPERTY OF THE SOFTWARE AND ONLY
 * AUTHORIZES YOU TO USE THE SOFTWARE IN ACCORDANCE WITH THE TERMS SET OUT IN
 * THE LICENSE AGREEMENT.
 */
package model.blocks;

import exceptions.BlockNotFound;
import exceptions.FunctionNotFoundException;
import java.util.PriorityQueue;
import lombok.Getter;
import lombok.Setter;
import static model.SNA.evaluate;
import model.entities.Function;
import model.entities.Xact;
import utils.Constants;

/**
 * A class representing the TRANSFER block.
 *
 * @author Pau Fonseca i Casas
 * @author Ezequiel Andujar Montes
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 *
 * A TRANSFER Block causes the Active Transaction to jump to a new Block
 * location.
 */
public class Transfer extends Bloc {

    @Getter
    @Setter
    private String A;
    @Getter
    @Setter
    private String B;
    @Getter
    @Setter
    private String C;
    @Getter
    @Setter
    private String D;
    @Getter
    @Setter
    private PriorityQueue<Xact> BloquedXacts;

    /**
     * String identifying Both TRANSFERs.
     */
    public static final String BOTH = "Both";
    /**
     * String identifying All TRANSFERs.
     */
    public static final String ALL = "All";
    /**
     * String identifying Pick TRANSFERs.
     */
    public static final String PICK = "Pick";
    /**
     * String identifying FN TRANSFERs.
     */
    public static final String FN = "FN";
    /**
     * String identifying P TRANSFERs.
     */
    public static final String P = "P";
    /**
     * String identifying SBR TRANSFERs.
     */
    public static final String SBR = "SBR";
    /**
     * String identifying Fraction TRANSFERs.
     */
    public static final String FRACTION = "Fraction";
    /**
     * String identifying Number TRANSFERs.
     */
    public static final String NUMBER = "Number";
    /**
     * String identifying SNA TRANSFERs.
     */
    public static final String SNA = "SNA";

    /**
     * String identifying Nul TRANSFERs
     */
    public static final String NUL = "NUL";

    /**
     * String identifying SIM TRANSFERs
     */
    public static final String SIM = "SIM";

    /**
     * Creates a new instance of Transfer.
     */
    public Transfer() {

        BloquedXacts = new PriorityQueue<>(1000, getModel().getTimeComparator());
    }

    /**
     * Creates a new instance of Transfer.
     *
     * @param comentari the comment of the block.
     * @param label the label of the block.
     * @param A The type of the transfer (zero parameter).
     * @param B the first parameter (depends on the type of transfer).
     * @param C the second parameter (depends on the type of transfer).
     * @param D the third parameter (depends on the type of transfer).
     */
    public Transfer(String comentari, String label, String A, String B, String C, String D) {
        super(Constants.idTransfer, label, comentari);
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        BloquedXacts = new PriorityQueue<>(1000, getModel().getPriorityComparator());
    }

    /**
     *
     * @param tr
     * @return
     * @throws Exception
     *
     * A TRANSFER Block may operate in one of 9 "modes", each with different
     * properties. When a Transaction enters a TRANSFER Block, Operand A is used
     * to determine the mode of operation of the Block. The meaning of operands
     * B and C depend on the mode. When you do not specify an operand which
     * corresponds to a Block location, the next sequential Block after the
     * TRANSFER Block is used.
     */
    @Override
    public Bloc execute(Xact tr) throws Exception {

        Bloc nextBlock = null;

        Xact activeTransaction = checkBloqued(tr);
        Bloc blocB = getProces().findBloc(B);
        Bloc blocC = getProces().findBloc(C);

        if (blocB == null) {
            blocB = nextBloc(tr);
        }
        if (blocC == null) {
            blocC = nextBloc(tr);
        }

        /**
         * When the A Operand is BOTH, the TRANSFER Block operates in "Both
         * Mode". In Both Mode, the Block specified by Operand B is tested. If
         * it refuses to admit the Active Transaction, the Block specified in
         * Operand C is tested. The first Block to admit the Transaction will be
         * the new destination. If neither Block admits the Transaction, it
         * stays in the TRANSFER Block until it can enter one or the other.
         */
        if (A.equals(BOTH)) {

            if (blocB.test(activeTransaction)) {
                nextBlock = blocB;
            } else if (blocC.test(activeTransaction)) {
                nextBlock = blocC;
            } else {
                activeTransaction.setDelayed(true);
                BloquedXacts.add(activeTransaction);
            }
        } /**
         * When the A Operand is ALL, the TRANSFER Block operates in "All Mode".
         * In "All" Mode, the Block specified by Operand B is tested. If this
         * Block refuses to admit the Active Transaction, Blocks are tested in
         * succession until the Block specified by Operand C is passed, unless
         * one of the Blocks tested admits the Transaction prior to reaching the
         * Block specified in Operand C. The location of each succeeding test
         * Block is calculated by adding Operand D to the previous test Block
         * location. If Operand D is not used, every Block between those
         * specified by B and C, inclusive, are tested. If Operand C is not
         * used, only one Block is tested. No Block with a location higher than
         * Operand C is tested. The first Block to admit the Transaction will be
         * the new destination. If no Block admits the Transaction, it stays in
         * the TRANSFER Block until it can enter one.
         */
        else if (A.equals(ALL)) {

            if (blocB.test(activeTransaction)) {
                nextBlock = blocB;
            } else {
                nextBlock = testBlocks(B, C, D, tr);

                if (nextBlock == null) {
                    activeTransaction.setDelayed(true);
                    BloquedXacts.add(activeTransaction);
                }
            }

        } /**
         * When the A Operand is PICK, the TRANSFER Block operates in "Pick"
         * Mode. In Pick Mode, a destination is chosen randomly.
         */
        else if (A.equals(PICK)) {

            if (!(B.isEmpty() || C.isEmpty())) {
                throw new Exception("At Transfer Block: " + getLabel() + ".First place or last place not specified");
            } else {
                int firstPlace = Integer.parseInt(B);
                int lastPlace = Integer.parseInt(C);

                if (getProces().getBlocs().size() > lastPlace) {
                    lastPlace = getProces().getBlocs().size();
                }
                java.util.Random rnd = new java.util.Random();
                int randomIndex = rnd.nextInt((lastPlace - firstPlace)) + firstPlace;

                nextBlock = getProces().getBlocs().get(randomIndex);
            }

        } /**
         * When the A Operand is FN, the TRANSFER Block operates in "Function
         * Mode". In Function Mode, the destination is chosen by evaluating a
         * function entity, specified in B, and adding an optional increment
         * specified in C.
         */
        else if (A.equals(FN)) {

            Function f = getModel().getFunctions().stream()//
                    .filter(fn -> fn.getName().equals(B))//
                    .findFirst()//
                    .orElse(null);

            if (f == null) {
                throw new FunctionNotFoundException(B);
            }

            Integer blockPos = Math.round(f.evaluate()) + Integer.valueOf(C);

            try {
                nextBlock = getProces().getBlocs().get(blockPos);
            }
            catch (IndexOutOfBoundsException e) {
                throw new BlockNotFound(blockPos);
            }

        } /**
         * When the A Operand is P, the TRANSFER Block operates in "Parameter
         * Mode". In Parameter Mode, the Active Transaction jumps to a location
         * calculated from the sum of a Parameter value and Operand C. If C is
         * not specified, the Parameter value is the location of the new
         * destination.
         */
        else if (A.equals(P)) {

            Float parameter = (Float) tr.getParameter(B);

            if (parameter == null) {
                throw new Exception("At Transfer Block: "
                        + getLabel()
                        + ". Transaction parameter "
                        + B
                        + " not specified");
            } else if (parameter > getProces().getBlocs().size()) {
                throw new Exception("At Transfer Block: "
                        + getLabel()
                        + ". Transaction parameter "
                        + B
                        + " with value out of bounds");
            } else {
                nextBlock = getProces().getBlocs().get(Math.round(parameter));
            }

        } /**
         * When the A Operand is SBR, the TRANSFER Block operates in "Subroutine
         * Mode". In Subroutine Mode, the Active Transaction always jumps to the
         * location specified by the B Operand. The location of the transfer
         * Block is placed in the Transaction Parameter specified in Operand C.
         */
        else if (A.equals(SBR)) {

            tr.getTransactionParameters().put(C, new Float(getProces().getBlocs().indexOf(this)));

            if (blocB != null) {
                nextBlock = blocB;
            }

        } /**
         * When the A Operand is SIM, the TRANSFER Block operates in
         * "Simultaneous Mode". In Simultaneous Mode, the Active Transaction
         * jumps to one of two locations depending on the Delay Indicator of the
         * Transaction. If the Delay Indicator is set, the Transaction jumps to
         * the location specified by the C Operand and the Delay Indicator is
         * reset (turned off). If the Delay Indicator is reset (off), the
         * Transaction jumps to the location specified by the B Operand.
         */
        else if (A.equals(SIM)) {

            if (tr.isDelayed()) {
                nextBlock = blocC;
            } else {
                nextBlock = blocB;
            }
            tr.setDelayed(false);

        } 
        else if (A.equals(NUL) || A.isEmpty()) {

            if (blocB != null) {
                nextBlock = blocB;
            } else {
                throw new Exception("At Transfer Block: "
                        + getLabel()
                        + ". Block "
                        + B
                        + " not found");
            }

        } else if (A.isEmpty() && !B.isEmpty()) {
            nextBlock = getModel().findBloc(B);

        } /**
         *
         * When the A Operand is not a keyword, the TRANSFER Block operates in
         * "Fractional Mode". In Fractional Mode, the Active Transaction jumps
         * to the location specified by the C Operand with a probability given
         * by Operand A (Can be also an SNA). If Operand A is a nonnegative
         * integer, it is interpreted as parts-per-thousand and converted to a
         * fractional probability. The alternate destination is specified in
         * Operand B, or the NSB if Operand B is omitted.
         */
        else if (A.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")){

            try {

                float probability = Float.valueOf(evaluate(A, getModel(), tr));

                if (probability > 1) {
                    probability = probability / 1000;
                }

                java.util.Random rnd = new java.util.Random();
                float randomFloat = rnd.nextFloat();

                if (randomFloat >= probability) {
                    nextBlock = blocC;
                } else if (blocB != null) {
                    nextBlock = blocB;
                } else {
                    nextBlock = nextBloc(tr);
                }

            } catch (NumberFormatException e) {
                throw new Exception("At Transfer Block: "
                        + getLabel()
                        + " at proces " + getProces().getDescpro()
                        + ". Bad Fraction Format");
            }
        }
        return nextBlock;
    }

    @Override
    public boolean test(Xact tr) {
        return true;
    }

    private Xact checkBloqued(Xact tr) {

        if (this.BloquedXacts.isEmpty()) {
            return tr;
        }
        BloquedXacts.add(tr);
        return BloquedXacts.poll();
    }

    /**
     * Tests all blocs between bloc B and C included and returns the bloc that
     * admits the active transaction tr and returns the bloc that has admited
     * it. If non of the blocks admits the active transaction, it returns null
     *
     * @param B starting bloc
     * @param C ending bloc
     * @param D increment
     * @param tr active transaction
     * @return
     */
    private Bloc testBlocks(String B, String C, String D, Xact tr) {

        int index = blocIndex(B);
        Integer increment = Integer.parseInt(D) == 0 ? 1 : Integer.parseInt(D);

        // Tests all blocks between B && C
        if (index != -1 && !C.isEmpty()) {
            int k;
            for (k = index; k < getProces().getBlocs().size() && !(getProces().getBlocs().get(k)).getLabel().equals(C); k += increment) {
                Bloc b = (getProces().getBlocs().get(k));

                if (b.getLabel().equals(C)) {
                    if (b.test(tr)) {
                        return b;
                    }
                    return null;
                } else if (b.test(tr)) {
                    return b;
                }
            }
            // Remaining blocks

            while (k < getProces().getBlocs().size()) {

                Bloc b = getProces().getBlocs().get(k);

                if (b.getLabel().equals(C)) {
                    if (b.test(tr)) {
                        return b;
                    }
                    return null;
                } else if (b.test(tr)) {
                    return b;
                }
                k++;
            }
        } // If C is not specified, only one block is tested
        else if (index != -1 && index + 1 < getProces().getBlocs().size()) {
            Bloc b = getProces().getBlocs().get(index + 1);
            if (b.test(tr)) {
                return b;
            }
        }
        return null;
    }

    private int blocIndex(String B) {

        Bloc block = getProces().getBlocs().stream()//
                .filter(b -> b.getLabel().equals(B))//
                .findFirst()//
                .orElse(null);

        if (block == null) {
            return -1;
        }

        return getProces().getBlocs().indexOf(block);
    }

    @Override
    public String name() {
        return "Transfer";
    }
}
