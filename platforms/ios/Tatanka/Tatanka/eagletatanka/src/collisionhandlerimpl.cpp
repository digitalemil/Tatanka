#include "all.h"

#include "collisionhandlerimpl.h"

CollisionHandlerImpl::~CollisionHandlerImpl() {

}

CollisionHandlerImpl::CollisionHandlerImpl(Thing* thing, Thing** things, int s, int e) {
	collisionHadHappend = 0;
	me = 0;
	other = 0;
	others = 0;
	start = 0;
	end = 0;
	enabled = true;

  me=thing;
  start=s;
  end=e;
  others=things;
  clearCollision();
}


bool CollisionHandlerImpl::isEnabled() {

  return enabled;
}


void CollisionHandlerImpl::enable() {

  enabled=true;
}


void CollisionHandlerImpl::disable() {

  enabled=false;
}


bool CollisionHandlerImpl::canCollide(Thing* thing) {

  return true;
}


bool CollisionHandlerImpl::handleCollision(Thing* thing) {

  return false;
}


bool CollisionHandlerImpl::checkCollision() {

  if (!enabled)   return false;
  BoundingCircle **mybcs=me->getBCs();
  int mybcslength=me->getNumberOfBCs();
  for (int i=start; i < end; i++) {
    if (others[i] == 0) {
      return false;
    }
    if (!canCollide(others[i]) || me == others[i])     continue;
    BoundingCircle **bcs=others[i]->getBCs();
    int bcslength=others[i]->getNumberOfBCs();
    for (int h=0; h < bcslength; h++) {
      BoundingCircle *bbc=bcs[h];
      if (bbc == 0 || bbc->getCoordinateTap() == 0)       continue;
      float bx=bbc->getCoordinateTap()->getX();
      float by=bbc->getCoordinateTap()->getY();
      float br=bbc->getCoordinateTap()->getR() * others[i]->sx * others[i]->rsx;
      for (int j=0; j < mybcslength; j++) {
        BoundingCircle *abc=mybcs[j];
        if (abc->getCoordinateTap() == 0)         continue;
        float ax=abc->getCoordinateTap()->getX();
        float ay=abc->getCoordinateTap()->getY();
        float ar=abc->getCoordinateTap()->getR() * me->sx * me->rsx;
        if ((bx - ax) * (bx - ax) + (by - ay) * (by - ay) <= (br + ar) * (br + ar)) {
          other=others[i];
          collisionHadHappend=other->getType();
          return handleCollision(others[i]);
        }
      }
    }
  }
  return false;
}


void CollisionHandlerImpl::clearCollision() {

  collisionHadHappend=NOCOLLISION;
  other=0;
}


int CollisionHandlerImpl::collisionHappend() {

  return collisionHadHappend;
}


Thing* CollisionHandlerImpl::getOther() {

  return other;
}


