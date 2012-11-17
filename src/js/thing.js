function Thing(n) {
	this.nonvisiblecancollide = false;
	this.thinginit(n);
}

Thing.prototype = new Bone();

Thing.prototype.thinginit = function(n) {
	this.data = null;
	this.init(0, 0, 0, 0, n);
	this.movable = true;
	this.maxr = 0;
	this.nbc = 0;
	this.changed = true;
	this.cancollide = true;
	this.parts = new Array(n);
	this.layer = 0;
	this.bcs = null;
	this.collisionHandler= null;
};

Thing.prototype.setCollisionHandler= function(handler) {
	this.collisionHandler= handler;
}; 


Thing.prototype.getLayer = function() {
	return this.layer;
};

Thing.prototype.setLayer = function(l) {
	this.layer = l;
};

Thing.prototype.getThingData = function() {
	if (this.data == null || this.data == undefined) {
		// alert("create data array: "+this.name);
		this.data = new Array(this.nd);
	}
	// alert(this.name+" "+this.invaliddata+" "+this.data);
	this.getData(this.data, 0, 0, 0, 0, 1.0, 0, 0, 1.0);
	// alert(this.name+" "+this.data);
	
	if(this.collisionHandler!= null) {
		this.collisionHandler.checkCollision(this);	
	}
		
	return this.data;
};

Thing.prototype.getType = function() {
	return THING;
};

Thing.prototype.changeHandled = function() {
	this.changed = false;
};

Thing.prototype.change = function() {
	this.changed = true;
};

Thing.prototype.deleteData = function() {
	this.data = null;
	this.invalidateData();
};

Thing.prototype.reset = function() {
	this.thingReset();
};

Thing.prototype.thingReset = function() {
	this.invalidateData();
	// this.partreset();
	for ( var i = 0; i < this.pn; i++) {
		this.parts[i].reset();
	}
};

function ThingContainer(nthings) {
	this.__setup();
}

ThingContainer.prototype.__setup = function(nthings, layer) {
	this.n = nthings;
	this.things = new Array(this.n);
	this.layers = new Array(layer);
};

ThingContainer.prototype.addThings = function(objs, p, layer) {
	this.pos = p;
	var ninlayer = 0;
	this.layers[layer] = p;
	for ( var i = 0; i < this.n; i++) {
		if (this.things[i].getLayer() == layer) {
			// console.log("adding: "+this.things[i].getName()+" at: "+p);
			objs[p++] = this.things[i];
			ninlayer++;
		}
	}
	return ninlayer;
};

ThingContainer.prototype.removeThings = function(objs) {
	var lastlayer = -1;
	var thingsinlayer = 0;
	for ( var i = 0; i < this.n; i++) {
		var layer = this.things[i].getLayer();
		var p = this.layers[layer];

		if (lastlayer != layer)
			thingsinlayer = 0;
		else
			thingsinlayer++;

		objs[p + thingsinlayer] = null;
	}
};

ThingContainer.prototype.translate = function(x, y, z) {
	for ( var i = 0; i < this.n; i++) {
		this.things[i].translate(x, y, z);
	}
};

ThingContainer.prototype.rotate = function(deg) {
	for ( var i = 0; i < this.n; i++) {
		this.things[i].rotate(deg);
	}
};

ThingContainer.prototype.scale = function(sx, sy) {
	for ( var i = 0; i < this.n; i++) {
		this.things[i].scale(sx, sy);
	}
};

ThingContainer.prototype.translateRoot = function(x, y, z) {
	for ( var i = 0; i < this.n; i++) {
		this.things[i].translateRoot(x, y, z);
	}
};

ThingContainer.prototype.rotateRoot = function(deg) {
	for ( var i = 0; i < this.n; i++) {
		this.things[i].rotateRoot(deg);
	}
};

ThingContainer.prototype.scaleRoot = function(sx, sy) {
	for ( var i = 0; i < this.n; i++) {
		this.things[i].scaleRoot(sx, sy);
	}
};

ThingContainer.prototype.getType = function() {
	return THINGCONTAINER;
};

function CollisionHandler() {
}

CollisionHandler.prototype.setup = function(ts, start, end) {
	this.things = ts;
	this.start = start;
	this.end = end;
	this.enabled = true;
};

CollisionHandler.prototype.isEnabled = function() {
	return this.enabled;
}

CollisionHandler.prototype.enable = function() {
	this.enabled = true;
}

CollisionHandler.prototype.disable = function() {
	this.enabled = false;
}

CollisionHandler.prototype.canCollide = function(thing) {
	return true;
}

CollisionHandler.prototype.checkCollision = function(thing) {
	if (!this.enabled)
		return;

	var thingbcs= thing.getBCs();

	for ( var i = this.start; i < this.end; i++) {
//		console.log("Checking Collision: "+thing.name+" vs. "+this.things[i]);
		if (this.things[i] == undefined || this.things[i] == null)
			return;
		if (!this.canCollide(this.things[i]))
			continue;

		var bcs = this.things[i].getBCs();
		for ( var h = 0; h < this.things[i].getNumberOfBCs(); h++) {
	//		console.log("h: "+h+" "+bcs[h]+" "+bcs[h].getCoordinateTap());
			
			var bbc = bcs[h];
			var bx = bbc.getCoordinateTap().getX();
			var by = bbc.getCoordinateTap().getY();
			var br = bbc.getCoordinateTap().getR() * this.things[i].sx
					* this.things[i].rsx; // radius
			for ( var j = 0; j < thing.getNumberOfBCs(); j++) {
				var abc = thingbcs[j];
				// Check for hit
				var ax = abc.getCoordinateTap().getX();
				var ay = abc.getCoordinateTap().getY();
				var ar = abc.getCoordinateTap().getR() * thing.sx * thing.rsx; // radius
				if ((bx - ax) * (bx - ax) + (by - ay) * (by - ay) <= (br + ar)
						* (br + ar)) {
					this.handleCollision(thing, this.things[i]);
				}
			}
		}

	}
};

CollisionHandler.prototype.handleCollision = function(thing) {

}
