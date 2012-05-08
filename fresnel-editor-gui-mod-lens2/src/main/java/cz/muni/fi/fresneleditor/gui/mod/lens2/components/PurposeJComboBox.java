/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.fresneleditor.gui.mod.lens2.components;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import fr.inria.jfresnel.lenses.LensPurposeType;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class PurposeJComboBox extends JComboBox {

	public PurposeJComboBox() {
		addItem(LensPurposeType.NOT_SPECIFIED);
		addItem(LensPurposeType.DEFAULT);
		addItem(LensPurposeType.LABEL);

		setRenderer(LCR);
	}

	private ListCellRenderer LCR = new DefaultListCellRenderer() {
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {

			Component component = super.getListCellRendererComponent(list,
					value, index, isSelected, cellHasFocus);

			String purposeLabel;
			LensPurposeType type = (LensPurposeType) value;
			switch (type) {
			case DEFAULT:
				purposeLabel = "Default";
				break;
			case LABEL:
				purposeLabel = "Label";
				break;
			case NOT_SPECIFIED:
				purposeLabel = "Undefined";
				break;
			default:
				throw new ArrayIndexOutOfBoundsException(
						"Unknown lens purpose: " + type.toString());
			}

			((JLabel) component).setText(purposeLabel);

			return component;
		}
	};

}
