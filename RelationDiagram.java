package ravensproject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RelationDiagram {
	
	public RavensFigure ravensFigure;
	public Integer numShapes;
	public String relations[][];
	
	List<String> relationSentences = new ArrayList<String>();
	public HashMap<String,String> relationSentencesHashMap = new HashMap<String, String>();
	
	////////WORK TOGETHER TO CONSTRUCT SENTENCES
	List<String> relationShape1 = new ArrayList<String>();
	List<String> relationShape2 = new ArrayList<String>();
	List<String> relationType = new ArrayList<String>();
	////////END
	HashMap<String,RavensObject> objects;
	List<RavensObject> ravensObjectArray = new ArrayList<RavensObject>();
	
	
	
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
		
		for (RavensObject value : this.objects.values()) {
			ravensObjectArray.add(value);
		}

		
		for(String objectName : figure.getObjects().keySet()) {

			RavensObject thisObject = figure.getObjects().get(objectName);
			this.objectArray.add(thisObject.getName());
			if (thisObject.getAttributes().get("shape") != null) this.shapeArray.add(thisObject.getAttributes().get("shape"));
			if (thisObject.getAttributes().get("size") != null) this.sizeArray.add(thisObject.getAttributes().get("size"));
			if (thisObject.getAttributes().get("fill") != null) this.fillArray.add(thisObject.getAttributes().get("fill"));
			if (thisObject.getAttributes().get("angle") != null) this.angleArray.add(thisObject.getAttributes().get("angle"));
			if (thisObject.getAttributes().get("alignment") != null) this.alignmentArray.add(thisObject.getAttributes().get("alignment"));

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
		
		// Create temporary diagram to be able to make changes
		RelationDiagram tempDiagram = new RelationDiagram();
		tempDiagram.buildDiagram(diagramComparedTo.ravensFigure);
		
		// Difference to return
		Integer difference = 0;
		
		// Increment Difference for items deleted or added
		Integer numShapesDifference = Math.abs(this.numShapes - tempDiagram.numShapes);
		difference += (numShapesDifference*5);
		
		
		Integer numShapesMatched = 0;
		
		// Iterate through shapes of figure 1
		for (int i = 0; i < this.numShapes; i++) {
			
			// Possible matches array
			List<RavensObject> possibleShapeMatches = new ArrayList<RavensObject>();
			
			// Iterate through shapes of figure 2 and try to find possible matches.
			for (int j = 0; j < tempDiagram.numShapes; j++) {
				if (this.ravensObjectArray.get(i).getAttributes().get("shape").equals(tempDiagram.ravensObjectArray.get(j).getAttributes().get("shape"))) {
					possibleShapeMatches.add(tempDiagram.ravensObjectArray.get(j));
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
						tempDiagram.ravensObjectArray.remove(j);
						tempDiagram.numShapes-=1;
						break;
					}
				}
			
			}
			
		}
		
		return difference;
	}
	
	
	// SQL-style coalesce function. Return first non-null argument when given two inputs.
    public static <T> T coalesce(T a, T b) {
        return a == null ? b : a;
    }
    
}
