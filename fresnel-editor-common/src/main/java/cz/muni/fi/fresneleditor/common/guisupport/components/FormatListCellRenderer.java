/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.components;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import fr.inria.jfresnel.Format;

/**
 * List cell renderer for {@link Format} object.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
class FormatListCellRenderer extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static FormatListCellRenderer instance;

	private FormatListCellRenderer() {
		super();
		// singleton constructor
	}

	/**
	 * Returns the singleton instance of this cell renderer.
	 * 
	 * @return the singleton instance of this cell renderer
	 */
	static FormatListCellRenderer getInstance() {
		if (instance == null) {
			instance = new FormatListCellRenderer();
		}
		return instance;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Component component = super.getListCellRendererComponent(list, value,
				index, isSelected, cellHasFocus);

		Format format = null;
		if (value instanceof Format) {
			format = (Format) value;
			if (component instanceof JLabel) {
				((JLabel) component).setText(format.getURI());
			}
		}
		return component;
	}
}
