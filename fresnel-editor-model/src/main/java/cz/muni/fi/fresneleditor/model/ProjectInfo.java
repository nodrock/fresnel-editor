/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.fresneleditor.model;

/**
 *
 * @author nodrock
 */
public class ProjectInfo {
    private String uri;
    private String name;
    private String description;

    public ProjectInfo(String uri, String name, String description) {
        this.uri = uri;
        this.name = name;
        this.description = description;
    }

    public ProjectInfo() {
        
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    
}
