package de.digitalemil.eaglerefimpl;

import java.awt.Graphics;
import java.util.Arrays;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import java.util.*;

import de.digitalemil.eagle.*;
import de.digitalemil.tatanka.TatankaModell;

public class UIComponent extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean done = false;
	private Image img;
	public int WIDTH = 768, HEIGHT = 1024;
	private static final Object lock = new Object();
	private HashMap textures = new HashMap(), texNames = new HashMap();
	boolean wasSwipe = false;
	private int startX = 0, startY = 0, maxtex = 1;
	Modell modell;

	public UIComponent(int w, int h) {
		HEIGHT = h;
		WIDTH = w;
	}

	public void paint(Graphics g) {
		synchronized (lock) {
			if (modell == null)
				generate();

			if (img == null) {
				img = createImage(WIDTH, HEIGHT);
				Graphics2D g1 = (Graphics2D) img.getGraphics();
				g1.setColor(Color.BLACK);
				g1.fillRect(0, 0, WIDTH, HEIGHT);
			}
			update();
			int s= modell.moveToOtherScreen();
			if(s!= Screen.STAYONSCREEN) {
					texNames= new HashMap();
					textures= new HashMap();
					maxtex= 1;
				modell.showScreen(s);
				
			}
			for (int i = 0; i < modell.getNumberOfThings(); i++) {
				if (modell.getThings()[i] == null)
					continue;
				if ((modell.getType(i) == Types.IMAGE)
						&& (modell.getImageName(i) != null || modell
								.getTexID(i) != 0)) {
					int t = modell.getTexID(i);
					if (modell.imageNameChanged(i)) {
						if (modell.isTexIDSet(i)) {
							// System.out.println("remove: "+t+ " "+textures);
							textures.remove(new Integer(t));
						}
						if (modell.getImageName(i) == null) {
							// System.out.println("setTexQuadID to 0");
							modell.setTexIDForQuad(i, 0);
							continue;
						}
						t = loadTexture(modell.getImageName(i),
								this.modell.getImageWidth(i),
								this.modell.getImageHeight(i));
						modell.setTexIDForQuad(i, t);
						System.out.println("setTexQuadID to t:" + t);
					}
				}
			}
			if (modell.skipFrame()) {
				g.drawImage(img, 0, 0, null);
				// super.paint(g);
				return;
			}

			paintData(g);
		}
	}

	public synchronized void paintData(Graphics g) {
		Graphics2D g1 = (Graphics2D) img.getGraphics();
		g1.setColor(new Color(modell.getBackgroudColor(), true));
		g1.fillRect(0, 0, WIDTH, HEIGHT);
		
		for (int t = 0; t < modell.getNumberOfThings(); t++) {
			if (!modell.isVisible(t))
				continue;

			if ((modell.getType(t) == Types.IMAGE)
					&& (modell.getImageName(t) != null || modell.getTexID(t) != 0)) {

				int tex = modell.getTexID(t);
				Image timg = (Image) textures.get(new Integer(tex));
				if (timg == null)
					continue;
				float[] d = modell.getData(t);
				int i = 6;
				// System.out.println(d[i]+" "+ d[i + 2]+" "+ d[i + 4]+" "+ d[i
				// + 6]);
				// System.out.println(d[i+1]+" "+ d[i + 3]+" "+ d[i + 5]+" "+
				// d[i + 7]);

				float xmin = min(d[i], d[i + 2], d[i + 4], d[i + 6]);
				float ymin = min(d[i + 1], d[i + 3], d[i + 5], d[i + 7]);
				//System.out.println("img: "+texNames.get(new Integer(tex)));
				g1.drawImage(timg, (int) (xmin + WIDTH / 2),
						(int) (HEIGHT / 2 + ymin), null);

			} else {
				String[] textandfont= modell.getTextAndFont(t);
				float[] d = modell.getData(t);
				int texts= 0;
				int di = 0;
				int len = modell.getNumberOfData(t);
				while (di < len) {
					int type = (int) d[di];
					if (type == Types.BOUNDINGCIRCLE
							&& !BoundingCircle.visibleboundingcircle) {
						di += 7;
						continue;
					}

					int ni = (int) d[di + 1];
					Color color = new Color((int) d[di + 2], true);
					int cor = (int) d[di + 3];
					if (type == Types.BOUNDINGCIRCLE) {
						di++;
					}
					if (type == Types.TEXT) {
						Font font= new Font(textandfont[texts+1], Font.BOLD, Math.round(d[di + 6]));
						g1.setColor(color);
						g1.setFont(font);
						
						FontMetrics fm= g1.getFontMetrics();
						
						int mw = fm.stringWidth(textandfont[texts]);
						int mh = fm.stringWidth("m");
						int x = (int)(d[di + 4] + Globals.getW2());
						switch ((int)d[di + 7]) {
						case Text.TEXT_RIGHT:
							x -= mw;
							break;
						case Text.TEXT_CENTER:
							x -= mw / 2;
							break;
						}
						g1.drawString(textandfont[texts], x, d[di + 5] + (int)Globals.getH2()
								+ mh / 2);
						texts += 2;
						di += 8;
					} else {
						int[] xa = new int[ni];
						int[] ya = new int[ni];
						for (int i = 0; i < 2 * ni; i += 2) {
							xa[i / 2] = (int) (d[di + 4 + i + 2 * cor] + WIDTH / 2);
							ya[i / 2] = (int) (d[di + 4 + i + 2 * cor + 1] + HEIGHT / 2);
						}
						g1.setColor(color);
						g1.fillPolygon(xa, ya, ni);
						di += 4 + 2 * (ni + 1);
					}
				}

			}
		}
		g1.setColor(Color.WHITE);

		g1.setFont(new Font("Times New Roman", Font.BOLD, 12));

		g1.drawString("fps: " + modell.getFps(), 20, 20);
		g.drawImage(img, 0, 0, null);
	}

	void touch(int ix, int iy) {
		synchronized (lock) {
			modell.touch(ix, iy);
		}
	}

	void touchStart(int ix, int iy) {
		synchronized (lock) {
			wasSwipe = modell.touchStart(ix, iy);
			startX = ix;
			startY = iy;
		}
	}

	void touchStop(int ix, int iy) {
		synchronized (lock) {
			modell.touchStop(ix, iy);
		}
	}

	public void key(int k) {
		// System.out.println("Key "+k);
		synchronized (lock) {
			if (k == KeyEvent.VK_LEFT) {
				modell.keyPressed(2);
			}
			if (k == KeyEvent.VK_RIGHT) {
				modell.keyPressed(3);
			}
			if (k == KeyEvent.VK_UP) {
				modell.keyPressed(0);
			}
			if (k == KeyEvent.VK_DOWN) {
				modell.keyPressed(1);
			}
			if (k == KeyEvent.VK_SPACE) {
				modell.keyPressed(4);
			}
			if (k == 47) {
				modell.zoom(-1);
			}
			if (k == 93) {
				modell.zoom(+1);
			}

		}
	}

	private void update() {

		synchronized (lock) {
			modell.update(System.currentTimeMillis());
		}
	}

	public void generate() {

		synchronized (lock) {
			System.out.println("setup UIComponent: " + WIDTH + " " + HEIGHT);
			Globals.setDefaults(768, 1024);
			Globals.set(WIDTH, HEIGHT);
			modell = new TatankaModell();
			modell.setup();
			modell.start();
			setSize(WIDTH, HEIGHT);
		}
	}

	public int loadTexture(String name, int w, int h) {
		for (int i = 1; i <= textures.size(); i++) {
			Image img = (Image) textures.get(new Integer(i));
			if(img== null)
				continue;
			if (name.equals(texNames.get(i)) && w == img.getWidth(null)
					&& h == img.getHeight(null))
				return i;
		}
		Image tex = loadImage(name, w, h);
		if (tex == null)
			return 0;
		int ret = new Integer(++maxtex).intValue();
		textures.put(new Integer(ret), tex);
		texNames.put(new Integer(ret), name);
		return ret;
	}

	public Dimension getPreferredSize() {
		return new Dimension(480, 320);
	}

	public float min(float f1, float f2, float f3, float f4) {
		float ret = f1;
		ret = Math.min(ret, f2);
		ret = Math.min(ret, f3);
		ret = Math.min(ret, f4);
		return ret;
	}

	public float max(float f1, float f2, float f3, float f4) {
		float ret = f1;
		ret = Math.max(ret, f2);
		ret = Math.max(ret, f3);
		ret = Math.max(ret, f4);
		return ret;
	}

	public Image loadImage(String name, int w, int h) {
		System.out.println("loadImage: " + name+" "+w+" "+h);
		Image image = null;
		InputStream bais = null;
		try {
			bais = getClass().getResourceAsStream("/" + name);

			byte[] imgdata = new byte[0];
			int actual;
			do {
				int len = bais.available();
				byte[] data = new byte[len];
				actual = bais.read(data, 0, len);
				if (actual <= 0) {
					continue;
				}
				byte[] newimgdata = new byte[actual + imgdata.length];
				for (int i = 0; i < imgdata.length; i++) {
					newimgdata[i] = imgdata[i];
				}
				for (int i = imgdata.length; i < imgdata.length + actual; i++) {
					newimgdata[i] = data[i - imgdata.length];
				}
				imgdata = newimgdata;
			} while (actual > 0);
			image = Toolkit.getDefaultToolkit().createImage(imgdata);
			MediaTracker mt = new MediaTracker(new Label(""));
			mt.addImage(image, 0);
			try {
				mt.waitForAll();
			} catch (Exception e) {
				System.err.println(name + ": " + e);
			}
			bais.close();
		} catch (Exception e) {
			System.err.println("Exception loading: " + name);
			e.printStackTrace();
		} finally {
			try {
				if (bais != null) {
					bais.close();
				}
			} catch (IOException e1) {
			}
		}
		Image ret = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return ret;
	}

}
