#ifndef _THINGCONTAINER_
#define _THINGCONTAINER_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"

#define null 0

class Thing;

class ThingContainer {
public:
	int n;
	Thing** things;
	int* layers;
	int pos;

	ThingContainer();
	virtual void init(int nthings);
	virtual void finalize();
	virtual int addThings(Thing** in, int p, int layer);
	virtual void removeThings(Thing** objs);
	virtual void beginTX();
	virtual void commitTX();
	virtual void rollbackTX();
	virtual void translate(float ix, float iy, float iz);
	virtual void rotate(float deg);
	virtual void scale(float sx, float sy);
	virtual void translateRoot(float x, float y, float z);
	virtual void rotateRoot(float deg);
	virtual void scaleRoot(float sx, float sy);
	virtual float getRotation(int t);
	virtual float getX(int t);
	virtual float getY(int t);
	virtual float getRx(int t);
	virtual float getRy(int t);
	virtual int getType();
	virtual int getN();
	virtual ~ThingContainer();
};


#endif

