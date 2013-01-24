#include "all.h"

#include "imagething.h"

ImageThing::~ImageThing() {

}

bool ImageThing::isTexidset() {

  return texidset;
}


ImageThing::ImageThing(unsigned char* name, float w, float h) : Thing(1) {
	texID = 0;
	texidset = false;
	texchanged = 0;
	texName = 0;

  //Thing::1);
  setTexName(name);
  init(w,h);
}


void ImageThing::init(float w, float h) {

  texID=0;
  texidset=false;
  texchanged=true;
  addPart(new Rectangle(w,h,0.0f,0.0f,0.0f,0.0f,0xFF880000));
  setupDone();
}


int ImageThing::getTexID() {

  return texID;
}


bool ImageThing::isTexchanged() {

  return texchanged;
}


unsigned char* ImageThing::getTexName() {

  return texName;
}


void ImageThing::setTexID(int i) {

  texidset=true;
  texID=i;
  texchanged=false;
}


void ImageThing::setTexName(unsigned char* n) {

  texchanged=true;
  texName=n;
}


int ImageThing::getType() {

  return Types::IMAGE;
}


void ImageThing::scale(float x, float y) {

  this->setDimension(getWidth() * x,getHeight() * y);
}


void ImageThing::setDimension(float w, float h) {

  ((Rectangle*)getParts()[0])->setDimension(h,w);
  this->texchanged=true;
  this->invalidateData();
}


float ImageThing::getWidth() {

  return ((Rectangle*)getParts()[0])->width * 2;
}


float ImageThing::getHeight() {

  return ((Rectangle*)getParts()[0])->height * 2;
}


Rectangle* ImageThing::getRectangle() {

  return ((Rectangle*)getParts()[0]);
}


bool ImageThing::isTexIDSet() {

  return texidset;
}


