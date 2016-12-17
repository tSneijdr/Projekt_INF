package outdated;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import core_array.Raster;

/**
 * Eine internet Subklasse von JPanel die der Darstellung von Rastern als
 * Graphiken dient
 * 
 * @author tobi
 *
 */
public class DrawingPanelProcentualNumerical extends DrawingPanel {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public DrawingPanelProcentualNumerical(Raster r, double scalar) {
		super(r, scalar);
	}

	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g.setColor(Color.BLACK);

		for (int x = 0; x < COLUMNS; x++) {
			for (int y = 0; y < ROWS; y++) {

				int left_x, lower_y;
				left_x = (delta_x * x);
				lower_y = (delta_y * y);

				{ // Prozentuale Zahl berechnen
					int strX, strY;
					strY = lower_y
							+ (int) ((delta_y + g2D.getFont().getSize()) / 2);
					strX = left_x + (delta_x - g2D.getFont().getSize()) / 2;

					// Rechnet Anteil der in der entsprechenden Zelle
					// stehenden Punkte
					// an der Gesamtzahl der Punkte aus
					double color_number = (double) RASTER.getNumberOfPoints(x,
							ROWS - 1 - y);
					color_number = color_number
							/ (double) RASTER.getNumberOfPoints();

					String procentual = (color_number * 100) + "%";

					g2D.getFont().getSize();
					g2D.drawString(procentual, strX, strY);
				}

				g2D.drawRect(left_x, lower_y, delta_x, delta_y);
			}
		}
	}
}
