/*
 * Fresnel Editor
 */
package cz.muni.fi.fresneleditor.gui.mod.vis;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.openrdf.model.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.FresnelApplication;
import cz.muni.fi.fresneleditor.common.ITabComponent;
import cz.muni.fi.fresneleditor.common.guisupport.ExtendedJList;
import cz.muni.fi.fresneleditor.common.guisupport.IContextMenu;
import cz.muni.fi.fresneleditor.common.guisupport.MessageDialog;
import cz.muni.fi.fresneleditor.common.utils.GuiUtils;
import cz.muni.fi.fresneleditor.common.visualization.IRDFVisualizer;
import cz.muni.fi.fresneleditor.common.visualization.RDFVisualizer;
import cz.muni.fi.fresneleditor.common.visualization.VisualizationParameter;
import cz.muni.fi.fresneleditor.gui.mod.vis.treemodel.VisualizationItemNode;
import cz.muni.fi.fresneleditor.gui.mod.vis.treemodel.VisualizationRootNode;
import cz.muni.fi.fresneleditor.model.DataRepositoryDao;
import cz.muni.fi.fresneleditor.model.FresnelRepositoryDao;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author Miroslav Warchil
 */
public class VisualizationJPanel extends javax.swing.JPanel implements
        ITabComponent<String> {

    private static final Logger LOG = LoggerFactory.getLogger(VisualizationJPanel.class);
    protected static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("cz/muni/fi/fresneleditor/gui/mod/vis/resources/VisualizationJPanel");
    // private static int NO_ROW_SELECTED = -1;
    private VisualizationJScrollPane representingScrollPane = null;
    protected VisualizationItemNode visualizationItemNode = null;
    // private VisualizationRootNode visualizationRootNode = null;
    protected IRDFVisualizer visualizer;
    protected String label;
    protected String defaultCssStylesheet;

    /**
     *
     */
    private class VisualizationJScrollPane extends JScrollPane implements
            IContextMenu {

        public VisualizationJScrollPane(VisualizationJPanel visualizationJPanel) {
            super(visualizationJPanel);
        }

        @Override
        public List<JMenuItem> getMenu() {
            return visualizationItemNode != null ? visualizationItemNode.getMenu() : Collections.<JMenuItem>emptyList();
        }
    }

    /**
     * Creates new form VisualizationJPanel
     * 
     * @param visualizer
     *            instance of visualizer used for visualization of RDF data
     */
    public VisualizationJPanel(IRDFVisualizer visualizer, String label) {
        this.visualizer = visualizer;
        this.label = label;
        initComponents();
        customInitComponents();
    }

    /** Creates new form VisualizationJPanel */
    public VisualizationJPanel() {
        this(new RDFVisualizer(), "Default Visualization");
    }

    private void customInitComponents() {
        List<URI> availGroups = ContextHolder.getInstance().getFresnelRepositoryDao().getGroupsURIs();
        ((ExtendedJList<URI>) groupsList).addElements(availGroups);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupSelectionPanel = new javax.swing.JPanel();
        groupsListScrollPane = new javax.swing.JScrollPane();
        groupsList = new cz.muni.fi.fresneleditor.common.guisupport.components.UrisJList();
        groupSelectionLabel = new javax.swing.JLabel();
        visualizeBtn = new javax.swing.JButton();
        cssJPanel = new javax.swing.JPanel();
        stylesheetUriText = new javax.swing.JTextField();
        sylesheetUriLabel = new javax.swing.JLabel();
        browseLocalCssBtn = new javax.swing.JButton();
        stylesheetDescLbl = new javax.swing.JLabel();
        outputFileJPanel = new javax.swing.JPanel();
        outputFileText = new javax.swing.JTextField();
        outputFileLbl = new javax.swing.JLabel();
        browseFileBtn = new javax.swing.JButton();
        outputFileDescLbl = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        svgVizSetJPanel = new javax.swing.JPanel();
        rectWidthJText = new javax.swing.JTextField();
        picHeightJText = new javax.swing.JTextField();
        propLine2LenghtJText = new javax.swing.JTextField();
        fontSizeJLabel = new javax.swing.JLabel();
        propLine1LenghtJLabel = new javax.swing.JLabel();
        propLine1LenghtJText = new javax.swing.JTextField();
        picHeightJLabel = new javax.swing.JLabel();
        fontSizeJText = new javax.swing.JTextField();
        propLine2LenghtJLabel = new javax.swing.JLabel();
        rectWidthJLabel = new javax.swing.JLabel();

        groupSelectionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Fresnel Group selection"));
        groupSelectionPanel.setName("groupSelectionPanel"); // NOI18N

        groupsListScrollPane.setName("groupsListScrollPane"); // NOI18N

        groupsList.setName("groupsList"); // NOI18N
        groupsListScrollPane.setViewportView(groupsList);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("cz/muni/fi/fresneleditor/gui/mod/vis/resources/VisualizationJPanel"); // NOI18N
        groupSelectionLabel.setText(bundle.getString("Select_group_which_will_be_used_for_visualisation_of_RDF_data:")); // NOI18N
        groupSelectionLabel.setName("groupSelectionLabel"); // NOI18N

        javax.swing.GroupLayout groupSelectionPanelLayout = new javax.swing.GroupLayout(groupSelectionPanel);
        groupSelectionPanel.setLayout(groupSelectionPanelLayout);
        groupSelectionPanelLayout.setHorizontalGroup(
            groupSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(groupSelectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(groupSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupsListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupSelectionLabel))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        groupSelectionPanelLayout.setVerticalGroup(
            groupSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, groupSelectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(groupSelectionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupsListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        visualizeBtn.setText(bundle.getString("Visualize_RDF_data")); // NOI18N
        visualizeBtn.setName("visualizeBtn"); // NOI18N
        visualizeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                visualizeBtnActionPerformed(evt);
            }
        });

        cssJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("CSS stylesheet reference"));
        cssJPanel.setName("cssJPanel"); // NOI18N

        stylesheetUriText.setName("stylesheetUriText"); // NOI18N

        sylesheetUriLabel.setText(bundle.getString("CSS_stylesheet_location_(URL):")); // NOI18N
        sylesheetUriLabel.setName("sylesheetUriLabel"); // NOI18N

        browseLocalCssBtn.setText(bundle.getString("Browse_local...")); // NOI18N
        browseLocalCssBtn.setName("browseLocalCssBtn"); // NOI18N
        browseLocalCssBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseLocalCssBtnActionPerformed(evt);
            }
        });

        stylesheetDescLbl.setFont(new java.awt.Font("Tahoma", 2, 11));
        stylesheetDescLbl.setForeground(new java.awt.Color(51, 51, 51));
        stylesheetDescLbl.setText(bundle.getString("If_no_CSS_stylesheet_is_specified_then_default_one_is_used.")); // NOI18N
        stylesheetDescLbl.setName("stylesheetDescLbl"); // NOI18N

        javax.swing.GroupLayout cssJPanelLayout = new javax.swing.GroupLayout(cssJPanel);
        cssJPanel.setLayout(cssJPanelLayout);
        cssJPanelLayout.setHorizontalGroup(
            cssJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cssJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cssJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cssJPanelLayout.createSequentialGroup()
                        .addComponent(stylesheetUriText, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseLocalCssBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(sylesheetUriLabel)
                    .addComponent(stylesheetDescLbl))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        cssJPanelLayout.setVerticalGroup(
            cssJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cssJPanelLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(sylesheetUriLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cssJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stylesheetUriText, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseLocalCssBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(stylesheetDescLbl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        outputFileJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Output file"));
        outputFileJPanel.setName("outputFileJPanel"); // NOI18N

        outputFileText.setName("outputFileText"); // NOI18N

        outputFileLbl.setText(bundle.getString("Path_to_output_file")); // NOI18N
        outputFileLbl.setName("outputFileLbl"); // NOI18N

        browseFileBtn.setText(bundle.getString("Browse_local...")); // NOI18N
        browseFileBtn.setName("browseFileBtn"); // NOI18N
        browseFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileBtnActionPerformed(evt);
            }
        });

        outputFileDescLbl.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        outputFileDescLbl.setForeground(new java.awt.Color(51, 51, 51));
        outputFileDescLbl.setText(bundle.getString("Output_file_description")); // NOI18N
        outputFileDescLbl.setName("outputFileDescLbl"); // NOI18N

        javax.swing.GroupLayout outputFileJPanelLayout = new javax.swing.GroupLayout(outputFileJPanel);
        outputFileJPanel.setLayout(outputFileJPanelLayout);
        outputFileJPanelLayout.setHorizontalGroup(
            outputFileJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputFileJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(outputFileJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(outputFileJPanelLayout.createSequentialGroup()
                        .addComponent(outputFileText, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseFileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(outputFileLbl)
                    .addComponent(outputFileDescLbl))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        outputFileJPanelLayout.setVerticalGroup(
            outputFileJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputFileJPanelLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(outputFileLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(outputFileJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputFileText, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseFileBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(outputFileDescLbl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel1.setText(this.label);
        jLabel1.setName("jLabel1"); // NOI18N

        svgVizSetJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("SVGSettingBorderTitle"))); // NOI18N
        svgVizSetJPanel.setName("svgVizSetJPanel"); // NOI18N

        rectWidthJText.setText("400");
        rectWidthJText.setName("rectWidthJText"); // NOI18N

        picHeightJText.setText("200");
        picHeightJText.setName("picHeightJText"); // NOI18N

        propLine2LenghtJText.setText("300");
        propLine2LenghtJText.setName("propLine2LenghtJText"); // NOI18N

        fontSizeJLabel.setText(bundle.getString("fontSize")); // NOI18N
        fontSizeJLabel.setName("fontSizeJLabel"); // NOI18N

        propLine1LenghtJLabel.setText(bundle.getString("propLine1Lenght")); // NOI18N
        propLine1LenghtJLabel.setName("propLine1LenghtJLabel"); // NOI18N

        propLine1LenghtJText.setText("100");
        propLine1LenghtJText.setName("propLine1LenghtJText"); // NOI18N

        picHeightJLabel.setText(bundle.getString("picHeight")); // NOI18N
        picHeightJLabel.setToolTipText("");
        picHeightJLabel.setName("picHeightJLabel"); // NOI18N

        fontSizeJText.setText("11");
        fontSizeJText.setName("fontSizeJText"); // NOI18N

        propLine2LenghtJLabel.setText(bundle.getString("propLine2Lenght")); // NOI18N
        propLine2LenghtJLabel.setName("propLine2LenghtJLabel"); // NOI18N

        rectWidthJLabel.setText(bundle.getString("rectWidth")); // NOI18N
        rectWidthJLabel.setName("rectWidthJLabel"); // NOI18N

        javax.swing.GroupLayout svgVizSetJPanelLayout = new javax.swing.GroupLayout(svgVizSetJPanel);
        svgVizSetJPanel.setLayout(svgVizSetJPanelLayout);
        svgVizSetJPanelLayout.setHorizontalGroup(
            svgVizSetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, svgVizSetJPanelLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(svgVizSetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rectWidthJLabel)
                    .addComponent(fontSizeJLabel)
                    .addComponent(propLine1LenghtJLabel)
                    .addComponent(picHeightJLabel)
                    .addComponent(propLine2LenghtJLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(svgVizSetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(propLine2LenghtJText, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rectWidthJText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(picHeightJText, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fontSizeJText, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(propLine1LenghtJText, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        svgVizSetJPanelLayout.setVerticalGroup(
            svgVizSetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(svgVizSetJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(svgVizSetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rectWidthJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rectWidthJLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(svgVizSetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(picHeightJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(picHeightJLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(svgVizSetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fontSizeJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fontSizeJLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(svgVizSetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(propLine1LenghtJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(propLine1LenghtJLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(svgVizSetJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(propLine2LenghtJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(propLine2LenghtJLabel))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(visualizeBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 487, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(outputFileJPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cssJPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(groupSelectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(svgVizSetJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(10, 10, 10)))
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupSelectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(svgVizSetJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cssJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputFileJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(visualizeBtn)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void visualizeBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_visualizeBtnActionPerformed
        if (this.svgVizSetJPanel.isVisible()) {
            if (!rectWidthJText.getText().matches("[1-9][0-9]*") || !picHeightJText.getText().matches("[1-9][0-9]*") || !fontSizeJText.getText().matches("[1-9][0-9]*") || !propLine1LenghtJText.getText().matches("[1-9][0-9]*") || !propLine2LenghtJText.getText().matches("[1-9][0-9]*")) {
                JOptionPane.showMessageDialog(this,
                        bundle.getString("SVG_Param_Error_text"),
                        bundle.getString("SVG_Param_Error_label"),
                        JOptionPane.ERROR_MESSAGE);
                this.svgVizSetJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("SVGSettingBorderTitle"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.RED));
                return;
            } else {
                this.svgVizSetJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("SVGSettingBorderTitle")));
            }
        }

        final DataRepositoryDao dataDao = ContextHolder.getInstance().getDataRepositoryDao();
        final URI groupUri = groupsList.getSelectedValueCasted();
        // Validate that some group was selected
        if (groupUri == null) {
            new MessageDialog(this, bundle.getString("No_group_selected"),
                    bundle.getString("Please_select_group")).setVisible(true);
            return;
        }
        // Validate that group contains at least one lens
        FresnelRepositoryDao fresnelDao = ContextHolder.getInstance().getFresnelRepositoryDao();
        List<URI> lensURIs = fresnelDao.getLensesUrisForGroup(groupUri);
        if (lensURIs == null || lensURIs.isEmpty()) {
            new MessageDialog(this, bundle.getString("Group_without_lens"),
                    bundle.getString("No_lens_text")).setVisible(true);
            return;
        }
        // Create parameter for visualization
        final VisualizationParameter visParam = new VisualizationParameter();
        if (StringUtils.hasText(stylesheetUriText.getText())) {
            visParam.setCssStylesheetURL(stylesheetUriText.getText());
        } else if (defaultCssStylesheet != null) {
            visParam.setCssStylesheetURL(defaultCssStylesheet);
        }

        if (this.svgVizSetJPanel.isVisible()) {
            visParam.setRectWidth(Integer.parseInt(rectWidthJText.getText()));
            visParam.setPicHeight(Integer.parseInt(picHeightJText.getText()));
            visParam.setFontSize(Integer.parseInt(fontSizeJText.getText()));
            visParam.setPropLine1Lenght(Integer.parseInt(propLine1LenghtJText.getText()));
            visParam.setPropLine2Lenght(Integer.parseInt(propLine2LenghtJText.getText()));
        }

        // Trigger visualization
        // if (groupUri != null) {
        if (visualizer == null) {
            visualizer = new RDFVisualizer();
        }
        // if (outputFileText.getText() == null
        // || "".equals(outputFileText.getText())) {
        // visualizer.visualize(dataDao.getRepository(), groupUri,
        // visParam, null);
        // } else {
        // visualizer.visualize(dataDao.getRepository(), groupUri,
        // visParam, outputFileText.getText());
        // }
        SwingWorker worker = new SwingWorker() {

            @Override
            public Object doInBackground() {
                if (outputFileText.getText() == null
                        || "".equals(outputFileText.getText())) {
                    visualizer.visualize(dataDao.getRepository(), groupUri,
                            visParam, null);
                } else {
                    visualizer.visualize(dataDao.getRepository(), groupUri,
                            visParam, outputFileText.getText());
                }
                return null;
            }
        };
        worker.execute();

        FresnelApplication.getApp().getBaseFrame().showPreviewPanelFullSize();
    }// GEN-LAST:event_visualizeBtnActionPerformed

    private void browseLocalCssBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_browseLocalCssBtnActionPerformed

        JFileChooser chooser = new JFileChooser();

        FileFilter filter = new FileNameExtensionFilter(
                bundle.getString("CSS_stylesheets_(*.css)"), "css");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(GuiUtils.getTopComponent());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                stylesheetUriText.setText(chooser.getSelectedFile().getCanonicalPath());
            } catch (IOException ex) {
                LOG.error(bundle.getString("Error_when_returning_from_file_chooser_dialog!"));
                return;
                // FIXME
            }
        }
    }// GEN-LAST:event_browseLocalCssBtnActionPerformed

    private void browseFileBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_browseFileBtnActionPerformed

        JFileChooser chooser = new JFileChooser();

        int returnVal = chooser.showOpenDialog(GuiUtils.getTopComponent());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                outputFileText.setText(chooser.getSelectedFile().getCanonicalPath());
            } catch (IOException ex) {
                LOG.error(bundle.getString("Error_when_returning_from_file_chooser_dialog!"));
                return;
                // FIXME
            }
        }
    }// GEN-LAST:event_browseFileBtnActionPerformed

    @Override
    public String getItem() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JScrollPane getScrollPane() {

        if (representingScrollPane == null) {
            representingScrollPane = new VisualizationJScrollPane(this);
        }

        return representingScrollPane;
    }

    @Override
    public String getLabel() {

        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDefaultCssStylesheet() {
        return defaultCssStylesheet;
    }

    public void setDefaultCssStylesheet(String defaultCssStylesheet) {
        this.defaultCssStylesheet = defaultCssStylesheet;
    }

    public void setVisibilitySVGVizSetting(boolean visible) {
        this.svgVizSetJPanel.setVisible(visible);
    }

    public VisualizationItemNode getVisualizationItemNode() {
        return visualizationItemNode;
    }

    public void setVisualizationItemNode(
            VisualizationItemNode visualizationItemNode) {
        this.visualizationItemNode = visualizationItemNode;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseFileBtn;
    private javax.swing.JButton browseLocalCssBtn;
    private javax.swing.JPanel cssJPanel;
    private javax.swing.JLabel fontSizeJLabel;
    private javax.swing.JTextField fontSizeJText;
    private javax.swing.JLabel groupSelectionLabel;
    private javax.swing.JPanel groupSelectionPanel;
    private cz.muni.fi.fresneleditor.common.guisupport.components.UrisJList groupsList;
    private javax.swing.JScrollPane groupsListScrollPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel outputFileDescLbl;
    private javax.swing.JPanel outputFileJPanel;
    private javax.swing.JLabel outputFileLbl;
    private javax.swing.JTextField outputFileText;
    private javax.swing.JLabel picHeightJLabel;
    private javax.swing.JTextField picHeightJText;
    private javax.swing.JLabel propLine1LenghtJLabel;
    private javax.swing.JTextField propLine1LenghtJText;
    private javax.swing.JLabel propLine2LenghtJLabel;
    private javax.swing.JTextField propLine2LenghtJText;
    private javax.swing.JLabel rectWidthJLabel;
    private javax.swing.JTextField rectWidthJText;
    private javax.swing.JLabel stylesheetDescLbl;
    private javax.swing.JTextField stylesheetUriText;
    private javax.swing.JPanel svgVizSetJPanel;
    private javax.swing.JLabel sylesheetUriLabel;
    private javax.swing.JButton visualizeBtn;
    // End of variables declaration//GEN-END:variables
}
