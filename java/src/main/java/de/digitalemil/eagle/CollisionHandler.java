package de.digitalemil.eagle;

public interface CollisionHandler {


	public boolean canCollide(Thing thing);

	public boolean handleCollision(Thing thing);

	public boolean checkCollision();

	public int collisionHappend();
	
	public Thing getOther();
	
	public void clearCollision();
}