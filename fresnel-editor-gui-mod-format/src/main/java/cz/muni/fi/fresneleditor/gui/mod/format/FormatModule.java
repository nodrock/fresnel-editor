/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.format;

import javax.swing.tree.DefaultMutableTreeNode;

import org.springframework.stereotype.Component;

import cz.muni.fi.fresneleditor.common.FresnelEditorModule;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ITreeModule;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ProjectTree;
import cz.muni.fi.fresneleditor.gui.mod.format.treemodel.FormatsRootNode;

/**
 * Fresnel editor module for editing Fresnel Formats.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz), Miroslav Warchil
 *         (warmir@mail.muni.cz)
 * 
 */
@Component
public class FormatModule extends FresnelEditorModule implements ITreeModule {

	public FormatModule() {
		super("Formats Module", "alpha");
	}

	@Override
	public DefaultMutableTreeNode getRoot(ProjectTree projectTree) {
		return new FormatsRootNode(projectTree);
	}

}
