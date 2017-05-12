package model.graph.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.scene.paint.Color;
import model.graph.graph.Node;

public class NodeData {

	// Lokalitätsdaten aus der Synthese
	private final int originalColumn;
	private final int originalRow;

	// Eltern- und Kinderknoten
	private final List<NodeData> children;
	private final Map<NodeData, Integer> numChildInstances;

	// Zusätzliche Informationnen zu diesem Knoten
	private final Set<String> informations;

	// Daten zum Darstellen des Knotens
	private Color color;
	private final Set<Node> allInstances;

	public NodeData(int column, int row, Set<String> informations) {
		this.originalColumn = column;
		this.originalRow = row;

		this.color = Color.BLACK;

		// Kinderliste
		children = new LinkedList<NodeData>();
		numChildInstances = new HashMap<NodeData, Integer>();

		// Infromationen
		this.informations = informations;

		// Alle Instanzen dieses Datensatzes
		allInstances = new HashSet<Node>();
	}

	// ---------------------------------------------------
	// Getter und Setter
	// ---------------------------------------------------

	public void setColor(Color color) {
		this.color = color;
		for (Node node : allInstances) {
				node.setColor();
		}
	}

	public List<NodeData> getChildren() {
		return new ArrayList<NodeData>(children);
	}

	public Set<String> getInformations() {
		return new HashSet<String>(informations);
	}

	public int getOriginalColumn() {
		return originalColumn;
	}

	public int getOriginalRow() {
		return originalRow;
	}

	public void addChild(NodeData child) {
		System.out.println("Wobalobdubdub a");
		if (child == this || child == null) {
			return;
		} else if (this.children.contains(child)) {
			final int count = numChildInstances.get(child).intValue();
			this.numChildInstances.put(child, count + 1);
			System.out.println("Wobalobdubdub b");
		} else {
			this.children.add(child);
			this.numChildInstances.put(child, 1);
		}
	}

	public int getNumberOfChildEdges(NodeData child) {
		if (this.numChildInstances.containsKey(child)) {
			return this.numChildInstances.get(child);
		} else {
			return 0;
		}
	}

	public void addInstance(Node node) {
		allInstances.add(node);
	}

	public Color getColor() {
		return this.color;
	}

}
