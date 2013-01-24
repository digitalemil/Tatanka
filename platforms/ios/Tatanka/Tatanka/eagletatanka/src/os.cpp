#include "os.h"

struct timeval OS::tim;

bool OS_paused= false;
long OS_p= 0L, OS_p1= 0L, OS_correct= 0L;
unsigned char tmptextbuffer[256];

long OS::currentTimeMillies() {
	return uncorrectedCurrentTimeMillies()- OS_p - OS_correct;
}

long OS::uncorrectedCurrentTimeMillies() {
	gettimeofday(&tim, 0);
	return (tim.tv_sec*1000L+ tim.tv_usec/1000);
}


void OS::pause() {
	OS_paused= !OS_paused;
	if(OS_paused== true) {
		OS_p1= uncorrectedCurrentTimeMillies();
	}
	else {
		OS_p+= uncorrectedCurrentTimeMillies()-OS_p1;
	}

}

void OS::pauseReset() {
	OS_paused= false;
	OS_p= OS_p1;
}

