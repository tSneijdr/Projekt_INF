package loader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import core.Point;
import core.Record;

/**
 * Speichert und liest Datensätze in/aus Textdateien
 * @author tobi
 *
 */
public abstract class Loader {

	/**
	 * Liest eine Textdatei ein und gibt einen Datensatz zurück
	 * @param filename name der Datei
	 * @return
	 * @throws Exception
	 */
	public static Record load(String filename) throws Exception {
		File f = new File(filename);
		if (!(f.exists() && f.canRead())) {
			throw new Exception(
					"Die zu lesende Datei exisitiert nicht oder kann nicht gelesen werden.");
		}

		Scanner s = null;
		LinkedList<String[]> list = new LinkedList<String[]>();
		String title;

		try {
			s = new Scanner(f);

			// Liest den Titel, der als erstes in der Datei stehen sollte aus
			title = s.nextLine();

			while (s.hasNextLine()) {
				String line = s.nextLine();

				// Bearbeitet den gelesenen String
				line = line.replaceAll(",", "");
				line = line.replaceAll("  ", " ");
				line = line.trim();

				String[] ar = line.split(" ");

				// Prüft die Länge
				if (ar.length != 4) {
					s.close();
					throw new IllegalArgumentException(
							"Eine der gelesenen Eingaben hat ein illegales Format.");
				}
				list.add(ar);
			}
			s.close();
		} catch (Exception e) {
			// Vermeide Speicherlecks
			if (s != null) {
				s.close();
			}

			e.printStackTrace();
			throw new Exception(
					"Beim Laden einer vorhandenen Datei ist ein Fehler passiert.");
		}

		// castet die Liste zu einem Punkt
		Point p = Caster.toPoint(list);

		Record r = null;
		if (title.contains(",")) {
			String[] l = title.split(",");
			if (l.length > 1) {
				r = new Record(l[0], l[1], p);
			} else {
				r = new Record(title, p);
			}

		} else {
			r = new Record(title, p);
		}

		return r;
	}

	/**
	 * Speichert einen Datensatz als CSV ab
	 * 
	 * @param filename
	 * @param p
	 */
	public static void save(String filename, Record rec) {
		File f = new File(filename);

		if (f.isDirectory()) {
			throw new IllegalArgumentException(
					"Dieser Dateiname beschreibt einen Ordner.");
		}

		if (f.exists()) {
			// Löscht eine bestehende Datei
			f.delete();
		}
		// Kreiert die Datei (neu)
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Beginnt das Schreiben
		Writer w = null;
		try {
			w = new PrintWriter(f);
			w.write(rec.TITLE + ", " + rec.PARTICIPANT);

			// Schreibt alle Punkte in die Datei
			List<String[]> list = Caster.toList(rec.firstPoint);
			for (String[] str : list) {
				String line = "";
				for (String s : str) {
					line += s + ", ";
				}
				// Bringe line in Form

				line = line.trim();
				line = line.substring(0, (line.length() - 1));
				line += "\n";

				w.write(line);
			}

		} catch (Exception e) {
			try {
				w.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
