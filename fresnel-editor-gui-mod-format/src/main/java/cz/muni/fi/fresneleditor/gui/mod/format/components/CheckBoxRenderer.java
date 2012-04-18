/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.format.components;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 28.3.2009
 */
public class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CheckBoxRenderer() {
		setHorizontalAlignment(JLabel.CENTER);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			// super.setBackground(table.getSelectionBackground());
			setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(table.getBackground());
		}
		setSelected((value != null && ((Boolean) value).booleanValue()));
		return this;
	}
}
