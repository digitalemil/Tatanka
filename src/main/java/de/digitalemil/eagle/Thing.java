//
package de.digitalemil.eagle;

import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

public class Thing extends Bone {
	protected int nbc, layer;
	protected boolean changed, cancollide, nonvisiblecancollide, movable;
	protected float maxr;
	protected CollisionHandler collisionHandler;
	

	public Thing(int n) {
		super(0.0f, 0.0f, 0.0f, 0.0f, n);
		movable = true;
		maxr = 0;
		nbc = 0;
		changed = true;
		cancollide = true;
	}
	
	@MethodDefinitionChangerAnnotation({"BY", "textAndFont=null", "delete [ ] textAndFont", "BY", "collisionHandler=null", "delete collisionHandler", "BY", "data=null", "delete [ ] data" })
	protected void finalize() throws Throwable {
		textAndFont= null;
		data= null;
	}
	
	public boolean isIn(int x, int y) {
		return false;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public void setCollisionHandler(CollisionHandler handler) {
		collisionHandler = handler;
	}

	public CollisionHandler getCollisionHandler() {
		return collisionHandler;
	}

	public int getType() {
		return Types.THING;
	}

	public void changeHandled() {
		changed = false;
	}

	public void change() {
		changed = true;
	}

	public float getRotation() {
		return rot;
	}

	public boolean hasChanged() {
		return changed;
	}

	public String[] getThingTextAndFont() {
		String [] taf= getTextAndFont();
		if (taf == null) {
			taf= new String[this.nt];
			setTextAndFont(taf);
		}
		getTextAndFont(taf, 0);
		return taf;	
	}
	
	public float[] getThingData() {
		if (getData() == null) {
			setData(new float[getNumberOfData()]);
		}
		getData(getData(), 0, 0, 0, 0, 1.0f, 0, 0, 1.0f);
		if (collisionHandler != null) {
			collisionHandler.checkCollision();
		}
		return getData();
	}

	public void reset() {
		invalidateData();
		sx= 1.0f;
		sy= 1.0f;
		
		for (int i = 0; i < getNumberOfParts(); i++) {
			getParts()[i].reset();
		}
	}

	public void deleteData() {
		setData(null);
		invalidateData();
	}
}
