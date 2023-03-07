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

import java.util.PriorityQueue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.entities.Xact;
import utils.Constants;

/**
 * A class representing the SAVAIL block.
 *
 * @author Pau Fonseca i Casas
 * @author Ezequiel Andujar Montes
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 *
 * A SAVAIL Block ensures that a Storage Entity is in the available state.
 */
@NoArgsConstructor
public class Savail extends Bloc {

    @Getter
    @Setter
    @SuppressWarnings("FieldMayBeFinal")
    private String A;

    /** 
     * Creates a new instance of Savail.
     *
     * @param comentari the comment of the block.
     * @param label the label of the block.
     * @param A the name of the STORAGE.
     */
    public Savail(String comentari, String label, String A) {
        super(Constants.idSavail, label, comentari);
        this.A = A;
    }
    

    /**
     * An SAVAIL Block ensures that a Storage Entity is in the available state.
     * If any Transactions are waiting on the Delay Chain of the Storage, they
     * are given a chance to have their storage requests satisfied by the
     * Storage Entity according to the first-fit-with-skip discipline. Those
     * Transactions whose storage requests cannot be satisfied remain on the
     * Delay Chain of the Storage Entity. If the Storage Entity is already in
     * the available state, the SAVAIL Block has no effect.
     *
     * @param tr
     * @return
     */
    @Override
    public Bloc execute(Xact tr) {

        getModel().getFacilities().get(A).setAvailable(true);        
        return nextBloc(tr);
    }   

    @Override
    public String name() {
        return "Savail";
    }
}
