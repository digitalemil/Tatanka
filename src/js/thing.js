function Thing(n) {
	this.nonvisiblecancollide= false;
	this.thinginit(n);
}

Thing.prototype= new Bone();

Thing.prototype.thinginit= function(n) {
	this.data= null;
	this.init(0, 0, 0, 0, n);
	this.movable = true;
	this.maxr = 0;
	this.nbc = 0;
	this.changed = true;
	this.cancollide = true;
	this.parts = new Array(n);
	this.parts[0]= "H";
};

Thing.prototype.getThingData= function() {
		if(this.data== null || this.data== undefined) {
		//	alert("create data array: "+this.name);
			this.data= new Array(this.nd);
		}
		//alert(this.name+" "+this.invaliddata+" "+this.data);
		this.getData(this.data, 0, 0, 0, 0, 1.0, 0, 0, 1.0);
		//alert(this.name+" "+this.data);
		
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
	this.thingReset();
};

Thing.prototype.thingReset= function() {
		this.invalidateData();
		//this.partreset();
		for (var i = 0; i < this.pn; i++) {
			this.parts[i].reset();
		}
};
