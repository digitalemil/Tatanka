#include "all.h"

#include "coordinatetapimpl.h"

CoordinateTapImpl::~CoordinateTapImpl() {

}

unsigned char* CoordinateTapImpl::toString() {

  //return (unsigned char*)"CoordinateTapImpl [name=" + name + (unsigned char*)", x="+ x+ (unsigned char*)", y="+ y+ (unsigned char*)", r="+ r+ (unsigned char*)", a11="+ a11+ (unsigned char*)", a21="+ a21+ (unsigned char*)", a12="+ a12+ (unsigned char*)", a22="+ a22+ (unsigned char*)"]";
}


CoordinateTapImpl::CoordinateTapImpl() {
	name = 0;
	x = -100000;
	y = -100000;
	r = 0;
	a11 = 0;
	a21 = 0;
	a12 = 0;
	a22 = 0;

  this->name=(unsigned char*)"";
}


CoordinateTapImpl::CoordinateTapImpl(unsigned char* n) {
	name = 0;
	x = -100000;
	y = -100000;
	r = 0;
	a11 = 0;
	a21 = 0;
	a12 = 0;
	a22 = 0;

  this->name=n;
}


void CoordinateTapImpl::save(float ix, float iy, float ir, float ia11, float ia21, float ia12, float ia22) {

  x=ix;
  y=iy;
  r=ir;
  a11=ia11;
  a21=ia21;
  a12=ia12;
  a22=ia22;
}


void CoordinateTapImpl::reset() {

  this->y=-100000;
  this->y=-100000;
}


unsigned char* CoordinateTapImpl::getName() {

  return name;
}


float CoordinateTapImpl::getX() {

  return x;
}


float CoordinateTapImpl::getY() {

  return y;
}


float CoordinateTapImpl::getR() {

  return r;
}


float CoordinateTapImpl::getA11() {

  return a11;
}


float CoordinateTapImpl::getA21() {

  return a21;
}


float CoordinateTapImpl::getA12() {

  return a12;
}


float CoordinateTapImpl::getA22() {

  return a22;
}


void CoordinateTapImpl::setName(unsigned char* name) {

  this->name=name;
}


