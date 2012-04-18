package cz.muni.fi.fresneleditor.gui.mod.lens2;

import fr.inria.jfresnel.Lens;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class InvalidLensDefinitionException extends RuntimeException {

	public InvalidLensDefinitionException(String message) {
		super(message);
	}

	public InvalidLensDefinitionException(Lens lens, String message) {
		this("Error when loading lens: " + lens == null ? "" : lens.toString()
				+ "\nERROR description:\n" + message);
	}

}
