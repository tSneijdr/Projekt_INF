package controller.graph.transformation.classes;

import controller.graph.transformation.Transformation;
import model.graph.Graph;

public class IdentityTransformation extends Transformation{

	public IdentityTransformation() {
		super("Identität");
		// TODO Auto-generated constructor stub
	}

	public Graph applyOn(Graph g) {
		return g;
	}

	
}
