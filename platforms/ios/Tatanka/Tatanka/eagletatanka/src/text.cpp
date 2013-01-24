#include "all.h"

#include "text.h"

Text::~Text() {
	finalize();

}

Text::Text(unsigned char* t, float irx, float iry, int ic) {
	align = 0;
	tx_align = 0;
	tx_size = 0;
	size = 0;
	nt = 0;
	font = 0;
	text = 0;
	tx_font = 0;
	tx_text = 0;
	isTextSet = false;

  setRoot(irx,iry,0,0);
  setColor(ic);
  tx_text=t;
  tx_size=12;
  tx_font=(unsigned char*)"";
  setText(t);
  size=12;
  font=(unsigned char*)"";
  align=TEXT_CENTER;
}


void Text::beginTX() {

  Part::beginTX();
  tx_text=text;
  tx_size=size;
  tx_font=font;
  tx_align=align;
}


void Text::rollbackTX() {

  Part::rollbackTX();
  text=tx_text;
  size=tx_size;
  font=tx_font;
  align=tx_align;
}


int Text::getNumberOfData() {

  return 8;
}


int Text::getTextAndFont(unsigned char** t, int startT) {

  int n=startT;
  t[n++]=text;
  t[n++]=font;
  return 2;
}


int Text::getNumberOfTextAndFont() {

  return 2;
}


int Text::getData(float* d, int startD, float xn, float yn, float zn, float a11, float a21, float a12, float a22) {

  int n=startD;
  d[n++]=getType();
  d[n++]=8;
  d[n++]=(a << 24) | (r << 16) | (g << 8)| b;
  d[n++]=1;
  float dummy;
  float dummy2;
  float rx1=rx + x;
  float ry1=ry + y;
  dummy=rx1;
  dummy2=ry1;
  d[n]=round(dummy * a11 + dummy2 * a12 + xn);
  d[n + 1]=round(dummy * a21 + dummy2 * a22 + yn);
  d[n + 2]=a11 * size;
  d[n + 3]=align;
  return getNumberOfData();
}


void Text::setText(unsigned char* t) {

     if(isTextSet) free(text); text=(unsigned char *)malloc((size_t)(strlen((const char*)t)+1)); strcpy((char*)text, (const char*)t); isTextSet= true;
  invalidateData();
}


void Text::setSize(int s) {

  size=s;
  invalidateData();
}


void Text::setFont(unsigned char* f) {

  font=f;
  invalidateData();
}


void Text::setAlignment(int a) {

  if (a >= TEXT_LEFT && a <= TEXT_CENTER) {
    align=a;
    invalidateData();
  }
}


unsigned char* Text::getText() {

  return text;
}


int Text::getSize() {

  return size;
}


unsigned char* Text::getFont() {

  return font;
}


int Text::getType() {

  return Types::TEXT;
}


int Text::getAlignment() {

  return align;
}


void Text::finalize() {

  if(isTextSet) free(text);
}


