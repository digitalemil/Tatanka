#ifndef _ROPE_
#define _ROPE_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "thing.h"

#define null 0

class Bone;
class Thing;
class Rectangle;
class Loop;
class Part;

class Rope : public Thing {
public:

	Rope();
	virtual int getType();
	virtual ~Rope();
};


#endif

