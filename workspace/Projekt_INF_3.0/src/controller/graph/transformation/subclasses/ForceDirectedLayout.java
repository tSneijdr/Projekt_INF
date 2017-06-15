/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.graph.transformation.subclasses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import controller.graph.transformation.Transformation;
import model.graph.graph.Edge;
import model.graph.graph.Graph;
import model.graph.graph.Node;

/**
 * Repräsentiert ein ForceDirected Layout
 * 
 * @author tobias schneider, tobias meisel
 */
public class ForceDirectedLayout extends Transformation {

	// -------------------------------------------------
	// Hilfsmethoden
	// -------------------------------------------------
	public static double f_a(double x, double k) {
		return Math.pow(x, 2) / k;
	}

	public static double f_r(double x, double k) {
		return Math.pow(k, 2) / x;
	}

	public static double cool(double t) {
		return Math.pow(t, 0.99);
	}

	/**
	 * Siehe Klasse Transformation
	 */
	@Override
	public void applyOn(Graph g) {
		if (g.getAllNodes().isEmpty() || g == null)
			return;

		new IdentityTransformation().applyOn(g);

		final List<Node> nodeList = g.getAllNodes();
		final List<Edge> edgeList = g.getAllEdges();

		final Map<Node, Vector> disp = new HashMap<Node, Vector>();

		final int W = g.getData().getRange().WIDTH; // width of pane
		final int H = g.getData().getRange().HEIGHT; // length of pane
		final int area = W * H;
		final int numOfNodes = nodeList.size();
		final double k = Math.sqrt(area / numOfNodes);

		System.out.println("         ForceDirected: W:" + W + ", H:" + H + ", numNodes: " + numOfNodes);

		{
			double x = 0.0;
			double y = 0.0;
			for (Node n : nodeList) {
				x += n.getxCenter();
				y += n.getyCenter();
			}
			x = x / nodeList.size();
			y = y / nodeList.size();
			System.out.println("         ForceDirected: avg x: " + x + ", avg y: " + y);
		}

		double t = 1000;
		final int iterations = 1000;
		for (int i = 0; i < iterations; i++) {
			disp.clear();

			for (Node node : nodeList) {
				disp.put(node, new Vector(0.0, 0.0));
			}

			// calculate repulsive forces
			for (Node v : nodeList) {
				for (Node u : nodeList) {
					if (v != u) {
						// delta is short hand for difference
						// vector between two positions of the two vertices
						final double deltaX = (double) (v.getxCenter() - u.getxCenter());
						final double deltaY = (double) (v.getyCenter() - u.getyCenter());
						final double deltaLength = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

						final double factor = f_r(deltaLength, k);
						disp.get(v).add(factor * deltaX / deltaLength, factor * deltaY / deltaLength);
					}
				}
			}

			// calculate attractive forces
			for (Edge e : edgeList) {
				// each edge is an ordered pair of vertices v and u
				final Node parent = e.getParent();
				final Node child = e.getChild();

				final double deltaX = parent.getxCenter() - child.getxCenter();
				final double deltaY = parent.getyCenter() - child.getyCenter();
				final double deltaLength = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

				final double factor = f_a(deltaLength, k);
				disp.get(parent).add(-(deltaX / deltaLength) * factor, -(deltaY / deltaLength) * factor);

				disp.get(child).add((deltaX / deltaLength) * factor, (deltaY / deltaLength) * factor);
			}

			// limit the maximum displacement to the temperature t
			// and then prevent from being displaced outside frame
			for (Node v : nodeList) {
				final double deltaLength = disp.get(v).getLength();

				final double factor = Math.min(deltaLength, t);
				v.setxCenter((int) (v.getxCenter() + (disp.get(v).getX() / deltaLength) * factor));
				v.setyCenter((int) (v.getyCenter() + (disp.get(v).getY() / deltaLength) * factor));

				v.setxCenter(Math.min(W / 2, Math.max(-W / 2, v.getxCenter())));
				v.setyCenter(Math.min(H / 2, Math.max(-H / 2, v.getyCenter())));
			}

			// reduce the tempereature as the layout approaches a better
			// configuration
			t = cool(t);
		}
	}

	/**
	 * Hilfsklasse, die einen Vektor repräsentiert
	 * @author tobi
	 *
	 */
	class Vector {
		private double x = 0;
		private double y = 0;

		public Vector(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public void add(double x, double y) {
			this.x += x;
			this.y += y;
		}

		public double getLength() {
			return Math.sqrt(x * x + y * y);
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

	}

}
