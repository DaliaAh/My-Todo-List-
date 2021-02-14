package ui;

import javax.swing.*;
import java.awt.*;


// A list renderer that adds check boxes to every element of a list
public class CheckBoxListCellRenderer extends JCheckBox implements ListCellRenderer {
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        setComponentOrientation(list.getComponentOrientation());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setSelected(isSelected);
        setEnabled(list.isEnabled());
        setText(value == null ? "" : value.toString());
        return this;
    }
}
