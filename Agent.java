package ravensproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// Uncomment these lines to access image processing.
//import java.awt.Image;
//import java.io.File;
//import javax.imageio.ImageIO;

/**
 * Your Agent for solving Raven's Progressive Matrices. You MUST modify this
 * file.
 * 
 * You may also create and submit new files in addition to modifying this file.
 * 
 * Make sure your file retains methods with the signatures:
 * public Agent()
 * public char Solve(RavensProblem problem)
 * 
 * These methods will be necessary for the project's main method to run.
 * 
 */
public class Agent {
    /**
     * The default constructor for your Agent. Make sure to execute any
     * processing necessary before your Agent starts solving problems here.
     * 
     * Do not add any variables to this signature; they will not be used by
     * main().
     * 
     */
	
	
    public Agent() {
        
    }
    /**
     * The primary method for solving incoming Raven's Progressive Matrices.
     * For each problem, your Agent's Solve() method will be called. At the
     * conclusion of Solve(), your Agent should return a String representing its
     * answer to the question: "1", "2", "3", "4", "5", or "6". These Strings
     * are also the Names of the individual RavensFigures, obtained through
     * RavensFigure.getName().
     * 
     * In addition to returning your answer at the end of the method, your Agent
     * may also call problem.checkAnswer(String givenAnswer). The parameter
     * passed to checkAnswer should be your Agent's current guess for the
     * problem; checkAnswer will return the correct answer to the problem. This
     * allows your Agent to check its answer. Note, however, that after your
     * agent has called checkAnswer, it will *not* be able to change its answer.
     * checkAnswer is used to allow your Agent to learn from its incorrect
     * answers; however, your Agent cannot change the answer to a question it
     * has already answered.
     * 
     * If your Agent calls checkAnswer during execution of Solve, the answer it
     * returns will be ignored; otherwise, the answer returned at the end of
     * Solve will be taken as your Agent's answer to this problem.
     * 
     * @param problem the RavensProblem your agent should solve
     * @return your Agent's answer to this problem
     */
    public int Solve(RavensProblem problem) {
    	
    	if (problem.hasVerbal() == false) {
    		return -1;
    	}
    	
    	Integer chosenMultipleChoiceAnswer = -1;
    	
    	// Specific solution to 2x2 problem
    	if (problem.getProblemType().equals("2x2")) {
    		System.out.println(problem.getName() + " 2x2");
    		
    		// Variables for relation Diagrams of all figures
    		RelationDiagram figureARelationDiagram = new RelationDiagram();
    		RelationDiagram figureBRelationDiagram = new RelationDiagram();
    		RelationDiagram figureCRelationDiagram = new RelationDiagram();
    		RelationDiagram figure1RelationDiagram = new RelationDiagram();
    		RelationDiagram figure2RelationDiagram = new RelationDiagram();
    		RelationDiagram figure3RelationDiagram = new RelationDiagram();
    		RelationDiagram figure4RelationDiagram = new RelationDiagram();
    		RelationDiagram figure5RelationDiagram = new RelationDiagram();
    		RelationDiagram figure6RelationDiagram = new RelationDiagram();
 
    		
    		// Build relationship diagrams for all figures above
    		for(String figureName : problem.getFigures().keySet()) {
        		
        		RavensFigure thisFigure = problem.getFigures().get(figureName);

        		switch (figureName) {
        			case ("A"):
        				figureARelationDiagram.buildDiagram(thisFigure);
        			break;
        			case ("B"):
        				figureBRelationDiagram.buildDiagram(thisFigure);
        			break;
        			case ("C"):
        				figureCRelationDiagram.buildDiagram(thisFigure);
        			break;
        			case ("1"):
        				figure1RelationDiagram.buildDiagram(thisFigure);
        			break;
        			case ("2"):
        				figure2RelationDiagram.buildDiagram(thisFigure);
        			break;
        			case ("3"):
        				figure3RelationDiagram.buildDiagram(thisFigure);
        			break;
        			case ("4"):
        				figure4RelationDiagram.buildDiagram(thisFigure);
        			break;
        			case ("5"):
        				figure5RelationDiagram.buildDiagram(thisFigure);
        			break;
        			case ("6"):
        				figure6RelationDiagram.buildDiagram(thisFigure);
        			break;
        		}
        		    		
    		}
    	
    		//MultipleChoiceAnswers in order
    		List<RelationDiagram> multipleChoiceRelationDiagrams = new ArrayList<RelationDiagram>();
    		multipleChoiceRelationDiagrams.add(figure1RelationDiagram);
    		multipleChoiceRelationDiagrams.add(figure2RelationDiagram);
    		multipleChoiceRelationDiagrams.add(figure3RelationDiagram);
    		multipleChoiceRelationDiagrams.add(figure4RelationDiagram);
    		multipleChoiceRelationDiagrams.add(figure5RelationDiagram);
    		multipleChoiceRelationDiagrams.add(figure6RelationDiagram);
    		
    		
    		// A - B comparisons
    		List<SemanticNetwork> possibleSemanticNetworksAB = buildSemanticNetwork(figureARelationDiagram, figureBRelationDiagram, problem.getProblemType());
    		if (possibleSemanticNetworksAB == null) {
    			return -1;
    		}
    		
    		// A - C comparisons
    		List<SemanticNetwork> possibleSemanticNetworksAC = buildSemanticNetwork(figureARelationDiagram, figureCRelationDiagram, problem.getProblemType());
    		if (possibleSemanticNetworksAC == null) {
    			return -1;
    		}
    		
    		List<RelationDiagram> possibleHorizontalAnswers = buildAnswerRelationDiagram(possibleSemanticNetworksAC, possibleSemanticNetworksAB, figureCRelationDiagram);
    		List<RelationDiagram> possibleVerticalAnswers = buildAnswerRelationDiagram(possibleSemanticNetworksAB, possibleSemanticNetworksAC, figureBRelationDiagram);
    		
    		List<RelationDiagram> chosenAnswersToCompare = new ArrayList<RelationDiagram>();
    		
    		Integer horizontalBestAnswerIndex = -1;
    		Integer verticalBestAnswerIndex = -1;
    		
    		Integer leastDifference = 99999;
    		
    		for (int i = 0; i < possibleHorizontalAnswers.size(); i++) {
    			for (int j = 0; j < possibleVerticalAnswers.size(); j++) {
    				Integer difference = possibleHorizontalAnswers.get(i).compare(possibleVerticalAnswers.get(j));
    				if (difference < leastDifference) {
    					leastDifference = difference;
    					horizontalBestAnswerIndex = i;
    					verticalBestAnswerIndex = j;
    				}
    			}
    		}
    		
    		// IDENTICAL answer in horizontal and vertical
    		if (leastDifference == 0) {
    			chosenAnswersToCompare.add(possibleHorizontalAnswers.get(horizontalBestAnswerIndex));
    		}
    		else {
    			chosenAnswersToCompare.add(possibleHorizontalAnswers.get(horizontalBestAnswerIndex));
    			chosenAnswersToCompare.add(possibleVerticalAnswers.get(verticalBestAnswerIndex));
    		}
    		
    		leastDifference = 99999;
    		
    		
    		for (int i = 0; i < chosenAnswersToCompare.size(); i++) {
    			for (int j = 0; j < multipleChoiceRelationDiagrams.size(); j++) {
    				Integer difference = chosenAnswersToCompare.get(i).compare(multipleChoiceRelationDiagrams.get(j));
    				if (difference < leastDifference) {
    					leastDifference = difference;
    					chosenMultipleChoiceAnswer = j+1;
    				}
    			}
    		}
    		
    		
    		
    		
    		
    	}
    	System.out.println("Answer Chosen: " + chosenMultipleChoiceAnswer);
    	System.out.println("");
    	return chosenMultipleChoiceAnswer;
    }
    
    
    /*// GENERATOR OF GENERATE & TEST WILL GENERATE DIFFERENT FIGURES BY PERFORMING TRANSFORMATIONS 
    // ON ALL SHAPES AND COMPARING RESULT TO SECOND FIGURE
    public RavensObject transformationInOrder(RavensObject shape, Integer typeOfTransformation) {
    	switch (typeOfTransformation) {
    	case 0: // UNCHANGED
    		return shape;
    		
    	case 1: // FILL or UNFILL
    		
    		//ITERATE THROUGH ALL ATTRIBUTES
    		for(String attributeName : shape.getAttributes().keySet()) {
    			
    			if (attributeName.equals("fill")) {
    				String attributeValue = shape.getAttributes().get(attributeName);
    				if (attributeValue.equals("yes")) {
    					shape.getAttributes().put(attributeName, "no");
    				}
    				else if (attributeValue.equals("no")) {
    					shape.getAttributes().put(attributeName, "yes");
    				}
    			}
    			
    			return shape;
    			
    		}
    	default:
    		return shape;
    	}
    }*/
    
    
    
    public List<SemanticNetwork> buildSemanticNetwork(RelationDiagram figureARelationDiagram, RelationDiagram figureBRelationDiagram, String problemType) {
    	Integer numShapesA = figureARelationDiagram.frames.size();
		Integer numShapesB = figureBRelationDiagram.frames.size();
		
		Integer numShapesDiff = numShapesB - numShapesA;
		
		
		List<SemanticNetwork> possibleSemanticNetworks = new ArrayList<SemanticNetwork>();
		
		
		// EASY CASE: SAME NUMBER OF SHAPES
		//if (numShapesDiff == 0) {
						
			// FIGURE A and B have more than one shape
			if (numShapesA > 1 | numShapesB > 1) {
				
				
				// Look at relationships that figures have against one another.
				Integer numRelationshipMatches = 0;
				
				int possibleObjectsMatchedWeight[][] = new int[numShapesA][numShapesB];
				
				
				//Used For Weights
				List<String> framesAInOrder = new ArrayList<String>();
				List<List <String>> framesBMatchedWithA = new ArrayList<List <String>>();
				List<List <Integer>> weightMatchesAB = new ArrayList<List <Integer>>();
				
				//Will compare each object in Figure A to each object in Figure B and build the possibleObjectsMatchedWeight matrix
				//When comparison is over, the relationships with the least amount of weight will be considered.
				
				// Iterate through each object in A
				//for (int i = 0; i < figureARelationDiagram.ravensObjectArray.size(); i ++) {
				//int i = 0;
				for(String frameNameA : figureARelationDiagram.frames.keySet()) {

					Frame tempFrameA = new Frame(figureARelationDiagram.frames.get(frameNameA));
					
			        
					//Used for Weights
					framesAInOrder.add(frameNameA);
					List<String> framesBInOrder = new ArrayList<String>();
					List<Integer> weightMatches = new ArrayList<Integer>();
					
					// Iterate through each object in B
					//for (int j = 0; j < figureBRelationDiagram.ravensObjectArray.size(); j++) {
					//int j = 0;
					for(String frameNameB : figureBRelationDiagram.frames.keySet()) {

						Frame tempFrameB = new Frame(figureBRelationDiagram.frames.get(frameNameB));
						
						//Used for Weights
						framesBInOrder.add(frameNameB);
						
						
						Integer numInsideRelationsA = tempFrameA.inside.size();
						Integer numAboveRelationsA = tempFrameA.above.size();
	    				//List<String> typesRelationsA = new ArrayList<String>();
	    				
	    				// Look at relationships between current object and all others in FIgure A
						/*for (int k = 0; k < figureARelationDiagram.relations[i].length; k++) {
							if (figureARelationDiagram.relations[i][k] != null) {
								numRelationsA++;
								typesRelationsA.add(figureARelationDiagram.relations[i][k]);
							}
						}*/
						
						// Keep track of relationships this particular object has
						Integer numInsideRelationsB = tempFrameB.inside.size();
						Integer numAboveRelationsB = tempFrameB.above.size();
	    				//List<String> typesRelationsB = new ArrayList<String>();
	    				
	    				//RELATIONSHIPS
	    				
	    				// Look at relationships between current object and all others in FIgure B
	    				/*for (int k = 0; k < figureBRelationDiagram.relations[j].length; k++) {
							if (figureBRelationDiagram.relations[j][k] != null) {
								numRelationsB++;
								typesRelationsB.add(figureBRelationDiagram.relations[j][k]);
							}
						}*/
						
						Integer numInsideUnmatchedRelations = Math.abs(numInsideRelationsA-numInsideRelationsB);
						Integer numAboveUnmatchedRelations = Math.abs(numAboveRelationsA-numAboveRelationsB);
						
						Integer numTotalRelationsA = numInsideRelationsA+numAboveRelationsA; //Keep adding all other relations
						Integer numTotalRelationsB = numInsideRelationsB+numAboveRelationsB; //Keep adding all other relations
						
						Integer numTotalUnmatchedRelations = Math.abs(numTotalRelationsA-numTotalRelationsB); 
	    				
	    				/*Integer numUnmatchedRelations = 0;
	    				List<String> tempTypesRelationsA = new ArrayList<String>();
	    				for (int k = 0; k < typesRelationsA.size(); k++) {
	    					tempTypesRelationsA.add(typesRelationsA.get(0));
	    				}
    					
    					List<String> tempTypesRelationsB = new ArrayList<String>();
    					for (int k = 0; k < typesRelationsB.size(); k++) {
	    					tempTypesRelationsB.add(typesRelationsB.get(0));
	    				}*/
						
						
    					
    					/*
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
	    				*/
	    				
	    				//numUnmatchedRelations += tempTypesRelationsA.size();
	    				//numUnmatchedRelations += tempTypesRelationsB.size();
	    				
						Integer tempWeight = 0;
						
	    				//possibleObjectsMatchedWeight[i][j]+=numInsideUnmatchedRelations*5;
	    				tempWeight += numInsideUnmatchedRelations*5;
	    				tempWeight += numAboveUnmatchedRelations*5;
						
						
	    				
	    				// Comparison of OTHER ATTRIBUTES
	    				
	    				if (tempFrameA.size.equals(tempFrameB.size) == false) {
	    					//possibleObjectsMatchedWeight[i][j]+=4;
	    					tempWeight+=5;
	    				}
	    				if (coalesce(tempFrameA.alignment, "null").equals(coalesce(tempFrameB.alignment, "null")) == false) {
	    					//possibleObjectsMatchedWeight[i][j]+=2;
	    					if(coalesce(tempFrameA.alignment, "null") != "null" & coalesce(tempFrameB.alignment, "null") != "null") {
	    						
	    						if (tempFrameA.alignment.equals("bottom") & tempFrameA.alignment.equals("top")) {
	    							tempWeight+=2;
	    						}
	    						else if (tempFrameA.alignment.equals("top") & tempFrameA.alignment.equals("bottom")) {
	    							tempWeight+=2;
	    						}
	    						else if (tempFrameA.alignment.equals("left") & tempFrameA.alignment.equals("right")) {
	    							tempWeight+=2;
	    						}
	    						else if (tempFrameA.alignment.equals("right") & tempFrameA.alignment.equals("left")) {
	    							tempWeight+=2;
	    						}
	    						else if (tempFrameA.alignment.equals("top-right") & tempFrameA.alignment.equals("top-left")) {
	    							tempWeight+=2;
	    						}
	    						else if (tempFrameA.alignment.equals("top-left") & tempFrameA.alignment.equals("top-right")) {
	    							tempWeight+=2;
	    						}
	    						else if (tempFrameA.alignment.equals("bottom-left") & tempFrameA.alignment.equals("bottom-right")) {
	    							tempWeight+=2;
	    						}
	    						else if (tempFrameA.alignment.equals("bottom-right") & tempFrameA.alignment.equals("bottom-left")) {
	    							tempWeight+=2;
	    						}
	    						else if (tempFrameA.alignment.equals("top-right") & tempFrameA.alignment.equals("bottom-right")) {
	    							tempWeight+=2;
	    						}
	    						else if (tempFrameA.alignment.equals("bottom-right") & tempFrameA.alignment.equals("top-right")) {
	    							tempWeight+=2;
	    						}
	    						else if (tempFrameA.alignment.equals("top-left") & tempFrameA.alignment.equals("bottom-left")) {
	    							tempWeight+=2;
	    						}
	    						else if (tempFrameA.alignment.equals("bottom-left") & tempFrameA.alignment.equals("top-left")) {
	    							tempWeight+=2;
	    						}
	    						else tempWeight+=4;
	    					}
	    					else tempWeight+=4;
	    				}
	    				if (coalesce(tempFrameA.angle, "null").equals(coalesce(tempFrameB.angle,"null")) == false) {
	    					//possibleObjectsMatchedWeight[i][j]+=2;
	    					if (coalesce(tempFrameA.angle, 999.0) != 999.0 & coalesce(tempFrameB.angle, 999.0) != 999.0) {
	    						if (Math.min(tempFrameB.angle, tempFrameA.angle)+((Math.abs(tempFrameB.angle - tempFrameA.angle))/2.0) % 90.0 == 0.0) { // Reflection
	    							tempWeight+=2;
	    						}
	    						else {
	    							tempWeight+=3;
	    						}
	    					}
	    					else tempWeight+=3;
	    				}
	    				if (coalesce(tempFrameA.shape, "null").equals(coalesce(tempFrameB.shape,"null")) == false) {
	    					//possibleObjectsMatchedWeight[i][j]+=3;
	    					tempWeight+=6;
	    				}
	    				if (coalesce(tempFrameA.fill,"null").equals(coalesce(tempFrameB.fill,"null")) == false) {
	    					//possibleObjectsMatchedWeight[i][j]+=1;
	    					tempWeight+=1;
	    				}
	    				
	    				weightMatches.add(tempWeight);
	    				
	    			}
					weightMatchesAB.add(weightMatches);
					framesBMatchedWithA.add(framesBInOrder);

				}
					
				// Now that we have our possibleObjectsMatchedWeight matrix populated, let's take a look which figures have the lowest
				// relationship weight between them.
				
				List<Integer> matchedAIndexes = new ArrayList<Integer>();
				List<Integer> matchedBIndexes = new ArrayList<Integer>();
				
				for (int i = 0; i < framesAInOrder.size(); i++) {
					Integer minWeight = 9999;
					//HashMap<Integer,List<Integer>> matchedIndexes = new HashMap<Integer, List<Integer>>();
					//List<Integer> matchedBIndexWeight = new ArrayList<Integer>();
					Integer minMatchIndexB = -1;
					for (int j = 0; j < framesBMatchedWithA.get(i).size(); j++) {
						
						if (weightMatchesAB.get(i).get(j) < minWeight) {
							minWeight = weightMatchesAB.get(i).get(j);
							minMatchIndexB = j;
						}
						
						
					}
					
					
					matchedAIndexes.add(i);
					matchedBIndexes.add(minMatchIndexB);
			
				}
				
				
				SemanticNetwork semanticNetwork = new SemanticNetwork(figureARelationDiagram, figureBRelationDiagram, problemType);
				
				
				
				// Temporary Arrays used to keep track of which objects have already been added to transformations arrays.
				// After each matched object is added, the index will be removed from these temp arrays.
				// Remaining indexes will be either Deleted objects from Figure A, or Added objects to Figure B
				List<Integer> tempAIndexes = new ArrayList<Integer>();
				List<Integer> tempBIndexes = new ArrayList<Integer>();
				for (int i = 0; i < numShapesA; i++) {
					tempAIndexes.add(i);
				}
				for (int i = 0; i < numShapesB; i++) {
					tempBIndexes.add(i);
				}
				//// End Temp Arrays
				
				for (int i = 0; i < Math.max(numShapesA, numShapesB); i++) {
					
					List<String> transformations = new ArrayList<String>();
					List<String> finalCharacteristic = new ArrayList<String>();
					
					if ((i < numShapesA) & (i < numShapesB)) {
						//String objectStringA = figureARelationDiagram.objectArray.get(matchedAIndexes.get(i));
						String objectStringA = framesAInOrder.get(matchedAIndexes.get(i));
						//String objectStringB = figureBRelationDiagram.objectArray.get(matchedBIndexes.get(i));
						String objectStringB = framesBMatchedWithA.get(i).get(matchedBIndexes.get(i));
						
						Frame tempFrameA = new Frame(figureARelationDiagram.frames.get(objectStringA));
						Frame tempFrameB = new Frame(figureBRelationDiagram.frames.get(objectStringB));
						
						// Remove matched objects' indexes from temp index array. Will be used later
						tempAIndexes.remove(matchedAIndexes.get(i));
						tempBIndexes.remove(matchedBIndexes.get(i));
						
						
						
						
						//if (coalesce(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("shape"),"null").equals(coalesce(figureARelationDiagram.objects.get(objectStringA).getAttributes().get("shape"), "null")) == false) {
						if (coalesce(tempFrameA.shape,"null").equals(coalesce(tempFrameB.shape,"null")) == false) {	
							transformations.add("shape");
							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("shape"));
						}
						//if (coalesce(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("fill"),"null").equals(coalesce(figureARelationDiagram.objects.get(objectStringA).getAttributes().get("fill"), "null")) == false) {
						if (coalesce(tempFrameA.fill, "null").equals(coalesce(tempFrameB.fill,"null")) == false) {	
							transformations.add("fill");
							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("fill"));
						}
						//if (coalesce(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("angle"),"null").equals(coalesce(figureARelationDiagram.objects.get(objectStringA).getAttributes().get("angle"), "null")) == false) {
						if (coalesce(tempFrameA.angle,"null").equals(coalesce(tempFrameB.angle,"null")) == false) {
	    					//possibleObjectsMatchedWeight[i][j]+=2;
							if (coalesce(tempFrameA.angle, 999.0) != 999.0 & coalesce(tempFrameB.angle, 999.0) != 999.0) {
								if (Math.min(tempFrameB.angle, tempFrameA.angle)+((Math.abs(tempFrameB.angle - tempFrameA.angle))/2.0) == 90.0 | 
	    								Math.min(tempFrameB.angle, tempFrameA.angle)+((Math.abs(tempFrameB.angle - tempFrameA.angle))/2.0) == 270.0) {
	    							transformations.add("vreflection");
	    							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("angle"));
	    						}
								else if (Math.min(tempFrameB.angle, tempFrameA.angle)+((Math.abs(tempFrameB.angle - tempFrameA.angle))/2.0) == 180.0) {
	    							transformations.add("hreflection");
	    							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("angle"));
								}
	    						else {
	    							transformations.add("angle");
	    							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("angle"));
	    						}
	    					}
						}
						//if (coalesce(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("size"),"null").equals(coalesce(figureARelationDiagram.objects.get(objectStringA).getAttributes().get("size"), "null")) == false) {
						if (coalesce(tempFrameA.size, "null").equals(coalesce(tempFrameB.size, "null")) == false) {	
							transformations.add("size");
							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("size"));
						}
						//if (coalesce(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"),"null").equals(coalesce(figureARelationDiagram.objects.get(objectStringA).getAttributes().get("alignment"), "null")) == false) {
						if (coalesce(tempFrameA.alignment, "null").equals(coalesce(tempFrameB.alignment,"null")) == false) {
							
							if (tempFrameA.alignment.equals("bottom") & tempFrameB.alignment.equals("top")) {
								transformations.add("hReflectionAlignment");
								finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
    						}
    						else if (tempFrameA.alignment.equals("top") & tempFrameB.alignment.equals("bottom")) {
    							transformations.add("hReflectionAlignment");
    							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
    						}
    						else if (tempFrameA.alignment.equals("left") & tempFrameB.alignment.equals("right")) {
    							transformations.add("vReflectionAlignment");
    							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
    						}
    						else if (tempFrameA.alignment.equals("right") & tempFrameB.alignment.equals("left")) {
    							transformations.add("vReflectionAlignment");
    							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
    						}
    						else if (tempFrameA.alignment.equals("top-right") & tempFrameB.alignment.equals("top-left")) {
    							transformations.add("vReflectionAlignment");
    							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
    						}
    						else if (tempFrameA.alignment.equals("top-left") & tempFrameB.alignment.equals("top-right")) {
    							transformations.add("vReflectionAlignment");
    							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
    						}
    						else if (tempFrameA.alignment.equals("bottom-left") & tempFrameB.alignment.equals("bottom-right")) {
    							transformations.add("vReflectionAlignment");
    							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
    						}
    						else if (tempFrameA.alignment.equals("bottom-right") & tempFrameB.alignment.equals("bottom-left")) {
    							transformations.add("vReflectionAlignment");
    							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
    						}
    						else if (tempFrameA.alignment.equals("top-right") & tempFrameB.alignment.equals("bottom-right")) {
    							transformations.add("hReflectionAlignment");
    							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
    						}
    						else if (tempFrameA.alignment.equals("bottom-right") & tempFrameB.alignment.equals("top-right")) {
    							transformations.add("hReflectionAlignment");
    							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
    						}
    						else if (tempFrameA.alignment.equals("top-left") & tempFrameB.alignment.equals("bottom-left")) {
    							transformations.add("hReflectionAlignment");
    							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
    						}
    						else if (tempFrameA.alignment.equals("bottom-left") & tempFrameB.alignment.equals("top-left")) {
    							transformations.add("hReflectionAlignment");
    							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
    						}
    						else {
							
    							transformations.add("alignment");
    							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
    						}
						}
						
						
						semanticNetwork.figure1shapes.add(figureARelationDiagram.objects.get(objectStringA));
						semanticNetwork.figure2shapes.add(figureBRelationDiagram.objects.get(objectStringB));
					}
					
					else if (i >= numShapesA) {
						//String objectStringB = figureBRelationDiagram.objectArray.get(tempBIndexes.get(0));
						String objectStringB = framesBMatchedWithA.get(0).get(matchedBIndexes.get(0));
						tempBIndexes.remove(0);
						transformations.add("added");
						finalCharacteristic.add("added");
						semanticNetwork.figure2shapes.add(figureBRelationDiagram.objects.get(objectStringB));
						semanticNetwork.addedShapes.add(figureBRelationDiagram.objects.get(objectStringB));
					}
					else if (i >= numShapesB) {
						//String objectStringA = figureARelationDiagram.objectArray.get(tempAIndexes.get(0));
						String objectStringA = framesAInOrder.get(matchedAIndexes.get(0));
						tempAIndexes.remove(0);
						transformations.add("deleted");
						finalCharacteristic.add("deleted");
						semanticNetwork.figure1shapes.add(figureARelationDiagram.objects.get(objectStringA));
						semanticNetwork.deletedShapes.add(figureARelationDiagram.objects.get(objectStringA));
					}
					
						semanticNetwork.transformations.add(transformations);
						semanticNetwork.finalCharacteristic.add(finalCharacteristic);
				}
					//Call update weights
					semanticNetwork.updateWeights();
					possibleSemanticNetworks.add(semanticNetwork);
					
					//System.out.println("Just created first semantic network");
					
					for (int j = 0; j < semanticNetwork.figure1shapes.size(); j++) {
						String tempShape = semanticNetwork.figure1shapes.get(j).getName();
						RavensObject tempObject = semanticNetwork.figure1shapes.get(j);
						
						semanticNetwork.figure1shapesHashMap.put(tempShape, tempObject);
					}
					for (int j = 0; j < semanticNetwork.figure2shapes.size(); j++) {
						semanticNetwork.figure2shapesHashMap.put(semanticNetwork.figure2shapes.get(j).getName(), semanticNetwork.figure2shapes.get(j));
					}
				
			
			}
			
			// Both FIGURE A and FIGURE B have only one shape
			else if (figureBRelationDiagram.numShapes == 1) {
				SemanticNetwork semanticNetwork = new SemanticNetwork(figureARelationDiagram, figureBRelationDiagram, "2x2");
				
				String frameAValue = figureARelationDiagram.objectArray.get(0);
				String frameBValue = figureBRelationDiagram.objectArray.get(0);
				
				Frame tempFrameA = new Frame(figureARelationDiagram.frames.get(frameAValue));
				Frame tempFrameB = new Frame(figureBRelationDiagram.frames.get(frameBValue));
				
				String objectStringA = frameAValue;
				String objectStringB = frameBValue;
				
				semanticNetwork.figure1shapes = figureARelationDiagram.ravensObjectArray;
				semanticNetwork.figure2shapes = figureBRelationDiagram.ravensObjectArray;
				
				// Add transformations that occurred to Semantic Network
				List<String> transformations = new ArrayList<String>();
				List<String> finalCharacteristic = new ArrayList<String>();
				
				//String shapeB = coalesce(figureBRelationDiagram.objects.get(figureBRelationDiagram.objectArray.get(0)).getAttributes().get("shape"),"null");
				
				//if (coalesce(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("shape"),"null").equals(coalesce(figureARelationDiagram.objects.get(objectStringA).getAttributes().get("shape"), "null")) == false) {
				if (coalesce(tempFrameA.shape,"null").equals(coalesce(tempFrameB.shape,"null")) == false) {	
					transformations.add("shape");
					finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("shape"));
				}
				//if (coalesce(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("fill"),"null").equals(coalesce(figureARelationDiagram.objects.get(objectStringA).getAttributes().get("fill"), "null")) == false) {
				if (coalesce(tempFrameA.fill, "null").equals(coalesce(tempFrameB.fill,"null")) == false) {	
					transformations.add("fill");
					finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("fill"));
				}
				//if (coalesce(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("angle"),"null").equals(coalesce(figureARelationDiagram.objects.get(objectStringA).getAttributes().get("angle"), "null")) == false) {
				if (coalesce(tempFrameA.angle,"null").equals(coalesce(tempFrameB.angle,"null")) == false) {
					//possibleObjectsMatchedWeight[i][j]+=2;
					if (coalesce(tempFrameA.angle, 999.0) != 999.0 & coalesce(tempFrameB.angle, 999.0) != 999.0) {
						if (Math.min(tempFrameB.angle, tempFrameA.angle)+((Math.abs(tempFrameB.angle - tempFrameA.angle))/2.0) == 90.0 | 
								Math.min(tempFrameB.angle, tempFrameA.angle)+((Math.abs(tempFrameB.angle - tempFrameA.angle))/2.0) == 270.0) {
							transformations.add("vreflection");
							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("angle"));
						}
						else if (Math.min(tempFrameB.angle, tempFrameA.angle)+((Math.abs(tempFrameB.angle - tempFrameA.angle))/2.0) == 180.0) {
							transformations.add("hreflection");
							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("angle"));
						}
						else {
							transformations.add("angle");
							finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("angle"));
						}
					}
				}
				//if (coalesce(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("size"),"null").equals(coalesce(figureARelationDiagram.objects.get(objectStringA).getAttributes().get("size"), "null")) == false) {
				if (coalesce(tempFrameA.size, "null").equals(coalesce(tempFrameB.size, "null")) == false) {	
					transformations.add("size");
					finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("size"));
				}
				//if (coalesce(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"),"null").equals(coalesce(figureARelationDiagram.objects.get(objectStringA).getAttributes().get("alignment"), "null")) == false) {
				if (coalesce(tempFrameA.alignment, "null").equals(coalesce(tempFrameB.alignment,"null")) == false) {
					
					if (tempFrameA.alignment.equals("bottom") & tempFrameB.alignment.equals("top")) {
						transformations.add("hReflectionAlignment");
						finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
					}
					else if (tempFrameA.alignment.equals("top") & tempFrameB.alignment.equals("bottom")) {
						transformations.add("hReflectionAlignment");
						finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
					}
					else if (tempFrameA.alignment.equals("left") & tempFrameB.alignment.equals("right")) {
						transformations.add("vReflectionAlignment");
						finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
					}
					else if (tempFrameA.alignment.equals("right") & tempFrameB.alignment.equals("left")) {
						transformations.add("vReflectionAlignment");
						finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
					}
					else if (tempFrameA.alignment.equals("top-right") & tempFrameB.alignment.equals("top-left")) {
						transformations.add("vReflectionAlignment");
						finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
					}
					else if (tempFrameA.alignment.equals("top-left") & tempFrameB.alignment.equals("top-right")) {
						transformations.add("vReflectionAlignment");
						finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
					}
					else if (tempFrameA.alignment.equals("bottom-left") & tempFrameB.alignment.equals("bottom-right")) {
						transformations.add("vReflectionAlignment");
						finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
					}
					else if (tempFrameA.alignment.equals("bottom-right") & tempFrameB.alignment.equals("bottom-left")) {
						transformations.add("vReflectionAlignment");
						finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
					}
					else if (tempFrameA.alignment.equals("top-right") & tempFrameB.alignment.equals("bottom-right")) {
						transformations.add("hReflectionAlignment");
						finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
					}
					else if (tempFrameA.alignment.equals("bottom-right") & tempFrameB.alignment.equals("top-right")) {
						transformations.add("hReflectionAlignment");
						finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
					}
					else if (tempFrameA.alignment.equals("top-left") & tempFrameB.alignment.equals("bottom-left")) {
						transformations.add("hReflectionAlignment");
						finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
					}
					else if (tempFrameA.alignment.equals("bottom-left") & tempFrameB.alignment.equals("top-left")) {
						transformations.add("hReflectionAlignment");
						finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
					}
					else {
					
						transformations.add("alignment");
						finalCharacteristic.add(figureBRelationDiagram.objects.get(objectStringB).getAttributes().get("alignment"));
					}
				}
					
				semanticNetwork.transformations.add(transformations);
				semanticNetwork.finalCharacteristic.add(finalCharacteristic);
				
				//Call update weights
				semanticNetwork.updateWeights();
				possibleSemanticNetworks.add(semanticNetwork);
				
				//System.out.println("Just created first semantic network");
				
				for (int i = 0; i < semanticNetwork.figure1shapes.size(); i++) {
					String tempShape = semanticNetwork.figure1shapes.get(i).getName();
					RavensObject tempObject = semanticNetwork.figure1shapes.get(i);
					
					semanticNetwork.figure1shapesHashMap.put(tempShape, tempObject);
				}
				for (int i = 0; i < semanticNetwork.figure2shapes.size(); i++) {
					semanticNetwork.figure2shapesHashMap.put(semanticNetwork.figure2shapes.get(i).getName(), semanticNetwork.figure2shapes.get(i));
				}
				
			}
		
		//}
		//else {
		//	return null;
		//}
		
		
		return possibleSemanticNetworks;
    }
		
    
    // Return Array of Possible Answers
    public List<RelationDiagram> buildAnswerRelationDiagram(List<SemanticNetwork> relationSemanticNetworkArray, List<SemanticNetwork> transformationSemanticNetworkArray, RelationDiagram initialRelationDiagram) {
    	List<RelationDiagram> possibleAnswersRelationDiagram = new ArrayList<RelationDiagram>();
    	
    	// Create temporary Relation Diagram identical to initial figure (which has frame diagram) to make changes to.
    	RelationDiagram tempRelationDiagram = new RelationDiagram();
		tempRelationDiagram.buildDiagram(initialRelationDiagram.ravensFigure);
    	
		
		// Iterate through Semantic Diagrams which contain possible relations between the figure to be transformed, and the figure we are comparing it to
    	for (int i = 0; i < relationSemanticNetworkArray.size(); i++) {
			SemanticNetwork relationSemanticNetwork = relationSemanticNetworkArray.get(i);
			
			// Iterate through shapes in figure to be transformed
			for (int j = 0; j < relationSemanticNetwork.figure2shapes.size(); j++) {
				RavensObject shapeToTransform = relationSemanticNetwork.figure2shapes.get(j);
				
				// If there is a figure remaining in the figure we are getting our transformations from, i.e. remaining figures are not added
				if (j < relationSemanticNetwork.figure1shapes.size()) {
					RavensObject shapeTransformIsRelatedTo = relationSemanticNetwork.figure1shapes.get(j);
				
					
					// Iterate through Semantic Networks which contain the actual transformations that will be applied
					for (int k = 0; k < transformationSemanticNetworkArray.size(); k++) {
						SemanticNetwork transformationSemanticNetwork = transformationSemanticNetworkArray.get(k);
						
						// Iterate through figures in the figure we are getting our transformations from
						for (int l = 0; l < transformationSemanticNetwork.figure1shapes.size(); l++) {
							List<String> transformationArray = new ArrayList<String>();
							List<String> finalCharacteristic = new ArrayList<String>();
							
							// Find shapeTransformIsRelatedTo and build arrays to make transformations
							if (transformationSemanticNetwork.figure1shapes.get(l).getName().equals(shapeTransformIsRelatedTo.getName())) {
								transformationArray = transformationSemanticNetwork.transformations.get(l);
								finalCharacteristic = transformationSemanticNetwork.finalCharacteristic.get(l);
							}
							
							
							// Make transformations
							for (int m = 0; m < transformationArray.size(); m++) {
								if (transformationArray.get(m).equals("deleted")) {
									tempRelationDiagram.frames.remove(shapeToTransform.getName());
								}
								else {
									if (transformationArray.get(m) == "vreflection") {
										Double tempAngle = tempRelationDiagram.frames.get(shapeToTransform.getName()).angle;
										if (Math.floor(tempAngle/90.0) == 0.0) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).angle = 90.0 + (90.0-tempAngle);
										}
										else if (Math.floor(tempAngle/90.0) == 1.0) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).angle = 90.0 - (tempAngle-90.0);
										}
										else if (Math.floor(tempAngle/90.0) == 2.0) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).angle = 270.0 + (tempAngle+270.0);
										}
										else if (Math.floor(tempAngle/90.0) == 3.0) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).angle = 270.0 - (tempAngle-270.0);
										}
										
									}
									else if (transformationArray.get(m) == "hreflection") {
										Double tempAngle = tempRelationDiagram.frames.get(shapeToTransform.getName()).angle;
										if (Math.floor(tempAngle/180.0) == 0.0) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).angle = 180.0 + (180.0-tempAngle);
										}
										else if (Math.floor(tempAngle/180.0) == 1.0) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).angle = 180.0 - (tempAngle-180.0);
										}
									}
									else if (transformationArray.get(m) == "shape") {
										tempRelationDiagram.frames.get(shapeToTransform.getName()).shape = finalCharacteristic.get(m);
									}
									else if (transformationArray.get(m) == "size") {
										tempRelationDiagram.frames.get(shapeToTransform.getName()).size = finalCharacteristic.get(m);
									}
									else if (transformationArray.get(m) == "fill") {
										tempRelationDiagram.frames.get(shapeToTransform.getName()).fill = finalCharacteristic.get(m);
									}
									else if (transformationArray.get(m) == "angle") {
										tempRelationDiagram.frames.get(shapeToTransform.getName()).angle = Double.parseDouble(finalCharacteristic.get(m));
									}
									else if (transformationArray.get(m) == "vReflectionAlignment") {
										String tempAlign = tempRelationDiagram.frames.get(shapeToTransform.getName()).alignment;
										if (tempAlign.equals("left")) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).alignment = "right";
										}
										else if (tempAlign.equals("right")) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).alignment = "left";
										}
										else if (tempAlign.equals("top-right")) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).alignment = "top-left";
										}
										else if (tempAlign.equals("top-left")) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).alignment = "top-right";
										}
										else if (tempAlign.equals("bottom-right")) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).alignment = "bottom-left";
										}
										else if (tempAlign.equals("bottom-left")) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).alignment = "bottom-right";
										}
									}
									else if (transformationArray.get(m) == "hReflectionAlignment") {
										String tempAlign = tempRelationDiagram.frames.get(shapeToTransform.getName()).alignment;
										if (tempAlign.equals("top")) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).alignment = "bottom";
										}
										else if (tempAlign.equals("bottom")) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).alignment = "top";
										}
										else if (tempAlign.equals("top-right")) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).alignment = "bottom-right";
										}
										else if (tempAlign.equals("top-left")) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).alignment = "bottom-left";
										}
										else if (tempAlign.equals("bottom-right")) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).alignment = "top-right";
										}
										else if (tempAlign.equals("bottom-left")) {
											tempRelationDiagram.frames.get(shapeToTransform.getName()).alignment = "top-left";
										}
									} 
									else if (transformationArray.get(m) == "alignment") {
										tempRelationDiagram.frames.get(shapeToTransform.getName()).alignment = finalCharacteristic.get(m);
									}
								}
								
							}
						}
							
						for (int l = 0; l < transformationSemanticNetwork.addedShapes.size(); l++) {
							tempRelationDiagram.objects.put(transformationSemanticNetwork.addedShapes.get(l).getName(), transformationSemanticNetwork.addedShapes.get(l));
							
							/*String objectName = transformationSemanticNetwork.addedShapes.get(l).getName();
							
							for(String attributeName : tempRelationDiagram.objects.get(objectName).getAttributes().keySet()) {

								String attributeValue = tempRelationDiagram.objects.get(objectName).getAttributes().get(attributeName);
								
								if ((attributeName.equals("shape") == false)
										& (attributeName.equals("angle") == false)
										& (attributeName.equals("fill") == false)
										& (attributeName.equals("size") == false)
										& (attributeName.equals("alignment") == false)) {
									
									if (attributeValue.length() > 1) {
										attributeValue = attributeValue.replace(",", "");
									}

									
									for (int m = 0; i < attributeValue.length(); i++){
									    Character c = attributeValue.charAt(i);
									    String cString = c.toString();
									    
										Integer relationObjectIndex = tempRelationDiagram.objectArray.indexOf(cString);
										
										this.relations[currObjectIndex][relationObjectIndex] = attributeName;
										
										
										
									}
									
								}*/
							
						}
					}
				}
				
			}
			
			
			
			possibleAnswersRelationDiagram.add(tempRelationDiagram);
    	}
    	
    	
    	
    	return possibleAnswersRelationDiagram;
    }
    
    
    // SQL-style coalesce function. Return first non-null argument when given two inputs.
    public static <T> T coalesce(T a, T b) {
        return a == null ? b : a;
    }
    
    
}
