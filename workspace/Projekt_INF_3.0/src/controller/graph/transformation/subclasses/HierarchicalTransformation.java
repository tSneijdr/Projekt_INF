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

public class HierarchicalTransformation extends Transformation {

	@Override
	public void applyOn(Graph g) {

		List<Node> orderedNodes = new LinkedList<Node>();

		Map<Integer, List<Node>> numOfKids = new HashMap<Integer, List<Node>>();
		Map<Integer, List<Node>> numOfParents = new HashMap<Integer, List<Node>>();

		Map<NodeData, Node> dataToNode = new HashMap<NodeData, Node>();

		List<Edge> edges = g.getAllEdges();
		List<Node> nodes = g.getAllNodes();

		// Setup
		{
			for (Node node : g.getAllNodes()) {
				dataToNode.put(node.getData(), node);
			}

			for (Node node : g.getAllNodes()) {
				final int num = node.getChildren().size();
System.out.println("num: " + num);
				
				if (!numOfKids.containsKey(num)) {
					numOfKids.put(num, new LinkedList<Node>());
				}
				numOfKids.get(num).add(node);
			}

			for (Node node : g.getAllNodes()) {
				final int num = node.getParents().size();

				if (!numOfParents.containsKey(num)) {
					numOfParents.put(num, new LinkedList<Node>());
				}
				numOfParents.get(num).add(node);
			}
		}

		final int radius = nodes.get(0).getRadius();
		{
			int index = 0;
			while (!nodes.isEmpty()) {
				if (numOfKids.containsKey(index)) {

					List<Node> level = numOfKids.get(index);
					// Place Kids
					for (int i = 0; i < level.size(); i++) {
						level.get(i).setxCenter(radius * 2 * index);
						level.get(i).setyCenter((int) (i * radius * 1.1));
					}
					nodes.removeAll(level);
				}

				index++;
			}
		}

	}
}
