package cz.muni.fi.fresneleditor.common.events;

import cz.muni.fi.fresneleditor.common.FresnelApplication;

/**
 * This interface should be implemented by classes that would like to be
 * notified when data of a particular repository changed (data added/removed).
 * The class has to register it self for listening to the events using
 * {@link FresnelApplication#addFresnelAppEventListener(Class, IFresnelAppEventListener)}
 * method.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public interface IRepositoryDataChangedListener extends
		IFresnelAppEventListener {

	/**
	 * Invoked after data in a repository specified by evt changed.
	 * 
	 * @param evt
	 *            the event data
	 */
	public void repositoryDataChanged(RepositoryDataChangedEvent evt);

}
