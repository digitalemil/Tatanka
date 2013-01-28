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
  Screen::registerScreen(huntingScreen);
  Screen::registerScreen(firstScreen);
  showScreen(firstScreen->getScreenID());
}


