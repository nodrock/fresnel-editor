/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.projecttree;

import javax.swing.tree.DefaultMutableTreeNode;

import cz.muni.fi.fresneleditor.common.IFresnelEditorModule;

/**
 * Interface for fresnel editor module that should be represented in the project
 * tree.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public interface ITreeModule extends IFresnelEditorModule {

	/**
	 * Returns root node of this fresnel editor module.
	 * 
	 * @param projectTreeModel
	 *            model for the project tree in which this module is represented
	 * @return the root node of this fresnel editor module
	 */
	public DefaultMutableTreeNode getRoot(ProjectTree projectTree);

}
