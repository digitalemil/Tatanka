#include "all.h"

#include "arrow.h"

Arrow::~Arrow() {
	finalize();

}

Arrow::Arrow() : Thing(1) {
	arrowAnimation = 0;

  //Thing::1);
  int brown=0xff705125;
  int white=0xffffffff;
  int stone=0xff404040;
  setName((unsigned char*)"Arrow");
  Bone* arrow=new Bone(0,-17,0,0,5);
  arrow->setName((unsigned char*)"arrow");
  Part* p=new Triangle(0,0,0,-2,8,0,0,2,8,0,stone);
  p->setName((unsigned char*)"stone");
  arrow->addPart(p);
  p=new Rectangle(1,20,0,18,0,0,brown);
  arrow->addPart(p);
  p=new Triangle(0,0,0,-2,29,-2,34,1,28,0,white);
  arrow->addPart(p);
  p=new Triangle(0,0,0,2,29,2,34,-1,28,0,white);
  arrow->addPart(p);
  p=new BoundingCircle(1,0,8,0);
  p->setName((unsigned char*)"BC");
  p->setCoordinateTap(new CoordinateTapImpl((unsigned char*)"BCArrow"));
  arrow->addPart(p);
  arrow->scaleRoot(2,2);
  arrow->setupDone();
  addPart(arrow);
  setupDone();
  arrowAnimation=new PartAnimation();
}


void Arrow::finalize() {

  delete arrowAnimation;
}


PartAnimation* Arrow::getArrowAnimation() {

  return arrowAnimation;
}


int Arrow::getType() {

  return TatankaTypes::ARROW;
}


void Arrow::setRootRotation(float f) {

  invalidateData();
  rrot=f;
}


float* Arrow::getThingData() {

  return Thing::getThingData();
}


void Arrow::setVisible(bool visible) {

  Thing::setVisibility(visible);
  translate(-getX() + 10000.0f,-getY(),0);
}


