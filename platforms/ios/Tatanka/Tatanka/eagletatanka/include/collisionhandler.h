#ifndef _COLLISIONHANDLER_
#define _COLLISIONHANDLER_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"

#define null 0

class Thing;

class CollisionHandler {
public:

	virtual bool canCollide(Thing* thing);
	virtual bool handleCollision(Thing* thing);
	virtual bool checkCollision();
	virtual int collisionHappend();
	virtual Thing* getOther();
	virtual void clearCollision();
	virtual ~CollisionHandler();
};


#endif

