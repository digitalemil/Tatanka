#include "all.h"

#include "modell.h"

Modell::~Modell() {

}

Modell::Modell(int n) {
	things = 0;
	numberOfThings = 0;
	_start = 0;
	frames = 0;
	fps = 0;

  things=(Thing **)new void*[n];
  for(int i=0; i< n; i++) things[i]= 0; Globals::setAllThings(things);
  _start=0;
}


void Modell::setup() {

}


void Modell::update(long currentTimeMillis) {

  Globals::frames++;
  frames++;
  if (_start == 0)   _start=PartAnimation::currentTimeMillis();
  long now=PartAnimation::currentTimeMillis();
  if (now - _start > 1000) {
    fps=(int)((1000 * frames) / (now - _start));
    _start=0;
    frames=0;
  }
}


unsigned char** Modell::getTextAndFont(int t) {

  return things[t]->getThingTextAndFont();
}


int Modell::getFps() {

  return fps;
}


void Modell::touch(int x, int y) {

}


bool Modell::touchStart(int x, int y) {

  return false;
}


bool Modell::touchStop(int x, int y) {

  return false;
}


Thing** Modell::getThings() {

  return things;
}


void Modell::keyPressed(int i) {

}


void Modell::zoom(int i) {

}


int Modell::getNumberOfThings() {

  return numberOfThings;
}


void Modell::start() {

}


bool Modell::isVisible(int t) {

  return things[t]->isVisible();
}


bool Modell::hasChanged(int t) {

  return things[t]->hasChanged();
}


unsigned char* Modell::getImageName(int t) {

  return ((ImageThing*)things[t])->getTexName();
}


int Modell::getType(int t) {

  return things[t]->getType();
}


int Modell::getTexID(int t) {

  return ((ImageThing*)things[t])->getTexID();
}


int Modell::getImageWidth(int t) {

  if (!(things[t]->getType() == Types::IMAGE))   return 0;
  return (int)((ImageThing*)things[t])->getWidth();
}


int Modell::getImageHeight(int t) {

  if (!(things[t]->getType() == Types::IMAGE))   return 0;
  return (int)((ImageThing*)things[t])->getHeight();
}


float* Modell::getData(int t) {

  return things[t]->getThingData();
}


int Modell::getNumberOfData(int t) {

  return things[t]->getNumberOfData();
}


bool Modell::imageNameChanged(int t) {

  return ((ImageThing*)things[t])->isTexchanged();
}


bool Modell::isTexIDSet(int t) {

  return ((ImageThing*)things[t])->isTexidset();
}


void Modell::setTexIDForQuad(int t, int i) {

  ((ImageThing*)things[t])->setTexID(i);
}


bool Modell::skipFrame() {

  return false;
}


void Modell::moveViewport(int i, int j) {

}


