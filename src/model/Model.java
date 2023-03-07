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
package model;

import model.entities.QueueReport;
import model.entities.Storage;
import model.entities.Xact;
import model.blocks.Generate;
import model.blocks.Bloc;
import model.blocks.Facility;
import java.io.Serializable;
import java.util.*;
import lombok.Data;
import model.entities.AmperVariable;
import model.entities.Function;
import model.entities.LogicSwitch;
import model.entities.SaveValue;
import utils.Constants;

/**
 * A class representing the model elements.
 *
 * @author Pau Fonseca i Casas
 * @author Ezequiel Andujar Montes
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
@Data
@SuppressWarnings("FieldMayBeFinal")
public final class Model implements Serializable {

    static final long serialVersionUID = 42L;

    private String name;
    private String description;
    private ArrayList<Proces> proces;
    private ArrayList<Storage> storages;
    private ArrayList<SaveValue> saveValues;
    private ArrayList<Function> functions;
    private ArrayList<LogicSwitch> switches;
    private ArrayList<AmperVariable<?>> amperVariables;
    private ArrayList<Matrix<Float>> matrix;

    /**
     * Current Event Chain
     */
    private PriorityQueue<Xact> CEC;

    /**
     * Future Event Chain
     */
    private PriorityQueue<Xact> FEC;

    /**
     * Blocked Event Chain
     */
    private HashMap<String, PriorityQueue<Xact>> BEC;

    /**
     * Preempted Xacts
     */
    private HashMap<String, PriorityQueue<Xact>> preemptedXacts;

    private HashMap<String, Facility> facilities;
    private HashMap<String, QueueReport> queues;

    /**
     * The transaction counter.
     */
    private int TC;
    /**
     * Identifier for the XACT created until the moment.
     */

    private int idxact;
    /**
     * Absolute simulation clock.
     */

    private ArrayList<Float> absoluteClock;

    /**
     * Relative simulation clock.
     */
    private float relativeClock;

    public Model() {

        proces = new ArrayList<>();
        storages = new ArrayList<>();
        saveValues = new ArrayList<>();
        matrix = new ArrayList<>();
        amperVariables = new ArrayList<>();
        functions = new ArrayList<>();
        switches = new ArrayList<>();
        absoluteClock = new ArrayList<>();

        CEC = new PriorityQueue<>(1000, this.getPriorityComparator());
        FEC = new PriorityQueue<>(1000, this.getTimeComparator());
        BEC = new HashMap<>();

        preemptedXacts = new HashMap<>();
        facilities = new HashMap<>();

        queues = new HashMap<>();
    }

    public Comparator<Xact> getPriorityComparator() {
        return (Xact o1, Xact o2) -> {
            if (o1.getPriority() > o2.getPriority()) {
                return -1;
            } else if (o1.getPriority() == o2.getPriority()) {
                return 0;
            } else {
                return 1;
            }
        };
    }

    public Comparator<Xact> getTimeComparator() {
        return (Xact o1, Xact o2) -> {
            if (o1.getMoveTime() < o2.getMoveTime()) {
                return -1;
            } else if (o1.getMoveTime() == o2.getMoveTime()) {
                return 0;
            } else {
                return 1;
            }
        };
    }

    public Float getAbsoluteClock() {

        Float sum = 0f;
        for (Float f : absoluteClock) {
            sum += f;
        }
        return sum;
    }

    public void incIdXact() {
        this.idxact++;
    }

    /**
     * Returns the saveValue entity named name
     *
     * @param name
     * @return
     */
    public SaveValue getSaveValue(String name) {

        return saveValues.stream()//
                .filter(sv -> sv.getName().equals(name))//
                .findFirst()//
                .orElse(null);
    }

    /**
     * Returns true if the relatedXact is currently preempted at any facility
     *
     * @param relatedXact
     * @return
     */
    public boolean preempted(Xact relatedXact) {

        while (preemptedXacts.entrySet().iterator().hasNext()) {

            Map.Entry<String, PriorityQueue<Xact>> pair;
            pair = preemptedXacts.entrySet().iterator().next();
            PriorityQueue<Xact> preempted = pair.getValue();

            if (preempted.contains(relatedXact)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the maximum capacity storage with titol name
     *
     * @param name the name of the storage
     * @return a Storage instance
     */
    public int getStorageMaxCapacity(String name) {
        Storage st;
        for (int i = 0; i < storages.size(); i++) {
            st = storages.get(i);
            if (st.getNom().equals(name)) {
                return storages.get(i).getValor();
            }
        }
        return 1;
    }

    /**
     * To initialize the GENERATE block. This method can be used as a template
     * for other initialization procedures.
     *
     * @throws java.lang.Exception
     */
    public void InitializeGenerateBocs() throws Exception {

        for (int j = 0; j < proces.size(); j++) {
            Proces p = proces.get(j);
            for (int k = 0; k < p.getBlocs().size(); k++) {
                Bloc b = p.getBlocs().get(k);
                if (b.getId() == Constants.idGenerate) {
                    ((Generate) b).execute(null);
                }
            }
        }
    }

    public int blocIndex(String bloc, Proces proces) {

        for (int k = 0; k < proces.getBlocs().size(); k++) {
            Bloc b = proces.getBlocs().get(k);
            if (b.getLabel().equals(bloc)) {
                return k;
            }
        }
        return -1;
    }

    /**
     * Returns the Bloc with the label label
     *
     * @param label
     * @return
     */
    public Bloc findBloc(String label) {

        for (Proces p : proces) {
            Bloc b = p.findBloc(label);
            if (b != null) {
                return b;
            }
        }
        return null;
    }

    public Storage getStorage(String sn) {

        return storages.stream()//
                .filter(s -> s.getNom().equals(sn))//
                .findFirst()//
                .orElse(null);
    }

    /**
     * Restore model status to the initial state
     */
    public void clean() {
        relativeClock = 0;
        CEC.clear();
        FEC.clear();
        BEC.clear();
        saveValues.forEach(sv -> sv.reset());
        facilities.forEach((k, v) -> v.clean());
        queues.forEach((k, v) -> v.clean());
        amperVariables.forEach(av -> av.reset());
        preemptedXacts.forEach((k, v) -> v.clear());

        proces.stream().forEach(p -> {
            p.getBlocs().stream().forEach(b -> {                
                b.clear();
            });
        });
    }

    /**
     * To execute the simulation model.
     *
     * @param b if true we execute the simulation step by step.
     * @throws java.lang.Exception
     */
    public void execute(boolean b) throws Exception {
        relativeClock = 0;
        InitializeGenerateBocs();
        if (!b) {
            executeAll();
        } else {
            executeStep();
        }
    }

    /**
     * To execute the simulation model.
     *
     * @throws java.lang.Exception
     */
    public void executeAll() throws Exception {
        Xact xact;
        //Simulation engine loop.

        while (TC > 0) {

            scanPhase();
            clockUpdatedPhase();

        }
        updateCurrentCount();
        System.out.println("Simulation terminated");
    }

    /**
     * To execute a single step of the simulation model. Executes untin a new
     * CLOCK UPDATE PHASE.
     *
     * @throws java.lang.Exception
     */
    public void executeStep() throws Exception {
        Xact xact;
        //Motor central de simulaci�.
        if (TC > 0) {
            scanPhase();
            clockUpdatedPhase();
        }
        updateCurrentCount();
    }

    private void scanPhase() throws Exception {
        Xact xact = CEC.poll();
        while (xact != null) {
            Bloc b = xact.getBloc();
            do {
                b = b.execute(xact);
            } while (b != null);
            xact = CEC.poll();
        }
    }

    private void clockUpdatedPhase() {

        Xact xact = FEC.poll();
        if (xact != null) {

            relativeClock = xact.getMoveTime();

            do {
                CEC.add(xact);
                xact = FEC.poll();

            } while (xact != null && xact.getMoveTime() == relativeClock);

            if (xact != null) {
                FEC.add(xact);
            }

            if (TC == 0) {
                absoluteClock.add(relativeClock);
            }
        }
        updateBEC();
    }

    private void updateBEC() {
        BEC.forEach((name, bloquedXacts) -> {

            Xact xactB = bloquedXacts.poll();

            if (xactB != null) {
                do {

                    if (xactB.restoreToFEC()) {
                        FEC.add(xactB);
                    } else {
                        CEC.add(xactB);
                    }
                    xactB = bloquedXacts.poll();

                } while (xactB != null);
            }
        });
    }

    /**
     * Updates the current count of the Blocs that has xact left
     */
    private void updateCurrentCount() {

        CEC.stream().forEach(xact -> {
            xact.getBloc().incCurrentCount();
        });

        FEC.stream().forEach(xact -> {
            xact.getBloc().incCurrentCount();
        });

        BEC.forEach((name, bloquedXacts) -> {
            bloquedXacts.forEach(xact -> {
                xact.getBloc().incCurrentCount();
            });
        });
    }
}
