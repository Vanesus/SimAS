// SimAs  / Editor
// ConjElementosLR0

package es.uco.simas.editor;

import es.uco.simas.util.gramatica.Gramatica;
import es.uco.simas.util.gramatica.Produccion;
import es.uco.simas.util.gramatica.Simbolo;
import java.util.ArrayList;

/**
 * @author vanesa
 */
public class ConjElementosLR0 {
    ArrayList<ElementosLR0> elementosLR0 = new ArrayList(); 
    int i; // numero de iteracion
    int col; //coleccion con la que se calcula
    String simbolo = "";
    Gramatica gramatica;
    ConjElementosLR0 conj;    
    
    public ConjElementosLR0(Gramatica gramatica){        
        this.gramatica = gramatica;
        this.primero();
    }
    
    public ConjElementosLR0(int i, int col, String simbolo, ConjElementosLR0 conj, Gramatica gr){
        this.i = i;
        this.col = col;
        this.simbolo = simbolo;
        this.conj = conj;        
        this.gramatica = gr;
        this.elementosLR0 = new ArrayList();       
        this.construir();
    }
    
    public ConjElementosLR0(int i, int col, String simbolo){
        this.i = i;
        this.col = col;
        this.simbolo = simbolo;
    }
    
    void primero(){
        ArrayList<Produccion> producciones = this.gramatica.getPr();
        ArrayList<String> simb = new ArrayList();
        Produccion prod = producciones.get(0);
        
        this.elementosLR0.add(new ElementosLR0(prod,0,prod.getConsec().get(0).getNombre()));
        
        String simbolo = prod.getConsec().get(0).getNombre();
        int i=1;
        while(i<producciones.size()){
            if(simbolo.equals(producciones.get(i).getAntec().getSimboloNT().getNombre())){
                if(producciones.get(i).getConsec().get(0).getNombre().equals("\u03b5")){
                    this.elementosLR0.add(new ElementosLR0(producciones.get(i),1, ""));                      
                }else{ 
                    this.elementosLR0.add(new ElementosLR0(producciones.get(i),0, producciones.get(i).getConsec().get(0).getNombre()));  
                }
            }
            i++;
        }                    
        i=1;
        while(i<this.elementosLR0.size()){            
            if(!this.elementosLR0.get(i).getPivote().equals(simbolo)){
                int k =0;
                if(simb.size()==0){
                    simb.add(this.elementosLR0.get(i).getPivote());
                }else{
                    int encontrado = 0;
                    while(k< simb.size()){
                        if(simb.get(k).equals(this.elementosLR0.get(i).getPivote())){                                        
                            encontrado = 1;
                            break;                            
                        }else
                            k++;
                    }
                    if(encontrado ==0){
                        simb.add(this.elementosLR0.get(i).getPivote());                        
                    }
                }
            }
            i++;
        }                
        
        int k =0;
        if(simb.size() != 0){
            while(k < simb.size()){                
                simbolo = simb.get(k);
                i=1;
                while(i<producciones.size()){
                    if(simbolo.equals(producciones.get(i).getAntec().getSimboloNT().getNombre())){
                        if(producciones.get(i).getConsec().get(0).getNombre().equals("\u03b5")){
                            this.elementosLR0.add(new ElementosLR0(producciones.get(i),1, ""));                             
                        }else{
                            this.elementosLR0.add(new ElementosLR0(producciones.get(i),0, producciones.get(i).getConsec().get(0).getNombre()));
                        }                        
                        int l=0;
                        int encontrado =0;
                        while(l < simb.size()){
                            if(simb.get(l).equals(producciones.get(i).getConsec().get(0).getNombre())){
                                encontrado = 1;                                
                                break;
                            }else{
                                encontrado = 0;
                                l++;
                            }
                        }
                        if(encontrado == 0){                            
                            simb.add(producciones.get(i).getConsec().get(0).getNombre());
                        }
                    }
                    i++;
                }
                k++;
            }
        }                                     
    }
       
    void construir(){
        ArrayList<ElementosLR0> elementos = this.conj.getElementosLR0();      
        ArrayList<String> simb = new ArrayList();
        ArrayList<Produccion> producciones = new ArrayList();
        producciones = this.gramatica.getPr();
        String simbolo = new String();
        int j=0;        
        while(j<elementos.size()){            
            ElementosLR0 el = new ElementosLR0(elementos.get(j).getProduc(),elementos.get(j).getPosicion(),elementos.get(j).getPivote());            
             if(elementos.get(j).getProduc().getConsec().get(0).getNombre().equals("\u03b5")){
                    el.setPosicion(el.getPosicion()+1);
                    el.setPivote("");
            } 
            
            if(el.getPivote().equals(this.simbolo)){                    
                el.setPosicion(el.getPosicion()+1);                
                String pivote = el.getPivote();                
                ArrayList<Simbolo> consec = el.getProduc().getConsec();                                  
                int k=0;
                                
                if(el.posicion < consec.size()){ //Seleccionar pivote
                    if(consec.get(el.posicion-1).getNombre().equals(pivote)){                                                
                        el.setPivote(consec.get(el.getPosicion()).getNombre());                            
                        int m =0;
                        if(simb.size()==0){
                            simb.add(el.getPivote());                                                                
                        }else{
                            int encontrado = 0;
                            while(m< simb.size()){
                                if(simb.get(m).equals(el.getPivote())){                                        
                                    encontrado = 1;
                                    break;                                        
                                }else
                                    m++;
                            }
                            if(encontrado == 0){
                                simb.add(el.getPivote());                                    
                            }
                        }                            

                         this.elementosLR0.add(new ElementosLR0(el.getProduc(),el.getPosicion(), el.getPivote()));                       
                    }
                }else{                            
                    el.setPivote("");

                    el.setPivote("");
                    this.elementosLR0.add(new ElementosLR0(el.getProduc(),el.getPosicion(), el.getPivote()));                                          
                 }                                      
            }
            j++;        
        }                        
        int k =0;
        if(simb.size() != 0){
            while(k < simb.size()){                
                simbolo = simb.get(k);
                int i=1;
                while(i<producciones.size()){
                    
                    if(simbolo.equals(producciones.get(i).getAntec().getSimboloNT().getNombre())){
                        if(producciones.get(i).getConsec().get(0).getNombre().equals("\u03b5")){
                            this.elementosLR0.add(new ElementosLR0(producciones.get(i),1, ""));                             
                        }else{
                            this.elementosLR0.add(new ElementosLR0(producciones.get(i),0, producciones.get(i).getConsec().get(0).getNombre()));
                        }
                        
                        int l=0;
                        while(l < simb.size()){
                            if(!simb.get(k).equals(producciones.get(i).getConsec().get(0).getNombre())){
                                simb.add(producciones.get(i).getConsec().get(0).getNombre());
                                break;
                            }
                            l++;
                        }
                    }
                    i++;
                }
                k++;
            }
        }                        
    }    

    public ArrayList<ElementosLR0> getElementosLR0() {
        return elementosLR0;
    }

    public void setElementosLR0(ArrayList<ElementosLR0> elementosLR0) {
        this.elementosLR0 = elementosLR0;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }            
}