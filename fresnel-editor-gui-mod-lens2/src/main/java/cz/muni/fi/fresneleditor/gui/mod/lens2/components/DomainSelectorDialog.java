/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.lens2.components;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JRadioButton;

import org.openrdf.model.Namespace;
import org.openrdf.model.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.data.SelectorType;
import cz.muni.fi.fresneleditor.common.guisupport.ExtendedDefaultComboBM;
import cz.muni.fi.fresneleditor.common.utils.FresnelUtils;
import cz.muni.fi.fresneleditor.common.utils.GuiUtils;
import cz.muni.fi.fresneleditor.gui.mod.lens2.model.LensSelector;
import cz.muni.fi.fresneleditor.gui.mod.lens2.model.LensSelector.LensDomain;
import cz.muni.fi.fresneleditor.model.DataRepositoryDao;

/**
 * Dialog for editing of lens domain selector.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * @version 21.3.2009
 */
public class DomainSelectorDialog extends javax.swing.JDialog {

	private static final Logger LOG = LoggerFactory
			.getLogger(DomainSelectorDialog.class);
	private ActionListener saveActionListener;

	/**
	 * 
	 * @param domainSelector
	 * @param parent
	 * @param modal
	 */
	public DomainSelectorDialog(LensSelector domainSelector,
			java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();

		// Load domain selector into dialog components
		loadDomainSelector(domainSelector);
		GuiUtils.centerOnScreen(this);
	}

	/**
	 * Fills dialog components with information about given domain selector.
	 * 
	 * @param domainSelector
	 */
	private void loadDomainSelector(LensSelector domainSelector) {

		if (domainSelector == null) {
			initDialog();
			return;
		}

		JRadioButton selectedDomainTypeRadio = classDomainRadio;
		LensDomain domain = domainSelector.getDomain();
		if (domain != null) {
			switch (domain) {
			case CLASS:
				selectedDomainTypeRadio = classDomainRadio;
				break;
			case INSTANCE:
				selectedDomainTypeRadio = instanceDomainRadio;
				break;
			default:
				LOG.error("Invalid domain type of given domain selector! ["
						+ domain + "]");
				throw new IndexOutOfBoundsException("domainType");
			}
		}

		JRadioButton selectedSelectorTypeRadio = simpleSelectorRadio;
		SelectorType type = domainSelector.getType();
		if (type != null) {
			switch (type) {
			case SIMPLE:
				selectedSelectorTypeRadio = simpleSelectorRadio;
				break;
			case FSL:
				selectedSelectorTypeRadio = fslQueryRadio;
				break;
			case SPARQL:
				selectedSelectorTypeRadio = sparqlQueryRadio;
				break;
			default:
				LOG.error("Invalid selector type of given domain selector!");
				throw new IndexOutOfBoundsException("selectorType");
			}
		}

		selectedDomainTypeRadio.setSelected(true);
		selectedSelectorTypeRadio.setSelected(true);
		selectorDomainTypeChanged();
		selectorTypeChanged();
		selectorValueTypeChanged();

		// init selector value selection
		String domainSelectorString = domainSelector.asString();
		if (SelectorType.SIMPLE.equals(type)) {
			// check if there is a existing resource available
			String selectorComboElement = getComboElement(domainSelectorString);
			if (selectorComboElement != null) {
				existingResourceCombo.setSelectedItem(selectorComboElement);
				existingResourceRadio.setSelected(true);
				existingResourceRadio.setEnabled(true);
				existingResourceCombo.setEnabled(true);
			} else {
				customSelectorText.setText(domainSelectorString);
			}
		} else {
			customSelectorText.setText(domainSelectorString);
		}
	}

	private String getComboElement(String value) {
		if (!StringUtils.hasText(value)) {
			return null;
		}
		for (int i = 0; i < existingResourceCombo.getModel().getSize(); i++) {
			String element = (String) existingResourceCombo.getModel()
					.getElementAt(i);
			if (FresnelUtils.equalsUris(element, value)) {
				return element;
			}
		}
		return null;
	}

	/**
	 * Initialises the dialog to default state based on empty lens selector.
	 */
	private void initDialog() {
		instanceDomainRadio.setSelected(true);
		simpleSelectorRadio.setSelected(true);
		customSelectorRadio.setSelected(true);
		customSelectorText.setText("");
	}

	/**
	 * Returns a {@link LensSelector} object representing the current state of
	 * this dialog.
	 * 
	 * @return
	 */
	public LensSelector saveLensSelector() {
		LensDomain domain = null;
		if (instanceDomainRadio.isSelected()) {
			domain = LensDomain.INSTANCE;
		} else if (classDomainRadio.isSelected()) {
			domain = LensDomain.CLASS;
		}

		SelectorType type = null;
		if (simpleSelectorRadio.isSelected()) {
			type = SelectorType.SIMPLE;
		} else if (fslQueryRadio.isSelected()) {
			type = SelectorType.FSL;
		} else if (sparqlQueryRadio.isSelected()) {
			type = SelectorType.SPARQL;
		}

		String value = null;
		if (customSelectorRadio.isSelected()) {
			// fixme igor: add validation
			value = customSelectorText.getText();
		} else {
			value = (String) existingResourceCombo.getSelectedItem();
		}

		if (SelectorType.SIMPLE.equals(type) && LensDomain.CLASS.equals(domain)) {
			value = FresnelUtils.replacePrefix(value);
		}

		return new LensSelector(domain, value, type);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		domainTypeBtnGroup = new javax.swing.ButtonGroup();
		selectorTypeBtnGroup = new javax.swing.ButtonGroup();
		buttonGroup1 = new javax.swing.ButtonGroup();
		instanceDomainRadio = new javax.swing.JRadioButton();
		classDomainRadio = new javax.swing.JRadioButton();
		domainTypeLabel = new javax.swing.JLabel();
		selectorTypeLabel = new javax.swing.JLabel();
		fslQueryRadio = new javax.swing.JRadioButton();
		sparqlQueryRadio = new javax.swing.JRadioButton();
		simpleSelectorRadio = new javax.swing.JRadioButton();
		saveBtn = new javax.swing.JButton();
		cancelBtn = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		existingResourceRadio = new javax.swing.JRadioButton();
		existingResourceCombo = new javax.swing.JComboBox();
		customSelectorRadio = new javax.swing.JRadioButton();
		selectorStringScrollPane = new javax.swing.JScrollPane();
		customSelectorText = new javax.swing.JTextArea();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		java.util.ResourceBundle bundle = java.util.ResourceBundle
				.getBundle("cz/muni/fi/fresneleditor/gui/mod/lens2/components/resources/DomainSelectorDialog"); // NOI18N
		setTitle(bundle.getString("Lens_domain_editor")); // NOI18N
		setModal(true);

		domainTypeBtnGroup.add(instanceDomainRadio);
		instanceDomainRadio.setSelected(true);
		instanceDomainRadio.setText(bundle.getString("instance_domain")); // NOI18N
		instanceDomainRadio.setName("instanceDomainRadio"); // NOI18N
		instanceDomainRadio
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						instanceDomainRadioActionPerformed(evt);
					}
				});

		domainTypeBtnGroup.add(classDomainRadio);
		classDomainRadio.setText(bundle.getString("class_domain")); // NOI18N
		classDomainRadio.setName("classDomainRadio"); // NOI18N
		classDomainRadio.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				classDomainRadioActionPerformed(evt);
			}
		});

		domainTypeLabel.setText(bundle.getString("Domain_type:")); // NOI18N
		domainTypeLabel.setName("domainTypeLabel"); // NOI18N

		selectorTypeLabel.setText(bundle.getString("Selector_type:")); // NOI18N
		selectorTypeLabel.setName("selectorTypeLabel"); // NOI18N

		selectorTypeBtnGroup.add(fslQueryRadio);
		fslQueryRadio.setText(bundle.getString("FSL_query")); // NOI18N
		fslQueryRadio.setName("fslQueryRadio"); // NOI18N
		fslQueryRadio.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fslQueryRadioActionPerformed(evt);
			}
		});

		selectorTypeBtnGroup.add(sparqlQueryRadio);
		sparqlQueryRadio.setText(bundle.getString("SPARQL_query")); // NOI18N
		sparqlQueryRadio.setName("sparqlQueryRadio"); // NOI18N
		sparqlQueryRadio.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				sparqlQueryRadioActionPerformed(evt);
			}
		});

		selectorTypeBtnGroup.add(simpleSelectorRadio);
		simpleSelectorRadio.setSelected(true);
		simpleSelectorRadio.setText(bundle.getString("Simple_selector")); // NOI18N
		simpleSelectorRadio.setName("simpleSelectorRadio"); // NOI18N
		simpleSelectorRadio
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						simpleSelectorRadioActionPerformed(evt);
					}
				});

		saveBtn.setText(bundle.getString("Save")); // NOI18N
		saveBtn.setName("saveBtn"); // NOI18N
		saveBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveBtnActionPerformed(evt);
			}
		});

		cancelBtn.setText(bundle.getString("Cancel")); // NOI18N
		cancelBtn.setName("cancelBtn"); // NOI18N
		cancelBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelBtnActionPerformed(evt);
			}
		});

		jPanel1.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Selector value"));
		jPanel1.setName("jPanel1"); // NOI18N

		buttonGroup1.add(existingResourceRadio);
		existingResourceRadio.setText(bundle.getString("existing_resource:")); // NOI18N
		existingResourceRadio.setName("existingResourceRadio"); // NOI18N
		existingResourceRadio
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						existingResourceRadioActionPerformed(evt);
					}
				});

		existingResourceCombo.setName("existingResourceCombo"); // NOI18N
		existingResourceCombo.setPreferredSize(new java.awt.Dimension(30, 20));

		buttonGroup1.add(customSelectorRadio);
		customSelectorRadio.setSelected(true);
		customSelectorRadio
				.setText(bundle.getString("custom_selector_string:")); // NOI18N
		customSelectorRadio.setName("customSelectorRadio"); // NOI18N
		customSelectorRadio
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						customSelectorRadioActionPerformed(evt);
					}
				});

		selectorStringScrollPane.setName("selectorStringScrollPane"); // NOI18N

		customSelectorText.setColumns(20);
		customSelectorText.setFont(new java.awt.Font("Monospaced", 0, 12));
		customSelectorText.setRows(5);
		customSelectorText.setName("customSelectorText"); // NOI18N
		selectorStringScrollPane.setViewportView(customSelectorText);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																selectorStringScrollPane,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																367,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				existingResourceRadio)
																		.addGap(8,
																				8,
																				8)
																		.addComponent(
																				existingResourceCombo,
																				0,
																				248,
																				Short.MAX_VALUE))
														.addComponent(
																customSelectorRadio,
																javax.swing.GroupLayout.Alignment.LEADING))
										.addContainerGap()));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																existingResourceRadio)
														.addComponent(
																existingResourceCombo,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(customSelectorRadio)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												selectorStringScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												119, Short.MAX_VALUE)
										.addContainerGap()));

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
																		cancelBtn,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		75,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		saveBtn,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		75,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(12, 12,
																		12))
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						domainTypeLabel)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(10,
																										10,
																										10)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(
																														classDomainRadio)
																												.addComponent(
																														instanceDomainRadio))))
																.addGap(46, 46,
																		46)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						selectorTypeLabel)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(10,
																										10,
																										10)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(
																														fslQueryRadio)
																												.addComponent(
																														sparqlQueryRadio)
																												.addComponent(
																														simpleSelectorRadio))))
																.addContainerGap(
																		147,
																		Short.MAX_VALUE))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jPanel1,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addContainerGap()))));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(domainTypeLabel)
												.addComponent(selectorTypeLabel))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		simpleSelectorRadio)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		fslQueryRadio)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		sparqlQueryRadio))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(3, 3, 3)
																.addComponent(
																		instanceDomainRadio)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		classDomainRadio)))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jPanel1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(saveBtn)
												.addComponent(cancelBtn))
								.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelBtnActionPerformed
		this.dispose();
	}// GEN-LAST:event_cancelBtnActionPerformed

	private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_saveBtnActionPerformed
		if (saveActionListener != null) {
			saveActionListener.actionPerformed(evt);
		}
		this.dispose();
	}// GEN-LAST:event_saveBtnActionPerformed

	private void existingResourceRadioActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_existingResourceRadioActionPerformed
		selectorValueTypeChanged();
	}// GEN-LAST:event_existingResourceRadioActionPerformed

	private void customSelectorRadioActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_customSelectorRadioActionPerformed
		selectorValueTypeChanged();
	}// GEN-LAST:event_customSelectorRadioActionPerformed

	private void instanceDomainRadioActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_instanceDomainRadioActionPerformed
		selectorDomainTypeChanged();
	}// GEN-LAST:event_instanceDomainRadioActionPerformed

	private void classDomainRadioActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_classDomainRadioActionPerformed
		selectorDomainTypeChanged();
	}// GEN-LAST:event_classDomainRadioActionPerformed

	private void simpleSelectorRadioActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_simpleSelectorRadioActionPerformed
		selectorTypeChanged();
	}// GEN-LAST:event_simpleSelectorRadioActionPerformed

	private void fslQueryRadioActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fslQueryRadioActionPerformed
		selectorTypeChanged();
	}// GEN-LAST:event_fslQueryRadioActionPerformed

	private void sparqlQueryRadioActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sparqlQueryRadioActionPerformed
		selectorTypeChanged();
	}// GEN-LAST:event_sparqlQueryRadioActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JButton cancelBtn;
	private javax.swing.JRadioButton classDomainRadio;
	private javax.swing.JRadioButton customSelectorRadio;
	private javax.swing.JTextArea customSelectorText;
	private javax.swing.ButtonGroup domainTypeBtnGroup;
	private javax.swing.JLabel domainTypeLabel;
	private javax.swing.JComboBox existingResourceCombo;
	private javax.swing.JRadioButton existingResourceRadio;
	private javax.swing.JRadioButton fslQueryRadio;
	private javax.swing.JRadioButton instanceDomainRadio;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JButton saveBtn;
	private javax.swing.JScrollPane selectorStringScrollPane;
	private javax.swing.ButtonGroup selectorTypeBtnGroup;
	private javax.swing.JLabel selectorTypeLabel;
	private javax.swing.JRadioButton simpleSelectorRadio;
	private javax.swing.JRadioButton sparqlQueryRadio;

	// End of variables declaration//GEN-END:variables

	/**
	 * Sets an action listener that is executed after save button is pressed.
	 */
	public void setSaveActionListener(ActionListener actionListener) {
		this.saveActionListener = actionListener;
	}

	private void selectorTypeChanged() {
		boolean simpleIsSelected = simpleSelectorRadio.isSelected();
		existingResourceRadio.setEnabled(simpleIsSelected);
		existingResourceCombo.setEnabled(simpleIsSelected);
		if (!simpleIsSelected) {
			customSelectorRadio.setSelected(true);
		}
	}

	private void selectorDomainTypeChanged() {
		DataRepositoryDao dataDao = ContextHolder.getInstance()
				.getDataRepositoryDao();
		List<Namespace> filter = dataDao.getNamespaces();
		List<Value> resources;
		if (classDomainRadio.isSelected()) {
			resources = dataDao.getClasses(filter);
		} else {
			resources = dataDao.getResources(filter);
		}
		List<String> resourcesAsStrings = Lists.transform(resources,
				new Function<Value, String>() {
					DataRepositoryDao dataDao = ContextHolder.getInstance()
							.getDataRepositoryDao();

					@Override
					public String apply(Value from) {
						return FresnelUtils.replaceNamespace(
								from.stringValue(), dataDao.getNamespaces());
					}
				});
		existingResourceCombo.setModel(new ExtendedDefaultComboBM<String>(
				resourcesAsStrings));
	}

	private void selectorValueTypeChanged() {
		boolean customIsSelected = customSelectorRadio.isSelected();
		customSelectorText.setEnabled(customIsSelected);
		existingResourceCombo.setEnabled(!customIsSelected);
	}

}
