package model.graph.graph;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Sphere;

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

	// Daten zur Darstellung
	private final List<Line> lineObjects;

	public Edge(Node parent, Node child) throws Exception {
		if (parent == child) {
			throw new Exception("Der Elternknoten kann nicht gleich dem Kindknoten sein.");
		}

		this.thickness = 1.0;

		this.parent = parent;
		this.child = child;

		this.parent.addOutgoingEdge(this);
		this.child.addIncomingEdge(this);

		lineObjects = new LinkedList<Line>();
	}

	public static List<Shape> getDrawableObject(Edge edge) {
		List<Shape> objects = new LinkedList<Shape>();
		Line l = null;

		// Setup der der Linie
		{
			l = new Line();

			l.setStartX(edge.getParent().getxCenter());
			l.setStartY(edge.getParent().getyCenter());

			l.setEndX(edge.getChild().getxCenter());
			l.setEndY(edge.getChild().getyCenter());

			if (edge.getParent().getColor().equals(edge.getChild().getColor())) {
				l.setStroke(edge.getParent().getColor());
			} else {
				l.setStroke(Color.BLACK);
			}

			l.setStrokeWidth(edge.getThickness());

			edge.addLineObject(l);
			objects.add(l);
		}

		// Setup des Pfeilkopfes
		{

			double targetX = l.getEndX();
			double targetY = l.getEndY();

			double corner1X;
			double corner1Y;

			double corner2X;
			double corner2Y;

			{
				// Geometrische Berechnungen zum ermitteln der Ecken
				final double bigDeffX = -(l.getEndX() - l.getStartX());
				final double bigDeffY = -(l.getEndY() - l.getStartY());
				final double bigLen = Math.sqrt(bigDeffX * bigDeffX + bigDeffY * bigDeffY);

				final double vecX = bigDeffX / bigLen;
				final double vecY = bigDeffY / bigLen;

				final double orthVecX = -vecY;
				final double orthVecY = vecX;

				double smallLen = bigLen / 40;

				targetX = targetX + vecX * edge.getChild().getRadius();
				targetY = targetY + vecY * edge.getChild().getRadius();

				final double crossX = targetX + vecX * smallLen;
				final double crossY = targetY + vecY * smallLen;

				corner1X = crossX + orthVecX * smallLen;
				corner1Y = crossY + orthVecY * smallLen;

				corner2X = crossX - orthVecX * smallLen;
				corner2Y = crossY - orthVecY * smallLen;
			}

			// Die drei Ecken des Pfeilkopfes
			Double[] target = { targetX, targetY };
			Double[] corner1 = { corner1X, corner1Y };
			Double[] corner2 = { corner2X, corner2Y };

			// Erstelle Pfeilkopf
			{
				/*
				 * Polygon poly = new Polygon();
				 * poly.getPoints().addAll(target);
				 * poly.getPoints().addAll(corner1);
				 * poly.getPoints().addAll(corner2);
				 * 
				 * // Setze Farbe auf gleiche Farbe wie Kante
				 * poly.setStroke(Color.BLACK); poly.setFill(Color.BLACK);
				 */

				Line l1 = new Line();
				Line l2 = new Line();

				// Setup l1
				{
					l1.setStartX(targetX);
					l1.setStartY(targetY);

					l1.setEndX(corner1X);
					l1.setEndY(corner1Y);

					l1.setStroke(l.getStroke());
					l1.setStrokeWidth(l.getStrokeWidth());
				}

				// Setup l2
				{
					l2.setStartX(targetX);
					l2.setStartY(targetY);

					l2.setEndX(corner2X);
					l2.setEndY(corner2Y);

					l2.setStroke(l.getStroke());
					l2.setStrokeWidth(l.getStrokeWidth());
				}

				edge.addLineObject(l1);
				edge.addLineObject(l2);

				objects.add(l1);
				objects.add(l2);
			}
		}

		return objects;
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

	public Node getParent() {
		return parent;
	}

	public Node getChild() {
		return child;
	}

	public void addLineObject(Line l) {
		this.lineObjects.add(l);
	}

	public List<Line> getLineObject() {
		return new LinkedList<Line>(this.lineObjects);
	}

	public void setColor() {
		if (this.getLineObject() != null) {
			if (this.getParent().getColor().equals(this.getChild().getColor())) {
				for (Line l : getLineObject()) {
					l.setStroke(this.getParent().getColor());
				}
			} else {
				for (Line l : getLineObject()) {
					l.setStroke(Color.BLACK);
				}
			}
		}
	}

	// ----------------------------------------------------
	// Java Methoden
	// ----------------------------------------------------
	public boolean equals(Edge e) {
		return this.parent.equals(e.getParent()) && child.equals(e.getChild()) && thickness.equals(e.thickness);
	}

}
