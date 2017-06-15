package utils.datastructures;

import java.util.HashSet;
import java.util.Set;

import model.points.Point;
import model.points.Record;
import utils.ranges.Range2D;

/**
 * Datenstruktur, stellt eine einfache Matrix dar Deprecated weil nicht
 * getestet, sollte aber funktionieren
 * 
 * @author tobias meisel
 *
 */
@Deprecated
public class Matrix {
	public final Range2D IMAGE_RANGE;
	public final int NUMBER_OF_COLUMNS;
	public final int NUMBER_OF_ROWS;

	public final int SIZE_OF_ROW;
	public final int SIZE_OF_COLUMN;

	private final int[] values;

	private final HashSet<Quadrupel<Integer, Integer, Integer, Integer>> arrows;

	public Matrix(Range2D range, int numberOfColumns, int numberOfRows) {
		IMAGE_RANGE = range;

		NUMBER_OF_COLUMNS = numberOfColumns;
		NUMBER_OF_ROWS = numberOfRows;

		SIZE_OF_ROW = (int) Math.ceil(((double) IMAGE_RANGE.HEIGHT / (double) NUMBER_OF_ROWS));
		SIZE_OF_COLUMN = (int) Math.ceil(((double) IMAGE_RANGE.WIDTH / (double) NUMBER_OF_COLUMNS));

		values = new int[NUMBER_OF_COLUMNS * NUMBER_OF_ROWS];
		for (int i = 0; i < values.length; i++) {
			values[i] = 0;
		}

		arrows = new HashSet<Quadrupel<Integer, Integer, Integer, Integer>>();
	}

	/**
	 * Gibt die Anzahl der Punkte in der durch diese Daten gegebenen Zelle
	 * zurück
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public int getNumberOfPointsAt(int x, int y) {
		if (x < 0 || NUMBER_OF_COLUMNS <= x) {
			throw new ArrayIndexOutOfBoundsException("Der x-Wert stimmt hier nicht.");
		}
		if (y < 0 || NUMBER_OF_ROWS <= y) {
			throw new ArrayIndexOutOfBoundsException("Der y-Wert stimmt hier nicht.");
		}

		return values[y * NUMBER_OF_ROWS + x];
	}

	/**
	 * Fügt der Matrix einen Punkt zu Beachte: Bei der zufügung eines einzelnen
	 * Punktes werden Pfeile zwischen Graphknoten nicht erhalten
	 * 
	 * @param p
	 */
	private void addPoint(Point p) {
		int x = p.getX() / SIZE_OF_COLUMN;
		int y = p.getY() / SIZE_OF_ROW;

		if (x < 0 || NUMBER_OF_COLUMNS <= x) {
			throw new ArrayIndexOutOfBoundsException("Der x-Wert stimmt hier nicht.");
		}
		if (y < 0 || NUMBER_OF_ROWS <= y) {
			throw new ArrayIndexOutOfBoundsException("Der y-Wert stimmt hier nicht.");
		}

		values[y * NUMBER_OF_ROWS + x]++;
	}

	private void addPoints(Set<Point> points) {
		for (Point p : points) {
			addPoint(p);
		}
	}

	public void addRecord(Record r) {
		addPoints(r.getAllPoints());

		Point p = r.getFirstPoint();
		while (p.getNextNode() != null) {
			int x1, x2, y1, y2;
			x1 = p.getX() / SIZE_OF_COLUMN;
			y1 = p.getY() / SIZE_OF_ROW;

			x2 = p.getNextNode().getX() / SIZE_OF_COLUMN;
			y2 = p.getNextNode().getY() / SIZE_OF_ROW;

			Quadrupel<Integer, Integer, Integer, Integer> q;
			q = new Quadrupel<Integer, Integer, Integer, Integer>(x1, y1, x2, y2);
			arrows.add(q);

			p = p.getNextNode();
		}

	}
}
