#ifndef _TEXTTHING_
#define _TEXTTHING_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "thing.h"

#define null 0

class Text;
class Thing;

class TextThing : public Thing {
public:
	Text* text;

	TextThing(unsigned char* t, float ix, float iy, int a, int s, int c);
	virtual void setText(unsigned char* t);
	virtual ~TextThing();
};


#endif

