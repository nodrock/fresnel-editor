/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.format2.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.fresneleditor.common.data.AdditionalContentGuiWrapper;

/**
 * Class which serves as table model for Format additional contents table.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 28.3.2009
 */
public class AdditionalContentTableModel extends AbstractTableModel {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory
			.getLogger(AdditionalContentTableModel.class);

	private static int COLUMN_COUNT = 6;
	private static String[] COLUMN_NAMES = {
			AdditionalContentTableColumnNames.ADDITIONAL_CONTENT_TYPE,
			AdditionalContentTableColumnNames.BEFORE,
			AdditionalContentTableColumnNames.AFTER,
			AdditionalContentTableColumnNames.FIRST,
			AdditionalContentTableColumnNames.LAST,
			AdditionalContentTableColumnNames.NO_VALUE };

	private List<AdditionalContentGuiWrapper> additionalContents = new ArrayList<AdditionalContentGuiWrapper>();

	public AdditionalContentTableModel() {
	}

	@Override
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public int getRowCount() {

		if (additionalContents == null) {
			return 0;
		}

		return additionalContents.size();
	}

	@Override
	public String getColumnName(int columnIndex) {

		if (columnIndex < 0 || columnIndex >= COLUMN_COUNT) {
			LOG.error("Invalid column index {}.", columnIndex);
			throw new IndexOutOfBoundsException("columnIndex");
		}

		return COLUMN_NAMES[columnIndex];
	}

	private boolean existsAdditionalContent(String value) {

		if (value == null || "".equals(value)) {
			return false;
		}

		return true;
	}

	@Override
	public Object getValueAt(int row, int col) {

		if (col < 0 || col > (COLUMN_COUNT - 1)) {
			LOG.error("Invalid column index {}.", col);
			throw new IndexOutOfBoundsException("col");
		}

		if (row < 0 || row >= additionalContents.size()) {
			LOG.error("Invalid row index {}.", row);
			throw new IndexOutOfBoundsException("row");
		}

		AdditionalContentGuiWrapper additionalContent = additionalContents
				.get(row);

		switch (col) {
		case 0:
			return additionalContent.getType();
		case 1:
			return existsAdditionalContent(additionalContent.getContentBefore());
		case 2:
			return existsAdditionalContent(additionalContent.getContentAfter());
		case 3:
			return existsAdditionalContent(additionalContent.getContentFirst());
		case 4:
			return existsAdditionalContent(additionalContent.getContentLast());
		case 5:
			return existsAdditionalContent(additionalContent
					.getContentNoValue());
		default:
			LOG.error("Invalid column index {}.", col);
			throw new IndexOutOfBoundsException("col");
		}
	}

	public void addAll(List<AdditionalContentGuiWrapper> additionalContents) {

		if (additionalContents == null) {
			LOG.error("List of additional contents which should be added is null.");
			throw new NullPointerException("additionalContents");
		}

		this.additionalContents.addAll(additionalContents);
		fireTableDataChanged();
		LOG.info("{} additional contents were added", additionalContents.size());
	}

	public List<AdditionalContentGuiWrapper> getAll() {

		return additionalContents;
	}

	public AdditionalContentGuiWrapper getRow(int rowIndex) {

		if (rowIndex < 0 || rowIndex >= additionalContents.size()) {
			LOG.error("Invalid row index {}.", rowIndex);
			throw new IndexOutOfBoundsException("rowIndex");
		}

		return additionalContents.get(rowIndex);
	}

	public void updateRow(int rowIndex,
			AdditionalContentGuiWrapper additionalContent) {

		if (rowIndex < 0 || rowIndex >= additionalContents.size()) {
			LOG.error("Invalid row index %s.", rowIndex);
			throw new IndexOutOfBoundsException("rowIndex");
		}

		additionalContents.set(rowIndex, additionalContent);
		fireTableRowsUpdated(rowIndex, rowIndex);
		LOG.info("{} additional content has been updated.",
				additionalContent.getType());
	}
}
