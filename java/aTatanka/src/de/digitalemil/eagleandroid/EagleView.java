package de.digitalemil.eagleandroid;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import de.digitalemil.eagle.BoundingCircle;
import de.digitalemil.eagle.Types;
import de.digitalemil.tatanka.TatankaModell;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
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
	private long start, frames;
	private String fps = "";
	int[] xa = new int[128];
	int[] ya = new int[128];
	private final static int RECTCOORDS = 6;
	private Paint paint, textPaint;
	private Path path;

	public EagleView(Context c) {
		super(c);
		this.context = c;
		setOnTouchListener(this);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setAntiAlias(true);
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		textPaint.setAntiAlias(true);
		path = new Path();
		path.setFillType(Path.FillType.EVEN_ODD);

		start = System.currentTimeMillis();
		getHolder().addCallback(this);
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public void draw(Canvas canvas) {
		// Log.v("TantankaView", "View Draw: " + width + " " + height);

		for (int i = 0; i < modell.getNumberOfThings(); i++) {
			if (modell.getThings()[i] == null)
				continue;
			if ((modell.getType(i) == Types.QUAD || modell.getType(i) == Types.TEXQUAD)
					&& (modell.getTexNameFromQuad(i) != null || modell
							.getTexID(i) != 0)) {
				int t = modell.getTexID(i);
				if (modell.texNameChanged(i)) {
					if (modell.isTexIDSet(i)) {
						textures.remove(new Integer(t));
					}
					if (modell.getTexNameFromQuad(i) == null) {
						modell.setTexIDForQuad(i, 0);
						continue;
					}
					t = loadTexture(modell.getTexNameFromQuad(i), width, height);
					modell.setTexIDForQuad(i, t);
				}
			}
		}
		if (modell.skipFrame()) {
			return;
		}
		frames++;
		canvas.drawRGB(0xCC, 0x9C, 0x5a);
		paint(canvas);
	}

	public void paint(Canvas canvas) {
		for (int t = 0; t < modell.getNumberOfThings(); t++) {
			if (!modell.isVisible(t))
				continue;

			if ((modell.getType(t) == Types.QUAD || modell.getType(t) == Types.TEXQUAD)
					&& (modell.getTexNameFromQuad(t) != null || modell
							.getTexID(t) != 0)) {
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
					paint.setColor((int) d[di + 2]);
					int cor = (int) d[di + 3];
					if (type == Types.BOUNDINGCIRCLE) {
						di++;
					}
					path.moveTo(d[di + 4 + 2 * cor] + w2, d[di + 4 + 2 * cor
							+ 1]
							+ h2);

					for (int i = 2; i < 2 * ni; i += 2) {
						path.lineTo(d[di + 4 + i + 2 * cor] + w2, d[di + 4 + i
								+ 2 * cor + 1]
								+ h2);
					}
					path.close();
					canvas.drawPath(path, paint);
					path.reset();
					di += 4 + 2 * (ni + 1);
				}

			}
		}
		// g1.setColor(Color.WHITE);

		// g1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		long now = System.currentTimeMillis();
		if (now - start > 1000) {
			int ifps = (int) (((1000.0f * frames) / (now - start)));
			fps = "fps: " + ifps;
			start = now;
			frames = 0;
			if (ifps < 40 && delay > 5)
				delay -= 5;
		}
		canvas.drawText(fps, 16, 16, textPaint);
		// g1.drawString(fps, 20, 20);
		// g.drawImage(img, 0, 0, null);
	}

	public int loadTexture(String name, int w, int h) {
		Bitmap tex = loadTextureBitmap(name);
		if (tex == null)
			return 0;
		int ret = new Integer(++maxtex).intValue();
		if ((name.contains("gras") || name.contains("brush"))) {
			int bgd = Math.max(w, h);
			System.out.println("Scaled Image: " + bgd);
			tex = Bitmap.createScaledBitmap(tex, bgd + 1, bgd + 1, true);
		}
		textures.put(new Integer(ret), tex);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		running = true;
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
		SurfaceHolder holder = getHolder();
		while (running) {

			try {
				Thread.currentThread().sleep(delay);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			Canvas canvas = getHolder().lockCanvas();

			if (canvas == null)
				continue;

			if (width == -1) {
				width = canvas.getWidth();
				w2 = width / 2;
				height = canvas.getHeight();
				h2 = height / 2;
				modell = new TatankaModell();
				modell.start();
			}

			try {
				synchronized (lock) {
					draw(canvas);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (canvas != null) {
					holder.unlockCanvasAndPost(canvas);
				}
			}

			modell.update(System.currentTimeMillis());

		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int eventaction = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (eventaction) {
		case MotionEvent.ACTION_DOWN:
			modell.touchStart(x, y);
			break;
		case MotionEvent.ACTION_MOVE:
			modell.touch(x, y);
			break;
		case MotionEvent.ACTION_UP:
			modell.touchStop(x, y);
			break;
		}
		return true;
	}
}
