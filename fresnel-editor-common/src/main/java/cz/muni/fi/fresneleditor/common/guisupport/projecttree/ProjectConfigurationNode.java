/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.projecttree;

import java.util.ResourceBundle;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.FresnelEditorConstants;
import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.config.ProjectConfiguration;
import cz.muni.fi.fresneleditor.common.config.projectconf.EditProjectConfigurationJPanel;

/**
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class ProjectConfigurationNode extends ATabNode<ProjectConfiguration> {

	private static final long serialVersionUID = 1L;

	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle(FresnelEditorConstants.PATH_RESOURCE_BUNDLE_COMMON);

	public ProjectConfigurationNode() {
		super(null);
	}

	@Override
	protected ITabComponent<ProjectConfiguration> createComponent() {
		return new EditProjectConfigurationJPanel(ContextHolder.getInstance()
				.getProjectConfigurationCopy(), ContextHolder.getInstance()
				.getApplicationConfiguration().getLastOpenProjectUrl());
	}

	@Override
	public String toString() {
		return bundle.getString("Project_configuration");
	}

}
