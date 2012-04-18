package cz.muni.fi.fresneleditor.gui.mod.lens;

import org.openrdf.model.URI;

import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ATabNode;
import cz.muni.fi.fresneleditor.common.utils.FresnelUtils;
import fr.inria.jfresnel.Lens;

/**
 * Implementation of {@link ATabNode} for {@link Lens} object.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public class LensItemNode extends ATabNode<URI> {

	public LensItemNode(URI lensUri) {
		super(lensUri);
	}

	@Override
	public String toString() {
		URI userObject2 = getUserObject();
		return FresnelUtils.getLocalName(userObject2 != null ? userObject2
				.stringValue() : "fresneled:newLens");
	}

	@Override
	protected ITabComponent<URI> createComponent() {
		return new LensJPanel(getUserObject(), this);
	}

	@Override
	public void clickAction(int clickCount) {
		if (clickCount == 2 && !isOpened()) {
			// fixme igor: enable this functionality for all tab nodes
			((LensJPanel) getComponent()).doReload();
		}
		super.clickAction(clickCount);
	}

}
