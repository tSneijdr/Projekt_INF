package controller;

import java.util.HashSet;
import java.util.Set;

import controller.graph.GraphController;
import controller.graph.transformation.TransformationType;
import javafx.scene.layout.BorderPane;
import model.points.Point;
import model.points.Record;
import model.points.Store;
import view.synthesis.InputController;

public class Controller {
	private final GraphController gc;

	public Controller(Store store, InputController incon) {
		// Wähle die gewünschten Punkte aus allen geladenen Punkten aus
		final Set<Point> points;
		{

			points = new HashSet<Point>();
			for (Record rec : store.getAllRecords()) {
				points.addAll(rec.getAllPoints());
			}
		}

		gc = new GraphController(points, incon);
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
	public BorderPane generatePane(int paneWidth, int paneHeight, TransformationType transType) {
		return gc.run(paneWidth, paneHeight, transType);
	}
}
