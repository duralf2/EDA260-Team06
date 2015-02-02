package register.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import register.logic.Register;
import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

public class RegistrationGUI extends JFrame {

	private StartNumberField startNumberField;
	private EntryList entryTable;
	private Register register;

	public RegistrationGUI(String title, Register register) {
		super(title);

		this.register = register;

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gbl);

		c.insets.set(5, 5, 5, 5);

		int fontSize = fontSize();

		JLabel startNumberLabel = new StartNumberLabel(fontSize);
		startNumberField = new StartNumberField(this, fontSize);
		JButton registerButton = new RegisterButton(this, fontSize);

		entryTable = new EntryList(fontSize, register);

		JScrollPane entryList = new JScrollPane(entryTable);
		TitledBorder titledBorder = new TitledBorder("Registrations");
		titledBorder.setTitleFont(entryList.getFont().deriveFont(Font.BOLD,
				fontSize / 2));
		entryList.setBorder(titledBorder);

		addToGrid(startNumberLabel, gbl, c, 0, 0, 1, 1,
				GridBagConstraints.NONE, 0, 0);
		addToGrid(startNumberField, gbl, c, 1, 0, 1, 1,
				GridBagConstraints.HORIZONTAL, 1, 0);
		addToGrid(registerButton, gbl, c, 2, 0, 1, 1, GridBagConstraints.NONE,
				0, 0);
		addToGrid(entryList, gbl, c, 0, 1, 3, 1, GridBagConstraints.BOTH, 1, 1);

		pack();

		setExtendedState(MAXIMIZED_BOTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addToGrid(JComponent component, GridBagLayout gbl,
			GridBagConstraints c, int x, int y, int width, int height,
			int fill, double wx, double wy) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;
		c.fill = fill;
		c.weightx = wx;
		c.weighty = wy;

		gbl.setConstraints(component, c);
		add(component);
	}

	private int fontSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
				getGraphicsConfiguration());
		int taskBarSize = screenInsets.bottom;
		int fontSize = (screenSize.height - taskBarSize - getHeight()) / 20;
		return fontSize;
	}

	public void register() {
		// TODO: Registrera den inmatade informationen här!
		String startNumber = startNumberField.getText();
		DataStructure ds = register.getDataStructure();
		if (isNumerical(startNumber)) {
			Contestant c = ds.getContestant(startNumber);
			if (c != null) {
				c.addFinishTime(new Time(Time.getCurrentTime()));
			} else {
				Contestant invalidContestant = new Contestant();
				invalidContestant
						.addFinishTime(new Time(Time.getCurrentTime()));
				ds.addContestantEntry(startNumber, invalidContestant);
			}
		} else if (startNumber.equals("x")) {
			Contestant unknown = new Contestant();
			unknown.addFinishTime(new Time(Time.getCurrentTime()));
			ds.addContestantEntry("x", unknown);
		} else if (startNumber.equals("dx")) {
			ds.removeContestant("x");
		} else if (startNumber.startsWith("x=")) {
			String number = startNumber.substring(2);
			if (isNumerical(number)) {
				Contestant c = ds.getContestant(number);
				Contestant x = ds.removeContestant("x");
				if (c != null) {
					c.addFinishTime(x.getFinishTime());
				} else {
					Contestant invalidContestant = new Contestant();
					invalidContestant.addFinishTime(x.getFinishTime());
					ds.addContestantEntry(number, invalidContestant);
				}
			}

		} else {
			JOptionPane.showMessageDialog(null,
					"Invalid startnumber, must be number or x");
		}

		register.writeFinishTimes();
		entryTable.update();

		startNumberField.setText("");
	}

	private boolean isNumerical(String startNumber) {
		return startNumber.matches("[1-9][0-9]*");
	}

}
