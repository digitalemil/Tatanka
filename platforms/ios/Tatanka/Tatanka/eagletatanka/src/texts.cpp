#include "all.h"

#include "texts.h"

Texts::~Texts() {

}

Texts::Texts() : Thing(3) {
	t1 = 0;
	t2 = 0;
	t3 = 0;

  //Thing::3);
  float s=Globals::getScale();
  t1=new Text((unsigned char*)"0",0,-60 * s,0x80000000);
  t1->setSize((int)(36 * s));
  t1->setAlignment(Text::TEXT_LEFT);
  addPart(t1);
  t2=new Text((unsigned char*)"30",0,-120 * s,0x80000000);
  t2->setSize((int)(36 * s));
  t2->setAlignment(Text::TEXT_LEFT);
  addPart(t2);
  t3=new Text((unsigned char*)"3",0,-180 * s,0x80000000);
  t3->setSize((int)(36 * s));
  t3->setAlignment(Text::TEXT_LEFT);
  addPart(t3);
  setupDone();
}


void Texts::setArrows(int a) {

  sprintf((char*)tmptextbuffer, (const char*)(unsigned char*)"%i", a);t2->setText(tmptextbuffer);//->setText((unsigned char*)"" + a);
}


