package de.digitalemil.eagle;

import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

public class TextThing extends Thing {
	protected Text text;
	
	public TextThing(String t, float ix, float iy, int a, int s, int c) {
		super(1);

		translate(ix,  iy, 0);
		
		text = new Text(t, 0, 0, c);
		text.setSize(s);
		text.setAlignment(a);
		
		addPart(text);
		setupDone();
	}
	
	public void setText(String t) {
		text.setText(t);
	}
}
