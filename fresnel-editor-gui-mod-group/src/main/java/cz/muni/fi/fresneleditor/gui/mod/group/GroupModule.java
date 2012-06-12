/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.group;

import javax.swing.tree.DefaultMutableTreeNode;

import org.springframework.stereotype.Component;

import cz.muni.fi.fresneleditor.common.FresnelEditorModule;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ITreeModule;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ProjectTree;
import cz.muni.fi.fresneleditor.gui.mod.group.treemodel.GroupsRootNode;

/**
 * Fresnel editor module for editing Fresnel Groups.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz), Miroslav Warchil
 *         (warmir@mail.muni.cz)
 * @version 18.4.2009
 */
@Component
public class GroupModule extends FresnelEditorModule implements ITreeModule {

	public GroupModule() {
		super("Groups Module", "alpha");
	}

	@Override
	public DefaultMutableTreeNode getRoot(ProjectTree projectTree) {
		return new GroupsRootNode(projectTree);
	}

}
