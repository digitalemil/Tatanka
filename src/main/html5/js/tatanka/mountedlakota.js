function MountedLakota(x, y) {
	this.__setup(3);
	this.things[0] = new Horse();
	this.mustang = this.things[0];
	this.mustang.setLayer(200);
	var ch = new LakotaCollisionHandler();
	ch.setup(this.mustang, allThings, 0, 1000);
	this.mustang.setCollisionHandler(ch);

	this.things[1] = new Sioux();
	this.lakota = this.things[1];
	this.lakota.setLayer(201);
	this.things[2] = new Arrow();
	this.arrow = this.things[2];
	this.arrow.setLayer(401);
	this.arrow.setVisibility(false);
	this.arrow.setName("Arrow");
	this.arrows= 30;
	// this.arrow.translateRoot(0, -26, 0);
	this.lakota.setFlyingArrow(this.arrow);

	this.translateRoot(x * scale, y * scale, 0);
	this.scaleRoot(scale * 0.8, scale * 0.8);
	this.lakotaRot = null;
	this.mustangRot = null;
	this.speed = 0;
	this.speedxeff = 0;
	this.speedyeff = 0;
	this.speedx = 0;
	this.speedy = 0;
}

MountedLakota.prototype = new ThingContainer(0);

MountedLakota.prototype.stopRotate = function() {
	this.rotate(0);
};

MountedLakota.prototype.rotate = function(rot) {
	if (this.lakotaRot == null) {
		this.lakotaRot = new PartAnimation();
	}
	if (this.mustangRot == null) {
		this.mustangRot = new PartAnimation();
	}

	rot = -this.lakota.rot + rot;

	while (rot < -180)
		rot += 360;

	while (rot > 180)
		rot = 360 - rot;

	if (rot == 0)
		return;

	rot = Math.floor(rot);

	var d = Math.abs(rot / 90) * 2000;
	this.lakotaRot.stop();
	this.lakotaRot.init(this.lakota, 0, 0, rot, 1.0, 1.0, d, false);
	this.lakotaRot.start();
	this.mustangRot.stop();
	this.mustangRot.init(this.mustang, 0, 0, rot, 1.0, 1.0, d, false);
	this.mustangRot.start();
};

MountedLakota.prototype.shoot = function(x, y) {
	if(this.arrows<= 0)
		return false;
	this.arrows--;
	return this.lakota.shoot(x, y);
};

MountedLakota.prototype.update = function(acceleration) {

	var phi = calcPhi(this.lakota.rot + 90);

	var sin = mysin[phi];
	var cos = -mycos[phi];

	var oldspeed = this.speed;
	this.speed += acceleration;
	if (this.speed > 4)
		this.speed = 4;
	if (this.speed < 0)
		this.speed = 0;
	if (oldspeed < 0.1 && this.speed > 0.1)
		this.start();
	if ((oldspeed < 0.5 && this.speed > 0.5)
			|| (oldspeed < 1 && this.speed > 1)
			|| (oldspeed < 2 && this.speed > 2)
			|| (oldspeed < 3 && this.speed > 3)
			|| (oldspeed < 3.5 && this.speed > 3.5)) {
		this.faster();
		this.faster();
	}
	if ((oldspeed > 0.5 && this.speed < 0.5)
			|| (oldspeed > 1 && this.speed < 1)
			|| (oldspeed > 2 && this.speed < 2)
			|| (oldspeed > 3 && this.speed < 3)
			|| (oldspeed > 3.5 && this.speed < 3.5)) {
		this.slower();
		this.slower();
	}
	if (this.speed == 0)
		this.stop();

	this.speedx = cos * this.speed * 2 * scale;
	this.speedy = sin * this.speed * 2 * scale;
	var dx = this.lakota.x;
	this.speedxeff = 0.0;
	this.speedyeff = 0.0;

	var yeff = (this.lakota.y + this.lakota.ry);

	if (this.speed < 2 && yeff < h2 / 3 * 2)
		this.speedyeff = -0.5;

	if (this.speed < 1 && yeff < h2 / 2)
		this.speedyeff = -0.5;

	if (this.speed > 2 && yeff > h2 / 2)
		this.speedyeff += +0.5;

	if (this.speed > 3 && yeff > 2 / 3.0 * h2)
		this.speedyeff += +0.5;

	if (dx < 160 * scale && dx > -160 * scale) {
		var w = 1 - Math.abs(dx / (200 * scale));
		this.speedxeff = w * this.speedx;
	}

	if (!(dx - this.speedx < 160 * scale && dx - this.speedx > -160 * scale))
		this.speedxeff = 0.0;

	this.lakota.arrow.setTXSupport(false);
	this.lakota.beginTX();
	this.mustang.beginTX();
	if (this.lakotaRot != null)
		this.lakotaRot.animateNow();
	if (this.mustangRot != null)
		this.mustangRot.animateNow();
	// System.out.println("translate speedx: "+speedx+" speedy: "+speedy);

	this.translate(-this.speedxeff, -this.speedyeff, 0);
	this.mustang.collisionHandler.clearCollision();
	this.mustang.getThingData();
	var t = this.mustang.collisionHandler.getCollisionHappend();
	if (t == this.mustang.collisionHandler.NOCOLLISION) {
		this.lakota.commitTX();
		this.mustang.commitTX();
	} else {
		this.lakota.rollbackTX();
		this.mustang.rollbackTX();
		this.translate(-this.lakota.x, -this.lakota.y, 0);

		this.speedxeff = 0.0;
		this.speedyeff = 0.0;
	}
	this.lakota.arrow.setTXSupport(true);

	this.mustang.animate();

	this.lakota.shootingani.animateImpl();
	this.lakota.getFlyingArrow().ani.animateNow();
};

MountedLakota.prototype.slower = function() {
	this.mustang.animation.slower();
};

MountedLakota.prototype.faster = function() {
	this.mustang.animation.faster();
};

MountedLakota.prototype.stop = function() {
	this.mustang.animation.stop();
	// fixes PartAnimation percentage> 0 problem
	this.mustang.reset();
};

MountedLakota.prototype.start = function() {
	this.mustang.animation.start();
};

MountedLakota.prototype.stopRotate = function() {
	this.rotate(0);
};

MountedLakota.prototype.getSpeedX = function() {
	// console.log("speedeffx: "+this.speedxeff+" speedx: "+this.speedx);
	if (this.speedxeff < 0.1 && this.speedxeff > -0.1)
		return this.speedx;
	else
		return this.speedxeff;
};

MountedLakota.prototype.getSpeedY = function() {
	return this.speedy - this.speedyeff;
};

MountedLakota.prototype.getArrows= function() {
	return this.arrows;
};
