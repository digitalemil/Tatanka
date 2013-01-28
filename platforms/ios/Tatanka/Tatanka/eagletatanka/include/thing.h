#ifndef _THING_
#define _THING_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "bone.h"

#define null 0

class Bone;
class CollisionHandler;

class Thing : public Bone {
public:
	int nbc;
	int layer;
	bool changed;
	bool cancollide;
	bool nonvisiblecancollide;
	bool movable;
	float maxr;
	CollisionHandler* collisionHandler;

	Thing(int n);
	virtual void finalize();
	virtual bool isIn(int x, int y);
	virtual int getLayer();
	virtual void setLayer(int layer);
	virtual void setCollisionHandler(CollisionHandler* handler);
	virtual CollisionHandler* getCollisionHandler();
	virtual int getType();
	virtual void changeHandled();
	virtual void change();
	virtual float getRotation();
	virtual bool hasChanged();
	virtual unsigned char** getThingTextAndFont();
	virtual float* getThingData();
	virtual void reset();
	virtual void deleteData();
	virtual ~Thing();
};


#endif

