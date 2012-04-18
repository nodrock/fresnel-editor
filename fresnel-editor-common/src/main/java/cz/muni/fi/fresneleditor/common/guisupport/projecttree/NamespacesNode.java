/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.projecttree;

import com.google.common.collect.Lists;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.reposconf.NamespacesConfigurationJPanel;

/**
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class NamespacesNode extends ATabNode<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NamespacesNode() {
		this(null);
	}

	public NamespacesNode(Object item) {
		super(item);
	}

	@Override
	protected ITabComponent<Object> createComponent() {
		return new NamespacesConfigurationJPanel(Lists.newArrayList(
				ContextHolder.getInstance().getFresnelRepositoryDao(),
				ContextHolder.getInstance().getDataRepositoryDao()));
	}

	@Override
	public String toString() {
		return NamespacesConfigurationJPanel.NAMESPACES_TAB_LABEL;
	}

}
