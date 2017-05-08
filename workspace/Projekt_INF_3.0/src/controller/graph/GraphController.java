package controller.graph;

import java.net.URL;
import java.util.Set;

import controller.graph.synthesis.Synthesis;
import controller.graph.transformation.Transformation;
import controller.graph.transformation.TransformationType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import model.graph.data.GraphData;
import model.graph.graph.Graph;
import model.points.Point;
import view.synthesis.InputController;

public class GraphController {

	private final GraphData data;

	private boolean showEdges = true;

	public GraphController(Set<Point> points, InputController controller) {
		Synthesis synthesis = controller.getType().getSynthesis();
		this.data = synthesis.applyOn(points, controller);

		// TODO Hier!
		{
			URL url = this.getClass().getResource("example.jpg");
			Image img = new Image(url.toString());
			this.data.setBackground(img);
		}
	}

	public BorderPane run(int paneWidth, int paneHeight, TransformationType transType) {

		// Erstelle einen nichttransformierten Graphen aus den Daten
		Graph graph = Transformation.getUntransformedGraph(data);

		// Transformiere Graph
		transType.getTransformation().applyOn(graph);

		System.out.println("   Größe des Datenknotens: " + data.getAllNodeData().size());
		System.out.println("   Größe des Graphen: " + graph.getAllNodes().size());

		// Erstelle Pane
		BorderPane pane;
		if (transType == TransformationType.ORIGINAL) {
			pane = Graph.getPane(paneWidth, paneHeight, graph, showEdges, true);
		} else {
			pane = Graph.getPane(paneWidth, paneHeight, graph, showEdges, false);
		}

		return pane;
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
