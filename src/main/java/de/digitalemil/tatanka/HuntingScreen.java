package de.digitalemil.tatanka;

import java.util.Arrays;

import de.digitalemil.eagle.Thing;
import de.digitalemil.eagle.Globals;
import de.digitalemil.eagle.ImageThing;
import de.digitalemil.eagle.Screen;
import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

public class HuntingScreen extends Screen {
	Prairie prairie;
	int nlakotas = 1, activelakota = 0, arrowsetal;
	MountedLakota lakotas[];
	TatankaHerd herds[];
	JoystickImpl joystick;
	Texts texts;
	ImageThing menu;

	public HuntingScreen() {
		id = 1;
	}

	@MethodDefinitionChangerAnnotation({"BY", "Thing[]", "Thing**", "BY", "prairie=null", "delete prairie", "BY", "lakotas[0]=null", "delete lakotas[0]", "BY", "lakotas= null", "delete [ ] lakotas", "BY", "herds[0]=null", "delete herds[0]", "BY", "herds=null", "delete [ ] herds", "BY", "joystick=null", "delete joystick", "BY", "texts=null", "delete texts", "BY", "menu=null", "delete menu", "BY", "things[arrowsetal]=null", "delete things[arrowsetal]" })
	protected void finalize() throws Throwable {
		prairie= null;
		lakotas[0]= null;
		lakotas= null;
		herds[0]= null;
		herds= null;
		joystick= null;
		texts= null;
		menu= null;
		Thing[] things= Globals.getAllThings();
		things[arrowsetal]= null;
		for(int i= 0; i< numberOfThings; i++) {
			things[i]= null;
		}
	}

	@MethodDefinitionChangerAnnotation({ "BY", "Thing[]", "Thing**", "BY",
			"new TatankaHerd[", "new TatankaHerd*[", "BY",
			"new MountedLakota[", "new MountedLakota*[", "BY",
			"joystick.translate", "joystick->Thing::translate" })
	public void activate() {
		super.activate();
		Thing[] things = Globals.getAllThings();
		prairie = new Prairie("valleygras512.jpg");
		herds = new TatankaHerd[1];
		herds[0] = new TatankaHerd(8);
		System.out.println("nlakotas: " + nlakotas);
		lakotas = new MountedLakota[nlakotas];
		for (int i = 0; i < nlakotas; i++) {
			lakotas[i] = new MountedLakota(80 * i, 320);
			// lakotas[i].translateRoot(-240*scale*nlakotas/2+i*100*scale,
			// -100*scale+-50*scale+getRandom(0, 100), 0);
		}

		int pos = 0;
		for (int layer = 0; layer < 1000; layer++) {
			pos += prairie.addThings(things, pos, layer);
			pos += herds[0].addThings(things, pos, layer);
			for (int j = 0; j < nlakotas; j++) {
				pos += lakotas[j].addThings(things, pos, layer);
			}
		}
		joystick = new JoystickImpl(lakotas[0]);
		things[pos++] = joystick;
		joystick.translate(Globals.getW2() - 128 * Globals.getScale(),
				Globals.getH2() - 108 * Globals.getScale(), 0);

		texts = new Texts();
		things[pos] = texts;
		things[pos].translate(-Globals.getW2() + 64 * Globals.getScale(),
				Globals.getH2(), 0);
		texts.setArrows(lakotas[activelakota].getArrows());
		pos++;
		arrowsetal= pos;  
		things[pos] = new ImageThing("arrowsetalmin.png",
				(int) (123 * Globals.getScale() / 2),
				(int) (354 * Globals.getScale() / 2));
		things[pos].translate(
				-Globals.getW2() + (int) (123 * Globals.getScale() / 2) / 2,
				Globals.getH2() - (int) (374 * Globals.getScale() / 2) / 2, 0);
		pos++;
		things[pos] = new ImageThing("tepee.png",
				(int) (2 * 64 * Globals.getScale() / 2),
				(int) (2 * 64 * Globals.getScale() / 2));
		things[pos].translate(Globals.getW2()
				- (int) (80 * Globals.getScale() / 2), -Globals.getH2()
				+ (int) (80 * Globals.getScale() / 2), 0);
		menu = (ImageThing) things[pos];
		pos++;

		numberOfThings = pos;
	}

	public void update(long currentTimeMillis) {
		super.update(currentTimeMillis);
		if (getFps() > 0)
			lakotas[activelakota].update(joystick.getRadius() / 20.0f
					/ getFps());
		else
			lakotas[activelakota].update(joystick.getRadius() / 20.0f / 30);

		for (int j = 0; j < nlakotas; j++) {
			if (lakotas[j] == lakotas[activelakota])
				continue;
			lakotas[j].update(0);
		}

		prairie.update(lakotas[activelakota].getSpeedX(),
				lakotas[activelakota].getSpeedY());
		herds[0].update(lakotas[activelakota].getSpeedX(),
				lakotas[activelakota].getSpeedY(),
				lakotas[activelakota].lakota.getX()
						+ lakotas[activelakota].lakota.getRx(),
				lakotas[activelakota].lakota.getY()
						+ lakotas[activelakota].lakota.getRy());

		int x = -(int) (herds[0].getAlphaX()
				- lakotas[activelakota].lakota.getX() - lakotas[activelakota].lakota
				.getRx());
		int y = (int) (herds[0].getAlphaY()
				- lakotas[activelakota].lakota.getY() - lakotas[activelakota].lakota
				.getRy());
		float newdir = (float) Math.atan2(y, x);
		if (newdir < 0) {
			newdir += 2 * Math.PI;
		}
		newdir = (float) (newdir * 360.0f / (2 * Math.PI));

		joystick.update((int) newdir);
	}

	public void touch(int x, int y) {
		joystick.move(x, y);
	}

	public boolean touchStart(int x, int y) {
		if (menu.isIn(x, y)) {
			nextscreen = 0;
			return true;
		}
		if (!lakotas[activelakota].shoot(x, y)) {
			joystick.down(x, y);
		} else {
			texts.setArrows(lakotas[activelakota].getArrows());
		}
		return true;
	}

	public boolean touchStop(int x, int y) {
		joystick.up();
		return true;
	}

}
