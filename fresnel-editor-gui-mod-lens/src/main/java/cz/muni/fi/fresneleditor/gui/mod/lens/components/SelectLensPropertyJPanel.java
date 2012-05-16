/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.lens.components;

import java.util.ArrayList;
import java.util.List;

import org.openrdf.model.Value;
import org.springframework.util.Assert;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.data.SelectorType;
import cz.muni.fi.fresneleditor.common.guisupport.ExtendedDefaultComboBM;
import cz.muni.fi.fresneleditor.common.utils.FresnelUtils;
import cz.muni.fi.fresneleditor.gui.mod.lens.PropertyVisibilityWrapper;
import cz.muni.fi.fresneleditor.gui.mod.lens.model.LensSelector;
import fr.inria.jfresnel.Constants;

/**
 * Panel that allows to set a property URI to {@link PropertyVisibilityWrapper}
 * object {@link PropertyVisibilityWrapper#setFresnelPropertyValueURI(String)}
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class SelectLensPropertyJPanel extends javax.swing.JPanel {

	private final EditShowPropertyJDialog editShowPropertyDialog;

	/**
	 * Creates new form SelectLensPropertyJPanel
	 * 
	 * @param withAllProperties
	 */
	public SelectLensPropertyJPanel(boolean withAllProperties,
			EditShowPropertyJDialog editShowPropertyDialog) {
		initComponents();
		setAllPropertiesVisible(withAllProperties);
		Assert.notNull(editShowPropertyDialog);
		this.editShowPropertyDialog = editShowPropertyDialog;
	}

	/**
	 * Constructs a panel without fresnel:allProperties option.
	 */
	public SelectLensPropertyJPanel() {
		initComponents();
		setAllPropertiesVisible(false);
		editShowPropertyDialog = null;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		existingPropertyRadio = new javax.swing.JRadioButton();
		customPropertyText = new javax.swing.JTextField();
		customPropertyRadio = new javax.swing.JRadioButton();
		allPropertiesRadio = new javax.swing.JRadioButton();
		existingPropertyCombo = new cz.muni.fi.fresneleditor.common.guisupport.components.ValueJComboBox();

		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance().getContext()
				.getResourceMap(SelectLensPropertyJPanel.class);
		setBorder(javax.swing.BorderFactory
				.createTitledBorder(javax.swing.BorderFactory
						.createTitledBorder(resourceMap
								.getString("Form.border.border.title")))); // NOI18N

		buttonGroup1.add(existingPropertyRadio);
		existingPropertyRadio.setFont(resourceMap
				.getFont("customPropertyRadio.font")); // NOI18N
		existingPropertyRadio.setText(resourceMap
				.getString("existingPropertyRadio.text")); // NOI18N
		existingPropertyRadio.setName("existingPropertyRadio"); // NOI18N
		existingPropertyRadio.setPreferredSize(new java.awt.Dimension(111, 20));
		existingPropertyRadio
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						existingPropertyRadioActionPerformed(evt);
					}
				});

		customPropertyText.setEnabled(false);
		customPropertyText.setName("customPropertyText"); // NOI18N

		buttonGroup1.add(customPropertyRadio);
		customPropertyRadio.setFont(resourceMap
				.getFont("customPropertyRadio.font")); // NOI18N
		customPropertyRadio.setSelected(true);
		customPropertyRadio.setText(resourceMap
				.getString("customPropertyRadio.text")); // NOI18N
		customPropertyRadio.setName("customPropertyRadio"); // NOI18N
		customPropertyRadio.setPreferredSize(new java.awt.Dimension(109, 20));
		customPropertyRadio
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						customPropertyRadioActionPerformed(evt);
					}
				});

		buttonGroup1.add(allPropertiesRadio);
		allPropertiesRadio.setFont(resourceMap
				.getFont("customPropertyRadio.font")); // NOI18N
		allPropertiesRadio.setText(resourceMap
				.getString("allPropertiesRadio.text")); // NOI18N
		allPropertiesRadio.setName("allPropertiesRadio"); // NOI18N
		allPropertiesRadio.setPreferredSize(new java.awt.Dimension(121, 20));
		allPropertiesRadio
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						allPropertiesRadioActionPerformed(evt);
					}
				});

		existingPropertyCombo.setFont(resourceMap
				.getFont("existingPropertyCombo.font")); // NOI18N
		existingPropertyCombo.setName("existingPropertyCombo"); // NOI18N
		existingPropertyCombo.setPreferredSize(new java.awt.Dimension(30, 20));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														allPropertiesRadio,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						customPropertyRadio,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						147,
																						javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						existingPropertyRadio,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						143,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						existingPropertyCombo,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						344,
																						javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						customPropertyText,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						344,
																						javax.swing.GroupLayout.PREFERRED_SIZE))))
								.addContainerGap(65, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														existingPropertyCombo,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														existingPropertyRadio,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		customPropertyRadio,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		allPropertiesRadio,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(
														customPropertyText,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))));
	}// </editor-fold>                        

	private void existingPropertyRadioActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_existingPropertyRadioActionPerformed
		propertySourceChanged();
	}// GEN-LAST:event_existingPropertyRadioActionPerformed

	private void customPropertyRadioActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_customPropertyRadioActionPerformed
		propertySourceChanged();
	}// GEN-LAST:event_customPropertyRadioActionPerformed

	private void allPropertiesRadioActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_allPropertiesRadioActionPerformed
		propertySourceChanged();
	}// GEN-LAST:event_allPropertiesRadioActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JRadioButton allPropertiesRadio;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JRadioButton customPropertyRadio;
	private javax.swing.JTextField customPropertyText;
	private cz.muni.fi.fresneleditor.common.guisupport.components.ValueJComboBox existingPropertyCombo;
	private javax.swing.JRadioButton existingPropertyRadio;
	// End of variables declaration//GEN-END:variables
	private List<String> usedShowProperties;
	private List<String> usedHideProperties;

	/**
	 * Set up the panel based on propertyURI value.
	 * 
	 * @param propertyURI
	 *            based on which the panel should be setup
	 */
	public void loadPropertyURI(String propertyURI) {
		customPropertyRadio.setSelected(true);

		if (propertyURI == null) {
			customPropertyText.setText(null);
			customPropertyText.setEnabled(true);
			return;
		}

		existingPropertyCombo.setSelectedItem(propertyURI);
		if (propertyURI.equals(existingPropertyCombo.getSelectedItem())) {
			existingPropertyRadio.setSelected(true);
			existingPropertyCombo.setEnabled(true);
		} else if (propertyURI.equals(Constants._allProperties)) {
			allPropertiesRadio.setSelected(true);
			allPropertiesRadio.setEnabled(true);
		} else {
			customPropertyText.setText(propertyURI);
			customPropertyText.setEnabled(true);
		}
	}

	/**
	 * Returns a property URI specified by this panel.
	 * 
	 * @return property URI specified by this panel
	 */
	public String savePropertyURI() {
		String propertyValueURI = null;
		if (customPropertyRadio.isSelected()) {
			propertyValueURI = customPropertyText.getText();
		} else if (allPropertiesRadio.isSelected()) {
			propertyValueURI = Constants._allProperties;
		} else {
			throw new UnsupportedOperationException("not implemented yet");
		}

		propertyValueURI = FresnelUtils.replacePrefix(propertyValueURI);

		return propertyValueURI;
	}

	/**
	 * Returns an error message if the validation fails (the uri defined by this
	 * panel is invalid for some reason). <br>
	 * Returns null if the validation is OK.
	 * 
	 * @return an error message if validation fails or <br>
	 *         null in case the validation succeeds
	 */
	public String validateInput() {
		String propertyUri;
		if (customPropertyRadio.isSelected()) {
			// custom property
			propertyUri = customPropertyText.getText();
//TODO: nodrock fix this
			String resultMessage = null;
//                                FresnelUtils.validateResourceUri(
//					propertyUri, ContextHolder.getInstance()
//							.getFresnelRepositoryDao());
			if (resultMessage != null) {
				return "Custom property URI '" + propertyUri
						+ "' does not have any URI specified.<br>"
						+ resultMessage;
			}
		} else if (allPropertiesRadio.isSelected()) {
			// fresnel:allProperties

			propertyUri = Constants._allProperties;
		} else {
			// existing property

			throw new UnsupportedOperationException("not implemented yet");
		}
		return null;
	}

	/**
	 * Fills the existing resources combo with properties available for resource
	 * specified by given selector.
	 * 
	 * @param selector
	 *            that specifies the resource whose properties should be loaded <br>
	 *            can be null
	 * @param usedShowProperties
	 *            list of property URIs which are invalid for this panel
	 * @param usedHideProperties
	 *            list of property URIs which are invalid for this panel
	 */
	// Currently only simple selectors are supported
	public void setUp(LensSelector selector, List<String> usedShowProperties,
			List<String> usedHideProperties) {
		this.usedShowProperties = usedShowProperties;
		this.usedHideProperties = usedHideProperties;
		// public void loadPossibleExistingProperties(LensSelector selector) {
		// TODO add support for not-simple selectors
		List<Value> properties = new ArrayList<Value>();
		if (selector != null && SelectorType.SIMPLE.equals(selector.getType())) {
			String uri = selector.asString();
			if (selector.getDomain().isInstanceDomain()) {
				properties = ContextHolder.getInstance().getDataRepositoryDao()
						.getPropertiesForInstance(uri);
			} else {
				properties = ContextHolder.getInstance().getDataRepositoryDao()
						.getDirectClassProperties(uri);
			}
		}

		List<Value> notUsedProperties = new ArrayList<Value>();
		for (Value property : properties) {
			if (isUsed(property.stringValue())) {
				notUsedProperties.add(property);
			}
		}

		existingPropertyCombo.setModel(new ExtendedDefaultComboBM<Value>(
				notUsedProperties));
		existingPropertyCombo.setEnabled(!notUsedProperties.isEmpty());
		existingPropertyRadio.setEnabled(!notUsedProperties.isEmpty());

		allPropertiesRadio.setEnabled(!isUsed(Constants._allProperties));

		if (customPropertyRadio.isSelected()) {
			customPropertyText.setEnabled(true);
		}
	}

	private void propertySourceChanged() {
		boolean customSelected = customPropertyRadio.isSelected();
		customPropertyText.setEnabled(customSelected);

		boolean existingSelected = existingPropertyRadio.isSelected();
		existingPropertyCombo.setEnabled(existingSelected);

		boolean allPropertiesSelected = allPropertiesRadio.isSelected();
		if (editShowPropertyDialog != null) {
			editShowPropertyDialog
					.enablePropertyConfigurationPanel(!allPropertiesSelected);
		}
	}

	/**
	 * Disables the allPropertiesRadio selector.
	 * 
	 * @param visible
	 */
	private void setAllPropertiesVisible(boolean visible) {
		allPropertiesRadio.setVisible(visible);
		allPropertiesRadio.setEnabled(visible);
		if (!visible) {
			// if it is not visible cannot be selected
			allPropertiesRadio.setSelected(false);
		}
	}

	/**
	 * Returns true if the given property is already used.
	 * 
	 * @param propertyUri
	 * @return true if the given property is already used
	 */
	private boolean isUsed(String propertyUri) {
		return isElementIn(usedShowProperties, propertyUri)
				|| isElementIn(usedHideProperties, propertyUri);
	}

	/**
	 * Returns true if the newVisibility is already element in propertiesList.
	 * 
	 * @param propertiesList
	 * @param newPropertyUri
	 * @return true if the newVisibility is already element in propertiesList
	 */
	public static boolean isElementIn(List<String> propertiesList,
			String newPropertyUri) {
		for (String visibility : propertiesList) {
			if (FresnelUtils.equalsUris(visibility, newPropertyUri)) {
				return true;
			}
		}
		return false;
	}

}
