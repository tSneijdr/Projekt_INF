package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import core.Point;
import core.Record;
import utils.Range2D;
import datastructures.Matrix;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1890682455992057761L;

	public final JPanel PANEL = new JPanel();
	public final SelectionPanel SELECTION = new SelectionPanel();
	
	public MainFrame(String title) {
		this.setTitle(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.add(SELECTION, BorderLayout.WEST);
		this.add(PANEL, BorderLayout.CENTER);
	}


	public void display() {
		// JFrame Einstellungen
		this.setVisible(true);
		this.pack();
	}
}
