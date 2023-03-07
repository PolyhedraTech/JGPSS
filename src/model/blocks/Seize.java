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
import java.util.PriorityQueue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.entities.Xact;
import utils.Constants;

/**
 * A class representing the SEIZE block.
 *
 * @author Pau Fonseca i Casas
 * @author Ezequiel Andujar Montes
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
@NoArgsConstructor
public class Seize extends Bloc {

    @Getter
    @Setter
    private String A;

    @Getter
    @Setter
    private int captureCount;

    /**
     * Creates a new instance of Seize
     *
     * @param comentari the comment of the block.
     * @param label the label of the block.
     * @param A the name of the SEIZE.
     */
    public Seize(String comentari, String label, String A) {

        super(Constants.idSeize, label, comentari);
        this.captureCount = 0;
        this.A = A;
    }

    /**
     * Increments the number of times a server has been capture
     */
    public void incCaptureCount() {
        captureCount++;
    }

    @Override
    public Bloc execute(Xact tr) {

        incTrans(tr);

        HashMap<String, Facility> facilities = getModel().getFacilities();

        if (facilities.get(A) == null) {
            facilities.put(A, new Facility(getModel()));
        }
        
        if (getModel().getBEC().get(A) == null) {
            getModel().getBEC().put(A, new PriorityQueue<>(1000, getModel().getPriorityComparator()));
        }

        // Attempt to capture the seize
        if (facilities.get(A).capture(tr)) {
            return nextBloc(tr);
        }

        // The Xacts remains on the block event chain 
        getModel().getBEC().get(A).add(tr);
        return null;
    }

    @Override
    public boolean test(Xact tr) {

        HashMap<String, Facility> facilities = this.getModel().getFacilities();

        if (facilities.get(A) == null) {
            Facility fs = new Facility(getModel());
            facilities.put(A, fs);
        }

        boolean available = facilities.get(A).isAvailable();

        if (!available) {
            tr.setDelayed(true);
        }
        return available;
    }  

    @Override
    public String name() {
        return "Seize";
    }
}
