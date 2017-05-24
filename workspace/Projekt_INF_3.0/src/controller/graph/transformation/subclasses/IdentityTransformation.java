package controller.graph.transformation.subclasses;

import controller.graph.transformation.Transformation;
import model.graph.graph.Graph;
import model.graph.graph.Node;

public class IdentityTransformation extends Transformation {

	public void applyOn(Graph g) {

		int radius = 0;
		for (Node node : g.getAllNodes()) {
			radius = (node.getRadius() > radius) ? node.getRadius() : radius;
		}

		for (Node node : g.getAllNodes()) {
			int centerX = node.getData().getOriginalColumn() * radius * 4;
			int centerY = node.getData().getOriginalRow() * radius * 4;

			node.setxCenter(centerX);
			node.setyCenter(centerY);
		}
	}

}
