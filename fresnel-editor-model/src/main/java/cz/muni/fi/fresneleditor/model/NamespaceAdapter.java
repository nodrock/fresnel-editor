package cz.muni.fi.fresneleditor.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.openrdf.model.Namespace;

/**
 * Class for serializing {@link Namespace} objects using JAXB.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public class NamespaceAdapter extends XmlAdapter<String, NamespaceImplCustom> {

	private static final String SEPARATOR = "_SEPARATOR_";

	@Override
	public String marshal(NamespaceImplCustom namespaceImpl) throws Exception {
		// prefix_SEPARATOR_name
		return namespaceImpl.getPrefix() + SEPARATOR + namespaceImpl.getName();
	}

	@Override
	public NamespaceImplCustom unmarshal(String jaxbString) throws Exception {
		int indexOf = jaxbString.indexOf(SEPARATOR);
		String prefix = jaxbString.substring(0, indexOf);
		String name = jaxbString.substring(indexOf + SEPARATOR.length());
		return new NamespaceImplCustom(prefix, name);
	}

}
