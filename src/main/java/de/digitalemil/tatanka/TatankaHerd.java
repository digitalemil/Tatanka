package de.digitalemil.tatanka;

import de.digitalemil.eagle.*;

public class TatankaHerd extends ThingContainer {
	private int alpha = 0;
	private int levelOfAggression = 1;
	float tspeed = 1.0f;

	public TatankaHerd(int n) {
		init(n);
		float scale = Globals.getScale();
		for (int i = 0; i < n; i++) {
			float s = 0.98f + Part.getRandom(0, 4) / 100.0f;

			things[i] = new Tatanka(s);
			things[i].scaleRoot(0.8f * scale, 0.8f * scale);

			things[i].setLayer(1);
			things[i].setName("Tatanka" + i);
			things[i].setCollisionHandler(new LakotaCollisionHandler(things[i],
					things, 0, i));
			alpha = n / 2;
			boolean done = false;
			int tries = 0;
			int hw = (int) ((n * 200.0f + Part.getRandom(0, 48))
					* Globals.getScale() / 2.4);
			int hh = (int) ((n * 200.0f + Part.getRandom(0, 48))
					* Globals.getScale() / 2.4);

			System.out.println("hw: "+hw);
			do {
				things[i].beginTX();
				int rx = -hw / 2 + Part.getRandom(0, hw);
				int ry = -Globals.getH2() / 2 - hh / 2 + Part.getRandom(0, hh);
				things[i].translateRoot(rx, ry, 0);
				// things[i].rotateRoot(-20 + Part.getRandom(0, 40));
				things[i].getThingData();

				if (things[i].getCollisionHandler().collisionHappend() != CollisionHandlerImpl.NOCOLLISION) {
					System.out.println("Tatanka collide: " + i + " " + rx + " "
							+ ry + " " + hw + " " + hh);
					things[i].rollbackTX();
					things[i].getCollisionHandler().clearCollision();
					if (tries == 200) {
						n = i - 1;
						things[i] = null;
					}

				} else {
					things[i].commitTX();
					done = true;
				}
				tries++;

			} while (!done);
		}
		for (int i = 0; i < n; i++) {
			things[i].setCollisionHandler(new LakotaCollisionHandler(things[i],
					Globals.getAllThings(), 4, 1000));
		}
		things[alpha].highlight(true);
	}

	private int getRotation(float speedx, float speedy, float lakotaX) {
		Tatanka tatanka = (Tatanka) things[alpha];
		if (tatanka.didCollide() || tatanka.getRotation() < -10
				|| (tatanka.getRotation() > 10 && tatanka.getRotation() < 350))
			return 0;
		int deg = ((tatanka.getX() + tatanka.getRx() - lakotaX) > 0) ? 1 : -1;
		deg *= levelOfAggression;
		if (Part.getRandom(0, 30) < 20)
			return 0;
		if (Globals.getFrames() % 20 == 0) {
			if (Part.getRandom(0, 100) < 50)
				levelOfAggression *= -1;
		}
		if (Globals.getFrames() % 5 == 0
				&& speedy > 0
				&& ((tatanka.getRotation() > 340 && tatanka.getRotation() <= 359)
						|| (tatanka.getRotation() < 20 && tatanka.getRotation() >= 0)
						|| (tatanka.getRotation() <= 180
								&& tatanka.getRotation() >= 20 && deg < 0) || (tatanka
						.getRotation() <= 340 && tatanka.getRotation() > 180 && deg > 0))) {
			return deg;
		}
		return 0;

	}

	public void update(float speedx, float speedy, float lakotaX, float lakotaY) {

		float sx, sy, sin, cos;
		boolean faster = false;

		if (tspeed < 3.5f) {
			float ispeed = tspeed;
			tspeed += 0.01f;
			if ((ispeed < 2.0f && tspeed > 2.0f)
					|| (ispeed < 2.5f && tspeed > 2.5f))
				faster = true;
		}

		for (int i = 0; i < n; i++) {
			Tatanka tatanka = (Tatanka) things[i];

			int phi = Part.calcPhi(tatanka.getRotation() + 90);
			float r2 = (tatanka.getX() + tatanka.getRx() - lakotaX)
					* (tatanka.getX() + tatanka.getRx() - lakotaX)
					+ (tatanka.getY() + tatanka.getRy() - lakotaY)
					* (tatanka.getY() + tatanka.getRy() - lakotaY);

			sin = Part.mysin[phi];
			cos = -Part.mycos[phi];
			sx = -cos * tspeed * 2 * Globals.getScale();
			sy = -sin * tspeed * 2 * Globals.getScale();
			/*
			 * if (things[alpha].getY() + things[alpha].getRy() < -2
			 * Globals.getH2()) sx = tspeed + speedy; if (things[alpha].getY() +
			 * things[alpha].getRy() > 2 * Globals .getH2()) sy = tspeed -
			 * speedy;
			 */
			if (faster) {
			//	tatanka.getAnimation().faster();
			//	tatanka.getAnimation().faster();
			}

			// System.out.println("speedx: "+speedx+" speedy: "+speedy+" sx: "+sx+" sy: "+sy+
			// " "+phi);
			tatanka.beginTX();
			tatanka.translate(sx + speedx, sy + speedy, 0);
			tatanka.rotate(getRotation(speedx, speedy, lakotaX));
			tatanka.getCollisionHandler().clearCollision();
			tatanka.getThingData();
			int t = tatanka.getCollisionHandler().collisionHappend();

			if (t == CollisionHandlerImpl.NOCOLLISION) {
				tatanka.setDidCollide(false);
				tatanka.commitTX();
			} else {
				// System.out.println("Collision: "+things[i].getName()+" with: "+tatanka.getCollisionHandler().getOther().getName());
				float r = 1.0f;
				if (things[i].getX() >= tatanka.getCollisionHandler()
						.getOther().getX()) {
					r = -1.0f;
				}

				tatanka.setDidCollide(true);
				tatanka.rollbackTX();
				things[i].rotate(-r);

				// tatanka.invalidateData();
			}
			tatanka.getCollisionHandler().clearCollision();
			tatanka.animate();
		}
		// System.out.println("Alpha: "+(things[alpha].getY()+
		// things[alpha].getRy()));
	}

	public int getAlphaX() {
		return (int) (things[alpha].getX() + things[alpha].getRx());
	}

	public int getAlphaY() {
		return (int) (things[alpha].getY() + things[alpha].getRy());
	}

}