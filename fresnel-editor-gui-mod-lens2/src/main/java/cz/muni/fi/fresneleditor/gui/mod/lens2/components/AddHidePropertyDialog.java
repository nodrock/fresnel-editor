/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.lens2.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import cz.muni.fi.fresneleditor.common.utils.GuiUtils;
import cz.muni.fi.fresneleditor.gui.mod.lens2.model.LensSelector;

/**
 * @author Igor Zemsky
 */
public class AddHidePropertyDialog extends javax.swing.JDialog {

	private ActionListener okActionListener;

	/**
	 * Dialog for selecting a hide property.
	 * 
	 * @param selector
	 *            defines a resource whose properties can possibly be edited <br>
	 *            Can be null. In that case no existing properties are offered
	 *            to the user.
	 * @param parent
	 * @param modal
	 * @param usedShowProperties
	 * @param usedHideProperties
	 */
	public AddHidePropertyDialog(LensSelector selector,
			List<String> usedShowProperties, List<String> usedHideProperties,
			java.awt.Frame parent, boolean modal) {

		super(parent, modal);
		initComponents();
		selectLensPropertyJPanel.setUp(selector, usedShowProperties,
				usedHideProperties);
		GuiUtils.centerOnScreen(this);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		selectLensPropertyJPanel = new cz.muni.fi.fresneleditor.gui.mod.lens2.components.SelectLensPropertyJPanel();
		cancelButton = new javax.swing.JButton();
		addButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance().getContext()
				.getResourceMap(AddHidePropertyDialog.class);
		setTitle(resourceMap.getString("Form.title")); // NOI18N

		selectLensPropertyJPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder(resourceMap
						.getString("selectLensPropertyJPanel.border.title"))); // NOI18N
		selectLensPropertyJPanel.setName("selectLensPropertyJPanel"); // NOI18N

		cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
		cancelButton.setName("cancelButton"); // NOI18N
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});

		addButton.setText(resourceMap.getString("addButton.text")); // NOI18N
		addButton.setName("addButton"); // NOI18N
		addButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(462, 462, 462)
								.addComponent(cancelButton)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(addButton)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(selectLensPropertyJPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { addButton, cancelButton });

		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(selectLensPropertyJPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(cancelButton)
												.addComponent(addButton))
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
		dispose();
	}// GEN-LAST:event_cancelButtonActionPerformed

	private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_addButtonActionPerformed
		okButtonClicked(evt);
	}// GEN-LAST:event_addButtonActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton addButton;
	private javax.swing.JButton cancelButton;
	private cz.muni.fi.fresneleditor.gui.mod.lens2.components.SelectLensPropertyJPanel selectLensPropertyJPanel;

	// End of variables declaration//GEN-END:variables

	public void setOkActionListener(ActionListener okActionListener) {
		this.okActionListener = okActionListener;
	}

	private void okButtonClicked(ActionEvent e) {
		if (okActionListener != null) {
			okActionListener.actionPerformed(e);
		}
	}

	/**
	 * Returns the URI specified by this dialog.
	 * 
	 * @return the URI specified by this dialog
	 */
	public String savePropertyURI() {
		return selectLensPropertyJPanel.savePropertyURI();
	}

	/**
	 * @see SelectLensPropertyJPanel#validateInput()
	 */
	public String validateInput() {
		return selectLensPropertyJPanel.validateInput();
	}

}
