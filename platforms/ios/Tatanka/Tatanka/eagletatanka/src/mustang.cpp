#include "all.h"

#include "mustang.h"

Mustang::~Mustang() {

}

Mustang::Mustang() : Thing(7) {
	animation = 0;

  //Thing::7);
  int brown=0xff3a270c;
  int brown2=0xff705125;
  int huf2=0xff404040;
  int white=0xffffffff;
  int white2=0xffE0E0E0;
  this->setName((unsigned char*)"Mustang");
  Bone* head=new Bone(0,-42,16,0,7);
  head->setName((unsigned char*)"Head");
  Ellipse* el=new Ellipse(8,12,0,-20,-3,0,Ellipse::TRIANGLES8,white);
  el->setName((unsigned char*)"h1");
  head->addPart(el);
  el=new Ellipse(8,12,0,-34,0,-2,Ellipse::TRIANGLES8,white);
  el->setName((unsigned char*)"h2");
  head->addPart(el);
  el=new Ellipse(2,3,-4,-40,-1,20,Ellipse::TRIANGLES8,brown);
  el->setName((unsigned char*)"NuesterLinks");
  head->addPart(el);
  el=new Ellipse(2,3,4,-40,-1,-20,Ellipse::TRIANGLES8,brown);
  el->setName((unsigned char*)"NuesterRechts");
  head->addPart(el);
  el=new Ellipse(12,12,0,-10,-1,0,Ellipse::TRIANGLES8,white);
  el->setName((unsigned char*)"Schaedel");
  head->addPart(el);
  el=new Ellipse(3,5,-8,-10,0,-15,Ellipse::TRIANGLES8,white2);
  el->setName((unsigned char*)"LinkesOhr");
  head->addPart(el);
  el=new Ellipse(3,5,8,-10,0,15,Ellipse::TRIANGLES8,white2);
  el->setName((unsigned char*)"RechtesOhr");
  head->addPart(el);
  head->setupDone();
  head->rotate(0);
  Bone* body=new Bone(0,0,0,0,10);
  body->setName((unsigned char*)"Body");
  Bone* leg=new Bone(0,-46,0,0,2);
  leg->setName((unsigned char*)"LeftForeleg");
  el=new Ellipse(4,8,-16,-6,-1,0,Ellipse::TRIANGLES8,huf2);
  leg->addPart(el);
  el->setName((unsigned char*)"LeftForelegPart1");
  el=new Ellipse(4,10,-15,0,0,0,Ellipse::TRIANGLES8,white2);
  leg->addPart(el);
  el->setName((unsigned char*)"LeftForelegPart2");
  leg->setupDone();
  body->addPart(leg);
  leg=new Bone(0,-46,0,0,2);
  leg->setName((unsigned char*)"RightForeleg");
  el=new Ellipse(4,8,16,-6,-1,0,Ellipse::TRIANGLES8,huf2);
  leg->addPart(el);
  el->setName((unsigned char*)"RightForelegPart1");
  el=new Ellipse(4,10,15,-0,0,0,Ellipse::TRIANGLES8,white2);
  leg->addPart(el);
  el->setName((unsigned char*)"RightForelegPart2");
  leg->setupDone();
  body->addPart(leg);
  leg=new Bone(0,32,0,0,2);
  leg->setName((unsigned char*)"RightHindleg");
  el=new Ellipse(4,8,16,0,-1,0,Ellipse::TRIANGLES8,huf2);
  leg->addPart(el);
  el->setName((unsigned char*)"RightHindlegPart1");
  el=new Ellipse(4,10,15,6,0,0,Ellipse::TRIANGLES8,white2);
  leg->addPart(el);
  el->setName((unsigned char*)"RightHindlegPart2");
  leg->setupDone();
  body->addPart(leg);
  leg=new Bone(0,32,0,0,2);
  leg->setName((unsigned char*)"LeftHindleg");
  el=new Ellipse(4,8,-16,0,-1,0,Ellipse::TRIANGLES8,huf2);
  leg->addPart(el);
  el->setName((unsigned char*)"LeftHindlegPart1");
  el=new Ellipse(4,10,-15,6,0,0,Ellipse::TRIANGLES8,white2);
  leg->addPart(el);
  el->setName((unsigned char*)"LeftHindlegPart2");
  leg->setupDone();
  body->addPart(leg);
  el=new Ellipse(22,30,0,-30,0,0,Ellipse::TRIANGLES8,white);
  el->setName((unsigned char*)"Rumpf");
  body->addPart(el);
  el=new Ellipse(20,40,0,0,0,0,Ellipse::TRIANGLES8,white);
  el->setName((unsigned char*)"Rumpf");
  body->addPart(el);
  el=new Ellipse(22,26,0,30,0,0,Ellipse::TRIANGLES8,white);
  el->setName((unsigned char*)"Rumpf");
  body->addPart(el);
  el=new Ellipse(8,12,10,-30,0,10,Ellipse::TRIANGLES8,brown2);
  el->setName((unsigned char*)"Fleck1");
  body->addPart(el);
  el=new Ellipse(10,14,-4,24,0,-10,Ellipse::TRIANGLES8,brown2);
  el->setName((unsigned char*)"Fleck2");
  body->addPart(el);
  el=new Ellipse(10,16,0,58,0,0,Ellipse::TRIANGLES8,brown2);
  el->setName((unsigned char*)"Tail");
  body->addPart(el);
  body->setupDone();
  Bone* rest=new Bone(0,-24,0,0,4);
  rest->setName((unsigned char*)"Rest");
  rest->addPart(head);
  el=new Ellipse(10,32,0,-28,-2,0,Ellipse::TRIANGLES8,white);
  el->setName((unsigned char*)"Throat");
  rest->addPart(el);
  el=new Ellipse(32,16,2,-22,-2,90,Ellipse::TRIANGLES10,brown2);
  el->setName((unsigned char*)"Maehne");
  el->setMaxEff(10);
  rest->addPart(el);
  el=new Ellipse(28,10,-4,-26,-2,-90,Ellipse::TRIANGLES8,brown2);
  el->setName((unsigned char*)"Maehne");
  el->setMaxEff(10);
  rest->addPart(el);
  rest->setupDone();
  body->scaleRoot(1.0f,1.0f);
  rest->scaleRoot(1.0f,1.0f);
  Part* bc=new BoundingCircle(24,0,30,0);
  bc->setCoordinateTap(new CoordinateTapImpl());
  this->addPart(bc);
  bc=new BoundingCircle(28,0,-20,0);
  bc->setCoordinateTap(new CoordinateTapImpl());
  this->addPart(bc);
  bc=new BoundingCircle(8,0,-96,0);
  bc->setCoordinateTap(new CoordinateTapImpl());
  this->addPart(bc);
  bc=new BoundingCircle(14,0,-72,0);
  bc->setCoordinateTap(new CoordinateTapImpl());
  this->addPart(bc);
  bc=new BoundingCircle(18,0,-52,0);
  bc->setCoordinateTap(new CoordinateTapImpl());
  this->addPart(bc);
  this->addPart(body);
  this->addPart(rest);
  this->setupDone();
  animation=new AnimalAnimation(this,16,1000);
  animation->startRun();
}


void Mustang::animate() {

  animation->animate();
}


int Mustang::getType() {

  return TatankaTypes::MUSTANG;
}


