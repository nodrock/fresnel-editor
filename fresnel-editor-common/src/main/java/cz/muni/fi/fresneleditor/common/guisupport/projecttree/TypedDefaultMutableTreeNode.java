/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.projecttree;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A wrapper for {@link DefaultMutableTreeNode} which allows typed setting and
 * getting of {@link DefaultMutableTreeNode}' user object.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 * @param <T>
 *            type of the user object
 */
public class TypedDefaultMutableTreeNode<T> extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TypedDefaultMutableTreeNode(T userObject) {
		super(userObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getUserObject() {
		return (T) super.getUserObject();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setUserObject(Object userObject) {
		@SuppressWarnings("unused")
		T obj = (T) userObject; // hack how to assure that only correct type is
								// set
		super.setUserObject(userObject);
	}

}