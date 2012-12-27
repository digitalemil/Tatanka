package de.digitalemil.eagle;

public class BoundingCircle extends Ellipse {
	public final static boolean visibleboundingcircle = false;

	protected int start;

	public BoundingCircle(float ir, float irx, float iry, float irz) {
		super(ir, ir, irx, iry, irz, 0.0f, Ellipse.TRIANGLES10, 0xFFFF0000);
		setRoot(irx, iry, irz, 0);
		start = 0;
	}

	public int getNumberOfData() {
		if (visibleboundingcircle)
			return 4 + 1 +2 * (this.triangles + 1);
		return 4 + 3;
	};

	public int getData(float[] d, int startD, float xn, float yn, float zn,
			float a11, float a21, float a12, float a22) {
		if (!invaliddata)
			return getNumberOfData();
	
		this.invaliddata = false;
		int n = startD;
		d[n++] = this.getType();
		d[n++] = this.maxeff;
		d[n++] = (this.a << 24) | (this.r << 16) | (this.g << 8) | this.b;
		d[n++] = (this.maxeff == this.triangles) ? 1 : 0;
		
		int phi = calcPhi(this.rot + this.rrot);
		float sinbeta = Part.mysin[phi];
		float cosbeta = Part.mycos[phi];
		float dummy;
		float dummy2;
		float rx1 = this.rx + this.x;
		float ry1 = this.ry + this.y;
		dummy = rx1;
		dummy2 = ry1;
		d[n] = Math.round(dummy * a11 + dummy2 * a12 + xn);
		d[n + 1] = Math.round(dummy * a21 + dummy2 * a22 + yn);
		d[n + 2] = this.r1;

		if (coordtap != null) {
			coordtap.save(d[n], d[n + 1], d[n + 2], a11, a21, a12, a22);
		}

		n += 3;
		if (!visibleboundingcircle) {
			return this.getNumberOfData();
		}
		for (int i = 0; i < maxeff; i++, n += 2) {
			float sinalpha = mysin[mycs[this.triangles] * 24 + i];
			float cosalpha = mycos[mycs[this.triangles] * 24 + i];

			dummy = rx1
					+ (this.r1 * cosalpha * cosbeta - this.r2 * sinalpha
							* sinbeta);
			dummy2 = ry1
					+ (this.r1 * cosalpha * sinbeta + this.r2 * sinalpha
							* cosbeta);
			d[n] = Math.round(dummy * a11 + dummy2 * a12 + xn);
			d[n + 1] = Math.round(dummy * a21 + dummy2 * a22 + yn);
		}

		return this.getNumberOfData();
	};

	public int getType() {
		return Types.BOUNDINGCIRCLE;
	};

	public int getStart() {
		return start;
	};
}