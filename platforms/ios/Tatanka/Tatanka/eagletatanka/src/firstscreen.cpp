#include "all.h"

#include "firstscreen.h"

FirstScreen::~FirstScreen() {

}

FirstScreen::FirstScreen() {
	hunt = 0;
	cont = 0;
	opts = 0;
	more = 0;

  id=0;
}


void FirstScreen::activate() {

  Screen::activate();
  Thing** things=Globals::getAllThings();
  int t=0;
  things[t++]=new ImageThing((unsigned char*)"background.png",(int)(Globals::getWidth()),(int)(Globals::getHeight()));
  int i=Part::getRandom(0,8);
  sprintf((char *)tmptextbuffer, (const char*)(unsigned char*)"tatanka%i.jpg", i); // tmptextbuffer=(unsigned char*)"tatanka" + i + (unsigned char*)".jpg";
  things[t]=new ImageThing((unsigned char*)"gradientall800.png",(int)(600 * Globals::getScale()),(int)(600 * Globals::getScale()));
  things[t++]->translate(0,Globals::getH2() + -300 * Globals::getScale(),0);
  things[t]=new ImageThing((unsigned char*)"name.png",(int)(1.8f * 256 * Globals::getScale()),(int)(1.8f * 128 * Globals::getScale()));
  things[t++]->translate(0,-Globals::getH2() + 156 * Globals::getScale(),0);
  hunt=t;
  things[t]=new ImageThing((unsigned char*)"hunt.png",(int)(1.0f * 256 * Globals::getScale()),(int)(1.0f * 128 * Globals::getScale()));
  things[t++]->translate(0,-Globals::getH2() + 320 * Globals::getScale(),0);
  cont=t;
  things[t]=new ImageThing((unsigned char*)"continue.png",(int)(1.0 * 256 * Globals::getScale()),(int)(1.0f * 128 * Globals::getScale()));
  things[t++]->translate(0,-Globals::getH2() + 440 * Globals::getScale(),0);
  opts=t;
  things[t]=new ImageThing((unsigned char*)"options.png",(int)(1.0f * 256 * Globals::getScale()),(int)(1.0f * 128 * Globals::getScale()));
  things[t++]->translate(0,-Globals::getH2() + 550 * Globals::getScale(),0);
  more=t;
  things[t]=new ImageThing((unsigned char*)"more.png",(int)(1.0f * 256 * Globals::getScale()),(int)(1.0f * 128 * Globals::getScale()));
  things[t++]->translate(0,-Globals::getH2() + 660 * Globals::getScale(),0);
  things[t]=new ImageThing((unsigned char*)"ribbonright.png",128 * Globals::getScale(),Globals::getHeight());
  things[t++]->translate(Globals::getW2() - 128 * Globals::getScale() / 2 + 1,0,0);
  things[t]=new ImageThing((unsigned char*)"ribbonleft.png",128 * Globals::getScale(),Globals::getHeight());
  things[t++]->translate(-Globals::getW2() + 128 * Globals::getScale() / 2 - 1,0,0);
  numberOfThings=t;
}


int FirstScreen::getBackgroundColor() {

  return 0xFF009c5a;
}


bool FirstScreen::touchStart(int x, int y) {

  Thing** things=Globals::getAllThings();
  if (things[hunt]->isIn(x,y)) {
    nextscreen=1;
  }
  if (things[more]->isIn(x,y)) {
    nextscreen=2;
  }
  return true;
}


