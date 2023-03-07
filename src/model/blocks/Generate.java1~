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
import utils.VarGlobals;
import model.entities.rng.RNG;

/**
 * A class representing the GENERATE block.
 *
 * @author Pau Fonseca i Casas
 * @author Ezequiel Andujar Montes
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
@NoArgsConstructor
public class Generate extends Bloc {

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
    private float C;
    //Creation limit.
    @Getter
    @Setter
    private float D;
    //Priority.
    @Getter
    @Setter
    private float E;
    //Not yet used.
    @Getter
    @Setter
    private int F;

    private float creationLimitNumber;
    private boolean creationLimit;

    /**
     * Creates a new instance of Generate.
     *
     * @param comentari the coment of te block.
     * @param label the label of the block.
     * @param A intergeneration time.
     * @param B halfrange or function modifier.
     * @param C start delay time.
     * @param D creation limit.
     * @param E priority.
     * @param F if 0 the parameter C is ignored.
     * @param gna
     */
    public Generate(String comentari, String label, String A, String B, Float C, Float D, Float E, int F, RNG gna) {

        super(Constants.idGenerate, label, comentari, gna);
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.E = E;
        this.F = F;
        this.setLabel(label);
        this.setComentari(comentari);
        creationLimitNumber = D;
        if (D > 0) {
            creationLimit = true;
        }

        VarGlobals.currentAssemblySet++;
    }

    @Override
    public Bloc execute(Xact tr) throws Exception {

        if ((creationLimit && creationLimitNumber > 0) || !creationLimit) {

            Float evalA = Float.valueOf(evaluate(A, getModel(), tr));
            Float evalB = Float.valueOf(evaluate(B, getModel(), tr));

            Xact xact;
            xact = new Xact();
            xact.setBloc(this);
            xact.setProces(getProces());
            xact.setCreatTime(getModel().getRelativeClock());
            xact.setPriority(E);
            xact.setAssemblySet(VarGlobals.currentAssemblySet);

            if (getModel().getRelativeClock() == 0 && F > 0) {
                xact.setMoveTime(C);
            } else {
                xact.setMoveTime(getModel().getRelativeClock() + getGna().generate(evalA, evalB));
            }
            getModel().incIdXact();
            xact.setID(getModel().getIdxact());
            getModel().getFEC().add(xact);
            creationLimitNumber--;
            incTrans(xact);
            System.out.println("Generate DONE.");
        } else {
            System.out.println("Creation limit reached.");
        }
        return nextBloc(tr);
    }

    @Override
    public String name() {
        return "Generate";
    }
}
