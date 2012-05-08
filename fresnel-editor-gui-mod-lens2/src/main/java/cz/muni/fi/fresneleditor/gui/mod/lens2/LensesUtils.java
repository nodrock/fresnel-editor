package cz.muni.fi.fresneleditor.gui.mod.lens2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openrdf.model.BNode;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.BNodeImpl;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.sail.memory.model.IntegerMemLiteral;
import org.springframework.util.Assert;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import cz.muni.fi.fresneleditor.common.data.SelectorType;
import cz.muni.fi.fresneleditor.gui.mod.lens2.model.LensSelector.LensDomain;
import fr.inria.jfresnel.Constants;
import fr.inria.jfresnel.Group;
import fr.inria.jfresnel.Lens;
import fr.inria.jfresnel.sesame.SesameBasicVisibility;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class LensesUtils {

	public static List<PropertyVisibilityWrapper> values2PropertyVisibilities(
			List<Value> values) {
		return Lists.transform(values, value2visibilityWrapperFunction);
	}

	private static final Function<Value, PropertyVisibilityWrapper> value2visibilityWrapperFunction = new Function<Value, PropertyVisibilityWrapper>() {
		@Override
		public PropertyVisibilityWrapper apply(final Value val) {
			// fixme igor: sesame dependency
			return new PropertyVisibilityWrapper(new SesameBasicVisibility(
					val.stringValue()));
		}
	};

	public static final List<StatementImpl> modelAsStatements(LensModel model) {
		List<StatementImpl> statements = new ArrayList<StatementImpl>();

		// fixme igor: can lense have multiple domains?? I do not think so
		// formats can have multiple domains
		// not supported by GUI yet

		Resource lensSubject = new URIImpl(model.getModelUri());
		{
			// the lens it self
			URI predicate = new URIImpl(Constants.RDF_NAMESPACE_URI
					+ Constants._type);
			Value object = new URIImpl(Constants._Lens);
			statements.add(new StatementImpl(lensSubject, predicate, object));
		}

		// selctor, domain
		if (model.getSelector() != null) {
			LensDomain lensDomain = model.getSelector().getDomain();
			URI predicate = new URIImpl(lensDomain.getUri());

			Value object;
			SelectorType selectorType = model.getSelector().getType();
			switch (selectorType) {
			case SIMPLE:
				// simple domain has to be Resource
				object = new URIImpl(model.getSelectorAsString());
				break;
			case FSL:
			case SPARQL:
				// fsl domain and sparql domain has to be Literal with datatype
				// specified
				object = new LiteralImpl(model.getSelectorAsString(),
						selectorType.getDatatypeURI());
				break;
			default:
				throw new ArrayIndexOutOfBoundsException(
						"unhandeled selector type: " + selectorType);
			}

			statements.add(new StatementImpl(lensSubject, predicate, object));
		}

		// purpose
		{
			String purp = model.getPurpose().getUri();
			if (purp != null) {
				Value object = new URIImpl(purp);
				URI predicate = new URIImpl(Constants.FRESNEL_NAMESPACE_URI
						+ Constants._purpose);
				statements
						.add(new StatementImpl(lensSubject, predicate, object));
			}
		}

		// hide properties
		List<PropertyVisibilityWrapper> hideProperties = model
				.getHideProperties();
		if (hideProperties != null) {
			for (PropertyVisibilityWrapper hide : hideProperties) {
				URI predicate = new URIImpl(Constants.FRESNEL_NAMESPACE_URI
						+ Constants._hideProperties);
				Value object = new URIImpl(hide.getFresnelPropertyValueURI());
				statements
						.add(new StatementImpl(lensSubject, predicate, object));
			}
		}

		// show properties
		statements.addAll(addShowPropertiesStatements(model, lensSubject));

		// groups
		List<Group> groups = model.getGroups();
		if (groups != null) {
			for (Group group : groups) {
				URI predicate = new URIImpl(Constants.FRESNEL_NAMESPACE_URI
						+ Constants._group);
				Value object = new URIImpl(group.getURI());
				statements
						.add(new StatementImpl(lensSubject, predicate, object));
			}
		}

		// comment
		if (model.getComment() != null) {
			URI predicate = new URIImpl(Constants.RDFS_NAMESPACE_URI
					+ Constants._comment);
			Value object = model.getComment();
			statements.add(new StatementImpl(lensSubject, predicate, object));
		}

		// label
		if (model.getLabel() != null) {
			// fixme igor: JFresnel sets the lens label even if it is not
			// defined in the repository
			// it generates it automatically from the URI
			URI predicate = new URIImpl(Constants.RDFS_NAMESPACE_URI
					+ Constants._label);
			Value object = model.getLabel();
			statements.add(new StatementImpl(lensSubject, predicate, object));
		}

		return statements;
	}

	/**
	 * Add statements to the 'statements' parameter that do represent this lens
	 * showProperties configuration.
	 * 
	 * @param statements
	 * @param lensSubject
	 *            the lens for which the statements are created
	 */
	private static final List<StatementImpl> addShowPropertiesStatements(
			LensModel model, Resource lensSubject) {
		List<StatementImpl> statements = new ArrayList<StatementImpl>();

		List<PropertyVisibilityWrapper> showProperties = model
				.getShowProperties();
		if (showProperties == null || showProperties.isEmpty()) {
			// do nothing
			return statements;
		}

		URIImpl showPropertiesPredicate = new URIImpl(
				Constants.FRESNEL_NAMESPACE_URI + Constants._showProperties);
		if (showProperties.size() == 1) {
			// there is only one property - we do not need to create list
			Value object = getShowPropertiesItem(model, statements,
					showProperties.get(0));
			statements.add(new StatementImpl(lensSubject,
					showPropertiesPredicate, object));
		} else {
			// more properties - we need a list

			/*
			 * :testLens2ItemsList rdf:type fresnel:Lens;
			 * fresnel:classLensDomain foaf:Person ; fresnel:showProperties (
			 * foaf:name foaf:surnam ). ------
			 * 
			 * <http://this#testLens2ItemsList> a fresnel:Lens ;
			 * fresnel:classLensDomain foaf:Person .
			 * 
			 * _:node13lhjvb99x11 rdf:first foaf:name ; rdf:rest
			 * _:node13lhjvb99x12 .
			 * 
			 * _:node13lhjvb99x12 rdf:first foaf:surnam ; rdf:rest rdf:nil .
			 * 
			 * :testLens2ItemsList fresnel:showProperties _:node13lhjvb99x11 .
			 */

			BNode listStart = new BNodeImpl("_list_item_"
					+ lensSubject.stringValue());
			statements.add(new StatementImpl(lensSubject,
					showPropertiesPredicate, listStart));

			URIImpl firstPredicate = new URIImpl(Constants.RDF_NAMESPACE_URI
					+ Constants._first);
			URIImpl restPredicate = new URIImpl(Constants.RDF_NAMESPACE_URI
					+ Constants._rest);

			for (int i = 0; i < showProperties.size(); i++) {
				PropertyVisibilityWrapper show = showProperties.get(i);
				Value propObject = getShowPropertiesItem(model, statements,
						show);
				BNode newListStart = new BNodeImpl("_list_item_"
						+ lensSubject.stringValue() + "_" + i);

				statements.add(new StatementImpl(listStart, firstPredicate,
						propObject));
				if (i < showProperties.size() - 1) {
					// this should be done for all but the last item
					statements.add(new StatementImpl(listStart, restPredicate,
							newListStart));
					listStart = newListStart;
				} else {
					// the last statement of the list differs
					URIImpl nilObject = new URIImpl(Constants._nil);
					statements.add(new StatementImpl(listStart, restPredicate,
							nilObject));
				}
			}
		}

		return statements;
	}

	/**
	 * Returns an object of showProperties predicate. Can be either blank node
	 * for complex fresnel:PropertyDescription or single value for simple
	 * property. For fresnel:PropertyDescription adds list of the statements
	 * that define this description into the 'statements' list parameter
	 * 
	 * @param statements
	 *            list of statements to which the statements defining the
	 *            property description are added
	 * @param show
	 *            URI of the property / property description
	 * @return
	 */
	private static final Value getShowPropertiesItem(LensModel model,
			List<StatementImpl> statements, PropertyVisibilityWrapper show) {
		if (show.isComplexPropertyDescription()) {
			// fixme igor: this is wrong - we could just dynamically create new
			// properties
			// for previously not defined MPVisibility.. we have to check each
			// property it self
			// depth, sublenses, use...
			// change the check to hasPropertiesDefined() method or somethin
			// like that...
			return addPropertyDescriptionStatements(model, statements, show);
		} else {
			return new URIImpl(show.getFresnelPropertyValueURI());
		}
	}

	/**
	 * Returns blank node that represents this property description and adds all
	 * statements defining this property description to 'statements' list
	 * parameter.
	 */
	private static final BNode addPropertyDescriptionStatements(
			LensModel model, List<StatementImpl> statements,
			PropertyVisibilityWrapper show) {
		/*
		 * [ rdf:type fresnel:PropertyDescription ; fresnel:property foaf:knows
		 * ; fresnel:sublens :foafPersonDefaultLens; fresnel:depth
		 * "5"^^xsd:nonNegativeInteger; fresnel:use :tableGroup ]
		 * ------------------------
		 * 
		 * _:node13lhiv9qbx9 a fresnel:PropertyDescription ; fresnel:property
		 * foaf:knows ; fresnel:sublens <http://this#foafPersonDefaultLens> ;
		 * fresnel:depth "5"^^xsd:nonNegativeInteger ; fresnel:use
		 * <http://this#tableGroup> .
		 */

		BNode propDesription = new BNodeImpl("_propDescBNode_"
				+ show.getFresnelPropertyValueURI());

		// rdf:type => _:node13lhiv9qbx9 a fresnel:PropertyDescription ;
		{
			URI predicate = new URIImpl(Constants.RDF_NAMESPACE_URI
					+ Constants._type);
			Value object = new URIImpl(Constants.FRESNEL_NAMESPACE_URI
					+ "PropertyDescription");
			statements
					.add(new StatementImpl(propDesription, predicate, object));
		}

		// fresnel:property foaf:knows ;
		{
			Assert.hasText(show.getFresnelPropertyValueURI(),
					"lens: " + model.getModelUri() + " propDescription: "
							+ show.getFresnelPropertyValueURI() + " show: "
							+ show);
			URI predicate = new URIImpl(Constants.FRESNEL_NAMESPACE_URI
					+ Constants._property);
			Value object = new URIImpl(show.getFresnelPropertyValueURI());
			statements
					.add(new StatementImpl(propDesription, predicate, object));
		}

		// fresnel:sublens <http://this#foafPersonDefaultLens> ;
		for (URI sublens : show.getSublensesURIs()) {
			URI predicate = new URIImpl(Constants.FRESNEL_NAMESPACE_URI
					+ Constants._sublens);
			Value object = sublens;
			statements
					.add(new StatementImpl(propDesription, predicate, object));
		}

		// fresnel:depth "5"^^xsd:nonNegativeInteger ;
		if (show.getMaxDepth() != PropertyVisibilityWrapper.MAX_DEPTH_NOT_DEFINED) {
			URI predicate = new URIImpl(Constants.FRESNEL_NAMESPACE_URI
					+ Constants._depth);
			// fixme igor: make constant of this
			Value object = new IntegerMemLiteral(model, new BigInteger(
					show.getMaxDepth() + ""));
			statements
					.add(new StatementImpl(propDesription, predicate, object));
		}

		// fresnel:use <http://this#tableGroup> .
		if (show.getFresnelUseUri() != null) {
			URI predicate = new URIImpl(Constants.FRESNEL_NAMESPACE_URI
					+ Constants._use);
			Value object = new URIImpl(show.getFresnelUseUri());
			statements
					.add(new StatementImpl(propDesription, predicate, object));
		}
		return propDesription;
	}

	/**
	 * Compares two lists of property visibilities. Returns true if both lists
	 * contains the same property visibilities.
	 * 
	 * @param list1
	 * @param list2
	 * @param orderMatters
	 *            if true than the order of the elements in the list is taken
	 *            into consideration when comparing the lists <br>
	 *            if false a match for prop visibility on i-th index can be
	 *            located on different than i-th index in the second list
	 * @return
	 */
	public static boolean equalsProperties(
			List<PropertyVisibilityWrapper> list1,
			List<PropertyVisibilityWrapper> list2, boolean orderMatters) {
		return compareLists(list1, list2, orderMatters,
				new Comparator<PropertyVisibilityWrapper>() {
					@Override
					public int compare(PropertyVisibilityWrapper o1,
							PropertyVisibilityWrapper o2) {
						if (o1.equals(o2)) {
							return 0;
						}
						return propertyRepresentant(o1).compareTo(
								propertyRepresentant(o2));
					}
				});
	}

	private static String propertyRepresentant(PropertyVisibilityWrapper wrapper) {
		return wrapper.getFresnelPropertyValueURI()
				+ wrapper.isComplexPropertyDescription();
	}

	/**
	 * Compares the two lists.
	 * 
	 * @param <T>
	 * @param list1
	 * @param list2
	 * @param orderMatters
	 *            if false than lists are sorted before comparision first
	 * @param comparator
	 *            used to compare the list elements
	 * @return true if both the lists contain the same property visibilities
	 */
	private static <T> boolean compareLists(List<T> list1, List<T> list2,
			boolean orderMatters, Comparator<T> comparator) {
		Assert.isTrue(list1.size() == list2.size());

		ArrayList<T> sorted1 = new ArrayList<T>(list1);

		ArrayList<T> sorted2 = new ArrayList<T>(list2);

		if (!orderMatters) {
			Collections.sort(sorted1, comparator);
			Collections.sort(sorted2, comparator);
		}

		for (int i = 0; i < sorted1.size(); i++) {
			if (comparator.compare(sorted1.get(i), sorted2.get(i)) != 0) {
				return false;
			}
		}
		return true;
	}

}
