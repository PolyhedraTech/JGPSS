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
 * A class representing the MATCH block.
 *
 * @author Pau Fonseca i Casas
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
@NoArgsConstructor
public class Match extends Bloc {

    @Getter
    @Setter
    @SuppressWarnings("FieldMayBeFinal")
    private String A;

    /**
     * Creates a new instance of Match.
     *
     * @param comentari the comment of the block.
     * @param label the label of the block.
     * @param A the number of transactions to be "matched".
     */
    public Match(String comentari, String label, String A) {
        super(Constants.idMatch, label, comentari);
        this.A = A;
    }

    /**
     * When a Transaction enters a MATCH Block, Operand A is evaluated
     * numerically, truncated, and the conjugate MATCH Block is found. If there
     * is no such Block, an Error Stop occurs.
     *
     * If the conjugate MATCH Block contains a Transaction (on its Match Chain)
     * of the same Assembly Set as the Active Transaction, the related
     * Transaction is removed from the Match Chain. If it is not currently
     * preempted at any Facility Entity, it is placed on the Current Events
     * Chain behind its priority peers. Similarly, if the Active Transaction is
     * not currently preempted at any Facility it is placed on the CEC, but
     * ahead of its peers.
     *
     * If either matching Transaction is currently preempted at a Facility, it
     * is not permitted to leave its MATCH Block until all preemptions have been
     * cleared on its behalf.
     *
     * If, when the Active Transaction enters the MATCH Block, no matching
     * Transaction is found, it comes to rest on the Match Chain of the MATCH
     * Block.
     *
     * @param tr
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public Bloc execute(Xact tr) throws Exception {

        Match matchBlock = (Match) getProces().findBloc(evaluate(A, getModel(), tr));

        if (matchBlock == null) {

            throw new Exception("In Match Bloc " + getLabel() + " at proces " + 
                    getProces().getDescpro() + "Conjugate match block not found");

        } else if (!matchBlock.getMatchChain().isEmpty()) {

            Xact relatedXact = matchBlock.findMatchingXact(tr);

            /**
             * If the conjugate MATCH Block contains a Transaction (on its Match
             * Chain) of the same Assembly Set as the Active Transaction, the
             * related Transaction is removed from the Match Chain
             */
            if (relatedXact != null) {
                matchBlock.getMatchChain().remove(relatedXact);

                /**
                 * If it is not currently preempted at any Facility Entity, it
                 * is placed on the Current Events Chain behind its priority
                 * peers
                 */
                boolean relatedPreempted = getModel().preempted(relatedXact);

                if (!relatedPreempted) {
                    relatedXact.setPriority(999);
                    getModel().getCEC().add(relatedXact);
                    nextBloc(relatedXact);
                } else {
                    matchBlock.getMatchChain().add(relatedXact);
                }

                /**
                 * if the Active Transaction is not currently preempted at any
                 * Facility it is placed on the CEC, but ahead of its peers.
                 */
                boolean activePreempted = getModel().preempted(tr);

                if (!activePreempted) {
                    tr.setPriority(1001);
                    getModel().getCEC().add(tr);
                    nextBloc(tr);
                } else {
                    getMatchChain().add(tr);
                }
            } /**
             * If, when the Active Transaction enters the MATCH Block, no
             * matching Transaction is found, it comes to rest on the Match
             * Chain of the MATCH Block.
             */
            else {
                matchBlock.getMatchChain().add(tr);
            }

        } /**
         * If, when the Active Transaction enters the MATCH Block, no matching
         * Transaction is found, it comes to rest on the Match Chain of the
         * MATCH Block.
         */
        else {
            matchBlock.getMatchChain().add(tr);
        }

        incTrans(tr);
        
        return null;
    }   

    /**
     *
     * @param xact
     * @return
     */
    public Xact findMatchingXact(Xact xact) {

        return getMatchChain().stream()//
                .filter(t -> t.getAssemblySet() == xact.getAssemblySet())//
                .findFirst()//
                .orElse(null);
    }   

    @Override
    public String name() {
        return "Match";
    }
}
