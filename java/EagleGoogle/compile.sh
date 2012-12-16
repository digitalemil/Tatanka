#!/bin/bash
rm -fr build
mkdir build

javac -cp lib/gwt-user.jar:lib/gwt-dev.jar -sourcepath src -d build src/de/digitalemil/eagle/*java src/de/digitalemil/tatanka/*java src/de/digitalemil/client/*java

java -cp lib/gwt-dev.jar:lib/gwt-user.jar:lib/validation-api-1.0.0.GA-sources.jar:lib/validation-api-1.0.0.GA.jar:build:src com.google.gwt.dev.Compiler -war www de.digitalemil.EagleGoogle

rm -fr www/WEB-INF
