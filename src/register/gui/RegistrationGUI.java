
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

	
	public static void main(String[] args)
	{
		RegistrationGUI screen = new RegistrationGUI("Registration window");
	}
	
	public RegistrationGUI(String registrationString)
	{
		super(registrationString);
		addGUIComponents();
	}
	
	public JPanel addButtonAndFields()
	{
		JPanel panel = new JPanel();
		JPanel panelTop = new JPanel();
		JPanel panelBot = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panelBot.setLayout(new BoxLayout(panelBot, BoxLayout.Y_AXIS));
		panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));
		
		JLabel startNumberLabel = new JLabel("Start number:");
		startNumberLabel.setFont(new Font("MyriadPro", Font.BOLD, 55));
		JTextField startNumberField = new JTextField(registerText);
		startNumberField.setToolTipText("Input start number.");
		startNumberField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		startNumberField.setFont(new Font("MyriadPro", Font.PLAIN, 55));
		
		
		JLabel nameLabel = new JLabel("Contestant name:");
		nameLabel.setFont(new Font("MyriadPro", Font.BOLD, 55));
		JTextField nameField = new JTextField(registerText);
		nameField.setToolTipText("Input contestant name.");
		nameField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		nameField.setFont(new Font("MyriadPro", Font.PLAIN, 55));
		
		
		JLabel startTimeLabel = new JLabel("Start time:");
		startTimeLabel.setFont(new Font("MyriadPro", Font.BOLD, 55));
		JTextField startTimeField = new JTextField(registerText);
		startTimeField.setToolTipText("Input start time.");
		startTimeField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		startTimeField.setFont(new Font("MyriadPro", Font.PLAIN, 55));
		
		JLabel finishTimeLabel = new JLabel("Finish time:");
		finishTimeLabel.setFont(new Font("MyriadPro", Font.BOLD, 55));
		JTextField finishTimeField = new JTextField(registerText);
		finishTimeField.setToolTipText("Input finish number.");
		finishTimeField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		finishTimeField.setFont(new Font("MyriadPro", Font.PLAIN, 55));
		
		
		JButton registerButton = new JButton("Register");
		registerButton.setToolTipText("Register entry.");
		registerButton.setFont(new Font("MyriadPro", Font.BOLD, 55));
		registerButton.setForeground(Color.WHITE);
		registerButton.setBackground(new Color(63, 181, 50));
		registerButton.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		registerButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		panelTop.add(startNumberLabel);
		panelTop.add(startNumberField);
		panelTop.add(nameLabel);
		panelTop.add(nameField);
		panelTop.add(startTimeLabel);
		panelTop.add(startTimeField);
		panelTop.add(finishTimeLabel);
		panelTop.add(finishTimeField);
		panelBot.add(registerButton);
		panel.add(panelTop);
		panel.add(panelBot);
		return panel;
	}
	
	public void addGUIComponents()
	{
		
		
		Container c = (Container)getContentPane();
		//c.setLayout(new FlowLayout(FlowLayout.LEFT));
		c.setLayout(new BoxLayout(c, BoxLayout.X_AXIS));
		
		c.setBackground(new Color(243,243,243));
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		c.add(leftPanel);
		c.add(new JSeparator(JSeparator.VERTICAL),BorderLayout.LINE_START);
		c.add(rightPanel);
		
//		leftPanel.add(registerField);
//		registerField.setPreferredSize(new Dimension(750, 100));

//		registerField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
//		
//		
//		leftPanel.add(registerButton);
//		registerButton.setPreferredSize(new Dimension(500, 100));
//		

//	
		JScrollPane entryList = new JScrollPane();
		rightPanel.add(entryList);
		entryList.setOpaque(true);
		entryList.setPreferredSize(new Dimension(800, 1000));
		entryList.getViewport().setBackground(new Color(173, 193, 214));
		entryList.setBorder(new TitledBorder("REGISTRATIONS"));
		entryList.setToolTipText("Old registrations.");
		entryList.setFont(new Font("MyriadPro", Font.PLAIN, 10));
		
		leftPanel.add(addButtonAndFields());
		
		pack();
		getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		this.setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	private static final long serialVersionUID = -307826388596514302L;
}
