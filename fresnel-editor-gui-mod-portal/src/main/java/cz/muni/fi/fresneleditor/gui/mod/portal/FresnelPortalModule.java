package cz.muni.fi.fresneleditor.gui.mod.portal;

import cz.muni.fi.fresneleditor.common.FresnelEditorModule;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ITreeModule;
import cz.muni.fi.fresneleditor.common.guisupport.projecttree.ProjectTree;
import javax.swing.tree.DefaultMutableTreeNode;
import org.springframework.stereotype.Component;

/**
 *
 * @author nodrock
 */
@Component
public class FresnelPortalModule extends FresnelEditorModule implements ITreeModule {

    public FresnelPortalModule() {
        super("Fresnel Portal Module", "alpha");
    }

    @Override
    public DefaultMutableTreeNode getRoot(ProjectTree projectTree) {
        return new FresnelPortalNode();
    }
}
