/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.format2.data.enums;

import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

import fr.inria.jfresnel.Constants;

/**
 * Enum represents possible value types of Fresnel Format
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 22.3.2009
 */
public enum ValueType {
	DEFAULT, // Format.VALUE_TYPE_NOT_SPECIFIED = 0;

	IMAGE, EXTERNAL_LINK, URI, NONE,

	VIDEO, AUDIO, ANIMATION, HTML, TEXT, TEXTSTREAM;

	public URI getURI() {
		URI object = null;

		switch (this) {
		case DEFAULT:
			// for DEFAULT return null
			break;
		case EXTERNAL_LINK:
			object = new URIImpl(Constants._externalLink);
			break;
		case IMAGE:
			object = new URIImpl(Constants._image);
			break;
		case NONE:
			object = new URIImpl(Constants._none);
			break;
		case URI:
			object = new URIImpl(Constants._uri);
			break;
		case VIDEO:
			object = new URIImpl(Constants._video);
			break;
		case AUDIO:
			object = new URIImpl(Constants._audio);
			break;
		case ANIMATION:
			object = new URIImpl(Constants._animation);
			break;
		case HTML:
			object = new URIImpl(Constants._html);
			break;
		case TEXT:
			object = new URIImpl(Constants._text);
			break;
		case TEXTSTREAM:
			object = new URIImpl(Constants._textstream);
			break;
		}

		return object;
	}

	@Override
	public String toString() {
		String result = "";
		switch (this) {
		case DEFAULT:
			result = "default";
			break;
		case EXTERNAL_LINK:
			result = "externalLink";
			break;
		case IMAGE:
			result = "image";
			break;
		case NONE:
			result = "none";
			break;
		case URI:
			result = "uri";
			break;
		case VIDEO:
			result = "video";
			break;
		case AUDIO:
			result = "audio";
			break;
		case ANIMATION:
			result = "animation";
			break;
		case HTML:
			result = "html";
			break;
		case TEXT:
			result = "text";
			break;
		case TEXTSTREAM:
			result = "textstream";
			break;
		}

		return result;
	}
}
