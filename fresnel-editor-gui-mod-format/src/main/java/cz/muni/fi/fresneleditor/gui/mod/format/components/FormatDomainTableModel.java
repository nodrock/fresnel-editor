/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.format.components;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.fresneleditor.gui.mod.format.data.DomainSelectorGuiWrapper;

/**
 * Class which serves as table model for Format domain table.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 21.3.2009
 */
public class FormatDomainTableModel extends AbstractTableModel {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory
			.getLogger(FormatDomainTableModel.class);
	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("cz/muni/fi/fresneleditor/gui/mod/format/resource-bundle-format");

	private static int COLUMN_COUNT = 3;
	private static String[] COLUMN_NAMES = {
			bundle.getString("Selector_domain"),
			bundle.getString("Selector_type"),
			bundle.getString("Selector_string") };

	private List<DomainSelectorGuiWrapper> domainSelectors = new ArrayList<DomainSelectorGuiWrapper>();

	@Override
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public int getRowCount() {
		return domainSelectors.size();
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

		if (row < 0 || row >= domainSelectors.size()) {
			LOG.error("Invalid row index {}.", row);
			throw new IndexOutOfBoundsException("row");
		}

		DomainSelectorGuiWrapper domainSelector = domainSelectors.get(row);

		switch (col) {
		case 0:
			return domainSelector.getDomainType();
		case 1:
			return domainSelector.getSelectorType();
		case 2:
			// Note: "..." handling is automatic
			return domainSelector.getSelectorString();
		default:
			throw new IndexOutOfBoundsException("col");
		}
	}

	public void deleteRow(int selectedRowIndex) {

		if (selectedRowIndex < 0 || selectedRowIndex >= domainSelectors.size()) {
			LOG.error("Invalid selected row index {}.", selectedRowIndex);
			throw new IndexOutOfBoundsException("selectedRowIndex");
		}

		domainSelectors.remove(selectedRowIndex);
		fireTableRowsDeleted(selectedRowIndex, selectedRowIndex);
		LOG.info("Domain selector has been deleted.");
	}

	public void addRow(DomainSelectorGuiWrapper domainSelector) {

		if (domainSelector == null) {
			LOG.error("Domain selector which should be added is null!");
			throw new NullPointerException("domainSelector");
		}

		domainSelectors.add(domainSelector);
		fireTableRowsInserted(domainSelectors.size() - 1,
				domainSelectors.size() - 1);
		LOG.info("{} format domain selector has been added.",
				domainSelector.getSelectorType());
	}

	public void addAll(List<DomainSelectorGuiWrapper> domainSelectors) {

		if (domainSelectors == null) {
			LOG.error("List of domain selectors which should be added is null!");
			throw new NullPointerException("domainSelectors");
		}

		this.domainSelectors.addAll(domainSelectors);
		fireTableDataChanged();
		LOG.info("{} domain selectors have been added.", domainSelectors.size());
	}

	public List<DomainSelectorGuiWrapper> getAll() {

		return domainSelectors;
	}

	public DomainSelectorGuiWrapper getRow(int rowIndex) {

		return domainSelectors.get(rowIndex);
	}

	public void updateRow(int rowIndex, DomainSelectorGuiWrapper domainSelector) {

		if (rowIndex < 0 || rowIndex >= domainSelectors.size()) {
			LOG.error("Invalid row index {}.", rowIndex);
			throw new IndexOutOfBoundsException("rowIndex");
		}

		domainSelectors.set(rowIndex, domainSelector);
		fireTableRowsUpdated(rowIndex, rowIndex);
		LOG.info("{} domain selector has been updated.",
				domainSelector.getDomainType());
	}
}
