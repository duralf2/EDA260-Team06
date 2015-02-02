package register.gui;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class StartNumberField extends JTextField {
	public StartNumberField(RegistrationGUI gui ,int fontSize) {
		
		setToolTipText("Input start number.");
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		setFont(getFont().deriveFont(Font.PLAIN, fontSize));
	}
}
