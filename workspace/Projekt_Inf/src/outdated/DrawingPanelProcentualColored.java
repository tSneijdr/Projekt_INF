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
public class DrawingPanelProcentualColored extends DrawingPanel {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public DrawingPanelProcentualColored(Raster r, double scalar) {
		super(r, scalar);
	}

	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;

		for (int x = 0; x < COLUMNS; x++) {
			for (int y = 0; y < ROWS; y++) {

				int left_x, lower_y;
				left_x = (delta_x * x);
				lower_y = (delta_y * y);

				// Färbung berechnen
				{
					int red, green, blue;

					red = 0;
					green = 0;
					blue = 255;

					// Rechnet Anteil der in der entsprechenden Zelle
					// stehenden Punkte
					// an der Gesamtzahl der Punkte aus
					double color_number = (double) RASTER.getNumberOfPoints(x,
							ROWS - 1 - y);
					color_number = color_number
							/ (double) RASTER.getNumberOfPoints();

					// Überträgt diese in ein "blau-auf-weiß" Farbmuster
					blue = (int) Math.round((color_number * 255));

					if (blue <= 0) {
						red = 255;
						green = 255;
						blue = 255;
					} else {
						blue = 255 - blue;
					}
					// setzt die Farbe
					g2D.setColor(new Color(red, green, blue));
				}

				g2D.fillRect(left_x, lower_y, delta_x, delta_y);

			}
		}
	}
}