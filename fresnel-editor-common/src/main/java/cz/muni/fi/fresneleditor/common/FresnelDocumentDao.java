/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.fresneleditor.common;

import fr.inria.jfresnel.Format;
import fr.inria.jfresnel.FresnelDocument;
import fr.inria.jfresnel.Group;
import fr.inria.jfresnel.Lens;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author nodrock
 */
public class FresnelDocumentDao {
    private FresnelDocument fresnelDocument;

    public FresnelDocument getFresnelDocument() {
        return fresnelDocument;
    }

    public void setFresnelDocument(FresnelDocument fresnelDocument) {
        this.fresnelDocument = fresnelDocument;
    }

    public FresnelDocumentDao(FresnelDocument fresnelDocument) {
        this.fresnelDocument = fresnelDocument;
    }

    public void updateLens(String lensUri, Lens lens){
        Lens oldLens = fresnelDocument.getLens(lensUri);
        Collection<Group> associatedGroups = oldLens.getAssociatedGroups();
        for(Group g : associatedGroups){
            g.getLenses().remove(oldLens);
            g.getLenses().add(lens);
        }
        
        fresnelDocument.getLenses().remove(oldLens);
        fresnelDocument.getLenses().add(lens);
    }
    
    public void updateFormat(String formatUri, Format format){
        Format oldFormat = fresnelDocument.getFormat(formatUri);
        Collection<Group> associatedGroups = oldFormat.getAssociatedGroups();
        for(Group g : associatedGroups){
            g.getFormats().remove(oldFormat);
            g.getFormats().add(format);
        }
        
        fresnelDocument.getFormats().remove(oldFormat);
        fresnelDocument.getFormats().add(format);
    }
    
    public void updateGroup(String groupUri, Group group){
        Group oldGroup = fresnelDocument.getGroup(groupUri);
        fresnelDocument.getGroups().remove(oldGroup);
        fresnelDocument.getGroups().add(group);
    }
    
    public List<Lens> getLenses(){
        return fresnelDocument.getLenses();
    }
    
    public List<Format> getFormats(){
        return fresnelDocument.getFormats();
    }
    
    public List<Group> getGroups(){
        return fresnelDocument.getGroups();
    }
    
    public Lens getLens(String lensUri){
        return fresnelDocument.getLens(lensUri);
    }
    
    public Format getFormat(String formatUri){
        return fresnelDocument.getFormat(formatUri);
    }
    
    public Group getGroup(String groupUri){
        return fresnelDocument.getGroup(groupUri);
    }
}
