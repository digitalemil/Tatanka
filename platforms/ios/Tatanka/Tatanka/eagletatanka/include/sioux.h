#ifndef _SIOUX_
#define _SIOUX_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "thing.h"

#define null 0

class CoordinateTapImpl;
class Bone;
class ArrowCollisionHandler;
class ShootingLakotaAnimation;
class Thing;
class Arrow;
class Part;
class Ellipse;
class CoordinateTap;

class Sioux : public Thing {
public:
	CoordinateTap* arrowCoords;
	Arrow* arrow;
	Arrow* flyingArrow;
	Bone* bow;
	Bone* leftarm;
	Bone* rightarm;
	Bone* upperparts;
	Bone* body;
	Part* fibre;
	ShootingLakotaAnimation* shootingAnimation;

	virtual ShootingLakotaAnimation* getShootingAnimation();
	virtual bool shoot(int x, int y);
	virtual Arrow* getArrow();
	virtual void setFibre(Part* fibre);
	virtual Part* getFibre();
	virtual void finalize();
	Sioux();
	virtual Bone* getBody();
	virtual Bone* getRightarm();
	virtual Bone* getUpperparts();
	virtual Arrow* getFlyingArrow();
	virtual void setFlyingArrow(Arrow* a);
	virtual Bone* getBow();
	virtual Bone* getLeftarm();
	virtual int getType();
	virtual ~Sioux();
};


#endif

