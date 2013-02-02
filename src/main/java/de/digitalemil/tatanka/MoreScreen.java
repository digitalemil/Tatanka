package de.digitalemil.tatanka;

import de.digitalemil.eagle.Globals;
import de.digitalemil.eagle.ImageThing;
import de.digitalemil.eagle.Part;
import de.digitalemil.eagle.Screen;
import de.digitalemil.eagle.Thing;
import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

public class MoreScreen extends Screen {
	private int back, gallery, about, help;
	public MoreScreen() {
		id = 2;
	}

	@MethodDefinitionChangerAnnotation({ "BY", "Part.get", "Part::get", "BY",
			"Thing[]", "Thing**", "BY", "String",
			"sprintf((char *)tmptextbuffer, (const char*)'tatanka%i@DOTjpg', i); //" })
	public void activate() {
		super.activate();
		Thing[] things = Globals.getAllThings();
		int t= 0;
		things[t++] = new ImageThing("background.png",
				(int) (Globals.getWidth()), (int) (Globals.getHeight()));
		int i = Part.getRandom(0, 8);
		String tmptextbuffer = "tatanka" + i + ".jpg";
		things[t] = new ImageThing(tmptextbuffer,
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
		
		gallery= t;
		things[t] = new ImageThing("gallery.png",
				(int) (1.0f * 256 * Globals.getScale()),
				(int) (1.0f * 128 * Globals.getScale()));
		things[t++].translate(0, -Globals.getH2() + 320* Globals.getScale(), 0);
		
		about= t;
		things[t] = new ImageThing("about.png",
				(int) (1.0 * 256 * Globals.getScale()),
				(int) (1.0f * 128 * Globals.getScale()));
		things[t++].translate(0, -Globals.getH2() + 440* Globals.getScale(), 0);
		
		help= t;
		things[t] = new ImageThing("help.png",
				(int) (1.0f * 256 * Globals.getScale()),
				(int) (1.0f * 128 * Globals.getScale()));
		things[t++].translate(0, -Globals.getH2() + 550* Globals.getScale(), 0);
		
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
			nextscreen = 0;
		}
		if (things[about].isIn(x, y)) {
			nextscreen = 3;
		}
		if (things[gallery].isIn(x, y)) {
			nextscreen = 4;
		}	
		return true;
	}

}
