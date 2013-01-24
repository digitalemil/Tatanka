#!/bin/sh
export J2CPP_HOME=.

java -cp $J2CPP_HOME/lib/*:$J2CPP-HOM7bin de.digitalemil.tocplusplus.Main $*
