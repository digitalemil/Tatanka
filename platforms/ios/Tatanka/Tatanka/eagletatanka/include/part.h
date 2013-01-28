#ifndef _PART_
#define _PART_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"

#define null 0

class Bone;
class BoundingCircle;
class CoordinateTap;

class Part {
public:
	static int parts;
	float rsx;
	float rsy;
	float sx;
	float sy;
	float x;
	float y;
	float z;
	float rx;
	float ry;
	float rz;
	float rot;
	float rrot;
	int a;
	int r;
	int g;
	int b;
	unsigned char* name;
	float tx_rsx;
	float tx_rsy;
	float tx_sx;
	float tx_sy;
	float tx_x;
	float tx_y;
	float tx_z;
	float tx_rot;
	float tx_rx;
	float tx_ry;
	float tx_rz;
	float tx_rrot;
	int tx_a;
	int tx_r;
	int tx_g;
	int tx_b;
	int tx_type;
	bool tx_invaliddata;
	bool tx_highlighted;
	bool intransaction;
	unsigned char* tx_name;
	CoordinateTap* coordtap;
	bool invaliddata;
	bool highlighted;
	bool supTX;
	Bone* parent;
	static float mycos[];
	static float mysin[];
	bool isTextSet;

	Part();
	virtual int getData(float* d, int startD, float xn, float yn, float zn, float a11, float a21, float a12, float a22) = 0;
	virtual int getNumberOfTextAndFont();
	virtual int getTextAndFont(unsigned char** t, int startT);
	virtual void setTXSupport(bool b);
	virtual bool supportsTX();
	virtual bool isInvalid();
	virtual void setCoordinateTap(CoordinateTap* ct);
	virtual CoordinateTap* getCoordinateTap();
	virtual void beginTX();
	virtual void commitTX();
	virtual void rollbackTX();
	virtual float getRsx();
	virtual float getRsy();
	virtual float getSx();
	virtual float getSy();
	virtual float getX();
	virtual float getY();
	virtual float getZ();
	virtual float getRx();
	virtual float getRy();
	virtual float getRz();
	virtual float getRotation();
	virtual float getRrot();
	static int calcPhi(float in);
	virtual unsigned char* getName();
	virtual void setName(unsigned char* n);
	virtual void finalize();
	virtual int getNumberOfData();
	virtual void setParent(Bone* bone);
	virtual bool invalidateData();
	virtual void setRoot(float x, float y, float z, float r);
	virtual void rotateRoot(float deg);
	virtual unsigned char* toString();
	virtual void setColor(int c);
	virtual void setAlpha(int alpha);
	virtual void scale(float x, float y);
	virtual void scaleRoot(float sx, float sy);
	virtual void clearScale();
	virtual void translate(float tx, float ty, float tz);
	virtual void translateRoot(float tx, float ty, float tz);
	virtual void clearTranslation();
	virtual bool canHaveChilds();
	virtual int addBCs(BoundingCircle** bcarray, int start);
	virtual int getNumberOfBCs();
	virtual void rotate(float r);
	virtual void clearRotation();
	virtual void highlight(bool bin);
	virtual void clearAll();
	virtual int getType();
	virtual void reset();
	static int getRandom(int min, int max);
	virtual ~Part();
};


#endif

