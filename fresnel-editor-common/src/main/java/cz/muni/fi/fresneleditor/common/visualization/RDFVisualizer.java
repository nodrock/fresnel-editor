/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.common.visualization;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.openrdf.model.URI;
import org.openrdf.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import cz.muni.fi.fresneleditor.common.BrowserUtils;
import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.FresnelApplication;
import cz.muni.fi.fresneleditor.common.FresnelEditorConstants;
import cz.muni.fi.fresneleditor.common.guisupport.MessageDialog;
import cz.muni.fi.fresneleditor.common.utils.URIUtils;
import cz.muni.fi.fresneleditor.model.FresnelRepositoryDao;
import fr.inria.jfresnel.Format;
import fr.inria.jfresnel.FresnelDocument;
import fr.inria.jfresnel.Group;
import fr.inria.jfresnel.Lens;
import fr.inria.jfresnel.sesame.SesameRenderer;

/**
 * Basic implementation of RDF visualizer - it's based on XSLT transformation.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 13.5.2009
 */
public class RDFVisualizer implements IRDFVisualizer {

	private static final Logger LOG = LoggerFactory
			.getLogger(RDFVisualizer.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visualize(Repository repository, URI groupUri,
			VisualizationParameter visParam, String pathToOutputFile) {

		// Initialization before visualization
		initialize();
		// Prepare fresnel document for rendering procedure
		FresnelDocument fd = prepareFresnelDocument(groupUri);
		// Render fresnel document and save it to result file
		renderFresnelDocumentToOutputFile(fd, repository, visParam,
				pathToOutputFile);
		// Show resulting html file in preview panel (using integrated LOBO
		// browser)
		showResultInPreviewPanel(pathToOutputFile);
	}

	/**
	 * {@inheritDoc}
	 */
	public void visualize(Repository repository, Group group,
			VisualizationParameter visParam) {

		// ValueFactory f = repository.getValueFactory();
		// URI result = f.createURI("foaf:Person");
		// try {
		// RepositoryConnection connection = repository.getConnection();
		// RepositoryResult<Statement> si = connection.getStatements(null,
		// RDF.TYPE, result, true);
		// System.out.println(si.hasNext());
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// return;
		// }

		// Initialization before visualization
		initialize();
		// Prepare fresnel document for rendering procedure
		FresnelDocument fd = prepareFresnelDocument(group);
		// Render fresnel document and save it to result file
		renderFresnelDocumentToOutputFile(fd, repository, visParam, null);
		// Show resulting html file in preview panel (using integrated LOBO
		// browser)
		showResultInPreviewPanel(null);
	}

	private void initialize() {
		File outputFile = new File(
				FresnelEditorConstants.DEFAULT_FILENAME_FINAL_XHTML);
		outputFile.delete();
	}

	private FresnelDocument prepareFresnelDocument(Group group) {

		FresnelDocument fd = new FresnelDocument();

		// Encapsulate group into array
		Group[] groupArray = new Group[1];
		groupArray[0] = group;

		if (group.getLenses() == null) {
			fd.setLenses(new Lens[0]);
		} else {
			fd.setLenses(group.getLenses());
		}

		if (group.getFormats() == null) {
			fd.setFormats(new Format[0]);
		} else {
			fd.setFormats(group.getFormats());
		}

		fd.setGroups(groupArray);

		return fd;
	}

	private FresnelDocument prepareFresnelDocument(URI groupUri) {

		// get group which should be used for visualization
		FresnelRepositoryDao fresnelDao = ContextHolder.getInstance()
				.getFresnelRepositoryDao();
		Group group = fresnelDao.getGroup(groupUri.toString());

		// display error message if no group with given URI is found
		if (group == null) {
			LOG.error("Fresnel Group '{}' for visualization not found!",
					groupUri);
			new MessageDialog(FresnelApplication.getApp().getBaseFrame(),
					"Visualization problem occured.",
					"Visualization of RDF could not be performed. Fresnel Group '"
							+ groupUri + "' for visualization not found!")
					.setVisible(true);
			return null;
		}

		// Encapsulate group into array
		Group[] groupArray = new Group[1];
		groupArray[0] = group;

		// Create Fresnel Document instance which will be passed to renderer
		FresnelDocument fd = new FresnelDocument();
		fd.setLenses(group.getLenses());
		fd.setFormats(group.getFormats());
		fd.setGroups(groupArray);

		return fd;
	}

	/**
	 * Renders Fresnel Document to XML and applies XSL template on it to earn
	 * XHTML page. Result is then stored as a file.
	 * 
	 * @param fd
	 *            Fresnel Document to be rendered
	 * @param repository
	 *            repository containing RDF to be rendered
	 */
	// TODO: Filename could be parameter
	private void renderFresnelDocumentToOutputFile(FresnelDocument fd,
			Repository repository, VisualizationParameter visParam,
			String pathToOutputFile) {

		ContextHolder ch = ContextHolder.getInstance();
        //determine which transform has to be used
        String transformation = "";
        if (ch.getTransformation() == FresnelEditorConstants.Transformations.MICRO) {
            transformation = FresnelEditorConstants.DEFAULT_XSL_MICTO_TEMPLATE_URL;
        } else if (ch.getTransformation() == FresnelEditorConstants.Transformations.RDFA) {
            transformation = FresnelEditorConstants.DEFAULT_XSL_RDFA_TEMPLATE_URL;
        } else if (ch.getTransformation() == FresnelEditorConstants.Transformations.HTML5) {
            transformation = FresnelEditorConstants.DEFAULT_XSL_HTML5_TEMPLATE_URL;
        } else if (ch.getTransformation() == FresnelEditorConstants.Transformations.SVG) {
            transformation = FresnelEditorConstants.DEFAULT_XSL_SVG_TEMPLATE_URL;
        } else {
            transformation = FresnelEditorConstants.DEFAULT_XSL_XHTML_TEMPLATE_URL;
        }
		
		SesameRenderer renderer = new SesameRenderer();
                
		ContextHolder.getInstance().getFresnelRepositoryDao()
				.printStatements(System.out, false);

		File finalXhtmlFile = null;
		if (pathToOutputFile == null) {
			if(transformation == FresnelEditorConstants.DEFAULT_XSL_SVG_TEMPLATE_URL){
				finalXhtmlFile = new File(FresnelEditorConstants.DEFAULT_FILENAME_FINAL_SVG);
			}else{
				finalXhtmlFile = new File(
					FresnelEditorConstants.DEFAULT_FILENAME_FINAL_XHTML);
			}
		} else {
			finalXhtmlFile = new File(pathToOutputFile);
		}

		try {
			Document document = renderer.render(fd, repository);

			// Prepare the DOM document for writing
			DOMSource source = new DOMSource(document);

			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(new StreamSource(transformation));

			// Set parameters to XSL template
			transformer.setParameter("pageTitle", visParam.getPageTitle());
			transformer.setParameter("cssStylesheetURL",
					visParam.getCssStylesheetURL());

			// Perform XSL transformation and write result into file
			transformer.transform(source, new StreamResult(
					new FileOutputStream(finalXhtmlFile)));

			LOG.info("Final XHTML document has been written to file: "
					+ FresnelEditorConstants.DEFAULT_FILENAME_FINAL_XHTML);

		} catch (Exception ex) {
			LOG.error("Transformation error: {}", ex.getMessage());
			// FIXME: Add proper stack trace logging.
			ex.printStackTrace();
			return;
		}
	}

	/**
	 * Displays result XHTML page in integrated LOBO web browser panel.
	 */
	private void showResultInPreviewPanel(String pathToOutputFile) {

		try {

			File finalXhtmlFile = null;
			if (pathToOutputFile == null) {
				finalXhtmlFile = new File(
						FresnelEditorConstants.DEFAULT_FILENAME_FINAL_XHTML);
			} else {
				finalXhtmlFile = new File(pathToOutputFile);
			}

			// Construct page URI
			// TODO: Ensure platform independent representation!
			String pageUri = URIUtils.addStylesheetURLPrefix(finalXhtmlFile
					.getAbsolutePath());

			LOG.info("Visualization of page on URI: " + pageUri);
			BrowserUtils.navigate(pageUri);

		} catch (Exception ex) {
			LOG.error("Rendering error: {}", ex.getMessage());
			// FIXME: Add proper stack trace logging.
			ex.printStackTrace();
			return;
		}
	}
}
