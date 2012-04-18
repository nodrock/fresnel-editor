/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.components;

import javax.swing.DefaultListCellRenderer;

/**
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class NamespacesListCellRenderer extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static NamespacesListCellRenderer instance;

	public static NamespacesListCellRenderer getInstance() {
		if (instance == null) {
			instance = new NamespacesListCellRenderer();
		}
		return instance;
	}

	private NamespacesListCellRenderer() {
		// singleton constructor
	}

}
