 Rectangle.prototype = new Part();

function Rectangle() {
}

Rectangle.prototype.init= function(w, h, irx, iry, irz, ir, ic) {
	this.setRoot(irx, iry, irz, ir);
	this.tex = new Array(8);
	this.width = w / 2;
	this.height = h / 2;
	this.sx= 1;
	this.sy= 1;
	this.setColor(ic);
	this.fillTex();
	
	this.tx_width= 0;
	this.tx_height= 0;
	this.tx_sx= 1;
	this.tx_sy= 1;
}


Rectangle.prototype.rectangleBeginTX = function() {
	this.partBeginTX();
	this.tx_sx= this.sx;
	this.tx_sy= this.sy;	
	this.tx_width= this.width;
	this.tx_height= this.height;
};

Rectangle.prototype.commitTX = function() {
	this.rectangleCommitTX();
};

Rectangle.prototype.beginTX = function() {
	this.rectangleBeginTX();
};

Rectangle.prototype.rollbackTX = function() {
	this.rectangleRollbackTX();
};

Rectangle.prototype.rectangleCommitTX = function() {
	this.partCommitTX();
};

Rectangle.prototype.rectangleRollbackTX = function() {
	this.partRollbackTX();
	this.sx = this.tx_sx;
	this.sy = this.tx_sy;
	this.width = this.tx_width;
	this.height = this.tx_height;
};


Rectangle.prototype.reset = function() {
	this.partreset();
};

Rectangle.prototype.fillTex = function() {
	this.tex[0] = 0.0;
	this.tex[1] = 1.0;
	this.tex[2] = 1.0;
	this.tex[3] = 1.0;
	this.tex[4] = 1.0;
	this.tex[5] = 0.0;
	this.tex[6] = 0.0;
	this.tex[7] = 0.0;
};

Rectangle.prototype.getNumberOfData = function() {
	return 4 + 2 * 5; // type, n, color, data 4*(x & y)
};

Rectangle.prototype.getData = function(d, startD, xn, yn, zn, a11, a21, a12,
		a22) {
	var n = startD;
	d[n++] = this.getType();
	d[n++] = 4;
	d[n++] = (this.a << 24) | (this.r << 16) | (this.g << 8) | this.b;
	d[n++] = 1;
	n+=2;
	var phi = (this.rot + this.rrot);
	while (phi < 0) {
		phi += 360;
	}
	phi %= 360;
	var sinbeta = mysin[phi];
	var cosbeta = mycos[phi];
	var dummy;
	var dummy2;
	var rx1 = 0;
	var ry1 = 0;

	for ( var i = 0; i < 4; i++) {
		switch (i) {
		case 0:
			rx1 = -this.width * this.sx;
			ry1 = this.height * this.sy;
			break;
		case 1:
			ry1 = -this.height * this.sy;
			break;
		case 2:
			rx1 = this.width * this.sx;
			break;
		case 3:
			ry1 = this.height * this.sy;
			break;
		}
		dummy = rx1 * cosbeta - ry1 * sinbeta + this.rx + this.x;
		dummy2 = rx1 * sinbeta + ry1 * cosbeta + this.ry + this.y;
		d[n] = Math.round(dummy * a11 + dummy2 * a12 + xn);
		d[n + 1] = Math.round(dummy * a21 + dummy2 * a22 + yn);
		
		n+= 2;
	}

	return this.getNumberOfData();
};

Rectangle.prototype.scale = function(isx, isy) {
	this.width *= isx;
	this.height *= isy;
};

Rectangle.prototype.setDimension = function(w, h) {
	this.width = w / 2;
	this.height = h / 2;
};

Rectangle.prototype.setTex = function(t00, t01, t02, t03, t04, t05, t06, t07) {
	this.tex[0] = t00;
	this.tex[1] = t01;
	this.tex[2] = t02;
	this.tex[3] = t03;
	this.tex[4] = t04;
	this.tex[5] = t05;
	this.tex[6] = t06;
	this.tex[7] = t07;
};

Rectangle.prototype.getNumberOfTex = function() {
	return 4;
};

Rectangle.prototype.getTexSize = function() {
	return 4 * 8;
};

Rectangle.prototype.getType = function() {
	return RECTANGLE;
};