package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main_package.Main;
import core.Mode;
import core.RecordDisplaySettings;
import core_gui.StandardGraphPanel;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1890682455992057761L;

	private final Main PARENT;
	private final HashSet<RecordDisplaySettings> ALL_SETTINGS;

	private final EditRecordDisplayPanel SELECTION;
	private final ContentPanel CONTENT_1;
	private final ContentPanel CONTENT_2;

	public MainFrame(Main parent, String title,
			HashSet<RecordDisplaySettings> allSettings) {
		this.ALL_SETTINGS = allSettings;
		this.PARENT = parent;
		
		CONTENT_1 = new ContentPanel(this);
		CONTENT_2 = new ContentPanel(this);
		
		SELECTION = new EditRecordDisplayPanel(ALL_SETTINGS);

		this.setTitle(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		SELECTION.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		CONTENT_1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		CONTENT_2.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		this.add(SELECTION, BorderLayout.WEST);
		this.add(CONTENT_1, BorderLayout.CENTER);
		this.add(CONTENT_2, BorderLayout.EAST);
	}

	public void display() {
		// JFrame Einstellungen
		this.setVisible(true);
		this.pack();
		this.repaint();
	}
	
	public void actualize(){
		this.SELECTION.actualize();
	}
	
	protected JPanel getContent(Mode m){
		return PARENT.getContent(m);
	}
}
