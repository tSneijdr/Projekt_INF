package model.graph.graph;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Stellt eine Kante zwischen zwei Knoten dar, dabei werden u.a. Daten wie Farbe
 * der Kante und Dicke einbezogen.
 * 
 * @author tobi
 *
 */
public class Edge {
	// Die Daten
	private final Node parent;
	private final Node child;

	// Daten zur Darstellung
	private Double thickness;

	public Edge(Node parent, Node child) {
		this.thickness = 1.0;

		this.parent = parent;
		this.child = child;
	}

	public Line getDrawableObject(double scaleFactor) {
		Line l = new Line();

		// Setup der Grundlagen
		{
			l.setStartX(parent.getxCenter() * scaleFactor);
			l.setStartY(parent.getyCenter() * scaleFactor);

			l.setEndX(child.getxCenter() * scaleFactor);
			l.setEndY(child.getyCenter() * scaleFactor);

			if (parent.getColor().equals(child.getColor())) {
				l.setStroke(parent.getColor());
			} else {
				l.setStroke(Color.BLACK);
			}

			l.setStrokeWidth(thickness * scaleFactor);
		}
		return l;
	}

	// ----------------------------------------------------------------
	// Getter und Setter
	// ----------------------------------------------------------------
	public Double getThickness() {
		return thickness;
	}

	public void setThickness(Double thickness) {
		this.thickness = thickness;
	}

}
