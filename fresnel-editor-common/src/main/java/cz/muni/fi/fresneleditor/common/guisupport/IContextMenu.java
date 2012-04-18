/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport;

import java.util.List;

import javax.swing.JMenuItem;

/**
 * Interface for object which can provide context menu.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public interface IContextMenu {

	/**
	 * Returns the menu items associated with this node.
	 * 
	 * @return the menu items
	 */
	public List<JMenuItem> getMenu();

}
