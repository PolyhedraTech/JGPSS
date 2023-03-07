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
package views;

import model.Proces;
import model.entities.Storage;
import model.blocks.*;
import java.awt.Frame;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.*;
import utils.Constants;
import utils.VarGlobals;
import model.entities.rng.RNG;

/**
 *
 * @author M.Dolores
 * @author Ezequiel Andujar Montes
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BlockDescriptionView extends JDialog {

    private static final long serialVersionUID = 1L;

    /**
     * Creates new form PantallaDescripcioBloc
     */
    private int tipusBloc;
    private Model model;
    private Proces proces;

    public BlockDescriptionView(Frame parent, Bloc bloc, int caso) {

        super(parent, Constants.tituloNewBloc, true);
        initComponents();
        VarGlobals.blocId = bloc.getId();
        blockName.setText(Constants.blocName.get(bloc.getId()).toUpperCase());
        buttonA.setVisible(false);
        comboOp.setVisible(false);
        GNACombo.setVisible(false);
        tipusBloc = bloc.getId();
        model = bloc.getModel();
        proces = bloc.getProces();
        initCombos();
        pintarBloc(bloc, caso);
    }

    public BlockDescriptionView(Frame parent, int tipusBloc, String urlbloc, Model model, Proces proces) {
        super(parent, Constants.tituloNewBloc, true);
        initComponents();
        VarGlobals.blocId = tipusBloc;
        this.tipusBloc = tipusBloc;
        this.model = model;
        this.proces = proces;
        buttonA.setVisible(false);
        blockName.setText(Constants.blocName.get(tipusBloc).toUpperCase());
        initCombos();
        dibuixarBloc(urlbloc);
        comboOp.setVisible(false);
        GNACombo.setVisible(false);
        GNALabel.setVisible(false);

        switch (tipusBloc) {
            case Constants.idGenerate:
                GNACombo.setVisible(true);
                GNALabel.setVisible(true);
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                jLabelC.setText(Constants.C);
                jLabelD.setText(Constants.D);
                jLabelE.setText(Constants.E);
                jLabelF.setText(Constants.F);
                break;

            case Constants.idFunavail:
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                jLabelC.setText(Constants.C);
                jLabelD.setText(Constants.D);
                jLabelE.setText(Constants.E);
                jLabelF.setText(Constants.F);
                break;

            case Constants.idRelease:
                buttonA.setText("Seize");
                buttonA.setVisible(true);
                jLabelA.setText(Constants.A);
                textA.setEnabled(false);
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                break;
            case Constants.idSeize:
            case Constants.idTerminate:
            case Constants.idFavail:
            case Constants.idAssemble:
            case Constants.idGather:
            case Constants.idMatch:
            case Constants.idPriority:
                jLabelA.setText(Constants.A);
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                break;
            case Constants.idAdvanced:
                GNACombo.setVisible(true);
                GNALabel.setVisible(true);
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                break;

            case Constants.idSavevavg:
                buttonA.setText("SaveValues...");
                buttonA.setVisible(true);
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                break;
            case Constants.idDepart:
            case Constants.idQueue:
            case Constants.idAssign:
            case Constants.idLoop:
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                break;
            case Constants.idSplit:
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                jLabelC.setText(Constants.C);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                break;
            case Constants.idTest:
                comboOp.setVisible(true);
                jLabelX.setText(Constants.X);
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                jLabelC.setText(Constants.C);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                break;
            case Constants.idTransfer:
                comboOp.setVisible(true);
                jLabelX.setText(Constants.A);
                jLabelA.setText(Constants.B);
                jLabelB.setText(Constants.C);
                jLabelC.setText(Constants.D);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                break;
            case Constants.idLogic:
                comboOp.setVisible(true);
                jLabelX.setText(Constants.X);
                jLabelA.setText(Constants.A);
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                break;
            case Constants.idGate:
                comboOp.setVisible(true);
                jLabelX.setText(Constants.X);
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                break;
            case Constants.idBuffer:
                buttonA.setVisible(true);
                textA.setVisible(false);
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                break;
            case Constants.idEnter:
            case Constants.idLeave:
                buttonA.setText("Storages...");
                buttonA.setVisible(true);
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                textA.setEnabled(false);
                break;
            case Constants.idSavail:
            case Constants.idSunavail:
                buttonA.setVisible(true);
                jLabelA.setText(Constants.A);
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                textA.setEnabled(false);
                break;
            default:
                break;
        }
    }

    private void initCombos() {

        GNACombo.setModel(new DefaultComboBoxModel(getGNAnames()));
        comboOp.setModel(new DefaultComboBoxModel(generarVectorGate()));
    }

    private void pintarBloc(Bloc b, int caso) {

        textDescripcio.setText(b.getComentari());
        jTextFielLabel.setText(b.getLabel());

        if (caso == Constants.ConsultarBloc) {
            botoCancel.setVisible(false);
        }
        switch (b.getId()) {
            case Constants.idGenerate:
                Generate g = (Generate) b;
                jLabelA.setText(Constants.A);
                textA.setText(g.getA());
                jLabelB.setText(Constants.B);
                TextB.setText(g.getB());
                jLabelC.setText(Constants.C);
                TextC.setText(Float.toString(g.getC()));
                jLabelD.setText(Constants.C);
                TextD.setText(Float.toString(g.getD()));
                jLabelE.setText(Constants.E);
                TextE.setText(Float.toString(g.getE()));
                jLabelF.setText(Constants.F);
                TextF.setText(Integer.toString(g.getF()));

                GNACombo.setVisible(true);
                GNALabel.setVisible(true);
                GNACombo.setModel(new DefaultComboBoxModel(getGNAnames()));
                GNACombo.getModel().setSelectedItem(b.getGna().name());

                dibuixarBloc(Constants.UrlGenerate);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                    TextB.setEnabled(false);
                    TextC.setEnabled(false);
                    TextE.setEnabled(false);
                    TextD.setEnabled(false);
                    TextF.setEnabled(false);
                }

                break;
            case Constants.idFunavail:
                Funavail Fun = (Funavail) b;
                jLabelA.setText(Constants.A);
                textA.setText(Fun.getA());
                jLabelB.setText(Constants.B);
                TextB.setText(Fun.getB());
                jLabelC.setText(Constants.C);
                TextC.setText(Fun.getC());
                jLabelD.setText(Constants.C);
                TextD.setText(Fun.getD());
                jLabelE.setText(Constants.E);
                TextE.setText(Fun.getE());
                jLabelF.setText(Constants.F);
                TextF.setText(Fun.getF());

                dibuixarBloc(Constants.UrlFunavail);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                    TextB.setEnabled(false);
                    TextC.setEnabled(false);
                    TextE.setEnabled(false);
                    TextD.setEnabled(false);
                    TextF.setEnabled(false);
                }

                break;

            case Constants.idTerminate:
                jLabelA.setText(Constants.A);
                Terminate t = (Terminate) b;
                textA.setText(Integer.toString(t.getA()));
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                dibuixarBloc(Constants.UrlTerminate);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                }
                break;
            case Constants.idRelease:
                jLabelA.setText(Constants.A);
                buttonA.setText("Seize...");
                buttonA.setVisible(true);
                Release r = (Release) b;
                textA.setText(r.getA());
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                dibuixarBloc(Constants.UrlRelease);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);

                }
                break;
            case Constants.idSeize:
                jLabelA.setText(Constants.A);
                Seize s = (Seize) b;
                textA.setText(s.getA());
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                dibuixarBloc(Constants.UrlSeize);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                }
                break;
            case Constants.idAdvanced:
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                Advance a = (Advance) b;
                textA.setText(a.getA());
                TextB.setText(a.getB());
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);

                GNACombo.setVisible(true);
                GNALabel.setVisible(true);
                GNACombo.setModel(new DefaultComboBoxModel(getGNAnames()));
                GNACombo.getModel().setSelectedItem(a.getGna().name());

                dibuixarBloc(Constants.UrlAdvanced);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                    TextB.setEnabled(false);
                }
                break;
            case Constants.idAssign:
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                Assign as = (Assign) b;
                textA.setText(as.getA());
                TextB.setText(Float.toString(as.getB()));
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                dibuixarBloc(Constants.UrlAssign);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                    TextB.setEnabled(false);
                }
                break;
            case Constants.idDepart:
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                Depart d = (Depart) b;
                textA.setText(d.getA());
                TextB.setText(Integer.toString(d.getB()));
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                dibuixarBloc(Constants.UrlDepart);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                    TextB.setEnabled(false);
                }
                break;
            case Constants.idQueue:
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                Queue q = (Queue) b;
                textA.setText(q.getA());
                TextB.setText(Integer.toString(q.getB()));
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                GNACombo.setVisible(false);
                dibuixarBloc(Constants.UrlQueue);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                    TextB.setEnabled(false);
                }
                break;
            case Constants.idEnter:
                buttonA.setText("Storages...");
                buttonA.setVisible(true);
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                Enter e = (Enter) b;
                textA.setText(e.getA());
                TextB.setText(Integer.toString(e.getB()));
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                dibuixarBloc(Constants.UrlEnter);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                    TextB.setEnabled(false);
                } else if (caso == Constants.ModificarBloc) {
                    textA.setEnabled(false);
                    buttonA.setVisible(true);
                }
                break;
            case Constants.idLeave:
                buttonA.setText("Storages...");
                buttonA.setVisible(true);
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                Leave l = (Leave) b;
                textA.setText(l.getA());
                TextB.setText(Integer.toString(l.getB()));
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                dibuixarBloc(Constants.UrlLeave);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                    TextB.setEnabled(false);
                } else if (caso == Constants.ModificarBloc) {
                    textA.setEnabled(false);
                    buttonA.setVisible(true);
                }
                break;

            case Constants.idTest:
                comboOp.setVisible(true);
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                jLabelC.setText(Constants.C);
                jLabelX.setText(Constants.X);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                Test test = (Test) b;

                comboOp.setModel(new DefaultComboBoxModel(this.generarVectorTest()));
                textA.setText(test.getA());
                TextB.setText(test.getB());
                TextC.setText(test.getC());
                comboOp.setSelectedItem(test.getX());

                dibuixarBloc(Constants.UrlTest);
                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                    TextB.setEnabled(false);
                    TextC.setEnabled(false);
                    comboOp.setEnabled(false);
                }
                break;
            case Constants.idTransfer:
                comboOp.setVisible(true);
                jLabelA.setText(Constants.B);
                jLabelB.setText(Constants.C);
                jLabelC.setText(Constants.D);
                jLabelX.setText(Constants.A);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                Transfer transfer = (Transfer) b;

                comboOp.setModel(new DefaultComboBoxModel(this.generarVectorTransfer()));
                textA.setText(transfer.getB());
                TextB.setText(transfer.getC());
                TextC.setText(transfer.getD());
                comboOp.setSelectedItem(transfer.getA());

                dibuixarBloc(Constants.UrlTransfer);
                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                    TextB.setEnabled(false);
                    TextC.setEnabled(false);
                    comboOp.setEnabled(false);
                }

                break;
            case Constants.idLogic:
                comboOp.setVisible(true);
                jLabelA.setText(Constants.A);
                jLabelX.setText(Constants.X);
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                Logic log = (Logic) b;

                comboOp.setModel(new DefaultComboBoxModel<>(this.generarVectorLogic()));
                textA.setText(log.getA());
                comboOp.setSelectedItem(log.getX());

                dibuixarBloc(Constants.UrlLogic);
                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                    comboOp.setEnabled(false);
                }
                break;
            case Constants.idGate:
                comboOp.setVisible(true);
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                jLabelX.setText(Constants.X);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                Gate gate = (Gate) b;

                comboOp.setModel(new DefaultComboBoxModel(generarVectorGate()));
                textA.setText(gate.getA());
                TextB.setText(gate.getB());
                comboOp.setSelectedItem(gate.getX());

                dibuixarBloc(Constants.UrlGate);
                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                    TextB.setEnabled(false);
                    comboOp.setEnabled(false);
                }
                break;
            case Constants.idSavevavg:
                buttonA.setText("SaveValue...");
                buttonA.setVisible(true);
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                Savevalue sav = (Savevalue) b;
                textA.setText(sav.getA());
                TextB.setText(sav.getB());
                dibuixarBloc(Constants.UrlSavevavg);
                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                    TextB.setEnabled(false);
                }
                break;
            case Constants.idLoop:
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                Loop loop = (Loop) b;
                textA.setText(loop.getA());
                TextB.setText(loop.getB());
                dibuixarBloc(Constants.UrlLoop);
                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                    TextB.setEnabled(false);
                }
                break;

            case Constants.idSplit:
                jLabelA.setText(Constants.A);
                jLabelB.setText(Constants.B);
                jLabelC.setText(Constants.C);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                Split split = (Split) b;
                textA.setText(Integer.toString(split.getA()));
                TextB.setText(split.getB());
                TextC.setText(split.getC());
                dibuixarBloc(Constants.UrlSplit);
                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                    TextB.setEnabled(false);
                    TextC.setEnabled(false);
                }
                break;

            case Constants.idFavail:
                jLabelA.setText(Constants.A);
                Favail f = (Favail) b;
                textA.setText(f.getA());
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                dibuixarBloc(Constants.UrlTerminate);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                }
                break;
            case Constants.idSavail:
                jLabelA.setText(Constants.A);
                Savail savail = (Savail) b;
                textA.setText(savail.getA());
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                dibuixarBloc(Constants.UrlSavail);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                } else if (caso == Constants.ModificarBloc) {
                    textA.setEnabled(false);
                    buttonA.setVisible(true);
                }
                break;
            case Constants.idSunavail:
                jLabelA.setText(Constants.A);
                Sunavail sunavail = (Sunavail) b;
                textA.setText(sunavail.getA());
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                dibuixarBloc(Constants.UrlSunavail);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                } else if (caso == Constants.ModificarBloc) {
                    textA.setEnabled(false);
                    buttonA.setVisible(true);
                }
                break;
            case Constants.idAssemble:
                jLabelA.setText(Constants.A);
                Assemble ass = (Assemble) b;
                textA.setText(Integer.toString(ass.getA()));
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                dibuixarBloc(Constants.UrlAssemble);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                }
                break;

            case Constants.idGather:
                jLabelA.setText(Constants.A);
                Gather gather = (Gather) b;
                textA.setText(Integer.toString(gather.getA()));
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                dibuixarBloc(Constants.UrlGather);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                }
                break;

            case Constants.idMatch:
                jLabelA.setText(Constants.A);
                Match m = (Match) b;
                textA.setText(m.getA());
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                dibuixarBloc(Constants.UrlMatch);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                }
                break;
            case Constants.idPriority:
                jLabelA.setText(Constants.A);
                Priority pri = (Priority) b;
                textA.setText(Integer.toString(pri.getA()));
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                dibuixarBloc(Constants.UrlPriority);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                }
                break;
            case Constants.idBuffer:
                textA.setVisible(false);
                TextB.setVisible(false);
                TextC.setVisible(false);
                TextD.setVisible(false);
                TextE.setVisible(false);
                TextF.setVisible(false);
                dibuixarBloc(Constants.UrlBuffer);

                if (caso == Constants.ConsultarBloc) {
                    textDescripcio.setEnabled(false);
                    jTextFielLabel.setEnabled(false);
                    textA.setEnabled(false);
                }
                break;

            default:
                generarPantallaError(Constants.errorconsultarbloc);
                break;
        }
    }

    private void dibuixarBloc(String url) {

        ImageIcon imagen = new ImageIcon(url);
        jLabel2.setSize(imagen.getIconHeight(), imagen.getIconWidth());
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource(url)));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textDescripcio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        textA = new javax.swing.JTextField();
        TextB = new javax.swing.JTextField();
        TextC = new javax.swing.JTextField();
        TextD = new javax.swing.JTextField();
        TextE = new javax.swing.JTextField();
        TextF = new javax.swing.JTextField();
        botoCancel = new javax.swing.JButton();
        botoOK = new javax.swing.JButton();
        jLabelA = new javax.swing.JLabel();
        jLabelB = new javax.swing.JLabel();
        jLabelC = new javax.swing.JLabel();
        jLabelD = new javax.swing.JLabel();
        jLabelE = new javax.swing.JLabel();
        jLabelF = new javax.swing.JLabel();
        buttonA = new javax.swing.JButton();
        comboOp = new javax.swing.JComboBox();
        jLabelX = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFielLabel = new javax.swing.JTextField();
        blockName = new javax.swing.JLabel();
        GNACombo = new javax.swing.JComboBox<>();
        GNALabel = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("BLOC COMMENTS");

        textDescripcio.setText("<<--comment-->>");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel2.setOpaque(true);

        TextB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBActionPerformed(evt);
            }
        });

        botoCancel.setText("Cancel");
        botoCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoCancelActionPerformed(evt);
            }
        });

        botoOK.setText("OK");
        botoOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoOKActionPerformed(evt);
            }
        });

        jLabelA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabelB.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabelC.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabelD.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabelE.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabelF.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        buttonA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAActionPerformed(evt);
            }
        });

        jLabelX.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("LABEL");

        blockName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        blockName.setText("BLOC NAME");

        GNACombo.setSelectedItem(null);

        GNALabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        GNALabel.setText("GNA");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .add(botoCancel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(botoOK, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 62, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabelF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                            .add(jLabelE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                            .add(jLabelD, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabelA, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabelB, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabelC, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabelX, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
                        .add(28, 28, 28)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                                .add(textA, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(buttonA, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, TextE)
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, TextD, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, TextC, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, TextB, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                                    .add(TextF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE))
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, comboOp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 257, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 121, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel3)
                            .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(GNALabel))
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(12, 12, 12)
                                .add(blockName)
                                .add(0, 0, Short.MAX_VALUE))
                            .add(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, textDescripcio)
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextFielLabel)))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(GNACombo, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .add(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 61, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(31, 31, 31)
                        .add(blockName)))
                .add(36, 36, 36)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(textDescripcio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextFielLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(GNACombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(GNALabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelX, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(comboOp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(9, 9, 9)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jLabelA, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(textA, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(buttonA, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jLabelB, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(TextB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(TextC, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(7, 7, 7)
                        .add(jLabelC, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .add(3, 3, 3)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabelD, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, TextD, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(jLabelE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(TextE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabelF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(TextF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botoOK)
                    .add(botoCancel))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        VarGlobals.continuar = false;
        setVisible(false);
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void buttonAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAActionPerformed

        switch (VarGlobals.blocId) {

            case Constants.idRelease:
                openSeizeView();
                break;
            case Constants.idEnter:
            case Constants.idLeave:
                openStorageView();
                break;
            case Constants.idSavevavg:
                openSaveValueView();
                break;
        }
    }//GEN-LAST:event_buttonAActionPerformed

    void openStorageView() {
        if (model.getStorages().isEmpty()) {
            generarPantallaError("No Storages defined");
            return;
        }

        StoragesView descBloc = new StoragesView(null, true);
        descBloc.setLocationRelativeTo(this);
        descBloc.setVisible(true);
        descBloc.dispose();

        if (VarGlobals.continuar) {

            Storage storage = model.getStorage(VarGlobals.nomStorageSeleccionat);

            if (storage != null) {
                textA.setText(VarGlobals.nomStorageSeleccionat);
                TextB.setText(String.valueOf(storage.getValor()));
            } else {
                generarPantallaError("Storage not valid");
            }
        }
    }

    void openSaveValueView() {

        if (model.getSaveValues().isEmpty()) {
            generarPantallaError("No SaveValues defined");
            return;
        }

        InitialView initialView = new InitialView(null, true);
        initialView.setLocationRelativeTo(this);
        initialView.setVisible(true);
        initialView.dispose();

        if (VarGlobals.continuar) {
            textA.setText(VarGlobals.nameSaveValueSelected);
        }
    }

    void openSeizeView() {

        List<Seize> seizes = proces.getBlocs().stream()//
                .filter(b -> b instanceof Seize)//    
                .map(b -> (Seize) b)
                .collect(Collectors.toList());

        if (seizes.isEmpty()) {
            generarPantallaError("No Seizes defined");
            return;
        }
        SeizeView seizeView = new SeizeView(null, true, seizes);
        seizeView.setLocationRelativeTo(this);
        seizeView.setVisible(true);
        seizeView.dispose();

        if (VarGlobals.continuar) {
            textA.setText(VarGlobals.nameSeizeSelected);
        }
    }

    private void botoOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoOKActionPerformed

        boolean tancarPantalla = true;
        VarGlobals.continuar = true;
        String strX, gnaType;
        RNG gna;
        try {
            switch (tipusBloc) {
                case Constants.idGenerate:
                    Generate g;
                    comprovarValorsPerDefecte(Constants.idGenerate);

                    gnaType = GNACombo.getSelectedItem().toString();
                    gna = VarGlobals.getGNA(gnaType);

                    g = new Generate(textDescripcio.getText(), jTextFielLabel.getText(),
                            textA.getText(),
                            TextB.getText(),
                            Float.valueOf(TextC.getText()),
                            Float.valueOf(TextD.getText()),
                            Float.valueOf(TextE.getText()),
                            Integer.parseInt(TextF.getText()),
                            gna);
                    g.setModel(model);
                    g.setProces(proces);
                    VarGlobals.bloc = g;
                    break;
                case Constants.idTerminate: {
                    Terminate t;
                    comprovarValorsPerDefecte(Constants.idTerminate);
                    t = new Terminate(textDescripcio.getText(), jTextFielLabel.getText(),
                            Integer.parseInt(textA.getText()));
                    t.setModel(model);
                    t.setProces(proces);
                    VarGlobals.bloc = t;
                    break;
                }
                case Constants.idAdvanced:
                    Advance a;
                    comprovarValorsPerDefecte(Constants.idAdvanced);

                    gnaType = GNACombo.getSelectedItem().toString();
                    gna = VarGlobals.getGNA(gnaType);

                    a = new Advance(textDescripcio.getText(), jTextFielLabel.getText(),
                            textA.getText(),
                            TextB.getText(), gna);
                    a.setModel(model);
                    a.setProces(proces);
                    VarGlobals.bloc = a;
                    break;
                case Constants.idAssign:
                    Assign as;
                    comprovarValorsPerDefecte(Constants.idAssign);
                    as = new Assign(textDescripcio.getText(), jTextFielLabel.getText(),
                            textA.getText(),
                            Float.valueOf(TextB.getText()));
                    as.setModel(model);
                    as.setProces(proces);
                    VarGlobals.bloc = as;
                    break;
                case Constants.idDepart:
                    Depart d;
                    if (!textA.getText().equals("")) {
                        comprovarValorsPerDefecte(Constants.idDepart);
                        d = new Depart(textDescripcio.getText(), jTextFielLabel.getText(),
                                textA.getText(),
                                Integer.parseInt(TextB.getText()));
                        d.setModel(model);
                        d.setProces(proces);
                        VarGlobals.bloc = d;
                    } else {
                        generarPantallaError(Constants.ErrorFaltaA);
                        tancarPantalla = false;
                    }
                    break;
                case Constants.idQueue:
                    Queue q;
                    if (!textA.getText().equals("")) {
                        comprovarValorsPerDefecte(Constants.idQueue);
                        q = new Queue(textDescripcio.getText(), jTextFielLabel.getText(),
                                textA.getText(),
                                Integer.parseInt(TextB.getText()));
                        q.setModel(model);
                        q.setProces(proces);
                        VarGlobals.bloc = q;
                    } else {
                        generarPantallaError(Constants.ErrorFaltaA);
                        tancarPantalla = false;
                    }
                    break;
                case Constants.idEnter:
                    Enter e;
                    comprovarValorsPerDefecte(Constants.idEnter);
                    if (!textA.getText().equals("")) {
                        e = new Enter(textDescripcio.getText(), jTextFielLabel.getText(),
                                textA.getText(),
                                Integer.parseInt(TextB.getText()));
                        e.setModel(model);
                        e.setProces(proces);
                        VarGlobals.bloc = e;
                    } else {
                        //error pero que no hi ha storage
                        generarPantallaError(Constants.ErrorFaltaStorage);
                        tancarPantalla = false;
                    }
                    break;
                case Constants.idLeave:
                    Leave l;
                    comprovarValorsPerDefecte(Constants.idLeave);
                    if (!textA.getText().equals("")) {
                        l = new Leave(textDescripcio.getText(), jTextFielLabel.getText(),
                                textA.getText(),
                                Integer.parseInt(TextB.getText()));
                        l.setModel(model);
                        l.setProces(proces);
                        VarGlobals.bloc = l;
                    } else {
                        //error pero que no hi ha storage
                        generarPantallaError(Constants.ErrorFaltaStorage);
                        tancarPantalla = false;

                    }
                    break;
                case Constants.idRelease:
                    Release r;
                    if (!textA.getText().equals("")) {
                        r = new Release(textDescripcio.getText(), jTextFielLabel.getText(),
                                textA.getText());
                        r.setModel(model);
                        r.setProces(proces);
                        VarGlobals.bloc = r;
                    } else {
                        generarPantallaError(Constants.ErrorFaltaA);
                        tancarPantalla = false;
                    }
                    break;
                case Constants.idSeize:
                    Seize s;
                    if (!textA.getText().equals("")) {
                        s = new Seize(textDescripcio.getText(), jTextFielLabel.getText(),
                                textA.getText());
                        s.setModel(model);
                        s.setProces(proces);
                        VarGlobals.bloc = s;
                    } else {
                        generarPantallaError(Constants.ErrorFaltaA);
                        tancarPantalla = false;
                    }
                    break;
                case Constants.idTest: {
                    Test t;
                    strX = (String) comboOp.getSelectedItem();
                    if (!strX.equals("")) {
                        if (!textA.getText().equals("")) {
                            comprovarValorsPerDefecte(Constants.idTest);
                            t = new Test(textDescripcio.getText(), jTextFielLabel.getText(),
                                    strX, textA.getText(), TextB.getText(), TextC.getText());
                            t.setModel(model);
                            t.setProces(proces);
                            VarGlobals.bloc = t;
                        } else {
                            generarPantallaError(Constants.ErrorFaltaA);
                            tancarPantalla = false;
                        }
                    } else {
                        generarPantallaError(Constants.ErrorFaltaX);
                        tancarPantalla = false;
                    }
                    break;
                }
                case Constants.idTransfer: {
                    Transfer transfer;
                    strX = (String) comboOp.getSelectedItem();
                    if (!strX.equals("")) {
                        comprovarValorsPerDefecte(Constants.idTransfer);
                        transfer = new Transfer(textDescripcio.getText(), jTextFielLabel.getText(),
                                strX, textA.getText(), TextB.getText(), TextC.getText());
                        transfer.setModel(model);
                        transfer.setProces(proces);
                        VarGlobals.bloc = transfer;
                    } else {
                        generarPantallaError(Constants.ErrorFaltaA);
                        tancarPantalla = false;
                    }
                    break;
                }
                case Constants.idLogic:
                    Logic log;
                    if (!textA.getText().equals("")) {
                        log = new Logic(textDescripcio.getText(), jTextFielLabel.getText(),
                                (String) comboOp.getSelectedItem(),
                                textA.getText());
                        log.setModel(model);
                        log.setProces(proces);
                        VarGlobals.bloc = log;
                    } else {
                        generarPantallaError(Constants.ErrorFaltaA);
                        tancarPantalla = false;
                    }
                    break;
                case Constants.idGate: {
                    Gate gat;
                    Transfer transfer;
                    strX = (String) comboOp.getSelectedItem();
                    if (!strX.equals("")) {
                        if (!textA.getText().equals("")) {
                            gat = new Gate(textDescripcio.getText(), jTextFielLabel.getText(),
                                    (String) comboOp.getSelectedItem(),
                                    textA.getText(),
                                    TextB.getText());
                            gat.setModel(model);
                            gat.setProces(proces);
                            VarGlobals.bloc = gat;
                        } else {
                            generarPantallaError(Constants.ErrorFaltaA);
                            tancarPantalla = false;
                        }
                    } else {
                        generarPantallaError(Constants.ErrorFaltaX);
                        tancarPantalla = false;
                    }
                    break;
                }
                case Constants.idSavevavg:
                    Savevalue sav;
                    if (!textA.getText().equals("")) {
                        comprovarValorsPerDefecte(Constants.idSavevavg);
                        sav = new Savevalue(textDescripcio.getText(), jTextFielLabel.getText(),
                                textA.getText(),
                                TextB.getText());

                        sav.setModel(model);
                        sav.setProces(proces);

                        VarGlobals.bloc = sav;
                    } else {
                        generarPantallaError(Constants.ErrorFaltaA);
                        tancarPantalla = false;
                    }
                    break;
                case Constants.idLoop:
                    Loop loop;
                    if (!textA.getText().equals("")) {
                        loop = new Loop(textDescripcio.getText(), jTextFielLabel.getText(),
                                textA.getText(),
                                TextB.getText());
                        loop.setModel(model);
                        loop.setProces(proces);
                        VarGlobals.bloc = loop;
                    } else {
                        generarPantallaError(Constants.ErrorFaltaA);
                        tancarPantalla = false;
                    }
                    break;
                case Constants.idSplit:
                    Split split;
                    if (!textA.getText().equals("")) {
                        comprovarValorsPerDefecte(Constants.idSplit);
                        split = new Split(textDescripcio.getText(), jTextFielLabel.getText(),
                                Integer.parseInt(textA.getText()),
                                TextB.getText(),
                                TextC.getText());
                        split.setModel(model);
                        split.setProces(proces);

                        VarGlobals.bloc = split;
                    } else {
                        generarPantallaError(Constants.ErrorFaltaA);
                        tancarPantalla = false;
                    }
                    break;

                case Constants.idFunavail:
                    //TODO validar los valores de entrada!!!!!!!
                    Funavail f;
                    comprovarValorsPerDefecte(Constants.idFunavail);
                    f = new Funavail(textDescripcio.getText(), jTextFielLabel.getText(),
                            textA.getText(),
                            TextB.getText(),
                            TextC.getText(),
                            TextD.getText(),
                            TextE.getText(),
                            TextF.getText());
                    f.setModel(model);
                    f.setProces(proces);
                    VarGlobals.bloc = f;
                    break;

                case Constants.idSavail:
                    Savail savail;
                    if (!textA.getText().equals("")) {
                        savail = new Savail(textDescripcio.getText(), jTextFielLabel.getText(),
                                textA.getText());
                        savail.setModel(model);
                        savail.setProces(proces);
                        VarGlobals.bloc = savail;
                    } else {
                        //error pero que no hi ha storage
                        generarPantallaError(Constants.ErrorFaltaStorage);
                        tancarPantalla = false;
                    }
                    break;

                case Constants.idSunavail:
                    Sunavail sunavail;
                    if (!textA.getText().equals("")) {
                        sunavail = new Sunavail(textDescripcio.getText(), jTextFielLabel.getText(),
                                textA.getText());
                        sunavail.setModel(model);
                        sunavail.setProces(proces);
                        VarGlobals.bloc = sunavail;
                    } else {
                        //error pero que no hi ha storage
                        generarPantallaError(Constants.ErrorFaltaStorage);
                        tancarPantalla = false;
                    }
                    break;
                case Constants.idAssemble:
                    Assemble ass;
                    comprovarValorsPerDefecte(Constants.idAssemble);
                    ass = new Assemble(textDescripcio.getText(), jTextFielLabel.getText(),
                            Integer.parseInt(textA.getText()));
                    ass.setModel(model);
                    ass.setProces(proces);
                    VarGlobals.bloc = ass;
                    break;
                case Constants.idGather:
                    Gather gather;
                    comprovarValorsPerDefecte(Constants.idGather);
                    gather = new Gather(textDescripcio.getText(), jTextFielLabel.getText(),
                            Integer.parseInt(textA.getText()));
                    gather.setModel(model);
                    gather.setProces(proces);
                    VarGlobals.bloc = gather;
                    break;
                case Constants.idMatch:
                    Match m;
                    if (!textA.getText().equals("")) {
                        m = new Match(textDescripcio.getText(), jTextFielLabel.getText(),
                                textA.getText());

                        m.setModel(model);
                        m.setProces(proces);
                        VarGlobals.bloc = m;
                    } else {
                        generarPantallaError(Constants.ErrorFaltaA);
                        tancarPantalla = false;
                    }
                    break;
                case Constants.idBuffer:
                    Buffer buf;
                    buf = new Buffer(textDescripcio.getText(), jTextFielLabel.getText());
                    buf.setModel(model);
                    buf.setProces(proces);
                    VarGlobals.bloc = buf;
                    break;
                case Constants.idPriority:
                    Priority pri;
                    comprovarValorsPerDefecte(Constants.idPriority);
                    pri = new Priority(textDescripcio.getText(), jTextFielLabel.getText(),
                            Integer.parseInt(textA.getText()));
                    pri.setModel(model);
                    pri.setProces(proces);
                    VarGlobals.bloc = pri;
                    break;
                default:
                    break;
            }
        } catch (NumberFormatException nf) {
            //si les dades s�n incorrectes o falten dades
            generarPantallaError(Constants.errorDades);
            tancarPantalla = false;

        }
        if (tancarPantalla) {
            setVisible(false);
            dispose();
        }
    }//GEN-LAST:event_botoOKActionPerformed

    private void botoCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoCancelActionPerformed

        VarGlobals.continuar = false;
        setVisible(false);
        dispose();
    }//GEN-LAST:event_botoCancelActionPerformed

    private void TextBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBActionPerformed

    private void comprovarValorsPerDefecte(int caso) {

        switch (caso) {
            case Constants.idGenerate:
                if (textA.getText().equals("") || (textA.getText() == null)) {
                    textA.setText("0");
                }
                if (TextB.getText().equals("") || (TextB.getText() == null)) {
                    TextB.setText("0");
                }
                if (TextC.getText().equals("") || (TextC.getText() == null)) {
                    TextC.setText("0");
                }
                if (TextD.getText().equals("") || (TextD.getText() == null)) {
                    TextD.setText("0");
                }
                if (TextE.getText().equals("") || (TextE.getText() == null)) {
                    TextE.setText("0");
                }
                if (TextF.getText().equals("") || (TextF.getText() == null)) {
                    TextF.setText("0");
                }
                break;

            case Constants.idTerminate:
            case Constants.idAssemble:
            case Constants.idGather:
            case Constants.idPriority:
            case Constants.idSplit:
                if (textA.getText().equals("") || (textA.getText() == null)) {
                    textA.setText("0");
                }
                break;

            case Constants.idAdvanced:
            case Constants.idTest:
                if (textA.getText().equals("") || (textA.getText() == null)) {
                    textA.setText("0");
                }
                if (TextB.getText().equals("") || (TextB.getText() == null)) {
                    TextB.setText("0");
                }
                break;
            case Constants.idQueue:
            case Constants.idDepart:
            case Constants.idEnter:
            case Constants.idLeave:
            case Constants.idSavevavg:
                if (TextB.getText().equals("") || (TextB.getText() == null)) {
                    TextB.setText("0");
                }
                break;
            case Constants.idTransfer:
                if (TextC.getText().equals("") || (TextC.getText() == null)) {
                    TextC.setText("0");
                }
                break;

            default:
                break;
        }

    }

    private void generarPantallaError(String mensage) {
        ErrorView perror = new ErrorView(mensage);
        perror.setLocationRelativeTo(this);
        perror.setVisible(true);
        perror.dispose();
    }

    private Object[] getGNAnames() {
        return Stream.of(VarGlobals.GNAType.values())//
                .map(Enum::name)//
                .collect(Collectors.toList()).toArray();
    }

    private Object[] generarVectorTest() {

        ArrayList<String> vTest = new ArrayList<>();
        vTest.add("");
        vTest.add(Test.E);
        vTest.add(Test.G);
        vTest.add(Test.GE);
        vTest.add(Test.L);
        vTest.add(Test.LE);
        vTest.add(Test.NE);

        return vTest.toArray();
    }

    private Object[] generarVectorTransfer() {

        ArrayList<String> vTransfer = new ArrayList<>();
        vTransfer.add("");
        vTransfer.add(Transfer.ALL);
        vTransfer.add(Transfer.BOTH);
        vTransfer.add(Transfer.FN);
        vTransfer.add(Transfer.FRACTION);
        vTransfer.add(Transfer.NUMBER);
        vTransfer.add(Transfer.P);
        vTransfer.add(Transfer.PICK);
        vTransfer.add(Transfer.SBR);
        vTransfer.add(Transfer.SNA);

        return vTransfer.toArray();
    }

    private Object[] generarVectorLogic() {

        ArrayList<String> vLogic = new ArrayList<>();
        vLogic.add("");
        vLogic.add(Logic.I);
        vLogic.add(Logic.R);
        vLogic.add(Logic.S);

        return vLogic.toArray();
    }

    private Object[] generarVectorGate() {

        ArrayList vGate = new ArrayList();
        vGate.add("");
        vGate.add(Gate.LS);
        vGate.add(Gate.SF);
        vGate.add(Gate.NU);

        return vGate.toArray();
    }

    public Object[] getSVnames() {

        return model.getSaveValues().stream()//
                .map(sv -> sv.getName())//
                .collect(Collectors.toList())//
                .toArray();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> GNACombo;
    private javax.swing.JLabel GNALabel;
    private javax.swing.JTextField TextB;
    private javax.swing.JTextField TextC;
    private javax.swing.JTextField TextD;
    private javax.swing.JTextField TextE;
    private javax.swing.JTextField TextF;
    private javax.swing.JLabel blockName;
    private javax.swing.JButton botoCancel;
    private javax.swing.JButton botoOK;
    private javax.swing.JButton buttonA;
    private javax.swing.JComboBox comboOp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelA;
    private javax.swing.JLabel jLabelB;
    private javax.swing.JLabel jLabelC;
    private javax.swing.JLabel jLabelD;
    private javax.swing.JLabel jLabelE;
    private javax.swing.JLabel jLabelF;
    private javax.swing.JLabel jLabelX;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFielLabel;
    private javax.swing.JTextField textA;
    private javax.swing.JTextField textDescripcio;
    // End of variables declaration//GEN-END:variables

}
