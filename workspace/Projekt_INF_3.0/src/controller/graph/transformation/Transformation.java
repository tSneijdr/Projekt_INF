package controller.graph.transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import controller.graph.transformation.classes.RandomTransformation;
import model.graph.data.GraphData;
import model.graph.data.NodeData;
import model.graph.graph.Edge;
import model.graph.graph.Graph;
import model.graph.graph.Node;

public abstract class Transformation {
	private final String name;

	public Transformation(String name) {
		this.name = name;
	}

	/**
	 * Erhält ein Set von Punkten und Generiert daraus einen Graphen
	 * 
	 * @param points
	 * @return
	 */
	public abstract Graph applyOn(Graph g);

	public String getName() {
		return name;
	}
	
	public static Graph get(TransformationType type, Graph graph) {
		Transformation trans;
		switch (type) {
		case IDENTITY:
			trans = new RandomTransformation();
			break;

		default:
			trans = new RandomTransformation();
			break;
		}
		return trans.applyOn(graph);
	}
	
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
			node.setxCenter(rand.nextInt(500));
			node.setyCenter(rand.nextInt(500));

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
					Edge edge = new Edge(parent, child);

					allEdges.add(edge);
				}
				for (NodeData data : node.getData().getChildren()) {
					// Test ob Kante bereits vorhanden ist
					if (visited.contains(data)) {
						continue;
					}

					Node parent = map.get(data);
					Node child = node;
					Edge edge = new Edge(parent, child);

					allEdges.add(edge);
				}

				visited.add(node.getData());
			}
		}

		return new Graph(graphData, allNodes, allEdges);
	}
}
