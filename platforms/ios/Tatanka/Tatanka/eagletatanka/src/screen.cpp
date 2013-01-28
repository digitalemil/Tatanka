#include "all.h"

#include "screen.h"

Screen::~Screen() {
	finalize();

}

Screen::Screen() {
	screen = 0;
	_start = 0;
	frames = 0;
	fps = 0;
	id = 0;
	numberOfThings = 0;

  _start=0;
}


void Screen::finalize() {

  Thing** things=Globals::getAllThings();
  for (int i=0; i < numberOfThings; i++) {
    delete things[i];
  }
}


int Screen::getNumberOfThings() {

  return numberOfThings;
}


int Screen::getFps() {

  return fps;
}


void Screen::update(long currentTimeMillis) {

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


Screen* Screen::getActiveScreen() {

  if (activeScreen < 0 || activeScreen >= MAXSCREEN)   return 0;
  return screens[activeScreen];
}


void Screen::activate() {

  activeScreen=getScreenID();
  //System::out->println((unsigned char*)"Screen " + this);
  //System::out->println((unsigned char*)"Screen " + getActiveScreen());
  nextscreen=STAYONSCREEN;
}


void Screen::deactivate() {

  int i; finalize();
}


int Screen::moveToOtherScreen() {

  return nextscreen;
}


int Screen::fillThingArray(Thing** things) {

  return getNumberOfThings();
}


void Screen::touch(int x, int y) {

}


bool Screen::touchStart(int x, int y) {

  return false;
}


bool Screen::touchStop(int x, int y) {

  return false;
}


int Screen::getScreenID() {

  return id;
}


Screen* Screen::getScreen(int id) {

  return screens[id];
}


void Screen::registerScreen(Screen* screen) {

  screens[screen->getScreenID()]=screen;
}


int Screen::getBackgroundColor() {

  return 0;
}


Screen** Screen::screens = null;
int Screen::activeScreen = -1;
int Screen::nextscreen = STAYONSCREEN;
