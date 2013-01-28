#include "all.h"

#include "tatanka.h"

Tatanka::~Tatanka() {
	finalize();

}

bool Tatanka::didCollide() {

  return didc;
}


void Tatanka::setDidCollide(bool didCollide) {

  this->didc=didCollide;
}


void Tatanka::finalize() {

  delete animation;
}


Tatanka::Tatanka(float scale) : Thing(12) {
	health = MAXHEALTH;
	hits = 0;
	speed = 0;
	didc = true;
	animation = 0;

  //Thing::12);
  setCoordinateTap(new CoordinateTapImpl());
  int darkbrown=0xff190d01;
  int lightbrown=0xffae9879;
  int brown=0xff3a270c;
  int horn=0xff666666;
  int huf=0xff000000;
  int red=0xff9f0609;
  int schnauze=0xff000000;
  this->setName((unsigned char*)"Tatanka");
  Bone* head=new Bone(0,-42,16,0,8);
  head->setName((unsigned char*)"Head");
  Ellipse* el=new Ellipse(1,1,0,-20,-3,0,Ellipse::TRIANGLES8,red);
  el->setName((unsigned char*)"Tongue");
  head->addPart(el);
  el=new Ellipse(14,12,0,-34,0,-2,Ellipse::TRIANGLES8,schnauze);
  el->setName((unsigned char*)"Schnauze");
  head->addPart(el);
  el=new Ellipse(14,5,-32,-12,-1,20,Ellipse::TRIANGLES8,horn);
  el->setName((unsigned char*)"LinkesHorn1");
  head->addPart(el);
  el=new Ellipse(4,8,-41,-22,0,15,Ellipse::TRIANGLES8,horn);
  el->setName((unsigned char*)"LinkesHorn2");
  head->addPart(el);
  el=new Ellipse(14,5,32,-12,-1,-20,Ellipse::TRIANGLES8,horn);
  el->setName((unsigned char*)"RechtesHorn1");
  head->addPart(el);
  el=new Ellipse(4,8,41,-22,0,-15,Ellipse::TRIANGLES8,horn);
  el->setName((unsigned char*)"RechtesHorn2");
  head->addPart(el);
  el=new Ellipse(28,32,0,-10,-1,0,Ellipse::TRIANGLES10,darkbrown);
  el->setName((unsigned char*)"Schaedel");
  head->addPart(el);
  head->setupDone();
  head->rotate(0);
  Bone* body=new Bone(0,0,0,0,9);
  body->setName((unsigned char*)"Body");
  Bone* leg=new Bone(0,-40,0,0,2);
  leg->setName((unsigned char*)"LeftForeleg");
  el=new Ellipse(8,12,-32,-6,-1,0,Ellipse::TRIANGLES8,huf);
  leg->addPart(el);
  el->setName((unsigned char*)"LeftForelegPart1");
  el=new Ellipse(8,12,-30,0,0,0,Ellipse::TRIANGLES8,lightbrown);
  leg->addPart(el);
  el->setName((unsigned char*)"LeftForelegPart2");
  leg->setupDone();
  body->addPart(leg);
  leg=new Bone(0,-40,0,0,2);
  leg->setName((unsigned char*)"RightForeleg");
  el=new Ellipse(8,12,32,-6,-1,0,Ellipse::TRIANGLES8,huf);
  leg->addPart(el);
  el->setName((unsigned char*)"RightForelegPart1");
  el=new Ellipse(8,12,30,-0,0,0,Ellipse::TRIANGLES8,lightbrown);
  leg->addPart(el);
  el->setName((unsigned char*)"RightForelegPart2");
  leg->setupDone();
  body->addPart(leg);
  leg=new Bone(0,38,0,0,2);
  leg->setName((unsigned char*)"RightHindleg");
  el=new Ellipse(8,12,28,0,-1,0,Ellipse::TRIANGLES8,darkbrown);
  leg->addPart(el);
  el->setName((unsigned char*)"RightHindlegPart1");
  el=new Ellipse(8,12,26,6,0,0,Ellipse::TRIANGLES8,brown);
  leg->addPart(el);
  el->setName((unsigned char*)"RightHindlegPart2");
  leg->setupDone();
  body->addPart(leg);
  leg=new Bone(0,38,0,0,2);
  leg->setName((unsigned char*)"LeftHindleg");
  el=new Ellipse(8,12,-28,0,-1,0,Ellipse::TRIANGLES8,darkbrown);
  leg->addPart(el);
  el->setName((unsigned char*)"LeftHindlegPart1");
  el=new Ellipse(8,12,-26,6,0,0,Ellipse::TRIANGLES8,brown);
  leg->addPart(el);
  el->setName((unsigned char*)"LeftHindlegPart2");
  leg->setupDone();
  body->addPart(leg);
  el=new Ellipse(36,44,0,-10,0,0,Ellipse::TRIANGLES10,brown);
  el->setName((unsigned char*)"Rumpf");
  body->addPart(el);
  el=new Ellipse(32,44,0,32,0,0,Ellipse::TRIANGLES10,brown);
  el->setName((unsigned char*)"Rumpf");
  body->addPart(el);
  el=new Ellipse(20,36,0,-20,0,0,Ellipse::TRIANGLES10,darkbrown);
  el->setName((unsigned char*)"Rumpf");
  body->addPart(el);
  el=new Ellipse(6,10,0,76,0,0,Ellipse::TRIANGLES8,darkbrown);
  el->setName((unsigned char*)"Tail");
  body->addPart(el);
  body->setupDone();
  Bone* rest=new Bone(0,-30,0,0,4);
  rest->setName((unsigned char*)"Rest");
  rest->addPart(head);
  el=new Ellipse(36,22,0,-12,-2,0,Ellipse::TRIANGLES10,darkbrown);
  el->setName((unsigned char*)"Shoulders2");
  rest->addPart(el);
  el=new Ellipse(38,16,0,-12,-2,0,Ellipse::TRIANGLES10,lightbrown);
  el->setName((unsigned char*)"Shoulders");
  rest->addPart(el);
  el=new Ellipse(16,36,0,-4,-2,0,Ellipse::TRIANGLES10,lightbrown);
  el->setName((unsigned char*)"Throat");
  rest->addPart(el);
  el=new Ellipse(12,50,0,60,-2,0,Ellipse::TRIANGLES10,red);
  el->setName((unsigned char*)"RumpfLinie");
  body->addPart(el);
  rest->setupDone();
  Part* bc=new BoundingCircle(15,22,-2,0);
  bc->setCoordinateTap(new CoordinateTapImpl());
  this->addPart(bc);
  bc=new BoundingCircle(15,-22,-2,0);
  bc->setCoordinateTap(new CoordinateTapImpl());
  this->addPart(bc);
  bc=new BoundingCircle(34,0,38,0);
  bc->setCoordinateTap(new CoordinateTapImpl());
  this->addPart(bc);
  bc=new BoundingCircle(36,0,-30,0);
  bc->setCoordinateTap(new CoordinateTapImpl());
  this->addPart(bc);
  bc=new BoundingCircle(28,0,-86,0);
  bc->setCoordinateTap(new CoordinateTapImpl());
  this->addPart(bc);
  this->addPart(body);
  this->addPart(rest);
  scaleRoot(scale,scale);
  for (int i=0; i < MAXARROWS; i++) {
    Arrow* arrow=new Arrow();
    arrow->setCoordinateTap(0);
    arrow->getByName((unsigned char*)"BC")->setCoordinateTap(0);
    sprintf((char*)tmptextbuffer, (const char*)(unsigned char*)"arrow%i", i); arrow->setName(tmptextbuffer); //((unsigned char*)"arrow" + i);
    arrow->setVisibility(false);
    addPart(arrow);
  }
  animation=new AnimalAnimation(this,16,1200 + (-200 + Part::getRandom(0,400)));
  animation->startRun();
  health=100;
  speed=0;
  this->setupDone();
}


void Tatanka::animate() {

  if (animation != 0)   animation->animate();
}


AnimalAnimation* Tatanka::getAnimation() {

  return animation;
}


void Tatanka::hit(int part) {

  hits++;
  if (hits >= MAXARROWS)   return;
  if (part < 2) {
    health-=80 + getRandom(0,40);
  }
 else {
    health-=30 + getRandom(0,10);
  }
  if (health <= 0) {
    speed=0;
    health=100;
  }
}


void Tatanka::resetHealth() {

  for (int i=0; i < this->MAXARROWS; i++) {
    sprintf((char*)tmptextbuffer, (const char*)(unsigned char*)"arrow%i", i); Arrow *arr= (Arrow *)getByName(tmptextbuffer); //=(Arrow*)getByName((unsigned char*)"arrow" + i);
    arr->setCoordinateTap(0);
    arr->getByName((unsigned char*)"BC")->setCoordinateTap(0);
    arr->translate(-arr->getX(),-arr->getY(),0);
    arr->rotate(-arr->getRotation());
    arr->setVisibility(false);
  }
  hits=0;
  health=100;
}


int Tatanka::getType() {

  return TatankaTypes::TATANKA;
}


