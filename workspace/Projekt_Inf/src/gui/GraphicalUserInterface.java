package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

import datastructures.Triple;

public class GraphicalUserInterface {

	/**
	 * 
	 * @param g
	 * @param numberColumns
	 *            die Anzahl der Spalten
	 * @param numberRows
	 *            die Anzahl der Reihen
	 * @param sizeColumn
	 *            die Größe einer Spalte
	 * @param sizeRow
	 *            die Größe einer Reihe
	 * @param triples
	 *            eine Liste mit Tripeln, die diskretisierte Rechtecke durch
	 *            ihre (x, y, n) Daten beschreibt (n ist hierbei die Anzahl der
	 *            Punkte in diesem Rechteck)
	 */
	public static void drawColorAndProcent(Graphics g, int numberColumns,
			int numberRows, int sizeColumn, int sizeRow,
			LinkedList<Triple<Integer, Integer, Integer>> triples) {

		int total = 0;
		Iterator<Triple<Integer, Integer, Integer>> it = triples.iterator();
		while (it.hasNext()) {
			total += it.next().getThird();
		}

		int minValue = 0;
		int maxValue = 0;
		double avg = 0;

		int width = sizeColumn * numberColumns;
		int height = sizeRow * numberRows;

		{ // Ermittle min, max und avg der Verteilung
			for (Triple<Integer, Integer, Integer> tri : triples) {
				minValue = (tri.getThird() < minValue) ? tri.getThird()
						: minValue;
				maxValue = (tri.getThird() > maxValue) ? tri.getThird()
						: maxValue;

				avg += (double) tri.getThird();
			}
			avg = avg / triples.size();
		}

		{// Hintergrund des Panels weiß färben
			g.setColor(Color.WHITE);

			g.fillRect(0, 0, width, height);
		}

		{ // Eigentliches Zeichnen
			for (Triple<Integer, Integer, Integer> tri : triples) {
				int x = tri.getFirst() * sizeColumn;
				int y = width - sizeRow - tri.getSecond() * sizeRow;

				{ // Hintergrund der Kacheln
					int red = 255;
					int green = 255;
					int blue = 127;

					g.setColor(new Color(red, green, blue));

					g.fillRect(x, y, sizeColumn, sizeRow);
				}
				{ // Vordergrund
					// Rahmen
					g.setColor(Color.BLACK);
					g.drawRect(x, y, sizeColumn, sizeRow);

					// Schrift
					int strX, strY;
					int fontSize = g.getFont().getSize();

					String str = Double.toString((double) tri.getThird()
							/ (double) total * 100.0);
					if (str.length() > 6) {
						str = str.substring(0, 5);
					}
					str += "%";

					strY = y + (int) ((sizeColumn + fontSize) / 2);
					strX = x + (sizeColumn - (str.length() - 2) * fontSize) / 2;

					g.drawString(str, strX, strY);
				}
			}
		}

	}
}
