#include "all.h"

#include "firstscreen.h"

FirstScreen::~FirstScreen() {

}

FirstScreen::FirstScreen() {

  id=0;
}


void FirstScreen::activate() {

  Screen::activate();
  Thing** things=Globals::getAllThings();
  things[4]=new ImageThing((unsigned char*)"ribbonleft.png",128 * Globals::getScale(),Globals::getHeight());
  things[4]->translate(-Globals::getW2() + 128 * Globals::getScale() / 2 - 1,0,0);
  things[3]=new ImageThing((unsigned char*)"ribbonright.png",128 * Globals::getScale(),Globals::getHeight());
  things[3]->translate(Globals::getW2() - 128 * Globals::getScale() / 2 + 1,0,0);
  things[2]=new ImageThing((unsigned char*)"name.png",(int)(1.5f * 256 * Globals::getScale()),(int)(1.5 * 128 * Globals::getScale()));
  things[2]->translate(0,-Globals::getH2() + 156 * Globals::getScale(),0);
  things[0]=new ImageThing((unsigned char*)"background.png",(int)(Globals::getWidth()),(int)(Globals::getHeight()));
  int i=Part::getRandom(0,8);
  sprintf((char *)tmptextbuffer, (const char*)(unsigned char*)"tatanka%i.jpg", i); // tmptextbuffer=(unsigned char*)"tatanka" + i + (unsigned char*)".jpg";
  things[1]=new ImageThing(tmptextbuffer,(int)(600 * Globals::getScale()),(int)(600 * Globals::getScale()));
  things[1]->translate(0,Globals::getH2() + -300 * Globals::getScale(),0);
  numberOfThings=5;
}


int FirstScreen::getBackgroundColor() {

  return 0xFF009c5a;
}


bool FirstScreen::touchStart(int x, int y) {

  Thing** things=Globals::getAllThings();
  if (things[2]->isIn(x,y)) {
    nextscreen=1;
  }
  return true;
}


