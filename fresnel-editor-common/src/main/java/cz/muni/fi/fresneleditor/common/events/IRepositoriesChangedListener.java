package cz.muni.fi.fresneleditor.common.events;

import cz.muni.fi.fresneleditor.common.FresnelApplication;

/**
 * This interface should be implemented by classes that would like to be
 * notified when list of available repositories changes (a new repository was
 * added, an existing repository was removed). The class has to register it self
 * for listening to the events using
 * {@link FresnelApplication#addFresnelAppEventListener(Class, IFresnelAppEventListener)}
 * method.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public interface IRepositoriesChangedListener extends IFresnelAppEventListener {

	/**
	 * Invoked after list of repositories changes.
	 * 
	 * @param evt
	 */
	public void repositoriesChanged(RepositoriesChangedEvent evt);

}
