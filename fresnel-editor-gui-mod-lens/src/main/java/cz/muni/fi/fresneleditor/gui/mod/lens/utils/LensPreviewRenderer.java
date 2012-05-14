/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.lens.utils;

import org.openrdf.model.URI;
import org.openrdf.repository.Repository;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.visualization.IRDFVisualizer;
import cz.muni.fi.fresneleditor.common.visualization.RDFVisualizer;
import cz.muni.fi.fresneleditor.common.visualization.VisualizationParameter;
import fr.inria.jfresnel.Group;
import fr.inria.jfresnel.Lens;
import fr.inria.jfresnel.sesame.SesameGroup;

/**
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 30.6.2009
 */
public class LensPreviewRenderer {

	/**
	 * 
	 * @param lens
	 * @param formatUri
	 * @param styleSheetURL
	 */
	public void renderLensPreview(Lens lens, URI formatUri, String styleSheetURL) {

		if (lens == null) {
			throw new NullPointerException("lens");
		}

		// Add format into array
		Lens[] lensArray = new Lens[1];
		lensArray[0] = lens;

		// Create default group
		Group defaultGroup = new Group(
				"http://www.fi.muni.cz/fresnel-editor#defaultVizGroup", "");
		defaultGroup.addLens(lens);

		VisualizationParameter visParam = new VisualizationParameter();
		visParam.setPageTitle("Lens '" + lens.getURI() + "' preview");

		if ((styleSheetURL != null) && !"".equals(styleSheetURL)) {
			// Specified CSS stylesheet
			visParam.setCssStylesheetURL(styleSheetURL);
		}

		// Trigger visualization algorithm to show preview in preview panel
		IRDFVisualizer visualizer = new RDFVisualizer();
		Repository dataRepo = ContextHolder.getInstance()
				.getDataRepositoryDao().getRepository();
		visualizer.visualize(dataRepo, defaultGroup, visParam);
	}
}
