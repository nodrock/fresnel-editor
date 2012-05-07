/**
 * Fresnel Editor
 * 
 * SVG Preprocessor
 */
package cz.muni.fi.fresneleditor.common.visualization.preprocessor;


/**
 *  
 * @author Milos Kalab <173388@mail.muni.cz>
 * @version 5. 5. 2012
 */
public abstract class AbstractValue {

    private String valueClass;
    private int valueId;
    private String valueOutputType;

    public AbstractValue(String valueClass, String valueOutputType) {
        this.valueClass = valueClass;
        this.valueOutputType = valueOutputType;
        TempXMLParser parser = TempXMLParser.getInstance();
        this.valueId = parser.getValueId();
        parser.incrementValueId();
    }

    public String getValueClass() {
        return valueClass;
    }

    public void setValueClass(String valueClass) {
        this.valueClass = valueClass;
    }

    public int getValueId() {
        return valueId;
    }

    public String getValueOutputType() {
        return valueOutputType;
    }

    public void setValueOutputType(String valueOutputType) {
        this.valueOutputType = valueOutputType;
    }
    
    public abstract String toString();
}
