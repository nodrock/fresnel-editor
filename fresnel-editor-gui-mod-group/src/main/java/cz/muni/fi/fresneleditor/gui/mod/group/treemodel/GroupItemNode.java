/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.gui.mod.group.treemodel;

import org.openrdf.model.URI;

import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ATabNode;
import cz.muni.fi.fresneleditor.gui.mod.group.GroupsJPanel;
import fr.inria.jfresnel.Group;

/**
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 18.4.2009
 */
public class GroupItemNode extends ATabNode<Group> {

	public GroupItemNode(Group group) {
		super(group);
	}

	@Override
	public String toString() {
		return getComponent().getLabel();
	}

	@Override
	protected ITabComponent<Group> createComponent() {
		return new GroupsJPanel(getUserObject(), this);
	}

	@Override
	public void clickAction(int clickCount) {
		if (clickCount == 2 && !isOpened()) {
			((GroupsJPanel) getComponent()).doReload();
		}
		super.clickAction(clickCount);
	}
}
