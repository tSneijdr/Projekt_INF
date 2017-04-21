package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import controller.graph.GraphController;
import controller.graph.synthesis.SynthesisType;
import controller.graph.transformation.TransformationType;
import controller.points.DataReader;
import controller.points.PointController;
import javafx.scene.layout.BorderPane;
import model.points.Point;
import model.points.Record;
import model.points.Store;
import utils.datastructures.Sixtupel;

public class Controller {
	private final Set<Point> points;
	private final GraphController gc;

	public Controller(Store store, SynthesisType synthType) {
		// Wähle die gewünschten Punkte aus allen geladenen Punkten aus
		{
			points = PointController.getPointsMenu(store);
		}
		gc = new GraphController(points, synthType);
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
			synthType = SynthesisType.RANDOM;

			// Wähle die Art der Transformation aus
			transType = TransformationType.IDENTITY;

		}
		
		return gc.run(paneWidth, paneHeight, transType);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Store loadStoreFromFile(String url) {
		HashMap<String, HashMap<String, List>> sortedSixtuples;
		ArrayList<Sixtupel<String, String, Integer, Integer, Integer, Integer>> allSixtuples;
		Store store = new Store();

		// Sortiere die Sextupel in eine 2D Map
		{
			// Lese die Daten als Liste von Sechstupeln aus dem File
			allSixtuples = DataReader.readInFile(url);

			sortedSixtuples = new HashMap<String, HashMap<String, List>>();
			// Sortiere die Sechstupel
			for (Sixtupel tupel : allSixtuples) {
				if (!(sortedSixtuples.containsKey(tupel.FIRST))) {
					HashMap<String, List> subMap = new HashMap<String, List>();
					sortedSixtuples.put((String) tupel.FIRST, subMap);
				}
				if (!sortedSixtuples.get(tupel.FIRST).containsKey(tupel.SECOND)) {
					sortedSixtuples.get(tupel.FIRST).put((String) tupel.SECOND, new LinkedList());
				}
				sortedSixtuples.get(tupel.FIRST).get(tupel.SECOND).add(tupel);
			}
		}

		System.out.println(allSixtuples.get(0).FIRST);

		// Erzeugt aus den sortierten Sechsertupeln Records und fügt sie zu
		{
			// Die einzelnen Bilder (repräsentirert durch ihre URL)
			for (String picture : sortedSixtuples.keySet()) {
				// Die einzelnen Propanden
				for (String propand : sortedSixtuples.get(picture).keySet()) {
					List<Sixtupel> tuples = sortedSixtuples.get(picture).get(propand);
					List<Point> points = new LinkedList<Point>();

					// Erzeugt Punkte aus den einzelnen Tupeln
					for (Sixtupel tuple : tuples) {
						int x = (int) tuple.FOURTH;
						int y = (int) tuple.FIFTH;

						Double timestamp = ((Integer) tuple.THIRD).doubleValue();
						Double duration = ((Integer) tuple.SIXTH).doubleValue();

						points.add(new Point(x, y, timestamp, duration));
					}

					// Erzeugt eine valide Datenreihe
					Point firstPoint = Point.link(points);
					Record record = new Record(picture, propand, firstPoint);

					// Fügt die neue Datenreihe hinzu
					store.addRecord(picture, record);
				}
			}
		}
		System.out.println(store.getAllRecords().get(0).getTitle());
		return store;
	}
}
