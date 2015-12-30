//SimAS  / Simulador
//Mi Render

package es.uco.simas.simulador;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Vanesa
 */
public class MiRender extends DefaultTableCellRenderer{
   
   public Component getTableCellRendererComponent(JTable table,
      Object value,
      boolean isSelected,
      boolean hasFocus,
      int row,
      int column
      )
   {
      Component cell = super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
      if (value != null && column !=  0 && value.toString().startsWith("E") && !value.toString().startsWith("Emp")) {         
         cell.setForeground(Color.RED);
      } else {
         cell.setForeground(Color.BLACK);
      }
      if(value != null){
        if(value == "Error" || value.toString().contains("conf"))
            cell.setForeground(Color.RED);
      }  
        if(value == "Emparejar")
            cell.setForeground(Color.BLUE);
        if(value == "Aceptar")
            cell.setForeground(Color.GREEN);
        if(value != null && column !=  0 && value.toString().startsWith("d"))
            cell.setForeground(Color.BLUE);
        if(value != null && column !=  0 && value.toString().startsWith("r"))
            cell.setForeground(Color.MAGENTA);
        if(value != null && column !=  0 && value.toString().contains("*"))
            cell.setForeground(Color.GRAY);
            
      return cell;
   }
}