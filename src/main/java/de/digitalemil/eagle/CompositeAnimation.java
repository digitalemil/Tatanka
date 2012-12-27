package de.digitalemil.eagle;

public class CompositeAnimation implements Animation {

	protected String name;
	protected boolean _loops, running;
	protected int maxlevel, maxanimation, level;
	protected Animation anis[];
	protected long _start;
	
	public CompositeAnimation(String n, int ml, int maxa, boolean l) {
		_loops = l;
		name = n;
		running = false;
		maxlevel = ml;
		maxanimation = maxa;
		anis = new Animation[ml * maxa];
		level = 0;
		for (int i = 0; i < maxlevel; i++) {
			for (int j = 0; j < maxanimation; j++) {
				anis[i * maxanimation + j] = null;
			}
		}
	}

	public void dispose() {
		for (int i = 0; i < maxlevel; i++) {
			for (int j = 0; j < maxanimation; j++) {
				anis[i * maxanimation + j] = null;
			}
		}
		anis = null;
	}

	public void increaseLevel() {
		level++;

		if (level >= maxlevel) {
			stop();
		} else {
			for (int i = 0; i < maxanimation; i++) {
				if (anis[level * maxanimation + i] == null) {
					continue;
				}
				anis[level * maxanimation + i].start();
			}
		}
	}

	public void addAnimation(Animation a, int l) {
		for (int i = 0; i < maxanimation; i++) {
			if (anis[l * maxanimation + i] == null) {
				anis[l * maxanimation + i] = a;
				return;
			}
		}
	}

	public Animation createReverseAnimation() {
		CompositeAnimation ret = new CompositeAnimation("Reverse" + name,
				maxlevel, maxanimation, _loops);
		for (int i = 0; i < maxlevel; i++) {
			for (int j = 0; j < maxanimation; j++) {
				if (anis[i * maxanimation + j] != null) {

					ret.anis[(maxlevel - 1 - i) * maxanimation + maxanimation
							- 1 - j] = anis[i * maxanimation + j]
							.createReverseAnimation();
				}
			}
		}
		return ret;
	}

	public void start() {
		level = 0;
		_start = PartAnimation.currentTimeMillis();
		for (int i = 0; i < maxanimation; i++) {
			if (anis[i] != null) {
				anis[i].start();
			}
		}
		running = true;
	}

	public float animateDelta(long delta) {
		return animate(_start + delta);
	}
	
	public float animate() {
		return animate(PartAnimation.currentTimeMillis());
	}

	public float animate(long now) {
		if (!running) {
			return 1.0f;
		}
		float ret = 0.0f;
		for (int i = 0; i < maxanimation; i++) {
		
			if (anis[level * maxanimation + i] == null) {
				continue;
			}
			float oneret = anis[level * maxanimation + i].animate(now);
			
			if (oneret >= 1.0 && i == maxanimation - 1) {	
				increaseLevel();
			}
			
			ret = oneret;
		}
		return ret;
	}

	public void finish() {
		if (level >= maxlevel) {
			return;
		}
		for (int i = 0; i < maxanimation; i++) {
			if (anis[level * maxanimation + i] != null) {
				anis[level * maxanimation + i].finish();
			}
		}

	}

	public void faster() {
		for (int a = 0; a < maxlevel; a++) {
			for (int i = 0; i < maxanimation; i++) {
				if (anis[a * maxanimation + i] != null) {
					anis[a * maxanimation + i].faster();
				}
			}
		}
	}

	public void slower() {
		for (int a = 0; a < maxlevel; a++) {
			for (int i = 0; i < maxanimation; i++) {
				if (anis[a * maxanimation + i] != null) {
					anis[a * maxanimation + i].slower();
				}
			}
		}
	}

	public void stop() {
		if (_loops) {
			start();
		} else {
			for (int a = 0; a < maxlevel; a++) {
				for (int i = 0; i < maxanimation; i++) {
					if (anis[a * maxanimation + i] != null) {
						anis[a * maxanimation + i].stop();
					}
				}
			}
			running = false;
		}
	}

	public int getType() {
		return Types.COMPOSITEANIMATION;
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return running;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cont() {
		// TODO Auto-generated method stub
		
	}
}