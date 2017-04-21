package model.graph.data;

import java.util.ArrayList;
import java.util.List;

public class GraphData {
	private final List<NodeData> allNodeData;
	
	public GraphData(List<NodeData> allNodeData){
		this.allNodeData = allNodeData;
	}
	
	// ---------------------------------------------------
	// Getter und Setter
	// ---------------------------------------------------
	public List<NodeData> getAllNodeData(){
		return new ArrayList<NodeData>(allNodeData);
	}
}
