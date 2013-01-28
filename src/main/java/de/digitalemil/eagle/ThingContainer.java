package de.digitalemil.eagle;

import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

public class ThingContainer {
	protected int n;
	protected Thing things[];
	protected int layers[];
	protected int pos;

	public ThingContainer() {
	}

	@MethodDefinitionChangerAnnotation({ "BY", "new Thing", "new Thing*" })
	public void init(int nthings) {
		n = nthings;
		things = new Thing[n];
		layers = new int[1000];
	}

	@MethodDefinitionChangerAnnotation({"BY", "things[i]=null", "delete things[i]", "BY", "things=null", "delete [ ] things", "BY", "layers=null", "delete [ ] layers"})
	protected void finalize() throws Throwable {
		for (int i = 0; i < n; i++) {
			things[i] = null;
		}
		things = null;
		layers = null;
	}

	public int addThings(Thing[] in, int p, int layer) {
		int ninlayer = 0;
		for (int i = 0; i < n; i++) {
			if (things[i].getLayer() == layer) {
				// System.out.println("adding: "+things[i].getName()+" at: "+p);
				in[p++] = things[i];
				layers[layer] = p;
				ninlayer++;
			}
		}
		return ninlayer;
	}

	public void removeThings(Thing[] objs) {
		int lastlayer = -1;
		int thingsinlayer = 0;
		for (int i = 0; i < n; i++) {
			int layer = things[i].getLayer();
			int p = layers[layer];

			if (lastlayer != layer)
				thingsinlayer = 0;
			else
				thingsinlayer++;

			objs[p + thingsinlayer] = null;
		}
	}

	public void beginTX() {
		for (int i = 0; i < n; i++) {
			if (things[i] != null)
				things[i].beginTX();
		}
	}

	public void commitTX() {
		for (int i = 0; i < n; i++) {
			if (things[i] != null)
				things[i].commitTX();
		}
	}

	public void rollbackTX() {
		for (int i = 0; i < n; i++) {
			if (things[i] != null)
				things[i].rollbackTX();
		}
	}

	public void translate(float ix, float iy, float iz) {
		for (int i = 0; i < n; i++) {
			if (things[i] != null)
				things[i].translate(ix, iy, iz);
		}
	}

	public void rotate(float deg) {
		for (int i = 0; i < n; i++) {
			if (things[i] != null)
				things[i].rotate(deg);
		}
	}

	public void scale(float sx, float sy) {
		for (int i = 0; i < n; i++) {
			if (things[i] != null)
				things[i].scale(sx, sy);
		}
	}

	public void translateRoot(float x, float y, float z) {
		for (int i = 0; i < n; i++) {
			if (things[i] != null)
				things[i].translateRoot(x, y, z);
		}
	}

	public void rotateRoot(float deg) {
		for (int i = 0; i < n; i++) {
			if (things[i] != null)
				things[i].rotateRoot(deg);
		}
	}

	public void scaleRoot(float sx, float sy) {
		for (int i = 0; i < n; i++) {
			if (things[i] != null)
				things[i].scaleRoot(sx, sy);
		}
	}

	public int getType() {
		return Types.THINGCONTAINER;
	}

}
