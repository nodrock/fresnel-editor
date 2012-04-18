/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.gui.mod.vis.treemodel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;

import org.openrdf.model.URI;

import cz.muni.fi.fresneleditor.common.FresnelApplication;
import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ARootTabNode;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ATabNode;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.IClickableNode;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ProjectTree;
import cz.muni.fi.fresneleditor.gui.mod.vis.SVGVisualisationJPanel;
import cz.muni.fi.fresneleditor.gui.mod.vis.VisualizationJPanel;
import cz.muni.fi.fresneleditor.gui.mod.vis.XHTMLVisualisationJPanel;

/**
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 10.5.2009
 */
public class VisualizationRootNode extends ARootTabNode<ITabComponent> implements
		IClickableNode {

	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("cz/muni/fi/fresneleditor/gui/mod/vis/resources/VisualizationJPanel");

	// String is just dummy value here
	private ITabComponent<String> component;

	public VisualizationRootNode(ProjectTree projectTree) {
		super(bundle.getString("RDF_data_visualization"), projectTree);
	}

	@Override
	protected ATabNode<ITabComponent> getNewChild(ITabComponent vis) {
		 return new VisualizationItemNode(vis);
	}

    @Override
    protected List<ITabComponent> getChildItems() {
        List<ITabComponent> items = new ArrayList<ITabComponent>();
        items.add(new XHTMLVisualisationJPanel());
        items.add(new SVGVisualisationJPanel());
        return items;
    }

	@Override
	public List<JMenuItem> getMenu() {

		List<JMenuItem> items = new ArrayList<JMenuItem>();

		if (FresnelApplication.getApp().getBaseFrame().constainsTab(this)) {
			JMenuItem closeTabItem = new JMenuItem(
					bundle.getString("Close_tab"));
			closeTabItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					closeTab();
				}
			});
			items.add(closeTabItem);
		}

		return items;
	}

	@Override
	public void clickAction(int clickCount) {
		if (clickCount == 1) {
			FresnelApplication.getApp().getBaseFrame().showTab(getComponent());
		} else if (clickCount == 2) {
			openTheNodeAsTab();
		}
	}

	/**
	 * Opens this node's representative in the application's tabbed pane.
	 */
	public void openTheNodeAsTab() {
		getComponent();
		FresnelApplication.getApp().getBaseFrame().openTab(this, component);
	}

	/**
	 * Closes this node representing tab.
	 */
	public void closeTab() {
		FresnelApplication.getApp().getBaseFrame().closeTab(this);
	}

	/**
	 * Returns cached instance of the item's representing component if it
	 * exists. Otherwise returns newly created object.
	 * 
	 * @return component that represents this node item
	 */
	protected final ITabComponent<String> getComponent() {
		if (component == null) {
			component = new VisualizationJPanel();
		}
		return component;
	}
}
