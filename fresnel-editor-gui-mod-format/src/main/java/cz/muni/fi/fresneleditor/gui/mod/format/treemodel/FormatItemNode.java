/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.fresneleditor.gui.mod.format.treemodel;

import org.openrdf.model.URI;

import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ATabNode;
import cz.muni.fi.fresneleditor.gui.mod.format.FormatsJPanel;

/**
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 7.4.2009
 */
public class FormatItemNode extends ATabNode<URI> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FormatItemNode(URI formatUri) {
		super(formatUri);
	}

	@Override
	public String toString() {
		return getComponent().getLabel();
	}

	@Override
	protected ITabComponent<URI> createComponent() {
		return new FormatsJPanel(getUserObject(), this);
	}

}
