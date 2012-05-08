/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.fresneleditor.gui.mod.lens.model;

import fr.inria.jfresnel.lenses.LensPurposeType;

/**
 *
 * @author nodrock
 */
public class PurposeDisplayFormat {
    private LensPurposeType purposeType;
	private String label;

	public PurposeDisplayFormat(LensPurposeType purposeType, String label) {
		this.purposeType = purposeType;
		if (label == null) {
			this.label = purposeType.toString();
		} else {
			this.label = label;
		}
	}
        
        public PurposeDisplayFormat(LensPurposeType purposeType) {
		this.purposeType = purposeType;
		this.label = purposeType.toString();
	}

	public LensPurposeType getPurposeType() {
		return purposeType;
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
		} else if (obj instanceof PurposeDisplayFormat) {
			return this.purposeType == ((PurposeDisplayFormat) obj).purposeType;
		} else {
			return false;
		}
	}
}
