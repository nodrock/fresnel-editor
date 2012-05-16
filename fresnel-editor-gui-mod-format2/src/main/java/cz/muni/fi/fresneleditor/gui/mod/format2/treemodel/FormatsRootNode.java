/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.fresneleditor.gui.mod.format2.treemodel;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.openrdf.model.URI;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.AFresnelRepoDataChangedListeningRootTabNode;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ATabNode;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ProjectTree;
import fr.inria.jfresnel.Format;

/**
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 7.4.2009
 */
public class FormatsRootNode extends
		AFresnelRepoDataChangedListeningRootTabNode<Format> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("cz/muni/fi/fresneleditor/gui/mod/format2/resource-bundle-format");

	public FormatsRootNode(ProjectTree projectTree) {
		super(bundle.getString("Fresnel_Formats"), projectTree);
	}

	@Override
	protected ATabNode<Format> getNewChild(Format format) {
		return new FormatItemNode(format);
	}

	@Override
	protected List<Format> getChildItems() {
		if (ContextHolder.getInstance().isProjectOpen()) {
			List<Format> formatsList = ContextHolder.getInstance().getFresnelDocumentDao().getFormats();
                        
			LOG.debug("Project {} [{} formats loaded]", ContextHolder
					.getInstance().getOpenProjectName(), formatsList.size());
			return formatsList;
		} else {
			// if no project is open reset the formats list
			return new ArrayList<Format>();
		}
	}

}
