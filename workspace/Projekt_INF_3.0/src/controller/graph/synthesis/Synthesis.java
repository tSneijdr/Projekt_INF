package controller.graph.synthesis;

import java.util.Set;

import controller.graph.synthesis.classes.RandomSynthesis;
import model.graph.data.GraphData;
import model.graph.graph.Graph;
import model.points.Point;

public abstract class Synthesis {

	/**
	 * Erhält ein Set von Punkten und Generiert daraus einen Graphen
	 * 
	 * @param points
	 * @return
	 */
	public abstract GraphData applyOn(Set<Point> points);

	public static GraphData get(SynthesisType type, Set<Point> points) {
		switch (type) {
		case RANDOM:
			return new RandomSynthesis().applyOn(points);
		default:
			return new RandomSynthesis().applyOn(points);
		}

	}

}
