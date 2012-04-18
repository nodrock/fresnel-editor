package cz.muni.fi.fresneleditor.gui.mod.lens;

import javax.swing.tree.DefaultMutableTreeNode;

import org.springframework.stereotype.Component;

import cz.muni.fi.fresneleditor.common.FresnelEditorModule;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ITreeModule;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ProjectTree;
import cz.muni.fi.fresneleditor.gui.mod.lens.treemodel.LensesRootNode;

/**
 * Fresnel editor module for editing fresnel lenses.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
@Component
public class LensesModule extends FresnelEditorModule implements ITreeModule {

	public LensesModule() {
		super("Lenses Module", "0.1a");
	}

	@Override
	public DefaultMutableTreeNode getRoot(ProjectTree projectTree) {
		return new LensesRootNode(projectTree);
	}

}
