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
	private final Map<NodeData, Integer> childThickness;

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
		childThickness = new HashMap<NodeData, Integer>();

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

	/**
	 * Fügt ein Kind dieses abstrakten Knoten hinzu
	 * 
	 * @param child
	 */
	public void addChild(NodeData child) {
		// Legalitätschecks
		if (child == this || child == null) {
			return;
		} else if (this.children.contains(child)) {
			final int count = childThickness.get(child).intValue();
			this.childThickness.put(child, count + 1);
		} else {
			this.children.add(child);
			this.childThickness.put(child, 1);
		}
	}

	/**
	 * Gibt die Anzahl der Punkte in diesem Knoten deren Nachfolger im
	 * übergebenen Knoten liegt zurück
	 * 
	 * @param child
	 * @return
	 */
	public int getNumberOfChildEdges(NodeData child) {
		if (this.childThickness.containsKey(child)) {
			return this.childThickness.get(child);
		} else {
			return 0;
		}
	}

	/**
	 * Fügt eine konkrete Darstellung dieses abstrakten Knotens hinzu
	 * 
	 * @param node
	 */
	public void addInstance(Node node) {
		allInstances.add(node);
	}

	public Color getColor() {
		return this.color;
	}

}
