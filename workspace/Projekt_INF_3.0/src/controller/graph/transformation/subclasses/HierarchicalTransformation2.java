package controller.graph.transformation.subclasses;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import controller.graph.transformation.Transformation;
import model.graph.data.NodeData;
import model.graph.graph.Edge;
import model.graph.graph.Graph;
import model.graph.graph.Node;

public class HierarchicalTransformation2 extends Transformation {

	@Override
	public void applyOn(Graph g) {

		Map<Integer, List<Node>> numOfKids;
		Map<Integer, List<Node>> numOfParents;

		Map<NodeData, Node> dataToNode = new HashMap<NodeData, Node>();

		List<Edge> edges = g.getAllEdges();
		List<Node> nodes = g.getAllNodes();

		// Setup
		{
			for (Node node : nodes) {
				dataToNode.put(node.getData(), node);
			}

			numOfParents = orderByIncomingEdges(nodes);
			numOfKids = orderByOutgoingEdges(nodes);
		}

		final int radius = nodes.get(0).getRadius();
		{
			int index = 0;
			while (!nodes.isEmpty()) {
				if (numOfParents.containsKey(index)) {
					List<Node> level = numOfParents.get(index);
					for (int i = 0; i < level.size(); i++) {
						level.get(i).setxCenter(radius * 2 * index);
						level.get(i).setyCenter((int) (i * radius * 1.1));
					}
					nodes.removeAll(level);
				}

				index++;
				System.out.println("Hier " + index + " " + nodes.size());
			}
		}

	}

	private Map<Integer, List<Node>> orderByIncomingEdges(List<Node> nodes) {
		Map<Integer, List<Node>> numOfParents = new HashMap<Integer, List<Node>>();

		// Mappe Anzahl der Eingehenden Kanten auf Knoten
		for (Node node : nodes) {
			final Set<Edge> in = node.getIncomingEdges();
			final Set<Node> neighbours = new HashSet<Node>();
			for (Edge e : in) {
				if (nodes.contains(e.getChild())) {
					neighbours.add(e.getChild());
				}
			}
			final int num = neighbours.size();
			System.out.println("num in: " + num);

			if (!numOfParents.containsKey(num)) {
				numOfParents.put(num, new LinkedList<Node>());
			}
			numOfParents.get(num).add(node);
		}

		return numOfParents;
	}

	private Map<Integer, List<Node>> orderByOutgoingEdges(List<Node> nodes) {
		Map<Integer, List<Node>> numOfKids = new HashMap<Integer, List<Node>>();

		// Mappe Anzahl der Ausgehenden Kanten auf Knoten
		for (Node node : nodes) {
			final Set<Edge> out = node.getOutgoingEdges();
			final Set<Node> neighbours = new HashSet<Node>();
			for (Edge e : out) {
				if (nodes.contains(e.getChild())) {
					neighbours.add(e.getChild());
				}
			}
			final int num = neighbours.size();
			System.out.println("num out: " + num);

			if (!numOfKids.containsKey(num)) {
				numOfKids.put(num, new LinkedList<Node>());
			}
			numOfKids.get(num).add(node);
		}

		return numOfKids;
	}
}
