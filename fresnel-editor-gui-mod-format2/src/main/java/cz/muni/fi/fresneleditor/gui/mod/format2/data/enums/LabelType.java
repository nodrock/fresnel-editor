/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.format2.data.enums;

/**
 * Enum represents possible label types for Fresnel Format.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 22.3.2009
 */
public enum LabelType {

	NOT_SPECIFIED, NONE, SHOW;

	@Override
	public String toString() {
		switch (this) {
		case NOT_SPECIFIED:
			return "NOT SPECIFIED";
		case NONE:
			return "fresnel:none";
		case SHOW:
			return "fresnel:show";
		default:
			return "";
		}
	}
}
