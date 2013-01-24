#include "all.h"

#include "rectangle.h"

Rectangle::~Rectangle() {

}

Rectangle::Rectangle(float w, float h, float irx, float iry, float irz, float ir, int ic) {
	width = 0;
	height = 0;
	tx_width = 0;
	tx_height = 0;

  init(w,h,irx,iry,irz,ir,ic);
}


void Rectangle::beginTX() {

  Part::beginTX();
  tx_width=width;
  tx_height=height;
}


void Rectangle::rollbackTX() {

  Part::rollbackTX();
  width=tx_width;
  height=tx_height;
}


void Rectangle::init(float w, float h, float irx, float iry, float irz, float ir, int ic) {

  setRoot(irx,iry,irz,ir);
  width=w / 2;
  height=h / 2;
  sx=1;
  sy=1;
  setColor(ic);
}


int Rectangle::getNumberOfData() {

  return 4 + 2 * 5;
}


int Rectangle::getData(float* d, int startD, float xn, float yn, float zn, float a11, float a21, float a12, float a22) {

  int n=startD;
  d[n++]=getType();
  d[n++]=4;
  d[n++]=(a << 24) | (r << 16) | (g << 8)| b;
  d[n++]=1;
  n+=2;
  int phi=calcPhi(rot + rrot);
  float sinbeta=mysin[phi];
  float cosbeta=mycos[phi];
  float dummy;
  float dummy2;
  float rx1=0;
  float ry1=0;
  for (int i=0; i < 4; i++) {
switch (i) {
case 0:
      rx1=-width * sx;
    ry1=height * sy;
  break;
case 1:
ry1=-height * sy;
break;
case 2:
rx1=width * sx;
break;
case 3:
ry1=height * sy;
break;
}
dummy=rx1 * cosbeta - ry1 * sinbeta + rx + x;
dummy2=rx1 * sinbeta + ry1 * cosbeta + ry + y;
d[n]=round(dummy * a11 + dummy2 * a12 + xn);
d[n + 1]=round(dummy * a21 + dummy2 * a22 + yn);
n+=2;
}
return getNumberOfData();
}


void Rectangle::scale(float isx, float isy) {

  width*=isx;
  height*=isy;
}


void Rectangle::setDimension(float w, float h) {

  width=w / 2;
  height=h / 2;
}


int Rectangle::getType() {

  return Types::RECTANGLE;
}


