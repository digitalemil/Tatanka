#include "all.h"

#include "rope.h"

Rope::~Rope() {

}

Rope::Rope() : Thing(1) {

  //Thing::1);
  int lightbrown=0xffae9879;
  this->name=(unsigned char*)"Rope";
  Bone* container=new Bone(0,0,0,0,1);
  container->setName((unsigned char*)"container");
  Part* p=new Rectangle(2,40,0,-20,0,0,lightbrown);
  container->addPart(p);
  Loop* loop=new Loop(40,32,36,28,0,-60,0,0,Ellipse::TRIANGLES20,lightbrown);
  loop->setName((unsigned char*)"Loop");
  container->addPart(loop);
  container->setupDone();
  addPart(container);
  setupDone();
}


int Rope::getType() {

  return TatankaTypes::ROPE;
}


