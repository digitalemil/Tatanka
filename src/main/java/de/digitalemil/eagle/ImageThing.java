package de.digitalemil.eagle;

import de.digitalemil.tocplusplus.ClazzModifierAnnotation;
import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

@ClazzModifierAnnotation({ "AF", "isTexNameSet", "boolean", "false" })
public class ImageThing extends Thing {
	protected int texID;
	protected boolean texidset= false, texchanged;
	protected String texName;
	
	public boolean isTexidset() {
		return texidset;
	}

	public ImageThing(String name, float w, float h) {
		super(1);
		setTexName(name);
		init(w, h);
	}
	
	@MethodDefinitionChangerAnnotation({"BY", "int i;", "if(isTexNameSet) free(texName);" })
	protected void finalize() throws Throwable {
		int i;
	}
	
	public boolean isIn(int ix, int iy) {
		ix-= Globals.getW2();
		iy-= Globals.getH2();
		if(ix> x+rx-getWidth()/2 && ix< x+rx+getWidth()/2 && iy> y+ry- getHeight()/2 && iy< y+ry+getHeight()/2)
			return true;
		return false;
	}


	public void init(float w, float h) {
		texID = 0;
		texidset = false;
		texchanged = true;
		addPart(new Rectangle(w, h, 0.0f, 0.0f, 0.0f, 0.0f, 0xFF880000));
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

	public void setTexID(int i) {
		texidset = true;
		texID = i;
		texchanged = false;
	}

	@MethodDefinitionChangerAnnotation({
		"BY",
		"texName=n;",
		"   if(isTexNameSet) free(texName); texName=(unsigned char *)malloc((size_t)(strlen((const char*)n)+1)); strcpy((char*)texName, (const char*)n); isTexNameSet= true;" })
	public void setTexName(String n) {
		texchanged = true;
		texName = n;
	}

	public int getType() {
		return Types.IMAGE;
	}

	public void scale(float x, float y) {
		this.setDimension( getWidth() * x, getHeight() * y);
	}

	public void setDimension(float w, float h) {
		((Rectangle) getParts()[0]).setDimension(h, w);
		this.texchanged = true;
		this.invalidateData();
	}

	public float getWidth() {
		return ((Rectangle) getParts()[0]).width * 2;
	}

	public float getHeight() {
		return ((Rectangle) getParts()[0]).height * 2;
	}

	public Rectangle getRectangle() {
		return ((Rectangle) getParts()[0]);
	}

	public boolean isTexIDSet() {
		return texidset;
	}
}
