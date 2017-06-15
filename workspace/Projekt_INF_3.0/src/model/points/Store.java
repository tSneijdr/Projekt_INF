package model.points;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import model.points.Record;

/**
 * Klasse die einen kompletten Speicher eines Programmablaufes darstellt
 * (mehrere Datensätze usw)
 * 
 * @author tobias meisel
 *
 */
public class Store {
	private final List<StoreNode> allNodes;

	public Store() {
		allNodes = new ArrayList<StoreNode>();
	}

	/**
	 * Fügt einen neuen Record sowie (falls nötig) die zugehörige url hinzu
	 * 
	 * @param url
	 * @param record
	 */
	public void addRecord(Record record) {
		if (record.getURL() == null || record.getURL().equals("")) {
			return;
		}

		String url = record.getURL();
		for (StoreNode node : allNodes) {
			if (node.getUrl().equals(url)) {
				node.addRecord(record);
				return;
			}
		}
		StoreNode newNode = new StoreNode(url);
		newNode.addRecord(record);
		allNodes.add(newNode);
	}

	/**
	 * Gibt alle Records in diesem Speicher zurück
	 * 
	 * @return
	 */
	public List<Record> getAllRecords() {
		List<Record> list = new LinkedList<Record>();
		for (StoreNode node : allNodes) {
			list.addAll(node.getRecords());
		}
		return list;
	}

	/**
	 * Gibt alle Records in diesem Speicher als Speicher zurück wenn Teile der
	 * Url oder die komplette URL übereinstimmen
	 * 
	 * @return
	 */
	private List<Record> getAllRecordsByURL(String url) {
		List<Record> list = new LinkedList<Record>();
		for (StoreNode node : allNodes) {
			boolean fit = node.getUrl().equals(url);
			if (!fit) {
				fit = node.getUrl().contains(url) || url.contains(node.getUrl());
			}

			if (fit) {
				list.addAll(node.getRecords());
			}
		}
		return list;
	}

	public Store getURLStore(String url) {
		Store res = new Store();
		for (Record rec : this.getAllRecordsByURL(url)) {
			res.addRecord(rec);
		}
		return res;
	}

	/**
	 * Klasse die einen Knoten der durch einen url-Wert definiert wird
	 * darstellt. Die Kinder sind die Records die diesem Bild zugeordnet sind
	 * 
	 * @author tobi
	 *
	 */
	private class StoreNode {

		private final String url;

		private final List<Record> allRecords;

		public StoreNode(String url) {
			this.url = url;

			allRecords = new ArrayList<Record>();
		}

		// --------------------------------------------
		// Getter und Setter
		// --------------------------------------------
		public String getUrl() {
			return url;
		}

		public List<Record> getRecords() {
			return new ArrayList<Record>(allRecords);
		}

		public void addRecord(Record rec) {
			allRecords.add(rec);
		}

	}
}
