package controller.graph;

import java.util.Set;

import controller.graph.synthesis.Synthesis;
import controller.graph.synthesis.SynthesisType;
import controller.graph.transformation.Transformation;
import controller.graph.transformation.TransformationType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import model.graph.data.GraphData;
import model.graph.graph.Graph;
import model.points.Point;

public class Subcontroller {

	private final GraphData data;

	public Subcontroller(Set<Point> points, final SynthesisType synthType, final Image img, final int numberOfColumns,
			final int numberOfRows) {
		Synthesis synthesis = synthType.getSynthesis();

		this.data = synthesis.applyOn(points, img, numberOfRows, numberOfRows);
		this.data.setBackground(img);
	}

	public Pane getPane(int paneWidth, int paneHeight, TransformationType transType) {

		// Erstelle einen nichttransformierten Graphen aus den Daten
		Graph graph = Transformation.getUntransformedGraph(data);

		// Transformiere Graph
		transType.getTransformation().applyOn(graph);

		// Erstelle Pane
		Pane pane;
		if (transType == TransformationType.ORIGINAL) {
			pane = Graph.getPane(paneWidth, paneHeight, graph, true, false);
		} else {
			pane = Graph.getPane(paneWidth, paneHeight, graph, false, true);
		}
		
		
		return pane;
	}

	// --------------------------------------------------------------------------------------------------
	// Getter und Setter
	// --------------------------------------------------------------------------------------------------
}
