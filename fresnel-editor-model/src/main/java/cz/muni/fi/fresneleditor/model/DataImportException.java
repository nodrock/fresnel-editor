package cz.muni.fi.fresneleditor.model;

public class DataImportException extends Exception {
	// fixme igor: runtime or checked exception?

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataImportException(String message, Exception cause) {
		super(message, cause);
	}

}
