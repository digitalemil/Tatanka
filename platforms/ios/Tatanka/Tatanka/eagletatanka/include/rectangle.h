#ifndef _RECTANGLE_
#define _RECTANGLE_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "part.h"

#define null 0

class Part;

class Rectangle : public Part {
public:
	float width;
	float height;
	float tx_width;
	float tx_height;

	Rectangle(float w, float h, float irx, float iry, float irz, float ir, int ic);
	virtual void beginTX();
	virtual void rollbackTX();
	virtual void init(float w, float h, float irx, float iry, float irz, float ir, int ic);
	virtual int getNumberOfData();
	virtual int getData(float* d, int startD, float xn, float yn, float zn, float a11, float a21, float a12, float a22);
	virtual void scale(float isx, float isy);
	virtual void setDimension(float w, float h);
	virtual int getType();
	virtual ~Rectangle();
};


#endif

