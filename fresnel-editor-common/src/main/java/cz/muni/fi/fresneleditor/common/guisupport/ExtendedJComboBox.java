/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport;

import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class ExtendedJComboBox<T> extends JComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExtendedJComboBox() {
		super();
	}

	/**
	 * Replaces current items with new list. Replaces old model with new one.
	 * 
	 * @param elements
	 */
	public void setElements(List<T> elements) {
		if (elements == null) {
			elements = Collections.emptyList();
		}
		// fill with items
		DefaultComboBoxModel model = getNewListModel();
		for (T item : elements) {
			model.addElement(item);
		}
		setModel(model);
	}

	/**
	 * Wrapper method for super.getSelectedItem() that returns casted object.
	 */
	@SuppressWarnings("unchecked")
	public T getSelectedItemCasted() {
		return (T) super.getSelectedItem();
	}

	protected DefaultComboBoxModel getNewListModel() {
		return new DefaultComboBoxModel();
	}
}
