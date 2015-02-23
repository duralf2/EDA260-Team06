package gui;

import gui.model.TimeRegistrationHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.BevelBorder;


public class PreRegistrationButton extends JButton implements ActionListener {
	private TimeRegistrationHandler registrationHandler;
	private int fontSize;

	/**
	 * The pre-register button for the <code>RegistrationGUI</code>.
	 * 
	 * @param fontSize The wanted size of the font.
	 * @param registrationHandler The <code>TimeRegistrationHandler</code>.
	 */
	public PreRegistrationButton(int fontSize, TimeRegistrationHandler registrationHandler) {
		super("Pre-register");
		this.fontSize = fontSize;
		this.registrationHandler = registrationHandler;
		setToolTipText("Pre-register time");
		setFont(getFont().deriveFont(Font.BOLD, fontSize));
		setForeground(Color.WHITE);
		setBackground(new Color(63, 181, 50));
		setOpaque(true);
		setBorder(new BevelBorder(BevelBorder.RAISED));
		addActionListener(this);
	}

	/**
	 * Pre-register a <code>contestant</code>.
	 * 
	 * @param e The action performed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(registrationHandler.hasPreRegistered())
		{
			PreRegistrationPopUp popUp = new PreRegistrationPopUp(registrationHandler, fontSize);
		}else{
			registrationHandler.preRegister();
		}
	}
}
