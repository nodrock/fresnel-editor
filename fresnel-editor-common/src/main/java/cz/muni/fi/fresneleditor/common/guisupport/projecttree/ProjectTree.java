/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.projecttree;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import cz.muni.fi.fresneleditor.common.AppEventsManager;
import cz.muni.fi.fresneleditor.common.events.IOpenProjectConfigurationChangedListener;
import cz.muni.fi.fresneleditor.common.events.OpenProjectConfigurationChangedEvent;
import cz.muni.fi.fresneleditor.common.guisupport.IContextMenu;

/**
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class ProjectTree extends JTree implements
		IOpenProjectConfigurationChangedListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProjectTree() {
		super();
		addMouseListener(getProjectTreeListener());
		AppEventsManager.getInstance().addFresnelAppEventListener(
				IOpenProjectConfigurationChangedListener.class, this);
	}

	private MouseListener getProjectTreeListener() {
		return new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultMutableTreeNode clickedNode = getClickedNode(e);
				if (clickedNode instanceof IClickableNode) {
					((IClickableNode) clickedNode).clickAction(e
							.getClickCount());
				}
			}

		};
	}

	private DefaultMutableTreeNode getClickedNode(MouseEvent e) {
		TreePath selPath = getPathForLocation(e.getX(), e.getY());

		DefaultMutableTreeNode selectedNode = null;
		try {
			selectedNode = (DefaultMutableTreeNode) selPath
					.getLastPathComponent();
		} catch (Exception E) {
		}
		return selectedNode;
	}

	private void showPopup(MouseEvent e) {
		// fixme igor: cache this?
		JPopupMenu popup = new JPopupMenu();
		int menuItemsCount = 0; // Prevents opening empty pop-up menu

		DefaultMutableTreeNode clickedNode = getClickedNode(e);
		if (e.isPopupTrigger() && clickedNode instanceof IContextMenu) {
			for (JMenuItem item : ((IContextMenu) clickedNode).getMenu()) {
				popup.add(item);
				menuItemsCount++;
			}
			if (menuItemsCount > 0) {
				popup.show((Component) e.getSource(), e.getX(), e.getY());
			}
		}
	}

	/**
	 * Removes the old tree model and creates a new one based on the currently
	 * opened project.
	 * 
	 * @param evt
	 *            the event that triggered the update
	 */
	@Override
	public void openProjectConfigurationChanged(
			OpenProjectConfigurationChangedEvent evt) {
		// change the model
		setModel(new ProjectTreeModel(this));
	}

}
