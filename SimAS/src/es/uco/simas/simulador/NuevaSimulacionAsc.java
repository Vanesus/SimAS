//SimAS  /  Simulador
//Nueva Simulacion Ascendente

package es.uco.simas.simulador;

import com.itextpdf.text.DocumentException;
import es.uco.simas.editor.Editor;
import es.uco.simas.editor.FuncionError;
import es.uco.simas.util.gramatica.Gramatica;
import es.uco.simas.util.gramatica.Produccion;
import es.uco.simas.util.gramatica.Terminal;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 * @author vanesa
 */
public class NuevaSimulacionAsc extends javax.swing.JFrame {

    public Gramatica gramatica;
    public Simulador ventanaPadre;
    private DefaultTableModel tabla = new DefaultTableModel();    
    Stack<String> pila = new Stack<String>();    
    Stack<String> entrada = new Stack<String>();
    private String accion = "";

    public NuevaSimulacionAsc(Gramatica gramatica, Simulador ventanaPadre) {                      
        initComponents();
                      
        this.gramatica = gramatica;
        this.ventanaPadre = ventanaPadre;
        if(this.ventanaPadre.getMetodoAscendente()==0){
            this.jLabel1.setText("Simulación Ascendente SLR");
        }
        if(this.ventanaPadre.getMetodoAscendente()==1){
            this.jLabel1.setText("Simulación Ascendente LR-canónico");
        }
        if(this.ventanaPadre.getMetodoAscendente()==3){
            this.jLabel2.setText("Simulación Ascendente LALR");
        }
                
        if(this.ventanaPadre.getCadenaEntrada() != null){
            iniciarSimulacion();            
        }
        this.jTableSim.setModel(this.tabla);  
        this.jTableSim.setDefaultRenderer (Object.class, new MiRender());
    }
    
    public void actualizarVisualizacion(){
        String cadena ="";
        this.pila.removeAllElements();
        this.entrada.removeAllElements(); 
        this.tabla = new DefaultTableModel();
        this.jTableSim.setModel(this.tabla); 
        
        ArrayList<Terminal> cadenaEntrada = this.ventanaPadre.getCadenaEntrada();
        int i = 0;
        while (i < cadenaEntrada.size()){
            cadena = cadena+cadenaEntrada.get(i).getNombre()+" ";
            i++;
        }
        this.jTextField1.setText(cadena);   
        if(!cadena.equals("")){            
            this.iniciarSimulacion();
        }
    }
    
    private void iniciarSimulacion(){
        this.tabla.addColumn("Pila");
        this.tabla.addColumn("Entrada");
        this.tabla.addColumn("Acción");       
                        
        ArrayList<Terminal> cadEntrada = this.ventanaPadre.getCadenaEntrada();        
        int i=0;                
        this.entrada.push("$");
        i = cadEntrada.size()-1;
        while(i >= 0){            
            this.entrada.push(cadEntrada.get(i).getNombre());
            i--;
        }
        this.pila.push("0");
        
        this.accion = this.buscarTablaAccion(0, this.entrada.peek());        
        
        Object []  linea = new Object[]  {
            mostrarPila(this.pila),
            mostrarEntrada(this.entrada),
            accion,
            };
                
        this.tabla.addRow(linea);    
    }
    
    private String mostrarPila(Stack pila){        
        int i = 0;
        String str = "";
        while(i < pila.size()){
            str = str + " "+pila.get(i);
            i++;
        }        
        return str;
    }
    
    private String mostrarEntrada(Stack pila){        
        int i = pila.size()-1;
        String str = "";
        while(i >= 0){
            str = str + " "+pila.get(i);
            i--;
        }        
        return str;
    }
    
    private String buscarTablaAccion(int fila, String columna){        
        DefaultTableModel tablaAccion = this.gramatica.getTlr().getTAccion().getMatrizAccion();        
        int i=0;
        int fil = -1;
        int col = -1;
        Object celda = "";
        
        while(i < tablaAccion.getRowCount()){  
            if(tablaAccion.getValueAt(i, 0).toString().contains("-")){
                int a =tablaAccion.getValueAt(i, 0).toString().indexOf("-");
                int numero = Integer.parseInt(tablaAccion.getValueAt(i, 0).toString().substring(0, a));
                if(fila == numero){
                    fil = i;                
                    break;
                }else{
                    i++;
                }
            }else{
                if(fila == Integer.parseInt(tablaAccion.getValueAt(i, 0).toString())){
                    fil = i;                
                    break;
                }else{
                    i++;
                }  
            }
        }
        i = 1;
        while(i < tablaAccion.getColumnCount()){            
            if(columna.equals(tablaAccion.getColumnName(i))){
                col = i;
                break;
            }else{
                i++;
            }
        }        
        if(fil != -1 && col != -1){            
            celda = tablaAccion.getValueAt(fil, col);             
        }
        if(celda == null){
            return "Error";            
        }else{
            return celda.toString();
        }                          
    }
    
     private String buscarTablaIra(int fila, String columna){                
        DefaultTableModel tablaIra = this.gramatica.getTlr().getTIrA().getMatrizIrA();                
        DefaultTableModel tablaAccion = this.gramatica.getTlr().getTAccion().getMatrizAccion();        
        int i=0;
        int fil = -1;
        int col = -1;
        Object celda = "";
        
        while(i < tablaIra.getRowCount()){            
            if(tablaAccion.getValueAt(i, 0).toString().contains("-")){
                int a = tablaAccion.getValueAt(i, 0).toString().indexOf("-");
                int numero = Integer.parseInt(tablaAccion.getValueAt(i, 0).toString().substring(0, a));
                if(fila == numero){
                    fil = i;                
                    break;
                }else{
                    i++;
                }
            }else{            
                if(fila == Integer.parseInt(tablaAccion.getValueAt(i, 0).toString())){
                    fil = i;                
                    break;
                }else{
                    i++;
                }
            }
        }
        i = 0;
        while(i < tablaIra.getColumnCount()){            
            if(columna.equals(tablaIra.getColumnName(i))){
                col = i;
                break;
            }else{
                i++;
            }
        }        
        if(fil != -1 && col != -1){            
            celda = tablaIra.getValueAt(fil, col);             
        }
        if(celda == null){
            return "Error";            
        }else{
            return celda.toString();
        }                          
    }
    
    private void siguientePaso(){        
        int desp = -1;
        int red = -1;
        int i=0;
        ArrayList<Produccion> prod = this.gramatica.getPr();
        if(this.accion.equals("Aceptar") || this.accion.equals("Error") || this.accion.contains("conf")){
            this.jButton4.setEnabled(false);
        }else{
            if(this.accion.startsWith("d")){
                this.pila.push(this.entrada.peek());
                this.entrada.pop();
                //desp = Integer.parseInt(this.accion.substring(1, this.accion.length()-1));
                this.pila.push(this.accion.substring(1, this.accion.length()));                
            }
            if(this.accion.startsWith("r")){  
                int aux;
                
                if(this.accion.substring(2, 2)==" "){
                    aux=1;                      
                }else{        
                    aux=2;
                }               
                    
                red= Integer.parseInt(this.accion.substring(1, aux));
                Produccion pr = prod.get(red);
                
                if(pr.getConsec().get(0).getNombre().equals("\u03b5")){
                    this.pila.push(pr.getAntec().getSimboloNT().getNombre());                       
                    String num =(this.buscarTablaIra(Integer.parseInt(this.pila.get(this.pila.size()-2)), this.pila.get(this.pila.size()-1)));                
                    this.pila.push(num);
                }else{
                    this.pila.pop();
                    i=pr.getConsec().size()-1;
                    while(i >= 0){
                        if(pr.getConsec().get(i).getNombre().equals(this.pila.peek())){
                            this.pila.pop();
                            if(i > 0)
                                this.pila.pop();
                        }
                        i--;
                    }
                    this.pila.push(pr.getAntec().getSimboloNT().getNombre());                     
                   
                    String num= "";
                    if(this.pila.get(this.pila.size()-2).contains("-")){                        
                        int a =this.pila.get(this.pila.size()-2).indexOf("-");
                        int numero = Integer.parseInt(this.pila.get(this.pila.size()-2).substring(0, a));                
                        num =(this.buscarTablaIra(numero, this.pila.get(this.pila.size()-1)));                
                    }else{                                                                      
                        num =(this.buscarTablaIra(Integer.parseInt(this.pila.get(this.pila.size()-2)), this.pila.get(this.pila.size()-1)));                
                    }
                    this.pila.push(num);
                }
            }
            
            if(this.pila.peek().contains("-")){
                int a =this.pila.peek().indexOf("-");
                int numero = Integer.parseInt(this.pila.peek().substring(0, a));                
                this.accion = this.buscarTablaAccion(numero, this.entrada.peek());
            }else{                       
                this.accion = this.buscarTablaAccion(Integer.parseInt(this.pila.peek()), this.entrada.peek());
            }
            
            if(this.accion.startsWith("r")){
                Produccion pr = prod.get(Integer.parseInt(this.accion.substring(1, this.accion.length())));
                this.accion = this.accion + "   " + pr.getAntec().getSimboloNT().getNombre();
                this.accion = this.accion + " \u2192 ";
                i=0;
                while(i < pr.getConsec().size()){
                    this.accion = this.accion +" "+pr.getConsec().get(i).getNombre();
                    i++;
                }
            }
            
            if(this.accion.startsWith("E") && !this.accion.equals("Error")){                
                funError();
            }                
            
            Object []  linea = new Object[]  {
                mostrarPila(this.pila),
                mostrarEntrada(this.entrada),
                this.accion,
                };
            this.tabla.addRow(linea); 
        }        
    }
    
    public DefaultTableModel getTabla(){
        this.jButton5ActionPerformed(null);
        return this.tabla;
    }
    
    private void funError(){
        ArrayList<FuncionError> funError = this.gramatica.getTlr().getTAccion().getFunError();        
        FuncionError fun = new FuncionError();
        int i = 0;
        int num = Integer.parseInt(this.accion.substring(1));
        
        while(i < funError.size()){
            if(funError.get(i).getIdentificador() == num){
                fun = funError.get(i);
                break;                
            }else
                i++;
        }                
        
        if(fun.getAccion()==1){
            this.entrada.push(fun.getSimbolo().getNombre());
        }
        if(fun.getAccion()==2){
            this.entrada.pop();
        }
        if(fun.getAccion()==3){
            this.entrada.pop();
            this.entrada.push(fun.getSimbolo().getNombre());
        }
        if(fun.getAccion()==4){
            this.pila.push(fun.getSimbolo().getNombre());
        }
        if(fun.getAccion()==5){
            this.pila.pop();
        }
        if(fun.getAccion()==6){
            this.pila.pop();
            this.pila.push(fun.getSimbolo().getNombre());
        }
        if(fun.getAccion()==7){
            this.accion = "Fin";
        }        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSim = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Simulación Ascendente");
        setBackground(new java.awt.Color(233, 242, 242));

        jPanel1.setBackground(new java.awt.Color(233, 242, 242));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Simulación Ascendente");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Cadena de Entrada: ");

        jButton1.setText("Editar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/uco/simas/resources/primero.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/uco/simas/resources/anterior.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/uco/simas/resources/siguiente.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/uco/simas/resources/ultimo.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTableSim.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableSim);

        jButton6.setText("Cancelar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Informe de la Simulación");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(32, 32, 32)
                                        .addComponent(jButton2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton3)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton4)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton5)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton1)
                                        .addGap(19, 19, 19))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton7))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel1)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        DefaultTableModel tabla2 = new DefaultTableModel();  
        this.tabla = tabla2;
        this.jTableSim.setModel(this.tabla);
        this.pila.removeAllElements();
        this.entrada.removeAllElements();
        this.accion = "";
        this.iniciarSimulacion();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ArrayList<Produccion> prod = this.gramatica.getPr();
        if(this.jTableSim.getRowCount()<2){
            this.jButton3.setEnabled(false);
        }else{
            this.jButton5.setEnabled(true);
            this.jButton3.setEnabled(true);
            this.jButton2.setEnabled(true);
            this.jButton4.setEnabled(true);
            String pila = this.tabla.getValueAt(this.jTableSim.getRowCount()-2, 0).toString();
            String entrada = this.tabla.getValueAt(this.jTableSim.getRowCount()-2, 1).toString();        
            this.tabla.removeRow(this.jTableSim.getRowCount()-1);        

            String[] p = pila.split(" ");
            String[] e = entrada.split(" ");
            int i = 1;
            this.pila.removeAllElements();
            while(i < p.length ){
                this.pila.push(p[i]);
                i++;
            }
            i=e.length-1;
            this.entrada.removeAllElements();
            while(i > 0 ){
                this.entrada.push(e[i]);
                i--;
            }
            
            this.accion = this.buscarTablaAccion(Integer.parseInt(this.pila.peek()), this.entrada.peek());
                        
            if(this.accion.startsWith("r")){
                Produccion pr = prod.get(Integer.parseInt(this.accion.substring(1, this.accion.length())));
                this.accion = this.accion + "   " + pr.getAntec().getSimboloNT().getNombre();
                this.accion = this.accion + " \u2192 ";
                i=0;
                while(i < pr.getConsec().size()){
                    this.accion = this.accion +" "+pr.getConsec().get(i).getNombre();
                    i++;
                }
            }                                        
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.siguientePaso();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int conf = JOptionPane.showConfirmDialog(null, "¿ Desea salir ?", "Salir",JOptionPane.YES_NO_OPTION);

        if(conf==0)
          this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CadEntrada ent = new CadEntrada(this);
        ent.setVisible(true);
        ent.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int i=0;
        while(i < 500){
            if(!this.accion.equals("Aceptar") || !this.accion.equals("Error") || !this.accion.contains("conf")){
                this.siguientePaso();
                i++;
            }else
                break;
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Boolean resultado= null;
        FileNameExtensionFilter filtro= null;
        JFileChooser selector= null;
        
        if(this.jTextField1.getText().isEmpty()){            
            JOptionPane.showConfirmDialog(null, "La cadena de entrada no puede estar vacía", "Error",JOptionPane.CLOSED_OPTION);        
        }else{            
            JFileChooser chooser = new JFileChooser();
            selector=chooser;
            FileNameExtensionFilter extension = new FileNameExtensionFilter("Informes de simulacion Ascendente (.pdf)",new String[]
                {"pdf"});    
            filtro=extension;
            selector.setFileFilter(filtro);
            File fichero = new File("informeSimulacionAscCad.pdf");
            selector.setSelectedFile(fichero);
            if(selector.showSaveDialog(null)==0) {
                try {           
                    resultado = this.ventanaPadre.generarInforme(selector.getSelectedFile().toString());
                } catch (DocumentException ex) {
                    Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                }

               if(resultado.booleanValue())  {
                    StringBuilder JdecGenerated80 = new StringBuilder();               
                }  else  {
                    JOptionPane.showConfirmDialog(null,"El informe de la gramática no se puede generar hasta que la gramática esté validada.", "Informe de la gramática", JOptionPane.DEFAULT_OPTION);              
                }
            } 
        }                                          

    }//GEN-LAST:event_jButton7ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableSim;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
