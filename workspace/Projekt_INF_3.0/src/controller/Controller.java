package controller;

import java.util.HashSet;
import java.util.Set;

import controller.graph.synthesis.Synthesis;
import controller.graph.synthesis.SynthesisType;
import controller.graph.transformation.Transformation;
import controller.graph.transformation.TransformationType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import model.graph.data.GraphData;
import model.graph.graph.Graph;
import model.points.Point;
import model.points.Record;
import model.points.Store;

/**
 * Hauptkontrollerklasse, von hier aus werden in einer Hierarchie fast alle
 * nicht-Gui-Operationen ausgerführt
 * 
 * @author tobias meisel
 *
 */
public class Controller {
	private final GraphData data;

	/**
	 * Standardkonstruktor Initialisiert den Subkontroller
	 * 
	 * @param store
	 *            Speicher mit allen Daten
	 * @param synthType
	 *            Art der Synthese der Daten aus den Ursprünglichen Daten
	 * @param img
	 *            Hintergrundbild
	 * @param numberOfColumns
	 * @param numberOfRows
	 */
	public Controller(Store store, final SynthesisType synthType, final Image img, final int numberOfColumns,
			final int numberOfRows) {

		// Wähle die gewünschten Punkte aus allen geladenen Punkten aus
		final Set<Point> points;
		{
			points = new HashSet<Point>();
			for (Record rec : store.getAllRecords()) {
				points.addAll(rec.getAllPoints());
			}
		}
		
		Synthesis synthesis = synthType.getSynthesis();

		this.data = synthesis.applyOn(points, img, numberOfRows, numberOfRows);
		this.data.setBackground(img);
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
		// LOG
		System.out.println("      Subcontroller: new Pane, width: " + paneWidth + ", height: " + paneHeight
				+ ", trans: " + transType.name());

		// Erstelle einen nichttransformierten Graphen aus den Daten
		final Graph graph = Transformation.getUntransformedGraph(data);

		// Transformiere Graph
		transType.getTransformation().applyOn(graph);

		// Erstelle Pane
		final Pane pane;
		if (transType == TransformationType.ORIGINAL) {
			pane = Graph.getPane(paneWidth, paneHeight, graph, true);
		} else {
			pane = Graph.getPane(paneWidth, paneHeight, graph, false);
		}

		return pane;
	}
}
