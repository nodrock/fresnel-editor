/**
 * Fresnel Editor
 * 
 * SVG Preprocessor
 */
package cz.muni.fi.fresneleditor.common.visualization.preprocessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  
 * @author Milos Kalab <173388@mail.muni.cz>
 * @version 5. 5. 2012
 */
class Property {

    private int propertyId;
    private String propertyUri;
    private String propertyLabel;
    private String originalPropertyLabel;
    private int longTextRowNumber = 1;
    private List<AbstractValue> values;
    private String propertyClass;
    private String propertyLabelClass;

    public Property(String propertyUri, String propertyClass, String propertyLabelClass, String propertyLabel) {
        this.propertyUri = propertyUri;

        int rowsNeeded = Utils.getInstance().countRows(propertyLabel, SVGPreprocessor.xslSet.getPropLine2Lenght());
        if (rowsNeeded > 1) {
            this.longTextRowNumber = rowsNeeded;
            this.propertyLabel = Utils.getInstance().shortenText(propertyLabel, SVGPreprocessor.xslSet.getPropLine2Lenght());
            this.originalPropertyLabel = propertyLabel;
        } else {
            this.propertyLabel = propertyLabel;
             this.originalPropertyLabel = "";
        }
        this.propertyClass = propertyClass;
        this.propertyLabelClass = propertyLabelClass;
        values = new ArrayList<AbstractValue>();
        TempXMLParser parser = TempXMLParser.getInstance();
        this.propertyId = parser.getPropertyId();
        parser.incrementPropertyId();
    }

    public String getPropertyClass() {
        return propertyClass;
    }

    public void setPropertyClass(String propertyClass) {
        this.propertyClass = propertyClass;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public String getPropertyLabel() {
        return propertyLabel;
    }

    public void setPropertyLabel(String propertyLabel) {
        int rowsNeeded = Utils.getInstance().countRows(propertyLabel, SVGPreprocessor.xslSet.getPropLine2Lenght());
        if (rowsNeeded > 1) {
            this.longTextRowNumber = rowsNeeded;
            this.propertyLabel = Utils.getInstance().shortenText(propertyLabel, SVGPreprocessor.xslSet.getPropLine2Lenght());
            this.originalPropertyLabel = propertyLabel;
        } else {
            this.propertyLabel = propertyLabel;
             this.originalPropertyLabel = "";
        }
    }

    public String getPropertyLabelClass() {
        return propertyLabelClass;
    }

    public void setPropertyLabelClass(String propertyLabelClass) {
        this.propertyLabelClass = propertyLabelClass;
    }

    public String getPropertyUri() {
        return propertyUri;
    }

    public void setPropertyUri(String propertyUri) {
        this.propertyUri = propertyUri;
    }

    public List<AbstractValue> getValues() {
        return values;
    }

    public void setValues(List<AbstractValue> values) {
        this.values = values;
    }

    public void addValue(AbstractValue value) {
        this.values.add(value);
    }

    public int getLongTextRowNumber() {
        return longTextRowNumber;
    }

    public void setLongTextRowNumber(int longTextRowNumber) {
        this.longTextRowNumber = longTextRowNumber;
    }

    public String getOriginalPropertyLabel() {
        return originalPropertyLabel;
    }

    public void setOriginalPropertyLabel(String originalPropertyLabel) {
        this.originalPropertyLabel = originalPropertyLabel;
    }

    @Override
    public String toString() {
        String s = "<property";
        if (!this.getPropertyClass().trim().isEmpty()) {
            s += " class=\"" + this.getPropertyClass() + "\"";
        }
        if (!this.getPropertyUri().trim().isEmpty()) {
            s += " uri=\"" + this.getPropertyUri() + "\"";
        }
        s += ">\n";
        s += "<label";
        s += " long-text-rows=\"" + this.getLongTextRowNumber() + "\"";
        if (!this.getPropertyLabelClass().trim().isEmpty()) {
            s += " class=\"" + this.getPropertyLabelClass() + "\"";
        }
        s += ">\n";
        if (!this.getPropertyLabel().trim().isEmpty()) {
            s += "<title";
            if (!this.getOriginalPropertyLabel().trim().isEmpty()) {
                s += " full-label=\"" + this.getOriginalPropertyLabel() + "\"";
            }
            s += ">" + this.getPropertyLabel() + "</title>\n";
        } else {
            s += "<title/>\n";
        }
        s += "</label>\n";
        List<AbstractValue> tempValues = this.getValues();
        if (!tempValues.isEmpty()) {
            s += "<values>\n";
            for (Iterator it = tempValues.iterator(); it.hasNext();) {
                AbstractValue value = (AbstractValue) it.next();
                s += value.toString();
            }
            s += "</values>\n";
        }
        s += "</property>\n";

        return s;
    }
}
