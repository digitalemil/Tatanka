#ifndef _PRAIRIE_
#define _PRAIRIE_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "thingcontainer.h"

#define null 0

class ImageThing;
class ThingContainer;

class Prairie : public ThingContainer {
public:
	int dim;
	int imgdim;
	int ntx;
	int nty;

	Prairie(unsigned char* imgname);
	virtual void update(float speedx, float speedy);
	virtual ~Prairie();
};


#endif

