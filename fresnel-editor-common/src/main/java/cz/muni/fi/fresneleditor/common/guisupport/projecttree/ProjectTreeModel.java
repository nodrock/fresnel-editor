/**
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.projecttree;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.FresnelApplication;
import cz.muni.fi.fresneleditor.common.FresnelEditorConstants;
import cz.muni.fi.fresneleditor.common.IFresnelEditorModule;
import java.util.ResourceBundle;

/**
 * Custom tree model for project tree menu.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz), Igor Zemsky
 *         (zemsky@mail.muni.cz)
 * @version 15. 3. 2009
 */
public class ProjectTreeModel extends DefaultTreeModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ProjectTree projectTree;
	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle(FresnelEditorConstants.PATH_RESOURCE_BUNDLE_COMMON);

	public ProjectTreeModel(ProjectTree projectTree) {

		super(new DefaultMutableTreeNode(bundle.getString("No_project_opened")));
		this.projectTree = projectTree;
		// If project is open the initialize project tree and change root node
		// name
		if (ContextHolder.getInstance().isProjectOpen()) {
			String openProjectName = ContextHolder.getInstance()
					.getOpenProjectName();
			DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) root;
			rootNode.setUserObject(openProjectName);
			initializeProjectTree();
		}

	}

	/**
	 * TODO
	 */
	public void getSelectedTreeNodeType() {
		throw new UnsupportedOperationException("getSelectedTreeNodeType");
	}

	/**
	 * Initializes project tree menu with empty nodes for available modules. If
	 * a project is opening of the project these nodes will be filled with
	 * appropriate child nodes.
	 * 
	 * @return reference to root tree node
	 */
	private TreeNode initializeProjectTree() {
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) root;

		// loading of dynamic modules
		List<IFresnelEditorModule> modules = FresnelApplication.getModules();
		Collections.sort(modules, getModulesComparator());
		for (IFresnelEditorModule module : modules) {
			if (module instanceof ITreeModule) {
				addModule((ITreeModule) module);
			}
		}

		// DefaultMutableTreeNode cssNode = new DefaultMutableTreeNode("CSS");
		DefaultMutableTreeNode namespacesNode = new NamespacesNode();
		DefaultMutableTreeNode projectConfigurationNode = new ProjectConfigurationNode();

		// rootNode.add(cssNode);
		rootNode.add(namespacesNode);
		rootNode.add(projectConfigurationNode);

		return rootNode;
	}

	public void addModule(ITreeModule module) {
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) root;
		rootNode.add(module.getRoot(projectTree));
	}

	/**
	 * Returns a name based comparator for fresnel editor modules.
	 * 
	 * @return name based comparator for fresnel editor modules
	 * @see String#compareTo(String)
	 */
	private Comparator<IFresnelEditorModule> getModulesComparator() {
		return new Comparator<IFresnelEditorModule>() {
			public int compare(IFresnelEditorModule o1, IFresnelEditorModule o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};
	}

}
