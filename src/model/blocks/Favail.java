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

import exceptions.FacilityNotFoundException;
import exceptions.UnrecognizedModelException;
import java.util.PriorityQueue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import static model.SNA.evaluate;
import model.entities.Xact;
import utils.Constants;

/**
 * A class representing the FAVAIL block.
 *
 * @author Pau Fonseca i Casas
 * @author Ezequiel Andujar Montes
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 *
 * A FAVAIL Block ensures that a Facility Entity is in the available state
 */
@NoArgsConstructor
public class Favail extends Bloc {

    @Getter
    @Setter
    private String A;

    /**
     * Creates a new instance of Favail.
     *
     * @param comentari the comment of the block.
     * @param label the label of the block.
     * @param A the name of the instalation.
     */
    public Favail(String comentari, String label, String A) {

        super(Constants.idFavail, label, comentari);
        this.A = A;
    }

    /**
     * To execute the block
     *
     * @param tr the current XACT (that cross the block).
     * @return return NULL if no more blocs can be executed now. The next block
     * otherwise.
     *
     * A FAVAIL Block ensures that a Facility Entity is in the available state.
     * If the Facility Entity was previously idle, the FAVAIL Block tries to
     * give ownership to a waiting Transaction. If the Facility Entity was
     * previously available, FAVAIL has no effect. FAVAIL cancels the affects of
     * FUNAVAIL on the Facility Entity, but does not affect displaced
     * Transactions.
     * @throws java.lang.Exception
     */
    @Override
    public Bloc execute(Xact tr) throws Exception {

        incTrans(tr);
        String facilityName = evaluate(A, getModel(), tr);

        PriorityQueue<Xact> BEC = getModel().getBEC().get(facilityName);
        PriorityQueue<Xact> preemptedXacts = getModel().getPreemptedXacts().get(facilityName);
        PriorityQueue<Xact> CEC = getModel().getCEC();

        if (getModel().getFacilities().get(facilityName) == null) {
            throw new FacilityNotFoundException();
        }

        if (getModel().getFacilities().get(facilityName).isAvailable()) {

            if (preemptedXacts != null && !preemptedXacts.isEmpty()) {
                CEC.add(preemptedXacts.poll());

            } else if (BEC != null && !BEC.isEmpty()) {
                CEC.add(BEC.poll());
            }
        }

        getModel().getFacilities().get(A).setAvailable(true);
        return nextBloc(tr);
    }

    @Override
    public String name() {
        return "Favail";
    }
}
