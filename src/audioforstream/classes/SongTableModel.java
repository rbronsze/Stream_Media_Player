/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioforstream.classes;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Polack
 */
public class SongTableModel extends AbstractTableModel{
    
    private Object[][] data;
    private String[] title;
    
    public SongTableModel(Object[][] data, String[] title){
        this.data = data;
        this.title = title;
    }

    public Object[][] getData() {
        return data;
    }
    
//    public Object[][] getStringData() {
//        
//        int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
//        Object temp[][] = new Object[nbRow][nbCol-1];
//        
//        for(int row = 0; row < nbRow; row++){
//            for(int col = 0; col < nbCol-1; col++){
//                temp[][] = data[][];
//            }
//        }
//        
//        return data;
//    }

    @Override
    public int getRowCount() {
        return this.data.length;
    }

    @Override
    public int getColumnCount() {
        return this.title.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.data[rowIndex][columnIndex];
    }
    
    public String getColumnName(int columnIndex) {
        return this.title[columnIndex];
    }
    
    public void addRow(Object[] data){
      int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
      Object temp[][] = this.data;
      this.data = new Object[nbRow+1][nbCol];
      for(Object[] value : temp)
         this.data[indice++] = value;
       
      
      this.data[indice] = data;
      temp = null;
      //Cette méthode permet d'avertir le tableau que les données
      //ont été modifiées, ce qui permet une mise à jour complète du tableau
      //this.fireTableDataChanged();
      this.fireTableRowsInserted(nbRow, nbRow);
   }
    
    public void removeRow(int position){
       
      int indice = 0, indice2 = 0;
      int nbRow = this.getRowCount()-1, nbCol = this.getColumnCount();
      Object temp[][] = new Object[nbRow][nbCol];
       
      for(Object[] value : this.data){
         if(indice != position){
            temp[indice2++] = value;
         }
         indice++;
      }
      this.data = temp;
      temp = null;
      //Cette méthode permet d'avertir le tableau que les données
      //ont été modifiées, ce qui permet une mise à jour complète du tableau
      this.fireTableRowsDeleted(position, position);
    }
    
    public void removeAll(){
        
        Object[][] temp = new Object[0][4];
        
        this.data = temp;
        temp = null;
        
        this.fireTableDataChanged();
        
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
//        if(columnIndex != 3){
//            return true;
//        }
        return false;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        data[rowIndex][columnIndex] = aValue;
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
}
