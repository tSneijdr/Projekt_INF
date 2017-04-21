package model.points;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import model.points.Record;

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
	public void addRecord(String url, Record record) {
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
	 * Gibt alle Records zurück die dem Teilnehmer mit dem mitgelieferten Namen
	 * gehören
	 * 
	 * @param name
	 * @return
	 */
	public List<Record> getByParticipant(String name) {
		List<Record> list = new LinkedList<Record>();
		for (StoreNode node : allNodes) {
			list.addAll(node.getParticipant(name));
		}
		return list;
	}

	/**
	 * Gibt alle Records die einem Bild mit der übergebenen URL zugeordnet sind
	 * zurück
	 * 
	 * @param url
	 */
	public List<Record> getByImage(String url) {
		List<Record> list = new LinkedList<Record>();
		for (StoreNode node : allNodes) {
			if (node.getUrl().equals(url)) {
				list.addAll(node.getRecords());
			}
		}
		return list;

	}

	/**
	 * Gibt alle Records die aktiv sind zurück
	 * 
	 * @param url
	 */
	public List<Record> getActiveRecords() {
		List<Record> list = new LinkedList<Record>();
		for (StoreNode node : allNodes) {
			for (Record record : node.getRecords()) {
				if (record.isActive()) {
					list.add(record);
				}
			}
		}
		return list;
	}

	/**
	 * Gibt alle Records zurück auf die die beiden Parameter zutreffen
	 * 
	 * @param url
	 * @param name
	 * @return
	 */
	public List<Record> getByImageAndParticipant(String url, String name) {
		List<Record> list = new LinkedList<Record>();
		for (StoreNode node : allNodes) {
			if (node.getUrl().equals(url)) {
				list.addAll(node.getParticipant(name));
			}
		}
		return list;
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

		public void addRecord(Record rec) {
			allRecords.add(rec);
		}

		/**
		 * Gibt alle Records mit dem gesuchten Namen zurück
		 * 
		 * @param name
		 * @return
		 */
		public List<Record> getParticipant(String name) {
			List<Record> list = new LinkedList<Record>();
			for (Record record : allRecords) {
				if (record.getParticipant().equals(name)) {
					list.add(record);
				}
			}
			return list;

		}

		// --------------------------------------------
		// Getter und Setter
		// --------------------------------------------
		public String getUrl() {
			return url;
		}

		public List<Record> getRecords() {
			return allRecords;
		}

	}
}
