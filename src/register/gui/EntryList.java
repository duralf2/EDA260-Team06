package register.gui;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import register.logic.Register;
import register.model.Contestant;
import register.model.DataStructure;

/**
 * This class represents the table of the gui, it uses a custom renderer to mark
 *  invalid registrations and temporary registrations.
 */
public class EntryList extends JTable {

	private DataStructure ds;

	public EntryList(int fontSize, register.logic.Register register) {
		super(1, 2);
		this.ds = register.getDataStructure();
		setDefaultRenderer(Object.class, new TableRenderer(ds));
		setFillsViewportHeight(true);
		setFont(getFont().deriveFont(Font.BOLD, fontSize));
		getTableHeader().setFont(getFont().deriveFont(Font.BOLD, fontSize/2));
		setRowHeight(fontSize);
		setOpaque(true);
		setToolTipText("Existing registrations");

		try {
			register.readGoalTimes(Register.DEFAULT_RESULT_FILE);
			register.readNames(Register.DEFAULT_NAME_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		updateTableContents();
	}

	
	/**
	 * Updates the contents of this entry list by reloading all the data from the
	 *  data structure.
	 * <br>
	 * <br>
	 * <b>Note:</b> This operation is slow if the database is big
	 */
	public void updateTableContents() {
		Map<String, Contestant> entries = ds.getAllContestantEntries();
		Set<String> keys = entries.keySet();
		String[] header = { "Start number", "Time" };
		List<String[]> rowData = new ArrayList<String[]>();
		for (String key : keys) {
			if (entries.get(key).finishTimeSize() > 0) {
				String currentRow = entries.get(key).getFinishTimes()
						.toString();
				String[] row = new String[2];
				row[0] = key;
				row[1] = currentRow.substring(1, currentRow.length() - 1);
				rowData.add(row);
			}
		}

		DefaultTableModel model = new NonEditableTableModel(
				rowData.toArray(new String[0][0]), header);
		setModel(model);
	}

	/**
	 * A custom table model to stop the table from being editable.
	 */
	private class NonEditableTableModel extends DefaultTableModel {
		public NonEditableTableModel(Object[][] data, Object[] header) {
			super(data, header);
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}
}
