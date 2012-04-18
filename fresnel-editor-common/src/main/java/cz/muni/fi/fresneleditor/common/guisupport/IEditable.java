/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport;

/**
 * Interface for objects which do support following operations on them: <li>save
 * <li>delete
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public interface IEditable {

	/**
	 * Performs persisting of the object.
	 */
	public void doSave();

	/**
	 * Performs permanent delete of the object.
	 */
	public void doDelete();

}
