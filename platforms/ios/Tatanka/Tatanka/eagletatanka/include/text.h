#ifndef _TEXT_
#define _TEXT_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "part.h"

#define null 0

class Part;

class Text : public Part {
public:
	static const int TEXT_LEFT = 0;
	static const int TEXT_RIGHT = 1;
	static const int TEXT_CENTER = 2;
	int align;
	int tx_align;
	int tx_size;
	int size;
	int nt;
	unsigned char* font;
	unsigned char* text;
	unsigned char* tx_font;
	unsigned char* tx_text;
	bool isTextSet;
	bool isFontSet;

	Text(unsigned char* t, float irx, float iry, int ic);
	virtual void beginTX();
	virtual void rollbackTX();
	virtual int getNumberOfData();
	virtual int getTextAndFont(unsigned char** t, int startT);
	virtual int getNumberOfTextAndFont();
	virtual int getData(float* d, int startD, float xn, float yn, float zn, float a11, float a21, float a12, float a22);
	virtual void setText(unsigned char* t);
	virtual void setSize(int s);
	virtual void setFont(unsigned char* f);
	virtual void setAlignment(int a);
	virtual unsigned char* getText();
	virtual int getSize();
	virtual unsigned char* getFont();
	virtual int getType();
	virtual int getAlignment();
	virtual void finalize();
	virtual ~Text();
};


#endif

