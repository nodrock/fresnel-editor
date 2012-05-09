/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.fresneleditor.gui.mod.portal.services;

import cz.muni.fi.fresneleditor.gui.mod.portal.model.Service;
import cz.muni.fi.fresneleditor.gui.mod.portal.model.Transformation;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author nodrock
 */
public interface PortalService {
    public List<Service> getServices();   
    
    public List<Transformation> getTransformations();
    
    public Integer uploadProject(InputStream project);
    
    public boolean visualizeProject(Integer projectId, String group, Integer service, Integer transformation, OutputStream result);
}
