package core;

import java.util.HashSet;

public abstract class API {
	// Daten die der Diskretisierung dienen
	public final int NUMBER_OF_ROWS;
	public final int NUMBER_OF_COLUMNS;
	public final int NUMBER_OF_TIMESTEPS;

	public final double LENGTH_TIMESTEP;
	public final double SIZE_ROW;
	public final double SIZE_COLUMN;

	// Alle Daten bzgl des Bildbereichs
	public final int MIN_X;
	public final int MIN_Y;
	public final int MIN_TIME;
	public final int MAX_X;
	public final int MAX_Y;
	public final int MAX_TIME;

	// Datenstrukturen
	protected final HashSet<Point> STARTINGPOINTS;
	protected final HashSet<Point> ALL_POINTS;

	public API(int minX, int minY, int minTime, int maxX, int maxY,
			int maxTime, int numberOfRows, int numberOfColumns,
			int numberOfTimesteps) {
		// Initialliere alle Daten des Bildbereichs
		{
			MIN_X = minX;
			MIN_Y = minY;
			MIN_TIME = minTime;
			MAX_X = maxX;
			MAX_Y = maxY;
			MAX_TIME = maxTime;
		}

		// Innitiert alle Daten zur Diskretisierung
		{
			this.NUMBER_OF_ROWS = numberOfRows;
			this.NUMBER_OF_COLUMNS = numberOfColumns;
			this.NUMBER_OF_TIMESTEPS = numberOfTimesteps;

			LENGTH_TIMESTEP = (double) MAX_TIME / (double) numberOfTimesteps;
			SIZE_ROW = (double) MAX_Y / (double) NUMBER_OF_ROWS;
			SIZE_COLUMN = (double) MAX_X / (double) NUMBER_OF_COLUMNS;
		}
		// Initiert alle Datenstrukturen
		{
			STARTINGPOINTS = new HashSet<Point>();
			ALL_POINTS = new HashSet<Point>();
		}
	}

	public API(int minX, int minY, int minTime, int maxX, int maxY,
			int maxTime, int numberOfRows, int numberOfColumns,
			int numberOfTimesteps, HashSet<Point> points) {

		// Initialliere alle Daten des Bildbereichs
		{
			MIN_X = minX;
			MIN_Y = minY;
			MIN_TIME = minTime;
			MAX_X = maxX;
			MAX_Y = maxY;
			MAX_TIME = maxTime;
		}

		// Innitiert alle Daten zur Diskretisierung
		{
			this.NUMBER_OF_ROWS = numberOfRows;
			this.NUMBER_OF_COLUMNS = numberOfColumns;
			this.NUMBER_OF_TIMESTEPS = numberOfTimesteps;

			LENGTH_TIMESTEP = (double) MAX_TIME / (double) numberOfTimesteps;
			SIZE_ROW = (double) MAX_Y / (double) NUMBER_OF_ROWS;
			SIZE_COLUMN = (double) MAX_X / (double) NUMBER_OF_COLUMNS;
		}
		// Initiert alle Datenstrukturen
		{
			STARTINGPOINTS = new HashSet<Point>();
			ALL_POINTS = new HashSet<Point>();
		}
		//
		{
			for (Point p : points){
				addPoint(p);
			}
		}

	}

	public int getNumberOfPoints() {
		return ALL_POINTS.size();
	}

	// -----------------------------------------------------------------
	// Prädikate -------------------------------------------------------
	// -----------------------------------------------------------------

	/**
	 * ein Prädikat ob ein Gegebener Punkt im Bildbereich der Timeline ist
	 * 
	 * @param p
	 * @return
	 */
	public boolean isInRange(Point p) {
		return (0 <= p.X && p.X < MAX_X && 0 <= p.Y && p.Y < MAX_Y
				&& 0 <= p.TIMEPOINT && p.TIMEPOINT < MAX_TIME);
	}

	/**
	 * Gibt zurück ob der mitgegebene x-Wert im Bildbereich liegt
	 * 
	 * @param x
	 * @return
	 */
	public boolean inX(int x) {
		return (MIN_X <= x && x < MAX_X);
	}

	/**
	 * Gibt zurück ob der mitgegebene y-Wert im Bildbereich liegt
	 * 
	 * @param y
	 * @return
	 */
	public boolean inY(int y) {
		return (MIN_Y <= y && y < MAX_Y);
	}

	/**
	 * Gibt zurück ob der mitgegebene Zeitpunkt im Bildbereich liegt
	 * 
	 * @param time
	 * @return
	 */
	public boolean inTime(int time) {
		return (MIN_TIME <= time && time < MAX_TIME);
	}

	// -----------------------------------------------------------------
	// Punkte verwalten ------------------------------------------------
	// -----------------------------------------------------------------
	public abstract void addPoint(Point p);

	public abstract void addPoints(Point startpoint);

	public abstract void addPoints(HashSet<Point> points);

	public abstract HashSet<Point> getStartingpoints();

	public abstract HashSet<Point> getPoints();

	public abstract HashSet<Point> getPoints(int start, int end);

	public abstract HashSet<Point> getPoints(int index);

	// -----------------------------------------------------------------
	// Sonstiges -------------------------------------------------------
	// -----------------------------------------------------------------

}
