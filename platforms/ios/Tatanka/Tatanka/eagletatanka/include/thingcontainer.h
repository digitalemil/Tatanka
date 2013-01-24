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
	virtual int getType();
	virtual ~ThingContainer();
};


#endif

