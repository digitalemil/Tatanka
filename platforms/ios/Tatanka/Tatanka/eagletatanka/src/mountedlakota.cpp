#include "all.h"

#include "mountedlakota.h"

MountedLakota::~MountedLakota() {
	finalize();

}

void MountedLakota::finalize() {

  int i;
  delete lakotaRot;
  delete mustangRot;
}


MountedLakota::MountedLakota(float x, float y) {
	mustang = 0;
	lakota = 0;
	arrow = 0;
	lakotaRot = 0;
	mustangRot = 0;
	speed = 0;
	speedxeff = 0;
	speedyeff = 0;
	speedx = 0;
	speedy = 0;
	arrows = 30;

  init(3);
  mustang=new Mustang();
  things[0]=mustang;
  mustang->setLayer(200);
  mustang->setCollisionHandler(new LakotaCollisionHandler(mustang,Globals::getAllThings(),0,256));
  lakota=new Sioux();
  things[1]=lakota;
  lakota->setLayer(201);
  arrow=new Arrow();
  things[2]=arrow;
  arrow->setLayer(401);
  arrow->setVisibility(false);
  arrow->setName((unsigned char*)"Arrow");
  arrow->translateRoot(0,-26,0);
  lakota->setFlyingArrow(arrow);
  float s=Globals::getScale();
  translateRoot(x * s,y * s,0);
  scaleRoot(s * 0.8f,s * 0.8f);
}


void MountedLakota::slower() {

  mustang->animation->slower();
}


void MountedLakota::faster() {

  mustang->animation->faster();
}


void MountedLakota::stop() {

  mustang->animation->stop();
}


void MountedLakota::start() {

  mustang->animation->start();
}


void MountedLakota::stopRotate() {

  rotate(0);
}


void MountedLakota::rotate(float rot) {

  if (lakotaRot == 0) {
    lakotaRot=new PartAnimation();
  }
  if (mustangRot == 0) {
    mustangRot=new PartAnimation();
  }
  rot=-lakota->getRotation() + rot;
  while (rot < -180)   rot+=360;
  while (rot > 180)   rot=360 - rot;
  if (rot == 0)   return;
  int d=(int)(abs(rot / 90) * 2000);
  lakotaRot->stop();
  lakotaRot->init(lakota,0,0,rot,1.0f,1.0f,d,false);
  lakotaRot->start();
  mustangRot->stop();
  mustangRot->init(mustang,0,0,rot,1.0f,1.0f,d,false);
  mustangRot->start();
}


Mustang* MountedLakota::getMustang() {

  return mustang;
}


Sioux* MountedLakota::getLakota() {

  return lakota;
}


int MountedLakota::getArrows() {

  return arrows;
}


bool MountedLakota::shoot(int x, int y) {

  if (arrows <= 0)   return false;
  arrows--;
  return lakota->shoot(x,y);
}


void MountedLakota::update(float acceleration) {

  int phi=Part::calcPhi(lakota->getRotation() + 90);
  float sin=Part::mysin[phi];
  float cos=Part::mycos[phi] * -1;
  float oldspeed=speed;
  speed+=acceleration;
  if (speed > 4)   speed=4;
  if (speed < 0)   speed=0;
  if (oldspeed < 0.1 && speed > 0.1)   start();
  if ((oldspeed < 0.5 && speed > 0.5) || (oldspeed < 1 && speed > 1) || (oldspeed < 2 && speed > 2)|| (oldspeed < 3 && speed > 3)|| (oldspeed < 3.5 && speed > 3.5)) {
    faster();
    faster();
  }
  if ((oldspeed > 0.5 && speed < 0.5) || (oldspeed > 1 && speed < 1) || (oldspeed > 2 && speed < 2)|| (oldspeed > 3 && speed < 3)|| (oldspeed > 3.5 && speed < 3.5)) {
    slower();
    slower();
  }
  if (speed == 0)   stop();
  speedx=cos * speed * 2* Globals::getScale();
  speedy=sin * speed * 2* Globals::getScale();
  float dx=lakota->getX();
  speedxeff=0.0f;
  speedyeff=0.0f;
  int yeff=(int)(lakota->getY() + lakota->getRy());
  if (speed < 2 && yeff < 512 * Globals::getScale())   speedyeff=-0.5f;
  if (speed < 1 && yeff < 256 * Globals::getScale())   speedyeff=-0.5f;
  if (speed > 2 && yeff > 256 * Globals::getScale())   speedyeff+=+0.5f;
  if (speed > 3 && yeff > 180 * Globals::getScale())   speedyeff+=+0.5f;
  float scale=Globals::getScale();
  if (dx < 160 * scale && dx > -160 * scale) {
    float w=1 - abs(dx / (200 * scale));
    speedxeff=w * speedx;
  }
  if (!(dx - speedx < 160 * Globals::getScale() && dx - speedx > -160 * Globals::getScale()))   speedxeff=0.0f;
  lakota->arrow->setTXSupport(false);
  lakota->beginTX();
  mustang->beginTX();
  if (lakotaRot != 0)   lakotaRot->animate();
  if (mustangRot != 0)   mustangRot->animate();
  translate(-speedxeff,-speedyeff,0);
  mustang->getCollisionHandler()->clearCollision();
  mustang->getThingData();
  int t=mustang->getCollisionHandler()->collisionHappend();
  if (t == CollisionHandlerImpl::NOCOLLISION) {
    lakota->commitTX();
    mustang->commitTX();
  }
 else {
    lakota->rollbackTX();
    mustang->rollbackTX();
    translate(-lakota->getX(),-lakota->getY(),0);
    speedxeff=0.0f;
    speedyeff=0.0f;
  }
  lakota->arrow->setTXSupport(true);
  mustang->animate();
  lakota->getShootingAnimation()->animate();
  lakota->getFlyingArrow()->getArrowAnimation()->animate();
}


float MountedLakota::getSpeedX() {

  if (speedxeff < 0.1 && speedxeff > -0.1)   return speedx;
 else   return speedxeff;
}


float MountedLakota::getSpeedY() {

  return speedy - speedyeff;
}


