#ifndef _JOYSTICK_
#define _JOYSTICK_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"

#define null 0


class Joystick {
public:

	virtual void up();
	virtual bool down(int x, int y);
	virtual void move(int tx, int ty);
	virtual void translate(float x, float y, float z);
	virtual ~Joystick();
};


#endif

