#include "all.h"

#include "ellipse.h"

Ellipse::~Ellipse() {

}

void Ellipse::beginTX() {

  Part::beginTX();
  tx_r1=r1;
  tx_r2=r2;
  tx_sr1=sr1;
  tx_sr2=sr2;
}


void Ellipse::rollbackTX() {

  Part::rollbackTX();
  r1=tx_r1;
  r2=tx_r2;
  sr1=tx_sr1;
  sr2=tx_sr2;
}


void Ellipse::slowDevice() {

  TRIANGLES8=8;
  TRIANGLES10=10;
  TRIANGLES12=10;
  TRIANGLES20=12;
  TRIANGLES24=12;
}


int Ellipse::getNumberOfTriangles() {

  return triangles;
}


int Ellipse::getMaxeff() {

  return maxeff;
}


int* Ellipse::getMycs() {

  return mycs;
}


float Ellipse::getR1() {

  return r1;
}


float Ellipse::getR2() {

  return r2;
}


Ellipse::Ellipse(float ir1, float ir2, float irx, float iry, float irz, float irotation, int itriangles, int icolor) {
	triangles = 0;
	maxeff = 0;
	sr1 = 0;
	sr2 = 0;
	r1 = 0;
	r2 = 0;
	tx_r1 = 0;
	tx_r2 = 0;
	tx_sr1 = 0;
	tx_sr2 = 0;

  //Part::);
  setRoot(irx,iry,irz,irotation);
  setColor(icolor);
  triangles=itriangles;
  maxeff=itriangles;
  setRadius(ir1,ir2);
  sr1=ir1;
  sr2=ir2;
}


float Ellipse::getRadius() {

  return r1;
}


void Ellipse::setRadius(float ir1, float ir2) {

  invalidateData();
  r1=ir1;
  r2=ir2;
}


float Ellipse::getXScale() {

  return r1 / sr1;
}


float Ellipse::getYScale() {

  return r2 / sr2;
}


void Ellipse::clearScale() {

  invalidateData();
  r1=sr1;
  r2=sr2;
}


int Ellipse::getNumberOfData() {

  return 4 + 2 * (maxeff + 1);
}


int Ellipse::getData(float* d, int startD, float xn, float yn, float zn, float a11, float a21, float a12, float a22) {

  if (!invaliddata)   return getNumberOfData();
  invaliddata=false;
  int n=startD;
  d[n++]=getType();
  d[n++]=maxeff;
  d[n++]=(a << 24) | (r << 16) | (g << 8)| b;
  d[n++]=(maxeff == triangles) ? 1 : 0;
  int phi=calcPhi(rot + rrot);
  float sinbeta=Part::mysin[phi];
  float cosbeta=Part::mycos[phi];
  float dummy;
  float dummy2;
  float rx1=rx + x;
  float ry1=ry + y;
  dummy=rx1;
  dummy2=ry1;
  d[n]=round(dummy * a11 + dummy2 * a12 + xn);
  d[n + 1]=round(dummy * a21 + dummy2 * a22 + yn);
  if (this->coordtap != 0)   this->coordtap->save(d[n],d[n + 1],phi,0,0,0,0);
  n+=2;
  for (int i=0; i < maxeff; i++, n+=2) {
    float sinalpha=mysin[mycs[triangles] * 24 + i];
    float cosalpha=mycos[mycs[triangles] * 24 + i];
    dummy=rx1 + (r1 * cosalpha * cosbeta - r2 * sinalpha * sinbeta);
    dummy2=ry1 + (r1 * cosalpha * sinbeta + r2 * sinalpha * cosbeta);
    d[n]=round(dummy * a11 + dummy2 * a12 + xn);
    d[n + 1]=round(dummy * a21 + dummy2 * a22 + yn);
  }
  return getNumberOfData();
}


void Ellipse::scale(float sx, float sy) {

  invalidateData();
  setRadius(r1 * sx,r2 * sy);
}


void Ellipse::scaleRoot(float sx, float sy) {

  invalidateData();
  rsx*=sx;
  rsy*=sy;
  setRadius(r1 * sx,r2 * sy);
}


int Ellipse::getType() {

  return Types::ELLIPSE;
}


void Ellipse::reset() {

  setRadius(rsx * sr1,rsy * sr2);
  Part::reset();
}


void Ellipse::setMaxEff(int m) {

  invalidateData();
  maxeff=m + 1;
}


int Ellipse::TRIANGLES8 = 12;
int Ellipse::TRIANGLES10 = 20;
int Ellipse::TRIANGLES12 = 20;
int Ellipse::TRIANGLES20 = 20;
int Ellipse::TRIANGLES24 = 24;
int Ellipse::TRIANGLES0 = 0;
float Ellipse::mysin[] = {0.0f,0.70710677f,1.0f,0.70710677f,-8.742278E-8f,-0.7071069f,-1.0f,-0.70710653f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.58778524f,0.95105654f,0.9510565f,0.5877852f,-8.742278E-8f,-0.58778536f,-0.9510565f,-0.9510565f,-0.58778495f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.5f,0.86602545f,1.0f,0.8660254f,0.50000006f,-8.742278E-8f,-0.5000002f,-0.86602545f,-1.0f,-0.86602545f,-0.49999976f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.309017f,0.58778524f,0.809017f,0.95105654f,1.0f,0.9510565f,0.809017f,0.5877852f,0.3090168f,-8.742278E-8f,-0.30901697f,-0.58778536f,-0.8090171f,-0.9510565f,-1.0f,-0.9510565f,-0.8090168f,-0.58778495f,-0.30901694f,0.0f,0.0f,0.0f,0.0f,0.0f,0.25881904f,0.5f,0.70710677f,0.86602545f,0.9659258f,1.0f,0.9659258f,0.8660254f,0.70710677f,0.50000006f,0.25881892f,-8.742278E-8f,-0.25881907f,-0.5000002f,-0.7071069f,-0.86602545f,-0.9659259f,-1.0f,-0.9659258f,-0.86602545f,-0.70710653f,-0.49999976f,-0.25881884f};
float Ellipse::mycos[] = {1.0f,0.70710677f,-4.371139E-8f,-0.70710677f,-1.0f,-0.70710665f,1.1924881E-8f,0.707107f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,1.0f,0.809017f,0.30901697f,-0.30901703f,-0.80901706f,-1.0f,-0.80901694f,-0.3090171f,0.30901712f,0.80901724f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,1.0f,0.8660254f,0.49999997f,-4.371139E-8f,-0.50000006f,-0.8660254f,-1.0f,-0.86602527f,-0.4999999f,1.1924881E-8f,0.4999999f,0.86602557f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,1.0f,0.95105654f,0.809017f,0.58778524f,0.30901697f,-4.371139E-8f,-0.30901703f,-0.5877852f,-0.80901706f,-0.9510566f,-1.0f,-0.95105654f,-0.80901694f,-0.58778507f,-0.3090171f,1.1924881E-8f,0.30901712f,0.5877855f,0.80901724f,0.95105654f,0.0f,0.0f,0.0f,0.0f,1.0f,0.9659258f,0.8660254f,0.70710677f,0.49999997f,0.25881907f,-4.371139E-8f,-0.25881916f,-0.50000006f,-0.70710677f,-0.8660254f,-0.9659259f,-1.0f,-0.9659258f,-0.86602527f,-0.70710665f,-0.4999999f,-0.25881898f,(float)1.1924881E-8,0.258819f,0.4999999f,0.707107f,0.86602557f,0.9659259f};
int Ellipse::mycs[] = {0,0,0,0,0,0,0,0,0,1,1,2,2,3,3,3,3,3,3,3,3,4,4,4,4};
