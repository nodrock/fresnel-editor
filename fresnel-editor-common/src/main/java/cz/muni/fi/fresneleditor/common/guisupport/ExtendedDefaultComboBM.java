/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport;

import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

/**
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class ExtendedDefaultComboBM<T> extends DefaultComboBoxModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExtendedDefaultComboBM(List<T> items) {
		if (items == null) {
			return;
		}
		for (T item : items) {
			addElement(item);
		}
	}

	public ExtendedDefaultComboBM(T[] items) {
		this(Arrays.asList(items));
	}

}
