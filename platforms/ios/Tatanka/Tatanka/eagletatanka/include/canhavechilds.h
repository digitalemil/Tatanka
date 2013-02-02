#ifndef _CANHAVECHILDS_
#define _CANHAVECHILDS_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"

#define null 0

class BoundingCircle;

class CanHaveChilds {
public:

	virtual int getNumberOfBCs();
	virtual int addBCs(BoundingCircle** bcs, int start);
	virtual ~CanHaveChilds();
};


#endif

