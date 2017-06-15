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
 * Benutzt Hierarchical Layout um alle Knoten in einem Kreis anzuordnen und die
 * Anzahl von Kanten die stark im inneren des Kreises liegen zu vermeiden. In
 * einigen Fällen kann dies doch jedoch dazu führen dass viele Kanten in der
 * Nähe des Kreises liegen
 *
 * @author tobias schneider, tobias meisel
 */
public class CircularLayout extends Transformation {

	@Override
	public void applyOn(Graph g) {
		final int num = g.getAllNodes().size();
		final List<Node> sortedNodes;

		{ // Anordnen der Knoten
			List<Node> nodes = g.getAllNodes();
			List<Node> visited = new LinkedList<Node>();
			List<List<Node>> target = new LinkedList<List<Node>>();

			// Ermittle die (hierarchische) Anordnung
			while (!nodes.isEmpty()) {
				for (Node node : startNodes(nodes)) {
					depthSearch(node, visited, 0, target);
				}
				nodes.removeAll(visited);
			}

			// Flatmappen der Anordnung
			List<Node> res = new LinkedList<Node>();
			for (int i = 0; i < target.size(); i++) {
				List<Node> list = target.get(i);

				for (int j = 0; j < list.size(); j++) {
					res.add(list.get(j));
				}
			}
			sortedNodes = res;
		}

		{ // Platzieren der Knoten
			final int maxRadius;
			{ // ermittle den maximalen Radius
				int radius = 0;

				for (Node node : g.getAllNodes()) {
					radius = (node.getRadius() > radius) ? node.getRadius() : radius;
				}
				maxRadius = radius;
			}

			final double umfang = maxRadius * 2 * num;
			final double radius = (umfang / 2 / Math.PI) * 1.1;

			final double sliceSize = 2 * Math.PI / num;

			for (int i = 0; i < g.getAllNodes().size(); i++) {
				Node node = sortedNodes.get(i);

				final double x = Math.cos(sliceSize * i - Math.PI / 2.0) * radius;
				final double y = Math.sin(sliceSize * i - Math.PI / 2.0) * radius;

				node.setxCenter((int) x);
				node.setyCenter((int) y);
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
