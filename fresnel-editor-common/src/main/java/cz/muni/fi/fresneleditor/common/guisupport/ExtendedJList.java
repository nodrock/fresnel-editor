/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class ExtendedJList<T> extends JList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExtendedJList(ListCellRenderer cellRenderer) {
		super();
		if (cellRenderer != null) {
			setCellRenderer(cellRenderer);
		}
		setModel(getNewListModel());
	}

	public ExtendedJList() {
		this(null);
	}

	/**
	 * Removes all set of items and replaces them with new list. Replaces old
	 * model with new one.
	 * 
	 * @param elements
	 */
	public void setElements(List<T> elements) {
		if (elements == null) {
			elements = Collections.emptyList();
		}
		// fill with items
		DefaultListModel model = getNewListModel();
		for (T item : elements) {
			model.addElement(item);
		}
		setModel(model);
	}

	protected DefaultListModel getNewListModel() {
		return new DefaultListModel();
	}

	public void addElement(T element) {
		((DefaultListModel) getModel()).addElement(element);
	}

	public boolean removeElement(T element) {
		return ((DefaultListModel) getModel()).removeElement(element);
	}

	/**
	 * Returns unmodifiable list of this JList elements.
	 * 
	 * @return unmodifiable list of this JList elements
	 */
	@SuppressWarnings("unchecked")
	public List<T> getElements() {
		int modelSize = getModel().getSize();
		List<T> elements = new ArrayList<T>(modelSize);
		for (int i = 0; i < modelSize; i++) {
			elements.add((T) getModel().getElementAt(i));
		}
		return Collections.unmodifiableList(elements);
	}

	@SuppressWarnings("unchecked")
	public T getElementAt(int index) {
		return (T) getModel().getElementAt(index);
	}

	public void removeElement(int i) {
		((DefaultListModel) getModel()).remove(i);
	}

	@SuppressWarnings("unchecked")
	public List<T> getSelectedValuesCasted() {
		Object[] selectedValues = super.getSelectedValues();
		List<T> casted = new ArrayList<T>();
		for (Object obj : selectedValues) {
			casted.add((T) obj);
		}
		return casted;
	}

	/**
	 * Returns the value for the smallest selected cell index; <i>the selected
	 * value</i> when only a single item is selected in the list. When multiple
	 * items are selected, it is simply the value for the smallest selected
	 * index. Returns {@code null} if there is no selection.
	 * <p>
	 * This is a convenience method that simply returns the model value for
	 * {@code getMinSelectionIndex}.
	 * 
	 * @return the first selected value
	 * @see #getMinSelectionIndex
	 * @see #getModel
	 * @see #addListSelectionListener
	 */
	@SuppressWarnings("unchecked")
	public T getSelectedValueCasted() {
		return (T) getSelectedValue();
	}

	public void addElements(List<T> elements) {
		for (T element : elements) {
			((DefaultListModel) getModel()).addElement(element);
		}
	}

	public void removeAllElements() {
		((DefaultListModel) getModel()).removeAllElements();
	}

	public boolean isEmpty() {
		return ((DefaultListModel) getModel()).isEmpty();
	}

}
