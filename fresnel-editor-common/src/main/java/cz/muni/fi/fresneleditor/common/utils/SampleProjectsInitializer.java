/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.common.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;

import cz.muni.fi.fresneleditor.common.AppEventsManager;
import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.FresnelEditorConstants;
import cz.muni.fi.fresneleditor.common.OpenProjectException;
import cz.muni.fi.fresneleditor.model.DataImportException;
import cz.muni.fi.fresneleditor.model.DataRepositoryDao;
import cz.muni.fi.fresneleditor.model.FresnelRepositoryDao;

/**
 * Class holds information about all available sample projects and provide
 * methods to open and initialize them.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 */
public class SampleProjectsInitializer {

	private static final String NAME_FILE_PROJECT_CONF = "project.xml";
	private static final String NAME_FILE_DATA = "data.n3";
	private static final String NAME_FILE_FRESNEL = "fresnel.n3";

	private static final String PROJECT_NAME_FOAF = "FOAF sample project";
	private static final String DIR_PROJECT_FOAF = "foaf";

	private static final String[] sampleProjects = { PROJECT_NAME_FOAF };

	private static final String DEFAULT_BASE_URI = "";

	/**
	 * @return list of available sample projects (their names)
	 */
	public static List<String> getAvailableSampleProjects() {
		return Arrays.asList(sampleProjects);
	}

	public static void openProject(String projectName) {

		if (PROJECT_NAME_FOAF.equals(projectName)) {
			openProjectInternal(PROJECT_NAME_FOAF, DIR_PROJECT_FOAF);
		} else {
			// FIXME: How to display error message - missing reference to parent
			// new MessageDialog();
		}
	}

	private static void openProjectInternal(String projectName,
			String directoryName) {

		ContextHolder context = ContextHolder.getInstance();

		String projectConfigurationURL = FresnelEditorConstants.PATH_SAMPLE_PROJECTS
				+ directoryName + File.separator + NAME_FILE_PROJECT_CONF;

		String dataFilePath = FresnelEditorConstants.PATH_SAMPLE_PROJECTS
				+ directoryName + File.separator + NAME_FILE_DATA;

		String fresnelFilePath = FresnelEditorConstants.PATH_SAMPLE_PROJECTS
				+ directoryName + File.separator + NAME_FILE_FRESNEL;

		try {
			context.openProject(projectConfigurationURL, false);

			DataRepositoryDao dataDAO = context.getDataRepositoryDao();
			FresnelRepositoryDao fresnelDAO = context.getFresnelRepositoryDao();

			// Clear all data from data and fresnel repositories
			dataDAO.clearAllData();
			fresnelDAO.clearAllData();
			// Add sample data into data and fresnel repositories
			dataDAO.addData(new File(dataFilePath), RDFFormat.N3,
					DEFAULT_BASE_URI);
			fresnelDAO.addData(new File(fresnelFilePath), RDFFormat.N3,
					DEFAULT_BASE_URI);

			AppEventsManager.getInstance().fireOpenProjectChanged(projectName);

			// FIXME in project configuration in GUI

		} catch (OpenProjectException ex) {
			throw new RuntimeException(ex);
			// FIXME
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataImportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// If some project is currently open then close it
		// if (context.isProjectOpen()) {
		// context.closeProject();
		// }

		// Create sample project
		// 1. Virtual project configuration
		// 2. In-memory data and fresnel repositories
		// 3. Import pre-configured data into data and fresnel repositories

		// ProjectConfiguration projectConfiguration = new
		// ProjectConfiguration();
		// projectConfiguration.setName(projectName);
		// projectConfiguration.setDataRepositoryName(REPOSITORY_SAMPLE_PROJECT_DATA);
		// projectConfiguration.setFresnelRepositoryName(REPOSITORY_SAMPLE_PROJECT_FRESNEL);
		// projectConfiguration
		// .setDescription("Sample project presenting Fresnel Editor feature on
		// FOAF ontology.");
		// projectConfiguration.setProjectConfigurationFileURL(null); // just
		// virtual project conf.
		//
		// context.setProjectConfiguration(projectConfiguration);
		//
		// FresnelRepositoryDao fresnelRepository = new
		// FresnelRepositoryDao(REPOSITORY_SAMPLE_PROJECT_FRESNEL);
		// context.setFresnelDao(fresnelRepository);
		//
		// DataRepositoryDao dataRepository = new
		// DataRepositoryDao(REPOSITORY_SAMPLE_PROJECT_DATA);
		// context.set

		// FIXME: Fill missing implementation
		// FIXME: Handle re-opening
	}
}
