#include "all.h"

#include "huntingscreen.h"

HuntingScreen::~HuntingScreen() {
	finalize();

}

HuntingScreen::HuntingScreen() {
	prairie = 0;
	nlakotas = 1;
	activelakota = 0;
	arrowsetal = 0;
	lakotas = 0;
	herds = 0;
	joystick = 0;
	texts = 0;
	menu = 0;

  id=1;
}


void HuntingScreen::finalize() {

  delete prairie;
  delete lakotas[0];
  lakotas=0;
  delete herds[0];
  delete [ ] herds;
  delete joystick;
  delete texts;
  delete menu;
  Thing** things=Globals::getAllThings();
  delete things[arrowsetal];
  for (int i=0; i < numberOfThings; i++) {
    things[i]=0;
  }
}


void HuntingScreen::activate() {

  Screen::activate();
  Thing** things=Globals::getAllThings();
  prairie=new Prairie((unsigned char*)"valleygras512.jpg");
  herds=new TatankaHerd*[1];
  herds[0]=new TatankaHerd(8);
  //System::out->println((unsigned char*)"nlakotas: " + nlakotas);
  lakotas=new MountedLakota*[nlakotas];
  for (int i=0; i < nlakotas; i++) {
    lakotas[i]=new MountedLakota(80 * i,320);
  }
  int pos=0;
  for (int layer=0; layer < 1000; layer++) {
    pos+=prairie->addThings(things,pos,layer);
    pos+=herds[0]->addThings(things,pos,layer);
    for (int j=0; j < nlakotas; j++) {
      pos+=lakotas[j]->addThings(things,pos,layer);
    }
  }
  joystick=new JoystickImpl(lakotas[0]);
  things[pos++]=joystick;
  joystick->Thing::translate(Globals::getW2() - 128 * Globals::getScale(),Globals::getH2() - 108 * Globals::getScale(),0);
  texts=new Texts();
  things[pos]=texts;
  things[pos]->translate(-Globals::getW2() + 64 * Globals::getScale(),Globals::getH2(),0);
  texts->setArrows(lakotas[activelakota]->getArrows());
  pos++;
  arrowsetal=pos;
  things[pos]=new ImageThing((unsigned char*)"arrowsetalmin.png",(int)(123 * Globals::getScale() / 2),(int)(354 * Globals::getScale() / 2));
  things[pos]->translate(-Globals::getW2() + (int)(123 * Globals::getScale() / 2) / 2,Globals::getH2() - (int)(374 * Globals::getScale() / 2) / 2,0);
  pos++;
  things[pos]=new ImageThing((unsigned char*)"tepee.png",(int)(2 * 64 * Globals::getScale() / 2),(int)(2 * 64 * Globals::getScale() / 2));
  things[pos]->translate(Globals::getW2() - (int)(80 * Globals::getScale() / 2),-Globals::getH2() + (int)(80 * Globals::getScale() / 2),0);
  menu=(ImageThing*)things[pos];
  pos++;
  numberOfThings=pos;
}


void HuntingScreen::update(long currentTimeMillis) {

  Screen::update(currentTimeMillis);
  if (getFps() > 0)   lakotas[activelakota]->update(joystick->getRadius() / 20.0f / getFps());
 else   lakotas[activelakota]->update(joystick->getRadius() / 20.0f / 30);
  for (int j=0; j < nlakotas; j++) {
    if (lakotas[j] == lakotas[activelakota])     continue;
    lakotas[j]->update(0);
  }
  prairie->update(lakotas[activelakota]->getSpeedX(),lakotas[activelakota]->getSpeedY());
  herds[0]->update(lakotas[activelakota]->getSpeedX(),lakotas[activelakota]->getSpeedY(),lakotas[activelakota]->lakota->getX() + lakotas[activelakota]->lakota->getRx(),lakotas[activelakota]->lakota->getY() + lakotas[activelakota]->lakota->getRy());
  int x=-(int)(herds[0]->getAlphaX() - lakotas[activelakota]->lakota->getX() - lakotas[activelakota]->lakota->getRx());
  int y=(int)(herds[0]->getAlphaY() - lakotas[activelakota]->lakota->getY() - lakotas[activelakota]->lakota->getRy());
  float newdir=(float)atan2(y,x);
  if (newdir < 0) {
    newdir+=2 * PI;
  }
  newdir=(float)(newdir * 360.0f / (2 * PI));
  joystick->update((int)newdir);
}


void HuntingScreen::touch(int x, int y) {

  joystick->move(x,y);
}


bool HuntingScreen::touchStart(int x, int y) {

  if (menu->isIn(x,y)) {
    nextscreen=0;
    return true;
  }
  if (!lakotas[activelakota]->shoot(x,y)) {
    joystick->down(x,y);
  }
 else {
    texts->setArrows(lakotas[activelakota]->getArrows());
  }
  return true;
}


bool HuntingScreen::touchStop(int x, int y) {

  joystick->up();
  return true;
}


