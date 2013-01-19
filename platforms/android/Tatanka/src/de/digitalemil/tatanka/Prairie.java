package de.digitalemil.tatanka;

import de.digitalemil.eagle.*;

public class Prairie extends ThingContainer {
	private int dim;
	int imgdim = 512, ntx, nty;

	public Prairie(String imgname) {
		ntx = Globals.getWidth() / imgdim + 2;
		nty = Globals.getHeight() / imgdim + 2;

		System.out.println("Tiles: " + ntx + " " + nty);
		init(ntx * nty);

		int nt = 0;
		for (int x = 0; x < ntx; x++) {
			for (int y = 0; y < nty; y++) {
				things[nt] = new ImageThing(imgname, imgdim, imgdim);
				things[nt].setName("Prairie:" + nt);
				things[nt].translate(
						-Math.abs((Globals.getWidth() - imgdim * ntx)) / 2 + x
								* imgdim + imgdim / 2 - Globals.getW2(),
						-Math.abs((Globals.getHeight() - imgdim * nty)) / 2 + y
								* imgdim + imgdim / 2 - Globals.getH2(), 0);
				if (y == 0)
					System.out.println(-Math.abs((Globals.getWidth() - imgdim
							* ntx))
							/ 2 + x * imgdim + imgdim / 2 - Globals.getW2());
				// things[i].setVisibility(false);
				nt++;
			}
		}
	}

	public void update(float speedx, float speedy) {
		// System.out.println("Prairie speed: "+speedx+ "  "+speedy);
		translate(speedx * 2, speedy * 2, 0);

		for (int i = 0; i < n; i++) {
			if (things[i].getX() > Globals.getWidth() / 2 + imgdim / 2)
				things[i].translate(-ntx * imgdim, 0, 0);
			if (things[i].getX() < -(Globals.getWidth() / 2 + imgdim / 2))
				things[i].translate(ntx * imgdim, 0, 0);
			if (things[i].getY() > Globals.getHeight() / 2 + imgdim / 2)
				things[i].translate(0, -nty * imgdim, 0);
			if (things[i].getY() < -(Globals.getHeight() / 2 + imgdim / 2))
				things[i].translate(0, nty * imgdim, 0);
		}
	}
}