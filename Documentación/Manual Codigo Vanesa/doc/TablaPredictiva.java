//SimAS  /  Editor
//Tabla Predictiva

package es.uco.simas.editor;

import es.uco.simas.util.gramatica.Gramatica;
import es.uco.simas.util.gramatica.Terminal;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

/**
 * @author vanesa
 */
public class TablaPredictiva {
    
    ArrayList<FuncionError> funError = new ArrayList();    
    private  DefaultTableModel matrizPred ;
    Gramatica gramatica;
    
    public TablaPredictiva(){
        DefaultTableModel tabla = new DefaultTableModel();
        this.matrizPred = tabla;        
    }
    
    public void construir(Gramatica gramatica){
        int i = 0;
        int j = 0;
        int fila = 0;
        int columna = 0;        
        this.gramatica = gramatica;
        DefaultListModel produc = gramatica.getProducciones();
        ArrayList<Terminal> primeros = null;
        ArrayList<Terminal> siguientes = null;
        
        this.matrizPred =new DefaultTableModel();                
        this.matrizPred.addColumn("");
        //Se anaden los simbolos terminales en las columnas
        while(i < this.gramatica.getTerm().size()){                
            this.matrizPred.addColumn(this.gramatica.getTerm().get(i).getNombre());
            i++;
        }
        this.matrizPred.addColumn("$");
        
        //Se anaden los simbolos no terminales en las filas
        i=0;                 
        while(i < this.gramatica.getNoTerm().size()){              
            Object []  linea = new Object[]  {
                  this.gramatica.getNoTerm().get(i).getNombre()
                 };
                this.matrizPred.addRow(linea);  
                
            i++;
        }
        
        i=0;
        while(i < produc.size()){
            String valor = produc.getElementAt(i).toString();            
            String [] separado = valor.split(" ");            
            String antec = separado[0];            
            
            int k=0;
            while(k < this.gramatica.getNoTerm().size()){
                if(this.gramatica.getNoTerm().get(k).getNombre().equals(separado[0])){
                    fila = k;                    
                    break;
                }
                k++;
            }
            
            if(this.gramatica.isNoTerminal(separado[2])){
                j=0;
                while(j < this.gramatica.getNoTerm().size()){
                    if(this.gramatica.getNoTerm().get(j).getNombre().equals(separado[2])){
                        primeros = this.gramatica.getNoTerm().get(j).getPrimeros();
                        break;
                    }else
                        j++;                                            
                }            
                if(primeros != null){
                    j=0;
                    while(j < primeros.size()){
                        String nombre = ""+(i+1);
                        Object objeto = nombre;                         

                        k=0;
                        while(k < this.gramatica.getTerm().size()){
                            if(this.gramatica.getTerm().get(k).getNombre().equals(primeros.get(j).getNombre())){
                                columna = k+1;                                
                                break;
                            }
                            k++;
                        }
                        if(columna!=0)
                            setCeldaPredictiva(objeto, fila, columna);
                        
                        j++;
                    }
                }
            }else{            
                if(this.gramatica.isTerminal(separado[2])){                 
                    String nombre = ""+(i+1);
                    Object objeto = nombre;  
                    k=0;
                    while(k < this.gramatica.getTerm().size()){
                        if(this.gramatica.getTerm().get(k).getNombre().equals(separado[2])){
                            columna = k+1;                            
                            break;
                        }
                        k++;
                    }
                    setCeldaPredictiva(objeto, fila, columna);

                }else{ // Si contiene epsilon se utiliza el conj.siguiente
                    if(separado[2].equals("\u03b5")){                        
                        j=0;
                        while(j < this.gramatica.getNoTerm().size()){
                            if(this.gramatica.getNoTerm().get(j).getNombre().equals(antec)){
                                siguientes = this.gramatica.getNoTerm().get(j).getSiguientes();
                                break;
                            }else
                                j++;                                            
                        }            
                        if(siguientes != null){                            
                            j=0;
                            while(j < siguientes.size()){
                                String nombre = ""+(i+1);
                                Object objeto = nombre;                         

                                k=0;
                                while(k < this.gramatica.getTerm().size()){
                                    if(siguientes.get(j).getNombre().equals("$")){
                                        columna = this.matrizPred.getColumnCount()-1;                                         
                                        break;
                                    }else{

                                        if(this.gramatica.getTerm().get(k).getNombre().equals(siguientes.get(j).getNombre())){
                                            columna = k+1;                                            
                                            break;
                                        }
                                    }
                                        k++;
                                }
                                setCeldaPredictiva(objeto, fila, columna);                              
                                j++;
                            }
                        }                        
                    }
                }            
            }
            i++;
        }
        
    }
    
    public DefaultTableModel getTabla(){
        return this.matrizPred;
    }
    
    public void setTabla(DefaultTableModel tabla){
        this.matrizPred = tabla;
    }            
    
    public Object getCeldaPredictiva(int i, int j){
        return this.matrizPred.getValueAt(i, j);
    }
    
    public void setCeldaPredictiva(Object objeto, int i, int j){        
        this.matrizPred.setValueAt(objeto, i, j);               
    }   
    
    public void crearFunError(FuncionError fun){                   
        this.funError.add(fun);                                         
    }

    public ArrayList<FuncionError> getFunError() {
        return funError;
    }
    
    public void setFunError(ArrayList<FuncionError> funcion){
        this.funError = funcion;
    }        
}
