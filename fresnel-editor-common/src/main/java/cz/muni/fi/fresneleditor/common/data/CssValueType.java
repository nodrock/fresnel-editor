/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.data;

/**
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 23.3.2009
 */
public enum CssValueType {

	CLASS, SNIPPET;

	@Override
	public String toString() {
		if (this == CLASS) {
			return "CSS class";
		} else if (this == SNIPPET) {
			return "CSS snippet";
		} else {
			throw new IndexOutOfBoundsException("CssValueType");
		}
	}
}
