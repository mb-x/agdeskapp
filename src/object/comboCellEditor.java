/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package object;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

/**
 *
 * @author win_bmx
 */
public class comboCellEditor extends DefaultCellEditor{

    public comboCellEditor(Object obj[]) {
      
        super(new JComboBox(obj));
  
    }
    
}
