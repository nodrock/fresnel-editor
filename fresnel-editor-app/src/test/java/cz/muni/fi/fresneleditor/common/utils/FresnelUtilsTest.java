package cz.muni.fi.fresneleditor.common.utils;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.openrdf.model.Namespace;
import org.openrdf.model.impl.NamespaceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.fresneleditor.model.BaseRepositoryDao;

public class FresnelUtilsTest extends TestCase {

	private static final Logger LOG = LoggerFactory
			.getLogger(FresnelUtilsTest.class);

	private ArrayList<String> validUris;
	private ArrayList<String> nonValidUris;
	private BaseRepositoryDao dao;
	private ArrayList<Namespace> knownNamespaces;

	public FresnelUtilsTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		initDao();
		initValidUris();
		initNonValidUris();
	}

	private void initDao() {
		dao = new BaseRepositoryDao("testDao");

		knownNamespaces = new ArrayList<Namespace>();
		knownNamespaces.add(new NamespaceImpl("prefix1", "http://prefix1"));
		knownNamespaces.add(new NamespaceImpl("p2", "http://prefix2"));
		knownNamespaces.add(new NamespaceImpl("p+", "http://prefix3"));
		knownNamespaces.add(new NamespaceImpl("p-", "http://prefix4"));
		knownNamespaces.add(new NamespaceImpl("p.", "http://prefix5"));
		knownNamespaces.add(new NamespaceImpl("p.prefix", "http://prefix6"));
		knownNamespaces.add(new NamespaceImpl("p+.-pref++", "http://prefix7"));
		dao.setNamespaces(knownNamespaces);
	}

	private void initNonValidUris() {
		nonValidUris = new ArrayList<String>();
		nonValidUris.add("namespaceNotKnown:resource");
		nonValidUris.add("namespaceOnly#");
		nonValidUris.add("namespaceOnly#thing");
		nonValidUris.add("1namespaceOnly://thing");
		nonValidUris.add("+namespaceOnly://thing");
	}

	private void initValidUris() {
		validUris = new ArrayList<String>();
		validUris.add("absoluteNamespace://namespace#localName");
		validUris.add("http://xmlns.com/foaf/0.1/name");

		for (Namespace n : knownNamespaces) {
			validUris.add(n.getPrefix() + ":resource");
			validUris.add(n.getName() + "#resource");
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testValidateUri() {
		for (Namespace namespace : dao.getNamespaces()) {
			LOG.debug("Known namespace: " + namespace);
		}
		for (String validUri : validUris) {
			LOG.debug("Valid uri=" + validUri);
			assertTrue(FresnelUtils.validateResourceUri(validUri, dao) == null);
		}
		LOG.debug("Valid URIs OK, starting to test invalid uris");
		for (String nonValidUri : nonValidUris) {
			LOG.debug("INValid uri=" + nonValidUri);
			assertTrue(FresnelUtils.validateResourceUri(nonValidUri, dao) != null);
		}
		LOG.debug("Invalid URIs OK");
	}

}
