function TatankaHerd(nanimals) {
	this.__setup(nanimals);

	this.alpha = 0;
	this.levelOfAggression = 1;
	this.tspeed = 1.0;

	for ( var i = 0; i < nanimals; i++) {
		var s = 0.98 + getRandom(0, 4) / 100.0;

		this.things[i] = new Tatanka(s);
		this.things[i].scaleRoot(0.8 * scale, 0.8 * scale);

		this.things[i].setLayer(1);
		this.things[i].setName("Tatanka" + i);
		var ch = new LakotaCollisionHandler();
		ch.setup(this.things[i], this.things, 0, i);
		this.things[i].setCollisionHandler(ch);
		this.alpha = this.n / 2;
		var done = false;
		var tries = 0;
		var hw = ((this.n * 200.0 + getRandom(0, 48)) * scale / 2.4);
		var hh = ((this.n * 200.0 + getRandom(0, 48)) * scale / 2.4);

		do {
			this.things[i].beginTX();
			var ra= getRandom(0, hw);
			var rx = -hw / 2 + ra;
			var ry = -h2 / 2 - hh / 2 + getRandom(0, hh);
			this.things[i].translateRoot(rx, ry, 0);
			// things[i].rotateRoot(-20 + Part.getRandom(0, 40));
			this.things[i].getThingData();
			if (ch.getCollisionHappend() != ch.NOCOLLISION) {
				this.things[i].rollbackTX();
				ch.clearCollision();
				console.log("after clear: "+ch.getCollisionHappend() );
				if (tries == 50) {
					n = i - 1;
				//	this.things[i] = null;
				//	this.things[i].commitTX();
					
				//	done= true;
				}

			} else {
				this.things[i].commitTX();
				done = true;
			}
			tries++;

		} while (!done);
	}
	for ( var i = 0; i < this.n; i++) {
		ch = new LakotaCollisionHandler();
		ch.setup(this.things[i], allThings, 4, 1000);
		this.things[i].setCollisionHandler(ch);
	}
	this.things[this.alpha].highlight(true);
}

TatankaHerd.prototype = new ThingContainer(0);

TatankaHerd.prototype.update = function(speedx, speedy, lakotaX, lakotaY) {

	var sx;
	var sy;
	var sin;
	var cos;
	var faster = false;

	if (this.tspeed < 3.5) {
		var ispeed = this.tspeed;
		this.tspeed += 0.01;
		if ((ispeed < 2.0 && this.tspeed > 2.0)
				|| (ispeed < 2.5 && this.tspeed > 2.5))
			faster = true;
	}

	for ( var i = 0; i < this.n; i++) {
		var tatanka = this.things[i];

		var phi = calcPhi(tatanka.rot + 90);
		var r2 = (tatanka.x + tatanka.rx - lakotaX)
				* (tatanka.x + tatanka.rx - lakotaX)
				+ (tatanka.y + tatanka.ry - lakotaY)
				* (tatanka.y + tatanka.ry - lakotaY);

		sin = mysin[phi];
		cos = -mycos[phi];
		sx = -cos * this.tspeed * 2 * scale;
		sy = -sin * this.tspeed * 2 * scale;

		tatanka.beginTX();
		tatanka.translate(sx + speedx, sy + speedy, 0);
		tatanka.rotate(this.getRotation(speedx, speedy, lakotaX));
		tatanka.collisionHandler.clearCollision();
		tatanka.getThingData();
		var t = tatanka.collisionHandler.getCollisionHappend();

		if (t == tatanka.collisionHandler.NOCOLLISION) {
			tatanka.setDidCollide(false);
			tatanka.commitTX();
		} else {
			var r = 1.0;
			if (this.things[i].x >= tatanka.collisionHandler.other.x) {
				r = -1.0;
			}

			tatanka.setDidCollide(true);
			tatanka.rollbackTX();
			this.things[i].rotate(-r);
		}
		if(tatanka.coordtap.x> height) {
		//	System.out.println("   ROTATION    ");
			tatanka.rotate(-tatanka.rot);
		}
		tatanka.collisionHandler.clearCollision();
		tatanka.animate();
	}
};

TatankaHerd.prototype.getRotation = function(speedx, speedy, lakotaX) {
	var tatanka = this.things[this.alpha];
	if (tatanka.didCollide() || tatanka.rot < -10
			|| (tatanka.rot > 10 && tatanka.rot < 350))
		return 0;
	var deg = ((tatanka.x + tatanka.rx - lakotaX) > 0) ? 1 : -1;
	deg *= this.levelOfAggression;
	if (getRandom(0, 30) < 20)
		return 0;
	if (frames % 20 == 0) {
		if (getRandom(0, 100) < 50)
			this.levelOfAggression *= -1;
	}
	if (frames % 5 == 0
			&& speedy > 0
			&& ((tatanka.rot > 340 && tatanka.rot <= 359)
					|| (tatanka.rot < 20 && tatanka.rot >= 0)
					|| (tatanka.rot <= 180 && tatanka.rot >= 20 && deg < 0) || (tatanka.rot <= 340
					&& tatanka.rot > 180 && deg > 0))) {
		return deg;
	}
	return 0;
};

TatankaHerd.prototype.getAlphaX = function() {
	return (this.things[this.alpha].x + this.things[this.alpha].rx);
};

TatankaHerd.prototype.getAlphaY = function() {
	return (this.things[this.alpha].y + this.things[this.alpha].ry);
};
