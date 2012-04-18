package cz.muni.fi.fresneleditor.model;

import java.util.Collections;
import java.util.List;

import org.openrdf.model.Namespace;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.query.QueryLanguage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class DataRepositoryDao extends BaseRepositoryDao {

	private final static Logger LOG = LoggerFactory
			.getLogger(DataRepositoryDao.class);

	/**
	 * @see BaseRepositoryDao#BaseRepositoryDao()
	 */
	public DataRepositoryDao(String repositoryId) {
		super(repositoryId);
	}

	public DataRepositoryDao(IRepositoryConfiguration conf) {
		super(conf.getName(), conf.getLocation(), conf.getType());
	}

	/**
	 * Returns all classes from repository.
	 * 
	 * @return
	 */
	public List<Value> getClasses() {
		return getClasses(Collections.<Namespace> emptyList());
	}

	/**
	 * Returns subjects which have rdf:type rdfs:Class and are defined in one of
	 * the namespaces contained in the filter.
	 * 
	 * @param filter
	 *            only properties from namespaces in filter will be included. <br>
	 *            If filter is <code>null</code> or empty all properties are
	 *            included
	 * 
	 * @return
	 */
	public List<Value> getClasses(List<Namespace> filter) {
		StringBuilder query = new StringBuilder();

		query.append(SparqlUtils.getSparqlQueryPrefixes(repository))
				.append(" SELECT ?className ")
				.append("   WHERE { ?className rdf:type rdfs:Class ")
				.append(" . }");

		List<Value> classes = execTupleQuery(query.toString(),
				QueryLanguage.SPARQL, "className");

		// take only classes that start with our prefix
		return filterIsEmtpy(filter) ? classes : ModelUtils.filterURIs(classes,
				filter);
	}

	/**
	 * Returns list of all properties in this repository
	 */
	public List<Value> getProperties() {
		return getProperties(null);
	}

	/**
	 * Returns list of all properties in this repository
	 * 
	 * @param filter
	 *            only properties from namespaces in filter will be included. <br>
	 *            If filter is <code>null</code> or empty all properties are
	 *            included
	 * 
	 * @return
	 */
	public List<Value> getProperties(List<Namespace> filter) {
		String query = SparqlUtils.getSparqlQueryPrefixes(repository);
		query += " SELECT DISTINCT ?property WHERE { ?property rdf:type rdf:Property . }";

		List<Value> properties = execTupleQuery(query, QueryLanguage.SPARQL,
				"property");

		if (filter == null) {
			filter = Collections.emptyList();
		}
		LOG.debug("All properties count: " + properties.size());
		List<Value> filteredProperties = ModelUtils.filterURIs(properties,
				filter);
		LOG.debug("All properties after applying filter '" + filter
				+ "' count: " + filteredProperties.size());
		return filteredProperties;
	}

	/**
	 * Returns all properties from repository with given prefix.
	 * 
	 * @param filter
	 *            defines which properties will only be returned
	 * @return returned properties with given namespace
	 */
	@SuppressWarnings("unchecked")
	public List<Value> getPropertiesForClass(URI filter) {
		Assert.notNull(filter);

		StringBuilder query = new StringBuilder(
				SparqlUtils.getSparqlQueryPrefixes(repository));
		// boolean isPrefixed = (filter.getNamespace() != null);

		query.append(" SELECT DISTINCT ?property WHERE {  ?x ?property ?y . ?x rdf:type ");
		query.append(SparqlUtils.getUriForSparqlQuery(filter.toString(),
				repository));
		query.append(" . ?property rdf:type rdf:Property . }");

		List<Value> properties = execTupleQuery(query.toString(),
				QueryLanguage.SPARQL, "property");

		List<Value> filteredProperties = null;
		// if(!isPrefixed){
		// for(Namespace nameSpc : getNamespaces()){
		// if(filter.toString() == nameSpc.getPrefix()){
		// List<Namespace> loadedNamespace = Lists.newArrayList();
		// loadedNamespace.add(nameSpc);
		// filteredProperties = ModelUtils.filterURIs(properties,
		// loadedNamespace );
		// break;
		// }
		// }
		// }

		if (filteredProperties == null) {
			filteredProperties = (List<Value>) ((properties != null) ? properties
					: Lists.newArrayList());
		}

		LOG.debug("All properties after applying filter '" + filter
				+ "' count: " + filteredProperties.size());
		return filteredProperties;
	}

	/**
	 * Returns all properties from repository with given prefix.
	 * 
	 * @param filter
	 *            defines which properties will only be returned
	 * @return returned properties with given namespace
	 * @author nodrock
	 * @SuppressWarnings("unchecked") public List<Value>
	 *                                getPropertiesForClass(URI filter) {
	 *                                Assert.notNull(filter);
	 * 
	 *                                StringBuilder query = new
	 *                                StringBuilder(SparqlUtils
	 *                                .getSparqlQueryPrefixes(repository));
	 *                                String filter2Namespace =
	 *                                getNamespace(filter.toString());
	 *                                query.append(
	 *                                " SELECT DISTINCT ?property WHERE {  ?x ?property ?y . ?x rdf:type "
	 *                                ); if(filter2Namespace == null){
	 *                                query.append
	 *                                ("<").append(filter.toString())
	 *                                .append("> "); } else {
	 *                                query.append(filter2Namespace); }
	 *                                query.append
	 *                                (" . ?property rdf:type rdf:Property . }"
	 *                                );
	 * 
	 *                                List<Value> properties =
	 *                                execTupleQuery(query.toString(),
	 *                                QueryLanguage.SPARQL, "property");
	 * 
	 *                                List<Value> filteredProperties = null;
	 *                                if(filter2Namespace == null){
	 *                                for(Namespace nameSpc : getNamespaces()){
	 *                                if(filter.toString() ==
	 *                                nameSpc.getPrefix()){ List<Namespace>
	 *                                loadedNamespace = Lists.newArrayList();
	 *                                loadedNamespace.add(nameSpc);
	 *                                filteredProperties =
	 *                                ModelUtils.filterURIs(properties,
	 *                                loadedNamespace ); break; } } }
	 * 
	 *                                if(filteredProperties == null){
	 *                                filteredProperties = (List<Value>)
	 *                                ((properties !=
	 *                                null)?properties:Lists.newArrayList()); }
	 * 
	 *                                LOG.debug(
	 *                                "All properties after applying filter '" +
	 *                                filter + "' count: " +
	 *                                filteredProperties.size()); return
	 *                                filteredProperties; }
	 */
	/**
	 * Returns list of properties which are directly defined for a given class
	 * and are not inferred. <br>
	 * <br>
	 * For each property which is returned in the list there exists <br>
	 * <code>classUri property someValue</code> <br>
	 * triple in the repository.
	 * 
	 * @param classUri
	 * @return
	 */
	public List<Value> getDirectClassProperties(String classUri) {
		StringBuilder query = new StringBuilder();
		query.append(SparqlUtils.getSparqlQueryPrefixes(repository));
		query.append(" SELECT DISTINCT ?property WHERE ")
				.append(" { ?property rdf:type rdf:Property . ")
				.append("   ?property rdfs:domain ")
				.append(SparqlUtils.getUriForSparqlQuery(classUri, repository))
				.append(" . ").append(" } ");

		return execTupleQuery(query.toString(), QueryLanguage.SPARQL,
				"property");
	}

	/**
	 * Returns all properties from repository with given prefix.
	 * 
	 * @param filter
	 *            defines which properties will only be returned
	 * @return returned properties with given namespace
	 */
	@SuppressWarnings("unchecked")
	public List<Value> getPropertiesForInstance(URI filter) {
		Assert.notNull(filter);

		StringBuilder query = new StringBuilder(
				SparqlUtils.getSparqlQueryPrefixes(repository));
		// String filter2Namespace =
		// getNamespace(filter.getNamespace().substring(0,filter.getNamespace().length()-1));
		// boolean isPrefixed = (filter.getNamespace() != null);

		query.append(" SELECT DISTINCT ?property WHERE {  ");
		query.append(SparqlUtils.getUriForSparqlQuery(filter.toString(),
				repository));
		query.append(" ?property ?y . ?property rdf:type rdf:Property . }");

		List<Value> properties = execTupleQuery(query.toString(),
				QueryLanguage.SPARQL, "property");

		List<Value> filteredProperties = null;
		// if(!isPrefixed){
		// for(Namespace nameSpc : getNamespaces()){
		// if(filter.toString() == nameSpc.getPrefix()){
		// List<Namespace> loadedNamespace = Lists.newArrayList();
		// loadedNamespace.add(nameSpc);
		// filteredProperties = ModelUtils.filterURIs(properties,
		// loadedNamespace );
		// break;
		// }
		// }
		// }

		if (filteredProperties == null) {
			filteredProperties = (List<Value>) ((properties != null) ? properties
					: Lists.newArrayList());
		}

		LOG.debug("All properties after applying filter '" + filter
				+ "' count: " + filteredProperties.size());
		return filteredProperties;
	}

	/**
	 * Returns list of properties which are directly defined for a given
	 * instance and are not inferred. <br>
	 * For each property which is returned in the list there exists <br>
	 * <code>instanceUri property someValue</code> <br>
	 * triple in the repository.
	 * 
	 * @param classUri
	 * @return
	 */
	public List<Value> getPropertiesForInstance(String instanceUri) {
		StringBuilder query = new StringBuilder();
		query.append(SparqlUtils.getSparqlQueryPrefixes(repository));
		query.append(" SELECT DISTINCT ?property WHERE ")
				.append(" { ")
				.append(SparqlUtils.getUriForSparqlQuery(instanceUri,
						repository)).append(" ?property ?x . } ");

		return execTupleQuery(query.toString(), QueryLanguage.SPARQL,
				"property");
	}

}
