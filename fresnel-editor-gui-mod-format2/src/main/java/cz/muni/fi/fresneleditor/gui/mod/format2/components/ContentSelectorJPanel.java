package cz.muni.fi.fresneleditor.gui.mod.format2.components;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.jdesktop.application.Application;

import cz.muni.fi.fresneleditor.common.data.AdditionalContentGuiWrapper;
import cz.muni.fi.fresneleditor.common.data.AdditionalContentPositionType;
import cz.muni.fi.fresneleditor.common.data.CssValueType;
import cz.muni.fi.fresneleditor.common.data.StyleGuiWrapper;
import cz.muni.fi.fresneleditor.common.data.StyleType;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ContentSelectorJPanel extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox contentEnabled;
	private JTextField contentTextField;
	// we use this component to edit additional content and styles
	private AdditionalContentGuiWrapper additionalContent = null;
	private AdditionalContentPositionType position = null;

	private StyleGuiWrapper style = null;
	private StyleType styleType = null;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */

	public ContentSelectorJPanel() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(400, 20));
			BoxLayout thisLayout = new BoxLayout(this,
					javax.swing.BoxLayout.X_AXIS);
			this.setLayout(thisLayout);
			this.setName("this");
			this.setMaximumSize(new java.awt.Dimension(32767, 20));
			{
				contentEnabled = new JCheckBox();
				this.add(contentEnabled);
				contentEnabled.setName("contentEnabled");
				contentEnabled.setBounds(-1, 0, 61, 20);
				contentEnabled.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent evt) {
						contentEnabledItemStateChanged(evt);
					}
				});
			}
			{
				this.add(Box.createHorizontalStrut(5));
			}
			{
				contentTextField = new JTextField();
				this.add(contentTextField);
				contentTextField.setBounds(66, 0, 328, 23);
				contentTextField.setName("contentTextField");
				contentTextField.setPreferredSize(new java.awt.Dimension(329,
						20));
				contentTextField.setVisible(false);
			}
			Application.getInstance().getContext().getResourceMap(getClass())
					.injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setLabel(String text) {
		contentEnabled.setText(text);
	}

	public String getLabel() {
		return contentEnabled.getText();
	}

	public Boolean isContentEnabled() {
		return contentEnabled.isSelected();
	}

	private void contentEnabledItemStateChanged(ItemEvent evt) {
		contentTextField.setVisible(contentEnabled.isSelected());
		contentTextField.setEnabled(contentEnabled.isSelected());
		this.validate();
	}

	public void setContentEnabled(Boolean value) {
		contentEnabled.setSelected(value);
		contentTextField.setEnabled(value);
		contentTextField.setVisible(value);
		this.validate();
	}

	public Boolean getContentEnabled() {
		return contentEnabled.isSelected();
	}

	public String getContentText() {
		if (getContentEnabled()) {
			return contentTextField.getText();
		}
		return null;
	}

	public void setContentText(String text) {
		if (text != null) {
			setContentEnabled(true);
			contentTextField.setText(text);
		}
	}

	public void setAdditionalContentGuiWrapper(
			AdditionalContentGuiWrapper additionalContent,
			AdditionalContentPositionType position) {
		this.additionalContent = additionalContent;
		this.position = position;
		if (position == AdditionalContentPositionType.CONTENT_BEFORE) {
			setContentText(additionalContent.getContentBefore());
		} else if (position == AdditionalContentPositionType.CONTENT_AFTER) {
			setContentText(additionalContent.getContentAfter());
		} else if (position == AdditionalContentPositionType.CONTENT_FIRST) {
			setContentText(additionalContent.getContentFirst());
		} else if (position == AdditionalContentPositionType.CONTENT_LAST) {
			setContentText(additionalContent.getContentLast());
		} else if (position == AdditionalContentPositionType.CONTENT_NO_VALUE) {
			setContentText(additionalContent.getContentNoValue());
		}
	}

	public AdditionalContentGuiWrapper getAdditionalContentGuiWrapper() {
		if (this.additionalContent != null || this.position != null) {
			if (position == AdditionalContentPositionType.CONTENT_BEFORE) {
				additionalContent.setContentBefore(getContentText());
			} else if (position == AdditionalContentPositionType.CONTENT_AFTER) {
				additionalContent.setContentAfter(getContentText());
			} else if (position == AdditionalContentPositionType.CONTENT_FIRST) {
				additionalContent.setContentFirst(getContentText());
			} else if (position == AdditionalContentPositionType.CONTENT_LAST) {
				additionalContent.setContentLast(getContentText());
			} else if (position == AdditionalContentPositionType.CONTENT_NO_VALUE) {
				additionalContent.setContentNoValue(getContentText());
			}

			return additionalContent;
		}

		return null;
	}

	public void setStyleType(StyleType styleType) {
		this.styleType = styleType;
	}

	public void setStyleGuiWrapper(StyleGuiWrapper style) {
		if (style != null) {
			this.style = style;
			setContentText(style.getValue());
		}
	}

	public StyleGuiWrapper getStyleGuiWrapper() {
		if (style == null && styleType != null) {
			if (!getContentEnabled() || getContentText().equals("")) {
				return null;
			}
			style = new StyleGuiWrapper(styleType);
		}

		style.setValueType(CssValueType.CLASS);
		style.setValue(getContentText());

		return style;
	}
}
