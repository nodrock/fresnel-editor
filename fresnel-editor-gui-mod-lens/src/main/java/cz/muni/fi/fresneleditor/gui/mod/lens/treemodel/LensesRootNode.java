package cz.muni.fi.fresneleditor.gui.mod.lens.treemodel;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.openrdf.model.URI;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.AFresnelRepoDataChangedListeningRootTabNode;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ATabNode;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ProjectTree;
import fr.inria.jfresnel.Lens;

public class LensesRootNode extends
		AFresnelRepoDataChangedListeningRootTabNode<Lens> {

	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("cz/muni/fi/fresneleditor/gui/mod/lens/resources/LensJPanel");

	public LensesRootNode(ProjectTree projectTree) {
		super(bundle.getString("Fresnel_Lenses"), projectTree);
	}

	@Override
	protected ATabNode<Lens> getNewChild(Lens lens) {
		return new LensItemNode(lens);
	}

	@Override
	protected List<Lens> getChildItems() {
		if (ContextHolder.getInstance().isProjectOpen()) {
                    List<Lens> lensesList = ContextHolder.getInstance().getFresnelDocumentDao().getLenses();
			
			LOG.debug("Project {} [{} lenses loaded]", ContextHolder
					.getInstance().getOpenProjectName(), lensesList.size());
			return lensesList;
		} else {
			// if no project is reset the lenses list
			return new ArrayList<Lens>();
		}
	}

}
