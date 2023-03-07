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

import java.util.HashMap;
import lombok.NoArgsConstructor;
import model.entities.Xact;
import utils.Constants;

/**
 * A class representing the ASSIGN GPSS block.
 *
 * @author Pau Fonseca i Casas
 * @author M.Dolores
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
@NoArgsConstructor
public class Assign extends Bloc {

    private String A;
    private float B;

    /**
     * Creates a new instance of ASSIGN block.
     *
     * @param comentari the comment of the block.
     * @param label the label of the block.
     * @param A the parameter of the XACT that receives the value.
     * @param B the value to be assigned.
     */
    public Assign(String comentari, String label, String A, Float B) {

        super(Constants.idAssign, label, comentari);        
        this.A = A;
        this.B = B;
    }

    /**
     * To get the A parameter of the ASSIGN
     *
     * @return the parameter of the XACT that receives the value.
     */
    public String getA() {
        return A;
    }

    /**
     * To set the A parameter
     *
     * @param A the parameter of the XACT that receives the value.
     */
    public void setA(String A) {
        this.A = A;
    }

    /**
     * To get the B parameter of the ASSIGN
     *
     * @return the value to be assigned.
     */
    public float getB() {
        return B;
    }

    /**
     * To set the B parameter
     *
     * @param B the valuetobe assigned.
     */
    public void setB(float B) {
        this.B = B;
    }

    /**
     * To execute the block
     *
     * @param tr the current XACT (that cross the block).
     * @return return NULL if no more blocs can be executed now. The next block
     * otherwise.
     */
    @Override
    public Bloc execute(Xact tr) {

        incTrans(tr);
        HashMap<String, Object> transactionParameters = tr.getTransactionParameters();

        if (transactionParameters.get(A.split("(\\+|\\-)")[0]) == null) {
            transactionParameters.put(A,B);
        } else {

            Float value = (Float) transactionParameters.get(A);

            if (A.endsWith("+")) {
                transactionParameters.put(A.split("\\+")[0], value + B);
            } else if (A.endsWith("-")) {
                transactionParameters.put(A.split("\\-")[0], value - B);
            } else {
                transactionParameters.put(A,B);
            }
        }
        return nextBloc(tr);
    }  

    @Override
    public String name() {
        return "Assign";
    }    
   
}
