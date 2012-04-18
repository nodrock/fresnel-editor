/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.components;

import javax.swing.JComboBox;

import cz.muni.fi.fresneleditor.common.guisupport.ExtendedJComboBox;
import org.openrdf.model.Namespace;

/**
 * {@link JComboBox} component for {@link Namespace} objects.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class NamespacesJComboBox extends ExtendedJComboBox<Namespace> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NamespacesJComboBox() {
		super();
		setRenderer(NamespacesListCellRenderer.getInstance());
	}

}
