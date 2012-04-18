package cz.muni.fi.fresneleditor.model;

import java.io.Serializable;
import java.util.List;

import cz.muni.fi.fresneleditor.model.BaseRepositoryDao.RepositoryDomain;
import cz.muni.fi.fresneleditor.model.BaseRepositoryDao.RepositoryType;

/**
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public interface IRepositoryConfiguration extends Serializable {

	public abstract String getName();

	public abstract void setName(String name);

	public abstract RepositoryDomain getDomain();

	public abstract void setDomain(RepositoryDomain domain);

	public abstract RepositoryType getType();

	public abstract void setType(RepositoryType type);

	public abstract String getLocation();

	public abstract void setLocation(String location);

	public abstract void setNamespaces(List<NamespaceImplCustom> namespaces);

	public abstract List<NamespaceImplCustom> getNamespaces();

	public abstract void addNamespace(NamespaceImplCustom namespace);

	public abstract void removeNamespace(NamespaceImplCustom namespace);

}