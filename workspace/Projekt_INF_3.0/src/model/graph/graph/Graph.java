package model.graph.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import model.graph.data.GraphData;
import model.graph.data.NodeData;

public class Graph {

	// Speichern von Knoten und Kanten
	private final Set<Node> allNodes;
	private final Set<Edge> allEdges;

	// Mapping vin Daten auf Knoten
	private final Map<NodeData, Node> mapping;

	// Originale Daten
	private final GraphData data;

	/**
	 * Standardkonsturktor
	 */
	public Graph(GraphData data, Set<Node> allNodes, Set<Edge> allEdges, Map<NodeData, Node> mapping) {
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
	public static Pane getPane(int paneWidth, int paneHeight, Graph graph, boolean showBgImage) {

		// Setup für das Pane
		Pane pane = new AnchorPane();
		pane.setMinSize(paneWidth, paneHeight);
		pane.setPrefSize(paneWidth, paneHeight);
		pane.setMaxSize(paneWidth, paneHeight);

		pane.setFocusTraversable(true);

		// Skaliere den Graphen
		{
			if (!showBgImage) {
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
			}

			// Setze Hintergrundbild
			{
				if (showBgImage) {
					final double height = (double) graph.getData().getRange().HEIGHT;
					final double width = (double) graph.getData().getRange().WIDTH;

					final double columnSize = width / graph.getData().getNumColumns();
					final double rowSize = height / graph.getData().getNumRows();

					for (int i = 1; i < graph.getData().getNumColumns(); i++) {
						Line l = new Line();
						l.setStroke(Color.GREY);
						l.setStartY(0);
						l.setEndY(graph.getData().getRange().HEIGHT);

						l.setStartX(i * columnSize);
						l.setEndX(i * columnSize);
						pane.getChildren().add(0, l);
					}

					for (int i = 1; i < graph.getData().getNumRows(); i++) {
						Line l = new Line();
						l.setStroke(Color.GREY);
						l.setStartX(0);
						l.setEndX(graph.getData().getRange().WIDTH);

						l.setStartY(i * rowSize);
						l.setEndY(i * rowSize);
						pane.getChildren().add(0, l);
					}

					Image image = graph.getData().getBackground();
					ImageView view = new ImageView(image);

					// view.setFitHeight(graph.getData().getRange().HEIGHT);
					// view.setFitWidth(graph.getData().getRange().WIDTH);

					view.setX(0);
					view.setY(0);

					pane.getChildren().add(0, view);
				}
			}
		}

		pane.getChildren().addAll(Graph.getContent(graph));		
		return pane;
	}

	/**
	 * Gibt eine Gruppe aller Objekte des Graphen zurück die gezeichnet werden
	 * sollen
	 * 
	 * @return Group
	 */
	private static List<Shape> getContent(Graph graph) {
		List<Shape> g = new LinkedList<Shape>();
		for (Edge edge : graph.getAllEdges()) {
			g.addAll(Edge.getDrawableObject(edge));
		}
		for (Node node : graph.getAllNodes()) {
			g.add(Node.getDrawableObject(node));
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
