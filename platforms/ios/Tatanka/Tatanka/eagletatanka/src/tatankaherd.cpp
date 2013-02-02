#include "all.h"

#include "tatankaherd.h"

TatankaHerd::~TatankaHerd() {

}

TatankaHerd::TatankaHerd(int n) {
	alpha = 0;
	levelOfAggression = 1;
	tspeed = 1.0f;

  init(n);
  float scale=Globals::getScale();
  for (int i=0; i < n; i++) {
    float s=0.98f + Part::getRandom(0,4) / 100.0f;
    things[i]=new Tatanka(s);
    things[i]->scaleRoot(0.8f * scale,0.8f * scale);
    things[i]->setLayer(1);
    things[i]->setName((unsigned char*)"Tatanka" + i);
    things[i]->setCollisionHandler(new LakotaCollisionHandler(things[i],things,0,i));
    alpha=n / 2;
    bool done=false;
    int tries=0;
    int hw=(int)((n * 200.0f + Part::getRandom(0,48)) * Globals::getScale() / 2.4);
    int hh=(int)((n * 200.0f + Part::getRandom(0,48)) * Globals::getScale() / 2.4);
    do {
      things[i]->beginTX();
      int rx=-hw / 2 + Part::getRandom(0,hw);
      int ry=Globals::getH2() / -2 - hh / 2 + Part::getRandom(0,hh);
      things[i]->translateRoot(rx,ry,0);
      if (rx < 0)       things[i]->rotate(Part::getRandom(1,3));
 else       things[i]->rotate(-Part::getRandom(1,3));
      things[i]->getThingData();
      if (things[i]->getCollisionHandler()->collisionHappend() != CollisionHandlerImpl::NOCOLLISION) {
        //System::out->println((unsigned char*)"Tatanka collide: " + i + (unsigned char*)" "+ rx+ (unsigned char*)" "+ ry+ (unsigned char*)" "+ hw+ (unsigned char*)" "+ hh);
        things[i]->rollbackTX();
        things[i]->getCollisionHandler()->clearCollision();
        if (tries == 200) {
          n=i - 1;
          things[i]=0;
        }
      }
 else {
        things[i]->commitTX();
        done=true;
      }
      tries++;
    }
 while (!done);
  }
  for (int i=0; i < n; i++) {
    things[i]->setCollisionHandler(new LakotaCollisionHandler(things[i],Globals::getAllThings(),4,Globals::getMaxThing()));
  }
  things[alpha]->highlight(true);
}


int TatankaHerd::getNewRotation(float speedx, float speedy, float lakotaX) {

  Tatanka *tatanka=(Tatanka*)things[alpha];
  if (tatanka->didCollide() || tatanka->getRotation() < -10 || (tatanka->getRotation() > 10 && tatanka->getRotation() < 350))   return 0;
  int deg=((tatanka->getX() + tatanka->getRx() - lakotaX) > 0) ? 1 : -1;
  deg*=levelOfAggression;
  if (Part::getRandom(0,30) < 20)   return 0;
  if (Globals::getFrames() % 20 == 0) {
    if (Part::getRandom(0,100) < 50)     levelOfAggression*=-1;
  }
  if (Globals::getFrames() % 50 == 0 && speedy > 0 && ((tatanka->getRotation() > 340 && tatanka->getRotation() <= 359) || (tatanka->getRotation() < 20 && tatanka->getRotation() >= 0) || (tatanka->getRotation() <= 180 && tatanka->getRotation() >= 20 && deg < 0)|| (tatanka->getRotation() <= 340 && tatanka->getRotation() > 180 && deg > 0))) {
    return deg;
  }
  return 0;
}


void TatankaHerd::update(float speedx, float speedy, float lakotaX, float lakotaY) {

  float sx, sy, sin, cos;
  bool faster=false;
  if (tspeed < 3.5f) {
    float ispeed=tspeed;
    tspeed+=0.01f;
    if ((ispeed < 2.0f && tspeed > 2.0f) || (ispeed < 2.5f && tspeed > 2.5f))     faster=true;
  }
  for (int i=0; i < n; i++) {
    Tatanka *tatanka=(Tatanka*)things[i];
    int phi=Part::calcPhi(tatanka->getRotation() + 90);
    float r2=(tatanka->getX() + tatanka->getRx() - lakotaX) * (tatanka->getX() + tatanka->getRx() - lakotaX) + (tatanka->getY() + tatanka->getRy() - lakotaY) * (tatanka->getY() + tatanka->getRy() - lakotaY);
    sin=Part::mysin[phi];
    cos=-Part::mycos[phi];
    sx=-cos * tspeed * 2* Globals::getScale();
    sy=-sin * tspeed * 2* Globals::getScale();
    if (faster) {
    }
    tatanka->beginTX();
    tatanka->translate(sx + speedx,sy + speedy,0);
    tatanka->rotate(getNewRotation(speedx,speedy,lakotaX));
    tatanka->getCollisionHandler()->clearCollision();
    tatanka->getThingData();
    int t=tatanka->getCollisionHandler()->collisionHappend();
    if (t == CollisionHandlerImpl::NOCOLLISION) {
      tatanka->setDidCollide(false);
      tatanka->commitTX();
    }
 else {
      float r=1.0f;
      if (things[i]->getX() >= tatanka->getCollisionHandler()->getOther()->getX()) {
        r=-1.0f;
      }
      tatanka->rollbackTX();
      bool foundplace=false;
      float n=0;
      while (!foundplace) {
        tatanka->beginTX();
        n+=0.5;
        if (tatanka->getY() + tatanka->getRy() > tatanka->getCollisionHandler()->getOther()->getY() + tatanka->getCollisionHandler()->getOther()->getRy() && tatanka->getCollisionHandler()->getOther()->getType() == TatankaTypes::TATANKA)         sy=-sin * (tspeed - n) * 2* Globals::getScale();
 else         sy=-sin * (tspeed + n) * 2* Globals::getScale();
        if (tatanka->getX() + tatanka->getRx() > tatanka->getCollisionHandler()->getOther()->getX() + tatanka->getCollisionHandler()->getOther()->getRx() && tatanka->getCollisionHandler()->getOther()->getType() == TatankaTypes::TATANKA)         sx=-cos * (tspeed + n) * 2* Globals::getScale();
 else         sx=-cos * (tspeed - n) * 2* Globals::getScale();
        things[i]->translate(sx + speedx,sy + speedy,0);
        things[i]->rotate(-r * 2);
        tatanka->getCollisionHandler()->clearCollision();
        tatanka->getThingData();
        t=tatanka->getCollisionHandler()->collisionHappend();
        if (t != CollisionHandlerImpl::NOCOLLISION)         tatanka->rollbackTX();
 else {
          tatanka->commitTX();
          foundplace=true;
        }
        if (n >= 5 && t != CollisionHandlerImpl::NOCOLLISION) {
          foundplace=true;
          things[i]->translate(speedx,speedy,0);
        }
      }
    }
    if (tatanka->getCoordinateTap()->getY() > Globals::getHeight()) {
      tatanka->rotate(-tatanka->getRotation());
      tatanka->translate(0,-100 * Globals::getScale(),0);
    }
    tatanka->getCollisionHandler()->clearCollision();
    tatanka->animate();
  }
}


int TatankaHerd::getTX(int t) {

  return (int)(things[t]->getX() + things[t]->getRx());
}


int TatankaHerd::getTY(int t) {

  return (int)(things[t]->getY() + things[t]->getRy());
}


