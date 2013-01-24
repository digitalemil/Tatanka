#include "all.h"

#include "prairie.h"

Prairie::~Prairie() {

}

Prairie::Prairie(unsigned char* imgname) {
	dim = 0;
	imgdim = 512;
	ntx = 0;
	nty = 0;

  ntx=Globals::getWidth() / imgdim + 2;
  nty=Globals::getHeight() / imgdim + 2;
  init(ntx * nty);
  int nt=0;
  for (int x=0; x < ntx; x++) {
    for (int y=0; y < nty; y++) {
      things[nt]=new ImageThing(imgname,imgdim,imgdim);
      things[nt]->setName((unsigned char*)"Prairie");
      things[nt]->translate(-abs((Globals::getWidth() - imgdim * ntx)) / 2 + x * imgdim + imgdim / 2 - Globals::getW2(),-abs((Globals::getHeight() - imgdim * nty)) / 2 + y * imgdim + imgdim / 2 - Globals::getH2(),0);
      nt++;
    }
  }
}


void Prairie::update(float speedx, float speedy) {

  translate(speedx * 2,speedy * 2,0);
  for (int i=0; i < n; i++) {
    if (things[i]->getX() > Globals::getWidth() / 2 + imgdim / 2)     things[i]->translate(-ntx * imgdim,0,0);
    if (things[i]->getX() < -(Globals::getWidth() / 2 + imgdim / 2))     things[i]->translate(ntx * imgdim,0,0);
    if (things[i]->getY() > Globals::getHeight() / 2 + imgdim / 2)     things[i]->translate(0,-nty * imgdim,0);
    if (things[i]->getY() < -(Globals::getHeight() / 2 + imgdim / 2))     things[i]->translate(0,nty * imgdim,0);
  }
}


