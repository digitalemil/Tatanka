#ifndef _ANIMALANIMATION_
#define _ANIMALANIMATION_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "compositeanimation.h"

#define null 0

class Thing;
class PartAnimation;
class CompositeAnimation;

class AnimalAnimation : public CompositeAnimation {
public:
	static const int STOP = 0;
	static const int STARTWALK = 1;
	static const int WALK = 2;
	static const int STOPWALK = 3;
	static const int STARTRUNNING = 4;
	static const int RUNNING = 5;
	static const int STOPRUNNING = 6;
	static const int RUNWALK = 7;
	int state;
	int sl;
	int dur;
	int maxstep;
	float lslx;
	float lsly;
	long lt;
	Thing* animal;
	CompositeAnimation* ka;
	PartAnimation* dir;
	bool killed;
	float speed;

	AnimalAnimation(Thing* thing, int steplength, int duration);
	virtual void finalize();
	virtual void undoTranslation();
	virtual void kill();
	virtual void createKillAnimation();
	virtual void unkill();
	virtual void left(float degree);
	virtual void right(float degree);
	virtual void rotate(float degree);
	virtual void clear();
	virtual void createWalk(int s, int d);
	virtual void stopWalk();
	virtual void startRun();
	virtual void increaseLevelImpl();
	virtual void startWalk();
	virtual void start();
	virtual float animate();
	virtual int getType();
	virtual ~AnimalAnimation();
};


#endif

