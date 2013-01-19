package de.digitalemil.eagle;

public abstract class Modell {

	protected Thing things[];
	protected int numberOfThings;
	private long _start, frames;
	private int fps;


	@SearchAndReplaceAnnotation({ "BY", "new Thing", "new Thing*" })
	public Modell(int n) {
		things = new Thing[n];
		Globals.setAllThings(things);
		_start = 0;
		setup();
	}

	public abstract void setup();

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

	public String[] getTextAndFont(int t) {
		return things[t].getThingTextAndFont();
	}

	public int getFps() {
		return fps;
	}

	public void touch(int x, int y) {
	}

	public boolean touchStart(int x, int y) {
		return false;
	}

	public boolean touchStop(int x, int y) {
		return false;
	}

	public Thing[] getThings() {
		return things;
	}

	public void keyPressed(int i) {
		// TODO Auto-generated method stub

	}

	public void zoom(int i) {
		// TODO Auto-generated method stub

	}

	public int getNumberOfThings() {
		// TODO Auto-generated method stub
		return numberOfThings;
	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public boolean isVisible(int t) {
		return things[t].isVisible();
	}

	public boolean hasChanged(int t) {
		return things[t].hasChanged();
	}

	public String getImageName(int t) {
		return ((ImageThing) things[t]).getTexName();
	}

	public int getType(int t) {
		return things[t].getType();
	}

	public int getTexID(int t) {
		return ((ImageThing) things[t]).getTexID();
	}

	public int getImageWidth(int t) {
		if(! (things[t].getType() == Types.IMAGE))
			return 0;
		return (int)((ImageThing) things[t]).getWidth();
	}

	public int getImageHeight(int t) {
		if(! (things[t].getType() == Types.IMAGE))
			return 0;
		return (int)((ImageThing) things[t]).getHeight();
	}
	
	public float[] getData(int t) {
		return things[t].getThingData();
	}

	public int getNumberOfData(int t) {
		return things[t].getNumberOfData();
	}

	public boolean imageNameChanged(int t) {
		return ((ImageThing) things[t]).isTexchanged();
	}

	public boolean isTexIDSet(int t) {
		return ((ImageThing) things[t]).isTexidset();
	}

	public void setTexIDForQuad(int t, int i) {
		((ImageThing) things[t]).setTexID(i);
	}

	public boolean skipFrame() {
		// TODO Auto-generated method stub
		return false;
	}

	public void moveViewport(int i, int j) {
		// TODO Auto-generated method stub

	}
}