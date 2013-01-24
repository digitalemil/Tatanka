#ifndef _BOUNDINGCIRCLE_
#define _BOUNDINGCIRCLE_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "ellipse.h"

#define null 0

class Ellipse;

class BoundingCircle : public Ellipse {
public:
	static const bool visibleboundingcircle = false;
	int start;

	BoundingCircle(float ir, float irx, float iry, float irz);
	virtual int getNumberOfData();
	virtual int getData(float* d, int startD, float xn, float yn, float zn, float a11, float a21, float a12, float a22);
	virtual int getType();
	virtual int getStart();
	virtual ~BoundingCircle();
};


#endif

