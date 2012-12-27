package de.digitalemil.tatanka;

import de.digitalemil.eagle.CollisionHandlerImpl;
import de.digitalemil.eagle.Thing;

public class LakotaCollisionHandler extends CollisionHandlerImpl {
	
	public LakotaCollisionHandler(Thing thing, Thing[] things, int s, int e) {
		super(thing, things, s, e);
	}
	
	public boolean canCollide(Thing thing) {
		if (!me.isVisible())
			return false;
		return (thing.getType() == TatankaTypes.TATANKA || thing.getType()== TatankaTypes.MUSTANG);
	}

}
