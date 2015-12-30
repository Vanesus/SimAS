/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uco.simas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 *
 * @author vanesa
 */
public class PanelFondo extends javax.swing.JPanel {

    /**
     * Creates new form PanelFondo
     */
    public PanelFondo() {
        initComponents();
        this.setSize(706,634);
        
    }

    @Override
    public void paintComponent(Graphics g){
        Dimension tam = getSize();
        ImageIcon imagenFondo;
        imagenFondo = new ImageIcon(new ImageIcon(getClass().getResource("/es/uco/simas/resources/logo2.png")).getImage());
        setOpaque(false);
        g.drawImage(imagenFondo.getImage(), 0, 0, 350 ,250,null);
        super.paintComponents(g);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
