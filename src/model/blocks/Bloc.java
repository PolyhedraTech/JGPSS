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

import model.Proces;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import lombok.Getter;
import lombok.Setter;
import model.Model;
import model.entities.Xact;
import model.entities.rng.RNG;

/**
 * A class representing the ASSIGN GPSS block.
 *
 * @author Pau Fonseca i Casas
 * @author M.Dolores
 * @author Ezequiel Andujar
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
public abstract class Bloc {

    @Getter
    @Setter
    int id;
    @Getter
    @Setter
    private String label;
    @Getter
    @Setter
    private String comentari;
    @Getter
    @Setter
    int posx;
    @Getter
    @Setter
    private int posy;
    @Getter
    @Setter
    private Model model;
    @Getter
    @Setter
    private Proces proces;
    @Getter
    @Setter
    private int pos;
    @Getter
    @Setter
    private int currentCount;
    @Getter
    @Setter
    private RNG gna;
    private HashSet<Xact> entryCount, retryCount;

    /**
     * Match chain used for blocks MATCH, GATHER, ASSEMBLE
     */
    @Getter
    private final PriorityQueue<Xact> matchChain;

    /**
     * Creates a new instance of Bloc
     *
     * @param id
     * @param label
     * @param comentari
     * @param model
     */
    public Bloc(int id, String label, String comentari, Model model) {

        this(id, label, comentari);
        this.model = model;
    }

    public Bloc(int id, String label, String comentari, Model model, RNG gna) {

        this(id, label, comentari);
        this.model = model;
        this.gna = gna;
    }

    public Bloc(int id, String label, String comentari, RNG gna) {
        this(id, label, comentari);
        this.gna = gna;
    }

    public Bloc(int id, String label, String comentari) {
        this.id = id;
        this.label = label;
        this.comentari = comentari;
        entryCount = new HashSet<>();
        matchChain = new PriorityQueue<>();
        retryCount = new HashSet<>();
        currentCount = 0;
    }

    public Bloc() {
        entryCount = new HashSet<>();
        matchChain = new PriorityQueue<>();
        retryCount = new HashSet<>();
    }

    /**
     * Increases the transaction that have entered the block
     *
     * @param xact
     */
    public void incTrans(Xact xact) {

        if (entryCount.contains(xact)) {
            retryCount.add(xact);
        } else {
            entryCount.add(xact);
        }
    }

    /**
     * Returns the total transactions entries on the block
     *
     * @return
     */
    public int getEntryCount() {
        return entryCount.size();
    }

    public int getRetryCount() {
        return retryCount.size();
    }

    public void incCurrentCount() {
        currentCount++;
    }

    /**
     * To execute the block. Must be implemented in each block.
     *
     * @param tr the current XACT (that cross the block).
     * @return return NULL if no more blocs can be executed now. The next block
     * otherwise.
     * @throws java.lang.Exception if some error at runtime happens throws an
     * Exception
     */
    public abstract Bloc execute(Xact tr) throws Exception;
    
    
    public abstract String name();

    /**
     * Returns true if the bloc admits the current, false in case the xact is
     * refuse by the block. Must be implemented in each block
     *
     * @param tr the current XACT (that attempts to cross the bloc)
     * @return
     */
    public boolean test(Xact tr) {
        return true;
    }

    /**
     * Allows to obtanin thenext block in the current process of the XACT.
     * Modifies the current block of the XACT.
     *
     * @param tr the XACT to update it's current block.
     * @return the next block to be executed.
     */
    public Bloc nextBloc(Xact tr) {
        if (tr != null) {
            Bloc nextBloc = tr.getBloc();
            Proces pr = tr.getProces();
            ArrayList<Bloc> bls = pr.getBlocs();
            int mida = bls.size();
            int posicio = nextBloc.pos + 1;
            if (posicio < mida) {
                nextBloc = bls.get(posicio);
            } else {
                nextBloc = null;
            }

            tr.setBloc(nextBloc);
            return nextBloc;
        } else {
            return null;
        }
    }
    
    public void clear() {
        currentCount = 0;
        entryCount.clear();
        retryCount.clear();
        matchChain.clear();
    }
}
