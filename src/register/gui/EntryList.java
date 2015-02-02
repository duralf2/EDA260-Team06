package register.gui;

import java.awt.Font;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import register.logic.Register;
import register.model.Contestant;
import register.model.DataStructure;

public class EntryList extends JTable {
	
	private DataStructure ds;
	
	public EntryList(int fontSize, register.logic.Register register) {
		super(1, 2);
		setFillsViewportHeight(true);
		setFont(new Font("Arial", Font.BOLD, fontSize));
		setRowHeight(fontSize);
		setOpaque(true);
		setToolTipText("Old registrations.");
		this.ds = register.getDataStructure();
		try {
			register.readGoalTimes(Register.DEFAULT_RESULT_FILE);
		} catch (IOException e) {
			//TODO: exception handling
		}
		update();
	}
	
	// TODO:
	public void update(){
		Map<String, Contestant> entries = ds.getAllContestantEntries();
		Set<String> keys = entries.keySet();
		String[] header = {"Startnummer", "Måltid"};
		String[][] rowData = new String[keys.size()][2];
		int i = 0;
		for (String key : keys) {
			rowData[i][0] = key;
			rowData[i][1] = entries.get(key).getFinishTime().toString();
			i++;
		}
		DefaultTableModel model = new DefaultTableModel(rowData, header);
		setModel(model);
		
	}
}
