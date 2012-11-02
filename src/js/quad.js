Quad.prototype = new Thing(1);

function Quad() {
}

Quad.prototype.quadinit = function(x, y, z) {
	this.parts = new Array(1);
	var red = 0xFF880000;
	this.texID = 0;
	this.texidset = false;
	this.texchanged = false;
	var el = new Rectangle();
	el.init(x, y, 0, 0, z, 0, red);
	el.name = "";
	this.add(el);	
	this.setupDone();
}

Quad.prototype.getTex = function() {
	return this.parts[0].tex;
};

Quad.prototype.setTexID = function(i) {
	this.texidset = true;
	this.texID = i;
	this.texchanged = false;
};

Quad.prototype.setTexName = function(n) {
	this.texchanged = true;
	this.texName = n;
};

Quad.prototype.getTexSize = function() {
	return this.parts[0].getTexSize();
};

Quad.prototype.getType = function() {
	return QUAD;
};

Quad.prototype.setDimension = function(w, h) {
	this.parts[0].setDimension(h, w);
};

Quad.prototype.getWidth = function() {
	return this.parts[0].height * 2;
};

Quad.prototype.getHeight = function() {
	return this.parts[0].width * 2;
};

Quad.prototype.getRectangle = function() {
	return this.parts[0];
};

Quad.prototype.isTexIDSet = function() {
	return this.texidset;
};

Quad.prototype.getNumberOfTex = function() {
	return this.parts[0].getNumberOfTex();
};


