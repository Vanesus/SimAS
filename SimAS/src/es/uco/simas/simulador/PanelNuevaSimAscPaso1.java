//SimAS  /  Simulador
//Panel Nueva Simulacion Ascendente paso 1

package es.uco.simas.simulador;

import javax.swing.JOptionPane;

/**
 * @author vanesa
 */
public class PanelNuevaSimAscPaso1 extends javax.swing.JPanel {

    private VentanaSimuladorAsc ventanaPadre;
    int metodo = -1;
            
    public PanelNuevaSimAscPaso1(VentanaSimuladorAsc ventanaPadre) {
        initComponents();
        this.ventanaPadre = ventanaPadre;                
    
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
        jButtonCancelar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();

        setBackground(new java.awt.Color(233, 244, 244));
        setToolTipText("Simulación Ascendente. Paso 1.");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Elige el Método de simulación Ascendente:");

        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/uco/simas/resources/ultimo.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/uco/simas/resources/siguiente.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/uco/simas/resources/anterior.png"))); // NOI18N
        jButton4.setEnabled(false);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/uco/simas/resources/primero.png"))); // NOI18N
        jButton5.setEnabled(false);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Método SLR", "Método LR-Canónico", "Método LALR" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(29, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jButtonCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel1)
                .addGap(53, 53, 53)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonCancelar)
                        .addComponent(jButton3)
                        .addComponent(jButton4)
                        .addComponent(jButton5)))
                .addGap(24, 24, 24))
        );

        getAccessibleContext().setAccessibleParent(jButtonCancelar);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        int conf = JOptionPane.showConfirmDialog(null, "¿Desea salir del asistente de la simulación de la gramática?", "Salir",JOptionPane.YES_NO_OPTION);

        if(conf==0)
          this.ventanaPadre.dispose();
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.metodo = this.jComboBox1.getSelectedIndex();
        this.ventanaPadre.setMetodo(this.metodo);        
        this.ventanaPadre.cambiarPaso(2);       
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                
        this.metodo = this.jComboBox1.getSelectedIndex();          
        this.ventanaPadre.setMetodo(this.metodo);   
        
        this.ventanaPadre.gramatica.generarConjPrim();
        this.ventanaPadre.gramatica.generarConjSig();
        this.ventanaPadre.paso2.construirConjuntos(this.ventanaPadre.gramatica);
        
        PanelNuevaSimAscPaso3 paso3 = new PanelNuevaSimAscPaso3(this.ventanaPadre);
        
        PanelNuevaSimAscPaso4 paso4 = new PanelNuevaSimAscPaso4(this.ventanaPadre);
        
        this.ventanaPadre.cambiarPaso(5);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}