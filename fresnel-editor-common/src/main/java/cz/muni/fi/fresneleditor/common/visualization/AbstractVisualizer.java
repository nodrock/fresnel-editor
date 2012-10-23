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

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.FresnelApplication;
import cz.muni.fi.fresneleditor.common.guisupport.MessageDialog;
import fr.inria.jfresnel.Format;
import fr.inria.jfresnel.FresnelDocument;
import fr.inria.jfresnel.Group;
import fr.inria.jfresnel.Lens;
import fr.inria.jfresnel.sesame.SesameRenderer;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic implementation of RDF visualizer - it's based on XSLT transformation.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 13.5.2009
 * 
 * changes made in May 2012 by Milos Kalab
 */
public abstract class AbstractVisualizer implements IRDFVisualizer {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractVisualizer.class);
    protected String defaultOutputFilename;
    protected String XSLTemplate;

    public AbstractVisualizer(String defaultOutputFilename, String XSLTemplate) {
        this.defaultOutputFilename = defaultOutputFilename;
        this.XSLTemplate = XSLTemplate;
    }

    public AbstractVisualizer(String XSLTemplate) {
        this.XSLTemplate = XSLTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visualize(Repository repository, URI groupUri, VisualizationParameter visParam,
            String pathToOutputFile) {

        // Initialization before visualization
        initialize();
        // Prepare fresnel document for rendering procedure
        FresnelDocument fd = prepareFresnelDocument(groupUri);
        // Render fresnel document and save it to result file
        renderFresnelDocumentToOutputFile(fd, repository, visParam, pathToOutputFile);
        // Show resulting html file in preview panel (using integrated LOBO browser)
        showResultInPreviewPanel(pathToOutputFile);
    }

    /**
     * {@inheritDoc}
     */
    public void visualize(Repository repository, Group group, VisualizationParameter visParam) {
        // Initialization before visualization
        initialize();
        // Prepare fresnel document for rendering procedure
        FresnelDocument fd = prepareFresnelDocument(group);
        // Render fresnel document and save it to result file
        renderFresnelDocumentToOutputFile(fd, repository, visParam, null);
        // Show resulting html file in preview panel (using integrated LOBO browser)
        showResultInPreviewPanel(null);
    }

    protected void initialize() {
        File outputFile = new File(defaultOutputFilename);
        outputFile.delete();
    }

    protected FresnelDocument prepareFresnelDocument(Group group) {

        FresnelDocument fd = new FresnelDocument();

        List<Group> groups = new ArrayList<Group>();
        groups.add(group);

        if (group.getLenses() == null) {
            fd.setLenses(new ArrayList<Lens>());
        } else {
            fd.setLenses(new ArrayList<Lens>(group.getLenses()));
        }

        if (group.getFormats() == null) {
            fd.setFormats(new ArrayList<Format>());
        } else {
            fd.setFormats(new ArrayList<Format>(group.getFormats()));
        }

        fd.setGroups(groups);

        return fd;
    }

    protected FresnelDocument prepareFresnelDocument(URI groupUri) {

        // get group which should be used for visualization
//        FresnelRepositoryDao fresnelDao = ContextHolder.getInstance().getFresnelRepositoryDao();
        Group group = ContextHolder.getInstance().getFresnelDocumentDao().getGroup(groupUri.toString());

        // display error message if no group with given URI is found
        if (group == null) {
            LOG.error("Fresnel Group '{}' for visualization not found!", groupUri);
            new MessageDialog(FresnelApplication.getApp().getBaseFrame(), "Visualization problem occured.",
                    "Visualization of RDF could not be performed. Fresnel Group '" + groupUri
                    + "' for visualization not found!").setVisible(true);
            return null;
        }

        List<Group> groups = new ArrayList<Group>();
        groups.add(group);

        // Create Fresnel Document instance which will be passed to renderer
        FresnelDocument fd = new FresnelDocument();
        fd.setLenses(new ArrayList<Lens>(group.getLenses()));
        fd.setFormats(new ArrayList<Format>(group.getFormats()));
        fd.setGroups(groups);

        return fd;
    }

    /**
     * Renders Fresnel Document to XML and applies XSL template on it. Result is then stored as a
     * file.
     *
     * @param fd
     *            Fresnel Document to be rendered
     * @param repository
     *            repository containing RDF to be rendered
     */
    // TODO: Filename could be parameter
    protected void renderFresnelDocumentToOutputFile(FresnelDocument fd, Repository repository,
            VisualizationParameter visParam, String pathToOutputFile) {

        SesameRenderer renderer = new SesameRenderer();

//        ContextHolder.getInstance().getFresnelRepositoryDao().printStatements(System.out, false);

        File finalFile = null;
        if (pathToOutputFile == null) {
            finalFile = new File(defaultOutputFilename);
        } else {
            finalFile = new File(pathToOutputFile);
        }

        try {
            Document document = renderer.render(fd, repository);

            if (visParam.getFontSize() >0 || visParam.getPicHeight() >0 || visParam.getRectWidth() >0 || visParam.getPropLine1Lenght() >0 || visParam.getPropLine2Lenght() >0) {
                document = modifyInternalXML(document, visParam);
            } else {
                document = modifyInternalXML(document);
            }

            // Prepare the DOM document for writing
            DOMSource source = new DOMSource(document);

            TransformerFactory tFactory = TransformerFactory.newInstance();
//            Transformer transformer = tFactory.newTransformer();  // pro vypsani interniho xml bez transformace
            Transformer transformer = tFactory.newTransformer(new StreamSource(XSLTemplate));

            // Set parameters to XSL template
            transformer.setParameter("pageTitle", visParam.getPageTitle());
            transformer.setParameter("cssStylesheetURL", visParam.getCssStylesheetURL());
            // Perform XSL transformation and write result into file

            transformer.transform(source, new StreamResult(new FileOutputStream(finalFile)));

            LOG.info("Final document has been written to file: " + defaultOutputFilename);

        } catch (Exception ex) {
            LOG.error("Transformation error: {}", ex.getMessage());
            // FIXME: Add proper stack trace logging.
            ex.printStackTrace();
            return;
        }
    }

    /**
     * This method can modify internal XML file if neccessary.
     * @param doc source document
     * @return modified DOM document
     */
    protected Document modifyInternalXML(Document doc) {
        return doc; // no changes
    }

    protected Document modifyInternalXML(Document doc, VisualizationParameter visParam) {
        return doc; // no changes
    }

    /**
     * Displays result of visualization
     * @param pathToOutputFile path to file that will be used as output file
     */
    protected abstract void showResultInPreviewPanel(String pathToOutputFile);
}
