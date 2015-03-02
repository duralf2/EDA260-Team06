package gui;

import gui.model.TimeRegistrationHandler;

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
import javax.swing.border.BevelBorder;

public class PreRegistrationPopUp extends JDialog implements ActionListener {
	private JLabel updateLabel;
	private JButton yes;
	private JButton	 no;
	private TimeRegistrationHandler preRegistered;
	private JPanel panel;
	
	public PreRegistrationPopUp (TimeRegistrationHandler preRegistered, int fontSize) {
		this.preRegistered = preRegistered;
		updateLabel = new JLabel("Do you want to update the pre-registered time?");
		
		yes = new JButton("Yes");
		no = new JButton("No");
		yes.addActionListener(this);
		no.addActionListener(this);
		
		updateLabel.setFont(updateLabel.getFont().deriveFont(Font.BOLD, fontSize/2));
		updateLabel.setFont(updateLabel.getFont().deriveFont(Font.BOLD, fontSize/2));
		
		yes.setForeground(Color.WHITE);
		yes.setBackground(new Color(63, 181, 50));
		yes.setOpaque(true);
		yes.setBorder(new BevelBorder(BevelBorder.RAISED));
		yes.setFont(yes.getFont().deriveFont(Font.BOLD, fontSize/2));
		
		no.setForeground(Color.WHITE);
		no.setBackground(new Color(243, 83, 83));
		no.setOpaque(true);
		no.setBorder(new BevelBorder(BevelBorder.RAISED));
		no.setFont(no.getFont().deriveFont(Font.BOLD, fontSize/2));
		
		panel = new JPanel(new GridLayout(2, 1, 5, 5));
		JPanel innerpannel = new JPanel(new GridLayout(1,2,5,5));
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.add(updateLabel);
		panel.add(updateLabel);
		panel.add(innerpannel);
		innerpannel.add(no);
		innerpannel.add(yes);
		
		add(panel);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(screenSize.width / 2, screenSize.height / 6);
		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);

		setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == yes){
		preRegistered.preRegister();
		}
		setVisible(false);
		dispose();
		
	}
	
}
