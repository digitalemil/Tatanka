#include "all.h"

#include "partanimation.h"

PartAnimation::~PartAnimation() {

}

PartAnimation::PartAnimation() {
	coloranim = false;
	fca = 0;
	sduration = 0;
	duration = 0;
	rduration = 0;
	_loops = false;
	running = false;
	part = 0;
	translationX = 0;
	translationY = 0;
	scaleX = 0;
	scaleY = 0;
	rotation = 0;
	lsx = 0;
	lsy = 0;
	lx = 0;
	ly = 0;
	lr = 0;
	lca = 0;
	delta = 0;
	_start = 0;

}


PartAnimation::PartAnimation(Part* p, float tx, float ty, float tr, float tsx, float tsy, int d, bool l) {
	coloranim = false;
	fca = 0;
	sduration = 0;
	duration = 0;
	rduration = 0;
	_loops = false;
	running = false;
	part = 0;
	translationX = 0;
	translationY = 0;
	scaleX = 0;
	scaleY = 0;
	rotation = 0;
	lsx = 0;
	lsy = 0;
	lx = 0;
	ly = 0;
	lr = 0;
	lca = 0;
	delta = 0;
	_start = 0;

  init(p,tx,ty,tr,tsx,tsy,d,l);
}


void PartAnimation::init(Part* p, float tx, float ty, float tr, float tsx, float tsy, int d, bool l) {

  translationX=tx;
  translationY=ty;
  scaleX=tsx;
  scaleY=tsy;
  rotation=tr;
  rduration=sduration=duration=d;
  part=p;
  _loops=l;
}


PartAnimation::PartAnimation(Part* p, int a, int d) {
	coloranim = false;
	fca = 0;
	sduration = 0;
	duration = 0;
	rduration = 0;
	_loops = false;
	running = false;
	part = 0;
	translationX = 0;
	translationY = 0;
	scaleX = 0;
	scaleY = 0;
	rotation = 0;
	lsx = 0;
	lsy = 0;
	lx = 0;
	ly = 0;
	lr = 0;
	lca = 0;
	delta = 0;
	_start = 0;

  initColorAnim(p,a,d);
}


void PartAnimation::initColorAnim(Part* p, int a, int d) {

  coloranim=true;
  fca=a;
  sduration=duration=d;
  part=p;
  _loops=false;
  running=false;
}


long PartAnimation::currentTimeMillis() {

  return OS::currentTimeMillies();
}


void PartAnimation::start() {

  running=true;
  sduration=rduration;
  duration=sduration;
  lsx=lsy=1.0f;
  lca=part->a;
  lx=0.0f;
  ly=0.0f;
  lr=0.0f;
  delta=0;
  _start=currentTimeMillis();
}


PartAnimation* PartAnimation::createReverseAnimation() {

  int d=duration;
  float r=-rotation;
  float x=-translationX;
  float y=-translationY;
  float sx=1.0f / scaleX;
  float sy=1.0f / scaleY;
  if (running) {
    float p=(currentTimeMillis() - _start) / duration;
    d*=p;
    x*=p;
    y*=p;
    sx=(1.0f + (scaleX - 1.0f) * p);
    sy=(1.0f + (scaleY - 1.0f) * p);
    r*=p;
  }
  return new PartAnimation(part,x,y,r,sx,sy,d,_loops);
}


float PartAnimation::animate() {

  return animate(currentTimeMillis());
}


float PartAnimation::animate(long now) {

  if (!running) {
    return 1.0f;
  }
  long delta=(now - _start);
  float percentage=(float)delta / (float)duration;
  if (percentage > 1.0f) {
    percentage=1.0f;
  }
  if (delta >= duration && !_loops) {
    percentage=1.0f;
    finish();
  }
  if (coloranim) {
    part->setAlpha((int)(lca + (fca - lca) * percentage));
  }
 else {
    float v1=(1.0f + (scaleX - 1.0f) * percentage);
    float v2=(1.0f + (scaleY - 1.0f) * percentage);
    part->translate(translationX * percentage - lx,translationY * percentage - ly,0.0f);
    lx=translationX * percentage;
    ly=translationY * percentage;
    part->scale(v1 / lsx,v2 / lsy);
    lsx=v1;
    lsy=v2;
    part->rotate(rotation * percentage - lr);
    lr=rotation * percentage;
  }
  if (delta >= duration && _loops) {
    start();
  }
  return percentage;
}


void PartAnimation::setValues(float x, float y, float sx, float sy, float rot) {

  part->clearAll();
  part->translate(x,y,0.0f);
  part->rotate(rot);
  part->scale(sx,sy);
}


void PartAnimation::finish() {

  running=false;
}


bool PartAnimation::isRunning() {

  return running;
}


void PartAnimation::stop() {

  running=false;
}


void PartAnimation::pause() {

  duration=(int)(1.0f - (currentTimeMillis() - _start) / duration);
}


void PartAnimation::cont() {

  running=true;
}


float PartAnimation::animateDelta(long d) {

  delta+=d;
  return animate(_start + delta);
}


void PartAnimation::faster() {

  sduration=(int)(sduration * 0.8f);
}


void PartAnimation::slower() {

  sduration=(int)(sduration * 1.2f);
}


int PartAnimation::getType() {

  return Types::PARTANIMATION;
}


