package de.digitalemil.eagle;

public class PartAnimation implements Animation {
	protected boolean coloranim = false;
	protected int fca, sduration, duration, rduration;
	protected boolean _loops = false, running = false;
	protected Part part;
	protected float translationX, translationY, scaleX, scaleY, rotation;
	protected float lsx, lsy, lx, ly, lr;
	protected int lca, delta;
	protected long _start;

	public PartAnimation() {
	}
	
	public PartAnimation(Part p, float tx, float ty, float tr, float tsx,
			float tsy, int d, boolean l) {
		init(p, tx, ty, tr, tsx, tsy, d, l);
	}

	public void init(Part p, float tx, float ty, float tr, float tsx,
			float tsy, int d, boolean l) {
		translationX = tx;
		translationY = ty;
		scaleX = tsx;
		scaleY = tsy;
		rotation = tr;
		rduration= sduration = duration = d;
		part = p;
		_loops = l;
	}

	public PartAnimation(Part p, int a, int d) {
		initColorAnim(p, a, d);
	}

	public void initColorAnim(Part p, int a, int d) {
		coloranim = true;
		fca = a;
		sduration = duration = d;
		part = p;
		_loops = false;
		running = false;
	}

	@SearchAndReplaceAnnotation({ "BY", "System.currentTimeMillis", "OS::currentTimeMillies" })
	public static long currentTimeMillis() {
		return System.currentTimeMillis();
	}

	public void start() {
		running = true;
		sduration= rduration;		
		duration = sduration;
		lsx = lsy = 1.0f;
		lca = part.a;
		lx = 0.0f;
		ly = 0.0f;
		lr = 0.0f;
		delta = 0;
		_start = currentTimeMillis();
	}

	public PartAnimation createReverseAnimation() {
		int d = duration;
		float r = -rotation;
		float x = -translationX;
		float y = -translationY;
		float sx = 1.0f / scaleX;
		float sy = 1.0f / scaleY;

		if (running) {
			float p = (currentTimeMillis() - _start) / duration;
			d *= p;
			x *= p;
			y *= p;
			sx = (1.0f + (scaleX - 1.0f) * p);
			sy = (1.0f + (scaleY - 1.0f) * p);
			r *= p;
		}
		return new PartAnimation(part, x, y, r, sx, sy, d, _loops);
	}

	public float animate() {
		return animate(currentTimeMillis());
	}

	public float animate(long now) {
		if (!running) {
			return 1.0f;
		}
		long delta = (now - _start);
		float percentage = (float)delta / (float)duration;
	//	System.out.println("PartAnimation translate: "+percentage+" "+delta+" "+now+" "+_start+" "+duration);
		
		if (percentage > 1.0f) {
			percentage = 1.0f;
		}
		if (delta >= duration && !_loops) {
			percentage = 1.0f;
			finish();
		}
		if (coloranim) {
			part.setAlpha((int) (lca + (fca - lca) * percentage));
		} else {
			float v1 = (1.0f + (scaleX - 1.0f) * percentage);
			float v2 = (1.0f + (scaleY - 1.0f) * percentage);
//System.out.println("PartAnimation  translate: "+(translationX * percentage - lx)+" "+(translationY
//		* percentage - ly)+" "+delta+ " "+percentage);
			part.translate(translationX * percentage - lx, translationY
					* percentage - ly, 0.0f);
			lx = translationX * percentage;
			ly = translationY * percentage;
			part.scale(v1 / lsx, v2 / lsy);
			lsx = v1;
			lsy = v2;
			part.rotate(rotation * percentage - lr);
			lr = rotation * percentage;
		}
		if (delta >= duration && _loops) {
			start();
		}
		return percentage;
	}

	public void setValues(float x, float y, float sx, float sy, float rot) {
		part.clearAll();
		part.translate(x, y, 0.0f);
		part.rotate(rot);
		part.scale(sx, sy);
	}

	public void finish() {
		running = false;
	}

	public boolean isRunning() {
		return running;
	}

	public void stop() {
		running = false;
	}

	public void pause() {
		duration = (int) (1.0f - (currentTimeMillis() - _start) / duration);
	}

	public void cont() {
		running = true;
	}

	public float animateDelta(long d) {
		delta += d;
		return animate(_start + delta);
	}

	public void faster() {
		sduration= (int)(sduration*0.8f);
	}

	public void slower() {	
		sduration= (int)(sduration*1.2f);
	}

	public int getType() {
		return Types.PARTANIMATION;
	}
}