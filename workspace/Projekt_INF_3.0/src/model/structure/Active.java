package model.structure;

import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.core.Point;
import model.core.Record;
import model.datastructures.Octree;
import model.datastructures.Quadtree;
import utils.Range2D;
import utils.Range3D;

public class Active {
	// Dient zum mappen in 2D und 3D
	private Quadtree quad;
	private Octree oct;

	// Hilfsvariablen
	private int numberOfPoints;
	private int n = 10;

	// Größe des Feldes
	private Range3D range;

	// Speichert alle in dieser Instanz aktiven Records
	private final ObservableList<Record> allActiveRecords;

	// -------------------------------------------------------------
	// Konstruktoren
	// -------------------------------------------------------------
	public Active(Range3D range, int n) {
		allActiveRecords = FXCollections.observableArrayList();

		if (range != null) {
			quad = new Quadtree(range.to2DRange(), n);
			oct = new Octree(range, n);
		}

		numberOfPoints = 0;
	}

	public Active(int n) {
		this(null, n);
	}

	public Active() {
		this(10);
	}

	// ----------------------------------------------------------------
	// Anderes
	// ----------------------------------------------------------------
	/**
	 * Fügt ein Record der Datenhaltung zu
	 * 
	 * Wirft nullPointerException bei null-Wert
	 * 
	 * @param rec
	 *            das Record
	 */
	public void addRecord(Record rec) {
		if (this.range == null)
			return;

		if (rec == null)
			throw new NullPointerException("Es wurde ein null-Wert als Record übergeben");

		// Speichert das Record
		allActiveRecords.add(rec);

		// Fügt die einzelnen Punkte der Datenhaltungsebene hinzu
		for (Point p : rec.getAllPoints()) {
			numberOfPoints += 1;
			quad.add(p);
			oct.add(p);
		}
	}

	public int getNumberOfPoints() {
		return numberOfPoints;
	}

	public void setRange(Range3D range) {
		if (this.range == null || !this.range.equals(range)) {
			// Update die Werte und leere die Datenstrukturen
			this.range = range;
			this.quad = new Quadtree(range.to2DRange(), n);
			this.oct = new Octree(range, n);
			this.numberOfPoints = 0;
		}
	}

	public Set<Point> get(Range3D range) {
		return oct.getPoints(range);
	}

	public Set<Point> get(Range2D range) {
		return quad.getPoints(range);
	}

}
