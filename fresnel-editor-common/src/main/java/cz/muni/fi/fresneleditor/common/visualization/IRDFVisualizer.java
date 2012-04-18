/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.common.visualization;

import org.openrdf.model.URI;
import org.openrdf.repository.Repository;

import fr.inria.jfresnel.Group;

/**
 * Main interface for visualizers of RDF data stored in Sesame repository.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 13.5.2009
 */
public interface IRDFVisualizer {

	/**
	 * Main visualization algorithm.
	 * 
	 * @param repository
	 *            repository with RDF data to be visualized
	 * @param visParam
	 *            instance of visualization parameter which contains several
	 *            property values to customize visualization process
	 * @param group
	 *            URI of group which will be used for visualization, this group
	 *            will be read out from fresnel repository and used in
	 *            visualization process
	 * @param pathToOutputFile
	 *            path to output file where visualization result should be
	 *            stored as XHTML webpage
	 */
	public void visualize(Repository repository, URI groupUri,
			VisualizationParameter visParam, String pathToOutputFile);

	/**
	 * Special visualization algorithm using specific group instance. This is
	 * used mainly for creation of "fast previews".
	 * 
	 * @param repository
	 *            repository with RDF data to be visualized
	 * @param visParam
	 *            instance of visualization parameter which contains several
	 *            property values to customize visualization process
	 * @param group
	 *            explicitly specified group instance which will be used for
	 *            visualization - should contain all relevant lenses and formats
	 */
	public void visualize(Repository repository, Group group,
			VisualizationParameter visParam);
}
