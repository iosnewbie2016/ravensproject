package ravensproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SemanticNetwork {
	RelationDiagram figure1RelationDiagram;
	RelationDiagram figure2RelationDiagram;
	RelationDiagram figure3RelationDiagram;

	List<RavensObject> figure1shapes = new ArrayList<RavensObject>();
	List<RavensObject> figure2shapes = new ArrayList<RavensObject>();
	
	List<List<String>> transformations = new ArrayList<List<String>>();
	List<List<String>> finalCharacteristic = new ArrayList<List<String>>();
	
	List<Integer> weightOfShapeTransformation = new ArrayList<Integer>();
	
	public HashMap<String,RavensObject> figure1shapesHashMap = new HashMap<String, RavensObject>();
	public HashMap<String,RavensObject> figure2shapesHashMap = new HashMap<String, RavensObject>();;
	
	Integer totalWeight = 0;
	
	public String problemType;
	
	public SemanticNetwork(RelationDiagram figure1RelationDiagram, RelationDiagram figure2RelationDiagram, String problemType) {
		this.figure1RelationDiagram = figure1RelationDiagram;
		this.figure2RelationDiagram = figure2RelationDiagram;
		this.problemType = problemType;
		
		
		
	}
	
	
	public void updateWeights() {
		for (int i = 0; i < figure1shapes.size(); i ++) {
			Integer tempWeightShape = 0;
			for (int j = 0; j < transformations.get(i).size(); j++) {
				switch (transformations.get(i).get(j)) {
				case ("shape"):
					tempWeightShape += 7;
				break;
				case ("size"):
					tempWeightShape += 6;
				break;
				case ("angle"):
					tempWeightShape += 4;
				break;
				case ("alignment"):
					tempWeightShape += 4;
				break;
				case ("fill"):
					tempWeightShape += 1;
				break;
				/*case ("shape"): //Reserved for Reflected
					tempWeightShape += 7;
				break;
				case ("shape"): //Reserved for Deleted
					tempWeightShape += 7;
				break;*/
				}
			}
			weightOfShapeTransformation.add(tempWeightShape);
			totalWeight+=tempWeightShape;
		}
	}
}
