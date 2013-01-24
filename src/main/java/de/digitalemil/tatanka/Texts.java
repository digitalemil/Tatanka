package de.digitalemil.tatanka;

import de.digitalemil.eagle.*;
import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

public class Texts extends Thing {
	private Text t1, t2, t3;
	
	@MethodDefinitionChangerAnnotation({ "BY", "-80", "-120", "BY", "-30", "-60", "BY", "-150", "-180" })
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
	
	
	@MethodDefinitionChangerAnnotation({ "BY", "t2",  "sprintf((char*)tmptextbuffer, (const char*)'%i', a);t2->setText(tmptextbuffer);//" })
	public void setArrows(int a) {
		t2.setText(""+a);
	}
}