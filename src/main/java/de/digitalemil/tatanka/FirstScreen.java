package de.digitalemil.tatanka;

import de.digitalemil.eagle.Globals;
import de.digitalemil.eagle.ImageThing;
import de.digitalemil.eagle.Part;
import de.digitalemil.eagle.Screen;
import de.digitalemil.eagle.Thing;
import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

public class FirstScreen extends Screen {

	public FirstScreen() {
		id = 0;
	}

		
	@MethodDefinitionChangerAnnotation({ "BY", "Part.get", "Part::get", "BY",
			"Thing[]", "Thing**", "BY", "String",
			"sprintf((char *)tmptextbuffer, (const char*)'tatanka%i@DOTjpg', i); //" })
	public void activate() {
		super.activate();
		Thing[] things = Globals.getAllThings();

		things[4] = new ImageThing("ribbonleft.png", 128 * Globals.getScale(),
				Globals.getHeight());
		things[4].translate(
				-Globals.getW2() + 128 * Globals.getScale() / 2 - 1, 0, 0);
		things[3] = new ImageThing("ribbonright.png", 128 * Globals.getScale(),
				Globals.getHeight());
		things[3].translate(Globals.getW2() - 128 * Globals.getScale() / 2 + 1,
				0, 0);
		things[2] = new ImageThing("name.png",
				(int) (1.5f * 256 * Globals.getScale()),
				(int) (1.5 * 128 * Globals.getScale()));
		things[2].translate(0, -Globals.getH2() + 156 * Globals.getScale(), 0);
		things[0] = new ImageThing("background.png",
				(int) (Globals.getWidth()), (int) (Globals.getHeight()));

		int i = Part.getRandom(0, 8);
		String tmptextbuffer = "tatanka" + i + ".jpg";
		things[1] = new ImageThing(tmptextbuffer,
				(int) (600 * Globals.getScale()),
				(int) (600 * Globals.getScale()));
		things[1].translate(0, Globals.getH2() + -300 * Globals.getScale(), 0);

		numberOfThings = 5;
	}

	public int getBackgroundColor() {
		return 0xFF009c5a;
	}

	@MethodDefinitionChangerAnnotation({ "BY", "Thing[]", "Thing**" })
	public boolean touchStart(int x, int y) {
		Thing[] things = Globals.getAllThings();

		if (things[2].isIn(x, y)) {
			nextscreen = 1;
		}
		return true;
	}

}
