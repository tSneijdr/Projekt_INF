package controller.graph.transformation.subclasses;

import controller.graph.transformation.Transformation;
import model.graph.graph.Graph;
import model.graph.graph.Node;

public class OriginalTransformation extends Transformation {

	public Graph applyOn(Graph g) {

		int maxRow = 0;
		int maxColumn = 0;
		for (Node node : g.getAllNodes()) {
			final int row = node.getData().getOriginalRow();
			final int column = node.getData().getOriginalColumn();
			
			maxRow = (row > maxRow) ? row: maxRow;
			maxColumn  = (column > maxColumn) ? column : maxColumn;
		}
		
		final int width = g.getData().getRange().WIDTH;
		final int height = g.getData().getRange().HEIGHT;
		
		double columnSize = (double) width / (double) maxColumn;
		double rowSize = (double) height / (double) maxRow;
		
		for (Node node : g.getAllNodes()){
			int centerX = node.getData().getOriginalColumn() * (int) columnSize + (int)(columnSize/2);
			int centerY = node.getData().getOriginalRow() * (int) rowSize + (int)(rowSize/2);
			
			node.setxCenter(centerX);
			node.setyCenter(centerY);
		}

		return g;
	}

}
