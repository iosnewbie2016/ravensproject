package ravensproject;

import java.util.ArrayList;
import java.util.List;

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
    		List<SemanticNetwork> possibleSemanticNetworksAB = buildSemanticNetwork(figureARelationDiagram, figureBRelationDiagram);
    		
    		
    		// A - C comparisons
    		List<SemanticNetwork> possibleSemanticNetworksAC = buildSemanticNetwork(figureARelationDiagram, figureCRelationDiagram);
    		
    		
    		List<RelationDiagram> possibleHorizontalAnswers = buildAnswerRelationDiagram(possibleSemanticNetworksAC, possibleSemanticNetworksAB, figureCRelationDiagram);
    		List<RelationDiagram> possibleVerticalAnswers = buildAnswerRelationDiagram(possibleSemanticNetworksAB, possibleSemanticNetworksAC, figureBRelationDiagram);
    		
    		List<RelationDiagram> chosenAnswersToCompare = new ArrayList<RelationDiagram>();
    		
    		Integer horizontalBestAnswerIndex = -1;
    		Integer verticalBestAnswerIndex = -1;
    		
    		Integer leastDifference = 9999;
    		
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
    		
    		leastDifference = 9999;
    		
    		
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
    
    
    
    public List<SemanticNetwork> buildSemanticNetwork(RelationDiagram figureARelationDiagram, RelationDiagram figureBRelationDiagram) {
    	Integer numShapesA = figureARelationDiagram.numShapes;
		Integer numShapesB = figureBRelationDiagram.numShapes;
		
		Integer numShapesDeleted = figureARelationDiagram.numShapes - figureBRelationDiagram.numShapes;
		
		
		List<SemanticNetwork> possibleSemanticNetworks = new ArrayList<SemanticNetwork>();
		
		
		// EASY CASE: SAME NUMBER OF SHAPES
		if (numShapesDeleted == 0) {
			Integer numRelationshipMatches = 0;
			
			
			// FIGURE A has more than one shape
			if (figureARelationDiagram.numShapes > 1) {
				for (int i = 0; i < figureARelationDiagram.relationType.size(); i ++) {
    				
    				if (figureARelationDiagram.relationType.get(i) == figureBRelationDiagram.relationType.get(i)) {
    					
    					
    					numRelationshipMatches++;
    				}
				}	
			
			}
			
			// Both FIGURE A and FIGURE B have only one shape
			else if (figureBRelationDiagram.numShapes == 1) {
				SemanticNetwork semanticNetwork = new SemanticNetwork(figureARelationDiagram, figureBRelationDiagram, "2x2");
				
				
				
				semanticNetwork.figure1shapes = figureARelationDiagram.ravensObjectArray;
				semanticNetwork.figure2shapes = figureBRelationDiagram.ravensObjectArray;
				
				// Add transformations that occurred to Semantic Network
				List<String> transformations = new ArrayList<String>();
				List<String> finalCharacteristic = new ArrayList<String>();
				
				String shapeB = coalesce(figureBRelationDiagram.objects.get(figureBRelationDiagram.objectArray.get(0)).getAttributes().get("shape"),"null");
				
				if (coalesce(figureBRelationDiagram.objects.get(figureBRelationDiagram.objectArray.get(0)).getAttributes().get("shape"),"null").equals(coalesce(figureARelationDiagram.objects.get(figureARelationDiagram.objectArray.get(0)).getAttributes().get("shape"), "null")) == false) {
					
					transformations.add("shape");
					finalCharacteristic.add(figureBRelationDiagram.objects.get(figureBRelationDiagram.objectArray.get(0)).getAttributes().get("shape"));
				}
				else if (coalesce(figureBRelationDiagram.objects.get(figureBRelationDiagram.objectArray.get(0)).getAttributes().get("fill"),"null").equals(coalesce(figureARelationDiagram.objects.get(figureARelationDiagram.objectArray.get(0)).getAttributes().get("fill"), "null")) == false) {
					
					transformations.add("fill");
					finalCharacteristic.add(figureBRelationDiagram.objects.get(figureBRelationDiagram.objectArray.get(0)).getAttributes().get("fill"));
				}
				else if (coalesce(figureBRelationDiagram.objects.get(figureBRelationDiagram.objectArray.get(0)).getAttributes().get("angle"),"null").equals(coalesce(figureARelationDiagram.objects.get(figureARelationDiagram.objectArray.get(0)).getAttributes().get("angle"), "null")) == false) {
					
					transformations.add("angle");
					finalCharacteristic.add(figureBRelationDiagram.objects.get(figureBRelationDiagram.objectArray.get(0)).getAttributes().get("angle"));
				}
				else if (coalesce(figureBRelationDiagram.objects.get(figureBRelationDiagram.objectArray.get(0)).getAttributes().get("size"),"null").equals(coalesce(figureARelationDiagram.objects.get(figureARelationDiagram.objectArray.get(0)).getAttributes().get("size"), "null")) == false) {
					
					transformations.add("size");
					finalCharacteristic.add(figureBRelationDiagram.objects.get(figureBRelationDiagram.objectArray.get(0)).getAttributes().get("size"));
				}
				else if (coalesce(figureBRelationDiagram.objects.get(figureBRelationDiagram.objectArray.get(0)).getAttributes().get("alignment"),"null").equals(coalesce(figureARelationDiagram.objects.get(figureARelationDiagram.objectArray.get(0)).getAttributes().get("alignment"), "null")) == false) {
					
					transformations.add("alignment");
					finalCharacteristic.add(figureBRelationDiagram.objects.get(figureBRelationDiagram.objectArray.get(0)).getAttributes().get("alignment"));
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
		
		}
		
		
		return possibleSemanticNetworks;
    }
    
    
    public List<RelationDiagram> buildAnswerRelationDiagram(List<SemanticNetwork> relationSemanticNetworkArray, List<SemanticNetwork> transformationSemanticNetworkArray, RelationDiagram initialRelationDiagram) {
    	List<RelationDiagram> possibleAnswersRelationDiagram = new ArrayList<RelationDiagram>();
    	
    	for (int i = 0; i < relationSemanticNetworkArray.size(); i++) {
			SemanticNetwork relationSemanticNetwork = relationSemanticNetworkArray.get(i);
			
			for (int j = 0; j < relationSemanticNetwork.figure2shapes.size(); j++) {
				RavensObject shapeToTransform = relationSemanticNetwork.figure2shapes.get(j);
				RavensObject shapeTransformIsRelatedTo = relationSemanticNetwork.figure1shapes.get(j);
				List<String> transformationArray = new ArrayList<String>();
				List<String> finalCharacteristic = new ArrayList<String>();
				
				
				for (int k = 0; k < transformationSemanticNetworkArray.size(); k++) {
					SemanticNetwork transformationSemanticNetwork = transformationSemanticNetworkArray.get(k);
					for (int l = 0; l < transformationSemanticNetwork.figure1shapes.size(); l++) {
						if (transformationSemanticNetwork.figure1shapes.get(l).getName().equals(shapeTransformIsRelatedTo.getName())) {
							transformationArray = transformationSemanticNetwork.transformations.get(l);
							finalCharacteristic = transformationSemanticNetwork.finalCharacteristic.get(l);
						}
						
						RelationDiagram tempRelationDiagram = initialRelationDiagram;
						
						for (int m = 0; m < transformationArray.size(); m++) {
							tempRelationDiagram.objects.get(shapeToTransform.getName()).getAttributes().put(transformationArray.get(m), finalCharacteristic.get(m));
						}
						
						possibleAnswersRelationDiagram.add(tempRelationDiagram);
						
					}
				}
			}
    	}
    	
    	
    	return possibleAnswersRelationDiagram;
    }
    
    
    // SQL-style coalesce function. Return first non-null argument when given two inputs.
    public static <T> T coalesce(T a, T b) {
        return a == null ? b : a;
    }
    
    
}
