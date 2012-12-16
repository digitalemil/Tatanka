//
package de.digitalemil.eagle;

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

	public float[] getThingData() {
		if (getData() == null) {
			setData(new float[getNd()]);
		}
		getData(getData(), 0, 0, 0, 0, 1.0f, 0, 0, 1.0f);
		if (collisionHandler != null) {
			collisionHandler.checkCollision();
		}
		return getData();
	}

	public void reset() {
		invalidateData();
		for(int i= 0; i< pn; i++) {
			getParts()[i].reset();
		}
	}
	
	public void deleteData() {
		setData(null);
		invalidateData();
	}
}
