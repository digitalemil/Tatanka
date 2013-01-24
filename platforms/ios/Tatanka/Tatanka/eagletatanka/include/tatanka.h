#ifndef _TATANKA_
#define _TATANKA_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "thing.h"

#define null 0

class Bone;
class CoordinateTapImpl;
class Thing;
class Arrow;
class AnimalAnimation;
class BoundingCircle;
class Part;
class Ellipse;

class Tatanka : public Thing {
public:
	static const int MAXARROWS = 5;
	static const int MAXHEALTH = 100;
	int health;
	int hits;
	float speed;
	bool didc;
	AnimalAnimation* animation;

	virtual bool didCollide();
	virtual void setDidCollide(bool didCollide);
	Tatanka(float scale);
	virtual void animate();
	virtual AnimalAnimation* getAnimation();
	virtual void hit(int part);
	virtual void resetHealth();
	virtual int getType();
	virtual ~Tatanka();
};


#endif

