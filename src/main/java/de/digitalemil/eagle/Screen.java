package de.digitalemil.eagle;

import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

public class Screen {
	protected int screen;
	public final static int STAYONSCREEN= -1;
	public final static int MAXSCREEN= 256;
	protected static Screen[] screens;//
	protected static int activeScreen= -1;
	private long _start, frames;
	protected int fps, id, numberOfThings;
	protected static int nextscreen= STAYONSCREEN;
	
	public Screen() {
		_start = 0;
	}
	
	@MethodDefinitionChangerAnnotation({ "BY", "Thing[]", "Thing**", "BY", "things[i]=null", "delete things[i]", "BY", "things=null", "delete [ ] things" })
	protected void finalize() throws Throwable {
		Thing[] things= Globals.getAllThings();
		
		for(int i= 0; i< numberOfThings; i++) {
			things[i]= null;
		}
	}
	
	public int getNumberOfThings() {
		return numberOfThings;
	}
	
	public int getFps() {
		return fps;
	}
	
	public void update(long currentTimeMillis) {
	//	System.out.println("update: "+ frames);
		Globals.frames++;
		frames++;
		if (_start == 0)
			_start = PartAnimation.currentTimeMillis();

		long now = PartAnimation.currentTimeMillis();
		if (now - _start > 1000) {
			fps = (int)((1000 * frames) / (now - _start));
			_start = 0;
			frames = 0;
		}
	}

	public static Screen getActiveScreen() {
		if(activeScreen< 0 || activeScreen>= MAXSCREEN)
			return null;
		return screens[activeScreen];
	}
		
	
	public void activate() {
		activeScreen= getScreenID();
		System.out.println("Screen "+ this);
		System.out.println("Screen "+ getActiveScreen());
		nextscreen= STAYONSCREEN;	
	}
	
	@MethodDefinitionChangerAnnotation({ "BY", "int i", "int i; finalize()" })
	public void deactivate() {
		int i;
	}
	
	static public int moveToOtherScreen() {
		return nextscreen;
	}
	
	public int fillThingArray(Thing[] things) {
		
		return getNumberOfThings();
	}
	
	public void touch(int x, int y) {
	}

	public boolean touchStart(int x, int y) {
		return false;
	}

	public boolean touchStop(int x, int y) {
		return false;
	}
	
	public int getScreenID() {
		return id;
	}
	
	public static Screen getScreen(int id) {
		return screens[id];
	}
	
	public static void registerScreen(Screen screen) {
		screens[screen.getScreenID()]= screen;
	}

	public int getBackgroundColor() {
		return 0;
	}
	
}
