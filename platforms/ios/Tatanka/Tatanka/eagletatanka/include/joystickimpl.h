#ifndef _JOYSTICKIMPL_
#define _JOYSTICKIMPL_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "thing.h"
#include "joystick.h"

#define null 0

class Thing;
class Joystick;
class MountedLakota;
class PartAnimation;
class Part;
class Ellipse;

class JoystickImpl : public Thing, Joystick {
public:
	int r;
	int touchx;
	int touchy;
	int touchphi;
	int nmarkers;
	bool pressed;
	PartAnimation* ani;
	MountedLakota* lakota;
	Part* stick;
	Part** marker;

	JoystickImpl(MountedLakota* sioux, int markers);
	virtual void finalize();
	virtual void up();
	virtual bool down(int x, int y);
	virtual bool convert(int mx, int my);
	virtual void move(int tx, int ty);
	virtual void update(int i, int phi, float r, float d);
	virtual void update();
	virtual int getRadius();
	virtual void setMarker(int i, int phi, float r, float d);
	virtual ~JoystickImpl();
};


#endif

