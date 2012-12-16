package de.digitalemil.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import java.util.HashMap;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Timer;

import de.digitalemil.tatanka.TatankaModell;
import de.digitalemil.eagle.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EagleGoogle implements EntryPoint {
	static final String holderId = "canvasholder";
	static final String upgradeMessage = "Your browser does not support the HTML5 Canvas. Please upgrade your browser to view this demo.";
	Canvas canvas;
	int mouseX, mouseY;
	static final int refreshRate = 25;
	int h2, w2;
	final CssColor redrawColor = CssColor.make("rgba(255,255,255,0.6)");
	Context2d context;
	Modell modell;
	boolean mouseDown = false;
	private final static int RECTCOORDS = 6;
	private GTexture tex;

	private HashMap textures = new HashMap();
	private int maxtex = 0, frames;
	long start;
	private int swidth, sheight;
	private boolean fullscreen = false;

	public native String getNyatiRotation()/*-{
		return $wnd.nyatiRotation;
	}-*/;

	public native void alert(String alrt)/*-{
		$wnd.alert(alrt);
	}-*/;

	public native void log(String alrt)/*-{
		console.log(alrt);
	}-*/;

	public native String fullScreen()/*-{
		return $wnd.nyatiFullScreen;
	}-*/;

	public native String getScreenWidth()/*-{
		return $wnd.screen.width;
	}-*/;

	public native String getScreenHeight()/*-{
		return $wnd.screen.height;
	}-*/;

	public native String getWidth()/*-{
		return $wnd.innerWidth;
	}-*/;

	public native String getHeight()/*-{
		return $wnd.innerHeight;
	}-*/;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		canvas = Canvas.createIfSupported();
		if (canvas == null) {
			RootPanel.get(holderId).add(new Label(upgradeMessage));
			return;
		}
		try {
			String d = getWidth();
			swidth = new Integer(d).intValue();
			d = getHeight();
			sheight = new Integer(d).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// swidth= 480;
		// sheight= 640;
		w2 = swidth / 2;
		h2 = sheight / 2;
		log("width:" + swidth + " height: " + sheight);
		canvas.setWidth(swidth + "px");
		canvas.setHeight(sheight + "px");
		canvas.setCoordinateSpaceWidth(swidth);
		canvas.setCoordinateSpaceHeight(sheight);

		RootPanel.get(holderId).add(canvas, 10, 10);
		context = canvas.getContext2d();
		initHandlers();

		Globals.setDefaults(768, 1024);
		Globals.set(swidth, sheight);
		modell = new TatankaModell();
		modell.start();

		final Timer timer = new Timer() {
			@Override
			public void run() {
				doUpdate();
			}
		};
		timer.scheduleRepeating(25);
	}

	void touch(int ix, int iy) {
		if (!mouseDown)
			return;
		modell.touch(ix, iy);
	}

	void touchStart(int ix, int iy) {
		modell.touchStart(ix, iy);
	}

	void touchStop(int ix, int iy) {
		modell.touchStop(ix, iy);
	}

	public int loadTexture(String name) {
		GTexture tex = new GTexture("res/" + name);
		int ret = new Integer(++maxtex).intValue();
		textures.put(new Integer(ret), tex);
		System.out.println("Loading texture: " + name + " " + ret);

		return ret;
	}

	void doUpdate() {
		paint();
	}

	void initHandlers() {
		canvas.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				mouseX = event.getRelativeX(canvas.getElement());
				mouseY = event.getRelativeY(canvas.getElement());
			}
		});

		canvas.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				mouseX = event.getX();
				mouseY = event.getY();
				mouseDown = false;
				touchStop(mouseX, mouseY);
			}
		});

		canvas.addTouchStartHandler(new TouchStartHandler() {
			public void onTouchStart(TouchStartEvent event) {
				event.preventDefault();
				if (event.getTouches().length() > 0) {
					Touch touch = event.getTouches().get(0);
					mouseX = event.getTouches().get(0).getScreenX();
					mouseY = event.getTouches().get(0).getScreenY();
					mouseDown = true;
					// alert("x: " + mouseX + " y: " + mouseY);
					touchStart(mouseX, mouseY);
				}
				event.preventDefault();
			}
		});

		canvas.addTouchMoveHandler(new TouchMoveHandler() {
			public void onTouchMove(TouchMoveEvent event) {
				event.preventDefault();
				if (event.getTouches().length() > 0) {
					Touch touch = event.getTouches().get(0);
					mouseX = event.getTouches().get(0).getScreenX();
					mouseY = event.getTouches().get(0).getScreenY();

					touch(mouseX, mouseY);
				}
				event.preventDefault();
			}
		});

		canvas.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				mouseX = event.getX(); // getClientX();
				mouseY = event.getY();

				mouseDown = true;
				// alert("x: " + mouseX + " y: " + mouseY);
				touchStart(mouseX, mouseY);
			}
		});

		canvas.addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				mouseX = event.getX();
				mouseY = event.getY();

				touch(mouseX, mouseY);
			}
		});

		canvas.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				mouseX = event.getX();
				mouseY = event.getY();

				mouseDown = false;
				touchStop(mouseX, mouseY);
			}
		});

		canvas.addTouchEndHandler(new TouchEndHandler() {
			public void onTouchEnd(TouchEndEvent event) {
				event.preventDefault();
				mouseX = event.getTouches().get(0).getScreenX();
				mouseY = event.getTouches().get(0).getScreenY();

				mouseDown = false;
				touchStop(mouseX, mouseY);
			}
		});

		canvas.addGestureStartHandler(new GestureStartHandler() {
			public void onGestureStart(GestureStartEvent event) {
				event.preventDefault();
			}
		});
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

	public void paintData() {
		for (int t = 0; t < modell.getNumberOfThings(); t++) {
			if (!modell.isVisible(t))
				continue;
			if ((modell.getType(t) == Types.QUAD || modell.getType(t) == Types.TEXQUAD)
					&& (modell.getTexNameFromQuad(t) != null || modell
							.getTexID(t) != 0)) {

				int tex = modell.getTexID(t);
				GTexture timg = (GTexture) textures.get(new Integer(tex));
				if (timg == null)
					continue;

				float[] d = modell.getData(t);

				float xmin = min(d[RECTCOORDS], d[RECTCOORDS + 2],
						d[RECTCOORDS + 4], d[RECTCOORDS + 6]);
				float ymin = min(d[RECTCOORDS + 1], d[RECTCOORDS + 3],
						d[RECTCOORDS + 5], d[RECTCOORDS + 7]);
			//	log(timg.getImageElement()+" "+(int) (xmin + w2)+" "+(int) (h2 + ymin)+" "+	(int)((Quad) modell.getThings()[t]).getWidth()+" "+(int)((Quad) modell.getThings()[t]).getHeight());
				context.drawImage(timg.getImageElement(), (int) (xmin + w2),
						(int) (h2 + ymin),
						(int)((Quad) modell.getThings()[t]).getWidth(),
						(int)((Quad) modell.getThings()[t]).getHeight());
			} else {

				float[] d = modell.getData(t);
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
					int c = (int) d[di + 2];
					int a = (c >>> 24);
					int r = ((c & 0x00FF0000) >> 16);
					int gr = ((c & 0x0000FF00) >> 8);
					int b = ((c & 0x000000FF));

					CssColor color = CssColor.make("rgba(" + r + ", " + gr
							+ ", " + b + ", " + (a / 256.0f) + ")");
					context.setFillStyle(color);
					int cor = (int) d[di + 3];
					if (type == Types.BOUNDINGCIRCLE) {
						di++;
					}
					context.beginPath();

					context.moveTo(d[di + 4 + 2 * cor] + w2, d[di + 4 + 2 * cor
							+ 1]
							+ h2);

					for (int i = 2; i < 2 * ni; i += 2) {
						context.lineTo(d[di + 4 + i + 2 * cor] + w2, d[di + 4
								+ i + 2 * cor + 1]
								+ h2);
					}
					context.closePath();
					context.fill();
					di += 4 + 2 * (ni + 1);
				}
			}
		}
		CssColor color = CssColor.make("rgba(0,0,0,1.0)");
		context.setFillStyle(color);
		context.strokeText("fps: " + modell.getFps(), 16, 16);

	}

	private int[] copyOfRange(int[] src, int start, int end, int delta) {
		int n = end - start;
		int[] ret = new int[n];
		for (int i = 0; i < n; i++) {
			ret[i] = src[start + i] + delta;
		}
		return ret;
	}

	public void paint() {
		CssColor color = CssColor.make("rgba(255,255,255,0.6)");

		if (start == 0) {
			start = System.currentTimeMillis();
		}
		modell.update((int) PartAnimation.currentTimeMillis());

		for (int i = 0; i < modell.getNumberOfThings(); i++) {
			// if(!modell.isVisible(i))
			// continue;
			if ((modell.getType(i) == Types.QUAD || modell.getType(i) == Types.TEXQUAD)
					&& (modell.getTexNameFromQuad(i) != null || modell
							.getTexID(i) != 0)) {
				int t = modell.getTexID(i);
				if (modell.texNameChanged(i)) {
					if (modell.isTexIDSet(i)) {
						System.out
								.println("remove (" + i + "): " + t + " "
										+ textures + " n:"
										+ modell.getNumberOfThings());
						textures.remove(new Integer(t));
					}
					if (modell.getTexNameFromQuad(i) == null) {
						System.out.println("setTexQuadID to 0");
						modell.setTexIDForQuad(i, 0);
						continue;
					}
					t = loadTexture(modell.getTexNameFromQuad(i));
					modell.setTexIDForQuad(i, t);
					System.out.println("setTexQuadID to t:" + t);
				}
			}
		}
		if (modell.skipFrame()) {
			System.out.println("skiping frame...");
			return;
		}

		frames++;
		paintData();
	}
}
