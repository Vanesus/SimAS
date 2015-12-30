//SimAS  /  Editor
// PateAccion

package es.uco.simas.editor;

import es.uco.simas.util.gramatica.Gramatica;
import es.uco.simas.util.gramatica.Produccion;
import es.uco.simas.util.gramatica.Terminal;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 * @author vanesa
 */
public class ParteAccion {
    ArrayList<FuncionError> funError = new ArrayList();    
    private  DefaultTableModel matrizAccion ;
    Gramatica gramatica;  
    
    public ParteAccion(Gramatica gramatica){
        this.gramatica = gramatica;        
    }
    
    void construir(int met){
        int i =0;
        ColCanLR0 coleccion = new ColCanLR0();
        ColCanLR1 col1 = new ColCanLR1();
        ColCanLALR col2 = new ColCanLALR();
        if(met == 1)
            coleccion = this.gramatica.getColeccionLR0();                    
        if(met == 2)
            col1 = this.gramatica.getColeccionLR1();                    
        if(met == 3)
            col2 = this.gramatica.getColeccionLALR();                    
        
        this.matrizAccion =new DefaultTableModel();  
        if(met == 3){
            this.matrizAccion.addColumn("Estados Antiguos");
            this.matrizAccion.addColumn("Estados Nuevos");
        }else{
            this.matrizAccion.addColumn("Estados");
        }
        //Se añaden los simbolos terminales en las columnas
        while(i < this.gramatica.getTerm().size()){            
            this.matrizAccion.addColumn(this.gramatica.getTerm().get(i).getNombre());
            i++;
        }
        this.matrizAccion.addColumn("$");
        
        if(met == 1){  //SLR
            //Se añaden los estados en las filas
            i=0;                 
            while(i < this.gramatica.getColeccionLR0().getConjElementosLR0().size()){                                        
                Object []  linea = new Object[]  {
                      i
                     };
                    this.matrizAccion.addRow(linea);  
                i++;
            }

            //Los añaden los desplazamientos
            i=0;
            while(i < coleccion.getConjElementosLR0().size()){            
                int fila = coleccion.getConjElementosLR0().get(i).col;
                int columna = 0;
                int j=0;
                int encontrado =0;
                while(j < this.matrizAccion.getColumnCount()){
                    if(coleccion.getConjElementosLR0().get(i).simbolo.equals(this.matrizAccion.getColumnName(j))){
                        columna = j;
                        encontrado = 1;
                        break;
                    }else
                        j++;
                }
                if(encontrado == 1){
                    if(this.matrizAccion.getValueAt(fila, columna)== null){
                        this.matrizAccion.setValueAt("d"+coleccion.getConjElementosLR0().get(i).getI(), fila, columna);
                    }else{                        
                        String valor = this.matrizAccion.getValueAt(fila, columna)+", d"+coleccion.getConjElementosLR0().get(i).getI();
                        this.matrizAccion.setValueAt("conf. "+valor, fila, columna);
                    }
                }
                i++;
            }
            i=0;
            while(i < coleccion.getConj2().size()){               
                int fila = coleccion.getConj2().get(i).col;
                int columna = 0;
                int j=0;
                int encontrado =0;
                while(j < this.matrizAccion.getColumnCount()){
                    if(coleccion.getConj2().get(i).simbolo.equals(this.matrizAccion.getColumnName(j))){
                        columna = j;
                        encontrado = 1;
                        break;
                    }else
                        j++;
                }
                if(encontrado == 1){
                    if(this.matrizAccion.getValueAt(fila, columna) == null){
                        this.matrizAccion.setValueAt("d"+coleccion.getConj2().get(i).getI(), fila, columna);                
                    }else{                        
                        String valor = this.matrizAccion.getValueAt(fila, columna)+", d"+coleccion.getConj2().get(i).getI();
                        this.matrizAccion.setValueAt("conf. "+valor, fila, columna);                                     
                    }
                }

                i++;
            }
            this.reducciones(met);
        }
        
        if(met == 2){    //LR-canonico        
            //Se añaden los estados en las filas
            i=0;                 
            while(i < this.gramatica.getColeccionLR1().getConjElementosLR1().size()){                                        
                Object []  linea = new Object[]  {
                      i
                     };
                    this.matrizAccion.addRow(linea);  
                i++;
            }

            //Los añaden los desplazamientos
            i=0;
            while(i < col1.getConjElementosLR1().size()){            
                int fila = col1.getConjElementosLR1().get(i).col;                
                int columna = 0;
                int j=0;
                int encontrado =0;
                while(j < this.matrizAccion.getColumnCount()){
                    if(col1.getConjElementosLR1().get(i).simbolo.equals(this.matrizAccion.getColumnName(j))){
                        columna = j;
                        encontrado = 1;
                        break;
                    }else
                        j++;
                }
                if(encontrado == 1){
                    if(this.matrizAccion.getValueAt(fila, columna)== null){
                        this.matrizAccion.setValueAt("d"+col1.getConjElementosLR1().get(i).getI(), fila, columna);
                    }else{
                        String valor = this.matrizAccion.getValueAt(fila, columna)+", d"+col1.getConjElementosLR1().get(i).getI();
                        this.matrizAccion.setValueAt("conf. "+valor, fila, columna);                                                             
                    }
                }
                i++;
            }
            i=0;            
            while(i < col1.getConj2().size()){                   
                int fila = col1.getConj2().get(i).col;
                int columna = 0;
                int j=0;
                int encontrado =0;
                while(j < this.matrizAccion.getColumnCount()){
                    if(col1.getConj2().get(i).simbolo.equals(this.matrizAccion.getColumnName(j))){
                        columna = j;
                        encontrado = 1;
                        break;
                    }else
                        j++;
                }
                if(encontrado == 1){
                    if(this.matrizAccion.getValueAt(fila, columna) == null)
                        this.matrizAccion.setValueAt("d"+col1.getConj2().get(i).getI(), fila, columna);                
                    else{
                        String valor = this.matrizAccion.getValueAt(fila, columna)+", d"+col1.getConj2().get(i).getI();
                        this.matrizAccion.setValueAt("conf. "+valor, fila, columna);                                                                    
                    }
                }
                i++;
            }
            this.reducciones(met);
        }
        if(met == 3){    //LALR                        
            //Se añaden los estados en las filas
            i=0;                 
            while(i < this.gramatica.getColeccionLALR().getConjElementosLALR().size()){   
                if(this.gramatica.getColeccionLALR().getConjElementosLALR().get(i).getV()!=-1){
                    Object []  linea = new Object[]  {                          
                          this.gramatica.getColeccionLALR().getConjElementosLALR().get(i).getI()+"-"+this.gramatica.getColeccionLALR().getConjElementosLALR().get(i).getV(),
                          i
                         };
                    this.matrizAccion.addRow(linea);
                }else{
                    Object []  linea2 = new Object[]  {                        
                         this.gramatica.getColeccionLALR().getConjElementosLALR().get(i).getI(),
                         i
                        };
                    this.matrizAccion.addRow(linea2);
                }                      
                i++;
            }

            //Se añaden los desplazamientos
            i=0;            
            while(i < col2.getConjElementosLALR().size()){                  
                int fila = col2.getConjElementosLALR().get(i).col;
                int columna = 0;
                int j=0;
                int encontrado =0;
                while(j < this.matrizAccion.getColumnCount()){
                    if(col2.getConjElementosLALR().get(i).simbolo.equals(this.matrizAccion.getColumnName(j))){
                        columna = j;
                        encontrado = 1;
                        break;
                    }else
                        j++;
                }
                if(encontrado == 1){
                    if(this.matrizAccion.getValueAt(fila, columna)== null){
                        if(col2.getConjElementosLALR().get(i).getV() != -1){
                            this.matrizAccion.setValueAt("d"+col2.getConjElementosLALR().get(i).getI()+"-"+col2.getConjElementosLALR().get(i).getV(), fila, columna);
                        }else{
                            this.matrizAccion.setValueAt("d"+col2.getConjElementosLALR().get(i).getI(), fila, columna);
                        }
                    }else{ 
                        String valor= "";
                        if(col2.getConjElementosLALR().get(i).getV() != -1){
                            valor = this.matrizAccion.getValueAt(fila, columna)+", d"+col2.getConjElementosLALR().get(i).getI()+"-"+col2.getConjElementosLALR().get(i).getV();                            
                        }else{
                            valor = this.matrizAccion.getValueAt(fila, columna)+", d"+col2.getConjElementosLALR().get(i).getI();                            
                        }                        
                        this.matrizAccion.setValueAt("conf. "+valor, fila, columna);                                                      
                    }
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
                //int fila = col2.getConj2().get(i).col;
                int columna = 0;
                int j=0;
                int encontrado =0;
                while(j < this.matrizAccion.getColumnCount()){
                    if(col2.getConj2().get(i).simbolo.equals(this.matrizAccion.getColumnName(j))){
                        columna = j;
                        encontrado = 1;
                        break;
                    }else
                        j++;
                }
                if(encontrado == 1){
                    System.out.println(fila +"   "+ columna);
                    if(this.matrizAccion.getValueAt(fila, columna) == null){
                        if(col2.getConj2().get(i).getV() != -1){
                            this.matrizAccion.setValueAt("d"+col2.getConj2().get(i).getI()+"-"+col2.getConj2().get(i).getV(), fila, columna);                                            
                        }else{
                            this.matrizAccion.setValueAt("d"+col2.getConj2().get(i).getI(), fila, columna);                
                        }                        
                    }else{
                        String valor= "";
                        if(col2.getConj2().get(i).getV() != -1){
                            valor = this.matrizAccion.getValueAt(fila, columna)+", d"+col2.getConj2().get(i).getI()+"-"+col2.getConj2().get(i).getV();
                        }else{
                            valor = this.matrizAccion.getValueAt(fila, columna)+", d"+col2.getConj2().get(i).getI();                            
                        }                        
                        this.matrizAccion.setValueAt("conf. "+valor, fila, columna);                                     
                    }
                }

                i++;
            }
            this.reducciones(met);
        }
    }
    
    public void reducciones(int met){
        ArrayList<ConjElementosLR0> coleccion = new ArrayList();
        ArrayList<ConjElementosLR1> col1 = new ArrayList();
        ArrayList<ConjElementosLR1> col2 = new ArrayList();
        if(met == 1)
            coleccion = this.gramatica.getColeccionLR0().getConjElementosLR0();
        if(met == 2)
            col1 = this.gramatica.getColeccionLR1().getConjElementosLR1();
        if(met == 3)
            col2 = this.gramatica.getColeccionLALR().getConjElementosLALR();
        
        ArrayList<Produccion> produc = new ArrayList();//this.gramatica.getPr();
        ArrayList<Integer> estado = new ArrayList();
        ArrayList<Terminal> siguientes = new ArrayList<>();
        int i=0;
        int v=0; 
        if(met == 1){
            while(i < coleccion.size()){
                int j=0;
                while(j < coleccion.get(i).getElementosLR0().size()){
                    if(coleccion.get(i).getElementosLR0().get(j).getPosicion() == coleccion.get(i).getElementosLR0().get(j).getProduc().getConsec().size()){
                        estado.add(coleccion.get(i).getI());
                        produc.add(coleccion.get(i).getElementosLR0().get(j).getProduc());
                        v++;
                    }
                    j++;                    
                }
                i++;
            }
            
            i=0;
            while(i < estado.size()){
                if(estado.get(i) == 1 && produc.get(i).getAntec().getSimboloNT().getNombre().equals(this.gramatica.getSimbInicial()+"'")){
                    this.matrizAccion.setValueAt("Aceptar", 1, this.matrizAccion.getColumnCount()-1);
                }else{
                    int m=0;
                    //Conjunto siguiente del antecedente
                    while(m < this.gramatica.getNoTerm().size()){
                        siguientes = new ArrayList();
                        if (this.gramatica.getNoTerm().get(m).getNombre().equals(produc.get(i).getAntec().getSimboloNT().getNombre())){
                            siguientes = this.gramatica.getNoTerm().get(m).getSiguientes();                        
                            break;
                        }else
                            m++;
                    }
                    int j=1;
                    while(j < this.matrizAccion.getColumnCount()){
                        int k=0;
                        while(k < siguientes.size()){                        
                            if(this.matrizAccion.getColumnName(j).equals(siguientes.get(k).getNombre())){                            
                                int n=1;                            
                                int pr =0;
                                //buscar el numero de la produccion
                                while(n < this.gramatica.getPr().size()){                                
                                    pr=0;                                
                                    int iguales =1;                                
                                    if(this.gramatica.getPr().get(n).getAntec().getSimboloNT().getNombre().equals(produc.get(i).getAntec().getSimboloNT().getNombre())){                                    
                                        int p=0;                     
                                        if(this.gramatica.getPr().get(n).getConsec().size() != produc.get(i).getConsec().size()){
                                            iguales =0;
                                        }else{
                                            while(p < this.gramatica.getPr().get(n).getConsec().size()){                                            
                                                if(!this.gramatica.getPr().get(n).getConsec().get(p).getNombre().equals(produc.get(i).getConsec().get(p).getNombre())){                                            
                                                    iguales =0;
                                                    break;
                                                }else
                                                    p++;
                                            }
                                        }        
                                        if(iguales == 1){
                                            pr = n;                                        
                                            break;
                                        }                                
                                    }
                                    n++;                                
                                }  
                                if(pr !=0){
                                    if(this.matrizAccion.getValueAt(estado.get(i), j) == null)
                                        this.matrizAccion.setValueAt("r"+pr, estado.get(i), j);
                                    else {
                                        String valor = this.matrizAccion.getValueAt(estado.get(i),j)+", r"+pr;                                        
                                        this.matrizAccion.setValueAt("conf. "+valor, estado.get(i), j);
                                    }
                                    break;
                                }
                            }
                            k++;
                        }
                        j++;
                    }
                }            
                i++;
            }       
        }
        
        if(met == 2){            
            ArrayList<String> anticipacion = new ArrayList();
            while(i < col1.size()){
                int j=0;
                while(j < col1.get(i).getElementosLR1().size()){
                    //Elementos con el punto al final
                    if(col1.get(i).getElementosLR1().get(j).getPosicion() == col1.get(i).getElementosLR1().get(j).getProduc().getConsec().size()){
                        int est = col1.get(i).getI();
                        Produccion prod = col1.get(i).getElementosLR1().get(j).getProduc();
                        anticipacion = col1.get(i).getElementosLR1().get(j).getAnticipacion();
                        int n=1;                            
                        int pr =0;
                        //buscar el numero de la produccion
                        while(n < this.gramatica.getPr().size()){                                
                            pr=0;                                
                            int iguales =1;                                
                            if(this.gramatica.getPr().get(n).getAntec().getSimboloNT().getNombre().equals(prod.getAntec().getSimboloNT().getNombre())){                                    
                                int p=0;                                    
                                while(p < this.gramatica.getPr().get(n).getConsec().size()){
                                    if(!this.gramatica.getPr().get(n).getConsec().get(p).getNombre().equals(prod.getConsec().get(p).getNombre())){                                            
                                        iguales =0;
                                        break;
                                    }else
                                        p++;
                                }
                                if(iguales == 1){
                                    pr = n;                                        
                                    break;
                                }                                
                            }
                            n++;                                
                        }
                        if(est == 1 && prod.getAntec().getSimboloNT().getNombre().equals(this.gramatica.getSimbInicial()+"'")){
                            this.matrizAccion.setValueAt("Aceptar", i, this.matrizAccion.getColumnCount()-1);
                        }else{                                   

                            v=1;
                            
                            while(v < this.matrizAccion.getColumnCount()){         
                                int w = 0;
                                while(w < anticipacion.size()){                                   
                                    if(anticipacion.get(w).equals(this.matrizAccion.getColumnName(v))){

                                        if(pr !=0){
                                            if(this.matrizAccion.getValueAt(est, v) == null)
                                                this.matrizAccion.setValueAt("r"+pr, est, v);
                                            else{ 
                                                String valor = this.matrizAccion.getValueAt(est,v)+", r"+pr;                                                                                        
                                                this.matrizAccion.setValueAt("conf. "+valor, est, v);                                                
                                            }
                                        }
                                    }
                                    w++;
                                }
                                v++;
                            }
                        }                                               
                    }
                    j++;                    
                }
                i++;
            }                          
        } 
        
        if(met == 3){            
            ArrayList<String> anticipacion = new ArrayList();
            while(i < col2.size()){
                int j=0;
                while(j < col2.get(i).getElementosLR1().size()){
                    //Elementos con el punto al final
                    if(col2.get(i).getElementosLR1().get(j).getPosicion() == col2.get(i).getElementosLR1().get(j).getProduc().getConsec().size()){
                        int est = col2.get(i).getY();
                        Produccion prod = col2.get(i).getElementosLR1().get(j).getProduc();
                        anticipacion = col2.get(i).getElementosLR1().get(j).getAnticipacion();
                        int n=1;                            
                        int pr =0;
                        //buscar el numero de la produccion
                        while(n < this.gramatica.getPr().size()){                                
                            pr=0;                                
                            int iguales =1;                                
                            if(this.gramatica.getPr().get(n).getAntec().getSimboloNT().getNombre().equals(prod.getAntec().getSimboloNT().getNombre())){                                    
                                int p=0;                                    
                                while(p < this.gramatica.getPr().get(n).getConsec().size()){
                                    if(!this.gramatica.getPr().get(n).getConsec().get(p).getNombre().equals(prod.getConsec().get(p).getNombre())){                                            
                                        iguales =0;
                                        break;
                                    }else
                                        p++;
                                }

                                if(iguales == 1){
                                    pr = n;                                        
                                    break;
                                }                                
                            }
                            n++;                                
                        }
                        if(est == 1 && prod.getAntec().getSimboloNT().getNombre().equals(this.gramatica.getSimbInicial()+"'")){
                            this.matrizAccion.setValueAt("Aceptar", i, this.matrizAccion.getColumnCount()-1);
                        }else{                                   

                            v=1;                            
                            while(v < this.matrizAccion.getColumnCount()){         
                                int w = 0;
                                while(w < anticipacion.size()){                                   
                                    if(anticipacion.get(w).equals(this.matrizAccion.getColumnName(v))){

                                        if(pr !=0){
                                            if(this.matrizAccion.getValueAt(est, v) == null)
                                                this.matrizAccion.setValueAt("r"+pr, est, v);
                                            else{
                                                String valor = this.matrizAccion.getValueAt(est,v)+", r"+pr;                                                                                        
                                                this.matrizAccion.setValueAt("conf. "+valor, est, v);                                                                                                                        
                                            }    
                                        }
                                    }
                                    w++;
                                }
                                v++;
                            }
                        }                                               
                    }
                    j++;                    
                }
                i++;
            }                          
        } 
        
    }

    public DefaultTableModel getMatrizAccion() {
        return matrizAccion;
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