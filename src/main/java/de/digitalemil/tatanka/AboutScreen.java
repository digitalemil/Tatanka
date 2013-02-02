package de.digitalemil.tatanka;

import de.digitalemil.eagle.Globals;
import de.digitalemil.eagle.ImageThing;
import de.digitalemil.eagle.Modell;
import de.digitalemil.eagle.Part;
import de.digitalemil.eagle.Screen;
import de.digitalemil.eagle.Text;
import de.digitalemil.eagle.TextThing;
import de.digitalemil.eagle.Thing;
import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

public class AboutScreen extends Screen {
	private int back;
	
	public AboutScreen() {
		id = 3;
	}

	@MethodDefinitionChangerAnnotation({ "BY", "Part.get", "Part::get", "BY",
			"Thing[]", "Thing**", "BY", "String v",
			"sprintf((char *)tmptextbuffer, (const char*)'Version: %5.5f', Modell::getVersion());//", "BY", "v,", "tmptextbuffer," })
	public void activate() {
		super.activate();
		Thing[] things = Globals.getAllThings();
		int t= 0;
		things[t++] = new ImageThing("background.png",
				(int) (Globals.getWidth()), (int) (Globals.getHeight()));
		
		things[t] = new ImageThing("willi.jpg",
				(int) (600 * Globals.getScale()),
				(int) (600 * Globals.getScale()));
		things[t++].translate(0, Globals.getH2() + -300 * Globals.getScale(), 0);
		things[t] = new ImageThing("gradientall800.png",
				(int) (600 * Globals.getScale()),
				(int) (600 * Globals.getScale()));
		things[t++].translate(0, Globals.getH2() + -300 * Globals.getScale(), 0);
		things[t] = new ImageThing("name.png",
				(int) (1.8f * 256 * Globals.getScale()),
				(int) (1.8f * 128 * Globals.getScale()));
		things[t++].translate(0, -Globals.getH2() + 156 * Globals.getScale(), 0);
		
		TextThing t1 = new TextThing("by digitalemil", 0, (int)(-Globals.getH2() + 320* Globals.getScale()), Text.TEXT_CENTER, 24, 0xFF592b13);
		things[t++]= t1;
		String v= "Version "+Modell.getVersion();
		t1 = new TextThing(v, 0, (int)(-Globals.getH2() + 440* Globals.getScale()), Text.TEXT_CENTER, 24, 0xFF592b13);
		things[t++]= t1;
		
		back= t;
		things[t] = new ImageThing("back.png",
				(int) (1.0f * 256 * Globals.getScale()),
				(int) (1.0f * 128 * Globals.getScale()));
		things[t++].translate(0, -Globals.getH2() + 660* Globals.getScale(), 0);
		
		things[t] = new ImageThing("ribbonright.png", 128 * Globals.getScale(),
				Globals.getHeight());
		things[t++].translate(Globals.getW2() - 128 * Globals.getScale() / 2 + 1,
				0, 0);
		things[t] = new ImageThing("ribbonleft.png", 128 * Globals.getScale(),
				Globals.getHeight());
		things[t++].translate(
				-Globals.getW2() + 128 * Globals.getScale() / 2 - 1, 0, 0);
		
		
		
		numberOfThings = t;
	}

	public int getBackgroundColor() {
		return 0xFF009c5a;
	}

	@MethodDefinitionChangerAnnotation({ "BY", "Thing[]", "Thing**" })
	public boolean touchStart(int x, int y) {
		Thing[] things = Globals.getAllThings();

		if (things[back].isIn(x, y)) {
			nextscreen = 2;
		}
		return true;
	}

}
