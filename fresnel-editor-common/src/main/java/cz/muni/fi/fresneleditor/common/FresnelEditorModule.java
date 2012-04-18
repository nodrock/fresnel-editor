package cz.muni.fi.fresneleditor.common;

/**
 * Default implementation of {@link IFresnelEditorModule} interface.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public class FresnelEditorModule implements IFresnelEditorModule {

	private final String name;
	private final String version;

	/**
	 * Constructor which sets the name and the version of this module.
	 * 
	 * @param name
	 *            name of the module
	 * @param version
	 *            version of the module
	 */
	public FresnelEditorModule(String name, String version) {
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

}
