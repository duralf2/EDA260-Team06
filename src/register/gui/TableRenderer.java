package register.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import register.logic.TimeRegistrationHandler;
import register.model.ContestantTimes;

/**
 * This class is a custom table renderer used to make the contents of the gui
 * table more informative. Invalid registrations (missing contestants) are
 * rendered with a red background and temporary registrations (the field is set
 * to 'x') are rendered in cyan.
 */
public class TableRenderer extends DefaultTableCellRenderer {

	private TimeRegistrationHandler registrationHandler;

	/**
	 * The constructor for <code>TableRenderer</code>.
	 * 
	 * @param registrationHandler The <code>TimeRegistrationHandler</code>.
	 */
	public TableRenderer(TimeRegistrationHandler registrationHandler) {
		this.registrationHandler = registrationHandler;
	}

	/**
	 * Returns the table cell renderer. During a printing operation,
	 * this method will be called with isSelected and hasFocus values of false
	 * to prevent selection and focus from appearing in the printed output.
	 * 
	 * @param table The JTable
	 * @param value The value to assign to the cell at [row, column]
	 * @param isSelected True if cell is selected
	 * @param hasFocus True if cell has focus
	 * @param row The row of the cell to render
	 * @param column The column of the cell to render
	 * @return The table cell renderer
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component component = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		boolean isRegistered = registrationHandler.isRegistered((String) value);
		if (((String) value).equals("Pre-registered time"))
			component.setBackground(new Color(20, 230, 230));
		else if (column == 0 && (!isRegistered))
			component.setBackground(Color.PINK);
		else
			component.setBackground(table.getBackground());

		return component;
	}
}
