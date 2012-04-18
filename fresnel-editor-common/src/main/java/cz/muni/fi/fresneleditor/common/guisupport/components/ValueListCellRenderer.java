/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.components;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import org.openrdf.model.Resource;
import org.openrdf.model.Value;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.utils.FresnelUtils;

/**
 * List cell renderer for {@link Value} object.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class ValueListCellRenderer extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ValueListCellRenderer instance;

	private ValueListCellRenderer() {
		// singleton constructor
		super();
	}

	/**
	 * Returns the singleton instance of this cell renderer.
	 * 
	 * @return the singleton instance of this cell renderer
	 */
	public static ValueListCellRenderer getInstance() {
		if (instance == null) {
			instance = new ValueListCellRenderer();
		}
		return instance;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Component component = super.getListCellRendererComponent(list, value,
				index, isSelected, cellHasFocus);

		if (value instanceof Value) {
			if (component instanceof JLabel) {
				String text = value.toString();
				if (Resource.class.isAssignableFrom(value.getClass())) {
					text = FresnelUtils.replaceNamespace(((Resource) value)
							.stringValue(), ContextHolder.getInstance()
							.getBothDaosNamespaces());
				} else {
					throw new IllegalArgumentException("the value is of type: "
							+ value.getClass());
				}
				((JLabel) component).setText(text);
			}
		}
		return component;
	}

}
