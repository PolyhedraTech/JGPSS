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

import exceptions.MalformedFunctionDistributionException;
import java.awt.Frame;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.entities.Function;
import utils.Constants;
import utils.VarGlobals;

/**
 *
 * @author Ezequiel Andujar Montes
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class FunctionView extends javax.swing.JDialog {

    private final static long serialVersionUID = 1L;

    Function functionAux = null;
    int posFunction = -1;

    /**
     * Creates new form PantallaStorages
     *
     * @param parent
     * @param verOK
     */
    public FunctionView(Frame parent, boolean verOK) {

        super(parent, Constants.tituloFunction, true);

        initComponents();
        initListeners();
        populateCombo();

        if (!verOK) {
            botoOK.setEnabled(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        ALabel = new javax.swing.JLabel();
        BLabel = new javax.swing.JLabel();
        BValue = new javax.swing.JTextField();
        botoOK = new javax.swing.JButton();
        botoSave = new javax.swing.JButton();
        botoCanel = new javax.swing.JButton();
        AValue = new javax.swing.JTextField();
        DistributionLabel = new javax.swing.JLabel();
        DistributionValue = new javax.swing.JTextField();
        NameLabel = new javax.swing.JLabel();
        comboFunctions = new javax.swing.JComboBox<>();

        jTextField1.setText("jTextField1");

        ALabel.setText("A");

        BLabel.setText("B");

        BValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BValueActionPerformed(evt);
            }
        });

        botoOK.setText("Ok");
        botoOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoOKActionPerformed(evt);
            }
        });

        botoSave.setText("Save");
        botoSave.setEnabled(false);
        botoSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoSaveActionPerformed(evt);
            }
        });

        botoCanel.setText("Cancel");
        botoCanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoCanelActionPerformed(evt);
            }
        });

        AValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AValueActionPerformed(evt);
            }
        });

        DistributionLabel.setText("Distribution");

        NameLabel.setText("Name");

        comboFunctions.setEditable(true);
        comboFunctions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFunctionsActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(41, 41, 41)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(botoCanel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 140, Short.MAX_VALUE)
                        .add(botoOK)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(botoSave))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(ALabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(BLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(DistributionLabel)
                            .add(NameLabel))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(BValue, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .add(AValue)
                            .add(DistributionValue)
                            .add(comboFunctions, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .add(61, 61, 61))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(38, 38, 38)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(NameLabel)
                    .add(comboFunctions, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(ALabel)
                    .add(AValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(BValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(BLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(DistributionValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(DistributionLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 27, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botoOK)
                    .add(botoCanel)
                    .add(botoSave))
                .add(23, 23, 23))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botoCanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoCanelActionPerformed
        VarGlobals.continuar = false;
        setVisible(false);
        dispose();
    }//GEN-LAST:event_botoCanelActionPerformed

    private void botoSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoSaveActionPerformed

        if (!isValidInput()) {
            generarPantallaError(Constants.errorDades);
            return;
        }

        try {

            String functionName = comboFunctions.getSelectedItem().toString();
            String A = AValue.getText();
            String B = BValue.getText();
            String distribution = DistributionValue.getText();

            if (!existsFunction(functionName)) {

                VarGlobals.model.getFunctions().add(new Function(functionName, A, B, distribution));

                comboFunctions.addItem(functionName);

            } else {

                functionAux.setA(A);
                functionAux.setB(B);
                functionAux.setDistribution(distribution);
            }
            botoOK.setEnabled(true);
            botoOK.setVisible(true);
        } catch (NumberFormatException | MalformedFunctionDistributionException e) {
            generarPantallaError(e.getMessage());
        }
        VarGlobals.esModificat = true;
    }//GEN-LAST:event_botoSaveActionPerformed

    private boolean isValidInput() {

        String name = comboFunctions.getSelectedItem().toString();
        String A = AValue.getText();
        String B = BValue.getText();
        String distribution = DistributionLabel.getText();
        return !A.isEmpty() && !B.isEmpty() && !distribution.isEmpty() && !name.isEmpty();
    }

    private void generarPantallaError(String mensage) {
        ErrorView perror = new ErrorView(mensage);
        perror.setLocationRelativeTo(this);
        perror.setVisible(true);
        perror.dispose();
    }
    private void botoOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoOKActionPerformed

        VarGlobals.continuar = true;
        String functionSelected = comboFunctions.getSelectedItem().toString();
        VarGlobals.nameFunctionSelected = functionSelected;
        setVisible(false);
        dispose();
    }//GEN-LAST:event_botoOKActionPerformed

    private void BValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BValueActionPerformed

    private void AValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AValueActionPerformed

    private void comboFunctionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFunctionsActionPerformed

        initParameters();
    }//GEN-LAST:event_comboFunctionsActionPerformed

    @SuppressWarnings("unchecked")
    private void populateCombo() {
        if (VarGlobals.model.getFunctions().size() > 0) {
            comboFunctions.setModel(new DefaultComboBoxModel(getFunctionNames(VarGlobals.model.getFunctions())));
            initParameters();
            botoSave.setEnabled(true);
        }
    }

    private void initParameters() {

        if (VarGlobals.model.getFunctions().isEmpty()) {
            return;
        }

        String name = comboFunctions.getSelectedItem().toString();

        Function func = VarGlobals.model.getFunctions().stream()//
                .filter(f -> f.getName().equals(name))//
                .findFirst()//
                .orElse(null);

        if (func != null) {
            BValue.setText(func.getB());
            AValue.setText(func.getA());
            DistributionValue.setText(func.getDistribution());
        }
    }

    private Object[] getFunctionNames(ArrayList<Function> functions) {

        return functions.stream()//
                .map(s -> s.getName())//
                .collect(Collectors.toList())//
                .toArray();
    }

    private boolean existsFunction(String name) {
        boolean b = false;
        Function f;
        for (int i = 0; ((i < VarGlobals.model.getFunctions().size()) && !b); i++) {
            f = VarGlobals.model.getFunctions().get(i);
            if (f.getName().equals(name)) {
                functionAux = VarGlobals.model.getFunctions().get(i);
                posFunction = i;
                b = true;
            }
        }
        return b;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ALabel;
    private javax.swing.JTextField AValue;
    private javax.swing.JLabel BLabel;
    private javax.swing.JTextField BValue;
    private javax.swing.JLabel DistributionLabel;
    private javax.swing.JTextField DistributionValue;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JButton botoCanel;
    private javax.swing.JButton botoOK;
    private javax.swing.JButton botoSave;
    private javax.swing.JComboBox<String> comboFunctions;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    private void initListeners() {

        BValue.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkInputValues();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkInputValues();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkInputValues();
            }
        });
    }

    private void checkInputValues() {
        botoSave.setEnabled(isValidInput());
    }
}
