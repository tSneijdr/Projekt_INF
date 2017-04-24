package utils.datastructures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import model.points.Point;
import utils.ranges.Range2D;

/**
 * Datenstruktur, stellt einen einfachen Quadtree dar
 * 
 * @author tobi
 *
 */
public class Quadtree {
	private final QuadtreeNode root;

	public final Range2D RANGE;

	public Quadtree(Range2D r, int maxNumElements) {
		RANGE = r;

		root = new QuadtreeNode(maxNumElements, r);
	}

	public void add(Point p) {
		root.add(p);
	}

	public Set<Point> getPoints(Range2D searchRange){
		return root.getPoints(searchRange);
	}
	
	private class QuadtreeNode {
		private QuadtreeNode NW = null, NE = null, SW = null, SE = null;
		private QuadtreeNode[] subtrees = { NW, NE, SW, SE };
		private final int maxNumElements;
		private final ArrayList<Point> points;
		public final Range2D range;

		public QuadtreeNode(int maxNumberOfElements, Range2D range) {
			this.maxNumElements = maxNumberOfElements;
			this.range = range;

			points = new ArrayList<Point>();
		}

		/**
		 * Fügt dem Knoten einen neuen Punkt zu. Falls der Knoten Unterknoten
		 * hat wird auch dem entsprechenden Unterknoten der Punkt zugefügt
		 * 
		 * @param p
		 */
		public void add(Point p) {
			if (!points.contains(p)) {
				points.add(p);
			}

			// Weitergeben an Unterbäume falls diese existieren
			if (NE != null && NW != null && SE != null && SW != null) {
				// Falls der letzte Punkt den Knoten voll gemacht hat wird
				// gesplittet
				if (points.size() > maxNumElements) {
					initializeSubtrees();
				}

				if (NW.range.inRange(p)) {
					NW.add(p);
				} else if (NE.range.inRange(p)) {
					NE.add(p);
				} else if (SW.range.inRange(p)) {
					SW.add(p);
				} else if (SE.range.inRange(p)) {
					SE.add(p);
				}
			}
		}

		/**
		 * Initialisiert alle Unterbäume mit den entsprechenden Ranges
		 */
		private void initializeSubtrees() {
			// nur ausführen wenn eine Aufteilung des Nodes notwendig und noch
			// nicht geschehen ist
			if (points.size() > maxNumElements && NW == null && NE == null && SE == null && SW == null) {
				int midX = range.MIN_X + (range.WIDTH / 2);
				int midY = range.MIN_Y + (range.HEIGHT / 2);

				// initialisiere die neuen Rangen
				Range2D nwRange = new Range2D(range.MIN_X, (range.MIN_X + midX), (range.MIN_Y + midY), range.MAX_Y);
				Range2D neRange = new Range2D((range.MIN_X + midX), range.MAX_X, (range.MIN_Y + midY), range.MAX_Y);
				Range2D swRange = new Range2D(range.MIN_X, (range.MIN_X + midX), range.MIN_Y, (range.MIN_Y + midY));
				Range2D seRange = new Range2D((range.MIN_X + midX), range.MAX_X, range.MIN_Y, (range.MIN_Y + midY));

				// initialisiere die neuen QuadtreeNodes
				NW = new QuadtreeNode(maxNumElements, nwRange);
				NE = new QuadtreeNode(maxNumElements, neRange);
				SW = new QuadtreeNode(maxNumElements, swRange);
				SE = new QuadtreeNode(maxNumElements, seRange);

				// Fügt Punkte erneut hinzu, so dass sie an Unternäume übergeben
				// werden
				for (Point p : points) {
					add(p);
				}
			}
		}

		/**
		 * Sucht alle in dem Suchraum gegebenen Punkte
		 * 
		 * @param searchRange
		 * @return
		 */
		public Set<Point> getPoints(Range2D searchRange) {
			if (!this.range.overlap(searchRange)) {
				return new HashSet<Point>();
			}

			HashSet<Point> fittingPoints = new HashSet<Point>();
			if (hasSubtrees()) {
				// Rekursiver Aufruf auf alle entsprechenden Unterknoten wenn
				// existent
				for (QuadtreeNode node : this.subtrees) {
					if (node != null && node.range.overlap(searchRange)) {
						fittingPoints.addAll(node.getPoints(searchRange));
					}
				}

			} else {
				// Untersucht alle Punkte in diesem Knoten
				for (Point p : this.points) {
					if (searchRange.inRange(p)) {
						fittingPoints.add(p);
					}
				}
			}
			return fittingPoints;
		}

		/**
		 * Gibt zurück ob dieser Knoten über Unterknoten verfügt
		 * 
		 * @return
		 */
		private boolean hasSubtrees() {
			for (QuadtreeNode node : this.subtrees) {
				if (node != null)
					return true;
			}
			return false;
		}
	}
}
