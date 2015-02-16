package register.gui;

import io.ReadFile;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import register.logic.TimeRegistrationHandler;
import register.model.ContestantTimes;
import register.model.PreRegistrationDialog;

/**
 * This class represents the table of the gui, it uses a custom renderer to mark
 * invalid registrations and temporary registrations.
 */
public class EntryList extends JTable implements Observer {

	private TimeRegistrationHandler registrationHandler;

	public EntryList(int fontSize, TimeRegistrationHandler registrationHandler) {
		super(1, 2);
		this.registrationHandler = registrationHandler;
		registrationHandler.observContestantTimes(this);
		setDefaultRenderer(Object.class, new TableRenderer(registrationHandler));
		setFillsViewportHeight(true);
		setFont(getFont().deriveFont(Font.BOLD, fontSize));
		setRowHeight(fontSize);
		setOpaque(true);
		setToolTipText("Old registrations.");
		update(null, null);
	}

	public void update(Observable arg0, Object arg1) {
		Map<String, ArrayList<String>> timeEntries = registrationHandler
				.getAllRegisteredTimes();
		String[] header = { "Start number", "Time" };
		Set<Entry<String, ArrayList<String>>> entries = timeEntries.entrySet();
		List<String[]> rowData = new ArrayList<String[]>();
		for (Entry<String, ArrayList<String>> e : entries) {
			ArrayList<String> entryTimes = e.getValue();
			if (entryTimes.size() > 0) {
				String currentRow = timesAsString(entryTimes);
				String[] row = new String[2];
				String startNumber = e.getKey();
				if (startNumber.equals("x"))
					startNumber = "Pre-registered time";
				row[0] = startNumber;
				row[1] = currentRow;
				rowData.add(row);
			}
		}

		DefaultTableModel model = new NonEditableTableModel(
				rowData.toArray(new String[0][0]), header);
		setModel(model);
		getSelectionModel().addListSelectionListener(
				new PreRegistrationEditListener());
	}

	private String timesAsString(ArrayList<String> entryTimes) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < entryTimes.size(); i++) {
			sb.append(entryTimes.get(i));
			if (i != entryTimes.size() - 1)
				sb.append(", ");

		}

		return sb.toString();
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

	// TODO - Add code to update model
	private class PreRegistrationEditListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selectedRow = getSelectedRow();
			if (selectedRow != -1) {
				String startNumber = (String) getValueAt(selectedRow, 0);
				clearSelection();
				if (startNumber.equals("Pre-registered time")) {
					PreRegistrationDialog t = new PreRegistrationDialog();
					System.out.println(t.getOption());
					if (t.getOption()==PreRegistrationDialog.REGISTER_OPTION){
						String startNumberInput = t.getStartNumber();
						boolean isValid = registrationHandler.register("x="
								+ startNumberInput);
						if (!isValid) {
							JOptionPane.showMessageDialog(null,
								registrationHandler.getLastError());
						}
					}else if (t.getOption()==PreRegistrationDialog.REMOVE_OPTION){
						registrationHandler.register("dx");
					}
				}
			}
		}

	}

}
