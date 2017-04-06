package model;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Stellt einen Knoten im Graphen dar Speichert u.a. Daten zum darstellen des
 * Knotens
 * 
 * @author tobi
 *
 */
public class Node {

	// Beschreibt die Darstellung des Knotens
	private Color color;
	private NodeType shape;

	// Beschreibt den Ort des Knotens sowie Maße
	private int xCenter;
	private int yCenter;

	private int radius;

	// Eltern- und Kinderknoten
	private final List<Node> parents;
	private final List<Node> children;

	public Node() {
		this.color = Color.BLACK;
		this.shape = NodeType.DEFAULT;

		this.xCenter = 0;
		this.yCenter = 0;

		this.radius = 1;

		// Eltern- und Kinderliste
		parents = new LinkedList<Node>();
		children = new LinkedList<Node>();
	}

	/**
	 * Gibt eine zeichenbare Darstellung des Knotens zurück
	 * 
	 * @return
	 */
	public Shape getDrawableObject(double scaleFactor, double displacementX, double displacementY) {
		// Rechne den Skalierungsfaktor ein und gebe ein entsprechendes Objekt
		// zurück
		{
			double localXCenter = (double) this.xCenter * scaleFactor + displacementX;
			double localYCenter = (double) this.yCenter * scaleFactor + displacementY;
			double localRadius = (double) this.radius * scaleFactor;

			switch (shape) {
			case RECTANGLE: {
				Rectangle rec = new Rectangle();

				rec.setX((localXCenter - (localRadius / 2)));
				rec.setY((localYCenter - (localRadius / 2)));
				rec.setWidth(localRadius);
				rec.setHeight(localRadius);

				rec.setStroke(this.color);
				rec.setFill(this.color);

				return rec;
			}

			case CIRCLE: {
				Circle circle = new Circle();

				circle.setCenterX(localXCenter);
				circle.setCenterY(localYCenter);
				circle.setRadius(localRadius);

				circle.setStroke(this.color);
				circle.setFill(this.color);

				return circle;
			}

			case DIAMOND: {
				Rectangle rec = new Rectangle();

				rec.setX(localXCenter - (localRadius / 2));
				rec.setY(localYCenter - (localRadius / 2));
				rec.setWidth(localRadius);
				rec.setHeight(localRadius);

				rec.setRotate(45);
				rec.rotateProperty();

				rec.setStroke(this.color);
				rec.setFill(this.color);

				return rec;
			}

			case PICTURE:
			case DEFAULT:
			default:
				Circle circle = new Circle();

				circle.setCenterX(localXCenter);
				circle.setCenterY(localYCenter);
				circle.setRadius(localRadius);

				circle.setStroke(this.color);
				circle.setFill(this.color);

				return circle;
			}
		}
	}

	// ------------------------------------------------------------------
	// Getter und Setter
	// ------------------------------------------------------------------
	public void addChild(Node child) {
		children.add(child);
	}

	public List<Node> getChildren() {
		return children;
	}

	public void addParent(Node parent) {
		children.add(parent);
	}

	public List<Node> getParents() {
		return parents;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public NodeType getShape() {
		return shape;
	}

	public void setShape(NodeType shape) {
		this.shape = shape;
	}

	public int getxCenter() {
		return xCenter;
	}

	public int getyCenter() {
		return yCenter;
	}

	public int getRadius() {
		return radius;
	}

	public void setxCenter(int xCenter) {
		this.xCenter = xCenter;
	}

	public void setyCenter(int yCenter) {
		this.yCenter = yCenter;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
}
