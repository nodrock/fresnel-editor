/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.fresneleditor.gui.mod.lens.components;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import fr.inria.jfresnel.Lens;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class PurposeJComboBox extends JComboBox {

	public PurposeJComboBox() {
		addItem(Lens.PURPOSE_UNDEFINED);
		addItem(Lens.PURPOSE_DEFAULT);
		addItem(Lens.PURPOSE_LABEL);

		setRenderer(LCR);
	}

	private ListCellRenderer LCR = new DefaultListCellRenderer() {
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {

			Component component = super.getListCellRendererComponent(list,
					value, index, isSelected, cellHasFocus);

			String purposeLabel;
			short shortValue = (Short) value;
			switch (shortValue) {
			case Lens.PURPOSE_DEFAULT:
				purposeLabel = "Default";
				break;
			case Lens.PURPOSE_LABEL:
				purposeLabel = "Label";
				break;
			case Lens.PURPOSE_UNDEFINED:
				purposeLabel = "Undefined";
				break;
			default:
				throw new ArrayIndexOutOfBoundsException(
						"Unknown lens purpose: " + shortValue);
			}

			((JLabel) component).setText(purposeLabel);

			return component;
		}
	};

}
