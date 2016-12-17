package utils;

import tier_0.Point;



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

	public boolean overlap(Range3D r) {
		return inX(r.MIN_X) || inX(r.MAX_X) || inY(r.MIN_Y) || inY(r.MAX_Y);
	}

	public boolean inRange(int x, int y) {
		return inX(x) || inY(y);
	}

	public boolean inRange(Point p) {
		return inRange(p.X, p.Y);
	}

}
