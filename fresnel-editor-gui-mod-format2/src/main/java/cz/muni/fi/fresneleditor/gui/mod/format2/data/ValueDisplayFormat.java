package cz.muni.fi.fresneleditor.gui.mod.format2.data;

import fr.inria.jfresnel.formats.FormatValueType;

public class ValueDisplayFormat {

	private FormatValueType valueType;
	private String label;

	public ValueDisplayFormat(FormatValueType valueType, String label) {
		this.valueType = valueType;
		if (label == null) {
			this.label = valueType.toString();
		} else {
			this.label = label;
		}
	}
        
        public ValueDisplayFormat(FormatValueType valueType) {
		this.valueType = valueType;
		this.label = valueType.toString();
	}

	public FormatValueType getValueType() {
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
