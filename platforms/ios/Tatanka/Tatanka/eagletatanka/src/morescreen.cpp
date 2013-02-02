#include "all.h"

#include "morescreen.h"

MoreScreen::~MoreScreen() {

}

MoreScreen::MoreScreen() {
	back = 0;
	gallery = 0;
	about = 0;
	help = 0;

  id=2;
}


void MoreScreen::activate() {

  Screen::activate();
  Thing** things=Globals::getAllThings();
  int t=0;
  things[t++]=new ImageThing((unsigned char*)"background.png",(int)(Globals::getWidth()),(int)(Globals::getHeight()));
  int i=Part::getRandom(0,8);
  sprintf((char *)tmptextbuffer, (const char*)(unsigned char*)"tatanka%i.jpg", i); // tmptextbuffer=(unsigned char*)"tatanka" + i + (unsigned char*)".jpg";
  things[t]=new ImageThing(tmptextbuffer,(int)(600 * Globals::getScale()),(int)(600 * Globals::getScale()));
  things[t++]->translate(0,Globals::getH2() + -300 * Globals::getScale(),0);
  things[t]=new ImageThing((unsigned char*)"gradientall800.png",(int)(600 * Globals::getScale()),(int)(600 * Globals::getScale()));
  things[t++]->translate(0,Globals::getH2() + -300 * Globals::getScale(),0);
  things[t]=new ImageThing((unsigned char*)"name.png",(int)(1.8f * 256 * Globals::getScale()),(int)(1.8f * 128 * Globals::getScale()));
  things[t++]->translate(0,-Globals::getH2() + 156 * Globals::getScale(),0);
  gallery=t;
  things[t]=new ImageThing((unsigned char*)"gallery.png",(int)(1.0f * 256 * Globals::getScale()),(int)(1.0f * 128 * Globals::getScale()));
  things[t++]->translate(0,-Globals::getH2() + 320 * Globals::getScale(),0);
  about=t;
  things[t]=new ImageThing((unsigned char*)"about.png",(int)(1.0 * 256 * Globals::getScale()),(int)(1.0f * 128 * Globals::getScale()));
  things[t++]->translate(0,-Globals::getH2() + 440 * Globals::getScale(),0);
  help=t;
  things[t]=new ImageThing((unsigned char*)"help.png",(int)(1.0f * 256 * Globals::getScale()),(int)(1.0f * 128 * Globals::getScale()));
  things[t++]->translate(0,-Globals::getH2() + 550 * Globals::getScale(),0);
  back=t;
  things[t]=new ImageThing((unsigned char*)"back.png",(int)(1.0f * 256 * Globals::getScale()),(int)(1.0f * 128 * Globals::getScale()));
  things[t++]->translate(0,-Globals::getH2() + 660 * Globals::getScale(),0);
  things[t]=new ImageThing((unsigned char*)"ribbonright.png",128 * Globals::getScale(),Globals::getHeight());
  things[t++]->translate(Globals::getW2() - 128 * Globals::getScale() / 2 + 1,0,0);
  things[t]=new ImageThing((unsigned char*)"ribbonleft.png",128 * Globals::getScale(),Globals::getHeight());
  things[t++]->translate(-Globals::getW2() + 128 * Globals::getScale() / 2 - 1,0,0);
  numberOfThings=t;
}


int MoreScreen::getBackgroundColor() {

  return 0xFF009c5a;
}


bool MoreScreen::touchStart(int x, int y) {

  Thing** things=Globals::getAllThings();
  if (things[back]->isIn(x,y)) {
    nextscreen=0;
  }
  if (things[about]->isIn(x,y)) {
    nextscreen=3;
  }
  if (things[gallery]->isIn(x,y)) {
    nextscreen=4;
  }
  return true;
}


