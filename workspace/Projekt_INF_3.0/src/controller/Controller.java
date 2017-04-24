package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import controller.graph.GraphController;
import controller.graph.synthesis.SynthesisType;
import controller.graph.transformation.TransformationType;
import controller.points.DataReader;
import controller.points.PointController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import model.points.Point;
import model.points.Record;
import model.points.Store;
import utils.datastructures.Sixtupel;
import view.synthesis.InputController;

public class Controller {
	private final Set<Point> points;
	private final GraphController gc;

	public Controller(Store store) {
		// Wähle die gewünschten Punkte aus allen geladenen Punkten aus
		{
			//points = PointController.getPointsMenu(store);
			
			points = store.getAllRecords().get(2).getAllPoints();
		}
		
		InputController con = InputController.run();
		
		
		gc = new GraphController(points, con);
	}

	/**
	 * Nimmt Parameter entgegen und erzeugt daraus ein Pane das einen Ausschnitt
	 * aller Punkte darstellt
	 * 
	 * @param paneWidth
	 * @param paneHeight
	 * @param store
	 * @return
	 */
	public BorderPane generatePane(int paneWidth, int paneHeight, boolean showEdges) {
		SynthesisType synthType = null;
		TransformationType transType = null;

		// Erzeuge einen Graphen aus den Punkten und erzeugt daraus den Pane
		{
			// Wähle die Art der Synthese aus
			synthType = SynthesisType.STANDARD;

			// Wähle die Art der Transformation aus
			transType = TransformationType.IDENTITY;

		}
		
		return gc.run(paneWidth, paneHeight, transType);
	}
}
