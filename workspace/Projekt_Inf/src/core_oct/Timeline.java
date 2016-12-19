package core_oct;

import java.util.HashSet;

import core.Point;

public class Timeline {
	private final OctreeNode root;

	public Timeline() {
		//root = new OctreeNode(0, 0, 100, 100);
		root = new OctreeNode(0, 0, 0, 0, 0, 0, 0);

	}

	public class OctreeNode {

		private OctreeNode NWB, NEB, SWB, SEB, NWE, NEE, SWE, SEE;

		private final HashSet<Point> POINTS;

		// Variablen -----------------------------------------------------------
		/** Bezeichnet jeweils die linke bzw untere Grenze des Bildbereichs **/
		public final int LEFT_BOUND, LOWER_BOUND;
		/** Startzeitpunkt des Bildbereichs **/
		public final int STARTTIME;
		/** linke Grenze + Weite, bzw untere Grenze + Höhe des Bildbereichs **/
		public final int RIGHT_BOUND, UPPER_BOUND;
		/** Erster Zeitpunkt der nicht mehr im Bildbereich liegt **/
		public final int ENDTIME;
		/** Breite und Höhe des Bildbereichs **/
		public final int WIDTH, HEIGHT;
		/** zeitliche Länge des Bildbereichs **/
		public final int LENGTH;

		/** Bezeichnet wie viele Elemente ein Knoten maximal enthalten darf **/
		public final int SIZE;

		public OctreeNode(int leftBound, int lowerBound, int starttime,
				int width, int height, int length, int maxSize) {
			LEFT_BOUND = leftBound;
			LOWER_BOUND = lowerBound;
			STARTTIME = starttime;

			WIDTH = width;
			HEIGHT = height;
			LENGTH = length;

			UPPER_BOUND = LOWER_BOUND + HEIGHT;
			RIGHT_BOUND = LEFT_BOUND + WIDTH;
			ENDTIME = STARTTIME + LENGTH;

			SIZE = maxSize;

			POINTS = new HashSet<Point>();
		}

		public void addPoint(Point p) {
			POINTS.add(p);
			if (NWB == null) {
				if (POINTS.size() >= SIZE) {
					branch();
				}
			} else {

			}

		}

		private void branch() {
			if (NWB == null) {
				int middleX = WIDTH / 2;
				int middleY = HEIGHT / 2;
				int middleTime = LENGTH / 2;

				NWB = new OctreeNode(LEFT_BOUND, LOWER_BOUND + middleY,
						STARTTIME, middleX, middleY, middleTime, SIZE);
				// TODO Hier weiter machen
				NEB = new OctreeNode(LEFT_BOUND, LOWER_BOUND + middleY,
						STARTTIME, middleX, middleY, middleTime, SIZE);
				SWB = new OctreeNode(LEFT_BOUND, LOWER_BOUND + middleY,
						STARTTIME, middleX, middleY, middleTime, SIZE);
				SEB = new OctreeNode(LEFT_BOUND, LOWER_BOUND + middleY,
						STARTTIME, middleX, middleY, middleTime, SIZE);

				NWE = new OctreeNode(LEFT_BOUND, LOWER_BOUND + middleY,
						STARTTIME, middleX, middleY, middleTime, SIZE);
				NEE = new OctreeNode(LEFT_BOUND, LOWER_BOUND + middleY,
						STARTTIME, middleX, middleY, middleTime, SIZE);
				SWE = new OctreeNode(LEFT_BOUND, LOWER_BOUND + middleY,
						STARTTIME, middleX, middleY, middleTime, SIZE);
				SEE = new OctreeNode(LEFT_BOUND, LOWER_BOUND + middleY,
						STARTTIME, middleX, middleY, middleTime, SIZE);
			}

		}
	}
}
