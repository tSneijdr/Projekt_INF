package controller.graph.synthesis;

public enum SynthesisType{
	RANDOM("Gibt einen vollständig zufälligen Graphen zurück");

	private final String description;

	SynthesisType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}

}
