package model.graph.data;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import utils.ranges.Range2D;

public class GraphData {
	private final List<NodeData> allNodeData;
	private final Range2D range;
	private Image originalImage;

	public GraphData(List<NodeData> allNodeData, Image background) {
		this.allNodeData = allNodeData;

		Range2D r = new Range2D(0, (int) background.getWidth(), 0, (int) background.getHeight());
		this.range = r;

		this.originalImage = background;
	}

	// ---------------------------------------------------
	// Getter und Setter
	// ---------------------------------------------------
	public List<NodeData> getAllNodeData() {
		return new ArrayList<NodeData>(allNodeData);
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
}
