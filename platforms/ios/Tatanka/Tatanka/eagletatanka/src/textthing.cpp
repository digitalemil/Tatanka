#include "all.h"

#include "textthing.h"

TextThing::~TextThing() {

}

TextThing::TextThing(unsigned char* t, float ix, float iy, int a, int s, int c) : Thing(1) {
	text = 0;

  //Thing::1);
  translate(ix,iy,0);
  text=new Text(t,0,0,c);
  text->setSize(s);
  text->setAlignment(a);
  addPart(text);
  setupDone();
}


void TextThing::setText(unsigned char* t) {

  text->setText(t);
}


