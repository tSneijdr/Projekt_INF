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

public class GraphController {

	private final GraphData data;

	private boolean showEdges = true;

	public GraphController(Set<Point> points, SynthesisType synthType) {
		this.data = Synthesis.get(synthType, points);
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

}
