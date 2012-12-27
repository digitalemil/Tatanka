var joystick;

function Joystick() {
	this.r= 10;
}

Joystick.prototype = new Thing(3);

Joystick.prototype.setup= function(mountedlakota) {
	this.thinginit(3);
	var gray1 = 0x40000000;
	var gray2 = 0x80000000;
	var red = 0x80FF0000;
	
	this.setName("Joystick");
	this.add(new Ellipse(48, 48, 0, 0, -3, 0, TRIANGLES20, gray1));
	this.stick= new Ellipse(20, 20, 0, 0, -3, 0, TRIANGLES20, gray2);
	this.add(this.stick);
	this.marker = new Ellipse(4, 4, 0, 0, -3, 0, TRIANGLES20, red);
	this.marker.translate(0,  -44, 0);
	this.add(this.marker);
	
	this.scaleRoot(scale * 2, scale * 2);
	this.setupDone();
	
	this.lakota= mountedlakota;
	
	this.ani = new PartAnimation();
};

Joystick.prototype.up = function(event) {
	if (!this.pressed)
		return;
	r = -10;
	this.lakota.stopRotate();
	this.pressed = false;
	this.ani.init(this.stick, -this.stick.x, -this.stick.y, 0, 1.0, 1.0, 1000, false);
	this.ani.start();
};

Joystick.prototype.down = function(x, y) {	
	var res = this.convert(x, y);
	if (res)
		this.pressed = true;
	this.ani.stop();
	return res;
};

Joystick.prototype.convert = function(mx, my) {
	this.touchx = (mx - w2 - this.x - this.stick.x);
	this.touchy = (my - h2 - this.y - this.stick.y);

	var instick = false;
	if (this.touchx * this.touchx + this.touchy * this.touchy < 20 * 2 * scale
			* 20 * 2 * scale) {
		instick = true;
	}

	var newdir = Math.atan2(-this.touchy, this.touchx);

	if (newdir < 0) {
		newdir += 2 * Math.PI;
	}
	newdir = (newdir * 360 / (2 * Math.PI));
	while (newdir > 360.0)
		newdir -= 360.0;
	this.touchphi = Math.floor(newdir);
	return instick;
};

Joystick.prototype.move = function(tx, ty) {
	if (!this.pressed)
		return;
	
	this.convert(tx + this.stick.x, ty + this.stick.y);
	this.r =  Math.floor(Math.sqrt(Math.min(40 * 40,
			((this.touchx * this.touchx) + (this.touchy * this.touchy)) / scale / scale)));

	var phi = calcPhi(this.touchphi);
	
	var sinphi = mysin[phi];
	var cosphi = mycos[phi];
	// System.out.println("phi: " + phi + " " + r);
	tx = (cosphi * this.r);
	ty = (-sinphi * this.r);
	this.stick.beginTX();
	this.stick.translate(tx - this.stick.x, ty - this.stick.y, 0);

	if (this.stick.x * this.stick.x + this.stick.y * this.stick.y > 72
			* scale * 72 * scale) {
		this.stick.rollbackTX();
		this.r = (72 * scale);
		tx = (cosphi * this.r);
		ty = (-sinphi * this.r);
		this.stick.translate(tx - this.stick.x, ty - this.stick.y, 0);
	} else
		this.stick.commitTX();

	this.lakota.rotate(-this.stick.x / 40.0 * 45.0);

	if (this.stick.y > 0)
		this.r *= -1;
};


Joystick.prototype.update= function(phi) {
	this.setMarker(phi);
	this.updateImpl();
};

Joystick.prototype.updateImpl= function() {
	this.ani.animateNow();
};

Joystick.prototype.getRadius= function() {
	return this.r;
};

Joystick.prototype.setMarker= function(phi) {
	this.marker.translate(-this.marker.x+(mycos[phi] * -44.0), -this.marker.y-(mysin[phi] * -44.0),0);
};


