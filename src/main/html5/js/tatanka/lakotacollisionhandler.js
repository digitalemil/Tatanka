function LakotaCollisionHandler() {
}

LakotaCollisionHandler.prototype = new CollisionHandler();

LakotaCollisionHandler.prototype.canCollide= function(thing) {
	if (!this.me.visible)
		return false;
	return (thing.getType() == TATANKA || thing.getType()== MUSTANG);
};