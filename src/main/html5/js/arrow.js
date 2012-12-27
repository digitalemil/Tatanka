
function Arrow() {
	this.thinginit(2);
	this.coords = new CoordinateTap("Arrow");

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
	this.bccoords = new CoordinateTap("BCArrow");
	p.setCoordinateTap(this.bccoords);
	arrow.add(p);
	arrow.scaleRoot(2, 2);

	arrow.setupDone();
	this.add(arrow);
	this.setupDone();

	this.ani = new PartAnimation();
}

Arrow.prototype = new Thing(1);

Arrow.prototype.getType = function() {
	return ARROW;
};
