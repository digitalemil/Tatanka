var services;
var OPTIMIZED= true;
var fullOptimized= false;

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
	this.getView().setActive(true);
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

function View(c, o, callback) {
	this.textures = new Array(1);
	this.ctx = c.getContext('2d');
	this.init(c, o, callback);
}

View.prototype.init = function(c, o, callback) {
	this.things = o;
	this.cb = callback;
	services = new Services();
	services.setView(this);
	services.getView().enabled = true;
	this.font= "bold "+Math.round(32*scale)+"px sans-serif";
	this.timer = new MyTimer(itsTime);
	fpstimer = new MyTimer(fpsTime);
	services.getView().update();

	this.timer.start(25);
	fpstimer.start(1000);
	
	this.tatankas= 0;
	this.tatankahealth= 100;
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
		this.ctx.moveTo(d[di + 4 + 2*cor] + canvaswidth / 2, d[di + 4 + 1+ 2*cor]
				+ canvasheight / 2);
		for ( var li = 2; li < 2*(ni); li+=2) {
			
			this.ctx.lineTo(d[di + 4 + li+ 2*cor] + canvaswidth / 2, d[di + 4
					+ li + 1+ 2*cor]
					+ canvasheight / 2);
		}
		this.ctx.closePath();
		this.ctx.fill();
		di += 4 + 2* (ni+1);
	}

}
View.prototype.paintQuad= function(quad, teximg) {
	var d = quad.getThingData();
	var i = 6;
	var xmin = this.min(d[i], d[i + 2], d[i + 4], d[i + 6]);
	var ymin = this.min(d[i + 1], d[i + 3], d[i + 5], d[i + 7]);
			
	if(OPTIMIZED) {
		this.ctx.drawImage(teximg, xmin+canvaswidth/2, canvasheight/2+ymin);
		return;
	}
	var xmax = this.max(d[i], d[i + 2], d[i + 4], d[i + 6]);
	var ymax = this.max(d[i + 1], d[i + 3], d[i + 5], d[i + 7]);

	var dx = Math.round(Math.min(d[i], d[i + 4]));
	var dy = Math.round(Math.min(d[i + 1], d[i + 5]));

	var dw = (Math.round(Math.abs(xmax - xmin) + 0.5));
	var dh = (Math.round(Math.abs(ymax - ymin) + 0.5));

	var phi = -(quad.rot - 90) / 360.0 * Math.PI * 2;
	var smcphi2 = (Math.sin(phi) * Math.sin(phi) - Math.cos(phi)
			* Math.cos(phi));
	var dh1 = Math.round(((-Math.abs(dw * Math.sin(phi)) - dh
			* Math.cos(phi)) / smcphi2));
	var dw1 = Math.round(((-Math.abs(dh * Math.sin(phi)) - dw
			* Math.cos(phi)) / smcphi2));
	var dh2 = Math.round(((Math.abs(dw * Math.sin(phi)) - dh
			* Math.cos(phi)) / smcphi2));
	var dw2 = Math.round(((Math.abs(dh * Math.sin(phi)) - dw
			* Math.cos(phi)) / smcphi2));

	dh1 = Math.min(dh1, dh2);
	dw1 = Math.min(dw1, dw2);

	/*
	 * var dh1 = ymax- ymin; var dw1 = xmax- xmin; phi= rot;
	 */
	var tx = quad.getTex();
	// console.log(teximg.width+ " "+ teximg.height+ " "+tx);
	var sx = Math.round((tx[0] * teximg.width));
	var sy = Math.round((tx[5] * teximg.height));
	var sw = Math.round((tx[2] * teximg.width - sx));
	var sh = Math.round((tx[1] * teximg.height - sy));
	this.ctx.translate(dx - (dw - dw1), dy + ((phi < 0) ? 1 : -1)
			* (dh - dh1));
	// this.ctx.translate(canvaswidth/2, canvasheight/2);
	this.ctx.rotate(phi);

	this.ctx.drawImage(teximg, sx, sy, sw, sh, canvaswidth / 2,
			canvasheight / 2, dw1, dh1);
	
	//this.ctx.drawImage(teximg, dw1, dh1);

	// console.log("sx: "+sx+ " sy: "+sy+" sw: "+sw+" sh: "+sh+" dw1: "+dw1+" dh1: "+dh1);
	this.ctx.rotate(-phi);
	// this.ctx.translate(-canvaswidth/2, -canvasheight/2);

	// this.ctx.translate(-(dw1/2+canvaswidth/2),-(dh1/2+canvasheight/2));

	this.ctx.translate(-(dx - (dw - dw1)), -(dy + ((phi < 0) ? 1 : -1)
			* (dh - dh1)));
}

View.prototype.paintPrep= function() {

//	 this.ctx.clearRect(0, 0, canvaswidth, canvasheight);
		for ( var t = 0; t < this.things.length; t++) {
			if (this.things[t] == undefined)
				continue;
			if (this.things[t].getType() == QUAD) {
				var quad = this.things[t];
				var texname = quad.texName;
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

		frames++;
}
View.prototype.paint = function() {	
	if (!this.enabled) 
		return;
	fps++;

	if (!(this.pa == undefined)) {
		this.pa.animateNow();
	}
	if (tatankaani != undefined) {
		tatankaani.tatankaAnimate();
	}
	if (horseani != undefined) {
		horseani.tatankaAnimate();
	}
	if (this.cb != undefined && this.cb != null) {
		this.cb();
	}
	if(!fullOptimized) {
		this.paintPrep();
	}
	else {
		this.ctx.fillStyle='#8f9237';
		this.ctx.fillRect(0, 0, canvaswidth, canvasheight);
	}
	// this.ctx.clearRect(0, 0, canvaswidth, canvasheight);
	/*
	 * if(!this.bgimg.loaded) return; for(var y= 0; y<= canvasheight/256; y++) {
	 * for(var x= 0; x<= canvaswidth/256; x++) { this.ctx.drawImage(this.bgimg,
	 * x*256, y*256); } }
	 */
	
	// this.things[0].rotate(0.01);
	
	for ( var t = 0; t < this.things.length; t++) {
		if (this.things[t] == undefined )
			continue;
		
		if (!this.things[t].visible)
			continue;
		if (this.things[t].getType() == QUAD) {
			if(fullOptimized)
				continue;
			var quad = this.things[t];
			
			if (quad.texID == 0)
				continue;
			var tex = quad.texID;
			var teximg = this.textures[tex];
			if (!teximg.loaded)
				continue;
			this.paintQuad(quad, teximg);
			
			// quad.rotate(rot);
		} else {
			var d = this.things[t].getThingData();
			var di = 0;
			var len = this.things[t].nd;
			//console.log("Painitng: "+this.things[t].name);
			this.paintThing(d, di, len);
			//console.log("Painitng done.");
			
		}
	}  
	
	this.ctx.font = this.font;
	this.ctx.fillStyle = "#592b13";
	this.ctx.strokeStyle = "#592b13";
	this.ctx.fillText("  fps: " + fpstext, 4, 36);
	var tx= 104*scale;
	this.ctx.fillText(this.siouxs, tx, canvasheight-150*scale);
	this.ctx.fillText(this.arrows, tx, canvasheight-80*scale);
	if(this.tatankas> 100) {
		tx-= 32*scale;
	}
	this.ctx.fillText(this.tatankas, tx, canvasheight-24*scale);

	// console.log("fps: "+fpstext);
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

View.prototype.setActive = function(b) {
};

View.prototype.setCourse = function(deg, x, y) {
};

View.prototype.keyPressed = function(key) {
	switch (key) {
	case 73:
	case 50: // i: Up
		break;

	case 74:
	case 52:// j: Left
		break;

	case 75:
	case 58: // k: Down
		break;

	case 76:
	case 56: // l: Right
		break;
	}
};

View.prototype.update = function(timer) {
	this.paint();
};

View.prototype.loadTexture = function(name) {
	for ( var i = 1; i < this.textures.length; i++) {
	//	console.log("Texture already loaded: " + this.textures[i].src);

		if (this.textures[i] != undefined
				&& this.textures[i].src.match(".*res/" + name)) {
			return i;
		}
	}
	var id = this.textures.length;
	var texture = new Image();
	this.textures[id] = texture;
	console.log("id top: "+id);
	texture.loaded = false;
	var texs= this.textures;
	texture.onload = function() {
		texture.loaded = true;
		if(OPTIMIZED && (name.match(".*gras.*") || name.match(".*brush.*"))) {
			var bgd = Math.max(canvaswidth, canvasheight);
			var img= createOffScreenImage(bgd+1, bgd+1);
			var ctx= img.getContext('2d');
			ctx.drawImage(texture, 0, 0, bgd+1, bgd+1);
			texs[id]= img;
			texs[id].loaded= true;
		}
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
