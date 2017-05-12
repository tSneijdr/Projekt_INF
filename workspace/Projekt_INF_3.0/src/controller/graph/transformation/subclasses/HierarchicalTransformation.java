package controller.graph.transformation.subclasses;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import controller.graph.transformation.Transformation;
import model.graph.graph.Edge;
import model.graph.graph.Graph;
import model.graph.graph.Node;

public class HierarchicalTransformation extends Transformation {

	@Override
	public void applyOn(Graph g) {
		List<Node> allNodes = g.getAllNodes();
		List<Edge> allEdges = g.getAllEdges();
		Map<Node, Set<Edge>> outgoing = new HashMap<Node, Set<Edge>>();
		Map<Node, Set<Edge>> incoming = new HashMap<Node, Set<Edge>>();
		List<List<Node>> rankSortedNodes = new LinkedList<List<Node>>();

		// Init Map
		{
			for (Edge edge : allEdges) {
				// Init Elter-Mapping
				if (!outgoing.containsKey(edge.getParent())) {
					outgoing.put(edge.getParent(), new HashSet<Edge>());
				}
				outgoing.get(edge.getParent()).add(edge);

				// Init Kind-Mapping
				if (!incoming.containsKey(edge.getChild())) {
					incoming.put(edge.getChild(), new HashSet<Edge>());
				}
				incoming.get(edge.getChild()).add(edge);
			}
		}

		// Sortiere nach Rang
		while (!allNodes.isEmpty()) {

			List<Node> rank = new LinkedList<Node>();
			{

				// Entferne alle Knoten die Keine Eltern haben
				{
					rank.addAll(allNodes);
					rank.removeAll(incoming.keySet());
					System.out.println("   Neue Ebene mit " + rank.size() + " ermittelt");
				}

				// Es gibt noch Knoten aber der rang ist leer => Zyklus
				if (rank.isEmpty() && !allNodes.isEmpty()) {
					// Ermittle den Knoten mit den wenigsten einkommenden
					// Knoten
					// TODO ersetzen mit echter Zyklenkontrolle
					rank.add(allNodes.get(0));
					int minIn = incoming.get(rank.get(0)).size();

					for (Node node : incoming.keySet()) {
						final int in = incoming.get(node).size();
						if (in < minIn) {
							rank.clear();
							rank.add(node);
						} else if (in == minIn) {
							rank.add(node);
						}

					}

					for (Node node : rank) {
						incoming.remove(node);
					}
				}

				// Lösche invalide Kanten
				{
					// Sammle alle jetzt invaliden Kanten
					Set<Edge> invalidEdges = new HashSet<Edge>();
					for (Node parent : rank) {
						if (outgoing.containsKey(parent)) {
							invalidEdges.addAll(outgoing.get(parent));
						}
						outgoing.remove(parent);
					}

					// Lösche Invalide Kanten aus incoming
					List<Node> toBeRemoved = new LinkedList<Node>();
					for (Node node : incoming.keySet()) {
						Set<Edge> list = incoming.get(node);
						for (Edge e : invalidEdges) {
							list.remove(e);
							if (list.isEmpty()) {
								toBeRemoved.add(node);
							}
						}
					}
					for (Node node : toBeRemoved){
						incoming.remove(node);
					}
				}
			}

			allNodes.removeAll(rank);
			rankSortedNodes.add(rank);
		}

		// Eigentliches verändern des Graphens
		{
			// Ermitteln der maximalen Breite
			int maxBroath = 0;
			final int dist = 30;

			for (List<Node> list : rankSortedNodes) {
				maxBroath = (list.size() > maxBroath) ? list.size() : maxBroath;
			}
			maxBroath = (maxBroath - 1) * dist + 2 * dist;

			// Setzen der Werte
			for (int i = 0; i < rankSortedNodes.size(); i++) {
				List<Node> list = rankSortedNodes.get(i);
				// Ermittle Abstand
				int add = (maxBroath - (list.size() - 1) * dist) / 2;

				// Setze passende Werte
				for (int j = 0; j < list.size(); j++) {
					list.get(j).setxCenter((i + 1) * dist);
					list.get(j).setyCenter(j * dist + add);
				}
				System.out.println("   " + i + " => " + list.size());
			}
		}

	}

}
