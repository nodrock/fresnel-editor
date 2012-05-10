/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SVGVisualizationParamsJPanel.java
 *
 * Created on May 7, 2012, 3:03:02 PM
 */
package cz.muni.fi.fresneleditor.gui.mod.vis;


import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Milos Kalab <173388@mail.muni.cz>
 */
public class SVGVisualizationParamsJPanel extends javax.swing.JFrame {
    
    private static final Logger LOG = LoggerFactory
			.getLogger(VisualizationJPanel.class);
	protected static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("cz/muni/fi/fresneleditor/gui/mod/vis/resources/VisualizationJPanel");


    /** Creates new form SVGVisualizationParamsJPanel */
    public SVGVisualizationParamsJPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rectWidthJLabel = new javax.swing.JLabel();
        rectWidthJText = new javax.swing.JTextField();
        picHeightJText = new javax.swing.JTextField();
        picHeightJLabel = new javax.swing.JLabel();
        fontSizeJLabel = new javax.swing.JLabel();
        fontSizeJText = new javax.swing.JTextField();
        propLine1LenghtJLabel = new javax.swing.JLabel();
        propLine1LenghtJText = new javax.swing.JTextField();
        propLine2JLabel = new javax.swing.JLabel();
        propLine2JText = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("cz/muni/fi/fresneleditor/gui/mod/vis/resources/VisualizationJPanel"); // NOI18N
        rectWidthJLabel.setText(bundle.getString("rectWidth")); // NOI18N
        rectWidthJLabel.setName("rectWidthJLabel"); // NOI18N

        rectWidthJText.setText("400");
        rectWidthJText.setName("rectWidthJText"); // NOI18N

        picHeightJText.setText("200");
        picHeightJText.setName("picHeightJText"); // NOI18N

        picHeightJLabel.setText(bundle.getString("picHeight")); // NOI18N
        picHeightJLabel.setToolTipText("");
        picHeightJLabel.setName("picHeightJLabel"); // NOI18N

        fontSizeJLabel.setText(bundle.getString("fontSize")); // NOI18N
        fontSizeJLabel.setName("fontSizeJLabel"); // NOI18N

        fontSizeJText.setText("11");
        fontSizeJText.setName("fontSizeJText"); // NOI18N

        propLine1LenghtJLabel.setText(bundle.getString("propLine1Lenght")); // NOI18N
        propLine1LenghtJLabel.setName("propLine1LenghtJLabel"); // NOI18N

        propLine1LenghtJText.setText("100");
        propLine1LenghtJText.setName("propLine1LenghtJText"); // NOI18N

        propLine2JLabel.setText(bundle.getString("propLine2Lenght")); // NOI18N
        propLine2JLabel.setName("propLine2JLabel"); // NOI18N

        propLine2JText.setText("300");
        propLine2JText.setName("propLine2JText"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rectWidthJLabel)
                    .addComponent(fontSizeJLabel)
                    .addComponent(propLine1LenghtJLabel)
                    .addComponent(picHeightJLabel)
                    .addComponent(propLine2JLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(propLine2JText, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rectWidthJText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(picHeightJText, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fontSizeJText, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(propLine1LenghtJText, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rectWidthJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rectWidthJLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(picHeightJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(picHeightJLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fontSizeJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fontSizeJLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(propLine1LenghtJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(propLine1LenghtJLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(propLine2JText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(propLine2JLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SVGVisualizationParamsJPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SVGVisualizationParamsJPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SVGVisualizationParamsJPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SVGVisualizationParamsJPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SVGVisualizationParamsJPanel().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fontSizeJLabel;
    private javax.swing.JTextField fontSizeJText;
    private javax.swing.JLabel picHeightJLabel;
    private javax.swing.JTextField picHeightJText;
    private javax.swing.JLabel propLine1LenghtJLabel;
    private javax.swing.JTextField propLine1LenghtJText;
    private javax.swing.JLabel propLine2JLabel;
    private javax.swing.JTextField propLine2JText;
    private javax.swing.JLabel rectWidthJLabel;
    private javax.swing.JTextField rectWidthJText;
    // End of variables declaration//GEN-END:variables
}
