#ifndef _COLLISIONHANDLERIMPL_
#define _COLLISIONHANDLERIMPL_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "collisionhandler.h"

#define null 0

class Thing;
class BoundingCircle;
class CollisionHandler;

class CollisionHandlerImpl : public CollisionHandler {
public:
	static const int NOCOLLISION = -1;
	int collisionHadHappend;
	Thing* me;
	Thing* other;
	Thing** others;
	int start;
	int end;
	bool enabled;

	CollisionHandlerImpl(Thing* thing, Thing** things, int s, int e);
	virtual bool isEnabled();
	virtual void enable();
	virtual void disable();
	virtual bool canCollide(Thing* thing);
	virtual bool handleCollision(Thing* thing);
	virtual bool checkCollision();
	virtual void clearCollision();
	virtual int collisionHappend();
	virtual Thing* getOther();
	virtual ~CollisionHandlerImpl();
};


#endif

