package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class PreRegistrationDialog extends JDialog implements ActionListener {
	private JLabel startNumberLabel;
	private JTextField startNumberField;
	private JButton register;
	private JButton removePreRegister;
	public static final int REGISTER_OPTION = 0;
	public static final int REMOVE_OPTION = 1;
	private int option;

	public PreRegistrationDialog(int fontSize) {
		setTitle("Edit pre-registered time");
		setModal(true);
		option = -1;

		startNumberLabel = new JLabel("Enter startnumber");
		startNumberField = new JTextField(10);
		register = new JButton("Register");
		removePreRegister = new JButton("Delete");
		register.addActionListener(this);
		removePreRegister.addActionListener(this);
		
		startNumberLabel.setFont(startNumberLabel.getFont().deriveFont(Font.BOLD, fontSize/2));
		startNumberField.setFont(startNumberField.getFont().deriveFont(Font.BOLD, fontSize/2));
		
		register.setForeground(Color.WHITE);
		register.setBackground(new Color(63, 181, 50));
		register.setBorder(new BevelBorder(BevelBorder.RAISED));
		register.setFont(register.getFont().deriveFont(Font.BOLD, fontSize/2));
		
		removePreRegister.setForeground(Color.WHITE);
		removePreRegister.setBackground(new Color(243, 83, 83));
		removePreRegister.setBorder(new BevelBorder(BevelBorder.RAISED));
		removePreRegister.setFont(removePreRegister.getFont().deriveFont(Font.BOLD, fontSize/2));
		
		JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.add(startNumberLabel);
		panel.add(startNumberField);
		panel.add(removePreRegister);
		panel.add(register);
		
		add(panel);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(screenSize.width / 3, screenSize.height / 6);
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