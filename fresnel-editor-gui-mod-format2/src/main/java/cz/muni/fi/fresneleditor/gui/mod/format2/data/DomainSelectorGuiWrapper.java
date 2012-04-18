/**
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.format2.data;

import cz.muni.fi.fresneleditor.common.data.DomainType;
import cz.muni.fi.fresneleditor.common.data.SelectorType;

/**
 * Class holds all necessary information about domain selector for GUI (domain
 * selector dialog).
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 21. 3. 2009
 */
public class DomainSelectorGuiWrapper implements Cloneable {

	private DomainType domainType = DomainType.PROPERTY;

	private SelectorType selectorType = SelectorType.SIMPLE;

	private String selectorString = "";

	private boolean updated = false;

	/**
	 * Simple constructor.
	 */
	public DomainSelectorGuiWrapper() {
	}

	/**
	 * Constructor with domain type parameter.
	 */
	public DomainSelectorGuiWrapper(DomainType domainType) {

		this.domainType = domainType;
	}

	/**
	 * Complete constructor with all domain selector parameters
	 * 
	 * @param selectorType
	 * @param domainType
	 * @param selectorString
	 */
	public DomainSelectorGuiWrapper(DomainType domainType,
			SelectorType selectorType, String selectorString) {

		this.domainType = domainType;
		this.selectorType = selectorType;
		this.selectorString = selectorString;
	}

	/**
	 * @return the selectorType
	 */
	public SelectorType getSelectorType() {
		return selectorType;
	}

	/**
	 * @param selectorType
	 *            the selectorType to set
	 */
	public void setSelectorType(SelectorType selectorType) {
		this.selectorType = selectorType;
	}

	/**
	 * @return the domainType
	 */
	public DomainType getDomainType() {
		return domainType;
	}

	/**
	 * @param domainType
	 *            the domainType to set
	 */
	public void setDomainType(DomainType domainType) {
		this.domainType = domainType;
	}

	/**
	 * @return the selectorString
	 */
	public String getSelectorString() {
		return selectorString;
	}

	/**
	 * @param selectorString
	 *            the selectorString to set
	 */
	public void setSelectorString(String selectorString) {
		this.selectorString = selectorString;
	}

	/**
	 * @return true if encapsulated selector data were updated
	 */
	public boolean isUpdated() {
		return updated;
	}

	/**
	 * @param updated
	 *            true if encapsulated selector data were updated
	 */
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "Domain selector (" + domainType + ", " + selectorType + ", "
				+ selectorString + ")";
	}

	@Override
	public DomainSelectorGuiWrapper clone() {

		DomainSelectorGuiWrapper ds = new DomainSelectorGuiWrapper();

		ds.setSelectorType(this.getSelectorType());
		ds.setDomainType(this.getDomainType());
		ds.setSelectorString(this.getSelectorString());

		return ds;
	}
}
