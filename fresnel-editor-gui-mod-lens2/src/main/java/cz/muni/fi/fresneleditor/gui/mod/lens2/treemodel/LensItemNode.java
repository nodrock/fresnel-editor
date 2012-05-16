package cz.muni.fi.fresneleditor.gui.mod.lens2.treemodel;

import org.openrdf.model.URI;

import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ATabNode;
import cz.muni.fi.fresneleditor.common.utils.FresnelUtils;
import cz.muni.fi.fresneleditor.gui.mod.lens2.LensJPanel2;
import fr.inria.jfresnel.Lens;

/**
 * Implementation of {@link ATabNode} for {@link Lens} object.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public class LensItemNode extends ATabNode<Lens> {

	public LensItemNode(Lens lens) {
		super(lens);
	}

	@Override
	public String toString() {
		return FresnelUtils.getLocalName(getUserObject() != null ? getUserObject().getURI() : "fresneled:newLens");
        }

	@Override
	protected ITabComponent<Lens> createComponent() {
		return new LensJPanel2(getUserObject(), this);
	}

	@Override
	public void clickAction(int clickCount) {
		if (clickCount == 2 && !isOpened()) {
			// fixme igor: enable this functionality for all tab nodes
			((LensJPanel2) getComponent()).doReload();
		}
		super.clickAction(clickCount);
	}

}
