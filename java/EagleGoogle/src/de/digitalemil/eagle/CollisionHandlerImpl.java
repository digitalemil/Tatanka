package de.digitalemil.eagle;

import de.digitalemil.tatanka.TatankaTypes;

public class CollisionHandlerImpl implements CollisionHandler {
	public final static int NOCOLLISION= -1;
	public int collisionHappend;
	
	protected Thing me, other;
	protected Thing others[];
	protected int start, end;
	protected boolean enabled= true;
	
	
	public CollisionHandlerImpl(Thing thing, Thing[] things, int s, int e) {
		me= thing;
		start= s;
		end= e;
		others= things;
		clearCollision();
	} 

	public boolean isEnabled() {
		return enabled;
	}
	
	public void enable() {
		enabled= true;
	}
	
	public void disable() {
		enabled= false;
	}
	
	public boolean canCollide(Thing thing) {
		return true;
	}
	
	public boolean handleCollision(Thing thing) {
		return false;
	}

	public boolean checkCollision() {
		if (!enabled)
			return false;
		BoundingCircle mybcs[]= me.getBCs();

		for (int i = start; i < end; i++) {
			if (others[i] == null) {
				//System.out.println("Others== null: "+me+" "+i);
				return false;
			}
			if (!canCollide(others[i])  || me== others[i])
				continue;
			
			BoundingCircle bcs[] = others[i].getBCs();
			
			for ( int h = 0; h < bcs.length; h++) {
				BoundingCircle bbc = bcs[h];
				if(bbc== null || bbc.getCoordinateTap()== null)
					continue;
				
				float bx = bbc.getCoordinateTap().getX();
				float by = bbc.getCoordinateTap().getY();
				float br = bbc.getCoordinateTap().getR() * others[i].sx
						* others[i].rsx; // radius
				for ( int j = 0; j < mybcs.length; j++) {
					BoundingCircle abc = mybcs[j];
					if(abc.getCoordinateTap()==null)
						continue;
					float ax = abc.getCoordinateTap().getX();
					float ay = abc.getCoordinateTap().getY();
					float ar = abc.getCoordinateTap().getR() * me.sx * me.rsx; // radius
						
					if ((bx - ax) * (bx - ax) + (by - ay) * (by - ay) <= (br + ar)
							* (br + ar)) {	
						other= others[i];		
						collisionHappend= other.getType();
						return handleCollision(others[i]);
					}
				}
			}

		}
		return false;
	}
	
	public void clearCollision() {
		collisionHappend= NOCOLLISION;
		other= null;
	}

	public int collisionHappend() {
		return collisionHappend;
	}
	
	public Thing getOther() {
		return other;
	}
}