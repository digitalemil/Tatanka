function Joystick() {
	/*
	 * this.thinginit(2); var gray1 = 0x40000000; var gray2 = 0x80000000;
	 * this.setName("Joystick"); this.add(new Ellipse(32, 32, 0, 0, -3, 0,
	 * TRIANGLES20, gray1)); this.add(new Ellipse(12, 12, 0, 0, -3, 0,
	 * TRIANGLES20, gray2)); this.setupDone();
	 */
}

Joystick.prototype = new ThingContainer(2);

function ArrowCollisionHandler(t, s, e) {
	this.setup(t, s, e);
}

ArrowCollisionHandler.prototype = new CollisionHandler();

ArrowCollisionHandler.prototype.canCollide = function(thing) {
	return (thing.getType()== TATANKA);
};

ArrowCollisionHandler.prototype.handleCollision= function(arrow, tatanka) {
	var narr = tatanka.getByName("arrow" + tatanka.hits);
	narr.setVisibility(true);
	var phi = calcMyPhi(tatanka.rot+ tatanka.rrot);
	var sinphi = mysin[phi];
	var cosphi = mycos[phi];
//	ax = ((arrow.rx+ arrow.x)/(tatanka.sx*tatanka.rsx) - tatanka.x - tatanka.rx);
//	ay = ((arrow.ry+ arrow.y)/(tatanka.sy*tatanka.rsy) - tatanka.y - tatanka.ry)
	ax = ((arrow.rx+arrow.x) - tatanka.x - tatanka.rx)/tatanka.rsx/tatanka.sx;
	ay = ((arrow.ry+arrow.y) - tatanka.y - tatanka.ry)/tatanka.rsy/tatanka.sy;

	dx = (ax * cosphi - ay * sinphi);
	dy = (ay * cosphi + ax * sinphi);
	narr.getByName("stone").translateRoot(20000, 20000, 0);
	narr.translate(-narr.x -narr.rx + dx, -narr.y- narr.ry + dy, 0);
	LOG("Hit: "+dx+" "+dy);
	FlushLog();
	narr.rrot = -tatanka.rot - tatanka.rrot + arrow.rot + arrow.rrot;
	tatanka.hit();

	arrow.setVisibility(false);
	arrow.getBCs()[0].getCoordinateTap().reset();
};

function TatankaHerd(nanimals) {
	this.__setup(nanimals);
	for ( var i = 0; i < nanimals; i++) {
		this.things[i] = new Tatanka();
		this.things[i].translateRoot(i * 50 * scale * (-4 + getRandom(0, 8)),
				50 * scale * (-1 + getRandom(0, 2)), 0);
		this.things[i].rotateRoot(-20 + getRandom(0, 40));
		//this.things[i].rotateRoot(90);
		var s = 0.98 + getRandom(0, 4) / 100;
		this.things[i].scaleRoot(s, s);
		this.things[i].setLayer(1);
		this.things[i].setName("Tatanka" + i);
	}
	this.tatanka = this.things[0];
	this.scaleRoot(0.6 * scale, 0.6 * scale);
}

TatankaHerd.prototype = new ThingContainer(0);

TatankaHerd.prototype.update = function() {
	for ( var i = 0; i < this.n; i++) {
		//this.things[i].rotate(3);
		this.things[i].animation.animateImpl();
	}
};

function Prairie(canvaswidth, canvasheight, imgname) {
	this.__setup(4);
	this.dim = Math.max(canvaswidth, canvasheight) + 1;
	var x = 0;
	var y = 0;
	for ( var i = 0; i < this.n; i++) {
		if (i == 2)
			x++;
		if (i == 1 || i == 3)
			y = 1;
		else
			y = 0;
		this.things[i] = new Quad();
		this.things[i].setName("Prairie:" + i);
		this.things[i].quadinit(this.dim, this.dim, 0);
		this.things[i].translate(this.dim * x, this.dim * y);
	}
	this.things[0].setTexName(imgname); // "valleygras512.jpg"
}

Prairie.prototype = new ThingContainer(0);

Prairie.prototype.update = function(speedx, speedy) {
	var isset = this.things[0].isTexIDSet();
	if (isset && !this.things[1].isTexIDSet()) {
		for ( var i = 1; i < this.n; i++) {
			this.things[i].setTexID(this.things[0].getTexID());
		}
	}
	this.translate(speedx, speedy);

	for ( var i = 0; i < 4; i++) {
		if (this.things[i].y > this.dim)
			this.things[i].y -= 2 * this.dim;
		if (this.things[i].y < -this.dim)
			this.things[i].y += 2 * this.dim;
		if (this.things[i].x > this.dim)
			this.things[i].x -= 2 * this.dim;
		if (this.things[i].x < -this.dim)
			this.things[i].x += 2 * this.dim;
	}
};

function MountedLakota(s, sx, sy) {
	this.__setup(3);
	this.things[0] = new Horse();
	this.mustang = this.things[0];
	this.mustang.setLayer(200);
	this.things[1] = new Sioux();
	this.lakota = this.things[1];
	this.lakota.setLayer(201);
	this.things[2] = new Arrow();
	this.arrow = this.things[2];
	this.arrow.setLayer(401);
	this.arrow.setVisibility(false);
	this.arrow.setName("Arrow");
	//this.arrow.translateRoot(0, -26, 0);
	this.lakota.setFlyingArrow(this.arrow);

	this.translateRoot(sx * s, sy * s, 0);
	this.scaleRoot(s * 0.6, s * 0.6);
}

MountedLakota.prototype = new ThingContainer(0);

MountedLakota.prototype.shoot = function(x, y) {
	this.lakota.shoot(x, y);
};

MountedLakota.prototype.update = function() {
	this.lakota.shootingani.animateImpl();
	this.lakota.getFlyingArrow().ani.animateNow();
	this.mustang.animation.animateImpl();
};

function Arrow() {
	this.thinginit(2);
	this.coords = new CoordCopy("Arrow");

	this.setCoordinateTap(this.coords);

	var brown = 0xff705125;
	var white = 0xffffffff;
	var stone = 0xff404040;
	
	this.name = "Arrow";
	var arrow = new Bone(0, -8, 0, 0, 5);
	arrow.name = "arrow";
	var p = new Triangle(0, 0, 0, -2, 8, 0, 0, 2, 8, 0, stone);
	p.name = "stone";
	arrow.add(p);
	var p = new Rectangle();
	p.init(1, 20, 0, 18, 0, 0, brown);
	arrow.add(p);
	var p = new Triangle(0, 0, 0, -2, 29, -2, 34, 1, 28, 0, white);
	arrow.add(p);
	var p = new Triangle(0, 0, 0, 2, 29, 2, 34, -1, 28, 0, white);
	arrow.add(p);
	p = new BoundingCircle();
	p.setName("BC");
	p.initBoundingCircle(1, 0, 8, 0);
	this.bccoords = new CoordCopy("BCArrow");
	p.setCoordinateTap(this.bccoords);
	arrow.add(p);
	arrow.scaleRoot(2, 2);

	arrow.setupDone();
	this.add(arrow);
	this.setupDone();
	
	this.ani= new PartAnimation();
}

Arrow.prototype = new Thing(1);

Arrow.prototype.getType = function() {
	return ARROW;
};

function Sioux() {
	this.thinginit(3);
	this.arrowCoords = new CoordCopy();

	this.arrow = new Arrow();
	this.arrow.translateRoot(0, 0, 0);
	this.arrow.visible = false;
	this.arrow.getByName("BC").setCoordinateTap(null);

	var darkbrown = 0xff190d01;
	var lightbrown = 0xffae9879;
	var lightbrown2 = 0xff997959;
	var brown = 0xffcc8060;
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

	this.body = new Bone(0, 0, 0, 0, 8);
	this.body.setName("Body");
	el = new Ellipse(12, 6, 8, 12, 0, -20, TRIANGLES10, lightbrown);
	this.body.add(el);
	el = new Ellipse(6, 3, 12, 12, 0, -20, TRIANGLES8, white);
	this.body.add(el);
	el = new Ellipse(2, 4, -14, -10, 0, 20, TRIANGLES8, darkbrown);
	el.name = "Messer";
	this.body.add(el);

	el = new Ellipse(22, 12, 0, 0, 0, 0, TRIANGLES10, brown);
	el.name = "Rumpf";
	this.body.add(el);

	this.leftarm = new Bone(-22, 0, 0, 10, 3);
	this.leftarm.setName("ArmLeft");
	el = new Ellipse(5, 16, 0, -10, 0, 0, TRIANGLES10, brown);
	el.name = "ArmLeft2";
	this.leftarm.add(el);
	el = new Ellipse(4, 6, 0, -26, 0, 0, TRIANGLES10, brown);
	el.name = "HandLeft";
	this.leftarm.add(el);
	this.bow = new Bone(0, -30, 0, 0, 3);
	this.bow.setName("Bow");
	this.fibre = new Ellipse(1, 1, 0, 15, 0, 0, TRIANGLES8, white);
	this.fibre.setName("Fibre");
	this.bow.add(this.fibre);
	el = new Ellipse(2, 8, 0, 8, 0, 0, TRIANGLES8, darkbrown);
	el.name = "Bow1";
	this.bow.add(el);
	this.bow.setCoordinateTap(new CoordCopy());

	bone= new Bone(0, -26, 0, 0, 1);
	bone.add(this.arrow);
	bone.setupDone();
	this.bow.add(bone);
	this.bow.setupDone();

	this.leftarm.add(this.bow);

	this.leftarm.setupDone();
	this.body.add(this.leftarm);

	this.rightarm = new Bone(22, 0, 0, 20, 2);
	this.rightarm.setName("ArmRight");
	el = new Ellipse(5, 16, 0, -10, 0, 0, TRIANGLES10, brown);
	el.name = "ArmRight2";
	this.rightarm.add(el);
	el = new Ellipse(4, 6, 0, -26, 0, 0, TRIANGLES10, brown);
	el.name = "HandRight";
	this.rightarm.add(el);
	this.rightarm.setupDone();
	this.body.add(this.rightarm);

	el = new Ellipse(8, 8, -18, 0, 0, 0, TRIANGLES10, brown);
	el.name = "ShoulderLeft";
	this.body.add(el);
	el = new Ellipse(8, 8, 18, 0, 0, 0, TRIANGLES10, brown);
	el.name = "ShoulderRight";
	this.body.add(el);

	this.body.setupDone();

	this.upperparts = new Bone(0, 0, 0, 0, 2);
	this.upperparts.setName("rest");
	this.upperparts.add(this.body);
	this.upperparts.add(head);

	this.upperparts.setupDone();

	this.add(legs);
	this.add(this.upperparts);

	this.setupDone();

	this.shootingani = new LakotaAnimation(this);
}

Sioux.prototype = new Thing(3);

Sioux.prototype.setFlyingArrow = function(a) {
	this.flyingArrow = a;
};

Sioux.prototype.getFlyingArrow = function() {
	return this.flyingArrow;
};

Sioux.prototype.getType = function() {
	return SIOUX;
};

Sioux.prototype.shoot = function(shootAtX, shootAtY) {
	this.shootingani.shoot(shootAtX, shootAtY);
	activelakota++;
	if (activelakota >= nlakotas)
		activelakota = 0;
};

function Tatanka() {
	this.thinginit(12);
	this.coords = new CoordCopy();
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
	bc.setCoordinateTap(new CoordCopy());
	this.add(bc);
	bc = new BoundingCircle();
	bc.initBoundingCircle(16, -20, -2, 0);
	bc.setCoordinateTap(new CoordCopy());
	this.add(bc);
	bc = new BoundingCircle();
	bc.initBoundingCircle(35, 0, 38, 0);
	bc.setCoordinateTap(new CoordCopy());
	this.add(bc);
	bc = new BoundingCircle();
	bc.initBoundingCircle(35, 0, -30, 0);
	bc.setCoordinateTap(new CoordCopy());
	this.add(bc);
	bc = new BoundingCircle();
	bc.initBoundingCircle(28, 0, -85, 0);
	bc.setCoordinateTap(new CoordCopy());
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
	
	this.tatankahealth= 100;
	this.speed= 0;
}

Tatanka.prototype = new Thing(12);

Tatanka.prototype.hit= function(part) {
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
	tap= new CoordCopy();
	this.setCoordinateTap(tap);
	
	var bc;
	bc = new BoundingCircle();
	bc.initBoundingCircle(24, 0, 30, 0);
	bc.setCoordinateTap(new CoordCopy());
	this.add(bc);
	bc.name = "bc";
	bc = new BoundingCircle();
	bc.initBoundingCircle(28, 0, -20, 0);
	bc.setCoordinateTap(new CoordCopy());
	this.add(bc);
	bc = new BoundingCircle();
	bc.initBoundingCircle(8, 0, -96, 0);
	bc.setCoordinateTap(new CoordCopy());
	this.add(bc);
	bc = new BoundingCircle();
	bc.initBoundingCircle(14, 0, -72, 0);
	bc.setCoordinateTap(new CoordCopy());
	this.add(bc);
	bc = new BoundingCircle();
	bc.initBoundingCircle(18, 0, -52, 0);
	bc.setCoordinateTap(new CoordCopy());

	this.add(bc);
	this.add(body);
	this.add(rest);

	this.scaleRoot(1.2, 1.2);
	// this.setRoot(0, -24, 0, 0);
	this.setupDone();

	this.animation = new AnimalAnimation(this, 16, 1000);
	this.animation.startRun();

}

Horse.prototype = new Thing(7);

Horse.prototype.getType = function() {
	return HORSE;
};

function Rope() {
	var lightbrown = 0xffae9879;

	this.name = "Rope";
	var container = new Bone(0, 0, 0, 0, 1);
	container.name = "container";

	var p = new Rectangle();
	p.init(2, 40, 0, -20, 0, 0, lightbrown);
	container.add(p);
	var el = new Loop(40, 32, 36, 28, 0, -60, 0, 0, TRIANGLES20, lightbrown);
	el.name = "Loop";
	container.add(el);

	container.setupDone();
	this.add(container);
	this.setupDone();
}

Rope.prototype.getType = function() {
	return ROPE;
};

Rope.prototype = new Thing(1);

function SiouxWithRope() {
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

	var rope = new Rope();
	rope.translate(12, -30, 0);
	this.add(rope);

	this.setupDone();
}

SiouxWithRope.prototype.getType = function() {
	return SIOUXWITHROPE;
};

SiouxWithRope.prototype = new Thing(3);
