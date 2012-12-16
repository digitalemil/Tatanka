package de.digitalemil.tatanka;

import de.digitalemil.eagle.*;

public class TatankaModell extends Modell {
	Prairie prairie;
	static int nlakotas = 1, activelakota= 0;
	MountedLakota lakotas[];
	TatankaHerd herds[];
	JoystickImpl joystick;
	
	public TatankaModell() {
		super(64);
	}

	public void update(long currentTimeMillis) {
		super.update(currentTimeMillis);
		if(getFps()> 0)
			lakotas[activelakota].update(joystick.getRadius()/20.0f/getFps());
		else
			lakotas[activelakota].update(joystick.getRadius()/20.0f/30);
				
		for(int j= 0; j< nlakotas; j++) {
			if(lakotas[j]== lakotas[activelakota])
				continue;
			lakotas[j].update(0);
		}
		
		prairie.update(lakotas[activelakota].getSpeedX(), lakotas[activelakota].getSpeedY());		
		herds[0].update(lakotas[activelakota].getSpeedX(), lakotas[activelakota].getSpeedY(),  lakotas[activelakota].lakota.getX()+ lakotas[activelakota].lakota.getRx(), lakotas[activelakota].lakota.getY()+ lakotas[activelakota].lakota.getRy());
		
		int x = -(int) (herds[0].getAlphaX()- lakotas[activelakota].lakota.getX()- lakotas[activelakota].lakota.getRx());
		int y = (int) (herds[0].getAlphaY()- lakotas[activelakota].lakota.getY()- lakotas[activelakota].lakota.getRy());
		float newdir = (float) Math.atan2(y, x);
		if (newdir < 0) {
			newdir += 2 * Math.PI;
		}
		newdir = (float) (newdir * 360.0f / (2 * Math.PI));
		
		joystick.update((int)newdir);
	}

	public void setup() {
		prairie = new Prairie("valleygras512.jpg");
		herds = new TatankaHerd[1];
		herds[0] = new TatankaHerd(8);
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
		joystick.translate(Globals.getW2()-128 * Globals.getScale() ,
				Globals.getH2() - 108 * Globals.getScale(), 0);

		numberOfThings = pos;
	}

	public void touch(int x, int y) {
		joystick.move(x, y);
	}

	public boolean touchStart(int x, int y) {
		if(!lakotas[activelakota].shoot(x, y)) {
			joystick.down(x, y);
		}
		return true;
	}

	public boolean touchStop(int x, int y) {
		joystick.up();
		return true;
	}
}
