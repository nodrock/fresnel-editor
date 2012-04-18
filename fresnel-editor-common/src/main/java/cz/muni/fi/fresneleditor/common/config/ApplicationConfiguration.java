/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.common.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.fresneleditor.common.ContextHolder;

/**
 * Class for holding application configuration values - instance is constructed
 * on the basis of XML configuration file (default name "application.xml").
 * 
 * @author Miroslav Warchil
 * @version 14. 3. 2009
 */
@XmlRootElement
public class ApplicationConfiguration implements Serializable {

	private static final Logger LOG = LoggerFactory
			.getLogger(ApplicationConfiguration.class);

	private static final long serialVersionUID = 1L;

	public static String DEFAULT_APPLICATION_CONFIG_FILE = "application.xml";

	/**
	 * The name of the last recently opened project.
	 */
	private String lastOpenProjectUrl;

	/**
	 * Configuration metadata about Sesame RDF repositories. Key is repository
	 * name.
	 */
	Map<String, RepositoryConfiguration> repositoryConfigurations = new HashMap<String, RepositoryConfiguration>();

	/**
	 * Map of application-wide known namespaces. Key is namespace prefix.
	 */
	List<NamespaceConfiguration> namespaceConfigurations = new ArrayList<NamespaceConfiguration>();

	/**
	 * Returns the name of the last opened project. Can return null if the
	 * application was closed and no project was opened at that time or if
	 * currently is no project open.
	 * 
	 * @return the lastProjectConfigurationFileURL
	 */
	public String getLastOpenProjectUrl() {
		return lastOpenProjectUrl;
	}

	/**
	 * @param lastOpenProjectUrl
	 *            the lastOpenProjectName to set
	 */
	public void setLastOpenProjectUrl(String lastOpenProjectUrl) {
		this.lastOpenProjectUrl = lastOpenProjectUrl;
	}

	/**
	 * @return the repositoryConfigurations
	 */
	public Map<String, RepositoryConfiguration> getRepositoryConfigurations() {
		return repositoryConfigurations;
	}

	/**
	 * @param repositoryConfigurations
	 *            the repositoryConfigurations to set
	 */
	public void setRepositoryConfigurations(
			Map<String, RepositoryConfiguration> repositoryConfigurations) {
		this.repositoryConfigurations = repositoryConfigurations;
	}

	/**
	 * Adds new repository configuration to available repositories if there does
	 * not exists a repository with the same name already.
	 * 
	 * @param repositoryConfiguration
	 *            repository configuration to be added. The
	 *            repositoryConfiguration name cannot be empty.
	 * 
	 * @return <br>
	 *         false if the repository was not added because there already
	 *         exists a repository with same name <br>
	 *         true if the repository was added
	 * @throws AddRepositoryException
	 */
	public void addRepositoryConfiguration(
			RepositoryConfiguration repositoryConfiguration)
			throws AddRepositoryException {

		if (repositoryConfiguration.getName() == null
				|| "".equals(repositoryConfiguration.getName())) {
			String message = "Cannot add repository configuration with empty name!";
			LOG.error(message);
			throw new AddRepositoryException(message);
		}

		if (this.repositoryConfigurations.containsKey(repositoryConfiguration
				.getName())) {
			String message = "Cannot create repository configuration with name: '"
					+ repositoryConfiguration.getName()
					+ "'. Repository with that name already exists.";
			LOG.info(message);
			throw new AddRepositoryException(message);
		}

		LOG.info(
				"Adding '{}' repository configuration to available repositories",
				repositoryConfiguration.getName());
		this.repositoryConfigurations.put(repositoryConfiguration.getName(),
				repositoryConfiguration);
	}

	/**
	 * Deletes the repository from Fresnel Editor application.
	 * 
	 * @param repositoryName
	 *            name of repository to be removed
	 */
	public void removeRepositoryConfiguration(String repositoryName) {

		// fixme igor: remove this repository configuration from all projects...
		// currently it is not possible to delete repository if it is used by a
		// project..

		// check if the repository is not currently active
		if (ContextHolder.getInstance().isActiveRepository(repositoryName)) {
			LOG.error("Cannot remove repository '"
					+ repositoryName
					+ "' because it is currently used for fresnel or data storage. Close project first.");
			return;
		}

		if (!this.repositoryConfigurations.containsKey(repositoryName)) {
			LOG.error("Repository configuration for repository '"
					+ repositoryName + "' cannnot be found!");
			return;
		}

		this.repositoryConfigurations.remove(repositoryName);
	}

	/**
	 * @return the namespaceConfigurations
	 */
	public List<NamespaceConfiguration> getNamespaceConfigurations() {
		return namespaceConfigurations;
	}

	/**
	 * @param namespaceConfigurations
	 *            the namespaceConfigurations to set
	 */
	public void setNamespaceConfigurations(
			List<NamespaceConfiguration> namespaceConfigurations) {
		this.namespaceConfigurations = namespaceConfigurations;
	}

	/**
	 * @param namespaceConfiguration
	 *            namespace configuration to be added
	 */
	public void addNamespaceConfiguration(
			NamespaceConfiguration namespaceConfiguration) {
		this.namespaceConfigurations.add(namespaceConfiguration);
	}

	/**
	 * @param namespaceConfiguration
	 *            namespace configuration to be deleted (equals method is used
	 *            for equality evaluation)
	 */
	public void removeNamespaceConfiguration(
			NamespaceConfiguration namespaceConfiguration) {
		this.namespaceConfigurations.remove(namespaceConfiguration);
	}

}
