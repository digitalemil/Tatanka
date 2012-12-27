package de.digitalemil.tatanka;

import de.digitalemil.eagle.*;

public class Arrow extends Thing {
	PartAnimation arrowAnimation;

	public Arrow() {
		super(1);
		int brown = 0xff705125;
		int white = 0xffffffff;
		int stone = 0xff404040;

		setName("Arrow");
		Bone arrow = new Bone(0, -17, 0, 0, 5);
		arrow.setName("arrow");
		Part p = new Triangle(0, 0, 0, -2, 8, 0, 0, 2, 8, 0, stone);
		p.setName("stone");
		arrow.addPart(p);
		p = new Rectangle(1, 20, 0, 18, 0, 0, brown);
		arrow.addPart(p);
		p = new Triangle(0, 0, 0, -2, 29, -2, 34, 1, 28, 0, white);
		arrow.addPart(p);
		p = new Triangle(0, 0, 0, 2, 29, 2, 34, -1, 28, 0, white);
		arrow.addPart(p);

		p = new BoundingCircle(1, 0, 8, 0);
		p.setName("BC");
		p.setCoordinateTap(new CoordinateTapImpl("BCArrow"));
		arrow.addPart(p);

		arrow.scaleRoot(2, 2);
		arrow.setupDone();
		addPart(arrow);
		setupDone();

		arrowAnimation = new PartAnimation();
	}

	public PartAnimation getArrowAnimation() {
		return arrowAnimation;
	}

	public int getType() {
		return TatankaTypes.ARROW;
	}

	public void setRootRotation(float f) {
		invalidateData();
		rrot = f;
	}
	
	public float[] getThingData() {
	//	System.out.println("A: "+this+" "+isVisible());
		return super.getThingData();
	}
	
	public void setVisible(boolean visible) {
		super.setVisibility(visible);
		
		translate(-getX()+10000.0f, -getY(), 0);
	}
	
	/*
	public void beginTX() {
		super.beginTX();
		System.err.println(this);
		new Exception().printStackTrace();
	}*/
}
