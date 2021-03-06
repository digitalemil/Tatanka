package de.digitalemil.eagle;

public class Triangle extends Part {
	protected float x1, y1, x2, y2, x3, y3;

	public Triangle(float rx, float ry, float rz, float px1, float py1,
			float px2, float py2, float px3, float py3, float pr, int color) {
		setRoot(rx, ry, rz, pr);
		setColor(color);
		x1 = px1;
		y1 = py1;
		x2 = px2;
		y2 = py2;
		x3 = px3;
		y3 = py3;
	}

	public int getNumberOfData() {
		return 4 + 2 * (3 + 1); // type, n coord, color, data 3*(x & y)
	};

	public int getData(float[] d, int startD, float xn, float yn, float zn,
			float a11, float a21, float a12, float a22) {
		int n = startD;
		d[n++] = getType();
		d[n++] = 3;
		d[n++] = (a << 24) + (r << 16) + (g << 8) + b;
		d[n++] = 1;
		n += 2;
		int phi = calcPhi(rot+ rrot);
		float sinbeta = mysin[phi];
		float cosbeta = mycos[phi];
		float dummy;
		float dummy2;
		if (this.coordtap != null) {
			float rx1 = this.rx + this.x;
			float ry1 = this.ry + this.y;
			this.coordtap.save(Math.round(rx1 * a11 + ry1 * a12 + xn), Math.round(rx1 * a21 + ry1 * a22 + yn), 0, 0, 0, 0, 0);
		}
		dummy = x1 * cosbeta - y1 * sinbeta + rx + x;
		dummy2 = x1 * sinbeta + y1 * cosbeta + ry + y;
		d[n] = Math.round(dummy * a11 + dummy2 * a12 + xn);
		d[n + 1] = Math.round(dummy * a21 + dummy2 * a22 + yn);
		dummy = x2 * cosbeta - y2 * sinbeta + rx + x;
		dummy2 = x2 * sinbeta + y2 * cosbeta + ry + y;
		d[n + 2] = Math.round(dummy * a11 + dummy2 * a12 + xn);
		d[n + 3] = Math.round(dummy * a21 + dummy2 * a22 + yn);
		dummy = x3 * cosbeta - y3 * sinbeta + rx + x;
		dummy2 = x3 * sinbeta + y3 * cosbeta + ry + y;
		d[n + 4] = Math.round(dummy * a11 + dummy2 * a12 + xn);
		d[n + 5] = Math.round(dummy * a21 + dummy2 * a22 + yn);
		return getNumberOfData();
	};

	public int getType() {
		return Types.TRIANGLE;
	}
}
