package controller.graph;

import java.util.Set;

import controller.graph.synthesis.Synthesis;
import controller.graph.synthesis.SynthesisType;
import controller.graph.transformation.Transformation;
import controller.graph.transformation.TransformationType;
import javafx.scene.layout.BorderPane;
import model.graph.Graph;
import model.points.Point;

public abstract class GraphController {

	public static BorderPane run(int paneWidth, int paneHeight, Set<Point> points, SynthesisType synthType, TransformationType transType) {
		// Synthese eines Graphes aus dem Set der Punkte (ausgewählt durch Menü)
		
		// TODO: Implementiere Auswahlmenü
		Graph graph = Synthesis.get(synthType, points);

		// Transformation des Graphens (ausgewählt durch Menü)

		// TODO: Implementiere Auswahlmenü
		graph = Transformation.get(transType, graph);
		
		return graph.getPane(paneWidth, paneHeight);
	}
}
