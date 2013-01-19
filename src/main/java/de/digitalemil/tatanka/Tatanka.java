package de.digitalemil.tatanka;

import de.digitalemil.eagle.*;

public class Tatanka extends Thing {
	public final static int MAXARROWS = 5, MAXHEALTH = 100;
	protected int health = MAXHEALTH, hits = 0;
	protected float speed;
	protected boolean didc= true;
	
	public boolean didCollide() {
		return didc;
	}

	public void setDidCollide(boolean didCollide) {
		this.didc = didCollide;
	}

	protected AnimalAnimation animation;

	public Tatanka(float scale) {
		super(12);
		setCoordinateTap(new CoordinateTapImpl());
		
		int darkbrown = 0xff190d01;
		int lightbrown = 0xffae9879;
		int brown = 0xff3a270c;
		int horn = 0xff666666;
		int huf = 0xff000000;
		int red = 0xff9f0609;
		int schnauze = 0xff000000;

		this.setName("Tatanka");
		Bone head = new Bone(0, -42, 16, 0, 8);
		head.setName("Head");

		Ellipse el = new Ellipse(1, 1, 0, -20, -3, 0, Ellipse.TRIANGLES8, red);
		el.setName("Tongue");
		head.addPart(el);
		el = new Ellipse(14, 12, 0, -34, 0, -2, Ellipse.TRIANGLES8, schnauze);
		el.setName("Schnauze");
		head.addPart(el);
		el = new Ellipse(14, 5, -32, -12, -1, 20, Ellipse.TRIANGLES8, horn);
		el.setName("LinkesHorn1");
		head.addPart(el);
		el = new Ellipse(4, 8, -41, -22, 0, 15, Ellipse.TRIANGLES8, horn);
		el.setName("LinkesHorn2");
		head.addPart(el);
		el = new Ellipse(14, 5, 32, -12, -1, -20, Ellipse.TRIANGLES8, horn);
		el.setName("RechtesHorn1");
		head.addPart(el);
		el = new Ellipse(4, 8, 41, -22, 0, -15, Ellipse.TRIANGLES8, horn);
		el.setName("RechtesHorn2");
		head.addPart(el);

		el = new Ellipse(28, 32, 0, -10, -1, 0, Ellipse.TRIANGLES10, darkbrown);
		el.setName("Schaedel");
		head.addPart(el);

		head.setupDone();
		head.rotate(0);

		Bone body = new Bone(0, 0, 0, 0, 9);
		body.setName("Body");

		Bone leg = new Bone(0, -40, 0, 0, 2);
		leg.setName("LeftForeleg");
		el = new Ellipse(8, 12, -32, -6, -1, 0, Ellipse.TRIANGLES8, huf);
		leg.addPart(el);
		el.setName("LeftForelegPart1");
		el = new Ellipse(8, 12, -30, 0, 0, 0, Ellipse.TRIANGLES8, lightbrown);
		leg.addPart(el);
		el.setName("LeftForelegPart2");
		leg.setupDone();
		body.addPart(leg);
		leg = new Bone(0, -40, 0, 0, 2);
		leg.setName("RightForeleg");
		el = new Ellipse(8, 12, 32, -6, -1, 0, Ellipse.TRIANGLES8, huf);
		leg.addPart(el);
		el.setName("RightForelegPart1");
		el = new Ellipse(8, 12, 30, -0, 0, 0, Ellipse.TRIANGLES8, lightbrown);
		leg.addPart(el);
		el.setName("RightForelegPart2");
		leg.setupDone();
		body.addPart(leg);

		leg = new Bone(0, 38, 0, 0, 2);
		leg.setName("RightHindleg");
		el = new Ellipse(8, 12, 28, 0, -1, 0, Ellipse.TRIANGLES8, darkbrown);
		leg.addPart(el);
		el.setName("RightHindlegPart1");
		el = new Ellipse(8, 12, 26, 6, 0, 0, Ellipse.TRIANGLES8, brown);
		leg.addPart(el);
		el.setName("RightHindlegPart2");
		leg.setupDone();
		body.addPart(leg);
		leg = new Bone(0, 38, 0, 0, 2);
		leg.setName("LeftHindleg");
		el = new Ellipse(8, 12, -28, 0, -1, 0, Ellipse.TRIANGLES8, darkbrown);
		leg.addPart(el);
		el.setName("LeftHindlegPart1");
		el = new Ellipse(8, 12, -26, 6, 0, 0, Ellipse.TRIANGLES8, brown);
		leg.addPart(el);
		el.setName("LeftHindlegPart2");
		leg.setupDone();
		body.addPart(leg);

		el = new Ellipse(36, 44, 0, -10, 0, 0, Ellipse.TRIANGLES10, brown);
		el.setName("Rumpf");
		body.addPart(el);
		el = new Ellipse(32, 44, 0, 32, 0, 0, Ellipse.TRIANGLES10, brown);
		el.setName("Rumpf");
		body.addPart(el);
		el = new Ellipse(20, 36, 0, -20, 0, 0, Ellipse.TRIANGLES10, darkbrown);
		el.setName("Rumpf");
		body.addPart(el);

		el = new Ellipse(6, 10, 0, 76, 0, 0, Ellipse.TRIANGLES8, darkbrown);
		el.setName("Tail");
		body.addPart(el);
		body.setupDone();

		Bone rest = new Bone(0, -30, 0, 0, 4);
		rest.setName("Rest");
		rest.addPart(head);
		el = new Ellipse(36, 22, 0, -12, -2, 0, Ellipse.TRIANGLES10, darkbrown);
		el.setName("Shoulders2");
		rest.addPart(el);
		el = new Ellipse(38, 16, 0, -12, -2, 0, Ellipse.TRIANGLES10, lightbrown);
		el.setName("Shoulders");
		rest.addPart(el);

		el = new Ellipse(16, 36, 0, -4, -2, 0, Ellipse.TRIANGLES10, lightbrown);
		el.setName("Throat");
		rest.addPart(el);
		el = new Ellipse(12, 50, 0, 60, -2, 0, Ellipse.TRIANGLES10, red);
		el.setName("RumpfLinie");
		body.addPart(el);

		rest.setupDone();

		Part bc = new BoundingCircle(15, 22, -2, 0);
		bc.setCoordinateTap(new CoordinateTapImpl());
		this.addPart(bc);
		bc = new BoundingCircle(15, -22, -2, 0);
		bc.setCoordinateTap(new CoordinateTapImpl());
		this.addPart(bc);
		bc = new BoundingCircle(34, 0, 38, 0);
		bc.setCoordinateTap(new CoordinateTapImpl());
		this.addPart(bc);
		bc = new BoundingCircle(36, 0, -30, 0);
		bc.setCoordinateTap(new CoordinateTapImpl());
		this.addPart(bc);
		bc = new BoundingCircle(28, 0, -86, 0);
		bc.setCoordinateTap(new CoordinateTapImpl());
		this.addPart(bc);

		this.addPart(body);
		this.addPart(rest);
		
		scaleRoot(scale, scale);

		for (int i = 0; i < MAXARROWS; i++) {
			Arrow arrow = new Arrow();
			arrow.setCoordinateTap(null);
			arrow.getByName("BC").setCoordinateTap(null);
			arrow.setName("arrow" + i);
			arrow.setVisibility(false);
			addPart(arrow);
		}
		
		animation = new AnimalAnimation(this, 16, 1200+(-200 + Part.getRandom(0, 400)));
		animation.startRun();

		health = 100;
		speed = 0;

		this.setupDone();
	}
	
	public void animate() {	
		if(animation!= null)
			animation.animate();
	}	
	
	public AnimalAnimation getAnimation() {
		return animation;
	}

	public void hit(int part) {
		hits++;
		if(hits>= MAXARROWS)
			return;
		if (part < 2) {
			health -= 80 + getRandom(0, 40);
		} else {
			health -= 30 + getRandom(0, 10);
		}
		if (health <= 0) {
			speed = 0;
			health = 100;
		}
	}

	@SearchAndReplaceAnnotation({ "BY", "Arrow arr", "Arrow *arr" })
	public void resetHealth() {
		for (int i = 0; i < this.MAXARROWS; i++) {
			Arrow arr = (Arrow)getByName("arrow" + i);
			arr.setCoordinateTap(null);
			arr.getByName("BC").setCoordinateTap(null);
			arr.translate(-arr.getX(), -arr.getY(), 0);
			arr.rotate(-arr.getRotation());
			arr.setVisibility(false);
		}
		hits = 0;
		health = 100;
	}

	public int getType() {
		return TatankaTypes.TATANKA;
	}
}