// SimAS  /  Editor
//FuncionError

package es.uco.simas.editor;

import es.uco.simas.util.gramatica.Terminal;
/**
 * @author vanesa
 */
public class FuncionError {
    
    public  static  final  int INSERTAR_ENTRADA =1;
    public  static  final  int BORRAR_ENTRADA =2;
    public  static  final  int MODIFICAR_ENTRADA =3;
    public  static  final  int INSERTAR_PILA =4;
    public  static  final  int BORRAR_PILA =5;
    public  static  final  int MODIFICAR_PILA =6;
    public  static  final  int TERMINAR_ANALISIS =7;
    private  int identificador ;
    private  int accion ;
    private  String mensaje ;
    private  Terminal simbolo ;

    public FuncionError(int id, int acc, String mensaje){
        this.identificador = id;
        this.accion = acc;
        this.mensaje = mensaje;            
    }
    public FuncionError(){
        
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getAccion() {
        return accion;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public Terminal getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(Terminal simbolo) {
        this.simbolo = simbolo;
    }  
}