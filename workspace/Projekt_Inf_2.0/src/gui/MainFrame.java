package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1890682455992057761L;

	public final JPanel PANEL = new JPanel();
	public final SelectionPanel SELECTION = new SelectionPanel();
	public final ModePanel MODE = new ModePanel();
	
	
	public MainFrame(String title) {
		this.setTitle(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		SELECTION.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		MODE.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		PANEL.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		this.add(SELECTION, BorderLayout.WEST);
		this.add(PANEL, BorderLayout.CENTER);
		this.add(MODE, BorderLayout.SOUTH);
	}


	public void display() {
		// JFrame Einstellungen
		this.setVisible(true);
		this.pack();
	}
}
