#ifndef _BONE_
#define _BONE_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "part.h"

#define null 0

class BoundingCircle;
class Part;

class Bone : public Part {
public:
	float* data;
	unsigned char** textAndFont;
	Part** parts;
	int nd;
	int nt;
	int maxp;
	BoundingCircle** bcs;
	int pn;
	int nbcs;
	bool visible;

	virtual void setData(float* data);
	virtual float* getData();
	virtual Part** getParts();
	virtual int getNumberOfParts();
	virtual unsigned char** getTextAndFont();
	virtual void setTextAndFont(unsigned char** taf);
	virtual int getTextAndFont(unsigned char** t, int startT);
	Bone(float x, float y, float z, float r, int n);
	virtual int getNumberOfBCs();
	virtual void finalize();
	virtual BoundingCircle** getBCs();
	virtual int addBCs(BoundingCircle** bcarray, int start);
	virtual void beginTX();
	virtual void commitTX();
	virtual void rollbackTX();
	virtual bool isVisible();
	virtual void setVisible(bool visible);
	virtual Part* getByName(unsigned char* n);
	virtual void add(Part* p, float x, float y, float z, float r);
	virtual void addPart(Part* p);
	virtual int getNumberOfTextAndFont();
	virtual int getNumberOfData();
	virtual bool canHaveChilds();
	virtual void setupDone();
	virtual int getData(float* d, int startD, float xn, float yn, float zn, float a11, float a21, float a12, float a22);
	virtual bool invalidateData();
	virtual void setVisibility(bool v);
	virtual void setColor(int c);
	virtual void setAlpha(int c);
	virtual int getType();
	virtual void highlight(bool b);
	virtual void reset();
	virtual ~Bone();
};


#endif

