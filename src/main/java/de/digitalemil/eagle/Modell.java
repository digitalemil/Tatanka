package de.digitalemil.eagle;

public abstract class Modell {

	protected Thing things[];
	protected int numberOfThings;
	private long start, frames;
	private int fps;

	public Modell(int n) {
		things = new Thing[n];
		Globals.setAllThings(things);
		start = 0;
		setup();
	}

	public abstract void setup();

	public void update(long currentTimeMillis) {
	//	System.out.println("update: "+ frames);
		Globals.frames++;
		frames++;
		if (start == 0)
			start = PartAnimation.currentTimeMillis();

		long now = PartAnimation.currentTimeMillis();
		if (now - start > 1000) {
			fps = (int)((1000 * frames) / (now - start));
			start = 0;
			frames = 0;
		}
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

	public String getTexNameFromQuad(int t) {
		return ((Quad) things[t]).getTexName();
	}

	public int getType(int t) {
		return things[t].getType();
	}

	public int getTexID(int t) {

		return ((Quad) things[t]).getTexID();
	}

	public float[] getData(int t) {
		return things[t].getThingData();
	}

	public float[] getTex(int t) {
		return ((Quad) things[t]).getTex();
	}

	public int getNumberOfData(int t) {
		return things[t].getNumberOfData();
	}

	public boolean texNameChanged(int t) {
		return ((Quad) things[t]).isTexchanged();
	}

	public boolean isTexIDSet(int t) {
		return ((Quad) things[t]).isTexidset();
	}

	public void setTexIDForQuad(int t, int i) {
		((Quad) things[t]).setTexID(i);
	}

	public boolean skipFrame() {
		// TODO Auto-generated method stub
		return false;
	}

	public void moveViewport(int i, int j) {
		// TODO Auto-generated method stub

	}
}