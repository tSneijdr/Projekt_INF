package model.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Stellt einen Knoten im Graphen dar. Speichert u.a. Daten zum Darstellen des
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

	// Zusätzliche Informationnen zu diesem Knoten
	private final Set<String> informations;

	public Node(HashSet<String> informations) {
		this.color = Color.BLACK;
		this.shape = NodeType.DEFAULT;

		this.xCenter = 0;
		this.yCenter = 0;

		this.radius = 1;

		// Eltern- und Kinderliste
		parents = new LinkedList<Node>();
		children = new LinkedList<Node>();

		// Infromationen
		this.informations = informations;
	}

	public Node() {
		this(null);
	}

	/**
	 * Gibt eine zeichenbare Darstellung des Knotens zurück
	 * 
	 * Die Daten werden hierbei aus diesem Node bezogen Die Form des
	 * zurückgegebenen Objekts hängt vom NodeShape-Objekt shape ab, der
	 * default-Wert ist ein Kreis
	 * 
	 * @return
	 */
	public Shape getDrawableObject(double scaleFactor) {
		final Shape result;

		// Rechne den Skalierungsfaktor ein und gebe ein entsprechendes
		// geformtes Objekt zurück, wobei spezifische Informationen bereits
		// gesetzt sind
		{
			double localXCenter = (double) this.xCenter * scaleFactor;
			double localYCenter = (double) this.yCenter * scaleFactor;
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

				result = rec;
				break;
			}

			case CIRCLE: {
				Circle circle = new Circle();

				circle.setCenterX(localXCenter);
				circle.setCenterY(localYCenter);
				circle.setRadius(localRadius);

				circle.setStroke(this.color);
				circle.setFill(this.color);

				result = circle;
				break;
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

				result = rec;
				break;
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

				result = circle;
				break;
			}
		}

		// Zufügen aller Eventlistener
		{
			result.setFocusTraversable(true);

			result.setOnMouseClicked((MouseEvent event) -> {

				if (!(informations == null || informations.isEmpty())) {
					// Fügt ein Kontextmenü mit allen zusätlichen Informationen
					// zu
					ContextMenu contextMenu = new ContextMenu();
					
					for (String info : informations) {
						MenuItem item = new MenuItem(info);
						item.setDisable(false);
						contextMenu.getItems().add(item);
					}

					contextMenu.show(result, event.getScreenX(), event.getScreenY());
				}
			});

		}

		return result;
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
