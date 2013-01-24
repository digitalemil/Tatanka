#ifndef _COORDINATETAP_
#define _COORDINATETAP_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"

#define null 0


class CoordinateTap {
public:

	virtual void save(float ix, float iy, float ir, float ia11, float ia21, float ia12, float ia22);
	virtual void reset();
	virtual unsigned char* getName();
	virtual float getX();
	virtual float getY();
	virtual float getR();
	virtual float getA11();
	virtual float getA21();
	virtual float getA12();
	virtual float getA22();
	virtual void setName(unsigned char* name);
	virtual ~CoordinateTap();
};


#endif

