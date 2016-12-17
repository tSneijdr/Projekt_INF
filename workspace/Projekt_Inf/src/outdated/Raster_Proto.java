package outdated;

import java.util.HashSet;

import javafx.util.Pair;

public class Raster_Proto {
	private int[] raster;
	public final int maxX;
	public final int maxY;

	/**
	 * 
	 * @param x
	 *            Anzahl der Raster auf x Achse
	 * @param y
	 *            Anzahl der Raster auf y Achse
	 */
	public Raster_Proto(int x, int y) {
		raster = new int[x * y];
		for (int i = 0; i < raster.length; i++) {
			raster[i] = 0;
		}

		maxX = x;
		maxY = y;
	}

	public int getValue(int x, int y) {
		if (0 <= x && x < maxX && 0 <= y && y < maxY) {
			return raster[maxX * y + x];
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	public void setValue(int x, int y, int value) {
		if (0 <= x && x < maxX && 0 <= y && y < maxY) {
			raster[maxX * y + x] = value;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	public void incrementValue(int x, int y) {
		if (0 <= x && x < maxX && 0 <= y && y < maxY) {
			raster[maxX * y + x]++;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	public void addGraph(double[] curve) {
		// Abfrage ob der Array legal formatiert ist
		if (curve.length % 4 != 0) {
			throw new IllegalArgumentException();
		}

		int i = 0;
		HashSet<Pair<Integer, Integer>> set = new HashSet<Pair<Integer, Integer>>();

		while (i < curve.length) {
			int abtaster = 1000;
			double abtastrate = 1.0 / abtaster;

			double x1 = curve[i], y1 = curve[i + 1], x2 = curve[i + 2], y2 = curve[i + 3];
			double m = (x2 - x1) / (y2 - y1);
			i += 4;

			for (int index = 0; index < abtaster; index++) {
				int buffer_x = (int) Math.floor(x1 + index * abtastrate);
				int buffer_y = (int) Math.floor(y1 + m * abtastrate);

				Pair<Integer, Integer> p = new Pair<Integer, Integer>(buffer_x,
						buffer_y);
				set.add(p);
			}
		}

		for (Pair<Integer, Integer> p : set) {
			incrementValue(p.getKey(), p.getValue());
		}
	}

	public void drawMatrix() {
		// TODO Auto-generated method stub

	}

	public void drawMatrixColored() {
		// TODO Auto-generated method stub

	}

	public void drawMatrixProcentual() {
		// TODO Auto-generated method stub

	}

	public void printMatrix() {
		for (int y = maxY - 1; y >= 0; y--) {
			for (int x = 0; x < maxX; x++) {
				System.out.print(getValue(x, y) + " ");
			}
			System.out.print("\n");
		}

	}

}
