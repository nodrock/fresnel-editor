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

/**
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 18.4.2009
 */
public class GroupsRootNode extends
		AFresnelRepoDataChangedListeningRootTabNode<URI> {

	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("cz/muni/fi/fresneleditor/gui/mod/group/resources/GroupsJPanel");

	public GroupsRootNode(ProjectTree projectTree) {
		super(bundle.getString("Fresnel_Groups"), projectTree);
	}

	@Override
	protected ATabNode<URI> getNewChild(URI groupUri) {
		return new GroupItemNode(groupUri);
	}

	@Override
	protected List<URI> getChildItems() {
		if (ContextHolder.getInstance().isProjectOpen()) {
			// fixme igor: reimplement to URIs....
			List<URI> groupsUriList = ContextHolder.getInstance()
					.getFresnelRepositoryDao().getGroupsURIs();
			LOG.debug("Project {} [{} groups loaded]", ContextHolder
					.getInstance().getOpenProjectName(), groupsUriList.size());
			return groupsUriList;
		} else {
			// if no project is reset the formats list
			return new ArrayList<URI>();
		}
	}

}
