package de.digitalemil.tatanka;

import de.digitalemil.eagle.*;

public class MountedLakota extends ThingContainer {
	Mustang mustang;
	Sioux lakota;
	Arrow arrow;
	PartAnimation lakotaRot = null;
	PartAnimation mustangRot = null;
	float speed, speedxeff, speedyeff, speedx, speedy;

	public MountedLakota(float x, float y) {
		init(3);

		mustang = new Mustang();
		things[0] = mustang;
		mustang.setLayer(200);
		mustang.setCollisionHandler(new LakotaCollisionHandler(mustang, Globals
				.getAllThings(), 0, 1000));

		lakota = new Sioux();
		things[1] = lakota;
		lakota.setLayer(201);
		arrow = new Arrow();
		things[2] = arrow;
		arrow.setLayer(401);
		arrow.setVisibility(false);
		arrow.setName("Arrow");
		arrow.translateRoot(0, -26, 0);
		lakota.setFlyingArrow(arrow);

		float s = Globals.getScale();
		translateRoot(x * s, y * s, 0);
		scaleRoot(s * 0.8f, s * 0.8f);
	}

	public void slower() {
		mustang.animation.slower();
	}

	public void faster() {
		mustang.animation.faster();
	}

	public void stop() {
		mustang.animation.stop();
	}

	public void start() {
		mustang.animation.start();
	}

	public void stopRotate() {
		rotate(0);
	}

	public void rotate(float rot) {
		if (lakotaRot == null) {
			lakotaRot = new PartAnimation();
		}
		if (mustangRot == null) {
			mustangRot = new PartAnimation();
		}
		rot = -lakota.getRotation() + rot;

		while (rot < -180)
			rot += 360;

		while (rot > 180)
			rot = 360 - rot;

		if (rot == 0)
			return;

		int d = (int) (Math.abs(rot / 90) * 2000);
		// super.rotate(rot);

		lakotaRot.stop();
		lakotaRot.init(lakota, 0, 0, rot, 1.0f, 1.0f, d, false);
		lakotaRot.start();

		mustangRot.stop();
		mustangRot.init(mustang, 0, 0, rot, 1.0f, 1.0f, d, false);
		mustangRot.start();
	}

	public Mustang getMustang() {
		return mustang;
	}

	public Sioux getLakota() {
		return lakota;
	}

	public boolean shoot(int x, int y) {
		return lakota.shoot(x, y);
	}

	public void update(float acceleration) {

		int phi = Part.calcPhi(lakota.getRotation() + 90);

		float sin = Part.mysin[phi];
		float cos = -Part.mycos[phi];

		float oldspeed = speed;
		speed += acceleration;
		if (speed > 4)
			speed = 4;
		if (speed < 0)
			speed = 0;
		if (oldspeed < 0.1 && speed > 0.1)
			start();
		if ((oldspeed < 0.5 && speed > 0.5) || (oldspeed < 1 && speed > 1)
				|| (oldspeed < 2 && speed > 2) || (oldspeed < 3 && speed > 3)
				|| (oldspeed < 3.5 && speed > 3.5)) {
			faster();
			faster();
		}
		if ((oldspeed > 0.5 && speed < 0.5) || (oldspeed > 1 && speed < 1)
				|| (oldspeed > 2 && speed < 2) || (oldspeed > 3 && speed < 3)
				|| (oldspeed > 3.5 && speed < 3.5)) {
			slower();
			slower();
		}
		if (speed == 0)
			stop();

		speedx = cos * speed * Globals.getScale() *3;
		speedy = sin * speed * Globals.getScale() *3;
		float dx = lakota.getX();
		speedxeff = 0.0f;
		speedyeff = 0.0f;

		int yeff = (int) (lakota.getY() + lakota.getRy());

		if (speed < 2 && yeff < 512 * Globals.getScale())
			speedyeff = -0.5f;

		if (speed < 1 && yeff < 256 * Globals.getScale())
			speedyeff = -0.5f;

		if (speed > 2 && yeff > 256 * Globals.getScale())
			speedyeff += +0.5f;

		if (speed > 3 && yeff > 180 * Globals.getScale())
			speedyeff += +0.5f;

		// System.out.println("speed: "+speed+ " effy: "+speedyeff+
		// " accel: "+acceleration+" "+(256*Globals.getScale())+
		// " "+lakota.getY());

		float scale = Globals.getScale();
		if (dx < 160 * scale && dx > -160 * scale) {
			float w = 1 - Math.abs(dx / (200 * scale));
			speedxeff = w * speedx;
		}

		if (!(dx - speedx < 160 * Globals.getScale() && dx - speedx > -160
				* Globals.getScale()))
			speedxeff = 0.0f;

		lakota.arrow.setTXSupport(false);
		lakota.beginTX();
		mustang.beginTX();
		if (lakotaRot != null)
			lakotaRot.animate();
		if (mustangRot != null)
			mustangRot.animate();
		// System.out.println("translate speedx: "+speedx+" speedy: "+speedy);

		translate(-speedxeff, -speedyeff, 0);
		mustang.getCollisionHandler().clearCollision();
		mustang.getThingData();
		int t = mustang.getCollisionHandler().collisionHappend();
		if (t == CollisionHandlerImpl.NOCOLLISION) {
			lakota.commitTX();
			mustang.commitTX();
		} else {
			lakota.rollbackTX();
			mustang.rollbackTX();
			translate(-lakota.getX(), -lakota.getY(), 0);
			
			speedxeff = 0.0f;
			speedyeff = 0.0f;
		}
		lakota.arrow.setTXSupport(true);
		mustang.animate();

		lakota.getShootingAnimation().animate();
		lakota.getFlyingArrow().getArrowAnimation().animate();
	}

	public float getSpeedX() {
		// System.out.println("speedeffx: "+speedxeff+" speedy: "+speedy);
		if (speedxeff < 0.1 && speedxeff > -0.1)
			return speedx;
		else
			return speedxeff;
	}

	public float getSpeedY() {
		return speedy - speedyeff;
	}
}
