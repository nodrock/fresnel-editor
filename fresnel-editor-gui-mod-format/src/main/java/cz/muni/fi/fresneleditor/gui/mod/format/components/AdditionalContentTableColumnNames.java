/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.format.components;

import java.util.ResourceBundle;

/**
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 28.3.2009
 */
public class AdditionalContentTableColumnNames {

	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("cz/muni/fi/fresneleditor/gui/mod/format/resource-bundle-format");

	public static final String ADDITIONAL_CONTENT_TYPE = bundle
			.getString("Additional_cont_type");
	public static final String BEFORE = bundle.getString("Before");
	public static final String AFTER = bundle.getString("After");
	public static final String FIRST = bundle.getString("First");
	public static final String LAST = bundle.getString("Last");
	public static final String NO_VALUE = bundle.getString("No_value");
}
