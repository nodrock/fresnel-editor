/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.model;

import java.util.List;

import org.openrdf.model.Statement;

import fr.inria.jfresnel.Constants;

/**
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 22.4.2009
 */
public interface IModel {

	public final String PROPERTY_RDF_TYPE = Constants.RDF_NAMESPACE_URI
			+ "type";
	public final String PROPERTY_RDFS_LABEL = Constants.RDFS_NAMESPACE_URI
			+ "label";
	public final String PROPERTY_RDFS_COMMENT = Constants.RDFS_NAMESPACE_URI
			+ "comment";
	public final String DATATYPE_STYLE_CLASS = Constants.FRESNEL_NAMESPACE_URI
			+ "styleClass";

	/**
	 * Returns resource definition (for example Fresnel Lens) as list of RDF
	 * statements.
	 * 
	 * @return list of RDF statements representing given resource
	 */
	List<? extends Statement> asStatements();

	/**
	 * Returns resource URI.
	 * 
	 * @return resource URI
	 */
	public String getModelUri();
}
