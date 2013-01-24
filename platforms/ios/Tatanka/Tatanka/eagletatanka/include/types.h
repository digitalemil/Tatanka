#ifndef _TYPES_
#define _TYPES_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"

#define null 0


class Types {
public:
	static const int PART = 0;
	static const int BONE = 1;
	static const int RECTANGLE = 2;
	static const int QUAD = 3;
	static const int THING = 4;
	static const int ELLIPSE = 15;
	static const int TRIANGLE = 28;
	static const int TEXQUAD = 30;
	static const int IMAGE = 3;
	static const int BOUNDINGCIRCLE = 25;
	static const int PARTANIMATION = 6;
	static const int COMPOSITEANIMATION = 7;
	static const int LOOP = 83;
	static const int THINGCONTAINER = 84;
	static const int LAKOTAANIMATION = 85;
	static const int TEXT = 112;

	virtual ~Types();
};


#endif

