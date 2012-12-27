
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


