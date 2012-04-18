/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.fresneleditor.gui.mod.lens2.components;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;

import com.jidesoft.swing.CheckBoxList;
import com.jidesoft.swing.CheckBoxListCellRenderer;

import cz.muni.fi.fresneleditor.gui.mod.lens2.PropertyVisibilityWrapper;
import cz.muni.fi.fresneleditor.gui.mod.lens2.components.PropertyVisibilityJList.PropertyVisibilityLMCellRenderer;

/**
 * 
 * @author namornik
 */
public class PropertySelector<T> extends CheckBoxList {

	// private final static Logger LOG =
	// LoggerFactory.getLogger(BaseRepositoryDao.class);

	protected boolean isHidingProperties;

	/**
	 * Simple constructor should do the job.
	 */
	public PropertySelector() {
		super();
		isHidingProperties = false;
		// setElements((List<T>) new ArrayList<PropertyVisibilityWrapper>());
		_listCellRenderer = new VisibilityCheckBoxCellRenderer();
		// setClickInCheckBoxOnly(true);
		// addListSelectionListener(new
		// javax.swing.event.ListSelectionListener() {
		// public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
		// propertiesSelectionChanged(evt);
		// }
		// });
		setFocusable(false);
	}

	// private void
	// propertiesSelectionChanged(javax.swing.event.ListSelectionEvent evt){
	// setSelectedIndices(new int[0]);
	// }

	/**
	 * Replaces old model with new one.
	 * 
	 * @param elements
	 *            new Model
	 */
	@SuppressWarnings("unchecked")
	public void setElements(List<T> elements) {
		if (elements == null) {
			elements = Collections.emptyList();
		}
		// fill with items
		// DefaultListModel model = getNewListModel();
		List<T> list = (List<T>) Arrays.asList(((DefaultListModel) getModel())
				.toArray());
		// List<Integer> indices = Lists.newArrayList();

		for (T item : elements) {
			for (int i = 0; i < list.size(); i++) {
				// if(list.get(i).toString() == ((PropertyVisibilityWrapper)
				// item.toString()).getFresnelPropertyValueURI())
				if (((PropertyVisibilityWrapper) list.get(i))
						.getFresnelPropertyValueURI().equals(
								((PropertyVisibilityWrapper) item)
										.getFresnelPropertyValueURI()))
					// indices.add(new Integer(i));
					addCheckBoxListSelectedIndex(i);
			}
		}

	}

	// public void addElement(T element) {
	// ((DefaultListModel) getModel()).addElement(element);
	// }

	// public boolean removeElement(T element) {
	// return ((DefaultListModel) getModel()).removeElement(element);
	// }

	/**
	 * Separating this functionality into a protected method gives us the
	 * opportunity to use different class in children.
	 * 
	 * @return new instance of model
	 */
	protected DefaultListModel getNewListModel() {
		return new DefaultListModel();
	}

	@SuppressWarnings("unchecked")
	public List<T> getCheckedElements() {
		// List<T> elements = new ArrayList<T>(modelSize);
		// for (Object obj : getCheckBoxListSelectedValues()){
		// elements.add(obj);
		// }
		return (List<T>) Collections.unmodifiableList(Arrays
				.asList(getCheckBoxListSelectedValues()));
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

	public void setIsHidingProperties(boolean isHidingProperties) {
		this.isHidingProperties = isHidingProperties;
		int[] checkedVals = getCheckBoxListSelectedIndices();
		clearCheckBoxListSelection();

		// we assume values are in increasing order, according to CheckboxList
		// JavaDoc
		int counter = 0;
		for (int i = 0; i < getModel().getSize(); i++) {
			if (counter >= checkedVals.length) {
				getCheckBoxListSelectionModel().addSelectionInterval(i,
						getModel().getSize() - 1);
				break;
			}
			if (checkedVals[counter] != i) {
				getCheckBoxListSelectionModel().addSelectionInterval(i, i);
				// counter++;
			} else {
				counter++;
			}
		}
		// setCheckBoxListSelectedIndices(indices);
	}

	public boolean isHidingProperties() {
		return isHidingProperties;
	}

	private class VisibilityCheckBoxCellRenderer extends
			CheckBoxListCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8701154698574523742L;

		public VisibilityCheckBoxCellRenderer() {
			super();
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean hasFocus) {
			// return super.getListCellRendererComponent(list, value, index,
			// isSelected, hasFocus);
			JComponent cmp;
			if (value instanceof PropertyVisibilityWrapper) {
				PropertyVisibilityWrapper propVisibility = (PropertyVisibilityWrapper) value;
				cmp = (JComponent) super.getListCellRendererComponent(list,
						PropertyVisibilityLMCellRenderer
								.propVisibility2Label(propVisibility), index,
						false, hasFocus);
				cmp.setToolTipText(PropertyVisibilityLMCellRenderer
						.propVisibility2Tooltip(propVisibility));
			} else {
				cmp = (JComponent) super.getListCellRendererComponent(list,
						value, index, false, hasFocus);
			}
			// boolean isAlreadySelected = false;
			// for(int i:getSelectedIndices()){
			// if(i==index){
			// return cmp;
			// }
			// }
			// if(!isAlreadySelected){
			// if(getCheckBoxListSelectionModel().isSelectedIndex(index) &&
			// !isSelected){
			// addSelectionInterval(index, index);
			// }
			// else if(!getCheckBoxListSelectionModel().isSelectedIndex(index)
			// && isSelected){
			// removeSelectionInterval(index, index);
			// }
			// }

			// if(isSelected){
			// removeSelectionInterval(index, index);
			// if(!getCheckBoxListSelectionModel().isSelectedIndex(index))
			// addCheckBoxListSelectedIndex(index);
			// else
			// removeCheckBoxListSelectedIndex(index);
			// }

			return cmp;
		}
	}

}
