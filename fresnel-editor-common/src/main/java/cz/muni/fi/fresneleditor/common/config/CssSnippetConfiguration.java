/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.common.config;

/**
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 14.3.2009
 */
public class CssSnippetConfiguration {

	private String name;

	private String snippet;

	public CssSnippetConfiguration() {
	}

	public CssSnippetConfiguration(String name, String snippet) {
		this.name = name;
		this.snippet = snippet;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the snippet
	 */
	public String getSnippet() {
		return snippet;
	}

	/**
	 * @param snippet
	 *            the snippet to set
	 */
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

}
