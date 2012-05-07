/**
 * Fresnel Editor
 * 
 * SVG Preprocessor
 */
package cz.muni.fi.fresneleditor.common.visualization.preprocessor;

import java.io.CharArrayWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *  
 * @author Milos Kalab <173388@mail.muni.cz>
 * @version 5. 5. 2012
 */
public class TempXMLHandler extends DefaultHandler {

    TempXMLParser parser = TempXMLParser.getInstance();
    private CharArrayWriter content = new CharArrayWriter();
    private Resource tempResource;
    private Property tempProperty;
    private AbstractValue tempValue;
    private String lastQName = "";
    private String currentQName = "";
    private String helpValueClass = null, helpValueOutputType = null;

    public TempXMLHandler() {
    }

    @Override
    public void startDocument() {
    }

    @Override
    public void endDocument() {
    }

    @Override
    public void startElement(String namespaceUri, String localName, String qName, Attributes attrs) {
        content.reset();

        if (qName.equalsIgnoreCase("resource")) {
            if (lastQName.equalsIgnoreCase("value")) {
                tempValue = new ResourceValue(helpValueClass, helpValueOutputType, parser.getResourceId());
                tempProperty.addValue(tempValue);
                tempResource = new Resource(attrs.getValue("uri"), "");
                tempResource.setHasParent(Boolean.TRUE);
            } else {
                tempResource = new Resource(attrs.getValue("uri"), "");
            }
            lastQName = "resource";
        }

        if (qName.equalsIgnoreCase("title")) {
            currentQName = "title";
        }

        if (qName.equalsIgnoreCase("property")) {
            String propertyUri = "", propertyClass = "";
            if (attrs.getValue("uri") != null) {
                propertyUri = attrs.getValue("uri");
            }
            if (attrs.getValue("class") != null) {
                propertyClass = attrs.getValue("class");
            }
            tempProperty = new Property(propertyUri, propertyClass, "", "");
            tempResource.addProperty(tempProperty);
        }

        if (qName.equalsIgnoreCase("label")) {
            String propertyLabelClass = "";
            if (attrs.getValue("class") != null) {
                propertyLabelClass = attrs.getValue("class");
            }
            tempProperty.setPropertyLabelClass(propertyLabelClass);
            lastQName = "label";
        }

        if (qName.equalsIgnoreCase("value")) {
            helpValueClass = "";
            helpValueOutputType = "";
            if (attrs.getValue("class") != null) {
                helpValueClass = attrs.getValue("class");
            }
            if (attrs.getValue("output-type") != null) {
                helpValueOutputType = attrs.getValue("output-type");
            }
            lastQName = "value";
        }
    }

    @Override
    public void endElement(String namespaceUri, String localName, String qName) {
        if (currentQName.equals("title")) {
            if (lastQName.equalsIgnoreCase("resource")) {
                tempResource.setResourceLabel(content.toString());
            }
            if (lastQName.equalsIgnoreCase("label")) {
                tempProperty.setPropertyLabel(content.toString());
            }
            if (lastQName.equalsIgnoreCase("value")) {
                tempValue = new SimpleValue(helpValueClass, helpValueOutputType, content.toString());
                tempProperty.addValue(tempValue);
                this.helpValueClass = "";
                this.helpValueOutputType = "";
            }
            lastQName = "";
            currentQName = "";
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        content.write(ch, start, length);
    }
}
