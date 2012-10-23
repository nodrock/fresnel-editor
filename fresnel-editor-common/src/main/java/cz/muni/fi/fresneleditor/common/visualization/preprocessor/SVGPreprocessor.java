/**
 * Fresnel Editor
 * 
 * SVG Preprocessor
 */
package cz.muni.fi.fresneleditor.common.visualization.preprocessor;

import cz.muni.fi.fresneleditor.common.visualization.VisualizationParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 * Manager class of the preprocessing of the data
 * 
 * @author Milos Kalab <173388@mail.muni.cz>
 * @version 5. 5. 2012
 */
public class SVGPreprocessor {

    private static final Logger LOG = LoggerFactory.getLogger(SVGPreprocessor.class);
    public static XSLStylesheetSettings xslSet;

    public SVGPreprocessor() {
        LOG.info("Initializing SVG preprocessor");
        xslSet = new XSLStylesheetSettings();
    }

    public SVGPreprocessor(VisualizationParameter visParam) {
        LOG.info("Initializing SVG preprocessor");
        xslSet = new XSLStylesheetSettings(visParam);
    }

    /**
	 * A method that manages the whole text preprocessing
	 * 
	 * @param doc the document to be preprocessed
     *  
	 */
    public Document processXMLForSVG(Document doc) {
        xslSet.changeXSLSettings();
        TempXMLParser parser = TempXMLParser.getInstance();
        parser.setStringToParse(doc);
        parser.parse();
        return Utils.getInstance().parseStringToDocument(parser.serializeToOutput());
    }
}
