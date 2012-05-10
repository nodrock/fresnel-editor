/**
 * Fresnel Editor
 * 
 * SVG Preprocessor
 */
package cz.muni.fi.fresneleditor.common.visualization.preprocessor;

import cz.muni.fi.fresneleditor.common.FresnelEditorConstants;
import cz.muni.fi.fresneleditor.common.visualization.VisualizationParameter;
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
    private int textResXInd = 35;
    private int propLine1Lenght = 100;
    private int propLine2Lenght = 100;

    public XSLStylesheetSettings() {
    }

    public XSLStylesheetSettings(int rectWidth, int picHeight, int fontSize, int rectResXInd, int propLine1Lenght, int propLine2Lenght) {
        if (rectWidth != 0 || picHeight != 0 || fontSize != 0 || propLine1Lenght != 0 || propLine2Lenght != 0) {
            this.rectWidth = rectWidth;
            this.picHeight = picHeight;
            this.fontSize = fontSize;
            if (rectResXInd != 0) {
                this.rectResXInd = rectResXInd;
            }
            this.propLine1Lenght = propLine1Lenght;
            this.propLine2Lenght = propLine2Lenght;
        }
    }

    public XSLStylesheetSettings(VisualizationParameter visParam) {
        System.out.println("TESTOVANI GUI PARAMETRU __ "+visParam.getRectWidth()+" __ "+visParam.getRectWidth()+" __ "+visParam.getFontSize()+" __ "+visParam.getPropLine1Lenght()+" __ "+visParam.getPropLine2Lenght());
        this.rectWidth = visParam.getRectWidth();
        this.picHeight = visParam.getPicHeight();
        this.fontSize = visParam.getFontSize();
        this.propLine1Lenght = visParam.getPropLine1Lenght();
        this.propLine2Lenght = visParam.getPropLine2Lenght();
    }
    
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
                if (param.getAttribute("name").getValue().equals("rect_res_text_x_ind")) {
                    param.setText(Integer.toString(textResXInd));
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

    public int getTextResXInd() {
        return textResXInd;
    }

    public void setTextResXInd(int textResXInd) {
        this.textResXInd = textResXInd;
    }
}
