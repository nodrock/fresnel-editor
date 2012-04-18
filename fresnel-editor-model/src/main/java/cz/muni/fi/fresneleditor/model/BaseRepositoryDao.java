package cz.muni.fi.fresneleditor.model;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Namespace;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.n3.N3Writer;
import org.openrdf.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.openrdf.sail.memory.MemoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import fr.inria.jfresnel.Constants;
import fr.inria.jfresnel.fsl.FSLHierarchyStore;
import fr.inria.jfresnel.fsl.FSLNSResolver;
import fr.inria.jfresnel.fsl.FSLPath;
import fr.inria.jfresnel.fsl.sesame.FSLSesameEvaluator;

/**
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class BaseRepositoryDao {

	public static enum RepositoryDomain {
		DATA, FRESNEL
	}

	public enum RepositoryType {
		LOCAL, HTTP
	}

	private final static Logger LOG = LoggerFactory
			.getLogger(BaseRepositoryDao.class);

	protected static final Resource[] NULL_CONTEXTS = new Resource[] {};

	protected Repository repository;

	protected String name;
	protected String url;
	protected RepositoryType type;

	/**
	 * Creates a DAO that operates above an in memory repository. The data is
	 * not persisted in any way (all the work is lost after disposing this
	 * repository).
	 */
	public BaseRepositoryDao(String repositoryId) {
		this(repositoryId, null, null);
	}

	/**
	 * Creates a new DAO which operates above defined repository. For a local
	 * repository the data is persisted.
	 * 
	 * @param repositoryId
	 *            unique repository ID
	 * @param url
	 *            based on repository type specifies either directory for local
	 *            repository or is an HTTP URL for HTTP repository
	 * @param type
	 *            repository type
	 */
	public BaseRepositoryDao(String repositoryId, String url,
			RepositoryType type) {
		this.name = repositoryId;
		this.url = url;
		this.type = type;

		if (url == null) {
			// no URL - create a DAO that operates above an in memory repository
			repository = getSailMemoryInferenceRepository();
		} else {

			switch (type) {
			case HTTP:
				repository = new HTTPRepository(url);
				break;
			case LOCAL:
				repository = getSailPersistentInferenceRepository(url);
				break;
			default:
				throw new IllegalArgumentException(
						"Unsupported type of repository: " + type);
			}
		}
	}

	/**
	 * Adds the statements from the file to the repository of this DAO.
	 * 
	 * @param file
	 * @param format
	 *            RDF format of the file
	 * @param baseURI
	 *            the base URI to which are elements without any prefix
	 *            specification resolved in the imported file <br>
	 *            if the baseURI is empty String or null than file URL is used
	 *            as base URI see
	 *            {@link RepositoryConnection#add(File, String, RDFFormat, Resource...)}
	 * @throws DataImportException
	 */
	public long addData(File file, RDFFormat format, String baseURI)
			throws DataImportException {
		return addFileToRepository(repository, file, baseURI, format);
	}

	/**
	 * Adds the statements from the reader.
	 * 
	 * nodrock
	 * 
	 * @param is
	 * @param format
	 * @param baseURI
	 * @return
	 * @throws DataImportException
	 */
	public long addData(Reader reader, RDFFormat format, String baseURI)
			throws DataImportException {
		if (!StringUtils.hasText(baseURI)) {
			// FIXME: netusim co by som sem dal :)
			baseURI = "http://this";
		}

		RepositoryConnection conn = null;
		try {

			conn = repository.getConnection();
			long sizeBefore = conn.size(new Resource[] {});
			conn.add(reader, baseURI, format);
			long sizeAfter = conn.size(new Resource[] {});
			conn.close();
			return sizeAfter - sizeBefore;
		} catch (OpenRDFException e) {
			// handle exception
			throw new DataImportException("Error during adding data to '"
					+ getName() + "' repository: " + e.getMessage(), e);
		} catch (IOException e) {
			// handle io exception
			throw new DataImportException("Error during adding data to '"
					+ getName() + "' repository: " + e.getMessage(), e);
		} finally {
			closeConnection(conn);
		}
	}

	/**
	 * Adds given statements to this repository.
	 * 
	 * @param statementsToAdd
	 * @return number of imported statements (can be different from number of
	 *         passed statements if the repository already included some of
	 *         those statements)
	 * @throws DataImportException
	 */
	public long addData(List<? extends Statement> statementsToAdd)
			throws DataImportException {
		RepositoryConnection con = null;
		try {
			con = repository.getConnection();
			long sizeBefore = con.size(new Resource[] {});
			con.add(statementsToAdd, NULL_CONTEXTS);
			long sizeAfter = con.size(new Resource[] {});
			con.close();
			return sizeAfter - sizeBefore;
		} catch (OpenRDFException e) {
			// handle exception
			throw new DataImportException("Error during adding data to '"
					+ getName() + "' repository: " + e.getMessage(), e);
		} finally {
			closeConnection(con);
		}
	}

	/**
	 * Prints all statements contained in this repository to the [out] output
	 * stream in N3 format.
	 * 
	 * @param out
	 *            OutputStream to which all statements are printed
	 * @param includeInferred
	 * @return number of statements contained this DAO's repository
	 */
	public long printStatements(OutputStream out, boolean includeInferred) {
		return printStatements(new N3Writer(out), includeInferred);
	}

	/**
	 * Prints all statements contained in this repository to the [out] output
	 * stream in given format.
	 * 
	 * Closes the rdfHandler.
	 * 
	 * @param rdfHandler
	 *            RDF output format handler
	 * @param includeInferred
	 * @return number of statements contained this DAO's repository
	 */
	public long printStatements(RDFHandler rdfHandler, boolean includeInferred) {
		long count = 0;
		// TODO: maybe pass this context as parameter?
		Resource[] contexts = new Resource[0];
		RepositoryConnection con;
		try {
			con = repository.getConnection();
			count = con.size(contexts);
			con.exportStatements(null, null, null, includeInferred, rdfHandler,
					contexts);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} catch (RDFHandlerException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		}
		return count;
	}

	/**
	 * Returns result of a tuple query with single binding name.
	 * 
	 * @param query
	 * @param language
	 * @param bindingName
	 * @return
	 */
	public List<Value> execTupleQuery(String query, QueryLanguage language,
			String bindingName) {
		List<String> bindingNames = Lists.newArrayList(bindingName);
		List<Map<String, Value>> result = execTupleQuery(query, language,
				bindingNames);

		if (result == null) {
			return null;
		}

		List<Value> resultList = new ArrayList<Value>(result.size());
		for (Map<String, Value> entry : result) {
			resultList.add(entry.get(bindingName));
		}

		return resultList;
	}

	public List<Map<String, Value>> execSPARQLQuery(String query) {

		RepositoryConnection con = null;
		try {
			con = repository.getConnection();
			String fullQuery = new String();// SparqlUtils.getSparqlQueryPrefixes(repository);
			fullQuery += query;
			LOG.debug("Executing query: " + fullQuery.toString());
			TupleQueryResult result = con.prepareTupleQuery(
					QueryLanguage.SPARQL, fullQuery).evaluate();

			List<Map<String, Value>> resultList = new ArrayList<Map<String, Value>>();
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Map<String, Value> res = new HashMap<String, Value>();
				for (String bindingName : bindingSet.getBindingNames()) {
					Value value = bindingSet.getValue(bindingName);
					res.put(bindingName, value);
				}
				resultList.add(res);
			}

			return resultList;
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} finally {
			closeConnection(con);
		}

		return null;
	}

	/**
	 * Returns result of a tuple query with multiple binding names. The result
	 * is in format of a list with each item being a map keyed by given binding
	 * names.
	 * 
	 * @param query
	 * @param language
	 * @param bindingNames
	 * @return
	 */
	protected List<Map<String, Value>> execTupleQuery(String query,
			QueryLanguage language, List<String> bindingNames) {
		RepositoryConnection con = null;
		try {
			con = repository.getConnection();
			LOG.debug("Executing query: " + query.toString());
			TupleQueryResult result = con.prepareTupleQuery(language, query)
					.evaluate();

			List<Map<String, Value>> resultList = new ArrayList<Map<String, Value>>();

			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Map<String, Value> res = new HashMap<String, Value>();
				for (String bindingName : bindingNames) {
					Value value = bindingSet.getValue(bindingName);
					res.put(bindingName, value);
				}
				resultList.add(res);
			}
			return resultList;
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} finally {
			closeConnection(con);
		}

		return null;
	}

	public Vector evalFSLPath(String fslPath) {
		FSLNSResolver nsr = new FSLNSResolver();

		nsr.addPrefixBinding("rdf",
				"http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		nsr.addPrefixBinding("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		nsr.addPrefixBinding("foaf", "http://xmlns.com/foaf/0.1/");

		FSLHierarchyStore fhs = new FSLHierarchyStore();

		FSLSesameEvaluator fse = new FSLSesameEvaluator(nsr, fhs);

		fse.setDataRepository(repository);

		FSLPath p = FSLPath.pathFactory(fslPath, nsr, FSLPath.NODE_STEP);

		Vector pathInstances = fse.evaluatePath(p);

		return pathInstances;
	}

	public TupleResult executeTupleQuery(QueryLanguage language, String query) {
		RepositoryConnection con = null;
		try {
			con = repository.getConnection();
			LOG.debug("Executing query: " + query.toString());

			TupleQueryResult result = con.prepareTupleQuery(language, query)
					.evaluate();

			List<String> bindingNames = result.getBindingNames();

			List<Map<String, Value>> resultList = new ArrayList<Map<String, Value>>();

			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Map<String, Value> res = new HashMap<String, Value>();
				for (String bindingName : bindingNames) {
					Value value = bindingSet.getValue(bindingName);
					res.put(bindingName, value);
				}
				resultList.add(res);
			}

			return new TupleResult(bindingNames, resultList);
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} finally {
			closeConnection(con);
		}

		return null;
	}

	public boolean executeBooleanQuery(QueryLanguage language, String query) {
		RepositoryConnection con = null;
		try {
			con = repository.getConnection();
			LOG.debug("Executing query: " + query.toString());

			return con.prepareBooleanQuery(language, query).evaluate();
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} finally {
			closeConnection(con);
		}

		return false;
	}

	/**
	 * Returns initialized persistent repository for given dataDir. If dataDir
	 * is null returns in memory repository.
	 * 
	 * @param dataDir
	 *            the directory to which the store is persisted. Can be null -
	 *            in this case in memory repository is created and the
	 *            repository data is not persisted.
	 * 
	 * @return
	 */
	private static Repository initRepository(String dataDir) {
		boolean persistant = dataDir != null;

		File dataDirFile = null;
		boolean creatingNew = false;

		Repository myRepository;
		if (persistant) {
			dataDirFile = new File(dataDir);
			creatingNew = !dataDirFile.exists();
			myRepository = new SailRepository(
					new ForwardChainingRDFSInferencer(new MemoryStore(
							dataDirFile)));
		} else {
			myRepository = new SailRepository(new MemoryStore());
		}

		// Initialize the repository
		try {
			if (persistant) {
				if (creatingNew) {
					LOG.info("Creating new persistance repository: {}",
							dataDirFile.getAbsolutePath());
				} else {
					LOG.info(
							"Trying to open existing persistance repository from directory: {}",
							dataDirFile.getAbsolutePath());
				}
			} else {
				LOG.info("Creating new memory store repository");
			}
			myRepository.initialize();
			LOG.info("Repository was successfully initialized");
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		}

		return myRepository;
	}

	/**
	 * Shortcut method for {@link #initRepository(String)}.
	 * 
	 * @param dataDir
	 * @return
	 */
	private static Repository getSailPersistentInferenceRepository(
			String dataDir) {
		return initRepository(dataDir);
	}

	/**
	 * Shortcut method for {@link #initRepository(String)} where the String
	 * parameter is null.
	 * 
	 * @return
	 */
	private static Repository getSailMemoryInferenceRepository() {
		return initRepository(null);
	}

	/**
	 * 
	 * @param repository
	 * @param file
	 * @param baseURI
	 * @param rdfFormat
	 * @return number of statements that were added
	 * @throws DataImportException
	 *             if an error occurs during adding the data to repository
	 */
	private long addFileToRepository(Repository repository, File file,
			String baseURI, RDFFormat rdfFormat) throws DataImportException {

		if (!StringUtils.hasText(baseURI)) {
			// fixme igor: shouldn't here be the full name and not only the last
			// part?
			baseURI = "file:///" + file.getName();
		}

		RepositoryConnection con = null;
		try {
			con = repository.getConnection();
			long sizeBefore = con.size(new Resource[] {});
			con.add(file, baseURI, rdfFormat);
			long sizeAfter = con.size(new Resource[] {});
			con.close();
			return sizeAfter - sizeBefore;
		} catch (OpenRDFException e) {
			// handle exception
			throw new DataImportException("Error during adding data to '"
					+ getName() + "' repository: " + e.getMessage(), e);
		} catch (IOException e) {
			// handle io exception
			throw new DataImportException("Error during adding data to '"
					+ getName() + "' repository: " + e.getMessage(), e);
		} finally {
			closeConnection(con);
		}
	}

	public String getName() {
		return name;
	}

	public Repository getRepository() {
		return repository;
	}

	/**
	 * Adds given list of namespaces to repository known namespaces.
	 * 
	 * @param namespaces
	 *            list of namespaces which should be defined inside this
	 *            repository
	 */
	public void setNamespaces(List<Namespace> namespaces) {
		RepositoryConnection con = null;
		try {
			con = repository.getConnection();
			for (Namespace namespace : namespaces) {
				con.setNamespace(namespace.getPrefix(), namespace.getName());
			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} finally {
			closeConnection(con);
		}
	}

	/**
	 * Returns all declared namespaces in this repository.
	 * 
	 * @see RepositoryConnection#getNamespaces()
	 */
	public List<Namespace> getNamespaces() {
		RepositoryConnection con = null;
		try {
			con = repository.getConnection();
			RepositoryResult<Namespace> result = con.getNamespaces();
			ArrayList<Namespace> transformedResult = new ArrayList<Namespace>();
			ArrayList<Namespace> namespaces = result
					.addTo(new ArrayList<Namespace>());
			List<NamespaceImplCustom> transformed = Lists.transform(namespaces,
					new Function<Namespace, NamespaceImplCustom>() {
						@Override
						public NamespaceImplCustom apply(Namespace from) {
							return new NamespaceImplCustom(from);
						}
					});
			transformedResult.addAll(transformed);
			return transformedResult;
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} finally {
			closeConnection(con);
		}
		return null;
	}

	/**
	 * Returns name of the namespace associated with given prefix or null if
	 * there is no such namespace defined.
	 * 
	 * @param prefix
	 * @return
	 */
	public String getNamespace(String prefix) {
		RepositoryConnection con = null;
		try {
			con = repository.getConnection();
			return con.getNamespace(prefix);
		} catch (RepositoryException e) {
			LOG.warn(e.getMessage(), e);
		} finally {
			closeConnection(con);
		}
		return null;
	}

	/**
	 * Shortcut method for calling
	 * {@link #getResources(Value, boolean, Resource[])} with default
	 * parameters. <br>
	 * 
	 * @param resourceType
	 *            this is the type of which the returned resources will be
	 * @return list of subjects whose rdf:type is object
	 */
	public List<Resource> getResources(Value resourceType) {
		return getResources(resourceType, false, new Resource[] {});
	}

	/**
	 * Returns all subjects
	 * 
	 * @param resourceType
	 * @param includeInferred
	 * @param contexts
	 * @return
	 */
	public List<Resource> getResources(Value resourceType,
			boolean includeInferred, Resource[] contexts) {
		List<Resource> result = new ArrayList<Resource>();
		RepositoryConnection con = null;
		try {
			con = repository.getConnection();
			URI pred = new URIImpl(Constants.RDF_NAMESPACE_URI
					+ Constants._type);
			RepositoryResult<Statement> statements = con.getStatements(null,
					pred, resourceType, includeInferred, contexts);
			while (statements.hasNext()) {
				Statement next = statements.next();
				result.add(next.getSubject());
			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
		} finally {
			closeConnection(con);
		}
		return result;
	}

	/**
	 * Return all resources (RDF subjects which have rdf:type rdfs:Resource).
	 * 
	 * @return
	 */
	public List<Value> getResources() {
		return getResources(Collections.<Namespace> emptyList());
	}

	/**
	 * Return resources (RDF subjects which have rdf:type rdfs:Resource) which
	 * are in filtered namespaces.
	 * 
	 * @param filter
	 *            only resources from namespaces in filter will be included. <br>
	 *            If filter is <code>null</code> or empty all properties are
	 *            included
	 * 
	 * @return
	 */
	public List<Value> getResources(List<Namespace> filter) {
		String query = SparqlUtils.getSparqlQueryPrefixes(repository);
		query += " SELECT ?resource WHERE { ?resource rdf:type rdfs:Resource . } ";
		List<Value> result = execTupleQuery(query, QueryLanguage.SPARQL,
				"resource");
		// fixme igor: is it not possible to apply the filter in the sparql
		// query?
		return filterIsEmtpy(filter) ? result : ModelUtils.filterURIs(result,
				filter);
	}

	public List<Value> getDistinctInstances(List<Namespace> filter) {
		String query = SparqlUtils.getSparqlQueryPrefixes(repository);
		query += " SELECT DISTINCT * WHERE { ?a a ?b . ?b rdf:type rdfs:Resource . } ";
		List<Value> result = execTupleQuery(query, QueryLanguage.SPARQL, "a");
		return filterIsEmtpy(filter) ? result : ModelUtils.filterURIs(result,
				filter);
	}

	/**
	 * Returns number of all the statements in the repository.
	 * 
	 * @return
	 */
	public long size() {
		return size(new Resource[] {});
	}

	/**
	 * Returns count of statements that are in the specified contexts in the
	 * repository.
	 * 
	 * @param contexts
	 *            see {@link RepositoryConnection#size(Resource...)}
	 * @return
	 */
	public long size(Resource... contexts) {
		RepositoryConnection con = null;
		try {
			con = repository.getConnection();
			return con.size(contexts);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			LOG.warn(e.getMessage(), e);
			return 0;
		} finally {
			closeConnection(con);
		}
	}

	protected void closeConnection(RepositoryConnection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (RepositoryException e) {
				// fixme igor: is this correct handling of this situation?

				// do nothing
				LOG.warn("Error during closing connection to repository '"
						+ getName() + "': " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Utility method. Returns true if filter is null or empty list.
	 * 
	 * @param filter
	 *            list to check
	 * @return true if filter is null or empty list. False otherwise.
	 */
	protected boolean filterIsEmtpy(List<Namespace> filter) {
		return filter == null || filter.isEmpty();
	}

	/**
	 * Adds given namespace to namespaces defined in this repository.
	 * 
	 * @param namespace
	 *            to add
	 */
	public void addNamespace(Namespace namespace) throws DataImportException {

		LOG.info(getName() + ": adding namespace: " + namespace);

		RepositoryConnection con = null;
		try {
			con = repository.getConnection();

			String namespaceName = namespace.getName();
			// TODO: why???
			if (!namespaceName.endsWith("#")) {
				namespaceName += "#";
			}
			// @prefix fresnel: <http://www.w3.org/2004/09/fresnel#> .
			// StringReader reader = new StringReader("@prefix "+
			// namespace.getPrefix()
			// + ":  <" + namespaceName+ "> .");
			// con.add(reader, "", RDFFormat.N3, NULL_CONTEXTS);
			// con.commit();
			con.setNamespace(namespace.getPrefix(), namespace.getName());

			con.close();

			con = repository.getConnection();
			RepositoryResult<Namespace> res = con.getNamespaces();
			List<Namespace> list = res.asList();
			System.out.print(list.toString());
			con.close();

		} catch (OpenRDFException e) {
			// handle exception
			throw new DataImportException("Error during adding data to '"
					+ getName() + "' repository: " + e.getMessage(), e);
			// } catch (IOException e) {
			// // handle io exception
			// throw new DataImportException("Error during adding data to '" +
			// getName() + "' repository: "
			// + e.getMessage(), e);
		} finally {
			closeConnection(con);
		}
	}

	/**
	 * Returns true if there exists a resource within this repository from given
	 * namespace.
	 * 
	 * @param namespace
	 * @return true if there exists a resource within this repository from given
	 *         namespace
	 */
	public boolean isNamespaceUsed(Namespace namespace) {
		// TODO
		return true;
	}

	/**
	 * Returns all objects for given subject and predicate.
	 * 
	 * @param subject
	 * @param predicate
	 * @return
	 */
	public List<URI> getObjects(Resource subject, URI predicate) {
		// fixme igor: make this more generic so it can return list of Values
		RepositoryConnection connection = null;
		List<URI> objects = new ArrayList<URI>();
		try {
			connection = repository.getConnection();
			RepositoryResult<Statement> result = connection.getStatements(
					subject, predicate, null, false, NULL_CONTEXTS);
			while (result.hasNext()) {
				Statement next = result.next();
				objects.add(new URIImpl(next.getObject().stringValue()));
			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return objects;
	}

	/**
	 * @return
	 */
	protected String getBaseUriForThisRepository() {
		// TODO
		// fixme igor; how to get the base URI? is it necessary?
		return "http://" + getName();
	}

	/**
	 * Warning! Deletes all statements from repository!
	 */
	public void clearAllData() throws RepositoryException {

		RepositoryConnection connection = repository.getConnection();
		connection.clear();
		connection.close();
	}

}
