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
import static model.SNA.evaluate;
import model.entities.Xact;
import utils.Constants;

/**
 * A class representing the SUNAVAIL block.
 *
 * @author Pau Fonseca i Casas
 * @author Ezequiel Andujar Montes
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
@NoArgsConstructor
public class Sunavail extends Bloc {

    @Getter
    @Setter
    @SuppressWarnings("FieldMayBeFinal")
    private String A;

    /**
     * Creates a new instance of Sunavail
     *
     * @param comentari the comment of the block.
     * @param label the label of the block.
     * @param A the name of the STORAGE affected by the block.
     */
    public Sunavail(String comentari, String label, String A) {
        super(Constants.idSunavail, label, comentari);
        this.A = A;
    }

    @Override
    public Bloc execute(Xact tr) throws Exception {

        String facilityName = evaluate(A, getModel(), tr);

        if (getModel().getFacilities().get(facilityName) == null) {
            getModel().getFacilities().put(facilityName, new Facility(getModel()));
        } else {
            throw new Exception("Storage " + facilityName + "not found");
        }

        Facility facilityState = getModel().getFacilities().get(facilityName);
        facilityState.setAvailable(false);

        return nextBloc(tr);

    }

    @Override
    public String name() {
        return "Sunavail";
    }
}
