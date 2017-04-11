package controller.graph.transformation;

public enum TransformationType {
	IDENTITY ("Nimmt keine Änderung am Graph vor.");
	
	private final String description;

	TransformationType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
