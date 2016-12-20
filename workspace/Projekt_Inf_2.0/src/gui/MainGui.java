package gui;

import java.awt.Component;

import javax.swing.JFrame;

import core.Point;
import utils.Range2D;
import datastructures.Matrix;

public class MainGui extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1890682455992057761L;

	public static void main(String[] args) {
		Range2D r = new Range2D(0, 100, 0, 100);
		Matrix m = new Matrix(r, 10, 10);
		m.addPoint(new Point(1, 1, 1, 1));

		StandardGraphPanel p = new StandardGraphPanel(500, 500, m);
		
		MainGui g = new MainGui();
		g.add(p);
		g.display();
	}

	public MainGui() {
		
	}
	
	public void addElement(Component comp){
		this.getContentPane().add(comp);
	}

	public void display() {


		// JFrame Einstellungen
		this.setVisible(true);
		this.setTitle("Haupt-GUI - Test 1");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
	}
}
