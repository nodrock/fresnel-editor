/**
 * Fresnel Editor
 * 
 * SVG Preprocessor
 */
package cz.muni.fi.fresneleditor.common.visualization.preprocessor;

import cz.muni.fi.fresneleditor.common.FresnelEditorConstants;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Milos Kalab <173388@mail.muni.cz>
 * @version 5. 5. 2012
 */
public class XSLStylesheetSettings {

    private static final Logger LOG = LoggerFactory.getLogger(XSLStylesheetSettings.class);
    public static final Namespace XSLNAMESPACE = Namespace.getNamespace("xsl", "http://www.w3.org/1999/XSL/Transform");
    
    private int rectWidth = 200;
    private int picHeight = 200;
    private int fontSize = 12;
    private int rectResXInd = 20;
    private int propLine1Lenght = 100;
    private int propLine2Lenght = 100;

    public XSLStylesheetSettings() {
    }

    public XSLStylesheetSettings(int rectWidth, int picHeight, int fontSize, int rectResXInd, int propLine1Lenght, int propLine2Lenght) {
        if (rectWidth != 0 || picHeight != 0 || fontSize != 0 || rectResXInd != 0 || propLine1Lenght != 0 || propLine2Lenght != 0) {
            this.rectWidth = rectWidth;
            this.picHeight = picHeight;
            this.fontSize = fontSize;
            this.rectResXInd = rectResXInd;
            this.propLine1Lenght = propLine1Lenght;
            this.propLine2Lenght = propLine2Lenght;
        }
    }

//    public void getCSSStylesheetPath() {
//        SAXBuilder builder = new SAXBuilder();
//        File file = new File(SVGPreprocessorConstants.DEFAULT_XSL_SVG_TEMPLATE_URL);
//
//        try {
//            Document document = (Document) builder.build(file);
//            Element rootNode = document.getRootElement();
//
//            Element svgNode = rootNode.getChild("template", SVGPreprocessorConstants.XSLNAMESPACE).getChild("svg", SVGPreprocessorConstants.SVGNAMESPACE);
//            String styleText = svgNode.getChild("style", SVGPreprocessorConstants.SVGNAMESPACE).getText();
//            String[] textArray = styleText.split(" ");
//            ///////////////////////// ADD THE STRINGS TO CSSSTYLESHEETSETTINGS
//            String typeText = textArray[0].trim();
//            System.out.println(typeText);
//            System.out.println(textArray[1]);
//            String filePath = textArray[1].substring(4, textArray[1].length());
//            filePath = filePath.substring(0, filePath.length()-2);
//            System.out.println("yoooooooooou_"+filePath);
//            ///////////////////////// ADD THE STRINGS TO CSSSTYLESHEETSETTINGS
//
//
//        } catch (IOException ex) {
//            LOGGER.log(Level.SEVERE, "DocumentBuilder cannot be created", ex);
//        } catch (JDOMException ex) {
//            LOGGER.log(Level.SEVERE, "DocumentBuilder cannot be created", ex);
//        }
//    }

    public void changeXSLSettings() {

        SAXBuilder builder = new SAXBuilder();
        File file = new File(FresnelEditorConstants.DEFAULT_XSL_SVG_TEMPLATE_URL);

        try {
            Document document = (Document) builder.build(file);
            Element rootNode = document.getRootElement();

            List parameters = rootNode.getChildren("param", XSLNAMESPACE);
            for (Iterator it = parameters.iterator(); it.hasNext();) {
                Element param = (Element) it.next();
                if (param.getAttribute("name").getValue().equals("rect_width")) {
                    param.setText(Integer.toString(rectWidth));
                }
                if (param.getAttribute("name").getValue().equals("pic_width")) {
                    param.setText(Integer.toString(rectWidth));
                }
                if (param.getAttribute("name").getValue().equals("pic_height")) {
                    param.setText(Integer.toString(picHeight));
                }
                if (param.getAttribute("name").getValue().equals("font_size")) {
                    param.setText(Integer.toString(fontSize));
                }
                if (param.getAttribute("name").getValue().equals("rect_res_x_ind")) {
                    param.setText(Integer.toString(rectResXInd));
                }
                if (param.getAttribute("name").getValue().equals("prop_line_ind_1")) {
                    param.setText(Integer.toString(propLine1Lenght));
                }
                if (param.getAttribute("name").getValue().equals("prop_line_ind_2")) {
                    param.setText(Integer.toString(propLine2Lenght));
                }
                XMLOutputter xmlOutput = new XMLOutputter();
                xmlOutput.output(document, new FileWriter(file));
            }
        } catch (IOException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("DocumentBuilder cannot be created");
        } catch (JDOMException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("DocumentBuilder cannot be created");
        }
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(int picHeight) {
        this.picHeight = picHeight;
    }

    public int getPropLine1Lenght() {
        return propLine1Lenght;
    }

    public void setPropLine1Lenght(int propLine1Lenght) {
        this.propLine1Lenght = propLine1Lenght;
    }

    public int getPropLine2Lenght() {
        return propLine2Lenght;
    }

    public void setPropLine2Lenght(int propLine2Lenght) {
        this.propLine2Lenght = propLine2Lenght;
    }

    public int getRectResXInd() {
        return rectResXInd;
    }

    public void setRectResXInd(int rectResXInd) {
        this.rectResXInd = rectResXInd;
    }

    public int getRectWidth() {
        return rectWidth;
    }

    public void setRectWidth(int rectWidth) {
        this.rectWidth = rectWidth;
    }
}
