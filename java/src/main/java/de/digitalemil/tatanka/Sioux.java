package de.digitalemil.tatanka;

import de.digitalemil.eagle.*;

public class Sioux extends Thing {

	CoordinateTap arrowCoords;
	Arrow arrow, flyingArrow;
	Bone bow, leftarm, rightarm, upperparts, body;
	Part fibre;
	ShootingLakotaAnimation shootingAnimation;

	public ShootingLakotaAnimation getShootingAnimation() {
		return shootingAnimation;
	}

	public boolean shoot(int x, int y) {
		return shootingAnimation.shoot(x, y);
	}
	
	public Arrow getArrow() {
		return arrow;
	}

	public void setFibre(Part fibre) {
		this.fibre = fibre;
	}

	public Part getFibre() {
		return fibre;
	}

	public Sioux() {
		super(3);
		arrow = new Arrow();
		arrow.translateRoot(0, 0, 0);
		arrow.setVisibility(false);		
		arrow.getByName("BC").setCoordinateTap(null);
		arrow.setCoordinateTap(new CoordinateTapImpl());
		int darkbrown = 0xff190d01;
		int lightbrown = 0xffae9879;
		int lightbrown2 = 0xff997959;
		int stone = 0xff404040;
		int brown = 0xffcc8060;
		int brown2 = 0xff705125;
		int white = 0xffffffff;
		int black = 0xFF000000;
		int blue = 0xFF0000FF;
		Ellipse el;
		Bone bone;

		this.setName("Sioux");
		Bone head = new Bone(0.0f, 0.0f, 0.0f, 0.0f, 8);
		head.setName("Head");

		el = new Ellipse(14, 14, 0, 0, -3, 0, Ellipse.TRIANGLES12, black);
		el.setName("Schaedel");
		head.addPart(el);
		el = new Ellipse(6, 4, 0, 6, 0, -2, Ellipse.TRIANGLES8, blue);
		el.setName("");
		head.addPart(el);
		el = new Ellipse(10, 3, 8, 4, -1, -20, Ellipse.TRIANGLES8, white);
		el.setName("Feder1");
		head.addPart(el);
		head.setupDone();

		Bone legs = new Bone(0, 0, 0, 0, 4);
		el = new Ellipse(5, 8, 24, -31, 0, 15, Ellipse.TRIANGLES8, lightbrown2);
		el.setName("RechterMokasin");
		legs.addPart(el);
		el = new Ellipse(8, 22, 14, -8, 0, 30, Ellipse.TRIANGLES10, lightbrown);
		el.setName("RechtesBein");
		legs.addPart(el);
		el = new Ellipse(5, 8, -24, -31, 0, -15, Ellipse.TRIANGLES8,
				lightbrown2);
		el.setName("LinkerMokasin");
		legs.addPart(el);
		el = new Ellipse(8, 22, -14, -8, 0, -30, Ellipse.TRIANGLES10,
				lightbrown);
		el.setName("LinkesBein");
		legs.addPart(el);
		legs.setupDone();

		body = new Bone(0, 0, 0, 0, 8);
		body.setName("Body");
		el = new Ellipse(12, 6, 8, 12, 0, -20, Ellipse.TRIANGLES10, lightbrown);
		body.addPart(el);
		el = new Ellipse(6, 3, 12, 12, 0, -20, Ellipse.TRIANGLES8, white);
		body.addPart(el);
		el = new Ellipse(2, 4, -14, -10, 0, 20, Ellipse.TRIANGLES8, darkbrown);
		el.setName("Messer");
		body.addPart(el);

		el = new Ellipse(22, 12, 0, 0, 0, 0, Ellipse.TRIANGLES10, brown);
		el.setName("Rumpf");
		body.addPart(el);

		leftarm = new Bone(-22, 0, 0, 10, 3);
		leftarm.setName("ArmLeft");
		el = new Ellipse(5, 16, 0, -10, 0, 0, Ellipse.TRIANGLES10, brown);
		el.setName("ArmLeft2");
		leftarm.addPart(el);
		el = new Ellipse(4, 6, 0, -26, 0, 0, Ellipse.TRIANGLES10, brown);
		el.setName("HandLeft");
		leftarm.addPart(el);
		bow = new Bone(0, -30, 0, 0, 3);
		bow.setName("Bow");
		fibre = new Ellipse(1, 1, 0, 15, 0, 0, Ellipse.TRIANGLES8, white);
		fibre.setName("Fibre");
		bow.addPart(fibre);
		el = new Ellipse(2, 8, 0, 8, 0, 0, Ellipse.TRIANGLES8, darkbrown);
		el.setName("Bow1");
		bow.addPart(el);
		bow.setCoordinateTap(new CoordinateTapImpl());

		bone = new Bone(0, -26, 0, 0, 1);
		bone.addPart(arrow);
		bone.setupDone();
		
		bow.addPart(bone);
		bow.setupDone();

		leftarm.addPart(bow);
		leftarm.setupDone();
	
		body.addPart(leftarm);

		rightarm = new Bone(22, 0, 0, 20, 2);
		rightarm.setName("ArmRight");
		el = new Ellipse(5, 16, 0, -10, 0, 0, Ellipse.TRIANGLES10, brown);
		el.setName("ArmRight2");
		rightarm.addPart(el);
		el = new Ellipse(4, 6, 0, -26, 0, 0, Ellipse.TRIANGLES10, brown);
		el.setName("HandRight");
		rightarm.addPart(el);
		rightarm.setupDone();
		body.addPart(rightarm);

		el = new Ellipse(8, 8, -18, 0, 0, 0, Ellipse.TRIANGLES10, brown);
		el.setName("ShoulderLeft");
		body.addPart(el);
		el = new Ellipse(8, 8, 18, 0, 0, 0, Ellipse.TRIANGLES10, brown);
		el.setName("ShoulderRight");
		body.addPart(el);

		body.setupDone();

		upperparts = new Bone(0, 0, 0, 0, 2);
		upperparts.setName("rest");
		upperparts.addPart(body);
		upperparts.addPart(head);

		upperparts.setupDone();

		this.addPart(legs);
		this.addPart(upperparts);

		this.setupDone();
		
		shootingAnimation= new ShootingLakotaAnimation(this);
	}

	public Bone getBody() {
		return body;
	}

	public Bone getRightarm() {
		return rightarm;
	}

	public Bone getUpperparts() {
		return upperparts;
	}

	public Arrow getFlyingArrow() {
		return flyingArrow;
	}
	
	public void setFlyingArrow(Arrow a) {
		flyingArrow= a;
		flyingArrow.setCollisionHandler(new ArrowCollisionHandler(flyingArrow, Globals.getAllThings()));
	}
	
	public Bone getBow() {
		return bow;
	}

	public Bone getLeftarm() {
		return leftarm;
	}

	public int getType() {
		return TatankaTypes.SIOUX;
	}
}
