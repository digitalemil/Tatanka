function Arrow() {
	var brown = 0xff705125;
	var white = 0xffffffff;
	var stone = 0xff404040;

	this.name = "Arrow";
	var container= new Bone(0, 0, 0, 0, 1);
	container.name = "container";
	var arrow = new Bone(0, -17, 0, 0, 3);
	arrow.name = "arrow";
	//var p = new Triangle(0, 0, 0, -2, 8, 0, 0, 2, 8, 0, stone);
	//arrow.add(p);
	var p = new Rectangle();
	p.init(1, 20, 0, 18, 0, 0, brown);
	arrow.add(p);
	var p = new Triangle(0, 0, 0, -2, 29, -2, 34, 1, 28, 0, white);
	arrow.add(p);
	var p = new Triangle(0, 0, 0, 2, 29, 2, 34, -1, 28, 0, white);
	arrow.add(p);
	arrow.scaleRoot(2, 2);
	arrow.setupDone();
	container.add(arrow);
	container.setupDone();
	this.add(container);
	this.setupDone();
}

Arrow.prototype.getType = function() {
	return ARROW;
};

Arrow.prototype = new Thing(1);

function Sioux() {
	var darkbrown = 0xff190d01;
	var lightbrown = 0xffae9879;
	var lightbrown2 = 0xff997959;
	var stone = 0xff404040;
	var brown = 0xffcc8060;
	var brown2 = 0xff705125;
	var white = 0xffffffff;
	var black = 0xFF000000;
	var blue = 0xFF0000FF;
	var el;
	var bone;

	this.name = "Sioux";
	var head = new Bone(0, -0, 0, 0, 8);
	head.name = "Head";

	el = new Ellipse(14, 14, 0, 0, -3, 0, TRIANGLES12, black);
	el.name = "Schaedel";
	head.add(el);
	el = new Ellipse(6, 4, 0, 6, 0, -2, TRIANGLES8, blue);
	el.name = "";
	head.add(el);
	el = new Ellipse(10, 3, 8, 4, -1, -20, TRIANGLES8, white);
	el.name = "Feder1";
	head.add(el);
	head.setupDone();

	var legs = new Bone(0, 0, 0, 0, 4);
	el = new Ellipse(5, 8, 24, -31, 0, 15, TRIANGLES8, lightbrown2);
	el.name = "RechterMokasin";
	legs.add(el);
	el = new Ellipse(8, 22, 14, -8, 0, 30, TRIANGLES10, lightbrown);
	el.name = "RechtesBein";
	legs.add(el);
	el = new Ellipse(5, 8, -24, -31, 0, -15, TRIANGLES8, lightbrown2);
	el.name = "LinkerMokasin";
	legs.add(el);
	el = new Ellipse(8, 22, -14, -8, 0, -30, TRIANGLES10, lightbrown);
	el.name = "LinkesBein";
	legs.add(el);
	legs.setupDone();

	var body = new Bone(0, 0, 0, 0, 8);
	body.name = "Body";
	el = new Ellipse(12, 6, 8, 12, 0, -20, TRIANGLES10, lightbrown);
	body.add(el);
	el = new Ellipse(6, 3, 12, 12, 0, -20, TRIANGLES8, white);
	body.add(el);
	el = new Ellipse(2, 4, -14, -10, 0, 20, TRIANGLES8, darkbrown);
	el.name = "Messer";
	body.add(el);

	el = new Ellipse(22, 12, 0, 0, 0, 0, TRIANGLES10, brown);
	el.name = "Rumpf";
	body.add(el);

	bone = new Bone(-22, 0, 0, 10, 3);
	bone.name = "ArmLeft";
	el = new Ellipse(5, 16, 0, -10, 0, 0, TRIANGLES10, brown);
	el.name = "ArmLeft2";
	bone.add(el);
	el = new Ellipse(4, 6, 0, -26, 0, 0, TRIANGLES10, brown);
	el.name = "HandLeft";
	bone.add(el);
	var handbone = new Bone(0, -30, 0, 0, 3);
	handbone.name = "Bow";
	el = new Ellipse(1, 1, 0, 15, 0, 0, TRIANGLES8, white);
	el.name = "Fibre";
	handbone.add(el);
	el = new Ellipse(2, 8, 0, 8, 0, 0, TRIANGLES8, darkbrown);
	el.name = "Bow1";
	handbone.add(el);

	var arrow = new Bone(0, -34, 0, 0, 5);
	arrow.name = "Arrow";
	var p = new Triangle(0, 0, 0, -2, 8, 0, 0, 2, 8, 0, stone);
	p.name = "arrhead";
	arrow.add(p);
	p = new Rectangle();
	p.init(1, 20, 0, 18, 0, 0, brown2);
	p.name = "arrbody";
	arrow.add(p);
	p = new Triangle(0, 0, 0, -2, 29, -2, 34, 1, 28, 0, white);
	p.name = "arrend1";
	arrow.add(p);
	p = new Triangle(0, 0, 0, 2, 29, 2, 34, -1, 28, 0, white);
	p.name = "arrend2";
	arrow.add(p);
	p= new BoundingCircle();
	p.initBoundingCircle(2, 0, 2, 0);
	arrow.add(p);
	

	arrow.visible = false;
	arrow.rsx = 2.0;
	arrow.rsy = 2.0;
	arrow.setupDone();

	handbone.add(arrow);
	handbone.setupDone();

	bone.add(handbone);

	bone.setupDone();
	body.add(bone);

	bone = new Bone(22, 0, 0, 20, 2);
	bone.name = "ArmRight";
	el = new Ellipse(5, 16, 0, -10, 0, 0, TRIANGLES10, brown);
	el.name = "ArmRight2";
	bone.add(el);
	el = new Ellipse(4, 6, 0, -26, 0, 0, TRIANGLES10, brown);
	el.name = "HandRight";
	bone.add(el);
	bone.setupDone();
	body.add(bone);

	el = new Ellipse(8, 8, -18, 0, 0, 0, TRIANGLES10, brown);
	el.name = "ShoulderLeft";
	body.add(el);
	el = new Ellipse(8, 8, 18, 0, 0, 0, TRIANGLES10, brown);
	el.name = "ShoulderRight";
	body.add(el);

	body.setupDone();

	var rest = new Bone(0, 0, 0, 0, 2);
	rest.name = "rest";
	rest.add(body);
	rest.add(head);
	rest.setupDone();

	this.add(legs);
	this.add(rest);
	this.setupDone();
}

Sioux.prototype.getType = function() {
	return SIOUX;
};

Sioux.prototype = new Thing(3);

function Tatanka() {
	var darkbrown = 0xff190d01;
	var lightbrown = 0xffae9879;
	var brown = 0xff3a270c;
	var horn = 0xff666666;
	var huf = 0xff000000;
	var red = 0xff9f0609;
	var schnauze = 0xff000000;
	var el;

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
	bc= new BoundingCircle();
	bc.initBoundingCircle(15, 22, -2, 0);
	this.add(bc);
	bc= new BoundingCircle();
	bc.initBoundingCircle(15, -22, -2, 0);
	this.add(bc);
	bc= new BoundingCircle();
	bc.initBoundingCircle(34, 0, 38, 0);
	this.add(bc);
	bc= new BoundingCircle();
	bc.initBoundingCircle(36, 0, -30, 0);
	this.add(bc);
	bc= new BoundingCircle();
	bc.initBoundingCircle(28, 0, -86, 0);
	this.add(bc);
	
	this.add(body);
	this.add(rest);
	
	
	
	this.setupDone();
}

Tatanka.prototype.getType = function() {
	return TATANKA;
};

Tatanka.prototype = new Thing(7);

function Horse() {
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
	var bc;
	bc= new BoundingCircle();
	bc.initBoundingCircle(24, 0, 30, 0);
	this.add(bc);
	bc= new BoundingCircle();
	bc.initBoundingCircle(28, 0, -20, 0);
	this.add(bc);
	bc= new BoundingCircle();
	bc.initBoundingCircle(8, 0, -96, 0);
	this.add(bc);
	bc= new BoundingCircle();
	bc.initBoundingCircle(14, 0, -72, 0);
	this.add(bc);
	bc= new BoundingCircle();
	bc.initBoundingCircle(18, 0, -52, 0);
	this.add(bc);
	this.add(body);
	this.add(rest);
	
	
	// this.scaleRoot(0.9, 0.9);
	// this.setRoot(0, -24, 0, 0);
	this.setupDone();

}

Horse.prototype.getType = function() {
	return HORSE;
};

Horse.prototype = new Thing(7);
