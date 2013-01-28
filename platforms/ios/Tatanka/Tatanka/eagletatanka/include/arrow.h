#ifndef _ARROW_
#define _ARROW_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "thing.h"

#define null 0

class CoordinateTapImpl;
class Triangle;
class Bone;
class Thing;
class Rectangle;
class PartAnimation;
class BoundingCircle;
class Part;

class Arrow : public Thing {
public:
	PartAnimation* arrowAnimation;

	Arrow();
	virtual void finalize();
	virtual PartAnimation* getArrowAnimation();
	virtual int getType();
	virtual void setRootRotation(float f);
	virtual float* getThingData();
	virtual void setVisible(bool visible);
	virtual ~Arrow();
};


#endif

