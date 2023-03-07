/**
 * Software end-user license agreement.
 *
 * The LICENSE.TXT containing the license is located in the JGPSS project.
 * License.txt can be downloaded here:
 * href="http://www-eio.upc.es/~Pau/index.php?q=node/28
 *
 * NOTICE TO THE USER: BY COPYING, INSTALLING OR USING THIS SOFTWARE OR PART OF
 * THIS SOFTWARE, YOU AGREE TO THE TERMS AND CONDITIONS OF THE LICENSE AGREEMENT
 * AS IF IT WERE A WRITTEN AGREEMENT NEGOTIATED AND SIGNED BY YOU. THE LICENSE
 * AGREEMENT IS ENFORCEABLE AGAINST YOU AND ANY OTHER LEGAL PERSON ACTING ON
 * YOUR BEHALF. IF, AFTER READING THE TERMS AND CONDITIONS HEREIN, YOU DO NOT
 * AGREE TO THEM, YOU MAY NOT INSTALL THIS SOFTWARE ON YOUR COMPUTER. UPC IS THE
 * OWNER OF ALL THE INTELLECTUAL PROPERTY OF THE SOFTWARE AND ONLY AUTHORIZES
 * YOU TO USE THE SOFTWARE IN ACCORDANCE WITH THE TERMS SET OUT IN THE LICENSE
 * AGREEMENT.
 */
package model.blocks;

import java.util.HashMap;
import java.util.PriorityQueue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.entities.Storage;
import model.entities.Xact;
import utils.Constants;

/**
 * A class representing the ENTER block.
 *
 * @author Pau Fonseca i Casas
 * @author Ezequiel Andujar Montes
 * @version 1
 * @see <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
@SuppressWarnings("FieldMayBeFinal")
@NoArgsConstructor
public class Enter extends Bloc {

    @Getter
    @Setter
    private String A;

    @Getter
    @Setter
    private int B;

    /**
     * Creates a new instance of Enter
     *
     * @param comentari the comment of the block.
     * @param label the block label.
     * @param A the name of the storage.
     * @param B the number of instances needed.
     */
    public Enter(String comentari, String label, String A, int B) {

        super(Constants.idEnter, label, comentari);
        this.A = A;
        this.B = B > 0 ? B : 1;
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
        
        HashMap<String, Facility> facilities = getModel().getFacilities();

        Storage storage = getModel().getStorage(A);

        if (facilities.get(A) == null) {
            facilities.put(A, new Facility(getModel(), storage.getValor()));
        }

        if (getModel().getBEC().get(A) == null) {
            getModel().getBEC().put(A, new PriorityQueue<>(1000, getModel().getPriorityComparator()));
        }

        if (facilities.get(A).capture(B, tr)) {

            return nextBloc(tr);

        } else {
            getModel().getBEC().get(A).add(tr);
            return null;
        }
    }

    @Override
    public boolean test(Xact tr) {

        HashMap<String, Facility> facilities = this.getModel().getFacilities();

        Storage storage = getModel().getStorage(A);

        if (facilities.get(A) == null) {
            facilities.put(A, new Facility(getModel(), storage.getValor()));
        }

        if (getModel().getBEC().get(A) == null) {
            getModel().getBEC().put(A, new PriorityQueue<>(1000, getModel().getPriorityComparator()));
        }

        boolean available = facilities.get(A).isAvailable();
        tr.setDelayed(!available);
        return available;
    }

    @Override
    public int getCurrentCount() {
        return getModel().getBEC().get(A).size();
    }

    @Override
    public String name() {
        return "Enter";
    }
}
