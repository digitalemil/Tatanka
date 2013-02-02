#include "all.h"

#include "tatankamodell.h"

TatankaModell::~TatankaModell() {

}

TatankaModell::TatankaModell() : Modell(256) {

  //Modell::256);
}


void TatankaModell::setup() {

  Screen* huntingScreen=new HuntingScreen();
  Screen* firstScreen=new FirstScreen();
  Screen* moreScreen=new MoreScreen();
  Screen::registerScreen(huntingScreen);
  Screen::registerScreen(firstScreen);
  Screen::registerScreen(moreScreen);
  Screen::registerScreen(new AboutScreen());
  Screen::registerScreen(new GalleryScreen());
  showScreen(firstScreen->getScreenID());
}


