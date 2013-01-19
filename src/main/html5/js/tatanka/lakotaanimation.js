
function LakotaAnimation(sioux) {
	this.lakota = sioux;
	this.init("LakotaAnimation", 8, 1, false);
}

LakotaAnimation.prototype = new CompositeAnimation();

LakotaAnimation.prototype.clear = function() {
	for ( var i = 0; i < this.maxanimation; i++) {
		for ( var j = 0; j < this.maxlevel; j++) {
			if (this.anis[j * this.maxanimation + i] != null) {
				this.anis[j * this.maxanimation + i].stop();
				if (this.anis[j * this.maxanimation + i] != null)
					this.anis[j * this.maxanimation + i] = null;
				this.anis[j * this.maxanimation + i] = null;
			}
		}
	}
};


LakotaAnimation.prototype.shoot = function(shootAtX, shootAtY) {
	if (this.shooting)
		return false;

	this.clear();
	this._loops = false;
	var na = 9;

	this.arrow = this.lakota.getFlyingArrow();
	this.shooting = true;
	var x = shootAtX - w2 - this.lakota.bow.getCoordinateTap().getX();
	var y = h2 - shootAtY + this.lakota.bow.getCoordinateTap().getY();
	var newdir = Math.atan2(y, x);
	var sr = this.lakota.rot;
	if (sr > 180)
		sr = -360 + sr;
	if (newdir < 0) {
		newdir += 2 * Math.PI;
	}
	newdir = newdir * 360 / (2 * Math.PI);
	this.deg = -135 + newdir - sr;
	// console.log("deg: "+deg+" sr: "+sr);
	if (this.deg > 90) {
		this.shooting = false;
		return false;
	}
	this.lakota.arrow.beginTX();
	
	this.lakota.arrow.setVisibility(true);

	var sa1 = new CompositeAnimation();
	sa1.init("1", 1, 2, false);

	var pa = new PartAnimation();
	pa.init(this.lakota.bow, 0, 0, 35, 1.0, 1.0, 250);
	sa1.addAnimation(pa, 0);

	pa = new PartAnimation();
	pa.init(this.lakota.rightarm, 0, 0, 40, 1.0, 1.4, 250);
	sa1.addAnimation(pa, 0);

	var sa2 = new CompositeAnimation();
	sa2.init("2", 1, 1, false);
	pa = new PartAnimation();
	pa.init(this.lakota.upperparts, 0, 0, this.deg, 1.0, 1.0, Math
			.abs(this.deg * 3));
	sa2.addAnimation(pa, 0);

	var sa3 = new CompositeAnimation();
	sa3.init("3", 1, 4, false);
	pa = new PartAnimation();
	pa.init(this.lakota.leftarm, 0, 0, 0, 1.0, 1.5, 200, false);
	sa3.addAnimation(pa, 0);
	pa = new PartAnimation();
	pa.init(this.lakota.rightarm, 0, 0, 0, 1.0, 1 / 1.4, 400, false);
	sa3.addAnimation(pa, 0);
	pa = new PartAnimation();
	pa.init(this.lakota.arrow, 0, 15, 0, 1.0, 1 / 1.5, 400, false);
	sa3.addAnimation(pa, 0);
	pa = new PartAnimation();
	pa.init(this.lakota.fibre, 0, 0, 0, 1.0, 15, 400, false);
	sa3.addAnimation(pa, 0);

	var sa4 = new CompositeAnimation();
	sa4.init("4", 1, 3, false);
	
	pa = new PartAnimation();
	pa.init(this.lakota.fibre, 0, 0, 0, 1.0, 1.0/15, 200, false);
	sa4.addAnimation(pa, 0);
	
	pa = new PartAnimation();
	pa.init(this.lakota.bow, 0, 0, 0, 1.0, 1.0/1.5, 200, false);
	sa4.addAnimation(pa, 0);
	
	pa = new PartAnimation();
	pa.init(this.lakota.arrow, 0, 0, 0, 1.0, 1.5, 200, false);
	sa4.addAnimation(pa, 0);

	var sa5 = new CompositeAnimation();
	sa5.init("5", 1, 3, false);
	pa = new PartAnimation();
	pa.init(this.lakota.bow, 0, 0, 0, 1.0, 1.5, 500, false);
	sa5.addAnimation(pa, 0);
	pa = new PartAnimation();
	pa.init(this.lakota.rightarm, 0, 0, 0, 1.0, 1.0 / 1.2, 500, false);
	sa5.addAnimation(pa, 0);
	pa = new PartAnimation();
	pa.init(this.lakota.leftarm, 0, 0, 0, 1.0, 1.0 / 1.5, 500, false);
	sa5.addAnimation(pa, 0);
	
	var d= Math.abs(this.deg * 3)*2;
	var sa6 = new CompositeAnimation();
	sa6.init("6", 1, 4, false);
	pa = new PartAnimation();
	pa.init(this.lakota.leftarm, 0, 0, -30, 1.0, 1.0, d, false);
	sa6.addAnimation(pa, 0);
	pa = new PartAnimation();
	pa.init(this.lakota.rightarm, 0, 0, -40, 1.0, 1.0, d, false);
	sa6.addAnimation(pa, 0);
	pa = new PartAnimation();
	pa.init(this.lakota.bow, 0, 0, -25, 1.0, 1.0, d, false);
	sa6.addAnimation(pa, 0);
	pa = new PartAnimation();
	pa.init(this.lakota.upperparts, 0, 0, -this.deg, 1.0, 1.0, d, false);
	sa6.addAnimation(pa, 0);
	
	
	this.addAnimation(sa1, 0);
	this.addAnimation(sa2, 1);
	this.addAnimation(sa3, 2);
	this.addAnimation(sa4, 3);
	this.addAnimation(sa5, 4);
	this.addAnimation(sa6, 5);

	this.start();
	return true;
};

LakotaAnimation.prototype.increaseLevelImpl = function() {
	switch (this.level) {
	case 2:
		break;
	case 3:
		var l = Math.sqrt(width * width + height
				* height);
			
		this.arrow.translate(-this.arrow.x-this.arrow.rx + this.lakota.arrow.coords.x
				, -this.arrow.y -this.arrow.ry
				+ this.lakota.arrow.coords.y);
		
		var arot = this.lakota.rot + this.lakota.upperparts.rot
				+ this.lakota.body.rot + this.lakota.leftarm.rot
				+ this.lakota.bow.rot + this.lakota.arrow.rot
				+ this.lakota.rrot + this.lakota.upperparts.rrot
				+ this.lakota.body.rrot + this.lakota.leftarm.rrot
				+ this.lakota.bow.rrot + this.lakota.arrow.rrot;
		this.arrow.rotate(-this.arrow.rot + arot);
		this.arrow.setVisibility(true);

		var phi = calcPhi(this.arrow.rot + this.arrow.rrot);
		var sinbeta = mysin[phi];
		var cosbeta = mycos[phi];

		this.arrow.ani.init(this.arrow, -sinbeta * l, -cosbeta * l, 0, 1.0, 1.0, 1500, false);
		this.arrow.ani.name="ArrowAni";
		this.arrow.ani.start();
		this.lakota.arrow.setVisibility(false);
		break;
	case 6:
		this.lakota.arrow.rollbackTX();
		this.lakota.reset();
		this.shooting = false;
		break;
	}
};

LakotaAnimation.prototype.startImpl = function() {
	this.start();
	this.lt = this._start = (new Date()).getTime();
};

LakotaAnimation.prototype.animateImpl = function() {
	var now = (new Date()).getTime();
	
	if (!this.running) {
		return 1.0;
	}

	var l = this.level;
	var ret = this.animate(now);
	if (l != this.level)
		this.increaseLevelImpl();

	return ret;
};

LakotaAnimation.prototype.getType = function() {
	return LAKOTAANIMATION;
};
