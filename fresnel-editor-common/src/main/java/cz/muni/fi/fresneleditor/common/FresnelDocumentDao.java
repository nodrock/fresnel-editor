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
        Collection<Lens> lenses = oldGroup.getLenses();
        for(Lens l : lenses){
            l.getAssociatedGroups().remove(oldGroup);
            l.getAssociatedGroups().add(group);
        }
        Collection<Format> formats = oldGroup.getFormats();
        for(Format f : formats){
            f.getAssociatedGroups().remove(oldGroup);
            f.getAssociatedGroups().add(group);
        }
        
        fresnelDocument.getGroups().remove(oldGroup);
        fresnelDocument.getGroups().add(group);
    }
    
    public void deleteLens(String lensUri){
        Lens oldLens = fresnelDocument.getLens(lensUri);
        Collection<Group> associatedGroups = oldLens.getAssociatedGroups();
        for(Group g : associatedGroups){
            g.getLenses().remove(oldLens);
        }
        
        fresnelDocument.getLenses().remove(oldLens);
    }
    
    public void deleteFormat(String formatUri){
        Format oldFormat = fresnelDocument.getFormat(formatUri);
        Collection<Group> associatedGroups = oldFormat.getAssociatedGroups();
        for(Group g : associatedGroups){
            g.getFormats().remove(oldFormat);
        }
        
        fresnelDocument.getFormats().remove(oldFormat);
    }
    
    public void deleteGroup(String groupUri){
        Group oldGroup = fresnelDocument.getGroup(groupUri);
        Collection<Lens> lenses = oldGroup.getLenses();
        for(Lens l : lenses){
            l.getAssociatedGroups().remove(oldGroup);      
        }
        Collection<Format> formats = oldGroup.getFormats();
        for(Format f : formats){
            f.getAssociatedGroups().remove(oldGroup);         
        }
        
        fresnelDocument.getGroups().remove(oldGroup);       
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
    
    public void addGroup(Group group){
        for(Lens l : group.getLenses()){
            l.addAssociatedGroup(group);
        }
        for(Format f : group.getFormats()){
            f.addAssociatedGroup(group);
        }
        fresnelDocument.getGroups().add(group);
    }
}
