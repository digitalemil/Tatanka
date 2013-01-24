#ifndef _LOOP_
#define _LOOP_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "ellipse.h"

#define null 0

class Ellipse;

class Loop : public Ellipse {
public:
	float ri1;
	float ri2;

	Loop(float ir1, float ir2, float iir1, float iir2, float irx, float iry, float irz, float irotation, int itriangles, int icolor);
	virtual int getNumberOfData();
	virtual int getType();
	virtual int getData(float* d, int startD, float xn, float yn, float zn, float a11, float a21, float a12, float a22);
	virtual ~Loop();
};


#endif

