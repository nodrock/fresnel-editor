/**
 * Fresnel Editor
 * 
 * SVG Preprocessor
 */
package cz.muni.fi.fresneleditor.common.visualization.preprocessor;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 *  
 * @author Milos Kalab <173388@mail.muni.cz>
 * @version 5. 5. 2012
 */
public class SVGPreprocessor {
   
    private static final Logger LOG = LoggerFactory.getLogger(SVGPreprocessor.class);
    public static XSLStylesheetSettings xslSet;

    public SVGPreprocessor() {
        LOG.info("Initializing SVG preprocessor");
    }

    public Document processXMLForSVG(Document doc){
        xslSet = new XSLStylesheetSettings();
        xslSet.changeXSLSettings();
        TempXMLParser parser = TempXMLParser.getInstance();
        parser.setStringToParse(doc);
        parser.parse();
        return Utils.getInstance().parseStringToDocument(parser.serializeToOutput());
    }
}
