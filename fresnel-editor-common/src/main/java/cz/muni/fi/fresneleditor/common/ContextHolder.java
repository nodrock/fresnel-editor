package cz.muni.fi.fresneleditor.common;

import cz.muni.fi.fresneleditor.common.FresnelEditorConstants.Transformations;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.openrdf.model.Namespace;
import org.openrdf.model.impl.NamespaceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import cz.muni.fi.fresneleditor.common.config.ApplicationConfiguration;
import cz.muni.fi.fresneleditor.common.config.ProjectConfiguration;
import cz.muni.fi.fresneleditor.common.config.RepositoryConfiguration;
import cz.muni.fi.fresneleditor.common.guisupport.MessageDialog;
import cz.muni.fi.fresneleditor.common.utils.CannotOpenProjectException;
import cz.muni.fi.fresneleditor.common.utils.ConfigurationUtils;
import cz.muni.fi.fresneleditor.common.utils.GuiUtils;
import cz.muni.fi.fresneleditor.common.utils.LoadConfigurationException;
import cz.muni.fi.fresneleditor.common.utils.SaveConfigurationException;
import cz.muni.fi.fresneleditor.model.BaseRepositoryDao;
import cz.muni.fi.fresneleditor.model.DataImportException;
import cz.muni.fi.fresneleditor.model.DataRepositoryDao;
import cz.muni.fi.fresneleditor.model.FresnelRepositoryDao;
import cz.muni.fi.fresneleditor.model.BaseRepositoryDao.RepositoryDomain;
import fr.inria.jfresnel.Constants;

/**
 * Singleton. Main access point to currently open repository DAOs and also
 * application and project configuration.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz), Miroslav warchil
 *         (warmir@mail.muni.cz)
 * @version 14. 3. 2009
 */
public class ContextHolder {

	private static final Logger LOG = LoggerFactory
			.getLogger(ContextHolder.class);

	/**
	 * Singleton instance.
	 */
	private static ContextHolder instance;

	/**
	 * URL of application configuration file (default: 'application.xml').
	 * Cannot be null otherwise singleton instance cannot be created.
	 */
	private final String applicationConfigurationFileURL;

	/**
	 * Loaded application configuration. Cannot be null - loading is done in
	 * constructor.
	 */
	private final ApplicationConfiguration applicationConfiguration;

	/**
	 * Loaded project configuration.<br>
	 * When no project is open this property is null.
	 */
	private ProjectConfiguration projectConfiguration;

	/**
	 * Path to currently opened project - null if no project is opened.
	 */
	// private String projectConfigurationFileURL;
	/**
	 * Domain dao.<br>
	 * When no project is open this property is null.
	 */
	private DataRepositoryDao domainDao;

	/**
	 * Fresnel dao.<br>
	 * When no project is open this property is null.
	 */
	private FresnelRepositoryDao fresnelDao;

        private String selectedDataRepositoryName;

        public String getSelectedDataRepositoryName() {
            return selectedDataRepositoryName;
        }

        public void setSelectedDataRepositoryName(String selectedDataRepositoryName) {
            this.selectedDataRepositoryName = selectedDataRepositoryName;
        }
        
        
        
    /**
	 * Selected transformation.<br>
         * default: XHTML
	 */
	private FresnelEditorConstants.Transformations transformation;

	public static ContextHolder getInstance() {

		if (instance == null) {
			// TODO: Allow changing of default application config file?
			LOG.info("Creating instance of ContextHolder singleton.");
			instance = new ContextHolder(
					ApplicationConfiguration.DEFAULT_APPLICATION_CONFIG_FILE);
		}

		return instance;
	}

	/**
	 * The only constructor - requires application configuration file.
	 */
	private ContextHolder(String applicationConfigurationFileURL) {

		ApplicationConfiguration validConfiguration = null;
		String validURL = null;
		try {
			validURL = applicationConfigurationFileURL;
			validConfiguration = (ApplicationConfiguration) ConfigurationUtils
					.loadConfiguration(applicationConfigurationFileURL,
							ApplicationConfiguration.class);
		} catch (LoadConfigurationException e) {
			// application configuration was deleted, corrupted or this is the
			// first
			// start of the application

			validURL = ApplicationConfiguration.DEFAULT_APPLICATION_CONFIG_FILE;
			LOG.warn(e.getMessage(), e);

			validConfiguration = new ApplicationConfiguration();
			try {
				LOG.warn("No application configuration file of name '"
						+ applicationConfigurationFileURL
						+ "' found! Creating new empty application configuration with config file: '"
						+ applicationConfigurationFileURL + "'");
				ConfigurationUtils.saveConfiguration(validURL,
						validConfiguration);
			} catch (SaveConfigurationException e1) {
				LOG.warn(e1.getMessage(), e1);
				LOG.warn("Could not create initial application configuration! Exiting application");
				FresnelApplication.getApp().exit();
			}
		}

		Assert.notNull(validConfiguration);
		Assert.notNull(validURL);

		this.applicationConfiguration = validConfiguration;
		this.applicationConfigurationFileURL = validURL;
        this.transformation = FresnelEditorConstants.Transformations.XHTML;

	}

	/**
	 * Creates new DAO for repository identified by repository name. If there is
	 * no repository configuration for given repository name returns null.
	 * 
	 * NOTE: this function is now private because we would like to prevent
	 * creating more than one DAO class per repository. Instead of using this
	 * function you should use getRepositoryDao.
	 * 
	 * @param repositoryName
	 *            name of repository which new DAO will be created for
	 * @return <br>
	 *         new DAO if there exists configuration for given repository name <br>
	 *         null if none of previous is fulfilled
	 */
	private BaseRepositoryDao createRepositoryDao(String repositoryName) {

		if (repositoryName == null || "".equals(repositoryName)) {
			LOG.error("Cannot create repository with empty name!");
		}

		RepositoryConfiguration repositoryConfiguration = applicationConfiguration
				.getRepositoryConfigurations().get(repositoryName);

		if (repositoryConfiguration == null) {
			LOG.error("Cannot create repository DAO - repository configuration for repository '"
					+ repositoryName + "' not found");
			return null;
		}

		BaseRepositoryDao dao = null;

		switch (repositoryConfiguration.getDomain()) {
		case DATA:
			dao = new DataRepositoryDao(repositoryConfiguration);
			break;
		case FRESNEL:
			dao = new FresnelRepositoryDao(repositoryConfiguration);
			break;
		default:
			LOG.error("Invalid repository domain: "
					+ repositoryConfiguration.getDomain());
			throw new ArrayIndexOutOfBoundsException();
		}

		addDefaultNamespaces(dao);

		return dao;
	}

	private Map<String, BaseRepositoryDao> repositories = new HashMap<String, BaseRepositoryDao>();

	public BaseRepositoryDao getRepositoryDao(String repositoryName) {
		if (getDataRepositoryName() != null && getDataRepositoryName().equals(repositoryName)) {
			return getDataRepositoryDao();
		} else if (getFresnelRepositoryName() != null && getFresnelRepositoryName().equals(repositoryName)) {
			return getFresnelRepositoryDao();
		} else {
			BaseRepositoryDao repository = repositories.get(repositoryName);
			if (repository == null) {
				repository = createRepositoryDao(repositoryName);
				repositories.put(repositoryName, repository);
			}
			return repository;
		}
	}

	private void addDefaultNamespaces(BaseRepositoryDao dao) {
		// dao.getNamespaces();
		try {
			dao.addNamespace(new NamespaceImpl("fresnel",
					Constants.FRESNEL_NAMESPACE_URI));
			dao.addNamespace(new NamespaceImpl("rdf",
					"http://www.w3.org/1999/02/22-rdf-syntax-ns#"));
			dao.addNamespace(new NamespaceImpl("rdfs",
					"http://www.w3.org/2000/01/rdf-schema#"));
			dao.addNamespace(new NamespaceImpl("foaf",
					"http://xmlns.com/foaf/0.1/"));
		} catch (DataImportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Saves the whole configuration (application configuration, current project
	 * configuration).
	 * 
	 * @throws SaveConfigurationException
	 *             if an error occurs during saving configuration items
	 */
	public void saveCompleteConfiguration() throws SaveConfigurationException {

		// 1. Save application configuration
		ConfigurationUtils.saveConfiguration(applicationConfigurationFileURL,
				applicationConfiguration);

		// 2. If project is open then save also project configuration
		if (isProjectOpen()) {
			ConfigurationUtils.saveConfiguration(
					applicationConfiguration.getLastOpenProjectUrl(),
					projectConfiguration);
		}
	}

	/**
	 * Method for opening new project. Does nothing if the project we want to
	 * open is already opened.
	 * 
	 * @param projectName
	 *            the name of the project to open
	 * @param isStart
	 *            true if it is start of Fresnel Editor, false otherwise
	 */
	public void openProject(String projectConfigurationFileUrl, boolean isStart)
			throws OpenProjectException {

		if (isProjectOpen()
				&& applicationConfiguration.getLastOpenProjectUrl().equals(
						projectConfigurationFileUrl)) {
			// do nothing - the project we want to open is currently opened
			return;
		}

		ProjectConfiguration configurationToOpen;
		try {
			configurationToOpen = getProjectConfiguration(projectConfigurationFileUrl);
		} catch (LoadConfigurationException e) {
			throw new OpenProjectException(e);
		}

		if (isStart) {
			// Don't re-open sample projects again because they are using temp
			// repositories
			if (configurationToOpen.isSample()) {
				return;
			}
		}

		// If some project is open then close it
		if (isProjectOpen()) {
			closeProject();
		}

		// Load project configuration
		this.projectConfiguration = configurationToOpen;

		// try to open both repositories - if fail do not allow to open project
		if (getFresnelRepositoryDao() == null) {
			this.projectConfiguration = null;
			String message = "Cannot load fresnel repository DAO ["
					+ getFresnelRepositoryName() + "]- cannot open project ["
					+ configurationToOpen.getName() + "]!";
			OpenProjectException e = new OpenProjectException(message);
			LOG.warn(message);
			LOG.debug(message, e);
			throw e;
		}
		if (getDataRepositoryDao() == null) {
			this.projectConfiguration = null;
			String message = "Cannot load data repository DAO ["
					+ getFresnelRepositoryName() + "]- cannot open project ["
					+ configurationToOpen.getName() + "]!";
			OpenProjectException e = new OpenProjectException(message);
			LOG.warn(message);
			LOG.debug(message, e);
			throw e;
		}

		applicationConfiguration
				.setLastOpenProjectUrl(projectConfigurationFileUrl);

		// Hide preview panel
		FresnelApplication.getApp().getBaseFrame().hidePreviewPanel();
		// Close all tabs
		FresnelApplication.getApp().getBaseFrame().closeAllOpenedTabs();
	}

	public ProjectConfiguration getProjectConfiguration(
			String projectConfigurationFileUrl)
			throws CannotOpenProjectException, LoadConfigurationException {

		// String projectConfigurationFileUrl =
		// applicationConfiguration.getProjectConfigurationFileUrl(projectName);
		if (projectConfigurationFileUrl == null) {
			LOG.warn(
					"Trying to open not existing project: {}. The project was not opened.",
					projectConfigurationFileUrl);
			throw new CannotOpenProjectException("Cannot open project file: '"
					+ projectConfigurationFileUrl + "'.");
		}

		ProjectConfiguration configurationToOpen = (ProjectConfiguration) ConfigurationUtils
				.loadConfiguration(projectConfigurationFileUrl,
						ProjectConfiguration.class);
		return configurationToOpen;
	}

	/**
	 * Method for closing projects.
	 */
	public void closeProject() {

		if (isProjectOpen()) {

			// Automatically save project configuration
			try {
				ConfigurationUtils.saveConfiguration(
						applicationConfiguration.getLastOpenProjectUrl(),
						projectConfiguration);
			} catch (SaveConfigurationException e) {
				new MessageDialog(
						GuiUtils.getTopComponent(),
						"Cannot save configuration",
						"Error while saving configuration for project '"
								+ projectConfiguration.getName()
								+ "':"
								+ e.getMessage()
								+ "\n\nThe project configuration was not updated.")
						.setVisible(true);
			}

			projectConfiguration = null;
			applicationConfiguration.setLastOpenProjectUrl(null);
		} else {

			LOG.warn("Trying to close project when no project is open! No action performed.");
		}
	}

	public boolean isProjectOpen() {
		return projectConfiguration != null;
	}

	/**
	 * Returns true if the given repository is currently used by opened project.
	 * 
	 * @param repositoryName
	 * @return true if the given repository is currently used by opened project.
	 *         Returns false otherwise.
	 */
	public boolean isActiveRepository(String repositoryName) {
		return (isProjectOpen() && repositoryName != null && (repositoryName
				.equals(getFresnelRepositoryName()) || repositoryName
				.equals(getDataRepositoryName())));
	}

	/**
	 * @return the applicationConfigurationFileURL
	 */
	public String getApplicationConfigurationFileURL() {
		return applicationConfigurationFileURL;
	}

	/**
	 * @return the applicationConfiguration
	 */
	public ApplicationConfiguration getApplicationConfiguration() {
		return applicationConfiguration;
	}

	/**
	 * Returns the configuration of currently opened project. Can return null if
	 * no project is currently opened.
	 * 
	 * @return the projectConfiguration of the currently opened project
	 */
	public ProjectConfiguration getProjectConfiguration() {
		return projectConfiguration;
	}

	/**
	 * Returns the name of currently opened project. Can return null if no
	 * project is currently opened.
	 * 
	 * @return the name of currently opened project
	 */
	public String getOpenProjectName() {
		if (projectConfiguration != null) {
			return projectConfiguration.getName();
		}
		return null;
	}

	/**
	 * Returns the domain dao of the currently opened project.
	 * 
	 * @return the dataRepositoryDao of the currently opened project
	 */
	public DataRepositoryDao getDataRepositoryDao() {
		if (domainDao == null
				|| !domainDao.getName().equals(selectedDataRepositoryName)) {
			// Set corresponding DAO
			domainDao = (DataRepositoryDao) createRepositoryDao(selectedDataRepositoryName);
		}
		return domainDao;
	}

	/**
	 * Returns the fresnel repository of the currently opened project.
	 * 
	 * @return the frensnelRepositoryDao of the currently opened project
	 */
	public FresnelRepositoryDao getFresnelRepositoryDao() {
		if (!isProjectOpen()) {
			return null;
		}
		String currentFresnelRepo = projectConfiguration
				.getFresnelRepositoryName();
		if (fresnelDao == null
				|| !fresnelDao.getName().equals(currentFresnelRepo)) {
			fresnelDao = (FresnelRepositoryDao) createRepositoryDao(currentFresnelRepo);
		}
		return fresnelDao;
	}

	/**
	 * Sets the currently opened project fresnel repository name.
	 * 
	 * @param fresnelRepositoryName
	 *            name of the fresnel repository (DAO will be constructed for
	 *            this repository)
	 */
	public void setFresnelRepositoryName(String fresnelRepositoryName) {
		projectConfiguration.setFresnelRepositoryName(fresnelRepositoryName);
	}

	/**
	 * Returns namespaces declared in both fresnel and data daos. Each namespace
	 * is listed only once even if it appears in both daos.
	 * 
	 * @return
	 */
	public List<Namespace> getBothDaosNamespaces() {
		Set<Namespace> namespaces = new HashSet<Namespace>();

		DataRepositoryDao dataRepositoryDao = ContextHolder.getInstance()
				.getDataRepositoryDao();
		if (dataRepositoryDao != null) {
			namespaces.addAll(dataRepositoryDao.getNamespaces());
		}

		FresnelRepositoryDao fresnelRepositoryDao = ContextHolder.getInstance()
				.getFresnelRepositoryDao();
		if (fresnelRepositoryDao != null) {
			namespaces.addAll(fresnelRepositoryDao.getNamespaces());
		}
		return new ArrayList<Namespace>(namespaces);
	}

	/**
	 * Returns the list of available fresnel repositories.
	 * 
	 * @return the list of available fresnel repositories
	 */
	public List<String> getFresnelRepositoryNames() {
		List<String> names = new ArrayList<String>();
		for (Entry<String, RepositoryConfiguration> entry : applicationConfiguration
				.getRepositoryConfigurations().entrySet()) {
			if (RepositoryDomain.FRESNEL.equals(entry.getValue().getDomain())) {
				names.add(entry.getValue().getName());
			}
		}
		return names;
	}

	/**
	 * Returns the list of available data repositories.
	 * 
	 * @return the list of available data repositories
	 */
	public List<String> getDataRepositoryNames() {
		List<String> names = new ArrayList<String>();
		for (Entry<String, RepositoryConfiguration> entry : applicationConfiguration
				.getRepositoryConfigurations().entrySet()) {
			if (RepositoryDomain.DATA.equals(entry.getValue().getDomain())) {
				names.add(entry.getValue().getName());
			}
		}
		return names;
	}

	/**
	 * Returns the name of the active data repository for currently opened
	 * project. Can return null if no project is opened.
	 * 
	 * @return the name of data repository for currently opened project <br>
	 *         Can return null
	 */
	public String getDataRepositoryName() {
		return selectedDataRepositoryName;
	}

	/**
	 * Returns the name of the active fresnel repository for currently opened
	 * project. Can return null if no project is opened.
	 * 
	 * @return the name of data repository for currently opened project <br>
	 *         Can return null
	 */
	public String getFresnelRepositoryName() {
		if (projectConfiguration != null) {
			return projectConfiguration.getFresnelRepositoryName();
		}
		return null;
	}

	/**
	 * Returns the COPY of project configuration of currently opened project.
	 * The changes in the returned object are not reflected in the currently
	 * opened project settings. For the changes to take effect it is necessary
	 * to persist the copy object and then reload the project configuration.
	 * 
	 * @return copy of project configuration of currently opened project <br>
	 *         Can return null if no project is opened
	 */
	public ProjectConfiguration getProjectConfigurationCopy() {
		if (projectConfiguration != null) {
			return new ProjectConfiguration(projectConfiguration);
		}
		return null;
	}
	
	public void setProjectConfiguration(ProjectConfiguration pc) {
		projectConfiguration = pc;
		
	}

	/**
	 * @return the projectConfigurationFileURL
	 */
	// public String getProjectConfigurationFileURL() {
	// return projectConfigurationFileURL;
	// }
	/**
	 * @param projectConfigurationFileURL
	 *            the projectConfigurationFileURL to set
	 */
	// public void setProjectConfigurationFileURL(String
	// projectConfigurationFileURL) {
	// this.projectConfigurationFileURL = projectConfigurationFileURL;
	// }
	/**
	 * HELPER SETTERS
	 */

	/**
	 * Setter for testing purposes.
	 * 
	 * @param fresnelDao
	 *            fresnel DAO to be set
	 */
	public void setFresnelDao(FresnelRepositoryDao fresnelDao) {
		this.fresnelDao = fresnelDao;
	}
	
    public Transformations getTransformation() {
        return transformation;
    }

    public void setTransformation(Transformations transformation) {
        this.transformation = transformation;
    }


}
