package controller.graph.synthesis;

import java.util.Set;

import controller.graph.synthesis.random.RandomSynthesis;
import controller.graph.synthesis.standard.StandardSynthesis;
import model.graph.data.GraphData;
import model.graph.graph.Graph;
import model.points.Point;

public abstract class Synthesis {
	private final String description;
	
	public Synthesis(String description){
		this.description = description;
	}
	
	/**
	 * Erhält ein Set von Punkten und Generiert daraus einen Graphen
	 * 
	 * @param points
	 * @return
	 */
	public abstract GraphData applyOn(Set<Point> points);

	public static GraphData get(SynthesisType type, Set<Point> points) {
		return type.getSynthesis().applyOn(points);
	}

}
