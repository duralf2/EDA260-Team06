package register.model;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PreRegistrationDialog extends JDialog implements ActionListener {
	private JLabel startNumberLabel;
	private JTextField startNumberField;
	private JButton register;
	private JButton removePreRegister;
	public static final int REGISTER_OPTION = 0;
	public static final int REMOVE_OPTION = 1;
	private int option;

	public PreRegistrationDialog() {
		setTitle("Pre-register");
		setModal(true);
		option = -1;


		startNumberLabel = new JLabel("Enter startnumber");
		startNumberField = new JTextField();
		register = new JButton("Register");
		removePreRegister = new JButton("Remove pre-registration");
		startNumberField.setColumns(10);
		register.addActionListener(this);
		removePreRegister.addActionListener(this);
		setLayout(new GridLayout(2, 2));

		add(startNumberLabel);
		add(startNumberField);
		add(removePreRegister);
		add(register);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);

		setVisible(true);
	}

	public int getOption() {
		return option;
	}

	public String getStartNumber() {
		return startNumberField.getText();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == register) {
			option = REGISTER_OPTION;

		} else {
			option = REMOVE_OPTION;
		}
		setVisible(false);
		dispose();
	}

}