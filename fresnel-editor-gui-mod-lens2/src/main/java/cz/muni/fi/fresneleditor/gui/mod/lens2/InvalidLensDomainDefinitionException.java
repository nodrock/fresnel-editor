package cz.muni.fi.fresneleditor.gui.mod.lens2;

import fr.inria.jfresnel.Lens;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class InvalidLensDomainDefinitionException extends
		InvalidLensDefinitionException {

	public InvalidLensDomainDefinitionException(Lens lens, String message) {
		super(lens, message);
	}

}
