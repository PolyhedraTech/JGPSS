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

/**
 * A class representing the LOOP block.
 *
 * @author Pau Fonseca i Casas
 * @author Ezequiel Andujar Montes
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 *
 *
 * A LOOP Block modifies a Parameter and controls the destination of the Active
 * Transaction based on the result.
 */
import exceptions.ParameterNotANumberException;
import exceptions.ParameterNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import static model.SNA.evaluate;
import static java.lang.Integer.valueOf;
import model.entities.Xact;
import utils.Constants;

@NoArgsConstructor
@SuppressWarnings("FieldMayBeFinal")
public class Loop extends Bloc {

    @Getter
    @Setter
    private String A;

    @Getter
    @Setter
    private String B;

    /**
     * Creates a new instance of Loop.
     *
     * @param comentari the comment of the block.
     * @param label the labelof the block.
     * @param A the parameter that contains the number of itereation to be done.
     * @param B the label to go if A > 0.
     */
    public Loop(String comentari, String label, String A, String B) {
        super(Constants.idLoop, label, comentari);
        this.A = A;
        this.B = B;
    }

    /**
     * When a Transaction enters a LOOP Block, Operand A is evaluated,
     * truncated, and used to find the Transaction Parameter with that number.
     * If there is no such Parameter, an Error Stop occurs. Otherwise the value
     * of the Parameter is decreased by 1. If the new value of the Parameter is
     * greater than zero and the B Operand is specified, the Transaction is
     * scheduled for the location specified in the B Operand. Otherwise, the
     * Transaction proceeds to the Next Sequential Block.
     *
     * @param tr
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public Bloc execute(Xact tr) throws Exception {

        incTrans(tr);

        String param = evaluate(A, getModel(), tr);

        if (tr.getParameter(param) == null) {
            throw new ParameterNotANumberException(param);
        }
        if (!(tr.getParameter(param) instanceof Integer)) {
            throw new ParameterNotFoundException(param);
        }

        Integer counter = (Integer) tr.getParameter(param);

        counter -= 1;
        
        if (counter == 0) {
            tr.getTransactionParameters().put(param, counter);
            return nextBloc(tr);
        }

        return getProces().findBloc(B);
    }

    @Override
    public String name() {
        return "Loop";
    }
}
