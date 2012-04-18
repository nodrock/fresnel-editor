/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.common.utils;

import java.io.File;

/**
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 30.6.2009
 */
public class URIUtils {

	public static final String FILE_URL_PROTOCOL_PREFIX = "file:";

	/**
	 * 
	 */
	public static String addStylesheetURLPrefix(String uri) {

		if (!uri.startsWith(FILE_URL_PROTOCOL_PREFIX)) {
			String prefix = FILE_URL_PROTOCOL_PREFIX;
			if (File.separator.equals("\\")) {
				prefix = prefix + "\\\\\\";
			} else {
				prefix = prefix + File.separator + File.separator;
			}
			uri = prefix + uri;
		}

		return uri;
	}
}
