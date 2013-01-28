#include "all.h"

#include "compositeanimation.h"

CompositeAnimation::~CompositeAnimation() {
	finalize();

}

CompositeAnimation::CompositeAnimation(unsigned char* n, int ml, int maxa, bool l) {
	name = 0;
	_loops = 0;
	running = 0;
	maxlevel = 0;
	maxanimation = 0;
	level = 0;
	anis = 0;
	_start = 0;

  _loops=l;
  name=n;
  running=false;
  maxlevel=ml;
  maxanimation=maxa;
  anis=(Animation**)new void*[ml * maxa];
  level=0;
  for (int i=0; i < maxlevel; i++) {
    for (int j=0; j < maxanimation; j++) {
      anis[i * maxanimation + j]=0;
    }
  }
  PartAnimation::animations++;
}


void CompositeAnimation::finalize() {

  dispose();
  PartAnimation::animations--;
}


void CompositeAnimation::dispose() {

  for (int i=0; i < maxlevel; i++) {
    for (int j=0; j < maxanimation; j++) {
      if (anis[i * maxanimation + j] != 0)       delete anis[i*maxanimation+j];
    }
  }
  delete [ ] anis;
}


void CompositeAnimation::increaseLevel() {

  level++;
  if (level >= maxlevel) {
    stop();
  }
 else {
    for (int i=0; i < maxanimation; i++) {
      if (anis[level * maxanimation + i] == 0) {
        continue;
      }
      anis[level * maxanimation + i]->start();
    }
  }
}


void CompositeAnimation::addAnimation(Animation* a, int l) {

  for (int i=0; i < maxanimation; i++) {
    if (anis[l * maxanimation + i] == 0) {
      anis[l * maxanimation + i]=a;
      return;
    }
  }
}


Animation* CompositeAnimation::createReverseAnimation() {

  CompositeAnimation* ret=new CompositeAnimation((unsigned char*)"Reverse",maxlevel,maxanimation,_loops);
  for (int i=0; i < maxlevel; i++) {
    for (int j=0; j < maxanimation; j++) {
      if (anis[i * maxanimation + j] != 0) {
        ret->anis[(maxlevel - 1 - i) * maxanimation + maxanimation - 1 - j]=anis[i * maxanimation + j]->createReverseAnimation();
      }
    }
  }
  return ret;
}


void CompositeAnimation::start() {

  level=0;
  _start=PartAnimation::currentTimeMillis();
  for (int i=0; i < maxanimation; i++) {
    if (anis[i] != 0) {
      anis[i]->start();
    }
  }
  running=true;
}


float CompositeAnimation::animateDelta(long delta) {

  return animate(_start + delta);
}


float CompositeAnimation::animate() {

  return animate(PartAnimation::currentTimeMillis());
}


float CompositeAnimation::animate(long now) {

  if (!running) {
    return 1.0f;
  }
  float ret=0.0f;
  for (int i=0; i < maxanimation; i++) {
    if (anis[level * maxanimation + i] == 0) {
      continue;
    }
    float oneret=anis[level * maxanimation + i]->animate(now);
    if (oneret >= 1.0 && i == maxanimation - 1) {
      increaseLevel();
    }
    ret=oneret;
  }
  return ret;
}


void CompositeAnimation::finish() {

  if (level >= maxlevel) {
    return;
  }
  for (int i=0; i < maxanimation; i++) {
    if (anis[level * maxanimation + i] != 0) {
      anis[level * maxanimation + i]->finish();
    }
  }
}


void CompositeAnimation::faster() {

  for (int a=0; a < maxlevel; a++) {
    for (int i=0; i < maxanimation; i++) {
      if (anis[a * maxanimation + i] != 0) {
        anis[a * maxanimation + i]->faster();
      }
    }
  }
}


void CompositeAnimation::slower() {

  for (int a=0; a < maxlevel; a++) {
    for (int i=0; i < maxanimation; i++) {
      if (anis[a * maxanimation + i] != 0) {
        anis[a * maxanimation + i]->slower();
      }
    }
  }
}


void CompositeAnimation::stop() {

  if (_loops) {
    start();
  }
 else {
    for (int a=0; a < maxlevel; a++) {
      for (int i=0; i < maxanimation; i++) {
        if (anis[a * maxanimation + i] != 0) {
          anis[a * maxanimation + i]->stop();
        }
      }
    }
    running=false;
  }
}


int CompositeAnimation::getType() {

  return Types::COMPOSITEANIMATION;
}


bool CompositeAnimation::isRunning() {

  return running;
}


void CompositeAnimation::pause() {

}


void CompositeAnimation::cont() {

}


