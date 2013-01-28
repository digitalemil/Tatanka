#ifndef _GLOBALS_
#define _GLOBALS_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"

#define null 0

class Thing;

class Globals {
public:
	static int width;
	static int height;
	static float scale;
	static int w2;
	static int h2;
	static Thing** allThings;
	static int defaultWidth;
	static int defaultHeight;
	static int frames;
	static int maxthings;

	static int getMaxThing();
	static int getFrames();
	static int getWidth();
	static int getHeight();
	static int getW2();
	static int getH2();
	static float getScale();
	static void calcScale();
	static void setWidth(int width);
	static void setHeight(int height);
	static void set(int w, int h);
	static void setAllThings(Thing** all, int n);
	static int getDefaultWidth();
	static int getDefaultHeight();
	static void setDefaults(int defaultWidth, int defaultHeight);
	static Thing** getAllThings();
	virtual ~Globals();
};


#endif

