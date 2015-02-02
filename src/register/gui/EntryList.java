package register.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class EntryList extends JScrollPane {
	public EntryList(int fontSize) {
		
		setPreferredSize(new Dimension(800, 600));
		setOpaque(true);
		getViewport().setBackground(new Color(173, 193, 214));
		setToolTipText("Old registrations.");
	}
}
