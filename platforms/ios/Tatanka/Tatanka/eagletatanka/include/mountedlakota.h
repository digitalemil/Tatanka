#ifndef _MOUNTEDLAKOTA_
#define _MOUNTEDLAKOTA_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "thingcontainer.h"

#define null 0

class LakotaCollisionHandler;
class ThingContainer;
class Arrow;
class PartAnimation;
class Sioux;
class Mustang;

class MountedLakota : public ThingContainer {
public:
	Mustang* mustang;
	Sioux* lakota;
	Arrow* arrow;
	PartAnimation* lakotaRot;
	PartAnimation* mustangRot;
	float speed;
	float speedxeff;
	float speedyeff;
	float speedx;
	float speedy;
	int arrows;

	virtual void finalize();
	MountedLakota(float x, float y);
	virtual void slower();
	virtual void faster();
	virtual void stop();
	virtual void start();
	virtual void stopRotate();
	virtual void rotate(float rot);
	virtual Mustang* getMustang();
	virtual Sioux* getLakota();
	virtual int getArrows();
	virtual bool shoot(int x, int y);
	virtual void update(float acceleration);
	virtual float getSpeedX();
	virtual float getSpeedY();
	virtual ~MountedLakota();
};


#endif

