package cz.muni.fi.fresneleditor.gui.mod.vis.treemodel;


import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ATabNode;
import cz.muni.fi.fresneleditor.gui.mod.vis.VisualizationJPanel;

/**
 *
 * @author Jan Kolinek
 * @version 25.5.2010
 */
public class VisualizationItemNode extends ATabNode<ITabComponent> {

    private ITabComponent comp;

    public VisualizationItemNode(ITabComponent comp) {
        super(comp);
        this.comp = comp;

    }

    @Override
    public String toString() {
        return getComponent().getLabel();
    }

    @Override
    protected ITabComponent<ITabComponent> createComponent() {
        VisualizationJPanel vp = (VisualizationJPanel) comp;
        vp.setVisualizationItemNode(this);
        return comp;
    }
}
