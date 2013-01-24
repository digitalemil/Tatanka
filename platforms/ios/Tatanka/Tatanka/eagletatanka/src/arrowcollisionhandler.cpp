#include "all.h"

#include "arrowcollisionhandler.h"

ArrowCollisionHandler::~ArrowCollisionHandler() {

}

ArrowCollisionHandler::ArrowCollisionHandler(Arrow* arrow, Thing** things) : CollisionHandlerImpl((Thing*)arrow, things, 4, 1000) {

  //CollisionHandlerImpl::arrow,things,4,1000);
}


bool ArrowCollisionHandler::canCollide(Thing* thing) {

  if (!me->isVisible())   return false;
  return (thing->getType() == TatankaTypes::TATANKA);
}


bool ArrowCollisionHandler::handleCollision(Thing* t) {

  //System::out->println((unsigned char*)"HIT!!!");
  Tatanka *tatanka=(Tatanka*)t;
  Arrow*arrow=(Arrow*)me;
  sprintf((char*)tmptextbuffer, (const char*)(unsigned char*)"arrow%i", tatanka->hits); Arrow* narr= (Arrow*)tatanka->getByName(tmptextbuffer); //=(Arrow*)tatanka->getByName((unsigned char*)"arrow" + tatanka->hits);
  if (narr == 0) {
    return false;
  }
  arrow->getArrowAnimation()->stop();
  narr->setVisibility(true);
  int phi=Part::calcPhi(tatanka->getRotation() + tatanka->getRrot());
  float sinphi=Part::mysin[phi];
  float cosphi=Part::mycos[phi];
  float ax=((arrow->getRx() + arrow->getX()) - tatanka->getX() - tatanka->getRx()) / tatanka->getRsx() / tatanka->getSx();
  float ay=((arrow->getRy() + arrow->getY()) - tatanka->getY() - tatanka->getRy()) / tatanka->getRsy() / tatanka->getSy();
  float dx=(ax * cosphi - ay * sinphi);
  float dy=(ay * cosphi + ax * sinphi);
  narr->getByName((unsigned char*)"stone")->translateRoot(20000,20000,0);
  narr->translate(-narr->getX() - narr->getRx() + dx,-narr->getY() - narr->getRy() + dy,0);
  narr->setRootRotation(-tatanka->getRotation() - tatanka->getRrot() + arrow->getRotation() + arrow->getRrot());
  tatanka->hit(0);
  arrow->setVisibility(false);
  arrow->getBCs()[0]->getCoordinateTap()->reset();
  return true;
}


unsigned char* ArrowCollisionHandler::toString() {

  return (unsigned char*)"ArrowCollisionHandler *";
}


int ArrowCollisionHandler::n = 0;
