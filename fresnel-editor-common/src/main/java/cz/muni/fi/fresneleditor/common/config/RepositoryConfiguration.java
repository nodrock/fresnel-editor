package cz.muni.fi.fresneleditor.common.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import cz.muni.fi.fresneleditor.model.IRepositoryConfiguration;
import cz.muni.fi.fresneleditor.model.NamespaceImplCustom;
import cz.muni.fi.fresneleditor.model.BaseRepositoryDao.RepositoryDomain;
import cz.muni.fi.fresneleditor.model.BaseRepositoryDao.RepositoryType;

@XmlRootElement
public class RepositoryConfiguration implements IRepositoryConfiguration,
		Serializable {

	private static final long serialVersionUID = 1L;

	// fixme igor: JAXB prohibited the usage of IRepositoryConfiguration
	// interface on
	// most places, is this acceptable?

	/**
	 * The meaning of the returned value depends on the the value of
	 * {@link #type} property value: <li>for {@link RepositoryType#HTTP} - the
	 * returned value represents HTTP URL of the repository <li>for
	 * {@link RepositoryType#LOCAL} - the returned value the directory in which
	 * the data for this repository will be persisted
	 */
	private String location;
	private String name;
	private RepositoryDomain domain;
	private RepositoryType type;

	// /**
	// * True if repository should not be persisted and user is not allowed to
	// use this repository
	// * for imports/exports. This is used for example in sample projects
	// repositories.
	// */
	// private boolean virtual;

	private List<NamespaceImplCustom> namespaces = new ArrayList<NamespaceImplCustom>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RepositoryDomain getDomain() {
		return domain;
	}

	public void setDomain(RepositoryDomain domain) {
		this.domain = domain;
	}

	public RepositoryType getType() {
		return type;
	}

	public void setType(RepositoryType type) {
		this.type = type;
	}

	/**
	 * @see #location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @see #location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	public void setNamespaces(List<NamespaceImplCustom> namespaces) {
		this.namespaces = namespaces;
	}

	public List<NamespaceImplCustom> getNamespaces() {
		return namespaces;
	}

	public void addNamespace(NamespaceImplCustom namespace) {
		namespaces.add(namespace);
	}

	public void removeNamespace(NamespaceImplCustom namespace) {
		namespaces.remove(namespace);
	}

	// /**
	// * @return the virtual
	// */
	// public boolean isVirtual() {
	// return virtual;
	// }
	//
	// /**
	// * @param virtual the virtual to set
	// */
	// public void setVirtual(boolean virtual) {
	// this.virtual = virtual;
	// }

	/**
	 * HashCode implementation on the basis of name attribute (name must be
	 * unique);
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Equals implementation on the basis of name attribute (name must be
	 * unique);
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RepositoryConfiguration other = (RepositoryConfiguration) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "repository (name: " + getName() + ", domain: " + getDomain()
				+ ", type: " + getType() + ", location: " + getLocation() + ")";
	}
}
