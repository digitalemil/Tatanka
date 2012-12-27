function TatankaModell() {
	
}

TatankaModell.prototype = new Modell(64);

TatankaModell.prototype.setup= function() {
	console.log("scale: "+scale+" w: "+width+" h: "+height);
	this.prairie = new Prairie("valleygras512.jpg");
	this.herds = new Array(1);
	this.herds[0] = new TatankaHerd(8);

	this.nlakotas= 1;
	this.lakotas = new Array(this.nlakotas);
	this.activelakota= 0;
	
	for (var i = 0; i < this.nlakotas; i++) {
		this.lakotas[i] = new MountedLakota(80 * i, 320);
	}

	var pos = 0;
	for (var layer = 0; layer < 1000; layer++) {
		pos += this.prairie.addThings(allThings, pos, layer);
		pos += this.herds[0].addThings(allThings, pos, layer);
		for (var j = 0; j < this.nlakotas; j++) {
			pos += this.lakotas[j].addThings(allThings, pos, layer);
		}
	}
	this.joystick = new Joystick();
	this.joystick.setup(this.lakotas[0]);
	allThings[pos++] = this.joystick;
	this.joystick.translate(w2-128 * scale ,
			h2 - 108 * scale, 0);
	
	numberOfThings = pos;
	console.log(allThings[0]);
	console.log(allThings[0].getType());
	console.log(allThings[0].texName);
	
};

TatankaModell.prototype.update= function(currentTimeMillis) {
	this.modellupdate(currentTimeMillis);
	
 	if(this.fps> 0)
		this.lakotas[this.activelakota].update(this.joystick.getRadius()/20.0/this.getFps());
	else
		this.lakotas[this.activelakota].update(this.joystick.getRadius()/20.0/30);
	this.lakotas[this.activelakota].update(0);

	for(var j= 0; j< this.nlakotas; j++) {
		if(this.lakotas[j]== this.lakotas[this.activelakota])
			continue;
		this.lakotas[j].update(0);
	}
	
	//console.log(this.lakotas[this.activelakota].getSpeedX()+" "+ this.lakotas[this.activelakota].getSpeedY());
	this.prairie.update(this.lakotas[this.activelakota].getSpeedX(), this.lakotas[this.activelakota].getSpeedY());		
	this.herds[0].update(this.lakotas[this.activelakota].getSpeedX(), this.lakotas[this.activelakota].getSpeedY(),  this.lakotas[this.activelakota].lakota.x+ this.lakotas[this.activelakota].lakota.rx, this.lakotas[this.activelakota].lakota.y+ this.lakotas[this.activelakota].lakota.ry);
	
	
	var x = -(this.herds[0].getAlphaX()- this.lakotas[this.activelakota].lakota.x- this.lakotas[this.activelakota].lakota.rx);
	var y = (this.herds[0].getAlphaY()- this.lakotas[this.activelakota].lakota.y- this.lakotas[this.activelakota].lakota.ry);
	var newdir = Math.atan2(y, x);
	if (newdir < 0) {
		newdir += 2 * Math.PI;
	}
	newdir = Math.floor((newdir * 360.0 / (2 * Math.PI)));
	
	this.joystick.update(newdir);
};

TatankaModell.prototype.touch= function(x, y) {
	this.joystick.move(x, y);
};

TatankaModell.prototype.touchStart= function(x, y) {
	if(!this.lakotas[this.activelakota].shoot(x, y)) {
		this.joystick.down(x, y);
	}
	return true;
};

TatankaModell.prototype.touchStop= function(x, y) {
	this.joystick.up();
	return true;
};
