package gui;

import javax.swing.JPanel;

import core_array.Raster;
import outdated.DrawingPanelProcentualColored;
import outdated.DrawingPanelProcentualCombined;
import outdated.DrawingPanelProcentualNumerical;

public class Gui {
	/**
	 * Gibt einen String zurück der den Raster darstellt
	 * 
	 * @param r
	 * @return
	 */
	public static String printRaster(Raster r) {
		String res = "";
		for (int i = r.NUMBER_OF_ROWS - 1; i >= 0; i--) {
			for (int j = 0; r.NUMBER_OF_COLUMNS > j; j++) {
				res += Integer.toString(r.getNumberOfPoints(i, j));
			}
			System.out.println();
		}
		return res;
	}

	public JPanel getDrawingPanel(Raster raster, GraphicMode g, int scalar) {
		if (scalar <= 0 || g == null) {
			throw new IllegalArgumentException(
					"g darf nicht null und scalar nicht <=0 sein");
		}

		switch (g) {
		case PROCENTUAL_COLORED:
			return new DrawingPanelProcentualColored(raster, scalar);
		case PROCENTUAL_NUMERICAL:
			return new DrawingPanelProcentualNumerical(raster, scalar);
		case PROCENTUAL_COMBINED:
			return new DrawingPanelProcentualCombined(raster, scalar);
		default:
			throw new IllegalArgumentException(
					"Dieses Element ist kein (definiertes) Element von GraphicMode.");
		}
	}
}
