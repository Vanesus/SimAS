// SimAs  / Editor
// ConjElementosLR1

package es.uco.simas.editor;

import es.uco.simas.util.gramatica.Gramatica;
import es.uco.simas.util.gramatica.NoTerminal;
import es.uco.simas.util.gramatica.Produccion;
import es.uco.simas.util.gramatica.Simbolo;
import java.util.ArrayList;

/**
 * @author vanesa
 */
public class ConjElementosLR1 {
    ArrayList<ElementosLR1> elementosLR1 = new ArrayList(); 
    Gramatica gramatica;
    int i; // numero de iteracion
    int col; //coleccion con la que se calcula
    String simbolo = "";   
    ConjElementosLR1 conj;      
    int v=-1;
    int y;
    
    public ConjElementosLR1(){
        
    }
    
    public ConjElementosLR1(Gramatica gramatica){        
        this.gramatica = gramatica;
        this.primero();
    }
    
    public ConjElementosLR1(int i, int col, String simbolo, ConjElementosLR1 conj, Gramatica gr){
        this.i = i;
        this.col = col;
        this.simbolo = simbolo;
        this.conj = conj;        
        this.gramatica = gr;
        this.elementosLR1 = new ArrayList();         
        this.construir();
    }
    public ConjElementosLR1(int i, int col, String simbolo){
        this.i = i;
        this.col = col;
        this.simbolo = simbolo;
    }
    
    void primero(){        
        ArrayList<Produccion> producciones = this.gramatica.getPr();
        ArrayList<String> simb = new ArrayList();
        Produccion prod = producciones.get(0);
        ArrayList<String> ant = new ArrayList();
        ElementosLR1 aux = new ElementosLR1();
        ant.add("$");
        
        //S' -> .S, $
        this.elementosLR1.add(new ElementosLR1(prod,0,prod.getConsec().get(0).getNombre(), ant)); 
        aux = this.elementosLR1.get(this.elementosLR1.size()-1);
        
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
                if(producciones.get(i).getConsec().get(0).getNombre().equals("\u03b5")){
                    this.elementosLR1.add(new ElementosLR1(producciones.get(i),1, "",ant));  
                }else{ 
                    this.elementosLR1.add(new ElementosLR1(producciones.get(i),0, producciones.get(i).getConsec().get(0).getNombre(),ant));  
                }                
            }
            i++;
        }            
        i=1;        
        int tam = this.elementosLR1.size();        
        while (i < tam){            
            if(this.elementosLR1.get(i).getPosicion() != this.elementosLR1.get(i).getProduc().getConsec().size()){
                if(this.gramatica.isNoTerminal(this.elementosLR1.get(i).getProduc().getConsec().get(this.elementosLR1.get(i).getPosicion()).getNombre())){
                    int j=0;
                    simbolo = this.elementosLR1.get(i).getProduc().getConsec().get(this.elementosLR1.get(i).getPosicion()).getNombre();
                    aux = this.elementosLR1.get(i);
                    while(j < producciones.size()){
                        if(simbolo.equals(producciones.get(j).getAntec().getSimboloNT().getNombre())){
                            ant = new ArrayList();                        
                            if(calcularAnt(aux)!=null){
                                ant = calcularAnt(aux);                    
                            }else{
                                ant = aux.getAnticipacion();
                            }
                            if(producciones.get(j).getConsec().get(0).getNombre().equals("\u03b5")){
                                this.elementosLR1.add(new ElementosLR1(producciones.get(j),1, "", ant)); 
                            }else{
                                this.elementosLR1.add(new ElementosLR1(producciones.get(j),0, producciones.get(j).getConsec().get(0).getNombre(), ant)); 
                            }                             
                        }
                        j++;
                    }
                }
            }
            i++;
        }        
        if(tam != this.elementosLR1.size()){
            do{                                
                i=tam;
                tam = this.elementosLR1.size();
                while (i < tam){                    
                    if(this.elementosLR1.get(i).getPosicion() != this.elementosLR1.get(i).getProduc().getConsec().size()){
                        if(this.gramatica.isNoTerminal(this.elementosLR1.get(i).getProduc().getConsec().get(this.elementosLR1.get(i).getPosicion()).getNombre())){                            
                            int j=0;
                            simbolo = this.elementosLR1.get(i).getProduc().getConsec().get(this.elementosLR1.get(i).getPosicion()).getNombre();                            
                            aux = this.elementosLR1.get(i);
                            while(j < producciones.size()){                                
                                if(simbolo.equals(producciones.get(j).getAntec().getSimboloNT().getNombre())){
                                    ant = new ArrayList();                        
                                    if(calcularAnt(aux)!=null){
                                        ant = calcularAnt(aux);                    
                                    }else{
                                        ant = aux.getAnticipacion();
                                    }
                                    if(producciones.get(j).getConsec().get(0).getNombre().equals("\u03b5")){
                                        this.elementosLR1.add(new ElementosLR1(producciones.get(j),1, "", ant)); 
                                    }else{
                                        this.elementosLR1.add(new ElementosLR1(producciones.get(j),0, producciones.get(j).getConsec().get(0).getNombre(), ant)); 
                                    }                                     
                                }
                                j++;
                            }
                        }
                    }
                    i++;
                }
            }while(tam > this.elementosLR1.size());
        }                
        this.agrupamos(this.elementosLR1);                                           
    }
    
    void construir(){        
        ArrayList<ElementosLR1> elementos = this.conj.getElementosLR1();      
        ArrayList<String> simb = new ArrayList();
        ArrayList<Produccion> producciones = new ArrayList();
        producciones = this.gramatica.getPr();
        String simbolo = new String();
        ArrayList<String> ant = new ArrayList();
        ElementosLR1 aux = new ElementosLR1();
        ArrayList<ElementosLR1> elem = new ArrayList();        

        int j=0;             
        while(j<elementos.size()){                                            
            ElementosLR1 el = new ElementosLR1(elementos.get(j).getProduc(),elementos.get(j).getPosicion(),elementos.get(j).getPivote(), elementos.get(j).getAnticipacion());
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
                        this.elementosLR1.add(new ElementosLR1(el.getProduc(),el.getPosicion(), el.getPivote(), el.getAnticipacion()));
                        if(simb.size() !=0)
                            elem.add(this.elementosLR1.get(this.elementosLR1.size()-1));                                            
                    }
                }else{                            
                    el.setPivote("");
                    this.elementosLR1.add(new ElementosLR1(el.getProduc(),el.getPosicion(), el.getPivote(),el.getAnticipacion()));                                                                                                                      
                }                
            }
            j++;
        }       
            int prueba=0;            
        int k =0;
        if(simb.size() != 0){            
            while(k < simb.size()){                 
                simbolo = simb.get(k);                
                aux = elem.get(k);                
                ant = new ArrayList();  
                ArrayList<String> ant2 = new ArrayList();

                if(calcularAnt(aux)!=null){                   
                    ant = calcularAnt(aux);                    
                }else{                   
                    ant = aux.getAnticipacion();
                }                           
                int i=1;
                while(i<producciones.size()){                    
                    if(simbolo.equals(producciones.get(i).getAntec().getSimboloNT().getNombre())){
                        if(producciones.get(i).getConsec().get(0).getNombre().equals("\u03b5")){
                            this.elementosLR1.add(new ElementosLR1(producciones.get(i),1, "",ant));                             
                        }else{
                            this.elementosLR1.add(new ElementosLR1(producciones.get(i),0, producciones.get(i).getConsec().get(0).getNombre(),ant)); 
                        }                        
                        if(this.gramatica.isNoTerminal(producciones.get(i).getConsec().get(0).getNombre())){                            
                            if(prueba <1){
                                elem.add(this.elementosLR1.get(this.elementosLR1.size()-1));
                                simb.add(producciones.get(i).getConsec().get(0).getNombre());
                                prueba++;
                            }                           
                        }
                    }
                    i++;
                }
                k++;
            }
        }   
        this.agrupamos(this.elementosLR1);
    }
    
    public ArrayList<String> calcularAnt(ElementosLR1 elemento){//Produccion produccion, int posicion){
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
    
    public void agrupamos(ArrayList<ElementosLR1> elementos){        
        int i=1;
        ArrayList<ElementosLR1> elementosLR = new ArrayList();
        ElementosLR1 iguales = new ElementosLR1();
        elementosLR.add(elementos.get(0));
        while(i < elementos.size()){            
            ElementosLR1 el = elementos.get(i);
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
                elementosLR.set(j, new ElementosLR1(el.getProduc(),el.getPosicion(), el.getPivote(), ant2));               
            } 
            i++;
        }
        this.setElementosLR1(elementosLR);        
    }

    public ArrayList<ElementosLR1> getElementosLR1() {
        return elementosLR1;
    }

    public void setElementosLR1(ArrayList<ElementosLR1> elementosLR1) {
        this.elementosLR1 = elementosLR1;
    }   

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }    

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }            
}