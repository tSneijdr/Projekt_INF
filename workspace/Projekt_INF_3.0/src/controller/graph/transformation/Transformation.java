package controller.graph.transformation;

import java.util.Set;

import controller.graph.transformation.classes.IdentityTransformation;
import model.graph.Graph;
import model.points.Point;

public abstract class Transformation {
	private final String name;

	public Transformation(String name) {
		this.name = name;
	}

	/**
	 * Erhält ein Set von Punkten und Generiert daraus einen Graphen
	 * 
	 * @param points
	 * @return
	 */
	public abstract Graph applyOn(Graph g);

	public String getName() {
		return name;
	}
	
	public static Graph get(TransformationType type, Graph graph) {
		Transformation trans;
		switch (type) {
		case IDENTITY:
			trans = new IdentityTransformation();
			break;

		default:
			trans = new IdentityTransformation();
			break;
		}
		return trans.applyOn(graph);
	}
}
