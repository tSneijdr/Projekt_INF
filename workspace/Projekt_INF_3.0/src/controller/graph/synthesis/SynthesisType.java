package controller.graph.synthesis;

import controller.graph.synthesis.subclasses.StandardSynthesis;

/**
 * Enum das alle Arten von Synthesen enthält. Diese Klasse ist in der
 * ursprünglichen Version de facto redundant, bietet aber Möglichkeiten das
 * Programm um eigene Implementationen zu erweitern, zum Beispiel Synthese durch
 * Quadtree o.ä.
 * 
 * @author tobias meisel
 *
 */
public enum SynthesisType {
	STANDARD("Erzeugt einen Graphen durch Rasterisierung");

	private final String description;

	private SynthesisType(String description) {
		this.description = description;
	}

	/**
	 * Gibt die Beschreibung des Synthesemodells zurück
	 * 
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Gibt ein passendes Syntheseobjekt zurück
	 * 
	 * @return
	 */
	public Synthesis getSynthesis() {
		switch (this) {
		case STANDARD:
			return new StandardSynthesis();
		default:
			return new StandardSynthesis();
		}
	}
}
