package de.digitalemil.tatanka;

import de.digitalemil.eagle.*;

public class Prairie extends ThingContainer {
	private int dim;

	public Prairie(String imgname) {
		dim = Math.max(Globals.getWidth(), Globals.getHeight());
		int nt = 2 * Math.round(0.5f + dim / 512.0f)+1;
		System.out.println("Tiles " + nt + " " + nt * nt);
		init(nt * nt);

		for (int x = -nt/2; x <= nt/2; x++) {
			for (int y = -nt/2; y <= nt/2; y++) {
				int p= (y+nt/2)*nt+x+nt/2;
				things[p] = new Quad(dim, dim, 0);
				things[p].setName("Prairie:" + p);
				things[p].translateRoot(dim * x, dim * y, 0);
				// things[i].setVisibility(false);
			}
		}
		((Quad) things[0]).setTexName(imgname); // "valleygras512.jpg"
	}

	public void update(float speedx, float speedy) {
		boolean isset = ((Quad) things[0]).isTexIDSet();
		if (isset && !((Quad) things[1]).isTexIDSet()) {
			for (int i = 1; i < n; i++) {
				// System.out.println("Prairie set Tex: "+((Quad)
				// things[0]).getTexID());
				((Quad) things[i]).setTexID(((Quad) things[0]).getTexID());
			}
		}
		translate(speedx, speedy, 0);

		for (int i = 0; i < n; i++) {
			if (things[i].getY() > dim/2)
				things[i].translate(0, - dim, 0);
			if (things[i].getY() < -dim/2)
				things[i].translate(0, + dim, 0);
			if (things[i].getX() > dim/2)
				things[i].translate(- dim, 0, 0);
			if (things[i].getX() < -dim)
				things[i].translate( dim, 0, 0);
		}

	}
}
