package cz.muni.fi.fresneleditor.common.events;

import java.util.EventObject;

public class RepositoriesChangedEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new event for notifying about the change of available
	 * repositories.
	 * 
	 * @param source
	 *            the source that triggered the event
	 */
	public RepositoriesChangedEvent(Object source) {
		super(source);
	}

}
