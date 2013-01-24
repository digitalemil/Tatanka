#!/bin/sh
export J2CPP_HOME=.

java -cp "lib/j2cpp/*" de.digitalemil.tocplusplus.Main $*
