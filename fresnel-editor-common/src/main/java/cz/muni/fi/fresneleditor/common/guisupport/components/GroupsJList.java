/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.components;

import javax.swing.JList;

import cz.muni.fi.fresneleditor.common.guisupport.ExtendedJList;
import fr.inria.jfresnel.Group;

/**
 * {@link JList} component for {@link Group} objects.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class GroupsJList extends ExtendedJList<Group> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GroupsJList() {
		super();
		setCellRenderer(GroupListCellRenderer.getInstance());
	}

}
