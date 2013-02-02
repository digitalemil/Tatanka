#ifndef _MORESCREEN_
#define _MORESCREEN_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "screen.h"

#define null 0

class ImageThing;
class Thing;
class Screen;

class MoreScreen : public Screen {
public:
	int back;
	int gallery;
	int about;
	int help;

	MoreScreen();
	virtual void activate();
	virtual int getBackgroundColor();
	virtual bool touchStart(int x, int y);
	virtual ~MoreScreen();
};


#endif

