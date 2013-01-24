#ifndef _ELLIPSE_
#define _ELLIPSE_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "part.h"

#define null 0

class Part;

class Ellipse : public Part {
public:
	static int TRIANGLES8;
	static int TRIANGLES10;
	static int TRIANGLES12;
	static int TRIANGLES20;
	static int TRIANGLES24;
	static int TRIANGLES0;
	int triangles;
	int maxeff;
	float sr1;
	float sr2;
	float r1;
	float r2;
	float tx_r1;
	float tx_r2;
	float tx_sr1;
	float tx_sr2;
	static float mysin[];
	static float mycos[];
	static int mycs[];

	virtual void beginTX();
	virtual void rollbackTX();
	static void slowDevice();
	virtual int getNumberOfTriangles();
	virtual int getMaxeff();
	virtual int* getMycs();
	virtual float getR1();
	virtual float getR2();
	Ellipse(float ir1, float ir2, float irx, float iry, float irz, float irotation, int itriangles, int icolor);
	virtual float getRadius();
	virtual void setRadius(float ir1, float ir2);
	virtual float getXScale();
	virtual float getYScale();
	virtual void clearScale();
	virtual int getNumberOfData();
	virtual int getData(float* d, int startD, float xn, float yn, float zn, float a11, float a21, float a12, float a22);
	virtual void scale(float sx, float sy);
	virtual void scaleRoot(float sx, float sy);
	virtual int getType();
	virtual void reset();
	virtual void setMaxEff(int m);
	virtual ~Ellipse();
};


#endif

