//SimAS  / editor
// ElementosLR0

package es.uco.simas.editor;

import es.uco.simas.util.gramatica.Produccion;

/**
 * @author vanesa
 */
public class ElementosLR0 {
    public Produccion produc;
    int posicion;
    String pivote;
    
    public ElementosLR0(Produccion produc, int posicion, String pivote){
        this.produc = produc;
        this.posicion = posicion;
        this.pivote = pivote;
    }

    public Produccion getProduc() {
        return produc;
    }

    public void setProduc(Produccion produc) {
        this.produc = produc;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getPivote() {
        return pivote;
    }

    public void setPivote(String pivote) {
        this.pivote = pivote;
    }        
}
