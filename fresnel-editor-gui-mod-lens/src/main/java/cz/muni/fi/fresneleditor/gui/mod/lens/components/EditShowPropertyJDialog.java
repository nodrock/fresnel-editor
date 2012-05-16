/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EditShowPropertyJDialog.java
 *
 * Created on May 8, 2009, 10:39:47 AM
 */

package cz.muni.fi.fresneleditor.gui.mod.lens.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import cz.muni.fi.fresneleditor.common.utils.GuiUtils;
import cz.muni.fi.fresneleditor.gui.mod.lens.PropertyVisibilityWrapper;
import cz.muni.fi.fresneleditor.gui.mod.lens.model.LensSelector;
import fr.inria.jfresnel.Constants;
import fr.inria.jfresnel.visibility.AllPropertiesVisibility;
import fr.inria.jfresnel.visibility.BasicVisibility;
import fr.inria.jfresnel.visibility.MPVisibility;
import fr.inria.jfresnel.visibility.PropertyDescriptionProperties;
import java.util.ArrayList;
import org.openrdf.model.URI;

/**
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class EditShowPropertyJDialog extends javax.swing.JDialog {

	private PropertyVisibilityWrapper propertyVisibility;
	private String originalPropertyVisibilityURI;

	/**
	 * Constructs edit dialog for creating new show property.
	 * 
	 * @param selector
	 *            defines a resource whose properties can possibly be edited <br>
	 *            Can be null. In that case no existing properties are offered
	 *            to the user.
	 * @param parent
	 * @param modal
	 */
	public EditShowPropertyJDialog(LensSelector selector,
			List<String> usedShowProperties, List<String> usedHideProperties,
			java.awt.Frame parent, boolean modal) {
		this(selector, null, usedShowProperties, usedHideProperties, parent,
				modal);
	}

	/**
	 * Constructs an edit dialog for editing existing show property.
	 * 
	 * @param selector
	 *            defines a resource whose properties can possibly be edited <br>
	 *            Can be null. In that case no existing properties are offered
	 *            to the user.
	 * @param propertyVisibility
	 *            visibility to edit
	 * @param parent
	 * @param modal
	 */
	public EditShowPropertyJDialog(LensSelector selector,
			PropertyVisibilityWrapper propertyVisibility,
			List<String> usedShowProperties, List<String> usedHideProperties,
			java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		this.propertyVisibility = propertyVisibility;
		this.originalPropertyVisibilityURI = propertyVisibility == null ? null
				: propertyVisibility.getFresnelPropertyValueURI();
		initComponents();
		selectLensPropertyJPanel1.setUp(selector, usedShowProperties,
				usedHideProperties);
		loadPropertyVisibility();
		GuiUtils.centerOnScreen(this);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		propertyConfigurationPanel1 = new cz.muni.fi.fresneleditor.gui.mod.lens.components.PropertyConfigurationPanel();
		saveButton = new javax.swing.JButton();
		cancelButton = new javax.swing.JButton();
		selectLensPropertyJPanel1 = new cz.muni.fi.fresneleditor.gui.mod.lens.components.SelectLensPropertyJPanel(
				true, this);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		propertyConfigurationPanel1.setName("propertyConfigurationPanel1"); // NOI18N

		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance().getContext()
				.getResourceMap(EditShowPropertyJDialog.class);
		saveButton.setText(resourceMap.getString("saveButton.text")); // NOI18N
		saveButton.setName("saveButton"); // NOI18N
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveButtonActionPerformed(evt);
			}
		});

		cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
		cancelButton.setName("cancelButton"); // NOI18N
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});

		selectLensPropertyJPanel1.setName("selectLensPropertyJPanel1"); // NOI18N

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		cancelButton)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		saveButton))
												.addGroup(
														layout.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																false)
																.addComponent(
																		selectLensPropertyJPanel1,
																		javax.swing.GroupLayout.Alignment.LEADING,
																		0,
																		0,
																		Short.MAX_VALUE)
																.addComponent(
																		propertyConfigurationPanel1,
																		javax.swing.GroupLayout.Alignment.LEADING,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		564,
																		Short.MAX_VALUE)))
								.addContainerGap()));

		layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { cancelButton, saveButton });

		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(selectLensPropertyJPanel1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(propertyConfigurationPanel1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(9, 9, 9)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(cancelButton)
												.addComponent(saveButton))
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pack();
	}// </editor-fold>                        

	private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_saveButtonActionPerformed
		saveButtonClicked(evt);
	}// GEN-LAST:event_saveButtonActionPerformed

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
		dispose();
	}// GEN-LAST:event_cancelButtonActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton cancelButton;
	private cz.muni.fi.fresneleditor.gui.mod.lens.components.PropertyConfigurationPanel propertyConfigurationPanel1;
	private javax.swing.JButton saveButton;
	private cz.muni.fi.fresneleditor.gui.mod.lens.components.SelectLensPropertyJPanel selectLensPropertyJPanel1;
	// End of variables declaration//GEN-END:variables
	private ActionListener saveActionListener;

	/**
	 * Returns property visibility updated based on the values in this dialog.
	 * The returned property visibility is new instance in case a new visibility
	 * property was created or the same property which was passed to this dialog
	 * in case of editing a property visibility.
	 * 
	 * @return new property visibility object in case of creating new property
	 *         visibility or the very same property visibility instance which
	 *         was passed to this dialog in case of editing existing property
	 *         visibility
	 */
	public PropertyVisibilityWrapper savePropertyVisibility() {
                String uri = selectLensPropertyJPanel1.savePropertyURI();
            
		if (propertyVisibility == null && uri.equals(Constants._allProperties)) {
                    propertyVisibility = new PropertyVisibilityWrapper(new AllPropertiesVisibility());
		}else if(propertyVisibility == null){
                    propertyVisibility = new PropertyVisibilityWrapper(new BasicVisibility(uri));
                }
                	
		propertyConfigurationPanel1.savePropertyVisibility(propertyVisibility);
                if(propertyVisibility.isComplexPropertyDescription()){
                    PropertyDescriptionProperties propertyDescriptionProperties = new PropertyDescriptionProperties();
                    propertyDescriptionProperties.depth = propertyVisibility.getMaxDepth();
                    propertyDescriptionProperties.property = propertyVisibility.getFresnelPropertyValueURI();
                    propertyDescriptionProperties.use = propertyVisibility.getFresnelUseUri();
                    List<String> uris = new ArrayList<String>();
                    for(URI sublensUri : propertyVisibility.getSublensesURIs()){
                        uris.add(sublensUri.toString());
                    }
                    propertyDescriptionProperties.sublens = uris;
                    
                    propertyVisibility = new PropertyVisibilityWrapper(new MPVisibility(propertyDescriptionProperties));
                }

		return propertyVisibility;
	}

	private void loadPropertyVisibility() {
		String propertyURI = null;
		if (propertyVisibility == null) {
			setTitle("Add new property");
		} else {
			propertyURI = propertyVisibility.getFresnelPropertyValueURI();
			setTitle("Edit property " + propertyURI);
		}

		selectLensPropertyJPanel1.loadPropertyURI(propertyURI);
		propertyConfigurationPanel1.loadPropertyVisibility(propertyVisibility);
		enablePropertyConfigurationPanel(!Constants._allProperties
				.equals(propertyURI));
	}

	/**
	 * Sets an action listener which is executed after the dialog is closed by
	 * pressing Save button.
	 * 
	 * @param actionListener
	 */
	public void setSaveActionListener(ActionListener actionListener) {
		this.saveActionListener = actionListener;
	}

	private void saveButtonClicked(ActionEvent e) {
		if (saveActionListener != null) {
			saveActionListener.actionPerformed(e);
		}
	}

	/**
	 * @see SelectLensPropertyJPanel#validateInput()
	 */
	public String validateInput() {
		return selectLensPropertyJPanel1.validateInput();
	}

	public void enablePropertyConfigurationPanel(boolean enabled) {
		propertyConfigurationPanel1.enableWithChildren(enabled);
	}

	public String getPropertyUri() {
		return selectLensPropertyJPanel1.savePropertyURI();
	}

	public String getOriginalPropertyUri() {
		return originalPropertyVisibilityURI;
	}

}
