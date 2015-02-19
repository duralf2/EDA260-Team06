package register.gui;

import java.awt.Font;

import javax.swing.JLabel;

public class StartNumberLabel extends JLabel {
	/**
	 * The startnumber label for the <code>RegistrationGUI</code>.
	 * 
	 * @param fontSize The wanted size of the font.
	 */
	public StartNumberLabel(int fontSize) {
		super ("Start number:");
		setFont(getFont().deriveFont(Font.BOLD, fontSize));
	}
}
