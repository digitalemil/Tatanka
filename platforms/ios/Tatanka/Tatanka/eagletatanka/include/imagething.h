#ifndef _IMAGETHING_
#define _IMAGETHING_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "thing.h"

#define null 0

class Thing;
class Rectangle;

class ImageThing : public Thing {
public:
	int texID;
	bool texidset;
	bool texchanged;
	unsigned char* texName;
	bool isTexNameSet;

	virtual bool isTexidset();
	ImageThing(unsigned char* name, float w, float h);
	virtual void finalize();
	virtual bool isIn(int ix, int iy);
	virtual void init(float w, float h);
	virtual int getTexID();
	virtual bool isTexchanged();
	virtual unsigned char* getTexName();
	virtual void setTexID(int i);
	virtual void setTexName(unsigned char* n);
	virtual int getType();
	virtual void scale(float x, float y);
	virtual void setDimension(float w, float h);
	virtual float getWidth();
	virtual float getHeight();
	virtual Rectangle* getRectangle();
	virtual bool isTexIDSet();
	virtual ~ImageThing();
};


#endif

