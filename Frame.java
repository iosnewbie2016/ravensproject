package ravensproject;

import java.util.HashMap;

public class Frame {
	public String name;
	public String shape;
	public String size;
	public String fill;
	public Double angle;
	public String alignment;
	public HashMap<String,Frame> inside = new HashMap<String, Frame>();
	public HashMap<String,Frame> hasInside = new HashMap<String, Frame>();
	public HashMap<String,Frame> above = new HashMap<String, Frame>();
	public HashMap<String,Frame> below = new HashMap<String, Frame>();
	
	Frame(String name) {
		this.name = name;
	}
	
	Frame(Frame frame) {
		this.name = frame.name;
		this.shape = frame.shape;
		this.size = frame.size;
		this.fill = frame.fill;
		this.angle = frame.angle;
		this.alignment = frame.alignment;
		this.inside = frame.inside;
		this.hasInside = frame.hasInside;
		this.above = frame.above;
		this.below = frame.below;
	}
	
}

