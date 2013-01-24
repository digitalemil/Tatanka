#ifndef _COMPOSITEANIMATION_
#define _COMPOSITEANIMATION_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "animation.h"

#define null 0

class Animation;
class CompositeAnimation;

class CompositeAnimation : public Animation {
public:
	unsigned char* name;
	bool _loops;
	bool running;
	int maxlevel;
	int maxanimation;
	int level;
	Animation** anis;
	long _start;

	CompositeAnimation(unsigned char* n, int ml, int maxa, bool l);
	virtual void dispose();
	virtual void increaseLevel();
	virtual void addAnimation(Animation* a, int l);
	virtual Animation* createReverseAnimation();
	virtual void start();
	virtual float animateDelta(long delta);
	virtual float animate();
	virtual float animate(long now);
	virtual void finish();
	virtual void faster();
	virtual void slower();
	virtual void stop();
	virtual int getType();
	virtual bool isRunning();
	virtual void pause();
	virtual void cont();
	virtual ~CompositeAnimation();
};


#endif

