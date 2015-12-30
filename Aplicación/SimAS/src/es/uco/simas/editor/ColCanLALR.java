//SimAS / Editor
// ColCanLALR

package es.uco.simas.editor;

import es.uco.simas.util.gramatica.Gramatica;
import es.uco.simas.util.gramatica.Simbolo;
import java.util.ArrayList;

/**
 * @author vanesa
 */
public class ColCanLALR {
    Gramatica gramatica;
    ArrayList<ConjElementosLR1> conjElementosLALR = new ArrayList();      
    ArrayList<ConjElementosLR1> conj2 = new ArrayList(); 
    StringBuffer coleccion = new StringBuffer();
    ColCanLR1 colLR1 = new ColCanLR1();    
    int i;    
    int v;           
    public ColCanLALR(){ //Constructor vacio
        
    }
    
    public ColCanLALR(Gramatica gramatica, ColCanLR1 col){       
        this.gramatica = gramatica;       
        this.colLR1 = col;
    }
    
    public void construir(){ //Construir la coleccion
        ArrayList<ConjElementosLR1> conjLR1 = this.colLR1.getConjElementosLR1();
        unificarConjuntos();
        
        ArrayList<ConjElementosLR1> c = colLR1.getConj2();
        int w=0;
        while(w < c.size()){
            int z=0;
            while(z < this.conjElementosLALR.size()){
                if(c.get(w).getI() == this.conjElementosLALR.get(z).getI()){
                    c.get(w).setV(this.conjElementosLALR.get(z).getV());
                    conj2.add(c.get(w));
                }
                z++;
            }            
            w++;
        }
        
        
        int k=0;
        
        while(k < this.conjElementosLALR.size()){
            this.conjElementosLALR.get(k).setY(k);
            coleccion.append("I").append(this.conjElementosLALR.get(k).getI());
            if(this.conjElementosLALR.get(k).getV() != -1){
                coleccion.append("-").append(this.conjElementosLALR.get(k).getV());
            }            
            coleccion.append(" = ");
            imprimirConjunto(k);
            coleccion.append("\n\n");
            k++;            
        }      
    }
    
    public String getColeccion(){ //Devuelve la coleccion
        return this.coleccion.toString();
    }  

    public void unificarConjuntos(){  //Unifica los conjuntos
        ArrayList<ConjElementosLR1> conjLR1 = this.colLR1.getConjElementosLR1();
        int x=0;        
        while(x < conjLR1.size()){
            int prueba = comprobarConjunto(conjLR1.get(x));            
            if(prueba == -1){                    
                this.conjElementosLALR.add(conjLR1.get(x));                  
            }else{                               
                anticipacion(prueba, x);                                
            }
            x++;
        }
    }
    
    public int comprobarConjunto(ConjElementosLR1 conj){
        int conjunto = -1;
        int i = 1;        
        while(i < this.conjElementosLALR.size()){
            int j=0;
            int iguales = 0;
            while(j < conj.getElementosLR1().size()){                
                if(elementosIguales(conj.getElementosLR1().get(j), this.conjElementosLALR.get(i).getElementosLR1().get(j))==1){                    
                    j++;
                    iguales = 1;
                }else{
                    iguales =0;
                    break;
                }                    
            }
            if(iguales == 1){                
                conjunto = i;
                this.conjElementosLALR.get(i).setV(conj.getI());
                ConjElementosLR1 el = conj;
                el.setI(this.conjElementosLALR.get(i).getI());
                el.setV(this.conjElementosLALR.get(i).getV());                
                this.conj2.add(el);
                break;
            }else
                i++;
        }
        return conjunto;        
    }
    
    public int elementosIguales(ElementosLR1 el1, ElementosLR1 el2){
        int i=0;
        int iguales =-1;
         
            if(el1.getPosicion() == el2.getPosicion() && el1.getProduc().getAntec().getSimboloNT().getNombre().equals(el2.getProduc().getAntec().getSimboloNT().getNombre())){
                while(i < el1.getProduc().getConsec().size()){               
                    iguales = 0;
                    if(el1.getProduc().getConsec().get(i).getNombre().equals(el2.getProduc().getConsec().get(i).getNombre())){
                        i++;
                    }else{
                        iguales =0;
                        break;
                    }
                    iguales = 1;    
                }
            }          
        return iguales;
    }

    void anticipacion(int conjunto, int igual){        
        ConjElementosLR1 conj = this.conjElementosLALR.get(conjunto);
        ConjElementosLR1 conjIgual = this.colLR1.getConjElementosLR1().get(igual);
        ArrayList<String> anticipacion1 = conj.getElementosLR1().get(0).getAnticipacion();
        ArrayList<String> anticipacion2 = conjIgual.getElementosLR1().get(0).getAnticipacion();
        ArrayList<String> anticipacion = anticipacion1;
        int k=0;                
        
        while(k < anticipacion2.size()){
            int l=0;
            int encontrado = -1;
            while(l < anticipacion.size()){
                if(anticipacion.get(l).equals(anticipacion2.get(k)))
                    encontrado = 1;                                      
                l++;
            }
            if(encontrado == -1){
                anticipacion.add(anticipacion2.get(k));
            }
            k++;
        }                    
        k=0;
        while(k < this.conjElementosLALR.get(conjunto).getElementosLR1().size()){
            this.conjElementosLALR.get(conjunto).getElementosLR1().get(k).setAnticipacion(anticipacion);
            k++;      
        }                
    }
     
    void imprimirConjunto(int conj){
        int j=0;
        ArrayList<ElementosLR1> elementos = this.conjElementosLALR.get(conj).getElementosLR1();
        coleccion.append("{ ");  
                        
        while(j < elementos.size()){
            coleccion.append("[");
            ArrayList<Simbolo> consec= elementos.get(j).getProduc().getConsec();
            int posicion = elementos.get(j).getPosicion();
            int k=0;
            coleccion.append(elementos.get(j).getProduc().getAntec().getSimboloNT().getNombre());
            coleccion.append(" \u2192 ");
            if(posicion==0){
                coleccion.append(" \u25CF  ");
            }
            if("\u03B5".equals(consec.get(0).getNombre())){ //Si el consecuente contiene epsilon solo se imprime el punto
                coleccion.append(" \u25CF  ");
            }else{
                while(k < consec.size() ){               
                    coleccion.append(consec.get(k).getNombre()).append(" ");
                    k++;
                    if(posicion == k){
                        coleccion.append(" \u25CF  ");
                    }                
                }
            }
            int l=0;
                coleccion.append(", ");                
                while(l < elementos.get(j).getAnticipacion().size()){
                    coleccion.append(elementos.get(j).getAnticipacion().get(l));
                    l++;
                    if(l < elementos.get(j).getAnticipacion().size())
                        coleccion.append(", ");                    
            }
            coleccion.append("]");
            j++;
            if(j == elementos.size()){                
                coleccion.append(" }");
            }else
                coleccion.append(", ");
        }
    }

    public ColCanLR1 getColLR1() {
        return colLR1;
    }

    public ArrayList<ConjElementosLR1> getConj2() {
        return conj2;
    }

    public ArrayList<ConjElementosLR1> getConjElementosLALR() {
        return this.conjElementosLALR;
    }
}