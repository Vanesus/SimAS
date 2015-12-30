//SimAS  /  Simulador
//Panel Nueva Simulacion Ascendente paso 3

package es.uco.simas.simulador;

import es.uco.simas.editor.ColCanLALR;
import es.uco.simas.editor.ColCanLR0;
import es.uco.simas.editor.ColCanLR1;
import javax.swing.JOptionPane;

/**
 * @author vanesa
 */
public class PanelNuevaSimAscPaso3 extends javax.swing.JPanel {

    private VentanaSimuladorAsc ventanaPadre;
    
    public PanelNuevaSimAscPaso3(VentanaSimuladorAsc ventanaPadre) {
        initComponents();
        this.ventanaPadre =ventanaPadre;
        if(this.ventanaPadre.getMetodo() == 0){            
            this.jLabel1.setText("Colección Canónica Elementos LR(0)");
            if(this.ventanaPadre.getGramatica().getColeccionLR0()==null){                
                ColCanLR0 col = new ColCanLR0(this.ventanaPadre.getGramatica());                
                col.construir();                
                this.ventanaPadre.getGramatica().setColeccionLR0(col);
                this.ventanaPadre.setColeccion(col);            
            }
                this.jTextArea1.setText(this.ventanaPadre.getGramatica().getColeccionLR0().getColeccion());
        }
        if(this.ventanaPadre.getMetodo() == 1){
            this.jLabel1.setText("Colección Canónica LR(1)-elementos");
            if(this.ventanaPadre.getGramatica().getColeccionLR1()==null){
                ColCanLR1 col = new ColCanLR1(this.ventanaPadre.getGramatica());
                col.construir();
                this.ventanaPadre.getGramatica().setColeccionLR1(col);
                this.ventanaPadre.setColeccion(col);            
            }
                this.jTextArea1.setText(this.ventanaPadre.getGramatica().getColeccionLR1().getColeccion());
       
        }
        if(this.ventanaPadre.getMetodo() == 2){
            this.jLabel1.setText("Colección Canónica Elementos LALR(1)");
            ColCanLR1 col = new ColCanLR1();
            if(this.ventanaPadre.getGramatica().getColeccionLALR()==null){
                if(this.ventanaPadre.getGramatica().getColeccionLR1() == null){
                    col = new ColCanLR1(this.ventanaPadre.getGramatica());
                    col.construir();
                }else{
                    col = this.ventanaPadre.getGramatica().getColeccionLR1();                    
                }
                
                ColCanLALR colLALR = new ColCanLALR(this.ventanaPadre.getGramatica(), col);
                colLALR.construir();
                
                this.ventanaPadre.getGramatica().setColeccionLALR(colLALR);
                this.ventanaPadre.setColeccion(colLALR);            
            }
                this.jTextArea1.setText(this.ventanaPadre.getGramatica().getColeccionLALR().getColeccion());       
        }        
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setBackground(new java.awt.Color(233, 244, 244));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Colección Canónica Elementos LR(0)");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/uco/simas/resources/ultimo.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/uco/simas/resources/siguiente.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/uco/simas/resources/anterior.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/uco/simas/resources/primero.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Cancelar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(201, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(177, 177, 177))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton5)
                        .addComponent(jButton4)
                        .addComponent(jButton3)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(2, 2, 2)))
                .addGap(19, 19, 19))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int conf = JOptionPane.showConfirmDialog(null, "¿Desea salir del asistente de la simulación de la gramática?", "Salir",JOptionPane.YES_NO_OPTION);

        if(conf==0)
          this.ventanaPadre.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.ventanaPadre.cambiarPaso(2);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.ventanaPadre.cambiarPaso(1);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.ventanaPadre.cambiarPaso(4);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                        
        PanelNuevaSimAscPaso4 paso4 = new PanelNuevaSimAscPaso4(this.ventanaPadre);
        
        this.ventanaPadre.cambiarPaso(5);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
