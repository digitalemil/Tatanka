#include "all.h"

#include "lakotacollisionhandler.h"

LakotaCollisionHandler::~LakotaCollisionHandler() {

}

LakotaCollisionHandler::LakotaCollisionHandler(Thing* thing, Thing** things, int s, int e) : CollisionHandlerImpl(thing, things, s, e) {

  //CollisionHandlerImpl::thing,things,s,e);
}


bool LakotaCollisionHandler::canCollide(Thing* thing) {

  if (!me->isVisible())   return false;
  return (thing->getType() == TatankaTypes::TATANKA || thing->getType() == TatankaTypes::MUSTANG);
}


