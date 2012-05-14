/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.format.utils;

import java.util.Vector;

import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.guisupport.dialogs.PreviewDialog;
import cz.muni.fi.fresneleditor.common.visualization.IRDFVisualizer;
import cz.muni.fi.fresneleditor.common.visualization.RDFVisualizer;
import cz.muni.fi.fresneleditor.common.visualization.VisualizationParameter;
import cz.muni.fi.fresneleditor.model.DataRepositoryDao;
import cz.muni.fi.fresneleditor.model.IModel;
import fr.inria.jfresnel.Format;
import fr.inria.jfresnel.Group;
import fr.inria.jfresnel.Lens;
import fr.inria.jfresnel.sesame.SesameGroup;
import fr.inria.jfresnel.sesame.SesameLens;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class encapsulates algorithms for rendering of Lens and Format preview.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 29.6.2009
 */
public class FormatPreviewRenderer {

	/**
	 * 
	 * @param lensUri
	 * @param format
	 * @param styleSheetUri
	 */
	public void renderFormatPreview(URI lensUri, Format format,
			String styleSheetUri) {

		if (format == null) {
			throw new NullPointerException("format");
		}

		// Add format into array
		Collection<Format> formatArray = new ArrayList<Format>();
		formatArray.add(format);

		String baseResourceClass = null;
		baseResourceClass = findBaseResourceClass(format);

		// Create default group
		Group defaultGroup = new Group(
				"http://www.fi.muni.cz/fresnel-editor#defaultVizGroup", "");
		defaultGroup.addFormat(format);

		Lens renderLens = null;
		VisualizationParameter visParam = new VisualizationParameter();
		visParam.setPageTitle("Format '" + format.getURI() + "' preview");

		if ((lensUri == null) || "".equals(lensUri.toString())
				|| PreviewDialog.DEFAULT_LENS_NAME.equals(lensUri.toString())) {
			// Default lens
			renderLens = new Lens(
					"http://www.fi.muni.cz/fresnel-editor#defaultVizLens", "");
			if (baseResourceClass != null) {
				renderLens.addClassDomain(baseResourceClass);
			} else {
				renderLens
						.addClassDomain("http://www.w3.org/2000/01/rdf-schema#Resource");
			}
			renderLens.setPropertiesVisibility(new Vector(), new Vector(), 0);
			renderLens.addAssociatedGroup(defaultGroup);
			renderLens.addAssociatedFormats(formatArray);
			defaultGroup.addLens(renderLens);
		} else {
			// Specified lens
			// TODO
		}

		if ((styleSheetUri != null) && !"".equals(styleSheetUri)) {
			// Default CSS stylesheet
			visParam.setCssStylesheetURL(styleSheetUri);
		}

		// Trigger visualization algorithm to show preview in preview panel
		IRDFVisualizer visualizer = new RDFVisualizer();
		Repository dataRepo = ContextHolder.getInstance()
				.getDataRepositoryDao().getRepository();
		visualizer.visualize(dataRepo, defaultGroup, visParam);
	}

	private String findBaseResourceClass(Format format) {

		DataRepositoryDao dataDao = ContextHolder.getInstance()
				.getDataRepositoryDao();
		RepositoryConnection con = null;
		try {
			con = dataDao.getRepository().getConnection();
		} catch (RepositoryException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return null;
		}

		String baseResourceClass = null;

		if (!format.getBasicPropertyDomains().isEmpty()) {
			// Find the domain where at least one resource exist in data
			// repository
			for (String domain : format.getBasicPropertyDomains()) {
				try {
					RepositoryResult<Statement> result = con.getStatements(
							null, new URIImpl(domain), null, true);
					if (!result.hasNext()) {
						continue;
					}
					while (result.hasNext()) {
						Statement statement = result.next();
						Value value = statement.getSubject();
						RepositoryResult<Statement> innerResult = con
								.getStatements(
										new URIImpl(value.stringValue()),
										new URIImpl(IModel.PROPERTY_RDF_TYPE),
										null, true);
						if (!innerResult.hasNext()) {
							continue;
						}
						while (innerResult.hasNext()) {
							Statement innerStatement = innerResult.next();
							Value innerValue = innerStatement.getObject();
							return innerValue.stringValue();
						}
					}
				} catch (RepositoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		}

		return baseResourceClass;
	}
}
