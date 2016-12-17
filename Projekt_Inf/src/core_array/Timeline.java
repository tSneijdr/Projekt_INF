package core_array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import core.API;
import core.Point;

public class Timeline extends API {

	// Datenstrukturen
	private final Raster[] TIMELINE;

	// -----------------------------------------------------------------
	// Konstruktoren ---------------------------------------------------
	// -----------------------------------------------------------------
	/**
	 * 
	 * @param minX
	 *            der kleinste mögliche x-Wert des Bildbereichs
	 * @param minY
	 *            der kleinste mögliche y-Wert des Bildbereichs
	 * @param minTime
	 *            die Startzeit (beliebige Einheit)
	 * @param maxX
	 *            der größte mögliche x-Wert des Bildbereichs
	 * @param maxY
	 *            der größte mögliche y-Wert des Bildbereichs
	 * @param maxTime
	 *            der letzte mögliche Zeitpunkt
	 * @param numberOfRows
	 *            In welcher Auflösung soll die x-Achse diskretisiert werden
	 * @param numberOfColumns
	 *            In welcher Auflösung soll die y-Achse diskretisiert werden
	 * @param numberOfTimesteps
	 *            In wie viele Schritte soll die Zeitachse unterteilt werden
	 */
	public Timeline(int minX, int minY, int minTime, int maxX, int maxY,
			int maxTime, int numberRows, int numberColumns, int numberTimesteps) {
		super(minX, minY, minTime, maxX, maxY, maxTime, numberRows,
				numberColumns, numberTimesteps);
		// Initiert alle Datenstrukturen
		{
			TIMELINE = new Raster[NUMBER_OF_TIMESTEPS];

			// Iniziiert alle Raster
			for (int i = 0; i < NUMBER_OF_TIMESTEPS; i++) {
				TIMELINE[i] = createNewMatrix();
			}
		}
	}

	// -----------------------------------------------------------------
	// Hinzufügen von Punkten ------------------------------------------
	// -----------------------------------------------------------------
	/**
	 * Fügt einen Punkt seinen Daten entsprechend zu
	 * 
	 * @param p
	 */
	public void addPoint(Point p) {
		if (ALL_POINTS.contains(p)) {
			return;
		}

		int t, x, y;
		t = (int) Math.floor((double) p.TIMEPOINT / (double) LENGTH_TIMESTEP);
		x = (int) Math.floor((double) p.X / (double) SIZE_COLUMN);
		y = (int) Math.floor((double) p.Y / (double) SIZE_ROW);

		t = (t <= 0) ? 0 : t;
		x = (x <= 0) ? 0 : x;
		y = (y <= 0) ? 0 : y;

		if (0 <= x && x < NUMBER_OF_COLUMNS && 0 <= y && y < NUMBER_OF_ROWS
				&& 0 <= t && t < NUMBER_OF_TIMESTEPS) {
			TIMELINE[t].addPoint(p);
			ALL_POINTS.add(p);
		} else {
			throw new IllegalArgumentException("Punkt nicht im Raster "
					+ p.toString() + " mit Koordinaten (t:" + t + ", x:" + x
					+ ", y:" + y + ")");
		}
	}

	/**
	 * Nimmt Daten entgegen und ordnet sie richtig ein
	 * 
	 * @param x
	 * @param y
	 * @param time
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void addNewPoints(double[] x, double[] y, double[] duration) {
		int size = duration.length;

		if (size != y.length || size != x.length) {
			throw new IllegalArgumentException();
		}

		double totalTime = 0;

		if (size >= 1) {
			// Startpunkt
			totalTime += duration[0];
			Point p1 = new Point(x[0], y[0], duration[0], totalTime);
			STARTINGPOINTS.add(p1);
			addPoint(p1);

			// alle anderen Punkte
			for (int index = 1; index < size; index++) {
				totalTime += duration[index];
				Point p2 = new Point(x[index], y[index], duration[index],
						totalTime);

				try {
					p1.setNextNode(p2);
					p2.setPreviousNode(p1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				addPoint(p2);
				p1 = p2;
			}
		}

	}

	/**
	 * Nimmt Daten entgegen und ordnet sie richtig ein
	 * 
	 * @param x
	 * @param y
	 * @param time
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void addNewPoints(double[] x, double[] y, double[] duration,
			double[] timestamp, String[] name) {
		int size = duration.length;

		if (size != y.length || size != x.length) {
			throw new IllegalArgumentException();
		}

		if (size >= 1) {
			// Startpunkt
			Point p1 = new Point(name[0], x[0], y[0], duration[0], timestamp[0]);
			STARTINGPOINTS.add(p1);
			addPoint(p1);

			// alle anderen Punkte
			for (int index = 1; index < size; index++) {
				Point p2 = new Point(x[index], y[index], duration[index],
						timestamp[index]);

				try {
					p1.setNextNode(p2);
					p2.setPreviousNode(p1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				addPoint(p2);
				p1 = p2;
			}
		}

	}

	public void addPoints(Point startPoint) {
		if (startPoint != null) {
			Point currentPoint = startPoint;
			while (currentPoint.getPreviousNode() != null) {
				currentPoint = currentPoint.getPreviousNode();
			}

			// Fügt den Beginn dieser Datenlinie der Liste der Startpunkte zu
			STARTINGPOINTS.add(currentPoint);

			// Iteriert durch alle Punkte und fügt sie entsprechend hinzu
			addPoint(currentPoint);
			while (currentPoint.getNextNode() != null) {
				currentPoint = currentPoint.getNextNode();
				addPoint(currentPoint);
			}
		}
	}

	// -----------------------------------------------------------------
	// Konstruktoren ---------------------------------------------------
	// -----------------------------------------------------------------
	protected Raster createNewMatrix() {
		return new Raster(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS, LENGTH_TIMESTEP,
				SIZE_ROW, SIZE_COLUMN);
	}

	// -----------------------------------------------------------------
	// Zusammenfassen von Punkten --------------------------------------
	// -----------------------------------------------------------------
	/**
	 * Fast alle Raster der in dem Array vorhandenen Zeitscheiben zusammen
	 * 
	 * @param numbers
	 * @return
	 */
	@Deprecated
	public Raster getSum(int[] numbers) {
		Raster r = createNewMatrix();

		for (int i : numbers) {
			if (0 <= i && i < NUMBER_OF_TIMESTEPS) {
				r = Raster.add(r, TIMELINE[i]);
			} else {
				throw new IllegalArgumentException();
			}
		}

		return r;
	}

	/**
	 * Fast alle Zeitscheiben zusammen die im gegebenen Intervall liegen
	 * (inklusive Anfang und Ende)
	 * 
	 * @param intervallAnfang
	 * @param intervallEnde
	 * @return
	 */
	public HashSet<Point> getPoints(int intervallAnfang, int intervallEnde) {
		if (!(inTime(intervallAnfang) && inTime(intervallEnde))) {
			throw new IllegalArgumentException();
		}

		Raster r = createNewMatrix();
		HashSet<Point> s = new HashSet<Point>();

		for (Point p : ALL_POINTS) {
			if (intervallAnfang <= p.TIMEPOINT && intervallEnde > p.TIMEPOINT) {
				s.add(p);
			}
		}
		return s;
	}

	/**
	 * Gibt eine Kopie des n-ten Rasters zurück
	 * 
	 * @param n
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashSet<Point> getPoints(int n) {
		if (0 <= n && n < NUMBER_OF_TIMESTEPS) {
			HashSet<Point> s = new HashSet<Point>();
			for (Point p : TIMELINE[n].ALL_POINTS) {
				s.add(p);
			}
			return s;
		} else {
			throw new IllegalArgumentException(
					"Dieser ZEitabschnitt ist unbekannt.");
		}
	}

	public HashSet<Point> getStartingpoints() {
		HashSet<Point> res = new HashSet<Point>();
		for (Point p : STARTINGPOINTS) {
			res.add(p);
		}
		return res;
	}

	@Override
	public void addPoints(HashSet<Point> points) {
		// TODO Auto-generated method stub

	}

	@Override
	public HashSet<Point> getPoints() {
		HashSet<Point> s = new HashSet<Point>();
		for (Point p : ALL_POINTS) {
			s.add(p);
		}
		return s;
	}
}
