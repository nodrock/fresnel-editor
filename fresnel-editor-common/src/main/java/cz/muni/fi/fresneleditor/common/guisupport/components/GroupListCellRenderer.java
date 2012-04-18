/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.components;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import fr.inria.jfresnel.Group;

/**
 * List cell renderer for {@link Group} object.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class GroupListCellRenderer extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static GroupListCellRenderer instance;

	private GroupListCellRenderer() {
		super();
		// singleton constructor
	}

	/**
	 * Returns the singleton instance of this cell renderer.
	 * 
	 * @return the singleton instance of this cell renderer
	 */
	static GroupListCellRenderer getInstance() {
		if (instance == null) {
			instance = new GroupListCellRenderer();
		}
		return instance;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Component component = super.getListCellRendererComponent(list, value,
				index, isSelected, cellHasFocus);

		Group group = null;
		if (value instanceof Group) {
			group = (Group) value;
			if (component instanceof JLabel) {
				((JLabel) component).setText(group.getURI());
			}
		}
		return component;
	}
}
