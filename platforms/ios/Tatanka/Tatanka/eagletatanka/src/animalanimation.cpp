#include "all.h"

#include "animalanimation.h"

AnimalAnimation::~AnimalAnimation() {
	finalize();

}

AnimalAnimation::AnimalAnimation(Thing* thing, int steplength, int duration) : CompositeAnimation((unsigned char*)"AnimalAnimation", 4, 1, true) {
	state = 0;
	sl = 0;
	dur = 0;
	maxstep = 0;
	lslx = 0;
	lsly = 0;
	lt = 0;
	animal = 0;
	ka = 0;
	dir = 0;
	killed = false;
	speed = 1.0f;

  //CompositeAnimation::(unsigned char*)"AnimalAnimation",4,1,true);
  animal=thing;
  sl=steplength;
  dur=duration;
  PartAnimation::animations++;
}


void AnimalAnimation::finalize() {

  int i;
  delete ka;
  delete dir;
  PartAnimation::animations--;
}


void AnimalAnimation::undoTranslation() {

  animal->translate(-lslx,-lsly,0.0f);
  lslx=0.0f;
  lsly=0.0f;
}


void AnimalAnimation::kill() {

  stopWalk();
  if (dir != 0)   dir->stop();
  killed=true;
}


void AnimalAnimation::createKillAnimation() {

  if (ka == 0) {
    ka=new CompositeAnimation((unsigned char*)"KillAnimation",3,1,false);
    int duration=dur;
    float stepl=sl;
    stepl*=1.5;
    duration/=8;
    CompositeAnimation* ka1=new CompositeAnimation((unsigned char*)"ka",1,9,false);
    PartAnimation* pa=new PartAnimation();
    pa->init(animal->getByName((unsigned char*)"LeftForelegPart1"),0.0f,-sl,0.0f,1.0f,1.0f,duration,false);
    ka1->addAnimation(pa,0);
    pa=new PartAnimation();
    pa->init(animal->getByName((unsigned char*)"LeftForelegPart2"),0,-sl / 2,0,1.0f,2.0f,duration,false);
    ka1->addAnimation(pa,0);
    pa=new PartAnimation();
    pa->init(animal->getByName((unsigned char*)"RightHindlegPart1"),0,sl,0,1.0f,1.0f,duration,false);
    ka1->addAnimation(pa,0);
    pa=new PartAnimation();
    pa->init(animal->getByName((unsigned char*)"RightHindlegPart2"),0,sl / 2,0,1.0f,2.0f,duration,false);
    ka1->addAnimation(pa,0);
    pa=new PartAnimation();
    pa->init(animal->getByName((unsigned char*)"RightForelegPart1"),0,-sl,0,1.0f,1.0f,duration,false);
    ka1->addAnimation(pa,0);
    pa=new PartAnimation();
    pa->init(animal->getByName((unsigned char*)"RightForelegPart2"),0,-sl / 2,0,1.0f,2.0f,duration,false);
    ka1->addAnimation(pa,0);
    pa=new PartAnimation();
    pa->init(animal->getByName((unsigned char*)"LeftHindlegPart1"),0,sl,0,1.0f,1.0f,duration,false);
    ka1->addAnimation(pa,0);
    pa=new PartAnimation();
    pa->init(animal->getByName((unsigned char*)"LeftHindlegPart2"),0,sl / 2,0,1.0f,2.0f,duration,false);
    ka1->addAnimation(pa,0);
    pa=new PartAnimation();
    pa->init(animal,0.0f,0.0f,0.0f,0.95f,0.95f,duration,false);
    ka1->addAnimation(pa,0);
    ka->addAnimation(ka1,0);
    pa=new PartAnimation();
    pa->initColorAnim(animal,128,1000);
    PartAnimation* pa1=new PartAnimation();
    pa1->init(animal->getByName((unsigned char*)"Tongue"),0.0f,-20.0f,0.0f,10.0f,16.0f,1000,false);
    ka->addAnimation(pa1,2);
    ka->start();
  }
}


void AnimalAnimation::unkill() {

  killed=false;
  animal->rotate(-animal->getRotation());
  if (ka != 0) {
    ka->stop();
    ka=0;
  }
}


void AnimalAnimation::left(float degree) {

  rotate(degree);
}


void AnimalAnimation::right(float degree) {

  rotate(-degree);
}


void AnimalAnimation::rotate(float degree) {

  if (!(state > STOP && state <= RUNWALK))   return;
  if (dir != 0 && dir->isRunning())   return;
  if (state == WALK)   degree*=1.5;
  int d=dur;
  if (state == RUNNING)   d/=3;
  dir=new PartAnimation();
  dir->init(animal,0.0f,0.0f,degree,1.0f,1.0f,d,false);
  dir->start();
}


void AnimalAnimation::clear() {

  for (int i=0; i < maxanimation; i++) {
    for (int j=0; j < maxlevel; j++) {
      if (anis[j * maxanimation + i] != 0) {
        anis[j * maxanimation + i]->stop();
        if (anis[j * maxanimation + i] != 0)         anis[j * maxanimation + i]=0;
        anis[j * maxanimation + i]=0;
      }
    }
  }
}


void AnimalAnimation::createWalk(int s, int d) {

  clear();
  _loops=true;
  int na=10;
  int isl=sl;
  CompositeAnimation* wa1=new CompositeAnimation((unsigned char*)"2",1,na,false);
  int duration=dur;
  if (s == 2) {
    isl*=1.5;
    duration/=8;
  }
  isl*=1.5f;
  if (!strcmp((const char *)animal->getName(), (const char *)(unsigned char*)"Tatanka"))   isl*=1.25f;
  duration*=1.5f;
  PartAnimation* pa=new PartAnimation();
  pa->init(animal,0.0f,0.0f,0.0f,1.0f,1.075f,duration,false);
  wa1->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"LeftForelegPart1"),0,-isl,0,1.0f,1.0f,duration,false);
  wa1->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"LeftForelegPart2"),0,-isl / 2,0,1.0f,2.0f,duration,false);
  wa1->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"RightHindlegPart1"),0,isl,0,1.0f,1.0f,duration,false);
  wa1->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"RightHindlegPart2"),0,isl / 2,0,1.0f,2.0f,duration,false);
  wa1->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"RightForelegPart1"),0,-0.7f * isl,0,1.0f,1.0f,duration,false);
  wa1->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"RightForelegPart2"),0,-0.7f * isl / 2,0,1.0f,2.0f,duration,false);
  wa1->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"LeftHindlegPart1"),0,0.7f * isl,0,1.0f,1.0f,duration,false);
  wa1->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"LeftHindlegPart2"),0,0.7f * isl / 2,0,1.0f,2.0f,duration,false);
  wa1->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"Head"),0,0,d,1.0f,1.0f,duration,false);
  wa1->addAnimation(pa,0);
  CompositeAnimation* wa2=new CompositeAnimation((unsigned char*)"2.1",1,na,false);
  pa=new PartAnimation();
  pa->init(animal,0.0f,0.0f,0.0f,1.0f,1.075f,duration,false);
  wa2->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"LeftForelegPart1"),0,-0.7f * isl,0,1.0f,1.0f,duration,false);
  wa2->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"LeftForelegPart2"),0,-0.7f * isl / 2,0,1.0f,2.0f,duration,false);
  wa2->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"RightHindlegPart1"),0,0.7f * isl,0,1.0f,1.0f,duration,false);
  wa2->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"RightHindlegPart2"),0,0.7f * isl / 2,0,1.0f,2.0f,duration,false);
  wa2->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"RightForelegPart1"),0,-isl,0,1.0f,1.0f,duration,false);
  wa2->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"RightForelegPart2"),0,-isl / 2,0,1.0f,2.0f,duration,false);
  wa2->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"LeftHindlegPart1"),0,0.8f * isl,0,1.0f,1.0f,duration,false);
  wa2->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"LeftHindlegPart2"),0,0.8f * isl / 2,0,1.0f,2.0f,duration,false);
  wa2->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(animal->getByName((unsigned char*)"Head"),0,0,-d,1.0f,1.0f,duration,false);
  wa2->addAnimation(pa,0);
  addAnimation(wa2,0);
  addAnimation(wa2->createReverseAnimation(),1);
  addAnimation(wa1,2);
  addAnimation(wa1->createReverseAnimation(),3);
  start();
}


void AnimalAnimation::stopWalk() {

  _loops=false;
switch (state) {
case STARTWALK:
case WALK:
    state=STOPWALK;
  break;
case STARTRUNNING:
case RUNNING:
state=STOPRUNNING;
break;
}
}


void AnimalAnimation::startRun() {

  _loops=false;
  if (state == STOP) {
    createWalk(2,0);
    state=RUNNING;
  }
 else   state=STARTRUNNING;
}


void AnimalAnimation::increaseLevelImpl() {

  if (killed) {
  }
  if (state == STOPRUNNING && level == 4) {
    state=STOP;
    running=false;
    if (killed)     createKillAnimation();
  }
  if (state == STOPWALK && level == 4) {
    state=STOP;
    running=false;
    if (killed)     createKillAnimation();
  }
  if (state == STARTRUNNING && level == 4) {
    createWalk(2,0);
    state=RUNNING;
  }
  if (state == RUNWALK && level == 4) {
    int deg=0;
    if (animal->getType() == TatankaTypes::TATANKA)     deg=12;
    createWalk(1,deg);
    state=WALK;
  }
  if (state == STARTWALK && level == 4) {
    state=WALK;
  }
  if (level == 4)   animal->reset();
}


void AnimalAnimation::startWalk() {

  if (state >= STARTRUNNING && state <= RUNWALK) {
    _loops=false;
    state=RUNWALK;
    return;
  }
  if (state != WALK) {
    int deg=0;
    if (animal->getType() == TatankaTypes::TATANKA)     deg=12;
    createWalk(1,deg);
    state=STARTWALK;
  }
}


void AnimalAnimation::start() {

  CompositeAnimation::start();
  lt=_start=PartAnimation::currentTimeMillis();
  animal->reset();
}


float AnimalAnimation::animate() {

  long now=PartAnimation::currentTimeMillis();
  if (dir != 0 && dir->isRunning()) {
    dir->animate(now);
  }
  if (ka != 0) {
    ka->animate(now);
    if (!ka->isRunning()) {
      ka=0;
    }
    return 0.0f;
  }
  if (!running) {
    return 1.0f;
  }
  if (state == STOP)   return 0.0f;
  int l=level;
  float ret = CompositeAnimation::animate(now);
  if (l != level)   increaseLevelImpl();
  float rot=(float)PI * 2.0f * animal->getRotation() / 360.0f;
  long delta=now - lt;
  float percentage=(delta / dur) * speed;
  if (state == STARTRUNNING || state == RUNWALK) {
    percentage*=2;
  }
  if (state == RUNNING) {
    percentage*=3;
  }
  if (percentage > 1.0f) {
    percentage=1.0f;
  }
  int esl=sl;
  if (state == RUNNING || state == STARTRUNNING)   esl*=1.5;
  if (state != STOP) {
    int phi=Part::calcPhi(rot);
    lslx=-Part::mysin[phi] * esl * animal->getSx()* percentage;
    lsly=-Part::mycos[phi] * esl * animal->getSy()* percentage;
  }
  lt=now;
  return ret;
}


int AnimalAnimation::getType() {

  return TatankaTypes::TATANKANIMATION;
}


