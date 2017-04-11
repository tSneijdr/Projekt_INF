package controller.graph.synthesis;

import java.util.Set;

import controller.graph.synthesis.classes.RandomSynthesis;
import model.graph.Graph;
import model.points.Point;

public abstract class Synthesis {

	private final String name;

	public Synthesis(String name) {
		this.name = name;
	}

	/**
	 * Erhält ein Set von Punkten und Generiert daraus einen Graphen
	 * 
	 * @param points
	 * @return
	 */
	public abstract Graph applyOn(Set<Point> points);

	public String getName() {
		return name;
	}

	public static Graph get(SynthesisType type, Set<Point> points) {
		Synthesis synthesis = null;
		switch (type) {
		case RANDOM:
			synthesis = new RandomSynthesis();
			break;
		default:
			synthesis = new RandomSynthesis();
			break;
		}
		
		return synthesis.applyOn(points);
	}

}
