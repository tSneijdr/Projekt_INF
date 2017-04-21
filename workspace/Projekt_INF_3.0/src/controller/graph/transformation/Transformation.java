package controller.graph.transformation;

import controller.graph.transformation.classes.RandomTransformation;
import model.graph.data.GraphData;
import model.graph.graph.Graph;

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
	public abstract Graph applyOn(GraphData g);

	public String getName() {
		return name;
	}
	
	public static Graph get(TransformationType type, GraphData data) {
		Transformation trans;
		switch (type) {
		case IDENTITY:
			trans = new RandomTransformation();
			break;

		default:
			trans = new RandomTransformation();
			break;
		}
		return trans.applyOn(data);
	}
}
