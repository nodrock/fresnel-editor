/*
 * SvgShowJPanel.java
 *
 * Created on 1.6.2010, 20:49:14
 */
package cz.muni.fi.fresneleditor.common.visualization.svgshow;

import cz.muni.fi.fresneleditor.common.visualization.preprocessor.Utils;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author pete
 */
public class SvgShowJPanel extends javax.swing.JPanel {

    private static final Logger LOG = LoggerFactory.getLogger(SvgShowJPanel.class);
    private JSVGCanvas canvas;
    private File file;

    /** Creates new form SvgShowJPanel */
    public SvgShowJPanel(File file) {
        initComponents();

        canvas = new JSVGCanvas();
        this.add(canvas);

        this.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                canvas.setSize(getWidth(), getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });

        try {
            InputStream svgInputStream = new FileInputStream(file);
            //InputStream svgInputStream = getClass().getResourceAsStream("/cz/muni/fi/fresneleditor/common/visualization/svgshow/Dharma_Wheel.svg");
            LoggerFactory.getLogger(this.getClass()).info("Displaying file " + file.getAbsolutePath());
            
            String parserClassName = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parserClassName);
            
            Document doc = f.createDocument(parserClassName, svgInputStream);

        System.out.println("_______________________________VISUALIZE_________________________________");
        System.out.println("________________________________________________________________________");
        System.out.println("________________________________________________________________________");
        System.out.println("________________________________________________________________________");
        System.out.println(Utils.getInstance().parseDocumentToString(doc));
        System.out.println("________________________________________________________________________");
        System.out.println("________________________________________________________________________");
        System.out.println("________________________________________________________________________");
        System.out.println("________________________________________________________________________");
            
            // sets the panel size to size of the displaying svg image

 //           Element svgElement = (Element) doc.getElementsByTagName("svg:svg").item(0);
            Element svgElement = (Element) doc.getElementsByTagName("svg:svg").item(0);
                 
            Integer w = Math.round(Float.parseFloat(svgElement.getAttribute("width")));
            Integer h = Math.round(Float.parseFloat(svgElement.getAttribute("height")));
//            canvas.setSize(w, h);
            canvas.setSize(getWidth(), getHeight());
            this.setPreferredSize(new Dimension(w, h));

            svgInputStream.close();
            canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC); // nutne pro updatovani pri upravach dokumentu (volani getUpdateManager...)
            canvas.setDocument(doc);

            if (doc.getDocumentElement().getAttribute("viewBox").isEmpty()) {
                LoggerFactory.getLogger(this.getClass()).warn("Input SVG file has't set viewBox. Disable autoresize!");
            }
        } catch (IOException ex) {
            LoggerFactory.getLogger(this.getClass()).error(null, ex);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 799, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 562, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
