package core;

public class Point {
	public final double X;
	public final double Y;
	/** Zeit an dem der Punkt erreicht wird **/
	public final double TIMEPOINT;
	/** Zeit die der Punkt betrachtet wird **/
	public final double DURATION;
	public final String TITLE;

	private Point previousNode;
	private Point nextNode;

	public Point(String title, double x, double y, double duration,
			double timepoint) {
		X = x;
		Y = y;
		TIMEPOINT = timepoint;
		DURATION = duration;

		previousNode = null;
		nextNode = null;

		TITLE = title;
	}

	public Point(double x, double y, double timepoint, double duration) {
		X = x;
		Y = y;
		TIMEPOINT = timepoint;
		DURATION = duration;

		previousNode = null;
		nextNode = null;

		TITLE = "default";
	}

	// -----------------------------------------------------------------
	// Getter und Setter -----------------------------------------------
	// -----------------------------------------------------------------

	/**
	 * Gibt den im Datensatz nächsten Punkt zurück
	 * 
	 * @return nächster Punkt im Datensatz, oder null wenn kein solcher
	 *         existiert
	 */
	public Point getNextNode() {
		return nextNode;
	}

	/**
	 * Stellt den nächsten Punkt auf den Punkt p, wenn noch kein nächster Punkt
	 * festgelegt ist. Falls doch wird eine Fehlermeldung geworfen
	 * 
	 * @param p
	 *            der nächste Punkt
	 * @throws Exception
	 */
	public void setNextNode(Point p) throws Exception {
		if (nextNode == null) {
			nextNode = p;
		} else {
			throw new Exception("Wert wurde bereits gesetzt");
		}
	}

	/**
	 * Gibt den Punkt zurück der im Datensatz vor diesem steht
	 * 
	 * @return nächster Punkt im Datensatz, oder null wenn kein solcher
	 *         existiert
	 */
	public Point getPreviousNode() {
		return previousNode;
	}

	/**
	 * Stellt den Verweis auf den letzen Punkt auf den Punkt p, wenn noch kein
	 * Punkt festgelegt ist. Falls doch wird eine Fehlermeldung geworfen.
	 * 
	 * @param p
	 *            der nächste Punkt
	 * @throws Exception
	 */
	public void setPreviousNode(Point p) throws Exception {
		if (previousNode == null) {
			previousNode = p;
		} else {
			throw new Exception("Wert wurde bereits gesetzt");
		}
	}

	// -----------------------------------------------------------------
	// System ----------------------------------------------------------
	// -----------------------------------------------------------------
	public String toString() {
		return "(" + TITLE + ", " + X + ", " + Y + ", " + TIMEPOINT + ", "
				+ DURATION + ")";
	}

	public boolean equals(Point p) {
		if (!(this.toString().equals(p.toString()))) {
			return false;
		}

		{
			Point currentPoint1, currentPoint2;

			// Vergleicht alle Knoten die in der Liste vor diesem stehen
			currentPoint1 = this;
			currentPoint2 = p;

			while (currentPoint1.previousNode != null
					&& currentPoint2.previousNode != null) {
				currentPoint1 = currentPoint1.previousNode;
				currentPoint2 = currentPoint2.previousNode;

				if (!(currentPoint1.toString().equals(currentPoint2.toString()))) {
					return false;
				}
			}
			if (!(currentPoint1.previousNode == null && currentPoint2.previousNode == null)) {
				return false;
			}

			// Vergleicht alle Knoten die in der Liste nach diesem stehen
			currentPoint1 = this;
			currentPoint2 = p;

			while (currentPoint1.nextNode != null
					&& currentPoint2.nextNode != null) {
				currentPoint1 = currentPoint1.nextNode;
				currentPoint2 = currentPoint2.nextNode;

				if (!(currentPoint1.toString().equals(currentPoint2.toString()))) {
					return false;
				}
			}
			if (!(currentPoint1.nextNode == null && currentPoint2.nextNode == null)) {
				return false;
			}
		}
		return true;

	}

	public Point copy() throws Exception {
		// Kopie des eigentlichen Knotens
		Point p = new Point(TITLE, X, Y, DURATION, TIMEPOINT);

		Point currentNode1, currentNode2;

		{ // Kopiert alle nach hinten verlinkten Nodes
			currentNode1 = this;
			currentNode2 = p;

			while (currentNode1.getPreviousNode() != null) {
				currentNode1 = currentNode1.getPreviousNode();
				Point p1 = new Point(currentNode1.TITLE, currentNode1.X,
						currentNode1.Y, currentNode1.DURATION,
						currentNode1.TIMEPOINT);
				try {
					p1.setNextNode(currentNode2);
					currentNode2.setPreviousNode(p1);
				} catch (Exception e) {
					throw e;
				}
				currentNode2 = p1;
			}
		}
		{ // Kopiert alle nach vorne verlinkten Nodes
			currentNode1 = this;
			currentNode2 = p;

			while (currentNode1.getNextNode() != null) {
				currentNode1 = currentNode1.getNextNode();
				Point p1 = new Point(currentNode1.TITLE, currentNode1.X,
						currentNode1.Y, currentNode1.DURATION,
						currentNode1.TIMEPOINT);
				try {
					p1.setPreviousNode(currentNode2);
					currentNode2.setNextNode(p1);
				} catch (Exception e) {
					throw e;
				}
				currentNode2 = p1;
			}

		}

		return p;
	}
}
