package controller.graph.transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		final List<NodeData> allData = graphData.getAllNodeData();

		final List<Node> allNodes = new ArrayList<Node>();
		final List<Edge> allEdges = new ArrayList<Edge>();

		final Map<NodeData, Node> map = new HashMap<NodeData, Node>();

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
		for (Node node1 : allNodes) {
			for (NodeData data : node1.getData().getChildren()) {
				Node node2 = map.get(data);

				try {
					Edge edge = new Edge(node1, node2);
					final double thickness =(double) node1.getData().getNumberOfChildEdges(node2.getData());
					System.out.println("Dicke: " + thickness);
					edge.setThickness(thickness);

					allEdges.add(edge);
				} catch (Exception e) {
				}
			}

		}

		return new Graph(graphData, allNodes, allEdges, map);
	}
}
