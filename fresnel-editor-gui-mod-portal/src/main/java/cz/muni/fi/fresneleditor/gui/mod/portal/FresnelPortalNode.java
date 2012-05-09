/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.fresneleditor.gui.mod.portal;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.config.ProjectConfiguration;
import cz.muni.fi.fresneleditor.common.config.projectconf.EditProjectConfigurationJPanel;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ARootTabNode;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ATabNode;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.IClickableNode;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ProjectTree;
import java.util.List;

/**
 *
 * @author nodrock
 */
public class FresnelPortalNode extends ATabNode<ProjectConfiguration> {

//    private static final ResourceBundle bundle = java.util.ResourceBundle
//                    .getBundle(FresnelEditorConstants.PATH_RESOURCE_BUNDLE_COMMON);

    public FresnelPortalNode() {
            super(null);
    }

    @Override
    protected ITabComponent<ProjectConfiguration> createComponent() {
            return new FresnelPortalJPanel();
        
    }

    @Override
    public String toString() {
            return "Fresnel Portal";
    }
    
}
