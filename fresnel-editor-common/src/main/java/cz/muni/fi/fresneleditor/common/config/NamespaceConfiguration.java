/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.common.config;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class representing configuration of one namespace.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 14. 3. 2009
 */
@XmlRootElement
public class NamespaceConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;

	private String prefix;

	private String namespaceURI;

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the namespaceUri
	 */
	public String getNamespaceURI() {
		return namespaceURI;
	}

	/**
	 * @param namespaceUri
	 *            the namespaceUri to set
	 */
	public void setNamespaceURI(String namespaceUri) {
		this.namespaceURI = namespaceUri;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((namespaceURI == null) ? 0 : namespaceURI.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final NamespaceConfiguration other = (NamespaceConfiguration) obj;
		if (namespaceURI == null) {
			if (other.namespaceURI != null)
				return false;
		} else if (!namespaceURI.equals(other.namespaceURI))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "namespace (prefix: " + getPrefix() + ", namespaceURI: "
				+ getNamespaceURI() + ")";
	}
}
