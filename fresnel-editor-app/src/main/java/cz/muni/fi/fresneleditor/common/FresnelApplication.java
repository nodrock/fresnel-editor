/**
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common;

import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.jdesktop.application.SingleFrameApplication;
import org.openrdf.repository.Repository;
import org.openrdf.repository.http.HTTPRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cz.muni.fi.fresneleditor.common.config.ApplicationConfiguration;
import cz.muni.fi.fresneleditor.common.utils.SaveConfigurationException;

/**
 * Main entry point to Fresnel Editor application.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * @version 14. 3. 2009
 */
public class FresnelApplication extends SingleFrameApplication {

	private static final Logger LOG = LoggerFactory
			.getLogger(FresnelApplication.class);

	/**
	 * Reference to base JFrame.
	 */
	private BaseJFrame mainJFrame;

	/**
	 * True if Fresnel Editor is fully initialized.
	 */
	private boolean initialized = false;

	private static ApplicationContext appContext;

	/**
	 * Fresnel Editor main method.
	 * 
	 * @param args
	 *            parameters from command line
	 */
	public static void main(String[] args) {
		launch(FresnelApplication.class, args);
	}

	@Override
	protected void startup() {
		init();
		show(mainJFrame);
		// Do initialization which can be performed only after showing of main
		// JFrame.
		initAfterShow();
	}

	private void init() {	
		// Load Fresnel Editor properties into system properties
		loadFresnelEditorProperties();

		// Create instance if ContextHolder singleton
		ContextHolder.getInstance();

		if (!initialized) {
			getSpringContext();

			ApplicationConfiguration applicationConfiguration = ContextHolder
					.getInstance().getApplicationConfiguration();

			mainJFrame = new BaseJFrame();

			if (applicationConfiguration == null) {
				// application configuration was deleted or this is the first
				// start of the application
				LOG.warn("No application configuration file of name '"
						+ ContextHolder.getInstance()
								.getApplicationConfigurationFileURL()
						+ "' found! Creating new empty application configuration with config file: '"
						+ ContextHolder.getInstance()
								.getApplicationConfigurationFileURL() + "'");
			}

			// open the project that was the last open project
			try {
				LOG.info("Trying to open last opened project: '{}'",
						applicationConfiguration.getLastOpenProjectUrl());
				ContextHolder.getInstance().openProject(
						applicationConfiguration.getLastOpenProjectUrl(), true);
			} catch (OpenProjectException e) {
				// do nothing
			}

			// try {
			// ContextHolder.getInstance().getFresnelRepositoryDao().getRepository().getConnection().clear();
			// }
			// catch (RepositoryException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			// File fresnelFile = new File("src/mains/resources/foaf_short.n3");
			// try {
			// ContextHolder.getInstance().getFresnelRepositoryDao().addData(fresnelFile,
			// RDFFormat.N3, null);
			// } catch (DataImportException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			// ContextHolder.getInstance().getFresnelRepositoryDao().printStatements(System.out,
			// false);

			System.out.println("----------------");
			//
			// ContextHolder.getInstance().getDataRepositoryDao().printStatements(System.out,
			// false);
			
			AppEventsManager.getInstance().fireOpenProjectChanged(this);

			/*
			 * Before exit() really terminates the Application, it polls the
			 * exitListeners by calling their canExit method. If any canExit
			 * method returns false, exit processing is aborted. If not, the
			 * exitListeners' willExit method is called, and then finally the
			 * Application's shutdown method. These methods are the right place
			 * to take care of any final cleanup.
			 */
			addExitListener(new ExitListener() {
				@Override
				public boolean canExit(EventObject arg0) {
					return true;
				}

				@Override
				public void willExit(EventObject arg0) {
					try {
						ContextHolder.getInstance().saveCompleteConfiguration();
					} catch (SaveConfigurationException e) {
						LOG.warn(
								"Exiting application: Could not save complete configuration: %",
								e.getMessage());
					}
				}
			});
		}

		initialized = true;
	}

	private void initAfterShow() {
		// When application is started preview panel should be hidden.
		hidePreviewPanel();
	}

	@SuppressWarnings("unchecked")
	public static List<IFresnelEditorModule> getModules() {
		List<IFresnelEditorModule> modules = new ArrayList<IFresnelEditorModule>();
		Map<String, IFresnelEditorModule> modulesMap = getSpringContext()
				.getBeansOfType(IFresnelEditorModule.class);
		LOG.info(
				"Retrieving Fresnel Editor modules. Number of modules retrieved: {}",
				modulesMap.size());

		for (Entry<String, IFresnelEditorModule> entry : modulesMap.entrySet()) {
			IFresnelEditorModule value = entry.getValue();
			LOG.info("Located module with label: {} which is of type {}",
					value.getName(), value.getClass());
			modules.add(value);
		}

		return modules;
	}

	private static ApplicationContext getSpringContext() {
		if (appContext == null) {
			appContext = new ClassPathXmlApplicationContext(
					"fresneleditorContext.xml");
		}
		return appContext;
	}

	/**
	 * Helper method for retrieving typed active application instance.
	 * 
	 * @return
	 */
	public static FresnelApplication getApp() {
		return (FresnelApplication) FresnelApplication.getInstance();
	}

	// fixme igor: how to make mainJFrame available to nodes?
	public BaseJFrame getBaseFrame() {
		return mainJFrame;
	}

	/**
	 * Loads properties from FRESNEL_EDITOR_PROP_FILE into System properties so
	 * that these properties are accessible across the whole application.
	 */
	private void loadFresnelEditorProperties() {

		LOG.info("Loading Fresnel Editor properties from file: {}.",
				FresnelEditorConstants.PATH_FRESNEL_EDITOR_PROP);

		Properties fresnelEditorProperties = new Properties();
		try {
			fresnelEditorProperties.load(new FileInputStream(new File(
					FresnelEditorConstants.PATH_FRESNEL_EDITOR_PROP)));
		} catch (Exception ex) {
			LOG.warn("Cannot load Fresnel Editor properties from file: {}!",
					FresnelEditorConstants.PATH_FRESNEL_EDITOR_PROP);
			return;
		}

		for (Object key : fresnelEditorProperties.keySet()) {
			LOG.info("\t{}: {}", (String) key,
					fresnelEditorProperties.getProperty((String) key));
			System.setProperty((String) key,
					fresnelEditorProperties.getProperty((String) key));
		}

		LOG.info("All Fresnel Editor properties loaded successfully.");
	}

	/***************************************************************************
	 * Functionality related to preview panel
	 **************************************************************************/

	/**
	 * Displays render preview panel.
	 */
	public void showPreviewPanel() {
		mainJFrame.showPreviewPanel();
	}

	/**
	 * Displays render preview panel.
	 */
	public void hidePreviewPanel() {
		mainJFrame.hidePreviewPanel();
	}

	/**
	 * 
	 * @param newPreviewPanel
	 *            new preview panel to be used
	 */
	public void updatePreviewPanel(Component newPreviewPanel) {
		mainJFrame.updatePreviewPanel(newPreviewPanel);
	}

}
