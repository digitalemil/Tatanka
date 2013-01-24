package de.digitalemil.eagle;

import de.digitalemil.tocplusplus.ClazzModifierAnnotation;
import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

@ClazzModifierAnnotation({ "AF", "isTextSet", "boolean", "false" })
public abstract class Part {

	protected float rsx = 1.0f, rsy = 1.0f, sx = 1.0f, sy = 1.0f;
	protected float x = 0.0f, y = 0.0f, z = 0.0f, rx = 0.0f, ry = 0.0f,
			rz = 0.0f;

	protected float rot = 0.0f, rrot = 0.0f;
	protected int a = 0, r = 0, g = 0, b = 0;
	protected String name;

	protected float tx_rsx, tx_rsy, tx_sx, tx_sy, tx_x, tx_y, tx_z, tx_rot,
			tx_rx, tx_ry, tx_rz, tx_rrot;
	protected int tx_a, tx_r, tx_g, tx_b, tx_type = getType();
	protected boolean tx_invaliddata = true, tx_highlighted = false,
			intransaction = false;
	protected String tx_name;
	protected CoordinateTap coordtap;
	protected boolean invaliddata = true, highlighted = false, supTX = true;

	protected Bone parent = null;

	public Part() {
		name= "";
	}

	abstract public int getData(float[] d, int startD, float xn, float yn,
			float zn, float a11, float a21, float a12, float a22);

	public int getNumberOfTextAndFont() {
		return 0;
	}

	public int getTextAndFont(String[] t, int startT) {
		return 0;
	}

	public void setTXSupport(boolean b) {
		supTX = b;
	}

	public boolean supportsTX() {
		return supTX;
	}

	public boolean isInvalid() {
		return invaliddata;
	}

	public void setCoordinateTap(CoordinateTap ct) {
		coordtap = ct;
	}

	public CoordinateTap getCoordinateTap() {
		return coordtap;
	}

	/*
	 * public String saveState() { return "PART|" + this.tx_type + "|" +
	 * this.tx_name + "|" + this.tx_rsx + "||"; }
	 * 
	 * public boolean loadState(String instate) { return true; }
	 */
	@MethodDefinitionChangerAnnotation({ "BY", "throw", "//throw", "BY",
			"@EMPTYSTRING", "(unsigned char *)@EMPTYSTRING" })
	public void beginTX() {
		if (intransaction) {
			throw new RuntimeException("Already in transaction: " + name);
		}
		intransaction = true;
		tx_type = getType();
		if (name == null)
			tx_name = "";
		else
			tx_name = name;
		tx_rsx = rsx;
		tx_rsy = rsy;
		tx_sx = sx;
		tx_sy = sy;
		tx_x = x;
		tx_y = y;
		tx_z = z;
		tx_rot = rot;
		tx_a = a;
		tx_r = r;
		tx_g = g;
		tx_b = b;
		tx_rx = rx;
		tx_ry = ry;
		tx_rz = rz;
		tx_rrot = rrot;
		tx_invaliddata = invaliddata;
		tx_highlighted = highlighted;
	}

	@MethodDefinitionChangerAnnotation({ "BY", "throw", "//throw" })
	public void commitTX() {
		if (!intransaction) {
			throw new RuntimeException("Can not commit: Not in transaction: "
					+ name);
		}
		intransaction = false;
	}

	@MethodDefinitionChangerAnnotation({ "BY", "throw", "//throw" })
	public void rollbackTX() {
		if (!intransaction) {
			throw new RuntimeException("Can not rollback: Not in transaction: "
					+ name);
		}
		intransaction = false;

		rsx = tx_rsx;
		rsy = tx_rsy;
		sx = tx_sx;
		sy = tx_sy;
		x = tx_x;
		y = tx_y;
		z = tx_z;
		rot = tx_rot;
		a = tx_a;
		r = tx_r;
		g = tx_g;
		b = tx_b;
		rx = tx_rx;
		ry = tx_ry;
		rz = tx_rz;
		rrot = tx_rrot;
		name = tx_name;
		invaliddata = true; // tx_invaliddata;
		highlighted = tx_highlighted;
	}

	public float getRsx() {
		return rsx;
	}

	public float getRsy() {
		return rsy;
	}

	public float getSx() {
		return sx;
	}

	public float getSy() {
		return sy;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getRx() {
		return rx;
	}

	public float getRy() {
		return ry;
	}

	public float getRz() {
		return rz;
	}

	public float getRotation() {
		return rot;
	}

	public float getRrot() {
		return rrot;
	}

	public static int calcPhi(float in) {
		int phi = Math.round(in);
		while (phi < 0)
			phi += 360;
		phi %= 360;
		return phi;
	}

	public String getName() {
		return name;
	}

	@MethodDefinitionChangerAnnotation({
		"BY",
		"name=n;",
		"   if(isTextSet) free(name); name=(unsigned char *)malloc((size_t)(strlen((const char*)n)+1)); strcpy((char*)name, (const char*)n); isTextSet= true;" })
	public void setName(String n) {
		name = n;
	}

	@MethodDefinitionChangerAnnotation({"BY", "int i;", "if(isTextSet) free(name);" })
	protected void finalize() throws Throwable {
		int i;
	}
	public int getNumberOfData() {
		return 0; // type, n, color, data triangles*(x & y)
	}

	public void setParent(Bone bone) {
		parent = bone;
	}

	public boolean invalidateData() {
		boolean i = invaliddata;
		invaliddata = true;
		if (parent != null)
			parent.invaliddata = true;
		return i;
	}

	public void setRoot(float x, float y, float z, float r) {
		invalidateData();
		rx = x;
		ry = y;
		rz = z;
		rrot = r;
	}

	public void rotateRoot(float deg) {
		invalidateData();
		rrot = r;
	}

	@Override
	@MethodDefinitionChangerAnnotation({ "BY", "return ", "//return" })
	public String toString() {
		return "Part [rsx=" + rsx + ", rsy=" + rsy + ", sx=" + sx + ", sy="
				+ sy + ", x=" + x + ", y=" + y + ", z=" + z + ", rx=" + rx
				+ ", ry=" + ry + ", rz=" + rz + ", rot=" + rot + ", rrot="
				+ rrot + ", a=" + a + ", r=" + r + ", g=" + g + ", b=" + b
				+ ", name=" + name + ", intransaction=" + intransaction
				+ ", invaliddata=" + invaliddata + ", highlighted="
				+ highlighted + "]";
	}

	@MethodDefinitionChangerAnnotation({ "BY", "a=", "a = (unsigned int) (c >> 24);//" })
	public void setColor(int c) {
		invalidateData();
		a = (int) (c >>> 24);
		r = (int) ((c & 0x00FF0000) >> 16);
		g = (int) ((c & 0x0000FF00) >> 8);
		b = (int) ((c & 0x000000FF));
	}

	public void setAlpha(int alpha) {
		invalidateData();
		a = alpha;
	}

	public void scale(float x, float y) {
		invalidateData();
		sx *= x;
		sy *= y;
	}

	public void scaleRoot(float sx, float sy) {
		invalidateData();
		rsx *= sx;
		rsy *= sy;
	}

	public void clearScale() {
		invalidateData();
		sx = sy = 1.0f;
	}

	public void translate(float tx, float ty, float tz) {
		invalidateData();
		x += tx;
		y += ty;
		z += tz;
	}

	public void translateRoot(float tx, float ty, float tz) {
		invalidateData();
		rx += tx;
		ry += ty;
		rz += tz;
	}

	public void clearTranslation() {
		invalidateData();
		x = y = z = 0.0f;
	}

	public boolean canHaveChilds() {
		return false;
	}

	public int addBCs(BoundingCircle bcarray[], int start) {
		return start;
	}

	public int getNumberOfBCs() {
		return 0;
	}
	
	public void rotate(float r) {
		rot += r;
		while (rot < 0.0) {
			rot += 360.0;
		}
		while (rot >= 360.0) {
			rot -= 360.0;
		}
		invalidateData();
	}

	public void clearRotation() {
		invalidateData();
		rot = 0;
	}

	public void highlight(boolean bin) {
		int ored = r;
		int og = g;
		int ob = b;
		invalidateData();
		if (bin && !highlighted) {

			r = (int) (1.3 * r > 255.0 ? 255.0 : 1.3 * r);
			g = (int) (1.3 * g > 255.0 ? 255.0 : 1.3 * g);
			b = (int) (1.3 * b > 255.0 ? 255.0 : 1.3 * b);
			highlighted = true;
		} else {
			if (!bin && highlighted) {
				r = ored;
				g = og;
				b = ob;
				highlighted = false;
			}
		}
	}

	public void clearAll() {
		clearTranslation();
		clearScale();
		clearRotation();
	}

	@MethodDefinitionChangerAnnotation({ "BY", "Types.PART", "Types::PART" })
	public int getType() {
		return Types.PART;
	}

	public void reset() {
		x = 0;
		y = 0;
		z = 0;
		rot = 0;
		sx = sy = 1.0f;
		invalidateData();
	}

	@MethodDefinitionChangerAnnotation({ "BY", "random()", "(((float)rand())/RAND_MAX)" })
	public static int getRandom(int min, int max) {
		if (min > max) {
			return -1;
		}

		if (min == max) {
			return min;
		}

		double r;

		do {
			r = Math.random();
		} while (r == 1.0);

		return min + Math.round((float) r * (max - min + 1));
	}

	public final static float mycos[] = { 1.0f, 0.9998477f, 0.99939084f,
			0.9986295f, 0.9975641f, 0.9961947f, 0.9945219f, 0.99254614f,
			0.99026805f, 0.98768836f, 0.9848077f, 0.98162717f, 0.9781476f,
			0.97437006f, 0.9702957f, 0.9659258f, 0.9612617f, 0.9563047f,
			0.95105654f, 0.94551855f, 0.9396926f, 0.9335804f, 0.92718387f,
			0.92050487f, 0.9135454f, 0.9063078f, 0.89879405f, 0.8910065f,
			0.88294756f, 0.8746197f, 0.8660254f, 0.8571673f, 0.8480481f,
			0.83867055f, 0.82903755f, 0.81915206f, 0.809017f, 0.7986355f,
			0.7880108f, 0.777146f, 0.76604444f, 0.7547096f, 0.7431448f,
			0.7313537f, 0.7193398f, 0.70710677f, 0.6946584f, 0.6819984f,
			0.6691306f, 0.656059f, 0.64278764f, 0.6293204f, 0.6156615f,
			0.60181504f, 0.58778524f, 0.57357645f, 0.5591929f, 0.54463905f,
			0.52991927f, 0.5150381f, 0.5f, 0.4848096f, 0.46947157f, 0.4539905f,
			0.43837115f, 0.42261827f, 0.40673664f, 0.39073113f, 0.37460658f,
			0.35836795f, 0.34202015f, 0.32556817f, 0.309017f, 0.2923717f,
			0.27563736f, 0.25881904f, 0.2419219f, 0.22495106f, 0.20791169f,
			0.190809f, 0.17364818f, 0.15643446f, 0.1391731f, 0.12186934f,
			0.104528464f, 0.087155744f, 0.06975647f, 0.052335955f,
			0.034899496f, 0.017452406f, 6.123234E-17f, -0.017452406f,
			-0.034899496f, -0.052335955f, -0.06975647f, -0.087155744f,
			-0.104528464f, -0.12186934f, -0.1391731f, -0.15643446f,
			-0.17364818f, -0.190809f, -0.20791169f, -0.22495106f, -0.2419219f,
			-0.25881904f, -0.27563736f, -0.2923717f, -0.309017f, -0.32556817f,
			-0.34202015f, -0.35836795f, -0.37460658f, -0.39073113f,
			-0.40673664f, -0.42261827f, -0.43837115f, -0.4539905f,
			-0.46947157f, -0.4848096f, -0.5f, -0.5150381f, -0.52991927f,
			-0.54463905f, -0.5591929f, -0.57357645f, -0.58778524f,
			-0.60181504f, -0.6156615f, -0.6293204f, -0.64278764f, -0.656059f,
			-0.6691306f, -0.6819984f, -0.6946584f, -0.70710677f, -0.7193398f,
			-0.7313537f, -0.7431448f, -0.7547096f, -0.76604444f, -0.777146f,
			-0.7880108f, -0.7986355f, -0.809017f, -0.81915206f, -0.82903755f,
			-0.83867055f, -0.8480481f, -0.8571673f, -0.8660254f, -0.8746197f,
			-0.88294756f, -0.8910065f, -0.89879405f, -0.9063078f, -0.9135454f,
			-0.92050487f, -0.92718387f, -0.9335804f, -0.9396926f, -0.94551855f,
			-0.95105654f, -0.9563047f, -0.9612617f, -0.9659258f, -0.9702957f,
			-0.97437006f, -0.9781476f, -0.98162717f, -0.9848077f, -0.98768836f,
			-0.99026805f, -0.99254614f, -0.9945219f, -0.9961947f, -0.9975641f,
			-0.9986295f, -0.99939084f, -0.9998477f, -1.0f, -0.9998477f,
			-0.99939084f, -0.9986295f, -0.9975641f, -0.9961947f, -0.9945219f,
			-0.99254614f, -0.99026805f, -0.98768836f, -0.9848077f,
			-0.98162717f, -0.9781476f, -0.97437006f, -0.9702957f, -0.9659258f,
			-0.9612617f, -0.9563047f, -0.95105654f, -0.94551855f, -0.9396926f,
			-0.9335804f, -0.92718387f, -0.92050487f, -0.9135454f, -0.9063078f,
			-0.89879405f, -0.8910065f, -0.88294756f, -0.8746197f, -0.8660254f,
			-0.8571673f, -0.8480481f, -0.83867055f, -0.82903755f, -0.81915206f,
			-0.809017f, -0.7986355f, -0.7880108f, -0.777146f, -0.76604444f,
			-0.7547096f, -0.7431448f, -0.7313537f, -0.7193398f, -0.70710677f,
			-0.6946584f, -0.6819984f, -0.6691306f, -0.656059f, -0.64278764f,
			-0.6293204f, -0.6156615f, -0.60181504f, -0.58778524f, -0.57357645f,
			-0.5591929f, -0.54463905f, -0.52991927f, -0.5150381f, -0.5f,
			-0.4848096f, -0.46947157f, -0.4539905f, -0.43837115f, -0.42261827f,
			-0.40673664f, -0.39073113f, -0.37460658f, -0.35836795f,
			-0.34202015f, -0.32556817f, -0.309017f, -0.2923717f, -0.27563736f,
			-0.25881904f, -0.2419219f, -0.22495106f, -0.20791169f, -0.190809f,
			-0.17364818f, -0.15643446f, -0.1391731f, -0.12186934f,
			-0.104528464f, -0.087155744f, -0.06975647f, -0.052335955f,
			-0.034899496f, -0.017452406f, -1.8369701E-16f, 0.017452406f,
			0.034899496f, 0.052335955f, 0.06975647f, 0.087155744f,
			0.104528464f, 0.12186934f, 0.1391731f, 0.15643446f, 0.17364818f,
			0.190809f, 0.20791169f, 0.22495106f, 0.2419219f, 0.25881904f,
			0.27563736f, 0.2923717f, 0.309017f, 0.32556817f, 0.34202015f,
			0.35836795f, 0.37460658f, 0.39073113f, 0.40673664f, 0.42261827f,
			0.43837115f, 0.4539905f, 0.46947157f, 0.4848096f, 0.5f, 0.5150381f,
			0.52991927f, 0.54463905f, 0.5591929f, 0.57357645f, 0.58778524f,
			0.60181504f, 0.6156615f, 0.6293204f, 0.64278764f, 0.656059f,
			0.6691306f, 0.6819984f, 0.6946584f, 0.70710677f, 0.7193398f,
			0.7313537f, 0.7431448f, 0.7547096f, 0.76604444f, 0.777146f,
			0.7880108f, 0.7986355f, 0.809017f, 0.81915206f, 0.82903755f,
			0.83867055f, 0.8480481f, 0.8571673f, 0.8660254f, 0.8746197f,
			0.88294756f, 0.8910065f, 0.89879405f, 0.9063078f, 0.9135454f,
			0.92050487f, 0.92718387f, 0.9335804f, 0.9396926f, 0.94551855f,
			0.95105654f, 0.9563047f, 0.9612617f, 0.9659258f, 0.9702957f,
			0.97437006f, 0.9781476f, 0.98162717f, 0.9848077f, 0.98768836f,
			0.99026805f, 0.99254614f, 0.9945219f, 0.9961947f, 0.9975641f,
			0.9986295f, 0.99939084f, 0.9998477f };
	public final static float mysin[] = { 0.0f, 0.017452406f, 0.034899496f,
			0.052335955f, 0.06975647f, 0.087155744f, 0.104528464f, 0.12186934f,
			0.1391731f, 0.15643446f, 0.17364818f, 0.190809f, 0.20791169f,
			0.22495106f, 0.2419219f, 0.25881904f, 0.27563736f, 0.2923717f,
			0.309017f, 0.32556817f, 0.34202015f, 0.35836795f, 0.37460658f,
			0.39073113f, 0.40673664f, 0.42261827f, 0.43837115f, 0.4539905f,
			0.46947157f, 0.4848096f, 0.5f, 0.5150381f, 0.52991927f,
			0.54463905f, 0.5591929f, 0.57357645f, 0.58778524f, 0.60181504f,
			0.6156615f, 0.6293204f, 0.64278764f, 0.656059f, 0.6691306f,
			0.6819984f, 0.6946584f, 0.70710677f, 0.7193398f, 0.7313537f,
			0.7431448f, 0.7547096f, 0.76604444f, 0.777146f, 0.7880108f,
			0.7986355f, 0.809017f, 0.81915206f, 0.82903755f, 0.83867055f,
			0.8480481f, 0.8571673f, 0.8660254f, 0.8746197f, 0.88294756f,
			0.8910065f, 0.89879405f, 0.9063078f, 0.9135454f, 0.92050487f,
			0.92718387f, 0.9335804f, 0.9396926f, 0.94551855f, 0.95105654f,
			0.9563047f, 0.9612617f, 0.9659258f, 0.9702957f, 0.97437006f,
			0.9781476f, 0.98162717f, 0.9848077f, 0.98768836f, 0.99026805f,
			0.99254614f, 0.9945219f, 0.9961947f, 0.9975641f, 0.9986295f,
			0.99939084f, 0.9998477f, 1.0f, 0.9998477f, 0.99939084f, 0.9986295f,
			0.9975641f, 0.9961947f, 0.9945219f, 0.99254614f, 0.99026805f,
			0.98768836f, 0.9848077f, 0.98162717f, 0.9781476f, 0.97437006f,
			0.9702957f, 0.9659258f, 0.9612617f, 0.9563047f, 0.95105654f,
			0.94551855f, 0.9396926f, 0.9335804f, 0.92718387f, 0.92050487f,
			0.9135454f, 0.9063078f, 0.89879405f, 0.8910065f, 0.88294756f,
			0.8746197f, 0.8660254f, 0.8571673f, 0.8480481f, 0.83867055f,
			0.82903755f, 0.81915206f, 0.809017f, 0.7986355f, 0.7880108f,
			0.777146f, 0.76604444f, 0.7547096f, 0.7431448f, 0.7313537f,
			0.7193398f, 0.70710677f, 0.6946584f, 0.6819984f, 0.6691306f,
			0.656059f, 0.64278764f, 0.6293204f, 0.6156615f, 0.60181504f,
			0.58778524f, 0.57357645f, 0.5591929f, 0.54463905f, 0.52991927f,
			0.5150381f, 0.5f, 0.4848096f, 0.46947157f, 0.4539905f, 0.43837115f,
			0.42261827f, 0.40673664f, 0.39073113f, 0.37460658f, 0.35836795f,
			0.34202015f, 0.32556817f, 0.309017f, 0.2923717f, 0.27563736f,
			0.25881904f, 0.2419219f, 0.22495106f, 0.20791169f, 0.190809f,
			0.17364818f, 0.15643446f, 0.1391731f, 0.12186934f, 0.104528464f,
			0.087155744f, 0.06975647f, 0.052335955f, 0.034899496f,
			0.017452406f, 1.2246469E-16f, -0.017452406f, -0.034899496f,
			-0.052335955f, -0.06975647f, -0.087155744f, -0.104528464f,
			-0.12186934f, -0.1391731f, -0.15643446f, -0.17364818f, -0.190809f,
			-0.20791169f, -0.22495106f, -0.2419219f, -0.25881904f,
			-0.27563736f, -0.2923717f, -0.309017f, -0.32556817f, -0.34202015f,
			-0.35836795f, -0.37460658f, -0.39073113f, -0.40673664f,
			-0.42261827f, -0.43837115f, -0.4539905f, -0.46947157f, -0.4848096f,
			-0.5f, -0.5150381f, -0.52991927f, -0.54463905f, -0.5591929f,
			-0.57357645f, -0.58778524f, -0.60181504f, -0.6156615f, -0.6293204f,
			-0.64278764f, -0.656059f, -0.6691306f, -0.6819984f, -0.6946584f,
			-0.70710677f, -0.7193398f, -0.7313537f, -0.7431448f, -0.7547096f,
			-0.76604444f, -0.777146f, -0.7880108f, -0.7986355f, -0.809017f,
			-0.81915206f, -0.82903755f, -0.83867055f, -0.8480481f, -0.8571673f,
			-0.8660254f, -0.8746197f, -0.88294756f, -0.8910065f, -0.89879405f,
			-0.9063078f, -0.9135454f, -0.92050487f, -0.92718387f, -0.9335804f,
			-0.9396926f, -0.94551855f, -0.95105654f, -0.9563047f, -0.9612617f,
			-0.9659258f, -0.9702957f, -0.97437006f, -0.9781476f, -0.98162717f,
			-0.9848077f, -0.98768836f, -0.99026805f, -0.99254614f, -0.9945219f,
			-0.9961947f, -0.9975641f, -0.9986295f, -0.99939084f, -0.9998477f,
			-1.0f, -0.9998477f, -0.99939084f, -0.9986295f, -0.9975641f,
			-0.9961947f, -0.9945219f, -0.99254614f, -0.99026805f, -0.98768836f,
			-0.9848077f, -0.98162717f, -0.9781476f, -0.97437006f, -0.9702957f,
			-0.9659258f, -0.9612617f, -0.9563047f, -0.95105654f, -0.94551855f,
			-0.9396926f, -0.9335804f, -0.92718387f, -0.92050487f, -0.9135454f,
			-0.9063078f, -0.89879405f, -0.8910065f, -0.88294756f, -0.8746197f,
			-0.8660254f, -0.8571673f, -0.8480481f, -0.83867055f, -0.82903755f,
			-0.81915206f, -0.809017f, -0.7986355f, -0.7880108f, -0.777146f,
			-0.76604444f, -0.7547096f, -0.7431448f, -0.7313537f, -0.7193398f,
			-0.70710677f, -0.6946584f, -0.6819984f, -0.6691306f, -0.656059f,
			-0.64278764f, -0.6293204f, -0.6156615f, -0.60181504f, -0.58778524f,
			-0.57357645f, -0.5591929f, -0.54463905f, -0.52991927f, -0.5150381f,
			-0.5f, -0.4848096f, -0.46947157f, -0.4539905f, -0.43837115f,
			-0.42261827f, -0.40673664f, -0.39073113f, -0.37460658f,
			-0.35836795f, -0.34202015f, -0.32556817f, -0.309017f, -0.2923717f,
			-0.27563736f, -0.25881904f, -0.2419219f, -0.22495106f,
			-0.20791169f, -0.190809f, -0.17364818f, -0.15643446f, -0.1391731f,
			-0.12186934f, -0.104528464f, -0.087155744f, -0.06975647f,
			-0.052335955f, -0.034899496f, -0.017452406f };

}