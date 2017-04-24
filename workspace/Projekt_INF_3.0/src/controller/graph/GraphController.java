package controller.graph;

import java.util.Set;

import controller.graph.synthesis.Synthesis;
import controller.graph.synthesis.SynthesisType;
import controller.graph.transformation.Transformation;
import controller.graph.transformation.TransformationType;
import javafx.scene.layout.BorderPane;
import model.graph.data.GraphData;
import model.graph.graph.Graph;
import model.points.Point;
import view.popup.SynthesisPopupController;

public class GraphController {

	private final GraphData data;

	private boolean showEdges = true;

	public GraphController(Set<Point> points, SynthesisPopupController controller) {
		this.data = Synthesis.get(controller.getType(), points);
	}

	public BorderPane run(int paneWidth, int paneHeight, TransformationType transType) {

		// Erstelle einen nichttransformierten Graphen aus den Daten
		Graph graph = Transformation.getUntransformedGraph(data);

		// Transformiere Graph
		Graph result = Transformation.get(transType, graph);

		// Erstelle Pane
		return result.getPane(paneWidth, paneHeight, showEdges);
	}

	// --------------------------------------------------------------------------------------------------
	// Getter und Setter
	// --------------------------------------------------------------------------------------------------
	public boolean isShowEdges() {
		return showEdges;
	}

	public void setShowEdges(boolean showEdges) {
		this.showEdges = showEdges;
	}

	public GraphData getData() {
		return data;
	}

}
