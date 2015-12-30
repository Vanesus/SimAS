//SimAS  /  Simulador
//Ventana simulador Ascendente

package es.uco.simas.simulador;

import es.uco.simas.util.gramatica.Gramatica;
import es.uco.simas.SimAS;
import es.uco.simas.editor.ColCanLR0;
import es.uco.simas.editor.ColCanLR1;
import es.uco.simas.editor.ColCanLALR;

/**
 * @author vanesa
 */
public class VentanaSimuladorAsc extends javax.swing.JFrame {

   private PanelNuevaSimAscPaso1 paso1;
   PanelNuevaSimAscPaso2 paso2;
   private PanelNuevaSimAscPaso3 paso3;
   private PanelNuevaSimAscPaso4 paso4;
   private PanelNuevaSimAscPaso5 paso5;
   private PanelNuevaSimAscPaso6 paso6;
   
   public  Simulador simulacion ;
   public Gramatica gramatica;
   public int metodo = -1;
   
    public VentanaSimuladorAsc(Gramatica gramatica) {
        initComponents();
        this.gramatica = gramatica;
        this.setResizable(false);
        PanelNuevaSimAscPaso1 paso1 = new PanelNuevaSimAscPaso1(this);
        this.paso1 = paso1;
        
        PanelNuevaSimAscPaso2 paso2 = new PanelNuevaSimAscPaso2(this);
        this.paso2 = paso2;                           
        
        this.getContentPane().removeAll();
        this.setContentPane(this.paso1);
        this.pack();
        this.validate();
        this.setTitle("Simulador Ascendente. Paso 1 de 5");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
    }
    
    public Simulador getSimulador(){
        return this.simulacion;
    }
    public void setSimulador(Simulador sim){
        this.simulacion = sim;
    }
    public void setMetodo(int m){
        this.metodo = m;
    }
    public int getMetodo(){
        return this.metodo;
    }
    
    public void cambiarPaso( int paso) {
    switch(paso){   
      case 1:{
        this.setContentPane(this.paso1);
        this.pack();
        this.setVisible(true);
        this.validate();
        this.setTitle("Simulador Ascendente. Paso 1 de 5");
        break;    
      }
     case 2: {                  
        this.setContentPane(this.paso2);
        this.pack();
        this.setVisible(true);
        this.validate();
        this.gramatica.generarConjPrim();
        this.gramatica.generarConjSig();
        this.paso2.construirConjuntos(this.gramatica);
        this.setTitle("Simulador Ascendente. Paso 2 de 5");
        this.setLocationRelativeTo(null);
        break;            
      }
      case 3: 
      {
        PanelNuevaSimAscPaso3 paso3 = new PanelNuevaSimAscPaso3(this);
        this.paso3 = paso3;
        this.setContentPane(this.paso3);        
        this.pack();
        this.setVisible(true);
        this.validate();                
        this.setTitle("Simulador Ascendente. Paso 3 de 5");
        break;    
      }
      case 4: 
      {     
        PanelNuevaSimAscPaso4 paso4 = new PanelNuevaSimAscPaso4(this);
        this.paso4 = paso4;
        this.setContentPane(this.paso4);
        this.pack();        
        this.setVisible(true);
        this.validate();        
        this.setTitle("Simulador Ascendente. Paso 4 de 5");
        break;
      }
      case 5: 
      {                    
        PanelNuevaSimAscPaso5 paso5 = new PanelNuevaSimAscPaso5(this);
        this.paso5 = paso5;  
        this.setContentPane(this.paso5);
        this.pack();        
        this.setVisible(true);
        this.validate();        
        this.setTitle("Simulador Ascendente. Paso 5 de 5");
        this.setLocationRelativeTo(null);
        break;    
      }
      case 6: 
      {                      
        PanelNuevaSimAscPaso6 paso6 = new PanelNuevaSimAscPaso6(this);
        this.paso6 = paso6;  
        this.setContentPane(this.paso6);
        this.pack();        
        this.setVisible(true);
        this.validate();        
        this.setTitle("Simulador Ascendente. Paso 6 de 6");
        this.setLocationRelativeTo(null);
        break;
      }         
    }
 }
    public Gramatica getGramatica(){
        return this.gramatica;
    }
    
    void setGramatica(Gramatica gr){
        this.gramatica = gr;
    }    
    
    public void finalizarAsistente(){        
        this.dispose();
        SimAS simas = new SimAS();        
        simas.lanzarSimulador(2, this.gramatica, this.getMetodo());     
    }
    
    public void setColeccion(ColCanLR0 col){
        this.gramatica.setColeccionLR0(col);
    }
    
    public void setColeccion(ColCanLR1 col){
       this.gramatica.setColeccionLR1(col);
    }
    
    public void setColeccion(ColCanLALR col){
       this.gramatica.setColeccionLALR(col);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}