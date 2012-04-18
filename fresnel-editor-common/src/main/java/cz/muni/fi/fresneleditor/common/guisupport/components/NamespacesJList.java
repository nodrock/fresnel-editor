/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.components;

import javax.swing.JList;

import org.openrdf.model.Namespace;

import cz.muni.fi.fresneleditor.common.guisupport.ExtendedJList;

/**
 * {@link JList} component for {@link Namespace} objects.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class NamespacesJList extends ExtendedJList<Namespace> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NamespacesJList() {
		super();
		setCellRenderer(NamespacesListCellRenderer.getInstance());
	}

}
