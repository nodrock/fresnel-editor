/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.fresneleditor.common.visualization.preprocessor;

import cz.muni.fi.fresneleditor.common.FresnelEditorConstants;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Parser of the intermediate XML file. It holds the resources that are to be 
 * visualized and manages IDs of parsed objects
 * 
 * @author Milos Kalab <173388@mail.muni.cz>
 */
public class TempXMLParser {

    private static final Logger LOG = LoggerFactory.getLogger(SVGPreprocessor.class);
    
    private static TempXMLParser instance = null;
    private int resourceId = 0;
    private int propertyId = 0;
    private int valueId = 0;
    private Map<Integer, Resource> resourceMap;
    private String stringToParse = null;

    protected TempXMLParser() {
        resourceMap = new HashMap<Integer, Resource>();
    }

    public static TempXMLParser getInstance() {
        if (instance == null) {
            instance = new TempXMLParser();
        }
        return instance;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void incrementPropertyId() {
        this.propertyId++;
    }

    public void incrementResourceId() {
        this.resourceId++;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void incrementValueId() {
        this.valueId++;
    }

    public int getValueId() {
        return valueId;
    }

    public Map<Integer, Resource> getResourceMap() {
        return resourceMap;
    }

    public void addResourceToMap(Resource resource) {
        try {
            this.resourceMap.put(resource.getResourceId(), resource);
        } catch (ClassCastException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("The information can't be stored in this collection");
        } catch (IllegalArgumentException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("The information can't be stored in this collection");
        }
    }

    public Resource getResourceFromMap(int resourceId) {
        Resource resource = null;
        try {
            resource = this.resourceMap.get(resourceId);
        } catch (ClassCastException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("The key does not match the type used in the collection");
        }
        return resource;
    }

    public void setStringToParse(String stringToParse) {
        this.stringToParse = stringToParse;
    }

    public void setStringToParse(File fileToParse) {
        this.stringToParse = Utils.getInstance().parseDocumentToString(Utils.getInstance().parseFileToDocument(fileToParse));
    }

    public void setStringToParse(Document documentToParse) {
        this.stringToParse = Utils.getInstance().parseDocumentToString(documentToParse);
    }

    public void parse() {
        TempXMLHandler handler = new TempXMLHandler();
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(new InputSource(new StringReader(stringToParse)), handler);
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
    }

    public String serializeToOutput() {
        String output = null;
        if (!resourceMap.isEmpty()) {
            output = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            output += "<?xml-stylesheet type=\"text/xsl\" href=\"";
            output += FresnelEditorConstants.DEFAULT_XSL_SVG_TEMPLATE_URL;
            output += "\"?>\n";
            output += "<!DOCTYPE results PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" ";
            output += "\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n";
            output += "<results xmlns=\"http://www.w3.org/2004/09/fresnel-tree\">\n";

            Iterator itRes = resourceMap.entrySet().iterator();
            while (itRes.hasNext()) {
                Map.Entry entry = (Map.Entry) itRes.next();
                Resource tempResource = (Resource) entry.getValue();
                if(!tempResource.getHasParent()){output += tempResource.toString();}
                itRes.remove();
            }
            output += "</results>\n";
            return output;
        } else {
            return output;
        }
    }
}
