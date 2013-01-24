#ifndef _OS_
#define _0S_

#include <sys/time.h>



class OS {
private:
    static struct timeval tim;
public:
    static long currentTimeMillies();

	static long uncorrectedCurrentTimeMillies();
	
	static void pause();
	
	static void pauseReset();
};
#endif


