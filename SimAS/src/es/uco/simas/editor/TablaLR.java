// SimAS  /  Editor
// Tabla LR

package es.uco.simas.editor;

import es.uco.simas.util.gramatica.Gramatica;
import javax.swing.table.DefaultTableModel;

/**
 * @author vanesa
 */
public class TablaLR {
    int metodoAscendente;
    ParteAccion TAccion;
    ParteIrA TIrA;
    DefaultTableModel matrizLR ;
    DefaultTableModel matrizAccion;
    DefaultTableModel matrizIra;
    Gramatica gramatica;
    
    public TablaLR(Gramatica gramatica){
        this.gramatica = gramatica;        
    }
    
    public void construir(int i){        
        this.TAccion = new ParteAccion(this.gramatica);
        this.TAccion.construir(i);
        this.TIrA = new ParteIrA(this.gramatica);
        this.TIrA.construir(i);
        this.matrizAccion = this.TAccion.getMatrizAccion();
        this.matrizIra = this.TIrA.getMatrizIrA();
        
        this.gramatica.setTlr(this);            
    }

    public int getMetodoAscendente() {
        return metodoAscendente;
    }

    public void setMetodoAscendente(int metodoAscendente) {
        this.metodoAscendente = metodoAscendente;
    }

    public ParteAccion getTAccion() {
        return TAccion;
    }

    public void setTAccion(ParteAccion TAccion) {
        this.TAccion = TAccion;
    }

    public ParteIrA getTIrA() {
        return TIrA;
    }

    public void setTIrA(ParteIrA TIrA) {
        this.TIrA = TIrA;
    }

    public DefaultTableModel getMatrizLR() {
        return matrizLR;
    }  
}