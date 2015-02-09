package register.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class StartNumberField extends JTextField implements ActionListener {
	private RegistrationGUI gui;

	public StartNumberField(RegistrationGUI gui, int fontSize) {
		super (5);
		this.gui = gui;
		addActionListener(this);
		setToolTipText("Input start number");
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		setFont(getFont().deriveFont(Font.PLAIN, fontSize));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.register();
	}
}
