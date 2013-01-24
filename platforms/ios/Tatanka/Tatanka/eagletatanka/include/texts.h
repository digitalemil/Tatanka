#ifndef _TEXTS_
#define _TEXTS_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "thing.h"

#define null 0

class Text;
class Thing;

class Texts : public Thing {
public:
	Text* t1;
	Text* t2;
	Text* t3;

	Texts();
	virtual void setArrows(int a);
	virtual ~Texts();
};


#endif

