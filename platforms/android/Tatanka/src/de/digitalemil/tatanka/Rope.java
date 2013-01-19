package de.digitalemil.tatanka;

import de.digitalemil.eagle.*;

public class Rope extends Thing {

	public Rope() {
		super(1);

		int lightbrown = 0xffae9879;

		this.name = "Rope";
		Bone container = new Bone(0, 0, 0, 0, 1);
		container.setName("container");

		Part p = new Rectangle(2, 40, 0, -20, 0, 0, lightbrown);
		container.addPart(p);
		Loop loop = new Loop(40, 32, 36, 28, 0, -60, 0, 0, Ellipse.TRIANGLES20,
				lightbrown);
		loop.setName("Loop");
		container.addPart(loop);
		container.setupDone();
		addPart(container);
		setupDone();
	}

	public int getType() {
		return TatankaTypes.ROPE;
	};

}