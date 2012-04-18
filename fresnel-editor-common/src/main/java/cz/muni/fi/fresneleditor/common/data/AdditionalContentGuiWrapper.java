/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.data;

/**
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 28.3.2009
 */
public class AdditionalContentGuiWrapper implements Cloneable {

	private AdditionalContentType type = null;

	private String contentBefore = null;

	private String contentAfter = null;

	private String contentFirst = null;

	private String contentLast = null;

	private String contentNoValue = null;

	private boolean updated = false;

	/**
	 * Default constructor.
	 */
	public AdditionalContentGuiWrapper(AdditionalContentType type) {

		this.type = type;
	}

	/**
	 * Full constructor.
	 */
	public AdditionalContentGuiWrapper(AdditionalContentType type,
			String contentBefore, String contentAfter, String contentFirst,
			String contentLast, String contentNoValue) {

		this.type = type;
		this.contentBefore = contentBefore;
		this.contentAfter = contentAfter;
		this.contentFirst = contentFirst;
		this.contentLast = contentLast;
		this.contentNoValue = contentNoValue;
	}

	public int getCountOfSetAdditionalContents() {

		int counter = 0;

		if (contentBefore != null) {
			counter++;
		}

		if (contentAfter != null) {
			counter++;
		}

		if (contentFirst != null) {
			counter++;
		}

		if (contentLast != null) {
			counter++;
		}

		if (contentNoValue != null) {
			counter++;
		}

		return counter;
	}

	/**
	 * 
	 * @return
	 */
	public String getContentAfter() {
		return contentAfter;
	}

	/**
	 * 
	 * @param contentAfter
	 */
	public void setContentAfter(String contentAfter) {
		this.contentAfter = contentAfter;
	}

	/**
	 * 
	 * @return
	 */
	public String getContentBefore() {
		return contentBefore;
	}

	/**
	 * 
	 * @param contentBefore
	 */
	public void setContentBefore(String contentBefore) {
		this.contentBefore = contentBefore;
	}

	/**
	 * 
	 * @return
	 */
	public String getContentFirst() {
		return contentFirst;
	}

	/**
	 * 
	 * @param contentFirst
	 */
	public void setContentFirst(String contentFirst) {
		this.contentFirst = contentFirst;
	}

	/**
	 * 
	 * @return
	 */
	public String getContentLast() {
		return contentLast;
	}

	/**
	 * 
	 * @param contentLast
	 */
	public void setContentLast(String contentLast) {
		this.contentLast = contentLast;
	}

	/**
	 * 
	 * @return
	 */
	public String getContentNoValue() {
		return contentNoValue;
	}

	/**
	 * 
	 * @param contentNoValue
	 */
	public void setContentNoValue(String contentNoValue) {
		this.contentNoValue = contentNoValue;
	}

	/**
	 * 
	 * @return
	 */
	public AdditionalContentType getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 */
	public void setType(AdditionalContentType type) {
		this.type = type;
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
	public AdditionalContentGuiWrapper clone() {

		AdditionalContentGuiWrapper additionalContent = new AdditionalContentGuiWrapper(
				this.getType());

		additionalContent.setContentFirst(this.getContentFirst());
		additionalContent.setContentLast(this.getContentLast());
		additionalContent.setContentBefore(this.getContentBefore());
		additionalContent.setContentAfter(this.getContentAfter());
		additionalContent.setContentNoValue(this.getContentNoValue());

		return additionalContent;
	}
}
