/*
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.common.guisupport.projecttree;

import java.util.HashSet;
import java.util.Set;

import cz.muni.fi.fresneleditor.common.AppEventsManager;
import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.events.IRepositoryDataChangedListener;
import cz.muni.fi.fresneleditor.common.events.RepositoryDataChangedEvent;

/**
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * @param <I>
 *            data type which given node is supposed to hold
 */
public abstract class AFresnelRepoDataChangedListeningRootTabNode<I> extends
		ARootTabNode<I> implements IRepositoryDataChangedListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AFresnelRepoDataChangedListeningRootTabNode(String label,
			ProjectTree projectTree) {
		super(label, projectTree);
		AppEventsManager.getInstance().addFresnelAppEventListener(
				IRepositoryDataChangedListener.class, this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void repositoryDataChanged(RepositoryDataChangedEvent evt) {
		String changedRepo = evt.getRepositoryName();
		if (changedRepo.equals(ContextHolder.getInstance()
				.getFresnelRepositoryName())) {
			// react only if the currently opened fresnel repository changed

			Set<I> openedTabIDs = new HashSet<I>();

			// remember opened tabs
			if (!isLeaf()) {
				for (int i = 0; i < children.size(); i++) {
					ATabNode<I> tabNode = (ATabNode<I>) children.get(i);
					if ((tabNode.isOpened())) {
						openedTabIDs.add(tabNode.getUserObject());
						tabNode.closeTab();
					}
				}
			}

			// refresh the nodes
			updateChildren();

			// open the tabs again
			if (!isLeaf()) {
				for (int i = 0; i < children.size(); i++) {
					ATabNode<I> tabNode = (ATabNode<I>) children.get(i);
					if (openedTabIDs.contains(tabNode.getUserObject())) {
						// open the tab via the node click action...
						tabNode.openTheNodeAsTab();
					}
				}
			}
		}
	}

}
