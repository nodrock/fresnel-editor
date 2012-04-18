/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.components;

import cz.muni.fi.fresneleditor.common.guisupport.ExtendedJComboBox;
import org.openrdf.model.Value;

/**
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class ValueJComboBox extends ExtendedJComboBox<Value> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValueJComboBox() {
		super();
		setRenderer(ValueListCellRenderer.getInstance());
	}

}
