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
import static model.SNA.evaluate;
import model.entities.Xact;
import utils.Constants;

/**
 * A class representing the FUNAVAIL block.
 *
 * @author Pau Fonseca i Casas
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 *
 * FUNAVAIL Blocks are used to make a Facility Entity unavailable for ownership
 * by Transactions
 */
@NoArgsConstructor
@SuppressWarnings("FieldMayBeFinal")
public class Funavail extends Bloc {

    //Intergeneration time.
    @Getter
    @Setter
    private String A;
    //Halfrange or Function Modifier.
    @Getter
    @Setter
    private String B;
    //Start delay time.
    @Getter
    @Setter
    private String C;
    //Creation limit.
    @Getter
    @Setter
    private String D;
    //Priority.
    @Getter
    @Setter
    private String E;
    //Not yet used.
    @Getter
    @Setter
    private String F;

    /**
     * Creates a new instance of Funavail
     *
     * @param comentari the comment of the block.
     * @param label the label of the block.
     * @param A the instalation that become unavailable (SEIZE).
     * @param B modality (RE) remover or (CO) continue.
     * @param C label of the block to send the XACT that are in the instalation.
     * @param D parameter that receives the residual time of the XACT.
     * @param E modality (RE) remover of (CO) continue for the PREEMPT XACT's.
     * @param F label to send the PREEMP XACT's.
     */
    public Funavail(String comentari, String label, String A, String B, String C, String D, String E, String F) {

        super(Constants.idFunavail, label, comentari);

        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.E = E;
        this.F = F;
        this.setLabel(label);
        this.setComentari(comentari);
    }

    /**
     * The complexity of the FUNAVAIL Block is due to the three classes of
     * Transactions which must be dealt with:
     *
     * 1. the owning Transaction (operands B-D),
     *
     * 2. preempted Transactions on the Interrupt Chain (operands E-F), and
     *
     * 3. delayed Transactions on the Delay Chain or Pending Chain (operands
     * G-H).
     *
     * A FUNAVAIL Block allows you to put a Facility "out of action" and to
     * control the fate of Transactions waiting for, using, or preempted at the
     * Facility Entity. Transactions arriving during the unavailable period will
     * be delayed and not be granted ownership. A FUNAVAIL Block has no effect
     * if the Facility is already unavailable.
     *
     * When the REmove option is used, the Transactions are removed from
     * contention for the Facility If the REmove option is used for pending and
     * delayed Transactions, i. e. if G is RE, then H must be used to redirect
     * the Transactions. When the COntinue option is used, the Transactions on
     * the specific Facility chain may continue to own the Facility, even during
     * the unavailable period. In this case, Facility utilization statistics are
     * adjusted to include this time.
     *
     * When an alternate destination Block is used, the Transactions are
     * displaced from their current context and redirected to the new Block.
     * Delayed and pending Transactions, which are controlled by operands G and
     * H, cannot be redirected without also using the REmove option.
     *
     * The owning Transaction, controlled by operands B through D, and preempted
     * Transactions, controlled by operands E and F, can remain in contention
     * for the Facility and yet be displaced to a new destination. This is done
     * by specifying an alternate destination without using the corresponding RE
     * option.
     *
     * When RE is not used in Operand B, any owning Transaction becomes
     * preempted at this Facility. Such Transactions cannot leave ASSEMBLE,
     * GATHER, or MATCH Blocks or enter (nonzero) ADVANCE Blocks until all
     * preemptions are cleared.
     *
     * @param tr
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public Bloc execute(Xact tr) throws Exception {

        incTrans(tr);
        String facilityName = evaluate(A, getModel(), tr);
        Facility facilityState = getModel().getFacilities().get(facilityName);
        facilityState.setAvailable(false);

        Xact owningXact = facilityState.getOwningXact();

        Bloc destinationB = getProces().findBloc(evaluate(C, getModel(), tr));
        Bloc destinationF = getProces().findBloc(evaluate(F, getModel(), tr));
       
        if (B.equals("RE")) {

            if (destinationB != null && destinationB instanceof Release) {
                throw new Exception("In Block FUNAVAIL " + getLabel() + " at Process "
                        + getProces().getDescpro() + "Operand C refers to a Release Block and must not be used with RE option");
            }

            facilityState.setOwningXact(null);

        } 
        else if (B.equals("CO")) {

        } 
        else if (B.isEmpty()) {

            if (getModel().getPreemptedXacts().get(facilityName) == null) {                
                
                getModel().getPreemptedXacts().put(facilityName, new PriorityQueue<>(1000, getModel().getPriorityComparator()));
            }
            getModel().getPreemptedXacts().get(facilityName).add(tr);
        }
        
        if (!C.isEmpty() && destinationB != null) {

            if (getModel().getFEC().contains(owningXact)) {
                for (Object b : getProces().getBlocs()) {

                    if (b instanceof Advance) {
                        owningXact.setBloc((Bloc) b);
                        break;
                    }
                }
                owningXact.setBlockRoute(destinationB);
            } else {
                owningXact.setBloc(destinationB);
            }
        } else {
            throw new Exception("In Block FUNAVAIL " + getLabel() + " at Process "
                    + getProces().getDescpro() + "Missing operand C or block not found");
        }
       
        if (getModel().getFEC().contains(owningXact)) {

            String residualTimeName = evaluate(D, getModel(), tr);

            if (C.isEmpty()) {
                tr.restore(true);
            }

            float residualTimeValue = Math.abs(getModel().getRelativeClock() - owningXact.getMoveTime());

            if (!residualTimeName.isEmpty()) {
                owningXact.getTransactionParameters().put(residualTimeName, residualTimeValue);
            }

            owningXact.getTransactionParameters().put("residual-time", residualTimeValue);
            getModel().getFEC().remove(owningXact);
            owningXact.setMoveTime(owningXact.getMoveTime() - residualTimeValue);
            getModel().getBEC().get(A).add(owningXact);
        }
       
        if (E.equals("CO")) {

            if (getModel().getPreemptedXacts().get(facilityName) != null) {
                while (getModel().getPreemptedXacts().get(facilityName).iterator().hasNext()) {

                    Xact xact = getModel().getPreemptedXacts().get(facilityName).iterator().next();
                    xact.setOwnershipGranted(true);
                }
            }
        } 
        else if (E.equals("RE") && getModel().getPreemptedXacts().get(facilityName) != null) {

            if (destinationF != null && destinationF instanceof Release) {
                throw new Exception("In Block FUNAVAIL " + getLabel() + " at Process " + getProces().getDescpro()
                        + "Operand C refers to a Release Block and must not be used with RE option");
            }

            while (!getModel().getBEC().get(facilityName).isEmpty()) {
                Xact preemptedXact = getModel().getBEC().get(facilityName).poll();
                preemptedXact.setOwnershipGranted(true);
                getModel().getCEC().add(preemptedXact);
            }

        } 
        else if (E.isEmpty() && getModel().getPreemptedXacts().get(facilityName) != null) {
            while (getModel().getBEC().get(facilityName).iterator().hasNext()) {

                Xact bloquedXsct = getModel().getBEC().get(facilityName).iterator().next();
                bloquedXsct.setOwnershipGranted(false);
            }
        }

        if (!F.isEmpty() && destinationF != null) {

            if (getModel().getPreemptedXacts().get(facilityName) != null) {
                while (getModel().getPreemptedXacts().get(facilityName).iterator().hasNext()) {
                    Xact bloquedXact = getModel().getPreemptedXacts().get(facilityName).iterator().next();
                    bloquedXact.setBloc(destinationF);
                }
            }
        } else {
            throw new Exception("In Block FUNAVAIL " + getLabel() + " at Process "
                    + getProces().getDescpro() + "Missing operand F");
        }
        return nextBloc(tr);
    }

    @Override
    public String name() {
        return "Funavail";
    }
}
