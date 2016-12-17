package outdated;

import gui.GraphicMode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JPanel;

import datastructures.Pair;
import datastructures.Triple;

public class Raster_Proto_2 implements Raster_interface {

	public final double WIDTH;
	public final double HEIGHT;
	public final int ROWS;
	public final int COLUMNS;

	private final double SIZE_COLUMN;
	private final double SIZE_ROW;

	public long NUMBER_OF_POINTS;

	private final int[] raster;
	private final long[] rasterTime;

	private final HashSet<Pair<Double, Double>> POINTS;
	
	// Konstruktoren -----------------------------------------------------
	public Raster_Proto_2(int rows, int columns, double width, double height) {
		this.WIDTH = width;
		this.HEIGHT = height;

		this.ROWS = rows;
		this.COLUMNS = columns;

		this.SIZE_COLUMN = WIDTH / COLUMNS;
		this.SIZE_ROW = HEIGHT / ROWS;

		NUMBER_OF_POINTS = 0;

		raster = new int[COLUMNS * ROWS];
		rasterTime = new long[COLUMNS * ROWS];
		
		POINTS = new HashSet<Pair<Double,Double>>();
	}

	/**
	 * Wie Standartkonstruktor nur wird die Größe von h als gcd(width, height)
	 * festgelegt
	 * 
	 * @param width
	 * @param columns
	 */
	public Raster_Proto_2(int width, int columns) {
		this.WIDTH = width;
		this.COLUMNS = columns;

		// TODO
		this.ROWS = 1;
		this.HEIGHT = 1;

		this.SIZE_COLUMN = WIDTH / COLUMNS;
		this.SIZE_ROW = HEIGHT / ROWS;

		raster = new int[COLUMNS * ROWS];
		rasterTime = new long[COLUMNS * ROWS];
		
		POINTS = new HashSet<Pair<Double,Double>>();
	}

	// Boolean Predikate ---------------------------------------
	public boolean inBound(int x, int y) {
		return (x >= 0 && x < COLUMNS && y >= 0 && y < ROWS);
	}

	// Access Methods ------------------------------------------
	// Anzahl an Punkten in (x,y)
	@Override
	public int getValue(int x, int y) {
		if (inBound(x, y)) {
			return raster[y * COLUMNS + x];
		} else {
			throw new ArrayIndexOutOfBoundsException("X:" + x + " Y:" + y);
		}
	}

	@Override
	public void setValue(int x, int y, int value) {
		if (inBound(x, y)) {
			raster[y * COLUMNS + x] = value;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	public void incrementValue(int x, int y) {
		if (inBound(x, y)) {
			raster[y * COLUMNS + x]++;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	// summierte Zeit in (x, y)
	@Override
	public long getTime(int x, int y) {
		if (inBound(x, y)) {
			return rasterTime[y * COLUMNS + x];
		} else {
			throw new ArrayIndexOutOfBoundsException("X:" + x + " Y:" + y);
		}
	}

	public void increaseTime(int x, int y, long deltaTime) {
		if (inBound(x, y)) {
			rasterTime[y * COLUMNS + x] += deltaTime;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	// Calculating Methods ----------------------------------
	@Override
	public void addGraph(Pair<Double, Double>[] graph) {

		// Stellt Abtastrate auf 1000 pro metrische Einheit
		final int abtastrate = (int) Math.floor(Math.sqrt(Math.pow(HEIGHT, 2)
				+ Math.pow(WIDTH, 2)) * 1000);
		final boolean[] copy_raster = new boolean[(raster.length)];

		if (graph.length <= 0) {
			throw new IllegalArgumentException();
		}

		// inBound Check
		for (int i = 0; i < graph.length; i++) {
			if (!(0 <= graph[i].getFirst() && graph[i].getSecond() < WIDTH
					&& 0 <= graph[i].getSecond() && graph[i].getSecond() < HEIGHT)) {
				throw new IllegalArgumentException(
						"Ein Wert liegt nicht im Bildbereich.");
			}
		}

		// Füge alle Punkte der Punkteliste zu
		for (Pair<Double, Double> point : graph){
			POINTS.add(point);
		}
		
		// Graph ist nur ein Punkt
		if (graph.length == 1) {
			int x, y;
			x = graph[0].getFirst().intValue();
			y = graph[0].getSecond().intValue();
			incrementValue(x, y);
			NUMBER_OF_POINTS++;
			return;
		}

		for (int i = 0; i < (graph.length - 1); i++) {

			double x1 = graph[i].getFirst(), y1 = graph[i].getSecond();
			double x2 = graph[i + 1].getFirst(), y2 = graph[i + 1].getSecond();

			for (int j = 0; j <= abtastrate; j++) {
				int cache_x, cache_y;

				cache_x = (int) ((x1 + j * (x2 - x1) / (double) abtastrate) / (double) SIZE_COLUMN);
				cache_y = (int) ((y1 + j * (y2 - y1) / (double) abtastrate) / (double) SIZE_ROW);

				if (inBound(cache_x, cache_y)) {
					copy_raster[cache_y * COLUMNS + cache_x] = true;
				}
			}
		}

		// Übertrage Werte aus der copy in den eigentlichen Raster
		for (int i = 0; i < raster.length; i++) {
			if (copy_raster[i] == true) {
				raster[i]++;
				NUMBER_OF_POINTS++;
			}
		}

	}

	@Override
	public void addPoint(Pair<Double, Double> point) {
		// TODO Auto-generated method stub
		int column, row;
		double x, y;
		x = point.getFirst();
		y = point.getSecond();

		row = (int) Math.floor(y / this.SIZE_ROW);
		column = (int) Math.floor(x / this.SIZE_COLUMN);

		NUMBER_OF_POINTS++;
		POINTS.add(point);

		this.incrementValue(column, row);
	}

	@Override
	public void addGraphWithTimes(Triple<Double, Double, Long>[] graph) {
		@SuppressWarnings("unchecked")
		Pair<Double, Double>[] g = new Pair[graph.length];
		for (int i = 0; i < graph.length; i++) {
			double x = graph[i].getFirst(), y = graph[i].getSecond();
			long time = graph[i].getThird();

			g[i] = new Pair<Double, Double>(x, y);

			this.increaseTime((int) (x / SIZE_COLUMN), (int) (y / SIZE_ROW),
					time);
		}
		addGraph(g);
	}

	@Override
	public void addPointWithTime(Triple<Double, Double, Long> point) {
		int row, column;
		double x, y;
		long time;

		x = point.getFirst();
		y = point.getSecond();
		time = point.getThird();

		row = (int) Math.floor(y / this.SIZE_ROW);
		column = (int) Math.floor(x / this.SIZE_COLUMN);

		NUMBER_OF_POINTS++;
		POINTS.add(new Pair<Double, Double>(x,y));
		
		this.incrementValue(column, row);
		this.increaseTime(column, row, time);
	}

	// ------------------------------------------------------
	// Drawing Methods --------------------------------------
	// ------------------------------------------------------

	@Override
	public void printMatrix() {
		for (int i = ROWS - 1; i >= 0; i--) {
			for (int j = 0; COLUMNS > j; j++) {

				System.out.print(getValue(j, i) + " ");
			}
			System.out.println();
		}

	}

	@Override
	public void drawMatrix(GraphicMode g, int scalar) {

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.setTitle("Beispiel einer Graphik");

		JPanel d = this.getDrawingPanel(g, scalar);

		frame.getContentPane().add("Center", d);

		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);

	}

	@Override
	public JPanel getDrawingPanel(GraphicMode g, int scalar) {
		if (scalar <= 0 || g == null) {
			throw new IllegalArgumentException(
					"g darf nicht null und scalar nicht <=0 sein");
		}

		switch (g) {
		case PROCENTUAL_COLORED:
			return new DrawingPanelProcentualColored(scalar);
		case PROCENTUAL_NUMERICAL:
			return new DrawingPanelProcentualNumerical(scalar);
		case PROCENTUAL_COMBINED:
			return new DrawingPanelProcentualCombined(scalar);
		default:
			throw new IllegalArgumentException(
					"Dieses Element ist kein (definiertes) Element von GraphicMode.");
		}
	}

	// ------------------------------------------------------
	// Genestete Klassen zur Generierung von Graphiken in ---
	// JPanels ----------------------------------------------
	// ------------------------------------------------------

	/**
	 * Eine Abstrakte Klasse die Grundlagen für alle anderen Panels legt
	 * 
	 * @author tobi
	 *
	 */
	private abstract class DrawingPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		// Private finale Attribute
		private final int SCALAR;
		private final int PANEL_WIDTH;
		private final int PANEL_HEIGHT;

		public final int delta_x;
		public final int delta_y;

		public DrawingPanel(int scalar) {
			SCALAR = scalar;

			delta_x = (int) (Math.round(SIZE_COLUMN * SCALAR));
			delta_y = (int) (Math.round(SIZE_ROW * SCALAR));

			PANEL_WIDTH = delta_x * COLUMNS;
			PANEL_HEIGHT = delta_y * ROWS;

			this.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
			this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

			System.out.println(PANEL_WIDTH + " " + PANEL_HEIGHT);
		}
	}

	/**
	 * Eine internet Subklasse von JPanel die der Darstellung von Rastern als
	 * Graphiken dient
	 * 
	 * @author tobi
	 *
	 */
	public class DrawingPanelProcentualColored extends DrawingPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DrawingPanelProcentualColored(int scalar) {
			super(scalar);
		}

		public void paint(Graphics g) {
			Graphics2D g2D = (Graphics2D) g;

			for (int x = 0; x < COLUMNS; x++) {
				for (int y = 0; y < ROWS; y++) {

					int left_x, lower_y;
					left_x = (delta_x * x);
					lower_y = (delta_y * y);

					// Färbung berechnen
					{
						int red, green, blue;

						red = 0;
						green = 0;
						blue = 255;

						// Rechnet Anteil der in der entsprechenden Zelle
						// stehenden Punkte
						// an der Gesamtzahl der Punkte aus
						double color_number = (double) getValue(x, ROWS - 1 - y);
						color_number = color_number / (double) NUMBER_OF_POINTS;

						// Überträgt diese in ein "blau-auf-weiß" Farbmuster
						blue = (int) Math.round((color_number * 255));

						if (blue <= 0) {
							red = 255;
							green = 255;
							blue = 255;
						} else {
							blue = 255 - blue;
						}
						// setzt die Farbe
						g2D.setColor(new Color(red, green, blue));
					}

					g2D.fillRect(left_x, lower_y, delta_x, delta_y);

				}
			}
		}
	}

	/**
	 * Eine internet Subklasse von JPanel die der Darstellung von Rastern als
	 * Graphiken dient
	 * 
	 * @author tobi
	 *
	 */
	public class DrawingPanelProcentualNumerical extends DrawingPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DrawingPanelProcentualNumerical(int scalar) {
			super(scalar);
		}

		public void paint(Graphics g) {
			Graphics2D g2D = (Graphics2D) g;
			g.setColor(Color.BLACK);

			for (int x = 0; x < COLUMNS; x++) {
				for (int y = 0; y < ROWS; y++) {

					int left_x, lower_y;
					left_x = (delta_x * x);
					lower_y = (delta_y * y);

					// Prozentuale Zahl berechnen
					{
						int strX, strY;
						strY = lower_y
								+ (int) ((delta_y + g2D.getFont().getSize()) / 2);
						strX = left_x + (delta_x - g2D.getFont().getSize()) / 2;

						// Rechnet Anteil der in der entsprechenden Zelle
						// stehenden Punkte
						// an der Gesamtzahl der Punkte aus
						double color_number = (double) getValue(x, ROWS - 1 - y);
						color_number = color_number / (double) NUMBER_OF_POINTS;

						String procentual = (color_number * 100) + "%";

						g2D.getFont().getSize();
						g2D.drawString(procentual, strX, strY);
					}

					g2D.drawRect(left_x, lower_y, delta_x, delta_y);
				}
			}
		}
	}

	/**
	 * Eine internet Subklasse von JPanel die der Darstellung von Rastern als
	 * Graphiken dient
	 * 
	 * @author tobi
	 *
	 */
	public class DrawingPanelProcentualCombined extends DrawingPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DrawingPanelProcentualCombined(int scalar) {
			super(scalar);
			// TODO Auto-generated constructor stub
		}

		public void paint(Graphics g) {
			Graphics2D g2D = (Graphics2D) g;

			for (int x = 0; x < COLUMNS; x++) {
				for (int y = 0; y < ROWS; y++) {

					int left_x, lower_y;
					left_x = (delta_x * x);
					lower_y = (delta_y * y);

					// Färbung berechnen
					{
						int red, green, blue;

						red = 0;
						green = 0;
						blue = 255;

						// Rechnet Anteil der in der entsprechenden Zelle
						// stehenden Punkte
						// an der Gesamtzahl der Punkte aus
						double color_number = (double) getValue(x, ROWS - 1 - y);
						color_number = color_number / (double) NUMBER_OF_POINTS;

						// Überträgt diese in ein "blau-auf-weiß" Farbmuster
						blue = (int) Math.round((color_number * 255));

						if (blue <= 0) {
							red = 255;
							green = 255;
							blue = 255;
						} else {
							blue = 255 - blue;
						}
						// setzt die Farbe
						g2D.setColor(new Color(red, green, blue));
					}

					g2D.fillRect(left_x, lower_y, delta_x, delta_y);
				}
			}

			g2D.setColor(Color.BLACK);
			for (int x = 0; x < COLUMNS; x++) {
				for (int y = 0; y < ROWS; y++) {

					int left_x, lower_y;
					left_x = (delta_x * x);
					lower_y = (delta_y * y);

					// Prozentuale Zahl berechnen
					{
						int strX, strY;
						strY = lower_y
								+ (int) ((delta_y + g2D.getFont().getSize()) / 2);
						strX = left_x + (delta_x - g2D.getFont().getSize()) / 2;

						// Rechnet Anteil der in der entsprechenden Zelle
						// stehenden Punkte
						// an der Gesamtzahl der Punkte aus
						double color_number = (double) getValue(x, ROWS - 1 - y);
						color_number = color_number / (double) NUMBER_OF_POINTS;

						String procentual;
						procentual = (int) Math.round(color_number * 100) + "%";

						g2D.getFont().getSize();
						g2D.drawString(procentual, strX, strY);
					}

					g2D.drawRect(left_x, lower_y, delta_x, delta_y);
				}
			}
		}
	}
}
