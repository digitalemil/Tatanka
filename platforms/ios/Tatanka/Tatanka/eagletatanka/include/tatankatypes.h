#ifndef _TATANKATYPES_
#define _TATANKATYPES_

#include <string.h>
#include <math.h>
#include <cstdlib>
#include "types.h"
#include "types.h"

#define null 0

class Types;

class TatankaTypes : public Types {
public:
	static const int SIOUX = 101;
	static const int ARROW = 102;
	static const int TATANKA = 103;
	static const int ROPE = 104;
	static const int MUSTANG = 105;
	static const int TATANKANIMATION = 106;

	virtual ~TatankaTypes();
};


#endif

