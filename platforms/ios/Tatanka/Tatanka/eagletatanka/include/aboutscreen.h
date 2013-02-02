#ifndef _ABOUTSCREEN_
#define _ABOUTSCREEN_

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

class AboutScreen : public Screen {
public:
	int back;

	AboutScreen();
	virtual void activate();
	virtual int getBackgroundColor();
	virtual bool touchStart(int x, int y);
	virtual ~AboutScreen();
};


#endif

