package cz.muni.fi.fresneleditor.common.utils;

public class WriteProtectedException extends DeleteConfigurationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WriteProtectedException(String message) {
		super(message);
	}

}
