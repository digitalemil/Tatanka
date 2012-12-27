var services;
var OPTIMIZED= true;
var fullOptimized= false;
var fps= 0;

function Services() {
	this.view;
}

Services.prototype.update = function() {
	console.log("Timer!");
};

Services.prototype.getView = function() {
	return this.view;
};

Services.prototype.setActive = function(b) {
};

Services.prototype.setView = function(v) {
	if (this.getView())
		this.getView().setActive(false);
	this.view = v;
//	this.getView().setActive(true);
};

function itsTime(timer) {
//	console.log("services timer: "+timer.timeout);
	services.getView().update(timer);
}

function fpsTime(timer) {
	fpstext = fps;
	if (fps >= 41) {
		services.getView().timer.timeout += 1;
	}
	if (fps < 40 && services.getView().timer.timeout > 0)
		services.getView().timer.timeout -= 4;
	fps = 0;
};

var createOffScreenImage = function(width, height) {
    var buffer = document.createElement('canvas');
    buffer.width = width;
    buffer.height = height;
    return buffer;
};

function View(canvas) {
	this.textures = new Array(1);
	this.ctx = canvas.getContext('2d');

	setWidth(canvas.width);
	setHeight(canvas.height);
	this.modell= new TatankaModell();
	this.modell.setup();
console.log("numberOfThings: "+this.modell.getNumberOfThings());
	this.init();
}

View.prototype.init = function() {
	services = new Services();
	services.setView(this);
	services.getView().enabled = true;
	this.font= "bold "+Math.round(32*scale)+"px sans-serif";
	this.timer = new MyTimer(itsTime);
	fpstimer = new MyTimer(fpsTime);
	services.getView().update();

	this.timer.start(25);
	fpstimer.start(1000);
	this.n= 0;
};

View.prototype.loadImg = function(n) {
	var img = new Image();
	img.loaded = false;
	img.onload = function() {
		img.loaded = true;
	};
	img.src = n;
	return img;
};

View.prototype.paintThing= function(d, di, len) {
	while (di < len) {
		var type= d[di]; 
		if(type== BOUNDINGCIRCLE && !visibleboundingcircle) {
			di+= 7;
			continue;
		}
		var ni = d[di + 1];
		var color = RGB2HTML(d[di + 2]);
		var cor= d[di + 3];
		this.ctx.fillStyle = color;
		this.ctx.strokeStyle = color;
		this.ctx.beginPath();
		if(type== BOUNDINGCIRCLE) {
			di++;
		}	
		var dd= di + 4 + 2*cor; 
		this.ctx.moveTo(d[dd] + w2, d[dd+1] + h2);
		var nimax= 2* ni;
		for ( var li = 2; li < nimax; li+=2) {
			this.ctx.lineTo(d[dd + li] + w2, d[dd + li + 1] + h2);
		}
		this.ctx.closePath();
		this.ctx.fill();
		di += 4 + 2* (ni+1);
	}
};

View.prototype.paintQuad= function(quad, teximg) {
	var d = quad.getThingData();
	var i = 6;
	var xmin = this.min(d[i], d[i + 2], d[i + 4], d[i + 6]);
	var ymin = this.min(d[i + 1], d[i + 3], d[i + 5], d[i + 7]);

//	if(this.n< 40)
//	console.log("x: "+(xmin+w2)+" y: "+(h2+ymin)+" "+quad+" "+quad.name+" "+teximg+" "+teximg.loaded+" "+teximg.src);
//	this.n++;
	this.ctx.drawImage(teximg, xmin+w2, h2+ymin);
};

View.prototype.paintPrep= function() {
	var things= this.modell.getThings();
//	 this.ctx.clearRect(0, 0, canvaswidth, canvasheight);
		for ( var t = 0; t < this.modell.getNumberOfThings(); t++) {
			if (things[t] == undefined)
				continue;
			if (things[t].getType() == QUAD) {
				var quad = things[t];
				var texname = quad.getTexName();
				var tex = quad.texID;
				
				if ((texname == null || texname == undefined)) {
					continue;
				}
				if (quad.texchanged) {
					if (quad.isTexIDSet()) {
						this.textures[tex] = null;
					}
					if (texname == null) {
						console.log("setTexQuadID to 0");
						quad.setTexID(0);
						continue;
					}
					tex = this.loadTexture(texname);
					quad.setTexID(tex);
					console.log("setTexQuadID to t:" + tex);
				}
			}
		}
		this.ctx.fillStyle = '#000000';
		this.ctx.strokeStyle = '#000000';
};

View.prototype.paint = function() {	
	this.modell.update();
	var things= this.modell.getThings();
	this.paintPrep();
	for ( var t = 0; t < this.modell.getNumberOfThings(); t++) {
		if (things[t] == undefined )
			continue;
		
		if (!things[t].visible)
			continue;
		
		if (things[t].getType() == QUAD) {
			var quad = things[t];
		//	console.log("t: "+t+" "+quad.texID);
			if (quad.texID == 0)
				continue;
			var tex = quad.texID;
			if(tex== undefined)
				continue;
			var teximg = this.textures[tex];
			if (!teximg.loaded)
				continue;
			this.paintQuad(quad, teximg);
		} else {
			var d = things[t].getThingData();
			var di = 0;
			var len = things[t].nd;
		//	console.log("Paint thing: "+ things[t].name);
			this.paintThing(d, di, len);
		}
	}  
	
	this.ctx.font = this.font;
	this.ctx.fillStyle = "#592b13";
	this.ctx.strokeStyle = "#592b13";
	this.ctx.fillText("  fps: " + this.modell.getFps(), 4, 36);
	var tx= 104*scale;
	/*
	this.ctx.fillText(this.siouxs, tx, canvasheight-150*scale);
	this.ctx.fillText(this.arrows, tx, canvasheight-80*scale);
	if(this.tatankas> 100) {
		tx-= 32*scale;
	}
	this.ctx.fillText(this.tatankas, tx, canvasheight-24*scale);
	*/
};

function dec2hex(i) {
	return (i + 0x100).toString(16).substr(-2).toUpperCase();
}

function RGB2HTML(c) {
	var a = (c >>> 24)/255;
	var r = ((c & 0x00FF0000) >> 16);
	var g = ((c & 0x0000FF00) >> 8);
	var b = ((c & 0x000000FF));
	return "rgba("+r+","+g+","+b+","+a+")"; 
	return "#" + dec2hex(r) + dec2hex(g) + dec2hex(b);
};


View.prototype.update = function(timer) {
	this.paint();
};

View.prototype.loadTexture = function(name) {
	var id = this.textures.length;
	var texture = new Image();
	this.textures[id] = texture;
	texture.loaded = false;
	
	texture.onload = function() {
		texture.loaded = true;
	};

	texture.src = "res/" + name;
	console.log("Tex: " + name + " id: " + id + " " + this.textures[id].src);

	return id;
};

View.prototype.min = function(f1, f2, f3, f4) {
	var ret = f1;
	ret = Math.min(ret, f2);
	ret = Math.min(ret, f3);
	ret = Math.min(ret, f4);
	return ret;
};

View.prototype.max = function(f1, f2, f3, f4) {
	var ret = f1;
	ret = Math.max(ret, f2);
	ret = Math.max(ret, f3);
	ret = Math.max(ret, f4);
	return ret;
};

View.prototype.up= function(e) {
	e.preventDefault();
	var event;
	if (e.touches != undefined) {
        event = e.touches[0];
	} else {
        event = e;
	}

	var x = event.clientX;
	var y=  event.clientY;
	this.modell.touchStop(x, y);
};


View.prototype.down= function(e) {
	e.preventDefault();
	var event;
	if (e.touches != undefined) {
        event = e.touches[0];
	} else {
        event = e;
	}

	var x = event.clientX;
	var y=  event.clientY;
	this.modell.touchStart(x, y);
};


View.prototype.move= function(e) {
	e.preventDefault();
	var event;
	if (e.touches != undefined) {
        event = e.touches[0];
	} else {
        event = e;
	}

	var x = event.clientX;
	var y=  event.clientY;
	this.modell.touch(x, y);
};

