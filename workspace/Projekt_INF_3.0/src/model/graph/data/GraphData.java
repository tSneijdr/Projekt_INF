package model.graph.data;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.image.Image;
import utils.ranges.Range2D;

/**
 * Stellt einen abstrakten Graph dar, hierbei werden wichtige daten, z.b. Größe
 * und Hintergrund, hier gespeichert
 * 
 * @author tobias meisel
 *
 */
public class GraphData {
	private final Set<NodeData> allNodeData;
	private final Range2D range;
	private Image originalImage;

	// Daten zur Rasterisierung
	private final int numColumns;
	private final int numRows;

	public GraphData(Set<NodeData> allNodeData, Image image, int numColumns, int numRows) {
		this.allNodeData = allNodeData;

		Range2D r = new Range2D(0, (int) image.getWidth(), 0, (int) image.getHeight());
		this.range = r;

		this.originalImage = image;

		this.numColumns = numColumns;
		this.numRows = numRows;
	}

	// ---------------------------------------------------
	// Getter und Setter
	// ---------------------------------------------------
	public Set<NodeData> getAllNodeData() {
		return new HashSet<NodeData>(allNodeData);
	}

	public Range2D getRange() {
		return range;
	}

	public Image getBackground() {
		return originalImage;
	}

	public void setBackground(Image img) {
		this.originalImage = img;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public int getNumRows() {
		return numRows;
	}
}
