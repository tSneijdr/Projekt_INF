package controller.graph.synthesis;

import java.util.Set;

import javafx.scene.image.Image;
import model.graph.data.GraphData;
import model.points.Point;

/**
 * Abstracte Klasse die einen Methodenkopf definiert
 * 
 * @author tobi
 *
 */
public abstract class Synthesis {
	/**
	 * Erhält ein Set von Punkten und Generiert daraus einen abstrakten Graphen
	 * 
	 * @param points
	 * @return
	 */
	public abstract GraphData applyOn(final Set<Point> points, final Image image, final int numberOfColumns,
			final int numberOfRows);
}
