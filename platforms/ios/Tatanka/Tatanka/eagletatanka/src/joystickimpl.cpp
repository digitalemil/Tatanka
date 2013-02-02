#include "all.h"

#include "joystickimpl.h"

JoystickImpl::~JoystickImpl() {
	finalize();

}

JoystickImpl::JoystickImpl(MountedLakota* sioux, int markers) : Thing(2 + markers) {
	r = 0;
	touchx = 0;
	touchy = 0;
	touchphi = 0;
	nmarkers = 0;
	pressed = false;
	ani = new PartAnimation();
	lakota = 0;
	stick = 0;
	marker = 0;

  //Thing::2 + markers);
  int gray1=0x40000000;
  int gray2=0x80000000;
  int red=0x80592b13;
  setName((unsigned char*)"Joystick");
  addPart(new Ellipse(48,48,0,0,-3,0,Ellipse::TRIANGLES20,gray1));
  stick=new Ellipse(20,20,0,0,-3,0,Ellipse::TRIANGLES20,gray2);
  addPart(stick);
  nmarkers=markers;
  marker=(Part**)new void*[nmarkers];
  for (int i=0; i < nmarkers; i++) {
    marker[i]=new Ellipse(2,4,0,0,-3,0,Ellipse::TRIANGLES8,red);
    marker[i]->translate(0,-44,0);
    addPart(marker[i]);
  }
  scaleRoot(Globals::getScale() * 2,Globals::getScale() * 2);
  setupDone();
  lakota=sioux;
}


void JoystickImpl::finalize() {

  delete ani; delete marker;
}


void JoystickImpl::up() {

  if (!pressed)   return;
  r=-10;
  lakota->stopRotate();
  pressed=false;
  ani->init(stick,-stick->getX(),-stick->getY(),0,1.0f,1.0f,1000,false);
  ani->start();
}


bool JoystickImpl::down(int x, int y) {

  bool res=convert(x,y);
  if (res)   pressed=true;
  ani->stop();
  return res;
}


bool JoystickImpl::convert(int mx, int my) {

  touchx=(int)(mx - Globals::getW2() - x- stick->getX());
  touchy=(int)(my - Globals::getH2() - y- stick->getY());
  bool instick=false;
  if (touchx * touchx + touchy * touchy < 20 * 2 * Globals::getScale()* 20* 2* Globals::getScale()) {
    instick=true;
  }
  float newdir=(float)atan2(-touchy,touchx);
  if (newdir < 0) {
    newdir+=2 * PI;
  }
  newdir=(float)(newdir * 360 / (2 * PI));
  while (newdir > 360.0f)   newdir-=360.0f;
  touchphi=(int)newdir;
  return instick;
}


void JoystickImpl::move(int tx, int ty) {

  if (!pressed)   return;
  convert((int)(tx + stick->getX()),(int)(ty + stick->getY()));
  r=(int)sqrt(min(40 * 40,((touchx * touchx) + (touchy * touchy)) / Globals::getScale() / Globals::getScale()));
  int phi=Part::calcPhi(touchphi);
  float sinphi=mysin[phi];
  float cosphi=mycos[phi];
  tx=(int)(cosphi * r);
  ty=(int)(-sinphi * r);
  stick->beginTX();
  stick->translate(tx - stick->getX(),ty - stick->getY(),0);
  if (stick->getX() * stick->getX() + stick->getY() * stick->getY() > 72 * Globals::getScale() * 72* Globals::getScale()) {
    stick->rollbackTX();
    r=(int)(72 * Globals::getScale());
    tx=(int)(cosphi * r);
    ty=(int)(-sinphi * r);
    stick->translate(tx - stick->getX(),ty - stick->getY(),0);
  }
 else   stick->commitTX();
  lakota->rotate(-stick->getX() / 40.0f * 45.0f);
  if (stick->getY() > 0)   r*=-1;
}


void JoystickImpl::update(int i, int phi, float r, float d) {

  setMarker(i,phi,r,d);
  if (i == 0)   update();
}


void JoystickImpl::update() {

  ani->animate();
}


int JoystickImpl::getRadius() {

  return r;
}


void JoystickImpl::setMarker(int i, int phi, float r, float d) {

  marker[i]->translate(0,-marker[i]->getY(),0);
  marker[i]->translate(-marker[i]->getX() + (mycos[phi] * d * -44.0f),-marker[i]->getY() - (mysin[phi] * d * -44.0f),0);
  marker[i]->rotate(-marker[i]->getRotation() - r);
}


