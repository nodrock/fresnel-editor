/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.projecttree;

/**
 * Interface for project tree node which implements reaction on mouse click.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public interface IClickableNode {

	/**
	 * This method is invoked after clicking on this node.
	 * 
	 * @param clickCount
	 *            determines whether it is single mouse click or double click
	 *            (or even more..)
	 */
	public void clickAction(int clickCount);

}
