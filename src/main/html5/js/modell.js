function Modell(n) {
	this.init(n);
}

Modell.prototype.init= function(n) {
		allThings = new Array(n);
		this.start = 0;
		this.fps= 0;
		this.frames= 0;
};

Modell.prototype.modellupdate= function(currentTimeMillies) {
		this.frames++;
		if (this.start == 0)
			this.start = PartAnimation.prototype.currentTimeMillies();
		var now = PartAnimation.prototype.currentTimeMillies();
		if (now - this.start > 1000) {
			this.fps = Math.floor(((1000 * this.frames) / (now - this.start)));
			this.start = 0;
			this.frames = 0;
		}
};

Modell.prototype.getFps= function() {
		return this.fps;
};

Modell.prototype.getThings= function() {
		return allThings;
};

Modell.prototype.getNumberOfThings= function() {
		return numberOfThings;
};

Modell.prototype.isVisible= function(t) {
		return allThings[t].isVisible();
};

Modell.prototype.hasChanged= function(t) {
		return allThings[t].hasChanged();
};

Modell.prototype.getTexNameFromQuad= function(t) {
		return allThings[t].getTexName();
};

Modell.prototype.getType= function(t) {
		return allThings[t].getType();
};

Modell.prototype.getTexID= function(t) {
		return allThings[t].getTexID();
};

Modell.prototype.getData= function(t) {
		return allThings[t].getThingData();
};

Modell.prototype.getTex= function(t) {
		return allThings[t].getTex();
};

Modell.prototype.getNumberOfData= function(t) {
		return allThings[t].getNumberOfData();
};

Modell.prototype.texNameChanged= function(t) {
		return allThings[t].isTexchanged();
};

Modell.prototype.isTexIDSet= function(t) {
		return allThings[t].isTexidset();
};

Modell.prototype.setTexIDForQuad= function(t, i) {
		allThings[t].setTexID(i);
};

