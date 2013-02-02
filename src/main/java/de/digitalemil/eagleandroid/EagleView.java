package de.digitalemil.eagleandroid;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import de.digitalemil.eagle.BoundingCircle;
import de.digitalemil.eagle.Globals;
import de.digitalemil.eagle.Screen;
import de.digitalemil.eagle.Text;
import de.digitalemil.eagle.Types;
import de.digitalemil.tatanka.TatankaModell;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.GetChars;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class EagleView extends SurfaceView implements SurfaceHolder.Callback,
		Runnable, OnTouchListener {
	private boolean running = false;
	private Thread thread;
	private Object lock = new Object();
	private int delay = 20;
	private TatankaModell modell;
	private int width = -1, height = -1, w2, h2, maxtex = 0;
	private Context context;
	private HashMap textures = new HashMap();
	private HashMap texNames = new HashMap();
	private HashMap texUsage = new HashMap();

	int[] xa = new int[128];
	int[] ya = new int[128];
	private final static int RECTCOORDS = 6;
	private Paint paint, textPaint;
	private Path path;
	private final static boolean ANTIALIASING = true;

	public EagleView(Context c) {
		super(c);
		this.context = c;
		setOnTouchListener(this);
		if (ANTIALIASING) {
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			paint.setAntiAlias(true);
			textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			textPaint.setAntiAlias(true);
		} else {
			paint = new Paint();
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			paint.setAntiAlias(false);
			textPaint = new Paint();
			textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			textPaint.setAntiAlias(false);
		}
		path = new Path();
		path.setFillType(Path.FillType.EVEN_ODD);

		getHolder().addCallback(this);

	}

	public void draw(Canvas canvas) {
		 Log.v("TantankaView", "View Draw: " + width + " " + height);
		  int s= modell.moveToOtherScreen();
	       if(s!= Screen.STAYONSCREEN) {
	           for(int i= 1; i< textures.size(); i++) {
	        	   textures.remove(i);
	        	   texNames.remove(i);
	        	   texUsage.remove(i);
	           } 
	           if (Screen.getActiveScreen() != null) {
	               Screen.getActiveScreen().deactivate();
	               
	           }
	           Screen.getScreen(s).activate();
	    //       NSLog(@"Parts after activate %i\n", Part::parts);
	  //         NSLog(@"Animations after activate %i\n", PartAnimation::animations);
//	     NSLog(@"Things after activate %i\n", modell->getNumberOfThings());
	           
	       }

		for (int i = 0; i < modell.getNumberOfThings(); i++) {
			if (modell.getThings()[i] == null)
				continue;
			if ((modell.getType(i) == Types.IMAGE)
					&& (modell.getImageName(i) != null || modell.getTexID(i) != 0)) {
				int t = modell.getTexID(i);
				if (modell.imageNameChanged(i)) {
					if (modell.isTexIDSet(i)) {
						int n= (Integer)texUsage.get(t);
						n--;
						texUsage.put(t, n);
						if(((Integer)texUsage.get(t))== 0) {
							textures.remove(t);
							texNames.remove(t);
							texUsage.remove(t);
						}
					}
					if (modell.getImageName(i) == null) {
						modell.setTexIDForQuad(i, 0);
						continue;
					}
					t = loadTexture(modell.getImageName(i), modell.getImageWidth(i), modell.getImageHeight(i));
					modell.setTexIDForQuad(i, t);
				}
			}
		}
		if (modell.skipFrame()) {
			return;
		}
		canvas.drawRGB(0xCC, 0x9C, 0x5a);
		paint(canvas);
	}

	public void paint(Canvas canvas) {
		for (int t = 0; t < modell.getNumberOfThings(); t++) {
			if (!modell.isVisible(t))
				continue;

			if ((modell.getType(t) == Types.IMAGE)
					&& (modell.getImageName(t) != null || modell.getTexID(t) != 0)) {
				Bitmap timg = (Bitmap) textures.get(new Integer(modell
						.getTexID(t)));
				if (timg == null)
					continue;
				float[] d = modell.getData(t);

				float xmin = min(d[RECTCOORDS], d[RECTCOORDS + 2],
						d[RECTCOORDS + 4], d[RECTCOORDS + 6]);
				float ymin = min(d[RECTCOORDS + 1], d[RECTCOORDS + 3],
						d[RECTCOORDS + 5], d[RECTCOORDS + 7]);

				canvas.drawBitmap(timg, (int) (xmin + w2), (int) (h2 + ymin),
						null);

			} else {
				String[] textandfont = modell.getTextAndFont(t);
				float[] d = modell.getData(t);
				int texts = 0;

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
					paint.setColor((int) d[di + 2]);
					int cor = (int) d[di + 3];
					if (type == Types.BOUNDINGCIRCLE) {
						di++;
					}
					if (type == Types.TEXT) {
						Typeface tf= paint.getTypeface();
						paint.setTypeface(Typeface.create(textandfont[texts + 1], Typeface.BOLD));
						float ts= paint.getTextSize();
						paint.setTextSize(Math.round(d[di + 6]));
						Rect fm= new Rect();
						paint.getTextBounds(textandfont[texts], 0, textandfont[texts].length(), fm);
						int mw = fm.width();
						int mh = fm.height();
						int x = (int) (d[di + 4] + Globals.getW2());
						switch ((int) d[di + 7]) {
						case Text.TEXT_RIGHT:
							x -= mw;
							break;
						case Text.TEXT_CENTER:
							x -= mw / 2;
							break;
						}
						
						canvas.drawText(textandfont[texts], x, d[di + 5]
								+ (int) Globals.getH2() + mh / 2, paint);
						paint.setTextSize(ts);
						paint.setTypeface(tf);
						texts += 2;
						di += 8;
					} else {
						path.moveTo(d[di + 4 + 2 * cor] + w2, d[di + 4 + 2
								* cor + 1]
								+ h2);

						for (int i = 2; i < 2 * ni; i += 2) {
							path.lineTo(d[di + 4 + i + 2 * cor] + w2, d[di + 4
									+ i + 2 * cor + 1]
									+ h2);
						}
						path.close();
						canvas.drawPath(path, paint);
						path.reset();
						di += 4 + 2 * (ni + 1);
					}
				}

			}
		}

		canvas.drawText("fps: " + modell.getFps(), 16, 16, textPaint);
	}

	public int loadTexture(String name, int w, int h) {
		Log.v("loadTexture: ", name+ " "+w+" "+h);
		for (int i = 1; i <= textures.size(); i++) {
			Bitmap img = (Bitmap) textures.get(new Integer(i));
			if (name.equals(texNames.get(i)) && w == img.getWidth()
					&& h == img.getHeight()) {
				int n= (Integer)texUsage.get(i);
				n++;
				texUsage.put(i, n);
				return i;
			}
		}
		Bitmap tex = loadTextureBitmap(name);
		if (tex == null)
			return 0;
		int ret = new Integer(++maxtex).intValue();
		tex = Bitmap.createScaledBitmap(tex, w, h, true);
		textures.put(ret, tex);
		texNames.put(ret, name);
		texUsage.put(ret, 1);
		return ret;
	}

	private Bitmap loadTextureBitmap(String name) {
		 Log.v("loading texture: ", name);
		Bitmap bitmap;
		int id = context.getResources().getIdentifier(
				name.substring(0, name.indexOf('.')), "drawable",
				"de.digitalemil.eagleandroid");

		Log.v("TEXTURES",
				" id: " + id + " name: " + name.substring(0, name.indexOf('.')));

		InputStream is = context.getResources().openRawResource(id);
		try {
			bitmap = BitmapFactory.decodeStream(is);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		go();
		// TODO Auto-generated method stub

	}

	public void go() {
		 Log.v("TantankaView", "go");
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		holder.setFormat(PixelFormat.RGBA_8888);
		go();
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		running = false;
		// TODO Auto-generated method stub

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

	@Override
	public void run() {
		long dur = 0;
		SurfaceHolder holder = getHolder();
		while (running) {
			try {
				Thread.currentThread().sleep(delay);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			Canvas canvas = null;
			try {
				synchronized (lock) {
					canvas = holder.lockCanvas();

					if (canvas == null)
						continue;

					if (width == -1) {
						 Log.v("TantankaView", "setup");
							
						width = canvas.getWidth();
						w2 = width / 2;
						height = canvas.getHeight();
						h2 = height / 2;
						Globals.setDefaults(768, 1024);
						Globals.set(width, height);
						modell = new TatankaModell();
						modell.setup();
						modell.start();
					}
					long now = System.currentTimeMillis();
					modell.update(now);
					draw(canvas);
					dur = System.currentTimeMillis() - now;

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (canvas != null) {
					holder.unlockCanvasAndPost(canvas);
				}
			}
			delay = (int) (39 - dur);
			if (delay < 0)
				delay = 0;
			// System.out.println("delay: "+delay+" "+dur);
			// delay= 20;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int eventaction = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();
		synchronized (lock) {
			switch (eventaction) {
			case MotionEvent.ACTION_POINTER_2_DOWN:
				modell.touchStart((int) event.getX(1), (int) event.getY(1));
				break;
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				modell.touchStart(x, y);
				break;
			case MotionEvent.ACTION_MOVE:
				modell.touch(x, y);
				break;
			case MotionEvent.ACTION_UP:
				modell.touchStop(x, y);
				break;
			}
		}

		return true;
	}
}
