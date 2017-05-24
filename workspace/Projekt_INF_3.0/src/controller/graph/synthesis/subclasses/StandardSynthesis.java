package controller.graph.synthesis.subclasses;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import controller.graph.synthesis.Synthesis;
import javafx.scene.image.Image;
import model.graph.data.GraphData;
import model.graph.data.NodeData;
import model.points.Point;
import utils.datastructures.Quadtree;
import utils.ranges.Range2D;

public class StandardSynthesis extends Synthesis {

	@Override
	public GraphData applyOn(Set<Point> points, final Image img, final int numberOfRows, final int numberOfColumns) {
		Set<NodeData> allNodeData = new HashSet<NodeData>();
		Map<Point, NodeData> map = new HashMap<Point, NodeData>();

		Range2D range = new Range2D(0, (int) img.getWidth(), 0, (int) img.getHeight());

		// Datenstruktur zur Auswahl der Punkte
		Quadtree tree = new Quadtree(range, 5);
		for (Point point : points) {
			tree.add(point);
		}

		{
			// Erstellt Knoten
			int sizeColumn = Math.floorDiv(range.WIDTH, numberOfColumns);
			int sizeRow = Math.floorDiv(range.HEIGHT, numberOfRows);
			for (int x = 0; x < numberOfColumns; x++) {
				for (int y = 0; y < numberOfRows; y++) {
					// Suchfläche
					final Range2D r = new Range2D(x * sizeColumn, (x + 1) * sizeColumn, y * sizeRow, (y + 1) * sizeRow);
					final Set<Point> localPoints = tree.getPoints(r);

					// Skippe wenn keine Knoten gefunden wurden
					if (localPoints == null || localPoints.isEmpty()) {
						continue;
					} else {
						System.out.println("      " + localPoints.size() + " Punkt(e) => 1 Rohknoten");
					}

					// Zusätzliche relevante Daten können hier eingefügt werden
					Set<String> informations = new HashSet<String>();
					informations.add("Anzahl der Knoten: " + localPoints.size());

					// Knoten
					NodeData data = new NodeData(x, y, informations);
					allNodeData.add(data);

					// Updatet die map
					for (Point p : localPoints) {
						map.put(p, data);
					}

				}
			}
		}

		points = tree.getPoints(tree.RANGE);
		{
			// Erstellt Kanten
			for (Point currentPoint : points) {
				Point nextPoint = currentPoint.getNextNode();

				// Fügt Kante nur hinzu, wenn der nächste Punkt überhaupt im Set
				// liegt
				if (points.contains(nextPoint) && nextPoint != null) {
					map.get(currentPoint).addChild(map.get(nextPoint));
				}
			}
		}
		System.out.println("   Aus " + points.size() + " Punkt(en) wurde(n) " + allNodeData.size() + " Rohknoten.");
		System.out.println("   --> Synthese erfolgreich abgeschlossen.");
		return new GraphData(allNodeData, img, numberOfColumns, numberOfRows);
	}
}
