//SimAS  /  Simulador
//Simulador

package es.uco.simas.simulador;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import es.uco.simas.centroayuda.AcercaDe;
import es.uco.simas.editor.Editor;
import es.uco.simas.editor.FuncionError;
import es.uco.simas.editor.TablaLR;
import es.uco.simas.editor.TablaPredictiva;
import es.uco.simas.util.gramatica.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 * @author vanesa
 */
public class Simulador extends javax.swing.JFrame {

    ArrayList<Terminal> cadenaEntrada = new ArrayList<>();
    int metodoSimulacion;
    int modoFuncionamiento;
    int metodoAscendente = -1;
    Gramatica gramatica;
    
    public Simulador(int i, Gramatica gramatica, int met) {
        
        this.setCadenaEntrada(null);
        this.metodoSimulacion = i;
        this.gramatica = gramatica;
        this.metodoAscendente = met;
        
        DefaultListModel model = this.gramatica.getProducciones();        
        DefaultListModel model2 = new DefaultListModel();       
        initComponents();        
        this.jTable3.setVisible(false);
        this.jTable2.removeAll();
        this.jTable3.removeAll();
        this.jTable2.setDefaultRenderer (Object.class, new MiRender());
        this.jTable3.setDefaultRenderer (Object.class, new MiRender());
                      
        if(this.metodoSimulacion == 2){
            if(this.metodoAscendente == 0){                
                this.jLabelSimulador.setText("Simulacion Ascendente SLR");
            }
            if(this.metodoAscendente == 1){
                this.jLabelSimulador.setText("Simulacion Ascendente LR-Canonico");
            }
            if(this.metodoAscendente == 2){
                this.jLabelSimulador.setText("Simulacion Ascendente LALR");
            }
            
            this.jTable3.setVisible(true);            
            this.jLabelTabla.setText("Tabla LR");
            this.jLabelAccion.setVisible(true);
            this.jLabelIra.setVisible(true);
            TablaLR tlr = this.gramatica.getTlr();
            this.jTable2.setModel(tlr.getTAccion().getMatrizAccion());                                    
            this.jTable3.setModel(tlr.getTIrA().getMatrizIrA());
            this.funError(tlr.getTAccion().getFunError());
        }
        if(this.metodoSimulacion == 1){
            this.jTable3.setVisible(false);
            this.jLabelSimulador.setText("Simulacion Descendente");
            this.jLabelTabla.setText("Tabla Predictiva");     
            this.jLabelAccion.setVisible(false);
            this.jLabelIra.setVisible(false);
            TablaPredictiva tpred = this.gramatica.getTPredictiva();            
            this.jTable2.setModel(tpred.getTabla());
            this.funError(tpred.getFunError());                       
        }            
        
        if(this.gramatica.getProducciones() != null){
            int j =0;
            Object obj;
            obj = "P {";
            model2.addElement(obj);
            while (j < model.size()){
                obj = "   "+(j+1)+")   "+model.getElementAt(j);
                model2.addElement(obj);
                j++;
            }
            obj ="}";
            model2.addElement(obj);
            this.jList1.setModel(model2);
        }        
    }
    
    void funError(ArrayList<FuncionError> funError){
        StringBuilder string = new StringBuilder();
        DefaultListModel lista = new DefaultListModel();
            int j= 0;

            while(j < funError.size()) { 
                string = new StringBuilder();
                int accion;
                
                string = string.append(funError.get(j).getIdentificador());
                string = string.append(" - ");
                accion = funError.get(j).getAccion();
                if(accion == 1)
                    string.append("Insertar un Símbolo en la Entrada: ");
                if(accion == 2)
                    string.append("Borrar un Símbolo de la Entrada");
                if(accion == 3)
                    string.append("Modificar un Símbolo de la Entrada: ");
                if(accion == 4)
                    string.append("Insertar un Símbolo de la Pila: ");
                if(accion == 5)
                    string.append("Borrar un Símbolo de la Pila");
                if(accion == 6)
                    string.append("Modificar un Símbolo de la Pila: ");
                if(accion == 7)
                    string.append("Terminar el análisis");
                if(accion == 1 || accion ==3 || accion ==4 || accion ==6)
                    string.append(funError.get(j).getSimbolo().getNombre());

                lista.addElement(string);                   
                j++;            
            }
            this.jList2.setModel(lista);
    }
    
    public Boolean generarInforme(String fichero) throws DocumentException{
        try {
            String font = "fonts/arialuni.ttf";
            com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.LETTER , 45, 45, 54, 45);
            Image imagen = Image.getInstance("./src/es/uco/simas/resources/logo2Antes.png");
            imagen.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            imagen.scalePercent(40);

            LineSeparator ls = new LineSeparator();
             BaseFont bf;
             bf = BaseFont.createFont(font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
             
             Font titulo = new Font(bf, 21, Font.BOLD);
             Font font2 = new Font(bf, 15, Font.BOLD);
             Font font3 = new Font(bf, 12);
             Font font4 = new Font(bf, 12);
             Font font5 = new Font(bf, 13, Font.BOLD);
             BaseColor claro = new BaseColor(63,171,160);
             Font tabla1 = new Font(bf, 12, Font.BOLD);
             Font azul = new Font(bf, 12);
             Font rojo = new Font(bf, 12);
             Font verde = new Font(bf, 12);
             Font magenta = new Font(bf, 12);

             rojo.setColor(BaseColor.RED);
             azul.setColor(BaseColor.BLUE);
             verde.setColor(BaseColor.GREEN);
             magenta.setColor(BaseColor.MAGENTA);
             titulo.setColor(33, 77, 72);
             font2.setColor(BaseColor.BLACK);              
             font3.setColor(43,102,95);
             ls.setLineWidth(1);
             ls.setLineColor(claro);

             Paragraph parrafo = new Paragraph();
             Paragraph met = new Paragraph();

            if(this.metodoSimulacion == 1)
                parrafo = new Paragraph("     INFORME DE LA SIMULACION DESCENDENTE", titulo);
            if(this.metodoSimulacion == 2){                
                parrafo = new Paragraph("      INFORME DE LA SIMULACION ASCENDENTE", titulo);
                if(this.metodoAscendente == 0)
                    met = new Paragraph("                         METODO SLR         ", titulo);
                if(this.metodoAscendente == 1)
                    met = new Paragraph("                      METODO LR-CANONICO         ", titulo);
                if(this.metodoAscendente == 2)
                    met = new Paragraph("                         METODO LALR         ", titulo);
            }
                           
            int i =0;
            int j =0;
            Paragraph parrafo1 = new Paragraph("\n Producciones de la gramática: ", font2);

            DefaultListModel produc = this.gramatica.getProducciones();
            DefaultListModel produc2 = new DefaultListModel();
            i =0;
            Object obj;
            obj = "P {";
            produc2.addElement(obj);
            while (i < produc.size()){
                obj = "   "+(i+1)+")   "+produc.getElementAt(i);
                produc2.addElement(obj);
                i++;
            }
            obj ="}";
            produc2.addElement(obj);                    

            i=0;
            while(i<produc2.getSize()){

                parrafo1.add(new Paragraph("    "+produc2.getElementAt(i).toString(), font4));
                i++;
            }
            Paragraph parrafo2 = new Paragraph("\n Conjunto Primero y Siguiente: \n\n", font2);                
            PdfPTable table = new PdfPTable(3);   
                                    
            PdfPCell celda =new PdfPCell (new Paragraph("Símbolos", tabla1));
            table.addCell(celda);
            celda =new PdfPCell (new Paragraph("Conjunto Primero", tabla1));
            table.addCell(celda);
            celda =new PdfPCell (new Paragraph("Conjunto Siguiente", tabla1));
            table.addCell(celda);

            i=0;
            while(i < this.gramatica.getNoTerm().size()){
                String primeros = "";
                String siguientes = "";
                celda =new PdfPCell (new Paragraph(this.gramatica.getNoTerm().get(i).getNombre(), font4));
                table.addCell(celda);
                j=0;
                while(j < this.gramatica.getNoTerm().get(i).getPrimeros().size()){
                    primeros = primeros +" "+ this.gramatica.getNoTerm().get(i).getPrimeros().get(j).getNombre();
                    j++;
                }
                celda =new PdfPCell (new Paragraph(primeros, font4));
                table.addCell(celda);
                j=0;
                while(j < this.gramatica.getNoTerm().get(i).getSiguientes().size()){
                    siguientes = siguientes +" "+ this.gramatica.getNoTerm().get(i).getSiguientes().get(j).getNombre();
                    j++;
                }
                celda =new PdfPCell (new Paragraph(siguientes, font4));
                table.addCell(celda);
                i++;
            }
                        
            Paragraph parrafo3 = new Paragraph("\n Funciones de Error: ", font2);                
            if(this.gramatica.getTPredictiva().getFunError().size() == 0)
                parrafo3.add(new Paragraph("No se han declarado Funciones de Error.", font4));
            else{
                ArrayList<FuncionError> funError = this.gramatica.getTPredictiva().getFunError();
                StringBuilder string = new StringBuilder();
                j= 0;

                while(j < funError.size()) { 
                    string = new StringBuilder();
                    int accion;

                    string = string.append(funError.get(j).getIdentificador());
                    string = string.append(" - ");
                    accion = funError.get(j).getAccion();
                    if(accion == 1)
                        string.append("Insertar un Símbolo en la Entrada: ");
                    if(accion == 2)
                        string.append("Borrar un Símbolo de la Entrada");
                    if(accion == 3)
                        string.append("Modificar un Símbolo de la Entrada: ");
                    if(accion == 4)
                        string.append("Insertar un Símbolo de la Pila: ");
                    if(accion == 5)
                        string.append("Borrar un Símbolo de la Pila");
                    if(accion == 6)
                        string.append("Modificar un Símbolo de la Pila: ");
                    if(accion == 7)
                        string.append("Terminar el análisis");
                    if(accion == 1 || accion ==3 || accion ==4 || accion ==6)
                        string.append(funError.get(j).getSimbolo().getNombre());

                    parrafo3.add(new Paragraph("\n    "+string, font4));
                    j++;            
                }
            }
            Paragraph parrafo4 = new Paragraph();
            if(this.cadenaEntrada != null){
                parrafo4 = new Paragraph("\n Cadena de Entrada: ", font2);                
                i = 0;
                String str ="  ";
                while(i < this.cadenaEntrada.size()){
                    str = str +" "+this.cadenaEntrada.get(i).getNombre();
                    i++;
                }
                parrafo4.add(new Paragraph("  "+str, font4));
            }
            Paragraph parrafo5 = new Paragraph();
            PdfPTable table2 = new PdfPTable(1);
            Paragraph parrafo6 = new Paragraph();
            PdfPTable table3 = new PdfPTable(1);
            PdfPTable table4 = new PdfPTable(1);
            Paragraph col1 = new Paragraph("\n Coleccion Canonica Elementos LR(0) \n\n", font2);
            PdfPTable col2 = new PdfPTable(1);
            Paragraph accion = new Paragraph("   PARTE ACCION \n\n", font5);
            Paragraph ira = new Paragraph("     PARTE IR_A \n\n", font5);

            if(this.metodoSimulacion == 1){   // Metodo Descendente
                parrafo5 = new Paragraph("\n Tabla Predictiva: \n\n", font2);                
                DefaultTableModel tpredictiva = this.gramatica.getTPredictiva().getTabla();
                table2 = new PdfPTable(tpredictiva.getColumnCount());                                   
                i=0;
                while(i < tpredictiva.getColumnCount()){  
                    celda =new PdfPCell (new Paragraph(tpredictiva.getColumnName(i), tabla1));                    
                    table2.addCell(celda);                    
                    i++;
                }
                i =0;
                j =0;
                while(i < tpredictiva.getRowCount()){
                    j=0;
                    while(j < tpredictiva.getColumnCount()){                       
                        if(tpredictiva.getValueAt(i, j) == null){
                            table2.addCell("");                                                    
                        }else{                           
                            if(tpredictiva.getValueAt(i, j).toString().startsWith("<")){
                                celda =new PdfPCell (new Paragraph("Emparejar", azul));                            
                                table2.addCell(celda);                            
                            }else{
                                if(j==0)
                                    celda =new PdfPCell (new Paragraph(tpredictiva.getValueAt(i, j).toString(), tabla1));
                                else{
                                    if(tpredictiva.getValueAt(i, j).toString().startsWith("E"))
                                        celda =new PdfPCell (new Paragraph(tpredictiva.getValueAt(i, j).toString(), rojo));
                                    else
                                        celda =new PdfPCell (new Paragraph(tpredictiva.getValueAt(i, j).toString(), font4));
                                }    
                                table2.addCell(celda);  
                            }                           
                        }
                        j++;
                    }
                    i++;
                }
                if(this.cadenaEntrada != null){
                    parrafo6 = new Paragraph("\n Simulacion Descendente: \n\n", font2);                
                    DefaultTableModel sim = this.gramatica.getTPredictiva().getTabla();
                    NuevaSimulacionDesc nuevo = new NuevaSimulacionDesc(this.gramatica, this);
                    sim = nuevo.getTabla();
                    table3 = new PdfPTable(sim.getColumnCount());                                   
                    i=0;
                    while(i < sim.getColumnCount()){   
                        celda =new PdfPCell (new Paragraph(sim.getColumnName(i), tabla1));
                        table3.addCell(celda);
                        i++;
                    }

                    i =0;
                    j =0;
                    while(i < sim.getRowCount()){
                        j=0;
                        while(j < sim.getColumnCount()){   
                            celda =new PdfPCell (new Paragraph(sim.getValueAt(i, j).toString(), font4));
                            table3.addCell(celda);                                                                        
                            j++;
                        }
                        i++;
                    }
                }
            }
            if(this.metodoSimulacion == 2){  //Metodo ascendente
                parrafo5 = new Paragraph("\n Tabla LR: \n\n", font2);                
                DefaultTableModel taccion = this.gramatica.getTlr().getTAccion().getMatrizAccion();
                DefaultTableModel tira = this.gramatica.getTlr().getTIrA().getMatrizIrA();
                table2 = new PdfPTable(taccion.getColumnCount());                        
                table4 = new PdfPTable(tira.getColumnCount()+1); 
                if(this.metodoAscendente ==0)
                    celda =new PdfPCell (new Paragraph(this.gramatica.getColeccionLR0().getColeccion(), font4));    
                if(this.metodoAscendente ==1)
                    celda =new PdfPCell (new Paragraph(this.gramatica.getColeccionLR1().getColeccion(), font4));    
                col2.addCell(celda);                
                i=0;
                while(i < taccion.getColumnCount()){  //parte accion
                    celda = new PdfPCell (new Paragraph(taccion.getColumnName(i), tabla1));
                    table2.addCell(celda);
                    i++;
                }
                i=0;
                celda = new PdfPCell (new Paragraph(taccion.getColumnName(i), tabla1));
                table4.addCell(celda);
                i=0;
                while(i < tira.getColumnCount()){  // parte ir_a                   
                    celda =new PdfPCell (new Paragraph(tira.getColumnName(i), tabla1));
                    table4.addCell(celda);                   
                    i++;
                }
                i =0;
                j =0;
                while(i < taccion.getRowCount()){  //parte accion
                    j=0;
                    while(j < taccion.getColumnCount()){                       
                        if(taccion.getValueAt(i, j) == null){
                            table2.addCell("");                                                    
                        }else{                       
                            if(taccion.getValueAt(i, j).toString().equals("Aceptar")){
                                celda =new PdfPCell (new Paragraph("Aceptar", verde));                            
                                table2.addCell(celda);    
                            }else{
                                if(taccion.getValueAt(i, j).toString().startsWith("<")){
                                    celda =new PdfPCell (new Paragraph("Emparejar", azul));                            
                                    table2.addCell(celda);                            
                                }else{
                                    if(j==0)
                                        celda =new PdfPCell (new Paragraph(taccion.getValueAt(i, j).toString(), tabla1));
                                    else{
                                        if(taccion.getValueAt(i, j).toString().startsWith("E")){
                                            celda =new PdfPCell (new Paragraph(taccion.getValueAt(i, j).toString(), rojo));
                                        }else{
                                            if(taccion.getValueAt(i, j).toString().startsWith("d")){
                                                celda =new PdfPCell (new Paragraph(taccion.getValueAt(i, j).toString(), azul));
                                            }else{
                                                if(taccion.getValueAt(i, j).toString().startsWith("r")){
                                                    celda =new PdfPCell (new Paragraph(taccion.getValueAt(i, j).toString(), magenta));
                                                }else{    
                                                    if(taccion.getValueAt(i, j).toString().startsWith("conf")){
                                                        celda =new PdfPCell (new Paragraph(taccion.getValueAt(i, j).toString(), rojo));
                                                    }else{        
                                                        celda =new PdfPCell (new Paragraph(taccion.getValueAt(i, j).toString(), font4));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    table2.addCell(celda);                                      
                                }
                            }
                        }
                        j++;
                    }
                    i++;
                }                
                i =0;
                j =0;
                while(i < tira.getRowCount()){ // parte ir_a     
                    j=0;
                    while(j < tira.getColumnCount()){ 
                        if(j == 0){                            
                            if(taccion.getValueAt(i,j) != null){                                
                                celda =new PdfPCell (new Paragraph(taccion.getValueAt(i, j).toString(), font4));
                                table4.addCell(celda); 
                            }
                        }
                        if(tira.getValueAt(i, j) == null){
                            table4.addCell("");                                                    
                        }else{
                            if(j==0)
                                celda =new PdfPCell (new Paragraph(tira.getValueAt(i, j).toString(), tabla1));
                            else
                                celda =new PdfPCell (new Paragraph(tira.getValueAt(i, j).toString(), font4));

                            table4.addCell(celda); 

                        }                       
                        j++;
                    }
                    i++;
                }
                
                if(this.cadenaEntrada != null){
                    parrafo6 = new Paragraph("\n Simulacion Ascendente: \n\n", font2);                
                    DefaultTableModel sim = this.gramatica.getTPredictiva().getTabla();
                    NuevaSimulacionAsc nuevo = new NuevaSimulacionAsc(this.gramatica, this);
                    sim = nuevo.getTabla();
                    table3 = new PdfPTable(sim.getColumnCount());                                   
                    i=0;
                    while(i < sim.getColumnCount()){   
                        celda =new PdfPCell (new Paragraph(sim.getColumnName(i), tabla1));
                        table3.addCell(celda);
                        i++;
                    }

                    i =0;
                    j =0;
                    while(i < sim.getRowCount()){
                        j=0;
                        while(j < sim.getColumnCount()){   
                            celda =new PdfPCell (new Paragraph(sim.getValueAt(i, j).toString(), font4));
                            table3.addCell(celda);                                                                        
                            j++;
                        }
                        i++;
                    }
                }
            }
           try {

                PdfWriter.getInstance(document, new FileOutputStream(fichero));
           } catch (DocumentException | FileNotFoundException ex) {
               Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
           }           

            document.open();
           try {

            document.add(imagen);
            document.add(parrafo);
            if(this.metodoSimulacion == 2)
                document.add(met);
            
            document.add(new Chunk(ls));
            document.add(parrafo1);
            document.add(parrafo2);                              
            document.add(table);
            document.add(parrafo3); 
            document.add(parrafo4); 
            if(this.metodoSimulacion == 2){
                document.add(col1);
                document.add(col2);
            }
            document.add(parrafo5); 
            if(this.metodoSimulacion == 2)
                document.add(accion); 
            document.add(table2);
            if(this.metodoSimulacion == 2){
                document.add(ira); 
                document.add(table4);
            }
            document.add(parrafo6); 
            document.add(table3);

           } catch (DocumentException ex) {
               Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
           }

            document.close();
        } catch (BadElementException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
     
     return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabelSimulador = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jLabelTabla = new javax.swing.JLabel();
        jLabelAccion = new javax.swing.JLabel();
        jLabelIra = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Simulador");

        jPanel1.setBackground(new java.awt.Color(233, 244, 244));
        jPanel1.setToolTipText("");
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setFocusable(false);

        jLabelSimulador.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelSimulador.setText("jLabel1");

        jList1.setPreferredSize(new java.awt.Dimension(706, 770));
        jScrollPane1.setViewportView(jList1);

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setText("Gramática");

        jScrollPane2.setViewportView(jList2);

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel3.setText("Funciones de Error");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "", "", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable2);

        jButton1.setText("Modificar Funciones de Error");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(jTable3);

        jButton4.setText("Generar Informe");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jToolBar1.setRollover(true);

        jButton3.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButton3.setText("Simular");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);
        jToolBar1.add(jSeparator1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/uco/simas/resources/salir.png"))); // NOI18N
        jButton2.setToolTipText("Salir");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jLabelTabla.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabelTabla.setText("jLabel1");

        jLabelAccion.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabelAccion.setText("Parte Accion");

        jLabelIra.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabelIra.setText("Parte Ir_a");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(48, 48, 48)
                                        .addComponent(jLabelTabla)
                                        .addGap(109, 109, 109)
                                        .addComponent(jLabelAccion)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabelIra)
                                        .addGap(38, 38, 38))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel2)
                                .addGap(301, 301, 301)
                                .addComponent(jLabel3))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(283, 283, 283)
                .addComponent(jLabelSimulador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(113, 113, 113))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSimulador)
                    .addComponent(jButton4))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTabla))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelAccion)
                                    .addComponent(jLabelIra))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(136, Short.MAX_VALUE))
        );

        jMenu1.setText("Simulador");

        jMenuItem1.setText("Nueva Simulacion");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Salir");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        jMenuItem3.setText("Centro de Ayuda");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("Acerca de ...");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int conf = JOptionPane.showConfirmDialog(null, "¿Desea salir del simulador?", "Salir",JOptionPane.YES_NO_OPTION);

        if(conf==0) {           
            this.dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        if(this.metodoSimulacion == 1){            
            NuevaSimulacionDesc simDesc = new NuevaSimulacionDesc(this.gramatica, this);
            simDesc.setVisible(true);
            simDesc.setLocationRelativeTo(null);
        }
        if(this.metodoSimulacion == 2){            
            NuevaSimulacionAsc simAsc = new NuevaSimulacionAsc(this.gramatica, this);
            simAsc.setVisible(true);
            simAsc.setLocationRelativeTo(null);
        }    
       
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(this.metodoSimulacion == 1){
            new VentanaSimuladorDesc(this.gramatica).cambiarPaso(4);
            this.dispose();
        }
        if(this.metodoSimulacion == 2){
            new VentanaSimuladorAsc(this.gramatica).cambiarPaso(5);
            this.dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        Boolean resultado= null;
        FileNameExtensionFilter filtro= null;
        JFileChooser selector= null;
                           
        JFileChooser chooser = new JFileChooser();
        selector=chooser;
        FileNameExtensionFilter extension = new FileNameExtensionFilter("Informes de simulacion Ascendente (.pdf)",new String[]
            {"pdf"});    
        filtro=extension;
        selector.setFileFilter(filtro);
        File fichero= new File("");
        if(this.metodoSimulacion==1)
            fichero = new File("informeSimulacionDesc.pdf");
        if(this.metodoSimulacion==2)
            fichero = new File("informeSimulacionAsc.pdf");
        selector.setSelectedFile(fichero);
        if(selector.showSaveDialog(null)==0) {
            try {           
                resultado = this.generarInforme(selector.getSelectedFile().toString());
            } catch (DocumentException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            }

          if(resultado.booleanValue())  {
            StringBuilder JdecGenerated80 = new StringBuilder();

          }  else  {
              JOptionPane.showConfirmDialog(null,"El informe de la gramática no se puede generar hasta que la gramática esté validada.", "Informe de la gramática", JOptionPane.DEFAULT_OPTION);
          }
        }                    
    
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        AcercaDe acerca = new AcercaDe();
        acerca.setVisible(true);
        acerca.setLocationRelativeTo(null); 
    }//GEN-LAST:event_jMenuItem4ActionPerformed
    
    public Gramatica getGramatica(){
        return this.gramatica;
    }
    
    public ArrayList<Terminal> getCadenaEntrada() {
        return cadenaEntrada;
    }

    public void setCadenaEntrada(ArrayList<Terminal> cadenaEntrada) {
        this.cadenaEntrada = cadenaEntrada;
    }

    public int getMetodoSimulacion() {
        return this.metodoSimulacion;
    }

    public void setMetodoSimulacion(int metodoSimulacion) {
        this.metodoSimulacion = metodoSimulacion;
    }

    public int getModoFuncionamiento() {
        return modoFuncionamiento;
    }

    public void setModoFuncionamiento(int modoFuncionamiento) {
        this.modoFuncionamiento = modoFuncionamiento;
    }

    public int getMetodoAscendente() {
        return this.metodoAscendente;
    }

    public void setMetodoAscendente(int metodoAscendente) {
        this.metodoAscendente = metodoAscendente;
    }
    
    public String actualizarVisualizacion(){
        String cadena ="";
        ArrayList<Terminal> cadenaEntrada = this.getCadenaEntrada();
        int i = 0;
        while (i < cadenaEntrada.size()){
            cadena = cadena+cadenaEntrada.get(i).getNombre()+" ";
            i++;
        }
        return cadena;        
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelAccion;
    private javax.swing.JLabel jLabelIra;
    private javax.swing.JLabel jLabelSimulador;
    private javax.swing.JLabel jLabelTabla;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
