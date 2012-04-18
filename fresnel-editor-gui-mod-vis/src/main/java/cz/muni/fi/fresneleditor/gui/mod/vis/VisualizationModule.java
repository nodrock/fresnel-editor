/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.gui.mod.vis;

import javax.swing.tree.DefaultMutableTreeNode;

import org.springframework.stereotype.Component;

import cz.muni.fi.fresneleditor.common.FresnelEditorModule;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ITreeModule;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ProjectTree;
import cz.muni.fi.fresneleditor.gui.mod.vis.treemodel.VisualizationRootNode;

/**
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 10.5.2009
 */
@Component
public class VisualizationModule extends FresnelEditorModule implements
		ITreeModule {

	public VisualizationModule() {
		super("Visualization Module", "alpha");
	}

	@Override
	public DefaultMutableTreeNode getRoot(ProjectTree projectTree) {
		return new VisualizationRootNode(projectTree);
	}
}
