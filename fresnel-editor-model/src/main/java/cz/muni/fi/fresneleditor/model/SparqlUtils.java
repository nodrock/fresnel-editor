package cz.muni.fi.fresneleditor.model;

import org.openrdf.model.Namespace;
import org.openrdf.model.impl.NamespaceImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class SparqlUtils {

	private static final Namespace RDF_NAMESPACE = new NamespaceImpl("rdf",
			"http://www.w3.org/1999/02/22-rdf-syntax-ns#");
	private static final Namespace RDFS_NAMESPACE = new NamespaceImpl("rdfs",
			"http://www.w3.org/2000/01/rdf-schema#");

	/**
	 * Returns a string containing prefixes defined in the given repository. The
	 * returned string is in format that can be used for a SPARQL query.
	 * 
	 * @param repository
	 * @return string containing prefixes defined in the given repository for a
	 *         usage in SPARQL query
	 */
	public static String getSparqlQueryPrefixes(Repository repository) {
		StringBuilder query = new StringBuilder();
		RepositoryResult<Namespace> namespaces;
		RepositoryConnection con = null;
		try {
			con = repository.getConnection();
			namespaces = con.getNamespaces();
			while (namespaces.hasNext()) {
				Namespace space = namespaces.next();
				appendNamespace(query, space);
			}

			// add well known rdf and rdfs prefixes if they are missing
			// they are used by basic queries (these might be issued on empty
			// repositories and the missing prefixes are causing troubles)
			if (query.indexOf(RDF_NAMESPACE.getName()) == -1) {
				appendNamespace(query, RDF_NAMESPACE);
			}
			if (query.indexOf(RDFS_NAMESPACE.getName()) == -1) {
				appendNamespace(query, RDFS_NAMESPACE);
			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return query.toString();
	}

	private static void appendNamespace(StringBuilder builder,
			Namespace namespace) {
		// PREFIX pref: <pref_name>
		builder.append(" PREFIX ").append(namespace.getPrefix()).append(": <")
				.append(namespace.getName()).append("> ");
	}

	/**
	 * Returns a string containing prefixes defined in the given repository. The
	 * returned string is in format that can be used for a SPARQL query.
	 * 
	 * @param repository
	 * @return string containing prefixes defined in the given repository for a
	 *         usage in SPARQL query
	 */
	public static String getSparqlQueryPrefixesHumanReadable(
			Repository repository) {
		StringBuilder query = new StringBuilder();
		RepositoryResult<Namespace> namespaces;
		RepositoryConnection con = null;
		try {
			con = repository.getConnection();
			namespaces = con.getNamespaces();
			while (namespaces.hasNext()) {
				Namespace space = namespaces.next();
				appendHumanReadableNamespace(query, space);
			}

			// add well known rdf and rdfs prefixes if they are missing
			// they are used by basic queries (these might be issued on empty
			// repositories and the missing prefixes are causing troubles)
			if (query.indexOf(RDF_NAMESPACE.getName()) == -1) {
				appendNamespace(query, RDF_NAMESPACE);
			}
			if (query.indexOf(RDFS_NAMESPACE.getName()) == -1) {
				appendNamespace(query, RDFS_NAMESPACE);
			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return query.toString();
	}

	private static void appendHumanReadableNamespace(StringBuilder builder,
			Namespace namespace) {
		// PREFIX pref: <pref_name>
		builder.append("PREFIX ").append(namespace.getPrefix()).append(": <")
				.append(namespace.getName()).append(">\n");
	}

	/**
	 * Returns format of given uri that can be used in a SPARQL query. If the
	 * uri is prefixed with a prefix which is known in the given repo than the
	 * return value of this function is the given uri. <br>
	 * If the uri is not prefixed with a known prefix it is assumed that it has
	 * no prefix and the value returned by this function is &lt;uri&gt;
	 * 
	 * @param uri
	 * @param repo
	 * @return given uri if it has prefix known by repo <br>
	 *         &lt;uri&gt otherwise
	 */
	public static String getUriForSparqlQuery(String uri, Repository repo) {
		// check if the instanceUri starts with one of known prefixes
		RepositoryConnection con = null;
		try {
			con = repo.getConnection();
			RepositoryResult<Namespace> namespaces = con.getNamespaces();
			while (namespaces.hasNext()) {
				Namespace space = namespaces.next();
				if (uri.startsWith(space.getPrefix())) {
					return uri;
				}
			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// instanceUri is not prefixed => it has to be put into <> symbols for
		// SPARQL query
		if (!uri.startsWith("<")) {
			uri = "<" + uri;
		}
		if (!uri.endsWith(">")) {
			uri += ">";
		}
		return uri;
	}

}
