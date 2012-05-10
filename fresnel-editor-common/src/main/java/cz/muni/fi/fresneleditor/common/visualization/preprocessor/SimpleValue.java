/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.fresneleditor.common.visualization.preprocessor;

/**
 *
 * @author Milos Kalab <173388@mail.muni.cz>
 */
public class SimpleValue extends AbstractValue {

    private String valueOutputType;
    private String valueContent;
    private String originalValueContent;
    private int longTextRowNumber = 1;

    public SimpleValue(String valueClass, String valueOutputType, String valueContent) {
        super(valueClass, valueOutputType);
        this.setValueContent(valueContent);
    }

    public String getValueContent() {
        return valueContent;
    }

    public void setValueContent(String valueContent) {
        int rectWidth = SVGPreprocessor.xslSet.getRectWidth();
        int rectMargin = SVGPreprocessor.xslSet.getTextResXInd()-SVGPreprocessor.xslSet.getRectResXInd();
        if (rectWidth > rectMargin) {
            rectWidth -= rectMargin; //margins in the rectangle
        }
        int rowsNeeded = Utils.getInstance().countRows(valueContent, rectWidth);
        if (rowsNeeded > 1) {
            this.longTextRowNumber = rowsNeeded;
            this.valueContent = Utils.getInstance().shortenText(valueContent, rectWidth);
            this.originalValueContent = valueContent;
        } else {
            this.valueContent = valueContent;
        }
    }

    private int getLongTextRowNumber() {
        return longTextRowNumber;
    }

    private void setLongTextRowNumber(int longTextRowNumber) {
        this.longTextRowNumber = longTextRowNumber;
    }

    public String getOriginalValueContent() {
        return originalValueContent;
    }

    public void setOriginalValueContent(String originalValueContent) {
        this.originalValueContent = originalValueContent;
    }

    @Override
    public String toString() {
        String s = "<value";
        if (!super.getValueClass().isEmpty()) {
            s += " class=\"" + super.getValueClass() + "\"";
        }
        if (!super.getValueOutputType().isEmpty()) {
            s += " output-type=\"" + this.getValueOutputType() + "\"";
            s += " long-text-rows=\"1\"";
        } else {
            s += " long-text-rows=\"" + this.getLongTextRowNumber() + "\"";
        }
        s += ">\n";
        if (!this.getValueContent().isEmpty()) {
            if (this.getValueOutputType().isEmpty() && this.getLongTextRowNumber() > 1) {
                s += "<title full-value=\"" + this.getOriginalValueContent() + "\"";
                s += ">" + this.getValueContent() + "</title>\n";
            } else {
                if (this.getOriginalValueContent() != null) {
                    s += "<title>" + this.getOriginalValueContent() + "</title>\n";
                } else {
                    s += "<title>" + this.getValueContent() + "</title>\n";
                }
            }
        } else {
            s += "<title/>\n";
        }
        s += "</value>\n";
        return s;
    }
}
