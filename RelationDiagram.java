package ravensproject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RelationDiagram {
	
	public RavensFigure ravensFigure;
	public Integer numShapes;
	public String relations[][];
	
	
	// FRAMES
	public HashMap<String,Frame> frames;
	
	List<String> relationSentences = new ArrayList<String>();
	public HashMap<String,String> relationSentencesHashMap = new HashMap<String, String>();
	
	////////WORK TOGETHER TO CONSTRUCT SENTENCES
	List<String> relationShape1 = new ArrayList<String>();
	List<String> relationShape2 = new ArrayList<String>();
	List<String> relationType = new ArrayList<String>();
	////////END
	
	HashMap<String,RavensObject> objects;
	List<RavensObject> ravensObjectArray = new ArrayList<RavensObject>();
	
	List<Integer> numRelationShips = new ArrayList<Integer>();
	List<List <String>> specificRelationships = new ArrayList<List<String>>();
	
	
	List<String> objectArray = new ArrayList<String>();
	
	
	List<String> shapeArray = new ArrayList<String>();
	List<String> sizeArray = new ArrayList<String>();
	List<String> fillArray = new ArrayList<String>();
	List<String> angleArray = new ArrayList<String>();
	List<String> alignmentArray = new ArrayList<String>();
	
	public RelationDiagram() {
		
	}
	
	/*public RelationDiagram(RavensFigure figure) {
		this.numShapes = figure.getObjects().size();
		this.relations = new String[this.numShapes][this.numShapes];
		this.objects = figure.getObjects();
		
		for(String objectName : figure.getObjects().keySet()) {

			RavensObject thisObject = figure.getObjects().get(objectName);
			this.objectArray.add(thisObject.getName());
			this.shapeArray.add(thisObject.getAttributes().get("shape"));
			this.sizeArray.add(thisObject.getAttributes().get("size"));
			this.fillArray.add(thisObject.getAttributes().get("fill"));
			this.angleArray.add(thisObject.getAttributes().get("angle"));
			this.alignmentArray.add(thisObject.getAttributes().get("alignment"));
			
			

		}
		
		for(String objectName : figure.getObjects().keySet()) {

			RavensObject thisObject = figure.getObjects().get(objectName);
			
			for(String attributeName : thisObject.getAttributes().keySet()) {

				String attributeValue = thisObject.getAttributes().get(attributeName);
				
				if ((attributeName.equals("shape") == false)
						& (attributeName.equals("angle") == false)
						& (attributeName.equals("fill") == false)
						& (attributeName.equals("size") == false)
						& (attributeName.equals("alignment") == false)) {
					
					
					if (attributeValue.length() > 1) {
						attributeValue = attributeValue.replace(",", "");
					}

					
					for (int i = 0; i < attributeValue.length(); i++){
					    Character c = attributeValue.charAt(i);
					    String cString = c.toString();
					    
					    Integer currObjectIndex = this.objectArray.indexOf(thisObject.getName());
						Integer relationObjectIndex = this.objectArray.indexOf(cString);
						
						this.relations[currObjectIndex][relationObjectIndex] = attributeName;
					}
					
				}
								
			}
					
			
		}
		
		System.out.println("Figure: " + figure.getName() + " Objects: " + this.objectArray);
		
		for (int i = 0; i < numShapes; i++) {
			for (int j = 0; j < numShapes; j++) {
				if (i != j & this.relations[i][j] != null)  {
					System.out.println(this.objectArray.get(i) + " (" + this.shapeArray.get(i) + ") is " + this.relations[i][j] + " " + this.objectArray.get(j) + " (" + this.shapeArray.get(j) + ")");
					this.relationSentences.add(this.shapeArray.get(i) + this.relations[i][j] + this.shapeArray.get(j));
				}
			}
		}
		
		
			
	} */
	
	public void buildDiagram(RavensFigure figure) {
		this.ravensFigure = figure;
		
		this.numShapes = figure.getObjects().size();
		this.relations = new String[this.numShapes][this.numShapes];
		this.objects = figure.getObjects();
		
		
		this.frames = new HashMap<String, Frame>(); // FRAME
		
		for (RavensObject value : this.objects.values()) {
			ravensObjectArray.add(value);
		}

		
		for(String objectName : figure.getObjects().keySet()) {

			RavensObject thisObject = figure.getObjects().get(objectName);
			this.objectArray.add(thisObject.getName());
			
			
			Frame tempFrame = new Frame(thisObject.getName());  //FRAME
			
			
			
			if (thisObject.getAttributes().get("shape") != null) {
				tempFrame.shape = thisObject.getAttributes().get("shape");  //FRAME
				this.shapeArray.add(thisObject.getAttributes().get("shape"));
			}
			if (thisObject.getAttributes().get("size") != null) {
				tempFrame.size = thisObject.getAttributes().get("size");  //FRAME
				this.sizeArray.add(thisObject.getAttributes().get("size"));
			}
			if (thisObject.getAttributes().get("fill") != null) {
				tempFrame.fill = thisObject.getAttributes().get("fill");  //FRAME
				this.fillArray.add(thisObject.getAttributes().get("fill"));
			}
			if (thisObject.getAttributes().get("angle") != null) {
				tempFrame.angle = Integer.parseInt(thisObject.getAttributes().get("angle"));  //FRAME
				this.angleArray.add(thisObject.getAttributes().get("angle"));
			}
			if (thisObject.getAttributes().get("alignment") != null) {
				tempFrame.alignment = thisObject.getAttributes().get("alignment");  //FRAME
				this.alignmentArray.add(thisObject.getAttributes().get("alignment"));
			}
			
			
			this.frames.put(thisObject.getName(), tempFrame);

		}
		
		for(String objectName : figure.getObjects().keySet()) {

			RavensObject thisObject = figure.getObjects().get(objectName);
			
			Integer numRelations = 0;
			List<String> specRelations = new ArrayList<String>();
			for(String attributeName : thisObject.getAttributes().keySet()) {

				String attributeValue = thisObject.getAttributes().get(attributeName);
				
				if ((attributeName.equals("shape") == false)
						& (attributeName.equals("angle") == false)
						& (attributeName.equals("fill") == false)
						& (attributeName.equals("size") == false)
						& (attributeName.equals("alignment") == false)) {
					
					
					if (attributeValue.length() > 1) {
						attributeValue = attributeValue.replace(",", "");
					}

					
					for (int i = 0; i < attributeValue.length(); i++){
					    Character c = attributeValue.charAt(i);
					    String cString = c.toString();
					    
					    Integer currObjectIndex = this.objectArray.indexOf(thisObject.getName());
						Integer relationObjectIndex = this.objectArray.indexOf(cString);
						
						this.relations[currObjectIndex][relationObjectIndex] = attributeName;
						
						
						//FRAMES
						if (attributeName.equals("inside")) {
							this.frames.get(thisObject.getName()).inside.put(cString, this.frames.get(cString));
						}
						
						
						numRelations+=1;
						specRelations.add(attributeName);
						
						
					}
					this.numRelationShips.add(numRelations);
					this.specificRelationships.add(specRelations);
					
					
				}
								
			}
					
			
		}
		
		//System.out.println("Figure: " + figure.getName() + " Objects: " + this.objectArray);
		
		for (int i = 0; i < numShapes; i++) {
			for (int j = 0; j < numShapes; j++) {
				if (i != j & this.relations[i][j] != null)  {
					//System.out.println(this.objectArray.get(i) + " (" + this.shapeArray.get(i) + ") is " + this.relations[i][j] + " " + this.objectArray.get(j) + " (" + this.shapeArray.get(j) + ")");
					this.relationSentences.add(this.shapeArray.get(i) + this.relations[i][j] + this.shapeArray.get(j));
					this.relationShape1.add(this.shapeArray.get(i));
					this.relationShape2.add(this.shapeArray.get(j));
					this.relationType.add(this.relations[i][j]);
					
					this.relationSentencesHashMap.put(this.objectArray.get(i), this.shapeArray.get(i) + this.relations[i][j] + this.shapeArray.get(j));
					
				}
			}
		}
		
			
		
	}
	
	
	
	
	public void buildDiagram(HashMap<String,RavensObject> objects) {
		
		this.numShapes = objects.size();
		this.relations = new String[this.numShapes][this.numShapes];
		this.objects = objects;
		
		for (RavensObject value : this.objects.values()) {
			ravensObjectArray.add(value);
		}

		
		for(String objectName : objects.keySet()) {

			RavensObject thisObject = objects.get(objectName);
			this.objectArray.add(thisObject.getName());
			if (thisObject.getAttributes().get("shape") != null) this.shapeArray.add(thisObject.getAttributes().get("shape"));
			if (thisObject.getAttributes().get("size") != null) this.sizeArray.add(thisObject.getAttributes().get("size"));
			if (thisObject.getAttributes().get("fill") != null) this.fillArray.add(thisObject.getAttributes().get("fill"));
			if (thisObject.getAttributes().get("angle") != null) this.angleArray.add(thisObject.getAttributes().get("angle"));
			if (thisObject.getAttributes().get("alignment") != null) this.alignmentArray.add(thisObject.getAttributes().get("alignment"));

		}
		
		for(String objectName : objects.keySet()) {

			RavensObject thisObject = objects.get(objectName);
			
			Integer numRelations = 0;
			List<String> specRelations = new ArrayList<String>();
			for(String attributeName : thisObject.getAttributes().keySet()) {

				String attributeValue = thisObject.getAttributes().get(attributeName);
				
				if ((attributeName.equals("shape") == false)
						& (attributeName.equals("angle") == false)
						& (attributeName.equals("fill") == false)
						& (attributeName.equals("size") == false)
						& (attributeName.equals("alignment") == false)) {
					
					
					if (attributeValue.length() > 1) {
						attributeValue = attributeValue.replace(",", "");
					}

					
					for (int i = 0; i < attributeValue.length(); i++){
					    
						numRelations+=1;
						specRelations.add(attributeName);
						
						
					}
					this.numRelationShips.add(numRelations);
					this.specificRelationships.add(specRelations);
						
						
				}
					
			}
								
		}
					
			
		
		
		//System.out.println("Figure: " + figure.getName() + " Objects: " + this.objectArray);
		
		for (int i = 0; i < numShapes; i++) {
			for (int j = 0; j < numShapes; j++) {
				if (i != j & this.relations[i][j] != null)  {
					//System.out.println(this.objectArray.get(i) + " (" + this.shapeArray.get(i) + ") is " + this.relations[i][j] + " " + this.objectArray.get(j) + " (" + this.shapeArray.get(j) + ")");
					this.relationSentences.add(this.shapeArray.get(i) + this.relations[i][j] + this.shapeArray.get(j));
					this.relationShape1.add(this.shapeArray.get(i));
					this.relationShape2.add(this.shapeArray.get(j));
					this.relationType.add(this.relations[i][j]);
					
					this.relationSentencesHashMap.put(this.objectArray.get(i), this.shapeArray.get(i) + this.relations[i][j] + this.shapeArray.get(j));
					
				}
			}
		}
		
			
		
	}
	
	
	
	
	public Integer compare(RelationDiagram diagramComparedTo) {
		this.numShapes = this.objects.size();
		diagramComparedTo.numShapes = diagramComparedTo.objects.size();
		
		RelationDiagram relation1 = new RelationDiagram();
		relation1.buildDiagram(this.objects);
		RelationDiagram relation2 = new RelationDiagram();
		relation2.buildDiagram(diagramComparedTo.objects);
		
		
		/*this.ravensObjectArray.clear();
		diagramComparedTo.ravensObjectArray.clear();
		
		for (RavensObject value : this.objects.values()) {
			this.ravensObjectArray.add(value);
		}
		for (RavensObject value : diagramComparedTo.objects.values()) {
			diagramComparedTo.ravensObjectArray.add(value);
		}*/
		
	
		
		Integer numShapesA = this.numShapes;
		Integer numShapesB = diagramComparedTo.numShapes;
		
		int possibleObjectsMatchedWeight[][] = new int[numShapesA][numShapesB];
		
		//Will compare each object in Figure A to each object in Figure B and build the possibleObjectsMatchedWeight matrix
		//When comparison is over, the relationships with the least amount of weight will be considered.
		
		// Iterate through each object in A
		for (int i = 0; i < this.objects.size(); i ++) {
			
			// Iterate through each object in B
			for (int j = 0; j < diagramComparedTo.objects.size(); j++) {
				
				Integer numRelationsA = 0;
				List<String> typesRelationsA = new ArrayList<String>();
				
				// Look at relationships between current object and all others in FIgure A
				for (int k = 0; k < this.relations[i].length; k++) {
					if (this.relations[i][k] != null) {
						numRelationsA++;
						typesRelationsA.add(this.relations[i][k]);
					}
				}
				
				// Keep track of relationships this particular object has
				Integer numRelationsB = 0;
				List<String> typesRelationsB = new ArrayList<String>();
				
				//RELATIONSHIPS
				
				// Look at relationships between current object and all others in FIgure B
				for (int k = 0; k < diagramComparedTo.relations[j].length; k++) {
					if (diagramComparedTo.relations[j][k] != null) {
						numRelationsB++;
						typesRelationsB.add(diagramComparedTo.relations[j][k]);
					}
				}
				
				Integer numUnmatchedRelations = 0;
				List<String> tempTypesRelationsA = new ArrayList<String>();
				for (int k = 0; k < typesRelationsA.size(); k++) {
					tempTypesRelationsA.add(typesRelationsA.get(0));
				}
				
				List<String> tempTypesRelationsB = new ArrayList<String>();
				for (int k = 0; k < typesRelationsB.size(); k++) {
					tempTypesRelationsB.add(typesRelationsB.get(0));
				}
				
				
				// Match similar relations in Figure A and Figure B
				Integer relationsMatched = 0;
				for (int k = 0; k < numRelationsA; k++) {
	
					for (int l = 0; l < numRelationsB; l++) {
						if (tempTypesRelationsA.get(k).equals(tempTypesRelationsB.get(l))) {
							tempTypesRelationsA.remove(k);
							tempTypesRelationsB.remove(l);
							numRelationsA-=1;
							numRelationsB-=1;
							break;
							
						}
					}
				}
				
				numUnmatchedRelations += tempTypesRelationsA.size();
				numUnmatchedRelations += tempTypesRelationsB.size();
				
				possibleObjectsMatchedWeight[i][j]+=numUnmatchedRelations*5;
				
				// Comparison of OTHER ATTRIBUTES
				
				if (coalesce(this.objects.get(this.objectArray.get(i)).getAttributes().get("size"),"null").equals(coalesce(diagramComparedTo.objects.get(diagramComparedTo.objectArray.get(j)).getAttributes().get("size"), "null")) == false) {
					possibleObjectsMatchedWeight[i][j]+=4;
				}
				if (coalesce(this.objects.get(this.objectArray.get(i)).getAttributes().get("alignment"),"null").equals(coalesce(diagramComparedTo.objects.get(diagramComparedTo.objectArray.get(j)).getAttributes().get("alignment"), "null")) == false) {
					possibleObjectsMatchedWeight[i][j]+=2;
				}
				if (coalesce(this.objects.get(this.objectArray.get(i)).getAttributes().get("angle"),"null").equals(coalesce(diagramComparedTo.objects.get(diagramComparedTo.objectArray.get(j)).getAttributes().get("angle"), "null")) == false) {
					possibleObjectsMatchedWeight[i][j]+=2;
				}
				if (coalesce(this.objects.get(this.objectArray.get(i)).getAttributes().get("shape"),"null").equals(coalesce(diagramComparedTo.objects.get(diagramComparedTo.objectArray.get(j)).getAttributes().get("shape"), "null")) == false) {
					possibleObjectsMatchedWeight[i][j]+=3;
				}
				if (coalesce(this.objects.get(this.objectArray.get(i)).getAttributes().get("fill"),"null").equals(coalesce(diagramComparedTo.objects.get(diagramComparedTo.objectArray.get(j)).getAttributes().get("fill"), "null")) == false) {
					possibleObjectsMatchedWeight[i][j]+=1;
				}
				
			}
				
		}
			
		// Now that we have our possibleObjectsMatchedWeight matrix populated, let's take a look which figures have the lowest
		// relationship weight between them.
		
		List<Integer> matchedAIndexes = new ArrayList<Integer>();
		List<Integer> matchedBIndexes = new ArrayList<Integer>();
		Integer differences = 0;
		
		for (int i = 0; i < possibleObjectsMatchedWeight.length; i++) {
			Integer minWeight = 9999;
			HashMap<Integer,List<Integer>> matchedIndexes = new HashMap<Integer, List<Integer>>();
			List<Integer> matchedBIndexWeight = new ArrayList<Integer>();
			Integer minMatchIndexB = -1;
			for (int j = 0; j < possibleObjectsMatchedWeight[i].length; j++) {
				
				if (possibleObjectsMatchedWeight[i][j] < minWeight) {
					minWeight = possibleObjectsMatchedWeight[i][j];
					minMatchIndexB = j;
				}
				
				
			}
			
			matchedBIndexWeight.add(minMatchIndexB);
			matchedBIndexWeight.add(minWeight);
			matchedIndexes.put(i, matchedBIndexWeight);
			
			differences+=minWeight;
			
			matchedAIndexes.add(i);
			matchedBIndexes.add(minMatchIndexB);

		}
		
		return differences;
	}
	
	/*public Integer compare(RelationDiagram diagramComparedTo) {
		
		// Create temporary diagram to be able to make changes
		RelationDiagram tempDiagram = diagramComparedTo;
		//RelationDiagram tempDiagram = new RelationDiagram();
		//tempDiagram.buildDiagram(diagramComparedTo.ravensFigure);
		
		// Difference to return
		Integer difference = 0;
		
		// Increment Difference for items deleted or added
		Integer numShapesDifference = Math.abs(this.numShapes - tempDiagram.numShapes);
		difference += (numShapesDifference*5);
		
		
		Integer numShapesMatched = 0;
		List <String> matchedObjects = new ArrayList<String>();
		// Iterate through shapes of figure 1
		for (int i = 0; i < this.numShapes; i++) {
			
			// Possible matches array
			List<RavensObject> possibleShapeMatches = new ArrayList<RavensObject>();
			
			// Iterate through shapes of figure 2 and try to find possible matches.
			for (int j = 0; j < tempDiagram.numShapes; j++) {
				if (matchedObjects.contains(tempDiagram.ravensObjectArray.get(j).getName()) == false) {
					if (this.ravensObjectArray.get(i).getAttributes().get("shape").equals(tempDiagram.ravensObjectArray.get(j).getAttributes().get("shape"))) {
						possibleShapeMatches.add(tempDiagram.ravensObjectArray.get(j));
					}
				}
			}
			
			
			// Find the match with the least difference out of the possible matches
			Integer leastDifference = 9999;
			Integer leastMatchIndex = -1;
			
			for (int j = 0; j < possibleShapeMatches.size(); j++) {
				Integer tempDifference = 0;
				
				if (coalesce(this.ravensObjectArray.get(i).getAttributes().get("fill"), "null").equals(coalesce(possibleShapeMatches.get(j).getAttributes().get("fill"), "null")) == false) {
					tempDifference += 1;
				}
				if (coalesce(this.ravensObjectArray.get(i).getAttributes().get("angle"), "null").equals(coalesce(possibleShapeMatches.get(j).getAttributes().get("angle"), "null")) == false) {
					tempDifference += 2;
				}
				if (coalesce(this.ravensObjectArray.get(i).getAttributes().get("alignment"), "null").equals(coalesce(possibleShapeMatches.get(j).getAttributes().get("alignment"), "null")) == false) {
					tempDifference += 2;
				}
				if (coalesce(this.ravensObjectArray.get(i).getAttributes().get("size"), "null").equals(coalesce(possibleShapeMatches.get(j).getAttributes().get("size"), "null")) == false) {
					tempDifference += 6;
				}
				if (coalesce(this.relationSentencesHashMap.get(this.ravensObjectArray.get(i).getName()), "null").equals(coalesce(tempDiagram.relationSentencesHashMap.get(possibleShapeMatches.get(j).getName()), "null")) == false) {
					tempDifference += 4;
				}
				
				if (tempDifference < leastDifference) {
					leastDifference = tempDifference;
					leastMatchIndex = j;
				}
			}
			
			difference += leastDifference;
			
			// Remove the matched shape
			if (leastMatchIndex != -1) {
				for (int j = 0; j < tempDiagram.numShapes; j++) {
					if (tempDiagram.ravensObjectArray.get(j).getName().equals(possibleShapeMatches.get(leastMatchIndex).getName())) {
						matchedObjects.add(possibleShapeMatches.get(leastMatchIndex).getName());
						//tempDiagram.ravensObjectArray.remove(j);
						//tempDiagram.numShapes-=1;
						break;
					}
				}
			
			}
			
		}
		
		return difference;
	}*/
	
	
	// SQL-style coalesce function. Return first non-null argument when given two inputs.
    public static <T> T coalesce(T a, T b) {
        return a == null ? b : a;
    }
    
}
