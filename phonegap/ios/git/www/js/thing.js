function Thing(n) {
	this.nbc;
	this.changed;
	this.cancollide;
	this.nonvisiblecancollide;
	this.maxr;
	this.thinginit(n);
}

Thing.prototype= new Bone();

Thing.prototype.thinginit= function(n) {
	this.init(0, 0, 0, 0, n);
	this.movable = true;
	this.maxr = 0;
	this.nbc = 0;
	this.changed = true;
	this.cancollide = true;
};

Thing.prototype.getThingData= function() {
		if(this.data== null || this.data== undefined) {
			this.data= new Array(this.nd);
		}
		this.getData(this.data, 0, 0, 0, 0, 1.0, 0, 0, 1.0);
		return this.data;
};
	
Thing.prototype.getType= function() {
		return THING;
};

Thing.prototype.changeHandled= function() {
		this.changed = false;
};

Thing.prototype.change= function() {
		this.changed = true;
};

Thing.prototype.deleteData= function() {	
		this.data = null;
		this.invalidateData();
};

Thing.prototype.reset= function() {
		this.invalidateData();
		//this.partreset();
		for (var i = 0; i < this.pn; i++) {
			this.parts[i].reset();
		}
};
