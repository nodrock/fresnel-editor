/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.format.dialogs;

import cz.muni.fi.fresneleditor.common.guisupport.dialogs.PreviewDialog;
import cz.muni.fi.fresneleditor.gui.mod.format.utils.FormatPreviewRenderer;
import fr.inria.jfresnel.Format;
import fr.inria.jfresnel.Lens;

/**
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 29.6.2009
 */
public class FormatPreviewDialog extends PreviewDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FormatPreviewDialog(java.awt.Frame parent, boolean modal, int type,
			Lens lens, Format format) {

		super(parent, modal, type, lens, format);
	}

	@Override
	public void renderAction() {

		FormatPreviewRenderer previewRenderer = new FormatPreviewRenderer();
		previewRenderer.renderFormatPreview(getSelectedUri(), format,
				getStylesheetUri());
	}
}
