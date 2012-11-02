function Bone(x, y, z, r, n) {
	this.init(x, y, z, r, n);
}

Bone.prototype = new Part();

Bone.prototype.init = function(ix, iy, iz, ir, n) {
	this.partinit();
	this.data = new Array();
	this.parts = new Array(n);
	this.pn = 0;
	this.nd = 0;
	this.rx = ix;
	this.ry = iy;
	this.rz = iz;
	this.rrot = ir;
	this.visible = true;
};

Bone.prototype.getByName = function(n) {
	var result = null;
	if (this.name != null && this.name == n) {
		return this;
	}
	for ( var i = 0; i < this.pn; i++) {
		var pn = this.parts[i].name;
		if (pn != null) {
			if (this.parts[i].name == n) {
				result = this.parts[i];
				break;
			}
		}
		if (this.parts[i].getType() == BONE) {
			result = this.parts[i].getByName(n);
		}
		if (result != null) {
			return result;
		}
	}
	return result;
};

Bone.prototype.add = function(p, ix, iy, iz, ir) {
	if (ix == undefined)
		ix = 0;
	if (iy == undefined)
		iy = 0;
	if (iz == undefined)
		iz = 0;
	if (ir == undefined)
		ir = 0;

	p.translateRoot(ix, iy, iz);
	p.rotate(ir);
	p.setParent(this);
	this.parts[this.pn++] = p;
	this.a = p.a;
};

Bone.prototype.addPart = function(p) {
	this.add(p, 0, 0, 0, 0);
};

Bone.prototype.getNumberOfData = function() {
	return this.nd;
};

Bone.prototype.setupDone = function() {
	this.nd = 0;
	for ( var i = 0; i < this.pn; i++) {
		this.nd += this.parts[i].getNumberOfData();
	}
	this.invalidateData();
};

Bone.prototype.getData = function(d, startD, xn, yn, zn, a11, a21, a12, a22) {
	if (!this.invaliddata)
		return this.getNumberOfData();
	var sv = startD;
	var phi = -(this.rot + this.rrot);
	phi = Math.round(phi);
	while (phi < 0)
		phi += 360;
	phi %= 360;

	//console.log("phi: "+phi);
	var cphi = mycos[phi];
	var sphi = mysin[phi];

	var ta11 = cphi;
	var ta21 = sphi;
	var ta12 = -sphi;
	var ta22 = cphi;

	xn = xn + a11 * (this.rx + this.x) + a12 * (this.ry + this.y);
	yn = yn + a21 * (this.rx + this.x) + a22 * (this.ry + this.y);

	var na11 = (ta11 * a11 + ta12 * a21) * this.sx * this.rsx;
	var na12 = (ta11 * a12 + ta12 * a22) * this.sy * this.rsy;
	var na21 = (ta21 * a11 + ta22 * a21) * this.sx * this.rsx;
	var na22 = (ta21 * a12 + ta22 * a22) * this.sy * this.rsy;
	if (!this.visible) {
		xn = -100000;
	}
	for ( var i = 0; i < this.pn; i++) {
		sv += this.parts[i].getData(d, sv, xn, yn, this.rz + this.z + zn, na11,
				na21, na12, na22);
	}
	return this.nd;
};

Bone.prototype.invalidateData = function() {
	this.invaliddata = true;
	if (!this.parent == undefined)
		this.parent.invaliddata = true;
	for ( var i = 0; i < this.pn; i++) {
		this.parts[i].invalidateData();
	}
	return i;
};

Bone.prototype.setVisibility = function(v) {
	this.visible = v;
	this.invalidateData();
};

Bone.prototype.setColor = function(c) {
	this.invalidateData();
	this.a = (c >> 24);
	this.r = ((c & 0x00FF0000) >> 16);
	this.g = ((c & 0x0000FF00) >> 8);
	this.b = ((c & 0x000000FF));
	for ( var i = 0; i < this.pn; i++) {
		this.parts[i].setColor(c);
	}
};

Bone.prototype.setAlpha = function(c) {
	this.invalidateData();
	this.a = c;
	for ( var i = 0; i < this.pn; i++) {
		this.parts[i].setAlpha(c);
	}
};

Bone.prototype.getType = function() {
	return BONE;
};

Bone.prototype.highlight = function(b) {
	this.invalidateData();

	this.highlighted = b;
	for ( var i = 0; i < this.pn; i++) {
		this.parts[i].highlight(b);
	}
};

Bone.prototype.reset = function() {
	this.invalidateData();
	this.partreset();
	for ( var i = 0; i < this.pn; i++) {
		this.parts[i].reset();
	}
};
