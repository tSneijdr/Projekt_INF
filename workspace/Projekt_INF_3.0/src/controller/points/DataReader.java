package controller.points;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.points.Point;
import model.points.Record;
import model.points.Store;
import utils.datastructures.Sixtupel;

public class DataReader {

	/**
	 * read in the eye tracking data from textfile which was created by python
	 * script data is returned as an arraylist consisting of a 6-tuple 0:
	 * filepath 1: proband 2: timestamp 3: x 4: y 5: fixation duration
	 * 
	 * @param filepath
	 * @return
	 */
	public static ArrayList<Sixtupel<String, String, Integer, Integer, Integer, Integer>> readInFile(String filepath) {
		ArrayList<Sixtupel<String, String, Integer, Integer, Integer, Integer>> dataList = new ArrayList<Sixtupel<String, String, Integer, Integer, Integer, Integer>>();

		try {
			Scanner scanner = new Scanner(new File(filepath));
			scanner.useDelimiter(Pattern.compile("]"));

			// get next String until "]" appears (end of the array)
			while (scanner.hasNext()) {
				String line = scanner.next();
				line = line.replace("[", "").replace("]", "");

				// all values are stored in between ""
				Pattern p = Pattern.compile("\"(.+?)\"");
				Matcher m = p.matcher(line);

				String imagePath = "";
				String proband = "";
				int timestamp = 0;
				int x = 0;
				int y = 0;
				int fixationDuration = 0;

				int index = 0;

				while (m.find()) {

					// System.out.println(m.group(1));

					switch (index) {
					case 0:
						imagePath = m.group(1);
						break;
					case 1:
						proband = m.group(1);
						break;
					case 2:
						timestamp = Integer.valueOf(m.group(1));
						break;
					case 3:
						x = Integer.valueOf(m.group(1));
						break;
					case 4:
						y = Integer.valueOf(m.group(1));
						break;
					case 5:
						fixationDuration = Integer.valueOf(m.group(1));
						break;
					}

					index++;
				}

				dataList.add(new Sixtupel<String, String, Integer, Integer, Integer, Integer>(imagePath, proband,
						timestamp, x, y, fixationDuration));

			}

			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		return dataList;
	}

	public static Store loadStoreFromFile(String url) {
		Store store = new Store();
		List<Sixtupel<String, String, Integer, Integer, Integer, Integer>> allTuples;
		allTuples = readInFile(url);
	
		System.out.println("   Größe der Eingabeliste: " +  allTuples.size());
		
		while (!allTuples.isEmpty()) {
			String first = allTuples.get(0).FIRST;

			// Filtere alle Ergebnisse erster Ordnung
			List<Sixtupel<String, String, Integer, Integer, Integer, Integer>> firstFit = null;
			{
				firstFit = new ArrayList<Sixtupel<String, String, Integer, Integer, Integer, Integer>>();
				for (Sixtupel t : allTuples) {
					if (t.FIRST.equals(first)) {
						firstFit.add(t);
					}
				}
				allTuples.removeAll(firstFit);
			}
			
			// Filtere alle Ergebnisse zweiter Ordnung
			while (!firstFit.isEmpty()) {
				String second = firstFit.get(0).SECOND;

				// Filtere alle Ergebnisse zweiter Ordnung
				List<Sixtupel<String, String, Integer, Integer, Integer, Integer>> secondFit = null;
				{
					secondFit = new ArrayList<Sixtupel<String, String, Integer, Integer, Integer, Integer>>();
					for (Sixtupel t : firstFit) {
						if (t.SECOND.equals(second)) {
							secondFit.add(t);
						}
					}
					firstFit.removeAll(secondFit);
				}

				

				// SecondFit enthält nun alle Punkte einer Datenreihe
				{
					Record rec = getRecord(secondFit);
					store.addRecord(rec.getTitle(), rec);
				}
			}

		}
		
		// Testprint
		System.out.println("Gelesene Daten:");
		int i = 0;
		for (Record rec : store.getAllRecords()){
			i += rec.getAllPoints().size();
			System.out.println("   Titel: '" + rec.getTitle() + "'");
			System.out.println("      Teilnehmer: '" + rec.getParticipant()+"'");
			System.out.println("      Anzahl der Punkte: " + rec.getAllPoints().size());
		}
		System.out.println("   Anzahl aller gelesenen Punkte: " + i);
		return store;
	}
	
	private static Record getRecord(List<Sixtupel<String, String, Integer, Integer, Integer, Integer>> sixtuples){
		String title = sixtuples.get(0).FIRST;
		String participant = sixtuples.get(0).SECOND;
		
		List<Point> points = new ArrayList<Point>();
		for (Sixtupel<String, String, Integer, Integer, Integer, Integer> s : sixtuples){
			points.add(getPoint(s));
		}
		Point firstPoint = Point.link(points);
		
		return new Record(title, participant, firstPoint);
		
	}
	
	private static Point getPoint(Sixtupel<String, String, Integer,Integer, Integer, Integer> sixtupel){
		int x = sixtupel.FOURTH;
		int y = sixtupel.FIFTH;
		
		double timepoint = sixtupel.THIRD;
		double duration = sixtupel.SIXTH;
		
		return new Point(x, y, timepoint, duration);
	}
}