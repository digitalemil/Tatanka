function BizAndSportsModell() {

}

BizAndSportsModell.prototype = new Modell(64);

BizAndSportsModell.prototype.setup = function() {
	console.log("scale: " + scale + " w: " + width + " h: " + height);
	this.widgets= new Array(10);
	
	for ( var x = 0; x < 4; x++) {
		for ( var y = 0; y < 2; y++) {
			this.widgets[y * 4 + x] = new WidgetWithIcon("Belt:" + (y * 10 + x), -3 * 200 + x * 400, -2 * 100 + y
					* 300, 0);
			this.widgets[y * 4 + x].scale(0.5, 0.5);
		}
	}

	var pos= 0;
	for (var layer = 0; layer < 1000; layer++) {
		for (var j = 0; j < 8; j++) {
			pos += this.widgets[j].addThings(allThings, pos, layer);
		}
	}
	numberOfThings = pos;
	console.log("NumberOfThings: "+numberOfThings);
	this.fr= 0;
};

BizAndSportsModell.prototype.update = function(currentTimeMillis) {
	if(this.fr%10==1) {
	//	allThings[50].scale(1.05, 1.05);
	}
	this.fr++;
	this.modellupdate(currentTimeMillis);
	for ( var i = 0; i < numberOfThings; i++) {
		if(allThings[i] instanceof Widget )
			allThings[i].update();
	}
};

BizAndSportsModell.prototype.touch = function(x, y) {
	for ( var i = 0; i < numberOfThings; i++) {
		if (allThings[i].dragged) {
			this.widgets[i/3].translate(-allThings[i].x + x - w2, -allThings[i].y
					+ y - h2);
			return;
		}
	}
};

BizAndSportsModell.prototype.touchStart = function(x, y) {
	// console.log("touch: "+(x-w2)+" "+(y-h2));
	for ( var i = 0; i < numberOfThings; i++) {
		if (allThings[i].isIn(x - w2, y - h2)) {
			allThings[i].dragged = true;
		}
	}
};

BizAndSportsModell.prototype.touchStop = function(x, y) {
	for ( var i = 0; i < numberOfThings; i++) {
		allThings[i].dragged = false;
	}
};

function WidgetWithIcon(text, x, y) {
	this.__setup(5);
	this.things[0] = new Widget(text);
	this.things[1] = new ImageThing("../../../keep-res/ekho.png", 48, 24);
	this.things[1].translate(120, 108, 0);
	this.things[2] = new ImageThing("../../../keep-res/io.png", 24, 24);
	this.things[2].translate(-144, 108, 0);
	this.things[3] = new ImageThing("res/ekho.png", 48, 24);
	this.things[3].translate(120, 108, 0);
	this.things[4] = new ImageThing("res/io.png", 24, 24);
	this.things[4].translate(-144, 108, 0);

	this.translate(x, y);
}

WidgetWithIcon.prototype = new ThingContainer(2);

function Widget(text) {
	this.thinginit(5);
	this.coords = new CoordinateTap("WidgetCoords");
	this.setCoordinateTap(this.coords);
	this.ct = new Array(2);
	this.rects = new Array(2);
	this.texts = new Array(6);
	this.hr = 90;
	this.updates = 0;
	var gray1 = 0x30FFFFFF;
	var gray2 = 0x80000000;
	var red1= 0x80AA0000;
	var hred= 0xAAFF0000;

	this.name = "Widget";
	this.rects[0] = new Rectangle();
	this.rects[0].init(320, 240, 0, 0, 0, 0, gray1);
	this.ct[0] = new CoordinateTap("Frame");
	this.rects[0].setCoordinateTap(this.ct[0]);
	this.add(this.rects[0]);

	this.rects[1] = new Rectangle();
	this.rects[1].init(316, 188, 0, 0, 0, 0, red1);
	this.ct[1] = new CoordinateTap("Body");
	this.rects[1].setCoordinateTap(this.ct[1]);
	this.add(this.rects[1]);
	
	this.heart = new Bone(92, -46, 0, 0, 8);
	this.heart.setName("Heart");
	var p = new Ellipse(10, 12, -8, 12, 0, -30, TRIANGLES10, hred);
	this.heart.add(p);
	p = new Ellipse(10, 12, 8, 12, 0, 30, TRIANGLES10, hred);
	this.heart.add(p);
	p = new Triangle(0, 10, 0, -16, 8, 0, 20, 16, 8, 0, hred);
	this.heart.add(p);

	this.heart.setupDone();
	this.heart.scale(2, 2);

	this.add(this.heart);
	this.add(new Line(-152, -72, 20, -72, 1, 0, 0xFFFFFFFF));	
	this.add(new Line(-152, -72, -152, 90, 1, 0, 0xFFFFFFFF));	
	this.add(new Line(-152, 90, 20, 90, 1, 0, 0xFFFFFFFF));	
	this.add(new Line(20, 90, 20, -72, 1, 0, 0xFFFFFFFF));	
	

	this.add(new Line(28, -72, 152, -72, 1, 0, 0xFFFFFFFF));	
	this.add(new Line(28, -72, 28, 90, 1, 0, 0xFFFFFFFF));	
	this.add(new Line(28, 90, 152, 90, 1, 0, 0xFFFFFFFF));	
	this.add(new Line(152, 90, 152, -72, 1, 0, 0xFFFFFFFF));	
	
	
	
	var it = 0;
	this.texts[it] = new Text();
	this.texts[it].init(text, 0, -108, 0xFF000000);
	this.texts[it].setFont("bold");
	this.texts[it].setSize(20);
	this.add(this.texts[it]);

	it++;
	this.texts[it] = new Text();
	this.texts[it].init("HR", 32, -64, 0xFF000000);
	this.texts[it].setFont("bold");
	this.texts[it].setSize(18);
	this.texts[it].setAlignment(TEXT_LEFT);
	
	this.add(this.texts[it]);

	it++;
	this.texts[it] = new Text();
	this.texts[it].init("Zone 5", -60, -20, 0xFF000000);
	this.texts[it].setFont("bold");
	this.texts[it].setSize(36);
	this.texts[it].setAlignment(TEXT_CENTER);
	this.add(this.texts[it]);

	
	it++;
	this.hrindex= it;	
	this.texts[it] = new Text();
	this.texts[it].init("130", 92, -20, 0xFF000000);
	this.texts[it].setFont("bold");
	this.texts[it].setSize(36);
	this.texts[it].setAlignment(TEXT_CENTER);
	this.add(this.texts[it]);

	it++;
	this.texts[it] = new Text();
	this.texts[it].init("Recovery", 92, 48, 0xFF000000);
	this.texts[it].setFont("bold");
	this.texts[it].setSize(12);
	this.texts[it].setAlignment(TEXT_CENTER);
	this.add(this.texts[it]);

	it++;
	this.texts[it] = new Text();
	this.texts[it].init("0", 146, 64, 0xFFFFFFFF);
	this.texts[it].setFont("bold");
	this.texts[it].setSize(36);
	this.texts[it].setAlignment(TEXT_RIGHT);
	this.add(this.texts[it]);
	
	it++;
	this.texts[it] = new Text();
	this.texts[it].init("Zone", -144, -64, 0xFF000000);
	this.texts[it].setFont("bold");
	this.texts[it].setSize(18);
	this.texts[it].setAlignment(TEXT_LEFT);
	this.add(this.texts[it]);
	
	it++;
	this.texts[it] = new Text();
	this.texts[it].init("Calories", -60, 48, 0xFF000000);
	this.texts[it].setFont("bold");
	this.texts[it].setSize(12);
	this.texts[it].setAlignment(TEXT_CENTER);
	this.add(this.texts[it]);
	this.setupDone();

	it++;
	this.texts[it] = new Text();
	this.texts[it].init("512", -60, 70, 0xFF000000);
	this.texts[it].setFont("bold");
	this.texts[it].setSize(18);
	this.texts[it].setAlignment(TEXT_CENTER);
	this.add(this.texts[it]);
	this.setupDone();
	this.ani = new HeartAnimation(this.heart, 800);
	this.ani.create();
	this.ani.setHR(this.hr);

}

Widget.prototype = new Thing(1);

Widget.prototype.getType = function() {
	return ARROW;
};

Widget.prototype.isIn = function(x, y) {
	this.dragged = false;

	for ( var i = 0; i < 2; i++) {
		// console.log("xmin: "+this.ct[i].a11+" xmax: "+this.ct[i].a21+ " ymin:
		// "+this.ct[i].a12+" ymax: "+this.ct[i].a22);
		if (x >= this.ct[i].a11 && x <= this.ct[i].a21 && y >= this.ct[i].a12
				&& y <= this.ct[i].a22) {
			return true;
		}
	}
	return false;
};

Widget.prototype.update = function() {
	this.updates++;

	this.ani.animateImpl();
	if (this.updates % 10 == 1) {
		this.hr += -2 + getRandom(0, 4);
		this.texts[this.hrindex].setText(this.hr);
		this.ani.setHR(this.hr);
	}
};

function HeartAnimation(heart, duration) {
	this.duration = duration;
	this.heart = heart;

	this.init("HeartAnimation", 2, 1, true);
}

HeartAnimation.prototype = new CompositeAnimation();

HeartAnimation.prototype.clear = function() {
	for ( var i = 0; i < this.maxanimation; i++) {
		for ( var j = 0; j < this.maxlevel; j++) {
			if (this.anis[j * this.maxanimation + i] != null) {
				this.anis[j * this.maxanimation + i].stop();
				if (this.anis[j * this.maxanimation + i] != null)
					this.anis[j * this.maxanimation + i] = null;
				this.anis[j * this.maxanimation + i] = null;
			}
		}
	}
};

HeartAnimation.prototype.setHR = function(hr) {
//	console.log("HR: "+hr);
	this.pa1.duration= Math.round(1000.0*60.0/hr/2);
	this.pa2.duration= Math.round(1000.0*60.0/hr/2);	
};

HeartAnimation.prototype.create = function(s, d) {
	this.clear();
	this._loops = true;

	this.pa1 = new PartAnimation();
	this.pa1.init(this.heart, 0.0, 0.0, 0.0, 1.32, 1.32, this.duration, false);

	this.addAnimation(this.pa1, 0);
	this.pa2= this.pa1.createReverseAnimation();
	this.addAnimation(this.pa2, 1);
	this.start();
};

HeartAnimation.prototype.increaseLevelImpl = function() {
	if (this.level == 2)
		this.heart.reset();
};

HeartAnimation.prototype.startImpl = function() {
	this.start();
	this.heart.reset();
};

HeartAnimation.prototype.animateImpl = function() {
	var now = (new Date()).getTime();

	if (!this.running) {
		return 1.0;
	}

	var l = this.level;
	var ret = this.animate(now);

	if (l != this.level) {
		this.increaseLevelImpl();
		if (l == 2) {
			this.heart.reset();
		}
	}

	return ret;
};

HeartAnimation.prototype.getType = function() {
	return ANIMATION;
};
