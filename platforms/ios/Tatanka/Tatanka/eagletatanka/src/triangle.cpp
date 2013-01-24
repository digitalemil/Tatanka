#include "all.h"

#include "triangle.h"

Triangle::~Triangle() {

}

Triangle::Triangle(float rx, float ry, float rz, float px1, float py1, float px2, float py2, float px3, float py3, float pr, int color) {
	x1 = 0;
	y1 = 0;
	x2 = 0;
	y2 = 0;
	x3 = 0;
	y3 = 0;
	tx_x1 = 0;
	tx_y1 = 0;
	tx_x2 = 0;
	tx_y2 = 0;
	tx_x3 = 0;
	tx_y3 = 0;

  setRoot(rx,ry,rz,pr);
  setColor(color);
  x1=px1;
  y1=py1;
  x2=px2;
  y2=py2;
  x3=px3;
  y3=py3;
}


void Triangle::beginTX() {

  Part::beginTX();
  tx_x1=x1;
  tx_y1=y1;
  tx_x2=x2;
  tx_y2=y2;
  tx_x3=x3;
  tx_y3=y3;
}


void Triangle::rollbackTX() {

  Part::rollbackTX();
  x1=tx_x1;
  y1=tx_y1;
  x2=tx_x2;
  y2=tx_y2;
  x3=tx_x3;
  y3=tx_y3;
}


int Triangle::getNumberOfData() {

  return 4 + 2 * (3 + 1);
}


int Triangle::getData(float* d, int startD, float xn, float yn, float zn, float a11, float a21, float a12, float a22) {

  int n=startD;
  d[n++]=getType();
  d[n++]=3;
  d[n++]=(a << 24) + (r << 16) + (g << 8)+ b;
  d[n++]=1;
  n+=2;
  int phi=calcPhi(rot + rrot);
  float sinbeta=mysin[phi];
  float cosbeta=mycos[phi];
  float dummy;
  float dummy2;
  if (this->coordtap != 0) {
    float rx1=this->rx + this->x;
    float ry1=this->ry + this->y;
    this->coordtap->save(round(rx1 * a11 + ry1 * a12 + xn),round(rx1 * a21 + ry1 * a22 + yn),0,0,0,0,0);
  }
  dummy=x1 * cosbeta - y1 * sinbeta + rx + x;
  dummy2=x1 * sinbeta + y1 * cosbeta + ry + y;
  d[n]=round(dummy * a11 + dummy2 * a12 + xn);
  d[n + 1]=round(dummy * a21 + dummy2 * a22 + yn);
  dummy=x2 * cosbeta - y2 * sinbeta + rx + x;
  dummy2=x2 * sinbeta + y2 * cosbeta + ry + y;
  d[n + 2]=round(dummy * a11 + dummy2 * a12 + xn);
  d[n + 3]=round(dummy * a21 + dummy2 * a22 + yn);
  dummy=x3 * cosbeta - y3 * sinbeta + rx + x;
  dummy2=x3 * sinbeta + y3 * cosbeta + ry + y;
  d[n + 4]=round(dummy * a11 + dummy2 * a12 + xn);
  d[n + 5]=round(dummy * a21 + dummy2 * a22 + yn);
  return getNumberOfData();
}


int Triangle::getType() {

  return Types::TRIANGLE;
}


