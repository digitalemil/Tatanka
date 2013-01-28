package de.digitalemil.eagle;

import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

public abstract class Modell {
	protected int maxthings;
	protected Thing things[];
	protected int numberOfThings;

	@MethodDefinitionChangerAnnotation({ "BY", "new Thing",
			"(Thing **)new void*", "BY", "Globals",
			"for(int i=0; i< n; i++) things[i]= 0; Globals" })
	public Modell(int n) {
		things = new Thing[n];
		Globals.setAllThings(things, n);
	}

	public void setup() {
	}
	
	
	public int moveToOtherScreen() {
		return Screen.getActiveScreen().moveToOtherScreen();
	}

	public void showScreen(int id) {
		if (Screen.getActiveScreen() != null)
			Screen.getActiveScreen().deactivate();
		Screen.getScreen(id).activate();
	}

	public void update(long currentTimeMillis) {
		if (Screen.getActiveScreen() != null)
			Screen.getActiveScreen().update(currentTimeMillis);
	}

	public String[] getTextAndFont(int t) {
		return things[t].getThingTextAndFont();
	}

	public int getFps() {
		if (Screen.getActiveScreen() != null)
			return Screen.getActiveScreen().getFps();
		return 0;
	}

	public void touch(int x, int y) {
		Screen.getActiveScreen().touch(x, y);
	}

	public boolean touchStart(int x, int y) {
		return Screen.getActiveScreen().touchStart(x, y);
	}

	public boolean touchStop(int x, int y) {
		return Screen.getActiveScreen().touchStop(x, y);
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
		if (Screen.getActiveScreen() != null)
			return Screen.getActiveScreen().getNumberOfThings();
		return -1;
	}

	public int getBackgroudColor() {
		if (Screen.getActiveScreen() != null)
			return Screen.getActiveScreen().getBackgroundColor();
		return 0;
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
		if (!(things[t].getType() == Types.IMAGE))
			return 0;
		return (int) ((ImageThing) things[t]).getWidth();
	}

	public int getImageHeight(int t) {
		if (!(things[t].getType() == Types.IMAGE))
			return 0;
		return (int) ((ImageThing) things[t]).getHeight();
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