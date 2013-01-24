#ifndef _LAKOTACOLLISIONHANDLER_
#define _LAKOTACOLLISIONHANDLER_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "collisionhandlerimpl.h"

#define null 0

class Thing;
class CollisionHandlerImpl;

class LakotaCollisionHandler : public CollisionHandlerImpl {
public:

	LakotaCollisionHandler(Thing* thing, Thing** things, int s, int e);
	virtual bool canCollide(Thing* thing);
	virtual ~LakotaCollisionHandler();
};


#endif

