#include "all.h"

#include "bone.h"

Bone::~Bone() {

}

void Bone::setData(float* data) {

  this->data=data;
}


float* Bone::getData() {

  return data;
}


Part** Bone::getParts() {

  return parts;
}


int Bone::getNumberOfParts() {

  return pn;
}


unsigned char** Bone::getTextAndFont() {

  return textAndFont;
}


void Bone::setTextAndFont(unsigned char** taf) {

  textAndFont=taf;
}


int Bone::getTextAndFont(unsigned char** t, int startT) {

  int st=startT;
  for (int i=0; i < pn; i++) {
    st+=parts[i]->getTextAndFont(t,st);
  }
  return this->nt;
}


Bone::Bone(float x, float y, float z, float r, int n) {
	data = 0;
	textAndFont = 0;
	parts = 0;
	nd = 0;
	nt = 0;
	maxp = 0;
	bcs = 0;
	pn = 0;
	nbcs = 0;
	visible = true;

  setRoot(x,y,z,r);
  maxp=n;
  parts=(Part**)new void*[maxp];
}


int Bone::getNumberOfBCs() {

  return nbcs;
}


BoundingCircle** Bone::getBCs() {

  if (bcs == 0) {
    bcs=(BoundingCircle**)new void*[nbcs];
    addBCs(bcs,0);
  }
  return bcs;
}


int Bone::addBCs(BoundingCircle** bcarray, int start) {

  for (int i=0; i < this->pn; i++) {
    if (parts[i]->getType() == Types::BOUNDINGCIRCLE) {
      bcarray[start++]=(BoundingCircle*)parts[i];
    }
    if (parts[i]->canHaveChilds()) {
      start=parts[i]->addBCs(bcarray,start);
    }
  }
  return start;
}


void Bone::beginTX() {

  if (!supportsTX())   return;
  Part::beginTX();
  for (int i=0; i < this->pn; i++) {
    if (parts[i]->supportsTX())     parts[i]->beginTX();
  }
}


void Bone::commitTX() {

  if (!supportsTX())   return;
  Part::commitTX();
  for (int i=0; i < this->pn; i++) {
    if (parts[i]->supportsTX())     parts[i]->commitTX();
  }
}


void Bone::rollbackTX() {

  if (!supportsTX())   return;
  Part::rollbackTX();
  for (int i=0; i < this->pn; i++) {
    if (parts[i]->supportsTX())     parts[i]->rollbackTX();
  }
}


bool Bone::isVisible() {

  return visible;
}


void Bone::setVisible(bool visible) {

  this->visible=visible;
}


Part* Bone::getByName(unsigned char* n) {

  Part *result=0;
  if (name != 0 && !strcmp((const char*)n, (const char *)name)) {
    return this;
  }
  for (int i=0; i < pn; i++) {
    unsigned char* pn=parts[i]->name;
    if (pn != 0) {
      if (!strcmp((const char*)n, (const char*)parts[i]->name)) {
        result=parts[i];
        break;
      }
    }
    if (parts[i]->getType() == Types::BONE) {
      result=((Bone*)parts[i])->getByName(n);
    }
    if (result != 0) {
      return result;
    }
  }
  return result;
}


void Bone::add(Part* p, float x, float y, float z, float r) {

  p->translateRoot(x,y,z);
  p->rotate(r);
  p->setParent(this);
  parts[pn++]=p;
  a=p->a;
}


void Bone::addPart(Part* p) {

  add(p,0.0f,0.0f,0.0f,0.0f);
}


int Bone::getNumberOfTextAndFont() {

  return nt;
}


int Bone::getNumberOfData() {

  return nd;
}


bool Bone::canHaveChilds() {

  return true;
}


void Bone::setupDone() {

  nd=0;
  nbcs=0;
  nt=0;
  for (int i=0; i < pn; i++) {
    nd+=parts[i]->getNumberOfData();
    nt+=this->parts[i]->getNumberOfTextAndFont();
    if (parts[i]->getType() == Types::BOUNDINGCIRCLE)     nbcs++;
    if (parts[i]->canHaveChilds()) {
      nbcs+=parts[i]->getNumberOfBCs();
    }
  }
  invalidateData();
}


int Bone::getData(float* d, int startD, float xn, float yn, float zn, float a11, float a21, float a12, float a22) {

  if (!invaliddata)   return getNumberOfData();
  int sv=startD;
  int phi=round(-(rot + rrot));
  while (phi < 0)   phi+=360;
  phi%=360;
  float cphi=mycos[phi];
  float sphi=mysin[phi];
  float ta11=cphi;
  float ta21=sphi;
  float ta12=-sphi;
  float ta22=cphi;
  xn=xn + a11 * (rx + x) + a12 * (ry + y);
  yn=yn + a21 * (rx + x) + a22 * (ry + y);
  if (coordtap != 0)   coordtap->save(xn,yn,phi,a11,a21,a12,a22);
  float na11=(ta11 * a11 + ta12 * a21) * sx * rsx;
  float na12=(ta11 * a12 + ta12 * a22) * sy * rsy;
  float na21=(ta21 * a11 + ta22 * a21) * sx * rsx;
  float na22=(ta21 * a12 + ta22 * a22) * sy * rsy;
  if (!visible) {
    xn=-100000;
  }
  for (int i=0; i < pn; i++) {
    sv+=parts[i]->getData(d,sv,xn,yn,rz + z + zn,na11,na21,na12,na22);
  }
  return nd;
}


bool Bone::invalidateData() {

  bool ret=invaliddata;
  invaliddata=true;
  if (parent != 0)   parent->invaliddata=true;
  for (int i=0; i < pn; i++) {
    parts[i]->invalidateData();
  }
  return ret;
}


void Bone::setVisibility(bool v) {

  visible=v;
  invalidateData();
}


void Bone::setColor(int c) {

  invalidateData();
  a=(c >> 24);
  r=((c & 0x00FF0000) >> 16);
  g=((c & 0x0000FF00) >> 8);
  b=((c & 0x000000FF));
  for (int i=0; i < pn; i++) {
    parts[i]->setColor(c);
  }
}


void Bone::setAlpha(int c) {

  invalidateData();
  a=c;
  for (int i=0; i < pn; i++) {
    parts[i]->setAlpha(c);
  }
}


int Bone::getType() {

  return Types::BONE;
}


void Bone::highlight(bool b) {

  invalidateData();
  highlighted=b;
  for (int i=0; i < pn; i++) {
    parts[i]->highlight(b);
  }
}


void Bone::reset() {

  invalidateData();
  Part::reset();
  for (int i=0; i < pn; i++) {
    parts[i]->reset();
  }
}


