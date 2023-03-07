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
 * A class representing the TERMINATE block.
 *
 * @author Pau Fonseca i Casas
 * @author Ezequiel Andujar Montes
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
@NoArgsConstructor
public class Terminate extends Bloc {

    @Getter
    @Setter
    @SuppressWarnings("FieldMayBeFinal")
    private int A;

    /**
     * Creates a new instance of Terminate
     *
     * @param comentari the comment of the block.
     * @param A the number to decrement to the TC each time a XACT cross the
     * block.
     * @param label the label of the block.
     */
    public Terminate(String comentari, String label, int A) {
        super(Constants.idTerminate, label, comentari);
        this.A = A == 0 ? 1 : A;
    }    

    @Override
    public Bloc execute(Xact tr) {      
        
        incTrans(tr);        
        getModel().setTC(getModel().getTC() - A);
        return null;
    }   

    @Override
    public String name() {
        return "Terminate";
    }
}
