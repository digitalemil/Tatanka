#include "all.h"

#include "thing.h"

Thing::~Thing() {

}

Thing::Thing(int n) : Bone(0.0f, 0.0f, 0.0f, 0.0f, n) {
	nbc = 0;
	layer = 0;
	changed = 0;
	cancollide = 0;
	nonvisiblecancollide = 0;
	movable = 0;
	maxr = 0;
	collisionHandler = 0;

  //Bone::0.0f,0.0f,0.0f,0.0f,n);
  movable=true;
  maxr=0;
  nbc=0;
  changed=true;
  cancollide=true;
}


int Thing::getLayer() {

  return layer;
}


void Thing::setLayer(int layer) {

  this->layer=layer;
}


void Thing::setCollisionHandler(CollisionHandler* handler) {

  collisionHandler=handler;
}


CollisionHandler* Thing::getCollisionHandler() {

  return collisionHandler;
}


int Thing::getType() {

  return Types::THING;
}


void Thing::changeHandled() {

  changed=false;
}


void Thing::change() {

  changed=true;
}


float Thing::getRotation() {

  return rot;
}


bool Thing::hasChanged() {

  return changed;
}


unsigned char** Thing::getThingTextAndFont() {

  unsigned char** taf=getTextAndFont();
  if (taf == 0) {
    taf=new unsigned char*[this->nt];
    setTextAndFont(taf);
  }
  getTextAndFont(taf,0);
  return taf;
}


float* Thing::getThingData() {

  if (getData() == 0) {
    setData(new float[getNumberOfData()]);
  }
  getData(getData(),0,0,0,0,1.0f,0,0,1.0f);
  if (collisionHandler != 0) {
    collisionHandler->checkCollision();
  }
  return getData();
}


void Thing::reset() {

  invalidateData();
  sx=1.0f;
  sy=1.0f;
  for (int i=0; i < getNumberOfParts(); i++) {
    getParts()[i]->reset();
  }
}


void Thing::deleteData() {

  setData(0);
  invalidateData();
}


