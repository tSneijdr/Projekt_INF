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

	public boolean inX(int x) {
		return (MIN_X <= x && x < MAX_X);
	}

	public boolean inY(int y) {
		return (MIN_Y <= y && y < MAX_Y);
	}

	public boolean overlap(Range2D searchRange) {
		return inX(searchRange.MIN_X) || inX(searchRange.MAX_X) || inY(searchRange.MIN_Y) || inY(searchRange.MAX_Y);
	}

	public boolean inRange(int x, int y) {
		return inX(x) || inY(y);
	}

	public boolean inRange(Point p) {
		return inRange(p.getX(), p.getY());
	}

}
