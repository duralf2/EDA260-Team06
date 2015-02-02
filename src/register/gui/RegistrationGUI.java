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
		titledBorder.setTitleFont(entryList.getFont().deriveFont(Font.BOLD, fontSize/2));
		entryList.setBorder(titledBorder);
		
		addToGrid(startNumberLabel, gbl, c, 0, 0, 1, 1, GridBagConstraints.NONE, 0, 0);
		addToGrid(startNumberField, gbl, c, 1, 0, 1, 1, GridBagConstraints.HORIZONTAL, 1, 0);
		addToGrid(registerButton  , gbl, c, 2, 0, 1, 1, GridBagConstraints.NONE, 0, 0);
		addToGrid(entryList	      , gbl, c, 0, 1, 3, 1, GridBagConstraints.BOTH, 1, 1);
		
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
		if(isNumerical(startNumber)){
			
			register.appendToFile(Register.DEFAULT_RESULT_FILE, startNumber);
			try {
				register.clear();
				register.readGoalTimes(Register.DEFAULT_RESULT_FILE);
				entryTable.update();

				if (register.getDataStructure().getContestant(startNumber).getName().equals(""))
				{
					JOptionPane.showMessageDialog(this, "The start number doesn't exist: "+startNumber, "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (IOException ioe) {
				//TODO: exception handling
			}
		}
		startNumberField.setText("");
	}

	private boolean isNumerical(String startNumber){
		return startNumber.matches("[0-9]+");
	}
	
}
