package register.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

public class RegistrationGUI extends JFrame {
	private static final long serialVersionUID = -307826388596514302L;

	private String registerText;

	private JTextField startTimeField;
	
	
	private JTextField nameField;
	private JTextField finishTimeField;
	private JTextField startNumberField;

	public RegistrationGUI(String registrationString) {
		super(registrationString);

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gbl);

		c.insets.set(5, 5, 5, 5);
		
		int fontSize = fontSize();
		
		JLabel startNumberLabel = new StartNumberLabel(fontSize);
		startNumberField = new StartNumberField(this, fontSize);
		
		
		JButton registerButton = new RegisterButton(this, fontSize);
		
		JScrollPane entryList = new JScrollPane(new EntryList(fontSize));
		entryList.setBorder(new TitledBorder("REGISTRATIONS"));
		

		
		addToGrid(startNumberLabel, gbl, c, 0, 0, 1, 1, GridBagConstraints.NONE, 0, 0);
		addToGrid(startNumberField, gbl, c, 1, 0, 1, 1, GridBagConstraints.HORIZONTAL, 1, 0);
		addToGrid(registerButton  , gbl, c, 2, 0, 1, 1, GridBagConstraints.NONE, 0, 0);
		addToGrid(entryList	      , gbl, c, 0, 1, 3, 1, GridBagConstraints.BOTH, 1, 1);
		
		pack();
		setMinimumSize(getSize());
		
		setExtendedState(MAXIMIZED_BOTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addToGrid(JComponent component, GridBagLayout gbl,
			GridBagConstraints c, int x, int y, int width, int height,
			int fill, double wx, double wy) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;
		c.fill = fill;
		c.weightx = wx;
		c.weighty = wy;
		
		gbl.setConstraints(component, c);
		add(component);
	}
	
	
	

	private void addGUIComponents() {
		Container c = (Container) getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.X_AXIS));

		c.setBackground(new Color(243, 243, 243));
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		c.add(leftPanel);
		c.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_START);
		c.add(rightPanel);

		JScrollPane entryList = new JScrollPane();
		rightPanel.add(entryList);
		entryList.setOpaque(true);
		entryList.setPreferredSize(new Dimension(800, 10));
		entryList.getViewport().setBackground(new Color(173, 193, 214));
		entryList.setBorder(new TitledBorder("REGISTRATIONS"));
		entryList.setToolTipText("Old registrations.");
		entryList.setFont(new Font("MyriadPro", Font.PLAIN, fontSize() / 5));

		leftPanel.add(addButtonAndFields());

		pack();

		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private JPanel addButtonAndFields() {
		int fontSize = fontSize();

		JPanel panel = new JPanel();
		JPanel panelTop = new JPanel();
		JPanel panelBot = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panelBot.setLayout(new BoxLayout(panelBot, BoxLayout.Y_AXIS));
		panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));

		JLabel startNumberLabel = new JLabel("Start number:");
		startNumberLabel.setFont(new Font("MyriadPro", Font.BOLD, fontSize));
		startNumberField = new JTextField(registerText);
		startNumberField.setToolTipText("Input start number.");
		startNumberField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		startNumberField.setFont(new Font("MyriadPro", Font.PLAIN, fontSize));

		JLabel nameLabel = new JLabel("Contestant name:");
		nameLabel.setFont(new Font("MyriadPro", Font.BOLD, fontSize));
		nameField = new JTextField(registerText);
		nameField.setToolTipText("Input contestant name.");
		nameField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		nameField.setFont(new Font("MyriadPro", Font.PLAIN, fontSize));

		JLabel startTimeLabel = new JLabel("Start time:");
		startTimeLabel.setFont(new Font("MyriadPro", Font.BOLD, fontSize));
		startTimeField = new JTextField(registerText);
		startTimeField.setToolTipText("Input start time.");
		startTimeField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		startTimeField.setFont(new Font("MyriadPro", Font.PLAIN, fontSize));

		JLabel finishTimeLabel = new JLabel("Finish time:");
		finishTimeLabel.setFont(new Font("MyriadPro", Font.BOLD, fontSize));
		finishTimeField = new JTextField(registerText);
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
				register();
			}
		});

		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				register();
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

	private int fontSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(
				getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		int fontSize = ((screenSize.width - getWidth()) + (screenSize.height
				- taskBarSize - getHeight())) / 60;
		return fontSize;
	}

	public void register() {

		// TODO RegistrationGUI; Registrera den inmatade informationen h�r!

		startNumberField.setText("");
		nameField.setText("");
		startTimeField.setText("");
		finishTimeField.setText("");
	}

	public static void main(String[] args) {
		RegistrationGUI screen = new RegistrationGUI("Registration window");
	}
}
