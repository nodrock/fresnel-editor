/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.lens2;

import cz.muni.fi.fresneleditor.gui.mod.lens2.treemodel.LensItemNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.URIImpl;
import org.springframework.util.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import cz.muni.fi.fresneleditor.common.AppEventsManager;
import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.data.SelectorType;
import cz.muni.fi.fresneleditor.common.guisupport.IContextMenu;
import cz.muni.fi.fresneleditor.common.guisupport.IEditable;
import cz.muni.fi.fresneleditor.common.guisupport.MessageDialog;
import cz.muni.fi.fresneleditor.common.guisupport.dialogs.PreviewDialog;
import cz.muni.fi.fresneleditor.common.utils.FresnelUtils;
import cz.muni.fi.fresneleditor.common.utils.GuiUtils;
import cz.muni.fi.fresneleditor.gui.mod.lens2.components.EditShowPropertyJDialog;
import cz.muni.fi.fresneleditor.gui.mod.lens2.components.LensPreviewDialog;
import cz.muni.fi.fresneleditor.gui.mod.lens2.components.SelectLensPropertyJPanel;
import cz.muni.fi.fresneleditor.gui.mod.lens2.model.LensSelector;
import cz.muni.fi.fresneleditor.gui.mod.lens2.utils.LensModelManager;
import fr.inria.jfresnel.Group;
import fr.inria.jfresnel.Lens;
import fr.inria.jfresnel.sesame.SesameLens;

/**
 * Main JPanel providing Fresnel Lens functionality to the user interface
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class LensJPanel2 extends javax.swing.JPanel implements
		ITabComponent<Lens>, IEditable {

	/**
	 * Generated by Eclipse.
	 */
	private static final long serialVersionUID = 7664903099015986941L;
	private JScrollPane representingScrollPane;
	private final LensItemNode lensItemNode;
	private Lens lens;
	private final LensModel initialLensModel;
	private boolean createNew;
	private LensSelector lensSelector;

	/**
	 * Creates new pane for editing/creation of new lens.
	 * 
	 * @param lensUri
	 *            if null than new lens can be created via this pane <br>
	 *            if not null than the specified lens can be edited
	 * @param lensItemNode
	 *            the node in the project tree that is represented by this pane
	 *            component
	 */
	public LensJPanel2(Lens lens, LensItemNode lensItemNode) {

		initComponents();
		this.lensItemNode = lensItemNode;
		this.lens = lens;
		createNew = lens == null;

		if (createNew) {
			this.initialLensModel = null;
		} else {
			this.initialLensModel = new LensModel(lens);
		}
		loadModel(initialLensModel);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		new javax.swing.ButtonGroup();
		commentJPanel = new javax.swing.JPanel();
		commentJScrollPane = new javax.swing.JScrollPane();
		commentTextArea = new javax.swing.JTextArea();
		groupsJPanel = new javax.swing.JPanel();
		groupsJScrollPane = new javax.swing.JScrollPane();
		associatedGroupsList = new cz.muni.fi.fresneleditor.common.guisupport.components.UrisJList();
		saveBtn = new javax.swing.JButton();
		deleteBtn = new javax.swing.JButton();
		reloadBtn = new javax.swing.JButton();
		closeBtn = new javax.swing.JButton();
		previewBtn = new javax.swing.JButton();
		nameLabel = new javax.swing.JPanel();
		lensNameTextField = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		propertiesPanel1 = new cz.muni.fi.fresneleditor.gui.mod.lens2.components.PropertiesPanel();
		propertiesPanel1.orderPropertiesPanel1
				.setEditButtonActionlistener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						editShowPropertyButtonClicked();
					}
				});
		purposePanel1 = new cz.muni.fi.fresneleditor.gui.mod.lens2.components.PurposePanel();
		domainSelector1 = new cz.muni.fi.fresneleditor.gui.mod.lens2.components.DomainSelector();
		domainSelector1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Take selector returned from domain selector dialog and
				// update the panels state based on it
				loadLensSelector(domainSelector1.getDomain());
			}
		});

		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance().getContext().getResourceMap(LensJPanel2.class);
		setBackground(resourceMap.getColor("Form.background")); // NOI18N
		setPreferredSize(new java.awt.Dimension(800, 494));

		commentJPanel.setBackground(new java.awt.Color(217, 228, 255));
		commentJPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder(resourceMap
						.getString("commentJPanel.border.title"))); // NOI18N
		commentJPanel.setName("commentJPanel"); // NOI18N

		commentJScrollPane.setName("commentJScrollPane"); // NOI18N

		commentTextArea.setColumns(20);
		commentTextArea.setFont(resourceMap.getFont("commentTextArea.font")); // NOI18N
		commentTextArea.setLineWrap(true);
		commentTextArea.setRows(5);
		commentTextArea.setName("commentTextArea"); // NOI18N
		commentJScrollPane.setViewportView(commentTextArea);

		javax.swing.GroupLayout commentJPanelLayout = new javax.swing.GroupLayout(
				commentJPanel);
		commentJPanel.setLayout(commentJPanelLayout);
		commentJPanelLayout
				.setHorizontalGroup(commentJPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								commentJPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												commentJScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												171, Short.MAX_VALUE)
										.addContainerGap()));
		commentJPanelLayout
				.setVerticalGroup(commentJPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								commentJPanelLayout
										.createSequentialGroup()
										.addComponent(
												commentJScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												101, Short.MAX_VALUE)
										.addContainerGap()));

		groupsJPanel.setBackground(resourceMap
				.getColor("groupsJPanel.background")); // NOI18N
		groupsJPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder(resourceMap
						.getString("groupsJPanel.border.title"))); // NOI18N
		groupsJPanel.setName("groupsJPanel"); // NOI18N
		groupsJPanel.setPreferredSize(new java.awt.Dimension(303, 303));

		groupsJScrollPane.setName("groupsJScrollPane"); // NOI18N

		associatedGroupsList
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		associatedGroupsList.setFocusable(false);
		associatedGroupsList.setName("associatedGroupsList"); // NOI18N
		associatedGroupsList.setSelectionBackground(new java.awt.Color(255,
				255, 255));
		associatedGroupsList
				.setSelectionForeground(new java.awt.Color(0, 0, 0));
		associatedGroupsList
				.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
					public void valueChanged(
							javax.swing.event.ListSelectionEvent evt) {
						associatedGroupsListValueChanged(evt);
					}
				});
		groupsJScrollPane.setViewportView(associatedGroupsList);

		javax.swing.GroupLayout groupsJPanelLayout = new javax.swing.GroupLayout(
				groupsJPanel);
		groupsJPanel.setLayout(groupsJPanelLayout);
		groupsJPanelLayout
				.setHorizontalGroup(groupsJPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								groupsJPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												groupsJScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												171, Short.MAX_VALUE)
										.addContainerGap()));
		groupsJPanelLayout
				.setVerticalGroup(groupsJPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								groupsJPanelLayout
										.createSequentialGroup()
										.addComponent(
												groupsJScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												173, Short.MAX_VALUE)
										.addContainerGap()));

		saveBtn.setText(resourceMap.getString("saveBtn.text")); // NOI18N
		saveBtn.setName("saveBtn"); // NOI18N
		saveBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveBtnActionPerformed(evt);
			}
		});

		deleteBtn.setText(resourceMap.getString("deleteBtn.text")); // NOI18N
		deleteBtn.setName("deleteBtn"); // NOI18N
		deleteBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteBtnActionPerformed(evt);
			}
		});

		reloadBtn.setText(resourceMap.getString("reloadBtn.text")); // NOI18N
		reloadBtn
				.setToolTipText(resourceMap.getString("reloadBtn.toolTipText")); // NOI18N
		reloadBtn.setName("reloadBtn"); // NOI18N
		reloadBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				reloadBtnActionPerformed(evt);
			}
		});

		closeBtn.setText(resourceMap.getString("closeBtn.text")); // NOI18N
		closeBtn.setName("closeBtn"); // NOI18N
		closeBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeBtnActionPerformed(evt);
			}
		});

		previewBtn.setText(resourceMap.getString("previewBtn.text")); // NOI18N
		previewBtn.setToolTipText(resourceMap
				.getString("previewBtn.toolTipText")); // NOI18N
		previewBtn.setName("previewBtn"); // NOI18N
		previewBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				previewBtnActionPerformed(evt);
			}
		});

		nameLabel.setBackground(new java.awt.Color(255, 241, 212));
		nameLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"Lens name",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				resourceMap.getFont("nameLabel.border.titleFont"))); // NOI18N
		nameLabel.setName("nameLabel"); // NOI18N

		lensNameTextField
				.setFont(resourceMap.getFont("lensNameTextField.font")); // NOI18N
		lensNameTextField.setText(resourceMap
				.getString("lensNameTextField.text")); // NOI18N
		lensNameTextField.setName("lensNameTextField"); // NOI18N

		jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
		jLabel1.setForeground(resourceMap.getColor("jLabel1.foreground")); // NOI18N
		jLabel1.setText("Example: http://example.org/custom.html#document"); // NOI18N
		jLabel1.setName("jLabel1"); // NOI18N

		javax.swing.GroupLayout nameLabelLayout = new javax.swing.GroupLayout(
				nameLabel);
		nameLabel.setLayout(nameLabelLayout);
		nameLabelLayout
				.setHorizontalGroup(nameLabelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								nameLabelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												nameLabelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jLabel1,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																266,
																Short.MAX_VALUE)
														.addGroup(
																nameLabelLayout
																		.createSequentialGroup()
																		.addComponent(
																				lensNameTextField,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				256,
																				Short.MAX_VALUE)
																		.addContainerGap()))));
		nameLabelLayout.setVerticalGroup(nameLabelLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				nameLabelLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(lensNameTextField,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(jLabel1)
						.addContainerGap(32, Short.MAX_VALUE)));

		propertiesPanel1.setName("propertiesPanel1"); // NOI18N

		purposePanel1.setName("purposePanel1"); // NOI18N

		domainSelector1.setName("domainSelector1"); // NOI18N

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		previewBtn)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		reloadBtn)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		saveBtn)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		deleteBtn)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		closeBtn))
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING)
																				.addGroup(
																						javax.swing.GroupLayout.Alignment.LEADING,
																						layout.createSequentialGroup()
																								.addComponent(
																										nameLabel,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										domainSelector1,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										238,
																										javax.swing.GroupLayout.PREFERRED_SIZE))
																				.addComponent(
																						propertiesPanel1,
																						javax.swing.GroupLayout.Alignment.LEADING,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						532,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				false)
																				.addComponent(
																						commentJPanel,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						groupsJPanel,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						203,
																						Short.MAX_VALUE)
																				.addComponent(
																						purposePanel1,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						203,
																						javax.swing.GroupLayout.PREFERRED_SIZE))))
								.addContainerGap()));

		layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { closeBtn, deleteBtn, reloadBtn,
						saveBtn });

		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		groupsJPanel,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		211,
																		Short.MAX_VALUE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		commentJPanel,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		purposePanel1,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				false)
																				.addComponent(
																						domainSelector1,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						124,
																						Short.MAX_VALUE)
																				.addComponent(
																						nameLabel,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		propertiesPanel1,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		328,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(previewBtn)
												.addComponent(reloadBtn)
												.addComponent(closeBtn)
												.addComponent(saveBtn)
												.addComponent(deleteBtn))
								.addGap(120, 120, 120)));
	}// </editor-fold>                        

	private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_saveBtnActionPerformed
		doSave();
	}// GEN-LAST:event_saveBtnActionPerformed

	private void associatedGroupsListValueChanged(
			javax.swing.event.ListSelectionEvent evt) {// GEN-FIRST:event_associatedGroupsListValueChanged
		// do nothing
	}// GEN-LAST:event_associatedGroupsListValueChanged

	private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deleteBtnActionPerformed
		doDelete();
	}// GEN-LAST:event_deleteBtnActionPerformed

	private void reloadBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_reloadBtnActionPerformed
		doReload();
	}// GEN-LAST:event_reloadBtnActionPerformed

	private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closeBtnActionPerformed
		closeButtonClicked();
	}// GEN-LAST:event_closeBtnActionPerformed

	private void previewBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_previewBtnActionPerformed
		// Export currently edited format to be displayed
		LensModelManager modelManager = new LensModelManager();
		Lens lens = modelManager.convertModel2JFresnel(saveModel());

		// Show preview dialog for setting preview parameters
		PreviewDialog previewDialog = new LensPreviewDialog(
				GuiUtils.getOwnerFrame(this), true, PreviewDialog.PREVIEW_LENS,
				lens, null);
		ResourceMap resourceMap = Application.getInstance().getContext()
				.getResourceMap(LensJPanel2.class);
		previewDialog.setSelectLabelText(resourceMap
				.getString("previewPanelText"));
		GuiUtils.centerOnScreen(previewDialog);
		previewDialog.setVisible(true);
	}// GEN-LAST:event_previewBtnActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private cz.muni.fi.fresneleditor.common.guisupport.components.UrisJList associatedGroupsList;
	private javax.swing.JButton closeBtn;
	private javax.swing.JPanel commentJPanel;
	private javax.swing.JScrollPane commentJScrollPane;
	private javax.swing.JTextArea commentTextArea;
	private javax.swing.JButton deleteBtn;
	private cz.muni.fi.fresneleditor.gui.mod.lens2.components.DomainSelector domainSelector1;
	private javax.swing.JPanel groupsJPanel;
	private javax.swing.JScrollPane groupsJScrollPane;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JTextField lensNameTextField;
	private javax.swing.JPanel nameLabel;
	private javax.swing.JButton previewBtn;
	private cz.muni.fi.fresneleditor.gui.mod.lens2.components.PropertiesPanel propertiesPanel1;
	private cz.muni.fi.fresneleditor.gui.mod.lens2.components.PurposePanel purposePanel1;
	private javax.swing.JButton reloadBtn;
	private javax.swing.JButton saveBtn;

	// End of variables declaration//GEN-END:variables

	/**
	 * Stores values set in Lens JPanel into lens model instance.
	 * 
	 * @return lens model instance holding information about given format
	 */
	private LensModel saveModel() {
		LensModel model = new LensModel(lensNameTextField.getText());

		// lens selector
		model.setSelector(lensSelector);

		// lens purpose
		model.setPurpose(purposePanel1.getPurpose());

		// lens comment
		String comment = commentTextArea.getText();
		if (StringUtils.hasText(comment)) {
			model.setComment(comment);
		}

		// hide properties
		model.setHideProperties(propertiesPanel1.getHideProperties());

		// show properties
		model.setShowProperties(propertiesPanel1.getShowProperties());

		// groups
		List<Group> associatedGroups = new ArrayList<Group>();
		// for (URI uri : associatedGroupsList.getElements()) {
		// associatedGroups.add(new Group(uri.stringValue(), ""));
		// }
		model.setGroups(associatedGroups);

		return model;
	}

	// private void hidePropertiesListClicked() {
	// boolean hasSelection = !hidePropertiesList.isSelectionEmpty();
	// removeHidePropertyBtn.setEnabled(hasSelection);
	// }

	// private void editSelectorButtonAP() {
	// final DomainSelectorDialog domainSelectorDialog = new
	// DomainSelectorDialog(lensSelector,
	// GuiUtils.getOwnerFrame(this), true);
	// domainSelectorDialog.setSaveActionListener(
	// new ActionListener() {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// // Take selector returned from domain selector dialog and
	// // update the panels state based on it
	// loadLensSelector(domainSelectorDialog.saveLensSelector());
	// }
	// });
	// domainSelectorDialog.setVisible(true);
	// }

	/**
	 * Updates the whole panel based on model.
	 * 
	 * @param actualLensModel
	 */
	private void loadModel(LensModel actualLensModel) {
		if (actualLensModel == null) {
			associatedGroupsList.setElements(new ArrayList<URI>());
			// hidePropertiesList.setElements(new
			// ArrayList<PropertyVisibilityWrapper>());
			// propertiesPanel1.propertySelectorPanel1.propertySelector.setElements(new
			// ArrayList<PropertyVisibilityWrapper>());
			// purposePanel1.setPurpose(Lens.PURPOSE_UNDEFINED);
			domainSelector1.reloadSelectors(null);
			lensNameTextField.setText(null);
		} else {
			List<URI> groupUris = new ArrayList<URI>();
			for (Group tempGroup : actualLensModel.getGroups()) {
				groupUris.add(new URIImpl(tempGroup.getURI()));
			}
			associatedGroupsList.setElements(groupUris);

			// hidePropertiesList.setElements(getCopyOfList(actualLensModel.getHideProperties()));
			// showPropertiesList.setElements(getCopyOfList(actualLensModel.getShowProperties()));
			purposePanel1.setPurpose(actualLensModel.getPurpose());
			lensNameTextField.setText(actualLensModel.getModelUri());

		}

		// comment
		String commentString = "";
		commentTextArea.setText(actualLensModel.getComment());

		// selector, domain
		LensSelector selector = actualLensModel != null ? actualLensModel
				.getSelector() : null;
		loadLensSelector(selector);
	}

	// private List<PropertyVisibilityWrapper>
	// getCopyOfList(List<PropertyVisibilityWrapper> originalList) {
	// ArrayList<PropertyVisibilityWrapper> arrayList = new
	// ArrayList<PropertyVisibilityWrapper>();
	// for (PropertyVisibilityWrapper cloneable : originalList) {
	// arrayList.add(cloneable.getCopy());
	// }
	// return arrayList;
	// }

	// private void removeShowPropertyButtonClicked() {
	// PropertyVisibilityWrapper selectedValue =
	// showPropertiesList.getSelectedValueCasted();
	// if (selectedValue != null) {
	// showPropertiesList.removeElement(selectedValue);
	// }
	// }

	private void loadLensSelector(LensSelector selector) {
		this.lensSelector = selector;
		if (selector == null)
			propertiesPanel1.setModel(new DefaultListModel());
		if (selector != null && selector.getType().equals(SelectorType.SIMPLE)) {

			if (!selector.equals(domainSelector1.getDomain()))
				domainSelector1.setDomain(selector);

			propertiesPanel1.setModel((DefaultListModel) LensModel
					.getProperties(new URIImpl(selector.asString()), !selector
							.getDomain().isInstanceDomain()));
			// propertiesPanel1.propertySelectorPanel1.propertySelector.setElements(getCopyOfList(initialLensModel.getShowProperties()));
			if (initialLensModel != null
					&& selector.asString().equals(
							initialLensModel.getSelectorAsString())) {
				propertiesPanel1.setShowProperties(initialLensModel
						.getShowProperties());
				propertiesPanel1.setHideProperties(initialLensModel
						.getHideProperties());
			}
		}
	}

	// private void addShowPropertyButtonClicked() {
	// final EditShowPropertyJDialog editShowPropertyJDialog =
	// new EditShowPropertyJDialog(lensSelector, getUsedShowProperties(),
	// getUsedHideProperties(), GuiUtils.getOwnerFrame(this), true);
	// editShowPropertyJDialog.setSaveActionListener(new ActionListener() {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// String validationResult = editShowPropertyJDialog.validateInput();
	// String propertyUri = editShowPropertyJDialog.getPropertyUri();
	// if (validationResult == null && isUsed(propertyUri)) {
	// validationResult = "The property '" + propertyUri +
	// "' is already used in one of show/hide properties list.";
	// }
	// if (validationResult == null) {
	// PropertyVisibilityWrapper visibility =
	// editShowPropertyJDialog.savePropertyVisibility();
	// showPropertiesList.addElement(visibility);
	// editShowPropertyJDialog.dispose();
	// } else {
	// new MessageDialog(GuiUtils.getOwnerFrame(LensJPanel2.this),
	// "Cannot save changes", validationResult).setVisible(true);
	// }
	//
	//
	// }
	// });
	// editShowPropertyJDialog.setVisible(true);
	// }

	private void editShowPropertyButtonClicked() {
		PropertyVisibilityWrapper propVisibility = propertiesPanel1.orderPropertiesPanel1
				.getSelectedValueCasted();
		if (propVisibility != null) {
			final String originalPropertyUri = propVisibility
					.getFresnelPropertyValueURI();
			final EditShowPropertyJDialog editShowPropertyJDialog = new EditShowPropertyJDialog(
					lensSelector, propVisibility, getUsedShowProperties(),
					getUsedHideProperties(), GuiUtils.getOwnerFrame(this), true);
			editShowPropertyJDialog.setSaveActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String propertyUri = editShowPropertyJDialog
							.getPropertyUri();

					String validationResult = editShowPropertyJDialog
							.validateInput();
					if (validationResult == null
							&& !originalPropertyUri.equals(propertyUri)
							&& isUsed(propertyUri)) {
						validationResult = "The property '"
								+ propertyUri
								+ "' is already used in one of show/hide properties list.";
					}
					if (validationResult == null) {
						editShowPropertyJDialog.savePropertyVisibility();
						editShowPropertyJDialog.dispose();
						// showPropertiesList.repaint() does not update the GUI
						// correctly
						// in case when setting complex property description
						List<PropertyVisibilityWrapper> elements = propertiesPanel1.orderPropertiesPanel1
								.getElements();
						propertiesPanel1.orderPropertiesPanel1
								.setElements(elements);
					} else {
						new MessageDialog(GuiUtils
								.getOwnerFrame(LensJPanel2.this),
								"Cannot save changes", validationResult)
								.setVisible(true);
					}
				}
			});
			editShowPropertyJDialog.setVisible(true);
		}
	}

	/**
	 * Returns true if the given property is already used.
	 * 
	 * @param propertyUri
	 * @return true if the given property is already used
	 */
	private boolean isUsed(String propertyUri) {
		return SelectLensPropertyJPanel.isElementIn(getUsedHideProperties(),
				propertyUri)
				|| SelectLensPropertyJPanel.isElementIn(
						getUsedShowProperties(), propertyUri);
	}

	// private void addHidePropertyButtonClicked() {
	// final AddHidePropertyDialog addHidePropertyDialog =
	// new AddHidePropertyDialog(lensSelector, getUsedShowProperties(),
	// getUsedHideProperties(), GuiUtils.getOwnerFrame(this), true);
	// addHidePropertyDialog.setOkActionListener(new ActionListener() {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// String propertyUri = addHidePropertyDialog.savePropertyURI();
	// String validationResult = addHidePropertyDialog.validateInput();
	//
	// if (validationResult == null && isUsed(propertyUri)) {
	// validationResult = "The property '" + propertyUri +
	// "' is already used in one of show/hide properties list.";
	// }
	// if (validationResult == null) {
	// PropertyVisibilityWrapper wrapper = new PropertyVisibilityWrapper();
	// wrapper.setFresnelPropertyValueURI(propertyUri);
	// // hidePropertiesList.addElement(wrapper);
	// addHidePropertyDialog.dispose();
	// } else {
	// new MessageDialog(GuiUtils.getOwnerFrame(LensJPanel2.this),
	// "Cannot save changes", validationResult).setVisible(true);
	// }
	// }
	// });
	// addHidePropertyDialog.setVisible(true);
	// }

	// private void removeHidePropertyButtonClicked() {
	// if (!hidePropertiesList.isSelectionEmpty()) {
	// hidePropertiesList.removeElement(hidePropertiesList.getSelectedValueCasted());
	// }
	// }

	@Override
	public Lens getItem() {
		return lens;
	}

	@Override
	public String getLabel() {
		return lensItemNode.toString();
	}

	@Override
	public JScrollPane getScrollPane() {
		if (representingScrollPane == null) {
			representingScrollPane = new LensJScrollPane(this);
		}
		return representingScrollPane;
	}

	/**
	 * Refreshes to initial state.
	 */
	public void doReload() {
		loadModel(initialLensModel);
	}

	@Override
	public void doDelete() {
		new MessageDialog(GuiUtils.getOwnerFrame(this), "Confirmation",
				"Do you really want to delete the lens '"
						+ initialLensModel.getModelUri() + "'?",
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ContextHolder.getInstance().getFresnelDocumentDao().deleteLens(lens.getURI());
						// fixme igor: remove the node and the tab if deleting
						// was succesfull
						AppEventsManager.getInstance()
								.fireRepositoryDataChanged(
										this,
										ContextHolder.getInstance()
												.getFresnelRepositoryName());
					}
				}).setVisible(true);
	}

	@Override
	public void doSave() {
		if (validateForm()) {
			LensModel newModel = saveModel();

			// fixme igor: update the initial model with the new model if the
			// save action is succesfull
			// probably best way would be to close/reopen the node/tab again..

			if (initialLensModel == null
					|| !newModel.getModelUri().equals(
							initialLensModel.getModelUri())) {
				lensItemNode.setUserObject(new URIImpl(newModel.getModelUri()));
			}

			LensModelManager modelManager = new LensModelManager();
                        
			if (createNew) {
				ContextHolder.getInstance().getFresnelDocumentDao().getLenses().add(modelManager.convertModel2JFresnel(newModel));
			} else {
				ContextHolder.getInstance().getFresnelDocumentDao().updateLens(lens.getURI(), modelManager.convertModel2JFresnel(newModel));
			}
			AppEventsManager.getInstance().fireRepositoryDataChanged(this,
					ContextHolder.getInstance().getFresnelRepositoryName());
		}
	}

	private boolean validateForm() {
            // TODO: 
		String lensName = lensNameTextField.getText();
		String validateMessage = null;
//                        FresnelUtils.validateResourceUri(lensName,
//				ContextHolder.getInstance().getFresnelRepositoryDao());
		if (validateMessage != null) {
			new MessageDialog(GuiUtils.getOwnerFrame(this), "Invalid lens URI",
					"The lens URI '" + lensName + "' is not valid:<br>"
							+ validateMessage).setVisible(true);
		}

		return validateMessage == null;
	}

	private class LensJScrollPane extends JScrollPane implements IContextMenu {

		/**
		 * ID generated by Eclipse.
		 */
		private static final long serialVersionUID = 6237927620665720799L;

		public LensJScrollPane(LensJPanel2 lensJPanel) {
			super(lensJPanel);
		}

		@Override
		public List<JMenuItem> getMenu() {
			return lensItemNode != null ? lensItemNode.getMenu() : Collections
					.<JMenuItem> emptyList();
		}
	}

	private List<String> getUsedShowProperties() {
		if (propertiesPanel1.getHideProperties() == null)
			return Lists.newArrayList("");
		return Lists.transform(propertiesPanel1.getShowProperties(),
				propVisibility2string);
	}

	private List<String> getUsedHideProperties() {
		if (propertiesPanel1.getHideProperties() == null)
			return Lists.newArrayList("");
		return Lists.transform(propertiesPanel1.getHideProperties(),
				propVisibility2string);
	}

	private static final Function<PropertyVisibilityWrapper, String> propVisibility2string = new Function<PropertyVisibilityWrapper, String>() {
		@Override
		public String apply(PropertyVisibilityWrapper from) {
			return from.getFresnelPropertyValueURI();
		}
	};

	// private void showPropertyDownButtonClicked() {
	// moveSelectedElement(showPropertiesList, false);
	// }
	//
	// private void showPropertyUpButtonClicked() {
	// moveSelectedElement(showPropertiesList, true);
	// }

	// private void moveSelectedElement(JList list, boolean moveUp) {
	// int index = list.getSelectedIndex();
	// if ((moveUp && index >= 1) || (!moveUp && (index + 1) <
	// list.getModel().getSize())) {
	// int targetIndex = index + (moveUp ? -1 : 1);
	// //we don't do anything for first and last item
	// Object selected = list.getModel().getElementAt(index);
	// boolean wasChecked = false;
	// if(list instanceof CheckBoxList){ //assuming only one item can be
	// selected
	// int [] checkedIndices =
	// ((CheckBoxList)list).getCheckBoxListSelectedIndices();
	// for(int i : checkedIndices){
	// if(i==index){
	// wasChecked = true;
	// }
	// }
	// }
	// DefaultListModel model = (DefaultListModel)list.getModel();
	// model.removeElementAt(index);
	// model.insertElementAt(selected, targetIndex);
	//
	// if(wasChecked){
	// int checkedCount =
	// ((CheckBoxList)list).getCheckBoxListSelectedIndices().length;
	// int [] newIndices =
	// Arrays.copyOf(((CheckBoxList)list).getCheckBoxListSelectedIndices(),
	// checkedCount + 1);
	// newIndices[checkedCount] = targetIndex;
	// ((CheckBoxList)list).setCheckBoxListSelectedIndices(newIndices);
	// }
	//
	// // refresh the selection
	// list.setSelectedIndex(targetIndex);
	// }
	// }

	private void closeButtonClicked() {
		lensItemNode.closeTab();
	}

}
