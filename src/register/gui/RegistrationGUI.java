
package register.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

public class RegistrationGUI extends JFrame
{
	private String registerText;
	private JButton registerButton = new JButton("Register");
	private JTextField registerField = new JTextField(registerText);
	private JScrollPane entryList = new JScrollPane();
	
	public static void main(String[] args)
	{
		RegistrationGUI screen = new RegistrationGUI("Registration window");
	}
	
	public RegistrationGUI(String registrationString)
	{
		super(registrationString);
		addGUIComponents();
	}
	
	public void addGUIComponents()
	{
		Container c = (Container)getContentPane();
		c.setLayout(new FlowLayout(FlowLayout.LEFT));
		c.setBackground(new Color(243,243,243));
		
		c.add(registerField);
		registerField.setPreferredSize(new Dimension(750, 100));
		registerField.setFont(new Font("MyriadPro", Font.PLAIN, 50));
		registerField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		registerField.setToolTipText("Input registration data.");
		
		c.add(registerButton);
		registerButton.setPreferredSize(new Dimension(500, 100));
		registerButton.setFont(new Font("MyriadPro", Font.BOLD, 55));
		registerButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		registerButton.setToolTipText("Validate entry.");
		registerButton.setForeground(Color.WHITE);
		registerButton.setBackground(new Color(63, 181, 50));
	
		c.add(entryList);
		entryList.setOpaque(true);
		entryList.setPreferredSize(new Dimension(600, 1000));
		entryList.getViewport().setBackground(new Color(173, 193, 214));
		entryList.setBorder(new TitledBorder("REGISTRATIONS"));
		entryList.setToolTipText("Old registrations.");
		entryList.setFont(new Font("MyriadPro", Font.PLAIN, 10));
		
		pack();
		getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		this.setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	private static final long serialVersionUID = -307826388596514302L;
}
