/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.components;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import fr.inria.jfresnel.Lens;

/**
 * {@link DefaultListCellRenderer} for {@link Lens} object.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
class LensesListCellRenderer extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static LensesListCellRenderer instance;

	private LensesListCellRenderer() {
		super();
		// singleton constructor
	}

	/**
	 * Returns the singleton instance of this cell renderer.
	 * 
	 * @return the singleton instance of this cell renderer
	 */
	static LensesListCellRenderer getInstance() {
		if (instance == null) {
			instance = new LensesListCellRenderer();
		}
		return instance;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Component component = super.getListCellRendererComponent(list, value,
				index, isSelected, cellHasFocus);

		Lens lens = null;
		if (value instanceof Lens) {
			lens = (Lens) value;
			if (component instanceof JLabel) {
				((JLabel) component).setText(lens.getLabel());
			}
		}
		return component;
	}
}