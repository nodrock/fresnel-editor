/**
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.common.guisupport.projecttree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.fresneleditor.common.FresnelApplication;
import cz.muni.fi.fresneleditor.common.guisupport.IContextMenu;

/**
 * Skeleton implementation of fresnel editor module's project tree root node
 * which can have children which are represented by {@link ATabNode}.
 * 
 * @param <I>
 *            type of the item that constitutes the child nodes data
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public abstract class ARootTabNode<I extends Object> extends
		DefaultMutableTreeNode implements IContextMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final Logger LOG = LoggerFactory.getLogger(ARootTabNode.class);

	protected final ProjectTree projectTree;

	/**
	 * Returns new child node instance.
	 * 
	 * @return
	 */
	protected abstract ATabNode<I> getNewChild(I item);

	/**
	 * Returns list of items representing children of this root node.
	 * 
	 * @return list of items representing children of this root node
	 */
	protected abstract List<I> getChildItems();

	/**
	 * 
	 * @param label
	 *            node label
	 * @param projectTree
	 * 
	 */
	public ARootTabNode(String label, ProjectTree projectTree) {
		super(label);
		this.projectTree = projectTree;

		updateChildren();
	}

	/**
	 * Removes all children and loads/appends children for items obtained from
	 * {@link #getChildItems()}.
	 * 
	 */
	protected void updateChildren() {
		removeAllChildren();

		// create initial child nodes
		for (I item : getChildItems()) {
			add(getNewChild(item));
		}

		((DefaultTreeModel) projectTree.getModel()).reload(this);
	}

	/**
	 * Returns the menu items common for all fresnel editor module root nodes. <br>
	 * Currently supported menu items: <li>Create new child node
	 * 
	 * @see IContextMenu#getMenu()
	 */
	@Override
	public List<JMenuItem> getMenu() {
		final TreeNode thisNode = this;
		JMenuItem item = new JMenuItem("Create new");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ATabNode<I> newChild = getNewChild(null);
				add(newChild);
				((ProjectTreeModel) projectTree.getModel()).reload(thisNode);

				// make its pane visible as tab
				FresnelApplication.getApp().getBaseFrame()
						.openTab(newChild, newChild.getComponent());
				FresnelApplication.getApp().getBaseFrame().hidePreviewPanel();
			}
		});

		return Arrays.asList(new JMenuItem[] { item });
	}

}
