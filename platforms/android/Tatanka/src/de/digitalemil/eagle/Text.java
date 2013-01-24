package de.digitalemil.eagle;

import de.digitalemil.tocplusplus.ClazzModifierAnnotation;
import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

@ClazzModifierAnnotation({ "AF", "isTextSet", "boolean", "false" })
public class Text extends Part {

	public final static int TEXT_LEFT = 0, TEXT_RIGHT = 1, TEXT_CENTER = 2;
	private int align, tx_align, tx_size, size, nt;
	private String font, text, tx_font, tx_text;

	public Text(String t, float irx, float iry, int ic) {
		setRoot(irx, iry, 0, 0);
		setColor(ic);
		tx_text = t;
		tx_size = 12;
		tx_font = "";
		setText(t);
		size = 12;
		font = "";
		align = TEXT_CENTER;
	}

	public void beginTX() {
		super.beginTX();
		tx_text = text;
		tx_size = size;
		tx_font = font;
		tx_align = align;
	}

	public void rollbackTX() {
		super.rollbackTX();
		text = tx_text;
		size = tx_size;
		font = tx_font;
		align = tx_align;
	}

	public int getNumberOfData() {
		return 8;
	}

	public int getTextAndFont(String[] t, int startT) {
		int n = startT;
		t[n++] = text;
		t[n++] = font;
		return 2;
	}

	public int getNumberOfTextAndFont() {
		return 2;
	}

	public int getData(float[] d, int startD, float xn, float yn, float zn,
			float a11, float a21, float a12, float a22) {
		int n = startD;
		d[n++] = getType();
		d[n++] = 8;
		d[n++] = (a << 24) | (r << 16) | (g << 8) | b;
		d[n++] = 1;
		float dummy;
		float dummy2;
		float rx1 = rx + x;
		float ry1 = ry + y;
		dummy = rx1;
		dummy2 = ry1;
		d[n] = Math.round(dummy * a11 + dummy2 * a12 + xn);
		d[n + 1] = Math.round(dummy * a21 + dummy2 * a22 + yn);
		d[n + 2] = a11 * size;
		d[n + 3] = align;
		return getNumberOfData();
	}

	@MethodDefinitionChangerAnnotation({
			"BY",
			"text=t;",
			"   if(isTextSet) free(text); text=(unsigned char *)malloc((size_t)(strlen((const char*)t)+1)); strcpy((char*)text, (const char*)t); isTextSet= true;" })
	public void setText(String t) {	
		text = t;
		invalidateData();
	}

	public void setSize(int s) {
		size = s;
		invalidateData();
	}

	public void setFont(String f) {
		font = f;
		invalidateData();
	}

	public void setAlignment(int a) {
		if (a >= TEXT_LEFT && a <= TEXT_CENTER) {
			align = a;
			invalidateData();
		}
	}

	public String getText() {
		return text;
	}

	public int getSize() {
		return size;
	}

	public String getFont() {
		return font;
	}

	public int getType() {
		return Types.TEXT;
	}

	public int getAlignment() {
		return align;
	}

	@MethodDefinitionChangerAnnotation({"BY", "int i;", "if(isTextSet) free(text);" })
	protected void finalize() throws Throwable {
		int i;
	}
}