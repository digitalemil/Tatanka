#ifndef _HUNTINGSCREEN_
#define _HUNTINGSCREEN_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "screen.h"

#define null 0

class Thing;
class ImageThing;
class TatankaHerd;
class Screen;
class Texts;
class Prairie;
class MountedLakota;
class JoystickImpl;

class HuntingScreen : public Screen {
public:
	Prairie* prairie;
	int nlakotas;
	int activelakota;
	int arrowsetal;
	MountedLakota** lakotas;
	TatankaHerd** herds;
	JoystickImpl* joystick;
	Texts* texts;
	ImageThing* menu;

	HuntingScreen();
	virtual void finalize();
	virtual void activate();
	virtual void update(long currentTimeMillis);
	virtual void touch(int x, int y);
	virtual bool touchStart(int x, int y);
	virtual bool touchStop(int x, int y);
	virtual ~HuntingScreen();
};


#endif

