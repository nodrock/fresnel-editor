
package cz.muni.fi.fresneleditor.gui.mod.vis;

import java.io.File;

import cz.muni.fi.fresneleditor.common.FresnelEditorConstants;
import cz.muni.fi.fresneleditor.common.visualization.XHTMLVisualizer;


/**
 * Panel for HTML visualization
 * @author Jan Kolinek
 * @version 25.5.2010
 */
public class XHTMLVisualisationJPanel extends VisualizationJPanel{

    public XHTMLVisualisationJPanel() {
        super(new XHTMLVisualizer(), bundle.getString("HTML Visualization"));
        File f = new File(FresnelEditorConstants.DEFAULT_CSS_STYLESHEET_URL);
        super.setDefaultCssStylesheet(f.getAbsolutePath());
        super.setVisibilitySVGVizSetting(false);
    }


}
