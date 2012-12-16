package de.digitalemil.eagle;

public class Loop extends Ellipse {
	protected float ri1, ri2;

	public Loop(float ir1, float ir2, float iir1, float iir2, float irx, float iry,
			float irz, float irotation, int itriangles, int icolor) {
		super(ir1, ir2, irx, iry, irz, irotation, itriangles, icolor);
		ri1 = iir1;
		ri2 = iir2;
	}

	public int getNumberOfData() {
		return 4 + 2 + 2 * (triangles + 1); // type, n, color, correction + root
		// data triangles*(x
		// & y)
	};

	public int getType() {
		return Types.LOOP;
	};

	public int getData(float[] d, int startD, float xn, float yn, float zn,
			float a11, float a21, float a12, float a22) {
		if (!invaliddata)
			return getNumberOfData();
		invaliddata = false;
		int n = startD;
		d[n++] = getType();
		d[n++] = maxeff;
		d[n++] = (a << 24) | (r << 16) | (g << 8) | b;
		d[n++] = 1;
		int phi = calcPhi(rot+ rrot);
		
		float sinbeta = Part.mysin[phi];
		float cosbeta = Part.mycos[phi];
		float dummy;
		float dummy2;
		float rx1 = rx + x;
		float ry1 = ry + y;
		dummy = rx1;
		dummy2 = ry1;
		d[n] = Math.round(dummy * a11 + dummy2 * a12 + xn);
		d[n + 1] = Math.round(dummy * a21 + dummy2 * a22 + yn);
		n += 2;
		int sn = n;
		for (int i = 0; i < triangles; i++, n += 2) {
			float sinalpha = mysin[mycs[triangles] * 24 + i];
			float cosalpha = mycos[mycs[triangles] * 24 + i];

			dummy = rx1 + (r1 * cosalpha * cosbeta - r2 * sinalpha * sinbeta);
			dummy2 = ry1 + (r1 * cosalpha * sinbeta + r2 * sinalpha * cosbeta);
			d[n] = Math.round(dummy * a11 + dummy2 * a12 + xn);
			d[n + 1] = Math.round(dummy * a21 + dummy2 * a22 + yn);
		}
		d[n] = d[sn];
		d[n + 1] = d[sn + 1];
		n += 2;
		sn = n;
		for (int i = triangles - 1; i >= 0; i--, n += 2) {
			float sinalpha = mysin[mycs[triangles] * 24 + i];
			float cosalpha = mycos[mycs[triangles] * 24 + i];

			dummy = rx1 + (ri1 * cosalpha * cosbeta - ri2 * sinalpha * sinbeta);
			dummy2 = ry1
					+ (ri1 * cosalpha * sinbeta + ri2 * sinalpha * cosbeta);
			d[n] = Math.round(dummy * a11 + dummy2 * a12 + xn);
			d[n + 1] = Math.round(dummy * a21 + dummy2 * a22 + yn);
		}
		d[n] = d[sn];
		d[n + 1] = d[sn + 1];

		return getNumberOfData();
	}
}
