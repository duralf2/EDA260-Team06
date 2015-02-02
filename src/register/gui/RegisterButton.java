package register.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class RegisterButton extends JButton {
	public RegisterButton(RegistrationGUI gui, int fontSize) {
		super ("Register");
		
		setToolTipText("Register entry.");
		setFont(getFont().deriveFont(Font.BOLD, fontSize));
		setForeground(Color.WHITE);
		setBackground(new Color(63, 181, 50));
		setBorder(new BevelBorder(BevelBorder.RAISED));
	}
	
	
}
