#ifndef _MODELL_
#define _MODELL_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"

#define null 0

class Thing;

class Modell {
public:
	int maxthings;
	Thing** things;
	int numberOfThings;
	static float version;
	static bool ps;

	Modell(int n);
	virtual void setup();
	virtual int moveToOtherScreen();
	virtual void showScreen(int id);
	virtual void update(long currentTimeMillis);
	virtual unsigned char** getTextAndFont(int t);
	virtual int getFps();
	virtual void touch(int x, int y);
	virtual bool touchStart(int x, int y);
	virtual bool touchStop(int x, int y);
	virtual Thing** getThings();
	virtual void keyPressed(int i);
	virtual bool isSoundOn();
	static bool shallPlaySound();
	static void playSound(bool s);
	static float getVersion();
	virtual void zoom(int i);
	static void setVersionNumber(float v);
	virtual int getNumberOfThings();
	virtual int getBackgroudColor();
	virtual void start();
	virtual bool isVisible(int t);
	virtual bool hasChanged(int t);
	virtual unsigned char* getImageName(int t);
	virtual int getType(int t);
	virtual int getTexID(int t);
	virtual int getImageWidth(int t);
	virtual int getImageHeight(int t);
	virtual float* getData(int t);
	virtual int getNumberOfData(int t);
	virtual bool imageNameChanged(int t);
	virtual bool isTexIDSet(int t);
	virtual void setTexIDForQuad(int t, int i);
	virtual bool skipFrame();
	virtual void moveViewport(int i, int j);
	virtual ~Modell();
};


#endif

