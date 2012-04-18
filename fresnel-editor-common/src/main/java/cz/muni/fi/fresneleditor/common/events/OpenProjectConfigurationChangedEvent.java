package cz.muni.fi.fresneleditor.common.events;

import java.util.EventObject;

public class OpenProjectConfigurationChangedEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Event raised if the configuration of the open project changes.
	 * 
	 * @param source
	 *            the <code>Component</code> object (container) that originated
	 *            the event
	 */
	public OpenProjectConfigurationChangedEvent(Object source) {
		super(source);
	}

}
