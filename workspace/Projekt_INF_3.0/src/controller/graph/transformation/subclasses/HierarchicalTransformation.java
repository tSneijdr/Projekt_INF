package controller.graph.transformation.subclasses;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import controller.graph.transformation.Transformation;
import model.graph.graph.Edge;
import model.graph.graph.Graph;
import model.graph.graph.Node;

/**
 * Stellt einen Algorithmus dar, der versucht den Graphen auf ein gutes
 * Hierarchisches Layout zu mappen
 * 
 * @author tobias schneider, tobias meisel
 */
public class HierarchicalTransformation extends Transformation {

	@Override
	public void applyOn(Graph g) {
		if (g == null || g.getAllNodes().isEmpty())
			return;

		List<Node> nodes = g.getAllNodes();
		List<Node> visited = new LinkedList<Node>();
		List<List<Node>> target = new LinkedList<List<Node>>();

		// Ermittle den Baum
		while (!nodes.isEmpty()) {
			for (Node node : startNodes(nodes)) {
				depthSearch(node, visited, 0, target);
			}
			nodes.removeAll(visited);
		}

		// Platizere die Knoten
		final int radius = visited.get(0).getRadius();
		for (int i = 0; i < target.size(); i++) {
			List<Node> level = target.get(i);
			final double displacement = level.size() * 1.1 * radius / 2.0;

			for (int j = 0; j < level.size(); j++) {
				System.out.println("i: " + i + " j: " + j);
				level.get(j).setxCenter(radius * 2 * i);
				level.get(j).setyCenter((int) ((j * radius * 1.1) - displacement));
			}

		}

	}

	private void depthSearch(Node currentNode, List<Node> visited, int depth, List<List<Node>> target) {
		visited.add(currentNode);

		// Füge Knoten an richtiger Stelle zu
		try {
			List<Node> list = target.get(depth);
			if (list == null) {
				target.add(depth, new LinkedList<Node>());
			}
		} catch (IndexOutOfBoundsException e) {
			target.add(depth, new LinkedList<Node>());
		}
		target.get(depth).add(currentNode);

		Set<Node> children;
		{ // Ermittle alle Kinder
			children = new HashSet<Node>();
			for (Edge e : currentNode.getOutgoingEdges()) {
				children.add(e.getChild());
			}
		}

		for (Node node : children) {
			if (!visited.contains(node)) {
				depthSearch(node, visited, (depth + 1), target);
			}
		}
	}

	/**
	 * Ermittle einen oder mehrere Startknoten
	 * 
	 * @param nodes
	 * @return
	 */
	private List<Node> startNodes(List<Node> nodes) {
		if (nodes.isEmpty())
			return null;

		// Ermittle Startknoten
		List<Node> start = new LinkedList<Node>();

		for (Node node : nodes) {
			if (node.getIncomingEdges().size() == 0) {
				start.add(node);
			}
		}

		int index = 1;
		outerLoop: while (start.isEmpty()) {
			for (Node node : nodes) {
				if (node.getIncomingEdges().size() == index) {
					start.add(node);
					break outerLoop;
				}
			}
			index++;
		}

		return start;
	}
}
