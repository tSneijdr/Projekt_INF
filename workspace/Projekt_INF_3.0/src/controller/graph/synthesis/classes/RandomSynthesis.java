package controller.graph.synthesis.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import controller.graph.synthesis.Synthesis;
import model.graph.data.GraphData;
import model.graph.data.NodeData;
import model.points.Point;

public class RandomSynthesis extends Synthesis {

	/**
	 * Erzeugt einen zufälligen Graphen mit n Knoten
	 * 
	 * @param n
	 */
	public GraphData applyOn(Set<Point> points) {
		List<NodeData> allNodeData = new ArrayList<NodeData>();

		Random r = new Random();
		int n = r.nextInt(100);

		// Erzeugt Knoten
		for (int index = 0; index < n; index++) {
			NodeData nodeData;
			{

				// Füge Informationen hinzu
				String inf = "Information: zufällige Information";
				String inf2 = "Test";
				HashSet<String> s = new HashSet<String>();
				s.add(inf);
				s.add(inf2);

				nodeData = new NodeData(0, 0, s);

				// Setzt den Knoten mit einer bistimmten Wahrscheinlichkeit auf
				// aktiv
				if (r.nextDouble() <= 0.2) {
					nodeData.setActive(true);
				}
			}
			allNodeData.add(nodeData);
		}

		// Erzeugt Kanten
		for (int index = 0; index < n; index++) {
			int i = r.nextInt(allNodeData.size());
			int j = r.nextInt(allNodeData.size());
			
			NodeData parent = allNodeData.get(i);
			NodeData child = allNodeData.get(j);

			// Symetrisches Einfügen von Eltern-Kind Beziehungen
			parent.addChild(child);
			child.addParent(parent);

		}

		return new GraphData(allNodeData);

	}

}
