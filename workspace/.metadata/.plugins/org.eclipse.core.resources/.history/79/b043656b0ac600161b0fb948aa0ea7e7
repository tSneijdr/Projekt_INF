package tier_0;

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

	public Record(Point p) {
		this("default", p);
	}

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

	public boolean isElement(Point p) {
		return getAllPoints().contains(p);
	}

}
