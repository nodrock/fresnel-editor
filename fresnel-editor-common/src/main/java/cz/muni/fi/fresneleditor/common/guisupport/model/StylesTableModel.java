/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.guisupport.model;

import cz.muni.fi.fresneleditor.common.FresnelEditorConstants;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.fresneleditor.common.data.StyleGuiWrapper;
import cz.muni.fi.fresneleditor.common.data.StyleType;
import java.util.ResourceBundle;

/**
 * Class which serves as table model for Format styles table.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 22.3.2009
 */
public class StylesTableModel extends AbstractTableModel {

	private static final ResourceBundle bundle = ResourceBundle
			.getBundle(FresnelEditorConstants.PATH_RESOURCE_BUNDLE_COMMON);

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory
			.getLogger(StylesTableModel.class);

	private static int COLUMN_COUNT = 3;
	private static String[] COLUMN_NAMES = { bundle.getString("Style_type"),
			bundle.getString("CSS_style_type"), bundle.getString("Value") };

	private List<StyleGuiWrapper> styles = new ArrayList<StyleGuiWrapper>();

	@Override
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public int getRowCount() {

		if (styles == null) {
			return 0;
		}

		return styles.size();
	}

	@Override
	public String getColumnName(int columnIndex) {

		if (columnIndex < 0 || columnIndex >= COLUMN_COUNT) {
			LOG.error("Invalid column index {}.", columnIndex);
			throw new IndexOutOfBoundsException("columnIndex");
		}

		return COLUMN_NAMES[columnIndex];
	}

	@Override
	public Object getValueAt(int row, int col) {

		if (col < 0 || col > (COLUMN_COUNT - 1)) {
			LOG.error("Invalid column index {}.", col);
			throw new IndexOutOfBoundsException("col");
		}

		if (row < 0 || row >= styles.size()) {
			LOG.error("Invalid row index {}.", row);
			throw new IndexOutOfBoundsException("row");
		}

		StyleGuiWrapper style = styles.get(row);

		switch (col) {
		case 0:
			return style.getType();
		case 1:
			return style.getValueType();
		case 2:
			return style.getValue();
		default:
			throw new IndexOutOfBoundsException("col");
		}
	}

	public void deleteRow(int selectedRowIndex) {

		if (selectedRowIndex < 0 || selectedRowIndex >= styles.size()) {
			LOG.error("Invalid selected row index {}.", selectedRowIndex);
			throw new IndexOutOfBoundsException("selectedRowIndex");
		}

		styles.remove(selectedRowIndex);
		fireTableRowsDeleted(selectedRowIndex, selectedRowIndex);

		LOG.info("Style has been deleted.");
	}

	public boolean addRow(StyleGuiWrapper style) {

		if (style == null) {
			LOG.error("Style which should be added is null.");
			throw new NullPointerException("style");
		}

		if (containsStyleType(style.getType())) {
			LOG.error("Cannot add another style of type {}.", style.getType());
			return false;
		}

		styles.add(style);
		fireTableRowsInserted(styles.size() - 1, styles.size() - 1);

		LOG.info("{} style has been added.", style.getType());

		return true;
	}

	public void addAll(List<StyleGuiWrapper> styles) {

		if (styles == null) {
			LOG.error("List of styles which should be added is null.");
			throw new NullPointerException("styles");
		}

		int countOfAddedStyles = 0;

		for (StyleGuiWrapper style : styles) {

			if (addRow(style)) {
				countOfAddedStyles++;
			}
		}

		fireTableDataChanged();

		LOG.info("Summary: {} styles have been added.", countOfAddedStyles);
	}

	public List<StyleGuiWrapper> getAll() {

		return styles;
	}

	public StyleGuiWrapper getRow(int rowIndex) {

		if (rowIndex < 0 || rowIndex >= styles.size()) {
			LOG.error("Invalid row index {}.", rowIndex);
			throw new IndexOutOfBoundsException("rowIndex");
		}

		return styles.get(rowIndex);
	}

	public void updateRow(int rowIndex, StyleGuiWrapper style) {

		if (rowIndex < 0 || rowIndex >= styles.size()) {
			LOG.error("Invalid row index {}.", rowIndex);
			throw new IndexOutOfBoundsException("rowIndex");
		}

		styles.set(rowIndex, style);
		fireTableRowsUpdated(rowIndex, rowIndex);
		LOG.info("{} style has been updated.", style.getType());
	}

	private boolean containsStyleType(StyleType styleType) {

		for (StyleGuiWrapper style : styles) {

			if (style.getType() == styleType) {
				return true;
			}
		}

		return false;
	}
}
