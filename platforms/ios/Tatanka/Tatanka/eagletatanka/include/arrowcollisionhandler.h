#ifndef _ARROWCOLLISIONHANDLER_
#define _ARROWCOLLISIONHANDLER_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "collisionhandlerimpl.h"

#define null 0

class Thing;
class Tatanka;
class Arrow;
class CollisionHandlerImpl;

class ArrowCollisionHandler : public CollisionHandlerImpl {
public:
	static int n;

	ArrowCollisionHandler(Arrow* arrow, Thing** things);
	virtual bool canCollide(Thing* thing);
	virtual bool handleCollision(Thing* t);
	virtual unsigned char* toString();
	virtual ~ArrowCollisionHandler();
};


#endif

