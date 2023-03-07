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

import exceptions.SaveValueNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.entities.SaveValue;
import model.entities.Xact;
import utils.Constants;

/**
 * A class representing the SAVEVALUE block.
 *
 * @author Pau Fonseca i Casas
 * @author Ezequiel Andujar Montes
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 *
 *
 * An SAVEVALUE Block is used to assign, increment, or decrement the value of a
 * Savevalue Entity. The A Operand is evaluated numerically, truncated, and used
 * as the Savevalue Entity number. Operand B is evaluated and used to determine
 * the new value for the Savevalue Entity. If Operand A is followed by +, then
 * the numeric equivalent of Operand B is added to the numeric equivalent of the
 * old value. If Operand A is followed by -, then the numeric equivalent of
 * Operand B is subtracted from the numeric equivalent of the old value. If
 * Operand A is not followed by a sign, the old value of the SAVEVALUE is
 * replaced by Operand B.
 *
 */
@NoArgsConstructor
@SuppressWarnings("FieldMayBeFinal")
public class Savevalue extends Bloc {

    @Getter
    @Setter
    private String A;

    @Getter
    @Setter
    private String B;

    /**
     * Creates a new instance of Savevalue.
     *
     * @param comentari the comment of the block.
     * @param label the label of the block.
     * @param A the name of the matrix.
     * @param B the value to store.
     */
    public Savevalue(String comentari, String label, String A, String B) {
        super(Constants.idSavevavg, label, comentari);
        this.A = A;
        this.B = B;
    }

    /**
     *
     * @param tr
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public Bloc execute(Xact tr) throws Exception {
        
        incTrans(tr);
        
        SaveValue sv = getModel().getSaveValue(A);
        
        if (sv == null) {
            throw new SaveValueNotFoundException(A);
        }        
        
        float value = getModel().getSaveValue(A).getValue();

        if (B.endsWith("-")) {
            float sValue = Float.parseFloat(B.split("\\-")[0]);
            sv.setValue(value - sValue);
        } else if (B.endsWith("+")) {
            float sValue = Float.parseFloat(B.split("\\+")[0]);
            sv.setValue(value + sValue);
        } else {
            float sValue = Float.parseFloat(B);
            sv.setValue(sValue);
        }
        return nextBloc(tr);
    }   

    @Override
    public String name() {
        return "SaveValue";
    }
}
