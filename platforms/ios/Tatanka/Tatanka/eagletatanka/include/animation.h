#ifndef _ANIMATION_
#define _ANIMATION_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"

#define null 0


class Animation {
public:

	virtual void start();
	virtual Animation* createReverseAnimation();
	virtual float animate();
	virtual float animate(long now);
	virtual void finish();
	virtual bool isRunning();
	virtual void stop();
	virtual void pause();
	virtual void cont();
	virtual float animateDelta(long d);
	virtual void faster();
	virtual void slower();
	virtual int getType();
	virtual ~Animation();
};


#endif

