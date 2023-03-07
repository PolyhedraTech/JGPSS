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
import model.blocks.Bloc;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import model.*;
import utils.Constants;
import utils.VarGlobals;

/**
 *
 * @author M.Dolores
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class PanelModelView extends JPanel {

    private static final long serialVersionUID = 1L;

    ArrayList<Bloc> arrayBlocs;
    //lista con los tï¿½tulos de los procesos
    ArrayList<String> titolsProc = new ArrayList<>();

    int numproceso, numbloc;
    Proces pro;
    //  int cont=0;
    private final Frame parent;

    /**
     * Creates new form PanelModelGpss
     *
     * @param parent
     */
    public PanelModelView(Frame parent) {
        //tamaï¿½o inicial
        initComponents();
        tractamentBotonsInicial();
        //si obrim un model pintar-lo
        if (VarGlobals.Abrir) {
            Dimension n = jPanelModel.getSize();
            int width = (int) n.getWidth();
            int height = (int) n.getHeight();
            if ((width == 0) && (height == 0)) {
                jPanelModel.setPreferredSize(new Dimension(960, 482));
                jPanelGlass.setPreferredSize(new Dimension(960, 482));
            }
            pintarModel(VarGlobals.model, false);
        }
        //posar le barres del scrollpanel
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //jScrollPane1.setvi
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        jPanelGlass.setOpaque(false);
        jPanelModel.setBackground(Color.WHITE);
        this.parent = parent;

    }

    //mï¿½tode que pinta el model al obrir-lo
    private void pintarModel(Model m, boolean tractarPanels) {
        habilitarBotons();
        //recorrer els processos
        //tractar els jPanels?
        if (tractarPanels) {
            this.jPanelModel.removeAll();
            //  this.jPanelGlass.removeAll();
            //this.jPanelModel.setVisible(true);
            this.jPanelModel.repaint();
            // this.jPanelGlass.setVisible(true);
            //this.jPanelGlass.repaint();
            this.jPanelModel.add(jPanelGlass);
        }
        int posx, posy;
        Bloc b;
        int idbloc;
        titolsProc.clear();
        if (VarGlobals.model.getProces().isEmpty()) {
            tractamentBotonsInicial();
        }

        Dimension n = jPanelModel.getSize();
        int maxy = (int) n.getHeight();
        int maxx = (int) n.getWidth();
        for (int i = 0; i < VarGlobals.model.getProces().size(); i++) {

            pro = VarGlobals.model.getProces().get(i);
            titolsProc.add(pro.getDescpro());
            //pintar el titol
            javax.swing.JLabel jLabeldibuix = new JLabel();
            jLabeldibuix.setText(pro.getDescpro());
            posx = pro.getPosx();
            posy = pro.getPosy();
            jPanelModel.add(jLabeldibuix).setBounds(posx, posy, Constants.x, 70);
            posy = posy + Constants.y;

            //pintar els blocs
            //agafar el mï¿½xim de tamny de les y
            if (maxy < pro.getFinposy()) {
                maxy = pro.getFinposy();
            }
            // if(maxx<pro.get)
            for (int j = 0; j < pro.getBlocs().size(); j++) {

                b = pro.getBlocs().get(j);
                idbloc = b.getId();
                switch (idbloc) {
                    case Constants.idGenerate:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlGenerate, b);
                        break;
                    case Constants.idAdvanced:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlAdvanced, b);
                        break;
                    case Constants.idDepart:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlDepart, b);
                        break;
                    case Constants.idEnter:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlEnter, b);
                        break;
                    case Constants.idLeave:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlLeave, b);
                        break;
                    case Constants.idQueue:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlQueue, b);
                        break;
                    case Constants.idRelease:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlRelease, b);
                        break;
                    case Constants.idSeize:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlSeize, b);
                        break;
                    case Constants.idTerminate:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlTerminate, b);
                        break;
                    case Constants.idTest:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlTest, b);
                        break;
                    case Constants.idTransfer:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlTransfer, b);
                        break;
                    case Constants.idLogic:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlLogic, b);
                        break;
                    case Constants.idGate:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlGate, b);
                        break;
                    case Constants.idSavevavg:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlSavevavg, b);
                        break;
                    case Constants.idLoop:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlLoop, b);
                        break;
                    case Constants.idSplit:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlSplit, b);
                        break;
                    case Constants.idFunavail:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlFunavail, b);
                        break;
                    case Constants.idAssign:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlAssign, b);
                        break;
                    case Constants.idFavail:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlFavail, b);
                        break;
                    case Constants.idSavail:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlSavail, b);
                        break;
                    case Constants.idSunavail:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlSunavail, b);
                        break;
                    case Constants.idAssemble:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlAssemble, b);
                        break;
                    case Constants.idGather:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlGather, b);
                        break;
                    case Constants.idMatch:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlMatch, b);
                        break;
                    case Constants.idPriority:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlPriority, b);
                        break;
                    case Constants.idBuffer:
                        pintarBloc(b.getLabel(), posx, posy, Constants.UrlBuffer, b);
                        break;

                }

                posy = posy + Constants.y;

            }

        }
        //cambiarTamaï¿½oJPanelsY(maxy);
        int tamanyx = Constants.x * VarGlobals.model.getProces().size();
        //cambiarTamaï¿½oJPanelsX(tamanyx);
        // cambiarTamaï¿½oJPanels(tamanyx,maxy);
        rellenarCombo();
    }

    private void tractamentBotonsInicial() {

        botoGenerate.setToolTipText(Constants.Generate);
        botoTerminate.setToolTipText(Constants.Terminate);
        botoAdvance.setToolTipText(Constants.Advanced);
        botoSeize.setToolTipText(Constants.Seize);
        botoRelease.setToolTipText(Constants.Release);
        botoEnter.setToolTipText(Constants.Enter);
        botoLeave.setToolTipText(Constants.Leave);
        botoQueue.setToolTipText(Constants.Queue);
        botoDepart.setToolTipText(Constants.Depart);

        botoGenerate.setEnabled(false);
        botoTerminate.setEnabled(false);
        botoAdvance.setEnabled(false);
        botoSeize.setEnabled(false);
        botoRelease.setEnabled(false);
        botoEnter.setEnabled(false);
        botoLeave.setEnabled(false);
        botoQueue.setEnabled(false);
        botoDepart.setEnabled(false);
        botoTest.setEnabled(false);
        botoTransfer.setEnabled(false);
        botoLogic.setEnabled(false);
        botoGate.setEnabled(false);
        botoSavevAvg.setEnabled(false);
        botoLoop.setEnabled(false);
        botoSplit.setEnabled(false);
        botoFunavail.setEnabled(false);
        botoSavail.setEnabled(false);
        botoAssemble.setEnabled(false);
        botoGather.setEnabled(false);
        botoMatch.setEnabled(false);
        botoPriority.setEnabled(false);
        botoBuffer.setEnabled(false);
        botoFavail.setEnabled(false);
        botoSunavail.setEnabled(false);
        botoAssign.setEnabled(false);

        comboModel.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupBorrarEliminar = new javax.swing.JPopupMenu();
        jMenuConsultar = new javax.swing.JMenuItem();
        jMenuModificar = new javax.swing.JMenuItem();
        jMenuBorrar = new javax.swing.JMenuItem();
        jMenuConsultarPorces = new javax.swing.JMenuItem();
        jMenuModificarProces = new javax.swing.JMenuItem();
        jMenuEliminarProces = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelModel = new javax.swing.JPanel();
        jPanelGlass = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        botoGenerate = new javax.swing.JButton();
        botoTerminate = new javax.swing.JButton();
        botoQueue = new javax.swing.JButton();
        botoDepart = new javax.swing.JButton();
        botoAdvance = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        botoSeize = new javax.swing.JButton();
        botoRelease = new javax.swing.JButton();
        botoEnter = new javax.swing.JButton();
        botoLeave = new javax.swing.JButton();
        botoGate = new javax.swing.JButton();
        botoSavevAvg = new javax.swing.JButton();
        botoFunavail = new javax.swing.JButton();
        botoSavail = new javax.swing.JButton();
        botoFavail = new javax.swing.JButton();
        botoSunavail = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        botoTest = new javax.swing.JButton();
        botoTransfer = new javax.swing.JButton();
        botoLogic = new javax.swing.JButton();
        botoLoop = new javax.swing.JButton();
        botoSplit = new javax.swing.JButton();
        botoAssemble = new javax.swing.JButton();
        botoGather = new javax.swing.JButton();
        botoMatch = new javax.swing.JButton();
        botoPriority = new javax.swing.JButton();
        botoBuffer = new javax.swing.JButton();
        botoAssign = new javax.swing.JButton();
        botoNewModel = new javax.swing.JButton();
        comboModel = new javax.swing.JComboBox();

        jPopupBorrarEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPopupBorrarEliminarMouseClicked(evt);
            }
        });
        jPopupBorrarEliminar.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupBorrarEliminarPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jMenuConsultar.setText("Block Properties");
        jMenuConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuConsultarActionPerformed(evt);
            }
        });
        jPopupBorrarEliminar.add(jMenuConsultar);

        jMenuModificar.setText("Modify Block");
        jMenuModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuModificarActionPerformed(evt);
            }
        });
        jPopupBorrarEliminar.add(jMenuModificar);

        jMenuBorrar.setText("delete Block");
        jMenuBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuBorrarActionPerformed(evt);
            }
        });
        jPopupBorrarEliminar.add(jMenuBorrar);

        jMenuConsultarPorces.setText("Proces Properties");
        jMenuConsultarPorces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuConsultarPorcesActionPerformed(evt);
            }
        });
        jPopupBorrarEliminar.add(jMenuConsultarPorces);

        jMenuModificarProces.setText("Modify Proces");
        jMenuModificarProces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuModificarProcesActionPerformed(evt);
            }
        });
        jPopupBorrarEliminar.add(jMenuModificarProces);

        jMenuEliminarProces.setText("Delete Proces");
        jMenuEliminarProces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuEliminarProcesActionPerformed(evt);
            }
        });
        jPopupBorrarEliminar.add(jMenuEliminarProces);

        setBackground(new java.awt.Color(204, 204, 255));
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                formPropertyChange(evt);
            }
        });

        jPanelModel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelModelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanelModelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanelModelMouseReleased(evt);
            }
        });

        jPanelGlass.setToolTipText("");
        jPanelGlass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanelGlassMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanelGlassMouseReleased(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelGlassLayout = new org.jdesktop.layout.GroupLayout(jPanelGlass);
        jPanelGlass.setLayout(jPanelGlassLayout);
        jPanelGlassLayout.setHorizontalGroup(
            jPanelGlassLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 500, Short.MAX_VALUE)
        );
        jPanelGlassLayout.setVerticalGroup(
            jPanelGlassLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 499, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout jPanelModelLayout = new org.jdesktop.layout.GroupLayout(jPanelModel);
        jPanelModel.setLayout(jPanelModelLayout);
        jPanelModelLayout.setHorizontalGroup(
            jPanelModelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelModelLayout.createSequentialGroup()
                .add(jPanelGlass, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(205, Short.MAX_VALUE))
        );
        jPanelModelLayout.setVerticalGroup(
            jPanelModelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelModelLayout.createSequentialGroup()
                .add(jPanelGlass, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(114, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanelModel);

        jTabbedPane1.setBackground(new java.awt.Color(204, 204, 255));

        botoGenerate.setBackground(new java.awt.Color(255, 255, 255));
        botoGenerate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/generate.GIF"))); // NOI18N
        botoGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoGenerateActionPerformed(evt);
            }
        });

        botoTerminate.setBackground(new java.awt.Color(255, 255, 255));
        botoTerminate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/terminate.GIF"))); // NOI18N
        botoTerminate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoTerminateActionPerformed(evt);
            }
        });

        botoQueue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/queue.GIF"))); // NOI18N
        botoQueue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoQueueActionPerformed(evt);
            }
        });

        botoDepart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/depart.GIF"))); // NOI18N
        botoDepart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoDepartActionPerformed(evt);
            }
        });

        botoAdvance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/advance.GIF"))); // NOI18N
        botoAdvance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoAdvanceActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(botoGenerate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(botoTerminate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(botoQueue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(botoDepart, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(botoAdvance, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botoGenerate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(botoTerminate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botoQueue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(botoDepart, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(botoAdvance, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(218, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Basics", jPanel1);

        botoSeize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/seize1.gif"))); // NOI18N
        botoSeize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoSeizeActionPerformed(evt);
            }
        });

        botoRelease.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/release.GIF"))); // NOI18N
        botoRelease.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoReleaseActionPerformed(evt);
            }
        });

        botoEnter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/enter.GIF"))); // NOI18N
        botoEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoEnterActionPerformed(evt);
            }
        });

        botoLeave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/leave.GIF"))); // NOI18N
        botoLeave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoLeaveActionPerformed(evt);
            }
        });

        botoGate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/gate.GIF"))); // NOI18N
        botoGate.setToolTipText("Gate");
        botoGate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoGateActionPerformed(evt);
            }
        });

        botoSavevAvg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Savevavg.GIF"))); // NOI18N
        botoSavevAvg.setToolTipText("Savevavg");
        botoSavevAvg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoSavevAvgActionPerformed(evt);
            }
        });

        botoFunavail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/funavail.GIF"))); // NOI18N
        botoFunavail.setToolTipText("Funavail");
        botoFunavail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoFunavailActionPerformed(evt);
            }
        });

        botoSavail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/savail.GIF"))); // NOI18N
        botoSavail.setToolTipText("Savail");
        botoSavail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoSavailActionPerformed(evt);
            }
        });

        botoFavail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/favail.gif"))); // NOI18N
        botoFavail.setToolTipText("Favail");
        botoFavail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoFavailActionPerformed(evt);
            }
        });

        botoSunavail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sunavail.gif"))); // NOI18N
        botoSunavail.setToolTipText("Sunavail");
        botoSunavail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoSunavailActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(botoSeize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(botoRelease, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(botoGate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(botoSavevAvg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(botoFavail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(botoFunavail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(botoSavail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(botoSunavail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                        .add(botoEnter, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(botoLeave, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botoSeize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(botoRelease, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(botoLeave, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(botoEnter, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botoGate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(botoSavevAvg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(botoFunavail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(botoFavail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(botoSavail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(botoSunavail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(88, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Facilities", jPanel2);

        botoTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/test.GIF"))); // NOI18N
        botoTest.setToolTipText("Test");
        botoTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoTestActionPerformed(evt);
            }
        });

        botoTransfer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/transfer2.GIF"))); // NOI18N
        botoTransfer.setToolTipText("Transfer");
        botoTransfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoTransferActionPerformed(evt);
            }
        });

        botoLogic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logic2.GIF"))); // NOI18N
        botoLogic.setToolTipText("Logic");
        botoLogic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoLogicActionPerformed(evt);
            }
        });

        botoLoop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/loop.GIF"))); // NOI18N
        botoLoop.setToolTipText("Loop");
        botoLoop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoLoopActionPerformed(evt);
            }
        });

        botoSplit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/split.GIF"))); // NOI18N
        botoSplit.setToolTipText("Split");
        botoSplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoSplitActionPerformed(evt);
            }
        });

        botoAssemble.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/assemble.GIF"))); // NOI18N
        botoAssemble.setToolTipText("Assemble");
        botoAssemble.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoAssembleActionPerformed(evt);
            }
        });

        botoGather.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/gather.GIF"))); // NOI18N
        botoGather.setToolTipText("Gather");
        botoGather.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoGatherActionPerformed(evt);
            }
        });

        botoMatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/match.GIF"))); // NOI18N
        botoMatch.setToolTipText("Match");
        botoMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoMatchActionPerformed(evt);
            }
        });

        botoPriority.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/priority.GIF"))); // NOI18N
        botoPriority.setToolTipText("Priority");
        botoPriority.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoPriorityActionPerformed(evt);
            }
        });

        botoBuffer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buffer.GIF"))); // NOI18N
        botoBuffer.setToolTipText("Buffer");
        botoBuffer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoBufferActionPerformed(evt);
            }
        });

        botoAssign.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/assign.gif"))); // NOI18N
        botoAssign.setToolTipText("Assign");
        botoAssign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoAssignActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(botoTest, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(botoTransfer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(botoLogic, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(botoLoop, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(botoSplit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(botoAssemble, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(botoGather, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(botoMatch, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(botoPriority, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(botoBuffer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(botoAssign, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botoTest, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(botoTransfer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botoLogic, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(botoLoop, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botoSplit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(botoAssemble, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botoGather, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(botoMatch, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botoPriority, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(botoBuffer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(botoAssign, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Logics", jPanel3);

        botoNewModel.setText("Create Process");
        botoNewModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoNewModelActionPerformed(evt);
            }
        });

        comboModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboModelActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(13, 13, 13)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 181, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(botoNewModel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 181, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(comboModel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 181, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 446, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(botoNewModel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(comboModel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botoSavailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoSavailActionPerformed

        descripcioBloc(Constants.UrlSavail, Constants.idSavail, Constants.Savail);
    }//GEN-LAST:event_botoSavailActionPerformed

    private void botoFunavailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoFunavailActionPerformed

        descripcioBloc(Constants.UrlFunavail, Constants.idFunavail, Constants.Funavail);
    }//GEN-LAST:event_botoFunavailActionPerformed

    private void botoSavevAvgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoSavevAvgActionPerformed

        descripcioBloc(Constants.UrlSavevavg, Constants.idSavevavg, Constants.Savevavg);
    }//GEN-LAST:event_botoSavevAvgActionPerformed

    private void botoGateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoGateActionPerformed

        descripcioBloc(Constants.UrlGate, Constants.idGate, Constants.Gate);
    }//GEN-LAST:event_botoGateActionPerformed

    private void botoBufferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoBufferActionPerformed

        descripcioBloc(Constants.UrlBuffer, Constants.idBuffer, Constants.Buffer);
    }//GEN-LAST:event_botoBufferActionPerformed

    private void botoPriorityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoPriorityActionPerformed

        descripcioBloc(Constants.UrlPriority, Constants.idPriority, Constants.Priority);
    }//GEN-LAST:event_botoPriorityActionPerformed

    private void botoMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoMatchActionPerformed

        descripcioBloc(Constants.UrlMatch, Constants.idMatch, Constants.Match);
    }//GEN-LAST:event_botoMatchActionPerformed

    private void botoGatherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoGatherActionPerformed

        descripcioBloc(Constants.UrlGather, Constants.idGather, Constants.Gather);
    }//GEN-LAST:event_botoGatherActionPerformed

    private void botoAssembleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoAssembleActionPerformed

        descripcioBloc(Constants.UrlAssemble, Constants.idAssemble, Constants.Assemble);
    }//GEN-LAST:event_botoAssembleActionPerformed

    private void botoSplitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoSplitActionPerformed

        descripcioBloc(Constants.UrlSplit, Constants.idSplit, Constants.Split);
    }//GEN-LAST:event_botoSplitActionPerformed

    private void botoLoopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoLoopActionPerformed

        descripcioBloc(Constants.UrlLoop, Constants.idLoop, Constants.Loop);
    }//GEN-LAST:event_botoLoopActionPerformed

    private void botoLogicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoLogicActionPerformed

        descripcioBloc(Constants.UrlLogic, Constants.idLogic, Constants.Logic);
    }//GEN-LAST:event_botoLogicActionPerformed

    private void botoTransferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoTransferActionPerformed

        descripcioBloc(Constants.UrlTransfer, Constants.idTransfer, Constants.Transfer);
    }//GEN-LAST:event_botoTransferActionPerformed

    private void botoTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoTestActionPerformed

        descripcioBloc(Constants.UrlTest, Constants.idTest, Constants.Test);
    }//GEN-LAST:event_botoTestActionPerformed

    private void formPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_formPropertyChange

    }//GEN-LAST:event_formPropertyChange

    private void jMenuConsultarPorcesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuConsultarPorcesActionPerformed
        NewProcessView pnm = new NewProcessView(titolsProc, Constants.ConsultarProces, VarGlobals.model.getProces().get(numproceso).getDescpro());
        pnm.setLocationRelativeTo(this);

        pnm.setVisible(true);
        pnm.dispose();
        limpiarGlobales();
    }//GEN-LAST:event_jMenuConsultarPorcesActionPerformed

    private void jMenuModificarProcesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuModificarProcesActionPerformed
        //  PantallaNewProces(Vector titols, int cas, String strTitol)
        NewProcessView pnm = new NewProcessView(titolsProc, Constants.ModificarProces, VarGlobals.model.getProces().get(numproceso).getDescpro());
        pnm.setLocationRelativeTo(this);

        pnm.setVisible(true);
        pnm.dispose();
        if (VarGlobals.continuar) {
            VarGlobals.model.getProces().get(numproceso).setDescpro(VarGlobals.Descripcio);
            pintarModel(VarGlobals.model, true);
        }
        limpiarGlobales();

    }//GEN-LAST:event_jMenuModificarProcesActionPerformed

    private void jMenuConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuConsultarActionPerformed
        Bloc b = VarGlobals.model.getProces().get(numproceso).getBlocs().get(numbloc);
        BlockDescriptionView descBloc = new BlockDescriptionView(parent, b, Constants.ConsultarBloc);
        descBloc.setLocationRelativeTo(this);

        descBloc.setVisible(true);
        descBloc.dispose();
        limpiarGlobales();
    }//GEN-LAST:event_jMenuConsultarActionPerformed

    private void jMenuEliminarProcesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuEliminarProcesActionPerformed
        Object[] options = {
            Constants.borrar,
            Constants.cancel
        };

        int sel = javax.swing.JOptionPane.showOptionDialog(
                frame,
                Constants.BorrarProces,
                Constants.caution,
                javax.swing.JOptionPane.DEFAULT_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE,
                null,
                options, options[0]
        );

        if (sel == 0) {

            //donar-li les noves posicions x als procesos segï¿½ents
            if (VarGlobals.model.getProces().size() > (numproceso + 1)) {
                for (int i = (numproceso + 1); i < VarGlobals.model.getProces().size(); i++) {
                    int xini = VarGlobals.model.getProces().get(i).getPosx();
                    VarGlobals.model.getProces().get(i).setPosx(xini - Constants.x);
                    VarGlobals.model.getProces().get(i).setFinposx(xini - Constants.x);
                }
            }
            VarGlobals.model.getProces().remove(numproceso);
            pintarModel(VarGlobals.model, true);
        }
    }//GEN-LAST:event_jMenuEliminarProcesActionPerformed

    private void jMenuBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBorrarActionPerformed
        Object[] options = {
            Constants.borrar,
            Constants.cancel
        };

        int sel = javax.swing.JOptionPane.showOptionDialog(
                frame,
                Constants.BorrarBloc,
                Constants.caution,
                javax.swing.JOptionPane.DEFAULT_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE,
                null,
                options, options[0]
        );

        if (sel == 0) {
            VarGlobals.model.getProces().get(numproceso).getBlocs().remove(numbloc);
            int finposy = VarGlobals.model.getProces().get(numproceso).getFinposy();
            VarGlobals.model.getProces().get(numproceso).setFinposy(finposy - Constants.y);
            this.jPanelModel.removeAll();
            this.jPanelGlass.removeAll();
            pintarModel(VarGlobals.model, true);
        }


    }//GEN-LAST:event_jMenuBorrarActionPerformed

    private void jMenuModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuModificarActionPerformed
        Bloc b = VarGlobals.model.getProces().get(numproceso).getBlocs().get(numbloc);
        BlockDescriptionView descBloc = new BlockDescriptionView(parent, b, Constants.ModificarBloc);
        descBloc.setLocationRelativeTo(this);

        descBloc.setVisible(true);
        descBloc.dispose();

        if (VarGlobals.continuar) {
            VarGlobals.model.getProces().get(numproceso).getBlocs().set(numbloc, VarGlobals.bloc);
            pintarModel(VarGlobals.model, true);
        }
        limpiarGlobales();
    }//GEN-LAST:event_jMenuModificarActionPerformed

    private void jPanelGlassMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelGlassMousePressed

        if (evt.isPopupTrigger()) {
            if (existsbloc(evt)) {
                jPopupBorrarEliminar.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }//GEN-LAST:event_jPanelGlassMousePressed

    private void jPanelGlassMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelGlassMouseReleased

        if (evt.isPopupTrigger()) {
            if (existsbloc(evt)) {
                jPopupBorrarEliminar.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }//GEN-LAST:event_jPanelGlassMouseReleased
    private boolean existsbloc(java.awt.event.MouseEvent evt) {
        numproceso = 0;
        numbloc = 0;
        boolean b = false;
        Proces prox;
        int contx = 0;
        int conty;
        for (int i = 0; ((i < VarGlobals.model.getProces().size()) && !b); i++) {
            if ((contx < evt.getX()) && (evt.getX() <= (contx + Constants.x))) {
                numproceso = i;
                //proces que correspon a la pos x del event
                prox = VarGlobals.model.getProces().get(numproceso);
                conty = Constants.y;
                if (prox.getBlocs().isEmpty()) {
                    if ((0 < evt.getY()) && (evt.getY() <= (conty))) {
                        jMenuBorrar.setEnabled(false);
                        jMenuConsultar.setEnabled(false);
                        jMenuModificar.setEnabled(false);
                        b = true;
                    }
                } else {
                    for (int j = 0; ((j < prox.getBlocs().size()) && !b); j++) {
                        if ((conty < evt.getY()) && (evt.getY() <= (conty + Constants.y))) {
                            numbloc = j;
                            jMenuBorrar.setEnabled(true);
                            jMenuConsultar.setEnabled(true);
                            jMenuModificar.setEnabled(true);
                            b = true;
                        } else if ((0 < evt.getY()) && (evt.getY() <= (conty))) {
                            jMenuBorrar.setEnabled(false);
                            jMenuConsultar.setEnabled(false);
                            jMenuModificar.setEnabled(false);
                            b = true;
                        } else {
                            conty = conty + Constants.y;
                        }
                    }
                }
            } else {
                contx = contx + Constants.x;
            }
        }

        return b;
    }
    private void jPanelModelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelModelMouseReleased

    }//GEN-LAST:event_jPanelModelMouseReleased

    private void jPanelModelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelModelMousePressed

    }//GEN-LAST:event_jPanelModelMousePressed

    private void jPanelModelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelModelMouseClicked

    }//GEN-LAST:event_jPanelModelMouseClicked

    private void jPopupBorrarEliminarPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupBorrarEliminarPopupMenuWillBecomeInvisible

    }//GEN-LAST:event_jPopupBorrarEliminarPopupMenuWillBecomeInvisible

    private void jPopupBorrarEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPopupBorrarEliminarMouseClicked

    }//GEN-LAST:event_jPopupBorrarEliminarMouseClicked

    private void botoEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoEnterActionPerformed
        descripcioBloc(Constants.UrlEnter, Constants.idEnter, Constants.Enter);
    }//GEN-LAST:event_botoEnterActionPerformed

    private void botoLeaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoLeaveActionPerformed
        descripcioBloc(Constants.UrlLeave, Constants.idLeave, Constants.Leave);
    }//GEN-LAST:event_botoLeaveActionPerformed

    private void botoReleaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoReleaseActionPerformed
        descripcioBloc(Constants.UrlRelease, Constants.idRelease, Constants.Release);
    }//GEN-LAST:event_botoReleaseActionPerformed

    private void botoSeizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoSeizeActionPerformed
        descripcioBloc(Constants.UrlSeize, Constants.idSeize, Constants.Seize);
    }//GEN-LAST:event_botoSeizeActionPerformed

    private void botoDepartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoDepartActionPerformed
        descripcioBloc(Constants.UrlDepart, Constants.idDepart, Constants.Depart);
    }//GEN-LAST:event_botoDepartActionPerformed

    private void botoQueueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoQueueActionPerformed
        descripcioBloc(Constants.UrlQueue, Constants.idQueue, Constants.Queue);
    }//GEN-LAST:event_botoQueueActionPerformed

    private void botoAdvanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoAdvanceActionPerformed
        descripcioBloc(Constants.UrlAdvanced, Constants.idAdvanced, Constants.Advanced);
    }//GEN-LAST:event_botoAdvanceActionPerformed

    private void comboModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboModelActionPerformed
        //valor del combo escogido
        String valor = (String) comboModel.getSelectedItem();
        //buscar las posiciones x e y

        boolean trobat = false;
        Proces p;
        for (int i = 0; i < VarGlobals.model.getProces().size() && !trobat; i++) {
            p = VarGlobals.model.getProces().get(i);
            if (p.getDescpro().equals(valor)) {
                //  x=p.getFinposx();
                // y=p.getFinposy();
                pro = p;
                arrayBlocs = p.getBlocs();
                trobat = true;
            }

        }

    }//GEN-LAST:event_comboModelActionPerformed

    private void botoTerminateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoTerminateActionPerformed

        descripcioBloc(Constants.UrlTerminate, Constants.idTerminate, Constants.Terminate);
    }//GEN-LAST:event_botoTerminateActionPerformed

    private void botoGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoGenerateActionPerformed

        descripcioBloc(Constants.UrlGenerate, Constants.idGenerate, Constants.Generate);

    }//GEN-LAST:event_botoGenerateActionPerformed

    private void botoNewModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoNewModelActionPerformed
                
        NewProcessView pnm = new NewProcessView(titolsProc);
        pnm.setLocationRelativeTo(this);

        pnm.setVisible(true);
        pnm.dispose();
        int x;
        int y;
        //creem un nou proces amb nomï¿½s la descripció
        if (VarGlobals.continuar) {

            if (VarGlobals.model.getProces().isEmpty()) {
                x = 0;
                y = 0;
            } else {
                //si hay procesos cogemos la pos del último
                int cuantos = VarGlobals.model.getProces().size();
                Proces p = VarGlobals.model.getProces().get(cuantos - 1);
                x = p.getPosx() + Constants.x;
                y = 0;

            }
            //creamos nuevo proceso
            pro = new Proces(VarGlobals.Descripcio, VarGlobals.model);
            //dibujar();
            //poner la desccripción del proceso
            javax.swing.JLabel jLabeldibuix = new JLabel();
            jLabeldibuix.setText(VarGlobals.Descripcio);
            jPanelModel.add(jLabeldibuix).setBounds(x, y, Constants.x, 70);

            pro.setPosx(x);
            pro.setPosy(y);
            pro.setFinposx(x);
            pro.setFinposy(y + Constants.y);
            VarGlobals.model.getProces().add(pro);

            //añadir el proceso en el combobox
            titolsProc.add(pro.getDescpro());
            rellenarCombo();
            y += Constants.y;
            habilitarBotons();

            arrayBlocs = new ArrayList<>();
            //limpiar la variable Global
            VarGlobals.Descripcio = "";
            //tamaño del panel
            cambiarTamanoJPanelsX(x + Constants.x);
            VarGlobals.esModificat = true;
        }

    }//GEN-LAST:event_botoNewModelActionPerformed

    private void botoFavailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoFavailActionPerformed
        descripcioBloc(Constants.UrlFavail, Constants.idFavail, Constants.Favail);
}//GEN-LAST:event_botoFavailActionPerformed

    private void botoSunavailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoSunavailActionPerformed
        descripcioBloc(Constants.UrlSunavail, Constants.idSunavail, Constants.Sunavail);
}//GEN-LAST:event_botoSunavailActionPerformed

    private void botoAssignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoAssignActionPerformed
        descripcioBloc(Constants.UrlAssign, Constants.idAssign, Constants.Assign);
}//GEN-LAST:event_botoAssignActionPerformed

    private void rellenarCombo() {

        comboModel.setModel(new javax.swing.DefaultComboBoxModel(titolsProc.toArray()));
        comboModel.setEnabled(true);
        if (titolsProc.size() > 0) {
            comboModel.setSelectedItem(pro.getDescpro());
        }
    }

    private void descripcioBloc(String url, int tipusBloc, String nomBloc) {
        // VarGlobals
        Model model = VarGlobals.model;
        Proces proces = VarGlobals.model.getProces().get(numproceso);
        BlockDescriptionView descBloc = new BlockDescriptionView(parent, tipusBloc, url, model, proces);
        descBloc.setLocationRelativeTo(this);

        descBloc.setVisible(true);
        descBloc.dispose();
        int x = pro.getFinposx();
        int y = pro.getFinposy();
        if (VarGlobals.continuar) {
            pintarBloc(VarGlobals.bloc.getLabel(), x, y, url, VarGlobals.bloc);

            y += 60;
            arrayBlocs.add(VarGlobals.bloc);
            pro.setBlocs(arrayBlocs);
            pro.setFinposy(y);
            cambiarTamanoJPanelsY(y);
            VarGlobals.esModificat = true;
        }
        limpiarGlobales();

    }

    private void cambiarTamanoJPanelsY(int y) {

        Dimension n = jPanelModel.getSize();
        int width = (int) n.getWidth();
        int height = (int) n.getHeight();

        if (n.getHeight() <= y) {
            jPanelModel.setPreferredSize(new Dimension(width, y));
            jPanelGlass.setPreferredSize(new Dimension(width, y));

            // jPanelModel.setSize(width,x);
            jPanelModel.revalidate();
        }
        n = jPanelGlass.getSize();
        width = (int) n.getWidth();
        height = (int) n.getHeight();
        if (n.getHeight() <= y) {
            jPanelGlass.setPreferredSize(new Dimension(width, y));
            jPanelModel.revalidate();
        }
    }

    private void cambiarTamanoJPanelsX(int x) {

        Dimension n = jPanelModel.getSize();
        int width = (int) n.getWidth();
        int height = (int) n.getHeight();

        if (n.getWidth() <= x) {
            jPanelModel.setPreferredSize(new Dimension(x, height));
            jPanelGlass.setPreferredSize(new Dimension(x, height));

            // jPanelModel.setSize(width,x);
            jPanelModel.revalidate();
        }
        n = jPanelGlass.getSize();
        width = (int) n.getWidth();
        height = (int) n.getHeight();
        if (n.getWidth() <= x) {
            jPanelGlass.setPreferredSize(new Dimension(x, height));
            jPanelModel.revalidate();
        }
    }

    private void cambiarTamanoJPanels(int x, int y) {

        Dimension n = jPanelModel.getSize();
        int width = (int) n.getWidth();
        int height = (int) n.getHeight();
        if (width == 0) {
            width = x;
        }
        if (height == 0) {
            height = y;
        }

        if ((width <= x) && (height > y)) {
            //    cambiarTamaï¿½oJPanelsX(x);
        } else if ((height <= y) && (width > x)) {
            // cambiarTamaï¿½oJPanelsY(y);
        } else if ((width <= x) && (height <= y)) {

        }
    }

    private void pintarBloc(String labelBloc, int x, int y, String url, Bloc b) {
        javax.swing.JLabel jLabelbloc = new JLabel();
        jLabelbloc.setText(labelBloc);
        jPanelModel.add(jLabelbloc).setBounds(x, y, 60, 60);
        javax.swing.JLabel jLabeldibuix;

        //jlabel amb la representaciï¿½ grï¿½fica del bloc
        jLabeldibuix = new JLabel();
        jLabeldibuix.setIcon(new javax.swing.ImageIcon(getClass().getResource(url)));

        //per a distinguir el bloc li posem el nom del proces on estï¿½ el bloc i la pos y on es troba
        jPanelModel.add(jLabeldibuix).setBounds(x + 60, y, 65, 70);

    }

    private void habilitarBotons() {
        botoGenerate.setEnabled(true);
        botoTerminate.setEnabled(true);
        botoAdvance.setEnabled(true);
        botoSeize.setEnabled(true);
        botoRelease.setEnabled(true);
        botoEnter.setEnabled(true);
        botoLeave.setEnabled(true);
        botoQueue.setEnabled(true);
        botoDepart.setEnabled(true);
        botoTest.setEnabled(true);
        botoTransfer.setEnabled(true);
        botoLogic.setEnabled(true);
        botoGate.setEnabled(true);
        botoSavevAvg.setEnabled(true);
        botoLoop.setEnabled(true);
        botoSplit.setEnabled(true);
        botoFunavail.setEnabled(true);
        botoSavail.setEnabled(true);
        botoFavail.setEnabled(true);
        botoSunavail.setEnabled(true);
        botoAssemble.setEnabled(true);
        botoGather.setEnabled(true);
        botoMatch.setEnabled(true);
        botoPriority.setEnabled(true);
        botoBuffer.setEnabled(true);
        botoAssign.setEnabled(true);
    }

    private void limpiarGlobales() {
        VarGlobals.bloc = null;
        VarGlobals.Descripcio = "";
        VarGlobals.esModificat = true;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botoAdvance;
    private javax.swing.JButton botoAssemble;
    private javax.swing.JButton botoAssign;
    private javax.swing.JButton botoBuffer;
    private javax.swing.JButton botoDepart;
    private javax.swing.JButton botoEnter;
    private javax.swing.JButton botoFavail;
    private javax.swing.JButton botoFunavail;
    private javax.swing.JButton botoGate;
    private javax.swing.JButton botoGather;
    private javax.swing.JButton botoGenerate;
    private javax.swing.JButton botoLeave;
    private javax.swing.JButton botoLogic;
    private javax.swing.JButton botoLoop;
    private javax.swing.JButton botoMatch;
    private javax.swing.JButton botoNewModel;
    private javax.swing.JButton botoPriority;
    private javax.swing.JButton botoQueue;
    private javax.swing.JButton botoRelease;
    private javax.swing.JButton botoSavail;
    private javax.swing.JButton botoSavevAvg;
    private javax.swing.JButton botoSeize;
    private javax.swing.JButton botoSplit;
    private javax.swing.JButton botoSunavail;
    private javax.swing.JButton botoTerminate;
    private javax.swing.JButton botoTest;
    private javax.swing.JButton botoTransfer;
    private javax.swing.JComboBox comboModel;
    private javax.swing.JMenuItem jMenuBorrar;
    private javax.swing.JMenuItem jMenuConsultar;
    private javax.swing.JMenuItem jMenuConsultarPorces;
    private javax.swing.JMenuItem jMenuEliminarProces;
    private javax.swing.JMenuItem jMenuModificar;
    private javax.swing.JMenuItem jMenuModificarProces;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelGlass;
    private javax.swing.JPanel jPanelModel;
    private javax.swing.JPopupMenu jPopupBorrarEliminar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
    private JFrame frame;

}
