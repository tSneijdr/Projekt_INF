package model.points;

import java.util.LinkedList;
import java.util.List;

/**
 * Stellt einen einzelnen Punkt eines datesatzes dar, so wie er von einem Sensor
 * ausgegeben wurde
 * 
 * 
 * @author tobias meisel
 *
 */
public class Point {

	private final int X;
	private final int Y;
	/** Zeit an dem der Punkt erreicht wird **/
	private final double TIMEPOINT;
	/** Zeit die der Punkt betrachtet wird **/
	private final double DURATION;

	private boolean isAlreadySet = false;

	private Point previousNode = null;
	private Point nextNode = null;

	/**
	 * Standardkonstruktor, Parameternamen sollten selbsterklärend sein
	 * 
	 * @param x
	 * @param y
	 * @param timepoint
	 * @param duration
	 */
	public Point(int x, int y, double timepoint, double duration) {
		X = x;
		Y = y;
		TIMEPOINT = timepoint;
		DURATION = duration;
	}

	/**
	 * Erhält eine Menge von Punkten und verknüpft sie so dass sie einen
	 * Datenpunkt ergeben.
	 * 
	 * @param points
	 * @return Den ersten Punkt der Datenreihe
	 */
	public static Point link(List<Point> points) {
		if (points == null) {
			throw new NullPointerException();
		}

		for (Point p : points) {
			if (p.isAlreadySet == true) {
				throw new IllegalArgumentException("Einer der Punkte ist bereits Mitglied einer Datenreihe");
			}
		}

		if (points.size() <= 0) {
			return null;
		}

		// Kopiere die Liste, sodass die übergebene Liste nicht verändert wird
		LinkedList<Point> copy = new LinkedList<Point>(points);

		Point lastPoint = null;
		while (!copy.isEmpty()) {
			Point minimalPoint = copy.get(0);
			// Ermittle den frühesten Punkt
			for (Point p : copy) {
				if (p.TIMEPOINT < minimalPoint.TIMEPOINT) {
					minimalPoint = p;
				}
			}

			copy.remove(minimalPoint);
			minimalPoint.previousNode = lastPoint;
			if (lastPoint != null) {
				lastPoint.nextNode = minimalPoint;
			}

			minimalPoint.isAlreadySet = true;
			lastPoint = minimalPoint;
		}

		// Iteriert zum Anfang der Liste
		while (lastPoint.getPreviousNode() != null) {
			lastPoint = lastPoint.getPreviousNode();
		}

		return lastPoint;
	}

	// -----------------------------------------------------------------
	// System ----------------------------------------------------------
	// -----------------------------------------------------------------
	public String toString() {
		return "(" + X + ", " + Y + ", " + TIMEPOINT + ", " + DURATION + ")";
	}

	public boolean equals(Point p) {
		if (!(this.toString().equals(p.toString()))) {
			return false;
		}

		{
			Point currentPoint1, currentPoint2;

			// Vergleicht alle Knoten die in der Liste vor diesem stehen
			currentPoint1 = this;
			currentPoint2 = p;

			while (currentPoint1.previousNode != null && currentPoint2.previousNode != null) {
				currentPoint1 = currentPoint1.previousNode;
				currentPoint2 = currentPoint2.previousNode;

				if (!(currentPoint1.toString().equals(currentPoint2.toString()))) {
					return false;
				}
			}
			if (!(currentPoint1.previousNode == null && currentPoint2.previousNode == null)) {
				return false;
			}

			// Vergleicht alle Knoten die in der Liste nach diesem stehen
			currentPoint1 = this;
			currentPoint2 = p;

			while (currentPoint1.nextNode != null && currentPoint2.nextNode != null) {
				currentPoint1 = currentPoint1.nextNode;
				currentPoint2 = currentPoint2.nextNode;

				if (!(currentPoint1.toString().equals(currentPoint2.toString()))) {
					return false;
				}
			}
			if (!(currentPoint1.nextNode == null && currentPoint2.nextNode == null)) {
				return false;
			}
		}
		return true;
	}

	// ----------------------------------------------------------------------------
	// Getter und Setter
	// ----------------------------------------------------------------------------
	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}

	public double getTIMEPOINT() {
		return TIMEPOINT;
	}

	public double getDURATION() {
		return DURATION;
	}

	/**
	 * Gibt den im Datensatz nächsten Punkt zurück
	 * 
	 * @return nächster Punkt im Datensatz, oder null wenn kein solcher
	 *         existiert
	 */
	public Point getNextNode() {
		return nextNode;
	}

	/**
	 * Gibt den Punkt zurück der im Datensatz vor diesem steht
	 * 
	 * @return nächster Punkt im Datensatz, oder null wenn kein solcher
	 *         existiert
	 */
	public Point getPreviousNode() {
		return previousNode;
	}

}
