package model.graph.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class NodeData {

	// Lokalitätsdaten aus der Synthese
	private final int originalColumn;
	private final int originalRow;

	// Eltern- und Kinderknoten
	private final List<NodeData> parents;
	private final List<NodeData> children;

	// Zusätzliche Informationnen zu diesem Knoten
	private final Set<String> informations;

	// Daten zum Darstellen des Knotens
	private boolean active;

	public NodeData(int column, int row, HashSet<String> informations) {
		this.originalColumn = column;
		this.originalRow = row;

		this.active = false;

		// Eltern- und Kinderliste
		parents = new LinkedList<NodeData>();
		children = new LinkedList<NodeData>();

		// Infromationen
		this.informations = informations;
	}

	// ---------------------------------------------------
	// Getter und Setter
	// ---------------------------------------------------
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void toggle() {
		System.out.println("Toggle");
		setActive(!active);
	}

	public List<NodeData> getParents() {
		return new ArrayList<NodeData>(parents);
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
		if (this.children.contains(child) || child == this) {
			return;
		} else {
			this.children.add(child);
			child.addParent(this);
		}
	}

	public void addParent(NodeData parent) {
		if (this.parents.contains(parent) || parent == this) {
			return;
		} else {
			this.parents.add(parent);
			parent.addChild(this);
		}
	}

}
