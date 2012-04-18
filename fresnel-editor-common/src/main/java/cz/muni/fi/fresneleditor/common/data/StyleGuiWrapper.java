/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.data;

/**
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 22.3.2009
 */
public class StyleGuiWrapper implements Cloneable {

	private StyleType type = StyleType.LABEL;

	private CssValueType valueType = CssValueType.CLASS;

	private String value = "";

	private boolean updated = false;

	/**
	 * Basic simple constructor.
	 * 
	 * @param styleType
	 */
	public StyleGuiWrapper(StyleType styleType) {
		this.type = styleType;
	}

	public StyleType getType() {
		return type;
	}

	public void setType(StyleType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CssValueType getValueType() {
		return valueType;
	}

	public void setValueType(CssValueType valueType) {
		this.valueType = valueType;
	}

	/**
	 * @return the updated
	 */
	public boolean isUpdated() {
		return updated;
	}

	/**
	 * @param updated
	 *            the updated to set
	 */
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	@Override
	public StyleGuiWrapper clone() {

		StyleGuiWrapper style = new StyleGuiWrapper(this.getType());

		style.setValue(this.getValue());
		style.setValueType(this.getValueType());

		return style;
	}
}
