package cz.muni.fi.fresneleditor.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openrdf.model.Namespace;
import org.openrdf.model.impl.NamespaceImpl;

/**
 * Serializable implementation of {@link Namespace}. This class also provides
 * the equals() and hashCode() methods based on prefix and name fields.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
@XmlRootElement
@XmlJavaTypeAdapter(NamespaceAdapter.class)
public class NamespaceImplCustom extends NamespaceImpl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NamespaceImplCustom(Namespace namespace) {
		super(namespace.getPrefix(), namespace.getName());
	}

	public NamespaceImplCustom(String prefix, String name) {
		super(prefix, name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result
				+ ((getPrefix() == null) ? 0 : getPrefix().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof NamespaceImplCustom))
			return false;
		NamespaceImplCustom other = (NamespaceImplCustom) obj;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		if (getPrefix() == null) {
			if (other.getPrefix() != null)
				return false;
		} else if (!getPrefix().equals(other.getPrefix()))
			return false;
		return true;
	}

}
