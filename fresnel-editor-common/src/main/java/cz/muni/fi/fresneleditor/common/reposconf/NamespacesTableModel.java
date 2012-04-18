/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.common.reposconf;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.openrdf.model.Namespace;

/**
 * @author Igor Zemsky (zemsky@mail.muni.cz), Miroslav Warchil
 *         (warmir@mail.muni.cz)
 */
public class NamespacesTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static enum Columns {
		PREFIX, NAME
	}

	private List<Namespace> rows = new ArrayList<Namespace>();

	@Override
	public int getColumnCount() {
		return Columns.values().length;
	}

	public String getColumnName(int col) {
		switch (Columns.values()[col]) {
		case NAME:
			return "Name";
		case PREFIX:
			return "Prefix";
		default:
			throw new ArrayIndexOutOfBoundsException("Column index: " + col);
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (Columns.values()[column]) {
		case NAME:
			return rows.get(row).getName();
		case PREFIX:
			return rows.get(row).getPrefix();
		default:
			throw new ArrayIndexOutOfBoundsException("Column index: " + column);
		}
	}

	public void setRows(List<Namespace> rows) {
		this.rows = rows;
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	public Namespace getRow(int row) {
		return rows.get(row);
	}

}
