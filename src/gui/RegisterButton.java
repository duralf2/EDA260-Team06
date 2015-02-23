package gui;

import gui.model.TimeRegistrationHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;

public class RegisterButton extends JButton implements ActionListener{
	private TimeRegistrationHandler registrationHandler;
	private StartNumberField startNumberField;

	/**
	 * The register button for the <code>RegistrationGUI</code>.
	 * 
	 * @param fontSize The wanted size of the font.
	 * @param registrationHandler The <code>TimeRegistrationHandler</code>.
	 * @param startNumberField The <code>StartNumberField</code>
	 */
	public RegisterButton(int fontSize, TimeRegistrationHandler registrationHandler, StartNumberField startNumberField) {
		super ("Register");
		this.registrationHandler = registrationHandler;
		this.startNumberField = startNumberField;
		addActionListener(this);
		setToolTipText("Register entry.");
		setFont(getFont().deriveFont(Font.BOLD, fontSize));
		setForeground(Color.WHITE);
		setBackground(new Color(63, 181, 50));
		setOpaque(true);
		setBorder(new BevelBorder(BevelBorder.RAISED));
	}

	/**
	 * Empties the field when registered a <code>contestant</code>.
	 * 
	 * @param e The action performed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean isValid = registrationHandler.register(startNumberField.getText());
		if(!isValid) {
			JOptionPane.showMessageDialog(null, registrationHandler.getLastError());
		}
		startNumberField.setText("");
	}
}
