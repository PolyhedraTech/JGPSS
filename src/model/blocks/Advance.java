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
import model.SNA;
import model.entities.Xact;
import utils.Constants;
import model.entities.rng.RNG;

/**
 * A class representing the ADVANCE GPSS block.
 *
 * @author Pau Fonseca i Casas
 * @author M.Dolores
 * @author Ezequiel Andujar Montes
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
@NoArgsConstructor
@SuppressWarnings("FieldMayBeFinal")
public class Advance extends Bloc {

    @Getter
    @Setter
    private String A;

    @Getter
    @Setter
    private String B;

    /**
     * Creates a new instance of ADVANCE.
     *
     * @param comentari the comment of the ADVANCE block.
     * @param label the label of the block.
     * @param A the A parameter value of the block.
     * @param B the B parameter value of the block.
     * @param gna
     */
    public Advance(String comentari, String label, String A, String B, RNG gna) {

        super(Constants.idAdvanced, label, comentari, gna);
        this.A = A;
        this.B = B;
    }

    /**
     * The method that executes the block.
     *
     * @param tr the transaction that cross the block.
     * @return this method returns the next block of the transaction active.
     * NULL if is removed from the CEC (ADVANCE, TERMINATE, or bloqued
     * situation).
     * @throws java.lang.Exception
     */
    @Override
    public Bloc execute(Xact tr) throws Exception {

        incTrans(tr);

        Float evalA = Float.valueOf(SNA.evaluate(A, getModel(), tr));
        Float evalB = Float.valueOf(SNA.evaluate(B, getModel(), tr));

        if (tr.getParameter("residual-time") != null) {
            tr.setMoveTime(getModel().getRelativeClock() + (Float) tr.getParameter("residual-time"));
        } else {
            tr.setMoveTime(getModel().getRelativeClock() + getGna().generate(evalA, evalB));
        }
        if (tr.getBlockRoute() != null) {
            tr.setBloc(tr.getBlockRoute());
        } else {
            nextBloc(tr);
        }

        getModel().getFEC().add(tr);
        return null;
    }

    @Override
    public String name() {
        return "Advance";
    }
}
