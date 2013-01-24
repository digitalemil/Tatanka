#ifndef _TRIANGLE_
#define _TRIANGLE_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "part.h"

#define null 0

class Part;

class Triangle : public Part {
public:
	float x1;
	float y1;
	float x2;
	float y2;
	float x3;
	float y3;
	float tx_x1;
	float tx_y1;
	float tx_x2;
	float tx_y2;
	float tx_x3;
	float tx_y3;

	Triangle(float rx, float ry, float rz, float px1, float py1, float px2, float py2, float px3, float py3, float pr, int color);
	virtual void beginTX();
	virtual void rollbackTX();
	virtual int getNumberOfData();
	virtual int getData(float* d, int startD, float xn, float yn, float zn, float a11, float a21, float a12, float a22);
	virtual int getType();
	virtual ~Triangle();
};


#endif

