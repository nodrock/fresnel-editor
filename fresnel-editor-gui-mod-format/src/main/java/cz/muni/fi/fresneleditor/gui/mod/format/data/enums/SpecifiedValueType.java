/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.format.data.enums;

/**
 * Enum which represents specified value types of Fresnel Format.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 22.3.2009
 */
public enum SpecifiedValueType {

	IMAGE, EXTERNAL_LINK, URI, NONE
	/* REPLACED_RESOURCE */;

	@Override
	public String toString() {
		switch (this) {
		case IMAGE:
			return "fresnel:image";
		case EXTERNAL_LINK:
			return "fresnel:externalLink";
		case URI:
			return "fresnel:uri";
		case NONE:
			return "fresnel:none";
		default:
			return "";
		}
	}
}
