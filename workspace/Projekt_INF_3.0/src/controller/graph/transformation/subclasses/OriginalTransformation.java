package controller.graph.transformation.subclasses;

import controller.graph.transformation.Transformation;
import model.graph.data.GraphData;
import model.graph.graph.Graph;
import model.graph.graph.Node;

/**
 * Repräsentiert eine Transformation die einen Graphen auf sein in der Synthese
 * erstelltes Layout abbildet, dies stellt eine auf die Gesamtgröße des
 * ursprünglichen Bildes skalierte version der IdentityTransformation dar
 * 
 * @author tobias schneider, tobias meisel
 */
public class OriginalTransformation extends Transformation {

	public void applyOn(Graph g) {

		GraphData data = g.getData();

		final int width = data.getRange().WIDTH;
		final int height = data.getRange().HEIGHT;

		final int maxColumn = data.getNumColumns();
		final int maxRow = data.getNumRows();

		double columnSize = (double) width / (double) maxColumn;
		double rowSize = (double) height / (double) maxRow;

		for (Node node : g.getAllNodes()) {
			int centerX = node.getData().getOriginalColumn() * (int) columnSize + (int) (columnSize / 2);
			int centerY = node.getData().getOriginalRow() * (int) rowSize + (int) (rowSize / 2);

			node.setxCenter(centerX);
			node.setyCenter(centerY);
		}
	}

}
