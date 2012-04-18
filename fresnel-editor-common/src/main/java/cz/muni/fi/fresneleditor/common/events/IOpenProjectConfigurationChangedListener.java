package cz.muni.fi.fresneleditor.common.events;

import cz.muni.fi.fresneleditor.common.FresnelApplication;

/**
 * This interface should be implemented by classes that would like to be
 * notified when open project configuration changes. This might for example
 * include closing of opened project, change of open projects name, change of
 * open projects fresnel repository or closing and opening a different project.
 * The class has to register it self for listening to the events using
 * {@link FresnelApplication#addFresnelAppEventListener(Class, IFresnelAppEventListener)}
 * method.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public interface IOpenProjectConfigurationChangedListener extends
		IFresnelAppEventListener {

	/**
	 * Invoked after a project is closed and another project is opened.
	 * 
	 * @param evt
	 */
	public void openProjectConfigurationChanged(
			OpenProjectConfigurationChangedEvent evt);

}
