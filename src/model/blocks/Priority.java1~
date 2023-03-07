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
 * A class representing the PRIORITY block.
 *
 * @author Pau Fonseca i Casas
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */

@NoArgsConstructor
public class Priority extends Bloc {

    @Getter
    @Setter
    @SuppressWarnings("FieldMayBeFinal")
    private int A;    

    /**
     * Creates a new instance of Priority.
     *
     * @param comentari the comment of the block.
     * @param label the label of the block.
     * @param A the priority bigger implies more priority.
     */
    public Priority(String comentari, String label, int A) {
        
        super(Constants.idPriority, label, comentari);        
        this.A = A;
    }    

    /**
     * When a Transaction enters a PRIORITY Block, Operand A is evaluated
     * numerically, truncated, and assigned to the priority of the Active
     * Transaction. The Transaction is scheduled for the Next Sequential Block
     * and is placed on the CEC according to its new priority. 
     *
     * Transaction priorities are integers. When a Transaction is created
     * without an explicit priority, it is given a priority of 0, by default.
     *
     * @param tr
     * @return
     */
    @Override
    public Bloc execute(Xact tr) {        
            
        incTrans(tr);
        tr.setPriority(A);        
        getModel().getCEC().add(tr);
        nextBloc(tr);        
        return null;
    }

    @Override
    public String name() {
        return "Priority";
    }
}
