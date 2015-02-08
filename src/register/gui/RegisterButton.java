package register.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.BevelBorder;

public class RegisterButton extends JButton implements ActionListener{
	private RegistrationGUI gui;
	
	public RegisterButton(RegistrationGUI gui, int fontSize) {
		super ("Register");
		this.gui = gui;
		addActionListener(this);
		setToolTipText("Register entry.");
		setFont(getFont().deriveFont(Font.BOLD, fontSize));
		setForeground(Color.WHITE);
		setBackground(new Color(63, 181, 50));
		setBorder(new BevelBorder(BevelBorder.RAISED));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.register();
	}
}
