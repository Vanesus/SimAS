//SimAS  / Editor
// ColCanLR1

package es.uco.simas.editor;

import es.uco.simas.util.gramatica.Antecedente;
import es.uco.simas.util.gramatica.Consecuente;
import es.uco.simas.util.gramatica.Gramatica;
import es.uco.simas.util.gramatica.NoTerminal;
import es.uco.simas.util.gramatica.Produccion;
import es.uco.simas.util.gramatica.Simbolo;
import java.util.ArrayList;

/**
 * @author vanesa
 */
public class ColCanLR1 {
    Gramatica gramatica;
    ArrayList<ConjElementosLR1> conjElementosLR1 = new ArrayList();
    ArrayList<ConjElementosLR1> conj2 = new ArrayList();
    StringBuffer coleccion = new StringBuffer();
    int i;
    
    public ColCanLR1(){ //Constructor vacio
        
    }
    public ColCanLR1(Gramatica gramatica){ 
        this.gramatica = gramatica;
              
        ArrayList<Produccion> pr = this.gramatica.getPr();
        if(!pr.get(0).getAntec().getSimboloNT().getNombre().equals(this.gramatica.getSimbInicial()+"'")){
            Antecedente ant = new Antecedente();        
            ant.setSimboloNT(new NoTerminal(gramatica.getSimbInicial()+"'",gramatica.getSimbInicial()+"'"));
            Consecuente con = new Consecuente();
            ArrayList<Simbolo> simb = new ArrayList();
            simb.add(new Simbolo(gramatica.getSimbInicial(),gramatica.getSimbInicial()));
            con.setConjSimbolos(simb);
            Produccion produc = new Produccion();
            produc.setConsec(simb);
            produc.setAntec(ant);
            pr.add(0, produc);
            gramatica.setPr(pr);
            this.gramatica = gramatica;    
        }
    }
    
    public void construir(){
        ArrayList<String> simbolos = new ArrayList();             
        this.i=0;
        int j=0;
        
        coleccion.append("I").append(this.i).append(" = ");
        this.conjElementosLR1.add(new ConjElementosLR1(this.gramatica));
        
        ArrayList<ElementosLR1> elementos = this.conjElementosLR1.get(this.i).getElementosLR1();        
        while(j < elementos.size()){
            if(elementos.get(j).getPosicion() < elementos.get(j).getProduc().getConsec().size()){
                if(simbolos.size() ==0)
                    simbolos.add(elementos.get(j).getPivote());
                else{
                    int k=0;
                    int encontrado = 0;
                    while(k < simbolos.size()){
                        if(simbolos.get(k).equals(elementos.get(j).getPivote())){
                            encontrado = 1;                        
                            break;
                        }else 
                            k++;                        
                    }
                    if(encontrado == 0)
                        simbolos.add(elementos.get(j).getPivote());
                }
            }
            j++;        
        }                               
        imprimirConjunto(0);
        
        j=0;
        while(j < simbolos.size()){
            this.i++;                   
            this.conjElementosLR1.add(new ConjElementosLR1(i,0,simbolos.get(j), conjElementosLR1.get(0),this.gramatica));
            j++;
            coleccion.append("\n\n");
            coleccion.append(" Ir_a (I"+this.conjElementosLR1.get(j).col+", "+this.conjElementosLR1.get(j).simbolo+") = ");        
            imprimirConjunto(j);
            coleccion.append(" = I"+ this.conjElementosLR1.get(j).i);
        }               
        
        int tam = this.conjElementosLR1.size();

        int m =1;        
        while(m < this.conjElementosLR1.size()){                  
            elementos = this.conjElementosLR1.get(m).getElementosLR1();
            simbolos = new ArrayList();
            j=0;
            while(j < elementos.size()){  
                int posicion = elementos.get(j).getPosicion();
                if(elementos.get(j).getPivote() != ""){
                    if(simbolos.size() == 0 )
                            simbolos.add(elementos.get(j).getPivote());
                    else{
                        int k=0;
                        int encontrado = 0;
                        while(k < simbolos.size()){
                            if(simbolos.get(k).equals(elementos.get(j).getPivote())){
                                encontrado = 1;                        
                                break;
                            }else 
                                k++;                        
                        }
                        if(encontrado ==0)
                            simbolos.add(elementos.get(j).getPivote());
                    }                
                }
                j++;
            }
            
            if(simbolos.size()==0){
                coleccion.append("\n\n\u2200 X \u2208  V:    Ir_a (I"+this.conjElementosLR1.get(m).getI()+", X) = \u2205");
            }
            j=0;
            while(j < simbolos.size()){                
                this.i++;
                int imprimir=-1;
                ConjElementosLR1 conj = new ConjElementosLR1(i,m,simbolos.get(j), conjElementosLR1.get(m),this.gramatica);
                int prueba = this.comprobarConjunto(conj);                
                if(prueba == -1){
                    this.conjElementosLR1.add(conj);
                    imprimir = this.i;
                }else{
                    conj = new ConjElementosLR1(prueba,m,simbolos.get(j));
                    this.conj2.add(conj);
                   
                    conj.setI(this.conjElementosLR1.get(prueba).getI());
                    imprimir = prueba;
                    this.i--;
                }
                j++;
                coleccion.append("\n\n");
                coleccion.append("Ir_a (I"+conj.col+", "+conj.simbolo+") = ");        
                imprimirConjunto(imprimir);
                coleccion.append(" = I"+ conj.i);
            }                                     
            m++;
        }
        
    }
    
    void imprimirConjunto(int conj){
        int j=0;
        ArrayList<ElementosLR1> elementos = this.conjElementosLR1.get(conj).getElementosLR1();
        coleccion.append("{ ");  
                        
        while(j < elementos.size()){
            coleccion.append("[");
            ArrayList<Simbolo> consec= elementos.get(j).getProduc().getConsec();
            int posicion = elementos.get(j).getPosicion();
            int k=0;
            coleccion.append(elementos.get(j).getProduc().getAntec().getSimboloNT().getNombre());
            coleccion.append(" \u2192 ");
            if(posicion==0){
                coleccion.append(" \u25CF  ");//\u25AA ");
            }
            if("\u03B5".equals(consec.get(0).getNombre())){ //Si el consecuente contiene epsilon solo se imprime el punto
                coleccion.append(" \u25CF  ");//\u25AA");
            }else{
                while(k < consec.size() ){               
                    coleccion.append(consec.get(k).getNombre()).append(" ");
                    k++;
                    if(posicion == k){
                        coleccion.append(" \u25CF  ");//\u25AA ");//\u25CF ");//\u00b7 ");
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
    
     public String getColeccion(){
        return this.coleccion.toString();
    }  

    public ArrayList<ConjElementosLR1> getConjElementosLR1() {
        return conjElementosLR1;
    }

    public ArrayList<ConjElementosLR1> getConj2() {
        return conj2;
    }     
     
     public int comprobarConjunto(ConjElementosLR1 conj){
        int conjunto = -1;
        int i = 1;        
        while(i < this.conjElementosLR1.size()){
            int j=0;
            int iguales = 0;
            while(j < conj.getElementosLR1().size()){                
                if(elementosIguales(conj.getElementosLR1().get(j), this.conjElementosLR1.get(i).getElementosLR1().get(j))==1 && this.anticipacionIgual(conj.getElementosLR1().get(j), this.conjElementosLR1.get(i).getElementosLR1().get(j))==1){
                    j++;
                    iguales = 1;
                }else{
                    iguales =0;
                    break;
                }                    
            }
            if(iguales == 1){
                conjunto = i;
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
    
    public int anticipacionIgual(ElementosLR1 el1, ElementosLR1 el2){
        int iguales = -1;
        int j=0;
        
        if(el1.getAnticipacion().size() == el2.getAnticipacion().size()){                    
            while(j < el1.getAnticipacion().size()){                
                if(el1.getAnticipacion().get(j).equals(el2.getAnticipacion().get(j))){
                    j++;
                }else{
                    iguales = 0;
                    break;
                }
                iguales = 1;
            }
        }else{
            iguales = 0;
        }
        return iguales;
    }
}