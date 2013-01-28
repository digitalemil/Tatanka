#ifndef _PARTANIMATION_
#define _PARTANIMATION_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "animation.h"

#define null 0

class Animation;
class PartAnimation;
class Part;

class PartAnimation : public Animation {
public:
	bool coloranim;
	int fca;
	int sduration;
	int duration;
	int rduration;
	bool _loops;
	bool running;
	Part* part;
	float translationX;
	float translationY;
	float scaleX;
	float scaleY;
	float rotation;
	float lsx;
	float lsy;
	float lx;
	float ly;
	float lr;
	int lca;
	int delta;
	long _start;
	static int animations;

	PartAnimation();
	virtual void finalize();
	PartAnimation(Part* p, float tx, float ty, float tr, float tsx, float tsy, int d, bool l);
	virtual void init(Part* p, float tx, float ty, float tr, float tsx, float tsy, int d, bool l);
	PartAnimation(Part* p, int a, int d);
	virtual void initColorAnim(Part* p, int a, int d);
	static long currentTimeMillis();
	virtual void start();
	virtual PartAnimation* createReverseAnimation();
	virtual float animate();
	virtual float animate(long now);
	virtual void setValues(float x, float y, float sx, float sy, float rot);
	virtual void finish();
	virtual bool isRunning();
	virtual void stop();
	virtual void pause();
	virtual void cont();
	virtual float animateDelta(long d);
	virtual void faster();
	virtual void slower();
	virtual int getType();
	virtual ~PartAnimation();
};


#endif

