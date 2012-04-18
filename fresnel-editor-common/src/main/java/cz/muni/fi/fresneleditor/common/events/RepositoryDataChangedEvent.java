package cz.muni.fi.fresneleditor.common.events;

import java.util.EventObject;

import org.springframework.util.Assert;

public class RepositoryDataChangedEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String repositoryName;

	/**
	 * Event fired when the data in given repository changes.
	 * 
	 * @param source
	 * @param repositoryName
	 *            the name of the repository whose data changed. Cannot be null.
	 */
	public RepositoryDataChangedEvent(Object source, String repositoryName) {
		super(source);
		Assert.notNull(repositoryName);
		this.repositoryName = repositoryName;
	}

	/**
	 * Returns name of the change repository.
	 * 
	 * @return the name of the repository whose data changed. Cannot return null
	 */
	public String getRepositoryName() {
		return repositoryName;
	}

}
