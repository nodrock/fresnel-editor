/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.components;

import cz.muni.fi.fresneleditor.common.guisupport.ExtendedJList;
import fr.inria.jfresnel.Lens;

/**
 * JList component for {@link Lens} objects.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class LensesJList extends ExtendedJList<Lens> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LensesJList() {
		super();
		setCellRenderer(LensesListCellRenderer.getInstance());
	}

}