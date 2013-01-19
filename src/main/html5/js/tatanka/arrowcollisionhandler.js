function ArrowCollisionHandler(t, s, e) {
	this.setup(t, s, e);
}

ArrowCollisionHandler.prototype = new CollisionHandler();

ArrowCollisionHandler.prototype.canCollide = function(thing) {
	return (thing.getType() == TATANKA);
};

ArrowCollisionHandler.prototype.handleCollision = function(tatanka) {
	var narr = tatanka.getByName("arrow" + tatanka.hits);
	var arrow= this.me;
	narr.setVisibility(true);
	var phi = calcPhi(tatanka.rot + tatanka.rrot);
	var sinphi = mysin[phi];
	var cosphi = mycos[phi];
	// ax = ((arrow.rx+ arrow.x)/(tatanka.sx*tatanka.rsx) - tatanka.x -
	// tatanka.rx);
	// ay = ((arrow.ry+ arrow.y)/(tatanka.sy*tatanka.rsy) - tatanka.y -
	// tatanka.ry)
	ax = ((arrow.rx + arrow.x) - tatanka.x - tatanka.rx) / tatanka.rsx
			/ tatanka.sx;
	ay = ((arrow.ry + arrow.y) - tatanka.y - tatanka.ry) / tatanka.rsy
			/ tatanka.sy;

	dx = (ax * cosphi - ay * sinphi);
	dy = (ay * cosphi + ax * sinphi);
	narr.getByName("stone").translateRoot(20000, 20000, 0);
	narr.translate(-narr.x - narr.rx + dx, -narr.y - narr.ry + dy, 0);
	narr.rrot = -tatanka.rot - tatanka.rrot + arrow.rot + arrow.rrot;
	tatanka.hit();

	arrow.setVisibility(false);
	arrow.getBCs()[0].getCoordinateTap().reset();
};

