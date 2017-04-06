package model;

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

	// Daten zur Darstellung
	private Color color;
	private Double thickness;

	// Die beiden verbundenen Kanten
	private Node parent;
	private Node child;

	public Edge(Node parent, Node child) {
		color = Color.DARKGREEN;
		thickness = 1.0;

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

			l.setStroke(color);
			l.setStrokeWidth(thickness * scaleFactor);
		}
		return l;
	}

}
