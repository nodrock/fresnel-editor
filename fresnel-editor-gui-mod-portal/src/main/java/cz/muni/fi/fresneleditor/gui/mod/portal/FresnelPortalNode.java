package cz.muni.fi.fresneleditor.gui.mod.portal;

import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.config.ProjectConfiguration;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ATabNode;

/**
 *
 * @author nodrock
 */
public class FresnelPortalNode extends ATabNode<ProjectConfiguration> {

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
