#include "all.h"

#include "shootinglakotaanimation.h"

ShootingLakotaAnimation::~ShootingLakotaAnimation() {

}

ShootingLakotaAnimation::ShootingLakotaAnimation(Sioux* s) : CompositeAnimation((unsigned char*)"LakotaAnimation", 8, 1, false) {
	lakota = 0;
	arrow = 0;
	shooting = false;
	width = 0;
	height = 0;
	lt = 0;
	scale = 0;

  //CompositeAnimation::(unsigned char*)"LakotaAnimation",8,1,false);
  lakota=s;
  width=Globals::getWidth();
  height=Globals::getHeight();
  scale=Globals::getScale();
}


void ShootingLakotaAnimation::clear() {

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


bool ShootingLakotaAnimation::shoot(int shootAtX, int shootAtY) {

  if (shooting)   return false;
  clear();
  _loops=false;
  int na=9;
  arrow=lakota->getFlyingArrow();
  shooting=true;
  int x=(int)(shootAtX - Globals::getW2() - lakota->getBow()->getCoordinateTap()->getX());
  int y=(int)(Globals::getH2() - shootAtY + lakota->getBow()->getCoordinateTap()->getY());
  float newdir=(float)atan2(y,x);
  float sr=lakota->getRotation();
  if (sr > 180)   sr=-360 + sr;
  if (newdir < 0) {
    newdir+=2 * PI;
  }
  newdir=(float)(newdir * 360.0f / (2 * PI));
  float deg=-135 + newdir - sr;
  if (deg > 90) {
    shooting=false;
    return false;
  }
  lakota->arrow->beginTX();
  lakota->arrow->setVisibility(true);
  CompositeAnimation* sa1=new CompositeAnimation((unsigned char*)"1",1,2,false);
  PartAnimation* pa=new PartAnimation();
  pa->init(lakota->getBow(),0,0,35,1.0f,1.0f,250,false);
  sa1->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(lakota->getRightarm(),0,0,40,1.0f,1.4f,250,false);
  sa1->addAnimation(pa,0);
  CompositeAnimation* sa2=new CompositeAnimation((unsigned char*)"2",1,1,false);
  pa=new PartAnimation();
  pa->init(lakota->upperparts,0,0,deg,1.0f,1.0f,(int)abs(deg * 3),false);
  sa2->addAnimation(pa,0);
  CompositeAnimation* sa3=new CompositeAnimation((unsigned char*)"3",1,4,false);
  pa=new PartAnimation();
  pa->init(lakota->leftarm,0,0,0,1.0f,1.5f,200,false);
  sa3->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(lakota->rightarm,0,0,0,1.0f,1 / 1.4f,400,false);
  sa3->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(lakota->arrow,0,15,0,1.0f,1 / 1.5f,400,false);
  sa3->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(lakota->getFibre(),0,0,0,1.0f,15,400,false);
  sa3->addAnimation(pa,0);
  CompositeAnimation* sa4=new CompositeAnimation((unsigned char*)"4",1,3,false);
  pa=new PartAnimation();
  pa->init(lakota->getFibre(),0,0,0,1.0f,1.0f / 15,200,false);
  sa4->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(lakota->getBow(),0,0,0,1.0f,1.0f / 1.5f,200,false);
  sa4->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(lakota->getArrow(),0,0,0,1.0f,1.5f,200,false);
  sa4->addAnimation(pa,0);
  CompositeAnimation* sa5=new CompositeAnimation((unsigned char*)"5",1,3,false);
  pa=new PartAnimation();
  pa->init(lakota->getBow(),0,0,0,1.0f,1.5f,500,false);
  sa5->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(lakota->getRightarm(),0,0,0,1.0f,1.0f / 1.2f,500,false);
  sa5->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(lakota->getLeftarm(),0,0,0,1.0f,1.0f / 1.5f,500,false);
  sa5->addAnimation(pa,0);
  int d=(int)abs(deg * 3) * 2;
  CompositeAnimation* sa6=new CompositeAnimation((unsigned char*)"6",1,4,false);
  pa=new PartAnimation();
  pa->init(lakota->getLeftarm(),0,0,-30,1.0f,1.0f,d,false);
  sa6->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(lakota->getRightarm(),0,0,-40,1.0f,1.0f,d,false);
  sa6->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(lakota->bow,0,0,-25,1.0f,1.0f,d,false);
  sa6->addAnimation(pa,0);
  pa=new PartAnimation();
  pa->init(lakota->upperparts,0,0,-deg,1.0f,1.0f,d,false);
  sa6->addAnimation(pa,0);
  addAnimation(sa1,0);
  addAnimation(sa2,1);
  addAnimation(sa3,2);
  addAnimation(sa4,3);
  addAnimation(sa5,4);
  addAnimation(sa6,5);
  start();
  return true;
}


void ShootingLakotaAnimation::increaseLevelImpl() {

  int l, phi;
  float sinbeta, cosbeta, arot;
switch (level) {
case 2:
    break;
case 3:
  l=(int)(sqrt(width * width + height * height));
arrow->translate(-arrow->getX() - arrow->getRx() + lakota->getArrow()->getCoordinateTap()->getX(),-arrow->getY() - arrow->getRy() + lakota->getArrow()->getCoordinateTap()->getY(),0);
arot=lakota->getRotation() + lakota->getUpperparts()->getRotation() + lakota->getBody()->getRotation()+ lakota->getLeftarm()->getRotation()+ lakota->getBow()->getRotation()+ lakota->getArrow()->getRotation()+ lakota->getRrot()+ lakota->getUpperparts()->getRrot()+ lakota->getBody()->getRrot()+ lakota->getLeftarm()->getRrot()+ lakota->getBow()->getRrot()+ lakota->getArrow()->getRrot();
arrow->rotate(-arrow->getRotation() + arot);
arrow->setVisibility(true);
phi=Part::calcPhi(arrow->getRotation() + arrow->getRrot());
sinbeta=Part::mysin[phi];
cosbeta=Part::mycos[phi];
arrow->getArrowAnimation()->init(arrow,-sinbeta * l,-cosbeta * l,0,1.0f,1.0f,1500,false);
arrow->getArrowAnimation()->start();
lakota->arrow->setVisibility(false);
break;
case 6:
lakota->arrow->rollbackTX();
lakota->reset();
shooting=false;
break;
}
}


void ShootingLakotaAnimation::start() {

  CompositeAnimation::start();
  lt=_start=PartAnimation::currentTimeMillis();
}


float ShootingLakotaAnimation::animate() {

  long now=PartAnimation::currentTimeMillis();
  if (!running) {
    return 1.0f;
  }
  int l=level;
  float ret=CompositeAnimation::animate(now);
  if (l != level)   increaseLevelImpl();
  return ret;
}


int ShootingLakotaAnimation::getType() {

  return Types::LAKOTAANIMATION;
}


