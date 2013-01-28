#ifndef _TATANKAMODELL_
#define _TATANKAMODELL_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "modell.h"

#define null 0

class Screen;
class Modell;
class FirstScreen;
class HuntingScreen;

class TatankaModell : public Modell {
public:

	TatankaModell();
	virtual void setup();
	virtual ~TatankaModell();
};


#endif

