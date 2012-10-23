/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.gui.mod.format.data;

import java.util.ArrayList;
import java.util.List;

import org.openrdf.model.BNode;
import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.BNodeImpl;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cz.muni.fi.fresneleditor.common.data.AdditionalContentGuiWrapper;
import cz.muni.fi.fresneleditor.common.data.StyleGuiWrapper;
import cz.muni.fi.fresneleditor.model.IModel;
import fr.inria.jfresnel.Constants;
import fr.inria.jfresnel.Format;
import fr.inria.jfresnel.formats.FormatValueLabelPolicy;
import fr.inria.jfresnel.formats.FormatValueType;

/**
 * Class which wraps all necessary data about Fresnel Format for GUI to be able
 * to work with it.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 19.3.2009
 */
public class FormatModel implements IModel {

	private static final Logger LOG = LoggerFactory
			.getLogger(FormatModel.class);

	public final String NAMESPACE_URI_XSD = "http://www.w3.org/2001/XMLSchema#";
	public final String DATATYPE_XSD_STRING = NAMESPACE_URI_XSD + "string";
	public final String TYPE_RESOURCE = Constants.RDFS_NAMESPACE_URI
			+ "Resource";

	// Note: Should be full URI string
	private String uri;

	// rdfs:label
	private String label = "";

	// rdfs:comment
	private String comment = "";

	// JFresnel Format class is not used directly in GUI
	private Format fresnelFormat;

	private List<DomainSelectorGuiWrapper> domainSelectors = new ArrayList<DomainSelectorGuiWrapper>();

	private List<StyleGuiWrapper> styles = new ArrayList<StyleGuiWrapper>();

	private List<AdditionalContentGuiWrapper> additionalContents = new ArrayList<AdditionalContentGuiWrapper>();

	private FormatValueLabelPolicy labelType;

	private String literalLabelValue;

	private FormatValueType valueType;

	private List<URI> associatedGroupURIs = new ArrayList<URI>();

	/**
	 * Default empty constructor.
	 */
	public FormatModel() {
	}

	/**
	 * Returns Fresnel Format URI.
	 * 
	 * @return Fresnel Format URI
	 */
	@Override
	public String getModelUri() {
		return uri;
	}

	@Override
	public List<? extends Statement> asStatements() {

		List<StatementImpl> resultStatements = new ArrayList<StatementImpl>();

//		Resource formatSubject = new URIImpl(uri);
//		URI predicate = null;
//		Value object = null;
//		StatementImpl statement = null;
//
//		// format a Resource
//		predicate = new URIImpl(PROPERTY_RDF_TYPE);
//		object = new URIImpl(TYPE_RESOURCE);
//		statement = new StatementImpl(formatSubject, predicate, object);
//		resultStatements.add(statement);
//
//		// rdf:type
//		predicate = new URIImpl(PROPERTY_RDF_TYPE);
//		object = new URIImpl(Constants._Format);
//		statement = new StatementImpl(formatSubject, predicate, object);
//		resultStatements.add(statement);
//
//		// rdfs:label
//		if (label != null) {
//			predicate = new URIImpl(PROPERTY_RDFS_LABEL);
//			object = label;
//			statement = new StatementImpl(formatSubject, predicate, object);
//			resultStatements.add(statement);
//		}
//
//		// rdfs:comment
//		if (comment != null) {
//			predicate = new URIImpl(PROPERTY_RDFS_COMMENT);
//			object = comment;
//			statement = new StatementImpl(formatSubject, predicate, object);
//			resultStatements.add(statement);
//		}
//
//		// FORMAT DOMAINS
//		for (DomainSelectorGuiWrapper domainSelector : domainSelectors) {
//
//			String domainTypePropertyUri = null;
//			switch (domainSelector.getDomainType()) {
//			case PROPERTY:
//				domainTypePropertyUri = Constants.FRESNEL_NAMESPACE_URI
//						+ Constants._propertyFormatDomain; // TODO: Create own
//															// constant
//				break;
//			case CLASS:
//				domainTypePropertyUri = Constants.FRESNEL_NAMESPACE_URI
//						+ Constants._classFormatDomain; // TODO: Create own
//														// constant
//				break;
//			case INSTANCE:
//				domainTypePropertyUri = Constants.FRESNEL_NAMESPACE_URI
//						+ Constants._instanceFormatDomain; // TODO: Create own
//															// constant
//				break;
//			default:
//				LOG.error("Invalid format domain type - %s!",
//						domainSelector.getDomainType());
//				throw new IndexOutOfBoundsException(
//						"Invalid format domain type!");
//			}
//			predicate = new URIImpl(domainTypePropertyUri);
//
//			switch (domainSelector.getSelectorType()) {
//			case SIMPLE:
//				object = new URIImpl(domainSelector.getSelectorString());
//				break;
//			case FSL:
//				object = new LiteralImpl(domainSelector.getSelectorString(),
//						new URIImpl(Constants._fslSelector));
//				break;
//			case SPARQL:
//				object = new LiteralImpl(domainSelector.getSelectorString(),
//						new URIImpl(Constants._sparqlSelector));
//				break;
//			default:
//				LOG.error("Invalid format domain selector type - %s!",
//						domainSelector.getSelectorType());
//				throw new IndexOutOfBoundsException(
//						"Invalid format domain type!");
//			}
//
//			statement = new StatementImpl(formatSubject, predicate, object);
//			resultStatements.add(statement);
//		}
//
//		// FORMAT LABEL
//		if (labelType == FormatValueLabelPolicy.NOT_SPECIFIED) {
//			// Nothing
//			// TODO: Maybe fresnel:default
//		} else if (labelType == FormatValueLabelPolicy.NONE) {
//			predicate = new URIImpl(Constants.FRESNEL_NAMESPACE_URI
//					+ Constants._label); // TODO: Create own constant
//			object = new URIImpl(Constants._none);
//			resultStatements.add(new StatementImpl(formatSubject, predicate,
//					object));
//		} else if (labelType == FormatValueLabelPolicy.SHOW) {
//			predicate = new URIImpl(Constants.FRESNEL_NAMESPACE_URI
//					+ Constants._label);
//			object = new LiteralImpl(literalLabelValue, new URIImpl(
//					DATATYPE_XSD_STRING));
//			resultStatements.add(new StatementImpl(formatSubject, predicate,
//					object));
//		} else {
//			LOG.error("Invalid format label type - %s!", labelType);
//			throw new IndexOutOfBoundsException("Invalid format label type!");
//		}
//
//		// FORMAT VALUE
//		if (valueType == FormatValueType.NOT_SPECIFIED) {
//			// Nothing
//			// TODO: Maybe fresnel:default
//		} else {
//			predicate = new URIImpl(Constants.FRESNEL_NAMESPACE_URI
//					+ Constants._value); // TODO: Create own constant
//			object = new URIImpl(valueType.getUri());
//                        if (object == null) {
//                            LOG.error("Invalid format value type - %s!", valueType);
//                            throw new IndexOutOfBoundsException("Invalid format value type!");
//			}
//			resultStatements.add(new StatementImpl(formatSubject, predicate,
//					object));
//		}
//
//		// FORMAT STYLES
//		for (StyleGuiWrapper style : styles) {
//
//			String styleTypeUri = null;
//			switch (style.getType()) {
//			case LABEL:
//				styleTypeUri = Constants.FRESNEL_NAMESPACE_URI
//						+ Constants._labelStyle;
//				break;
//			case VALUE:
//				styleTypeUri = Constants.FRESNEL_NAMESPACE_URI
//						+ Constants._valueStyle;
//				break;
//			case PROPERTY:
//				styleTypeUri = Constants.FRESNEL_NAMESPACE_URI
//						+ Constants._propertyStyle;
//				break;
//			case RESOURCE:
//				styleTypeUri = Constants.FRESNEL_NAMESPACE_URI
//						+ Constants._resourceStyle;
//				break;
//			default:
//				LOG.error("Invalid format style type - %s!", style.getType());
//				throw new IndexOutOfBoundsException(
//						"Invalid format style type!");
//			}
//			predicate = new URIImpl(styleTypeUri);
//
//			// FIXME: Support for CSS snippets
//			object = new LiteralImpl(style.getValue(), new URIImpl(
//					DATATYPE_STYLE_CLASS));
//
//			resultStatements.add(new StatementImpl(formatSubject, predicate,
//					object));
//		}
//
//		// FORMAT ADDITIONAL CONTENT
//		for (AdditionalContentGuiWrapper additionalContent : additionalContents) {
//
//			if (additionalContent.getCountOfSetAdditionalContents() > 0) {
//
//				String additionalContentTypeUri = null;
//				switch (additionalContent.getType()) {
//				case LABEL:
//					additionalContentTypeUri = Constants.FRESNEL_NAMESPACE_URI
//							+ Constants._labelFormat;
//					break;
//				case VALUE:
//					additionalContentTypeUri = Constants.FRESNEL_NAMESPACE_URI
//							+ Constants._valueFormat;
//					break;
//				case PROPERTY:
//					additionalContentTypeUri = Constants.FRESNEL_NAMESPACE_URI
//							+ Constants._propertyFormat;
//					break;
//				case RESOURCE:
//					additionalContentTypeUri = Constants.FRESNEL_NAMESPACE_URI
//							+ Constants._resourceFormat;
//					break;
//				default:
//					LOG.error("Invalid additional content type - %s!",
//							additionalContent.getType());
//					throw new IndexOutOfBoundsException(
//							"Invalid additional content type!");
//				}
//				predicate = new URIImpl(additionalContentTypeUri);
//
//				object = mapAdditionalContentObject(resultStatements,
//						additionalContent);
//
//				resultStatements.add(new StatementImpl(formatSubject,
//						predicate, object));
//			} else {
//				LOG.info("Empty additional content - nothing will be mapped to statements.");
//			}
//		}
//
//		for (URI groupURI : associatedGroupURIs) {
//			predicate = new URIImpl(Constants.FRESNEL_NAMESPACE_URI
//					+ Constants._group);
//			object = groupURI;
//			resultStatements.add(new StatementImpl(formatSubject, predicate,
//					object));
//		}
//
//		System.out.println(resultStatements);

		return resultStatements;
	}

	private Value mapAdditionalContentObject(
			List<StatementImpl> resultStatements,
			AdditionalContentGuiWrapper additionalContent) {

		int additionalContentsCount = additionalContent
				.getCountOfSetAdditionalContents();
		URI predicate = null;
		Value object = null;

		if (additionalContentsCount <= 0) {
			LOG.warn("mapAdditionalContentObject() called with additionalContentsCount <=0. Mapping skipped!");
			return null;
		}

		BNode additionalContentsBNode = new BNodeImpl(
				additionalContent.getType() + "_" + getUri());

		if (StringUtils.hasText(additionalContent.getContentBefore())) {
			predicate = new URIImpl(Constants._contentBefore);
			object = new LiteralImpl(additionalContent.getContentBefore(),
					DATATYPE_XSD_STRING);
			resultStatements.add(new StatementImpl(additionalContentsBNode,
					predicate, object));
		}

		if (StringUtils.hasText(additionalContent.getContentAfter())) {
			predicate = new URIImpl(Constants._contentAfter);
			object = new LiteralImpl(additionalContent.getContentAfter(),
					DATATYPE_XSD_STRING);
			resultStatements.add(new StatementImpl(additionalContentsBNode,
					predicate, object));
		}

		if (StringUtils.hasText(additionalContent.getContentFirst())) {
			predicate = new URIImpl(Constants._contentFirst);
			object = new LiteralImpl(additionalContent.getContentFirst(),
					DATATYPE_XSD_STRING);
			resultStatements.add(new StatementImpl(additionalContentsBNode,
					predicate, object));
		}

		if (StringUtils.hasText(additionalContent.getContentLast())) {
			predicate = new URIImpl(Constants._contentLast);
			object = new LiteralImpl(additionalContent.getContentLast(),
					DATATYPE_XSD_STRING);
			resultStatements.add(new StatementImpl(additionalContentsBNode,
					predicate, object));
		}

		if (StringUtils.hasText(additionalContent.getContentNoValue())) {
			predicate = new URIImpl(Constants._contentNoValue);
			object = new LiteralImpl(additionalContent.getContentNoValue(),
					DATATYPE_XSD_STRING);
			resultStatements.add(new StatementImpl(additionalContentsBNode,
					predicate, object));
		}

		return additionalContentsBNode;
	}

	/**
	 * @return the fresnelFormat
	 */
	public Format getFresnelFormat() {
		return fresnelFormat;
	}

	/**
	 * @param fresnelFormat
	 *            the fresnelFormat to set
	 */
	public void setFresnelFormat(Format fresnelFormat) {
		this.fresnelFormat = fresnelFormat;
	}

	/**
	 * 
	 * @return
	 */
	public List<DomainSelectorGuiWrapper> getDomainSelectors() {
		return domainSelectors;
	}

	/**
	 * 
	 * @param domainSelectors
	 */
	public void setDomainSelectors(
			List<DomainSelectorGuiWrapper> domainSelectors) {
		this.domainSelectors = domainSelectors;
	}

	/**
	 * 
	 * @return
	 */
	public List<StyleGuiWrapper> getStyles() {
		return styles;
	}

	public void setStyles(List<StyleGuiWrapper> styles) {
		this.styles = styles;
	}

	/**
	 * 
	 * @return
	 */
	public List<AdditionalContentGuiWrapper> getAdditionalContents() {
		return additionalContents;
	}

	/**
	 * 
	 * @param additionalContents
	 */
	public void setAdditionalContents(
			List<AdditionalContentGuiWrapper> additionalContents) {
		this.additionalContents = additionalContents;
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
	 * 
	 * @return
	 */
	public FormatValueLabelPolicy getLabelType() {
		return labelType;
	}

	/**
	 * 
	 * @param labelType
	 */
	public void setLabelType(FormatValueLabelPolicy labelType) {
		this.labelType = labelType;
	}

	/**
	 * 
	 * @return
	 */
	public String getLiteralLabelValue() {
		return literalLabelValue;
	}

	/**
	 * 
	 * @param literalLabelValue
	 */
	public void setLiteralLabelValue(String literalLabelValue) {
		this.literalLabelValue = literalLabelValue;
	}

	/**
	 * 
	 * @return
	 */
	public FormatValueType getValueType() {
		return valueType;
	}

	/**
	 * 
	 * @param valueType
	 */
	public void setValueType(FormatValueType valueType) {
		this.valueType = valueType;
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
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the associatedGroupURIs
	 */
	public List<URI> getAssociatedGroupURIs() {
		return associatedGroupURIs;
	}

	/**
	 * @param associatedGroupURIs
	 *            the associatedGroupURIs to set
	 */
	public void setAssociatedGroupURIs(List<URI> associatedGroupURIs) {
		this.associatedGroupURIs = associatedGroupURIs;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append("\n");
		sb.append("=== FORMAT OUTPUT BEGIN ===\n");

		sb.append("URI: " + uri + "\n");
		sb.append("Label: " + label + "\n");
		sb.append("Comment: " + comment + "\n");

		sb.append("Format domains:\n");
		for (DomainSelectorGuiWrapper ds : domainSelectors) {
			sb.append("\t" + ds + "\n");
		}

		sb.append("Label type: " + labelType + "\n");
		sb.append("Literal label value: " + literalLabelValue + "\n");

		sb.append("=== FORMAT OUTPUT END ===");

		return sb.toString();
	}
}
