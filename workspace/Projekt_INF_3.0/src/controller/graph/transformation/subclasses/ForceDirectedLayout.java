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
 *
 * @author ts
 */
public class ForceDirectedLayout extends Transformation {

	public static double f_a(double x, double k) {
		return Math.pow(x, 2) / k;
	}

	public static double f_r(double x, double k) {
		return Math.pow(k, 2) / x;
	}

	public static double cool(double t) {
		return Math.pow(t, 0.99);
	}

	@Override
	public void applyOn(Graph g) {
		if (g.getAllNodes().isEmpty() || g == null) return;
		
		final List<Node> nodeList = g.getAllNodes();
		final List<Edge> edgeList = g.getAllEdges();

		final Map<Node, Double> xDisp = new HashMap<Node, Double>();
		final Map<Node, Double> yDisp = new HashMap<Node, Double>();

		final int W; // width of pane
		final int H; // length of pane
		final int area;

		final int numOfNodes = nodeList.size();
		final int iterations = 100;

		// Bereite Knoten vor
		{
			// Setze Knoten auf Identität als Ausgangslage
			new IdentityTransformation().applyOn(g);

			int maxX = 0;
			int maxY = 0;
			for (Node node : g.getAllNodes()) {
				maxX = (node.getxCenter() > maxX) ? node.getxCenter() : maxX;
				maxY = (node.getyCenter() > maxY) ? node.getyCenter() : maxY;
			}

			W = maxX;
			H = maxY;

			area = W * H;
		}

		double t = 0.1 * W;

		final double k = Math.sqrt(area / numOfNodes);

		for (Node node : nodeList) {
			xDisp.put(node, 0.0);
			yDisp.put(node, 0.0);
		}

		for (int i = 1; i <= iterations; i++) {

			// calculate repulsive forces
			for (Node v : nodeList) {
				// vertex has position and disposition attributes for x and y
				xDisp.put(v, 0.0);
				yDisp.put(v, 0.0);

				for (Node u : nodeList) {
					if (v != u) {
						// delta is short hand for difference
						// vector between two positions of the two vertices
						final double deltaX = (double) (v.getxCenter() - u.getxCenter());
						final double deltaY = (double) (v.getyCenter() - u.getyCenter());
						final double deltaLength = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

						final double factor = f_r(deltaLength, k);
						xDisp.put(v, (xDisp.get(v) + (deltaX / deltaLength) * factor));
						yDisp.put(v, (yDisp.get(v) + (deltaY / deltaLength) * factor));
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
				xDisp.put(parent, (xDisp.get(parent) - (deltaX / deltaLength) * factor));
				yDisp.put(parent, (yDisp.get(parent) - (deltaY / deltaLength) * factor));
				xDisp.put(child, (xDisp.get(child) + (deltaX / deltaLength) * factor));
				yDisp.put(child, (yDisp.get(child) + (deltaY / deltaLength) * factor));
			}

			// limit the maximum displacement to the temperature t
			// and then prevent from being displaced outside frame
			for (Node v : nodeList) {
				final double deltaLength = Math.sqrt(Math.pow(xDisp.get(v), 2) + Math.pow(yDisp.get(v), 2));

				final double factor = Math.min(deltaLength, t);
				v.setxCenter((int) (v.getxCenter() + (xDisp.get(v) / deltaLength) * factor));
				v.setyCenter((int) (v.getyCenter() + (yDisp.get(v) / deltaLength) * factor));

				v.setxCenter(Math.min(W / 2, Math.max(-W / 2, v.getxCenter())));
				v.setyCenter(Math.min(H / 2, Math.max(-H / 2, v.getyCenter())));
			}

			// reduce the tempereature as the layout approaches a better
			// configuration
			t = cool(t);
		}
	}

}
