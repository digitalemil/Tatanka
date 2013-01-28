#include "all.h"

#include "sioux.h"

Sioux::~Sioux() {
	finalize();

}

ShootingLakotaAnimation* Sioux::getShootingAnimation() {

  return shootingAnimation;
}


bool Sioux::shoot(int x, int y) {

  return shootingAnimation->shoot(x,y);
}


Arrow* Sioux::getArrow() {

  return arrow;
}


void Sioux::setFibre(Part* fibre) {

  this->fibre=fibre;
}


Part* Sioux::getFibre() {

  return fibre;
}


void Sioux::finalize() {

  delete shootingAnimation;
}


Sioux::Sioux() : Thing(3) {
	arrowCoords = 0;
	arrow = 0;
	flyingArrow = 0;
	bow = 0;
	leftarm = 0;
	rightarm = 0;
	upperparts = 0;
	body = 0;
	fibre = 0;
	shootingAnimation = 0;

  //Thing::3);
  arrow=new Arrow();
  arrow->translateRoot(0,0,0);
  arrow->setVisibility(false);
  arrow->getByName((unsigned char*)"BC")->setCoordinateTap(0);
  arrow->setCoordinateTap(new CoordinateTapImpl());
  int darkbrown=0xff190d01;
  int lightbrown=0xffae9879;
  int lightbrown2=0xff997959;
  int stone=0xff404040;
  int brown=0xffcc8060;
  int brown2=0xff705125;
  int white=0xffffffff;
  int black=0xFF000000;
  int blue=0xFF0000FF;
  this->setName((unsigned char*)"Sioux");
  Bone* head=new Bone(0.0f,0.0f,0.0f,0.0f,8);
  head->setName((unsigned char*)"Head");
  Ellipse* el=new Ellipse(14,14,0,0,-3,0,Ellipse::TRIANGLES12,black);
  el->setName((unsigned char*)"Schaedel");
  head->addPart(el);
  el=new Ellipse(6,4,0,6,0,-2,Ellipse::TRIANGLES8,blue);
  el->setName((unsigned char*)"");
  head->addPart(el);
  el=new Ellipse(10,3,8,4,-1,-20,Ellipse::TRIANGLES8,white);
  el->setName((unsigned char*)"Feder1");
  head->addPart(el);
  head->setupDone();
  Bone* legs=new Bone(0,0,0,0,4);
  el=new Ellipse(5,8,24,-31,0,15,Ellipse::TRIANGLES8,lightbrown2);
  el->setName((unsigned char*)"RechterMokasin");
  legs->addPart(el);
  el=new Ellipse(8,22,14,-8,0,30,Ellipse::TRIANGLES10,lightbrown);
  el->setName((unsigned char*)"RechtesBein");
  legs->addPart(el);
  el=new Ellipse(5,8,-24,-31,0,-15,Ellipse::TRIANGLES8,lightbrown2);
  el->setName((unsigned char*)"LinkerMokasin");
  legs->addPart(el);
  el=new Ellipse(8,22,-14,-8,0,-30,Ellipse::TRIANGLES10,lightbrown);
  el->setName((unsigned char*)"LinkesBein");
  legs->addPart(el);
  legs->setupDone();
  body=new Bone(0,0,0,0,8);
  body->setName((unsigned char*)"Body");
  el=new Ellipse(12,6,8,12,0,-20,Ellipse::TRIANGLES10,lightbrown);
  body->addPart(el);
  el=new Ellipse(6,3,12,12,0,-20,Ellipse::TRIANGLES8,white);
  body->addPart(el);
  el=new Ellipse(2,4,-14,-10,0,20,Ellipse::TRIANGLES8,darkbrown);
  el->setName((unsigned char*)"Messer");
  body->addPart(el);
  el=new Ellipse(22,12,0,0,0,0,Ellipse::TRIANGLES10,brown);
  el->setName((unsigned char*)"Rumpf");
  body->addPart(el);
  leftarm=new Bone(-22,0,0,10,3);
  leftarm->setName((unsigned char*)"ArmLeft");
  el=new Ellipse(5,16,0,-10,0,0,Ellipse::TRIANGLES10,brown);
  el->setName((unsigned char*)"ArmLeft2");
  leftarm->addPart(el);
  el=new Ellipse(4,6,0,-26,0,0,Ellipse::TRIANGLES10,brown);
  el->setName((unsigned char*)"HandLeft");
  leftarm->addPart(el);
  bow=new Bone(0,-30,0,0,3);
  bow->setName((unsigned char*)"Bow");
  fibre=new Ellipse(1,1,0,15,0,0,Ellipse::TRIANGLES8,white);
  fibre->setName((unsigned char*)"Fibre");
  bow->addPart(fibre);
  el=new Ellipse(2,8,0,8,0,0,Ellipse::TRIANGLES8,darkbrown);
  el->setName((unsigned char*)"Bow1");
  bow->addPart(el);
  bow->setCoordinateTap(new CoordinateTapImpl());
  Bone* bone=new Bone(0,-26,0,0,1);
  bone->addPart(arrow);
  bone->setupDone();
  bow->addPart(bone);
  bow->setupDone();
  leftarm->addPart(bow);
  leftarm->setupDone();
  body->addPart(leftarm);
  rightarm=new Bone(22,0,0,20,2);
  rightarm->setName((unsigned char*)"ArmRight");
  el=new Ellipse(5,16,0,-10,0,0,Ellipse::TRIANGLES10,brown);
  el->setName((unsigned char*)"ArmRight2");
  rightarm->addPart(el);
  el=new Ellipse(4,6,0,-26,0,0,Ellipse::TRIANGLES10,brown);
  el->setName((unsigned char*)"HandRight");
  rightarm->addPart(el);
  rightarm->setupDone();
  body->addPart(rightarm);
  el=new Ellipse(8,8,-18,0,0,0,Ellipse::TRIANGLES10,brown);
  el->setName((unsigned char*)"ShoulderLeft");
  body->addPart(el);
  el=new Ellipse(8,8,18,0,0,0,Ellipse::TRIANGLES10,brown);
  el->setName((unsigned char*)"ShoulderRight");
  body->addPart(el);
  body->setupDone();
  upperparts=new Bone(0,0,0,0,2);
  upperparts->setName((unsigned char*)"rest");
  upperparts->addPart(body);
  upperparts->addPart(head);
  upperparts->setupDone();
  this->addPart(legs);
  this->addPart(upperparts);
  this->setupDone();
  shootingAnimation=new ShootingLakotaAnimation(this);
}


Bone* Sioux::getBody() {

  return body;
}


Bone* Sioux::getRightarm() {

  return rightarm;
}


Bone* Sioux::getUpperparts() {

  return upperparts;
}


Arrow* Sioux::getFlyingArrow() {

  return flyingArrow;
}


void Sioux::setFlyingArrow(Arrow* a) {

  flyingArrow=a;
  flyingArrow->setCollisionHandler(new ArrowCollisionHandler(flyingArrow,Globals::getAllThings()));
}


Bone* Sioux::getBow() {

  return bow;
}


Bone* Sioux::getLeftarm() {

  return leftarm;
}


int Sioux::getType() {

  return TatankaTypes::SIOUX;
}


