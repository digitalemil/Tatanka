package de.digitalemil.tatanka;

import de.digitalemil.eagle.*;
import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

public class ArrowCollisionHandler extends CollisionHandlerImpl {
	private static int n = 0;

	@MethodDefinitionChangerAnnotation({"SC", "arrow", "(Thing*)arrow" } )
	public ArrowCollisionHandler(Arrow arrow, Thing things[]) {
		super(arrow, things, 4, 1000);
	}

	public boolean canCollide(Thing thing) {
		if (!me.isVisible())
			return false;
		return (thing.getType() == TatankaTypes.TATANKA);
	}


	@MethodDefinitionChangerAnnotation({ "BY", "Arrow narr", "sprintf((char*)tmptextbuffer, (const char*)'arrow%i', tatanka->hits); Arrow  narr= (Arrow)tatanka.getByName(tmptextbuffer); //", "BY", "Arrow ", "Arrow*", "BY", "Tatanka t", "Tatanka *t", "BY", "Part.", "Part::" })
	public boolean handleCollision(Thing t) {
		System.out.println("HIT!!!");
		Tatanka tatanka = (Tatanka) t;
		Arrow arrow = (Arrow) me;
		Arrow narr = (Arrow) tatanka.getByName("arrow" + tatanka.hits);
		if (narr == null) {
			return false;
		}
		arrow.getArrowAnimation().stop();

		narr.setVisibility(true);
		int phi = Part.calcPhi(tatanka.getRotation() + tatanka.getRrot());
		float sinphi = Part.mysin[phi];
		float cosphi = Part.mycos[phi];

		float ax = ((arrow.getRx() + arrow.getX()) - tatanka.getX() - tatanka
				.getRx()) / tatanka.getRsx() / tatanka.getSx();
		float ay = ((arrow.getRy() + arrow.getY()) - tatanka.getY() - tatanka
				.getRy()) / tatanka.getRsy() / tatanka.getSy();

		float dx = (ax * cosphi - ay * sinphi);
		float dy = (ay * cosphi + ax * sinphi);
		narr.getByName("stone").translateRoot(20000, 20000, 0);
		narr.translate(-narr.getX() - narr.getRx() + dx,
				-narr.getY() - narr.getRy() + dy, 0);
		narr.setRootRotation(-tatanka.getRotation() - tatanka.getRrot()
				+ arrow.getRotation() + arrow.getRrot());
		tatanka.hit(0);

		arrow.setVisibility(false);
		arrow.getBCs()[0].getCoordinateTap().reset();
		return true;
	}

	public String toString() {
		return "ArrowCollisionHandler []";
	}

}