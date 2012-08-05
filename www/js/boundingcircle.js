var visibleboundingcircle= false;
var bcarrow= undefined;


function BoundingCircle() {
}

BoundingCircle.prototype = new Ellipse();

BoundingCircle.prototype.initBoundingCircle= function(ir, irx, iry, irz) {
	this.init(ir, ir, irx, iry, irz, 0, 10, 0xff0000);
	this.setRoot(irx, iry, irz, 0);	
	this.start = 0;
};

BoundingCircle.prototype.getNumberOfData = function() {
	if(visibleboundingcircle)
		return 4+ 2 * (this.triangles+1); 
	return 4+3;
};

BoundingCircle.prototype.getData = function(d, startD, xn, yn, zn, a11, a21, a12, a22) {
	if (!this.invaliddata)
		return this.getNumberOfData();
	if(bcarrow== undefined) {
		console.log(this.parent);
		if(this.parent.getType()== BONE) {
			bcarrow= startD;
		}
	}
	this.invaliddata = false;
	var n = startD;
	d[n++] = this.getType();
	d[n++] = this.maxeff;
	d[n++] = (this.a << 24) | (this.r << 16) | (this.g << 8) | this.b;
	d[n++] = (this.maxeff== this.triangles)?1:0;
	//if(this.maxeff)
	var phi = (this.rot + this.rrot);
	phi = Math.round(phi);
	while (phi < 0)
		phi += 360;
	phi %= 360;

	// console.log("phi: "+phi);
	var sinbeta = mysin[phi];
	var cosbeta = mycos[phi];
	var dummy;
	var dummy2;
	var rx1 = this.rx + this.x;
	var ry1 = this.ry + this.y;
	dummy = rx1;
	dummy2 = ry1;
	d[n] = Math.round(dummy * a11 + dummy2 * a12 + xn);
	d[n+ 1] = Math.round(dummy * a21 + dummy2 * a22 + yn);
	n+= 2;
	if(!visibleboundingcircle) {
		d[n]= this.r1;
		return this.getNumberOfData();
	}
	for ( var i = 0; i < this.maxeff; i++, n+= 2) {
		var sinalpha = ellipseMysin[ellipseMycs[this.triangles] * 24 + i];
		var cosalpha = ellipseMycos[ellipseMycs[this.triangles] * 24 + i];

		dummy = rx1
				+ (this.r1 * cosalpha * cosbeta - this.r2 * sinalpha * sinbeta);
		dummy2 = ry1
				+ (this.r1 * cosalpha * sinbeta + this.r2 * sinalpha * cosbeta);
		d[n] = Math.round(dummy * a11 + dummy2 * a12 + xn);
		d[n+ 1] = Math.round(dummy * a21 + dummy2 * a22 + yn);
	}

	return this.getNumberOfData();
};

BoundingCircle.prototype.getType= function() {
		return BOUNDINGCIRCLE;
};

BoundingCircle.prototype.getStart= function() {
		return this.start;
};

BoundingCircle.prototype.getRadius= function() {
		return this.r1;
};
