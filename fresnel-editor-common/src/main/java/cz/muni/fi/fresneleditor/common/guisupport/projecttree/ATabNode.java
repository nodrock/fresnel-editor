/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.common.guisupport.projecttree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;

import cz.muni.fi.fresneleditor.common.BaseJFrame;
import cz.muni.fi.fresneleditor.common.FresnelApplication;
import cz.muni.fi.fresneleditor.common.FresnelEditorConstants;
import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.guisupport.IContextMenu;
import cz.muni.fi.fresneleditor.common.guisupport.IEditable;
import cz.muni.fi.fresneleditor.common.guisupport.MessageDialog;
import cz.muni.fi.fresneleditor.common.utils.GuiUtils;

/**
 * This class provides a skeletal implementation for the {@link ProjectTree}
 * node that will be represented in the {@link BaseJFrame}'s tabbed pane.
 * 
 * @param <I>
 *            type of the item that constitutes the child nodes data
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public abstract class ATabNode<I extends Object> extends
		TypedDefaultMutableTreeNode<I> implements IContextMenu, IClickableNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle(FresnelEditorConstants.PATH_RESOURCE_BUNDLE_COMMON);

	/**
	 * Creates new instance of a components that represents this node item (user
	 * object).
	 * 
	 * @return new instance of component representing the item
	 */
	protected abstract ITabComponent<I> createComponent();

	/**
	 * Instance of the component representing this node in the tabbed pane in
	 * {@link BaseJFrame}.
	 */
	// This component is cached. Pleas note that at the moment this component is
	// used as identifier
	// of the tab in the tabbed pane!
	private ITabComponent<I> component;

	/**
	 * Constructor that sets this node's constituting data
	 * 
	 * @param item
	 *            the data of this node
	 */
	public ATabNode(I item) {
		super(item);
	}

	/**
	 * Returns true if this node representative is present and opened in the
	 * {@link BaseJFrame} tabbed pane.
	 * 
	 * @return true if the representative of this node is present in the tabbed
	 *         pane
	 */
	public boolean isOpened() {
		return FresnelApplication.getApp().getBaseFrame().constainsTab(this);
	}

	/**
	 * Closes this node representing tab.
	 */
	public void closeTab() {
		FresnelApplication.getApp().getBaseFrame().closeTab(this);
	}

	/**
	 * Returns the menu items common for all module root child nodes which
	 * should be represented as tabs in the presentation part of
	 * {@link BaseJFrame}.
	 * 
	 * Currently provided menu items are: <li>close tab
	 * 
	 */
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

			if (getComponent() instanceof IEditable) {
				JMenuItem deleteItem = new JMenuItem(bundle.getString("Delete"));
				deleteItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// display confirmation dialog for deleting
						new MessageDialog(GuiUtils.getTopComponent(), bundle
								.getString("Confirmation"), bundle
								.getString("Do_you_really_want_to_delete_'")
								+ getComponent().getLabel() + "'?",
								new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										// trigger the delete action it self in
										// the OK listener of the dialog
										((IEditable) getComponent()).doDelete();
									}
								}).setVisible(true);
					}
				});
				items.add(deleteItem);

				JMenuItem saveItem = new JMenuItem(bundle.getString("Save"));
				saveItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// trigger the save action
						((IEditable) getComponent()).doSave();
					}
				});
				items.add(saveItem);
			}
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
		BaseJFrame frame = FresnelApplication.getApp().getBaseFrame();
		frame.openTab(this, getComponent());
		frame.hidePreviewPanel();
	}

	/**
	 * Returns cached instance of the item's representing component if it
	 * exists. Otherwise returns newly created object.
	 * 
	 * @return component that represents this node item
	 */
	protected final ITabComponent<I> getComponent() {
		if (component == null) {
			component = createComponent();
		}
		return component;
	}
}