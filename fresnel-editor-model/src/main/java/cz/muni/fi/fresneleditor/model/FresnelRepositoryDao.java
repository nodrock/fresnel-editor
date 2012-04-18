package cz.muni.fi.fresneleditor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.query.QueryLanguage;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import fr.inria.jfresnel.Constants;
import fr.inria.jfresnel.Format;
import fr.inria.jfresnel.FresnelDocument;
import fr.inria.jfresnel.Group;
import fr.inria.jfresnel.Lens;
import fr.inria.jfresnel.sesame.FresnelSesameParser;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class FresnelRepositoryDao extends BaseRepositoryDao {

	private final static Logger LOG = LoggerFactory
			.getLogger(FresnelRepositoryDao.class);

	// fixme igor: cache the fresnel document somehow after first parsing
	// refresh it only when the repository data changes

	/**
	 * @see BaseRepositoryDao#BaseRepositoryDao()
	 */
	public FresnelRepositoryDao(String repositoryId) {
		super(repositoryId);
	}

	public FresnelRepositoryDao(IRepositoryConfiguration conf) {
		super(conf.getName(), conf.getLocation(), conf.getType());
	}

	@Override
	public Repository getRepository() {
		return repository;
	}

	public FresnelDocument getFresnelDocument() {
		FresnelSesameParser parser = new FresnelSesameParser(null, null);
		return parser.parse(repository, "http://this");
	}

	/**
	 * Returns <code>fresnel:Lense</code>s defined in this repository.
	 * 
	 * @return
	 */
	public List<Lens> getLenses() {
		FresnelSesameParser parser = new FresnelSesameParser(null, null);
		FresnelDocument fresnelDocument = parser.parse(repository,
				getBaseUriForThisRepository());
		return Arrays.asList(fresnelDocument.getLenses());
	}

	/**
	 * Returns the list of objects which are of type fresnel:Lens.
	 * 
	 * @return
	 * 
	 * @return list of the lenses in this repository
	 */
	// fixme igor: check this method, the type checking is suspicious
	public List<URI> getLensURIs() {
		List<Resource> subjects = getResources(new URIImpl(Constants._Lens));
		List<URI> lensURIs = new ArrayList<URI>();
		for (Resource resource : subjects) {
			if (resource instanceof URI) {
				lensURIs.add((URI) resource);
			} else {
				Assert.isTrue(false);
				// fixme igor: how to solve this? we should never get here.. but
				// what if we do?:)
			}
		}
		return lensURIs;
	}

	/**
	 * Returns specific lens from the repository.
	 * 
	 * @param lensURI
	 *            URI of the lens to return
	 * @return lens with specified URI
	 */
	public Lens getLens(String lensURI) {
		FresnelSesameParser parser = new FresnelSesameParser(null, null);
		FresnelDocument fresnelDocument = parser.parse(repository,
				"http://this");
		return fresnelDocument.getLens(lensURI);
	}

	public Lens getLens(String lensURI, String baseURI) {
		FresnelSesameParser parser = new FresnelSesameParser(null, null);
		FresnelDocument fresnelDocument = parser.parse(repository, baseURI);
		return fresnelDocument.getLens(lensURI);
	}

	public Lens getLens(URI lensUri) {
		return getLens(lensUri.stringValue());
	}

	/**
	 * Returns <code>fresnel:Format</code>s defined in this repository.
	 * 
	 * @return
	 */
	public List<Format> getFormats() {
		// fresnel:Format
		// fixme igor; how to get the base URI? is it necessary?
		FresnelSesameParser parser = new FresnelSesameParser(null, null);
		FresnelDocument fresnelDocument = parser.parse(repository,
				getBaseUriForThisRepository());
		return Arrays.asList(fresnelDocument.getFormats());
	}

	public List<URI> getFormatsURIs() {
		List<Resource> subjects = getResources(new URIImpl(Constants._Format));
		List<URI> formatURIs = new ArrayList<URI>();
		for (Resource resource : subjects) {
			if (resource instanceof URI) {
				formatURIs.add((URI) resource);
			} else {
				Assert.isTrue(false);
				// fixme igor: how to solve this? we should never get here.. but
				// what if we do?:)
			}
		}
		return formatURIs;
	}

	/**
	 * Get the format whose URI is the given formatURI.
	 * 
	 * @param formatURI
	 * @return
	 */
	public Format getFormat(String formatURI) {
		FresnelSesameParser parser = new FresnelSesameParser(null, null);
		FresnelDocument fresnelDocument = parser.parse(repository,
				"http://this");
		return fresnelDocument.getFormat(formatURI);
	}

	/**
	 * Returns <code>fresnel:Group</code>s defined in this repository.
	 * 
	 * @return
	 */
	public List<Group> getGroups() {
		// fresnel:Group
		FresnelSesameParser parser = new FresnelSesameParser(null, null);
		FresnelDocument fresnelDocument = parser.parse(repository,
				getBaseUriForThisRepository());
		return Arrays.asList(fresnelDocument.getGroups());
	}

	public List<URI> getGroupsURIs() {
		List<Resource> subjects = getResources(new URIImpl(Constants._Group));
		List<URI> groupsURIs = new ArrayList<URI>();
		for (Resource resource : subjects) {
			if (resource instanceof URI) {
				groupsURIs.add((URI) resource);
			} else {
				Assert.isTrue(false);
				// fixme igor: how to solve this? we should never get here.. but
				// what if we do?:)
			}
		}
		return groupsURIs;
	}

	public Group getGroup(String groupURI) {
		FresnelSesameParser parser = new FresnelSesameParser(null, null);
		FresnelDocument fresnelDocument = parser.parse(repository,
				"http://this");
		return fresnelDocument.getGroup(groupURI);
	}

	/**
	 * Prints the statements to the log.
	 * 
	 * @param statements
	 */
	public void printStatements(List<? extends Statement> statements) {
		for (Statement st : statements) {
			LOG.debug(st.getSubject() + " " + st.getPredicate() + " "
					+ st.getObject());
		}
	}

	/**
	 * This method updates original Fresnel resource (in case when originalModel
	 * is not null) or inserts new Fresnel resource into repository (in case
	 * when originalModel is null).
	 * 
	 * @param originalModel
	 *            originalModel to be updated, if it is null then it means that
	 *            new model is inserted to repository
	 * @param newModel
	 *            updated originalModel or completely new model (in case when
	 *            originalModel is null)
	 */
	public void updateFresnelResource(IModel originalModel, IModel newModel) {

		RepositoryConnection connection = null;
		try {
			if (originalModel != null) {
				LOG.info("Updating statements of Fresnel resource ("
						+ originalModel.getModelUri() + ") in repository.");
				// delete original resource from repository
				connection = repository.getConnection();
				// set manual commit mode
				connection.setAutoCommit(false);

				// System.out.println("BEFORE DELETE:");
				// printStatements(System.out);
				// System.out.println("------------------------------------");
				// System.out.println("GOING TO DELETE:");
				// for (Statement s: originalModel.asStatements()) {
				// System.out.println(s);
				// }
				// System.out.println("------------------------------------");

				connection.remove(originalModel.asStatements());

				// System.out.println("AFTER DELETE:");
				// printStatements(connection.getStatements(null, null, null,
				// false, NULL_CONTEXTS).asList());
				// does not work - it is not commited
				// printStatements(System.out, false);

			} else {
				LOG.info("Adding new Fresnel resource ("
						+ newModel.getModelUri() + ") to repository.");
				connection = repository.getConnection();
			}

			// System.out.println("BEFORE INSERT:");
			// printStatements(connection.getStatements(null, null, null, false,
			// NULL_CONTEXTS).asList());
			// System.out.println("------------------------------------");
			// System.out.println("GOING TO INSERT:");
			// printStatements(newModel.asStatements());
			// System.out.println("------------------------------------");

			// add new resource to the repository
			connection.add(newModel.asStatements());
			connection.commit();

			System.out.println("AFTER COMMITED INSERT:");
			printStatements(System.out, false);

			LOG.info("Statements of resource (" + newModel.getModelUri()
					+ ") were succesfully updated.");

		} catch (RepositoryException e) {
			LOG.error(
					"Error when updating Fresnel resource ( "
							+ originalModel.getModelUri() + ")!", e);
			try {
				connection.rollback();
			} catch (RepositoryException e1) {
				LOG.error("Error when rolling back the repository update!", e);
				// TODO: Is additional finally clause needed here?
			}
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * Returns a contexts for given model which can be used to identify the
	 * statements belonging to this model.
	 * 
	 * @param newModel
	 * @return
	 */
	// private static Resource[] getContextsForModel(IModel newModel) {
	// return new Resource[] { new URIImpl(newModel.getModelUri()) };
	// }

	public void deleteLens(String lensUri) {
		// find all statements where lensUri is object, predicate or subject

		// for properties which do not directly refer to lensUri find the
		// statements

		// check showProperties:
		// if value is resource ok
		// if value is blank node - it is a list - delete the list definition
		// the list contains either property uris or blank nodes - blank nodes
		// are property descriptions

		// check hideProperties: can be list or final property

	}

	/**
	 * 
	 * @param resourceModel
	 */
	public void deleteFresnelResource(IModel resourceModel) {

		RepositoryConnection connection = null;
		try {
			if (resourceModel == null) {
				LOG.warn("Trying to delete null resource - delete operation skipped!");
				return;
			}
			LOG.info("Deleting Fresnel resource ("
					+ resourceModel.getModelUri() + ") from repository.");

			connection = repository.getConnection();
			connection.setAutoCommit(false);
			connection.remove(resourceModel.asStatements());

			connection.commit();

			LOG.info("Statements of resource (" + resourceModel.getModelUri()
					+ ") were succesfully deleted.");

		} catch (RepositoryException e) {
			LOG.error(
					"Error when deleting Fresnel resource ( "
							+ resourceModel.getModelUri() + ")!", e);
			try {
				connection.rollback();
			} catch (RepositoryException e1) {
				LOG.error("Error when rolling back the repository update!", e);
				// TODO: Is additional finally clause needed here?
			}
		} finally {
			closeConnection(connection);
		}
	}

	public List<URI> getLensesUrisForGroup(URI groupURI) {
		return getResourceUrisForGroup(groupURI, Constants._Lens);
	}

	public List<URI> getFormatsUrisForGroup(URI groupURI) {
		return getResourceUrisForGroup(groupURI, Constants._Format);
	}

	private List<URI> getResourceUrisForGroup(URI groupURI,
			String resourceTypeURI) {

		String prefixes = SparqlUtils.getSparqlQueryPrefixes(repository);

		String queryString = prefixes + " SELECT ?x WHERE { ?x fresnel:group <"
				+ groupURI + "> . " + " ?x rdf:type <" + resourceTypeURI
				+ "> }";

		List<Value> values = execTupleQuery(queryString, QueryLanguage.SPARQL,
				"x");

		List<URI> resourceUris = new ArrayList<URI>();

		if (values != null && values.size() != 0) {

			for (Value value : values) {
				resourceUris.add((URI) value);
			}
		}

		return resourceUris;
	}

}
