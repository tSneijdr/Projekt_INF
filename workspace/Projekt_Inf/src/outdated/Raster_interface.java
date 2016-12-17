package outdated;

import gui.GraphicMode;
import datastructures.Pair;
import datastructures.Triple;

import javax.swing.JPanel;

public interface Raster_interface {

	// --------------------------------------
	// Getter and Setter---------------------
	// --------------------------------------

	public int getValue(int x, int y);

	public void setValue(int x, int y, int value);
	
	public void incrementValue(int x, int y);
	
	public long getTime(int x, int y);

	public void increaseTime(int x, int y, long deltaTime);
	// --------------------------------------
	// --------------------------------------
	// --------------------------------------

	public void addGraph(Pair<Double, Double>[] graph);

	public void addPoint(Pair<Double, Double> point);
	
	public void addGraphWithTimes(Triple<Double, Double, Long>[] graph);
	
	public void addPointWithTime(Triple<Double, Double, Long> point);
	// --------------------------------------
	// Methods to draw Raster on screen------
	// --------------------------------------
	public void printMatrix();
	
	public void drawMatrix(GraphicMode g, int scalar);	
	// --------------------------------------
	// Methods to get JPanels with Graphics--
	// --------------------------------------
	public JPanel getDrawingPanel(GraphicMode g, int scalar);

	
	
}
