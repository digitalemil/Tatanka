package de.digitalemil.eagle;

public class Quad extends Thing {
	protected int texID;


	protected boolean texidset= false, texchanged;

	
	public boolean isTexidset() {
		return texidset;
	}

	protected String texName;


	public Quad(float w, float h, float z) {
		super(1);
		quadinit(w, h, z);
	}

	public void quadinit(float w, float h, float z) {
		texID = 0;
		texidset = false;
		texchanged = false;
		addPart(new Rectangle(w, h, 0.0f, 0.0f, z, 0.0f, 0xFF880000));
		setupDone();
	}

	public int getTexID() {
		return texID;
	}

	public boolean isTexchanged() {
		return texchanged;
	}

	public String getTexName() {
		return texName;
	}

	public float[] getTex() {
		return ((Rectangle) getParts()[0]).tex;
	}

	public void setTexID(int i) {
		texidset = true;
		texID = i;
		texchanged = false;
	}

	public void setTexName(String n) {
		texchanged = true;
		texName = n;
	}

	public int getTexSize() {
		return ((Rectangle) getParts()[0]).getTexSize();
	}

	public int getType() {
		return Types.QUAD;
	}


	public void setDimension(float w, float h) {
		((Rectangle) getParts()[0]).setDimension(h, w);
	}

	public float getWidth() {
		return ((Rectangle) getParts()[0]).height * 2;
	}

	public float getHeight() {
		return ((Rectangle) getParts()[0]).width * 2;
	}


	public int getNumberOfTex() {
		return ((Rectangle) getParts()[0]).getNumberOfTex();
	}
	

	public Rectangle getRectangle() {
		return ((Rectangle) getParts()[0]);
	}

	public boolean isTexIDSet() {
		return texidset;
	}
}
