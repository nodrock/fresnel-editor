/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.fresneleditor.common;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFReader;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import fr.inria.jfresnel.Constants;
import fr.inria.jfresnel.Format;
import fr.inria.jfresnel.FresnelDocument;
import fr.inria.jfresnel.Group;
import fr.inria.jfresnel.Lens;
import fr.inria.jfresnel.fsl.FSLHierarchyStore;
import fr.inria.jfresnel.fsl.FSLNSResolver;
import fr.inria.jfresnel.fsl.jena.FSLJenaHierarchyStore;
import fr.inria.jfresnel.jena.FresnelJenaParser;
import fr.inria.jfresnel.jena.FresnelJenaWriter;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author nodrock
 */
public class DatasetUtils {
    
    public static String getLastPart(String uri){
        String[] parts = uri.split("/");
        if(parts.length == 1){
            return uri;
        }
        String name = parts[parts.length-1];
        if(name.indexOf("#") == -1){
            return name;
        }
        String[] split = name.split("#");
        return split[split.length-1];
    }
    
    public static boolean replaceNamespaces(String newNamespace, InputStream is, OutputStream os){
        Model model = ModelFactory.createDefaultModel();
        Model newModel = ModelFactory.createDefaultModel();
        
        RDFReader parser = model.getReader("N3");
        parser.read(model, is, "");
        
        DatasetInfo dsi = parseDatasetInfo(model);
        if(dsi == null){
            return false;
        }
        writeDatasetInfo(newModel, dsi, newNamespace);
 
        FSLNSResolver nsr = new FSLNSResolver();
        FSLHierarchyStore fhs = new FSLJenaHierarchyStore();
        FresnelJenaParser fjp = new FresnelJenaParser(nsr, fhs);
        FresnelDocument fd = fjp.parse(model, "");
        
        String ns = newNamespace + dsi.getName() + "#";
        
        for(Lens lens : fd.getLenses()){
            lens.setURI(ns + getLastPart(lens.getURI()), ns);
        }
        
        for(Format format : fd.getFormats()){
            format.setURI(ns + getLastPart(format.getURI()), ns);
        }
        
        for(Group group : fd.getGroups()){
            group.setURI(ns + getLastPart(group.getURI()), ns);
        }
        
        // adds fresnelportal namespace and add all namespaces to outputmodel
        fd.getPrefixes().put("fresnelportal", newNamespace);
        fd.getPrefixes().put("fresnelportal" + dsi.getName(), ns);
        
        // some basic namespaces in case they are not there already
        fd.getPrefixes().put("rdf", Constants.RDF_NAMESPACE_URI);
        fd.getPrefixes().put("rdfs", Constants.RDFS_NAMESPACE_URI);
        fd.getPrefixes().put("fresnel", Constants.FRESNEL_NAMESPACE_URI);
        fd.getPrefixes().put("xsd", Constants.XSD_NAMESPACE_URI);         
        
        newModel.setNsPrefixes(fd.getPrefixes());
        
        FresnelJenaWriter fjw = new FresnelJenaWriter();
        fjw.write(fd, newModel);
        
        RDFWriter writer = newModel.getWriter("N3");
        writer.write(newModel, os, "");        
        
        return true;
    }
    
    public static DatasetInfo parseDatasetInfo(Model model){
        StmtIterator si = model.listStatements(null, model.createProperty(Constants.RDF_NAMESPACE_URI, Constants._type), model.getResource(NSConstants._Dataset));
	Statement s;
        Resource project;
	if (si.hasNext()){
	    s = si.nextStatement();
            project = s.getSubject();
            
            String uri = project.getURI();
            String[] parts = uri.split("/");
            if(parts.length <= 1){
                return null;
            }
            String name = parts[parts.length-1];
            if(name.indexOf("#") != -1){
                return null;
            }
            
            Statement titleStmt = project.getProperty(model.createProperty(NSConstants.DCTERMS_NAMESPACE_URI, NSConstants._title));
            Statement descriptionStmt = project.getProperty(model.createProperty(NSConstants.DCTERMS_NAMESPACE_URI, NSConstants._description));
            if(titleStmt == null){
                return null;
            }
            String title = titleStmt.getLiteral().getLexicalForm();
            if(title == null){
                return null;
            }
            String description = null;
            if(descriptionStmt != null){
                description = descriptionStmt.getLiteral().getLexicalForm();
            }
            if(description == null){
                description = "";
            }
         
            return new DatasetInfo(name, title, description);        
	}
	si.close();
        model.close();
        
        return null;
    }
    
    public static void writeDatasetInfo(Model model, DatasetInfo dataset, String namespace){              
        Resource datasetResource = model.createResource(namespace + dataset.getName());
        
        // rdf:type
        model.add(model.createStatement(datasetResource,
                model.createProperty(Constants.RDF_NAMESPACE_URI, Constants._type),
                model.createResource(NSConstants._Dataset)));

        // dcterms:title
        if (!dataset.getTitle().equals("")) {
            model.add(model.createStatement(datasetResource,
                    model.createProperty(NSConstants.DCTERMS_NAMESPACE_URI, NSConstants._title),
                    model.createLiteral(dataset.getTitle())));
        }
        
        // dcterms:description
        if (!dataset.getDescription().equals("")) {
            model.add(model.createStatement(datasetResource,
                    model.createProperty(NSConstants.DCTERMS_NAMESPACE_URI, NSConstants._description),
                    model.createLiteral(dataset.getDescription())));
        }    
    }
}
