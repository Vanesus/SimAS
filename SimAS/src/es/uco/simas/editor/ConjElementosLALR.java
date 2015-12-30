// SimAs  / Editor
// ConjElementosLALR

package es.uco.simas.editor;

import es.uco.simas.util.gramatica.Gramatica;
import es.uco.simas.util.gramatica.NoTerminal;
import es.uco.simas.util.gramatica.Produccion;
import es.uco.simas.util.gramatica.Simbolo;
import java.util.ArrayList;

/**
 * @author vanesa
 */
public class ConjElementosLALR {
    ArrayList<ElementosLALR> elementosLALR = new ArrayList(); 
    Gramatica gramatica;
    int i; // numero de iteracion
    int col; //coleccion con la que se calcula
    String simbolo = "";   
    ConjElementosLALR conj;  
            
    public ConjElementosLALR(Gramatica gramatica){        
        this.gramatica = gramatica;
        this.primero();
    }
    
    public ConjElementosLALR(int i, int col, String simbolo, ConjElementosLALR conj, Gramatica gr){
        this.i = i;
        this.col = col;
        this.simbolo = simbolo;
        this.conj = conj;        
        this.gramatica = gr;
        this.elementosLALR = new ArrayList();         
        this.construir();
    }
    public ConjElementosLALR(int i, int col, String simbolo){
        this.i = i;
        this.col = col;
        this.simbolo = simbolo;
    }
    
    void primero(){        
        ArrayList<Produccion> producciones = this.gramatica.getPr();
        ArrayList<String> simb = new ArrayList();
        Produccion prod = producciones.get(0);
        ArrayList<String> ant = new ArrayList();
        ElementosLALR aux = new ElementosLALR();
        ant.add("$");
        
        //S' -> .S, $
        this.elementosLALR.add(new ElementosLALR(prod,0,prod.getConsec().get(0).getNombre(), ant)); 
        aux = this.elementosLALR.get(this.elementosLALR.size()-1);
        
        String simbolo = prod.getConsec().get(0).getNombre();
        int i=1;
        while(i<producciones.size()){
            ant = new ArrayList();            
            if(simbolo.equals(producciones.get(i).getAntec().getSimboloNT().getNombre())){                
                if(calcularAnt(aux)!=null){
                    ant = calcularAnt(aux);                    
                }else{
                    ant = aux.getAnticipacion();                    
                }
                this.elementosLALR.add(new ElementosLALR(producciones.get(i),0, producciones.get(i).getConsec().get(0).getNombre(),ant));  
            }
            i++;
        }
            
        i=1;        
        int tam = this.elementosLALR.size();        
        while (i < tam){            
            if(this.elementosLALR.get(i).getPosicion() != this.elementosLALR.get(i).getProduc().getConsec().size()){
                if(this.gramatica.isNoTerminal(this.elementosLALR.get(i).getProduc().getConsec().get(this.elementosLALR.get(i).getPosicion()).getNombre())){
                    int j=0;
                    simbolo = this.elementosLALR.get(i).getProduc().getConsec().get(this.elementosLALR.get(i).getPosicion()).getNombre();
                    aux = this.elementosLALR.get(i);
                    while(j < producciones.size()){
                        if(simbolo.equals(producciones.get(j).getAntec().getSimboloNT().getNombre())){
                            ant = new ArrayList();                        
                            if(calcularAnt(aux)!=null){
                                ant = calcularAnt(aux);                    
                            }else{
                                ant = aux.getAnticipacion();
                            }
                            this.elementosLALR.add(new ElementosLALR(producciones.get(j),0, producciones.get(j).getConsec().get(0).getNombre(), ant));                                                   
                        }
                        j++;
                    }
                }
            }
            i++;
        }        
        if(tam != this.elementosLALR.size()){
            do{                                
                i=tam;
                tam = this.elementosLALR.size();
                while (i < tam){                    
                    if(this.elementosLALR.get(i).getPosicion() != this.elementosLALR.get(i).getProduc().getConsec().size()){
                        if(this.gramatica.isNoTerminal(this.elementosLALR.get(i).getProduc().getConsec().get(this.elementosLALR.get(i).getPosicion()).getNombre())){                            
                            int j=0;
                            simbolo = this.elementosLALR.get(i).getProduc().getConsec().get(this.elementosLALR.get(i).getPosicion()).getNombre();                            
                            aux = this.elementosLALR.get(i);
                            while(j < producciones.size()){                                
                                if(simbolo.equals(producciones.get(j).getAntec().getSimboloNT().getNombre())){
                                    ant = new ArrayList();                        
                                    if(calcularAnt(aux)!=null){
                                        ant = calcularAnt(aux);                    
                                    }else{
                                        ant = aux.getAnticipacion();
                                    }
                                    this.elementosLALR.add(new ElementosLALR(producciones.get(j),0, producciones.get(j).getConsec().get(0).getNombre(), ant));                                                   
                                }
                                j++;
                            }
                        }
                    }
                    i++;
                }
            }while(tam > this.elementosLALR.size());
        }                
        this.agrupamos(this.elementosLALR);                                           
    }
    
    void construir(){        
        ArrayList<ElementosLALR> elementos = this.conj.getElementosLALR();      
        ArrayList<String> simb = new ArrayList();
        ArrayList<Produccion> producciones = new ArrayList();
        producciones = this.gramatica.getPr();
        String simbolo = new String();
        ArrayList<String> ant = new ArrayList();
        ElementosLALR aux = new ElementosLALR();
        ArrayList<ElementosLALR> elem = new ArrayList();        

        int j=0;             
        while(j<elementos.size()){                                            
            ElementosLALR el = new ElementosLALR(elementos.get(j).getProduc(),elementos.get(j).getPosicion(),elementos.get(j).getPivote(), elementos.get(j).getAnticipacion());
            if(elementos.get(j).getProduc().getConsec().get(0).getNombre().equals("\u03b5")){
                    el.setPosicion(el.getPosicion()+1);
                    el.setPivote("");
            }  
            if(el.getPivote().equals(this.simbolo)){                
               
                    el.setPosicion(el.getPosicion()+1);
                
                String pivote=el.getPivote();                
                ArrayList<Simbolo> consec = el.getProduc().getConsec();                                                
                int k=0;                
                simb = new ArrayList();                   
                                
                if(el.posicion < consec.size()){ //Seleccionar pivote
                    if(consec.get(el.posicion-1).getNombre().equals(pivote) ){                        
                        
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
                        this.elementosLALR.add(new ElementosLALR(el.getProduc(),el.getPosicion(), el.getPivote(), el.getAnticipacion()));
                        if(simb.size() !=0)
                            elem.add(this.elementosLALR.get(this.elementosLALR.size()-1));                                            
                    }
                }else{                            
                    el.setPivote("");
                    this.elementosLALR.add(new ElementosLALR(el.getProduc(),el.getPosicion(), el.getPivote(),el.getAnticipacion()));                                                                                                                          
                }                
            }
            j++;
        }       
                        
        int k =0;
        if(simb.size() != 0){            
            while(k < simb.size()){                 
                simbolo = simb.get(k);                
                aux = elem.get(k);                
                ant = new ArrayList();                                                                                           
                ant = new ArrayList();                        
                if(calcularAnt(aux)!=null){                   
                    ant = calcularAnt(aux);                    
                }else{                   
                    ant = aux.getAnticipacion();
                }               
                int i=1;
                while(i<producciones.size()){
                    
                    if(simbolo.equals(producciones.get(i).getAntec().getSimboloNT().getNombre())){
                        if(producciones.get(i).getConsec().get(0).getNombre().equals("\u03b5")){
                            this.elementosLALR.add(new ElementosLALR(producciones.get(i),1, "",ant));                             
                        }else{
                            this.elementosLALR.add(new ElementosLALR(producciones.get(i),0, producciones.get(i).getConsec().get(0).getNombre(),ant)); 
                        }
                        elem.add(this.elementosLALR.get(this.elementosLALR.size()-1));
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
    
    public ArrayList<String> calcularAnt(ElementosLALR elemento){
        ArrayList<String> ant = new ArrayList();
        ArrayList<Simbolo> consec = elemento.getProduc().getConsec();
        ArrayList<NoTerminal> nterm = this.gramatica.getNoTerm(); 
        int posicion = elemento.getPosicion();      
        if(posicion < consec.size()-1){
            Simbolo simbolo = consec.get(posicion+1);
            if(this.gramatica.isTerminal(simbolo.getNombre())){
                ant.add(simbolo.getNombre());
                return ant;
            }else{
                int j=0;
                while(j < nterm.size()){
                    if(nterm.get(j).getNombre().equals(simbolo.getNombre())){
                        int k=0;
                        while(k < nterm.get(j).getPrimeros().size()){
                            ant.add(nterm.get(j).getPrimeros().get(k).getNombre());
                            k++;
                        }
                        break;
                    }else{
                        j++;
                    }
                }
                return ant;
            }
        }else
            return null;                
    }
    
    public void agrupamos(ArrayList<ElementosLALR> elementos){        
        int i=1;
        ArrayList<ElementosLALR> elementosLR = new ArrayList();
        ElementosLALR iguales = new ElementosLALR();
        elementosLR.add(elementos.get(0));
        while(i < elementos.size()){            
            ElementosLALR el = elementos.get(i);
            int j=0;            
            while(j < elementosLR.size()){
                iguales = null;
                if(elementosLR.get(j).getPosicion() == el.getPosicion() && el.getProduc().getConsec().size() == elementosLR.get(j).getProduc().getConsec().size()){                                 
                    int x=0;     
                    int ig=0;
                    while(x < el.getProduc().getConsec().size()){                          
                        if(!elementosLR.get(j).getProduc().getConsec().get(x).getNombre().equals(el.getProduc().getConsec().get(x).getNombre())){                                                          
                            break;
                        }else{
                            x++;
                            ig=1;
                        }
                    }
                    if(ig==1)
                        iguales = elementosLR.get(j);
                }
                if(iguales == null)
                    j++;
                else
                    break;
            }                                     
            if(iguales == null){                    
                elementosLR.add(el);
            }else{                 
                ArrayList<String> ant = new ArrayList();
                int k=0;
                while(k < el.getAnticipacion().size()){
                    ant.add(el.getAnticipacion().get(k));
                    k++;
                }                                                                   
                int l=0;           
                while(l < iguales.getAnticipacion().size()){                        
                    ant.add(iguales.getAnticipacion().get(l));
                    l++;                        
                }                                    
                int m=1;
                ArrayList<String> ant2 = new ArrayList();
                ant2.add(ant.get(0));
                while(m < ant.size()){                    
                    int n=0;
                    int enc = 0;
                    while(n < ant2.size()){
                        if(ant.get(m).equals(ant.get(n))){
                            enc = 1;
                            break;
                        }else
                            n++;                        
                    }
                    if(enc == 0)
                        ant2.add(ant.get(m));
                    m++;                    
                }
                elementosLR.set(j, new ElementosLALR(el.getProduc(),el.getPosicion(), el.getPivote(), ant2));               
            } 
            i++;
        }
        this.setElementosLALR(elementosLR);        
    }

    public ArrayList<ElementosLALR> getElementosLALR() {
        return elementosLALR;
    }

    public void setElementosLALR(ArrayList<ElementosLALR> elementosLALR) {
        this.elementosLALR = elementosLALR;
    }   

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }         
}