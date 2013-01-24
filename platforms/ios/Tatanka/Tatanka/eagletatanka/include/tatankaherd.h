#ifndef _TATANKAHERD_
#define _TATANKAHERD_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "thingcontainer.h"

#define null 0

class LakotaCollisionHandler;
class Tatanka;
class ThingContainer;

class TatankaHerd : public ThingContainer {
public:
	int alpha;
	int levelOfAggression;
	float tspeed;

	TatankaHerd(int n);
	virtual int getRotation(float speedx, float speedy, float lakotaX);
	virtual void update(float speedx, float speedy, float lakotaX, float lakotaY);
	virtual int getAlphaX();
	virtual int getAlphaY();
	virtual ~TatankaHerd();
};


#endif

