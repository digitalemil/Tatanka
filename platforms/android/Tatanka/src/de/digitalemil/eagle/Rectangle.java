package de.digitalemil.eagle;

public class Rectangle extends Part {
	protected float width, height, tx_width, tx_height;

	public Rectangle(float w, float h, float irx, float iry, float irz,
			float ir, int ic) {
		init(w, h, irx, iry, irz, ir, ic);
	}

	public void beginTX() {
		super.beginTX();
		tx_width= width;
		tx_height= height;
	}

	public void rollbackTX() {
		super.rollbackTX();
		width= tx_width;
		height= tx_height;
	}
	
	public void init(float w, float h, float irx, float iry, float irz,
			float ir, int ic) {
		setRoot(irx, iry, irz, ir);
		width = w / 2;
		height = h / 2;
		sx = 1;
		sy = 1;
		setColor(ic);
	}

	public int getNumberOfData() {
		return 4 + 2 * 5; // type, n, color, data 4*(x & y)
	}

	public int getData(float[] d, int startD, float xn, float yn, float zn,
			float a11, float a21, float a12, float a22) {
		int n = startD;
		d[n++] = getType();
		d[n++] = 4;
		d[n++] = (a << 24) | (r << 16) | (g << 8) | b;
		d[n++] = 1;
		n += 2;
		int phi = calcPhi(rot+rrot);

		float sinbeta = mysin[phi];
		float cosbeta = mycos[phi];
		float dummy;
		float dummy2;
		float rx1 = 0;
		float ry1 = 0;

		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0:
				rx1 = -width * sx;
				ry1 = height * sy;
				break;
			case 1:
				ry1 = -height * sy;
				break;
			case 2:
				rx1 = width * sx;
				break;
			case 3:
				ry1 = height * sy;
				break;
			}
			dummy = rx1 * cosbeta - ry1 * sinbeta + rx + x;
			dummy2 = rx1 * sinbeta + ry1 * cosbeta + ry + y;
			d[n] = Math.round(dummy * a11 + dummy2 * a12 + xn);
			d[n + 1] = Math.round(dummy * a21 + dummy2 * a22 + yn);

			n += 2;
		}

		return getNumberOfData();
	}

	public void scale(float isx, float isy) {
		width *= isx;
		height *= isy;
	}

	public void setDimension(float w, float h) {
		width = w / 2;
		height = h / 2;
	}

	public int getType() {
		return Types.RECTANGLE;
	}
}