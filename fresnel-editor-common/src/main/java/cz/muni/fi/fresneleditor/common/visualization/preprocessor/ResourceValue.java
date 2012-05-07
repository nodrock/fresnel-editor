/**
 * Fresnel Editor
 * 
 * SVG Preprocessor
 */
package cz.muni.fi.fresneleditor.common.visualization.preprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  
 * @author Milos Kalab <173388@mail.muni.cz>
 * @version 5. 5. 2012
 */
public class ResourceValue extends AbstractValue {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceValue.class);
    private int boundResourceId;

    public ResourceValue(String valueClass, String valueOutputType, int resourceId) {
        super(valueClass, valueOutputType);
        this.boundResourceId = resourceId;
    }

    public int getBoundResourceId() {
        return boundResourceId;
    }

    @Override
    public String toString() {
        TempXMLParser parser = TempXMLParser.getInstance();
        String s = "<value";
        if (!super.getValueClass().trim().isEmpty()) {
            s += " class=\"" + super.getValueClass() + "\"";
        }
        if (!super.getValueOutputType().trim().isEmpty()) {
            s += " output-type=\"" + this.getValueOutputType() + "\"";
        }
        s += ">\n";
        int resourceId = this.getBoundResourceId();
        Resource boundResource = parser.getResourceFromMap(resourceId);
        try {
            s += boundResource.toString();
        } catch (NullPointerException ex) {
            LOG.warn(ex.getMessage(), ex);
            LOG.warn("Can't find resource according to resourceId");
				
        }
        s += "</value>\n";
        return s;
    }
}
