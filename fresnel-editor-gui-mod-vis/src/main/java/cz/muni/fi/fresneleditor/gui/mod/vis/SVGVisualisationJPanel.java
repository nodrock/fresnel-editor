
package cz.muni.fi.fresneleditor.gui.mod.vis;

import cz.muni.fi.fresneleditor.common.FresnelEditorConstants;
import cz.muni.fi.fresneleditor.common.visualization.SVGVisualizer;
import java.io.File;

/**
 * Panel for SVG visualization
 * @author Jan Kolinek
 * @version 25.5.2010
 */
public class SVGVisualisationJPanel extends VisualizationJPanel{

    public SVGVisualisationJPanel() {
        super(new SVGVisualizer(),bundle.getString("SVG Visualization"));
        File f = new File(FresnelEditorConstants.DEFAULT_SVG_CSS_STYLESHEET_URL);
        super.setDefaultCssStylesheet(f.getAbsolutePath());
    }


}
