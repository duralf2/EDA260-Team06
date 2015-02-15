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
	public StartNumberField(int fontSize, TimeRegistrationHandler registrationHandler) {
		this.registrationHandler = registrationHandler;
		addActionListener(this);
		setToolTipText("Input start number.");
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		setFont(getFont().deriveFont(Font.PLAIN, fontSize));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		boolean isValid = registrationHandler.register(getText());
		if(!isValid) {
			JOptionPane.showMessageDialog(null, registrationHandler.getLastError());
		}
		setText("");
	}
}
