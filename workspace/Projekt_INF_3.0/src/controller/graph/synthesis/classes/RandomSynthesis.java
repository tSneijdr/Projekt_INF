package controller.graph.synthesis.classes;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import controller.graph.synthesis.Synthesis;
import model.graph.Graph;
import model.graph.Node;
import model.graph.NodeType;
import model.points.Point;

public class RandomSynthesis extends Synthesis {

	public RandomSynthesis() {
		super("Zufälliger Graph");
		// TODO Auto-generated constructor stub
	}

	/**
	 * Erzeugt einen zufälligen Graphen mit n Knoten
	 * 
	 * @param n
	 */
	public Graph applyOn(Set<Point> points) {
		Random r = new Random();
		int n = r.nextInt(100);

		Graph graph = new Graph();

		for (int index = 0; index < n; index++) {
			String inf = "Information: zufällige Information";
			String inf2 = "Test";
			HashSet<String> s = new HashSet<String>();
			s.add(inf);
			s.add(inf2);
			Node node = new Node(s);
			node.setShape(NodeType.DIAMOND);
			node.setxCenter(r.nextInt(300));
			node.setyCenter(r.nextInt(300));
			node.setRadius(10);

			graph.getAllNodes().add(node);

			List<Node> allNodes = graph.getAllNodes();
			for (int index2 = 0; index2 < r.nextInt(allNodes.size()); index2++) {

				try {
					{
						int i = allNodes.size();
						i = i <= 0 ? 1 : i;
						i = r.nextInt(i);
						i = i <= 0 ? 1 : i;

						node.addParent(allNodes.get(i));
						allNodes.get(i).addChild(node);
					}
					{
						int i = allNodes.size();
						i = i <= 0 ? 1 : i;
						i = r.nextInt(i);
						i = i <= 0 ? 1 : i;

						node.addParent(allNodes.get(i));
						allNodes.get(i).addChild(node);
					}
				} catch (Exception e) {
				}
			}
		}
		return graph;

	}

}
