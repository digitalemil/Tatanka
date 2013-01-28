#ifndef _SHOOTINGLAKOTAANIMATION_
#define _SHOOTINGLAKOTAANIMATION_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "compositeanimation.h"

#define null 0

class Arrow;
class PartAnimation;
class CompositeAnimation;
class Sioux;

class ShootingLakotaAnimation : public CompositeAnimation {
public:
	Sioux* lakota;
	Arrow* arrow;
	bool shooting;
	int width;
	int height;
	long lt;
	float scale;

	ShootingLakotaAnimation(Sioux* s);
	virtual void finalize();
	virtual void clear();
	virtual bool shoot(int shootAtX, int shootAtY);
	virtual void increaseLevelImpl();
	virtual void start();
	virtual float animate();
	virtual int getType();
	virtual ~ShootingLakotaAnimation();
};


#endif

