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
 * A class representing the GATE block.
 *
 * @author Pau Fonseca i Casas
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
@NoArgsConstructor
@SuppressWarnings("FieldMayBeFinal")
public class Gate extends Bloc {

    @Getter
    @Setter
    private String x;

    @Getter
    @Setter
    private String A;

    @Getter
    @Setter
    private String B;

    /**
     * GATE U
     */
    public static final String U = "U";
    /**
     * GATE NU
     */
    public static final String NU = "NU";
    /**
     * GATE SF
     */
    public static final String SF = "SF";
    /**
     * GATE SNF
     */
    public static final String SNF = "SNF";
    /**
     * GATE SE
     */
    public static final String SE = "SE";
    /**
     * GATE SNE
     */
    public static final String SNE = "SNE";
    /**
     * GATE LR
     */
    public static final String LR = "LR";
    /**
     * GATE LS
     */
    public static final String LS = "LS";

    /**
     *
     * @param comentari
     * @param label
     * @param x Type of GATE.
     * @param A Instalation analized.
     * @param B label to send the XACT if the GATE is closed.
     */
    public Gate(String comentari, String label, String x, String A, String B) {

        super(Constants.idGate, label, comentari);
        this.A = A;
        this.B = B;
        this.x = x;
    }

    /**
     * To execute the block
     *
     * @param tr the current XACT (that cross the block).
     * @return return NULL if no more blocs can be executed now. The next block
     * otherwise.
     *
     * When a Transaction attempts to enter such a GATE Block, and the test is
     * unsuccessful, the Transaction enters the GATE Block, is scheduled for the
     * alternate destination Block specified by the B Operand, and is placed on
     * the Current Events Chain in front of its priority peers. If the test is
     * successful, the Active Transaction enters the GATE Block and then
     * proceeds to the Next Sequential Block.
     * @throws java.lang.Exception
     */
    @Override
    public Bloc execute(Xact tr) throws Exception {

        incTrans(tr);
        Bloc nextBlock = null;
        Bloc blockB = getProces().findBloc(B);

        Facility facility = getModel().getFacilities().get(A);

        boolean gateType = x.equals(U)
                || x.equals(NU)
                || x.equals(SF)
                || x.equals(SNF)
                || x.equals(SE)
                || x.equals(SNE);

        if (!gateType) {
            throw new Exception("At Gate Block " + getLabel() + ". Unknown gate type " + gateType);
        } else {
            boolean test = x.equals(U) && !facility.isAvailable()
                    || x.equals(NU) && facility.isAvailable()
                    || x.equals(SF) && facility.storageFull()
                    || x.equals(SNF) && !facility.storageFull()
                    || x.equals(SE) && facility.storageEmpty()
                    || x.equals(SNE) && !facility.storageEmpty();

            if (test) {
                nextBlock = nextBloc(tr);
            } else {
                nextBlock = blockB;
            }
        }
        return nextBlock;
    }

    @Override
    public String name() {
        return "Gate";
    }
}
