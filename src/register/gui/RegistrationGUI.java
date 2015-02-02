package register.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

public class RegistrationGUI extends JFrame {

	private StartNumberField startNumberField;

	
	public RegistrationGUI(String registrationString) {
		super(registrationString);

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gbl);

		c.insets.set(5, 5, 5, 5);
		
		int fontSize = fontSize();
		
		JLabel startNumberLabel = new StartNumberLabel(fontSize);
		startNumberField = new StartNumberField(this, fontSize);
		JButton registerButton = new RegisterButton(this, fontSize);
		
		JScrollPane entryList = new JScrollPane(new EntryList(fontSize));
		TitledBorder titledBorder = new TitledBorder("Registrations");
		titledBorder.setTitleFont(entryList.getFont().deriveFont(Font.BOLD, fontSize/2));
		entryList.setBorder(titledBorder);
		

		
		addToGrid(startNumberLabel, gbl, c, 0, 0, 1, 1, GridBagConstraints.NONE, 0, 0);
		addToGrid(startNumberField, gbl, c, 1, 0, 1, 1, GridBagConstraints.HORIZONTAL, 1, 0);
		addToGrid(registerButton  , gbl, c, 2, 0, 1, 1, GridBagConstraints.NONE, 0, 0);
		addToGrid(entryList	      , gbl, c, 0, 1, 3, 1, GridBagConstraints.BOTH, 1, 1);
		
		pack();
		setMinimumSize(getSize());
		
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
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(
				getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		int fontSize = ((screenSize.width - getWidth()) + (screenSize.height
				- taskBarSize - getHeight())) / 60;
		return fontSize;
	}

	
	public void register() {

		// TODO: Registrera den inmatade informationen här!
		
		startNumberField.setText("");
	}

	public static void main(String[] args) {
		RegistrationGUI screen = new RegistrationGUI("Registration window");
	}
}
