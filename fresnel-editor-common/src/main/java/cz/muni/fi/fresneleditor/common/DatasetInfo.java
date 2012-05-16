/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.fresneleditor.common;

/**
 *
 * @author nodrock
 */
public class DatasetInfo {
    private String name;
    private String title;
    private String description;

    public DatasetInfo(String name, String title, String description) {
        this.name = name;
        this.title = title;
        this.description = description;
    }

    public DatasetInfo() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
