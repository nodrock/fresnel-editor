package cz.muni.fi.fresneleditor.common;

import javax.swing.JScrollPane;

/**
 * Interface for visual representation of an item as tab in the
 * {@link BaseJFrame}.
 * 
 * @param <I>
 *            type of the item that constitutes the data of this tab component
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public interface ITabComponent<I extends Object> {

	/**
	 * Returns the tab label.
	 * 
	 * @return
	 */
	public String getLabel();

	/**
	 * Returns the scroll pane which is displayed in the tab.
	 * 
	 * @return
	 */
	public JScrollPane getScrollPane();

	/**
	 * Returns the item represented by this tab component.
	 * 
	 * @return the item represented by this tab component
	 */
	public I getItem();

}
