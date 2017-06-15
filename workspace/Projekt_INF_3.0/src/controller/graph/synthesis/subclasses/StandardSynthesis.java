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

/**
 * Standardsynthese, überträgt eine Menge von Punkten durch Rasterisierung in
 * einen Graphen
 * 
 * @author tobias meisel
 */
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

		{ // Erstellt Knoten
			int sizeColumn = Math.floorDiv(range.WIDTH, numberOfColumns);
			int sizeRow = Math.floorDiv(range.HEIGHT, numberOfRows);
			for (int x = 0; x < numberOfColumns; x++) {
				for (int y = 0; y < numberOfRows; y++) {
					final Set<String> informations;
					final Range2D r;
					final Set<Point> localPoints;

					// Suchfläche
					r = new Range2D(x * sizeColumn, (x + 1) * sizeColumn, y * sizeRow, (y + 1) * sizeRow);
					localPoints = tree.getPoints(r);

					// Skippe wenn keine Knoten gefunden wurden
					if (localPoints == null || localPoints.isEmpty()) {
						continue;
					} else {
						System.out.println("      " + localPoints.size() + " point(s) => 1 abstract node");
					}

					{ // Zusätzliche Infos können hier eingefügt werden
						informations = new HashSet<String>();
						informations.add("Number of datapoints: " + localPoints.size());

						// Berechne die durchschnittlichen Werte der Datenpunkte
						// in diesem Knoten
						double avgDur = 0.0, avgX = 0.0, avgY = 0.0, avgTimepoint = 0.0;
						for (Point p : localPoints) {
							avgDur += p.getDURATION();
							avgTimepoint += p.getTIMEPOINT();
							avgX += p.getX();
							avgY += p.getY();
						}
						final int size = localPoints.size();
						avgDur = avgDur / (double) size;
						avgTimepoint = avgTimepoint / (double) size;
						avgX = avgX / (double) size;
						avgY = avgY / (double) size;

						informations.add("Average duration of datapoints: " + avgDur);
						informations.add("Average timepoint of datapoints: " + avgTimepoint);
						informations.add("Average x coordinates of datapoints: " + avgX);
						informations.add("Average y coordinates of datapoints: " + avgY);
					}
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

		// Log
		System.out.println(
				"   From " + points.size() + " datapoint(s) " + allNodeData.size() + " abstract nodes were extracted.");
		System.out.println("   --> Synthesis finished succesfully.");

		return new GraphData(allNodeData, img, numberOfColumns, numberOfRows);
	}
}
