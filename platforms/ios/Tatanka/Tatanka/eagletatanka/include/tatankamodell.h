#ifndef _TATANKAMODELL_
#define _TATANKAMODELL_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "modell.h"

#define null 0

class Bone;
class ImageThing;
class TatankaHerd;
class Texts;
class Prairie;
class Modell;
class MountedLakota;
class JoystickImpl;

class TatankaModell : public Modell {
public:
	Prairie* prairie;
	int nlakotas;
	int activelakota;
	MountedLakota** lakotas;
	TatankaHerd** herds;
	JoystickImpl* joystick;
	Texts* texts;

	TatankaModell();
	virtual void update(long currentTimeMillis);
	virtual void setup();
	virtual void touch(int x, int y);
	virtual bool touchStart(int x, int y);
	virtual bool touchStop(int x, int y);
	virtual ~TatankaModell();
};


#endif

