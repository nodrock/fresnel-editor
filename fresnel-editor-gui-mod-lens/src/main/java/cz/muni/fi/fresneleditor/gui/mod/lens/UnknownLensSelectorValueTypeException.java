package cz.muni.fi.fresneleditor.gui.mod.lens;

import fr.inria.jfresnel.Lens;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class UnknownLensSelectorValueTypeException extends
		InvalidLensDefinitionException {

	public UnknownLensSelectorValueTypeException(Lens lens, Object selectorValue) {
		super(lens, "The selector value '" + selectorValue
				+ "' which is of type " + selectorValue.getClass()
				+ " is not supported");
	}

	public UnknownLensSelectorValueTypeException(String message) {
		super(message);
	}
}
