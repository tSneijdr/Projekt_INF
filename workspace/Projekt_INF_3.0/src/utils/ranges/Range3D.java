package utils.ranges;

import model.points.Point;

/**
 * Stellt einen dreidimensionalen Raum da, der alle Punkte p mit: MIN_X <= p.x <
 * MAX_X MIN_Y <= p.y < MAX_Y MIN_TIME <= p.time < MAX_TIME umfasst (also immer
 * inklusive der unteren und exclusie der oberen Grenze)
 * 
 * @author tobias meisel
 *
 */
public class Range3D {
	public final int MIN_X;
	public final int MAX_X;
	public final int MIN_Y;
	public final int MAX_Y;
	public final int MIN_TIME;
	public final int MAX_TIME;

	public final int WIDTH;
	public final int HEIGHT;
	public final int LENGTH;

	/**
	 * Standardkonstruktor, parameternamen sollten aussagend genug sein
	 * 
	 * @param minX
	 * @param maxX
	 * @param minY
	 * @param maxY
	 * @param minTime
	 * @param maxTime
	 */
	public Range3D(int minX, int maxX, int minY, int maxY, int minTime, int maxTime) {
		MIN_X = minX;
		MAX_X = maxX;

		MIN_Y = minY;
		MAX_Y = maxY;

		MIN_TIME = minTime;
		MAX_TIME = maxTime;

		WIDTH = MAX_X - MIN_X;
		HEIGHT = MAX_Y - MIN_Y;
		LENGTH = MAX_TIME - MIN_TIME;
	}

	/**
	 * Erhält 3Drange aus 2drange mit den Zeitdaten
	 * 
	 * @param r
	 * @param minTime
	 * @param maxTime
	 */
	public Range3D(Range2D r, int minTime, int maxTime) {
		this(r.MIN_X, r.MAX_X, r.MIN_Y, r.MAX_Y, minTime, maxTime);
	}

	/**
	 * Prüft ob die gegebene x-Position in dieser Range liegt
	 * 
	 * @param x
	 * @return
	 */
	public boolean inX(int x) {
		return (MIN_X <= x && x < MAX_X);
	}

	/**
	 * Prüft ob die gegebene y-Position in dieser Range liegt
	 * 
	 * @param y
	 * @return
	 */
	public boolean inY(int y) {
		return (MIN_Y <= y && y < MAX_Y);
	}

	/**
	 * Prüft ob die gegebene Zeit-Position in dieser Range liegt
	 * 
	 * @param time
	 * @return
	 */
	public boolean inTime(double time) {
		return (MIN_TIME <= time && time < MAX_TIME);
	}

	/**
	 * Gibt zurück ob diese beiden Ranges überlappen
	 * 
	 * @param r
	 * @return
	 */
	public boolean overlap(Range3D r) {
		return inX(r.MIN_X) || inX(r.MAX_X) || inY(r.MIN_Y) || inY(r.MAX_Y) || inTime(r.MIN_TIME) || inTime(r.MAX_TIME);
	}

	/**
	 * Prüft ob dieser Punkt in der Range liegt
	 * 
	 * @param x
	 * @param y
	 * @param time
	 * @return
	 */
	public boolean inRange(int x, int y, double time) {
		return inX(x) && inY(y) && inTime(time);
	}

	/**
	 * Prüft ob dieser Punkt in der Range liegt
	 * 
	 * @param p
	 * @return
	 */
	public boolean inRange(Point p) {
		return inRange(p.getX(), p.getY(), p.getTIMEPOINT());
	}

	/**
	 * Reduziert diese Range auf eine 2D Range
	 * 
	 * @return
	 */
	public Range2D to2DRange() {
		return new Range2D(MIN_X, MAX_X, MIN_Y, MAX_Y);

	}

	public boolean equals(Range3D range) {
		return this.MAX_TIME == range.MAX_TIME && this.MIN_TIME == range.MIN_TIME && this.MAX_X == range.MAX_X
				&& this.MIN_X == range.MIN_X && this.MAX_Y == range.MAX_Y && this.MIN_Y == range.MIN_Y;
	}

}
