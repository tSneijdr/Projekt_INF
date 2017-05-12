package controller.graph.synthesis;

import controller.graph.synthesis.subclasses.StandardSynthesis;

public enum SynthesisType {
	STANDARD("Erzeugt einen Graphen durch Rasterisierung");

	private final String description;

	private SynthesisType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public Synthesis getSynthesis() {
		return new StandardSynthesis();
	}
}
