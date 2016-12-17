package core_array;

import java.util.HashSet;

import core.API;
import core.Point;

public class Raster {
	public final int NUMBER_OF_ROWS;
	public final int NUMBER_OF_COLUMNS;
	public final int NUMBER_OF_CELLS;

	public final double LENGTH_TIMESTEP;
	public final double SIZE_ROW;
	public final double SIZE_COLUMN;

	protected final HashSet<Point> ALL_POINTS;
	
	
	private final int[] POINTS;

	protected Raster(int numberRows, int numberColumns, double lenTimestep,
			double sizeRow, double sizeColumn) {
		NUMBER_OF_ROWS = numberRows;
		NUMBER_OF_COLUMNS = numberColumns;
		NUMBER_OF_CELLS = NUMBER_OF_ROWS * NUMBER_OF_COLUMNS;

		SIZE_ROW = sizeRow;
		SIZE_COLUMN = sizeColumn;
		LENGTH_TIMESTEP = lenTimestep;

		ALL_POINTS = new HashSet<Point>();
		
		POINTS = new int[NUMBER_OF_COLUMNS * NUMBER_OF_ROWS];
	}

	protected void addPoint(Point p) {

		ALL_POINTS.add(p);
		int x = (int) Math.floor((double) p.X / (double) SIZE_COLUMN);
		int y = (int) Math.floor((double) p.Y / (double) SIZE_ROW);

		POINTS[y * NUMBER_OF_ROWS + x]++;
	}

	// -----------------------------------------------------------------
	// Getter und Setter -----------------------------------------------
	// -----------------------------------------------------------------
	protected HashSet<Point> getAllPoints() {
		HashSet<Point> res = new HashSet<Point>();
		for (Point p : ALL_POINTS) {
			res.add(p);
		}
		return ALL_POINTS;
	}

	public int getNumberOfPoints(int x, int y) {
		if (0 <= x && x < NUMBER_OF_COLUMNS && 0 <= y && y < NUMBER_OF_ROWS) {
			return POINTS[y * NUMBER_OF_COLUMNS + x];
		} else {
			throw new IllegalArgumentException(
					"Punkt liegt nich im diskretisierten Bereich");
		}
	}

	@SuppressWarnings("unused")
	@Deprecated
	private int getNumberOfPoints(int n) {
		if (0 <= n && n < POINTS.length) {
			return POINTS[n];
		} else {
			throw new IllegalArgumentException(
					"Punkt liegt nich im diskretisierten Bereich");
		}
	}

	public int getNumberOfPoints() {
		return ALL_POINTS.size();
	}

	private void setNumberOfPoints(int x, int y, int n) {
		if (0 <= x && x < NUMBER_OF_COLUMNS && 0 <= y && y < NUMBER_OF_ROWS) {
			POINTS[y * NUMBER_OF_COLUMNS + x] = n;
		} else {
			throw new IllegalArgumentException(
					"Punkt liegt nich im diskretisierten Bereich");
		}
	}

	// -----------------------------------------------------------------
	// Filtern von Rastern ------------------------------------------
	// -----------------------------------------------------------------
	protected int[] getSummOfX() {
		int[] res = new int[NUMBER_OF_COLUMNS];
		for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
			res[i] = 0;
		}

		for (int i = 0; i < NUMBER_OF_CELLS; i++) {
			res[i % NUMBER_OF_COLUMNS] += POINTS[i];
		}

		return res;
	}

	protected int[] getSummOfY() {
		int[] res = new int[NUMBER_OF_ROWS];
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			res[i] = 0;
		}

		for (int i = 0; i < NUMBER_OF_CELLS; i++) {
			res[(int) Math.floor((double) i / (double) NUMBER_OF_COLUMNS)] += POINTS[i];
		}

		return res;
	}

	// -----------------------------------------------------------------
	// Rechnen mit Matrizen --------------------------------------------
	// -----------------------------------------------------------------
	public static Raster add(Raster r1, Raster r2) {
		if (!(r1.dataString().equals(r2.dataString()))) {
			throw new IllegalArgumentException(
					"Die Daten der Matrizen stimmen nicht überein");
		}

		Raster m = new Raster(r1.NUMBER_OF_ROWS, r1.NUMBER_OF_COLUMNS,
				r1.LENGTH_TIMESTEP, r1.SIZE_ROW, r1.SIZE_COLUMN);

		for (int i = 0; i < r1.NUMBER_OF_CELLS; i++) {
			m.POINTS[i] = r1.POINTS[i] + r2.POINTS[i];
		}

		m.ALL_POINTS.addAll(r1.getAllPoints());
		m.ALL_POINTS.addAll(r2.getAllPoints());

		return m;
	}

	/**
	 * Matrixmultiplikation, wobei r2 auf r1 multipliziert wird (r1*r2) Wird am
	 * besten mit einer frisch erstellten leeren Raster benutzt um undefiniertem
	 * Verhalten vorzubeugen
	 * 
	 * @param r1
	 * @param r2
	 * @return
	 */
	public static Raster multiply(Raster r1, Raster r2) {
		if (!(r1.NUMBER_OF_ROWS == r2.NUMBER_OF_COLUMNS)) {
			throw new IllegalArgumentException(
					"Die Matrizen können nicht multipliziert werden.");
		}
		if (r1.LENGTH_TIMESTEP != r2.LENGTH_TIMESTEP
				|| r1.SIZE_COLUMN != r2.SIZE_COLUMN
				|| r1.SIZE_ROW != r2.SIZE_ROW) {
			throw new IllegalArgumentException(
					"Die Daten der Matrizen stimmen nicht überein.");
		}

		Raster m = new Raster(r1.NUMBER_OF_ROWS, r2.NUMBER_OF_COLUMNS,
				r1.LENGTH_TIMESTEP, r1.SIZE_ROW, r1.SIZE_COLUMN);

		m.ALL_POINTS.addAll(r1.ALL_POINTS);
		m.ALL_POINTS.addAll(r2.ALL_POINTS);

		for (int x = 0; x < m.NUMBER_OF_COLUMNS; x++) {
			for (int y = 0; y < m.NUMBER_OF_ROWS; y++) {
				int res = 0;
				for (int i = 0; i < r1.NUMBER_OF_ROWS; i++) {
					res += r1.getNumberOfPoints(i, y)
							* r2.getNumberOfPoints(x, m.NUMBER_OF_ROWS - i - 1);
				}
				m.setNumberOfPoints(x, y, res);

			}
		}

		return m;

	}

	// -----------------------------------------------------------------
	// Anderes ---------------------------------------------------------
	// -----------------------------------------------------------------
	public String dataString() {
		return NUMBER_OF_ROWS + " " + NUMBER_OF_COLUMNS + " " + LENGTH_TIMESTEP
				+ " " + SIZE_ROW + " " + SIZE_COLUMN;
	}

	public String toString() {
		int size = 0;
		{
			int maxValue = 0;
			for (int i : POINTS) {
				maxValue = (i > maxValue) ? i : maxValue;
			}
			size = Integer.toString(maxValue).length();
		}
		String res = "";
		// Iteriert durch alle Zellen
		for (int i = NUMBER_OF_ROWS - 1; i >= 0; i--) {
			for (int j = 0; NUMBER_OF_COLUMNS > j; j++) {

				// Fügt Wert in uniformer Länge hinzu
				String s = Integer.toString(getNumberOfPoints(i, j));
				if (s.length() < size) {
					String dist = "";
					for (int k = 0; k < (size - s.length()); k++) {
						dist += " ";
					}
					res += " " + dist + s;
				} else {
					res += " " + s;
				}
			}
			res += "\n";
		}

		// Entferne das "\n" am Ende
		res = res.substring(0, res.length() - 1);

		return res;
	}
}
