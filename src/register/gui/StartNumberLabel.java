package register.gui;

import java.awt.Font;

import javax.swing.JLabel;

public class StartNumberLabel extends JLabel {
	public StartNumberLabel(int fontSize) {
		super ("Start number:");
		setFont(getFont().deriveFont(Font.BOLD, fontSize));
	}
}
