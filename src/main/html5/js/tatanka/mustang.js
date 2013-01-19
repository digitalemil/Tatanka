function Horse() {
	this.thinginit(7);
	var brown = 0xff3a270c;
	var brown2 = 0xff705125;
	var huf2 = 0xff404040;
	var white = 0xffffffff;
	var white2 = 0xffE0E0E0;

	var el;

	this.name = "Horse";
	var head = new Bone(0, -42, 16, 0, 8);
	head.name = "Head";

	el = new Ellipse(8, 12, 0, -20, -3, 0, TRIANGLES8, white);
	el.name = "h1";
	head.add(el);
	el = new Ellipse(8, 12, 0, -34, 0, -2, TRIANGLES8, white);
	el.name = "h2";
	head.add(el);
	el = new Ellipse(2, 3, -4, -40, -1, 20, TRIANGLES8, brown);
	el.name = "NuesterLinks";
	head.add(el);
	el = new Ellipse(2, 3, 4, -40, -1, -20, TRIANGLES8, brown);
	el.name = "NuesterRechts";
	head.add(el);
	el = new Ellipse(12, 12, 0, -10, -1, 0, TRIANGLES8, white);
	el.name = "Schaedel";
	head.add(el);
	el = new Ellipse(3, 5, -8, -10, 0, -15, TRIANGLES8, white2);
	el.name = "LinkesOhr";
	head.add(el);
	el = new Ellipse(3, 5, 8, -10, 0, 15, TRIANGLES8, white2);
	el.name = "RechtesOhr";
	head.add(el);

	head.setupDone();
	head.rotate(0);

	var body = new Bone(0, 0, 0, 0, 7);
	body.name = "Body";

	var leg = new Bone(0, -46, 0, 0, 2);
	leg.name = "LeftForeleg";
	el = new Ellipse(4, 8, -16, -6, -1, 0, TRIANGLES8, huf2);
	leg.add(el);
	el.name = "LeftForelegPart1";
	el = new Ellipse(4, 10, -15, 0, 0, 0, TRIANGLES8, white2);
	leg.add(el);
	el.name = "LeftForelegPart2";
	leg.setupDone();
	body.add(leg);
	leg = new Bone(0, -46, 0, 0, 2);
	leg.name = "RightForeleg";
	el = new Ellipse(4, 8, 16, -6, -1, 0, TRIANGLES8, huf2);
	leg.add(el);
	el.name = "RightForelegPart1";
	el = new Ellipse(4, 10, 15, -0, 0, 0, TRIANGLES8, white2);
	leg.add(el);
	el.name = "RightForelegPart2";
	leg.setupDone();
	body.add(leg);

	leg = new Bone(0, 32, 0, 0, 2);
	leg.name = "RightHindleg";
	el = new Ellipse(4, 8, 16, 0, -1, 0, TRIANGLES8, huf2);
	leg.add(el);
	el.name = "RightHindlegPart1";
	el = new Ellipse(4, 10, 15, 6, 0, 0, TRIANGLES8, white2);
	leg.add(el);
	el.name = "RightHindlegPart2";
	leg.setupDone();
	body.add(leg);
	leg = new Bone(0, 32, 0, 0, 2);
	leg.name = "LeftHindleg";
	el = new Ellipse(4, 8, -16, 0, -1, 0, TRIANGLES8, huf2);
	leg.add(el);
	el.name = "LeftHindlegPart1";
	el = new Ellipse(4, 10, -15, 6, 0, 0, TRIANGLES8, white2);
	leg.add(el);
	el.name = "LeftHindlegPart2";
	leg.setupDone();
	body.add(leg);

	el = new Ellipse(22, 30, 0, -30, 0, 0, TRIANGLES8, white);
	el.name = "Rumpf";
	body.add(el);
	el = new Ellipse(20, 40, 0, 0, 0, 0, TRIANGLES8, white);
	el.name = "Rumpf";
	body.add(el);
	el = new Ellipse(22, 26, 0, 30, 0, 0, TRIANGLES8, white);
	el.name = "Rumpf";
	body.add(el);
	el = new Ellipse(8, 12, 10, -30, 0, 10, TRIANGLES8, brown2);
	el.name = "Fleck1";
	body.add(el);
	el = new Ellipse(10, 14, -4, 24, 0, -10, TRIANGLES8, brown2);
	el.name = "Fleck2";
	body.add(el);

	el = new Ellipse(10, 16, 0, 58, 0, 0, TRIANGLES8, brown2);
	el.name = "Tail";
	body.add(el);
	body.setupDone();

	var rest = new Bone(0, -24, 0, 0, 2);
	rest.name = "Rest";
	rest.add(head);

	el = new Ellipse(10, 32, 0, -28, -2, 0, TRIANGLES8, white);
	el.name = "Throat";
	rest.add(el);
	el = new Ellipse(32, 16, 2, -22, -2, 90, TRIANGLES10, brown2);
	el.name = "Maehne";
	el.setMaxEff(10);
	rest.add(el);
	el = new Ellipse(28, 10, -4, -26, -2, -90, TRIANGLES8, brown2);
	el.name = "Maehne";
	el.setMaxEff(10);
	rest.add(el);

	rest.setupDone();

	body.scaleRoot(1.0, 1.0);
	rest.scaleRoot(1.0, 1.0);

	var tap;
	tap = new CoordinateTap();
	this.setCoordinateTap(tap);

	var bc;
	bc = new BoundingCircle();
	bc.initBoundingCircle(24, 0, 30, 0);
	bc.setCoordinateTap(new CoordinateTap("mbc"));
	this.add(bc);
	bc.name = "bc";
	bc = new BoundingCircle();
	bc.initBoundingCircle(28, 0, -20, 0);
	bc.setCoordinateTap(new CoordinateTap("mbc"));
	this.add(bc);
	bc = new BoundingCircle();
	bc.initBoundingCircle(8, 0, -96, 0);
	bc.setCoordinateTap(new CoordinateTap("mbc"));
	this.add(bc);
	bc = new BoundingCircle();
	bc.initBoundingCircle(14, 0, -72, 0);
	bc.setCoordinateTap(new CoordinateTap("mbc"));
	this.add(bc);
	bc = new BoundingCircle();
	bc.initBoundingCircle(18, 0, -52, 0);
	bc.setCoordinateTap(new CoordinateTap("mbc"));

	this.add(bc);
	this.add(body);
	this.add(rest);

	//this.scaleRoot(1.2, 1.2);
	// this.setRoot(0, -24, 0, 0);
	this.setupDone();

	this.animation = new AnimalAnimation(this, 16, 1000);
	this.animation.startRun();

}

Horse.prototype = new Thing(7);

Horse.prototype.getType = function() {
	return MUSTANG;
};

Horse.prototype.animate= function() {
	this.animation.animateImpl();
};


