#include "all.h"

#include "aboutscreen.h"

AboutScreen::~AboutScreen() {

}

AboutScreen::AboutScreen() {
	back = 0;

  id=3;
}


void AboutScreen::activate() {

  Screen::activate();
  Thing** things=Globals::getAllThings();
  int t=0;
  things[t++]=new ImageThing((unsigned char*)"background.png",(int)(Globals::getWidth()),(int)(Globals::getHeight()));
  things[t]=new ImageThing((unsigned char*)"willi.jpg",(int)(600 * Globals::getScale()),(int)(600 * Globals::getScale()));
  things[t++]->translate(0,Globals::getH2() + -300 * Globals::getScale(),0);
  things[t]=new ImageThing((unsigned char*)"gradientall800.png",(int)(600 * Globals::getScale()),(int)(600 * Globals::getScale()));
  things[t++]->translate(0,Globals::getH2() + -300 * Globals::getScale(),0);
  things[t]=new ImageThing((unsigned char*)"name.png",(int)(1.8f * 256 * Globals::getScale()),(int)(1.8f * 128 * Globals::getScale()));
  things[t++]->translate(0,-Globals::getH2() + 156 * Globals::getScale(),0);
  TextThing* t1=new TextThing((unsigned char*)"by digitalemil",0,(int)(-Globals::getH2() + 320 * Globals::getScale()),Text::TEXT_CENTER,24,0xFF592b13);
  things[t++]=t1;
  sprintf((char *)tmptextbuffer, (const char*)(unsigned char*)"Version: %5.5f", Modell::getVersion());//=(unsigned char*)"Version " + Modell::getVersion();
  t1=new TextThing(tmptextbuffer,0,(int)(-Globals::getH2() + 440 * Globals::getScale()),Text::TEXT_CENTER,24,0xFF592b13);
  things[t++]=t1;
  back=t;
  things[t]=new ImageThing((unsigned char*)"back.png",(int)(1.0f * 256 * Globals::getScale()),(int)(1.0f * 128 * Globals::getScale()));
  things[t++]->translate(0,-Globals::getH2() + 660 * Globals::getScale(),0);
  things[t]=new ImageThing((unsigned char*)"ribbonright.png",128 * Globals::getScale(),Globals::getHeight());
  things[t++]->translate(Globals::getW2() - 128 * Globals::getScale() / 2 + 1,0,0);
  things[t]=new ImageThing((unsigned char*)"ribbonleft.png",128 * Globals::getScale(),Globals::getHeight());
  things[t++]->translate(-Globals::getW2() + 128 * Globals::getScale() / 2 - 1,0,0);
  numberOfThings=t;
}


int AboutScreen::getBackgroundColor() {

  return 0xFF009c5a;
}


bool AboutScreen::touchStart(int x, int y) {

  Thing** things=Globals::getAllThings();
  if (things[back]->isIn(x,y)) {
    nextscreen=2;
  }
  return true;
}


