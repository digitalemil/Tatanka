package de.digitalemil.tatanka;

import de.digitalemil.eagle.*;
import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

public class Mustang extends Thing {
	AnimalAnimation animation;

	@MethodDefinitionChangerAnnotation({"BY", "animation=null", "delete animation" })
	protected void finalize() throws Throwable {
		animation= null;
	}
	
	public Mustang() {
		super(7);
		int brown = 0xff3a270c;
		int brown2 = 0xff705125;
		int huf2 = 0xff404040;
		int white = 0xffffffff;
		int white2 = 0xffE0E0E0;

		

		this.setName("Mustang");
		Bone head = new Bone(0, -42, 16, 0, 7);
		head.setName("Head");

		Ellipse el = new Ellipse(8, 12, 0, -20, -3, 0, Ellipse.TRIANGLES8, white);
		el.setName("h1");
		head.addPart(el);
		el = new Ellipse(8, 12, 0, -34, 0, -2, Ellipse.TRIANGLES8, white);
		el.setName("h2");
		head.addPart(el);
		el = new Ellipse(2, 3, -4, -40, -1, 20, Ellipse.TRIANGLES8, brown);
		el.setName("NuesterLinks");
		head.addPart(el);
		el = new Ellipse(2, 3, 4, -40, -1, -20, Ellipse.TRIANGLES8, brown);
		el.setName("NuesterRechts");
		head.addPart(el);
		el = new Ellipse(12, 12, 0, -10, -1, 0, Ellipse.TRIANGLES8, white);
		el.setName("Schaedel");
		head.addPart(el);
		el = new Ellipse(3, 5, -8, -10, 0, -15, Ellipse.TRIANGLES8, white2);
		el.setName("LinkesOhr");
		head.addPart(el);
		el = new Ellipse(3, 5, 8, -10, 0, 15, Ellipse.TRIANGLES8, white2);
		el.setName("RechtesOhr");
		head.addPart(el);

		head.setupDone();
		head.rotate(0);

		Bone body = new Bone(0, 0, 0, 0, 10);
		body.setName("Body");

		Bone leg = new Bone(0, -46, 0, 0, 2);
		leg.setName("LeftForeleg");
		el = new Ellipse(4, 8, -16, -6, -1, 0, Ellipse.TRIANGLES8, huf2);
		leg.addPart(el);
		el.setName("LeftForelegPart1");
		el = new Ellipse(4, 10, -15, 0, 0, 0, Ellipse.TRIANGLES8, white2);
		leg.addPart(el);
		el.setName("LeftForelegPart2");
		leg.setupDone();
		body.addPart(leg);
		leg = new Bone(0, -46, 0, 0, 2);
		leg.setName("RightForeleg");
		el = new Ellipse(4, 8, 16, -6, -1, 0, Ellipse.TRIANGLES8, huf2);
		leg.addPart(el);
		el.setName("RightForelegPart1");
		el = new Ellipse(4, 10, 15, -0, 0, 0, Ellipse.TRIANGLES8, white2);
		leg.addPart(el);
		el.setName("RightForelegPart2");
		leg.setupDone();
		body.addPart(leg);

		leg = new Bone(0, 32, 0, 0, 2);
		leg.setName("RightHindleg");
		el = new Ellipse(4, 8, 16, 0, -1, 0, Ellipse.TRIANGLES8, huf2);
		leg.addPart(el);
		el.setName("RightHindlegPart1");
		el = new Ellipse(4, 10, 15, 6, 0, 0, Ellipse.TRIANGLES8, white2);
		leg.addPart(el);
		el.setName("RightHindlegPart2");
		leg.setupDone();
		body.addPart(leg);
		leg = new Bone(0, 32, 0, 0, 2);
		leg.setName("LeftHindleg");
		el = new Ellipse(4, 8, -16, 0, -1, 0, Ellipse.TRIANGLES8, huf2);
		leg.addPart(el);
		el.setName("LeftHindlegPart1");
		el = new Ellipse(4, 10, -15, 6, 0, 0, Ellipse.TRIANGLES8, white2);
		leg.addPart(el);
		el.setName("LeftHindlegPart2");
		leg.setupDone();
		body.addPart(leg);

		el = new Ellipse(22, 30, 0, -30, 0, 0, Ellipse.TRIANGLES8, white);
		el.setName("Rumpf");
		body.addPart(el);
		el = new Ellipse(20, 40, 0, 0, 0, 0, Ellipse.TRIANGLES8, white);
		el.setName("Rumpf");
		body.addPart(el);
		el = new Ellipse(22, 26, 0, 30, 0, 0, Ellipse.TRIANGLES8, white);
		el.setName("Rumpf");
		body.addPart(el);
		el = new Ellipse(8, 12, 10, -30, 0, 10, Ellipse.TRIANGLES8, brown2);
		el.setName("Fleck1");
		body.addPart(el);
		el = new Ellipse(10, 14, -4, 24, 0, -10, Ellipse.TRIANGLES8, brown2);
		el.setName("Fleck2");
		body.addPart(el);

		el = new Ellipse(10, 16, 0, 58, 0, 0, Ellipse.TRIANGLES8, brown2);
		el.setName("Tail");
		body.addPart(el);
		body.setupDone();

		Bone rest = new Bone(0, -24, 0, 0, 4);
		rest.setName("Rest");
		rest.addPart(head);

		el = new Ellipse(10, 32, 0, -28, -2, 0, Ellipse.TRIANGLES8, white);
		el.setName("Throat");
		rest.addPart(el);
		el = new Ellipse(32, 16, 2, -22, -2, 90, Ellipse.TRIANGLES10, brown2);
		el.setName("Maehne");
		el.setMaxEff(10);
		rest.addPart(el);
		el = new Ellipse(28, 10, -4, -26, -2, -90, Ellipse.TRIANGLES8, brown2);
		el.setName("Maehne");
		el.setMaxEff(10);
		rest.addPart(el);

		rest.setupDone();

		body.scaleRoot(1.0f, 1.0f);
		rest.scaleRoot(1.0f, 1.0f);
		
		Part bc = new BoundingCircle(24, 0, 30, 0);
		bc.setCoordinateTap(new CoordinateTapImpl());
		this.addPart(bc);
		bc = new BoundingCircle(28, 0, -20, 0);
		bc.setCoordinateTap(new CoordinateTapImpl());
		this.addPart(bc);
		bc = new BoundingCircle(8, 0, -96, 0);
		bc.setCoordinateTap(new CoordinateTapImpl());
		this.addPart(bc);
		bc = new BoundingCircle(14, 0, -72, 0);
		bc.setCoordinateTap(new CoordinateTapImpl());
		this.addPart(bc);
		bc = new BoundingCircle(18, 0, -52, 0);
		bc.setCoordinateTap(new CoordinateTapImpl());
		this.addPart(bc);
		this.addPart(body);
		this.addPart(rest);

		// this.scaleRoot(0.9, 0.9);
		// this.setRoot(0, -24, 0, 0);
		this.setupDone();

		animation = new AnimalAnimation(this, 16, 1000);
		animation.startRun();

	}

	public void animate() {
		animation.animate();
	}

	public int getType() {
		return TatankaTypes.MUSTANG;
	}
}