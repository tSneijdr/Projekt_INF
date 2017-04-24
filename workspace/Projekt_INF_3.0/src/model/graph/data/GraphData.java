package model.graph.data;

import java.util.ArrayList;
import java.util.List;

import utils.ranges.Range2D;

public class GraphData {
	private final List<NodeData> allNodeData;
	private final Range2D range;
	
	public GraphData(List<NodeData> allNodeData, Range2D range){
		this.allNodeData = allNodeData;
		this.range = range;
	}
	
	// ---------------------------------------------------
	// Getter und Setter
	// ---------------------------------------------------
	public List<NodeData> getAllNodeData(){
		return new ArrayList<NodeData>(allNodeData);
	}
	
	public Range2D getRange(){
		return range;
	}
}
