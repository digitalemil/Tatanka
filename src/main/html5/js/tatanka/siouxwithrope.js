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

