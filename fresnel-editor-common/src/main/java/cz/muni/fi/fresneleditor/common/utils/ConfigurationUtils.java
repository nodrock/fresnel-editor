/**
 * 
 */
package cz.muni.fi.fresneleditor.common.utils;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class with utility methods for work with XML serialized configuration.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 14. 3. 2009
 */
public class ConfigurationUtils {

	private static final Logger LOG = LoggerFactory
			.getLogger(ConfigurationUtils.class);

	/**
	 * Saves configuration into XML file.
	 * 
	 * @param configurationFileName
	 *            name of target file with stored configuration
	 * @param configurationInstance
	 *            instance of configuration to be stored
	 * @param configurationClass
	 *            type (class) of configuration instance to be stored
	 *            (ApplicationConfiguration, ProjectConfiguration, ...)
	 * @throws SaveConfigurationException
	 */
	public static void saveConfiguration(String configurationFileName,
			Object configurationInstance) throws SaveConfigurationException {

		File file = new File(configurationFileName);
		LOG.info("Storing configuration ("
				+ configurationInstance.getClass().getSimpleName()
				+ ") to file: '" + file.getAbsolutePath() + "'");

		try {
			// fixme igor: possible optimalization - do not initialize the
			// marshaller every time
			JAXBContext jaxbContext = JAXBContext
					.newInstance(configurationInstance.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			marshaller.marshal(configurationInstance, file);

		} catch (JAXBException e) {
			LOG.error("Cannot store configuration into file: {}",
					file.getAbsolutePath());
			LOG.error(e.getMessage(), e);
			throw new SaveConfigurationException(
					"Cannot store configuration into file: "
							+ file.getAbsolutePath() + "\n\n" + e.getMessage(),
					e);
		}
	}

	/**
	 * Loads configuration from XML file.
	 * 
	 * @param configurationFileName
	 *            file with XML configuration to be loaded
	 * @param configurationClass
	 *            type (class) of configuration to be loaded
	 *            (ApplicationConfiguration, ProjectConfiguration, ...)
	 * @return instance of configuration class
	 * @throws FileNotFoundException
	 */
	public static Object loadConfiguration(String configurationFileName,
			Class<?> configurationClass) throws LoadConfigurationException {
		JAXBContext jaxbContext;

		File file = new File(configurationFileName);
		if (!file.exists()) {
			LOG.warn(
					"Trying to load configuration from a file which does not exist: {}",
					file.getAbsolutePath());
			throw new LoadConfigurationException(
					"Cannot load configuration. File '"
							+ file.getAbsolutePath() + "' does not exist");
		}

		LOG.info("Loading configuration (" + configurationClass.getSimpleName()
				+ ") from file: '" + file.getAbsolutePath() + "'");

		try {
			jaxbContext = JAXBContext.newInstance(configurationClass);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			return unmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			LOG.error("Cannot load configuration from file: %s",
					file.getAbsolutePath());
			LOG.error(e.getMessage(), e);
			throw new LoadConfigurationException(
					"Cannot load configuration from file '"
							+ configurationFileName
							+ "'. The file is probably corrupted: "
							+ e.getMessage());
		}
	}

	/**
	 * Deletes the given configuration file from the file system. This action is
	 * irreversible.
	 * 
	 * @param configurationFileURL
	 * @throws DeleteConfigurationException
	 *             if there was an error and the file was not deleted
	 */
	public static void deleteConfigurationFile(String configurationFileURL)
			throws DeleteConfigurationException {
		// A File object to represent the filename
		File file = new File(configurationFileURL);

		LOG.info("Deleting configuration file: '" + file.getAbsolutePath()
				+ "'");

		// Make sure the file or directory exists and isn't write protected
		if (!file.exists()) {
			DeleteConfigurationException e = new DeleteConfigurationException(
					"Delete: no such file: " + configurationFileURL);
			LOG.error(e.getMessage(), e);
			throw e;
		}

		if (!file.canWrite()) {
			WriteProtectedException e = new WriteProtectedException(
					"Delete: write protected: " + configurationFileURL);
			LOG.error(e.getMessage(), e);
			throw e;
		}

		// cannot delete directory
		if (file.isDirectory()) {
			throw new DeleteConfigurationException("Cannot delete directory: "
					+ configurationFileURL);
		}

		// Attempt to delete it
		boolean success = file.delete();

		if (!success) {
			DeleteConfigurationException e = new DeleteConfigurationException(
					"Delete: deletion failed");
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

}
