package cz.muni.fi.fresneleditor.common;

import java.util.EventObject;

import javax.swing.event.EventListenerList;

import cz.muni.fi.fresneleditor.common.events.IFresnelAppEventListener;
import cz.muni.fi.fresneleditor.common.events.IOpenProjectConfigurationChangedListener;
import cz.muni.fi.fresneleditor.common.events.IRepositoriesChangedListener;
import cz.muni.fi.fresneleditor.common.events.IRepositoryDataChangedListener;
import cz.muni.fi.fresneleditor.common.events.OpenProjectConfigurationChangedEvent;
import cz.muni.fi.fresneleditor.common.events.RepositoriesChangedEvent;
import cz.muni.fi.fresneleditor.common.events.RepositoryDataChangedEvent;

public class AppEventsManager {

	private final EventListenerList fresnelAppEventListeners = new EventListenerList();

	private static AppEventsManager instance;

	private AppEventsManager() {
		// singleton constructor
	}

	public static AppEventsManager getInstance() {
		if (instance == null) {
			instance = new AppEventsManager();
		}
		return instance;
	}

	/**
	 * Allows classes to register for different Fresnel Editor events.
	 * 
	 * @param listenerClass
	 *            listener class
	 * @param listener
	 *            listener instance
	 */
	public void addFresnelAppEventListener(Class listenerClass,
			IFresnelAppEventListener listener) {
		getFresnelAppEventListeners().add(listenerClass, listener);
	}

	/**
	 * Allows classes to unregister from listening to different Fresnel Editor
	 * events.
	 * 
	 * @param clazz
	 *            listener class
	 * @param listener
	 *            listener instance
	 */
	public void removeFresnelAppEventListener(Class clazz,
			IFresnelAppEventListener listener) {
		getFresnelAppEventListeners().remove(clazz, listener);
	}

	/**
	 * Fires new RepositoriesChangedEvent for notifying about the change of
	 * available repositories.
	 * 
	 * @param source
	 *            the source that triggered the event
	 */
	public void fireRepositoriesChanagedEvent(Object source) {
		fireEvent(IRepositoriesChangedListener.class,
				new RepositoriesChangedEvent(source));
	}

	/**
	 * Fires {@link OpenProjectConfigurationChangedEvent}. All listeners are
	 * notified.
	 * 
	 * @param source
	 *            the source which fired the event
	 */
	public void fireOpenProjectChanged(Object source) {
		fireEvent(IOpenProjectConfigurationChangedListener.class,
				new OpenProjectConfigurationChangedEvent(source));
	}

	/**
	 * Fires {@link RepositoryDataChangedEvent}. All listeners are notified.
	 * 
	 * @param source
	 *            the source which fired the event
	 */
	public void fireRepositoryDataChanged(Object source, String repositoryName) {
		fireEvent(IRepositoryDataChangedListener.class,
				new RepositoryDataChangedEvent(source, repositoryName));
	}

	private EventListenerList getFresnelAppEventListeners() {
		return getInstance().fresnelAppEventListeners;
	}

	private void fireEvent(Class<?> listenerClass, EventObject evt) {
		Object[] listeners = getFresnelAppEventListeners().getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == listenerClass) {
				IFresnelAppEventListener listener = (IFresnelAppEventListener) listeners[i + 1];
				if (listener instanceof IOpenProjectConfigurationChangedListener
						&& evt instanceof OpenProjectConfigurationChangedEvent) {
					((IOpenProjectConfigurationChangedListener) listener)
							.openProjectConfigurationChanged((OpenProjectConfigurationChangedEvent) evt);
				} else if (listener instanceof IRepositoriesChangedListener
						&& evt instanceof RepositoriesChangedEvent) {
					((IRepositoriesChangedListener) listener)
							.repositoriesChanged((RepositoriesChangedEvent) evt);
				} else if (listener instanceof IRepositoryDataChangedListener
						&& evt instanceof RepositoryDataChangedEvent) {
					((IRepositoryDataChangedListener) listener)
							.repositoryDataChanged((RepositoryDataChangedEvent) evt);
				} else {
					throw new IllegalArgumentException(
							"The combination of event type: "
									+ listener.getClass()
									+ " and listener type: "
									+ listener.getClass() + " is not supported");
				}
			}
		}
	}

}
