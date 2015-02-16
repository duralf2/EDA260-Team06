package register.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;

import register.logic.TimeRegistrationHandler;

public class PreRegistrationButton extends JButton implements ActionListener {
	private TimeRegistrationHandler registrationHandler;

	public PreRegistrationButton(int fontSize,
			TimeRegistrationHandler registrationHandler) {
		super("Pre-register");
		this.registrationHandler = registrationHandler;
		setToolTipText("Pre-register time");
		setFont(getFont().deriveFont(Font.BOLD, fontSize));
		setForeground(Color.WHITE);
		setBackground(new Color(63, 181, 50));
		setBorder(new BevelBorder(BevelBorder.RAISED));
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		registrationHandler.register("x");
	}
}
