package controller.graph;

import java.util.Set;

import controller.graph.synthesis.Synthesis;
import controller.graph.transformation.Transformation;
import controller.graph.transformation.TransformationType;
import javafx.scene.layout.BorderPane;
import model.graph.data.GraphData;
import model.graph.graph.Graph;
import model.points.Point;
import view.synthesis.InputController;

public class GraphController {

	private final GraphData data;

	public GraphController(Set<Point> points, InputController controller) {
		Synthesis synthesis = controller.getType().getSynthesis();
		this.data = synthesis.applyOn(points, controller);
		this.data.setBackground(controller.getImage());
	}

	public BorderPane run(int paneWidth, int paneHeight, TransformationType transType) {

		// Erstelle einen nichttransformierten Graphen aus den Daten
		Graph graph = Transformation.getUntransformedGraph(data);

		// Transformiere Graph
		transType.getTransformation().applyOn(graph);

		// Erstelle Pane
		BorderPane pane;
		if (transType == TransformationType.ORIGINAL) {
			pane = Graph.getPane(paneWidth, paneHeight, graph, true);
		} else {
			pane = Graph.getPane(paneWidth, paneHeight, graph, false);
		}

		return pane;
	}

	// --------------------------------------------------------------------------------------------------
	// Getter und Setter
	// --------------------------------------------------------------------------------------------------
}
