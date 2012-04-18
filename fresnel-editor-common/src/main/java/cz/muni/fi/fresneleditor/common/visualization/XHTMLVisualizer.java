
package cz.muni.fi.fresneleditor.common.visualization;

import cz.muni.fi.fresneleditor.common.BrowserUtils;
import cz.muni.fi.fresneleditor.common.FresnelApplication;
import cz.muni.fi.fresneleditor.common.FresnelEditorConstants;
import cz.muni.fi.fresneleditor.common.utils.URIUtils;
import java.io.File;

/**
 *
 * @author Honza
 */
public class XHTMLVisualizer extends AbstractVisualizer {

    public XHTMLVisualizer() {
        super(FresnelEditorConstants.DEFAULT_FILENAME_FINAL_XHTML, FresnelEditorConstants.DEFAULT_XSL_XHTML_TEMPLATE_URL);
    }   

    /**
     * displays result in Lobo browser
     * @param pathToOutputFile
     */
    @Override
    protected void showResultInPreviewPanel(String pathToOutputFile) {
        {

            try {

                File finalXhtmlFile = null;
                if (pathToOutputFile == null) {
                    finalXhtmlFile = new File(FresnelEditorConstants.DEFAULT_FILENAME_FINAL_XHTML);
                } else {
                    finalXhtmlFile = new File(pathToOutputFile);
                }

                // Construct page URI
                // TODO: Ensure platform independent representation!
                String pageUri = URIUtils.addStylesheetURLPrefix(finalXhtmlFile.getAbsolutePath());

                LOG.info("Visualization of page on URI: " + pageUri);
                BrowserUtils.navigate(pageUri);

            } catch (Exception ex) {
                LOG.error("Rendering error: {}", ex.getMessage());
                // FIXME: Add proper stack trace logging.
                ex.printStackTrace();
                return;
            }
        }
    }
}
