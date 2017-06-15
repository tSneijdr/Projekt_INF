package utils.ranges;

import model.points.Point;

/**
 * Stellt einen zweidimensionalen Raum da, der alle Punkte p mit: MIN_X <= p.x <
 * MAX_X MIN_Y <= p.y < MAX_Y umfasst (also immer inklusive der unteren und
 * exclusie der oberen Grenze)
 * 
 * @author tobi
 *
 */
public class Range2D {
	public final int MIN_X;
	public final int MAX_X;
	public final int MIN_Y;
	public final int MAX_Y;

	public final int WIDTH;
	public final int HEIGHT;

	public Range2D(int minX, int maxX, int minY, int maxY) {
		MIN_X = minX;
		MAX_X = maxX;

		MIN_Y = minY;
		MAX_Y = maxY;

		WIDTH = MAX_X - MIN_X;
		HEIGHT = MAX_Y - MIN_Y;
	}

	/**
	 * Prüft ob die gegebene x-Position in dieser Range liegt
	 * @param x
	 * @return
	 */
	public boolean inX(int x) {
		return (MIN_X <= x && x < MAX_X);
	}

	/**
	 * Prüft ob die gegebene y-Position in dieser Range liegt
	 * @param y
	 * @return
	 */
	public boolean inY(int y) {
		return (MIN_Y <= y && y < MAX_Y);
	}

	/**
	 * Prüft ob die beiden ranges überlappen
	 * @param searchRange
	 * @return
	 */
	public boolean overlap(Range2D searchRange) {
		return inX(searchRange.MIN_X) || inX(searchRange.MAX_X) || inY(searchRange.MIN_Y) || inY(searchRange.MAX_Y);
	}

	/**
	 * Prüft ob dieser Punkt in der range liegt
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean inRange(int x, int y) {
		return inX(x) && inY(y);
	}

	/**
	 * prüft ob dieser Punkt in der Range liegt
	 * @param p
	 * @return
	 */
	public boolean inRange(Point p) {
		return inRange(p.getX(), p.getY());
	}

	public String toString() {
		return "(x: " + MIN_X + " -> " + MAX_X + ", y: " + MIN_Y + " -> " + MAX_Y + ")";
	}

}
