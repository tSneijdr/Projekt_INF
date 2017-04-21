package model.graph.graph;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import model.graph.data.GraphData;

public class Graph {

	private final GraphData data;
	
	
	private double factor = 1.0;

	// Faktoren für korrekte Darstellung
	private double oldX = 0.0;
	private double oldY = 0.0;

	
	private final List<Node> allNodes;
	private final List<Edge> allEdges;
	
	/**
	 * Standardkonsturktor
	 */
	public Graph(GraphData data, List<Node> allNodes, List<Edge> allEdges) {
		this.data = data;
		
		this.allNodes = allNodes;
		this.allEdges = allEdges;
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
	public BorderPane getPane(int paneWidth, int paneHeight, boolean showEdges) {
		this.factor = 1.0;

		// Setup für das Pane
		BorderPane pane = new BorderPane();
		pane.setMinSize(paneWidth, paneHeight);
		pane.setPrefSize(paneWidth, paneHeight);

		pane.setFocusTraversable(true);

		pane.setCenter(getContent(factor, showEdges));

		// Scrollevent setzen
		pane.setOnScroll((ScrollEvent event) -> {

			double deltaFactor = event.getDeltaY() * 0.01;

			factor = (factor + deltaFactor < 1) ? 1 : (factor + deltaFactor);
			System.out.println("Adjust factor by " + deltaFactor + " to " + factor);
			pane.setCenter(getContent(factor, showEdges));
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
					this.factor = 1.0;
					pane.setCenter(getContent(factor, showEdges));
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
				Double diffX = event.getScreenX() - oldX;
				Double diffY = event.getScreenY() - oldY;

				DoubleProperty propX = pane.translateXProperty();
				DoubleProperty propY = pane.translateYProperty();

				propX.set(propX.get() + diffX);
				propY.set(propY.get() + diffY);

				oldX = event.getScreenX();
				oldY = event.getScreenY();
			}
		});

		// ActionListener für Maus dragging
		pane.setOnMouseDragged((MouseEvent event) -> {
			// Maus-Dragging Funktionen
			{
				if (!(oldX == 0.0 && oldY == 0.0)) {
					DoubleProperty propX = pane.translateXProperty();
					DoubleProperty propY = pane.translateYProperty();

					Double diffX = event.getScreenX() - oldX;
					Double diffY = event.getScreenY() - oldY;

					System.out.println(event.getScreenX() + " " + event.getScreenX() + " || " + oldX + " " + oldY
							+ " || " + diffX + " " + diffY);

					double cutOffRange = 100;
					if (cutOffRange >= Math.sqrt(Math.pow(diffY, 2.0) + Math.pow(diffX, 2.0))) {
						propX.set(propX.get() + diffX);
						propY.set(propY.get() + diffY);

					}
				}

				oldX = event.getScreenX();
				oldY = event.getScreenY();
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
	private Group getContent(double factor, boolean showEdges) {
		Group g = new Group();
		for (Edge edge : allEdges) {
			g.getChildren().add(edge.getDrawableObject(factor));
		}
		for (Node node : allNodes) {
			g.getChildren().add(node.getDrawableObject(factor));
		}

		return g;
	}
}
