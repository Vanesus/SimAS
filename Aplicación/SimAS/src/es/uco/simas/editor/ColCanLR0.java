//SimAS  /  Editor
// ColCanLR0

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
public class ColCanLR0 {
    ArrayList<ConjElementosLR0> conjElementosLR0 = new ArrayList(); 
    ArrayList<ConjElementosLR0> conj2 = new ArrayList(); 
    Gramatica gramatica;
    int i;
    StringBuffer coleccion = new StringBuffer();
    
    //\u00b7 punto
    public ColCanLR0(){ //Constructor vacio
        
    }
    
    public ColCanLR0(Gramatica gramatica){    
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
        this.conjElementosLR0.add(new ConjElementosLR0(this.gramatica));
        
        ArrayList<ElementosLR0> elementos = this.conjElementosLR0.get(this.i).getElementosLR0();
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
            this.conjElementosLR0.add(new ConjElementosLR0(i,0,simbolos.get(j), conjElementosLR0.get(0),this.gramatica));
            j++;
            coleccion.append("\n\n");
            coleccion.append("Ir_a (I"+this.conjElementosLR0.get(j).col+", "+this.conjElementosLR0.get(j).simbolo+") = ");        
            imprimirConjunto(j);
            coleccion.append(" = I"+ this.conjElementosLR0.get(j).i);
        }               
        
        int tam = this.conjElementosLR0.size();        
        int m = 1;
        while(m < this.conjElementosLR0.size()){             
            elementos = this.conjElementosLR0.get(m).getElementosLR0();
            simbolos = new ArrayList();
            j=0;
            while(j < elementos.size()){                
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
                        if(encontrado == 0){
                            simbolos.add(elementos.get(j).getPivote());                            
                        }
                    }                
                }
                j++;
            }
            
            if(simbolos.size()==0){
                coleccion.append("\n\n\u2200 X \u2208  V:    Ir_a (I"+this.conjElementosLR0.get(m).getI()+", X) = \u2205");
            }
            j=0;            
            while(j < simbolos.size()){                
                this.i++;
                int imprimir=-1;
                ConjElementosLR0 conj = new ConjElementosLR0(i,m,simbolos.get(j), conjElementosLR0.get(m),this.gramatica);

                int prueba = this.comprobarConjunto(conj);                
                if(prueba == -1){
                    this.conjElementosLR0.add(conj);
                    imprimir = this.i;
                }else{
                    conj = new ConjElementosLR0(prueba,m,simbolos.get(j));
                    this.conj2.add(conj);
                   // conj = this.conjElementosLR0.get(prueba);
                    conj.setI(this.conjElementosLR0.get(prueba).getI());
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
        ArrayList<ElementosLR0> elementos = this.conjElementosLR0.get(conj).getElementosLR0();
        coleccion.append("{ ");               
        
        while(j < elementos.size()){             
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
            j++;
            if(j == elementos.size())
                coleccion.append(" }");
            else
                coleccion.append(", ");
        }
    }
    
    public String getColeccion(){
        return this.coleccion.toString();
    }        
        
    public int comprobarConjunto(ConjElementosLR0 conj){
        int conjunto = -1;
        int i = 1;
        
        while(i < this.conjElementosLR0.size()){            
           // System.out.println(this.conjElementosLR0.get(i).getElementosLR0().get(0).getProduc().getConsec().get(0).getNombre());
            int j=0;
            int iguales = 0;
            if(conj.getElementosLR0().size() == this.conjElementosLR0.get(i).getElementosLR0().size()){
                while(j < conj.getElementosLR0().size()){ 
                
                    if(elementosIguales(conj.getElementosLR0().get(j), this.conjElementosLR0.get(i).getElementosLR0().get(j))==1){
                        j++;
                        iguales = 1;
                    }else{
                        iguales =0;
                        break;
                    } 
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
    
    public int elementosIguales(ElementosLR0 el1, ElementosLR0 el2){        
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

    public ArrayList<ConjElementosLR0> getConjElementosLR0() {
        return conjElementosLR0;
    }

    public ArrayList<ConjElementosLR0> getConj2() {
        return conj2;
    }            
}