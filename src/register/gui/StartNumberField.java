package register.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import register.logic.TimeRegistrationHandler;

public class StartNumberField extends JTextField implements ActionListener {
	private TimeRegistrationHandler registrationHandler;

	/**
	 * The startnumber field for the <code>RegistrationGUI</code>.
	 * 
	 * @param fontSize The wanted size of the font.
	 * @param registrationHandler The <code>TimeRegistrationHandler</code>.
	 */
	public StartNumberField(int fontSize,
			TimeRegistrationHandler registrationHandler) {
		this.registrationHandler = registrationHandler;
		addActionListener(this);
		setToolTipText("Input start number.");
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		setFont(getFont().deriveFont(Font.PLAIN, fontSize));
	}

	/**
	 * Empties the field when registered a <code>contestant</code>.
	 * 
	 * @param arg0 The action performed.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		boolean isValid = registrationHandler.register(getText());
		if (!isValid) {
			JOptionPane.showMessageDialog(null,
					registrationHandler.getLastError());
		}
		setText("");
	}
}
