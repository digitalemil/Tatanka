#ifndef _SCREEN_
#define _SCREEN_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"

#define null 0

class Thing;
class Screen;

class Screen {
public:
	int screen;
	static const int STAYONSCREEN = -1;
	static const int MAXSCREEN = 256;
	static Screen** screens;
	static int activeScreen;
	long _start;
	long frames;
	int fps;
	int id;
	int numberOfThings;
	static int nextscreen;

	Screen();
	virtual void finalize();
	virtual int getNumberOfThings();
	virtual int getFps();
	virtual void update(long currentTimeMillis);
	static Screen* getActiveScreen();
	virtual void activate();
	virtual void deactivate();
	static int moveToOtherScreen();
	virtual int fillThingArray(Thing** things);
	virtual void touch(int x, int y);
	virtual bool touchStart(int x, int y);
	virtual bool touchStop(int x, int y);
	virtual int getScreenID();
	static Screen* getScreen(int id);
	static void registerScreen(Screen* screen);
	virtual int getBackgroundColor();
	virtual ~Screen();
};


#endif

