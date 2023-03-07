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

/*
 * PantallaRun.java
 *
 * Created on 21/04/2009, 21:21:30
 */
package views;

import java.awt.Frame;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Model;
import model.reports.HTMLReport;
import model.reports.Report;
import utils.Constants;
import utils.VarGlobals;

/**
 *
 * @author Pau
 */
public class RunView extends javax.swing.JDialog {

    static final long serialVersionUID = 1L;

    private Model model;
    private JFrame frame;

    /**
     * Creates new form PantallaRun
     *
     * @param model
     * @param parent
     * @param modal
     */
    public RunView(Model model, Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.model = model;
        updateValues();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        AbsoluteClockValue = new javax.swing.JTextField();
        RelativeClockValue = new javax.swing.JTextField();
        jButtonStep = new javax.swing.JButton();
        jButtonGo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        reportValue = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        TCvalue = new javax.swing.JTextField();
        resetButton = new javax.swing.JButton();
        SaveButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Absolute clock");

        jLabel2.setText("Relative clock");

        AbsoluteClockValue.setEnabled(false);
        AbsoluteClockValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbsoluteClockValueActionPerformed(evt);
            }
        });

        RelativeClockValue.setEnabled(false);

        jButtonStep.setText("Step");
        jButtonStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStepActionPerformed(evt);
            }
        });

        jButtonGo.setText("Go");
        jButtonGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGoActionPerformed(evt);
            }
        });

        reportValue.setContentType("text/html"); // NOI18N
        reportValue.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        reportValue.setEnabled(false);
        jScrollPane2.setViewportView(reportValue);

        jLabel3.setText("TC");

        TCvalue.setEnabled(false);

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        SaveButton.setText("Save");
        SaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(RelativeClockValue, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                            .addComponent(AbsoluteClockValue, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                            .addComponent(TCvalue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 323, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonGo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonStep, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(resetButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(SaveButton))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(AbsoluteClockValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(RelativeClockValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TCvalue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonStep)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonGo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resetButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SaveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStepActionPerformed

        if (model.getTC() == 0) {
            return;
        }
        try {
            model.executeStep();
            updateValues();

            HTMLReport report = new HTMLReport();
            report.createReport(model, "");
            reportValue.setText(report.getHTMLreport());
        } catch (Exception ex) {
            mostrarDialogo("Ok", "Cancel", ex.getMessage(), "Runtime Error");
        }
    }//GEN-LAST:event_jButtonStepActionPerformed

    private void jButtonGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGoActionPerformed

        if (model.getTC() == 0) {
            return;
        }
        try {
            model.executeAll();
            updateValues();
            HTMLReport report = new HTMLReport();
            report.createReport(model, "");
            reportValue.setText(report.getHTMLreport());
        } catch (Exception ex) {
            mostrarDialogo("Ok", "Cancel", ex.getMessage(), "Runtime Error");
        }
    }//GEN-LAST:event_jButtonGoActionPerformed

    private void AbsoluteClockValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbsoluteClockValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AbsoluteClockValueActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed

        model.clean();
        model.setTC(VarGlobals.TC);
        try {
            model.InitializeGenerateBocs();
        } catch (Exception ex) {

        }
        updateValues();
        reportValue.setText("");
    }//GEN-LAST:event_resetButtonActionPerformed

    private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveButtonActionPerformed

        ReportType reportType = new ReportType(null, true);
        reportType.setLocationRelativeTo(this);
        reportType.setVisible(true);
        Report report = VarGlobals.selectedReport;

        if (VarGlobals.continuar) {

            try {
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("", report.getType());
                fc.setFileFilter(filter);
                fc.setDialogType(JFileChooser.SAVE_DIALOG);
                fc.showSaveDialog(this);
                File Guardar = fc.getSelectedFile();

                if (Guardar != null) {
                    String path = fc.getSelectedFile().getAbsolutePath();
                    report.createReport(model, path);
                    model.clean();
                }
            } catch (Exception e) {
                generarPantallaError(Constants.errortxt);
            }
        }

    }//GEN-LAST:event_SaveButtonActionPerformed

    private int mostrarDialogo(String opcion1, String opcion2, String mensaje, String titulo) {

        Object[] options = {
            opcion1,
            opcion2
        };

        return javax.swing.JOptionPane.showOptionDialog(
                frame,
                mensaje,
                titulo,
                javax.swing.JOptionPane.DEFAULT_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE,
                null,
                options, options[0]
        );
    }

    private void generarPantallaError(String mensage) {
        ErrorView perror = new ErrorView(mensage);
        perror.setLocationRelativeTo(this);
        perror.setVisible(true);
        perror.dispose();
    }

    private void updateValues() {
        RelativeClockValue.setText(String.valueOf(model.getRelativeClock()));
        AbsoluteClockValue.setText(String.valueOf(model.getAbsoluteClock()));
        TCvalue.setText(String.format("%d", model.getTC()));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AbsoluteClockValue;
    private javax.swing.JTextField RelativeClockValue;
    private javax.swing.JButton SaveButton;
    private javax.swing.JTextField TCvalue;
    private javax.swing.JButton jButtonGo;
    private javax.swing.JButton jButtonStep;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane reportValue;
    private javax.swing.JButton resetButton;
    // End of variables declaration//GEN-END:variables

}