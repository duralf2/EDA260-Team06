package register.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.IOException;

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

/**
 * This class represents the program frame and is the core of the gui.
 */
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

	/**
	 * Reads the contents of the text field and tries to make a registration if
	 * the input is valid. This method handles normal registration, mass start
	 * registrations and temporary registrations.
	 */
	public void register() {
		// TODO: Model-View-Control plz? Also change the name, this method
		// doesn't only handle registrations anymore
		String startNumber = startNumberField.getText();
		DataStructure ds = register.getDataStructure();
		if (isNumerical(startNumber)) {
			registerContestantTime(startNumber, ds);
		} else if (startNumber.equals("*")) {
			registerMassStart();
		} else if (isPreRegistration(startNumber)) {
			preRegister(startNumber, ds);
		} else {
			JOptionPane.showMessageDialog(null,
					"Invalid startnumber, must be a number or x.");
		}

		register.writeFinishTimes();
		entryTable.update();

		startNumberField.setText("");
	}

	private void preRegister(String input, DataStructure ds) {
		if (input.equals("x")) {
			Contestant unknown = new Contestant();
			unknown.addFinishTime(new Time(Time.getCurrentTime()));
			ds.addContestantEntry("x", unknown);
		} else if (input.equals("dx")) {
			ds.removeContestant("x");
		} else if (input.startsWith("x=")) {
			assignStartNumberToPreRegistration(input, ds);
		}
	}

	private void assignStartNumberToPreRegistration(String input,
			DataStructure ds) {
		String number = input.substring(2);
		if (isNumerical(number)) {
			Contestant c = ds.getContestant(number);
			Contestant x = ds.removeContestant("x");
			if (x == null) {
				JOptionPane.showMessageDialog(null,
						"No pregesistered time set.");
			} else {
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
					"Invalid startnumber, must be a number.");
		}
	}

	private boolean isPreRegistration(String input) {
		return input.startsWith("x") || input.equals("dx");
	}

	private void registerContestantTime(String startNumber, DataStructure ds) {
		Contestant c = ds.getContestant(startNumber);
		if (c != null) {
			c.addFinishTime(new Time(Time.getCurrentTime()));
		} else {
			Contestant invalidContestant = new Contestant();
			invalidContestant.addFinishTime(new Time(Time.getCurrentTime()));
			ds.addContestantEntry(startNumber, invalidContestant);
		} 
	}

	private void registerMassStart() {
		register.performMassStart(Register.DEFAULT_RESULT_FILE);
		try {
			refreshEntryList();
		} catch (IOException e) {
			// TODO: exception handling
			e.printStackTrace();
		}
	}

	private boolean isNumerical(String startNumber) {
		return startNumber.matches("[1-9][0-9]*");
	}

	private void refreshEntryList() throws IOException {
		register.getDataStructure().clearContestantEntries(); // TODO RegGui;
																// Add the new
																// time directly
																// to the
																// datastructure
																// instead of
																// clearing it
																// and reading
																// it all from a
																// file
		register.readGoalTimes(Register.DEFAULT_RESULT_FILE);
		register.readNames(Register.DEFAULT_NAME_FILE);
		entryTable.update();
	}

}
