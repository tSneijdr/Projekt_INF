package model.graph.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import model.graph.data.GraphData;
import model.graph.data.NodeData;

public class Graph {
	// Faktoren für korrekte Darstellung
	private double oldX = 0.0;
	private double oldY = 0.0;

	// Speichern von Knoten und Kanten
	private final List<Node> allNodes;
	private final List<Edge> allEdges;

	// Mapping vin Daten auf Knoten
	private final Map<NodeData, Node> mapping;

	// Originale Daten
	private final GraphData data;

	/**
	 * Standardkonsturktor
	 */
	public Graph(GraphData data, List<Node> allNodes, List<Edge> allEdges, Map<NodeData, Node> mapping) {
		this.allNodes = allNodes;
		this.allEdges = allEdges;
		this.mapping = mapping;
		this.data = data;
	}

	/**
	 * Gibt ein JavaFX Pane zurück dass den Graphen darstellt. Kann in GUIs
	 * eingebaut werden
	 * 
	 * @param paneWidth
	 * @param paneHeight
	 * @param scaleFactor
	 * @return
	 */
	public static BorderPane getPane(int paneWidth, int paneHeight, Graph graph, boolean showEdges,
			boolean showBackgroundImage) {

		// Setup für das Pane
		BorderPane pane = new BorderPane();
		pane.setMinSize(paneWidth, paneHeight);
		pane.setPrefSize(paneWidth, paneHeight);

		pane.setFocusTraversable(true);

		// Skaliere den Graphen
		{
			int maxX = 0;
			int maxY = 0;
			for (Node node : graph.getAllNodes()) {
				int nodeX = node.getxCenter() + node.getRadius();
				int nodeY = node.getyCenter() + node.getRadius();

				maxX = (maxX > nodeX) ? maxX : nodeX;
				maxY = (maxY > nodeY) ? maxY : nodeY;
			}

			// Relativierung zum erhalten des Seitenverhältnisses
			double factor;
			{
				final double factorX = (double) paneWidth / (double) maxX;
				final double factorY = (double) paneHeight / (double) maxY;
				factor = Math.min(factorX, factorY);
			}

			for (Node node : graph.getAllNodes()) {
				int nodeX = (int) ((double) node.getxCenter() * factor);
				int nodeY = (int) ((double) node.getyCenter() * factor);

				node.setxCenter(nodeX);
				node.setyCenter(nodeY);
			}

			// Setze Hintergrundbild
			{
				if (showBackgroundImage) {
					Image image = graph.getData().getBackground();
					ImageView view = new ImageView(image);

					view.setFitHeight(graph.getData().getRange().HEIGHT);
					view.setFitWidth(graph.getData().getRange().WIDTH);
					
					pane.getChildren().add(0, view);
				}
			}
		}

		pane.setCenter(Graph.getContent(graph, showEdges));

		// Scrollevent setzen
		pane.setOnScroll((ScrollEvent event) -> {

			double deltaFactor = event.getDeltaY() * 0.01;

			/*
			 * graph.factor = (graph.factor + deltaFactor < 0.1) ? 0.1 :
			 * (graph.factor + deltaFactor);
			 * System.out.println("      Adjust factor by " + deltaFactor +
			 * " to " + graph.factor); pane.setCenter(getContent(graph.factor,
			 * graph, showEdges));
			 */

			final double scale = pane.getScaleX();
			final double newScale = ((scale + deltaFactor) < 0.1) ? 0.1 : scale + deltaFactor;

			pane.setScaleX(newScale);
			pane.setScaleY(newScale);

		});

		// ActionListener für alle Tastendrücke
		pane.setOnKeyPressed((KeyEvent event) -> {
			// Funktionalität für Bewegung in Pane
			{
				DoubleProperty propX = pane.translateXProperty();
				DoubleProperty propY = pane.translateYProperty();

				if (event.isControlDown()) {
					propX.set(0);
					propY.set(0);
					pane.setScaleX(1.0);
					pane.setScaleY(1.0);
				} else {

					Double speed = 20.0;

					if (event.getCode() == KeyCode.LEFT) {
						propX.set(propX.get() + speed);
					} else if (event.getCode() == KeyCode.RIGHT) {
						propX.set(propX.get() - speed);
					} else if (event.getCode() == KeyCode.UP) {
						propY.set(propY.get() + speed);
					} else if (event.getCode() == KeyCode.DOWN) {
						propY.set(propY.get() - speed);
					}
				}
			}
		});

		// ActionListener für alle Mausdrücke
		pane.setOnMouseClicked((MouseEvent event) -> {
			// Updatet den oldX und oldY Wert, notwendig für dragging
			if (event.isPrimaryButtonDown()) {
				Double diffX = event.getScreenX() - graph.oldX;
				Double diffY = event.getScreenY() - graph.oldY;

				DoubleProperty propX = pane.translateXProperty();
				DoubleProperty propY = pane.translateYProperty();

				propX.set(propX.get() + diffX);
				propY.set(propY.get() + diffY);

				graph.oldX = event.getScreenX();
				graph.oldY = event.getScreenY();
			}
		});

		// ActionListener für Maus dragging
		pane.setOnMouseDragged((MouseEvent event) -> {
			// Maus-Dragging Funktionen
			{
				if (!(graph.oldX == 0.0 && graph.oldY == 0.0)) {
					DoubleProperty propX = pane.translateXProperty();
					DoubleProperty propY = pane.translateYProperty();

					Double diffX = event.getScreenX() - graph.oldX;
					Double diffY = event.getScreenY() - graph.oldY;

					// System.out.println(event.getScreenX() + " " +
					// event.getScreenX() + " || " + oldX + " " + oldY
					// + " || " + diffX + " " + diffY);

					double cutOffRange = 100;
					if (cutOffRange >= Math.sqrt(Math.pow(diffY, 2.0) + Math.pow(diffX, 2.0))) {
						propX.set(propX.get() + diffX);
						propY.set(propY.get() + diffY);

					}
				}

				graph.oldX = event.getScreenX();
				graph.oldY = event.getScreenY();
			}
		});

		return pane;
	}

	/**
	 * Gibt eine Gruppe aller Objekte des Graphen zurück die gezeichnet werden
	 * sollen
	 * 
	 * @return Group
	 */
	private static Group getContent(Graph graph, boolean showEdges) {
		Group g = new Group();
		for (Edge edge : graph.getAllEdges()) {
			g.getChildren().addAll(Edge.getDrawableObject(edge));
		}
		for (Node node : graph.getAllNodes()) {
			g.getChildren().add(Node.getDrawableObject(node));
		}

		return g;
	}

	// ---------------------------------------------------------------
	// Getter und Setter
	// ---------------------------------------------------------------
	public List<Node> getAllNodes() {
		return new ArrayList<Node>(this.allNodes);
	}

	public List<Edge> getAllEdges() {
		return new ArrayList<Edge>(this.allEdges);
	}

	public Map<NodeData, Node> getMapping() {
		return new HashMap<NodeData, Node>(mapping);
	}

	public GraphData getData() {
		return data;
	}

	public void addNode(Node node) {
		this.allNodes.add(node);
	}
}
