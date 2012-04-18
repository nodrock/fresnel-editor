/*
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.gui.mod.format;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cz.muni.fi.fresneleditor.common.AppEventsManager;
import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.data.AdditionalContentGuiWrapper;
import cz.muni.fi.fresneleditor.common.data.StyleGuiWrapper;
import cz.muni.fi.fresneleditor.common.guisupport.ExtendedDefaultComboBM;
import cz.muni.fi.fresneleditor.common.guisupport.IContextMenu;
import cz.muni.fi.fresneleditor.common.guisupport.IEditable;
import cz.muni.fi.fresneleditor.common.guisupport.MessageDialog;
import cz.muni.fi.fresneleditor.common.guisupport.dialogs.AdditionalContentDialog;
import cz.muni.fi.fresneleditor.common.guisupport.dialogs.ElementDetailDialog;
import cz.muni.fi.fresneleditor.common.guisupport.dialogs.PreviewDialog;
import cz.muni.fi.fresneleditor.common.guisupport.model.StylesTableModel;
import cz.muni.fi.fresneleditor.common.utils.FresnelUtils;
import cz.muni.fi.fresneleditor.common.utils.GuiUtils;
import cz.muni.fi.fresneleditor.gui.mod.format.components.AdditionalContentTableColumnNames;
import cz.muni.fi.fresneleditor.gui.mod.format.components.AdditionalContentTableModel;
import cz.muni.fi.fresneleditor.gui.mod.format.components.CheckBoxRenderer;
import cz.muni.fi.fresneleditor.gui.mod.format.components.FormatDomainTableModel;
import cz.muni.fi.fresneleditor.gui.mod.format.data.DomainSelectorGuiWrapper;
import cz.muni.fi.fresneleditor.gui.mod.format.data.FormatModel;
import cz.muni.fi.fresneleditor.gui.mod.format.data.enums.LabelType;
import cz.muni.fi.fresneleditor.gui.mod.format.data.enums.SpecifiedValueType;
import cz.muni.fi.fresneleditor.gui.mod.format.data.enums.ValueType;
import cz.muni.fi.fresneleditor.gui.mod.format.dialogs.DomainSelectorDialog;
import cz.muni.fi.fresneleditor.gui.mod.format.dialogs.FormatPreviewDialog;
import cz.muni.fi.fresneleditor.gui.mod.format.treemodel.FormatItemNode;
import cz.muni.fi.fresneleditor.gui.mod.format.utils.FormatModelManager;
import cz.muni.fi.fresneleditor.model.FresnelRepositoryDao;
import fr.inria.jfresnel.Format;
import fr.inria.jfresnel.sesame.SesameFormat;

/**
 * Main panel for Formats module which can be plugged into Fresnel Editor.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 18. 3. 2009
 */
@Component
public class FormatsJPanel extends javax.swing.JPanel implements
		ITabComponent<URI>, IEditable {
	// fixme igor: TBD: what about the generic for this interface: is it
	// necessary/usefull?

	// FIXME: Listeners to enabling/disabling of subpanels.
	// TODO: Handle list of available style on the basis of format domain type.
	// TODO: Handle list of available style types in style dialog - add avail to
	// wrapper.
	// TODO: Validation of input from user ?!
	// TODO: How to solve renaming of format - URI changes.

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory
			.getLogger(FormatsJPanel.class);
	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("cz/muni/fi/fresneleditor/gui/mod/format/resource-bundle-format");

	private static int NO_ROW_SELECTED = -1;

	private FormatModel initialFormatModel = null;
	private URI formatUri = null;
	private String formatDescription = "";

	private FormatItemNode formatItemNode = null;
	private FormatJScrollPane representingScrollPane = null;

	private boolean createNewFormat = false;

	/**
     * 
     */
	private class FormatJScrollPane extends JScrollPane implements IContextMenu {

		public FormatJScrollPane(FormatsJPanel formatsJPanel) {
			super(formatsJPanel);
		}

		@Override
		public List<JMenuItem> getMenu() {
			return formatItemNode != null ? formatItemNode.getMenu()
					: Collections.<JMenuItem> emptyList();
		}
	}

	/**
	 * Default constructor.
	 */
	@Deprecated
	public FormatsJPanel() {

		initComponents();
		customInitComponents();
	}

	/**
	 * Default constructor for FormatsJPanel.
	 */
	public FormatsJPanel(URI formatUri, FormatItemNode formatItemNode) {

		this();

		this.formatItemNode = formatItemNode;
		this.createNewFormat = (formatUri == null);

		FormatModelManager modelManager = new FormatModelManager();

		if (createNewFormat) {
			initialFormatModel = modelManager.buildNewModel();
			formatUri = null;
		} else {
			FresnelRepositoryDao fresnelDao = ContextHolder.getInstance()
					.getFresnelRepositoryDao();
			Format format = fresnelDao.getFormat(formatUri.toString());
			this.formatUri = formatUri;

			initialFormatModel = modelManager.buildModel(format);
		}

		loadFormatModal(initialFormatModel);
	}

	/**
	 * Intializes Format JPanel on the basis of Fresnel Format contained in
	 * format model.
	 * 
	 * @param formatModel
	 *            format model representing Fresnel Format
	 */
	private void loadFormatModal(final FormatModel formatModel) {

		final FormatDomainTableModel formatDomainTableModel = (FormatDomainTableModel) domainTable
				.getModel();
		final StylesTableModel stylesTableModel = (StylesTableModel) stylesJPanel
				.getStylesTable().getModel();
		final AdditionalContentTableModel additionalContentTableModel = (AdditionalContentTableModel) additionalContentTable
				.getModel();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Load format name (URI)
				formatNameText.setText(formatModel.getUri());
				formatDescription = formatModel.getComment().getLabel();
				// Load table models
				// TODO: Helper for cloning
				// Domain selectors
				List<DomainSelectorGuiWrapper> dsModelList = new ArrayList<DomainSelectorGuiWrapper>();
				for (DomainSelectorGuiWrapper ds : formatModel
						.getDomainSelectors()) {
					dsModelList.add(ds.clone());
				}
				formatDomainTableModel.addAll(dsModelList);
				// Styles
				List<StyleGuiWrapper> styleModelList = new ArrayList<StyleGuiWrapper>();
				for (StyleGuiWrapper style : formatModel.getStyles()) {
					styleModelList.add(style.clone());
				}
				stylesTableModel.addAll(styleModelList);
				// Additional contents
				List<AdditionalContentGuiWrapper> acModelList = new ArrayList<AdditionalContentGuiWrapper>();
				for (AdditionalContentGuiWrapper additionalContent : formatModel
						.getAdditionalContents()) {
					acModelList.add(additionalContent.clone());
				}
				additionalContentTableModel.addAll(acModelList);
				// Set label settings
				if (formatModel.getLabelType() == LabelType.NONE) {
					noneLabelRadio.setSelected(true);
				} else if (formatModel.getLabelType() == LabelType.DEFAULT) {
					defaultLabelRadio.setSelected(true);
				} else if (formatModel.getLabelType() == LabelType.LITERAL) {
					literalLabelRadio.setSelected(true);
					literalText.setText(formatModel.getLiteralLabelValue());
				}
				// Set value settings
				if (formatModel.getValueType() == ValueType.DEFAULT) {
					defaultValueRadio.setSelected(true);
				} else if (formatModel.getValueType() == ValueType.SPECIFIED) {
					specifiedValueRadio.setSelected(true);
					specifiedValueCmbBox.setSelectedItem(formatModel
							.getSpecifiedValueType());
				} else {
					LOG.warn("No value type in format model - using default!");
					defaultValueRadio.setSelected(true);
				}
			}
		});
	}

	/**
	 * Stores values set in Format JPanel into format model instance.
	 * 
	 * @return format model instance holding informaation about given format
	 */
	private FormatModel saveFormatModel() {

		FormatModel format = new FormatModel();

		FormatDomainTableModel formatDomainTableModel = (FormatDomainTableModel) domainTable
				.getModel();
		StylesTableModel stylesTableModel = (StylesTableModel) stylesJPanel
				.getStylesTable().getModel();
		AdditionalContentTableModel additionalContentTableModel = (AdditionalContentTableModel) additionalContentTable
				.getModel();
		// Save format name (URI)
		format.setUri(formatNameText.getText());
		format.setComment(new LiteralImpl(formatDescription, initialFormatModel
				.getComment().getLanguage()));
		// Save domain selectors, styles and additonal contents
		format.setDomainSelectors(formatDomainTableModel.getAll());
		format.setStyles(stylesTableModel.getAll());
		format.setAdditionalContents(additionalContentTableModel.getAll());
		// Save label settings
		if (defaultLabelRadio.isSelected()) {
			format.setLabelType(LabelType.DEFAULT);
		} else if (noneLabelRadio.isSelected()) {
			format.setLabelType(LabelType.NONE);
		} else if (literalLabelRadio.isSelected()) {
			format.setLiteralLabelValue(literalText.getText());
			format.setLabelType(LabelType.LITERAL);
		} else {
			LOG.warn("No label type radio button selected - using default!");
			format.setLabelType(LabelType.DEFAULT);
		}
		// Save values settings
		if (defaultValueRadio.isSelected()) {
			format.setValueType(ValueType.DEFAULT);
		} else if (specifiedValueRadio.isSelected()) {
			format.setSpecifiedValueType((SpecifiedValueType) specifiedValueCmbBox
					.getSelectedItem());
			format.setValueType(ValueType.SPECIFIED);
		} else {
			LOG.warn("No label type radio button selected - using default!");
			format.setLabelType(LabelType.DEFAULT);
		}
		return format;
	}

	/**
	 * Custom initialization of GUI components.
	 */
	private void customInitComponents() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				additionalContentTable
						.getColumn(
								AdditionalContentTableColumnNames.ADDITIONAL_CONTENT_TYPE)
						.setMinWidth(130);
				additionalContentTable.getColumn(
						AdditionalContentTableColumnNames.BEFORE)
						.setCellRenderer(new CheckBoxRenderer());
				additionalContentTable.getColumn(
						AdditionalContentTableColumnNames.AFTER)
						.setCellRenderer(new CheckBoxRenderer());
				additionalContentTable.getColumn(
						AdditionalContentTableColumnNames.FIRST)
						.setCellRenderer(new CheckBoxRenderer());
				additionalContentTable.getColumn(
						AdditionalContentTableColumnNames.LAST)
						.setCellRenderer(new CheckBoxRenderer());
				additionalContentTable.getColumn(
						AdditionalContentTableColumnNames.NO_VALUE)
						.setCellRenderer(new CheckBoxRenderer());
			}
		});
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		labelBtnGroup = new javax.swing.ButtonGroup();
		valueBtnGroup = new javax.swing.ButtonGroup();
		formatNameLabel = new javax.swing.JLabel();
		formatNameText = new javax.swing.JTextField();
		labelsAndValuesPanel = new javax.swing.JPanel();
		labelLabel = new javax.swing.JLabel();
		valueLabel = new javax.swing.JLabel();
		defaultLabelRadio = new javax.swing.JRadioButton();
		noneLabelRadio = new javax.swing.JRadioButton();
		literalLabelRadio = new javax.swing.JRadioButton();
		literalText = new javax.swing.JTextField();
		defaultValueRadio = new javax.swing.JRadioButton();
		specifiedValueRadio = new javax.swing.JRadioButton();
		specifiedValueCmbBox = new javax.swing.JComboBox();
		formatDomainPanel = new javax.swing.JPanel();
		newSelectorBtn = new javax.swing.JButton();
		domainTypeLabel = new javax.swing.JLabel();
		domainTableSrollPane = new javax.swing.JScrollPane();
		domainTable = new javax.swing.JTable();
		editSelectorBtn = new javax.swing.JButton();
		deleteSelectorBtn = new javax.swing.JButton();
		formatsAndContentPanel = new javax.swing.JPanel();
		additionalContentTableScrollPane = new javax.swing.JScrollPane();
		additionalContentTable = new javax.swing.JTable();
		editFormatBtn = new javax.swing.JButton();
		formatLabel = new javax.swing.JLabel();
		detailsBtn = new javax.swing.JButton();
		saveBtn = new javax.swing.JButton();
		closeBtn = new javax.swing.JButton();
		stylesJPanel = new cz.muni.fi.fresneleditor.common.guisupport.panels.StylesJPanel();
		deleteBtn = new javax.swing.JButton();
		previewBtn = new javax.swing.JButton();

		setName("Form"); // NOI18N

		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance().getContext().getResourceMap(FormatsJPanel.class);
		formatNameLabel.setText(resourceMap.getString("formatNameLabel.text")); // NOI18N
		formatNameLabel.setName("formatNameLabel"); // NOI18N

		formatNameText.setText(resourceMap.getString("formatNameText.text")); // NOI18N
		formatNameText.setName("formatNameText"); // NOI18N

		labelsAndValuesPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder(resourceMap
						.getString("labelsAndValuesPanel.border.title"))); // NOI18N
		labelsAndValuesPanel.setName("labelsAndValuesPanel"); // NOI18N

		labelLabel.setText(resourceMap.getString("labelLabel.text")); // NOI18N
		labelLabel.setName("labelLabel"); // NOI18N

		valueLabel.setText(resourceMap.getString("valueLabel.text")); // NOI18N
		valueLabel.setName("valueLabel"); // NOI18N

		labelBtnGroup.add(defaultLabelRadio);
		defaultLabelRadio.setSelected(true);
		defaultLabelRadio.setText(resourceMap
				.getString("defaultLabelRadio.text")); // NOI18N
		defaultLabelRadio.setName("defaultLabelRadio"); // NOI18N

		labelBtnGroup.add(noneLabelRadio);
		noneLabelRadio.setText(resourceMap.getString("noneLabelRadio.text")); // NOI18N
		noneLabelRadio.setName("noneLabelRadio"); // NOI18N

		labelBtnGroup.add(literalLabelRadio);
		literalLabelRadio.setText(resourceMap
				.getString("literalLabelRadio.text")); // NOI18N
		literalLabelRadio.setName("literalLabelRadio"); // NOI18N

		literalText.setText(resourceMap.getString("literalText.text")); // NOI18N
		literalText.setName("literalText"); // NOI18N
		literalText.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				literalTextKeyPressed(evt);
			}
		});

		valueBtnGroup.add(defaultValueRadio);
		defaultValueRadio.setSelected(true);
		defaultValueRadio.setText(resourceMap
				.getString("defaultValueRadio.text")); // NOI18N
		defaultValueRadio.setName("defaultValueRadio"); // NOI18N

		valueBtnGroup.add(specifiedValueRadio);
		specifiedValueRadio.setText(resourceMap
				.getString("specifiedValueRadio.text")); // NOI18N
		specifiedValueRadio.setName("specifiedValueRadio"); // NOI18N

		specifiedValueCmbBox
				.setModel(new ExtendedDefaultComboBM<SpecifiedValueType>(
						SpecifiedValueType.values()));
		specifiedValueCmbBox.setName("specifiedValueCmbBox"); // NOI18N
		specifiedValueCmbBox
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						specifiedValueCmbBoxActionPerformed(evt);
					}
				});

		javax.swing.GroupLayout labelsAndValuesPanelLayout = new javax.swing.GroupLayout(
				labelsAndValuesPanel);
		labelsAndValuesPanel.setLayout(labelsAndValuesPanelLayout);
		labelsAndValuesPanelLayout
				.setHorizontalGroup(labelsAndValuesPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								labelsAndValuesPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(labelLabel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												labelsAndValuesPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																defaultLabelRadio)
														.addComponent(
																noneLabelRadio)
														.addComponent(
																literalLabelRadio))
										.addGroup(
												labelsAndValuesPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																labelsAndValuesPanelLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				literalText,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				182,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																labelsAndValuesPanelLayout
																		.createSequentialGroup()
																		.addGap(196,
																				196,
																				196)
																		.addComponent(
																				valueLabel)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				labelsAndValuesPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								labelsAndValuesPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												specifiedValueRadio)
																										.addGap(6,
																												6,
																												6)
																										.addComponent(
																												specifiedValueCmbBox,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												176,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								defaultValueRadio))))
										.addContainerGap(83, Short.MAX_VALUE)));
		labelsAndValuesPanelLayout
				.setVerticalGroup(labelsAndValuesPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								labelsAndValuesPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												labelsAndValuesPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																labelsAndValuesPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				labelsAndValuesPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								labelLabel)
																						.addComponent(
																								defaultLabelRadio))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				noneLabelRadio)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				labelsAndValuesPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								literalLabelRadio)
																						.addComponent(
																								literalText,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																labelsAndValuesPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				labelsAndValuesPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								defaultValueRadio)
																						.addComponent(
																								valueLabel))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				labelsAndValuesPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								specifiedValueRadio)
																						.addComponent(
																								specifiedValueCmbBox,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		formatDomainPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder(resourceMap
						.getString("formatDomainPanel.border.title"))); // NOI18N
		formatDomainPanel.setName("formatDomainPanel"); // NOI18N

		newSelectorBtn.setText(resourceMap.getString("newSelectorBtn.text")); // NOI18N
		newSelectorBtn.setName("newSelectorBtn"); // NOI18N
		newSelectorBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newSelectorBtnActionPerformed(evt);
			}
		});

		domainTypeLabel.setText(resourceMap.getString("domainTypeLabel.text")); // NOI18N
		domainTypeLabel.setName("domainTypeLabel"); // NOI18N

		domainTableSrollPane.setName("domainTableSrollPane"); // NOI18N

		domainTable.setModel(new FormatDomainTableModel());
		domainTable.setName("domainTable"); // NOI18N
		domainTable
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		domainTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				domainTableMouseClicked(evt);
			}
		});
		domainTableSrollPane.setViewportView(domainTable);

		editSelectorBtn.setText(resourceMap.getString("editSelectorBtn.text")); // NOI18N
		editSelectorBtn.setName("editSelectorBtn"); // NOI18N
		editSelectorBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editSelectorBtnActionPerformed(evt);
			}
		});

		deleteSelectorBtn.setText(resourceMap
				.getString("deleteSelectorBtn.text")); // NOI18N
		deleteSelectorBtn.setName("deleteSelectorBtn"); // NOI18N
		deleteSelectorBtn
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						deleteSelectorBtnActionPerformed(evt);
					}
				});

		javax.swing.GroupLayout formatDomainPanelLayout = new javax.swing.GroupLayout(
				formatDomainPanel);
		formatDomainPanel.setLayout(formatDomainPanelLayout);
		formatDomainPanelLayout
				.setHorizontalGroup(formatDomainPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								formatDomainPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												formatDomainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																domainTypeLabel)
														.addComponent(
																domainTableSrollPane,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																478,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												formatDomainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																deleteSelectorBtn,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																editSelectorBtn,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																newSelectorBtn,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap(71, Short.MAX_VALUE)));
		formatDomainPanelLayout
				.setVerticalGroup(formatDomainPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								formatDomainPanelLayout
										.createSequentialGroup()
										.addComponent(domainTypeLabel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												formatDomainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																formatDomainPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				newSelectorBtn)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				editSelectorBtn)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				deleteSelectorBtn))
														.addComponent(
																domainTableSrollPane,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																103,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));

		formatsAndContentPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder(resourceMap
						.getString("formatsAndContentPanel.border.title"))); // NOI18N
		formatsAndContentPanel.setName("formatsAndContentPanel"); // NOI18N

		additionalContentTableScrollPane
				.setName("additionalContentTableScrollPane"); // NOI18N

		additionalContentTable
				.setModel(new cz.muni.fi.fresneleditor.gui.mod.format.components.AdditionalContentTableModel());
		additionalContentTable.setName("additionalContentTable"); // NOI18N
		additionalContentTable
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		additionalContentTable
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						additionalContentTableMouseClicked(evt);
					}
				});
		additionalContentTableScrollPane
				.setViewportView(additionalContentTable);

		editFormatBtn.setText(resourceMap.getString("editFormatBtn.text")); // NOI18N
		editFormatBtn.setName("editFormatBtn"); // NOI18N
		editFormatBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editFormatBtnActionPerformed(evt);
			}
		});

		formatLabel.setText(resourceMap.getString("formatLabel.text")); // NOI18N
		formatLabel.setName("formatLabel"); // NOI18N

		javax.swing.GroupLayout formatsAndContentPanelLayout = new javax.swing.GroupLayout(
				formatsAndContentPanel);
		formatsAndContentPanel.setLayout(formatsAndContentPanelLayout);
		formatsAndContentPanelLayout
				.setHorizontalGroup(formatsAndContentPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								formatsAndContentPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												formatsAndContentPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																formatsAndContentPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				additionalContentTableScrollPane,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				483,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				editFormatBtn,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				114,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																formatLabel))
										.addContainerGap(59, Short.MAX_VALUE)));
		formatsAndContentPanelLayout
				.setVerticalGroup(formatsAndContentPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								formatsAndContentPanelLayout
										.createSequentialGroup()
										.addComponent(formatLabel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												formatsAndContentPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																formatsAndContentPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				editFormatBtn)
																		.addContainerGap())
														.addGroup(
																formatsAndContentPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				additionalContentTableScrollPane,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				95,
																				Short.MAX_VALUE)
																		.addGap(13,
																				13,
																				13)))));

		detailsBtn.setText(resourceMap.getString("detailsBtn.text")); // NOI18N
		detailsBtn.setName("detailsBtn"); // NOI18N
		detailsBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				detailsBtnActionPerformed(evt);
			}
		});

		saveBtn.setText(resourceMap.getString("saveBtn.text")); // NOI18N
		saveBtn.setName("saveBtn"); // NOI18N
		saveBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveBtnActionPerformed(evt);
			}
		});

		closeBtn.setText(resourceMap.getString("closeBtn.text")); // NOI18N
		closeBtn.setName("closeBtn"); // NOI18N
		closeBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeBtnActionPerformed(evt);
			}
		});

		stylesJPanel.setName("stylesJPanel"); // NOI18N

		deleteBtn.setText(resourceMap.getString("deleteBtn.text")); // NOI18N
		deleteBtn.setName("deleteBtn"); // NOI18N
		deleteBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteBtnActionPerformed(evt);
			}
		});

		previewBtn.setText(resourceMap.getString("previewBtn.text")); // NOI18N
		previewBtn.setName("previewBtn"); // NOI18N
		previewBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				previewBtnActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
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
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		previewBtn,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		79,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		formatNameLabel)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		formatNameText,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		398,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		detailsBtn,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		107,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(
														stylesJPanel,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														labelsAndValuesPanel,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														formatDomainPanel,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														formatsAndContentPanel,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addContainerGap(49, Short.MAX_VALUE)));

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
												.addComponent(formatNameLabel)
												.addComponent(
														formatNameText,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(detailsBtn))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(formatDomainPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(labelsAndValuesPanel,
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
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(formatsAndContentPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										11, Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(saveBtn)
												.addComponent(deleteBtn)
												.addComponent(closeBtn)
												.addComponent(previewBtn))
								.addContainerGap()));
	}// </editor-fold>//GEN-END:initComponents

	private void editSelectorBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_editSelectorBtnActionPerformed
		handleEditSelectorAction();
	}// GEN-LAST:event_editSelectorBtnActionPerformed

	private void newSelectorBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newSelectorBtnActionPerformed
		// Get table model
		final FormatDomainTableModel tableModel = (FormatDomainTableModel) domainTable
				.getModel();
		// Create new domain selector and pass it to domain selector dialog
		final DomainSelectorGuiWrapper ds = new DomainSelectorGuiWrapper();
		ds.setUpdated(false);
		LOG.info("Creating new domain selector: " + ds.toString());
		final DomainSelectorDialog domainSelectorDialog = new DomainSelectorDialog(
				GuiUtils.getOwnerFrame(this), true, ds);
		domainSelectorDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent ev) {
				// Take selector returned from domain selector dialog and add it
				// to table model
				LOG.info("Merging changes of domain selector: " + ds.toString());
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (ds.isUpdated()) {
							tableModel.addRow(ds);
						}
					}
				});
			}
		});
		// Display domain selector dialog
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GuiUtils.centerOnScreen(domainSelectorDialog);
				domainSelectorDialog.setVisible(true);
			}
		});
	}// GEN-LAST:event_newSelectorBtnActionPerformed

	private void deleteSelectorBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deleteSelectorBtnActionPerformed

		final int selectedRowIndex = domainTable.getSelectedRow();

		if (selectedRowIndex == NO_ROW_SELECTED) {
			return;
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				LOG.info("Deleting selected domain selector.");
				FormatDomainTableModel tableModel = (FormatDomainTableModel) domainTable
						.getModel();
				tableModel.deleteRow(selectedRowIndex);
			}
		});
	}// GEN-LAST:event_deleteSelectorBtnActionPerformed

	private void editFormatBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_editFormatBtnActionPerformed
		handleEditFormatAction();
	}// GEN-LAST:event_editFormatBtnActionPerformed

	private void additionalContentTableMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_additionalContentTableMouseClicked
		if (evt.getClickCount() == 2) {
			handleEditFormatAction();
		}
	}// GEN-LAST:event_additionalContentTableMouseClicked

	private void domainTableMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_domainTableMouseClicked
		if (evt.getClickCount() == 2) {
			handleEditSelectorAction();
		}
	}// GEN-LAST:event_domainTableMouseClicked

	private void literalTextKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_literalTextKeyPressed
		if (!literalLabelRadio.isSelected()) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					literalLabelRadio.setSelected(true);
				}
			});
		}
	}// GEN-LAST:event_literalTextKeyPressed

	private void specifiedValueCmbBoxActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_specifiedValueCmbBoxActionPerformed
		if (!specifiedValueRadio.isSelected()) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					specifiedValueRadio.setSelected(true);
				}
			});
		}
	}// GEN-LAST:event_specifiedValueCmbBoxActionPerformed

	private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_saveBtnActionPerformed
		doSave();
	}// GEN-LAST:event_saveBtnActionPerformed

	private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deleteBtnActionPerformed
		doDelete();
	}// GEN-LAST:event_deleteBtnActionPerformed

	private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closeBtnActionPerformed
		formatItemNode.closeTab();
	}// GEN-LAST:event_closeBtnActionPerformed

	private void previewBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_previewBtnActionPerformed

		// Export currently edited format to be displayed
		FormatModelManager modelManager = new FormatModelManager();
		SesameFormat format = modelManager
				.convertModel2JFresnel(saveFormatModel());

		// Show preview dialog for setting preview parameters
		PreviewDialog previewDialog = new FormatPreviewDialog(
				GuiUtils.getOwnerFrame(this), true,
				PreviewDialog.PREVIEW_FORMAT, null, format);
		GuiUtils.centerOnScreen(previewDialog);
		previewDialog.setVisible(true);
	}// GEN-LAST:event_previewBtnActionPerformed

	private void detailsBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_detailsBtnActionPerformed
		final ElementDetailDialog dialog = new ElementDetailDialog(
				GuiUtils.getOwnerFrame(this), true, this.formatDescription);
		final FormatsJPanel thisPanel = this;
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent ev) {
				thisPanel.setFormatDescription(dialog.getDescription());
			}
		});
		GuiUtils.centerOnScreen(dialog);
		dialog.setVisible(true);
	}// GEN-LAST:event_detailsBtnActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JTable additionalContentTable;
	private javax.swing.JScrollPane additionalContentTableScrollPane;
	private javax.swing.JButton closeBtn;
	private javax.swing.JRadioButton defaultLabelRadio;
	private javax.swing.JRadioButton defaultValueRadio;
	private javax.swing.JButton deleteBtn;
	private javax.swing.JButton deleteSelectorBtn;
	private javax.swing.JButton detailsBtn;
	private javax.swing.JTable domainTable;
	private javax.swing.JScrollPane domainTableSrollPane;
	private javax.swing.JLabel domainTypeLabel;
	private javax.swing.JButton editFormatBtn;
	private javax.swing.JButton editSelectorBtn;
	private javax.swing.JPanel formatDomainPanel;
	private javax.swing.JLabel formatLabel;
	private javax.swing.JLabel formatNameLabel;
	private javax.swing.JTextField formatNameText;
	private javax.swing.JPanel formatsAndContentPanel;
	private javax.swing.ButtonGroup labelBtnGroup;
	private javax.swing.JLabel labelLabel;
	private javax.swing.JPanel labelsAndValuesPanel;
	private javax.swing.JRadioButton literalLabelRadio;
	private javax.swing.JTextField literalText;
	private javax.swing.JButton newSelectorBtn;
	private javax.swing.JRadioButton noneLabelRadio;
	private javax.swing.JButton previewBtn;
	private javax.swing.JButton saveBtn;
	private javax.swing.JComboBox specifiedValueCmbBox;
	private javax.swing.JRadioButton specifiedValueRadio;
	private cz.muni.fi.fresneleditor.common.guisupport.panels.StylesJPanel stylesJPanel;
	private javax.swing.ButtonGroup valueBtnGroup;
	private javax.swing.JLabel valueLabel;

	// End of variables declaration//GEN-END:variables

	public void setFormatDescription(String description) {
		this.formatDescription = description;
	}

	private void handleEditFormatAction() {
		// Get selected additional content from table model
		final AdditionalContentTableModel tableModel = (AdditionalContentTableModel) additionalContentTable
				.getModel();
		final int selectedRowIndex = additionalContentTable.getSelectedRow();

		if (selectedRowIndex == NO_ROW_SELECTED) {
			return;
		}

		final AdditionalContentGuiWrapper additionalContent = tableModel
				.getRow(additionalContentTable.getSelectedRow());
		additionalContent.setUpdated(false);
		// Pass domain selector to domain selector dialog
		LOG.info("Editing additional content: " + additionalContent.toString());
		final AdditionalContentDialog additionalContentDialog = new AdditionalContentDialog(
				GuiUtils.getOwnerFrame(this), true, additionalContent);
		additionalContentDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent ev) {
				// Take updated additional content from Additional Content
				// Dialog and update table model
				LOG.info("Merging changes of additional content: "
						+ additionalContent.toString());
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (additionalContent.isUpdated()) {
							tableModel.updateRow(selectedRowIndex,
									additionalContent);
						}
					}
				});
			}
		});
		// Display Style Dialog
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GuiUtils.centerOnScreen(additionalContentDialog);
				additionalContentDialog.setVisible(true);
			}
		});
	}

	private void handleEditSelectorAction() {
		// Get selected domain selector from table model
		final FormatDomainTableModel tableModel = (FormatDomainTableModel) domainTable
				.getModel();
		final int selectedRowIndex = domainTable.getSelectedRow();

		if (selectedRowIndex == NO_ROW_SELECTED) {
			return;
		}

		final DomainSelectorGuiWrapper ds = tableModel.getRow(domainTable
				.getSelectedRow());
		// Pass domain selector to domain selector dialog
		LOG.info("Editing domain selector: " + ds.toString());
		final DomainSelectorDialog domainSelectorDialog = new DomainSelectorDialog(
				GuiUtils.getOwnerFrame(this), true, ds);
		domainSelectorDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent ev) {
				// Take updated selector from domain selector dialog and update
				// table model
				LOG.info("Merging changes of domain selector: " + ds.toString());
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						tableModel.updateRow(selectedRowIndex, ds);
					}
				});
			}
		});
		// Display domain selector dialog
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GuiUtils.centerOnScreen(domainSelectorDialog);
				domainSelectorDialog.setVisible(true);
			}
		});
	}

	@Override
	public String getLabel() {
		return createNewFormat ? bundle.getString("New_format") : FresnelUtils
				.getLocalName(initialFormatModel.getUri());
	}

	@Override
	public URI getItem() {
		return formatUri;
	}

	@Override
	public JScrollPane getScrollPane() {

		if (representingScrollPane == null) {
			representingScrollPane = new FormatJScrollPane(this);
		}

		return representingScrollPane;
	}

	@Override
	public void doSave() {

		if (validateForm()) {
			// Insert new statements
			FormatModel formatModel = saveFormatModel();
			FresnelRepositoryDao fresnelDao = ContextHolder.getInstance()
					.getFresnelRepositoryDao();
			if (createNewFormat) {
				fresnelDao.updateFresnelResource(null, formatModel);
			} else {
				fresnelDao.updateFresnelResource(initialFormatModel,
						formatModel);
			}

			// If changes were successfully commited then switch to new initial
			// model
			initialFormatModel = formatModel;
			createNewFormat = false;

			AppEventsManager.getInstance().fireRepositoryDataChanged(this,
					ContextHolder.getInstance().getFresnelRepositoryName());
		}
	}

	@Override
	public void doDelete() {
		new MessageDialog(GuiUtils.getOwnerFrame(this), "Confirmation",
				"Do you really want to delete the format '"
						+ initialFormatModel.getModelUri() + "'?",
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						FresnelRepositoryDao fresnelDao = ContextHolder
								.getInstance().getFresnelRepositoryDao();
						fresnelDao.deleteFresnelResource(initialFormatModel);
						AppEventsManager.getInstance()
								.fireRepositoryDataChanged(
										this,
										ContextHolder.getInstance()
												.getFresnelRepositoryName());
					}
				}).setVisible(true);
	}

	private boolean validateForm() {
		String formatName = formatNameText.getText();

		String validateMessage = FresnelUtils.validateResourceUri(formatName,
				ContextHolder.getInstance().getFresnelRepositoryDao());
		if (validateMessage != null) {
			new MessageDialog(GuiUtils.getOwnerFrame(this),
					bundle.getString("Invalid_Fresnel_Format_URI"),
					bundle.getString("The_Format_URI_'") + formatName
							+ bundle.getString("'_is_not_valid:<br>")
							+ validateMessage).setVisible(true);
		}

		return validateMessage == null;
	}
}
