package model.points;

import javafx.scene.paint.Color;
import java.util.HashSet;
import java.util.Set;

/**
 * Ein einzelner Datensatz. Enthält neben den Punkte auch z.b. den Titel und den
 * Namen des Teilnehmers
 * 
 * @author tobi
 *
 */
public class Record {
	// Daten die die eigentliche Datenreihe betreffen
	private final String url;

	private final String participant;
	private final Point firstPoint;

	// Daten die die Darstellung der Datenreihe betreffen
	private boolean active = true;
	private Color color = Color.BLACK;

	public Record(String url, String participant, Point p) {
		this.url = url;
		this.participant = participant;

		if (p != null) {
			// Iteriert zum Anfang des Datensatzes
			while (p.getPreviousNode() != null) {
				p = p.getPreviousNode();
			}
			firstPoint = p;
		} else {
			firstPoint = null;
		}
	}

	/**
	 * Gibt ein Set zurück das alle Punkte der Datenreihe enthält
	 * 
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getParticipant() {
		return participant;
	}

	public Point getFirstPoint() {
		return firstPoint;
	}

	public String toString() {
		return "( " + url + " || " + participant + " || " + firstPoint + " )";
	}

	public String getURL() {
		return url;
	}

}
