package register.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import register.logic.TimeRegistrationHandler;
import register.model.ContestantTimes;
import register.model.Time;

/**
 * This class represents the program frame and is the core of the gui.
 */
public class RegistrationGUI extends JFrame {

	private StartNumberField startNumberField;
	private EntryList entryTable;

	/**
	 * Constructor for <code>RegistrationGUI</code>.
	 * 
	 * @param title Name of window.
	 * @param registrationHandler The <code>TimeRegistrationHandler</code>.
	 */
	public RegistrationGUI(String title,
			TimeRegistrationHandler registrationHandler) {
		super(title);

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gbl);

		c.insets.set(5, 5, 5, 5);

		int fontSize = fontSize();

		JLabel startNumberLabel = new StartNumberLabel(fontSize);
		startNumberField = new StartNumberField(fontSize, registrationHandler);
		JButton registerButton = new RegisterButton(fontSize,
				registrationHandler, startNumberField);
		entryTable = new EntryList(fontSize, registrationHandler);

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

}
