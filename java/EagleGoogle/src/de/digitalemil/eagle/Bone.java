package de.digitalemil.eagle;

public class Bone extends Part implements CanHaveChilds {
	protected float[] data;
	protected Part[] parts;
	protected int nd = 0;
	
	/* For j2objc */
	public void setData(float[] data) {
		this.data = data;
	}

	public float[] getData() {
		return data;
	}

	public Part[] getParts() {
		return parts;
	}
	
	public int getNd() {
		return nd;
	}
	/* End for j2objc */

	protected BoundingCircle[] bcs;
	protected int pn = 0;
	protected int nbcs = 0;
	protected boolean visible = true;

	public Bone(float x, float y, float z, float r, int n) {
		setRoot(x, y, z, r);
		parts = new Part[n];
	}

	public int getNumberOfBCs() {
		return nbcs;
	}

	public BoundingCircle[] getBCs() {
		if (bcs == null) {
			bcs = new BoundingCircle[nbcs];
			addBCs(bcs, 0);
		}
		return bcs;
	}

	public int addBCs(BoundingCircle bcarray[], int start) {
		for (int i = 0; i < this.pn; i++) {
			if (parts[i].getType() == Types.BOUNDINGCIRCLE) {

				bcarray[start++] = (BoundingCircle) parts[i];
			}
			if (parts[i] instanceof CanHaveChilds) {
				start = ((CanHaveChilds) parts[i]).addBCs(bcarray, start);
			}
		}
		return start;
	}

	public void beginTX() {
		if (!supportsTX())
			return;
		super.beginTX();
		for (int i = 0; i < this.pn; i++) {
			if (parts[i].supportsTX())
				parts[i].beginTX();
		}
	}

	public void commitTX() {
		if (!supportsTX())
			return;
		super.commitTX();
		for (int i = 0; i < this.pn; i++) {
			if (parts[i].supportsTX())
				parts[i].commitTX();
		}
	}

	public void rollbackTX() {
		if (!supportsTX())
			return;
		super.rollbackTX();
		for (int i = 0; i < this.pn; i++) {
			if (parts[i].supportsTX())
				parts[i].rollbackTX();
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Part getByName(String n) {
		Part result = null;
		if (name != null && name == n) {
			return this;
		}
		for (int i = 0; i < pn; i++) {
			String pn = parts[i].name;
			if (pn != null) {
				if (n.equals(parts[i].name)) {
					result = parts[i];
					break;
				}
			}
			if (parts[i].getType() == Types.BONE) {
				result = ((Bone) parts[i]).getByName(n);
			}
			if (result != null) {
				return result;
			}
		}
		return result;
	}

	public void add(Part p, float x, float y, float z, float r) {
		p.translateRoot(x, y, z);
		p.rotate(r);
		p.setParent(this);
		parts[pn++] = p;
		a = p.a;
	}

	public void addPart(Part p) {
		add(p, 0.0f, 0.0f, 0.0f, 0.0f);
	}

	public int getNumberOfData() {
		return nd;
	}

	public void setupDone() {
		nd = 0;
		nbcs= 0;
		for (int i = 0; i < pn; i++) {
			nd += parts[i].getNumberOfData();
			if (parts[i].getType() == Types.BOUNDINGCIRCLE)
				nbcs++;
			if (parts[i] instanceof CanHaveChilds) {
				nbcs += ((CanHaveChilds) parts[i]).getNumberOfBCs();
			}
		}
		invalidateData();
	}

	public int getData(float[] d, int startD, float xn, float yn, float zn,
			float a11, float a21, float a12, float a22) {
		if (!invaliddata)
			return getNumberOfData();
		int sv = startD;
		int phi = Math.round(-(rot + rrot));
		while (phi < 0)
			phi += 360;
		phi %= 360;
		float cphi = mycos[phi];
		float sphi = mysin[phi];

		float ta11 = cphi;
		float ta21 = sphi;
		float ta12 = -sphi;
		float ta22 = cphi;
		xn = xn + a11 * (rx + x) + a12 * (ry + y);
		yn = yn + a21 * (rx + x) + a22 * (ry + y);

		if (coordtap != null)
			coordtap.save(xn, yn, phi, a11, a21, a12, a22);
		float na11 = (ta11 * a11 + ta12 * a21) * sx * rsx;
		float na12 = (ta11 * a12 + ta12 * a22) * sy * rsy;
		float na21 = (ta21 * a11 + ta22 * a21) * sx * rsx;
		float na22 = (ta21 * a12 + ta22 * a22) * sy * rsy;

		if (!visible) {
			xn = -100000;
		}
		for (int i = 0; i < pn; i++) {
			sv += parts[i].getData(d, sv, xn, yn, rz + z + zn, na11, na21,
					na12, na22);
		}
		return nd;
	}

	public boolean invalidateData() {
		boolean ret = invaliddata;
		invaliddata = true;
		if (parent != null)
			parent.invaliddata = true;
		for (int i = 0; i < pn; i++) {
			parts[i].invalidateData();
		}
		return ret;
	}

	public void setVisibility(boolean v) {
		visible = v;
		invalidateData();
	}

	public void setColor(int c) {
		invalidateData();
		a = (c >> 24);
		r = ((c & 0x00FF0000) >> 16);
		g = ((c & 0x0000FF00) >> 8);
		b = ((c & 0x000000FF));
		for (int i = 0; i < pn; i++) {
			parts[i].setColor(c);
		}
	}

	public void setAlpha(int c) {
		invalidateData();
		a = c;
		for (int i = 0; i < pn; i++) {
			parts[i].setAlpha(c);
		}
	}

	public int getType() {
		return Types.BONE;
	}

	public void highlight(boolean b) {
		invalidateData();

		highlighted = b;
		for (int i = 0; i < pn; i++) {
			parts[i].highlight(b);
		}
	}

	public void reset() {
		invalidateData();
		super.reset();
		for (int i = 0; i < pn; i++) {
			parts[i].reset();
		}
	}
}
