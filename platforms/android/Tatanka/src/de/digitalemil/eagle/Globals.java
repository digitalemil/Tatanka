package de.digitalemil.eagle;

public class Globals {
	private static int width, height;
	private static float scale;
	private static int w2;
	private static int h2;
	private static Thing[] allThings;
	private static int defaultWidth= 768;
	private static int defaultHeight= 1024;
	protected static int frames= 0;
	
	public static int getFrames() {
		return frames;
	}

	public static int getWidth() {
		return width;
	}
	
	public static int getHeight() {
		return height;
	}
	
	public static int getW2() {
		return w2;
	}
	
	public static int getH2() {
		return h2;
	}
	
	public static float getScale() {
		return scale;
	}
	

	private static void calcScale() { 
		scale = Math.min((float)width / defaultWidth,
				(float)height / defaultHeight);
		System.out.println("scale: "+scale);
	}
	
	private static void setWidth(int width) {
		Globals.width = width;
		w2= width/2;
	}
	
	private static void setHeight(int height) {
		Globals.height = height;
		h2= height/2;
	}
	
	public static void set(int w, int h) {
		setWidth(w);
		setHeight(h);
		calcScale();
	}
	
	protected static void setAllThings(Thing[] all) {
		allThings= all;
	}
	
	public static int getDefaultWidth() {
		return defaultWidth;
	}

	public static int getDefaultHeight() {
		return defaultHeight;
	}

	public static void setDefaults(int defaultWidth, int defaultHeight) {
		Globals.defaultWidth = defaultWidth;
		Globals.defaultHeight = defaultHeight;
		calcScale();
	}

	public static Thing[] getAllThings() {
		return allThings;
	}
}
