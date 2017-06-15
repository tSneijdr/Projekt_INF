package controller.graph.transformation;

import controller.graph.transformation.subclasses.CircularLayout;
import controller.graph.transformation.subclasses.ForceDirectedLayout;
import controller.graph.transformation.subclasses.HierarchicalTransformation;
import controller.graph.transformation.subclasses.IdentityTransformation;
import controller.graph.transformation.subclasses.OriginalTransformation;

/**
 * Enum das alle Arten von Transformationen enthält. Diese Klasse bietet
 * Möglichkeiten das Programm einfach um eigene Implementationen zu erweitern.
 * 
 * @author tobias meisel
 *
 */
public enum TransformationType {
	ORIGINAL("Grid with picture"), IDENTITY("Grid without picture"), HIERARCHICAL("Hierarchical"), FORCEDIRECTED(
			"Forcdirected"), CIRCULAR("Circular");

	private final String description;

	/**
	 * Konstruktor
	 * 
	 * @param s
	 */
	TransformationType(String s) {
		this.description = s;
	}

	/**
	 * Gibt die Beschreibung dieses Objekts zurück
	 * 
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Gibt das passende Transformationsobjekt zurück
	 * 
	 * @return
	 */
	public Transformation getTransformation() {

		switch (this) {

		case ORIGINAL:
			return new OriginalTransformation();
		case HIERARCHICAL:
			return new HierarchicalTransformation();
		case FORCEDIRECTED:
			return new ForceDirectedLayout();
		case CIRCULAR:
			return new CircularLayout();
		case IDENTITY:
			return new IdentityTransformation();
		default:
			return null;
		}
	}
}
