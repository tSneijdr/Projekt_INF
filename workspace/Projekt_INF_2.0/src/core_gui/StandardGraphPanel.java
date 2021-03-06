package core_gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import datastructures.Matrix;

/**
 * Ein Test
 * @author tobi
 *
 */
public class StandardGraphPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3822532822813872028L;

	private final Matrix MATRIX;

	public final int WIDTH;
	public final int HEIGHT;

	private final int ROW_SIZE;
	private final int COLUMN_SIZE;

	public StandardGraphPanel(int width, int height, Matrix m) {
		MATRIX = m;

		WIDTH = width;
		HEIGHT = height;

		ROW_SIZE = HEIGHT / m.NUMBER_OF_ROWS;
		COLUMN_SIZE = WIDTH / m.NUMBER_OF_COLUMNS;

		// Setzt die bevorzugte und minimale Größe
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		this.setPreferredSize(this.getMinimumSize());
	}

	public void paint(Graphics g) {
		int radius = 5;

		for (int x = 0; x < MATRIX.NUMBER_OF_COLUMNS; x++) {
			for (int y = 0; y < MATRIX.NUMBER_OF_ROWS; y++) {
				if (MATRIX.getNumberOfPointsAt(x, y) > 0) {
					int xCoord = x * COLUMN_SIZE + (COLUMN_SIZE - 2 * radius)
							/ 2;
					int yCoord = x * ROW_SIZE + (ROW_SIZE - 2 * radius) / 2;
					g.fillOval(xCoord, yCoord, 2 * radius, 2 * radius);
				}
				g.drawRect(x*COLUMN_SIZE, y*ROW_SIZE, COLUMN_SIZE, ROW_SIZE);
			}
		}
	}

}
