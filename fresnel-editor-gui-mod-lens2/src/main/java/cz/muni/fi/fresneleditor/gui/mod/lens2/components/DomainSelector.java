/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DomainSelector.java
 *
 * Created on 25.5.2010, 13:35:35
 */

package cz.muni.fi.fresneleditor.gui.mod.lens2.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import org.openrdf.model.Namespace;
import org.openrdf.model.Value;
import org.springframework.util.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.data.SelectorType;
import cz.muni.fi.fresneleditor.common.guisupport.ExtendedDefaultComboBM;
import cz.muni.fi.fresneleditor.common.utils.FresnelUtils;
import cz.muni.fi.fresneleditor.gui.mod.lens2.model.LensSelector;
import cz.muni.fi.fresneleditor.model.DataRepositoryDao;
import cz.muni.fi.fresneleditor.model.NamespaceImplCustom;

/**
 * 
 * @author namornik
 */
public class DomainSelector extends javax.swing.JPanel {

	private ActionListener actionLst;
	private LensSelector selector;
	private List<String> resourcesModel;

	// private DefaultComboBoxModel classesModel;
	// private DefaultComboBoxModel instancesModel;

	/** Creates new form DomainSelector */
	public DomainSelector() {
		initComponents();
		buttonGroup.setSelected(classButton.getModel(), true);
		reloadSelectors(null);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		buttonGroup = new javax.swing.ButtonGroup();
		classButton = new javax.swing.JRadioButton();
		instanceButton = new javax.swing.JRadioButton();
		resources = new javax.swing.JComboBox();

		setBackground(new java.awt.Color(255, 241, 212));
		setBorder(javax.swing.BorderFactory.createTitledBorder("Domain"));

		buttonGroup.add(classButton);
		classButton.setText("CLASS");
		classButton.setFocusable(false);
		classButton.setName("classButton"); // NOI18N
		classButton.setOpaque(false);
		classButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				classSelected(evt);
			}
		});

		buttonGroup.add(instanceButton);
		instanceButton.setText("INSTANCE");
		instanceButton.setFocusable(false);
		instanceButton.setName("instanceButton"); // NOI18N
		instanceButton.setOpaque(false);
		instanceButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				instanceButtonActionPerformed(evt);
			}
		});

		resources.setFocusable(false);
		resources.setName("resources"); // NOI18N
		resources.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				resourcesActionPerformed(evt);
			}
		});

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
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		classButton)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		67,
																		Short.MAX_VALUE)
																.addComponent(
																		instanceButton))
												.addComponent(
														resources,
														javax.swing.GroupLayout.Alignment.TRAILING,
														0, 199, Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(classButton)
												.addComponent(instanceButton))
								.addGap(18, 18, 18)
								.addComponent(resources,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(26, Short.MAX_VALUE)));
	}// </editor-fold>//GEN-END:initComponents

	private void classSelected(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_classSelected
		reloadSelectors(evt);

	}// GEN-LAST:event_classSelected

	private void resourcesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_resourcesActionPerformed

		if (actionLst != null)
			actionLst.actionPerformed(evt);
	}// GEN-LAST:event_resourcesActionPerformed

	private void instanceButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_instanceButtonActionPerformed
		reloadSelectors(evt);
	}// GEN-LAST:event_instanceButtonActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.ButtonGroup buttonGroup;
	private javax.swing.JRadioButton classButton;
	private javax.swing.JRadioButton instanceButton;
	private javax.swing.JComboBox resources;

	// End of variables declaration//GEN-END:variables

	public void addActionListener(ActionListener actionLst) {
		this.actionLst = actionLst;
	}

	public void setDomain(LensSelector sel) {
		if (!sel.getType().equals(SelectorType.SIMPLE))
			return;

		if (sel.getDomain().isInstanceDomain()) {
			buttonGroup.setSelected(instanceButton.getModel(), true);
		} else {
			buttonGroup.setSelected(classButton.getModel(), true);
		}

		selector = sel;

		reloadSelectors(null);

		// resources.setSelectedItem(selector.getDomain().getUri());
	}

	public LensSelector getDomain() {
		if (resources.getSelectedIndex() < 0)
			return null;

		LensSelector.LensDomain domain = null;
		if (buttonGroup.isSelected(instanceButton.getModel())) {
			domain = LensSelector.LensDomain.INSTANCE;
		} else if (buttonGroup.isSelected(classButton.getModel())) {
			domain = LensSelector.LensDomain.CLASS;
		}

		String value = null;

		value = (String) resources.getSelectedItem();

		if (LensSelector.LensDomain.CLASS.equals(domain)) {
			value = FresnelUtils.replacePrefix(value);
		}

		return new LensSelector(domain, value, SelectorType.SIMPLE);
	}

	public void reloadSelectors(ActionEvent e) {
		if (buttonGroup.getSelection() == null)
			return;
		String newValue = null;
		if (selector != null)
			newValue = FresnelUtils.replaceNamespace(selector.asString(),
					ContextHolder.getInstance().getDataRepositoryDao()
							.getNamespaces());
		// if(buttonGroup.isSelected(classButton.getModel())){
		//
		// classesModel = loadValues();
		// resources.setModel(classesModel);
		//
		// } else{
		// //load possible instances selectors
		// instancesModel = loadValues();
		// resources.setModel(instancesModel);
		//
		// }
		resourcesModel = new ArrayList<String>(loadValues());
		if (!resourcesModel.contains(newValue))
			resourcesModel.add(newValue);
		resources.setModel(new ExtendedDefaultComboBM<String>(resourcesModel));

		if (StringUtils.hasText(newValue))
			resources.setSelectedItem(newValue);
		else
			resources.setSelectedIndex(-1);

		if (actionLst != null)
			actionLst.actionPerformed(e);

	}

	private List<String> loadValues() {
		DataRepositoryDao dataDao = ContextHolder.getInstance()
				.getDataRepositoryDao();
		List<Namespace> filter = dataDao.getNamespaces();
		filter.remove(new NamespaceImplCustom("rdfs", dataDao
				.getNamespace("rdfs")));
		filter.remove(new NamespaceImplCustom("rdf", dataDao
				.getNamespace("rdf")));
		filter.remove(new NamespaceImplCustom("owl", dataDao
				.getNamespace("owl")));
		filter.remove(new NamespaceImplCustom("fresnel", dataDao
				.getNamespace("fresnel")));
		List<Value> resources_loc;
		if (buttonGroup.isSelected(classButton.getModel())) {
			resources_loc = dataDao.getClasses(filter);
		} else {
			resources_loc = dataDao.getDistinctInstances(filter);
		}

		List<String> resourcesAsStrings = Lists.transform(resources_loc,
				new Function<Value, String>() {
					DataRepositoryDao dataDao = ContextHolder.getInstance()
							.getDataRepositoryDao();

					@Override
					public String apply(Value from) {
						return FresnelUtils.replaceNamespace(
								from.stringValue(), dataDao.getNamespaces());
					}
				});
		return resourcesAsStrings;
	}

}
