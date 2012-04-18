package cz.muni.fi.fresneleditor.gui.mod.lens2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Resource;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.n3.N3Writer;
import org.openrdf.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.openrdf.sail.memory.MemoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import cz.muni.fi.fresneleditor.model.SparqlUtils;

/**
 * Class for performing various test.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class FresnelWorkerForTesting {

	private final Logger logger = LoggerFactory
			.getLogger(FresnelWorkerForTesting.class);

	private static final String FOAF_BASE_URI = "http://xmlns.com/foaf/0.1/";
	private static SailRepository myRepository;

	public FresnelWorkerForTesting() {
		initMyRepository();
	}

	private void initMyRepository() {
		myRepository = getSailMemoryInterferenceRepository();
		addFileToRepository(myRepository, "foaf.xml", FOAF_BASE_URI,
				RDFFormat.RDFXML);
	}

	/**
	 * 
	 * @param type
	 *            type from rdfs namespace (Resource, Class)
	 */
	public void printFullResources(String type) {
		SailRepository repo = getSailMemoryInterferenceRepository();

		addFileToRepository(repo, "foaf.xml",
				"http://my.opera.com/wapxana/xml/foaf#me", RDFFormat.RDFXML);

		String query = SparqlUtils.getSparqlQueryPrefixes(repo);
		query += " SELECT ?resource  ?y WHERE { ?resource ?y rdfs:" + type
				+ " . } ";
		List<Map<String, Value>> result = execTupleQuery(repo, query,
				QueryLanguage.SPARQL, Lists.newArrayList("resource", "y"));
		for (Map<String, Value> res : result) {
			if (isPrefixed(res.get("resource"),
					Lists.newArrayList("foaf:", FOAF_BASE_URI))) {
				// fixme igor: is this debug log necessary?
				logger.debug("nasel jsem: " + res.get("resource").stringValue()
						+ " " + res.get("y").stringValue() + " rdfs:" + type);
			}
		}
	}

	public List<Value> getResources() {
		String query = SparqlUtils.getSparqlQueryPrefixes(myRepository);
		query += " SELECT ?resource WHERE { ?resource rdf:type rdfs:Resource . } ";
		List<Value> result = execTupleQuery(myRepository, query,
				QueryLanguage.SPARQL, "resource");
		return filterURIs(result, Lists.newArrayList("foaf:", FOAF_BASE_URI));
	}

	public List<Value> getClasses() {
		StringBuilder query = new StringBuilder();

		// fixme igor: add filtering to the query?
		query.append(SparqlUtils.getSparqlQueryPrefixes(myRepository))
				.append(" SELECT ?className ")
				.append("   WHERE { ?className rdf:type rdfs:Class ")
				// .append("      FILTER (regex(?className, \"^").append(FOAF_BASE_URI).append("\")"
				// +
				// " || " +
				// .append(" FILTER ")
				// .append(" regex(?className, \"^foaf:\") " +
				.append(" . }");

		List<Value> classes = execTupleQuery(myRepository, query.toString(),
				QueryLanguage.SPARQL, "className");

		// take only classes that start with our prefix
		return filterURIs(classes, Lists.newArrayList("foaf:", FOAF_BASE_URI));
	}

	private List<Value> getDirectPropertiesForClass(String klassUri,
			Repository repo) {
		StringBuilder query = new StringBuilder();
		query.append(SparqlUtils.getSparqlQueryPrefixes(repo));

		query.append(" SELECT DISTINCT ?property WHERE ")
				.append(" { ?property rdf:type rdf:Property . ")
				.append("   ?property rdfs:domain ")
				.append(SparqlUtils.getUriForSparqlQuery(klassUri, repo))
				.append(" . ").append(" } ");

		/*
		 * query.append(" SELECT DISTINCT ?property WHERE ")
		 * .append(" { ?property rdf:type rdf:Property . ") .append(" } ");
		 */

		return execTupleQuery(repo, query.toString(), QueryLanguage.SPARQL,
				"property");
	}

	public List<Value> getAllPropertiesForClass(String fileName, String klassUri) {
		Repository repo = getSailMemoryInterferenceRepository();
		addFileToRepository(repo, fileName, null, RDFFormat.RDFXML);
		return getAllPropertiesForClass(klassUri, repo);
	}

	public List<Value> getAllPropertiesForClass(String klassUri) {
		return getAllPropertiesForClass(klassUri, myRepository);
	}

	private List<Value> getAllPropertiesForClass(String klassUri,
			Repository repo) {
		StringBuilder query = new StringBuilder();
		query.append(SparqlUtils.getSparqlQueryPrefixes(repo));

		query.append(" SELECT DISTINCT ?property WHERE ")
				.append(" { ?property rdf:type rdf:Property . ").append(" } ");

		return execTupleQuery(repo, query.toString(), QueryLanguage.SPARQL,
				"property");
	}

	public List<Value> getDirectPropertiesForClass(String fileName,
			String klassUri) {
		Repository repo = getSailMemoryInterferenceRepository();
		addFileToRepository(repo, fileName, null, RDFFormat.RDFXML);
		return getDirectPropertiesForClass(klassUri, repo);
	}

	public List<Value> getDirectPropertiesForClass(String klassUri) {
		return getDirectPropertiesForClass(klassUri, myRepository);
	}

	public List<Value> getPropertiesForInstance(String fileName,
			String instanceUri) {
		Repository repo = getSailMemoryInterferenceRepository();
		addFileToRepository(repo, fileName, null, RDFFormat.RDFXML);
		return getPropertiesForInstance(instanceUri, repo);
	}

	private List<Value> getPropertiesForInstance(String instanceUri,
			Repository repo) {
		StringBuilder query = new StringBuilder();
		query.append(SparqlUtils.getSparqlQueryPrefixes(repo));
		query.append(" SELECT DISTINCT ?property WHERE ").append(" { ")
				.append(SparqlUtils.getUriForSparqlQuery(instanceUri, repo))
				.append(" ?property ?x . } ");

		return execTupleQuery(repo, query.toString(), QueryLanguage.SPARQL,
				"property");
	}

	public List<Value> getPropertiesForInstance(String instanceUri) {
		return getPropertiesForInstance(instanceUri, myRepository);
	}

	public List<Value> getAllProperties() {
		String query = SparqlUtils.getSparqlQueryPrefixes(myRepository);
		query += " SELECT DISTINCT ?property WHERE { ?property rdf:type rdf:Property . }";

		List<Value> properties = execTupleQuery(myRepository, query,
				QueryLanguage.SPARQL, "property");
		return filterURIs(properties,
				Lists.newArrayList("foaf:", FOAF_BASE_URI));
	}

	private List<Value> filterURIs(List<Value> uris, List<String> prefixes) {
		List<Value> filteredClasses = new ArrayList<Value>();
		for (Value value : uris) {
			if (isPrefixed(value, prefixes)) {
				filteredClasses.add(value);
			}
		}

		return filteredClasses;
	}

	// fixme igor: make this filtering based on the context?
	private boolean isPrefixed(Value value, List<String> prefixes) {
		for (String pref : prefixes) {
			if (value.stringValue().startsWith(pref)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param fileName
	 * @param notUsed
	 *            is here only to distinguish between methods..
	 */
	public void printStatements(String fileName, Object notUsed) {
		SailRepository repo = getSailMemoryInterferenceRepository();
		addFileToRepository(repo, fileName, FOAF_BASE_URI, RDFFormat.RDFXML);
		printStatements(repo);
	}

	public void printStatements(Repository repo) {
		/*
		 * print the statements
		 */
		if (repo == null) {
			repo = myRepository;
		}

		Resource context = null;
		RepositoryConnection con;
		try {
			con = repo.getConnection();
			System.out.println("celkem je tu statements: " + con.size(context));
			con.exportStatements(null, null, null, true, new N3Writer(
					System.out), context);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RDFHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<Value> execTupleQuery(Repository repository, String query,
			QueryLanguage language, String bindingName) {
		List<String> bindingNames = Lists.newArrayList(bindingName);
		List<Map<String, Value>> result = execTupleQuery(repository, query,
				language, bindingNames);

		List<Value> resultList = new ArrayList<Value>(result.size());
		for (Map<String, Value> entry : result) {
			resultList.add(entry.get(bindingName));
		}

		return resultList;
	}

	private List<Map<String, Value>> execTupleQuery(Repository repository,
			String query, QueryLanguage language, List<String> bindingNames) {
		RepositoryConnection con = null;
		try {
			con = repository.getConnection();
			logger.debug("Executing query: " + query.toString());
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
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (RepositoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	private SailRepository getSailMemoryInterferenceRepository() {
		SailRepository memoryRepository = new SailRepository(
				new ForwardChainingRDFSInferencer(new MemoryStore()));
		try {
			memoryRepository.initialize();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memoryRepository;
	}

	private void addFileToRepository(Repository repository, String fileName,
			String baseURI, RDFFormat rdfFormat) {

		RepositoryConnection con = null;
		try {
			con = repository.getConnection();

			File file = new File(ClassLoader.getSystemResource(fileName)
					.getFile());
			con.add(file, baseURI, rdfFormat);
			con.close();

			/*
			 * print the statements Resource context = null;
			 * System.out.println("celkem je tu statements: " +
			 * con.size(context)); con.exportStatements(null, null, null, true,
			 * new N3Writer(System.out), context);
			 */

			// for adding data from web
			// URL url = new URL("http://xmlns.com/foaf/0.1/");
			// con.add(url, url.toString(), RDFFormat.RDFXML);

		} catch (OpenRDFException e) {
			// handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// handle io exception
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (RepositoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * public void doSomething() { FSLNSResolver nsr = new FSLNSResolver();
	 * FSLHierarchyStore fhs = new FSLJenaHierarchyStore(); FSLJenaEvaluator fje
	 * = new FSLJenaEvaluator(nsr, fhs);
	 * 
	 * FresnelJenaParser fp = new FresnelJenaParser(nsr, fhs);
	 * 
	 * File foafN3File = new File(ClassLoader.getSystemResource("foaf.n3")
	 * .getFile()); FresnelDocument fd = fp.parse(foafN3File,
	 * Constants.N3_READER);
	 * 
	 * Lens[] lenses = fd.getLenses(); Format[] formats = fd.getFormats();
	 * Format format = formats[0]; }
	 */
}