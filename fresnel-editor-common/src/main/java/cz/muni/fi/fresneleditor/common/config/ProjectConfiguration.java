/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.common.config;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.util.StringUtils;

/**
 * Class for holding project configuration values.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz), Igor Zemsky
 *         (zemsky@mail.muni.cz)
 * @version 14.3.2009
 */
@XmlRootElement
public class ProjectConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;

	// TODO: Cleanup not used code in this class.

	/**
	 * URL of project configuration file (default: 'project.xml').
	 */
	// private String projectConfigurationFileURL;

	/** The project name */
	private String name;

	private String description;

	/** True if it is a sample project, false otherwise. **/
	private boolean sample;

	private String fresnelRepositoryName;

	private String dataRepositoryName;

	// TODO: Add URL suffix
	private String cssStylesheetFileName;

	private Map<String, CssSnippetConfiguration> cssSnippets = new HashMap<String, CssSnippetConfiguration>();

	public ProjectConfiguration() {
	}

	public ProjectConfiguration(ProjectConfiguration configuration) {
		setName(configuration.name);
		// setProjectConfigurationFileURL(configuration.projectConfigurationFileURL);
		this.description = configuration.description;
		this.fresnelRepositoryName = configuration.fresnelRepositoryName;
		this.dataRepositoryName = configuration.dataRepositoryName;
		this.cssStylesheetFileName = configuration.cssStylesheetFileName;
		this.cssSnippets = new HashMap<String, CssSnippetConfiguration>(
				configuration.cssSnippets);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set. Must be a non empty String.
	 * 
	 * @throws IllegalArgumentException
	 *             if the name does not have any text
	 */
	public void setName(String name) {
		if (!StringUtils.hasText(name)) {
			throw new IllegalArgumentException(
					"The project configuration has to have "
							+ "valid name set. The name was: '" + name + "'");
		}
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the fresnelRepositoryName
	 */
	public String getFresnelRepositoryName() {
		return fresnelRepositoryName;
	}

	/**
	 * @param fresnelRepositoryName
	 *            the fresnelRepositoryName to set
	 */
	public void setFresnelRepositoryName(String fresnelRepositoryName) {
		this.fresnelRepositoryName = fresnelRepositoryName;
	}

	/**
	 * @return the cssStylesheetFileName
	 */
	public String getCssStylesheetFileName() {
		return cssStylesheetFileName;
	}

	/**
	 * @param cssStylesheetFileName
	 *            the cssStylesheetFileName to set
	 */
	public void setCssStylesheetFileName(String cssStylesheetFileName) {
		this.cssStylesheetFileName = cssStylesheetFileName;
	}

	/**
	 * Verifies if CSS stylesheet file really exists.
	 * 
	 * @return true if file exists, false otherwise
	 */
	public boolean isCssStylesheetFileNameValid() {
		File file = new File(cssStylesheetFileName);
		return file.exists();
	}

	/**
	 * @return the cssSnippets
	 */
	public Map<String, CssSnippetConfiguration> getCssSnippets() {
		return cssSnippets;
	}

	/**
	 * @param cssSnippets
	 *            the cssSnippets to set
	 */
	public void setCssSnippets(Map<String, CssSnippetConfiguration> cssSnippets) {
		this.cssSnippets = cssSnippets;
	}

	/**
	 * @param cssSnippet
	 *            CSS snippet to be added to project configuration
	 */
	public void addCssSnippet(CssSnippetConfiguration cssSnippet) {
		this.cssSnippets.put(cssSnippet.getName(), cssSnippet);
	}

	/**
	 * @param cssSnippetName
	 *            name of CSS snippet to be deleted
	 */
	public void deleteCssSnippet(String cssSnippetName) {
		this.cssSnippets.remove(cssSnippetName);
	}

	/**
	 * @return the sample
	 */
	public boolean isSample() {
		return sample;
	}

	/**
	 * @param sample
	 *            the sample to set
	 */
	public void setSample(boolean sample) {
		this.sample = sample;
	}

	/**
	 * @param projectConfigurationFileURL
	 *            the URL to set. Must be a not empty String.
	 * 
	 * @throws IllegalArgumentException
	 *             if the URL has no text
	 */
	// public void setProjectConfigurationFileURL(String
	// projectConfigurationFileURL) {
	// if (!StringUtils.hasText(projectConfigurationFileURL)) {
	// throw new
	// IllegalArgumentException("The project configuration has to have " +
	// "valid configuration file URL set. The URL was: '" +
	// projectConfigurationFileURL + "'");
	// }
	// this.projectConfigurationFileURL = projectConfigurationFileURL;
	// }

	// public String getProjectConfigurationFileURL() {
	// return projectConfigurationFileURL;
	// }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cssSnippets == null) ? 0 : cssSnippets.hashCode());
		result = prime
				* result
				+ ((cssStylesheetFileName == null) ? 0 : cssStylesheetFileName
						.hashCode());
		result = prime
				* result
				+ ((dataRepositoryName == null) ? 0 : dataRepositoryName
						.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime
				* result
				+ ((fresnelRepositoryName == null) ? 0 : fresnelRepositoryName
						.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		// result = prime
		// * result
		// + ((projectConfigurationFileURL == null) ? 0
		// : projectConfigurationFileURL.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectConfiguration other = (ProjectConfiguration) obj;
		if (cssSnippets == null) {
			if (other.cssSnippets != null)
				return false;
		} else if (!cssSnippets.equals(other.cssSnippets))
			return false;
		if (cssStylesheetFileName == null) {
			if (other.cssStylesheetFileName != null)
				return false;
		} else if (!cssStylesheetFileName.equals(other.cssStylesheetFileName))
			return false;
		if (dataRepositoryName == null) {
			if (other.dataRepositoryName != null)
				return false;
		} else if (!dataRepositoryName.equals(other.dataRepositoryName))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (fresnelRepositoryName == null) {
			if (other.fresnelRepositoryName != null)
				return false;
		} else if (!fresnelRepositoryName.equals(other.fresnelRepositoryName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		// if (projectConfigurationFileURL == null) {
		// if (other.projectConfigurationFileURL != null)
		// return false;
		// } else if (!projectConfigurationFileURL
		// .equals(other.projectConfigurationFileURL))
		// return false;
		return true;
	}

}
