/**
 * Fresnel Editor
 * 
 * SVG Preprocessor
 */
package cz.muni.fi.fresneleditor.common.visualization.preprocessor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.gvt.GraphicsNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGTextElement;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *  A class containing utility methods
 * 
 * @author Milos Kalab <173388@mail.muni.cz>
 * @version 5. 5. 2012
 */
public class Utils {

    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
    private static Utils instance = null;

    protected Utils() {
    }

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public Document parseStringToDocument(String input) {
        InputSource is = new InputSource(new StringReader(input));
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = null;

        try {
            dbf.setValidating(false);
            dbf.setFeature("http://xml.org/sax/features/namespaces", false);
            dbf.setFeature("http://xml.org/sax/features/validation", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        } catch (ParserConfigurationException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("DocumentBuilderFactory cannot support some feature(s)");
        }
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(is);
        } catch (ParserConfigurationException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("DocumentBuilder cannot be created");
        } catch (SAXException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("Parsing error occured during parsing attempt");
        } catch (IOException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("IO errors occured during parsing attempt");
        }
        return doc;
    }

    public Document parseFileToDocument(File input) {
        InputSource is = null;
        try {
            is = new InputSource(new FileReader(input));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            dbf.setValidating(false);
            dbf.setFeature("http://xml.org/sax/features/namespaces", false);
            dbf.setFeature("http://xml.org/sax/features/validation", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        } catch (ParserConfigurationException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("DocumentBuilderFactory cannot support some feature(s)");
        }
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(is);
        } catch (ParserConfigurationException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("DocumentBuilder cannot be created");
        } catch (SAXException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("Parsing error occured during parsing attempt");
        } catch (IOException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("IO errors occured during parsing attempt");
        }
        return document;
    }

    public String parseDocumentToString(Document input) {
        try {
            DOMSource domSource = new DOMSource(input);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("Error occured during transformation attempt");
            return null;
        }
    }

    public String parseFileToString(File input) {
        byte[] buffer = new byte[(int) input.length()];
        BufferedInputStream f = null;
        try {
            f = new BufferedInputStream(new FileInputStream(input));
            f.read(buffer);
        } catch (IOException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("Cannot read the file");
            return null;
        } finally {
            if (f != null) {
                try {
                    f.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return new String(buffer);
    }

    public synchronized float computeTextLenght(String text, int fontSize) {

        DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();
        Document document = domImpl.createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);

        Element root = document.getDocumentElement();
        Element textElm = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "text");
        Text textElmValue = document.createTextNode(text);
        textElm.setAttributeNS(null, "font-size", Integer.toString(fontSize));
        textElm.appendChild(textElmValue);
        root.appendChild(textElm);
        SVGTextElement svgTextElm = (SVGTextElement) textElm;

        SVGDocument svgDoc = (SVGDocument) document;
        UserAgent userAgent;
        DocumentLoader loader;
        BridgeContext ctx;
        GVTBuilder builder;
        GraphicsNode rootGN;

        userAgent = new UserAgentAdapter();
        loader = new DocumentLoader(userAgent);
        ctx = new BridgeContext(userAgent, loader);
        ctx.setDynamicState(BridgeContext.DYNAMIC);
        builder = new GVTBuilder();
        rootGN = builder.build(ctx, svgDoc);

        float length = svgTextElm.getComputedTextLength();
        return length;
    }

    public int countRows(String text, int length) {
        int numberOfRows = 1;
        int fontSize = SVGPreprocessor.xslSet.getFontSize();
        float textLength = this.computeTextLenght(text, fontSize);
        if (textLength > length) {
            numberOfRows = (int) Math.ceil(textLength / (double) length);
        }
        return numberOfRows;
    }

    public String shortenText(String text, int length) {
        String shortenedText = "";
        int fontSize = SVGPreprocessor.xslSet.getFontSize();
        float textLength = this.computeTextLenght(text, fontSize);
        if (textLength > length) {
            double ratio = textLength / (double) length;
            shortenedText = text.substring(0, (int) Math.ceil(text.length() / ratio));
            if (shortenedText.length() > 5) {
                shortenedText = shortenedText.substring(0, shortenedText.length() - 3) + "...";
            } else {
                shortenedText += "...";
            }
        }
        return shortenedText;
    }
}
