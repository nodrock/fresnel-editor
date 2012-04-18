package cz.muni.fi.fresneleditor.gui.mod.lens2;

import fr.inria.jfresnel.Lens;
import fr.inria.jfresnel.PropertyVisibility;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class UnsupportedPropertyVisibilityTypeException extends
		InvalidLensDefinitionException {

	public UnsupportedPropertyVisibilityTypeException(Lens lens,
			PropertyVisibility visibility) {
		super(lens, "PropertyVisibility of type '" + visibility.getClass()
				+ "' is not supported!");
	}

	public UnsupportedPropertyVisibilityTypeException(String string) {
		super(string);
	}

}
