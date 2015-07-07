package ravensproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// Uncomment these lines to access image processing.
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

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
public class Agent2 {
    /**
     * The default constructor for your Agent. Make sure to execute any
     * processing necessary before your Agent starts solving problems here.
     * 
     * Do not add any variables to this signature; they will not be used by
     * main().
     * 
     */
	
	
    public Agent2() {
        
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
    	
    	int selectedAnswer = -1;
    	
    	//RED = -4777216
    	//GREEN = -8000000
    	//BLUE = -12999999
    	
    
    	
    	Integer numAnswerChoices = 0;
    	ArrayList<Integer> answersToCompare = new ArrayList<Integer>();
    	if (problem.getProblemType().equals("2x2")) {
    		BufferedImage figureAImage = null;
        	BufferedImage figureBImage = null;
        	BufferedImage figureCImage = null;
    		
	    	RavensFigure figureA = problem.getFigures().get("A");
	    	RavensFigure figureB = problem.getFigures().get("B");
	    	RavensFigure figureC = problem.getFigures().get("C");
	    	
	    	numAnswerChoices = 6;
	    	for (int i = 1; i < numAnswerChoices+1; i++) {
	    		answersToCompare.add(i);
	    	}
	    	
	    	try { // Required by Java for ImageIO.read
	    		figureAImage = ImageIO.read(new File(figureA.getVisual()));
	    		figureBImage = ImageIO.read(new File(figureB.getVisual()));
	    		figureCImage = ImageIO.read(new File(figureC.getVisual()));
	    	} catch(Exception ex) {}
	    	
	    	
	    	
	    	
	    	ArrayList<ArrayList <Double>> distanceArrays = compareProcess(figureAImage, 
	    			figureBImage, figureCImage, problem, answersToCompare, 1);
	    	
	    	ArrayList<Integer> possibleAnswers = minDistance(distanceArrays, answersToCompare, 1);
	    	
	    	if (possibleAnswers.size() == 0) {
	    		System.out.println(problem.getName() + " " + problem.getProblemType() + " SKIPPED");
	    		return -1;
	    	}
	    	else if (possibleAnswers.size() == 1) {
	    		System.out.println(problem.getName() + " " + problem.getProblemType() + " Answer: " + possibleAnswers.get(0));
	    		return possibleAnswers.get(0);
	    	}
	    	else {

	    		ArrayList<ArrayList <Double>> distanceArraysReflect = compareProcess(figureAImage, 
		    			figureBImage, figureCImage, problem, possibleAnswers, 2);
	    		
	    		ArrayList<Integer> possibleAnswersReflect = minDistance(distanceArraysReflect, possibleAnswers, 2);
	    		
	    		System.out.println(problem.getName() + " " + problem.getProblemType() + " Answer: " + possibleAnswersReflect.get(0));
	    	
	    		return  possibleAnswersReflect.get(0);
	    	}
	    	
    	}
    	else {
    		BufferedImage figureAImage = null;
        	BufferedImage figureBImage = null;
        	BufferedImage figureCImage = null;
        	BufferedImage figureDImage = null;
        	BufferedImage figureEImage = null;
        	BufferedImage figureFImage = null;
        	BufferedImage figureGImage = null;
        	BufferedImage figureHImage = null;
    		
    		
    		RavensFigure figureA = problem.getFigures().get("A");
	    	RavensFigure figureB = problem.getFigures().get("B");
	    	RavensFigure figureC = problem.getFigures().get("C");
	    	RavensFigure figureD = problem.getFigures().get("D");
	    	RavensFigure figureE = problem.getFigures().get("E");
	    	RavensFigure figureF = problem.getFigures().get("F");
	    	RavensFigure figureG = problem.getFigures().get("G");
	    	RavensFigure figureH = problem.getFigures().get("H");
	    	
	    	numAnswerChoices = 8;
	    	for (int i = 1; i < numAnswerChoices+1; i++) {
	    		answersToCompare.add(i);
	    	}
	    	
	    	try { // Required by Java for ImageIO.read
	    		figureAImage = ImageIO.read(new File(figureA.getVisual()));
	    		figureBImage = ImageIO.read(new File(figureB.getVisual()));
	    		figureCImage = ImageIO.read(new File(figureC.getVisual()));
	    		figureDImage = ImageIO.read(new File(figureD.getVisual()));
	    		figureEImage = ImageIO.read(new File(figureE.getVisual()));
	    		figureFImage = ImageIO.read(new File(figureF.getVisual()));
	    		figureGImage = ImageIO.read(new File(figureG.getVisual()));
	    		figureHImage = ImageIO.read(new File(figureH.getVisual()));
	    	} catch(Exception ex) {}
	    	
	    	ArrayList<ArrayList <Double>> distanceArraysACG = compareProcess(figureAImage, 
	    			figureCImage, figureGImage, problem, answersToCompare, 1);
	    	
	    	ArrayList<ArrayList <Double>> distanceArraysBCH = compareProcess(figureBImage, 
	    			figureCImage, figureHImage, problem, answersToCompare, 1);
	    	
	    	ArrayList<ArrayList <Double>> distanceArraysDFG = compareProcess(figureDImage, 
	    			figureFImage, figureGImage, problem, answersToCompare, 1);
	    	
	    	ArrayList<ArrayList <Double>> distanceArraysEFH = compareProcess(figureEImage, 
	    			figureFImage, figureHImage, problem, answersToCompare, 1);
	    	
	    	ArrayList<ArrayList <Double>> distanceArrays = new ArrayList<ArrayList <Double>>();
	    	
	    	for (int i = 0; i < 2; i++) {
	    		distanceArrays.add(distanceArraysACG.get(i));
	    		distanceArrays.add(distanceArraysBCH.get(i));
	    		distanceArrays.add(distanceArraysDFG.get(i));
	    		distanceArrays.add(distanceArraysEFH.get(i));
	    	}
	    	
	    	
	    	ArrayList<Integer> possibleAnswers = minDistance(distanceArrays, answersToCompare, 1);
	    	
	    	if (possibleAnswers.size() == 0) {
	    		System.out.println(problem.getName() + " " + problem.getProblemType() + " SKIPPED");
	    		return -1;
	    	}
	    	else if (possibleAnswers.size() == 1) {
	    		System.out.println(problem.getName() + " " + problem.getProblemType() + " Answer: " + possibleAnswers.get(0));
	    		return possibleAnswers.get(0);
	    	}
	    	else {

	    		ArrayList<ArrayList <Double>> distanceArraysACGReflect = compareProcess(figureAImage, 
		    			figureCImage, figureGImage, problem, possibleAnswers, 1);
		    	
		    	ArrayList<ArrayList <Double>> distanceArraysBCHReflect = compareProcess(figureBImage, 
		    			figureCImage, figureHImage, problem, possibleAnswers, 1);
		    	
		    	ArrayList<ArrayList <Double>> distanceArraysDFGReflect = compareProcess(figureDImage, 
		    			figureFImage, figureGImage, problem, possibleAnswers, 1);
		    	
		    	ArrayList<ArrayList <Double>> distanceArraysEFHReflect = compareProcess(figureEImage, 
		    			figureFImage, figureHImage, problem, possibleAnswers, 1);
		    	
		    	ArrayList<ArrayList <Double>> distanceArraysReflect = new ArrayList<ArrayList <Double>>();
		    	
		    	for (int i = 0; i < 2; i++) {
		    		distanceArraysReflect.add(distanceArraysACGReflect.get(i));
		    		distanceArraysReflect.add(distanceArraysBCHReflect.get(i));
		    		distanceArraysReflect.add(distanceArraysDFGReflect.get(i));
		    		distanceArraysReflect.add(distanceArraysEFHReflect.get(i));
		    	}
		    	
		    	ArrayList<Integer> possibleAnswersReflect = minDistance(distanceArraysReflect, possibleAnswers, 2);
	    		
	    		System.out.println(problem.getName() + " " + problem.getProblemType() + " Answer: " + possibleAnswersReflect.get(0));
	    		
	    		return  possibleAnswersReflect.get(0);
	    	}
	    	
    	}
    	

    	
    	//ArrayList<Integer> figureANumPixels = numPixelAnalysis(figureAImage);
    	//ArrayList<Integer> figureBNumPixels = numPixelAnalysis(figureBImage);
    	//ArrayList<Integer> figureCNumPixels = numPixelAnalysis(figureCImage);
    	
    	/////////////////////////////////////////////////////////////////////////////
    	// TESTING OF MOST FUNCTIONS
    	/*BufferedImage reflectHorFigureA = reflectImageHorizontally(figureAImage);
    	BufferedImage reflectVerFigureA = reflectImageVertically(figureAImage);
    	BufferedImage rotate90FigureA = rotate90degrees(figureAImage,1);*/
    	//BufferedImage subAFromBFigureA = subtractPixels(figureAImage, figureBImage);
    	
	
    	/*File fileFigureA = new File("fileFigureA.png");
    	File fileFigureB = new File("fileFigureB.png");
    	File fileFigureHorA = new File("reflectHorFileFigureA.png");
    	File fileFigureVerA = new File("reflectVerFileFigureA.png");
    	File fileFigureRot90A = new File("rotate90FileFigureA.png");
    	File fileFigureSubAFromB = new File("subAFromBFileFigure.png");*/
    	//File fileObjectsFigureAs = new File("objectsFigureA.png");
    	//END TEST
    	/////////////////////////////////////////////////////////////////////////////

    	
    	
    	
    	
    	
    	
    	
    	
    	/*System.out.println("RGB AB" + rgbPerAB);
    	System.out.println("RGB C1" + rgbPerC1);
    	System.out.println("RGB C2" + rgbPerC2);
    	System.out.println("RGB C3" + rgbPerC3);
    	System.out.println("RGB C4" + rgbPerC4);
    	System.out.println("RGB C5" + rgbPerC5);
    	System.out.println("RGB C6" + rgbPerC6);*/
    	
    	
    	
    	
    	/*try {
    		ImageIO.write(figureAImage, "png", fileFigureA);
    		ImageIO.write(figureBImage, "png", fileFigureB);
    		ImageIO.write(reflectHorFigureA, "png", fileFigureHorA);
			ImageIO.write(reflectVerFigureA, "png", fileFigureVerA);
			ImageIO.write(rotate90FigureA, "png", fileFigureRot90A);
			ImageIO.write(subAFromBFigureA, "png", fileFigureSubAFromB);
			ImageIO.write(overlayPixelsFigureA, "png", fileFigureOverlayPixels);
		} catch (IOException e) {}
    	
    	
    	ArrayList <BufferedImage> objectsFigureA = separateFigures(figureAImage);
    	
    	// Write each image separated one by one
    	for (int i = 0; i < objectsFigureA.size(); i++) {
    		try {
        		ImageIO.write(objectsFigureA.get(i), "png", fileObjectsFigureAs);
    		} catch (IOException e) {}
    	}/*
    	
    	
    	/*System.out.println(figureANumPixels);
    	System.out.println(figureBNumPixels);
    	System.out.println(figureCNumPixels);*/


    }
 
    
    
    // OVERLAY, REMOVE TRAILING, COMPARE DISTANCES.
    // TYPE: 1 = normal
    //       2 = reflected
    public ArrayList<ArrayList<Double>> compareProcess(BufferedImage figureAImage, BufferedImage figureBImage, 
    		BufferedImage figureCImage, RavensProblem problem, ArrayList<Integer> answersToCompare, Integer type) {
    	
    	BufferedImage overlayPixelsAB = null;
    	BufferedImage overlayPixelsAC = null;
    	
    	ArrayList<Double> rgbPerAB = new ArrayList<Double>();
    	ArrayList<Double> rgbPerAC = new ArrayList<Double>();
    	
    	BufferedImage reflectVerFigureImageA = reflectImageVertically(figureAImage);
		BufferedImage reflectHorFigureImageA = reflectImageHorizontally(figureAImage);   		
		
		BufferedImage overlayPixelsABReflect = overlayPixelsHighlight(reflectVerFigureImageA, figureBImage, "RGB");
		BufferedImage overlayPixelsACReflect = overlayPixelsHighlight(reflectHorFigureImageA, figureCImage, "RGB");
    	
		overlayPixelsABReflect = removeTrailingPixels(overlayPixelsABReflect);
    	overlayPixelsACReflect = removeTrailingPixels(overlayPixelsACReflect);
    	
    	ArrayList<Double> rgbPerABReflect = percentRGBtoTotalRGBOneImage(overlayPixelsABReflect, "GB");
    	ArrayList<Double> rgbPerACReflect = percentRGBtoTotalRGBOneImage(overlayPixelsACReflect, "GB");
    	
    	Double sumPercentReflect = 0.0;
    	for (int i = 0; i < rgbPerABReflect.size(); i++) {
    		sumPercentReflect+=rgbPerABReflect.get(i);
    		sumPercentReflect+=rgbPerACReflect.get(i);
    	}
    	
    	
    	if (type == 2 | sumPercentReflect < 0.01) {
    		overlayPixelsAB = overlayPixelsABReflect;
    		overlayPixelsAC = overlayPixelsACReflect;
    		
    		rgbPerAB = rgbPerABReflect;
    		rgbPerAC = rgbPerACReflect;
    	}
    	else if (type == 1) {
    		overlayPixelsAB = overlayPixelsHighlight(figureAImage, figureBImage, "RGB");
    		overlayPixelsAC = overlayPixelsHighlight(figureAImage, figureCImage, "RGB");
    		
    		overlayPixelsAB = removeTrailingPixels(overlayPixelsAB);
        	overlayPixelsAC = removeTrailingPixels(overlayPixelsAC);
        	
        	rgbPerAB = percentRGBtoTotalRGBOneImage(overlayPixelsAB, "GB");
        	rgbPerAC = percentRGBtoTotalRGBOneImage(overlayPixelsAC, "GB");
        	
    	}
    	
    	
    	
    	
    	File fileFigureOverlayPixelsAB = new File(problem.getName() + "/overlayPixelsAB.png");
    	File fileFigureOverlayPixelsAC = new File(problem.getName() + "/overlayPixelsAC.png");

    	
    	try {
    		ImageIO.write(overlayPixelsAB, "png", fileFigureOverlayPixelsAB);
    		ImageIO.write(overlayPixelsAC, "png", fileFigureOverlayPixelsAC);
		} catch (IOException e) {}

    	
    	
    	
    	
    	
    	ArrayList<BufferedImage> objectsA = separateObjects(figureAImage, "RGB", 1);
    	ArrayList<BufferedImage> objectsB = separateObjects(figureBImage, "RGB", 1);
    	ArrayList<BufferedImage> objectsC = separateObjects(figureCImage, "RGB", 1);
    	
    	Integer numObjectsA = objectsA.size();
    	Integer numObjectsB = objectsB.size();
    	Integer numObjectsC = objectsC.size();
    	
    	Integer diffNumObjectsAB = numObjectsB-numObjectsA;
    	Integer diffNumObjectsAC = numObjectsC-numObjectsA;
    	
    	ArrayList <Double> distanceArrayCAns = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayBAns = new ArrayList <Double>();
    	ArrayList <ArrayList <Double>> rgbPerCArray = new ArrayList <ArrayList <Double>>();
    	ArrayList <ArrayList <Double>> rgbPerBArray = new ArrayList <ArrayList <Double>>();
    	
    	ArrayList <Integer> diffNumObjectsArrayCAns = new ArrayList <Integer>();
    	ArrayList <Integer> diffNumObjectsArrayBAns = new ArrayList <Integer>();
    	
    	
    	
    			
    	// Iterate through 6 answer choices and populate percentages of RGB pixels 
    	for (Integer i = 1; i < answersToCompare.size()+1; i++) {
    		
    		Integer currI = answersToCompare.get(i-1);
    		
    		RavensFigure figure = problem.getFigures().get(currI.toString());
    		BufferedImage figureImage = null;
    		try {
				figureImage = ImageIO.read(new File(figure.getVisual()));
			} catch (IOException e) {}
    		
    		//Generate png files to study
    		BufferedImage overlayPixelsCAns = null;
        	BufferedImage overlayPixelsBAns = null;
        	
        	
        	if (type == 2 | sumPercentReflect < 0.01) {
        		overlayPixelsCAns = overlayPixelsHighlight(reflectImageVertically(figureCImage), figureImage, "RGB");
        		overlayPixelsBAns = overlayPixelsHighlight(reflectImageHorizontally(figureBImage), figureImage, "RGB");
        	}
        	else if (type == 1) {
        		overlayPixelsCAns = overlayPixelsHighlight(figureCImage, figureImage, "RGB");
        		overlayPixelsBAns = overlayPixelsHighlight(figureBImage, figureImage, "RGB");
        	}

    		overlayPixelsCAns = removeTrailingPixels(overlayPixelsCAns);
    		overlayPixelsBAns = removeTrailingPixels(overlayPixelsBAns);
    		
    		File fileOverlayPixelsCAns = new File(problem.getName() + "/overlayPixelsC" + currI + ".png");
    		File fileOverlayPixelsBAns = new File(problem.getName() + "/overlayPixelsB" + currI + ".png");
    		
    		try {
        		ImageIO.write(overlayPixelsCAns, "png", fileOverlayPixelsCAns);
        		ImageIO.write(overlayPixelsBAns, "png", fileOverlayPixelsBAns);
    		} catch (IOException e) {}
    		
    		// Percentages of pixels between problem and answers.
    		//ArrayList<Double> rgbPerC = percentRGBtoTotalRGB(figureCImage, figureImage, "RGB");
    		//ArrayList<Double> rgbPerB = percentRGBtoTotalRGB(figureBImage, figureImage, "RGB");
    		ArrayList<Double> rgbPerC = percentRGBtoTotalRGBOneImage(overlayPixelsCAns, "GB");
    		ArrayList<Double> rgbPerB = percentRGBtoTotalRGBOneImage(overlayPixelsBAns, "GB");
    		
    		rgbPerCArray.add(rgbPerC);
    		rgbPerBArray.add(rgbPerB);
    		
    		//Distance between AB<->CAns & AC<->BAns
    		Double distanceAB_CAns = distance(rgbPerAB, rgbPerC);
    		Double distanceAC_BAns = distance(rgbPerAC, rgbPerB);
    		
    		
    		distanceArrayCAns.add(distanceAB_CAns);
    		distanceArrayBAns.add(distanceAC_BAns);
    		
    		
    		ArrayList<BufferedImage> objects = separateObjects(figureImage, "RGB", 1);
        	Integer numObjects = objects.size();

        	
        	Integer diffNumObjectsCAns = numObjects-numObjectsC;
        	Integer diffNumObjectsBAns = numObjects-numObjectsB;
    		
        	
        	diffNumObjectsArrayCAns.add(diffNumObjectsCAns);
        	diffNumObjectsArrayBAns.add(diffNumObjectsBAns);
    		

        	 
    	
    	}
    	
    	try {
    		
    		
            PrintWriter results = new PrintWriter(problem.getName() + "/ProblemResults.txt");                                                                  
            
            
        	results.println("Percent AB Overlay: " + rgbPerAB);
            results.println("Percent AC Overlay: " + rgbPerAB);
            results.println("");
            
            for (Integer i = 0; i < distanceArrayCAns.size(); i++) {
            	Integer currI = answersToCompare.get(i);
            	
            	results.println("Percentages C_Ans " + currI + ": " + rgbPerCArray.get(i));
		    	results.println("Distance AB_CAns " + currI + ": " + distanceArrayCAns.get(i));
		    	results.println("");
		    	results.println("Percentages B_Ans " + currI + ": " + rgbPerBArray.get(i));
		    	results.println("Distance AC_BAns " + currI + ": " + distanceArrayBAns.get(i));
		    	Double avg = (distanceArrayCAns.get(i)+distanceArrayBAns.get(i))/2;
		    	results.println("Average: " + avg);
		    	results.println("");
		    	results.println("");
            }
	    	results.close();
        	} catch(IOException ex) {
                System.out.println("Unable to create results file:");
                System.out.println(ex);
            }
    	
    	ArrayList<ArrayList<Double>> distanceArrays = new ArrayList<ArrayList<Double>>();
    	distanceArrays.add(distanceArrayCAns);
    	distanceArrays.add(distanceArrayBAns);
    	
    	return distanceArrays;
    	
    }
    
    // Calculate min distance from array of arrays of distances
    public ArrayList<Integer> minDistance(ArrayList<ArrayList<Double>> distanceArrays, 
    		ArrayList<Integer> answersToCompare, Integer type) {
    	
    	int minDistanceAnswer = -1;
    	Double minDistance = Double.POSITIVE_INFINITY;
    	Double minDiffObjects = Double.POSITIVE_INFINITY;
    	
    	ArrayList<Integer> possibleAnswers = new ArrayList<Integer>();
    	
    	ArrayList<Double> avgDistances = new ArrayList<Double>();
    	
    	for (Integer i = 0; i < distanceArrays.get(0).size(); i++) {
    		
    		Double sumDistance = 0.0;
    		
    		for (Integer j = 0; j < distanceArrays.size(); j++) {
    			sumDistance += distanceArrays.get(j).get(i);  			
    		}
    		

    		Double avgDistance = sumDistance/distanceArrays.size();
    		
    		avgDistances.add(avgDistance);
    	}
    	
    	for (Integer i = 0; i < avgDistances.size(); i++) {
    		
    		
    		//Integer sumDiffNumObjects = diffNumObjectsArrayCAns.get(i) + diffNumObjectsArrayBAns.get(i);
    		//Double avgDiffNumObjects = sumDiffNumObjects.doubleValue()/2;
    		
    		//Integer sumDiffNumObjectsAB_AC = diffNumObjectsAB + diffNumObjectsAC;
    		//Double avgDiffNumObjectsAB_AC = sumDiffNumObjectsAB_AC.doubleValue()/2;
    		
    		
    		//if (avgDiffNumObjects.equals(avgDiffNumObjectsAB_AC)) avgDistance -= 0.05;
    		
    		
    		if ((Math.abs(avgDistances.get(i) - minDistance) < 0.001) & (type ==1)) {
    			possibleAnswers.add(answersToCompare.get(i));
    		}
    		
    		else if (avgDistances.get(i) < minDistance) {
    			for (int j = 0; j < possibleAnswers.size(); j++) {
    				possibleAnswers.remove(0);
    			}
    			minDistance = avgDistances.get(i);
	    		minDistanceAnswer = answersToCompare.get(i);
    			possibleAnswers.add(answersToCompare.get(i));
    		}
    		
    	}
    	
    	if (minDistance > 0.5) {
    		ArrayList<Integer> emptyArray = new ArrayList<Integer>();
    		return emptyArray;
    	}
    	else return possibleAnswers;
    }
    
    // FUNCTIONS//////////////////////////////////////////////////////////////////////////////
    
    // Return array of Integers. First element is number of white pixels in image. 
    // Second is number of black pixels in image. 
    public ArrayList<Integer> numPixelAnalysis(BufferedImage image) {
    	ArrayList<Integer> pixelNumArray = new ArrayList<Integer>();
    	Integer whitePixels = 0;
    	Integer blackPixels = 0;
    	for(int i = 0 ; i < image.getWidth() ; i++) {
    		for(int j = 0 ; j < image.getHeight() ; j++) {
    			Integer pixelRGB = image.getRGB(i, j);
    			if (pixelRGB != -1) blackPixels++;
    			else whitePixels++;    					
    		}
    	}
    	pixelNumArray.add(whitePixels);
    	pixelNumArray.add(blackPixels);
    	
    	return pixelNumArray;
    }
    
    
    // Return number of pixels of a specific color
    public Integer numColorPixels(BufferedImage image, Integer color) {
    	Integer numPixels = 0;
    	for(int i = 0 ; i < image.getWidth() ; i++) {
    		for(int j = 0 ; j < image.getHeight() ; j++) {
    			//Integer pixelRGB = image.getRGB(i, j);
    			if (image.getRGB(i, j) == color) numPixels++; 					
    		}
    	}
    	
    	return numPixels;
    }
    
    
    // Remove pixels of a specific color (make them white)
    public BufferedImage removeColorPixels(BufferedImage image, Integer color) {
 
    	for(int i = 0 ; i < image.getWidth() ; i++) {
    		for(int j = 0 ; j < image.getHeight() ; j++) {
    			Integer pixelRGB = image.getRGB(i, j);
    			if (pixelRGB == color) image.setRGB(i, j, -1);; 					
    		}
    	}
    	
    	return image;
    }
    
    
    // Return number of pixels other than a specific color
    public Integer numOtherColorPixels(BufferedImage image, Integer color) {
    	Integer numPixels = 0;
    	for(int i = 0 ; i < image.getWidth() ; i++) {
    		for(int j = 0 ; j < image.getHeight() ; j++) {
    			Integer pixelRGB = image.getRGB(i, j);
    			if (pixelRGB != color) numPixels++; 					
    		}
    	}
    	
    	return numPixels;
    }
    
    
    // Return percent of pixels of a specific color to entire figure
    public Double percentColorPixels(BufferedImage image, Integer color) {
    	Integer numPixelsColor = 0;
    	Integer totalNumPixels = 0;
    	for(int i = 0 ; i < image.getWidth() ; i++) {
    		for(int j = 0 ; j < image.getHeight() ; j++) {
    			totalNumPixels++;
    			Integer pixelRGB = image.getRGB(i, j);
    			if (pixelRGB == color) {
    				numPixelsColor++; 					
    			}
    		}
    	}
    	Double percentReturn = numPixelsColor.doubleValue()/totalNumPixels.doubleValue();
    	return percentReturn;
    }
    
    
    // Returns percent of RGB pixels to total number of pixels in overlayed images
    ArrayList<Double> percentRGB(BufferedImage image1, BufferedImage image2) {
    	
    	Double numRedPixels = 0.0;
    	Double numGreenPixels = 0.0;
    	Double numBluePixels = 0.0;
    	Double numTotalPixels = 0.0;
    	
    	ArrayList<Double> percentArrayReturnArrayList = new ArrayList<Double>();
    	
    	for(int i = 0 ; i < image1.getWidth() ; i++) {
	    	for(int j = 0 ; j < image1.getHeight() ; j++) {
	    		
	    		numTotalPixels++;
	    		
	    		Integer pixelRGB1 = image1.getRGB(i, j);
	    		Integer pixelRGB2 = image2.getRGB(i, j);
	    		
	    		if (pixelRGB1 != -1 & pixelRGB2 != -1) numRedPixels++; //RED
	    		else if (pixelRGB1 != -1) numGreenPixels++; //GREEN
	    		else if (pixelRGB2 != -1) numBluePixels++; //BLUE
	    		
	    		
	    	}
	    		    	
    	}
    	
    	Double percentRedFigure12 = numRedPixels/numTotalPixels; // RED (on both)
    	Double percentGreenFigure12 = numGreenPixels/numTotalPixels; // GREEN (only in A)
    	Double percentBlueFigure12 = numBluePixels/numTotalPixels; // BLUE (only in B)
    	
    	percentArrayReturnArrayList.add(percentRedFigure12);
    	percentArrayReturnArrayList.add(percentGreenFigure12);
    	percentArrayReturnArrayList.add(percentBlueFigure12);
    	
    	return percentArrayReturnArrayList;
    	
    }
    
    
    
    // Returns percent of selected pixels to total number of NON-WHITE pixels in two images
    ArrayList<Double> percentRGBtoTotalRGB(BufferedImage image1, BufferedImage image2, String pixelsAccount) {
    	
    	Double numRedPixels = 0.0;
    	Double numGreenPixels = 0.0;
    	Double numBluePixels = 0.0;
    	Double numTotalPixels = 0.0;
    	Double numTotalRGBPixels = 0.0;
    	
    	ArrayList<Double> percentArrayReturnArrayList = new ArrayList<Double>();
    	
    	for(int i = 0 ; i < image1.getWidth() ; i++) {
	    	for(int j = 0 ; j < image1.getHeight() ; j++) {
	    		
	    		numTotalPixels++;
	    		
	    		Integer pixelRGB1 = image1.getRGB(i, j);
	    		Integer pixelRGB2 = image2.getRGB(i, j);
	    		
	    		if (pixelRGB1 != -1 & pixelRGB2 != -1) numRedPixels++; //RED
	    		else if (pixelRGB1 != -1) numGreenPixels++; //GREEN
	    		else if (pixelRGB2 != -1) numBluePixels++; //BLUE
	    		
	    		
	    	}
	    		    	
    	}
    	
    	numTotalRGBPixels = numRedPixels+numGreenPixels+numBluePixels;
    	
    	Double percentRedFigure12 = numRedPixels/numTotalRGBPixels; // RED (on both)
    	Double percentGreenFigure12 = numGreenPixels/numTotalRGBPixels; // GREEN (only in A)
    	Double percentBlueFigure12 = numBluePixels/numTotalRGBPixels; // BLUE (only in B)
    	
    	
    	for (int i = 0; i < pixelsAccount.length(); i++){
		    Character c = pixelsAccount.charAt(i);
		    String cString = c.toString();
		    
		    if (cString.equals("R")) percentArrayReturnArrayList.add(percentRedFigure12);
		    else if (cString.equals("G")) percentArrayReturnArrayList.add(percentGreenFigure12);
		    else if (cString.equals("B")) percentArrayReturnArrayList.add(percentBlueFigure12);

    	}
    	
    	return percentArrayReturnArrayList;
    	
    }
    
    
    
    
    
    // Returns percent of selected pixels to total number of NON-WHITE pixels in overlayed images
    ArrayList<Double> percentRGBtoTotalRGBOneImage(BufferedImage image, String pixelsAccount) {
    	
    	Double numRedPixels = 0.0;
    	Double numGreenPixels = 0.0;
    	Double numBluePixels = 0.0;
    	Double numTotalPixels = 0.0;
    	Double numTotalRGBPixels = 0.0;
    	
    	ArrayList<Double> percentArrayReturnArrayList = new ArrayList<Double>();
    	
    	for(int i = 0 ; i < image.getWidth() ; i++) {
	    	for(int j = 0 ; j < image.getHeight() ; j++) {
	    		
	    		numTotalPixels++;
	    		
	    		Integer pixelRGB1 = image.getRGB(i, j);
	    		
	    		//RED = -4777216
	        	//GREEN = -8000000
	        	//BLUE = -12999999
	    		
	    		if (image.getRGB(i, j) == -4777216) numRedPixels++; //RED
	    		else if (image.getRGB(i, j) == -8000000) numGreenPixels++; //GREEN
	    		else if (image.getRGB(i, j) == -12999999) numBluePixels++; //BLUE
	    		
	    		
	    	}
	    		    	
    	}
    	
    	
    	for (int i = 0; i < pixelsAccount.length(); i++){
		    Character c = pixelsAccount.charAt(i);
		    String cString = c.toString();
		    
		    if (cString.equals("R")) numTotalRGBPixels+=numRedPixels;
		    else if (cString.equals("G")) numTotalRGBPixels+=numGreenPixels;
		    else if (cString.equals("B")) numTotalRGBPixels+=numBluePixels;

    	}
    	
    	Double percentRedFigure12 = 0.0; // RED (on both)
    	Double percentGreenFigure12 = 0.0; // GREEN (only in A)
    	Double percentBlueFigure12 = 0.0; // BLUE (only in B)
    	
    	if (numTotalRGBPixels.equals(0.0) == false) {
	    	percentRedFigure12 += (numRedPixels/numTotalRGBPixels); // RED (on both)
	    	percentGreenFigure12 += (numGreenPixels/numTotalRGBPixels); // GREEN (only in A)
	    	percentBlueFigure12 += (numBluePixels/numTotalRGBPixels); // BLUE (only in B)
    	}
  	
    	for (int i = 0; i < pixelsAccount.length(); i++){
		    Character c = pixelsAccount.charAt(i);
		    String cString = c.toString();
		    
		    if (cString.equals("R")) percentArrayReturnArrayList.add(percentRedFigure12);
		    else if (cString.equals("G")) percentArrayReturnArrayList.add(percentGreenFigure12);
		    else if (cString.equals("B")) percentArrayReturnArrayList.add(percentBlueFigure12);

    	}
    	
    	return percentArrayReturnArrayList;
    	
    }
    
    
    
    
    
    // Reflect Image Horizontally
    public BufferedImage reflectImageVertically(BufferedImage image) {
    	BufferedImage imageReturn = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    	
    	for(int i = 0 ; i < image.getWidth() ; i++) {
    		for(int j = 0 ; j < image.getHeight() ; j++) {
    			imageReturn.setRGB(image.getWidth()-1-i, j, image.getRGB(i, j));
    		}
    	}
    	
    	return imageReturn;
    }
    
    
    // Reflect Image Vertically
    public BufferedImage reflectImageHorizontally(BufferedImage image) {
    	BufferedImage imageReturn = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    	
    	for(int i = 0 ; i < image.getWidth() ; i++) {
    		for(int j = 0 ; j < image.getHeight() ; j++) {
    			imageReturn.setRGB(i, image.getHeight()-1-j, image.getRGB(i, j));
    		}
    	}
    	
    	return imageReturn;
    }

    
    // Rotate 90 degrees
    public BufferedImage rotate90degrees(BufferedImage image, int numRotations) {
    	BufferedImage imageReturn = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    	
    	for (int r = 0; r < numRotations; r++) {  	
    	
	    	for(int i = 0 ; i < image.getWidth() ; i++) {
	    		for(int j = 0 ; j < image.getHeight() ; j++) {
	    			imageReturn.setRGB(image.getWidth()-1-j, i, image.getRGB(i, j));
	    		}
	    	}
	    	
    	}
    	
    	return imageReturn;
    }
    
    //Subtract one image in another. I.E, take the black pixels of one image and make those same pixels
    //white in the other image to see what black pixels are left
    public BufferedImage subtractPixels(BufferedImage image1, BufferedImage image2) {
    	BufferedImage imageReturn = new BufferedImage(image2.getWidth(), image2.getHeight(), BufferedImage.TYPE_INT_ARGB);
    	
    	for(int i = 0 ; i < image1.getWidth() ; i++) {
	    	for(int j = 0 ; j < image1.getHeight() ; j++) {
	    		imageReturn.setRGB(i,j,image2.getRGB(i, j));
	    		Integer pixelRGB = image1.getRGB(i, j);
	    		if (pixelRGB != -1) {
	    			imageReturn.setRGB(i,j,-1);
	    		}
	    	}
	    		    	
    	}
    	
    	return imageReturn;
    }
    
    
    //Overlay images. I.E. take black pixels of one image and place them in another.
    public BufferedImage overlayPixels(BufferedImage image1, BufferedImage image2) {
    	BufferedImage imageReturn = new BufferedImage(image2.getWidth(), image2.getHeight(), BufferedImage.TYPE_INT_ARGB);

    	for(int i = 0 ; i < image1.getWidth() ; i++) {
	    	for(int j = 0 ; j < image1.getHeight() ; j++) {
	    		imageReturn.setRGB(i,j,image2.getRGB(i, j));
	    		Integer pixelRGB = image1.getRGB(i, j);
	    		if (pixelRGB != -1) {
	    			imageReturn.setRGB(i,j,-16777216);
	    		}
	    	}
	    		    	
    	}
    	
    	return imageReturn;
    }
    
    
    //Overlay images and highlight colors 
    //Pixels from image 1 paint one color.
    //Pixels from image 2, paint another color.
    // Pixels in both images, paint another color
    // Returns actual image
    public BufferedImage overlayPixelsHighlight(BufferedImage image1, BufferedImage image2, String pixelsAccount) {
    	BufferedImage imageReturn = new BufferedImage(image2.getWidth(), image2.getHeight(), BufferedImage.TYPE_INT_ARGB);

    	for(int i = 0 ; i < image1.getWidth() ; i++) {
	    	for(int j = 0 ; j < image1.getHeight() ; j++) {
	    		Integer pixelRGB1 = image1.getRGB(i, j);
	    		Integer pixelRGB2 = image2.getRGB(i, j);
	    		Boolean pixelPainted = false;
	    		
	    		for (int k = 0; k < pixelsAccount.length(); k++){
	    		    Character c = pixelsAccount.charAt(k);
	    		    String cString = c.toString();
	    		    
	    		    if (cString.equals("R")) {
	    		    	if (pixelRGB1 != -1 & pixelRGB2 != -1) {
	    		    		imageReturn.setRGB(i,j,-4777216); //RED
	    		    		pixelPainted = true;
	    		    	}
	    		    }
	    		    else if (cString.equals("G")) {
	    		    	if (pixelRGB1 != -1 & pixelRGB2 == -1) {
	    		    		imageReturn.setRGB(i,j,-8000000); //GREEN
	    		    		pixelPainted = true;
	    		    	}
	    		    }
	    		    else if (cString.equals("B")) {
	    		    	if (pixelRGB1 == -1 & pixelRGB2 != -1) {
	    		    		imageReturn.setRGB(i,j,-12999999); //BLUE
	    		    		pixelPainted = true;
	    		    	}
	    		    }

	        	}
	    		
	    		if (pixelPainted == false) imageReturn.setRGB(i,j,-1);
	    		
	    		
	    		
	    	}
	    		    	
    	}
    	
    	return imageReturn;
    }
    
    
    
    
    
    //Returns array of images: square representation of the object touching all sides 
	//of the image. 
    //Type 1 = Isolate Figure
    // Type 2 = keep all surrounding pixels
    public ArrayList<BufferedImage> separateObjects(BufferedImage image, String pixelAccount, int type) {
    	ArrayList<BufferedImage> figureArray = new ArrayList<BufferedImage>();
    	BufferedImage tempImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    	
    	for(int i = 0 ; i < image.getWidth() ; i++) {
	    	for(int j = 0 ; j < image.getHeight() ; j++) {
	    		tempImage.setRGB(i, j, image.getRGB(i, j));
	    		
	    	}
    	}
    	
    	
    	
    	for(int i = 0 ; i < tempImage.getWidth() ; i++) {
	    	for(int j = 0 ; j < tempImage.getHeight() ; j++) {
	    		Integer pixelRGB = tempImage.getRGB(i, j);
	    		//if (pixelRGB != -1) {
	    		if ((pixelAccount.equals("RGB") & pixelRGB != -1) |
		    				(pixelAccount.equals("RG") & pixelRGB != -1 & pixelRGB != -12999999) |
		    				(pixelAccount.equals("RB") & pixelRGB != -1 & pixelRGB != -8000000)) {
	    			HashMap<String,ArrayList <Integer>> xyPlotPoints = new HashMap<String, ArrayList <Integer>>();
	    			
	    			boolean hasNeighbor = true;
	    			//ArrayList<ArrayList<Integer>> plotPointsToExamine = new ArrayList<ArrayList <Integer>>();
	    			HashMap<String,ArrayList <Integer>> plotPointsToExamine = new HashMap<String, ArrayList <Integer>>();
	    			
	    			Integer currX = i+0;
	    			Integer currY = j+0;
	    			Integer currPixelCol = pixelRGB+0;
	    			ArrayList<Integer> tempBlackPixel = new ArrayList<Integer>();
	    			tempBlackPixel.add(currX);
	    			tempBlackPixel.add(currY);
	    			tempBlackPixel.add(currPixelCol);
					String tempPixelRep = Integer.toString(currX) + Integer.toString(currY);
					plotPointsToExamine.put(tempPixelRep,  tempBlackPixel);
					
					//Used to track minX, maxX, minY, maxY
					Integer minXInt = currX;
					Integer maxXInt = currX;
					Integer minYInt = currY;
					Integer maxYInt = currY;
					String minXKey = tempPixelRep;
					String maxXKey = tempPixelRep;
					String minYKey = tempPixelRep;
					String maxYKey = tempPixelRep;
					
	    			while (hasNeighbor) {
	    				hasNeighbor = false;

	    				// If not on left margin
	    				if (currX > 0) {
	    					//if (tempImage.getRGB(currX-1, currY) != -1) {
	    					if ((pixelAccount.equals("RGB") & tempImage.getRGB(currX-1, currY) != -1) |
	    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX-1, currY) != -1 & tempImage.getRGB(currX-1, currY) != -12999999) |
	    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX-1, currY) != -1 & tempImage.getRGB(currX-1, currY) != -8000000)) {
	    						//hasNeighbor = true;
	    						Integer tempX = currX-1;
	    						Integer tempY = currY+0;
	    						
	    						ArrayList<Integer> blackPixel = new ArrayList<Integer>();
	    						blackPixel.add(tempX);
	    						blackPixel.add(tempY);
	    						blackPixel.add(tempImage.getRGB(tempX, tempY));
	    						String pixelRep = Integer.toString(tempX) + Integer.toString(tempY);
	    						plotPointsToExamine.put(pixelRep,  blackPixel);
	    						
	    						
	    						if (tempX < minXInt) {
	    							minXInt = tempX;
	    							minXKey = pixelRep;
	    						}
	    						if (tempX > maxXInt) {
	    							maxXInt = tempX;
	    							maxXKey = pixelRep;
	    						}
	    						if (tempY < minYInt) {
	    							minYInt = tempY;
	    							minYKey = pixelRep;
	    						}
	    						if (tempY > maxYInt) {
	    							maxYInt = tempY;
	    							maxYKey = pixelRep;
	    						}
	    						
	    					}
	    					// If not on top margin
	    					if (currY > 0) {
	    						//if (tempImage.getRGB(currX-1, currY) != -1) {
		    					if ((pixelAccount.equals("RGB") & tempImage.getRGB(currX-1, currY-1) != -1) |
		    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX-1, currY-1) != -1 & tempImage.getRGB(currX-1, currY-1) != -12999999) |
		    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX-1, currY-1) != -1 & tempImage.getRGB(currX-1, currY-1) != -8000000)) {
		    						//hasNeighbor = true;
		    						
		    						Integer tempX = currX-1;
		    						Integer tempY = currY-1;
		    						
		    						ArrayList<Integer> blackPixel = new ArrayList<Integer>();
		    						blackPixel.add(tempX);
		    						blackPixel.add(tempY);
		    						blackPixel.add(tempImage.getRGB(tempX, tempY));
		    						String pixelRep = Integer.toString(tempX) + Integer.toString(tempY);
		    						plotPointsToExamine.put(pixelRep,  blackPixel);
		    						
		    						
		    						if (tempX < minXInt) {
		    							minXInt = tempX;
		    							minXKey = pixelRep;
		    						}
		    						if (tempX > maxXInt) {
		    							maxXInt = tempX;
		    							maxXKey = pixelRep;
		    						}
		    						if (tempY < minYInt) {
		    							minYInt = tempY;
		    							minYKey = pixelRep;
		    						}
		    						if (tempY > maxYInt) {
		    							maxYInt = tempY;
		    							maxYKey = pixelRep;
		    						}
		    					}
		    					
		    					
		    					
		    				}
	    					// If not on bottom margin
		    				if (currY < tempImage.getHeight()-1) {
		    					//if (tempImage.getRGB(currX-1, currY) != -1) {
		    					if ((pixelAccount.equals("RGB") & tempImage.getRGB(currX-1, currY+1) != -1) |
		    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX-1, currY+1) != -1 & tempImage.getRGB(currX-1, currY+1) != -12999999) |
		    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX-1, currY+1) != -1 & tempImage.getRGB(currX-1, currY+1) != -8000000)) {
		    						//hasNeighbor = true;
		    						Integer tempX = currX-1;
		    						Integer tempY = currY+1;
		    						
		    						
		    						ArrayList<Integer> blackPixel = new ArrayList<Integer>();
		    						blackPixel.add(tempX);
		    						blackPixel.add(tempY);
		    						blackPixel.add(tempImage.getRGB(tempX, tempY));
		    						String pixelRep = Integer.toString(tempX) + Integer.toString(tempY);
		    						plotPointsToExamine.put(pixelRep,  blackPixel);
		    						
		    						
		    						if (tempX < minXInt) {
		    							minXInt = tempX;
		    							minXKey = pixelRep;
		    						}
		    						if (tempX > maxXInt) {
		    							maxYInt = tempX;
		    							maxXKey = pixelRep;
		    						}
		    						if (tempY < minYInt) {
		    							minYInt = tempY;
		    							minYKey = pixelRep;
		    						}
		    						if (tempY > maxYInt) {
		    							maxYInt = tempY;
		    							maxYKey = pixelRep;
		    						}
		    					}
		    				}
	    				}
	    				// If not on right margin
	    				if (currX < tempImage.getWidth()-1) {
	    					//if (tempImage.getRGB(currX-1, currY) != -1) {
	    					if ((pixelAccount.equals("RGB") & tempImage.getRGB(currX+1, currY) != -1) |
	    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX+1, currY) != -1 & tempImage.getRGB(currX+1, currY) != -12999999) |
	    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX+1, currY) != -1 & tempImage.getRGB(currX+1, currY) != -8000000)) {
	    						//hasNeighbor = true;
	    						Integer tempX = currX+1;
	    						Integer tempY = currY+0;
	    						
	    						ArrayList<Integer> blackPixel = new ArrayList<Integer>();
	    						blackPixel.add(tempX);
	    						blackPixel.add(tempY);
	    						blackPixel.add(tempImage.getRGB(tempX, tempY));
	    						String pixelRep = Integer.toString(tempX) + Integer.toString(tempY);
	    						plotPointsToExamine.put(pixelRep,  blackPixel);
	    						
	    						
	    						if (tempX < minXInt) {
	    							minXInt = tempX;
	    							minXKey = pixelRep;
	    						}
	    						if (tempX > maxXInt) {
	    							maxXInt = tempX;
	    							maxXKey = pixelRep;
	    						}
	    						if (tempY < minYInt) {
	    							minYInt = tempY;
	    							minYKey = pixelRep;
	    						}
	    						if (tempY > maxYInt) {
	    							maxYInt = tempY;
	    							maxYKey = pixelRep;
	    						}
	    					}
	    					// If not on top margin
	    					if (currY > 0) {
	    						//if (tempImage.getRGB(currX-1, currY) != -1) {
		    					if ((pixelAccount.equals("RGB") & tempImage.getRGB(currX+1, currY-1) != -1) |
		    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX+1, currY-1) != -1 & tempImage.getRGB(currX+1, currY-1) != -12999999) |
		    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX+1, currY-1) != -1 & tempImage.getRGB(currX+1, currY-1) != -8000000)) {
		    						//hasNeighbor = true;
		    						Integer tempX = currX+1;
		    						Integer tempY = currY-1;
		    						
		    						ArrayList<Integer> blackPixel = new ArrayList<Integer>();
		    						blackPixel.add(tempX);
		    						blackPixel.add(tempY);
		    						blackPixel.add(tempImage.getRGB(tempX, tempY));
		    						String pixelRep = Integer.toString(tempX) + Integer.toString(tempY);
		    						plotPointsToExamine.put(pixelRep,  blackPixel);
		    						
		    						
		    						if (tempX < minXInt) {
		    							minXInt = tempX;
		    							minXKey = pixelRep;
		    						}
		    						if (tempX > maxXInt) {
		    							maxXInt = tempX;
		    							maxXKey = pixelRep;
		    						}
		    						if (tempY < minYInt) {
		    							minYInt = tempY;
		    							minYKey = pixelRep;
		    						}
		    						if (tempY > maxYInt) {
		    							maxYInt = tempY;
		    							maxYKey = pixelRep;
		    						}
		    					}
		    				}
	    					// If not on bottom margin
		    				if (currY < tempImage.getHeight()-1) {
		    					//if (tempImage.getRGB(currX-1, currY) != -1) {
		    					if ((pixelAccount.equals("RGB") & tempImage.getRGB(currX+1, currY+1) != -1) |
		    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX+1, currY+1) != -1 & tempImage.getRGB(currX+1, currY+1) != -12999999) |
		    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX+1, currY+1) != -1 & tempImage.getRGB(currX+1, currY+1) != -8000000)) {
		    						//hasNeighbor = true;
		    						Integer tempX = currX+1;
		    						Integer tempY = currY+1;
		    						
		    						ArrayList<Integer> blackPixel = new ArrayList<Integer>();
		    						blackPixel.add(tempX);
		    						blackPixel.add(tempY);
		    						blackPixel.add(tempImage.getRGB(tempX, tempY));
		    						String pixelRep = Integer.toString(tempX) + Integer.toString(tempY);
		    						plotPointsToExamine.put(pixelRep,  blackPixel);
		    						
		    						
		    						if (tempX < minXInt) {
		    							minXInt = tempX;
		    							minXKey = pixelRep;
		    						}
		    						if (tempX > maxXInt) {
		    							maxXInt = tempX;
		    							maxXKey = pixelRep;
		    						}
		    						if (tempY < minYInt) {
		    							minYInt = tempY;
		    							minYKey = pixelRep;
		    						}
		    						if (tempY > maxYInt) {
		    							maxYInt = tempY;
		    							maxYKey = pixelRep;
		    						}
		    					}
		    				}
	    				}
	    				// If not on top margin
	    				if (currY > 0) {
	    					//if (tempImage.getRGB(currX-1, currY) != -1) {
	    					if ((pixelAccount.equals("RGB") & tempImage.getRGB(currX, currY-1) != -1) |
	    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX, currY-1) != -1 & tempImage.getRGB(currX, currY-1) != -12999999) |
	    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX, currY-1) != -1 & tempImage.getRGB(currX, currY-1) != -8000000)) {
	    						//hasNeighbor = true;
	    						Integer tempX = currX+0;
	    						Integer tempY = currY-1;
	    						
	    						ArrayList<Integer> blackPixel = new ArrayList<Integer>();
	    						blackPixel.add(tempX);
	    						blackPixel.add(tempY);
	    						blackPixel.add(tempImage.getRGB(tempX, tempY));
	    						String pixelRep = Integer.toString(tempX) + Integer.toString(tempY);
	    						plotPointsToExamine.put(pixelRep,  blackPixel);
	    						
	    						
	    						if (tempX < minXInt) {
	    							minXInt = tempX;
	    							minXKey = pixelRep;
	    						}
	    						if (tempX > maxXInt) {
	    							maxXInt = tempX;
	    							maxXKey = pixelRep;
	    						}
	    						if (tempY < minYInt) {
	    							minYInt = tempY;
	    							minYKey = pixelRep;
	    						}
	    						if (tempY > maxYInt) {
	    							maxYInt = tempY;
	    							maxYKey = pixelRep;
	    						}
	    					}
	    				}
	    				// If not on bottom margin
	    				if (currY < tempImage.getHeight()-1) {
	    					//if (tempImage.getRGB(currX-1, currY) != -1) {
	    					if ((pixelAccount.equals("RGB") & tempImage.getRGB(currX, currY+1) != -1) |
	    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX, currY+1) != -1 & tempImage.getRGB(currX, currY+1) != -12999999) |
	    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX, currY+1) != -1 & tempImage.getRGB(currX, currY+1) != -8000000)) {
	    						//hasNeighbor = true;
	    						Integer tempX = currX+0;
	    						Integer tempY = currY+1;
	    						
	    						ArrayList<Integer> blackPixel = new ArrayList<Integer>();
	    						blackPixel.add(tempX);
	    						blackPixel.add(tempY);
	    						blackPixel.add(tempImage.getRGB(tempX, tempY));
	    						String pixelRep = Integer.toString(tempX) + Integer.toString(tempY);
	    						plotPointsToExamine.put(pixelRep,  blackPixel);
	    						
	    						
	    						if (tempX < minXInt) {
	    							minXInt = tempX;
	    							minXKey = pixelRep;
	    						}
	    						if (tempX > maxXInt) {
	    							maxXInt = tempX;
	    							maxXKey = pixelRep;
	    						}
	    						if (tempY < minYInt) {
	    							minYInt = tempY;
	    							minYKey = pixelRep;
	    						}
	    						if (tempY > maxYInt) {
	    							maxYInt = tempY;
	    							maxYKey = pixelRep;
	    						}
	    					}
	    				}
	    			
	    				
	    				
	    				

	    				
	    				/*try {
	    				    Thread.sleep(500);                 //1000 milliseconds is one second.
	    				} catch(InterruptedException ex) {
	    				    Thread.currentThread().interrupt();
	    				}*/
	    				
		    			ArrayList<Integer> blackPixel = new ArrayList<Integer>();
						blackPixel.add(currX);
						blackPixel.add(currY);
						blackPixel.add(tempImage.getRGB(currX, currY));
						String pixelRep = Integer.toString(currX) + Integer.toString(currY);
						xyPlotPoints.put(pixelRep,  blackPixel);
		    			
						plotPointsToExamine.remove(pixelRep);
						
						tempImage.setRGB(currX, currY, -1);
						
						if (plotPointsToExamine.size() > 0) {
							HashMap.Entry<String,ArrayList <Integer>> entry = plotPointsToExamine.entrySet().iterator().next();
							currX = entry.getValue().get(0);
							currY = entry.getValue().get(1);
							hasNeighbor = true;
						}
						
						
	    				
	    			}
	    			
	    			//TODO: Complete this function. Look at xyPlotPoints HashMap, find minX, mminY, maxX, maxY.
		    		//Based on those values create a square representation of the object touching all sides 
		    		//of the image.
		    		
		    		Integer widthObject =  maxXInt-minXInt+1;
		    		Integer heightObject =  maxYInt-minYInt+1;
					
		    		
		    		/*for(int k = 0 ; k < objectImage.getWidth() ; k++) {
		        		for(int l = 0 ; l < objectImage.getHeight() ; l++) {
		        			objectImage.setRGB(k, l, -1);
		        		}
		        	}*/
		    		
		    		if (type == 1) {
		    			// CREATE TRANSPARENT BACKGROUND WITH WIDTH AND HEIGHT OF OBJECT IDENTIFIED 
		    			BufferedImage objectImage = new BufferedImage(widthObject, heightObject, BufferedImage.TYPE_INT_ARGB);
		    			for(String pixelRep : xyPlotPoints.keySet()) {
		    				
			    			ArrayList<Integer> xyArray = xyPlotPoints.get(pixelRep);
			    			Integer newX = xyArray.get(0)-minXInt;
			    			Integer newY = xyArray.get(1)-minYInt;
			    			Integer newPixelColor = xyArray.get(2);
			    			
			    			objectImage.setRGB(newX, newY, newPixelColor);

			    		}
		    			
		    			figureArray.add(objectImage);
		    		}
		    		else if (type == 2) {
		    			// CREATE ALL WHITE IMAGE WITH WIDTH AND HEIGHT OF ORIGINAL FIGURE
		    			BufferedImage objectImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		    			for(int k = 0 ; k < image.getWidth(); k++) {
			        		for(int l = 0 ; l < image.getHeight(); l++) {
			        			objectImage.setRGB(k, l, -1);
			        		}
		    			}
		    			
		    			
		    			for(String pixelRep : xyPlotPoints.keySet()) {
		    				ArrayList<Integer> xyArray = xyPlotPoints.get(pixelRep);
			    			objectImage.setRGB(xyArray.get(0), xyArray.get(1), xyArray.get(2));
			    		}
		    			
		    			figureArray.add(objectImage);
		    		}
		    		
		    		
		    		
		    		
					
		    		
	    		}
	    		
	    		
	
	    	}
    	}
    	
    	return figureArray;
    }
    
    
    
    // Calculate distance between two points. Points represented by arrays. 
    // If arrays are not of equal size, -1 is returned.
    public Double distance(ArrayList<Double> point1, ArrayList<Double> point2) {
    	if (point1.size() == point2.size()) {
    		Double distance = 0.0;
    		for (int i = 0; i <  point1.size(); i++) {
    			distance += (point1.get(i) - point2.get(i))*(point1.get(i) - point2.get(i));
    		}
    		distance = Math.sqrt(distance);
    		return distance;
    	}
    	else {return -1.0;}
    }
    
    
    // Return maximum line weight of a specific color
    public Integer lineWeight(BufferedImage image, Integer color) {
    	
    	Integer maxLineWeight = 0;
    	for(int i = 0 ; i < image.getWidth() ; i++) {
    		
    		Boolean consecutive = false;
    		Integer tempLineWeightHor = 0;
    		Integer tempLineWeightVer = 0;
    		ArrayList<Integer> startPixel = new ArrayList<Integer>();
    		ArrayList<Integer> endPixel = new ArrayList<Integer>();
    		startPixel.add(image.getWidth()-1);
    		startPixel.add(image.getHeight()-1);
    		endPixel.add(image.getWidth()-1);
    		endPixel.add(image.getHeight()-1);
    		
    		Integer tempLineWeightHorPix = 0;
    		for(int j = 0 ; j < image.getHeight() ; j++) {
    			
    			
    			
    			
    			// IF COLOR IS FOUND
    			if (image.getRGB(i, j) == color) {
    				tempLineWeightHorPix++; 	
    				
    				// IF END OF LINE IS REACHED
        			if (j == image.getHeight()-1) {
        				if (consecutive == false) {
    	    				startPixel.set(0, i);
    	    				startPixel.set(1, j);
    	    				consecutive = true;
        				}
        				
        				endPixel.set(0, i);
    	    			endPixel.set(1, j);
    	    			consecutive = false;
        				
    	    			Integer tempLineWeightVerPix = 0;
    	    			for (int k = startPixel.get(1); k <= endPixel.get(1); k++) {
    	    				
    	    				Integer tempLineWeightVerK = 0;
    	    				int x = startPixel.get(0);
    	    				

    	    				while(image.getRGB(x, k) == color) {
    	    					tempLineWeightVerK++;
    	    					x++;
    	    					
    	    					if (x == image.getWidth()) {
    	    						break;
    	    					}
    	    				}
    	    				
    	    				if (tempLineWeightVerK > tempLineWeightVerPix) tempLineWeightVerPix = tempLineWeightVerK;
    	    				
    	    			}
    	    			
    	    			Integer tempLineWeight = Math.min(tempLineWeightHorPix, tempLineWeightVerPix);
    	        		
    	        		if (tempLineWeight > maxLineWeight) maxLineWeight = tempLineWeight;
        			}
        		
    				
        			// 
        			else if (consecutive == false) {
	    				startPixel.set(0, i);
	    				startPixel.set(1, j);
    				}
    				consecutive = true;
    			}
    			
    			// IF COLOR IS NOT FOUND AND IT IS NOT THE END OF THE LINE
    			else {
    				if (consecutive == true) {
    					endPixel.set(0, i);
	    				endPixel.set(1, j-1);
	    				consecutive = false;
    				
	    				Integer tempLineWeightVerPix = 0;
	    				for (int k = startPixel.get(1); k <= endPixel.get(1); k++) {
	    					
	    					Integer tempLineWeightVerK = 0;
	    					int x = startPixel.get(0);
	    					
	    					while(image.getRGB(x, k) == color & x < image.getWidth()) {
	    						tempLineWeightVerK++;
	    						x++;
	    						
	    						if (x == image.getWidth()) {
    	    						break;
    	    					}
	    					}
	    					
	    					if (tempLineWeightVerK > tempLineWeightVerPix) tempLineWeightVerPix = tempLineWeightVerK;
	    					
	    				}
	    				
	    				Integer tempLineWeight = Math.min(tempLineWeightHorPix, tempLineWeightVerPix);
	        			
	        			if (tempLineWeight > maxLineWeight) maxLineWeight = tempLineWeight;
    				}
	
    			}
    			
    			
    			
				
    		}
    	}
    	

    	
    	return maxLineWeight;
    }
    
    
    // SQL-style coalesce function. Return first non-null argument when given two inputs.
    public static <T> T coalesce(T a, T b) {
        return a == null ? b : a;
    }
    
    
    
    
    // REMOVE TRAILING PIXELS CAUSED BY mis-alignment when overlaying images
    public BufferedImage removeTrailingPixels(BufferedImage image) {
	    // REMOVE TRAILING PIXELS FROM OVERLAYED IMAGES/////////////////////////////////////////
		ArrayList <BufferedImage> objectsOverlayRG = separateObjects(image, "RG", 2);
		ArrayList <BufferedImage> objectsOverlayRB = separateObjects(image, "RB", 2);

		
		//// REMOVE TRAILING PIXELS
		
		// Iterate through different RG objects in Overlayed Image AB
		for (int i = 0; i < objectsOverlayRG.size(); i++) {
			Integer numRedPixels = numColorPixels(objectsOverlayRG.get(i),-4777216);
			Integer numGreenPixels = numColorPixels(objectsOverlayRG.get(i),-8000000);
			
			Integer numTotalColorPixels = numRedPixels+numGreenPixels;
			
			Integer greenLineWeight = lineWeight(objectsOverlayRG.get(i),-8000000);
			
			// IF percentage of green pixels to total colored pixels is less than 15%
			if (numGreenPixels.doubleValue()/numTotalColorPixels.doubleValue() < 0.15 | greenLineWeight < 2) {
				
				// Make green pixels white in original overlayed image for that particular figure
				for(int j = 0 ; j < objectsOverlayRG.get(i).getWidth() ; j++) {
		    		for(int k = 0 ; k < objectsOverlayRG.get(i).getHeight() ; k++) {
		    			Integer pixelRGB = objectsOverlayRG.get(i).getRGB(j, k);
		    			if (pixelRGB == -8000000) {
		    				image.setRGB(j, k, -1);  
		    				objectsOverlayRG.get(i).setRGB(j, k, -1);
		    			}
		    		}
		    	}	 
			}
			
		}
		
		
		
		// Iterate through different RB objects in Overlayed Image AB
		for (int i = 0; i < objectsOverlayRB.size(); i++) {
			Integer numRedPixels = numColorPixels(objectsOverlayRB.get(i),-4777216);
			Integer numBluePixels = numColorPixels(objectsOverlayRB.get(i),-12999999);
			
			Integer numTotalColorPixels = numRedPixels+numBluePixels;
			
			Integer blueLineWeight = lineWeight(objectsOverlayRB.get(i),-12999999);
			
			// IF percentage of blue pixels to total colored pixels is less than 15%
			if (numBluePixels.doubleValue()/numTotalColorPixels.doubleValue() < 0.15 | blueLineWeight < 2) {
				
				// Make blue pixels white in original overlayed image for that particular figure
				for(int j = 0 ; j < objectsOverlayRB.get(i).getWidth() ; j++) {
		    		for(int k = 0 ; k < objectsOverlayRB.get(i).getHeight() ; k++) {
		    			Integer pixelRGB = objectsOverlayRB.get(i).getRGB(j, k);
		    			if (pixelRGB == -12999999) {
		    				image.setRGB(j, k, -1); 
		    				objectsOverlayRB.get(i).setRGB(j, k, -1);
		    			}
		    		}
		    	}	 
			}
			
		}
		
		
		return image;
    
    }
    
}
