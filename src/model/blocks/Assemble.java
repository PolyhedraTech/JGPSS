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
 * A class representing the ASSEMBLE GPSS block.
 *
 * @author Pau Fonseca i Casas
 * @author M.Dolores
 * @author Ezequiel Andujar Montes
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
@NoArgsConstructor
public class Assemble extends Bloc {

    @Getter
    @Setter
    private int A;

    /**
     * Creates a new instance of an ASSEMBLE block.
     *
     * @param comentari the comment of the ASSEMBLE block
     * @param label the label of the block.
     * @param A the A parameter value of the block.
     */
    public Assemble(String comentari, String label, int A) {
        
        super(Constants.idAssemble, label, comentari);
        this.A = A;
    }

    /**
     * To execute the block
     *
     * @param tr the current XACT (that cross the block).
     * @return return NULL if no more blocs can be executed now. The next block
     * otherwise.
     *
     * When a Transaction enters an ASSEMBLE Block, the Match Chain of the Block
     * is searched for a waiting Transaction of the same Assembly Set. If there
     * are no other members of the same Assembly Set present, the A Operand is
     * evaluated, truncated, decremented by one, and saved in a storage location
     * in the Transaction. If this number is zero, the Transaction immediately
     * attempts to enter the Next Sequential Block. Otherwise the Transaction is
     * placed on a queue attached to the ASSEMBLE Block called the Match Chain
     * to await the arrival of other members of its Assembly Set.
     *
     * When a Transaction enters an ASSEMBLE Block, if a waiting Transaction is
     * found, the entering Transaction is destroyed and the Transaction count
     * that was saved in the chained Transaction is reduced by one. When this
     * count becomes 0, the waiting Transaction is removed from the Match Chain.
     * If this Transaction has not been preempted at any Facility, it attempts
     * to enter the Next Sequential Block. When it does so, it is scheduled
     * behind active Transactions of the same priority.
     *
     * Preempted Transactions which complete an assembly at an ASSEMBLE Block
     * are not permitted to leave the Block until all preemptions have been
     * cleared. More discussion of the preemption mechanism can be found in
     * Section 9.4. Preempted Transactions which have been removed from the
     * Match Chain do not participate in later assemblies even though they
     * remain in the ASSEMBLE Block.
     *
     * ASSEMBLE Blocks differ from GATHER Blocks in that succeeding Transactions
     * are destroyed at an ASSEMBLE.
     *
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

    @Override
    public boolean test(Xact tr) {
        return true;
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
    public int getCurrentCount() {
        
        return getMatchChain().size();
        
    }

    @Override
    public String name() {
        return "Assemble";
    }
}
