//SimAS  /  Editor
// Parte ir_a

package es.uco.simas.editor;

import es.uco.simas.util.gramatica.Gramatica;
import javax.swing.table.DefaultTableModel;

/**
 * @author vanesa
 */
public class ParteIrA {
    private  DefaultTableModel matrizIrA ;
    Gramatica gramatica;    
    
    public ParteIrA(Gramatica gramatica){
        this.gramatica = gramatica;        
    }
    
    void construir(int met){
        ColCanLR0 coleccion = new ColCanLR0();
        ColCanLR1 col1 = new ColCanLR1();
        ColCanLALR col2 = new ColCanLALR();
        if(met == 1){
            coleccion = this.gramatica.getColeccionLR0();
        }
        if(met == 2){
            col1 = this.gramatica.getColeccionLR1();
        }
        if(met == 3){
            col2 = this.gramatica.getColeccionLALR();
        }
               
        int i =0;
        this.matrizIrA =new DefaultTableModel();                        
        //Se añaden los simbolos no terminales en las columnas
        while(i < this.gramatica.getNoTerm().size()){            
            this.matrizIrA.addColumn(this.gramatica.getNoTerm().get(i).getNombre());
            i++;
        }
        if(met == 1){
            //Se añaden los estados en las filas
            i=0;                 
            while(i < this.gramatica.getColeccionLR0().getConjElementosLR0().size()){                                        
                Object []  linea = new Object[]  {

                     };
                    this.matrizIrA.addRow(linea);  
                i++;
            }
                
            i=0;
            while(i < coleccion.getConjElementosLR0().size()){            
                int fila = coleccion.getConjElementosLR0().get(i).col;
                int columna = 0;
                int j=0;
                int encontrado =0;
                while(j < this.matrizIrA.getColumnCount()){
                    if(coleccion.getConjElementosLR0().get(i).simbolo.equals(this.matrizIrA.getColumnName(j))){
                        columna = j;
                        encontrado = 1;
                        break;
                    }else
                        j++;
                }
                if(encontrado == 1)
                    this.matrizIrA.setValueAt(coleccion.getConjElementosLR0().get(i).getI(), fila, columna);

                i++;
            }
            i=0;
            while(i < coleccion.getConj2().size()){               
                int fila = coleccion.getConj2().get(i).col;
                int columna = 0;
                int j=0;
                int encontrado =0;
                while(j < this.matrizIrA.getColumnCount()){
                    if(coleccion.getConj2().get(i).simbolo.equals(this.matrizIrA.getColumnName(j))){
                        columna = j;
                        encontrado = 1;
                        break;
                    }else
                        j++;
                }
                if(encontrado == 1){
                    this.matrizIrA.setValueAt(coleccion.getConj2().get(i).getI(), fila, columna);                
                }
                i++;
            }
        }
        if(met == 2){
            //Se añaden los estados en las filas
            i=0;                 
            while(i < this.gramatica.getColeccionLR1().getConjElementosLR1().size()){                                        
                Object []  linea = new Object[]  {

                     };
                    this.matrizIrA.addRow(linea);  
                i++;
            }
            i=0;
            while(i < col1.getConjElementosLR1().size()){            
                int fila = col1.getConjElementosLR1().get(i).col;
                int columna = 0;
                int j=0;
                int encontrado =0;
                while(j < this.matrizIrA.getColumnCount()){
                    if(col1.getConjElementosLR1().get(i).simbolo.equals(this.matrizIrA.getColumnName(j))){
                        columna = j;
                        encontrado = 1;
                        break;
                    }else
                        j++;
                }
                if(encontrado == 1)
                    this.matrizIrA.setValueAt(col1.getConjElementosLR1().get(i).getI(), fila, columna);

                i++;
            }
            i=0;
            while(i < col1.getConj2().size()){               
                int fila = col1.getConj2().get(i).col;
                int columna = 0;
                int j=0;
                int encontrado =0;
                while(j < this.matrizIrA.getColumnCount()){
                    if(col1.getConj2().get(i).simbolo.equals(this.matrizIrA.getColumnName(j))){
                        columna = j;
                        encontrado = 1;
                        break;
                    }else
                        j++;
                }
                if(encontrado == 1){
                    this.matrizIrA.setValueAt(col1.getConj2().get(i).getI(), fila, columna);                
                }
                i++;
            }
        }
        
        if(met == 3){
            //Se añaden los estados en las filas
            i=0;                 
            while(i < this.gramatica.getColeccionLALR().getConjElementosLALR().size()){                                        
                Object []  linea = new Object[]  {
                     };
                    this.matrizIrA.addRow(linea);  
                i++;
            }
            i=0;
            while(i < col2.getConjElementosLALR().size()){  
                int fila = col2.getConjElementosLALR().get(i).col;
                int columna = 0;
                int j=0;
                int encontrado =0;
                while(j < this.matrizIrA.getColumnCount()){
                    if(col2.getConjElementosLALR().get(i).simbolo.equals(this.matrizIrA.getColumnName(j))){
                        columna = j;
                        encontrado = 1;
                        break;
                    }else
                        j++;
                }
                if(encontrado == 1)
                    if(col2.getConjElementosLALR().get(i).getV() != -1){
                        this.matrizIrA.setValueAt(col2.getConjElementosLALR().get(i).getI()+"-"+col2.getConjElementosLALR().get(i).getV(), fila, columna);
                    }else{                    
                        this.matrizIrA.setValueAt(col2.getConjElementosLALR().get(i).getI(), fila, columna);
                    }
                i++;
            }
            i=0;
            while(i < col2.getConj2().size()){  
                int w=0;
                int fila = -1;
                while(w < col2.getConjElementosLALR().size()){
                    if(col2.getConjElementosLALR().get(w).getI() == col2.getConj2().get(i).col){
                        fila = w;                        
                        break;
                    }
                    w++;
                }
                if(fila == -1){
                    w=0;
                    while(w < col2.getConjElementosLALR().size()){
                    if(col2.getConjElementosLALR().get(w).getV() == col2.getConj2().get(i).col){
                        fila = w;                        
                        break;
                    }
                    w++;
                    }
                }
                
               // int fila = col2.getConj2().get(i).col;
                int columna = 0;
                int j=0;
                int encontrado =0;
                while(j < this.matrizIrA.getColumnCount()){
                    if(col2.getConj2().get(i).simbolo.equals(this.matrizIrA.getColumnName(j))){
                        columna = j;
                        encontrado = 1;
                        break;
                    }else
                        j++;
                }
                if(encontrado == 1){
                    if(col2.getConj2().get(i).getV() != -1){
                        this.matrizIrA.setValueAt(col2.getConj2().get(i).getI()+"-"+col2.getConj2().get(i).getV(), fila, columna); 
                    }else{
                        this.matrizIrA.setValueAt(col2.getConj2().get(i).getI(), fila, columna);                
                    }
                }
                i++;
            }
        }        
    }

    public DefaultTableModel getMatrizIrA() {
        return matrizIrA;
    }            
}