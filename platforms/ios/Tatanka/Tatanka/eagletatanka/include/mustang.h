#ifndef _MUSTANG_
#define _MUSTANG_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "thing.h"

#define null 0

class CoordinateTapImpl;
class Bone;
class Thing;
class AnimalAnimation;
class BoundingCircle;
class Part;
class Ellipse;

class Mustang : public Thing {
public:
	AnimalAnimation* animation;

	virtual void finalize();
	Mustang();
	virtual void animate();
	virtual int getType();
	virtual ~Mustang();
};


#endif

