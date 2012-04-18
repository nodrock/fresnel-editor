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
	private String cssStylesheetURL = FresnelEditorConstants.DEFAULT_CSS_STYLESHEET_URL;

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
}
