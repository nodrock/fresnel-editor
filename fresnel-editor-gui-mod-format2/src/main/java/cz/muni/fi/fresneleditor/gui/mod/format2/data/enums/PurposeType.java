package cz.muni.fi.fresneleditor.gui.mod.format2.data.enums;

import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

import fr.inria.jfresnel.Constants;

public enum PurposeType {
	DEFAULT, SCREEN, PROJECTION, PRINT;

	public URI getURI() {
		URI object = null;

		switch (this) {
		case DEFAULT:
			// for DEFAULT return null
			break;
		case SCREEN:
			object = new URIImpl(Constants._screen);
			break;
		case PROJECTION:
			object = new URIImpl(Constants._projection);
			break;
		case PRINT:
			object = new URIImpl(Constants._print);
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
		case SCREEN:
			result = "screen";
			break;
		case PROJECTION:
			result = "projection";
			break;
		case PRINT:
			result = "print";
			break;
		}

		return result;
	}
}
