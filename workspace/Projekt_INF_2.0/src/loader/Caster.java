package loader;

import java.util.LinkedList;
import java.util.List;

import core.Point;

/**
 * Enthält Methoden zum übertragen von Punkten in Listen und umgekehrt
 * Wird für io in files benötigt
 * @author tobi
 *
 */
public abstract class Caster {

	/**
	 * Erhält einen Punkt und gibt eine Liste 4-elementiger Arrays von Strings
	 * zurück. Hierbei gilt: str[0] = String.valueOf(point.X); str[1] =
	 * String.valueOf(point.Y); str[2] = String.valueOf(point.TIMEPOINT); str[3]
	 * = String.valueOf(point.DURATION);
	 * 
	 * @param point
	 * @return
	 */
	public static List<String[]> toList(Point point) {
		if (point == null)
			return null;

		// Navigiert zum Anfang der Liste
		while (point.getPreviousNode() != null) {
			point = point.getPreviousNode();
		}

		LinkedList<String[]> list = new LinkedList<String[]>();

		do {
			String[] str = new String[4];
			str[0] = String.valueOf(point.X);
			str[1] = String.valueOf(point.Y);
			str[2] = String.valueOf(point.TIMEPOINT);
			str[3] = String.valueOf(point.DURATION);

			list.add(str);
			point = point.getNextNode();
		} while (point != null);

		return list;
	}

	/**
	 * Erhält eine Liste von 4-elementiger Arrays und gibt einen Punkt zurück
	 * der eine Komplette datenreihe darstellt
	 * 
	 * es gilt:
	 * 
	 * str[0] = String.valueOf(point.X); str[1] = String.valueOf(point.Y);
	 * str[2] = String.valueOf(point.TIMEPOINT); str[3] =
	 * String.valueOf(point.DURATION);
	 * 
	 * @param pointList
	 * @return
	 */
	public static Point toPoint(List<String[]> pointList) {
		if (pointList == null || pointList.size() == 0)
			return null;

		for (String[] st : pointList) {
			if (st.length != 4)
				throw new IllegalArgumentException(
						"Einer der übergebenen Arrays hat eine illegale Länge");
		}

		LinkedList<Point> set = new LinkedList<Point>();
		for (String[] st : pointList) {
			int x, y;
			double timepoint, duration;
			// Caste die Stringliste in einen Point
			try {
				x = Integer.parseInt(st[0]);
				y = Integer.parseInt(st[1]);
				timepoint = Double.parseDouble(st[2]);
				duration = Double.parseDouble(st[3]);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalArgumentException(
						"Beim Casten der Liste zu einem Punkt ist ein Fehler aufgetreten");
			}

			Point p = new Point(x, y, timepoint, duration);

			// Fügt Point dem Ergebniss hinzu
			set.add(p);
		}

		// Verlinkt die Punkte aufeinander
		Point p = Point.link(set);

		return p;

	}
}
