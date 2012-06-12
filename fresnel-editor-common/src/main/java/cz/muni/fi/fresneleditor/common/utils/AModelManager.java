/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.query.QueryLanguage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.data.AdditionalContentGuiWrapper;
import cz.muni.fi.fresneleditor.common.data.AdditionalContentType;
import cz.muni.fi.fresneleditor.common.data.StyleGuiWrapper;
import cz.muni.fi.fresneleditor.common.data.StyleType;
import cz.muni.fi.fresneleditor.model.FresnelRepositoryDao;
import cz.muni.fi.fresneleditor.model.IModel;
import cz.muni.fi.fresneleditor.model.SparqlUtils;
import fr.inria.jfresnel.Constants;
import fr.inria.jfresnel.ContentFormat;

/**
 * Abstract class which serves as a basis for creation of Model Managers which
 * are responsible for building of resource models which are then used in GUI.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 22.4.2009
 */
public abstract class AModelManager<T> {

	private static final Logger LOG = LoggerFactory
			.getLogger(AModelManager.class);

	protected static final String PROPERTY_RESOURCE_STYLE = Constants.FRESNEL_NAMESPACE_URI
			+ Constants._resourceStyle;

	// TODO: Refactor to full URI
	protected static final String PROPERTY_RDFS_LABEL = "rdfs:label";
	protected static final String PROPERTY_RDFS_COMMENT = "rdfs:comment";

	protected static final String LITERAL_DATATYPE_SEPARATOR = "^^";
	protected static final String LITERAL_DATATYPE_SEPARATOR_REGEXP = "\\^\\^";

	/**
	 * Builds resource model on the basis of JFresnel source object. This model
	 * can be then used in modules as model for JPanels.
	 * 
	 * @param source
	 *            source object which is used for construction of model, it
	 *            should for example JFresnel Format or Lens object
	 * @return model for given Fresnel resource (Format, Lens, Group model)
	 */
	public abstract IModel buildModel(T source);

	/**
	 * 
	 * @param model
	 * @return
	 */
	public abstract T convertModel2JFresnel(IModel model);

	/**
	 * Gets resource rdfs:label property.
	 * 
	 * @param uri
	 *            URI of the resource which the label belongs to
	 * @return label value as a string
	 */
	protected Literal getResourceLabel(URI uri) {

		return getResourceStringProperties(uri, IModel.PROPERTY_RDFS_LABEL)
				.get(0);
	}

	/**
	 * Gets resource rdfs:comment property.
	 * 
	 * @param uri
	 *            URI of the resource which the label belongs to
	 * @return label value as a string
	 */
	protected Literal getResourceComment(URI uri) {

		return getResourceStringProperties(uri, IModel.PROPERTY_RDFS_COMMENT)
				.get(0);
	}

	/**
	 * 
	 * @param type
	 * @param contentFormat
	 * @return
	 */
	// TODO: Comment
	protected AdditionalContentGuiWrapper createAdditionalContentGuiWrapper(
			AdditionalContentType type, ContentFormat contentFormat) {

		AdditionalContentGuiWrapper additonalContent = null;

		if (contentFormat == null) {
			additonalContent = new AdditionalContentGuiWrapper(type);
		} else {
			additonalContent = new AdditionalContentGuiWrapper(type,
					contentFormat.getContentBefore(),
					contentFormat.getContentAfter(),
					contentFormat.getContentFirst(),
					contentFormat.getContentLast(),
					contentFormat.getContentNoValue());
		}

		return additonalContent;
	}

	/**
	 * Queries out resource style for given resource from Fresnel DAO.
	 * 
	 * @param resourceUri
	 *            URI of Fresnel resource which can have fresnel:resourceStyle
	 *            property
	 * @return StyleGuiWrapper representing parsed resource style
	 */
	protected StyleGuiWrapper getResourceStyle(URI resourceUri) {

		List<Literal> resourceStyleList = getResourceStringProperties(
				resourceUri, PROPERTY_RESOURCE_STYLE);
		List<String> resourceStyleStringList = new ArrayList<String>();

		for (Literal literal : resourceStyleList) {
			if (!"".equals(literal.getLabel())) {
				resourceStyleStringList.add(literal.getLabel());
			}
		}

		String resourceStyleString = resourceStyleStringList.isEmpty() ? null
				: resourceStyleStringList.get(0);

		if (resourceStyleString == null || "".equals(resourceStyleString)) {

			return null;
		}

		StyleGuiWrapper styleGuiWrapper = new StyleGuiWrapper(
				StyleType.RESOURCE);

		styleGuiWrapper.setValue(resourceStyleString);

		return styleGuiWrapper;
	}

	/**
	 * Helper for getting any String property of given Fresnel resource, for
	 * example: Format. Warning: uses
	 * 
	 * @param uri
	 *            JFresnel resource URI
	 * @param propertyName
	 *            name of String property to be obtained
	 * @return String property value or empty String if format property is not
	 *         defined
	 */
	protected List<Literal> getResourceStringProperties(URI uri,
			String propertyName) {

		FresnelRepositoryDao fresnelDao = ContextHolder.getInstance()
				.getFresnelRepositoryDao();

		String prefixes = SparqlUtils.getSparqlQueryPrefixes(fresnelDao
				.getRepository());

		String queryString = prefixes + " SELECT ?x WHERE { <" + uri + "> <"
				+ propertyName + "> ?x . }";

		List<Value> values = fresnelDao.execTupleQuery(queryString,
				QueryLanguage.SPARQL, "x");

		List<Literal> resultList = new ArrayList<Literal>();

		if (values != null && values.size() != 0) {

			for (Value value : values) {
				// TODO: nodrock kontrola ci naozaj dostavame Literal ak by sme
				// dostali BNode alebo URI tak ich zahodime
				if (value instanceof Literal) {
					resultList.add((Literal) value);
				}
			}

		} else {
			resultList.add(new LiteralImpl("", "en"));
		}

		return resultList;
	}

	protected List<Value> getResourceValueProperties(URI uri,
			String propertyName) {

		FresnelRepositoryDao fresnelDao = ContextHolder.getInstance()
				.getFresnelRepositoryDao();

		String prefixes = SparqlUtils.getSparqlQueryPrefixes(fresnelDao
				.getRepository());

		String queryString = prefixes + " SELECT ?x WHERE { <" + uri + "> <"
				+ propertyName + "> ?x . }";

		List<Value> values = fresnelDao.execTupleQuery(queryString,
				QueryLanguage.SPARQL, "x");

		return values;
	}

	/**
	 * Parses literal string to get literal value and literal datatype
	 * separately.
	 * 
	 * @param literal
	 *            RDF literal string in form: "something"^^prefix:something
	 * @return array of 2 elements - first one is literal value, second one is
	 *         literal datatype
	 */
	protected String[] parseLiteralWithDatatype(String literal) {

		String[] resultElements = null;

		int index = literal.indexOf(LITERAL_DATATYPE_SEPARATOR);

		// If domain String literal doesn't contain ^^ then we are not able to
		// distinguish between selector
		// types so this domain selector is ignored.
		if (index == -1) {
			LOG.warn(
					"Literal with datatype {} not processed - missing '^^' substring.",
					literal);
			return null;
		}

		resultElements = literal.split(LITERAL_DATATYPE_SEPARATOR_REGEXP);

		if (resultElements.length != 2) {
			LOG.warn(
					"Literal with datatype {} not processed - literal cannot be splitted using '^^'.",
					literal);
			return null;
		}

		// Handle '"' characters
		int length = resultElements[0].length();
		if (resultElements[0].charAt(0) == '"'
				&& resultElements[0].charAt(length - 1) == '"') {
			resultElements[0] = resultElements[0].substring(1, length - 1);
		}

		return resultElements;
	}
}
