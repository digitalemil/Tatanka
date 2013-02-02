#ifndef _FIRSTSCREEN_
#define _FIRSTSCREEN_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "screen.h"

#define null 0

class ImageThing;
class Thing;
class Screen;

class FirstScreen : public Screen {
public:
	int hunt;
	int cont;
	int opts;
	int more;

	FirstScreen();
	virtual void activate();
	virtual int getBackgroundColor();
	virtual bool touchStart(int x, int y);
	virtual ~FirstScreen();
};


#endif

