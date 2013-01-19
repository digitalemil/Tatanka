
function Prairie(imgname) {
	this.imgdim= 512;

	this.ntx = Math.floor(width / this.imgdim + 2);
	this.nty = Math.floor(height / this.imgdim + 2);
	//console.log("width: "+width+" height: "+height+ " ntx: "+this.ntx+" nty: "+this.nty);
	this.__setup(this.ntx* this.nty);
	
	var nt = 0;
	for (var x = 0; x < this.ntx; x++) {
		for (var y = 0; y < this.nty; y++) {
			this.things[nt] = new ImageThing("res/"+imgname, this.imgdim, this.imgdim);
			this.things[nt].setName("Prairie:" + nt);
			this.things[nt].translate(
					-Math.abs(Math.floor((width - this.imgdim * this.ntx) / 2)) + x
							* this.imgdim + this.imgdim / 2 - w2,
					-Math.abs(Math.floor((height - this.imgdim * this.nty) / 2)) + y
							* this.imgdim + this.imgdim / 2 - h2, 0);
			nt++;
		}
	}
}

Prairie.prototype = new ThingContainer(0);

Prairie.prototype.update = function(speedx, speedy) {
	this.translate(speedx * 2, speedy * 2, 0);

	for (var i = 0; i < this.n; i++) {
		if (this.things[i].x > width / 2 + this.imgdim / 2)
			this.things[i].translate(-this.ntx * this.imgdim, 0, 0);
		if (this.things[i].x < -(width / 2 + this.imgdim / 2))
			this.things[i].translate(this.ntx * this.imgdim, 0, 0);
		if (this.things[i].y > height / 2 + this.imgdim / 2)
			this.things[i].translate(0, -this.nty * this.imgdim, 0);
		if (this.things[i].y < -(height / 2 + this.imgdim / 2))
			this.things[i].translate(0, this.nty * this.imgdim, 0);
	}

};

