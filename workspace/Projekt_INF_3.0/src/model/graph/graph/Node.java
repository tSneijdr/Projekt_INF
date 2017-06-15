package model.graph.graph;

import java.util.HashSet;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
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

	// Beschreibt die Darstellung des Knotens
	private NodeType shape;

	// Beschreibt den Ort des Knotens sowie Maße
	private int xCenter;
	private int yCenter;

	private int radius;

	// Das eigentliche Objekt das dargestellt wird
	private Shape shapeObject;

	// Relation mit anderen Nodes
	private final Set<Edge> incoming;
	private final Set<Edge> outgoing;

	/**
	 * Erstellt einen konkreten Knoten aus einem abstrakten
	 * 
	 * @param data
	 */
	public Node(NodeData data) {
		this.data = data;
		this.data.addInstance(this);

		this.incoming = new HashSet<Edge>();
		this.outgoing = new HashSet<Edge>();

		// Setzt die passende Farbe
		setColor();

		this.shape = NodeType.DEFAULT;

		this.xCenter = 0;
		this.yCenter = 0;
		this.radius = 1;
	}

	/**
	 * Färbt alle nachfolgenden Knoten wie diesen Knoten
	 * 
	 * @param node
	 */
	public static void colorOutgoingEdges(Node node) {
		node.setColor();
		for (Edge edge : node.outgoing) {
			edge.getChild().getData().setColor(node.getColor());
			edge.setColor();
		}
	}

	/**
	 * Färbt alle eingehenden Knoten wie diesen Knoten
	 * 
	 * @param node
	 */
	public static void colorIncomingEdges(Node node) {
		node.setColor();
		for (Edge edge : node.incoming) {
			edge.getParent().getData().setColor(node.getColor());
			edge.setColor();
		}
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
	public static Shape getDrawableObject(Node node) {
		final Shape result;

		// Vorbereitung der Farbe
		node.setColor();

		// Rechne den Skalierungsfaktor ein und gebe ein entsprechendes
		// geformtes Objekt zurück, wobei spezifische Informationen bereits
		// gesetzt sind
		{
			double localXCenter = (double) node.xCenter;
			double localYCenter = (double) node.yCenter;
			double localRadius = (double) node.radius;

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

				circle.setStroke(node.getData().getColor());
				circle.setFill(node.getData().getColor());

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
				rec.setFill(node.getData().getColor());

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

					// Menu Item Color
					{
						Menu menu = new Menu("Color");

						Color[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.BROWN, Color.CRIMSON,
								Color.DARKOLIVEGREEN };
						String[] names = { "red", "green", "blue", "brown", "crimson", "dark olivegreen" };

						for (int i = 0; i < colors.length; i++) {
							String name = names[i];
							Color color = colors[i];

							MenuItem item = new MenuItem(name);
							item.setOnAction((ActionEvent e) -> {
								node.getData().setColor(color);
							});
							menu.getItems().add(item);
						}
						contextMenu.getItems().add(menu);
					}

					// Menu Item Aktiviere nachfolger
					{
						MenuItem controllItem = new MenuItem("Color all successors.");
						controllItem.setOnAction((ActionEvent e) -> {
							Node.colorOutgoingEdges(node);
						});
						contextMenu.getItems().add(controllItem);
					}

					// Menu Item Aktiviere Vorgänger
					{
						MenuItem controllItem = new MenuItem("Color all predecessors");
						controllItem.setOnAction((ActionEvent e) -> {
							Node.colorIncomingEdges(node);
						});
						contextMenu.getItems().add(controllItem);
					}

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
	// Getter und Setter (und adder)
	// ------------------------------------------------------------------
	public Color getColor() {
		return this.data.getColor();
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

	/**
	 * Aktualisiert die Farbe dieses Knotens sowie aller Kanten die diesen
	 * Knoten beinhalten
	 */
	public void setColor() {

		if (this.shapeObject != null) {
			this.shapeObject.setFill(this.getData().getColor());
			this.shapeObject.setStroke(this.getData().getColor());
		}
		for (Edge edge : outgoing) {
			edge.setColor();
		}
		for (Edge edge : incoming) {
			edge.setColor();
		}
	}

	public NodeData getData() {
		return data;
	}

	/**
	 * Fügt eine neue eingehende Kante die diesen Knoten betrifft zu
	 * 
	 * @param e
	 */
	public void addIncomingEdge(Edge e) {
		// Legalitätsprüfung
		if (e.getChild() == this && e.getParent() != this && !incoming.contains(e)) {
			for (Edge test : this.incoming) {
				if (e.getParent() == test.getParent()) { // Abbruch wenn Kante
															// vorhanden
					return;
				}
			}
			this.incoming.add(e);
		}
	}

	/**
	 * Fügt eine neue ausgehende Kante die diesen Knoten betrifft hinzu
	 * 
	 * @param e
	 */
	public void addOutgoingEdge(Edge e) {
		// Legalitätsprüfung
		if (e.getParent() == this && e.getChild() != this && !outgoing.contains(e)) {
			for (Edge test : this.outgoing) {
				if (e.getChild() == test.getChild()) { // Abbruch wenn Kante
														// vorhanden
					return;
				}
			}
			this.outgoing.add(e);
		}
	}

	public Set<Edge> getIncomingEdges() {
		Set<Edge> cp = new HashSet<Edge>();
		cp.addAll(this.incoming);
		return cp;
	}

	public Set<Edge> getOutgoingEdges() {
		Set<Edge> cp = new HashSet<Edge>();
		cp.addAll(this.outgoing);
		return cp;
	}
}
