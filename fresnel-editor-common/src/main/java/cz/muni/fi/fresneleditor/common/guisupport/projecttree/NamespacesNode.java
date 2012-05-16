/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.projecttree;

import com.google.common.collect.Lists;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.reposconf.NamespacesConfigurationJPanel;
import cz.muni.fi.fresneleditor.model.BaseRepositoryDao;
import java.util.ArrayList;
import java.util.List;

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
            List<BaseRepositoryDao> brd = new ArrayList<BaseRepositoryDao>();
            brd.add(ContextHolder.getInstance().getDataRepositoryDao());
            return new NamespacesConfigurationJPanel(brd);
	}

	@Override
	public String toString() {
		return NamespacesConfigurationJPanel.NAMESPACES_TAB_LABEL;
	}

}
