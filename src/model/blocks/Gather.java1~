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
 * A class representing the GATHER block.
 *
 * @author Pau Fonseca i Casas
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
@NoArgsConstructor
public class Gather extends Bloc {

    @Getter
    @Setter
    @SuppressWarnings("FieldMayBeFinal")
    private int A;

    /**
     * Creates a new instance of Gather
     *
     * @param comentari the coment of the block.
     * @param label the label.
     * @param A the number of XACT to be grouped.
     */
    public Gather(String comentari, String label, int A) {

        super(Constants.idGather, label, comentari);
        this.A = A;
    }   

    /**
     * To execute the block
     *
     * @param tr the current XACT (that cross the block).
     * @return return NULL if no more blocs can be executed now. The next block
     * otherwise.
     *
     * When a Transaction enters a GATHER Block, the Match Chain of the Block is
     * searched for a waiting Transaction of the same Assembly Set. If there are
     * no other members of the same Assembly Set present, the A Operand is
     * evaluated, truncated. decremented by one, and saved in a storage location
     * in the Active Transaction. If this number is less than or equal to zero,
     * the Transaction immediately attempts to enter the Next Sequential Block.
     * Otherwise, the Transaction is placed on a special chain in the GATHER
     * Block, called the Match Chain, to await the arrival of other members of
     * its Assembly Set.
     *
     * If the Active Transaction arrives to find other members of its Assembly
     * Set already on the Match Chain, the Active Transaction is also placed on
     * the chain and the Transaction count saved in the first chained
     * Transaction is reduced by one. When this count becomes 0, all related
     * Transactions are removed from the Match Chain. All Transactions which
     * have not been preempted at any Facility are then placed on the CEC behind
     * their priority peers.
     *
     * Preempted Transactions which have completed an assembly at a GATHER Block
     * are not permitted to leave the Block until all preemptions have been
     * cleared. More discussion of the preemption mechanism can be found in
     * Section 9.4. Preempted Transactions which have been removed from the
     * Match Chain do not participate in later gatherings even though they
     * remain in the GATHER Block.
     *
     * GATHER Blocks differ from ASSEMBLE Blocks in that Transactions after the
     * first are destroyed at an ASSEMBLE Block.
     */
    @Override
    public Bloc execute(Xact tr) {
        incTrans(tr);

        Xact waitingXact = findWaitingXact(tr);

        if (waitingXact == null) {

            A--;

            tr.setCounter(A);
            if (A == 0) {
                return nextBloc(tr);
            } else {
                getMatchChain().add(tr);
            }
        } else {

            if (waitingXact.decCounter() == 0) {
                getMatchChain().remove(waitingXact);

                if (getModel().preempted(waitingXact)) {
                    nextBloc(waitingXact);
                    getModel().getCEC().add(waitingXact);
                }
            }
        }
        return null;
    }   
    
    private Xact findWaitingXact(Xact tr) {

        if (getMatchChain().isEmpty()) {
            return null;
        }

        while (getMatchChain().iterator().hasNext()) {

            Xact currentTr = getMatchChain().iterator().next();

            if (currentTr.getAssemblySet() == tr.getAssemblySet()) {
                return currentTr;
            }
        }
        return null;
    }

    @Override
    public String name() {
        return "Gather";
    }
}
