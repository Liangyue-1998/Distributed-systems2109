package Entry;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

 public class MyRender extends AbstractCellEditor implements TableCellRenderer,ActionListener, TableCellEditor{

private static final long serialVersionUID = 1L;
private JButton button =null;

JTable table;
public MyRender(JTable table,JButton button){
this.button=button; 
this.table=table;
}



@Override
public Object getCellEditorValue() {
// TODO Auto-generated method stub
return null;
}

@Override
public Component getTableCellRendererComponent(JTable table, Object value,
boolean isSelected, boolean hasFocus, int row, int column) {
// TODO Auto-generated method stub
return button;
}

public void actionPerformed(ActionEvent e) {
// TODO Auto-generated method stub
JOptionPane.showMessageDialog(null, "Renderer semester", "The message", JOptionPane.OK_OPTION);

}

@Override
public Component getTableCellEditorComponent(JTable table, Object value,
boolean isSelected, int row, int column) {
// TODO Auto-generated method stub
return button;
}
}