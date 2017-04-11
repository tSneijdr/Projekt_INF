package model.points;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
	public Record[] getAllRecords() {
		return (Record[]) allNodes.stream().flatMap((StoreNode node) -> (node.getRecords())).toArray();
	}

	/**
	 * Gibt alle Records zurück die dem Teilnehmer mit dem mitgelieferten Namen
	 * gehören
	 * 
	 * @param name
	 * @return
	 */
	public Record[] getByParticipant(String name) {
		return (Record[]) allNodes.stream().flatMap((StoreNode node) -> (node.getRecords()))
				.filter((x) -> (x.getParticipant().equals(name))).toArray();
	}

	/**
	 * Gibt alle Records die einem Bild mit der übergebenen URL zugeordnet sind
	 * zurück
	 * 
	 * @param url
	 */
	public Record[] getByImage(String url) {
		return (Record[]) allNodes.stream().filter((StoreNode x) -> x.url.equals(url))
				.flatMap((StoreNode x) -> (x.getRecords())).toArray();
	}

	/**
	 * Gibt alle Records die aktiv sind zurück
	 * 
	 * @param url
	 */
	public Record[] getActiveRecords() {
		return (Record[]) allNodes.stream().flatMap((StoreNode n) -> (n.getRecords()))
				.filter((Record rec) -> rec.isActive()).toArray();
	}

	/**
	 * Gibt alle Records zurück auf die die beiden Parameter zutreffen
	 * 
	 * @param url
	 * @param name
	 * @return
	 */
	public Record[] getByImageAndParticipant(String url, String name) {
		return (Record[]) allNodes.stream().filter(x -> x.getUrl().equals(url))
				.flatMap((StoreNode x) -> (x.getParticipant(name))).toArray();
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
		public Stream<Record> getParticipant(String name) {
			return allRecords.stream().filter((part) -> (part.getParticipant().equals(name)));
		}

		// --------------------------------------------
		// Getter und Setter
		// --------------------------------------------
		public String getUrl() {
			return url;
		}

		public Stream<Record> getRecords() {
			return allRecords.stream();
		}

	}
}
