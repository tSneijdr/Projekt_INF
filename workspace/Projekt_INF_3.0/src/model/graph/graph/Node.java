package model.graph.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.graph.data.NodeData;

/**
 * Stellt einen Knoten im Graphen dar. Speichert u.a. Daten zum Darstellen des
 * Knotens
 * 
 * @author tobi
 *
 */
public class Node {

	//
	private final NodeData data;

	// Eltern und Kinder
	private final List<Node> children;
	private final List<Node> parents;

	// Beschreibt die Darstellung des Knotens
	private Color color;
	private NodeType shape;

	// Beschreibt den Ort des Knotens sowie Maße
	private int xCenter;
	private int yCenter;

	private int radius;

	// Das eigentliche Objekt das dargestellt wird
	private Shape shapeObject;

	public Node(NodeData data) {
		this.data = data;
		this.data.addInstance(this);

		this.children = new ArrayList<Node>();
		this.parents = new ArrayList<Node>();

		// Setzt die passende Farbe
		setColor();

		this.shape = NodeType.DEFAULT;

		this.xCenter = 0;
		this.yCenter = 0;
		this.radius = 1;
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
	public static Shape getDrawableObject(double scaleFactor, Node node) {
		final Shape result;

		// Vorbereitung der Farbe
		node.setColor();

		// Rechne den Skalierungsfaktor ein und gebe ein entsprechendes
		// geformtes Objekt zurück, wobei spezifische Informationen bereits
		// gesetzt sind
		{
			double localXCenter = (double) node.xCenter * scaleFactor;
			double localYCenter = (double) node.yCenter * scaleFactor;
			double localRadius = (double) node.radius * scaleFactor;

			switch (node.shape) {
			case RECTANGLE: {
				Rectangle rec = new Rectangle();

				rec.setX((localXCenter - (localRadius / 2)));
				rec.setY((localYCenter - (localRadius / 2)));
				rec.setWidth(localRadius);
				rec.setHeight(localRadius);

				rec.setStroke(node.getColor());
				rec.setFill(node.getColor());

				result = rec;
				break;
			}

			case CIRCLE: {
				Circle circle = new Circle();

				circle.setCenterX(localXCenter);
				circle.setCenterY(localYCenter);
				circle.setRadius(localRadius);

				circle.setStroke(node.color);
				circle.setFill(node.color);

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

				rec.setStroke(node.getColor());
				rec.setFill(node.color);

				result = rec;
				break;
			}

			case RAGE:
				Rectangle rec = new Rectangle();

				String url = node.getClass().getResource("rage.jpg").toString();
				Image image = new Image(url, localRadius * 10, localRadius * 10, false, false);
				System.out.println("W: " + image.getWidth() + ", H: " + image.getHeight());

				rec.setFill(new ImagePattern(image));
				System.out.println("W: " + rec.getWidth() + ", recH: " + rec.getHeight());

				rec.setVisible(true);

				rec.setX((localXCenter - (localRadius / 2)));
				rec.setY((localYCenter - (localRadius / 2)));
				rec.setWidth(localRadius);
				rec.setHeight(localRadius);

				rec.setStroke(Color.BLACK);

				result = rec;
				break;

			case PICTURE:
			case DEFAULT:
			default:
				Circle circle = new Circle();

				circle.setCenterX(localXCenter);
				circle.setCenterY(localYCenter);
				circle.setRadius(localRadius);

				circle.setStroke(node.getColor());
				circle.setFill(node.getColor());

				result = circle;
				break;
			}
		}

		// Zufügen aller Eventlistener
		{
			result.setFocusTraversable(true);

			result.setOnMouseClicked((MouseEvent event) -> {

				Set<String> informations = node.getData().getInformations();
				if (!(informations == null || informations.isEmpty())) {
					// Fügt ein Kontextmenü mit allen zusätlichen Informationen
					// zu
					ContextMenu contextMenu = new ContextMenu();

					MenuItem controllItem = new MenuItem("Activate");
					controllItem.setOnAction((ActionEvent e) -> {
						node.getData().toggle();
						node.setColor();
					});
					contextMenu.getItems().add(controllItem);

					for (String info : informations) {
						MenuItem item = new MenuItem(info);
						item.setDisable(false);
						contextMenu.getItems().add(item);
					}

					contextMenu.show(result, event.getScreenX(), event.getScreenY());
				}
			});

		}

		node.shapeObject = result;
		
		return result;
	}

	// ------------------------------------------------------------------
	// Getter und Setter
	// ------------------------------------------------------------------
	public List<Node> getChildren() {
		return new ArrayList<Node>(children);
	}

	public List<Node> getParents() {
		return new ArrayList<Node>(parents);
	}

	public void addChild(Node child) {
		if (children.contains(child) || child == this) {
			return;
		} else {
			this.children.add(child);
			child.addParent(this);
		}
	}

	public void addParent(Node parent) {
		if (parents.contains(parent) || parent == this) {
			return;
		} else {
			this.parents.add(parent);
			parent.addChild(this);
		}
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;

		if (this.shapeObject != null) {
			this.shapeObject.setFill(color);
			this.shapeObject.setStroke(color);
		}
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

		if (this.shapeObject != null) {
			this.shapeObject.setFill(color);
			this.shapeObject.setStroke(color);
		}
	}

	public void setyCenter(int yCenter) {
		this.yCenter = yCenter;
	}

	public void setRadius(int radius) {
		this.radius = radius;

	}

	public boolean isActive() {
		return data.isActive();
	}

	public void setActive(boolean active) {
		this.data.setActive(active);
		setColor();
	}

	public void setColor() {
		if (color == null) {
			setColor(Color.BLACK);
		}
		if (color == Color.BLACK || color == Color.RED) {
			setColor(((data.isActive()) ? Color.RED : Color.BLACK));
		}

	}

	public NodeData getData() {
		return data;
	}
	
}
