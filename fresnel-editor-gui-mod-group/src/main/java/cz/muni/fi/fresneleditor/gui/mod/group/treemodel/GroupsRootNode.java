/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.gui.mod.group.treemodel;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.openrdf.model.URI;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.AFresnelRepoDataChangedListeningRootTabNode;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ATabNode;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ProjectTree;
import fr.inria.jfresnel.Group;

/**
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 18.4.2009
 */
public class GroupsRootNode extends
		AFresnelRepoDataChangedListeningRootTabNode<Group> {

	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("cz/muni/fi/fresneleditor/gui/mod/group/resources/GroupsJPanel");

	public GroupsRootNode(ProjectTree projectTree) {
		super(bundle.getString("Fresnel_Groups"), projectTree);
	}

	@Override
	protected ATabNode<Group> getNewChild(Group group) {
		return new GroupItemNode(group);
	}

	@Override
	protected List<Group> getChildItems() {
		if (ContextHolder.getInstance().isProjectOpen()) {
			// fixme igor: reimplement to URIs....
			List<Group> groupsList = ContextHolder.getInstance().getFresnelDocumentDao().getGroups();
                        
			LOG.debug("Project {} [{} groups loaded]", ContextHolder
					.getInstance().getOpenProjectName(), groupsList.size());
			return groupsList;
		} else {
			// if no project is reset the formats list
			return new ArrayList<Group>();
		}
	}

}
