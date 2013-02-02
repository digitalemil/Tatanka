#ifndef _GALLERYSCREEN_
#define _GALLERYSCREEN_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "screen.h"

#define null 0

class TextThing;
class ImageThing;
class Thing;
class Screen;

class GalleryScreen : public Screen {
public:
	int back;
	int img;
	int n;
	long start;

	GalleryScreen();
	virtual void update(long currentTimeMillis);
	virtual void activate();
	virtual int getBackgroundColor();
	virtual bool touchStart(int x, int y);
	virtual ~GalleryScreen();
};


#endif

