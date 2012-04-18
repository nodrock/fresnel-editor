package cz.muni.fi.fresneleditor.common;

/**
 * Root interface for fresnel editor module.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public interface IFresnelEditorModule {

	/**
	 * Returns the name of this module.
	 * 
	 * @return the name of this module
	 */
	public String getName();

	/**
	 * Returns the version of this module.
	 * 
	 * @return the version of this module
	 */
	public String getVersion();

}
