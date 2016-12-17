package outdated;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class Drawing extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;

		g2D.drawRect(0, 0, 10, 10);
		g2D.drawRect(0, 10, 30, 30);
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		Drawing d = new Drawing();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add("Center", d);
		frame.pack();
		frame.setSize(new Dimension(200, 200));
		frame.setVisible(true);
	}
}
