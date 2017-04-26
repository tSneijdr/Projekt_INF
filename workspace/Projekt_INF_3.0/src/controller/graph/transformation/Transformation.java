package controller.graph.transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import controller.graph.transformation.subclasses.RandomTransformation;
import model.graph.data.GraphData;
import model.graph.data.NodeData;
import model.graph.graph.Edge;
import model.graph.graph.Graph;
import model.graph.graph.Node;

public abstract class Transformation {
	/**
	 * Erhält ein Set von Punkten und Generiert daraus einen Graphen
	 * 
	 * @param points
	 * @return
	 */
	public abstract void applyOn(Graph g);

	/**
	 * Erzeugt aus den übergebenen GraphenDaten einen noch untransformierten
	 * Graphen
	 * 
	 * @return
	 */
	public static Graph getUntransformedGraph(GraphData graphData) {
		List<NodeData> allData = graphData.getAllNodeData();

		List<Node> allNodes = new ArrayList<Node>();
		List<Edge> allEdges = new ArrayList<Edge>();

		Map<NodeData, Node> map = new HashMap<NodeData, Node>();

		Random rand = new Random();

		// Erstellt die Knoten
		for (NodeData data : allData) {
			Node node = new Node(data);

			// Setzt die Darstellung des Knotens
			node.setColor();
			node.setRadius(10);
			node.setxCenter(data.getOriginalColumn());
			node.setyCenter(data.getOriginalRow());

			map.put(data, node);
			allNodes.add(node);
		}

		// Erstellt die Kanten
		HashSet<NodeData> visited = new HashSet<NodeData>();
		for (Node node : allNodes) {
			if (!visited.contains(node.getData())) {
				for (NodeData data : node.getData().getChildren()) {
					// Test ob Kante bereits vorhanden ist
					if (visited.contains(data)) {
						continue;
					}

					Node parent = node;
					Node child = map.get(data);

					try {
						Edge edge = new Edge(parent, child);

						allEdges.add(edge);
					} catch (Exception e) {
					}
				}
				for (NodeData data : node.getData().getChildren()) {
					// Test ob Kante bereits vorhanden ist
					if (visited.contains(data)) {
						continue;
					}

					Node parent = map.get(data);
					Node child = node;

					try {
						Edge edge = new Edge(parent, child);

						allEdges.add(edge);
					} catch (Exception e) {
					}
				}

				visited.add(node.getData());
			}
		}

		return new Graph(graphData, allNodes, allEdges, map);
	}
}
