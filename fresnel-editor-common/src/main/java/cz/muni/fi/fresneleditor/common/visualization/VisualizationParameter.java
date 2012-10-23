/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.common.visualization;

import cz.muni.fi.fresneleditor.common.FresnelEditorConstants;
import cz.muni.fi.fresneleditor.common.utils.URIUtils;

/**
 * Simple JavaBean for holding parameter values influencing visualization
 * process.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 29.6.2009
 */
public class VisualizationParameter {

	public static final String DEFAULT_PAGE_TITLE = "RDF data visualization";

	private String pageTitle = "RDF data visualization";
	//private String cssStylesheetURL = FresnelEditorConstants.DEFAULT_CSS_STYLESHEET_URL;
	private String cssStylesheetURL = FresnelEditorConstants.DEFAULT_SVG_CSS_STYLESHEET_URL;

        private int rectWidth = 0;
        private int picHeight = 0;
        private int fontSize = 0;
        private int propLine1Lenght = 0;
        private int propLine2Lenght = 0;
        
	/**
	 * Default constructor.
	 */
	public VisualizationParameter() {
	}

	/**
	 * Extended constructor.
	 * 
	 * @param pageTitle
	 * @param cssStylesheetURL
	 */
	public VisualizationParameter(String pageTitle, String cssStylesheetURL) {
		this.pageTitle = pageTitle;
		setCssStylesheetURL(cssStylesheetURL);
	}

	/**
	 * @return the pageTitle
	 */
	public String getPageTitle() {
		return pageTitle;
	}

	/**
	 * @param pageTitle
	 *            the pageTitle to set
	 */
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	/**
	 * @return the cssStylesheetURL
	 */
	public String getCssStylesheetURL() {
		return cssStylesheetURL;
	}

	/**
	 * @param cssStylesheetURL
	 *            the cssStylesheetURL to set
	 */
	public void setCssStylesheetURL(String cssStylesheetURL) {
		// FIXME: Only local CSS stylesheets are supported now
		this.cssStylesheetURL = URIUtils
				.addStylesheetURLPrefix(cssStylesheetURL);
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

    public int getRectWidth() {
        return rectWidth;
    }

    public void setRectWidth(int rectWidth) {
        this.rectWidth = rectWidth;
    }        
}
