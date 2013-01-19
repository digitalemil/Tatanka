function Sioux() {
	this.thinginit(3);
	this.arrowCoords = new CoordinateTap();

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
	this.bow.setCoordinateTap(new CoordinateTap());

	bone = new Bone(0, -26, 0, 0, 1);
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
	var ch= new ArrowCollisionHandler();
	ch.setup(this.flyingArrow, allThings, 4, 1000);
	this.flyingArrow.setCollisionHandler(ch);	
};

Sioux.prototype.getFlyingArrow = function() {
	return this.flyingArrow;
};

Sioux.prototype.getType = function() {
	return SIOUX;
};

Sioux.prototype.shoot = function(shootAtX, shootAtY) {
	return this.shootingani.shoot(shootAtX, shootAtY);
};

