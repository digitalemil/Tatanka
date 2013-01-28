#include "all.h"

#include "modell.h"

Modell::~Modell() {

}

Modell::Modell(int n) {
	maxthings = 0;
	things = 0;
	numberOfThings = 0;

  things=(Thing **)new void*[n];
  for(int i=0; i< n; i++) things[i]= 0; Globals::setAllThings(things,n);
}


void Modell::setup() {

}


int Modell::moveToOtherScreen() {

  return Screen::getActiveScreen()->moveToOtherScreen();
}


void Modell::showScreen(int id) {

  if (Screen::getActiveScreen() != 0)   Screen::getActiveScreen()->deactivate();
  Screen::getScreen(id)->activate();
}


void Modell::update(long currentTimeMillis) {

  if (Screen::getActiveScreen() != 0)   Screen::getActiveScreen()->update(currentTimeMillis);
}


unsigned char** Modell::getTextAndFont(int t) {

  return things[t]->getThingTextAndFont();
}


int Modell::getFps() {

  if (Screen::getActiveScreen() != 0)   return Screen::getActiveScreen()->getFps();
  return 0;
}


void Modell::touch(int x, int y) {

  Screen::getActiveScreen()->touch(x,y);
}


bool Modell::touchStart(int x, int y) {

  return Screen::getActiveScreen()->touchStart(x,y);
}


bool Modell::touchStop(int x, int y) {

  return Screen::getActiveScreen()->touchStop(x,y);
}


Thing** Modell::getThings() {

  return things;
}


void Modell::keyPressed(int i) {

}


void Modell::zoom(int i) {

}


int Modell::getNumberOfThings() {

  if (Screen::getActiveScreen() != 0)   return Screen::getActiveScreen()->getNumberOfThings();
  return -1;
}


int Modell::getBackgroudColor() {

  if (Screen::getActiveScreen() != 0)   return Screen::getActiveScreen()->getBackgroundColor();
  return 0;
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


