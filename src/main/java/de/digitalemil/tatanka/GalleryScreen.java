package de.digitalemil.tatanka;

import de.digitalemil.eagle.Globals;
import de.digitalemil.eagle.ImageThing;
import de.digitalemil.eagle.Modell;
import de.digitalemil.eagle.PartAnimation;
import de.digitalemil.eagle.Screen;
import de.digitalemil.eagle.Text;
import de.digitalemil.eagle.TextThing;
import de.digitalemil.eagle.Thing;
import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

public class GalleryScreen extends Screen {
	private int back, img;
	private int n = 0;
	private long start;

	public GalleryScreen() {
		id = 4;
	}

	@MethodDefinitionChangerAnnotation({ "BY",
		"Thing[]", "Thing**", "BY", "String nimg", "sprintf((char *)tmptextbuffer, (const char*)'img%i.jpg', n); //", "BY", "nimg", "tmptextbuffer"})
	public void update(long currentTimeMillis) {
		long now= PartAnimation.currentTimeMillis();
		if(now- start< 2000) {
			return;
		}
		Thing[] things = Globals.getAllThings();
		n++;
		if(n== 31)
			n= 0;
		String nimg= "img"+n+".jpg";
		((ImageThing)things[img]).setTexName(nimg);
		start= now;
	}
	
	@MethodDefinitionChangerAnnotation({ "BY",
			"Thing[]", "Thing**", })
	public void activate() {
		super.activate();
		start= PartAnimation.currentTimeMillis();
		Thing[] things = Globals.getAllThings();
		int t = 0;
		things[t++] = new ImageThing("background.png",
				(int) (Globals.getWidth()), (int) (Globals.getHeight()));

		img= t;
		things[t] = new ImageThing("img0.jpg",
				(int) (0.8*800 * Globals.getScale()),
				(int) (0.8*576 * Globals.getScale()));
		things[t++]
				.translate(0, Globals.getH2() + -(0.4f*576) * Globals.getScale(), 0);
		things[t] = new ImageThing("gradientall800.png",
				(int) (0.8*800 * Globals.getScale()),
				(int) (0.8*576 * Globals.getScale()));
		things[t++]
				.translate(0, Globals.getH2() + -300 * Globals.getScale(), 0);
		things[t] = new ImageThing("name.png",
				(int) (1.8f * 256 * Globals.getScale()),
				(int) (1.8f * 128 * Globals.getScale()));
		things[t++]
				.translate(0, -Globals.getH2() + 156 * Globals.getScale(), 0);

		back = t;
		things[t] = new ImageThing("back.png",
				(int) (1.0f * 256 * Globals.getScale()),
				(int) (1.0f * 128 * Globals.getScale()));
		things[t++]
				.translate(0, -Globals.getH2() + 330 * Globals.getScale(), 0);
		
		TextThing t1 = new TextThing("Images by:", 0, (int)(-Globals.getH2() + 440* Globals.getScale()), Text.TEXT_CENTER, 24, 0xFF592b13);
		things[t++]= t1;
	
		TextThing t2 = new TextThing("Edward Sheriff Curtis", 0, (int)(-Globals.getH2() + 550* Globals.getScale()), Text.TEXT_CENTER, 24, 0xFF592b13);
		things[t++]= t2;
	
		things[t] = new ImageThing("ribbonright.png", 128 * Globals.getScale(),
				Globals.getHeight());
		things[t++].translate(Globals.getW2() - 128 * Globals.getScale() / 2
				+ 1, 0, 0);
		things[t] = new ImageThing("ribbonleft.png", 128 * Globals.getScale(),
				Globals.getHeight());
		things[t++].translate(-Globals.getW2() + 128 * Globals.getScale() / 2
				- 1, 0, 0);

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
