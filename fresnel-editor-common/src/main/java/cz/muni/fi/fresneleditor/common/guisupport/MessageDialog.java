/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import cz.muni.fi.fresneleditor.common.utils.GuiUtils;

/**
 * Helper class for displaying a message to user.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
/*
 * igor: I created it because I was not able to display a message that would
 * automatically wrap lines of text based on the dialog width (I could not find
 * such a functionality in SWING).
 */
public class MessageDialog extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static enum MessageDialogType {
		INFO, QUESTION
	}

	/**
	 * Creates a MessageDialog with a given title, message and an OK button. The
	 * OK button closes this MessageDialog.
	 * 
	 * @param parent
	 * @param title
	 * @param message
	 */
	public MessageDialog(java.awt.Component parent, String title, String message) {
		this(parent, title, message, MessageDialogType.INFO, null);
	}

	/**
	 * Creates a message dialog with given title and message. The message dialog
	 * contains two buttons - OK and Cancel. If the user presses Cancel the
	 * dialog is closed. If the user presses OK than the okAL action listener is
	 * executed and the dialog is closed.
	 * 
	 * @param parent
	 * @param title
	 * @param message
	 *            use HTML formatting, for example <br>
	 *            instead of '\n'
	 * @param okAL
	 *            the action listener that is executed if the user chooses OK
	 */
	public MessageDialog(java.awt.Component parent, String title,
			String message, ActionListener okAL) {
		this(parent, title, message, MessageDialogType.QUESTION, okAL);
	}

	/** Creates new form MessageDialog */
	private MessageDialog(java.awt.Component parent, String title,
			String message, MessageDialogType type, ActionListener okAL) {
		super(GuiUtils.getOwnerFrame(parent), true); // always create a modal
														// dialog
		initComponents();
		setTitle(title);

		if (MessageDialogType.QUESTION.equals(type)) {
			buttonsPanel.add(getCancelButton());
		}
		buttonsPanel.add(getOkButton(okAL));

		// the HTML tags will make the text wrapped
		messageLbl.setText("<HTML>" + message + "</HTML>");

		GuiUtils.centerOnScreen(this);

	}

	private Component getCancelButton() {
		JButton okButton = new JButton("Cancel");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MessageDialog.this.dispose();
			}
		});
		return okButton;
	}

	private JButton getOkButton(final ActionListener listener) {
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listener != null) {
					listener.actionPerformed(e);
				}
				MessageDialog.this.dispose();
			}
		});
		return okButton;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		messageLbl = new javax.swing.JLabel();
		buttonsPanel = new javax.swing.JPanel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setName("Form"); // NOI18N

		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance(
						cz.muni.fi.fresneleditor.common.FresnelApplication.class)
				.getContext().getResourceMap(MessageDialog.class);
		messageLbl.setFont(resourceMap.getFont("messageLbl.font")); // NOI18N
		messageLbl.setText(resourceMap.getString("messageLbl.text")); // NOI18N
		messageLbl.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		messageLbl.setName("messageLbl"); // NOI18N

		buttonsPanel.setBackground(resourceMap
				.getColor("buttonsPanel.background")); // NOI18N
		buttonsPanel.setName("buttonsPanel"); // NOI18N

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(buttonsPanel,
						javax.swing.GroupLayout.DEFAULT_SIZE, 382,
						Short.MAX_VALUE)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(12, 12, 12)
								.addComponent(messageLbl,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										358, Short.MAX_VALUE)
								.addGap(12, 12, 12)));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(messageLbl,
								javax.swing.GroupLayout.DEFAULT_SIZE, 260,
								Short.MAX_VALUE)
						.addGap(18, 18, 18)
						.addComponent(buttonsPanel,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel buttonsPanel;
	private javax.swing.JLabel messageLbl;
	// End of variables declaration//GEN-END:variables

}
