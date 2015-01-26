
package register.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
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
		int fontSize = fontSize();
		
		JPanel panel = new JPanel();
		JPanel panelTop = new JPanel();
		JPanel panelBot = new JPanel();
		
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panelBot.setLayout(new BoxLayout(panelBot, BoxLayout.Y_AXIS));
		panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));
		
		JLabel startNumberLabel = new JLabel("Start number:");
		startNumberLabel.setFont(new Font("MyriadPro", Font.BOLD, fontSize));
		JTextField startNumberField = new JTextField(registerText);
		startNumberField.setToolTipText("Input start number.");
		startNumberField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		startNumberField.setFont(new Font("MyriadPro", Font.PLAIN, fontSize));
		
		JLabel nameLabel = new JLabel("Contestant name:");
		nameLabel.setFont(new Font("MyriadPro", Font.BOLD, fontSize));
		final JTextField nameField = new JTextField(registerText);
		nameField.setToolTipText("Input contestant name.");
		nameField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		nameField.setFont(new Font("MyriadPro", Font.PLAIN, fontSize));
		
		
		JLabel startTimeLabel = new JLabel("Start time:");
		startTimeLabel.setFont(new Font("MyriadPro", Font.BOLD, fontSize));
		final JTextField startTimeField = new JTextField(registerText);
		startTimeField.setToolTipText("Input start time.");
		startTimeField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		startTimeField.setFont(new Font("MyriadPro", Font.PLAIN, fontSize));
		
		JLabel finishTimeLabel = new JLabel("Finish time:");
		finishTimeLabel.setFont(new Font("MyriadPro", Font.BOLD, fontSize));
		final JTextField finishTimeField = new JTextField(registerText);
		finishTimeField.setToolTipText("Input finish number.");
		finishTimeField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		finishTimeField.setFont(new Font("MyriadPro", Font.PLAIN, fontSize));
		
		
		final JButton registerButton = new JButton("Register");
		registerButton.setToolTipText("Register entry.");
		registerButton.setFont(new Font("MyriadPro", Font.BOLD, fontSize));
		registerButton.setForeground(Color.WHITE);
		registerButton.setBackground(new Color(63, 181, 50));
		registerButton.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		registerButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		startNumberField.addActionListener(new ActionListener() {
			   @Override
			    public void actionPerformed(ActionEvent e) {
				   nameField.requestFocusInWindow();    
			    }
			});
		
		nameField.addActionListener(new ActionListener() {
			   @Override
			    public void actionPerformed(ActionEvent e) {
				   startTimeField.requestFocusInWindow();    
			    }
			});
		
		startTimeField.addActionListener(new ActionListener() {
			   @Override
			    public void actionPerformed(ActionEvent e) {
				   finishTimeField.requestFocusInWindow();    
			    }
			});
		
		finishTimeField.addActionListener(new ActionListener() {
			   @Override
			    public void actionPerformed(ActionEvent e) {
				   registerButton.requestFocusInWindow(); 
			    }
			});
	
		registerButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				startTimeField.setText("OK");
				
			}
			
			
		});
		
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
		c.setLayout(new BoxLayout(c, BoxLayout.X_AXIS));
		
		
		c.setBackground(new Color(243,243,243));
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		c.add(leftPanel);
		c.add(new JSeparator(JSeparator.VERTICAL),BorderLayout.LINE_START);
		c.add(rightPanel);
		
		JScrollPane entryList = new JScrollPane();
		rightPanel.add(entryList);
		entryList.setOpaque(true);
		entryList.setPreferredSize(new Dimension(800, 1000));
		entryList.getViewport().setBackground(new Color(173, 193, 214));
		entryList.setBorder(new TitledBorder("REGISTRATIONS"));
		entryList.setToolTipText("Old registrations.");
		entryList.setFont(new Font("MyriadPro", Font.PLAIN, fontSize()/5));
		
		leftPanel.add(addButtonAndFields());
		
		pack();
		System.out.println(	getGraphicsConfiguration());
		System.out.println(c.getWidth());
		getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		this.setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public int fontSize(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		int fontSize = ((screenSize.width - getWidth()) + (screenSize.height - taskBarSize - getHeight()))/60;
		return fontSize;
	}
	
	private static final long serialVersionUID = -307826388596514302L;
}
