/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package object;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author win_bmx
 */
public class ColorCellRenderer extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, 
            Object value,boolean isSelected, boolean hasFocus, int row, int column){
      // Color color = (Color) value;
        Color color = null;
        Couleurs c;
        //JOptionPane.showMessageDialog(null, value.getClass().getSimpleName());
        if(value.getClass().getSimpleName().equals("String")){
            c = Couleurs.getCouleur((String)value);
        }else{
            c = (Couleurs) value;
        }
        
        if(c == Couleurs.Blanc){
            color = Color.white;
        }else if(c == Couleurs.Bleu){
            color = Color.blue;
        }else if(c == Couleurs.Jaune){
            color = Color.yellow;
        }else if(c == Couleurs.Vert){
            color = Color.green;
        }
        setText("");
        setBackground(color);
        return this;
    }
}
