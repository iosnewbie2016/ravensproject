package ravensproject;

import java.util.HashMap;

public class Frame {
	public String name;
	public String shape;
	public String size;
	public String fill;
	public Number angle;
	public String alignment;
	public HashMap<String,Frame> inside = new HashMap<String, Frame>();
	
	Frame(String name) {
		this.name = name;
	}
	
	
}

