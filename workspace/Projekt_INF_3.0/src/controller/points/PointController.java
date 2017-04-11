package controller.points;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import model.points.Point;
import model.points.Record;
import model.points.Store;
import utils.datastructures.Sixtupel;

public class PointController {

	/**
	 * 
	 * @param store
	 * @return
	 */
	public static Set<Point> getPointsMenu(Store store) {
		// TODO: Implement
		return null;
	}

	public static Store loadStoreFromFile(String url) {

		HashMap<String, HashMap<String, List>> sortedSixtuples;
		ArrayList<Sixtupel<String, String, Integer, Integer, Integer, Integer>> allSixtuples;

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

		Store store = new Store();

		// store.

		return store;
	}

	public static Record sixTupleToRecord() {
		String title = null;
		String participant = null;

		Record record = new Record(title, participant, null);
	}
}
