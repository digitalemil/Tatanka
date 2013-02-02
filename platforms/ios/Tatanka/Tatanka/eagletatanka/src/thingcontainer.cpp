#include "all.h"

#include "thingcontainer.h"

ThingContainer::~ThingContainer() {
	finalize();

}

ThingContainer::ThingContainer() {
	n = 0;
	things = 0;
	layers = 0;
	pos = 0;

}


void ThingContainer::init(int nthings) {

  n=nthings;
  things=new Thing*[n];
  layers=new int[1000];
}


void ThingContainer::finalize() {

  for (int i=0; i < n; i++) {
    delete things[i];
  }
  delete [ ] things;
  delete [ ] layers;
}


int ThingContainer::addThings(Thing** in, int p, int layer) {

  int ninlayer=0;
  for (int i=0; i < n; i++) {
    if (things[i]->getLayer() == layer) {
      in[p++]=things[i];
      layers[layer]=p;
      ninlayer++;
    }
  }
  return ninlayer;
}


void ThingContainer::removeThings(Thing** objs) {

  int lastlayer=-1;
  int thingsinlayer=0;
  for (int i=0; i < n; i++) {
    int layer=things[i]->getLayer();
    int p=layers[layer];
    if (lastlayer != layer)     thingsinlayer=0;
 else     thingsinlayer++;
    objs[p + thingsinlayer]=0;
  }
}


void ThingContainer::beginTX() {

  for (int i=0; i < n; i++) {
    if (things[i] != 0)     things[i]->beginTX();
  }
}


void ThingContainer::commitTX() {

  for (int i=0; i < n; i++) {
    if (things[i] != 0)     things[i]->commitTX();
  }
}


void ThingContainer::rollbackTX() {

  for (int i=0; i < n; i++) {
    if (things[i] != 0)     things[i]->rollbackTX();
  }
}


void ThingContainer::translate(float ix, float iy, float iz) {

  for (int i=0; i < n; i++) {
    if (things[i] != 0)     things[i]->translate(ix,iy,iz);
  }
}


void ThingContainer::rotate(float deg) {

  for (int i=0; i < n; i++) {
    if (things[i] != 0)     things[i]->rotate(deg);
  }
}


void ThingContainer::scale(float sx, float sy) {

  for (int i=0; i < n; i++) {
    if (things[i] != 0)     things[i]->scale(sx,sy);
  }
}


void ThingContainer::translateRoot(float x, float y, float z) {

  for (int i=0; i < n; i++) {
    if (things[i] != 0)     things[i]->translateRoot(x,y,z);
  }
}


void ThingContainer::rotateRoot(float deg) {

  for (int i=0; i < n; i++) {
    if (things[i] != 0)     things[i]->rotateRoot(deg);
  }
}


void ThingContainer::scaleRoot(float sx, float sy) {

  for (int i=0; i < n; i++) {
    if (things[i] != 0)     things[i]->scaleRoot(sx,sy);
  }
}


float ThingContainer::getRotation(int t) {

  return things[t]->getRotation();
}


float ThingContainer::getX(int t) {

  return things[t]->getX();
}


float ThingContainer::getY(int t) {

  return things[t]->getY();
}


float ThingContainer::getRx(int t) {

  return things[t]->getRx();
}


float ThingContainer::getRy(int t) {

  return things[t]->getRy();
}


int ThingContainer::getType() {

  return Types::THINGCONTAINER;
}


int ThingContainer::getN() {

  return n;
}


