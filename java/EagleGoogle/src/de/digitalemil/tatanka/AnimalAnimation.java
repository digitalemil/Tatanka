package de.digitalemil.tatanka;

import de.digitalemil.eagle.*;

public class AnimalAnimation extends CompositeAnimation {

	public final static int STOP = 0, STARTWALK = 1, WALK = 2, STOPWALK = 3,
			STARTRUNNING = 4, RUNNING = 5, STOPRUNNING = 6, RUNWALK = 7;

	protected int state, sl, dur, maxstep;
	protected float lslx, lsly;
	protected long lt;
	protected Thing animal;
	protected CompositeAnimation ka;
	protected PartAnimation dir;
	protected boolean killed = false;
	protected float speed= 1/0f;

	public AnimalAnimation(Thing thing, int steplength, int duration) {
		super("AnimalAnimation", 4, 1, true);
		animal = thing;
		sl = steplength;
		dur = duration;
	}
	
	public void undoTranslation() {
		animal.translate(-lslx, -lsly, 0.0f);
		lslx = 0.0f;
		lsly = 0.0f;
	}

	public void kill() {
		stopWalk();
		if (dir != null)
			dir.stop();
		killed = true;
	}

	public void createKillAnimation() {
		if (ka == null) {

			ka = new CompositeAnimation("KillAnimation", 3, 1, false);
			int duration = dur;
			float stepl = sl;

			stepl *= 1.5;
			duration /= 8;
		
			CompositeAnimation ka1 = new CompositeAnimation("ka", 1, 9, false);
			PartAnimation pa = new PartAnimation();
			pa.init(animal.getByName("LeftForelegPart1"), 0.0f, -sl, 0.0f,
					1.0f, 1.0f, duration, false);
			ka1.addAnimation(pa, 0);

			pa = new PartAnimation();
			pa.init(animal.getByName("LeftForelegPart2"), 0, -sl / 2, 0, 1.0f,
					2.0f, duration, false);
			ka1.addAnimation(pa, 0);

			pa = new PartAnimation();
			pa.init(animal.getByName("RightHindlegPart1"), 0, sl, 0, 1.0f,
					1.0f, duration, false);
			ka1.addAnimation(pa, 0);

			pa = new PartAnimation();
			pa.init(animal.getByName("RightHindlegPart2"), 0, sl / 2, 0, 1.0f,
					2.0f, duration, false);
			ka1.addAnimation(pa, 0);

			pa = new PartAnimation();
			pa.init(animal.getByName("RightForelegPart1"), 0, -sl, 0, 1.0f,
					1.0f, duration, false);
			ka1.addAnimation(pa, 0);

			pa = new PartAnimation();
			pa.init(animal.getByName("RightForelegPart2"), 0, -sl / 2, 0, 1.0f,
					2.0f, duration, false);
			ka1.addAnimation(pa, 0);

			pa = new PartAnimation();
			pa.init(animal.getByName("LeftHindlegPart1"), 0, sl, 0, 1.0f, 1.0f,
					duration, false);
			ka1.addAnimation(pa, 0);

			pa = new PartAnimation();
			pa.init(animal.getByName("LeftHindlegPart2"), 0, sl / 2, 0, 1.0f,
					2.0f, duration, false);
			ka1.addAnimation(pa, 0);

			pa = new PartAnimation();
			pa.init(animal, 0.0f, 0.0f, 0.0f, 0.95f, 0.95f, duration, false);
			ka1.addAnimation(pa, 0);
			ka.addAnimation(ka1, 0);

			pa = new PartAnimation();
			pa.initColorAnim(animal, 128, 1000);
			// ka.addAnimation(pa, 1);

			PartAnimation pa1 = new PartAnimation();
			pa1.init(animal.getByName("Tongue"), 0.0f, -20.0f, 0.0f, 10.0f,
					16.0f, 1000, false);
			ka.addAnimation(pa1, 2);

			ka.start();
		}
	}

	public void unkill() {
		// animal.setAlpha(1.0f);
		killed = false;
		animal.rotate(-animal.getRotation());
		if (ka != null) {
			ka.stop();
			ka = null;
		}
	}

	public void left(float degree) {
		rotate(degree);
	}

	public void right(float degree) {
		rotate(-degree);
	}

	public void rotate(float degree) {
		if (!(state > STOP && state <= RUNWALK))
			return;
		if (dir != null && dir.isRunning())
			return;

		if (state == WALK)
			degree *= 1.5;

		int d = dur;
		if (state == RUNNING)
			d /= 3;
		dir = new PartAnimation();
		dir.init(animal, 0.0f, 0.0f, degree, 1.0f, 1.0f, d, false);
		dir.start();
	}

	public void clear() {
		for (int i = 0; i < maxanimation; i++) {
			for (int j = 0; j < maxlevel; j++) {
				if (anis[j * maxanimation + i] != null) {
					anis[j * maxanimation + i].stop();
					if (anis[j * maxanimation + i] != null)
						anis[j * maxanimation + i] = null;
					anis[j * maxanimation + i] = null;
				}
			}
		}
	}

	public void createWalk(int s, int d) {
		clear();
		_loops = true;
		int na = 9;
		int isl = sl;
		CompositeAnimation wa1 = new CompositeAnimation("2", 1, na, false);
		int duration = dur;

		if (s == 2) {
			isl *= 1.5;
			duration /= 8;
		}
		PartAnimation pa = new PartAnimation();
		pa.init(animal.getByName("LeftForelegPart1"), 0, -isl, 0, 1.0f, 1.0f,
				duration, false);
		wa1.addAnimation(pa, 0);

		pa = new PartAnimation();
		pa.init(animal.getByName("LeftForelegPart2"), 0, -isl / 2, 0, 1.0f,
				2.0f, duration, false);
		wa1.addAnimation(pa, 0);

		pa = new PartAnimation();
		pa.init(animal.getByName("RightHindlegPart1"), 0, -isl, 0, 1.0f, 1.0f,
				duration, false);
		wa1.addAnimation(pa, 0);

		pa = new PartAnimation();
		pa.init(animal.getByName("RightHindlegPart2"), 0, -isl / 2, 0, 1.0f,
				2.0f, duration, false);
		wa1.addAnimation(pa, 0);

		pa = new PartAnimation();
		pa.init(animal.getByName("RightForelegPart1"), 0, isl, 0, 1.0f, 1.0f,
				duration, false);
		wa1.addAnimation(pa, 0);

		pa = new PartAnimation();
		pa.init(animal.getByName("RightForelegPart2"), 0, isl / 2, 0, 1.0f,
				2.0f, duration, false);
		wa1.addAnimation(pa, 0);

		pa = new PartAnimation();
		pa.init(animal.getByName("LeftHindlegPart1"), 0, isl, 0, 1.0f, 1.0f,
				duration, false);
		wa1.addAnimation(pa, 0);

		pa = new PartAnimation();
		pa.init(animal.getByName("LeftHindlegPart2"), 0, isl / 2, 0, 1.0f, 2.0f,
				duration, false);
		wa1.addAnimation(pa, 0);

		pa = new PartAnimation();
		pa.init(animal.getByName("Head"), 0, 0, d, 1.0f, 1.0f, duration, false);
		wa1.addAnimation(pa, 0);

		CompositeAnimation wa2 = new CompositeAnimation("2.1", 1, na, false);
		pa = new PartAnimation();
		pa.init(animal.getByName("LeftForelegPart1"), 0, isl, 0, 1.0f, 1.0f,
				duration, false);
		wa2.addAnimation(pa, 0);

		pa = new PartAnimation();
		pa.init(animal.getByName("LeftForelegPart2"), 0, isl / 2, 0, 1.0f, 2.0f,
				duration, false);
		wa2.addAnimation(pa, 0);

		pa = new PartAnimation();
		pa.init(animal.getByName("RightHindlegPart1"), 0, isl, 0, 1.0f, 1.0f,
				duration, false);
		wa2.addAnimation(pa, 0);
		pa = new PartAnimation();
		pa.init(animal.getByName("RightHindlegPart2"), 0, isl / 2, 0, 1.0f,
				2.0f, duration, false);
		wa2.addAnimation(pa, 0);
		pa = new PartAnimation();
		pa.init(animal.getByName("RightForelegPart1"), 0, -isl, 0, 1.0f, 1.0f,
				duration, false);
		wa2.addAnimation(pa, 0);
		pa = new PartAnimation();
		pa.init(animal.getByName("RightForelegPart2"), 0, -isl / 2, 0, 1.0f,
				2.0f, duration, false);
		wa2.addAnimation(pa, 0);
		pa = new PartAnimation();
		pa.init(animal.getByName("LeftHindlegPart1"), 0, -isl, 0, 1.0f, 1.0f,
				duration, false);
		wa2.addAnimation(pa, 0);
		pa = new PartAnimation();
		pa.init(animal.getByName("LeftHindlegPart2"), 0, -isl / 2, 0, 1.0f,
				2.0f, duration, false);
		wa2.addAnimation(pa, 0);
		pa = new PartAnimation();
		pa.init(animal.getByName("Head"), 0, 0, -d, 1.0f, 1.0f, duration, false);
		wa2.addAnimation(pa, 0);

		addAnimation(wa2, 0);
		addAnimation(wa2.createReverseAnimation(), 1);
		addAnimation(wa1, 2);
		addAnimation(wa1.createReverseAnimation(), 3);
		start();
	}

	public void stopWalk() {
		_loops = false;
		switch (state) {
		case STARTWALK:
		case WALK:
			state = STOPWALK;
			break;
		case STARTRUNNING:
		case RUNNING:
			state = STOPRUNNING;
			break;
		}
	}

	public void startRun() {
		// if (state == RUNNING)
		// return;
		_loops = false;
		if (state == STOP) {
			createWalk(2, 0);
			state = RUNNING;
		} else
			state = STARTRUNNING;
	}

	public void increaseLevelImpl() {
		if (killed) {
			// console.log("state: "+state+" l: "+level);
		}
		if (state == STOPRUNNING && level == 4) {
			state = STOP;
			running = false;
			if (killed)
				createKillAnimation();
		}
		if (state == STOPWALK && level == 4) {
			state = STOP;
			running = false;
			if (killed)
				createKillAnimation();
		}
		if (state == STARTRUNNING && level == 4) {
			createWalk(2, 0);
			state = RUNNING;
		}
		if (state == RUNWALK && level == 4) {
			int deg = 0;
			if (animal.getType() == TatankaTypes.TATANKA)
				deg = 12;
			createWalk(1, deg);
			state = WALK;
		}
		if (state == STARTWALK && level == 4) {
			state = WALK;
		}
		if (level == 4)
			animal.reset();
	}

	public void startWalk() {
		if (state >= STARTRUNNING && state <= RUNWALK) {
			_loops = false;
			state = RUNWALK;
			return;
		}
		if (state != WALK) {
			int deg = 0;
			if (animal.getType() == TatankaTypes.TATANKA)
				deg = 12;
			createWalk(1, deg);
			state = STARTWALK;
		}
	}

	public void start() {
		super.start();
		lt = _start = PartAnimation.currentTimeMillis();
		animal.reset();
	}

	public float animate() {
		long now = PartAnimation.currentTimeMillis();
		if (dir != null && dir.isRunning()) {
			dir.animate(now);
		}

		if (ka != null) {
			ka.animate(now);
			if (!ka.isRunning()) {
				ka = null;
			}
			return 0.0f;
		}

		if (!running) {
			return 1.0f;
		}

		if (state == STOP)
			return 0.0f;

		int l = level;
		float ret = animate(now);
		
		if (l != level)
			increaseLevelImpl();
		float rot = (float)Math.PI * 2.0f * animal.getRotation() / 360.0f;
		long delta = now - lt;
		float percentage = (delta / dur)*speed;
		if (state == STARTRUNNING || state == RUNWALK) {
			percentage *= 2;
		}
		if (state == RUNNING) {
			percentage *= 3;
		}
		if (percentage > 1.0f) {
			percentage = 1.0f;
		}
		int esl = sl;
		if (state == RUNNING || state == STARTRUNNING)
			esl *= 1.5;
		if (state != STOP) {
			int phi= Part.calcPhi(rot);
			
			lslx = -Part.mysin[phi] * esl * animal.getSx() * percentage;
			lsly = -Part.mycos[phi] * esl * animal.getSy() * percentage;
		}
		lt = now;
		return ret;
	};

	public int getType() {
		return TatankaTypes.TATANKANIMATION;
	}
}
