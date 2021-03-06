/*
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.common.reposconf;

import cz.muni.fi.fresneleditor.common.reposconf.DataManipulationJPanel.DataManipulationType;
import cz.muni.fi.fresneleditor.common.utils.GuiUtils;

/**
 * Simple panel that is a entry point for importing/exporting/deleting data
 * from/to a repository defined inside Fresnel Editor application.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz), Miroslav Warchil
 *         (warmir@mail.muni.cz)
 */
public class RepositoryDataManagerJPanel extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final RepositoriesConfigurationJPanel reposConfJPanel;

	/**
	 * Creates new form RepositoryDataManagerJPanel
	 */
	public RepositoryDataManagerJPanel() {
		initComponents();
		// dummy constructor for NetBeans
		reposConfJPanel = null;
	}

	public RepositoryDataManagerJPanel(
			RepositoriesConfigurationJPanel repositoriesConfigurationJPanel) {

		this.reposConfJPanel = repositoriesConfigurationJPanel;
		initComponents();
		setEnabledIncludingChildren(false);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		importButton = new javax.swing.JButton();
		exportButton = new javax.swing.JButton();
		editButton = new javax.swing.JButton();
		btnQuery = new javax.swing.JButton();

		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance().getContext()
				.getResourceMap(RepositoryDataManagerJPanel.class);
		setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap
				.getString("Form.border.title"))); // NOI18N
		setName("Form"); // NOI18N

		importButton.setFont(resourceMap.getFont("importButton.font")); // NOI18N
		importButton.setText(resourceMap.getString("importButton.text")); // NOI18N
		importButton.setName("importButton"); // NOI18N
		importButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				importButtonActionPerformed(evt);
			}
		});

		exportButton.setText(resourceMap.getString("exportButton.text")); // NOI18N
		exportButton.setName("exportButton"); // NOI18N
		exportButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exportButtonActionPerformed(evt);
			}
		});

		editButton.setText(resourceMap.getString("editButton.text")); // NOI18N
		editButton.setName("editButton"); // NOI18N
		editButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editButtonActionPerformed(evt);
			}
		});

		btnQuery.setText(resourceMap.getString("btnQuery.text")); // NOI18N
		btnQuery.setName("btnQuery"); // NOI18N
		btnQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnQueryActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(importButton)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(exportButton)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(editButton)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(btnQuery)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { btnQuery, editButton, exportButton,
						importButton });

		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(importButton)
												.addComponent(exportButton)
												.addComponent(editButton)
												.addComponent(btnQuery))
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
	}// </editor-fold>//GEN-END:initComponents

	private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_importButtonActionPerformed
		importButtonActionPerf();
	}// GEN-LAST:event_importButtonActionPerformed

	private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exportButtonActionPerformed
		showDataManipulationDialog(DataManipulationType.EXPORT);
	}// GEN-LAST:event_exportButtonActionPerformed

	private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_editButtonActionPerformed
		showEditDialog();
	}// GEN-LAST:event_editButtonActionPerformed

	private void btnQueryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnQueryActionPerformed
		showQueryDialog();
	}// GEN-LAST:event_btnQueryActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnQuery;
	private javax.swing.JButton editButton;
	private javax.swing.JButton exportButton;
	private javax.swing.JButton importButton;

	// End of variables declaration//GEN-END:variables

	private void importButtonActionPerf() {
		showDataManipulationDialog(DataManipulationType.IMPORT);
	}

	public void setEnabledIncludingChildren(boolean enabled) {
		super.setEnabled(enabled);
		// deleteButton.setEnabled(enabled);
		exportButton.setEnabled(enabled);
		importButton.setEnabled(enabled);
	}

	private void showDataManipulationDialog(DataManipulationType type) {
		new DataManipulationJDialog(GuiUtils.getOwnerFrame(this), true,
				reposConfJPanel.getSelectedRepository(), type).setVisible(true);
	}

	private void showEditDialog() {
		new EditRepositoryDialog(GuiUtils.getOwnerFrame(this), true,
				reposConfJPanel.getSelectedRepository()).setVisible(true);
	}

	private void showQueryDialog() {
		new QueryRepositoryJDialog(GuiUtils.getOwnerFrame(this), true,
				reposConfJPanel.getSelectedRepository()).setVisible(true);
	}
}
