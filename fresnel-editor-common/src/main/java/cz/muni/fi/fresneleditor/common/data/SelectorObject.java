package cz.muni.fi.fresneleditor.common.data;

public class SelectorObject {
	private Boolean isUpdated = false;
	private SelectorType selectorType;
	private String query;

	public SelectorObject(SelectorType selectorType, String query) {
		this.selectorType = selectorType;

		this.query = query;
	}

	public void setSelectorType(SelectorType selectorType) {
		this.selectorType = selectorType;
	}

	public SelectorType getSelectorType() {
		return selectorType;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public void setUpdated(Boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	public Boolean isUpdated() {
		return isUpdated;
	}
}
