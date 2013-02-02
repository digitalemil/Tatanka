
INCLUDE= -Iinclude -I/Developer/SDKs/MacOSX10.6.sdk/System/Library/Frameworks/JavaVM.framework/Headers

TARGET=$(shell uname)
DISTDIR= ${TARGET}
LD=$(CC)
MKDIR=mkdir

OBJECTFILES= \
	build/${DISTDIR}/animation.o \
	build/${DISTDIR}/bone.o \
	build/${DISTDIR}/boundingcircle.o \
	build/${DISTDIR}/canhavechilds.o \
	build/${DISTDIR}/collisionhandler.o \
	build/${DISTDIR}/collisionhandlerimpl.o \
	build/${DISTDIR}/compositeanimation.o \
	build/${DISTDIR}/coordinatetap.o \
	build/${DISTDIR}/coordinatetapimpl.o \
	build/${DISTDIR}/ellipse.o \
	build/${DISTDIR}/globals.o \
	build/${DISTDIR}/imagething.o \
	build/${DISTDIR}/joystick.o \
	build/${DISTDIR}/loop.o \
	build/${DISTDIR}/modell.o \
	build/${DISTDIR}/part.o \
	build/${DISTDIR}/partanimation.o \
	build/${DISTDIR}/rectangle.o \
	build/${DISTDIR}/screen.o \
	build/${DISTDIR}/text.o \
	build/${DISTDIR}/textthing.o \
	build/${DISTDIR}/thing.o \
	build/${DISTDIR}/thingcontainer.o \
	build/${DISTDIR}/triangle.o \
	build/${DISTDIR}/types.o \
	build/${DISTDIR}/aboutscreen.o \
	build/${DISTDIR}/animalanimation.o \
	build/${DISTDIR}/arrow.o \
	build/${DISTDIR}/arrowcollisionhandler.o \
	build/${DISTDIR}/firstscreen.o \
	build/${DISTDIR}/galleryscreen.o \
	build/${DISTDIR}/huntingscreen.o \
	build/${DISTDIR}/joystickimpl.o \
	build/${DISTDIR}/lakotacollisionhandler.o \
	build/${DISTDIR}/morescreen.o \
	build/${DISTDIR}/mountedlakota.o \
	build/${DISTDIR}/mustang.o \
	build/${DISTDIR}/prairie.o \
	build/${DISTDIR}/rope.o \
	build/${DISTDIR}/shootinglakotaanimation.o \
	build/${DISTDIR}/sioux.o \
	build/${DISTDIR}/tatanka.o \
	build/${DISTDIR}/tatankaherd.o \
	build/${DISTDIR}/tatankamodell.o \
	build/${DISTDIR}/tatankatypes.o \
	build/${DISTDIR}/texts.o \
	build/${DISTDIR}/os.o \

ifeq ($(TARGET), Darwin)
	CC=g++
	CFLAGS =  -O2 -c -fno-common -pipe  ${INCLUDE} -D${TARGET} -DMACOSX
	TARGETOBJECTFILES= ${OBJECTFILES} 
	LDFLAGS=-dynamiclib -fno-common -O2 -pipe -flat_namespace
	LIBNAME=tatanka.lib
	LDDISTDIR=build/${DISTDIR}
endif

ifeq ($(TARGET),Linux)
	CC=g++
	CFLAGS = -Wall ${INCLUDE} -D${TARGET} -c $(DEBUG) -m32 -shared -fPIC -MMD -MP 
	TARGETOBJECTFILES= ${OBJECTFILES}
	LDFLAGS=-shared -m32 -lc -lgcc -lstdc++ -lm
	LDDISTDIR=build/${DISTDIR}
	LIBNAME=libtatanka.so
endif

build/tatanka: ${TARGETOBJECTFILES} 
	${MKDIR} -p build/${DISTDIR} ${LDDISTDIR}
	${LD} -fPIC -Wl -o ${LDDISTDIR}/${LIBNAME} $(LDFLAGS) ${OBJECTFILES} ${LDLIBSOPTIONS} 
ifeq ($(TARGET), Darwin)
	$(CP) ${LDDISTDIR}/${LIBNAME} dist/${TARGET}/${LIBNAME}
endif

build/${DISTDIR}/animation.o: src/animation.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/animation.o src/animation.cpp

build/${DISTDIR}/bone.o: src/bone.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/bone.o src/bone.cpp

build/${DISTDIR}/boundingcircle.o: src/boundingcircle.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/boundingcircle.o src/boundingcircle.cpp

build/${DISTDIR}/canhavechilds.o: src/canhavechilds.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/canhavechilds.o src/canhavechilds.cpp

build/${DISTDIR}/collisionhandler.o: src/collisionhandler.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/collisionhandler.o src/collisionhandler.cpp

build/${DISTDIR}/collisionhandlerimpl.o: src/collisionhandlerimpl.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/collisionhandlerimpl.o src/collisionhandlerimpl.cpp

build/${DISTDIR}/compositeanimation.o: src/compositeanimation.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/compositeanimation.o src/compositeanimation.cpp

build/${DISTDIR}/coordinatetap.o: src/coordinatetap.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/coordinatetap.o src/coordinatetap.cpp

build/${DISTDIR}/coordinatetapimpl.o: src/coordinatetapimpl.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/coordinatetapimpl.o src/coordinatetapimpl.cpp

build/${DISTDIR}/ellipse.o: src/ellipse.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/ellipse.o src/ellipse.cpp

build/${DISTDIR}/globals.o: src/globals.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/globals.o src/globals.cpp

build/${DISTDIR}/imagething.o: src/imagething.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/imagething.o src/imagething.cpp

build/${DISTDIR}/joystick.o: src/joystick.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/joystick.o src/joystick.cpp

build/${DISTDIR}/loop.o: src/loop.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/loop.o src/loop.cpp

build/${DISTDIR}/modell.o: src/modell.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/modell.o src/modell.cpp

build/${DISTDIR}/part.o: src/part.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/part.o src/part.cpp

build/${DISTDIR}/partanimation.o: src/partanimation.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/partanimation.o src/partanimation.cpp

build/${DISTDIR}/rectangle.o: src/rectangle.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/rectangle.o src/rectangle.cpp

build/${DISTDIR}/screen.o: src/screen.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/screen.o src/screen.cpp

build/${DISTDIR}/text.o: src/text.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/text.o src/text.cpp

build/${DISTDIR}/textthing.o: src/textthing.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/textthing.o src/textthing.cpp

build/${DISTDIR}/thing.o: src/thing.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/thing.o src/thing.cpp

build/${DISTDIR}/thingcontainer.o: src/thingcontainer.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/thingcontainer.o src/thingcontainer.cpp

build/${DISTDIR}/triangle.o: src/triangle.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/triangle.o src/triangle.cpp

build/${DISTDIR}/types.o: src/types.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/types.o src/types.cpp

build/${DISTDIR}/aboutscreen.o: src/aboutscreen.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/aboutscreen.o src/aboutscreen.cpp

build/${DISTDIR}/animalanimation.o: src/animalanimation.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/animalanimation.o src/animalanimation.cpp

build/${DISTDIR}/arrow.o: src/arrow.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/arrow.o src/arrow.cpp

build/${DISTDIR}/arrowcollisionhandler.o: src/arrowcollisionhandler.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/arrowcollisionhandler.o src/arrowcollisionhandler.cpp

build/${DISTDIR}/firstscreen.o: src/firstscreen.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/firstscreen.o src/firstscreen.cpp

build/${DISTDIR}/galleryscreen.o: src/galleryscreen.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/galleryscreen.o src/galleryscreen.cpp

build/${DISTDIR}/huntingscreen.o: src/huntingscreen.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/huntingscreen.o src/huntingscreen.cpp

build/${DISTDIR}/joystickimpl.o: src/joystickimpl.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/joystickimpl.o src/joystickimpl.cpp

build/${DISTDIR}/lakotacollisionhandler.o: src/lakotacollisionhandler.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/lakotacollisionhandler.o src/lakotacollisionhandler.cpp

build/${DISTDIR}/morescreen.o: src/morescreen.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/morescreen.o src/morescreen.cpp

build/${DISTDIR}/mountedlakota.o: src/mountedlakota.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/mountedlakota.o src/mountedlakota.cpp

build/${DISTDIR}/mustang.o: src/mustang.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/mustang.o src/mustang.cpp

build/${DISTDIR}/prairie.o: src/prairie.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/prairie.o src/prairie.cpp

build/${DISTDIR}/rope.o: src/rope.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/rope.o src/rope.cpp

build/${DISTDIR}/shootinglakotaanimation.o: src/shootinglakotaanimation.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/shootinglakotaanimation.o src/shootinglakotaanimation.cpp

build/${DISTDIR}/sioux.o: src/sioux.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/sioux.o src/sioux.cpp

build/${DISTDIR}/tatanka.o: src/tatanka.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/tatanka.o src/tatanka.cpp

build/${DISTDIR}/tatankaherd.o: src/tatankaherd.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/tatankaherd.o src/tatankaherd.cpp

build/${DISTDIR}/tatankamodell.o: src/tatankamodell.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/tatankamodell.o src/tatankamodell.cpp

build/${DISTDIR}/tatankatypes.o: src/tatankatypes.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/tatankatypes.o src/tatankatypes.cpp

build/${DISTDIR}/texts.o: src/texts.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/texts.o src/texts.cpp

build/${DISTDIR}/os.o: src/os.cpp
	${MKDIR} -p build/${DISTDIR}
	$(CC) -I${INCLUDE} ${CFLAGS} -o build/${DISTDIR}/os.o src/os.cpp

clean:
	${RM} -rf build/${DISTDIR}/* build

