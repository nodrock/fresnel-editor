/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.group;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.fresneleditor.common.AppEventsManager;
import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.guisupport.ExtendedJList;
import cz.muni.fi.fresneleditor.common.guisupport.IContextMenu;
import cz.muni.fi.fresneleditor.common.guisupport.IEditable;
import cz.muni.fi.fresneleditor.common.guisupport.MessageDialog;
import cz.muni.fi.fresneleditor.common.guisupport.dialogs.ElementDetailDialog;
import cz.muni.fi.fresneleditor.common.guisupport.model.StylesTableModel;
import cz.muni.fi.fresneleditor.common.utils.FresnelUtils;
import cz.muni.fi.fresneleditor.common.utils.GuiUtils;
import cz.muni.fi.fresneleditor.gui.mod.group.data.GroupModel;
import cz.muni.fi.fresneleditor.gui.mod.group.treemodel.GroupItemNode;
import cz.muni.fi.fresneleditor.gui.mod.group.utils.GroupModelManager;
import fr.inria.jfresnel.Format;
import fr.inria.jfresnel.Group;
import fr.inria.jfresnel.Lens;
import java.util.ArrayList;
import org.openrdf.model.impl.URIImpl;

/**
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 18.4.2009
 */
public class GroupsJPanel extends javax.swing.JPanel implements
		ITabComponent<Group>, IEditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory
			.getLogger(GroupsJPanel.class);
	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("cz/muni/fi/fresneleditor/gui/mod/group/resources/GroupsJPanel");

	// private final String FILE_URI_PREFIX = "file://";
	private final int NOTHING_SELECTED = -1;

	private GroupModel initialGroupModel = null;
	private Group group = null;
	private String groupDescription = "";

	private GroupJScrollPane representingScrollPane = null;
	private GroupItemNode groupItemNode = null;

	private boolean createNewGroup = false;

	/**
     * 
     */
	private class GroupJScrollPane extends JScrollPane implements IContextMenu {

		public GroupJScrollPane(GroupsJPanel groupsJPanel) {
			super(groupsJPanel);
		}

		@Override
		public List<JMenuItem> getMenu() {
			return groupItemNode != null ? groupItemNode.getMenu()
					: Collections.<JMenuItem> emptyList();
		}
	}

	/**
	 * Creates new form GroupsJPanel
	 */
	@Deprecated
	public GroupsJPanel() {
		initComponents();
	}

	/**
	 * 
	 * @param group
	 */
	public GroupsJPanel(Group group, GroupItemNode groupItemNode) {

		initComponents();

                this.group = group;
		this.groupItemNode = groupItemNode;
		this.createNewGroup = (group == null);

		GroupModelManager modelManager = new GroupModelManager();

		if (createNewGroup) {
			initialGroupModel = modelManager.buildNewModel();
		} else {
			initialGroupModel = modelManager.buildModel(group);
		}

		loadGroupModel(initialGroupModel);
	}

	private void loadGroupModel(final GroupModel groupModel) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				groupNameText.setText(groupModel.getUri());
				groupDescription = groupModel.getComment();
				StylesTableModel stylesTableModel = (StylesTableModel) stylesJPanel
						.getStylesTable().getModel();
				stylesTableModel.addAll(groupModel.getStyles());
				stylesheetUriText.setText(groupModel.getCssStylesheetUrl());

				// Fill lenses and format lists
				((ExtendedJList<URI>) groupLensesList).removeAllElements();
				((ExtendedJList<URI>) groupLensesList).addElements(groupModel
						.getAssociatedLensUris());
				((ExtendedJList<URI>) groupFormatsList).removeAllElements();
				((ExtendedJList<URI>) groupFormatsList).addElements(groupModel
						.getAssociatedFormatUris());

                                List<Lens> lenses = ContextHolder.getInstance().getFresnelDocumentDao().getLenses();
				List<URI> availLenses = new ArrayList<URI>();
                                for(Lens l : lenses){
                                    availLenses.add(new URIImpl(l.getURI()));
                                }
                                
				availLenses.removeAll(groupModel.getAssociatedLensUris());
				((ExtendedJList<URI>) availLensesList).removeAllElements();
				((ExtendedJList<URI>) availLensesList).addElements(availLenses);

                                List<Format> formats = ContextHolder.getInstance().getFresnelDocumentDao().getFormats();
				List<URI> availFormats = new ArrayList<URI>();
                                for(Format f : formats){
                                    availFormats.add(new URIImpl(f.getURI()));
                                }
                                
				availFormats.removeAll(groupModel.getAssociatedFormatUris());
				((ExtendedJList<URI>) availFormatsList).removeAllElements();
				((ExtendedJList<URI>) availFormatsList)
						.addElements(availFormats);

				// groupModel.asStatements();
			}
		});
	}

	private GroupModel exportGroupModel() {

		GroupModel resultGroupModel = new GroupModel();

		resultGroupModel.setUri(groupNameText.getText());

		// TODO: This is not very clean.
		resultGroupModel.setLabel(initialGroupModel.getLabel());
		resultGroupModel.setComment(groupDescription);

		if (!FresnelUtils.isStringEmpty(stylesheetUriText.getText())) {
			resultGroupModel.setCssStylesheetUrl(stylesheetUriText.getText());
		}

		// Styles
		StylesTableModel stylesTableModel = (StylesTableModel) stylesJPanel
				.getStylesTable().getModel();
		resultGroupModel.setStyles(stylesTableModel.getAll());

		// Associated lenses and formats
		resultGroupModel
				.setAssociatedLensUris(((ExtendedJList<URI>) groupLensesList)
						.getElements());
		resultGroupModel
				.setAssociatedFormatUris(((ExtendedJList<URI>) groupFormatsList)
						.getElements());

		return resultGroupModel;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		stylesJPanel = new cz.muni.fi.fresneleditor.common.guisupport.panels.StylesJPanel();
		cssJPanel = new javax.swing.JPanel();
		stylesheetUriText = new javax.swing.JTextField();
		sylesheetUriLabel = new javax.swing.JLabel();
		browseLocalCssBtn = new javax.swing.JButton();
		groupDefinitionJPanel = new javax.swing.JPanel();
		lensesLabel = new javax.swing.JLabel();
		formatsLabel = new javax.swing.JLabel();
		addLensBtn = new javax.swing.JButton();
		addFormatBtn = new javax.swing.JButton();
		removeLensBtn = new javax.swing.JButton();
		removeFormatBtn = new javax.swing.JButton();
		availFormatsScrollPane = new javax.swing.JScrollPane();
		availFormatsList = new cz.muni.fi.fresneleditor.common.guisupport.components.UrisJList();
		availLensesScrollPane = new javax.swing.JScrollPane();
		availLensesList = new cz.muni.fi.fresneleditor.common.guisupport.components.UrisJList();
		groupLensesScrollPane = new javax.swing.JScrollPane();
		groupLensesList = new cz.muni.fi.fresneleditor.common.guisupport.components.UrisJList();
		groupFormatsScrollPane = new javax.swing.JScrollPane();
		groupFormatsList = new cz.muni.fi.fresneleditor.common.guisupport.components.UrisJList();
		groupLensesLabel = new javax.swing.JLabel();
		groupFormatsLabel = new javax.swing.JLabel();
		groupNameLabel = new javax.swing.JLabel();
		groupNameText = new javax.swing.JTextField();
		detailsBtn = new javax.swing.JButton();
		saveBtn = new javax.swing.JButton();
		deleteBtn = new javax.swing.JButton();
		closeBtn = new javax.swing.JButton();

		stylesJPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Group-level styles"));
		stylesJPanel.setName("stylesJPanel"); // NOI18N

		cssJPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("CSS stylesheet reference"));
		cssJPanel.setName("cssJPanel"); // NOI18N

		stylesheetUriText.setName("stylesheetUriText"); // NOI18N

		java.util.ResourceBundle bundle = java.util.ResourceBundle
				.getBundle("cz/muni/fi/fresneleditor/gui/mod/group/resources/GroupsJPanel"); // NOI18N
		sylesheetUriLabel.setText(bundle
				.getString("CSS_stylesheet_location_(URL):")); // NOI18N
		sylesheetUriLabel.setName("sylesheetUriLabel"); // NOI18N

		browseLocalCssBtn.setText(bundle.getString("Browse_local...")); // NOI18N
		browseLocalCssBtn.setName("browseLocalCssBtn"); // NOI18N
		browseLocalCssBtn
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						browseLocalCssBtnActionPerformed(evt);
					}
				});

		javax.swing.GroupLayout cssJPanelLayout = new javax.swing.GroupLayout(
				cssJPanel);
		cssJPanel.setLayout(cssJPanelLayout);
		cssJPanelLayout
				.setHorizontalGroup(cssJPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								cssJPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												cssJPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																cssJPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				stylesheetUriText,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				506,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				browseLocalCssBtn)
																		.addGap(17,
																				17,
																				17))
														.addGroup(
																cssJPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				sylesheetUriLabel)
																		.addContainerGap(
																				486,
																				Short.MAX_VALUE)))));
		cssJPanelLayout
				.setVerticalGroup(cssJPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								cssJPanelLayout
										.createSequentialGroup()
										.addGap(7, 7, 7)
										.addComponent(sylesheetUriLabel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												cssJPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																stylesheetUriText,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																22,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																browseLocalCssBtn))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		groupDefinitionJPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Group definition"));
		groupDefinitionJPanel.setName("groupDefinitionJPanel"); // NOI18N

		lensesLabel.setText(bundle.getString("Available_lenses:")); // NOI18N
		lensesLabel.setName("lensesLabel"); // NOI18N

		formatsLabel.setText(bundle.getString("Available_formats:")); // NOI18N
		formatsLabel.setName("formatsLabel"); // NOI18N

		addLensBtn.setText(bundle.getString("Add_to_group")); // NOI18N
		addLensBtn.setName("addLensBtn"); // NOI18N
		addLensBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addLensBtnActionPerformed(evt);
			}
		});

		addFormatBtn.setText(bundle.getString("Add_to_group")); // NOI18N
		addFormatBtn.setName("addFormatBtn"); // NOI18N
		addFormatBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addFormatBtnActionPerformed(evt);
			}
		});

		removeLensBtn.setText(bundle.getString("Remove_from_group")); // NOI18N
		removeLensBtn.setName("removeLensBtn"); // NOI18N
		removeLensBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeLensBtnActionPerformed(evt);
			}
		});

		removeFormatBtn.setText(bundle.getString("Remove_from_group")); // NOI18N
		removeFormatBtn.setName("removeFormatBtn"); // NOI18N
		removeFormatBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeFormatBtnActionPerformed(evt);
			}
		});

		availFormatsScrollPane.setName("availFormatsScrollPane"); // NOI18N

		availFormatsList.setName("availFormatsList"); // NOI18N
		availFormatsList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				availFormatsListMouseClicked(evt);
			}
		});
		availFormatsScrollPane.setViewportView(availFormatsList);

		availLensesScrollPane.setName("availLensesScrollPane"); // NOI18N

		availLensesList.setName("availLensesList"); // NOI18N
		availLensesList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				availLensesListMouseClicked(evt);
			}
		});
		availLensesScrollPane.setViewportView(availLensesList);

		groupLensesScrollPane.setName("groupLensesScrollPane"); // NOI18N

		groupLensesList.setName("groupLensesList"); // NOI18N
		groupLensesList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				groupLensesListMouseClicked(evt);
			}
		});
		groupLensesScrollPane.setViewportView(groupLensesList);

		groupFormatsScrollPane.setName("groupFormatsScrollPane"); // NOI18N

		groupFormatsList.setName("groupFormatsList"); // NOI18N
		groupFormatsList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				groupFormatsListMouseClicked(evt);
			}
		});
		groupFormatsScrollPane.setViewportView(groupFormatsList);

		groupLensesLabel.setText(bundle.getString("Lenses_in_group:")); // NOI18N
		groupLensesLabel.setName("groupLensesLabel"); // NOI18N

		groupFormatsLabel.setText(bundle.getString("Formats_in_group:")); // NOI18N
		groupFormatsLabel.setName("groupFormatsLabel"); // NOI18N

		javax.swing.GroupLayout groupDefinitionJPanelLayout = new javax.swing.GroupLayout(
				groupDefinitionJPanel);
		groupDefinitionJPanel.setLayout(groupDefinitionJPanelLayout);
		groupDefinitionJPanelLayout
				.setHorizontalGroup(groupDefinitionJPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								groupDefinitionJPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupDefinitionJPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																groupDefinitionJPanelLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																		.addGroup(
																				groupDefinitionJPanelLayout
																						.createSequentialGroup()
																						.addGroup(
																								groupDefinitionJPanelLayout
																										.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																										.addComponent(
																												availFormatsScrollPane,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												242,
																												Short.MAX_VALUE)
																										.addComponent(
																												availLensesScrollPane,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												242,
																												Short.MAX_VALUE))
																						.addPreferredGap(
																								javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																						.addGroup(
																								groupDefinitionJPanelLayout
																										.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																										.addComponent(
																												removeFormatBtn,
																												javax.swing.GroupLayout.Alignment.TRAILING,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												138,
																												Short.MAX_VALUE)
																										.addComponent(
																												addFormatBtn,
																												javax.swing.GroupLayout.Alignment.TRAILING,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												138,
																												Short.MAX_VALUE)
																										.addGroup(
																												javax.swing.GroupLayout.Alignment.TRAILING,
																												groupDefinitionJPanelLayout
																														.createSequentialGroup()
																														.addGroup(
																																groupDefinitionJPanelLayout
																																		.createParallelGroup(
																																				javax.swing.GroupLayout.Alignment.TRAILING)
																																		.addComponent(
																																				addLensBtn,
																																				javax.swing.GroupLayout.Alignment.LEADING,
																																				javax.swing.GroupLayout.DEFAULT_SIZE,
																																				136,
																																				Short.MAX_VALUE)
																																		.addComponent(
																																				removeLensBtn,
																																				javax.swing.GroupLayout.DEFAULT_SIZE,
																																				136,
																																				Short.MAX_VALUE))
																														.addGap(2,
																																2,
																																2)))
																						.addPreferredGap(
																								javax.swing.LayoutStyle.ComponentPlacement.RELATED))
																		.addGroup(
																				groupDefinitionJPanelLayout
																						.createSequentialGroup()
																						.addComponent(
																								lensesLabel)
																						.addGap(289,
																								289,
																								289)))
														.addGroup(
																groupDefinitionJPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				formatsLabel)
																		.addGap(282,
																				282,
																				282)))
										.addGroup(
												groupDefinitionJPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																groupLensesLabel)
														.addGroup(
																groupDefinitionJPanelLayout
																		.createSequentialGroup()
																		.addGap(2,
																				2,
																				2)
																		.addGroup(
																				groupDefinitionJPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								groupFormatsScrollPane,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								230,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								groupLensesScrollPane,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								230,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								groupFormatsLabel))))
										.addContainerGap()));
		groupDefinitionJPanelLayout
				.setVerticalGroup(groupDefinitionJPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								groupDefinitionJPanelLayout
										.createSequentialGroup()
										.addGroup(
												groupDefinitionJPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lensesLabel)
														.addComponent(
																groupLensesLabel))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												groupDefinitionJPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																groupDefinitionJPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				availLensesScrollPane,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				101,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(5,
																				5,
																				5)
																		.addGroup(
																				groupDefinitionJPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								formatsLabel)
																						.addComponent(
																								groupFormatsLabel)))
														.addGroup(
																groupDefinitionJPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				addLensBtn)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				removeLensBtn))
														.addComponent(
																groupLensesScrollPane,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																101,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												groupDefinitionJPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																groupFormatsScrollPane,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																101,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																groupDefinitionJPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				addFormatBtn)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				removeFormatBtn))
														.addComponent(
																availFormatsScrollPane,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																101,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(13, Short.MAX_VALUE)));

		groupNameLabel.setText(bundle.getString("Group_name_(URI):")); // NOI18N
		groupNameLabel.setName("groupNameLabel"); // NOI18N

		groupNameText.setName("groupNameText"); // NOI18N

		detailsBtn.setText(bundle.getString("Details...")); // NOI18N
		detailsBtn.setName("detailsBtn"); // NOI18N
		detailsBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				detailsBtnActionPerformed(evt);
			}
		});

		saveBtn.setText(bundle.getString("Save")); // NOI18N
		saveBtn.setName("saveBtn"); // NOI18N
		saveBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveBtnActionPerformed(evt);
			}
		});

		deleteBtn.setText(bundle.getString("Delete")); // NOI18N
		deleteBtn.setName("deleteBtn"); // NOI18N
		deleteBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteBtnActionPerformed(evt);
			}
		});

		closeBtn.setText(bundle.getString("Close")); // NOI18N
		closeBtn.setName("closeBtn"); // NOI18N
		closeBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeBtnActionPerformed(evt);
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
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		saveBtn,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		73,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		deleteBtn)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		closeBtn,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		76,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		groupNameLabel)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		groupNameText,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		407,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		detailsBtn,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		106,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(
														cssJPanel,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														groupDefinitionJPanel,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														stylesJPanel,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { closeBtn, deleteBtn, saveBtn });

		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(groupNameLabel)
												.addComponent(
														groupNameText,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														detailsBtn,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														23,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(groupDefinitionJPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(cssJPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(stylesJPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(saveBtn)
												.addComponent(deleteBtn)
												.addComponent(closeBtn))
								.addContainerGap(15, Short.MAX_VALUE)));
	}// </editor-fold>                        

	private void addLensBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_addLensBtnActionPerformed
		moveItemBetweenLists(availLensesList, groupLensesList);
	}// GEN-LAST:event_addLensBtnActionPerformed

	private void removeLensBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_removeLensBtnActionPerformed
		moveItemBetweenLists(groupLensesList, availLensesList);
	}// GEN-LAST:event_removeLensBtnActionPerformed

	private void removeFormatBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_removeFormatBtnActionPerformed
		moveItemBetweenLists(groupFormatsList, availFormatsList);
	}// GEN-LAST:event_removeFormatBtnActionPerformed

	private void browseLocalCssBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_browseLocalCssBtnActionPerformed

		JFileChooser chooser = new JFileChooser();

		FileFilter filter = new FileNameExtensionFilter(
				bundle.getString("CSS_stylesheets_(*.css)"),
				java.util.ResourceBundle
						.getBundle(
								"cz/muni/fi/fresneleditor/gui/mod/group/resources/GroupsJPanel")
						.getString("css"));
		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(GuiUtils.getTopComponent());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				stylesheetUriText.setText(chooser.getSelectedFile()
						.getCanonicalPath());
			} catch (IOException ex) {
				LOG.error(bundle
						.getString("Error_when_returning_from_file_chooser_dialog!"));
				return;
				// FIXME
			}
		}
	}// GEN-LAST:event_browseLocalCssBtnActionPerformed

	private void addFormatBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_addFormatBtnActionPerformed
		moveItemBetweenLists(availFormatsList, groupFormatsList);
	}// GEN-LAST:event_addFormatBtnActionPerformed

	private void availLensesListMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_availLensesListMouseClicked
		if (evt.getClickCount() == 2) {
			moveItemBetweenLists(availLensesList, groupLensesList);
		}
	}// GEN-LAST:event_availLensesListMouseClicked

	private void availFormatsListMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_availFormatsListMouseClicked
		if (evt.getClickCount() == 2) {
			moveItemBetweenLists(availFormatsList, groupFormatsList);
		}
	}// GEN-LAST:event_availFormatsListMouseClicked

	private void groupLensesListMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_groupLensesListMouseClicked
		if (evt.getClickCount() == 2) {
			moveItemBetweenLists(groupLensesList, availLensesList);
		}
	}// GEN-LAST:event_groupLensesListMouseClicked

	private void groupFormatsListMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_groupFormatsListMouseClicked
		if (evt.getClickCount() == 2) {
			moveItemBetweenLists(groupFormatsList, availFormatsList);
		}
	}// GEN-LAST:event_groupFormatsListMouseClicked

	private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_saveBtnActionPerformed
		doSave();
	}// GEN-LAST:event_saveBtnActionPerformed

	private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deleteBtnActionPerformed
		doDelete();
	}// GEN-LAST:event_deleteBtnActionPerformed

	private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closeBtnActionPerformed
		groupItemNode.closeTab();
	}// GEN-LAST:event_closeBtnActionPerformed

	private void detailsBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_detailsBtnActionPerformed
		final ElementDetailDialog dialog = new ElementDetailDialog(
				GuiUtils.getOwnerFrame(this), true, this.groupDescription);
		final GroupsJPanel thisPanel = this;
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent ev) {
				thisPanel.setGroupDescription(dialog.getDescription());
			}
		});
		GuiUtils.centerOnScreen(dialog);
		dialog.setVisible(true);
	}// GEN-LAST:event_detailsBtnActionPerformed

	public void moveItemBetweenLists(ExtendedJList<URI> sourceList,
			ExtendedJList<URI> targetList) {

		if (sourceList.getSelectedIndex() != NOTHING_SELECTED) {
			URI value = (URI) sourceList.getSelectedValue();
			targetList.addElement(value);
			sourceList.removeElement(value);
			// TODO: Sorting of target list!
		}
	}

	public void setGroupDescription(String description) {
		this.groupDescription = description;
	}

	@Override
	public String getLabel() {
		return createNewGroup ? bundle.getString("New_group") : FresnelUtils
				.getLocalName(initialGroupModel.getUri());
	}

	@Override
	public JScrollPane getScrollPane() {

		if (representingScrollPane == null) {
			representingScrollPane = new GroupJScrollPane(this);
		}

		return representingScrollPane;
	}

	@Override
	public Group getItem() {
		return group;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton addFormatBtn;
	private javax.swing.JButton addLensBtn;
	private cz.muni.fi.fresneleditor.common.guisupport.components.UrisJList availFormatsList;
	private javax.swing.JScrollPane availFormatsScrollPane;
	private cz.muni.fi.fresneleditor.common.guisupport.components.UrisJList availLensesList;
	private javax.swing.JScrollPane availLensesScrollPane;
	private javax.swing.JButton browseLocalCssBtn;
	private javax.swing.JButton closeBtn;
	private javax.swing.JPanel cssJPanel;
	private javax.swing.JButton deleteBtn;
	private javax.swing.JButton detailsBtn;
	private javax.swing.JLabel formatsLabel;
	private javax.swing.JPanel groupDefinitionJPanel;
	private javax.swing.JLabel groupFormatsLabel;
	private cz.muni.fi.fresneleditor.common.guisupport.components.UrisJList groupFormatsList;
	private javax.swing.JScrollPane groupFormatsScrollPane;
	private javax.swing.JLabel groupLensesLabel;
	private cz.muni.fi.fresneleditor.common.guisupport.components.UrisJList groupLensesList;
	private javax.swing.JScrollPane groupLensesScrollPane;
	private javax.swing.JLabel groupNameLabel;
	private javax.swing.JTextField groupNameText;
	private javax.swing.JLabel lensesLabel;
	private javax.swing.JButton removeFormatBtn;
	private javax.swing.JButton removeLensBtn;
	private javax.swing.JButton saveBtn;
	private cz.muni.fi.fresneleditor.common.guisupport.panels.StylesJPanel stylesJPanel;
	private javax.swing.JTextField stylesheetUriText;
	private javax.swing.JLabel sylesheetUriLabel;

	// End of variables declaration//GEN-END:variables

	public void doReload() {
		loadGroupModel(initialGroupModel);
	}

	@Override
	public void doSave() {

		if (validateForm()) {
			// Insert new statements
			GroupModel groupModel = exportGroupModel();
                        
                        GroupModelManager modelManager = new GroupModelManager();
                        
                        if (createNewGroup) {
				ContextHolder.getInstance().getFresnelDocumentDao().addGroup(modelManager.convertModel2JFresnel(groupModel));
			} else {
				ContextHolder.getInstance().getFresnelDocumentDao().updateGroup(group.getURI(), modelManager.convertModel2JFresnel(groupModel));
			}

			// If changes were successfully commited then switch to new initial
			// model
			initialGroupModel = groupModel;
			createNewGroup = false;

			AppEventsManager.getInstance().fireRepositoryDataChanged(this,
					ContextHolder.getInstance().getFresnelRepositoryName());
		}
	}

	@Override
	public void doDelete() {
		new MessageDialog(GuiUtils.getOwnerFrame(this), "Confirmation",
				"Do you really want to delete the group '"
						+ initialGroupModel.getModelUri() + "'?",
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ContextHolder.getInstance().getFresnelDocumentDao().deleteGroup(group.getURI());
						AppEventsManager.getInstance()
								.fireRepositoryDataChanged(
										this,
										ContextHolder.getInstance()
												.getFresnelRepositoryName());
					}
				}).setVisible(true);

	}

	private boolean validateForm() {
            // TODO: fix this
		String groupName = groupNameText.getText();
		String validateMessage = null;
                        //FresnelUtils.validateResourceUri(groupName,
			//	ContextHolder.getInstance().getFresnelRepositoryDao());
		if (validateMessage != null) {
			new MessageDialog(GuiUtils.getOwnerFrame(this),
					bundle.getString("Invalid_Fresnel_Group_URI"),
					bundle.getString("The_Group_URI_'") + groupName
							+ bundle.getString("'_is_not_valid:<br>")
							+ validateMessage).setVisible(true);
		}
		return validateMessage == null;
	}
}
