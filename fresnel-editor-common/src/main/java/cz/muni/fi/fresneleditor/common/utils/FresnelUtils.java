package cz.muni.fi.fresneleditor.common.utils;

import java.util.List;
import java.util.regex.Pattern;

import org.openrdf.model.Namespace;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.util.URIUtil;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.model.BaseRepositoryDao;
import cz.muni.fi.fresneleditor.model.ModelUtils;
import fr.inria.jfresnel.Group;
import fr.inria.jfresnel.Lens;
import fr.inria.jfresnel.fsl.FSLPath;
import fr.inria.jfresnel.sparql.SPARQLQuery;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class FresnelUtils {

	/*
	 * The URI's scheme name (prefix) consists of a letter followed by any
	 * combination of letters, digits, and the plus ("+"), period ("."), or
	 * hyphen ("-") characters; and is terminated by a colon (":")
	 */
	private static final String NAMESPACE_PREFIX_REGEX = "[a-zA-Z][a-zA-Z0-9\\\\+\\\\.\\\\-]*";

	/**
	 * Returns null if the given string is valid URI for given DAO. URI is valid
	 * in case it contains the name of the namespace and a local name <br>
	 * or the namespace is specified by an namespace prefix known for given DAO. <br>
	 * Returns a message describing why the validation failed otherwise.
	 * 
	 * @param uri
	 *            to validate
	 * @param dao
	 *            the repository DAO for which this URI should be valid <br>
	 *            cannot be null
	 * @return null if the given URI is valid for given DAO <br>
	 *         a descriptive message in case the URI's namespace is not defined
	 *         by the whole namespace's name of the URI uses namespace prefix
	 *         unknown for the given DAO
	 */
	public static String validateResourceUri(String uri, BaseRepositoryDao dao) {
		Assert.notNull(dao);
		if (!StringUtils.hasText(uri)) {
			return "URI is empty - it has to contain namespace and local name definitions";
		}

		try {
			String namespaceUriPart = FresnelUtils.getNamespace(uri);

			/* Check if the namespace is defined by an prefix: */
			if (validateNamespacePrefixFormat(namespaceUriPart)) {
				if (uri.startsWith(namespaceUriPart + ":")
						&& dao.getNamespace(namespaceUriPart) != null) {
					return null;
				} else {
					return "The uri does not start with a prefix of a known namespace";
				}
			} else {
				// the namespace is not prefix but it is a full namespace name
				if (!validateNamespaceNameFormat(namespaceUriPart)) {
					return "The uri does not have valid URI format - the URI starts with invalid schema name";
				}
			}
		} catch (Exception ex) {
			return "Invalid URI format!";
		}

		return null;
	}

	/**
	 * Returns namespace of this URI.
	 * 
	 * @param uri
	 * @return
	 */
	public static String getNamespace(String uri) {
		return parseUri(uri, true);
	}

	/**
	 * Returns local name of this URI.
	 * 
	 * @param uri
	 * @return
	 */
	public static String getLocalName(String uri) {
		return parseUri(uri, false);
	}

	/**
	 * If the URI contains a namespace declared in the namespaces list then this
	 * method return the URI where the namespace name is replaced by its prefix.
	 * 
	 * @param uri
	 *            in which the namespace name should be replaced by its prefix
	 * @param namespaces
	 *            list of declared namespaces
	 * @return URI where the namespace is replaced by its prefix if this
	 *         namespace is declared in the dao <br>
	 *         If the URI's namespace is not declared in the list of namespaces
	 *         returns the URI unchanged
	 */
	public static String replaceNamespace(String uri, List<Namespace> namespaces) {
		for (Namespace namespace : namespaces) {
			if (uri.startsWith(namespace.getName())) {
				return uri.replace(namespace.getName(), namespace.getPrefix()
						+ ":");
			}
		}
		return uri;
	}

	public static String replacePrefix(String value) {

		List<Namespace> namespaces = ContextHolder.getInstance()
				.getFresnelRepositoryDao().getNamespaces();
		String selectorNamespace = FresnelUtils.getNamespace(value);
		for (Namespace namespace : namespaces) {
			if (namespace.getPrefix().equals(selectorNamespace)) {
				value = namespace.getName() + FresnelUtils.getLocalName(value);
			}
		}

		return value;
	}

	private static String parseUri(String uri, boolean retrievingNamespace) {
		String localName;
		String namespace = "";
		/*
		 * if (uri.contains("#")) { // uri has format
		 * 'http://something#localName' localName =
		 * uri.substring(uri.indexOf("#")); namespace = uri.substring(0,
		 * uri.indexOf("#")); } else if (uri.contains("://")){ // find the last
		 * occurence of / that is separating } else if (uri.contains(":")){ //
		 * uri has format 'prefix:localName' localName =
		 * uri.substring(uri.indexOf(":")); namespace = uri.substring(0,
		 * uri.indexOf(":")); } else { // uri has only local name localName =
		 * uri; namespace = ""; } if (localName.startsWith("#") ||
		 * localName.startsWith(":")){ localName = localName.substring(1); }
		 */

		int localNameIdx = URIUtil.getLocalNameIndex(uri);

		namespace = uri.substring(0, localNameIdx);
		localName = uri.substring(localNameIdx);

		if (namespace.endsWith(":")) {
			// do not include the last : in the prefix name
			namespace = namespace.substring(0, localNameIdx - 1);
		}

		return retrievingNamespace ? namespace : localName;
	}

	/**
	 * Equals method for {@link Lens} objects. Compares the lenses based on
	 * their URIs.
	 * 
	 * @param lens1
	 * @param lens2
	 * @return true if the lenses URIs are equal
	 */
	public static final boolean equalsLens(Lens lens1, Lens lens2) {
		if (lens1 == lens2)
			return true;
		if (lens1 == null) {
			return false;
		}
		if (lens1.getURI() == lens2.getURI()) {
			return true;
		}
		if (lens1.getURI() == null) {
			return false;
		}
		if (!lens1.getURI().equals(lens2.getURI())) {
			return false;
		}
		return true;
	}

	/**
	 * Equals method for {@link Group} objects. Compares the groups based on
	 * their URIs.
	 * 
	 * @param g1
	 * @param g2
	 * @return true if the groups URIs are equal
	 */
	public static final boolean equalsGroup(Group g1, Group g2) {
		if (g1 == g2)
			return true;
		if (g1 == null) {
			return false;
		}
		if (g1.getURI() == g2.getURI()) {
			return true;
		}
		if (g1.getURI() == null) {
			return false;
		}
		if (!g1.getURI().equals(g2.getURI())) {
			return false;
		}
		return true;
	}

	/**
	 * Helper method for comparing lists of {@link Group} objects.
	 * <p>
	 * Returns true if for each group in groups1 list there is exactly one group
	 * with the same URI in the groups2 list and vice versa. The order of the
	 * groups in each of the list does not matter.
	 * 
	 * @param groups1
	 * @param groups2
	 * @return true if for each group from one list there is exactly one group
	 *         with the same URI in the second list
	 */
	public static boolean equalsGroups(List<Group> groups1, List<Group> groups2) {
		if (groups1 == groups2)
			return true;
		if (groups1 == null)
			return false;
		if (groups1.size() != groups2.size()) {
			return false;
		}

		// fixme igor: sort the groups first!

		for (int i = 0; i < groups1.size(); i++) {
			// the order does not matter - we have to find a match in the second
			// list for
			// each group in the first list
			Group g1 = groups1.get(i);
			int j = 0;
			Group g2 = groups2.get(j);
			boolean foundEqual = equalsGroup(g1, g2);
			while (j < groups2.size() && !foundEqual) {
				// until we did not find a match for g1 in the second list
				// and there is still a candidate for the match (we did not
				// check
				// all the items in the second list) try the next group from
				// second lists
				j++;
				g2 = groups2.get(j);
				foundEqual = equalsGroup(g1, g2);
			}
			if (!foundEqual) {
				// we iterated through all the items in the second list but
				// we did not find a matching group for group g1
				return false;
			}
		}
		return true;
	}

	public static boolean fslPathEquals(FSLPath path, Object other) {
		if (path == other)
			return true;
		if (path == null)
			return false;
		if (!(other instanceof FSLPath))
			return false;

		// TODO - is this sufficient? rather contribute equal methods to
		// JFresnel...

		return path.toString().equals(other.toString());
	}

	public static boolean sparqlQueryEquals(SPARQLQuery query, Object other) {
		if (query == other)
			return true;
		if (query == null)
			return false;
		if (!(other instanceof SPARQLQuery))
			return false;

		// TODO Auto-generated method stub
		return query.toString().equals(other.toString());
	}

	public static boolean isStringEmpty(String str) {

		if (str == null || "".equals(str)) {
			return true;
		}

		return false;
	}

	/**
	 * Returns true if and only if both of the given URIs do represent the same
	 * resource. The format of the URIs is irrelevant (they can use both prefix
	 * and whole name for namespace).
	 * 
	 * @param uri1
	 * @param uri2
	 * @return true if the given URIs do represent the same resource
	 * 
	 */
	public static boolean equalsUris(URI uri1, URI uri2) {
		return equalsUris(uri1.stringValue(), uri2.stringValue());
	}

	/**
	 * Returns true if and only if both of the given URIs do represent the same
	 * resource. The format of the URIs is irrelevant (they can use both prefix
	 * and whole name for namespace).
	 * 
	 * @param uri1
	 * @param uri2
	 * @return true if the given URIs do represent the same resource
	 * 
	 */
	public static boolean equalsUris(String uri1, String uri2) {
		if (uri1 == uri2)
			return true;
		if (uri1 == null)
			return false;
		if (uri1.equals(uri2))
			return true;

		List<Namespace> namespaces = ContextHolder.getInstance()
				.getFresnelRepositoryDao().getNamespaces();
		namespaces.addAll(ContextHolder.getInstance().getDataRepositoryDao()
				.getNamespaces());

		String shortUri1 = FresnelUtils.replaceNamespace(uri1, namespaces);
		String shortUri2 = FresnelUtils.replaceNamespace(uri2, namespaces);

		return shortUri1.equals(shortUri2);
	}

	/**
	 * Returns true if the given string can be used as namespace prefix. Returns
	 * false otherwise.
	 * 
	 * @param prefix
	 *            a string representing namespace prefix to validate
	 * @return true if the given string can be used as namespace prefix
	 */
	public static boolean validateNamespacePrefixFormat(String prefix) {
		return Pattern.matches(NAMESPACE_PREFIX_REGEX, prefix);
	}

	/**
	 * Returns true if the given string can be used as name of a namespace.
	 * 
	 * @param name
	 *            a string representing name of a namespace
	 * @return true if the given string can be used as name of a namespace
	 */
	public static boolean validateNamespaceNameFormat(String name) {
		String fullNamespaceRegex = NAMESPACE_PREFIX_REGEX + ":.*";
		return Pattern.matches(fullNamespaceRegex, name);
	}

	/**
	 * Returns list of URIs which are from one of the namespaces contained in
	 * filter.
	 * 
	 * @param uris
	 * @param filter
	 * @return list of URIs which are from one of the namespaces contained in
	 *         filter
	 * 
	 * @see ModelUtils#filterURIs(List, List)
	 */
	public List<Value> filterURIs(List<Value> uris, List<Namespace> filter) {
		return ModelUtils.filterURIs(uris, filter);
	}

	/**
	 * Returns true if given value object is defined in one of the given
	 * namespaces.
	 * 
	 * @param value
	 *            object to be checked against the list of namespaces
	 * @param filter
	 *            list of allowed namespaces. Cannot be <code>null</code>
	 * @return true if given value object is defined in one of the given filter
	 *         namespace
	 * 
	 * @see ModelUtils#isPrefixed(Value, List)
	 */
	public static boolean isPrefixed(Value value, List<Namespace> filter) {
		return ModelUtils.isPrefixed(value, filter);
	}

}
