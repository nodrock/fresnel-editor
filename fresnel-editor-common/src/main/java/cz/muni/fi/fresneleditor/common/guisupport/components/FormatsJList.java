/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.components;

import javax.swing.JList;

import cz.muni.fi.fresneleditor.common.guisupport.ExtendedJList;
import fr.inria.jfresnel.Format;

/**
 * {@link JList} component for {@link Format} objects.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class FormatsJList extends ExtendedJList<Format> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FormatsJList() {
		super();
		setCellRenderer(FormatListCellRenderer.getInstance());
	}

}
