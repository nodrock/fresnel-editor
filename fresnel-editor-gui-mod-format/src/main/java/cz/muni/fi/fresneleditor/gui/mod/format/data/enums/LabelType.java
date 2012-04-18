/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.format.data.enums;

/**
 * Enum represents possible label types for Fresnel Format.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 22.3.2009
 */
public enum LabelType {

	DEFAULT, NONE, LITERAL;

	@Override
	public String toString() {
		switch (this) {
		case DEFAULT:
			return "fresnel:default";
		case NONE:
			return "fresnel:none";
		case LITERAL:
			// FIXME: Check this
			return "";
		default:
			return "";
		}
	}
}
