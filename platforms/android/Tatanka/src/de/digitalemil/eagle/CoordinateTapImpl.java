package de.digitalemil.eagle;

public class CoordinateTapImpl implements CoordinateTap {

	@Override
	public String toString() {
		return "CoordinateTapImpl [name=" + name + ", x=" + x + ", y=" + y
				+ ", r=" + r + ", a11=" + a11 + ", a21=" + a21 + ", a12=" + a12
				+ ", a22=" + a22 + "]";
	}

	protected String name = "";
	protected float x = -100000, y = -100000, r = 0, a11 = 0, a21 = 0, a12 = 0,
			a22;

	public CoordinateTapImpl() {
	}

	public CoordinateTapImpl(String n) {
		this.name = n;
	}

	public void save(float ix, float iy, float ir, float ia11, float ia21,
			float ia12, float ia22) {
		x = ix;
		y = iy;
		r = ir;
		a11 = ia11;
		a21 = ia21;
		a12 = ia12;
		a22 = ia22;
	}

	public void reset() {
		this.y = -100000;
		this.y = -100000;
	}

	public String getName() {
		return name;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getR() {
		return r;
	}

	public float getA11() {
		return a11;
	}

	public float getA21() {
		return a21;
	}

	public float getA12() {
		return a12;
	}

	public float getA22() {
		return a22;
	}

	public void setName(String name) {
		this.name = name;
	}

}
