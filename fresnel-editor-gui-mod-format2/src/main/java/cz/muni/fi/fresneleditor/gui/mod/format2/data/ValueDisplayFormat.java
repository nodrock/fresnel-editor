package cz.muni.fi.fresneleditor.gui.mod.format2.data;

import cz.muni.fi.fresneleditor.gui.mod.format2.data.enums.ValueType;

public class ValueDisplayFormat {

	private ValueType valueType;
	private String label;

	public ValueDisplayFormat(ValueType valueType, String label) {
		this.valueType = valueType;
		if (label == null) {
			this.label = valueType.toString();
		} else {
			this.label = label;
		}
	}

	public ValueType getValueType() {
		return valueType;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return label;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof ValueDisplayFormat) {
			return this.valueType == ((ValueDisplayFormat) obj).valueType;
		} else {
			return false;
		}
	}

}
