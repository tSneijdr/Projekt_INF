package core;

import java.util.HashSet;
import java.util.Set;

public class Record {
	public final String TITLE;
	public final String PARTICIPANT;
	public final Point firstPoint;

	public Record(String title, String participant, Point p) {
		TITLE = title;
		PARTICIPANT = participant;

		// Iteriert zum Anfang des Datensatzes
		while (p.getPreviousNode() != null) {
			p = p.getPreviousNode();
		}
		firstPoint = p;
	}

	public Record(String title, Point p) {
		this(title, "default", p);
	}

	/**
	 * Konstruktor der nur einen Punkt als Parameter erhält
	 * @param p
	 */
	public Record(Point p) {
		this("default", p);
	}

	/**
	 * Gibt ein Set zurück das alle Punkte der Datenreihe enthält
	 * @return
	 */
	public Set<Point> getAllPoints() {
		HashSet<Point> s = new HashSet<Point>();

		Point p = firstPoint;
		s.add(p);
		while (p.getNextNode() != null) {
			p = p.getNextNode();
			s.add(p);
		}
		return s;
	}

	/**
	 * Ausdurck ob ein gegebener Punkt zu dieser Datenreihe gehört
	 * 
	 * @param p
	 * @return
	 */
	public boolean isElement(Point p) {
		Point current = this.firstPoint;

		if (current == p)
			return true;
		while (current.getNextNode() != null) {
			current = current.getNextNode();
			if (current == p) {
				return true;
			}
		}
		return false;
	}

}