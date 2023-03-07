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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.entities.Xact;
import utils.Constants;

/**
 * A class representing the SPLIT block.
 *
 * @author Pau Fonseca i Casas
 * @author Ezequiel Andujar Montes
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 *
 * A SPLIT Block creates Transactions in the same Assembly Set as the Active
 * Transaction.
 *
 */
@NoArgsConstructor
@SuppressWarnings("FieldMayBeFinal")
public class Split extends Bloc {

    @Getter
    @Setter
    private int A;

    @Getter
    @Setter
    private String B;

    @Getter
    @Setter
    private String C;

    /**
     * Creates a new instance of Split.
     *
     * @param comentari the comment of the block.
     * @param label the label of the block.
     * @param A the number of new XACT's to be created.
     * @param B the label for the new XATC's.
     * @param C the serial parameter.
     */
    public Split(String comentari, String label, int A, String B, String C) {
        super(Constants.idSplit, label, comentari);
        this.A = A;
        this.B = B;
        this.C = C;
    }

    /**
     *
     * To execute the block
     *
     * A SPLIT Block creates new Transactions which share the attributes of
     * their parent. Each offspring Transaction has the same priority and Mark
     * Time of the parent, and is in the same Assembly Set. If the Trace
     * Indicator is set in the parent Transaction, it is turned on in the
     * offspring Transaction. The new Transactions may be sent to an alternate
     * destination, by using a Block number for Operand B. The new Block number
     * is evaluated for each new Transaction. The optional C Operand specifies
     * the Parameter number of the newly created Transactions to receive a
     * serial number. The numbering starts at the value of the corresponding
     * Parameter in the parent Transaction, plus 1. For example, if 3 copies are
     * to be created with a serial number in Parameter 120, and the entering
     * Transaction has 15 in its Parameter 120, then the new Transactions will
     * have 16, 17, and 18, respectively, in their Parameters numbered 120. If
     * the parent Transaction has no such Parameter, it is created and the
     * numbering starts at 1. By using both B and C operands it is possible to
     * send each new Transaction to a different destination. The parent
     * Transaction and all the offspring Transactions all belong to the same set
     * of Transactions called an Assembly Set. *
     *
     * @param tr the current XACT (that cross the block).
     * @return return NULL if no more blocs can be executed now. The next block
     * @throws java.lang.Exception
     */
    @Override
    public Bloc execute(Xact tr) throws Exception {

        Integer serialNumber = 1;
        Bloc destinationBlock = getProces().findBloc(B);

        if (destinationBlock == null) {
            destinationBlock = nextBloc(tr);
        }

        if (!C.isEmpty()) {
            serialNumber = (Integer) tr.getParameter(C);
            if (serialNumber == null) {
                tr.getTransactionParameters().put(C, 1);
            }
        }

        while (A > 0) {

            try {
                Xact newXact = tr.clone();
                tr.setBloc(destinationBlock);
                serialNumber++;
                if (!C.isEmpty()) {
                    tr.getTransactionParameters().put(C, serialNumber);
                }
                getModel().getCEC().add(newXact);

            } catch (CloneNotSupportedException e) {
                throw new Exception("At Split block " + getLabel() + " Error on copy transaction ");
            }
            A--;
        }
        return destinationBlock;
    }   

    @Override
    public String name() {
        return "Split";
    }
}
