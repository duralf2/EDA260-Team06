package register.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import register.model.Contestant;
import register.model.DataStructure;

public class TableRenderer extends DefaultTableCellRenderer {
	
	private DataStructure data;
	
	public TableRenderer(DataStructure data) {
		this.data = data;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		
		Contestant contestant = data.getContestant((String)value);
		if (contestant == null || contestant.getName().equals(""))
			component.setBackground(Color.RED);
		
		return component;
	}
}