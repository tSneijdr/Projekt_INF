package outdated;
import java.awt.Dimension;

import javax.swing.JPanel;

import core_array.Raster;

	/**
	 * Eine Abstrakte Klasse die Grundlagen für alle anderen Panels legt
	 * 
	 * @author tobi
	 *
	 */
	public abstract class DrawingPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		// Private finale Attribute
		private final double SCALAR;
		private final int PANEL_WIDTH;
		private final int PANEL_HEIGHT;

		public final int delta_x;
		public final int delta_y;

		public final int ROWS;
		public final int COLUMNS;

		public final Raster RASTER;

		public DrawingPanel(Raster r, double scalar) {
			SCALAR = scalar;
			RASTER = r;

			ROWS = r.NUMBER_OF_ROWS;
			COLUMNS = r.NUMBER_OF_COLUMNS;

			delta_x = (int) (Math.round(r.SIZE_COLUMN * SCALAR));
			delta_y = (int) (Math.round(r.SIZE_ROW * SCALAR));

			PANEL_WIDTH = delta_x * r.NUMBER_OF_COLUMNS;
			PANEL_HEIGHT = delta_y * r.NUMBER_OF_ROWS;

			this.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
			this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

			System.out.println(PANEL_WIDTH + " " + PANEL_HEIGHT);
		}
	}