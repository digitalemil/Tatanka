function Tatanka() {
	this.thinginit(12);
	this.coords = new CoordinateTap();
	this.setCoordinateTap(this.coords);

	var darkbrown = 0xff190d01;
	var lightbrown = 0xffae9879;
	var brown = 0xff3a270c;
	var horn = 0xff666666;
	var huf = 0xff000000;
	var red = 0xff9f0609;
	var schnauze = 0xff000000;
	var el;

	this.MAXARROWS = 5;
	this.MAXHEALTH = 100;
	this.health = this.MAXHEALTH;
	this.hits = 0;
	this.didColl= true;

	
	this.name = "Tatanka";

	var head = new Bone(0, -42, 16, 0, 8);
	head.name = "Head";

	el = new Ellipse(1, 1, 0, -20, -3, 0, TRIANGLES8, red);
	el.name = "Tongue";
	head.add(el);
	el = new Ellipse(14, 12, 0, -34, 0, -2, TRIANGLES8, schnauze);
	el.name = "Schnauze";
	head.add(el);
	el = new Ellipse(14, 5, -32, -12, -1, 20, TRIANGLES8, horn);
	el.name = "LinkesHorn1";
	head.add(el);
	el = new Ellipse(4, 8, -41, -22, 0, 15, TRIANGLES8, horn);
	el.name = "LinkesHorn2";
	head.add(el);
	el = new Ellipse(14, 5, 32, -12, -1, -20, TRIANGLES8, horn);
	el.name = "RechtesHorn1";
	head.add(el);
	el = new Ellipse(4, 8, 41, -22, 0, -15, TRIANGLES8, horn);
	el.name = "RechtesHorn2";
	head.add(el);

	el = new Ellipse(28, 32, 0, -10, -1, 0, TRIANGLES10, darkbrown);
	el.name = "Schaedel";
	head.add(el);

	head.setupDone();
	head.rotate(0);

	var body = new Bone(0, 0, 0, 0, 7);
	body.name = "Body";

	var leg = new Bone(0, -40, 0, 0, 2);
	leg.name = "LeftForeleg";
	el = new Ellipse(8, 12, -32, -6, -1, 0, TRIANGLES8, huf);
	leg.add(el);
	el.name = "LeftForelegPart1";
	el = new Ellipse(8, 12, -30, 0, 0, 0, TRIANGLES8, lightbrown);
	leg.add(el);
	el.name = "LeftForelegPart2";
	leg.setupDone();
	body.add(leg);
	leg = new Bone(0, -40, 0, 0, 2);
	leg.name = "RightForeleg";
	el = new Ellipse(8, 12, 32, -6, -1, 0, TRIANGLES8, huf);
	leg.add(el);
	el.name = "RightForelegPart1";
	el = new Ellipse(8, 12, 30, -0, 0, 0, TRIANGLES8, lightbrown);
	leg.add(el);
	el.name = "RightForelegPart2";
	leg.setupDone();
	body.add(leg);

	leg = new Bone(0, 38, 0, 0, 2);
	leg.name = "RightHindleg";
	el = new Ellipse(8, 12, 28, 0, -1, 0, TRIANGLES8, darkbrown);
	leg.add(el);
	el.name = "RightHindlegPart1";
	el = new Ellipse(8, 12, 26, 6, 0, 0, TRIANGLES8, brown);
	leg.add(el);
	el.name = "RightHindlegPart2";
	leg.setupDone();
	body.add(leg);
	leg = new Bone(0, 38, 0, 0, 2);
	leg.name = "LeftHindleg";
	el = new Ellipse(8, 12, -28, 0, -1, 0, TRIANGLES8, darkbrown);
	leg.add(el);
	el.name = "LeftHindlegPart1";
	el = new Ellipse(8, 12, -26, 6, 0, 0, TRIANGLES8, brown);
	leg.add(el);
	el.name = "LeftHindlegPart2";
	leg.setupDone();
	body.add(leg);

	el = new Ellipse(36, 44, 0, -10, 0, 0, TRIANGLES10, brown);
	el.name = "Rumpf";
	body.add(el);
	el = new Ellipse(32, 44, 0, 32, 0, 0, TRIANGLES10, brown);
	el.name = "Rumpf";
	body.add(el);
	el = new Ellipse(20, 36, 0, -20, 0, 0, TRIANGLES10, darkbrown);
	el.name = "Rumpf";
	body.add(el);

	el = new Ellipse(6, 10, 0, 76, 0, 0, TRIANGLES8, darkbrown);
	el.name = "Tail";
	body.add(el);
	body.setupDone();

	var rest = new Bone(0, -30, 0, 0, 2);
	rest.name = "Rest";
	rest.add(head);
	el = new Ellipse(36, 22, 0, -12, -2, 0, TRIANGLES10, darkbrown);
	el.name = "Shoulders2";
	rest.add(el);
	el = new Ellipse(38, 16, 0, -12, -2, 0, TRIANGLES10, lightbrown);
	el.name = "Shoulders";
	rest.add(el);

	el = new Ellipse(16, 36, 0, -4, -2, 0, TRIANGLES10, lightbrown);
	el.name = "Throat";
	rest.add(el);
	el = new Ellipse(12, 50, 0, 60, -2, 0, TRIANGLES10, red);
	el.name = "RumpfLinie";
	body.add(el);

	rest.setupDone();

	body.scaleRoot(1.0, 1.0);
	rest.scaleRoot(1.0, 1.0);

	var bc;
	// Important BCs need to go first
	bc = new BoundingCircle();
	bc.initBoundingCircle(16, 20, 0, 0);
	bc.setCoordinateTap(new CoordinateTap("tbc0"));
	this.add(bc);
	bc = new BoundingCircle();
	bc.initBoundingCircle(16, -20, -2, 0);
	bc.setCoordinateTap(new CoordinateTap("tbc1"));
	this.add(bc);
	bc = new BoundingCircle();
	bc.initBoundingCircle(35, 0, 38, 0);
	bc.setCoordinateTap(new CoordinateTap("tbc2"));
	this.add(bc);
	bc = new BoundingCircle();
	bc.initBoundingCircle(35, 0, -30, 0);
	bc.setCoordinateTap(new CoordinateTap("tbc3"));
	this.add(bc);
	bc = new BoundingCircle();
	bc.initBoundingCircle(28, 0, -85, 0);
	bc.setCoordinateTap(new CoordinateTap("tbc4"));
	this.add(bc);
	this.add(body);
	this.add(rest);

	var i;

	for (i = 0; i < this.MAXARROWS; i++) {
		var arrow = new Arrow();
		arrow.name = "arrow" + i;
		arrow.setVisibility(false);
		this.add(arrow);
	}
	this.setupDone();

	this.animation = new AnimalAnimation(this, 16, 1000);
	this.animation.startRun();

	this.tatankahealth = 100;
	this.speed = 0;
}

Tatanka.prototype = new Thing(12);

Tatanka.prototype.hit = function(part) {
	this.hits++;
	if (part < 2) {
		this.health -= 80 + getRandom(0, 40);
	} else {
		this.health -= 30 + getRandom(0, 10);
	}
	if (this.health <= 0) {
		this.speed = 0;
		this.health = 100;
	}
};

Tatanka.prototype.resetHealth = function() {
	var i;
	for (i = 0; i < this.MAXARROWS; i++) {
		var arr = this.getByName("arrow" + i);
		arr.setCoordinateTap(null);
		arr.getByName("BC").setCoordinateTap(null);
		arr.x = 0;
		arr.y = 0;
		arr.rot = 0;
		arr.setVisibility(false);
	}
	this.hits = 0;
	this.health = 100;
};

Tatanka.prototype.getType = function() {
	return TATANKA;
};


Tatanka.prototype.didCollide= function() {
	return this.didColl;
};

Tatanka.prototype.setDidCollide= function(dc) {
	this.didColl = dc;
};

Tatanka.prototype.animate= function() {	
	if(this.animation!= null)
		this.animation.animateImpl();
};	

Tatanka.prototype.getAnimation= function() {
	return this.animation;
};

