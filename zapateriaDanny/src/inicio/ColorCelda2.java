/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inicio;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author porra
 */
public class ColorCelda2 extends JTable{
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int ColumnIndex)
    {
        Component com= super.prepareRenderer(renderer, rowIndex, ColumnIndex);
        if(getValueAt(rowIndex,ColumnIndex).getClass().equals(Integer.class))
        {
            com.setBackground(Color.YELLOW);
            com.setForeground(Color.black);
            
        }
        return com;
        
        
    }
    
}