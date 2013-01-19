package de.digitalemil.tatanka;

import de.digitalemil.eagle.*;

public class Texts extends Thing {
	private Text t1, t2, t3;
	
	public Texts() {
		super(3);

		float s= Globals.getScale();
		t1 = new Text("0", 0, -30* s,
				0x80000000);
		t1.setSize((int)(36*s));
		t1.setAlignment(Text.TEXT_LEFT);
		
		addPart(t1);
		t2 = new Text("30", 0, -80* s,
				0x80000000);
		t2.setSize((int)(36*s));
		t2.setAlignment(Text.TEXT_LEFT);
		
		addPart(t2);
		t3 = new Text("3", 0, -150* s,
				0x80000000);
		t3.setSize((int)(36*s));
		t3.setAlignment(Text.TEXT_LEFT);
		
		addPart(t3);
		setupDone();
	}
	
	public void setArrows(int a) {
		t2.setText(""+a);
	}
}