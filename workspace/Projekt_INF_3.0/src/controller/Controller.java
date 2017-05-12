package controller;

import java.util.HashSet;
import java.util.Set;

import controller.graph.Subcontroller;
import controller.graph.synthesis.SynthesisType;
import controller.graph.transformation.TransformationType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import model.points.Point;
import model.points.Record;
import model.points.Store;

public class Controller {
	private final Subcontroller sub;

	public Controller(Store store, final SynthesisType synthType, final Image img, final int numberOfColumns,
			final int numberOfRows) {
		
		// Wähle die gewünschten Punkte aus allen geladenen Punkten aus
		final Set<Point> points;
		{
			// TODO
			points = new HashSet<Point>();
			for (Record rec : store.getAllRecords()) {
				points.addAll(rec.getAllPoints());
			}
		}

		sub = new Subcontroller(points, synthType, img, numberOfColumns, numberOfRows);
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
	public Pane generatePane(int paneWidth, int paneHeight, TransformationType transType) {
		return sub.getPane(paneWidth, paneHeight, transType);
	}
}
