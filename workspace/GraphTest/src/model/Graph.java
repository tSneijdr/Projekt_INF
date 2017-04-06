package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Graph {
	private final List<Node> allNodes;

	// Faktoren für korrekte Darstellung
	private double factor = 1.0;
	private double displacementX = 0.0;
	private double displacementY = 0.0;

	private double oldX = 0.0;
	private double oldY = 0.0;

	public Graph() {
		allNodes = new LinkedList<Node>();
	}

	public void random(int n) {
		Random r = new Random();
		for (int index = 0; index < n; index++) {
			Node node = new Node();
			node.setShape(NodeType.CIRCLE);
			node.setxCenter(r.nextInt(500));
			node.setyCenter(r.nextInt(500));
			node.setRadius(10);

			allNodes.add(node);

			int i = allNodes.size();
			i = i <= 0 ? 1 : i;
			i = r.nextInt(i);
			i = i <= 0 ? 1 : i;

			try {
				node.addParent(allNodes.get(i));
				node.addChild(allNodes.get((i + 1) % allNodes.size()));

			} catch (Exception e) {
			}
		}

	}

	public BorderPane getPane(int paneWidth, int paneHeight, double scaleFactor) {
		this.factor = scaleFactor;

		BorderPane pane = new BorderPane();
		pane.setMinSize(paneWidth, paneHeight);
		pane.setPrefSize(paneWidth, paneHeight);

		pane.setFocusTraversable(true);

		pane.setCenter(getContent());

		// Scrollevent setzen
		{
			pane.setOnScroll(null);
			// pane.Event

			pane.setOnScroll((ScrollEvent event) -> {
				
				double deltaFactor = event.getDeltaY() * 0.01;

				factor = (factor + deltaFactor < 1) ? 1 : (factor + deltaFactor);
				System.out.println("Adjust factor by " + deltaFactor + " to " + factor);
				pane.setCenter(getContent());
				
				event.consume();
			});

			pane.setOnKeyPressed((KeyEvent event) -> {

				DoubleProperty propX = pane.translateXProperty();
				DoubleProperty propY = pane.translateYProperty();

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

				event.consume();
			});

			pane.setOnMouseClicked((MouseEvent event) -> {
				Double diffX = event.getScreenX() - oldX;
				Double diffY = event.getScreenY() - oldY;

				DoubleProperty propX = pane.translateXProperty();
				DoubleProperty propY = pane.translateYProperty();

				propX.set(propX.get() + diffX);
				propY.set(propY.get() + diffY);

				System.out.println("Kliiiiiiiiiiiickkk");
				oldX = event.getScreenX();
				oldY = event.getScreenY();
			});

			pane.setOnMouseDragged((MouseEvent event) -> {

				if (!(oldX == 0.0 && oldY == 0.0)) {
					DoubleProperty propX = pane.translateXProperty();
					DoubleProperty propY = pane.translateYProperty();

					Double diffX = event.getScreenX() - oldX;
					Double diffY = event.getScreenY() - oldY;

					// System.out.println(oldX + " " + diffX + " " +
					// event.getScreenX());
					// System.out.println(oldY + " " + diffY + " " +
					// event.getScreenY());
					// System.out.println();

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
			});

		}
		System.out.println("Anfangsdaten: " + pane.translateXProperty().get() + " " + pane.translateYProperty().get());
		return pane;
	}

	public Group getContent() {
		Group g = new Group();

		int counter = 0;
		LinkedList<Node> alreadyVisited = new LinkedList<Node>();

		for (Node n : this.allNodes) {
			if (!alreadyVisited.contains(n)) {

				for (Node n2 : n.getChildren()) {
					Line l = new Line();
					l.setStartX(n.getxCenter() * factor + displacementX);
					l.setStartY(n.getyCenter() * factor + displacementY);

					l.setEndX(n2.getxCenter() * factor + displacementX);
					l.setEndY(n2.getyCenter() * factor + displacementY);

					l.setStroke(Color.DARKGREEN);

					g.getChildren().add(l);
					counter++;
				}

				alreadyVisited.add(n);
			}

		}
		System.out.println(counter);

		for (Node n : allNodes) {
			g.getChildren().add(n.getDrawableObject(factor, displacementX, displacementY));
		}
		return g;
	}
}
