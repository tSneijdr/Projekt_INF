package core;

import java.util.LinkedList;
import java.util.List;

/**
 * Stellt einen einzelnen Punkt eines datesatzes dar
 * 
 * @author tobi
 *
 */
public class Point {
	public final int X;
	public final int Y;
	/** Zeit an dem der Punkt erreicht wird **/
	public final double TIMEPOINT;
	/** Zeit die der Punkt betrachtet wird **/
	public final double DURATION;

	private boolean isAlreadySet = false;

	private Point previousNode = null;
	private Point nextNode = null;

	public Point(int x, int y, double timepoint, double duration) {
		X = x;
		Y = y;
		TIMEPOINT = timepoint;
		DURATION = duration;
	}

	// -----------------------------------------------------------------
	// Getter und Setter -----------------------------------------------
	// -----------------------------------------------------------------

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

	/**
	 * Erhält eine Menge von Punkten und verknüpft sie so dass sie einen
	 * Datenpunkt ergeben.
	 * 
	 * @param points
	 * @return Den ersten Punkt der Datenreihe
	 */
	public static Point link(List<Point> points) {
		for (Point p : points) {
			if (p.isAlreadySet == true) {
				throw new IllegalArgumentException(
						"Einer der Punkte ist bereits Mitglied einer Datenreihe");
			}
		}

		if (points.size() <= 0) {
			return null;
		}

		// Kopiere die Liste, sodass die übergebene Liste nicht verändert wird
		LinkedList<Point> points2 = new LinkedList<Point>();
		points2.addAll(points);
		points = points2;

		Point lastPoint = null;
		while (!points.isEmpty()) {
			Point minimalPoint = points.get(0);
			for (Point p : points) {
				if (p.TIMEPOINT < minimalPoint.TIMEPOINT) {
					minimalPoint = p;
				}
			}

			points.remove(minimalPoint);
			minimalPoint.previousNode = lastPoint;
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

			while (currentPoint1.previousNode != null
					&& currentPoint2.previousNode != null) {
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

			while (currentPoint1.nextNode != null
					&& currentPoint2.nextNode != null) {
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
}
