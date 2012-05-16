/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.group.data;

import java.util.ArrayList;
import java.util.List;

import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.fresneleditor.common.data.AdditionalContentGuiWrapper;
import cz.muni.fi.fresneleditor.common.data.StyleGuiWrapper;
import cz.muni.fi.fresneleditor.common.utils.FresnelUtils;
import cz.muni.fi.fresneleditor.gui.mod.group.utils.GroupModelManager;
import cz.muni.fi.fresneleditor.model.IModel;
import fr.inria.jfresnel.Constants;
import fr.inria.jfresnel.Group;

/**
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 21.4.2009
 */
public class GroupModel implements IModel {

	private static final Logger LOG = LoggerFactory.getLogger(GroupModel.class);

	private String uri;

	private Group fresnelGroup;

	// rdfs:label
	private String label;

	// rdfs:comment
	private String comment;

	private String cssStylesheetUrl;

	private List<StyleGuiWrapper> styles = new ArrayList<StyleGuiWrapper>();

	private List<AdditionalContentGuiWrapper> additionalContents = new ArrayList<AdditionalContentGuiWrapper>();

	private List<URI> associatedLensUris = new ArrayList<URI>();
	private List<URI> associatedFormatUris = new ArrayList<URI>();

	/**
	 * Default constructor.
	 */
	public GroupModel() {
	}

	/**
	 * Returns Fresnel Group URI.
	 * 
	 * @return Fresnel Group URI
	 */
	@Override
	public String getModelUri() {
		return uri;
	}

	/**
     * 
     */
	public List<? extends Statement> asStatements() {

		List<StatementImpl> resultStatements = new ArrayList<StatementImpl>();

		Resource groupSubject = new URIImpl(uri);
		URI predicate = null;
		Value object = null;
		StatementImpl statement = null;

		// rdf:type
		predicate = new URIImpl(PROPERTY_RDF_TYPE);
		object = new URIImpl(Constants._Group);
		statement = new StatementImpl(groupSubject, predicate, object);
		resultStatements.add(statement);

		// rdfs:label
		if (!FresnelUtils.isStringEmpty(label)) {
			predicate = new URIImpl(PROPERTY_RDFS_LABEL);
			object = new LiteralImpl(label);
			statement = new StatementImpl(groupSubject, predicate, object);
			resultStatements.add(statement);
		}

		// rdfs:comment
		if (!FresnelUtils.isStringEmpty(comment)) {
			predicate = new URIImpl(PROPERTY_RDFS_COMMENT);
			object = new LiteralImpl(comment);
			statement = new StatementImpl(groupSubject, predicate, object);
			resultStatements.add(statement);
		}

		// Group styles
		for (StyleGuiWrapper style : styles) {

			String styleTypeUri = null;
			switch (style.getType()) {
			case LABEL:
				styleTypeUri = Constants.FRESNEL_NAMESPACE_URI
						+ Constants._labelStyle;
				break;
			case VALUE:
				styleTypeUri = Constants.FRESNEL_NAMESPACE_URI
						+ Constants._valueStyle;
				break;
			case PROPERTY:
				styleTypeUri = Constants.FRESNEL_NAMESPACE_URI
						+ Constants._propertyStyle;
				break;
			case RESOURCE:
				styleTypeUri = Constants.FRESNEL_NAMESPACE_URI
						+ Constants._resourceStyle;
				break;
			default:
				LOG.error("Invalid format style type - %s!", style.getType());
				throw new IndexOutOfBoundsException(
						"Invalid format style type!");
			}
			predicate = new URIImpl(styleTypeUri);

			// TODO: Support for CSS snippets
			object = new LiteralImpl(style.getValue(), DATATYPE_STYLE_CLASS);

			resultStatements.add(new StatementImpl(groupSubject, predicate,
					object));
		}

		// TODO: Support for additional contents

		// External CSS stylesheet
		if (!FresnelUtils.isStringEmpty(cssStylesheetUrl)) {

//			predicate = new URIImpl(
//					GroupModelManager.EXTERNAL_CSS_REFERENCE_PROPERTY);
//			object = new URIImpl(cssStylesheetUrl);
//			statement = new StatementImpl(groupSubject, predicate, object);
//			resultStatements.add(statement);
		}

		// Associations with Fresnel Lenses
		for (URI lensUri : associatedLensUris) {

			predicate = new URIImpl(Constants.FRESNEL_NAMESPACE_URI
					+ Constants._group);
			object = lensUri;
			statement = new StatementImpl(lensUri, predicate, groupSubject);
			resultStatements.add(statement);
		}

		// Associations with Fresnel Formats
		for (URI formatUri : associatedFormatUris) {

			predicate = new URIImpl(Constants.FRESNEL_NAMESPACE_URI
					+ Constants._group);
			object = formatUri;
			statement = new StatementImpl(formatUri, predicate, groupSubject);
			resultStatements.add(statement);
		}

		// TODO: Just testing output - remove afterwards.
		for (Statement s : resultStatements) {
			System.out.println(s);
		}

		return resultStatements;
	}

	/**
	 * 
	 * @return
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * 
	 * @param uri
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the group
	 */
	public Group getFresnelGroup() {
		return fresnelGroup;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setFresnelGroup(Group group) {
		this.fresnelGroup = group;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the cssStylesheetUrl
	 */
	public String getCssStylesheetUrl() {
		return cssStylesheetUrl;
	}

	/**
	 * @param cssStylesheetUrl
	 *            the cssStylesheetUrl to set
	 */
	public void setCssStylesheetUrl(String cssStylesheetUrl) {
		this.cssStylesheetUrl = cssStylesheetUrl;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the styles
	 */
	public List<StyleGuiWrapper> getStyles() {
		return styles;
	}

	/**
	 * @param styles
	 *            the styles to set
	 */
	public void setStyles(List<StyleGuiWrapper> styles) {
		this.styles = styles;
	}

	/**
	 * @return the additionalContents
	 */
	public List<AdditionalContentGuiWrapper> getAdditionalContents() {
		return additionalContents;
	}

	/**
	 * @param additionalContents
	 *            the additionalContents to set
	 */
	public void setAdditionalContents(
			List<AdditionalContentGuiWrapper> additionalContents) {
		this.additionalContents = additionalContents;
	}

	/**
	 * @return the associatedLensUris
	 */
	public List<URI> getAssociatedLensUris() {
		return associatedLensUris;
	}

	/**
	 * @param associatedLensUris
	 *            the associatedLensUris to set
	 */
	public void setAssociatedLensUris(List<URI> associatedLensUris) {
		this.associatedLensUris = associatedLensUris;
	}

	/**
	 * @return the associatedFormatUris
	 */
	public List<URI> getAssociatedFormatUris() {
		return associatedFormatUris;
	}

	/**
	 * @param associatedFormatUris
	 *            the associatedFormatUris to set
	 */
	public void setAssociatedFormatUris(List<URI> associatedFormatUris) {
		this.associatedFormatUris = associatedFormatUris;
	}

}
