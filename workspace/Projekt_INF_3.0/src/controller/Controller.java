package controller;

import java.util.Set;

import controller.graph.GraphController;
import controller.graph.synthesis.SynthesisType;
import controller.graph.transformation.TransformationType;
import controller.points.PointController;
import javafx.scene.layout.BorderPane;
import model.points.Point;
import model.points.Store;
import view.RootLayoutController;

public abstract class Controller {
		
	/**
	 * Nimmt Parameter entgegen und erzeugt daraus ein Pane das einen Ausschnitt
	 * aller Punkte darstellt
	 * 
	 * @param paneWidth
	 * @param paneHeight
	 * @param store
	 * @return
	 */
	public static BorderPane generatePane(int paneWidth, int paneHeight, Store store) {
		Set<Point> points = null;
		SynthesisType synthType = null;
		TransformationType transType = null;

		// Wähle die gewünschten Punkte aus allen geladenen Punkten aus
		{
			points = PointController.getPointsMenu(store);
		}

		// Erzeuge einen Graphen aus den Punkten und erzeugt daraus den Pane
		{
			// Wähle die Art der Synthese aus
			synthType = SynthesisType.RANDOM;

			// Wähle die Art der Transformation aus
			transType = TransformationType.IDENTITY;

		}

		return GraphController.run(paneWidth, paneHeight, points, synthType, transType);
	}
}
