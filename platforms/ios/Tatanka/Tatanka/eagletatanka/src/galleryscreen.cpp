#include "all.h"

#include "galleryscreen.h"

GalleryScreen::~GalleryScreen() {

}

GalleryScreen::GalleryScreen() {
	back = 0;
	img = 0;
	n = 0;
	start = 0;

  id=4;
}


void GalleryScreen::update(long currentTimeMillis) {

  long now=PartAnimation::currentTimeMillis();
  if (now - start < 2000) {
    return;
  }
  Thing** things=Globals::getAllThings();
  n++;
  if (n == 31)   n=0;
  sprintf((char *)tmptextbuffer, (const char*)(unsigned char*)"img%i.jpg", n); //=(unsigned char*)"img" + n + (unsigned char*)".jpg";
  ((ImageThing*)things[img])->setTexName(tmptextbuffer);
  start=now;
}


void GalleryScreen::activate() {

  Screen::activate();
  start=PartAnimation::currentTimeMillis();
  Thing** things=Globals::getAllThings();
  int t=0;
  things[t++]=new ImageThing((unsigned char*)"background.png",(int)(Globals::getWidth()),(int)(Globals::getHeight()));
  img=t;
  things[t]=new ImageThing((unsigned char*)"img0.jpg",(int)(0.8 * 800 * Globals::getScale()),(int)(0.8 * 576 * Globals::getScale()));
  things[t++]->translate(0,Globals::getH2() + -(0.4f * 576) * Globals::getScale(),0);
  things[t]=new ImageThing((unsigned char*)"gradientall800.png",(int)(0.8 * 800 * Globals::getScale()),(int)(0.8 * 576 * Globals::getScale()));
  things[t++]->translate(0,Globals::getH2() + -300 * Globals::getScale(),0);
  things[t]=new ImageThing((unsigned char*)"name.png",(int)(1.8f * 256 * Globals::getScale()),(int)(1.8f * 128 * Globals::getScale()));
  things[t++]->translate(0,-Globals::getH2() + 156 * Globals::getScale(),0);
  back=t;
  things[t]=new ImageThing((unsigned char*)"back.png",(int)(1.0f * 256 * Globals::getScale()),(int)(1.0f * 128 * Globals::getScale()));
  things[t++]->translate(0,-Globals::getH2() + 330 * Globals::getScale(),0);
  TextThing* t1=new TextThing((unsigned char*)"Images by:",0,(int)(-Globals::getH2() + 440 * Globals::getScale()),Text::TEXT_CENTER,24,0xFF592b13);
  things[t++]=t1;
  TextThing* t2=new TextThing((unsigned char*)"Edward Sheriff Curtis",0,(int)(-Globals::getH2() + 550 * Globals::getScale()),Text::TEXT_CENTER,24,0xFF592b13);
  things[t++]=t2;
  things[t]=new ImageThing((unsigned char*)"ribbonright.png",128 * Globals::getScale(),Globals::getHeight());
  things[t++]->translate(Globals::getW2() - 128 * Globals::getScale() / 2 + 1,0,0);
  things[t]=new ImageThing((unsigned char*)"ribbonleft.png",128 * Globals::getScale(),Globals::getHeight());
  things[t++]->translate(-Globals::getW2() + 128 * Globals::getScale() / 2 - 1,0,0);
  numberOfThings=t;
}


int GalleryScreen::getBackgroundColor() {

  return 0xFF009c5a;
}


bool GalleryScreen::touchStart(int x, int y) {

  Thing** things=Globals::getAllThings();
  if (things[back]->isIn(x,y)) {
    nextscreen=2;
  }
  return true;
}


