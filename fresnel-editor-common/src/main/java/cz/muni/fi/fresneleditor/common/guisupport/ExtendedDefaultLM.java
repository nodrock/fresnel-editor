/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport;

import java.util.List;

import javax.swing.DefaultListModel;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class ExtendedDefaultLM<T> extends DefaultListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for list model that allows initialisation of the list with
	 * items.
	 * 
	 * @param items
	 */
	public ExtendedDefaultLM(List<T> items) {
		if (items == null) {
			return;
		}
		for (T item : items) {
			addElement(item);
		}
	}

}
