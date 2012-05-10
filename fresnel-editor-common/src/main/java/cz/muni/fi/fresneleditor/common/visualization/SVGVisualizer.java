package cz.muni.fi.fresneleditor.common.visualization;

import cz.muni.fi.fresneleditor.common.FresnelApplication;
import cz.muni.fi.fresneleditor.common.FresnelEditorConstants;
import cz.muni.fi.fresneleditor.common.utils.URIUtils;
import cz.muni.fi.fresneleditor.common.visualization.preprocessor.SVGPreprocessor;
import cz.muni.fi.fresneleditor.common.visualization.preprocessor.Utils;
import cz.muni.fi.fresneleditor.common.visualization.svgshow.SvgShowJPanel;
import java.io.File;
import javax.swing.JScrollPane;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Visualization of data as a SVG document
 * @author Jan Kolinek
 * @version 25.5.2010
 */
public class SVGVisualizer extends AbstractVisualizer {

    final Integer LABEL_HEIGHT = 20;
    final Integer VALUE_HEIGHT = 20;
    final Integer IMAGE_HEIGHT = 128;
    final Integer IMAGE_WIDTH= 128;
    final Integer RESOURCE_MARGIN_BOTTOM = 25;

    public SVGVisualizer() {
        super(FresnelEditorConstants.DEFAULT_FILENAME_FINAL_SVG, FresnelEditorConstants.DEFAULT_XSL_SVG_TEMPLATE_URL);
    }

    /**
     * Adds y coordinate and height as attributes to elements in the internal xml.
     * @param doc
     * @return
     */
    @Override
    protected Document modifyInternalXML(Document doc) {
        SVGPreprocessor preprocessor = new SVGPreprocessor();
        return preprocessor.processXMLForSVG(doc);
    }
    
    /**
     * Adds y coordinate and height as attributes to elements in the internal xml.
     * @param doc
     * @return
     */
    protected Document modifyInternalXML(Document doc, VisualizationParameter visParam) {
        SVGPreprocessor preprocessor = new SVGPreprocessor(visParam);
        return preprocessor.processXMLForSVG(doc);
    }

    @Override
    protected void showResultInPreviewPanel(String pathToOutputFile) {
        try {

            File SVGFile = null;
            if (pathToOutputFile == null) {
                SVGFile = new File(FresnelEditorConstants.DEFAULT_FILENAME_FINAL_SVG);
            } else {
                SVGFile = new File(pathToOutputFile);
            }

            String pageUri = SVGFile.getAbsolutePath();

            LOG.info("Visualization of file on URI: " + pageUri);

            
            
            //XXXXXXXXXXXXXXXXXXXXXXXXXXX HERE TO INSERT PREPROCESSOR!
            
            
            // Show visualization preview panel
            SvgShowJPanel panel = new SvgShowJPanel(SVGFile);
            // Adds scroll bars to panel
            JScrollPane scrollpane = new JScrollPane(panel);

            FresnelApplication.getApp().updatePreviewPanel(scrollpane);
            FresnelApplication.getApp().hidePreviewPanel();
            FresnelApplication.getApp().showPreviewPanel();

        } catch (Exception ex) {
            LOG.error("Rendering error: {}", ex.getMessage());
            // FIXME: Add proper stack trace logging.
            ex.printStackTrace();
            return;
        }

    }
}
