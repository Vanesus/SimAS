//SimAS

package es.uco.simas;

import es.uco.simas.editor.Editor;
import es.uco.simas.simulador.Simulador;
import es.uco.simas.util.gramatica.Gramatica;

/**
 * @author vanesa
 */
public class SimAS {
    public SimAS(){
        
    }
 
    public void lanzarEditor(){ //Lanza el Editor
        Editor editor = new Editor();
        editor.setLocationRelativeTo(null);
        editor.setVisible(true);            
    }
    
    public void lanzarEditor(Gramatica gramatica){  //Editor con una gramatica
        Editor editor = new Editor(gramatica);
        editor.setLocationRelativeTo(null);
        editor.setVisible(true);
    }
  
    
    public void lanzarSimulador(int i, Gramatica gramatica, int met){   //Lanza el simulador     
        Simulador simulador = new Simulador(i, gramatica, met);
        simulador.setLocationRelativeTo(null);
        simulador.setVisible(true);
        
    }
    public void lanzarAyuda(){
        
    }
    
     public static void main(String args[]) {
         
       
        Thread t = new Thread(new Bienvenida());
        t.start();
       
          
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SimAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SimAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SimAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SimAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                SimAS simas = new SimAS();                               
            }
        });
     
    }
}
