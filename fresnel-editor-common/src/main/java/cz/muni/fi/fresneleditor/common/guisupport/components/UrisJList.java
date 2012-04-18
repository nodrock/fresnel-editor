/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.components;

import cz.muni.fi.fresneleditor.common.guisupport.ExtendedJList;
import org.openrdf.model.URI;

/**
 * JList component for {@link URI} objects.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class UrisJList extends ExtendedJList<URI> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UrisJList() {
		super();
		setCellRenderer(ValueListCellRenderer.getInstance());
	}

}
