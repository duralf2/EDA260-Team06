package register.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import register.model.Contestant;
import register.model.DataStructure;

/**
 * This class is a custom table renderer used to make the contents of the gui
 *  table more informative. Invalid registrations (missing contestants) are
 *  rendered with a red background and temporary registrations (the field is
 *  set to 'x') are rendered in cyan.
 */
public class TableRenderer extends DefaultTableCellRenderer {

	private DataStructure data;

	public TableRenderer(DataStructure data) {
		this.data = data;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component component = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		Contestant contestant = data.getContestant((String) value);
		if (((String) value).equals("x"))
			component.setBackground(new Color(20,230,230));
		else if (column == 0
				&& (contestant == null || contestant.getName().equals("")))
			component.setBackground(Color.PINK);
		else
			component.setBackground(table.getBackground());

		return component;
	}
}
