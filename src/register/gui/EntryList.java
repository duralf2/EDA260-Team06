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

public class EntryList extends JTable {
	
	private DataStructure ds;
	
	public EntryList(int fontSize, register.logic.Register register) {
		super(1, 2);
		this.ds = register.getDataStructure();
		setDefaultRenderer(Object.class, new TableRenderer(ds));
		setFillsViewportHeight(true);
		setFont(new Font("Arial", Font.BOLD, fontSize));
		setRowHeight(fontSize);
		setOpaque(true);
		setToolTipText("Old registrations.");
		
		try {
			register.readGoalTimes(Register.DEFAULT_RESULT_FILE);
			register.readNames(Register.DEFAULT_NAME_FILE);
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
		List<String[]> rowData = new ArrayList<String[]>();
		int i = 0;
		for (String key : keys) {
			System.out.println("k: " + key);
			System.out.println(entries.get(key).finishTimeSize());
			if (entries.get(key).finishTimeSize() > 0)
			{
				String[] row = new String[2];
				row[0] = key;
				row[1] = entries.get(key).getFinishTime().toString();
				rowData.add(row);
				i++;
			}
		}
		DefaultTableModel model = new DefaultTableModel(rowData.toArray(new String[0][0]), header);
		setModel(model);
		
	}
}
