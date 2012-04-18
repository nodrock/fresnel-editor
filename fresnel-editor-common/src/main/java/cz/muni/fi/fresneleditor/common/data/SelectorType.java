/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.common.data;

import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

import fr.inria.jfresnel.Constants;

/**
 * Contains possible selector types.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz), Igor Zemsky
 *         (zemsky@mail.muni.cz)
 * @version 19.3.2009
 */
public enum SelectorType {

	SIMPLE(null), FSL(new URIImpl(Constants._fslSelector)), SPARQL(new URIImpl(
			Constants._sparqlSelector));

	private URI datatypeURI;

	private SelectorType(URI datatypeURI) {
		this.datatypeURI = datatypeURI;
	}

	/**
	 * Returns data type URI for this selector type. For {@link #SIMPLE}
	 * selector type it returns null.
	 * 
	 * @return data type URI for this selector or null if this selector is
	 *         simple selector type
	 */
	public URI getDatatypeURI() {
		return datatypeURI;
	}

}
