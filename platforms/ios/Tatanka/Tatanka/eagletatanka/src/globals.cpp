#include "all.h"

#include "globals.h"

Globals::~Globals() {

}

int Globals::getMaxThing() {

  return maxthings;
}


int Globals::getFrames() {

  return frames;
}


int Globals::getWidth() {

  return width;
}


int Globals::getHeight() {

  return height;
}


int Globals::getW2() {

  return w2;
}


int Globals::getH2() {

  return h2;
}


float Globals::getScale() {

  return scale;
}


void Globals::calcScale() {

  scale=min(((float)width / defaultWidth),((float)height / defaultHeight));
}


void Globals::setWidth(int width) {

  Globals::width=width;
  w2=width / 2;
}


void Globals::setHeight(int height) {

  Globals::height=height;
  h2=height / 2;
}


void Globals::set(int w, int h) {

  setWidth(w);
  setHeight(h);
  calcScale();
}


void Globals::setAllThings(Thing** all, int n) {

  allThings=all;
  maxthings=n;
}


int Globals::getDefaultWidth() {

  return defaultWidth;
}


int Globals::getDefaultHeight() {

  return defaultHeight;
}


void Globals::setDefaults(int defaultWidth, int defaultHeight) {

  Globals::defaultWidth=defaultWidth;
  Globals::defaultHeight=defaultHeight;
  Screen::screens=(Screen**)new void*[Screen::MAXSCREEN];
  calcScale();
}


Thing** Globals::getAllThings() {

  return allThings;
}


int Globals::width = null;
int Globals::height = null;
float Globals::scale = null;
int Globals::w2 = null;
int Globals::h2 = null;
Thing** Globals::allThings = null;
int Globals::defaultWidth = 768;
int Globals::defaultHeight = 1024;
int Globals::frames = 0;
int Globals::maxthings = null;
