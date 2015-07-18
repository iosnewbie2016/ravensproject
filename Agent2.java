package ravensproject;

import java.util.ArrayList;
import java.util.Collections;
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
	
	//TODO: Colors: red, green, blue, pink, purple, turquoise, navyBlue, black
	
	public static int redColor = -4777216; 
	public static int greenColor = -8000000;
	public static int blueColor = -12999999;
	public static int pinkColor = -500000;
	public static int violetColor = -5500000;
	public static int turquoiseColor = -14000000;
	public static int navyBlueColor = -50000000;
	public static int blackColor = -16777216;
	
	public static Double minDistance = Double.POSITIVE_INFINITY;
	public static Double minDistanceDiff = 0.5;
	public static String colorsInOrder3x3 = "RGBPVT";
	public static String colorsNoOrder3x3 = "RGB";
	public static Boolean blurFigures = false;
	public static Boolean resizeFigures = false;
	public static Integer resizePercentage = 25;
	
	public static Boolean noOrder = true;
	public static Boolean inOrder = false;
	public static Boolean ratio = false;
	
	public static Double percentRedClassification = 0.9;
	public static Integer numPixelsDiffConstantChange = 20;
	
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
    	
    	//System.out.println(problem.getName());
    	int selectedAnswer = -1;
    	
    	//RED = redColor
    	//GREEN = greenColor
    	//BLUE = blueColor
    	
    	
    	
    	
    	BufferedImage testColors = new BufferedImage(700, 700, BufferedImage.TYPE_INT_ARGB); 
    	for (int i = 0; i < 700; i++) {
    		for (int j = 0; j < 100; j++) {
    			testColors.setRGB(i, j, -500000);
    		}
    		for (int j = 100; j < 200; j++) {
    			testColors.setRGB(i, j, -1600000);
    		}
    		for (int j = 200; j < 300; j++) {
    			testColors.setRGB(i, j, -4000000);
    		}
    		for (int j = 300; j < 400; j++) {
    			testColors.setRGB(i, j, -5500000);
    		}
    		for (int j = 400; j < 500; j++) {
    			testColors.setRGB(i, j, -6500000);
    		}
    		for (int j = 500; j < 600; j++) {
    			testColors.setRGB(i, j, -8500000);
    		}
    		for (int j = 600; j < 700; j++) {
    			testColors.setRGB(i, j, -14000000);
    		}
    	}
    	
    	File testColorsFile = new File("testColors.png");
    	
    	try {
    		ImageIO.write(testColors, "png", testColorsFile);
		} catch (IOException e) {}
    
    	
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
	    	
	    	
	    	
	    	
	    	ArrayList<ArrayList <Double>> distanceArrays = compareProcess2x2(figureAImage, 
	    			figureBImage, figureCImage, problem, answersToCompare, 1);
	    	
	    	
	    	//NEED TO UNCOMMENT FOR SOLVING 2x2 PROBLEMS
	    	/*ArrayList<Integer> possibleAnswers = minDistance(distanceArrays, answersToCompare, 1);
	    	
	    	if (possibleAnswers.size() == 0) {
	    		System.out.println(problem.getName() + " " + problem.getProblemType() + " SKIPPED");
	    		return -1;
	    	}
	    	else if (possibleAnswers.size() == 1) {
	    		System.out.println(problem.getName() + " " + problem.getProblemType() + " Answer: " + possibleAnswers.get(0));
	    		return possibleAnswers.get(0);
	    	}
	    	else {

	    		ArrayList<ArrayList <Double>> distanceArraysReflect = compareProcess2x2(figureAImage, 
		    			figureBImage, figureCImage, problem, possibleAnswers, 2);
	    		
	    		ArrayList<Integer> possibleAnswersReflect = minDistance(distanceArraysReflect, possibleAnswers, 2);
	    		
	    		System.out.println(problem.getName() + " " + problem.getProblemType() + " Answer: " + possibleAnswersReflect.get(0));
	    	
	    		return  possibleAnswersReflect.get(0);
	    	}*/
	    	///////////////////////////////////////////
	    	return -1; // Remove after fixing above
	    	
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
	    	
	    	
	    	//Image Array
	    	ArrayList<BufferedImage> imageArray = new ArrayList<BufferedImage>();
	    	imageArray.add(figureAImage);
	    	imageArray.add(figureBImage);
	    	imageArray.add(figureCImage);
	    	imageArray.add(figureDImage);
	    	imageArray.add(figureEImage);
	    	imageArray.add(figureFImage);
	    	imageArray.add(figureGImage);
	    	imageArray.add(figureHImage);
	    	
	    	ArrayList<Integer> horClassArray = problemClassification(imageArray);
	    	
	    	System.out.println(problem.getName() + " " + horClassArray);
	    	
	    	ArrayList<ArrayList<ArrayList <Double>>> distanceArrays = compareProcess3x3(imageArray, 
	    			problem, answersToCompare, 1);
	    	
	    	
	    	/*ArrayList<ArrayList <Double>> distanceArraysACG = compareProcess2x2(figureAImage, 
	    			figureCImage, figureGImage, problem, answersToCompare, 1);
	    	
	    	ArrayList<ArrayList <Double>> distanceArraysBCH = compareProcess2x2(figureBImage, 
	    			figureCImage, figureHImage, problem, answersToCompare, 1);
	    	
	    	ArrayList<ArrayList <Double>> distanceArraysDFG = compareProcess2x2(figureDImage, 
	    			figureFImage, figureGImage, problem, answersToCompare, 1);
	    	
	    	ArrayList<ArrayList <Double>> distanceArraysEFH = compareProcess2x2(figureEImage, 
	    			figureFImage, figureHImage, problem, answersToCompare, 1);
	    	
	    	ArrayList<ArrayList <Double>> distanceArrays = new ArrayList<ArrayList <Double>>();
	    	
	    	for (int i = 0; i < 2; i++) {
	    		distanceArrays.add(distanceArraysACG.get(i));
	    		distanceArrays.add(distanceArraysBCH.get(i));
	    		distanceArrays.add(distanceArraysDFG.get(i));
	    		distanceArrays.add(distanceArraysEFH.get(i));
	    	}*/
	    	
	    	
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
	    		System.out.println(problem.getName() + " " + problem.getProblemType() + " " + possibleAnswers);
	    		return -1;
	    	}
	    	/*else {

	    		ArrayList<ArrayList <Double>> distanceArraysACGReflect = compareProcess2x2(figureAImage, 
		    			figureCImage, figureGImage, problem, possibleAnswers, 1);
		    	
		    	ArrayList<ArrayList <Double>> distanceArraysBCHReflect = compareProcess2x2(figureBImage, 
		    			figureCImage, figureHImage, problem, possibleAnswers, 1);
		    	
		    	ArrayList<ArrayList <Double>> distanceArraysDFGReflect = compareProcess2x2(figureDImage, 
		    			figureFImage, figureGImage, problem, possibleAnswers, 1);
		    	
		    	ArrayList<ArrayList <Double>> distanceArraysEFHReflect = compareProcess2x2(figureEImage, 
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
	    	}*/
	    	
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
 
    
    
    // OVERLAY, REMOVE TRAILING, COMPARE DISTANCES. Specific for 3x3 problems.
    // TYPE: 1 = normal
    //       2 = reflected
    public ArrayList<ArrayList<ArrayList<Double>>> compareProcess3x3(ArrayList<BufferedImage> imageArray, RavensProblem problem, 
    		ArrayList<Integer> answersToCompare, Integer type) {
    	
    	//Blur Original Images
    	if (blurFigures) {
    		for (int i = 0; i < imageArray.size(); i++) {
    			imageArray.set(i, convFilter(imageArray.get(i)));
    		}
    	}
    	//Resize Original Images
	    if (resizeFigures) {
    		for (int i = 0; i < imageArray.size(); i++) {
	    		imageArray.set(i, imageResize(imageArray.get(i), resizePercentage));
	    	}
	    }
	    
    	ArrayList<BufferedImage> imageABCArray = new ArrayList<BufferedImage>();
    	imageABCArray.add(imageArray.get(0));
    	imageABCArray.add(imageArray.get(1));
    	imageABCArray.add(imageArray.get(2));
    	
    	ArrayList<BufferedImage> imageDEFArray = new ArrayList<BufferedImage>();
    	imageDEFArray.add(imageArray.get(3));
    	imageDEFArray.add(imageArray.get(4));
    	imageDEFArray.add(imageArray.get(5));
    	
    	ArrayList<BufferedImage> imageADGArray = new ArrayList<BufferedImage>();
    	imageADGArray.add(imageArray.get(0));
    	imageADGArray.add(imageArray.get(3));
    	imageADGArray.add(imageArray.get(6));
    	
    	ArrayList<BufferedImage> imageBEHArray = new ArrayList<BufferedImage>();
    	imageBEHArray.add(imageArray.get(1));
    	imageBEHArray.add(imageArray.get(4));
    	imageBEHArray.add(imageArray.get(7));
    	
    	ArrayList<BufferedImage> imageBFGArray = new ArrayList<BufferedImage>();
    	imageBFGArray.add(imageArray.get(1));
    	imageBFGArray.add(imageArray.get(5));
    	imageBFGArray.add(imageArray.get(6));
    	
    	ArrayList<BufferedImage> imageCDHArray = new ArrayList<BufferedImage>();
    	imageCDHArray.add(imageArray.get(2));
    	imageCDHArray.add(imageArray.get(3));
    	imageCDHArray.add(imageArray.get(7));
    	
    	
    	BufferedImage overlayPixelsABCInOrder = null;
    	BufferedImage overlayPixelsDEFInOrder = null;
    	BufferedImage overlayPixelsADGInOrder = null;
    	BufferedImage overlayPixelsBEHInOrder = null;
    	BufferedImage overlayPixelsBFGInOrder = null;
    	BufferedImage overlayPixelsCDHInOrder = null;
    	
    	BufferedImage overlayPixelsABCNoOrder = null;
    	BufferedImage overlayPixelsDEFNoOrder = null;
    	BufferedImage overlayPixelsADGNoOrder = null;
    	BufferedImage overlayPixelsBEHNoOrder = null;
    	BufferedImage overlayPixelsBFGNoOrder = null;
    	BufferedImage overlayPixelsCDHNoOrder = null;
    	
    	//IN ORDER
    	ArrayList<Double> rgbPerABCInOrder = new ArrayList<Double>();
    	ArrayList<Double> rgbPerDEFInOrder = new ArrayList<Double>();
    	ArrayList<Double> rgbPerADGInOrder = new ArrayList<Double>();
    	ArrayList<Double> rgbPerBEHInOrder = new ArrayList<Double>();
    	ArrayList<Double> rgbPerBFGInOrder = new ArrayList<Double>();
    	ArrayList<Double> rgbPerCDHInOrder = new ArrayList<Double>();
    	
    	//NO ORDER
    	ArrayList<Double> rgbPerABCNoOrder = new ArrayList<Double>();
    	ArrayList<Double> rgbPerDEFNoOrder = new ArrayList<Double>();
    	ArrayList<Double> rgbPerADGNoOrder = new ArrayList<Double>();
    	ArrayList<Double> rgbPerBEHNoOrder = new ArrayList<Double>();
    	ArrayList<Double> rgbPerBFGNoOrder = new ArrayList<Double>();
    	ArrayList<Double> rgbPerCDHNoOrder = new ArrayList<Double>();
    	
    	//RATIO
    	ArrayList<Double> ratioABC = new ArrayList<Double>();
    	ArrayList<Double> ratioDEF = new ArrayList<Double>();
    	ArrayList<Double> ratioADG = new ArrayList<Double>();
    	ArrayList<Double> ratioBEH = new ArrayList<Double>();
    	ArrayList<Double> ratioBFG = new ArrayList<Double>();
    	ArrayList<Double> ratioCDH = new ArrayList<Double>();

    	/*overlayPixelsABC = overlayPixelsArrayNoOrder3Images(imageABCArray);
    	overlayPixelsDEF = overlayPixelsArrayNoOrder3Images(imageDEFArray);
    	overlayPixelsADG = overlayPixelsArrayNoOrder3Images(imageADGArray);
    	overlayPixelsBEH = overlayPixelsArrayNoOrder3Images(imageBEHArray);*/
    	overlayPixelsABCInOrder = overlayPixelsArrayInOrder3Images(imageABCArray);
    	overlayPixelsDEFInOrder = overlayPixelsArrayInOrder3Images(imageDEFArray);
    	overlayPixelsADGInOrder = overlayPixelsArrayInOrder3Images(imageADGArray);
    	overlayPixelsBEHInOrder = overlayPixelsArrayInOrder3Images(imageBEHArray);
    	//Diagonal
    	overlayPixelsBFGInOrder = overlayPixelsArrayInOrder3Images(imageBFGArray);
    	overlayPixelsCDHInOrder = overlayPixelsArrayInOrder3Images(imageCDHArray);
    	///////////////////////////////////////////////////////////////////////////////////
    	//NO ORDER
    	overlayPixelsABCNoOrder = overlayPixelsArrayNoOrder3Images(imageABCArray);
    	overlayPixelsDEFNoOrder = overlayPixelsArrayNoOrder3Images(imageDEFArray);
    	overlayPixelsADGNoOrder = overlayPixelsArrayNoOrder3Images(imageADGArray);
    	overlayPixelsBEHNoOrder = overlayPixelsArrayNoOrder3Images(imageBEHArray);
    	//Diagonal
    	overlayPixelsBFGNoOrder = overlayPixelsArrayNoOrder3Images(imageBFGArray);
    	overlayPixelsCDHNoOrder = overlayPixelsArrayNoOrder3Images(imageCDHArray);
    	
    	/*File fileABC = new File("overlayPixelsABC.png");
    	File fileDEF = new File("overlayPixelsDEF.png");
    	File fileADG = new File("overlayPixelsADG.png");
    	File fileBEH = new File("overlayPixelsBEH.png");
    	File fileBFG = new File("overlayPixelsBFG.png");
    	File fileCDH = new File("overlayPixelsCDH.png");
    	
    	
    	try {
    		ImageIO.write(overlayPixelsABC, "png", fileABC);
    		ImageIO.write(overlayPixelsDEF, "png", fileDEF);
    		ImageIO.write(overlayPixelsADG, "png", fileADG);
    		ImageIO.write(overlayPixelsBEH, "png", fileBEH);
    		ImageIO.write(overlayPixelsBFG, "png", fileBFG);
    		ImageIO.write(overlayPixelsCDH, "png", fileCDH);
		} catch (IOException e) {}*/
    	
    	
    	// Skip for now. Think about resizing/blurring images.
    	/*overlayPixelsAB = removeTrailingPixels(overlayPixelsAB);
        overlayPixelsAC = removeTrailingPixels(overlayPixelsAC);*/
        	
        /*rgbPerABC = percentRGBtoTotalRGBOneImage(overlayPixelsABC, "RGB");
        rgbPerDEF = percentRGBtoTotalRGBOneImage(overlayPixelsDEF, "RGB");
        rgbPerADG = percentRGBtoTotalRGBOneImage(overlayPixelsADG, "RGB");
        rgbPerBEH = percentRGBtoTotalRGBOneImage(overlayPixelsBEH, "RGB");*/
    	rgbPerABCInOrder = percentRGBtoTotalRGBOneImage(overlayPixelsABCInOrder, colorsInOrder3x3);
        rgbPerDEFInOrder = percentRGBtoTotalRGBOneImage(overlayPixelsDEFInOrder, colorsInOrder3x3);
        rgbPerADGInOrder = percentRGBtoTotalRGBOneImage(overlayPixelsADGInOrder, colorsInOrder3x3);
        rgbPerBEHInOrder = percentRGBtoTotalRGBOneImage(overlayPixelsBEHInOrder, colorsInOrder3x3);
        //Diagonal
        rgbPerBFGInOrder = percentRGBtoTotalRGBOneImage(overlayPixelsBFGInOrder, colorsInOrder3x3);
        rgbPerCDHInOrder = percentRGBtoTotalRGBOneImage(overlayPixelsCDHInOrder, colorsInOrder3x3);
        ///////////////////////////////////////////////////////////////////////////////////
        rgbPerABCNoOrder = percentRGBtoTotalRGBOneImage(overlayPixelsABCNoOrder, colorsNoOrder3x3);
        rgbPerDEFNoOrder = percentRGBtoTotalRGBOneImage(overlayPixelsDEFNoOrder, colorsNoOrder3x3);
        rgbPerADGNoOrder = percentRGBtoTotalRGBOneImage(overlayPixelsADGNoOrder, colorsNoOrder3x3);
        rgbPerBEHNoOrder = percentRGBtoTotalRGBOneImage(overlayPixelsBEHNoOrder, colorsNoOrder3x3);
        //Diagonal
        rgbPerBFGNoOrder = percentRGBtoTotalRGBOneImage(overlayPixelsBFGNoOrder, colorsNoOrder3x3);
        rgbPerCDHNoOrder = percentRGBtoTotalRGBOneImage(overlayPixelsCDHNoOrder, colorsNoOrder3x3);
        
        //RATIO////////////////////////
    	ratioABC = changeRatio(imageABCArray);
    	ratioDEF = changeRatio(imageDEFArray);
    	ratioADG = changeRatio(imageADGArray);
    	ratioBEH = changeRatio(imageBEHArray);
    	//Diagonal
    	ratioBFG = changeRatio(imageBFGArray);
    	ratioCDH = changeRatio(imageCDHArray);
      
    	
    	ArrayList <Double> distanceArrayABC_GHAnsInOrder = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayDEF_GHAnsInOrder = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayADG_CFAnsInOrder = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayBEH_CFAnsInOrder = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayBFG_AEAnsInOrder = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayCDH_AEAnsInOrder = new ArrayList <Double>();
    	///////////////////////////////////////////////////////////////////////////////////
    	ArrayList <Double> distanceArrayABC_GHAnsNoOrder = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayDEF_GHAnsNoOrder = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayADG_CFAnsNoOrder = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayBEH_CFAnsNoOrder = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayBFG_AEAnsNoOrder = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayCDH_AEAnsNoOrder = new ArrayList <Double>();
    	///////////////RATIO//////////////////////////////////////////////////////
    	ArrayList <Double> distanceArrayABC_GHAnsRatio = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayDEF_GHAnsRatio = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayADG_CFAnsRatio = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayBEH_CFAnsRatio = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayBFG_AEAnsRatio = new ArrayList <Double>();
    	ArrayList <Double> distanceArrayCDH_AEAnsRatio = new ArrayList <Double>();
    	
    	
    	ArrayList <ArrayList <Double>> rgbPerGHAnsArrayInOrder = new ArrayList <ArrayList <Double>>();
    	ArrayList <ArrayList <Double>> rgbPerCFAnsArrayInOrder = new ArrayList <ArrayList <Double>>();
    	ArrayList <ArrayList <Double>> rgbPerAEAnsArrayInOrder = new ArrayList <ArrayList <Double>>();
    	/////////////////////////////////////////////////////////////////////////////////////////////
    	ArrayList <ArrayList <Double>> rgbPerGHAnsArrayNoOrder = new ArrayList <ArrayList <Double>>();
    	ArrayList <ArrayList <Double>> rgbPerCFAnsArrayNoOrder = new ArrayList <ArrayList <Double>>();
    	ArrayList <ArrayList <Double>> rgbPerAEAnsArrayNoOrder = new ArrayList <ArrayList <Double>>();
    	/////////////////////////////////////////////////////////////////////////////////////////////
    	ArrayList <ArrayList <Double>> GHAnsArrayRatio = new ArrayList <ArrayList <Double>>();
    	ArrayList <ArrayList <Double>> CFAnsArrayRatio = new ArrayList <ArrayList <Double>>();
    	ArrayList <ArrayList <Double>> AEAnsArrayRatio = new ArrayList <ArrayList <Double>>();
    	
    	
    			
    	// Iterate through answer choices and populate percentages of RGB pixels 
    	for (Integer i = 1; i < answersToCompare.size()+1; i++) {
    		
    		Integer currI = answersToCompare.get(i-1);
    		
    		RavensFigure figure = problem.getFigures().get(currI.toString());
    		BufferedImage figureImage = null;
    		try {
				figureImage = ImageIO.read(new File(figure.getVisual()));
			} catch (IOException e) {}
    		
    		//Resize answer choice
    		if (resizeFigures) figureImage = imageResize(figureImage, resizePercentage);
    		
    		
    		ArrayList<BufferedImage> imageGHAnsArray = new ArrayList<BufferedImage>();
    		imageGHAnsArray.add(imageArray.get(6));
    		imageGHAnsArray.add(imageArray.get(7));
    		imageGHAnsArray.add(figureImage);
    		
    		ArrayList<BufferedImage> imageCFAnsArray = new ArrayList<BufferedImage>();
    		imageCFAnsArray.add(imageArray.get(2));
    		imageCFAnsArray.add(imageArray.get(5));
    		imageCFAnsArray.add(figureImage);
    		
    		//Diagonal
    		ArrayList<BufferedImage> imageAEAnsArray = new ArrayList<BufferedImage>();
    		imageAEAnsArray.add(imageArray.get(0));
    		imageAEAnsArray.add(imageArray.get(4));
    		imageAEAnsArray.add(figureImage);
    		
    		
    		//Generate png files to study
    		BufferedImage overlayPixelsGHAnsInOrder = null;
        	BufferedImage overlayPixelsCFAnsInOrder = null;
        	BufferedImage overlayPixelsAEAnsInOrder = null;
        	
        	//No Order
    		BufferedImage overlayPixelsGHAnsNoOrder = null;
        	BufferedImage overlayPixelsCFAnsNoOrder = null;
        	BufferedImage overlayPixelsAEAnsNoOrder = null;
        	
        	/*if (type == 2 | sumPercentReflect < 0.01) {
        		overlayPixelsCAns = overlayPixelsHighlight(reflectImageVertically(figureCImage), figureImage, "RGB");
        		overlayPixelsBAns = overlayPixelsHighlight(reflectImageHorizontally(figureBImage), figureImage, "RGB");
        	}
        	else if (type == 1) {*/
        	overlayPixelsGHAnsInOrder = overlayPixelsArrayInOrder3Images(imageGHAnsArray);
        	overlayPixelsCFAnsInOrder = overlayPixelsArrayInOrder3Images(imageCFAnsArray);
        	overlayPixelsAEAnsInOrder = overlayPixelsArrayInOrder3Images(imageAEAnsArray);
        	
        	overlayPixelsGHAnsNoOrder = overlayPixelsArrayNoOrder3Images(imageGHAnsArray);
        	overlayPixelsCFAnsNoOrder = overlayPixelsArrayNoOrder3Images(imageCFAnsArray);
        	overlayPixelsAEAnsNoOrder = overlayPixelsArrayNoOrder3Images(imageAEAnsArray);
        	//}
        	
        	
        	// Skip for now. Think about resizing/blurring images.
    		/*overlayPixelsCAns = removeTrailingPixels(overlayPixelsCAns);
    		overlayPixelsBAns = removeTrailingPixels(overlayPixelsBAns);*/
        	
        	
    		
    		/*File fileOverlayPixelsGHAns = new File("overlayPixelsGHAns" + currI + ".png");
    		File fileOverlayPixelsCFAns = new File("overlayPixelsCFAns" + currI + ".png");
    		File fileOverlayPixelsAEAns = new File("overlayPixelsAEAns" + currI + ".png");
    		try {
        		ImageIO.write(overlayPixelsGHAns, "png", fileOverlayPixelsGHAns);
        		ImageIO.write(overlayPixelsCFAns, "png", fileOverlayPixelsCFAns);
        		ImageIO.write(overlayPixelsAEAns, "png", fileOverlayPixelsAEAns);
    		} catch (IOException e) {}*/
    		
    		// Percentages of pixels between problem and answers.
    		//ArrayList<Double> rgbPerC = percentRGBtoTotalRGB(figureCImage, figureImage, "RGB");
    		//ArrayList<Double> rgbPerB = percentRGBtoTotalRGB(figureBImage, figureImage, "RGB");
    		ArrayList<Double> rgbPerGHAnsInOrder = percentRGBtoTotalRGBOneImage(overlayPixelsGHAnsInOrder, colorsInOrder3x3);
    		ArrayList<Double> rgbPerCFAnsInOrder = percentRGBtoTotalRGBOneImage(overlayPixelsCFAnsInOrder, colorsInOrder3x3);
    		//Diagonal
    		ArrayList<Double> rgbPerAEAnsInOrder = percentRGBtoTotalRGBOneImage(overlayPixelsAEAnsInOrder, colorsInOrder3x3);
    		///////////////ORDER////////////////////////////////////////////////////
    		ArrayList<Double> rgbPerGHAnsNoOrder = percentRGBtoTotalRGBOneImage(overlayPixelsGHAnsNoOrder, colorsNoOrder3x3);
    		ArrayList<Double> rgbPerCFAnsNoOrder = percentRGBtoTotalRGBOneImage(overlayPixelsCFAnsNoOrder, colorsNoOrder3x3);
    		//Diagonal
    		ArrayList<Double> rgbPerAEAnsNoOrder = percentRGBtoTotalRGBOneImage(overlayPixelsAEAnsNoOrder, colorsNoOrder3x3);
    		//////////////RATIO//////////////////////////////////////////////////////
    		ArrayList<Double> ratioGHAns = changeRatio(imageGHAnsArray);
    		ArrayList<Double> ratioCFAns = changeRatio(imageCFAnsArray);
    		//Diagonal
    		ArrayList<Double> ratioAEAns = changeRatio(imageAEAnsArray);
    		
    		
    		rgbPerGHAnsArrayInOrder.add(rgbPerGHAnsInOrder);
    		rgbPerCFAnsArrayInOrder.add(rgbPerCFAnsInOrder);
    		rgbPerAEAnsArrayInOrder.add(rgbPerAEAnsInOrder);
    		
    		rgbPerGHAnsArrayNoOrder.add(rgbPerGHAnsNoOrder);
    		rgbPerCFAnsArrayNoOrder.add(rgbPerCFAnsNoOrder);
    		rgbPerAEAnsArrayNoOrder.add(rgbPerAEAnsNoOrder);
    		
    		GHAnsArrayRatio = new ArrayList <ArrayList <Double>>();
        	CFAnsArrayRatio = new ArrayList <ArrayList <Double>>();
        	AEAnsArrayRatio = new ArrayList <ArrayList <Double>>();
    		
    		//Distance between AB<->CAns & AC<->BAns
    		Double distanceABC_GHAnsInOrder = distance(rgbPerABCInOrder, rgbPerGHAnsInOrder);
    		Double distanceDEF_GHAnsInOrder = distance(rgbPerDEFInOrder, rgbPerGHAnsInOrder);
    		Double distanceADG_CFAnsInOrder = distance(rgbPerADGInOrder, rgbPerCFAnsInOrder);
    		Double distanceBEH_CFAnsInOrder = distance(rgbPerBEHInOrder, rgbPerCFAnsInOrder);
    		Double distanceBFG_AEAnsInOrder = distance(rgbPerBFGInOrder, rgbPerAEAnsInOrder);
    		Double distanceCDH_AEAnsInOrder = distance(rgbPerCDHInOrder, rgbPerAEAnsInOrder);
    		
    		Double distanceABC_GHAnsNoOrder = distance(rgbPerABCNoOrder, rgbPerGHAnsNoOrder);
    		Double distanceDEF_GHAnsNoOrder = distance(rgbPerDEFNoOrder, rgbPerGHAnsNoOrder);
    		Double distanceADG_CFAnsNoOrder = distance(rgbPerADGNoOrder, rgbPerCFAnsNoOrder);
    		Double distanceBEH_CFAnsNoOrder = distance(rgbPerBEHNoOrder, rgbPerCFAnsNoOrder);
    		Double distanceBFG_AEAnsNoOrder = distance(rgbPerBFGNoOrder, rgbPerAEAnsNoOrder);
    		Double distanceCDH_AEAnsNoOrder = distance(rgbPerCDHNoOrder, rgbPerAEAnsNoOrder);
    		
    		Double distanceABC_GHAnsRatio = distance(ratioABC, ratioGHAns);
    		Double distanceDEF_GHAnsRatio = distance(ratioDEF, ratioGHAns);
    		Double distanceADG_CFAnsRatio = distance(ratioADG, ratioCFAns);
    		Double distanceBEH_CFAnsRatio = distance(ratioBEH, ratioCFAns);
    		Double distanceBFG_AEAnsRatio = distance(ratioBFG, ratioAEAns);
    		Double distanceCDH_AEAnsRatio = distance(ratioCDH, ratioAEAns);
    		
    		distanceArrayABC_GHAnsInOrder.add(distanceABC_GHAnsInOrder);
    		distanceArrayDEF_GHAnsInOrder.add(distanceDEF_GHAnsInOrder);
    		
    		distanceArrayADG_CFAnsInOrder.add(distanceADG_CFAnsInOrder);
    		distanceArrayBEH_CFAnsInOrder.add(distanceBEH_CFAnsInOrder);
    		
    		distanceArrayBFG_AEAnsInOrder.add(distanceBFG_AEAnsInOrder);
    		distanceArrayCDH_AEAnsInOrder.add(distanceCDH_AEAnsInOrder);
    		////////////////////////////////////////////////////////////
    		distanceArrayABC_GHAnsNoOrder.add(distanceABC_GHAnsNoOrder);
    		distanceArrayDEF_GHAnsNoOrder.add(distanceDEF_GHAnsNoOrder);
    		
    		distanceArrayADG_CFAnsNoOrder.add(distanceADG_CFAnsNoOrder);
    		distanceArrayBEH_CFAnsNoOrder.add(distanceBEH_CFAnsNoOrder);
    		
    		distanceArrayBFG_AEAnsNoOrder.add(distanceBFG_AEAnsNoOrder);
    		distanceArrayCDH_AEAnsNoOrder.add(distanceCDH_AEAnsNoOrder);
    		////////////RATIO
    		distanceArrayABC_GHAnsRatio.add(distanceABC_GHAnsRatio);
    		distanceArrayDEF_GHAnsRatio.add(distanceDEF_GHAnsRatio);
    		
    		distanceArrayADG_CFAnsRatio.add(distanceADG_CFAnsRatio);
    		distanceArrayBEH_CFAnsRatio.add(distanceBEH_CFAnsRatio);
    		
    		distanceArrayBFG_AEAnsRatio.add(distanceBFG_AEAnsRatio);
    		distanceArrayCDH_AEAnsRatio.add(distanceCDH_AEAnsRatio);
    		
    		
    		
    		/*ArrayList<BufferedImage> objects = separateObjects(figureImage, "RGB", 1);
        	Integer numObjects = objects.size();

        	
        	Integer diffNumObjectsCAns = numObjects-numObjectsC;
        	Integer diffNumObjectsBAns = numObjects-numObjectsB;
    		
        	
        	diffNumObjectsArrayCAns.add(diffNumObjectsCAns);
        	diffNumObjectsArrayBAns.add(diffNumObjectsBAns);*/
    		

        	 
    	
    	}
    	
    	/*try {
    		
    		
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
            }*/

    	ArrayList<ArrayList<Double>> distanceArraysInOrder = new ArrayList<ArrayList<Double>>();
    	ArrayList<ArrayList<Double>> distanceArraysNoOrder = new ArrayList<ArrayList<Double>>();
    	/*distanceArrays.add(distanceArrayABC_GHAnsInOrder);
    	distanceArrays.add(distanceArrayDEF_GHAnsInOrder);
    	distanceArrays.add(distanceArrayADG_CFAnsInOrder);
    	distanceArrays.add(distanceArrayBEH_CFAnsInOrder);
    	distanceArrays.add(distanceArrayBFG_AEAnsInOrder);
    	distanceArrays.add(distanceArrayCDH_AEAnsInOrder);
    	//////////////////////////////////////////////////
    	distanceArrays.add(distanceArrayABC_GHAnsNoOrder);
    	distanceArrays.add(distanceArrayDEF_GHAnsNoOrder);
    	distanceArrays.add(distanceArrayADG_CFAnsNoOrder);
    	distanceArrays.add(distanceArrayBEH_CFAnsNoOrder);
    	distanceArrays.add(distanceArrayBFG_AEAnsNoOrder);
    	distanceArrays.add(distanceArrayCDH_AEAnsNoOrder);*/
    	
    	if (inOrder) {
    		/*distanceArraysInOrder.add(distanceArrayABC_GHAnsInOrder);
    		distanceArraysInOrder.add(distanceArrayDEF_GHAnsInOrder);
    		distanceArraysInOrder.add(distanceArrayADG_CFAnsInOrder);
    		distanceArraysInOrder.add(distanceArrayBEH_CFAnsInOrder);
    		distanceArraysInOrder.add(distanceArrayBFG_AEAnsInOrder);
    		distanceArraysInOrder.add(distanceArrayCDH_AEAnsInOrder);*/
    		if (includeDistance(distanceArrayABC_GHAnsInOrder)) distanceArraysInOrder.add(distanceArrayABC_GHAnsInOrder);
	    	if (includeDistance(distanceArrayDEF_GHAnsInOrder)) distanceArraysInOrder.add(distanceArrayDEF_GHAnsInOrder);
	    	if (includeDistance(distanceArrayADG_CFAnsInOrder)) distanceArraysInOrder.add(distanceArrayADG_CFAnsInOrder);
	    	if (includeDistance(distanceArrayBEH_CFAnsInOrder)) distanceArraysInOrder.add(distanceArrayBEH_CFAnsInOrder);
	    	if (includeDistance(distanceArrayBFG_AEAnsInOrder)) distanceArraysInOrder.add(distanceArrayBFG_AEAnsInOrder);
	    	if (includeDistance(distanceArrayCDH_AEAnsInOrder)) distanceArraysInOrder.add(distanceArrayCDH_AEAnsInOrder);
	    }
    	//////////////////////////////////////////////////
    	if (noOrder) {
    		/*distanceArraysNoOrder.add(distanceArrayABC_GHAnsNoOrder);
    		distanceArraysNoOrder.add(distanceArrayDEF_GHAnsNoOrder);
    		distanceArraysNoOrder.add(distanceArrayADG_CFAnsNoOrder);
    		distanceArraysNoOrder.add(distanceArrayBEH_CFAnsNoOrder);
        	distanceArraysNoOrder.add(distanceArrayBFG_AEAnsNoOrder);
        	distanceArraysNoOrder.add(distanceArrayCDH_AEAnsNoOrder);*/
    		if (includeDistance(distanceArrayABC_GHAnsNoOrder)) distanceArraysNoOrder.add(distanceArrayABC_GHAnsNoOrder);
	    	if (includeDistance(distanceArrayDEF_GHAnsNoOrder)) distanceArraysNoOrder.add(distanceArrayDEF_GHAnsNoOrder);
	    	if (includeDistance(distanceArrayADG_CFAnsNoOrder)) distanceArraysNoOrder.add(distanceArrayADG_CFAnsNoOrder);
	    	if (includeDistance(distanceArrayBEH_CFAnsNoOrder)) distanceArraysNoOrder.add(distanceArrayBEH_CFAnsNoOrder);
	    	if (includeDistance(distanceArrayBFG_AEAnsNoOrder)) distanceArraysNoOrder.add(distanceArrayBFG_AEAnsNoOrder);
	    	if (includeDistance(distanceArrayCDH_AEAnsNoOrder)) distanceArraysNoOrder.add(distanceArrayCDH_AEAnsNoOrder);
	    }
    	//////////////////////////////////////////////////
    	/*if (ratio) {
	    	if (includeDistance(distanceArrayABC_GHAnsRatio)) distanceArrays.add(distanceArrayABC_GHAnsRatio);
	    	if (includeDistance(distanceArrayDEF_GHAnsRatio)) distanceArrays.add(distanceArrayDEF_GHAnsRatio);
	    	if (includeDistance(distanceArrayADG_CFAnsRatio)) distanceArrays.add(distanceArrayADG_CFAnsRatio);
	    	if (includeDistance(distanceArrayBEH_CFAnsRatio)) distanceArrays.add(distanceArrayBEH_CFAnsRatio);
	    	if (includeDistance(distanceArrayBFG_AEAnsRatio)) distanceArrays.add(distanceArrayBFG_AEAnsRatio);
	    	if (includeDistance(distanceArrayCDH_AEAnsRatio)) distanceArrays.add(distanceArrayCDH_AEAnsRatio);
    	}*/
    	
    	ArrayList<ArrayList<ArrayList<Double>>> distanceArrays = new ArrayList<ArrayList<ArrayList<Double>>>();
    	
    	distanceArrays.add(distanceArraysInOrder);
    	distanceArrays.add(distanceArraysNoOrder);
    	return distanceArrays;
    	
    	
    }
    
    
    
 // OVERLAY, REMOVE TRAILING, COMPARE DISTANCES. Specific for 2x2 problems.
    // TYPE: 1 = normal
    //       2 = reflected
    public ArrayList<ArrayList<Double>> compareProcess2x2(BufferedImage figureAImage, BufferedImage figureBImage, 
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
    	
    	
    	
    	
    	//File fileFigureOverlayPixelsAB = new File(problem.getName() + "/overlayPixelsAB.png");
    	//File fileFigureOverlayPixelsAC = new File(problem.getName() + "/overlayPixelsAC.png");

    	
    	/*try {
    		ImageIO.write(overlayPixelsAB, "png", fileFigureOverlayPixelsAB);
    		ImageIO.write(overlayPixelsAC, "png", fileFigureOverlayPixelsAC);
		} catch (IOException e) {}*/

    	
    	
    	
    	
    	
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
    		
    		/*File fileOverlayPixelsCAns = new File(problem.getName() + "/overlayPixelsC" + currI + ".png");
    		File fileOverlayPixelsBAns = new File(problem.getName() + "/overlayPixelsB" + currI + ".png");
    		
    		try {
        		ImageIO.write(overlayPixelsCAns, "png", fileOverlayPixelsCAns);
        		ImageIO.write(overlayPixelsBAns, "png", fileOverlayPixelsBAns);
    		} catch (IOException e) {}*/
    		
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
    	
    	/*try {
    		
    		
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
            }*/
    	
    	ArrayList<ArrayList<Double>> distanceArrays = new ArrayList<ArrayList<Double>>();
    	distanceArrays.add(distanceArrayCAns);
    	distanceArrays.add(distanceArrayBAns);
    	
    	return distanceArrays;
    	
    }
    
    // Calculate min distance from array of arrays of distances
    public ArrayList<Integer> minDistance(ArrayList<ArrayList<ArrayList<Double>>> distanceArrays, 
    		ArrayList<Integer> answersToCompare, Integer type) {
    	
    	ArrayList<Integer> possibleAnswers = new ArrayList<Integer>();
    	int minDistanceAnswer = -1;
    	Double minDistanceDouble = Double.POSITIVE_INFINITY;
    	Double minDiffObjects = Double.POSITIVE_INFINITY;
    	
    	for (int x = 1; x < distanceArrays.size(); x++) {
	    	
	    	
	    	
	    	ArrayList<Double> avgDistances = new ArrayList<Double>();
	    	
	    	for (Integer i = 0; i < distanceArrays.get(x).get(0).size(); i++) {
	    		
	    		Double sumDistance = 0.0;
	    		
	    		for (Integer j = 0; j < distanceArrays.get(x).size(); j++) {
	    			sumDistance += distanceArrays.get(x).get(j).get(i);  			
	    		}
	    		
	
	    		Double avgDistance = sumDistance/distanceArrays.get(x).size();
	    		
	    		avgDistances.add(avgDistance);
	    	}
	    	
	    	for (Integer i = 0; i < avgDistances.size(); i++) {
	    		
	    		
	    		//Integer sumDiffNumObjects = diffNumObjectsArrayCAns.get(i) + diffNumObjectsArrayBAns.get(i);
	    		//Double avgDiffNumObjects = sumDiffNumObjects.doubleValue()/2;
	    		
	    		//Integer sumDiffNumObjectsAB_AC = diffNumObjectsAB + diffNumObjectsAC;
	    		//Double avgDiffNumObjectsAB_AC = sumDiffNumObjectsAB_AC.doubleValue()/2;
	    		
	    		
	    		//if (avgDiffNumObjects.equals(avgDiffNumObjectsAB_AC)) avgDistance -= 0.05;
	    		
	    		
	    		if ((Math.abs(avgDistances.get(i) - minDistanceDouble) < 0.001) & (type == 1)) {
	    			possibleAnswers.add(answersToCompare.get(i));
	    		}
	    		
	    		else if (avgDistances.get(i) < minDistanceDouble) {
	    			for (int j = 0; j < possibleAnswers.size(); j++) {
	    				possibleAnswers.remove(0);
	    			}
	    			minDistanceDouble = avgDistances.get(i);
		    		minDistanceAnswer = answersToCompare.get(i);
	    			possibleAnswers.add(answersToCompare.get(i));
	    		}
	    		
	    	}
    	}	
	    if (minDistanceDouble > 0.5) {
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
    //Found in Images 1 & 2: red   R
	//Only found in Image 1: green G
    //Only found in Image 2: blue  B
    
    //Only found in Image 3: pink  P
    //Found in Images 1 & 3: violet V
    //Found in images 2 & 3: turquoise  T
    //Found in Images 1&2&3: Navy Blue  N
    ArrayList<Double> percentRGBtoTotalRGBOneImage(BufferedImage image, String pixelsAccount) {
    	
    	
    	
    	Double numRedPixels = 0.0;
    	Double numGreenPixels = 0.0;
    	Double numBluePixels = 0.0;
    	Double numPinkPixels = 0.0;
    	Double numVioletPixels = 0.0;
    	Double numTurquoisePixels = 0.0;
    	Double numNavyBluePixels = 0.0;
    	Double numTotalPixels = 0.0;
    	Double numTotalColorPixels = 0.0;
    	
    	ArrayList<Double> percentArrayReturnArrayList = new ArrayList<Double>();
    	
    	for(int i = 0 ; i < image.getWidth() ; i++) {
	    	for(int j = 0 ; j < image.getHeight() ; j++) {
	    		
	    		numTotalPixels++;
	    		
	    		Integer pixelRGB1 = image.getRGB(i, j);
	    		
	    		//RED = redColor
	        	//GREEN = greenColor
	        	//BLUE = blueColor
	    		
	    		if (image.getRGB(i, j) == redColor) numRedPixels++; //
	    		else if (image.getRGB(i, j) == greenColor) numGreenPixels++; //
	    		else if (image.getRGB(i, j) == blueColor) numBluePixels++; //
	    		else if (image.getRGB(i, j) == pinkColor) numPinkPixels++; //
	    		else if (image.getRGB(i, j) == violetColor) numVioletPixels++; //
	    		else if (image.getRGB(i, j) == turquoiseColor) numTurquoisePixels++; //
	    		else if (image.getRGB(i, j) == navyBlueColor) numNavyBluePixels++; //
	    		
	    		
	    	}
	    		    	
    	}
    	
    	
    	
    	for (int i = 0; i < pixelsAccount.length(); i++){
		    Character c = pixelsAccount.charAt(i);
		    String cString = c.toString();
		    
		    if (cString.equals("R")) numTotalColorPixels+=numRedPixels;
		    else if (cString.equals("G")) numTotalColorPixels+=numGreenPixels;
		    else if (cString.equals("B")) numTotalColorPixels+=numBluePixels;
		    else if (cString.equals("P")) numTotalColorPixels+=numPinkPixels;
		    else if (cString.equals("V")) numTotalColorPixels+=numVioletPixels;
		    else if (cString.equals("T")) numTotalColorPixels+=numTurquoisePixels;
		    else if (cString.equals("N")) numTotalColorPixels+=numNavyBluePixels;

    	}
    	
    	Double percentRedFigure = 0.0; // 
    	Double percentGreenFigure = 0.0; // 
    	Double percentBlueFigure = 0.0; //
    	Double percentPinkFigure = 0.0; // 
    	Double percentVioletFigure = 0.0; //
    	Double percentTurquoiseFigure = 0.0; // 
    	Double percentNavyBlueFigure = 0.0; // 
    	
    	if (numTotalColorPixels.equals(0.0) == false) {
	    	percentRedFigure += (numRedPixels/numTotalColorPixels); // 
	    	percentGreenFigure += (numGreenPixels/numTotalColorPixels); // 
	    	percentBlueFigure += (numBluePixels/numTotalColorPixels); // 
	    	percentPinkFigure += (numPinkPixels/numTotalColorPixels);
	    	percentVioletFigure += (numVioletPixels/numTotalColorPixels);
	    	percentTurquoiseFigure += (numTurquoisePixels/numTotalColorPixels);
	    	percentNavyBlueFigure += (numNavyBluePixels/numTotalColorPixels);
    	}
  	
    	for (int i = 0; i < pixelsAccount.length(); i++){
		    Character c = pixelsAccount.charAt(i);
		    String cString = c.toString();
		    
		    if (cString.equals("R")) percentArrayReturnArrayList.add(percentRedFigure);
		    else if (cString.equals("G")) percentArrayReturnArrayList.add(percentGreenFigure);
		    else if (cString.equals("B")) percentArrayReturnArrayList.add(percentBlueFigure);
		    else if (cString.equals("P")) percentArrayReturnArrayList.add(percentPinkFigure);
		    else if (cString.equals("V")) percentArrayReturnArrayList.add(percentVioletFigure);
		    else if (cString.equals("T")) percentArrayReturnArrayList.add(percentTurquoiseFigure);
		    else if (cString.equals("N")) percentArrayReturnArrayList.add(percentNavyBlueFigure);

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
    
    
    //Keep constant pixels of two images.
    public BufferedImage constantPixels(BufferedImage image1, BufferedImage image2) {
    	BufferedImage imageReturn = new BufferedImage(image2.getWidth(), image2.getHeight(), BufferedImage.TYPE_INT_ARGB);
    	
    	for(int i = 0 ; i < image1.getWidth() ; i++) {
	    	for(int j = 0 ; j < image1.getHeight() ; j++) {
	    		imageReturn.setRGB(i,j,-1);
	    		Integer pixelRGB1 = image1.getRGB(i, j);
	    		Integer pixelRGB2 = image2.getRGB(i, j);
	    		
	    		if (pixelRGB1 != -1 & pixelRGB2 != -1) imageReturn.setRGB(i,j,blackColor);
	    		else imageReturn.setRGB(i,j,-1);
	    	}
	    		    	
    	}
    	
    	return imageReturn;
    }
    
    //Keep constant pixels of image array.
    public BufferedImage constantPixelsFromArray(ArrayList<BufferedImage> imageArray) {
    	BufferedImage imageReturn = new BufferedImage(imageArray.get(0).getWidth(), imageArray.get(0).getWidth(), BufferedImage.TYPE_INT_ARGB);
    	
    	for(int i = 0 ; i < imageReturn.getWidth() ; i++) {
	    	for(int j = 0 ; j < imageReturn.getHeight() ; j++) {
	    		Integer numBlackPixels = 0; 
	    		for (int k = 0; k < imageArray.size(); k++) {
	    			if (imageArray.get(k).getRGB(i, j) != -1) numBlackPixels+=1;
	    		}
	    		
	    		
	    		
	    		if (numBlackPixels == imageArray.size()) imageReturn.setRGB(i,j,blackColor);
	    		else imageReturn.setRGB(i,j,-1);

	    		
	    	}
	    		    	
    	}
    	
    	return imageReturn;
    }
    
    
    //Keep non-constant pixels of image array.
    public BufferedImage nonConstantPixelsFromArray(BufferedImage image1, BufferedImage image2) {
    	BufferedImage imageReturn = new BufferedImage(image1.getWidth(), image1.getWidth(), BufferedImage.TYPE_INT_ARGB);
    	
    	for(int i = 0 ; i < imageReturn.getWidth() ; i++) {
	    	for(int j = 0 ; j < imageReturn.getHeight() ; j++) {
	    		Integer numBlackPixels = 0; 
	    		if (image1.getRGB(i, j) != -1) numBlackPixels+=1;
	    		if (image2.getRGB(i, j) != -1) numBlackPixels+=1;
    		
	    		
	    		if (numBlackPixels == 1) imageReturn.setRGB(i,j,blackColor);
	    		else imageReturn.setRGB(i,j,-1);

	    		
	    	}
	    		    	
    	}
    	
    	return imageReturn;
    }
    
    
    //Overlay images regardless of order. 
    // If a pixel is found in one image paint it a certain color. 
    // If a pixel is found in two images paint it a certain color.
    // If a pixel is found in all three images paint it a certain color.
    public BufferedImage overlayPixelsArrayNoOrder3Images(ArrayList<BufferedImage> imageArray) {
    	BufferedImage imageReturn = new BufferedImage(imageArray.get(0).getWidth(), imageArray.get(0).getWidth(), BufferedImage.TYPE_INT_ARGB);
    	
    	for(int i = 0 ; i < imageReturn.getWidth() ; i++) {
	    	for(int j = 0 ; j < imageReturn.getHeight() ; j++) {
	    		Integer numBlackPixels = 0; 
	    		for (int k = 0; k < imageArray.size(); k++) {
	    			if (imageArray.get(k).getRGB(i, j) != -1) numBlackPixels+=1;
	    		}
	    		
	    		
	    		
	    		if (numBlackPixels == 1) imageReturn.setRGB(i,j,greenColor);
	    		else if (numBlackPixels == 2) imageReturn.setRGB(i,j,blueColor);
	    		else if (numBlackPixels == 3) imageReturn.setRGB(i,j,redColor);
	    		else imageReturn.setRGB(i,j,-1);

	    		
	    	}
	    		    	
    	}
    	
    	return imageReturn;
    }
    
    
    //Overlay images in order and highlight pixels in color. Specific for three images:
    //Only found in Image 1: green G
    //Only found in Image 2: blue  B
    //Found in Images 1 & 2: red   R
    //Only found in Image 3: pink  P
    //Found in Images 1 & 3: violet V
    //Found in images 2 & 3: turquoise  T
    //Found in Images 1&2&3: Navy Blue  N
    public BufferedImage overlayPixelsArrayInOrder3Images(ArrayList<BufferedImage> imageArray) {
    	BufferedImage imageReturn = new BufferedImage(imageArray.get(0).getWidth(), imageArray.get(0).getWidth(), BufferedImage.TYPE_INT_ARGB);
    	
    	for(int i = 0 ; i < imageReturn.getWidth() ; i++) {
	    	for(int j = 0 ; j < imageReturn.getHeight() ; j++) {
	    		Integer numBlackPixels = 0; 
	    		
	    		Boolean black1 = false;
	    		Boolean black2 = false;
	    		Boolean black3 = false;
	    		
	    		for (int k = 0; k < imageArray.size(); k++) {
	    			if (imageArray.get(k).getRGB(i, j) != -1) {
	    				if (k == 0) black1 = true;
	    				else if (k == 1) black2 = true;
	    				else if (k == 2) black3 = true;
	    			}
	    		}
	    		
	    		
	    		
	    		if (black1 == true & black2 == false & black3 == false) imageReturn.setRGB(i,j,greenColor);
	    		else if (black1 == false & black2 == true & black3 == false) imageReturn.setRGB(i,j,blueColor);
	    		else if (black1 == true & black2 == true & black3 == false) imageReturn.setRGB(i,j,redColor);
	    		else if (black1 == false & black2 == false & black3 == true) imageReturn.setRGB(i,j,pinkColor);
	    		else if (black1 == true & black2 == false & black3 == true) imageReturn.setRGB(i,j,violetColor);
	    		else if (black1 == false & black2 == true & black3 == true) imageReturn.setRGB(i,j,turquoiseColor);
	    		else if (black1 == true & black2 == true & black3 == true) imageReturn.setRGB(i,j,navyBlueColor);
	    		else imageReturn.setRGB(i,j,-1);

	    		
	    	}
	    		    	
    	}
    	
    	return imageReturn;
    }
    
    
    
    //Overlay images. I.E. take black pixels of one image and place them in another.
    public BufferedImage overlayPixels(BufferedImage image1, BufferedImage image2) {
    	BufferedImage imageReturn = new BufferedImage(image2.getWidth(), image2.getHeight(), BufferedImage.TYPE_INT_ARGB);

    	for(int i = 0 ; i < image1.getWidth() ; i++) {
	    	for(int j = 0 ; j < image1.getHeight() ; j++) {
	    		
	    		imageReturn.setRGB(i,j,-1);
	    		
	    		Integer pixelRGB1 = image1.getRGB(i, j);
	    		Integer pixelRGB2 = image2.getRGB(i, j);
	    		
	    		if (pixelRGB1 != -1 || pixelRGB2 != -1) {
	    			imageReturn.setRGB(i,j,blackColor);
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
	    		    		imageReturn.setRGB(i,j,redColor); //RED
	    		    		pixelPainted = true;
	    		    	}
	    		    }
	    		    else if (cString.equals("G")) {
	    		    	if (pixelRGB1 != -1 & pixelRGB2 == -1) {
	    		    		imageReturn.setRGB(i,j,greenColor); //GREEN
	    		    		pixelPainted = true;
	    		    	}
	    		    }
	    		    else if (cString.equals("B")) {
	    		    	if (pixelRGB1 == -1 & pixelRGB2 != -1) {
	    		    		imageReturn.setRGB(i,j,blueColor); //BLUE
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
		    				(pixelAccount.equals("RG") & pixelRGB != -1 & pixelRGB != blueColor) |
		    				(pixelAccount.equals("RB") & pixelRGB != -1 & pixelRGB != greenColor)) {
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
	    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX-1, currY) != -1 & tempImage.getRGB(currX-1, currY) != blueColor) |
	    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX-1, currY) != -1 & tempImage.getRGB(currX-1, currY) != greenColor)) {
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
		    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX-1, currY-1) != -1 & tempImage.getRGB(currX-1, currY-1) != blueColor) |
		    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX-1, currY-1) != -1 & tempImage.getRGB(currX-1, currY-1) != greenColor)) {
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
		    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX-1, currY+1) != -1 & tempImage.getRGB(currX-1, currY+1) != blueColor) |
		    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX-1, currY+1) != -1 & tempImage.getRGB(currX-1, currY+1) != greenColor)) {
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
	    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX+1, currY) != -1 & tempImage.getRGB(currX+1, currY) != blueColor) |
	    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX+1, currY) != -1 & tempImage.getRGB(currX+1, currY) != greenColor)) {
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
		    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX+1, currY-1) != -1 & tempImage.getRGB(currX+1, currY-1) != blueColor) |
		    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX+1, currY-1) != -1 & tempImage.getRGB(currX+1, currY-1) != greenColor)) {
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
		    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX+1, currY+1) != -1 & tempImage.getRGB(currX+1, currY+1) != blueColor) |
		    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX+1, currY+1) != -1 & tempImage.getRGB(currX+1, currY+1) != greenColor)) {
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
	    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX, currY-1) != -1 & tempImage.getRGB(currX, currY-1) != blueColor) |
	    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX, currY-1) != -1 & tempImage.getRGB(currX, currY-1) != greenColor)) {
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
	    			    			(pixelAccount.equals("RG") & tempImage.getRGB(currX, currY+1) != -1 & tempImage.getRGB(currX, currY+1) != blueColor) |
	    			    			(pixelAccount.equals("RB") & tempImage.getRGB(currX, currY+1) != -1 & tempImage.getRGB(currX, currY+1) != greenColor)) {
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
			Integer numRedPixels = numColorPixels(objectsOverlayRG.get(i),redColor);
			Integer numGreenPixels = numColorPixels(objectsOverlayRG.get(i),greenColor);
			
			Integer numTotalColorPixels = numRedPixels+numGreenPixels;
			
			Integer greenLineWeight = lineWeight(objectsOverlayRG.get(i),greenColor);
			
			// IF percentage of green pixels to total colored pixels is less than 15%
			if (numGreenPixels.doubleValue()/numTotalColorPixels.doubleValue() < 0.15 | greenLineWeight < 2) {
				
				// Make green pixels white in original overlayed image for that particular figure
				for(int j = 0 ; j < objectsOverlayRG.get(i).getWidth() ; j++) {
		    		for(int k = 0 ; k < objectsOverlayRG.get(i).getHeight() ; k++) {
		    			Integer pixelRGB = objectsOverlayRG.get(i).getRGB(j, k);
		    			if (pixelRGB == greenColor) {
		    				image.setRGB(j, k, -1);  
		    				objectsOverlayRG.get(i).setRGB(j, k, -1);
		    			}
		    		}
		    	}	 
			}
			
		}
		
		
		
		// Iterate through different RB objects in Overlayed Image AB
		for (int i = 0; i < objectsOverlayRB.size(); i++) {
			Integer numRedPixels = numColorPixels(objectsOverlayRB.get(i),redColor);
			Integer numBluePixels = numColorPixels(objectsOverlayRB.get(i),blueColor);
			
			Integer numTotalColorPixels = numRedPixels+numBluePixels;
			
			Integer blueLineWeight = lineWeight(objectsOverlayRB.get(i),blueColor);
			
			// IF percentage of blue pixels to total colored pixels is less than 15%
			if (numBluePixels.doubleValue()/numTotalColorPixels.doubleValue() < 0.15 | blueLineWeight < 2) {
				
				// Make blue pixels white in original overlayed image for that particular figure
				for(int j = 0 ; j < objectsOverlayRB.get(i).getWidth() ; j++) {
		    		for(int k = 0 ; k < objectsOverlayRB.get(i).getHeight() ; k++) {
		    			Integer pixelRGB = objectsOverlayRB.get(i).getRGB(j, k);
		    			if (pixelRGB == blueColor) {
		    				image.setRGB(j, k, -1); 
		    				objectsOverlayRB.get(i).setRGB(j, k, -1);
		    			}
		    		}
		    	}	 
			}
			
		}
		
		
		return image;
    
    }
    
    
    
    //Ratio of change between two images. Measured by count of black pixels.
    //Returns array of ratio. If two images passe through, one ratio is returned. 
    // If three images, two ratios are returned.
    public ArrayList<Double> changeRatio(ArrayList<BufferedImage> imageArray) {
    	ArrayList<Double> ratioArray = new ArrayList<Double>();
    	Integer countBlackPixelsImage1 = 0;
    	Integer countBlackPixelsImage2 = 0;
    	Double ratio = 0.0;
    	Integer diffPixels = 0;
    	
    	for (int i = 0; i < imageArray.size()-1; i++) {
    		countBlackPixelsImage1 = numOtherColorPixels(imageArray.get(i), -1);
    		countBlackPixelsImage2 = numOtherColorPixels(imageArray.get(i+1), -1);
    		diffPixels = Math.abs(countBlackPixelsImage2-countBlackPixelsImage1);
    		
    		if (countBlackPixelsImage1 == 0) countBlackPixelsImage1 = 1;
    		ratio = diffPixels.doubleValue()/countBlackPixelsImage1.doubleValue();
    		ratioArray.add(ratio);
    	}
    	
    	return ratioArray;
    }
    
    //Comparison of pixels added between images in an array. Only used for 3x3 problems.
    //Only pass 3 images in the array.
    public ArrayList<Integer> numPixelsChange(ArrayList<BufferedImage> imageArray) {
    	ArrayList<Integer> numPixelsChangeArray = new ArrayList<Integer>();
    	Integer countBlackPixelsImage1 = numOtherColorPixels(imageArray.get(0), -1);
    	Integer countBlackPixelsImage2 = numOtherColorPixels(imageArray.get(1), -1);
    	Integer countBlackPixelsImage3 = numOtherColorPixels(imageArray.get(2), -1);
    	
    	Integer diffPixels1 = countBlackPixelsImage2-countBlackPixelsImage1;
    	Integer diffPixels2 = countBlackPixelsImage3-countBlackPixelsImage2;
 
    	
    	numPixelsChangeArray.add(diffPixels1);
    	numPixelsChangeArray.add(diffPixels2);
 
    	
    	return numPixelsChangeArray;
    }
    
    
    
    /**
     * RETURN
     * 1: Constant Change /
     * 2: Subtract image1 from image2 /
     * 3: Subtract image2 from image1 /
     * 4: Keep constant pixels /
     * 5: Reflection
     * 6: Add images
     * 7: No change
     * 8: Keep non-constant pixels
   */    
    public ArrayList<Integer> problemClassification(ArrayList<BufferedImage> completeImageArray) {
    	
    	ArrayList<BufferedImage> imageArray = new ArrayList<BufferedImage>();
    	
    	ArrayList<Integer> classificationArray = new ArrayList<Integer>();
    	
    	for (int i = 0; i < completeImageArray.size()-2; i+=3) {
    		imageArray.clear();
    		
    		
    		imageArray.add(completeImageArray.get(i));
    		imageArray.add(completeImageArray.get(i+1));
    		imageArray.add(completeImageArray.get(i+2));
	    	//Subtract scenario
	    	BufferedImage subImage1 = subtractPixels(imageArray.get(0), imageArray.get(1));
	    	BufferedImage subImage2 = subtractPixels(imageArray.get(1), imageArray.get(0));
	    	BufferedImage overlaySubImage1Image3 = overlayPixelsHighlight(subImage1, imageArray.get(2), "RGB");
	    	BufferedImage overlaySubImage2Image3 = overlayPixelsHighlight(subImage2, imageArray.get(2), "RGB");
	    	
	    	overlaySubImage1Image3 = removeTrailingPixels(overlaySubImage1Image3);
	    	overlaySubImage2Image3 = removeTrailingPixels(overlaySubImage2Image3);
	    	
	    	
	    	File fileSubImage1 = new File("subImage1.png");
	    	File fileSubImage2 = new File("subImage2.png");
	    	File fileOverlaySubImage1Image3 = new File("overlaySubImage1Image3.png");
	    	File fileOverlaySubImage2Image3 = new File("overlaySubImage2Image3.png");
	    	
	    	try {
	    		ImageIO.write(subImage1, "png", fileSubImage1);
	    		ImageIO.write(subImage2, "png", fileSubImage2);
	    		ImageIO.write(overlaySubImage1Image3, "png", fileOverlaySubImage1Image3);
	    		ImageIO.write(overlaySubImage2Image3, "png", fileOverlaySubImage2Image3);
			} catch (IOException e) {}
	    	
	    	ArrayList<Double> percentRGBSubImage1Image3 = percentRGBtoTotalRGBOneImage(overlaySubImage1Image3, "RGB");
	    	ArrayList<Double> percentRGBSubImage2Image3 = percentRGBtoTotalRGBOneImage(overlaySubImage2Image3, "RGB");
	    	
	    	if (percentRGBSubImage1Image3.get(0) >= percentRedClassification) classificationArray.add(2); 
	    	if (percentRGBSubImage2Image3.get(0) >= percentRedClassification) classificationArray.add(3);
	
	    	
	    	//Only keep constant pixels
	    	BufferedImage constantImage = constantPixelsFromArray(imageArray);
	    	BufferedImage overlayconstantImageImage3 = overlayPixelsHighlight(constantImage, imageArray.get(2), "RGB");
	    	
	    	overlayconstantImageImage3 = removeTrailingPixels(overlayconstantImageImage3);
	    	
	    	
	    	ArrayList<Double> percentRGBconstantImageImage3 = percentRGBtoTotalRGBOneImage(overlayconstantImageImage3, "RGB");
	    	if (percentRGBconstantImageImage3.get(0) >= percentRedClassification) classificationArray.add(4);
	    	
	    	
	    	//Constant change
	    	ArrayList<Integer> numPixelsChangeArray = numPixelsChange(imageArray);
	    	if (Math.abs(numPixelsChangeArray.get(0)-numPixelsChangeArray.get(1)) < numPixelsDiffConstantChange) classificationArray.add(1);
	    	
	    	
	    	
	    	//Reflection////////////////
	    	BufferedImage reflectVerFigure1 = reflectImageVertically(imageArray.get(0));   		
			
			BufferedImage overlayPixels12Reflect = overlayPixelsHighlight(reflectVerFigure1, imageArray.get(2), "RGB");
	
			overlayPixels12Reflect = removeTrailingPixels(overlayPixels12Reflect);
			
			File fileOverlayPixels12Reflect = new File("overlayPixels12Reflect.png");
	    	
	    	try {
	    		ImageIO.write(overlayPixels12Reflect, "png", fileOverlayPixels12Reflect);
			} catch (IOException e) {}
	    	
	    	ArrayList<Double> rgbPer12Reflect = percentRGBtoTotalRGBOneImage(overlayPixels12Reflect, "RGB");
	   	
	    	/*Double sumPercentReflect = 0.0;
	    	for (int j = 1; j < rgbPer12Reflect.size(); j++) {
	    		sumPercentReflect+=rgbPer12Reflect.get(j);
	    	}
	    	if (sumPercentReflect < 0.01) classificationArray.add(5);*/
	    	
	    	
	    	if (rgbPer12Reflect.get(0) >= percentRedClassification) classificationArray.add(5);
	    	
	    	
	    	
	    	//END Reflection//////////////////////
	    	
	    	
	    	//Add Images
	    	BufferedImage addImage = overlayPixels(imageArray.get(0), imageArray.get(1));
	    	BufferedImage overlayAddImagesImage3 = overlayPixelsHighlight(addImage, imageArray.get(2), "RGB");
	    	
	    	overlayAddImagesImage3 = removeTrailingPixels(overlayAddImagesImage3);
	    	
	    	File fileAddImage = new File("addImage.png");
	    	File fileOverlayAddImagesImage3 = new File("overlayAddImagesImage3.png");
	    	
	    	try {
	    		ImageIO.write(addImage, "png", fileAddImage);
	    		ImageIO.write(overlayAddImagesImage3, "png", fileOverlayAddImagesImage3);
			} catch (IOException e) {}
	    	
	    	ArrayList<Double> percentRGBAddImageImage3 = percentRGBtoTotalRGBOneImage(overlayAddImagesImage3, "RGB");
	    	

	    	if (percentRGBAddImageImage3.get(0) >= percentRedClassification) classificationArray.add(6); 
	    	
	    	
	    	//No change
	    	BufferedImage overlayNoChange12 = overlayPixelsHighlight(imageArray.get(0), imageArray.get(1), "RGB");
	    	BufferedImage overlayNoChange23 = overlayPixelsHighlight(imageArray.get(1), imageArray.get(2), "RGB");
	    	
	    	overlayNoChange12 = removeTrailingPixels(overlayNoChange12);
	    	overlayNoChange23 = removeTrailingPixels(overlayNoChange23);
	    	
	    	File fileOverlayNoChange12 = new File("overlayNoChange12.png");
	    	File fileOverlayNoChange23 = new File("overlayNoChange23.png");
	    	
	    	try {
	    		ImageIO.write(overlayNoChange12, "png", fileOverlayNoChange12);
	    		ImageIO.write(overlayNoChange23, "png", fileOverlayNoChange23);
			} catch (IOException e) {}
	    	
	    	ArrayList<Double> percentRGBNoChange12 = percentRGBtoTotalRGBOneImage(overlayNoChange12, "RGB");
	    	ArrayList<Double> percentRGBNoChange23 = percentRGBtoTotalRGBOneImage(overlayNoChange23, "RGB");
	    	
	    	if ((percentRGBNoChange12.get(0) >= percentRedClassification) &
	    			(percentRGBNoChange23.get(0) >= percentRedClassification)) 
	    					classificationArray.add(7); 
    	
	    	
	    	
	    	//Only keep non-constant pixels
	    	BufferedImage nonConstantImage = nonConstantPixelsFromArray(imageArray.get(0), imageArray.get(1));
	    	BufferedImage overlayNonConstantImage3 = overlayPixelsHighlight(nonConstantImage, imageArray.get(2), "RGB");
	    	
	    	overlayNonConstantImage3 = removeTrailingPixels(overlayNonConstantImage3);
	    	
	    	
	    	ArrayList<Double> percentRGBNonConstantImageImage3 = percentRGBtoTotalRGBOneImage(overlayNonConstantImage3, "RGB");
	    	if (percentRGBNonConstantImageImage3.get(0) >= percentRedClassification) classificationArray.add(8);
	    	
	    	
    	}
    	
    	return classificationArray;
    }
    
    
    
    
    
    
    // Blurring - Convolution Filter
    public BufferedImage convFilter(BufferedImage image) {
    	BufferedImage imageReturn = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    	ArrayList<ArrayList <Double>> filterArray = new ArrayList<ArrayList <Double>>();
    	
    	Double factor = 1.0 / 13.0;
    	Double bias = 0.0;
    	
    	ArrayList<Double> filterRow1 = new ArrayList<Double>();
    	filterRow1.add(0.0);
    	filterRow1.add(0.0);
    	filterRow1.add(1.0);
    	filterRow1.add(0.0);
    	filterRow1.add(0.0);
    	ArrayList<Double> filterRow2 = new ArrayList<Double>();
    	filterRow2.add(0.0);
    	filterRow2.add(1.0);
    	filterRow2.add(1.0);
    	filterRow2.add(1.0);
    	filterRow2.add(0.0);
    	ArrayList<Double> filterRow3 = new ArrayList<Double>();
    	filterRow3.add(1.0);
    	filterRow3.add(1.0);
    	filterRow3.add(1.0);
    	filterRow3.add(1.0);
    	filterRow3.add(1.0);
    	ArrayList<Double> filterRow4 = new ArrayList<Double>();
    	filterRow4.add(0.0);
    	filterRow4.add(1.0);
    	filterRow4.add(1.0);
    	filterRow4.add(1.0);
    	filterRow4.add(0.0);
    	ArrayList<Double> filterRow5 = new ArrayList<Double>();
    	filterRow5.add(0.0);
    	filterRow5.add(0.0);
    	filterRow5.add(1.0);
    	filterRow5.add(0.0);
    	filterRow5.add(0.0);
    	
    	filterArray.add(filterRow1);
    	filterArray.add(filterRow2);
    	filterArray.add(filterRow3);
    	filterArray.add(filterRow4);
    	filterArray.add(filterRow5);
    	
    	Integer filterWidth = filterArray.size();
    	Integer filterHeight = filterArray.get(0).size();
    	
    	for (int i = 0; i < image.getWidth(); i++) {
    		for (int j = 0; j < image.getHeight(); j++) {
    			Double alpha = 0.0;
    			Double red = 0.0;
    			Double green = 0.0;
    			Double blue = 0.0;
    			
    			int imageAlpha = (image.getRGB(i, j) >>> 24) & 0xFF;
    			int imageRed = (image.getRGB(i, j) >>> 16) & 0xFF;
    			int imageGreen = (image.getRGB(i, j) >>> 8) & 0xFF;
    			int imageBlue = (image.getRGB(i, j) >>> 0) & 0xFF;
    			
    			//multiply every value of the filter with corresponding image pixel 
    			for (int x = 0; x < filterWidth; x++) {
    				for (int y = 0; y < filterHeight; y++) {
    					Integer imageX = (x - filterWidth / 2 + x + image.getWidth()) % image.getWidth(); 
    		            int imageY = (y - filterHeight / 2 + y + image.getHeight()) % image.getHeight(); 
    		            alpha += imageAlpha * filterArray.get(x).get(y);
    		            red += imageRed * filterArray.get(x).get(y); 
    		            green += imageGreen * filterArray.get(x).get(y); 
    		            blue += imageBlue * filterArray.get(x).get(y); 
    				}
    			}
    			
    			//truncate values smaller than zero and larger than 255 
    			Double alphaFactorBiasDouble = factor * alpha + bias;
    			Integer alphaFactorBiasInt = alphaFactorBiasDouble.intValue();    			
    			Double redFactorBiasDouble = factor * red + bias;
    			Integer redFactorBiasInt = redFactorBiasDouble.intValue();
    			Double greenFactorBiasDouble = factor * green + bias;
    			Integer greenFactorBiasInt = greenFactorBiasDouble.intValue();
    			Double blueFactorBiasDouble = factor * blue + bias;
    			Integer blueFactorBiasInt = blueFactorBiasDouble.intValue();
    			
    			Integer alphaFinal = Math.min(Math.max(alphaFactorBiasInt, 0), 255); 
    	        Integer redFinal = Math.min(Math.max(redFactorBiasInt, 0), 255); 
    	        Integer greenFinal = Math.min(Math.max(greenFactorBiasInt, 0), 255);
    	        Integer blueFinal = Math.min(Math.max(blueFactorBiasInt, 0), 255);
    	        
    	        Integer pixelFinal = (alphaFinal << 24) | (redFinal << 16) | (greenFinal << 8) | blueFinal;	
    	        imageReturn.setRGB(i,j,pixelFinal);
    		}
    	}
    	
    	return imageReturn;
    }
    
    //Resize Image
    public BufferedImage imageResize(BufferedImage image, Integer percentScale) {
    	Double actualScale = 100.0/percentScale;
    	Double newWidthDouble = image.getWidth()/actualScale;
    	Double newHeightDouble = image.getHeight()/actualScale;
    	Integer newWidth = newWidthDouble.intValue();
    	Integer newHeight = newHeightDouble.intValue();
    	BufferedImage imageReturn = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
    	
    	for (int i = 0; i < image.getWidth(); i+=actualScale) {
    		for (int j = 0; j < image.getHeight(); j+=actualScale) {
    			
    			imageReturn.setRGB(i/actualScale.intValue(), j/actualScale.intValue(), -1);
    			
    			for (int k = i; k < actualScale+i; k++) {
    				for (int l = j; l < actualScale+j; l++) {
    					
    					if (image.getRGB(k, l) != -1) imageReturn.setRGB(i/actualScale.intValue(), 
    							j/actualScale.intValue(), blackColor);
    					
    				}
    			}
    		}
    	
    	}
    	File fileResizedImage = new File("resizedImage.png");
    	
    	
    	try {
    		ImageIO.write(imageReturn, "png", fileResizedImage);
		} catch (IOException e) {}
    	return imageReturn;
    }
    
    //Include Distance in the distanceArrays?
    public Boolean includeDistance(ArrayList<Double> doubleArray) {
    	Double minDist = 0.0;
    	ArrayList<Double> sortedArray = (ArrayList<Double>) doubleArray.clone();
    	Collections.sort(sortedArray);
    	minDist = sortedArray.get(0);
    	if ((minDist < minDistance) | ((sortedArray.get(1) - sortedArray.get(0)) > minDistanceDiff)) {
    		return true;
    	}
    	else return false;

    }
    
}
