/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.fresneleditor.gui.mod.lens2.components;

import javax.swing.JLabel;

import fr.inria.jfresnel.Format;
import fr.inria.jfresnel.Group;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class FresnelUseLabel extends JLabel {

	private static final String GROUP_PREFIX = "Group: ";
	private static final String FORMAT_PREFIX = "Format: ";

	private Object value;

	public void setValue(Object value) {
		this.value = value;
		setText(valueToText());
	}

	private String valueToText() {
		if (value == null) {
			return "";
		}
		if (value instanceof Group) {
			return GROUP_PREFIX + ((Group) value).getURI();
		} else if (value instanceof Format) {
			return FORMAT_PREFIX + ((Format) value).getURI();
		} else {
			throw new IllegalArgumentException("parameter must be of type "
					+ Group.class + " or " + Format.class + "but was of type: "
					+ value.getClass());
		}
	}

	public Object getValue() {
		return value;
	}

}
