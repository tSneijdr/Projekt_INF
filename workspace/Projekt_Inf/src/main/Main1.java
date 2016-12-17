package main;

import gui.GraphicalUserInterface;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import core.Point;
import core_array.Raster;
import core_array.Timeline;
import datastructures.Triple;

public class Main1 {
	public static void main(String[] args) {
		Timeline t = new Timeline(0, 0, 0, 2000, 2000, 300000, 10, 10, 100);

		Point p2 = new Point(0, 0, 0, 0);
		
		Raster m;
		
		
		try {
			Point p = csvToPointList(
					"/home/tobi/Dokumente/Projekte/workspace/Projekt_Inf/data2/EyeTracking_dataset/data/TRA_1/Proband12.tsv",
					"	");
			Point startPoint = p;

			int i = 1;
			System.out.println(p.toString());
			while (p.getNextNode() != null) {
				i++;
				p = p.getNextNode();
			}
			System.out.println(i);
			System.out.println(p.toString());
			t.addPoints(startPoint);

			System.out.println();
			//System.out.println(new Timeline(t.getPoints()).toString());
			//System.out.println(t.copy().toString());
			System.out.println();
			System.out.println(t.getNumberOfPoints());

			{
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				frame.setTitle("Beispiel einer Graphik");

				LinkedList<Triple<Integer, Integer, Integer>> triples = new LinkedList<Triple<Integer, Integer, Integer>>();
				triples.add(new Triple<Integer, Integer, Integer>(0, 0, 2));
				triples.add(new Triple<Integer, Integer, Integer>(1,0,3));
				triples.add(new Triple<Integer, Integer, Integer>(1,1,7));
				triples.add(new Triple<Integer, Integer, Integer>(0,1,43));
				
				JPanel d = new JPanel() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public void paint(Graphics g) {
						GraphicalUserInterface.drawColorAndProcent(g, 10, 10, 70, 70, triples);
					}
				};

				d.setPreferredSize(new Dimension(1000, 1000));
				frame.getContentPane().add("Center", d);

				frame.pack();
				//frame.setResizable(false);
				frame.setVisible(true);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Point csvToPointList(String csvName, String seperator)
			throws Exception {
		File f = null;
		Scanner s = null;

		try {
			f = new File(csvName);
			s = new Scanner(f);

			String[] encodedPoint;

			Point firstPoint = null;
			Point latestPoint = null;
			{

				while (s.hasNextLine()) {
					encodedPoint = s.nextLine().split(seperator);

					if (encodedPoint.length == 5) {
						String name = encodedPoint[0];
						double x = Double.parseDouble(encodedPoint[2]);
						double y = Double.parseDouble(encodedPoint[3]);
						double duration = Double.parseDouble(encodedPoint[4]);
						double timepoint = Double.parseDouble(encodedPoint[1]);

						Point p = new Point(name, x, y, duration, timepoint);

						if (firstPoint == null) {
							firstPoint = p;
						}
						if (latestPoint != null) {
							latestPoint.setNextNode(p);
						}

						p.setPreviousNode(latestPoint);
						latestPoint = p;
					} else {
						String ep = "";
						for (String str : encodedPoint) {
							ep += str + " ";
						}
						System.out.println("Fehlerhafter Datensatz: " + ep);
					}
				}
			}
			s.close();

			return firstPoint;
		} catch (Exception e) {
			if (s != null) {
				s.close();
			}
			throw e;
		}
	}
}
