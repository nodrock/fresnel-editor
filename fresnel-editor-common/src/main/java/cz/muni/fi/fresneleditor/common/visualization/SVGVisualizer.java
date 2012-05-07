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
        Document outputDoc = preprocessor.processXMLForSVG(doc);
//        System.out.println("____________________________MUJ KOD_________________________________");
//        System.out.println("________________________________________________________________________");
//        System.out.println("________________________________________________________________________");
//        System.out.println("________________________________________________________________________");
//        System.out.println(Utils.getInstance().parseDocumentToString(outputDoc));
//        System.out.println("________________________________________________________________________");
//        System.out.println("________________________________________________________________________");
//        System.out.println("________________________________________________________________________");
//        System.out.println("________________________________________________________________________");
        return outputDoc;
//       
//        Integer y = 60; //start possition
//        NodeList resourceNodes = doc.getElementsByTagName("resource");
//        for (int r = 0; r < resourceNodes.getLength(); r++) {
//            Integer yRes = y;
//            Element resource = (Element) resourceNodes.item(r);
//            resource.setAttribute("y", yRes.toString());
//            NodeList children = resource.getChildNodes();
////            yRes += 5; // resource margin top
//            for (int p = 0; p < children.getLength(); p++) {  // property
//                Element prop = (Element) children.item(p);
//                if (prop.getNodeName().equals("property")) {
//                    prop.setAttribute("y", yRes.toString());
//                    Element labelEl = (Element) prop.getElementsByTagName("label").item(0);  // label
//                    yRes += LABEL_HEIGHT;
//                    labelEl.setAttribute("height", LABEL_HEIGHT.toString());
//                    labelEl.setAttribute("y", yRes.toString());
//
//                    Element valueEl = (Element) prop.getElementsByTagName("values").item(0).getFirstChild();  //value
////                    valueEl.setAttribute("y", String.valueOf(y));
//                    Integer valueHeight = 0;
//                    // in case of image
//                    if (valueEl.getAttribute("output-type").equals("http://www.w3.org/2004/09/fresnel#image")) {
//                        yRes+=5;
//                        valueEl.setAttribute("y", yRes.toString());
//                        valueEl.setAttribute("width", IMAGE_WIDTH.toString());
//                        valueEl.setAttribute("height", IMAGE_HEIGHT.toString());
//                        yRes += IMAGE_HEIGHT;
//                        valueHeight = IMAGE_HEIGHT + 5;
//                    }
//                    else{
//                        yRes += VALUE_HEIGHT;
//                        valueEl.setAttribute("y", yRes.toString());
//                        valueEl.setAttribute("height", VALUE_HEIGHT.toString()); 
//                        valueHeight = VALUE_HEIGHT;
//                    }
//                    prop.setAttribute("height", String.valueOf(valueHeight + LABEL_HEIGHT));
////                    yRes += 5; //property margin
//                }
//            }
//            resource.setAttribute("height", String.valueOf(yRes - y + 10));
//            y = yRes + RESOURCE_MARGIN_BOTTOM; // y for next resource element
//        }
//        ((Element)doc.getElementsByTagName("results").item(0)).setAttribute("height", y.toString());
//        return doc;
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
