package controller.graph.synthesis;

import java.util.Set;

import model.graph.data.GraphData;
import model.points.Point;
import view.synthesis.InputController;

public abstract class Synthesis {
	/**
	 * Erhält ein Set von Punkten und Generiert daraus einen Graphen
	 * 
	 * @param points
	 * @return
	 */
	public abstract GraphData applyOn(Set<Point> points, InputController controller);
}
