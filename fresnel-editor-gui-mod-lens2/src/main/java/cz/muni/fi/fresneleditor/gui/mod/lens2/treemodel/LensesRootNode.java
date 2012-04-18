package cz.muni.fi.fresneleditor.gui.mod.lens2.treemodel;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.openrdf.model.URI;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.AFresnelRepoDataChangedListeningRootTabNode;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ATabNode;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ProjectTree;
import cz.muni.fi.fresneleditor.gui.mod.lens2.LensItemNode;

public class LensesRootNode extends
		AFresnelRepoDataChangedListeningRootTabNode<URI> {

	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("cz/muni/fi/fresneleditor/gui/mod/lens2/resources/LensJPanel2");

	public LensesRootNode(ProjectTree projectTree) {
		super(bundle.getString("Fresnel_Lenses"), projectTree);
	}

	@Override
	protected ATabNode<URI> getNewChild(URI lensUri) {
		return new LensItemNode(lensUri);
	}

	@Override
	protected List<URI> getChildItems() {
		if (ContextHolder.getInstance().isProjectOpen()) {
			List<URI> lensUris = ContextHolder.getInstance()
					.getFresnelRepositoryDao().getLensURIs();
			LOG.debug("Project {} [{} lenses v2.0 loaded]", ContextHolder
					.getInstance().getOpenProjectName(), lensUris.size());
			return lensUris;
		} else {
			// if no project is reset the lenses list
			return new ArrayList<URI>();
		}
	}

}
